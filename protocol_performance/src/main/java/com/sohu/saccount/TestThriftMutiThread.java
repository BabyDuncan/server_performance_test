package com.sohu.saccount;

import com.google.common.base.Strings;
import com.sohu.saccount.thrift.ThriftClient;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * User: guohaozhao (guohaozhao116008@sohu-inc.com)
 * Date: 11/20/13 16:34
 */
public class TestThriftMutiThread {

    private static final Logger logger = Logger.getLogger(TestThriftMutiThread.class);

    public static void main(String[] args) throws TException, InterruptedException {

        int thread = 100;
        int loop = 100;
        if (args.length==2 && Strings.isNullOrEmpty(args[0])) {
            thread = Integer.parseInt(args[0]);
        }
        if (args.length==2 && Strings.isNullOrEmpty(args[1])) {
            loop = Integer.parseInt(args[1]);
        }

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:performance/applicationContext.xml");
        final ThriftClient thriftClient = (ThriftClient) applicationContext.getBean("thriftClient");
        long l1 = System.currentTimeMillis();
        final CountDownLatch countDownLatch = new CountDownLatch(thread);
        for (int i = 0; i < thread; i++) {
            final int finalLoop = loop;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < finalLoop; i++) {
                            System.out.println(thriftClient.getPayLoad());
                        }
                        countDownLatch.countDown();
                    } catch (TException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }).start();

        }
        countDownLatch.await();
        long l2 = System.currentTimeMillis();
        System.out.println("done !!" + (l2 - l1));
        //1649 ms
        //1000*100 10500ms
    }

}
