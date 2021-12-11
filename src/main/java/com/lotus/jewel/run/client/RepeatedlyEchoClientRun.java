package com.lotus.jewel.run.client;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import com.lotus.jewel.client.Client;
import com.lotus.jewel.client.handler.RepeatedlyEchoClientHandler;
import com.lotus.jewel.client.impl.NettyClient;

import io.netty.channel.ChannelInboundHandlerAdapter;


public class RepeatedlyEchoClientRun {

	public static void main(String[] args) throws Exception {
		
		String ipAddress = "127.0.0.1";
		int port = 8888;
		
		Constructor<? extends ChannelInboundHandlerAdapter> handlerConstructor
			= RepeatedlyEchoClientHandler.class.getConstructor(new Class[]{List.class});
		
		List<String> messageList = new ArrayList<String>();
		messageList.add("Channel Active");
		messageList.add("🐕");
		messageList.add("🐂");
		messageList.add("🐅");
		messageList.add("🐑");
		
		Client client = new NettyClient(ipAddress, port, handlerConstructor, messageList);
		client.start();
	}
}
