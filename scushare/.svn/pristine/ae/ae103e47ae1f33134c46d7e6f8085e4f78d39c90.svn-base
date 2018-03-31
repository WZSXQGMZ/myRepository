package com.scushare.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

/**
 * 获取文件md5工具类
 * http://blog.csdn.net/wangqiuyun/article/details/22941433
 *
 */
public class MD5Util {
	public static String getMd5ByFile(File file) throws FileNotFoundException {  
        String value = null;  
        FileInputStream in = new FileInputStream(file);  
    try {  
        MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());  
        MessageDigest md5 = MessageDigest.getInstance("MD5");  
        md5.update(byteBuffer);  
        BigInteger bi = new BigInteger(1, md5.digest());  
        value = bi.toString(16);  
    } catch (Exception e) {  
        e.printStackTrace();  
    } finally {  
            if(null != in) {  
                try {  
                in.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    return value;  
    }  
	
	public static String getMd5ByPath(String path) { 
		String md5 = null;
        try {
			FileInputStream fis= new FileInputStream(path);   
			md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
	        IOUtils.closeQuietly(fis);    
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
        return md5;
	}
}
