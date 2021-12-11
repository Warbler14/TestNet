package com.lotus.jewel.wrapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ClassConstructorWrapper <T>{

	final static Logger logger = LoggerFactory.getLogger(ClassConstructorWrapper.class);
	
	//ChannelInboundHandlerAdapter
	private Constructor<? extends T> constructor;
	
	public Object parameter;

	public Constructor<? extends T> getConstructor() {
		return constructor;
	}

	public void setConstructor(Constructor<? extends T> constructor) {
		this.constructor = constructor;
	}

	public Object getParameter() {
		return parameter;
	}

	public void setParameter(Object parameter) {
		this.parameter = parameter;
	}
	
	public T getInstance() {
		T result = null;
		
		try {
			result = constructor.newInstance(parameter);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			logger.error(e.getMessage());
		}
		
		return result;
	}
}
