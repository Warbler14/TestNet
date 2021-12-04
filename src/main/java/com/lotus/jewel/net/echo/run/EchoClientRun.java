package com.lotus.jewel.net.echo.run;

import java.lang.reflect.Constructor;

import com.lotus.jewel.client.Client;
import com.lotus.jewel.client.impl.NettyClient;
import com.lotus.jewel.net.echo.handler.EchoClientHandler;

import io.netty.channel.ChannelInboundHandlerAdapter;


public class EchoClientRun {

	public static void main(String[] args) throws Exception {
		
		String ipAddress = "127.0.0.1";
		int port = 8888;
		
		Constructor<? extends ChannelInboundHandlerAdapter> handlerConstructor
			= EchoClientHandler.class.getConstructor(new Class[]{String.class});

		String channelActiveMessage = "Channel Active 🐕";
		
		Client client = new NettyClient(ipAddress, port, handlerConstructor, channelActiveMessage);
		client.start();
	}
}
