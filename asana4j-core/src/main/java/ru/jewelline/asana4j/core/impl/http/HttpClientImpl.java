package ru.jewelline.asana4j.core.impl.http;


import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpRequest;
import ru.jewelline.asana4j.http.HttpRequestBuilder;
import ru.jewelline.asana4j.http.HttpResponse;
import ru.jewelline.asana4j.http.NetworkException;
import ru.jewelline.asana4j.utils.PreferencesService;
import ru.jewelline.asana4j.utils.ServiceLocator;

import java.io.ByteArrayOutputStream;
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

    private final ServiceLocator serviceLocator;

    public HttpClientImpl(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    @Override
    public HttpRequestBuilder newRequest() {
        return new HttpRequestBuilderImpl(this.serviceLocator, this);
    }

    public HttpResponse execute(HttpRequest request, HttpRequestTypeImpl requestMethod) {
        if (request == null || requestMethod == null) {
            new NetworkException(NetworkException.YOU_ARE_TRYING_TO_SEND_EMPTY_REQUEST,
                    "You are trying to send an empty request. It is not allowed.");
        }
        HttpResponseImpl response = new HttpResponseImpl(request);
        int retryCount = this.serviceLocator.getPreferencesService()
                .getInteger(PreferencesService.NETWORK_MAX_RETRY_COUNT, 3);
        int connectionTimeout = this.serviceLocator.getPreferencesService()
                .getInteger(PreferencesService.NETWORK_CONNECTION_TIMEOUT, 30000);
        String requestedUrl = request.getUrl();
        for(int current = 0; current < retryCount; current++) {
            try {
                URL url = new URL(requestedUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                Map<String, String> headers = request.getHeaders();
                if (!headers.isEmpty()) {
                    for (Map.Entry<String, String> header : headers.entrySet()) {
                        connection.setRequestProperty(header.getKey(), header.getValue());
                    }
                }
                connection.setConnectTimeout(connectionTimeout);
                connection.setDoInput(true);
                requestMethod.handleRequest(request, connection);
                InputStream serverAnswerStream = null;
                response.setResponseCode(connection.getResponseCode());
                if (response.status() == -1){
                    break; // retry the request
                } else if (response.status() >= 400 && response.status() < 600){
                    serverAnswerStream = connection.getErrorStream();
                } else {
                    serverAnswerStream = connection.getInputStream();
                }
                if (serverAnswerStream != null){
                    ByteArrayOutputStream responseStream = new ByteArrayOutputStream(getContentLength(connection));
                    copyStreams(serverAnswerStream, responseStream);
                    response.setResponseBody(responseStream.toByteArray());
                }
                break;
            } catch (MalformedURLException badUrlEx) {
                // do not try again, a client provided a bad url
                NetworkException netException = new NetworkException(NetworkException.MALFORMED_URL);
                netException.setRequestUrl(requestedUrl);
                throw netException;
            } catch (SocketTimeoutException timeoutEx) {
                if (retryCount >= current){
                    NetworkException netException = new NetworkException(NetworkException.CONNECTION_TIMED_OUT);
                    netException.setRequestUrl(requestedUrl);
                    throw netException;
                }
                break;
            } catch (IOException ioEx) {
                if (retryCount >= current){
                    NetworkException netException = new NetworkException(NetworkException.COMMUNICATION_FAILED,
                            ioEx.getLocalizedMessage());
                    netException.setRequestUrl(requestedUrl);
                    throw netException;
                }
                break;
            }
        }
        return response;
    }

    private int getContentLength(HttpURLConnection connection) {
        int contentLength = 32; // See javadoc for ByteArrayOutputStream
        String contentLengthAsString = connection.getHeaderField("Content-length");
        if (contentLengthAsString != null) {
            try {
                contentLength = Integer.parseInt(contentLengthAsString);
            } catch (NumberFormatException ex){
                contentLength = 1024;
            }
        }
        return contentLength;
    }

    public static void copyStreams(InputStream source, OutputStream destination) throws IOException{
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
    private static void close(Closeable stream){
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception ex){
            }
        }
    }
}
