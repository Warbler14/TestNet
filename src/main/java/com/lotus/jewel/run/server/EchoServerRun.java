package com.lotus.jewel.run.server;

import java.lang.reflect.Constructor;

import com.lotus.jewel.server.Server;
import com.lotus.jewel.server.handler.EchoServerHandler;
import com.lotus.jewel.server.impl.NettyServer;
import com.lotus.jewel.wrapper.ClassConstructorWrapper;

import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerRun {

	public static void main(String[] args) throws Exception {
		int serverPort = 8888;
		
		Server server = new NettyServer<ChannelInboundHandlerAdapter>(serverPort, getEchoServerHandlerWrapper());
		server.start();
	}
	
	private static ClassConstructorWrapper<ChannelInboundHandlerAdapter> getEchoServerHandlerWrapper() 
			throws Exception {
		Constructor<? extends ChannelInboundHandlerAdapter> handlerConstructor
			= EchoServerHandler.class.getConstructor(new Class[]{Object.class});

		ClassConstructorWrapper<ChannelInboundHandlerAdapter> wrapper 
			= new ClassConstructorWrapper<ChannelInboundHandlerAdapter>();
		wrapper.setConstructor(handlerConstructor);

		return wrapper;
	}
}
