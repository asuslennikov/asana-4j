package ru.jewelline.asana4j.core.impl.http;

import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpRequestBuilder;
import ru.jewelline.asana4j.http.HttpResponse;
import ru.jewelline.asana4j.http.NetworkException;
import ru.jewelline.asana4j.utils.PreferencesService;
import ru.jewelline.asana4j.utils.URLBuilder;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Map;

public class HttpClientImpl implements HttpClient {
    private static final int NO_SERVER_RESPONSE_CODE = -1;

    private final URLBuilder urlBuilder;
    private final PreferencesService preferencesService;

    public HttpClientImpl(URLBuilder urlBuilder, PreferencesService preferencesService) {
        this.urlBuilder = urlBuilder;
        this.preferencesService = preferencesService;
    }

    public static void copyStreams(InputStream source, OutputStream destination) throws IOException {
        if (source != null && destination != null) {
            try {
                int length = 0;
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
            } catch (Exception ex) {
            }
        }
    }

    @Override
    public HttpRequestBuilder newRequest() {
        return new HttpRequestBuilderImpl(this.urlBuilder, this);
    }

    public <O extends OutputStream> HttpResponse<O> execute(HttpRequestImpl<O> request, HttpMethodWorker requestWorker) {
        if (request == null || requestWorker == null) {
            new NetworkException(NetworkException.YOU_ARE_TRYING_TO_SEND_EMPTY_REQUEST,
                    "You are trying to send an empty request. It is not allowed.");
        }
        int retryCount = this.preferencesService.getInteger(PreferencesService.NETWORK_MAX_RETRY_COUNT, 3);
        HttpResponseImpl<O> response = new HttpResponseImpl<>(request.getDestinationStream());
        for (int current = 0; current < retryCount; current++) {
            try {
                HttpURLConnection connection = configureConnection(request, openConnection(request));
                requestWorker.process(request, connection);
                response.setCode(readServerResponse(request, connection));
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
                if (retryCount >= current) {
                    NetworkException netException = new NetworkException(NetworkException.CONNECTION_TIMED_OUT);
                    netException.setRequestUrl(request.getUrl());
                    throw netException;
                }
            } catch (IOException ioEx) {
                if (retryCount >= current) {
                    NetworkException netException = new NetworkException(NetworkException.COMMUNICATION_FAILED,
                            ioEx.getLocalizedMessage());
                    netException.setRequestUrl(request.getUrl());
                    throw netException;
                }
            }
        }
        return response;
    }

    protected HttpURLConnection openConnection(HttpRequestImpl request) throws IOException {
        URL url = new URL(request.getUrl());
        return (HttpURLConnection) url.openConnection();
    }

    protected HttpURLConnection configureConnection(HttpRequestImpl request, HttpURLConnection connection) {
        int connectionTimeout = this.preferencesService.getInteger(PreferencesService.NETWORK_CONNECTION_TIMEOUT, 30000);
        Map<String, String> headers = request.getHeaders();
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                connection.setRequestProperty(header.getKey(), header.getValue());
            }
        }
        connection.setConnectTimeout(connectionTimeout);
        connection.setDoInput(request.getDestinationStream() != null);
        return connection;
    }

    protected int readServerResponse(HttpRequestImpl request, HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();
        InputStream serverAnswerStream = null;
        if (responseCode == NO_SERVER_RESPONSE_CODE || request.getDestinationStream() == null) {
            return responseCode;
        } else if (responseCode >= 400 && responseCode < 600) {
            serverAnswerStream = connection.getErrorStream();
        } else {
            serverAnswerStream = connection.getInputStream();
        }
        copyStreams(serverAnswerStream, request.getDestinationStream());
        return responseCode;
    }
}
