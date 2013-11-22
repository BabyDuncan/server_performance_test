package com.sohu.saccount;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * User: guohaozhao (guohaozhao116008@sohu-inc.com)
 * Date: 11/20/13 16:45
 */
public class TestHttpMutliThread {

    private static final Logger logger = Logger.getLogger(TestHttpMutliThread.class);
    static HttpClient httpClient = new HttpClient();

    public static void main(String... args) {
        long l = System.currentTimeMillis();
        final CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        System.out.println(getGetResponse("http://10.16.1.48:8080/protocol_performance/httpPerf"));
                    }
                    countDownLatch.countDown();
                }
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        long l2 = System.currentTimeMillis();
        System.out.println("done !!" + (l2 - l));
        //43833ms   resin
        //49560ms   jetty
        //66162ms   netty
        // 1000*100 resin 430437ms
    }

    public static synchronized String getGetResponse(String url) {
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
