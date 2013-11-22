package com.sohu.saccount;

import com.sohu.saccount.thrift.ThriftClient;
import org.apache.thrift.TException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 */
public class TestThrift {
    public static void main(String[] args) throws TException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:performance/applicationContext.xml");
        ThriftClient thriftClient = (ThriftClient) applicationContext.getBean("thriftClient");
        long l1 = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            System.out.println(thriftClient.getPayLoad());
        }
        long l2 = System.currentTimeMillis();
        System.out.println("done !!" + (l2 - l1));
        //26884 ms
    }
}
