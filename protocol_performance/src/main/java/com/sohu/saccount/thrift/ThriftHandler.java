package com.sohu.saccount.thrift;

import com.sohu.saccount.performanceHandler.EightKHandler;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;

/**
 * User: guohaozhao (guohaozhao116008@sohu-inc.com)
 * Date: 11/20/13 11:06
 */
public class ThriftHandler implements TestService.Iface {

    private static final Logger logger = Logger.getLogger(ThriftHandler.class);

    @Override
    public String getPayLoad() throws TException {
        TestModel testModel = new TestModel().setPayload(new EightKHandler().handle());
        logger.info("handle one thrift request");
        return testModel.getPayload();
    }
}
