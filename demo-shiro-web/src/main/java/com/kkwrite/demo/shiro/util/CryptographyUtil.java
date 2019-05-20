package com.kkwrite.demo.shiro.util;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Md5Hash;

/** 
 * 类说明 
 *
 * @author Soosky Wang
 * @date 2018年9月20日 下午5:29:42 
 * @version 1.0.0
 */
public class CryptographyUtil {

	/**
	 * Base64编码
	 * @param str
	 * @return
	 */
	public static String encBase64(String str) {
		return Base64.encodeToString(str.getBytes());
	}
	
	/**
	 * 解码 Base64
	 * @param str
	 * @return
	 */
	public static String decBase64(String str) {
		return Base64.decodeToString(str.getBytes());
	}
	
	/**
	 * md5加密
	 * @param str
	 * @param salt
	 * @return
	 */
	public static String md5(String str, String salt) {
		return new Md5Hash(str, salt).toString();
	}
	
	public static void main(String[] args) {
		System.out.println(md5("admin", "1a2b3c"));
	}
	
}
