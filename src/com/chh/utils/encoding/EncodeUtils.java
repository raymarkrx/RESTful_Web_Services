/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: EncodeUtils.java 1211 2010-09-10 16:20:45Z calvinxiu $
 */
package com.chh.utils.encoding;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringEscapeUtils;

import sun.misc.BASE64Decoder;

import com.chh.utils.PrintUtils;

/**
 * 各种格式的编码加码工具类.
 * 
 * 集成Commons-Codec,Commons-Lang及JDK提供的编解码方法.
 * 
 * @author calvin
 * 
 * @modified alex
 */
public class EncodeUtils {

	public static final String DEFAULT_URL_ENCODING = "UTF-8";
	
	public static final String ALGORITHM_DES = "DES";
	
	/**
	 * 默认算法为DES
	 * 
	 * 可替换为以下任意一种算法，同时key值的size相应改变:
	 * DES          		key size must be equal to 56
	 * DESede(TripleDES) 	key size must be equal to 112 or 168
	 * AES          		key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available
	 * Blowfish     		key size must be multiple of 8, and can only range from 32 to 448 (inclusive)
	 * RC2          		key size must be between 40 and 1024 bits
	 * RC4(ARCFOUR) 		key size must be between 40 and 1024 bits
	 */
	public static final String DEFAULT_ALGORITHM = "DES";
	
	public static final String DEFAULT_DES_KEY = "DX_DES_DEFAULT_KEY";
	
	/**
	 * MD5编码.
	 */
	public static String md5Encode(String input){
		return DigestUtils.md5Hex(input);
	}

	/**
	 * Hex编码.
	 */
	public static String hexEncode(byte[] input) {
		return Hex.encodeHexString(input);
	}

	/**
	 * Hex解码.
	 */
	public static byte[] hexDecode(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw new IllegalStateException("Hex Decoder exception", e);
		}
	}
	

	/**
	 * Base64编码, URL安全(将Base64中的URL非法字符如+,/=转为其他字符, 见RFC3548).
	 */
	public static String base64UrlSafeEncode(byte[] input) {
		return Base64.encodeBase64URLSafeString(input);
	}

	/**
	 * Base64编码.
	 */
	public static String base64Encode(byte[] input) {
		//return new String(Base64.encodeBase64(input));
		return new String(Base64.encodeBase64String(input));
	}


	/**
	 * Base64解码.
	 */
	public static byte[] base64Decode(String input) {
		return Base64.decodeBase64(input);
	}

	// CHH 加，使用新的BASE64 编码解码
	// 将 s 进行 BASE64 编码
//	public static String getBASE64(String s) {
//		if (s == null)
//			return null;
//		return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
//	}
//
//	// 将 BASE64 编码的字符串 s 进行解码
//	public static String getFromBASE64(String s) {
//		if (s == null)
//			return null;
//		BASE64Decoder decoder = new BASE64Decoder();
//		try {
//			byte[] b = decoder.decodeBuffer(s);
//			return new String(b);
//		} catch (Exception e) {
//			return null;
//		}
//	}

	/**
	 * URL 编码, Encode默认为UTF-8. 
	 */
	public static String urlEncode(String input) {
		try {
			return URLEncoder.encode(input, DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unsupported Encoding Exception", e);
		}
	}

	/**
	 * URL 解码, Encode默认为UTF-8. 
	 */
	public static String urlDecode(String input) {
		try {
			return URLDecoder.decode(input, DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unsupported Encoding Exception", e);
		}
	}
	
	public static String charEscape(char c){
		Number n =(int)c;
		String encode = Integer.toHexString(n.intValue());
		while(encode.length()<4){
			encode = "0" + encode;
		}
		encode = "\\u" + encode;
		return encode;
	}

	/**
	 * Html 转码.
	 */
	public static String htmlEscape(String html) {
		return StringEscapeUtils.escapeHtml(html);
	}

	/**
	 * Html 解码.
	 */
	public static String htmlUnescape(String htmlEscaped) {
		return StringEscapeUtils.unescapeHtml(htmlEscaped);
	}

	/**
	 * Xml 转码.
	 */
	public static String xmlEscape(String xml) {
		return StringEscapeUtils.escapeXml(xml);
	}

	/**
	 * Xml 解码.
	 */
	public static String xmlUnescape(String xmlEscaped) {
		return StringEscapeUtils.unescapeXml(xmlEscaped);
	}
	
	/**
	 * 生成密钥
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws Exception
	 */
	public static String initKey() throws NoSuchAlgorithmException {
		return initKey(null);
	}
	
	/**
	 * 生成密钥
	 * 
	 * @param seed
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws Exception
	 */
	public static String initKey(String seed) throws NoSuchAlgorithmException{
		SecureRandom secureRandom = null;

		if (seed != null) {
			secureRandom = new SecureRandom(base64Decode(seed));
		} else {
			secureRandom = new SecureRandom();
		}

		KeyGenerator kg = KeyGenerator.getInstance(DEFAULT_ALGORITHM);
		kg.init(secureRandom);

		SecretKey secretKey = kg.generateKey();

		return base64Encode(secretKey.getEncoded());
	}
	
	/**
	 * 转换密钥<br>
	 * 
	 * @param key
	 * @return
	 * @throws InvalidKeyException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 * @throws Exception
	 */
	private static Key toKey(byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
		SecretKey secretKey = null;
		if(ALGORITHM_DES.equals(DEFAULT_ALGORITHM)){
			DESKeySpec dks = new DESKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DEFAULT_ALGORITHM);
			secretKey = keyFactory.generateSecret(dks);
		}else{
			secretKey = new SecretKeySpec(key, DEFAULT_ALGORITHM);
		}
		return secretKey;
	}
	
	/**
	 * 加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws NoSuchPaddingException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws Exception
	 */
	public static String encrypt(byte[] data, String key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		Key k = toKey(base64Decode(key));
		Cipher cipher = Cipher.getInstance(DEFAULT_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, k);
		return base64Encode(cipher.doFinal(data));
	}
	
	public static String encrypt(String data, String key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		return encrypt(data.getBytes(), key);
	}
	
	public static String encrypt(String data) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		String key = initKey(DEFAULT_DES_KEY);
		return encrypt(data.getBytes(), key);
	}
	
	/**
	 * 解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws NoSuchPaddingException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws Exception
	 */
	public static String decrypt(byte[] data, String key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		Key k = toKey(base64Decode(key));
		Cipher cipher = Cipher.getInstance(DEFAULT_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k);
		return new String(cipher.doFinal(data));
	}
	
	public static String decrypt(String data, String key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		return decrypt(base64Decode(data), key);
	}
	
	public static String decrypt(String data) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		String key = initKey(DEFAULT_DES_KEY);
		return decrypt(base64Decode(data), key);
	}
	
	public static void main(String[] args) {
		//char s = "|";
		//System.out.println(charEscape("\001".charAt(0)));
		//String a="Zf3nnFUV4v//3TUAAAqLAQAa9P//3vn//+sDAABntgUAVPj+/waI/v8AAAAAAAAAAAAAAAA=";
//		byte[] b=base64Decode(a);
//		String re=base64Encode(b);
//		PrintUtils.print(re);
		
		//String temp="test123abc";
//		PrintUtils.print("源字符："+temp);
//		 PrintUtils.print("编码后："+Base64.encodeBase64String(temp.getBytes()));
//		 PrintUtils.print("解码后："+new String(Base64.decodeBase64(Base64.encodeBase64String(temp.getBytes()))));
		
		
//		 PrintUtils.print("编码后："+base64Encode(temp.getBytes()));
//		 PrintUtils.print("解码后："+new String(base64Decode(base64Encode(temp.getBytes()))));
		 
//		int int1=101; //1个字节
//		int int2=102000; //4个字节
//		int int3=103000; //4个字节
//		
//		 PrintUtils.print("解码前,1个字节："+String.valueOf(int1));
//		 PrintUtils.print("解码前,4个字节："+String.valueOf(int2));
//		 PrintUtils.print("解码前,4个字节："+String.valueOf(int3));
//		
//		 byte[] a1=intToByte1(int1); 
//		 byte[] a2=intToByte4(int2); 
//		 byte[] a3=intToByte4(int3); 
//
//		 byte[] temp=combineTowBytes(a1,a2);
//		 temp=combineTowBytes(temp,a3);
//		 PrintUtils.print("传给我的BASE64后的字符串："+base64Encode(temp));
//		 String mesg="ZQDonFXoFwAAroMAADRDAQDPEQAAIff//0gMAACfBgUAjpv+/w7U//8AAAAAAAAAAAAAAAA=";
//		 byte[] result=base64Decode(mesg);//我解码
//		 PrintUtils.print("字节长度："+result.length);
//		 PrintUtils.print("解码后,1个字节："+String.valueOf(bytesToInt1(splitBytesArray(result,0,1))));
//		 PrintUtils.print("解码后,1-4个字节："+String.valueOf( bytesToInt4(splitBytesArray(result,1,4))));
//		 PrintUtils.print("解码后,5-8个字节："+String.valueOf( bytesToInt4(splitBytesArray(result,5,4))));
//		 PrintUtils.print("解码后,9-12个字节："+String.valueOf( bytesToInt4(splitBytesArray(result,9,4))));
//		 PrintUtils.print("解码后,13-16个字节："+String.valueOf( bytesToInt4(splitBytesArray(result,13,4))));
//		 PrintUtils.print("解码后,17-20个字节："+String.valueOf( bytesToInt4(splitBytesArray(result,17,4))));
//		 PrintUtils.print("解码后,21-24个字节："+String.valueOf( bytesToInt4(splitBytesArray(result,21,4))));
//		 PrintUtils.print("解码后,25-28个字节："+String.valueOf( bytesToInt4(splitBytesArray(result,25,4))));
//		 PrintUtils.print("解码后,29-32个字节："+String.valueOf( bytesToInt4(splitBytesArray(result,29,4))));
//		 PrintUtils.print("解码后,33-36个字节："+String.valueOf( bytesToInt4(splitBytesArray(result,33,4))));
//		 PrintUtils.print("解码后,37-40个字节："+String.valueOf( bytesToInt4(splitBytesArray(result,37,4))));
//		 PrintUtils.print("解码后,41-44个字节："+String.valueOf( bytesToInt4(splitBytesArray(result,41,4))));
//		 PrintUtils.print("解码后,45-48个字节："+String.valueOf( bytesToInt4(splitBytesArray(result,45,4))));
//		PrintUtils.print("解码后,49-52个字节："+String.valueOf( bytesToInt4(splitBytesArray(result,49,4))));   //若打开
		
//		int a=10;
//		System.out.println(Integer.toBinaryString(a));
		
		
		String a="150721131";
		int x=Integer.parseInt(a.substring(0, 8));
		int y=Integer.parseInt(a.substring(8, a.length()));
		PrintUtils.print("before x:"+x+",y:"+y);
		
		byte[]xb=new byte[4];
		xb=EncodeUtils.intToByte4(x);
		byte[]yb=new byte[4];
		yb=EncodeUtils.intToByte4(y);
	    
		byte[]b=EncodeUtils.combineTowBytes(xb,yb);
		String encode=EncodeUtils.base64Encode(b);
		PrintUtils.print("encode:"+encode);
		
		byte[]yy=EncodeUtils.base64Decode(encode);
		x= EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(yy,0,4));
		y= EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(yy,4,4));
		PrintUtils.print("after x:"+x+",y:"+y);
		
		//00000000 00000000 00000000 00000010
		//byte[]b=new byte[4];
//		b[0]=(byte)(a>>24);
//		b[1]=(byte)(a>>16);
//		b[2]=(byte)(a>>8);
//		b[3]=(byte)(a);
//		System.out.println(b[0]+""+b[1]+""+b[2]+""+b[3]);
		
		
		//EncodeUtils.bytesToInt4(b);
		
	}
	
	/** 
	 * 基于arraycopy合并两个byte[] 数组 
	 * @param byte[]  bytes1 --前面
	 * @param byte[]  bytes2 --后面
	 * @return   byte[]   bytes3  
	 */  
	    public  static byte[]   combineTowBytes(byte[] bytes1,byte[] bytes2){
	        byte[] bytes3 = new byte[bytes1.length+bytes2.length];  
	          System.arraycopy(bytes1,0,bytes3,0,bytes1.length);  
	          System.arraycopy(bytes2,0,bytes3,bytes1.length,bytes2.length);  
	          return bytes3 ;  
	    }
	    
		/** 
		 * 基于arraycopy合并两个byte[] 数组 
		 * @param byte[]  bytes1 
		 * @return  int start
		 * @param int offset 从0开始
		 */  
		    public  static byte[]   splitBytesArray(byte[] bytes1,int start,int offset){      
		          byte[] bytes2 = new byte[offset];
		          System.arraycopy(bytes1,start,bytes2,0,offset);
		          return bytes2 ;  
		    }
		    
		    
		    /** 
		     *基于位移的 byte[]转化成int 
		     * @param byte[] bytes 
		     * @return int  number 
		     */  
		      
		    public static int bytesToInt1(byte[] bytes) {
		        int number = bytes[0] & 0xFF;    
		        return number;  
		    }
	
		    /** 
		     *基于位移的 byte[]转化成int 
		     * @param byte[] bytes 
		     * @return int  number 
		     */  
		      
		    public static int byteToInt(byte bytes) {
		        int number = bytes & 0xFF;    
		        return number;  
		    }
		    
		    /** 
		     *将2个字节的byte数组，基于位移的 byte[]转化成int 
		     * @param byte[] bytes 
		     * @return int  number 
		     */  
//			  00000000  01001010   00000000  01001010
//			  [3]					[2]					[1]                     [0]
 		    public static int bytesToInt2(byte[] bytes) {
		        int number = bytes[0] & 0xFF;  
		        // "|="按位或赋值。  
		        number |= ((bytes[1] << 8) & 0xFF00);  
		        return number;  
		    }
		    
		    
		    /** 
		     *将4个字节的byte数组，基于位移的 byte[]转化成int 
		     * @param byte[] bytes 
		     * @return int  number 
		     */  
//			  00000000  01001010   00000000  01001010
//			  [3]					[2]					[1]                     [0]
 		    public static int bytesToInt4(byte[] bytes) {
		        int number = bytes[0] & 0xFF;  
		        // "|="按位或赋值。  
		        number |= ((bytes[1] << 8) & 0xFF00);  
		        number |= ((bytes[2] << 16) & 0xFF0000);  
		        number |= ((bytes[3] << 24) & 0xFF000000);  
		        return number;  
		    }
	
	/** 
	 * 基于位移的int转化成byte[] 
	 * @param int  number 
	 * @return byte[] 
	 */  
//	  00000000  01001010   00000000  01001010
//	  [3]					[2]					[1]                     [0]
	public static byte[] intToByte4(int number) {
	    byte[] abyte = new byte[4];  
	    // "&" 与（AND），对两个整型操作数中对应位执行布尔代数，两个位都为1时输出1，否则0。  
	    abyte[0] = (byte) (0xff & number);  
	    // ">>"右移位，若为正数则高位补0，若为负数则高位补1  
	    abyte[1] = (byte) ((0xff00 & number) >> 8);  
	    abyte[2] = (byte) ((0xff0000 & number) >> 16);  
	    abyte[3] = (byte) ((0xff000000 & number) >> 24);  
	    return abyte;  
	}
	
	public static byte[] intToByte1(int number) {  
	    byte[] abyte = new byte[1];  
	    // "&" 与（AND），对两个整型操作数中对应位执行布尔代数，两个位都为1时输出1，否则0。  
	    abyte[0] = (byte) (0xff & number);  
	    return abyte;  
	}
	
}
