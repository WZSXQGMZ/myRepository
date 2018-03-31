package com.scushare.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
/**
 * 密码加密、匹配工具类
 *
 */
public class PwdUtil {
	/**
	 * 加密密码函数
	 * @param pwd 用户密码明文
	 * @return 加密后的用户密码
	 */
	public static String encryptPwd(String pwd) {
		String salt = getSalt();
		String shaPwd = SHA_256(pwd + salt);
		return salt + shaPwd;
	}
	
	/**
	 * 匹配密码函数
	 * @param pwd 用户密码明文
	 * @param encryptedPwd 数据库中的加密密码
	 * @return 密码是否匹配(true/false)
	 */
	public static boolean confirmPwd(String pwd, String encryptedPwd) {
		String salt = encryptedPwd.substring(0, 16);
		String shaPwd = SHA_256(pwd + salt);
		if(shaPwd.equals(encryptedPwd.substring(16))) {
			return true;
		}else {
			return false;
		}
	}

	private static String SHA_256(String inputStr)
	{
		final String KEY_SHA = "SHA";  
	    BigInteger sha =null;
	    //System.out.println("=======加密前的数据:"+inputStr);
	    byte[] inputData = inputStr.getBytes();   
	    try {
	         MessageDigest messageDigest = MessageDigest.getInstance(KEY_SHA);  
	         messageDigest.update(inputData);
	         sha = new BigInteger(messageDigest.digest());   
	         //System.out.println("SHA加密后:" + sha.toString(32));   
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	return null;
	    }
	    return sha.toString(32);
	}
	
	private static String getSalt() {
		char[] salt = new char[16];
		final String SOURSE = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
		Random random = new Random();
		for(int i=0;i<salt.length;i++) {
			salt[i] = SOURSE.charAt(random.nextInt(62));
		}
		
		return new String(salt);
	}
}
