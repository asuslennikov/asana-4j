package ru.jewelline.request.http.impl;

import ru.jewelline.request.http.HttpRequest;
import ru.jewelline.request.http.HttpRequestBuilder;
import ru.jewelline.request.http.HttpRequestFactory;
import ru.jewelline.request.http.HttpResponse;
import ru.jewelline.request.http.NetworkException;
import ru.jewelline.request.http.UrlBuilder;
import ru.jewelline.request.http.config.HttpConfiguration;
import ru.jewelline.request.http.modifiers.RequestModifier;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Map;

public class HttpRequestFactoryImpl implements HttpRequestFactory {
    private static final int NO_SERVER_RESPONSE_CODE = -1;

    private final HttpConfiguration httpConfig;

    public HttpRequestFactoryImpl(HttpConfiguration httpConfig) {
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
    public HttpConfiguration getHttpConfiguration() {
        return this.httpConfig;
    }

    @Override
    public UrlBuilder urlBuilder() {
        return new UrlBuilderImpl(this.httpConfig.getUrlCharset());
    }

    @Override
    public HttpRequestBuilder newRequest(RequestModifier... requestModifiers) {
        if (requestModifiers == null || requestModifiers.length == 0){
            return new HttpRequestBuilderImpl(this);
        }
        return new HttpRequestBuilderWithModifiiers(this).withRequestModifiers(requestModifiers);
    }

    <T extends OutputStream> HttpResponse<T> execute(HttpRequest request, HttpResponseImpl<T> response) {
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

    HttpURLConnection createConnection(HttpRequest request) throws IOException {
        URL url = new URL(request.getUrl());
        return (HttpURLConnection) url.openConnection();
    }

    private HttpURLConnection configureConnection(HttpURLConnection connection, HttpRequest request) {
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

    private void readServerResponse(HttpResponseImpl httpResponse, HttpURLConnection connection) throws IOException {
        httpResponse.setCode(connection.getResponseCode());
        if (httpResponse.code() == NO_SERVER_RESPONSE_CODE) {
            return;
        }
        httpResponse.setHeaders(connection.getHeaderFields());
        if (httpResponse.payload() == null) {
            return;
        }
        InputStream serverResponseStream;
        if (httpResponse.code() >= 400 && httpResponse.code() < 600) {
            serverResponseStream = connection.getErrorStream();
        } else {
            serverResponseStream = connection.getInputStream();
        }
        copyStreams(serverResponseStream, httpResponse.payload());
    }
}
