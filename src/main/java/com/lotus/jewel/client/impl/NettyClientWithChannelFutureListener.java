package com.lotus.jewel.client.impl;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lotus.jewel.client.Client;
import com.lotus.jewel.wrapper.ClassConstructorWrapper;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;

public class NettyClientWithChannelFutureListener <CIHA extends ChannelInboundHandlerAdapter> implements Client{
	
	final static Logger logger = LoggerFactory.getLogger(NettyClientWithChannelFutureListener.class);

	private String ipAddress;
	
	private int port;
	
	private EventLoopGroup group;
	
	private ClassConstructorWrapper<CIHA> handlerWrapper;
	
	public NettyClientWithChannelFutureListener(final String ipAddress, final int port
			, ClassConstructorWrapper<CIHA> handlerWrapper) {
		this.ipAddress = ipAddress;
		this.port = port;
		this.handlerWrapper = handlerWrapper;
	}
	
	@Override
    public void start() throws Exception {
		if(handlerWrapper == null) {
    		throw new Exception("handler is null");
    	}
		
        group = new NioEventLoopGroup();
        
        try {
            Bootstrap bootstrap = new Bootstrap();  // bootstrap 생성
            bootstrap.group(group)  // 클라이언트 이벤트 처리할 EventLoopGroup을 지정.
                    .channel(NioSocketChannel.class)    // 채널 유형 NIO 지정
                    .remoteAddress(new InetSocketAddress(ipAddress, port)) // 서버의 InetSocketAddress를 설정
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {
                        	// 채널이 생성될 때 파이프라인에 EchoClientHandler 하나를 추가
                        	
                        	CIHA adapter = handlerWrapper.getInstance();
                        	if(adapter != null) {       			
                    			channel.pipeline().addLast(adapter);
                    		}
                        }
                    });
            ChannelFuture future = bootstrap.connect();   // 원격 피어로 연결
            
            
            //occur java.io.IOException: Connection reset by peer
            future.addListener(new ChannelFutureListener() {
				
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if(future.isSuccess()) {
						ByteBuf buffer = Unpooled.copiedBuffer("ChannelFutureListener hi", CharsetUtil.UTF_8);
						ChannelFuture wf = future.channel().writeAndFlush(buffer);
					} else {
						Throwable cause = future.cause();
						cause.printStackTrace();
					}
					
				}
			});
            
            future.sync();	// 연결이 완료되기를 기다림
            
            
            future.channel().closeFuture().sync();   // 채널이 닫힐 때까지 블로킹함.
        } finally {
            group.shutdownGracefully().sync();  // 스레드 풀을 종료하고 모든 리소스를 해제함
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
}
