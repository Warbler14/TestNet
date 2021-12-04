package com.lotus.jewel.net.echo.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
	
	final static Logger logger = LoggerFactory.getLogger(EchoClientHandler.class);
	
	private String channelActiveMessage;
	
	public EchoClientHandler(String channelActiveMessage) {
		this.channelActiveMessage = channelActiveMessage;
	}

	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
		if(channelActiveMessage != null) {
			Object message = buildMessage(channelActiveMessage);
			ctx.writeAndFlush(message);			
		}
    }
	
	private Object buildMessage(String message) {
		if(message != null) {
			return Unpooled.copiedBuffer(message, CharsetUtil.UTF_8);
		}
		
		return null;
	}
	
	@Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        logger.debug("Client receive : " + msg.toString(CharsetUtil.UTF_8));
    }
 
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();    // 예외 시 오류를 로깅하고 채널 닫기
        ctx.close();
    }

}
