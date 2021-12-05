package com.lotus.jewel.client.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lotus.jewel.util.StringUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
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
				ctx.write(StringUtil.stringToByteBuf(message));
			}
			ctx.flush();
		}
    }
	
	@Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		logger.debug("Client receive : " + msg.toString(CharsetUtil.UTF_8));
		
		ChannelFuture future = null;
        future = ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
        future.addListener(ChannelFutureListener.CLOSE);
    }
 
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
