package com.lotus.jewel.run.client;

import java.lang.reflect.Constructor;

import com.lotus.jewel.client.Client;
import com.lotus.jewel.client.handler.EchoClientHandler;
import com.lotus.jewel.client.impl.NettyClient;
import com.lotus.jewel.wrapper.ClassConstructorWrapper;

import io.netty.channel.ChannelInboundHandlerAdapter;


public class EchoClientRun {

	public static void main(String[] args) throws Exception {
		
		String ipAddress = "127.0.0.1";
		int port = 8888;
		
		Client client = new NettyClient<ChannelInboundHandlerAdapter>(ipAddress, port, getEchoClientHandlerWrapper());
		client.start();
	}
	
	private static ClassConstructorWrapper<ChannelInboundHandlerAdapter> getEchoClientHandlerWrapper() 
			throws Exception {
		Constructor<? extends ChannelInboundHandlerAdapter> handlerConstructor
			= EchoClientHandler.class.getConstructor(new Class[]{String.class});
		
		String channelActiveMessage = "Channel Active 🐕";

		ClassConstructorWrapper<ChannelInboundHandlerAdapter> wrapper 
			= new ClassConstructorWrapper<ChannelInboundHandlerAdapter>();
		wrapper.setConstructor(handlerConstructor);
		wrapper.setParameter(channelActiveMessage);

		return wrapper;
	}
}
