package com.scushare.utils;

import java.util.Random;

public class RandomUtil {
	public static int[] getRandomArray(int startNum, int endNum, int randomCount) {
		if(endNum <= startNum) {
			return null;
		}
		int mod = endNum - startNum + 1;
		if(mod > 200 || mod < randomCount) {
			return null;
		}
		short[] base = new short[mod];
		int[] result = new int[randomCount];
		for(int i = 0; i < mod; i++) {
			base[i] = (short)i;
		}
		Random random = new Random();
		for(int i = 0; i < randomCount; i++) {
			int currIndex = random.nextInt() % (mod - i) + i;
			short temp = base[currIndex];
			base[currIndex] = base[i];
			base[i] = temp;
			
			result[i] = temp + startNum;
		}
		
		return result;
	}
}
