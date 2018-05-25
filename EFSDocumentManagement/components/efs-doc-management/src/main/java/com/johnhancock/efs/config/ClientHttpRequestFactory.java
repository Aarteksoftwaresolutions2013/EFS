package com.johnhancock.efs.config;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.net.HttpURLConnection;

public class ClientHttpRequestFactory extends SimpleClientHttpRequestFactory {
    public ClientHttpRequestFactory() { }

    private static ClientHttpRequestFactory clientHttpRequestFactory = null;

    public static synchronized ClientHttpRequestFactory getInstance(){
        return (clientHttpRequestFactory == null) ? new ClientHttpRequestFactory() : clientHttpRequestFactory;
    }

    @Override
    protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
        if (connection instanceof HttpsURLConnection) {
            HttpsURLConnection huc= ((HttpsURLConnection) connection);
            huc.setHostnameVerifier(getHostnameVerifier());
            huc.setConnectTimeout(30000);
        }
        super.prepareConnection(connection, httpMethod);
    }

    private HostnameVerifier getHostnameVerifier(){
        return new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
    }

}
