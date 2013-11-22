package com.sohu.saccount.thrift;

import com.sohu.suc.thrift.pool.ThriftConnectionManager;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

/**
 * User: guohaozhao (guohaozhao116008@sohu-inc.com)
 * Date: 11/20/13 11:13
 */
public class ThriftClient implements TestService.Iface {

    private static final Logger logger = Logger.getLogger(ThriftClient.class);

    public void setThriftConnectionManager(ThriftConnectionManager thriftConnectionManager) {
        this.thriftConnectionManager = thriftConnectionManager;
    }

    private ThriftConnectionManager thriftConnectionManager;

    private TestService.Client getClient() {
        TProtocol protocol = new TBinaryProtocol(thriftConnectionManager.getSocket());
        TestService.Client client = new TestService.Client(protocol);
        return client;
    }

    @Override
    public String getPayLoad() throws TException {
        return getClient().getPayLoad();
    }

}
