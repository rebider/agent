package com.ryx.credit.common.util.agentUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;
import org.bouncycastle.asn1.x509.RSAPublicKeyStructure;

public class RSAUtil {

	public static PublicKey getRSAPublicKey(String rsaKeyStr, String fileSuffix, String keyAlgorithm) {
		InputStream in = new ByteArrayInputStream(rsaKeyStr.getBytes());
		return getRSAPublicKey(in, fileSuffix, keyAlgorithm);
	}

	public static PrivateKey getRSAPrivateKey(String rsaKeyStr, String fileSuffix, String password, String keyAlgorithm) throws RuntimeException {
		InputStream in = new ByteArrayInputStream(rsaKeyStr.getBytes());
		return getRSAPrivateKey(in, fileSuffix, password, keyAlgorithm);
	}

	public static PublicKey getRSAPublicKey(InputStream in, String fileSuffix, String keyAlgorithm) {
		ASN1InputStream ain = null;
		try {
			String keyType = "";
			if ("crt".equalsIgnoreCase(fileSuffix) || "txt".equalsIgnoreCase(fileSuffix) || "cer".equalsIgnoreCase(fileSuffix)) {
				keyType = "X.509";
			} else if ("pem".equalsIgnoreCase(fileSuffix)) {
				keyType = "PKCS12";
			} else if (("yljf").equalsIgnoreCase(fileSuffix)) {
				keyType = "yljf";
			} else {
				keyType = "PKCS12";
			}
			PublicKey pubKey = null;
			if ("X.509".equals(keyType)) {
				CertificateFactory factory = CertificateFactory.getInstance(keyType);
				Certificate cert = factory.generateCertificate(in);
				pubKey = cert.getPublicKey();
			} else if ("PKCS12".equals(keyType)) {
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				StringBuilder sb = new StringBuilder();
				String readLine = null;
				while ((readLine = br.readLine()) != null) {
					if (readLine.charAt(0) == '-') {
						continue;
					} else {
						sb.append(readLine);
						sb.append('\r');
					}
				}
				X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(Base64.decodeBase64(sb.toString()));
				KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
				pubKey = keyFactory.generatePublic(pubX509);
			} else if ("yljf".equals(keyType)) {
				BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
				String s = br.readLine();
				ain = new ASN1InputStream(hexStr2ByteArr(s));
				RSAPublicKeyStructure pStruct = RSAPublicKeyStructure.getInstance(ain.readObject());
				RSAPublicKeySpec spec = new RSAPublicKeySpec(pStruct.getModulus(), pStruct.getPublicExponent());
				KeyFactory kf = KeyFactory.getInstance("RSA");
				return kf.generatePublic(spec);
			}
			return pubKey;
		} catch (FileNotFoundException e) {
			throw new RuntimeException("公钥路径文件不存�?");
		} catch (CertificateException e) {
			throw new RuntimeException("生成证书文件错误");
		} catch (IOException e) {
			throw new RuntimeException("读取公钥异常");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(String.format("生成密钥工厂时没有[%s]此类算法", keyAlgorithm));
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException("生成公钥对象异常");
		} finally {
			try {
				if (ain != null) {
					ain.close();
				}
			} catch (IOException e) {

			}
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
			}
		}
	}

	public static PrivateKey getRSAPrivateKey(InputStream in, String fileSuffix, String password, String keyAlgorithm) throws RuntimeException {
		String keyType = "";
		if ("keystore".equalsIgnoreCase(fileSuffix)) {
			keyType = "JKS";
		} else if ("pfx".equalsIgnoreCase(fileSuffix) || "p12".equalsIgnoreCase(fileSuffix)) {
			keyType = "PKCS12";
		} else if ("jck".equalsIgnoreCase(fileSuffix)) {
			keyType = "JCEKS";
		} else if ("pem".equalsIgnoreCase(fileSuffix) || "pkcs8".equalsIgnoreCase(fileSuffix)) {
			keyType = "PKCS8";
		} else if ("pkcs1".equalsIgnoreCase(fileSuffix)) {
			keyType = "PKCS1";
		} else if ("yljf".equalsIgnoreCase(fileSuffix)) {
			keyType = "yljf";
		} else if ("ldys".equalsIgnoreCase(fileSuffix)) {
			keyType = "ldys";
		} else {
			keyType = "JKS";
		}
		try {
			PrivateKey priKey = null;
			if ("JKS".equals(keyType) || "PKCS12".equals(keyType) || "JCEKS".equals(keyType)) {
				KeyStore ks = KeyStore.getInstance(keyType);
				if (password != null) {
					char[] cPasswd = password.toCharArray();
					ks.load(in, cPasswd);
					Enumeration<String> aliasenum = ks.aliases();
					String keyAlias = null;
					while (aliasenum.hasMoreElements()) {
						keyAlias = (String) aliasenum.nextElement();
						priKey = (PrivateKey) ks.getKey(keyAlias, cPasswd);
						if (priKey != null)
							break;
					}
				}
			} else if ("yljf".equals(keyType)) {
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String s = br.readLine();
				PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(hexStr2Bytes(s));
				KeyFactory keyf = KeyFactory.getInstance("RSA");
				PrivateKey myprikey = keyf.generatePrivate(priPKCS8);
				return myprikey;
			} else if ("ldys".equals(keyType)) {
				byte[] b = new byte[20480];
				in.read(b);
				PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(b);
				KeyFactory keyf = KeyFactory.getInstance("RSA");
				PrivateKey myprikey = keyf.generatePrivate(priPKCS8);
				return myprikey;
			} else {
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				StringBuilder sb = new StringBuilder();
				String readLine = null;
				while ((readLine = br.readLine()) != null) {
					if (readLine.charAt(0) == '-') {
						continue;
					} else {
						sb.append(readLine);
						sb.append('\r');
					}
				}
				if ("PKCS8".equals(keyType)) {
					PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(sb.toString()));
					KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
					priKey = keyFactory.generatePrivate(priPKCS8);
				} else if ("PKCS1".equals(keyType)) {
					RSAPrivateKeyStructure asn1PrivKey = new RSAPrivateKeyStructure((ASN1Sequence) ASN1Sequence.fromByteArray(Base64.decodeBase64(sb.toString())));
					KeySpec rsaPrivKeySpec = new RSAPrivateKeySpec(asn1PrivKey.getModulus(), asn1PrivKey.getPrivateExponent());
					KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
					priKey = keyFactory.generatePrivate(rsaPrivKeySpec);
				}
			}
			return priKey;
		} catch (FileNotFoundException e) {
			throw new RuntimeException("私钥路径文件不存�?");
		} catch (KeyStoreException e) {
			throw new RuntimeException("获取KeyStore对象异常");
		} catch (IOException e) {
			throw new RuntimeException("读取私钥异常");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("生成私钥对象异常");
		} catch (CertificateException e) {
			throw new RuntimeException("加载私钥密码异常");
		} catch (UnrecoverableKeyException e) {
			throw new RuntimeException("生成私钥对象异常");
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException("生成私钥对象异常");
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
			}
		}
	}

	private static final byte[] hexStr2Bytes(String s) {
		byte[] bytes;
		bytes = new byte[s.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}

	private static final byte[] hexStr2ByteArr(String hexStr) {
		return new BigInteger(hexStr, 16).toByteArray();
	}

	public static byte[] encrypt(byte[] plainBytes, PublicKey publicKey, int keyLength, int reserveSize, String cipherAlgorithm) {
		int keyByteSize = keyLength / 8;
		int encryptBlockSize = keyByteSize - reserveSize;
		int nBlock = plainBytes.length / encryptBlockSize;
		if ((plainBytes.length % encryptBlockSize) != 0) {
			nBlock += 1;
		}
		try {
			Cipher cipher = Cipher.getInstance(cipherAlgorithm);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * keyByteSize);
			for (int offset = 0; offset < plainBytes.length; offset += encryptBlockSize) {
				int inputLen = plainBytes.length - offset;
				if (inputLen > encryptBlockSize) {
					inputLen = encryptBlockSize;
				}
				byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);
				outbuf.write(encryptedBlock);
			}
			outbuf.flush();
			outbuf.close();
			return outbuf.toByteArray();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(String.format("没有[%s]此类加密算法", cipherAlgorithm));
		} catch (NoSuchPaddingException e) {
			throw new RuntimeException(String.format("没有[%s]此类填充模式", cipherAlgorithm));
		} catch (InvalidKeyException e) {
			throw new RuntimeException("无效密钥");
		} catch (IllegalBlockSizeException e) {
			throw new RuntimeException("加密块大小不合法");
		} catch (BadPaddingException e) {
			throw new RuntimeException("错误填充模式");
		} catch (IOException e) {
			throw new RuntimeException("字节输出流异�?");
		}
	}

	public static byte[] decrypt(byte[] encryptedBytes, PrivateKey privateKey, int keyLength, int reserveSize, String cipherAlgorithm) {
		int keyByteSize = keyLength / 8;
		int decryptBlockSize = keyByteSize - reserveSize;
		int nBlock = encryptedBytes.length / keyByteSize;
		try {
			Cipher cipher = Cipher.getInstance(cipherAlgorithm);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * decryptBlockSize);
			for (int offset = 0; offset < encryptedBytes.length; offset += keyByteSize) {
				int inputLen = encryptedBytes.length - offset;
				if (inputLen > keyByteSize) {
					inputLen = keyByteSize;
				}
				byte[] decryptedBlock = cipher.doFinal(encryptedBytes, offset, inputLen);
				outbuf.write(decryptedBlock);
			}
			outbuf.flush();
			outbuf.close();
			return outbuf.toByteArray();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(String.format("没有[%s]此类解密算法", cipherAlgorithm));
		} catch (NoSuchPaddingException e) {
			throw new RuntimeException(String.format("没有[%s]此类填充模式", cipherAlgorithm));
		} catch (InvalidKeyException e) {
			throw new RuntimeException("无效密钥");
		} catch (IllegalBlockSizeException e) {
			throw new RuntimeException("解密块大小不合法");
		} catch (BadPaddingException e) {
			throw new RuntimeException("错误填充模式");
		} catch (IOException e) {
			throw new RuntimeException("字节输出流异�?");
		}
	}

	public static byte[] digitalSign(byte[] plainBytes, PrivateKey privateKey, String signAlgorithm) {
		try {
			Signature signature = Signature.getInstance(signAlgorithm);
			signature.initSign(privateKey);
			signature.update(plainBytes);
			byte[] signBytes = signature.sign();
			return signBytes;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(String.format("数字签名时没有[%s]此类算法", signAlgorithm));
		} catch (InvalidKeyException e) {
			throw new RuntimeException("数字签名时私钥无�?");
		} catch (SignatureException e) {
			throw new RuntimeException("数字签名时出现异�?");
		}
	}

	public static boolean verifyDigitalSign(byte[] plainBytes, byte[] signBytes, PublicKey publicKey, String signAlgorithm) {
		boolean isValid = false;
		try {
			Signature signature = Signature.getInstance(signAlgorithm);
			signature.initVerify(publicKey);
			signature.update(plainBytes);
			isValid = signature.verify(signBytes);
			return isValid;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(String.format("验证数字签名时没有[%s]此类算法", signAlgorithm));
		} catch (InvalidKeyException e) {
			throw new RuntimeException("验证数字签名时公钥无�?");
		} catch (SignatureException e) {
			throw new RuntimeException("验证数字签名时出现异�?");
		}
	}

}
