package com.lotus.jewel.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.util.StatusPrinter;

public class LogTest2 {

	final static Logger logger = LoggerFactory.getLogger(LogTest2.class);
/*
 * Exception in thread "main" java.lang.ClassCastException: org.slf4j.impl.SimpleLoggerFactory cannot be cast to ch.qos.logback.classic.LoggerContext
	at com.lotus.jewel.net.LogTest.main(LogTest.java:15)

 * 
 * */
	
	
	public static void main(String[] args) {
		LoggerContext context  = (LoggerContext) LoggerFactory.getILoggerFactory();
	
		try {
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(context);
			
			context.reset();
			configurator.doConfigure(args[0]);
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		StatusPrinter.printInCaseOfErrorsOrWarnings(context);
		
		logger.info("Entering applicaiton");
		
		
	
	}

}
