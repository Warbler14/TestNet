package com.lotus.jewel.run.client;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import com.lotus.jewel.client.Client;
import com.lotus.jewel.client.handler.RepeatedlyEchoClientHandler;
import com.lotus.jewel.client.impl.NettyClient;
import com.lotus.jewel.wrapper.ClassConstructorWrapper;

import io.netty.channel.ChannelInboundHandlerAdapter;


public class RepeatedlyEchoClientRun {

	public static void main(String[] args) throws Exception {
		
		String ipAddress = "127.0.0.1";
		int port = 8888;
		
		Client client = new NettyClient<ChannelInboundHandlerAdapter>(ipAddress, port, getEchoClientHandlerWrapper());
		client.start();
	}
	
	private static ClassConstructorWrapper<ChannelInboundHandlerAdapter> getEchoClientHandlerWrapper() 
			throws Exception {
		Constructor<? extends ChannelInboundHandlerAdapter> handlerConstructor
			= RepeatedlyEchoClientHandler.class.getConstructor(new Class[]{List.class});
		
		List<String> messageList = new ArrayList<String>();
		messageList.add("Channel Active");
		messageList.add("🐕");
		messageList.add("🐂");
		messageList.add("🐅");
		messageList.add("🐑");

		ClassConstructorWrapper<ChannelInboundHandlerAdapter> wrapper 
			= new ClassConstructorWrapper<ChannelInboundHandlerAdapter>();
		wrapper.setConstructor(handlerConstructor);
		wrapper.setParameter(messageList);

		return wrapper;
	}
}
