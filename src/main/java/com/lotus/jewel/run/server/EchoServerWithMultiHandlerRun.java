package com.lotus.jewel.run.server;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import com.lotus.jewel.server.Server;
import com.lotus.jewel.server.handler.ChannelGroupServerHandler;
import com.lotus.jewel.server.handler.EchoServerHandler;
import com.lotus.jewel.server.handler.ChannelGroupServerHandler.ChannelGroupWrapper;
import com.lotus.jewel.server.impl.NettyServerWithMultiHandler;
import com.lotus.jewel.wrapper.ClassConstructorWrapper;

import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerWithMultiHandlerRun {

	public static void main(String[] args) throws Exception {
		int serverPort = 8888;

		List<ClassConstructorWrapper<ChannelInboundHandlerAdapter>> list 
			= new ArrayList<ClassConstructorWrapper<ChannelInboundHandlerAdapter>>();

		appendChannelGroupServerHandler(list);
		appendEchoServerHandler(list);

		Server server = new NettyServerWithMultiHandler<ChannelInboundHandlerAdapter>(serverPort, list);
		server.start();
	}

	private static void appendEchoServerHandler(
			List<ClassConstructorWrapper<ChannelInboundHandlerAdapter>> channelInboundHandlerWrapperList)
			throws Exception {
		Constructor<? extends ChannelInboundHandlerAdapter> handlerConstructor
			= EchoServerHandler.class.getConstructor(new Class[] { Object.class });

		ClassConstructorWrapper<ChannelInboundHandlerAdapter> wrapper 
			= new ClassConstructorWrapper<ChannelInboundHandlerAdapter>();
		wrapper.setConstructor(handlerConstructor);

		channelInboundHandlerWrapperList.add(wrapper);
	}

	private static void appendChannelGroupServerHandler(
			List<ClassConstructorWrapper<ChannelInboundHandlerAdapter>> channelInboundHandlerWrapperList)
			throws Exception {
		Constructor<? extends ChannelInboundHandlerAdapter> handlerConstructor
			= ChannelGroupServerHandler.class.getConstructor(new Class[] { ChannelGroupWrapper.class });

		ClassConstructorWrapper<ChannelInboundHandlerAdapter> wrapper
			= new ClassConstructorWrapper<ChannelInboundHandlerAdapter>();
		wrapper.setConstructor(handlerConstructor);
		wrapper.setParameter(new ChannelGroupWrapper());

		channelInboundHandlerWrapperList.add(wrapper);
	}
}