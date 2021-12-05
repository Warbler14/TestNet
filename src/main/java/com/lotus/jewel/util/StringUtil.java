package com.lotus.jewel.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class StringUtil {

	public static ByteBuf stringToByteBuf(String message) {
		if(message != null) {
			return Unpooled.copiedBuffer(message, CharsetUtil.UTF_8);
		}
		
		return null;
	}
}
