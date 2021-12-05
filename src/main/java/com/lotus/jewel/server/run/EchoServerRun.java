package com.lotus.jewel.server.run;

import java.lang.reflect.Constructor;

import com.lotus.jewel.server.Server;
import com.lotus.jewel.server.handler.EchoServerHandler;
import com.lotus.jewel.server.impl.NettyServer;

import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerRun {

	public static void main(String[] args) throws Exception {
		int serverPort = 8888;
		
		Constructor<? extends ChannelInboundHandlerAdapter> handlerConstructor
			= EchoServerHandler.class.getConstructor(new Class[]{Object.class});
		
		Server server = new NettyServer(serverPort, handlerConstructor, null);
		server.start();
	}
}
