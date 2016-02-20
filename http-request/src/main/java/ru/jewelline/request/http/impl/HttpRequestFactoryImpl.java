package ru.jewelline.request.http.impl;

import ru.jewelline.request.http.HttpRequest;
import ru.jewelline.request.http.HttpRequestBuilder;
import ru.jewelline.request.http.HttpRequestFactory;
import ru.jewelline.request.http.HttpResponseReceiver;
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
import java.util.List;
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
        if (requestModifiers == null || requestModifiers.length == 0) {
            return new HttpRequestBuilderImpl(this);
        }
        return new HttpRequestBuilderWithModifiiers(this).withRequestModifiers(requestModifiers);
    }

    @Override
    public void execute(HttpRequest request, HttpResponseReceiver responseReceiver) {
        if (request == null) {
            throw new NetworkException(NetworkException.YOU_ARE_TRYING_TO_SEND_EMPTY_REQUEST,
                    "You are trying to send an empty request. It is not allowed.");
        }
        int retryCount = this.httpConfig.getRetryCount();
        for (int current = 1; current <= retryCount; current++) {
            try {
                HttpURLConnection connection = configureConnection(createConnection(request), request);
                HttpMethodSettings.getForHttpMethod(request.getMethod()).apply(connection, request);
                if (readServerResponse(connection, responseReceiver) == NO_SERVER_RESPONSE_CODE) {
                    continue; // retry the request
                }
                break;
            } catch (MalformedURLException badUrlEx) {
                // do not try again, the client has provided a bad url
                NetworkException netException = new NetworkException(NetworkException.MALFORMED_URL, badUrlEx.getLocalizedMessage());
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
    }

    HttpURLConnection createConnection(HttpRequest request) throws IOException {
        UrlBuilderImpl urlBuilder = (UrlBuilderImpl) urlBuilder().path(request.getUrl());
        Map<String, List<String>> queryParameters = request.getQueryParameters();
        if (queryParameters != null) {
            urlBuilder.setQueryParameter(queryParameters);
        }
        URL url = new URL(urlBuilder.build());
        return (HttpURLConnection) url.openConnection(this.httpConfig.getProxy());
    }

    private HttpURLConnection configureConnection(HttpURLConnection connection, HttpRequest request) {
        Map<String, List<String>> headers = request.getHeaders();
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, List<String>> header : headers.entrySet()) {
                for (String headerValue : header.getValue()) {
                    connection.addRequestProperty(header.getKey(), headerValue);
                }
            }
        }
        connection.setConnectTimeout(this.httpConfig.getConnectionTimeout());
        return connection;
    }

    private int readServerResponse(HttpURLConnection connection, HttpResponseReceiver responseReceiver) throws IOException {
        if (responseReceiver == null) {
            return 200; // do not read anything, just pretend that everything is ok
        }
        int responseCode = connection.getResponseCode();
        responseReceiver.setResponseCode(responseCode);
        if (responseCode == NO_SERVER_RESPONSE_CODE) {
            return NO_SERVER_RESPONSE_CODE;
        }
        responseReceiver.setResponseHeaders(connection.getHeaderFields());
        boolean inCorrectResponse = responseCode >= 400 && responseCode < 600;
        OutputStream destination = !inCorrectResponse
                ? responseReceiver.getResponseStream()
                : responseReceiver.getErrorStream();
        if (destination != null) {
            InputStream source = !inCorrectResponse
                    ? connection.getInputStream()
                    : connection.getErrorStream();
            copyStreams(source, destination);
        }
        return responseCode;
    }
}
