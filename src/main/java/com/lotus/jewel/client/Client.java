package com.lotus.jewel.client;

import io.netty.util.concurrent.Future;

public interface Client {
	
	public void start() throws Exception;

	public boolean isShuttingDown();
	
	public Future<?> shutdownGracefully();
	
	public void shutdown();
}
