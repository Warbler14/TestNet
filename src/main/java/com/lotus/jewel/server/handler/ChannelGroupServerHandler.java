package com.lotus.jewel.server.handler;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ChannelGroupServerHandler extends ChannelInboundHandlerAdapter {
	
	final static Logger logger = LoggerFactory.getLogger(ChannelGroupServerHandler.class);
	
	private ChannelGroupWrapper channelGroup;
	
	public ChannelGroupServerHandler(ChannelGroupWrapper channelGroup) {
		if(channelGroup == null) {
			this.channelGroup = new ChannelGroupWrapper();
		} else {
			this.channelGroup = channelGroup;			
		}
	}
	
	
	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
		int count = channelGroup.counter.incrementAndGet();
		ctx.channel().attr(channelGroup.id).set(count);
		logger.info("EchoCountServerHandler channelActive count : "  + count);
		
		channelGroup.channels.add(ctx.channel());
    }
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf)msg;
        logger.debug("Server received : " + in.toString(CharsetUtil.UTF_8));
        ctx.fireChannelRead(msg);
    }
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//	    ctx.channel().attr(id).remove();
	    ctx.channel().attr(channelGroup.id).set(null);
	    channelGroup.channels.remove(ctx.channel());
	}
	
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();	// 채널 닫기
    }
    
    public static class ChannelGroupWrapper {
    	public final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    	public final AttributeKey<Integer> id = AttributeKey.newInstance("id");
    	public AtomicInteger counter = new AtomicInteger(0);
    	
    }
}
