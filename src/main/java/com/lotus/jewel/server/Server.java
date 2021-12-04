package com.lotus.jewel.server;

import io.netty.util.concurrent.Future;

public interface Server {

	public void start() throws Exception;
	
	public boolean isShuttingDown();
	
	public Future<?> shutdownGracefully();
	
	public void shutdown();
	
}
