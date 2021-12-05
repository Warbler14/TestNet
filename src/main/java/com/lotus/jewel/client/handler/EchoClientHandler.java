package com.lotus.jewel.client.handler;

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

public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
	
	final static Logger logger = LoggerFactory.getLogger(EchoClientHandler.class);
	
	private String channelActiveMessage;
	
	public EchoClientHandler(String channelActiveMessage) {
		this.channelActiveMessage = channelActiveMessage;
	}

	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
		if(channelActiveMessage != null) {
			Object message = StringUtil.stringToByteBuf(channelActiveMessage);
			ctx.writeAndFlush(message);			
		}
    }
	
	@Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        logger.debug("Client receive : " + msg.toString(CharsetUtil.UTF_8));
        
        
        ChannelFuture future = null;
        future = ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
        future.addListener(ChannelFutureListener.CLOSE);
        
//        future = ctx.close();
//        future = ctx.disconnect();
        
//        future = ctx.channel().closeFuture();
//        future.channel().close();
        
        
        
    }
 
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();    // 예외 시 오류를 로깅하고 채널 닫기
        ctx.close();
    }

}
