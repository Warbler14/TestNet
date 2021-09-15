package com.lotus.jewel.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

public class LogTest1 {

	/*
	 * dependency 순서를 지켜야 함
	 * logback -> slf4j (logback-core -> slf4j-api)
	 * 
	 * */
	final static Logger logger = LoggerFactory.getLogger(LogTest1.class);
	
	
	
	public static void main(String[] args) {
		System.out.println("gogogo");
		
		//http://logback.qos.ch/manual/configuration.html
		
//		System.setProperty(ContextInitializer.CONFIG_FILE_PROPERTY, "logback-test.xml");
//		Logger logger = LoggerFactory.getLogger(TestMain.class);
		
        logger.trace("trace");
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
        
        
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		StatusPrinter.print(lc);

		//-DLogback.configurationFile=/path/to/Logback.xml
		
	}
}
