package ru.jewelline.asana4j.core.impl.http;

import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpRequest;
import ru.jewelline.asana4j.http.HttpRequestBuilder;
import ru.jewelline.asana4j.http.HttpResponse;
import ru.jewelline.asana4j.http.NetworkException;
import ru.jewelline.asana4j.utils.URLBuilder;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class HttpClientImpl implements HttpClient {
    private static final int NO_SERVER_RESPONSE_CODE = -1;

    private final URLBuilder urlBuilder;
    private final HttpConfiguration httpConfig;

    public HttpClientImpl(URLBuilder urlBuilder, HttpConfiguration httpConfig) {
        this.urlBuilder = urlBuilder;
        this.httpConfig = httpConfig;
    }

    public static void copyStreams(InputStream source, OutputStream destination) throws IOException {
        if (source != null && destination != null) {
            try {
                int length;
                byte[] buffer = new byte[8 * 1024];
                while ((length = source.read(buffer)) > 0) {
                    destination.write(buffer, 0, length);
                }
                destination.flush();
            } finally {
                close(source);
                close(destination);
            }
        }
    }

    private static void close(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception ignored) {
                // ignore this exception because we don't want to work with the stream any more
            }
        }
    }

    @Override
    public HttpRequestBuilder newRequest() {
        return new HttpRequestBuilderImpl(this.urlBuilder, this);
    }

    public <T extends OutputStream> HttpResponse<T> execute(HttpRequestImpl request, HttpResponseImpl<T> response) {
        if (request == null || response == null) {
            throw new NetworkException(NetworkException.YOU_ARE_TRYING_TO_SEND_EMPTY_REQUEST,
                    "You are trying to send an empty request. It is not allowed.");
        }
        int retryCount = this.httpConfig.getRetryCount();
        for (int current = 0; current < retryCount; current++) {
            try {
                HttpURLConnection connection = configureConnection(createConnection(request), request);
                HttpMethodSettings.getForHttpMethod(request.getMethod()).apply(connection, request);
                readServerResponse(response, connection);
                if (response.code() == NO_SERVER_RESPONSE_CODE) {
                    continue; // retry the request
                }
                break;
            } catch (MalformedURLException badUrlEx) {
                // do not try again, a client provided a bad url
                NetworkException netException = new NetworkException(NetworkException.MALFORMED_URL);
                netException.setRequestUrl(request.getUrl());
                throw netException;
            } catch (SocketTimeoutException timeoutEx) {
                if (current >= retryCount) {
                    NetworkException netException = new NetworkException(NetworkException.CONNECTION_TIMED_OUT);
                    netException.setRequestUrl(request.getUrl());
                    throw netException;
                }
            } catch (IOException ioEx) {
                if (current >= retryCount) {
                    NetworkException netException = new NetworkException(NetworkException.COMMUNICATION_FAILED,
                            ioEx.getLocalizedMessage());
                    netException.setRequestUrl(request.getUrl());
                    throw netException;
                }
            }
        }
        return response;
    }

    protected HttpURLConnection createConnection(HttpRequest request) throws IOException {
        URL url = new URL(request.getUrl());
        return (HttpURLConnection) url.openConnection();
    }

    protected HttpURLConnection configureConnection(HttpURLConnection connection, HttpRequest request) {
        Map<String, String> headers = request.getHeaders();
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                connection.setRequestProperty(header.getKey(), header.getValue());
            }
        }
        connection.setConnectTimeout(this.httpConfig.getConnectionTimeout());
        connection.setDoInput(true);
        return connection;
    }

    protected void readServerResponse(HttpResponseImpl<?> httpResponse, HttpURLConnection connection) throws IOException {
        httpResponse.setCode(connection.getResponseCode());
        if (httpResponse.code() == NO_SERVER_RESPONSE_CODE) {
            return;
        }
        Map<String, List<String>> headers = connection.getHeaderFields();
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, List<String>> header : headers.entrySet()) {
                httpResponse.setHeader(header.getKey(), header.getValue());
            }
        }
        if (httpResponse.output() == null){
            return;
        }
        InputStream serverResponseStream;
        if (httpResponse.code() >= 400 && httpResponse.code() < 600) {
            serverResponseStream = connection.getErrorStream();
        } else {
            serverResponseStream = connection.getInputStream();
        }
        copyStreams(serverResponseStream, httpResponse.output());
    }
}
