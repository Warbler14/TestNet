package com.lotus.jewel.server.impl;

import java.lang.reflect.Constructor;
import java.net.InetSocketAddress;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lotus.jewel.server.Server;
import com.lotus.jewel.server.handler.ChannelGroupServerHandler;
import com.lotus.jewel.server.handler.EchoServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;

public class NettyServerWithMultiHandler implements Server{
	
	final static Logger logger = LoggerFactory.getLogger(NettyServerWithMultiHandler.class);

	private final int port;
	
	private EventLoopGroup group;
	
	private List<ChannelInboundHandlerWrapper> handlerWrapperList;
	 
    public NettyServerWithMultiHandler(final int port
    		, List<ChannelInboundHandlerWrapper> channelInboundHandlerWrapperList) {
        
    	this.port = port;
        this.handlerWrapperList = channelInboundHandlerWrapperList;
    }
    
    public void start() throws Exception {
    	if(handlerWrapperList == null) {
    		throw new Exception("handlers is null");
    	}
    	
        group = new NioEventLoopGroup(); // EventLoopGroup 생성
        
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();  // ServerBootstrap 생성
            bootstrap.group(group)
                    .channel(NioServerSocketChannel.class)  // NIO 전송채널을 이용하도록 지정
                    .localAddress(new InetSocketAddress(port))  // 지정된 포트로 소켓 주소 설정
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                        	// EchoServerHandler 하나를 채널의 Channel Pipeline 으로 추가
                            //channel.pipeline().addLast(handler);
                        	
                        	ChannelPipeline pipeline = channel.pipeline();
                        	
                        	for (int idx = 0; idx < handlerWrapperList.size() ; idx++ ) {
                        		ChannelInboundHandlerWrapper channelInboundHandlerWrapper = handlerWrapperList.get(idx);
                        		
                        		Constructor<? extends ChannelInboundHandlerAdapter> handlerConstructor 
                        			= channelInboundHandlerWrapper.getHandlerConstructor();
                        		Object hadlerParameter = channelInboundHandlerWrapper.getHadlerParameter();
                        		
                        		ChannelInboundHandlerAdapter adapter = handlerConstructor.newInstance(hadlerParameter);
                        		
                        		pipeline.addLast(String.valueOf(idx), adapter);
                        		
							}
                        	
                        }
                    });
            
            ChannelFuture future = bootstrap.bind().sync();  // 서버를 비동기식으로 바인딩
            future.channel().closeFuture().sync();   // 채널의 CloseFuture를 얻고 완료될 때까지 현재 스레드를 블로킹
        } finally {
            group.shutdownGracefully().sync();  // EventLoopGroup을 종료하고 모든 리소스 해제
        }
    }

	@Override
	public boolean isShuttingDown() {
		if(group != null) {
			return group.isShutdown();			
		}
		
		return false;
	}

	@Override
	public Future<?> shutdownGracefully() {
		if(group != null) {
			try {
				group.shutdownGracefully().sync();
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
			}
		}
		
		return null;
	}

	@Override
	public void shutdown() {
		shutdownGracefully();
	}
	
	public static class ChannelInboundHandlerWrapper {
		
		private Constructor<? extends ChannelInboundHandlerAdapter> handlerConstructor;
		
		public Object hadlerParameter;

		public Constructor<? extends ChannelInboundHandlerAdapter> getHandlerConstructor() {
			return handlerConstructor;
		}

		public void setHandlerConstructor(Constructor<? extends ChannelInboundHandlerAdapter> handlerConstructor) {
			this.handlerConstructor = handlerConstructor;
		}

		public Object getHadlerParameter() {
			return hadlerParameter;
		}

		public void setHadlerParameter(Object hadlerParameter) {
			this.hadlerParameter = hadlerParameter;
		}
		
	}
}
