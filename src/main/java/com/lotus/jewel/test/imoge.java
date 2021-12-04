package com.lotus.jewel.test;

public class imoge {

	public static void main(String[] args) {
//		String buffer = "🐶🐱🐭🐹🐰🐻🧸🐨🐯🦁🐮🐷🐽🐸🐵🙈🙉🙊🐒🦍🦧🐔🐧🐦";
//		char[] ch = buffer.toCharArray();
//		for (int idx = 0 ; idx < ch.length ; idx ++ ) {
//			System.out.print( (int)ch[idx] + ".");
//			if(idx % 2 == 1) {
//				char[] icon = new char[2];
//				icon[0] = ch[idx-1];
//				icon[1] = ch[idx];
//				System.out.println(String.valueOf(icon));
//			}
//		}
		
		int front = 55357;
		int end = 56320;	//56374
		
		for (int idx = 0 ; idx < 2000 ; idx ++ ) {
			char[] icon = new char[2];
			icon[0] = (char)front;
			icon[1] = (char)(end + idx);
			System.out.print(String.valueOf(icon));
			if(idx % 20 == 19) {
				System.out.println();
			}
			
		}
	}
}
