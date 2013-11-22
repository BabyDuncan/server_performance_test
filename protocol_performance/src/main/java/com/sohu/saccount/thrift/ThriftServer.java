package com.sohu.saccount.thrift;

import com.sohu.suc.thrift.regist.ThriftServiceRegister;
import org.apache.log4j.Logger;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: guohaozhao (guohaozhao116008@sohu-inc.com)
 * Date: 11/20/13 11:06
 */
public class ThriftServer {

    private static final Logger logger = Logger.getLogger(ThriftServer.class);

    public static void main(String... args) throws TTransportException {

        TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(55555);

        TBinaryProtocol.Factory proFactory = new TBinaryProtocol.Factory();

        TestService.Iface testService = new ThriftHandler();
        TProcessor processor = new TestService.Processor<TestService.Iface>(testService);
        TServer server = new TThreadedSelectorServer(new TThreadedSelectorServer.Args(serverTransport).processor(processor)
                .protocolFactory(proFactory).selectorThreads(10).workerThreads(10));

        logger.info("thrift Server is started at port 55555");

        System.setProperty("swift.serverName", "thriftPerformance");
//        System.setProperty("swift.ip", "10.2.153.181");
        System.setProperty("swift.port", "55555");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:thrift/applicationContext.xml");
        ThriftServiceRegister thriftServiceRegister = (ThriftServiceRegister) applicationContext.getBean("thriftServiceRegisterImpl");
        thriftServiceRegister.publish();

        server.serve();
    }

}
