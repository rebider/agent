package com.ryx.credit.common.util.agentUtil;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;

public class AESUtil {

	public static String getAESKey() {
		return RandomCharUtil.getRandomALLChar(16);
	}

	public static byte[] encrypt(byte[] plainBytes, byte[] keyBytes, String keyAlgorithm, String cipherAlgorithm, String IV) {
		try {
			if (keyBytes.length % 8 != 0 || keyBytes.length < 16 || keyBytes.length > 32) {
				throw new RuntimeException("AES密钥长度不合�?");
			}
			Cipher cipher = Cipher.getInstance(cipherAlgorithm);
			SecretKey secretKey = new SecretKeySpec(keyBytes, keyAlgorithm);
			if (StringUtils.trimToNull(IV) != null) {
				IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
				cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
			} else {
				cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			}
			byte[] encryptedBytes = cipher.doFinal(plainBytes);
			return encryptedBytes;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(String.format("没有[%s]此类加密算法", cipherAlgorithm));
		} catch (NoSuchPaddingException e) {
			throw new RuntimeException(String.format("没有[%s]此类填充模式", cipherAlgorithm));
		} catch (InvalidKeyException e) {
			throw new RuntimeException("无效密钥");
		} catch (InvalidAlgorithmParameterException e) {
			throw new RuntimeException("无效密钥参数");
		} catch (BadPaddingException e) {
			throw new RuntimeException("错误填充模式");
		} catch (IllegalBlockSizeException e) {
			throw new RuntimeException("加密块大小不合法");
		}
	}

	public static byte[] decrypt(byte[] encryptedBytes, byte[] keyBytes, String keyAlgorithm, String cipherAlgorithm, String IV) {
		try {
			if (keyBytes.length % 8 != 0 || keyBytes.length < 16 || keyBytes.length > 32) {
				throw new RuntimeException("AES密钥长度不合�?");
			}
			Cipher cipher = Cipher.getInstance(cipherAlgorithm);
			SecretKey secretKey = new SecretKeySpec(keyBytes, keyAlgorithm);
			if (IV != null && StringUtils.trimToNull(IV) != null) {
				IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
				cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
			} else {
				cipher.init(Cipher.DECRYPT_MODE, secretKey);
			}
			byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
			return decryptedBytes;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(String.format("没有[%s]此类加密算法", cipherAlgorithm));
		} catch (NoSuchPaddingException e) {
			throw new RuntimeException(String.format("没有[%s]此类填充模式", cipherAlgorithm));
		} catch (InvalidKeyException e) {
			throw new RuntimeException("无效密钥");
		} catch (InvalidAlgorithmParameterException e) {
			throw new RuntimeException("无效密钥参数");
		} catch (BadPaddingException e) {
			throw new RuntimeException("错误填充模式");
		} catch (IllegalBlockSizeException e) {
			throw new RuntimeException("解密块大小不合法");
		}
	}

}
