package com.lotus.jewel.net.echo.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class RepeatedlyEchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
	
	final static Logger logger = LoggerFactory.getLogger(RepeatedlyEchoClientHandler.class);
	
	private List<String> messageList;
	
	public RepeatedlyEchoClientHandler(List<String> messageList) {
		this.messageList = messageList;
	}

	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
		if(messageList != null) {
			for (String message : messageList) {
				ctx.writeAndFlush(buildMessage(message));
			}
		}
    }
	
	private Object buildMessage(String message) {
		if(message != null) {
			return Unpooled.copiedBuffer(message, CharsetUtil.UTF_8);
		}
		
		return null;
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		 if(logger.isDebugEnabled()) {
        	logger.debug("channelRead");
        }
	      
	}
	
	
	@Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        if(logger.isDebugEnabled()) {
        	logger.debug("Client receive : " + msg.toString(CharsetUtil.UTF_8));
        }
    }
 
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();    // 예외 시 오류를 로깅하고 채널 닫기
        ctx.close();
    }

}
