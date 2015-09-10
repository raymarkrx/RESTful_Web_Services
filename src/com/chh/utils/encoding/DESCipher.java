
package com.chh.utils.encoding;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class DESCipher {
	private static final String DEFAULT_KEY = "DX_DES_DEFAULT_KEY";
	private Cipher encryptCipher = null;
	private Cipher decryptCipher = null;

	public DESCipher() throws Exception {
		this(DEFAULT_KEY);
	}

	public DESCipher(String strKey) throws Exception {
		Key key = getKey(strKey.getBytes());
		encryptCipher = Cipher.getInstance("DES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);
		decryptCipher = Cipher.getInstance("DES");
		decryptCipher.init(Cipher.DECRYPT_MODE, key);
	}

	/**
	 * Generate cipher key by giving byte array.
	 * 
	 * @param bytes
	 * @return
	 */
	private Key getKey(byte[] bytes) {
		byte[] bytesKey = new byte[8];
		for (int i = 0; i < bytes.length && i < bytesKey.length; i++) {
			bytesKey[i] = bytes[i];
		}
		return new SecretKeySpec(bytesKey, "DES");
	}

	/**
	 * Convert an byte array to a hex string. <br>
	 * i.g. byte[]{8,18} = 0813
	 * 
	 * @param bytes
	 * @return
	 */
	public static String byteArrayToHexString(byte[] bytes) throws Exception {
		int length = bytes.length;
		StringBuffer sb = new StringBuffer(length * 2);
		for (int i = 0; i < length; i++) {
			int tempData = bytes[i];
			while (tempData < 0) {
				tempData += 256;
			}
			if (tempData < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(tempData, 16));
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * Convert a hex string to an byte array.
	 * 
	 * @return
	 */
	public static byte[] hexStringToByteArray(String str) throws Exception {
		byte[] bytes = str.getBytes();
		int length = bytes.length;
		byte[] resultArray = new byte[length / 2];
		for (int i = 0; i < length; i = i + 2) {
			String strTmp = new String(bytes, i, 2);
			resultArray[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return resultArray;
	}

	public byte[] encrypt(byte[] bytes) throws Exception {
		return encryptCipher.doFinal(bytes);
	}

	public String encrypt(String str) throws Exception {
		return byteArrayToHexString(encrypt(str.getBytes()));
	}

	public byte[] decrypt(byte[] bytes) throws Exception {
		return decryptCipher.doFinal(bytes);
	}

	public String decrypt(String str) throws Exception {
		return new String(decrypt(hexStringToByteArray(str)));
	}

	public static String EncryptDES(String plaintext, String key) throws Exception {
		if (plaintext == null)
			return null;
		DESCipher des = new DESCipher(key);
		return des.encrypt(plaintext);
	}

	public static String DecryptDES(String ciphertext, String key) throws Exception {
		if (ciphertext == null)
			return null;
		DESCipher des = new DESCipher(key);
		return des.decrypt(ciphertext);
	}

	public static String EncryptDES(String plaintext) throws Exception {
		if (plaintext == null)
			return null;
		DESCipher des = new DESCipher();
		return des.encrypt(plaintext);
	}

	public static String DecryptDES(String ciphertext) throws Exception {
		if (ciphertext == null)
			return null;
		DESCipher des = new DESCipher();
		return des.decrypt(ciphertext);
	}

}