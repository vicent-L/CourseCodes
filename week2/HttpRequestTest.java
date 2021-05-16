package com.nio.test;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @Author lzg
 * @Date 2021-05-13 21:21
 */
public class HttpRequestTest {

    public static void main(String[] args) {
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(6 * 1000).setConnectTimeout(6 * 1000).build();

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();

        HttpGet httpGet = new HttpGet("http://localhost:8008/test");
        String respResult = requestByHttpClient(httpClient, httpGet);
        System.out.println(respResult);
    }

    public static String requestByHttpClient(HttpClient httpClient, HttpUriRequest request) {

        CloseableHttpResponse response = null;
        try {
            response = (CloseableHttpResponse) httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, Charset.defaultCharset());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {

                }
            }
        }
        return null;
    }
}
