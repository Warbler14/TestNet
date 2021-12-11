package com.lotus.jewel.run.client;

import java.lang.reflect.Constructor;

import com.lotus.jewel.client.Client;
import com.lotus.jewel.client.handler.EchoClientHandler;
import com.lotus.jewel.client.impl.NettyClientWithChannelFutureListener;

import io.netty.channel.ChannelInboundHandlerAdapter;


public class EchoClientWithNettyClientWithChannelFutureListenerRun {
	
	public static void main(String[] args) throws Exception {
		
		String ipAddress = "127.0.0.1";
		int port = 8888;
		
		Constructor<? extends ChannelInboundHandlerAdapter> handlerConstructor
			= EchoClientHandler.class.getConstructor(new Class[]{String.class});

		String channelActiveMessage = "Channel Active 🐧";
		
		Client client = new NettyClientWithChannelFutureListener(ipAddress, port, handlerConstructor, channelActiveMessage);
		client.start();
	}
}
