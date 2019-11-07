package com.ryx.credit.util;

import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.agentUtil.RSAUtil;

import java.security.PrivateKey;
import java.security.PublicKey;

public class Constants {

	public static PublicKey publicKey;
	public static PrivateKey privateKey;
	public static String serverUrl = AppConfig.getProperty("industryAuth_url");
	public static String posPmsPlusUrl = AppConfig.getProperty("pos_pms_plus_url");//pos分润查询
	
	// --机构号
	public static String cooperator = AppConfig.getProperty("industryAuth_cooperator");

	// --机构私钥，生产环境由机构生成RSA密钥，并将公钥发给我方报备
	public final static String LOCAL_PRIVATE_KEY = AppConfig.getProperty("industryAuth_local_private_key");

	// --我方公钥
	public final static String COOPER_PUBLIC_KEY = AppConfig.getProperty("industryAuth_cooper_public_key");

	static {
		try {
			publicKey = RSAUtil.getRSAPublicKey(Constants.COOPER_PUBLIC_KEY, "pem", "RSA");
			privateKey = RSAUtil.getRSAPrivateKey(Constants.LOCAL_PRIVATE_KEY, "pem", null, "RSA");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}
