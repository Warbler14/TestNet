package com.lotus.jewel.server.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;


public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	
	final static Logger logger = LoggerFactory.getLogger(EchoServerHandler.class);
	
	public EchoServerHandler() {}
	
	public EchoServerHandler(Object o) {}
	
	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("EchoServerHandler channelActive");
    }
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf)msg;
        logger.debug("Server received : " + in.toString(CharsetUtil.UTF_8));
        
        ctx.write(in); // 받은 메시지를 발신자에게로 Echo 시킨다.
    }
 
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.debug("channelReadComplete");
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER); // 대기중인 메시지를 플러시
        
//        future.addListener(ChannelFutureListener.CLOSE); //채널을 닫음
    }
 
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();	// 채널 닫기
    }
}
