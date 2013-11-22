package com.sohu.saccount.netty;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * User: guohaozhao (guohaozhao116008@sohu-inc.com)
 * Date: 11/20/13 17:19
 */
public class NettyServer {

    private static final Logger logger = Logger.getLogger(NettyServer.class);

    public static void main(String[] args) {
        start(8083);
    }

    public static void start(int port) {
        // 配置服务器-使用java线程池作为解释线程
        ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        // 设置 pipeline factory.
        bootstrap.setPipelineFactory(new ServerPipelineFactory());
        // 绑定端口
        bootstrap.bind(new InetSocketAddress(port));
        System.out.println("admin start on " + port);
    }

    private static class ServerPipelineFactory implements
            ChannelPipelineFactory {
        public ChannelPipeline getPipeline() throws Exception {
            // Create a default pipeline implementation.
            ChannelPipeline pipeline = Channels.pipeline();
            pipeline.addLast("decoder", new HttpRequestDecoder());
            pipeline.addLast("encoder", new HttpResponseEncoder());
            //http处理handler
            pipeline.addLast("handler", new NettyHandler());
            return pipeline;
        }
    }

}
