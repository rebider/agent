package com.ryx.credit.common.util;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.apache.commons.lang.StringUtils;
import java.util.Base64;

/**
 * 类描述：3DES加密工具类
 */
public class DES3 {

    private final static String encoding = "UTF-8";
	/**
	 * 方法描述：3DES加密
	 * @param plainText  明文
	 * @param secretKey  密钥
	 * @param iv         加密向量
	 * @return String    密文
	 * @throws Exception
	 */
	public static String encode(String plainText, String secretKey, String iv)
			throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
		return Base64.getEncoder().encodeToString(encryptData);
	}
 
	/**
	 * 方法描述： 3DES解密
	 * @param encryptText 密文
	 * @param secretKey   密钥
	 * @param iv          加密向量
	 * @return String     明文
	 * @throws Exception
	 */
	public static String decode(String encryptText, String secretKey, String iv)
			throws Exception {
		if (StringUtils.isBlank(encryptText) || StringUtils.isBlank(secretKey)) {
			return "";
		}
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede/CBC/NoPadding");
		IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
		byte[] decode = Base64.getDecoder().decode(encryptText);
		byte[] decryptData = cipher.doFinal(decode);
		return new String(decryptData, encoding).trim();
	}


	public static void main(String[] args) {
		try {
			String key = "4SF6BJ3D8TDOT8NOCZ8T7P1K";
//			String iv = "13002542";//IV length must be 8 bytes long//ntJ11abSGOU=
			String iv = "88888888";//IV length must be 8 bytes long
			//加密
			String encryptStr = DES3.encode("明文", key,iv);
			System.out.println(encryptStr);
			//解密
			String decryptStr = DES3.decode(encryptStr, key,iv);
			System.out.println(decryptStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}