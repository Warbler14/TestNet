package com.lotus.jewel.run.server;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import com.lotus.jewel.server.Server;
import com.lotus.jewel.server.handler.ChannelGroupServerHandler;
import com.lotus.jewel.server.handler.EchoServerHandler;
import com.lotus.jewel.server.handler.ChannelGroupServerHandler.ChannelGroupWrapper;
import com.lotus.jewel.server.impl.NettyServerWithMultiHandler;
import com.lotus.jewel.server.impl.NettyServerWithMultiHandler.ChannelInboundHandlerWrapper;

import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerWithMultiHandlerRun {

	public static void main(String[] args) throws Exception {
		int serverPort = 8888;
		
		List<ChannelInboundHandlerWrapper> list
			= new ArrayList<ChannelInboundHandlerWrapper>();
		
		appendChannelGroupServerHandler(list);
		appendEchoServerHandler(list);
		
		
		Server server = new NettyServerWithMultiHandler(serverPort, list);
		server.start();
	}
	
	private static void appendEchoServerHandler(
			List<ChannelInboundHandlerWrapper> channelInboundHandlerWrapperList) throws Exception{
		Constructor<? extends ChannelInboundHandlerAdapter> handlerConstructor
			= EchoServerHandler.class.getConstructor(new Class[]{Object.class});
	
		ChannelInboundHandlerWrapper wrapper = new ChannelInboundHandlerWrapper();
		wrapper.setHandlerConstructor(handlerConstructor);
		
		channelInboundHandlerWrapperList.add(wrapper);
	}

	private static void appendChannelGroupServerHandler(
			List<ChannelInboundHandlerWrapper> channelInboundHandlerWrapperList) throws Exception{
		Constructor<? extends ChannelInboundHandlerAdapter> handlerConstructor
			= ChannelGroupServerHandler.class.getConstructor(new Class[]{ChannelGroupWrapper.class});
	
		ChannelInboundHandlerWrapper wrapper = new ChannelInboundHandlerWrapper();
		wrapper.setHandlerConstructor(handlerConstructor);
		wrapper.setHadlerParameter(new ChannelGroupWrapper());
		
		channelInboundHandlerWrapperList.add(wrapper);
	}
}