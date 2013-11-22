package com.sohu.saccount;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;

/**
 * User: guohaozhao (guohaozhao116008@sohu-inc.com)
 * Date: 11/20/13 16:02
 * http://10.16.1.48:8080/protocol_performance/httpPerf
 * http://10.16.1.48:8081/httpPerf
 * http://10.16.1.48:8083
 */
public class TestHttp {
    static HttpClient httpClient = new HttpClient();

    public static void main(String... args) {

        long l = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            System.out.println(getGetResponse("http://10.16.1.48:8080/protocol_performance/httpPerf"));
        }
        long l2 = System.currentTimeMillis();
        System.out.println("done !!" + (l2 - l));
        //  45961 ms   resin
        //  50778 ms   jetty
        //  69072 ms   netty
    }

    public static String getGetResponse(String url) {
        String html = "";
        GetMethod getMethod = new GetMethod(url);
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + getMethod.getStatusLine());
            }
            html = getMethod.getResponseBodyAsString();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            getMethod.releaseConnection();
        }
        return html;
    }
}
