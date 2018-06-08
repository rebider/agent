package com.ryx.credit.common.util.agentUtil;

import java.security.PrivateKey;
import java.security.PublicKey;

public class Constants {

	public static PublicKey publicKey;
	public static PrivateKey privateKey;
	public static String serverUrl = "http://12.3.10.144:9998/iom-plugin/gateway";
	
	// --机构号
	public static String cooperator = "T00000000000001";

	// --机构私钥，生产环境由机构生成RSA密钥，并将公钥发给我方报备
	public final static String LOCAL_PRIVATE_KEY = 
			"MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDmTguRerXnsUvT" +
			"yoYSXTCy570VensC6lXXzDYf/hQg6AhwHKgSP+VQ23WnzBDR9wYOTcifUlYzJB/3" +
			"gUejY+Iv1sUzMbyJmjr7Z7QTqjEEdbbMD3+ZEn8o3D/pKQ8DTKOtsp4yiuhXasb+" +
			"3bUyY2Hlmpf3N3i3kMWrWdpaLy0aT31fEtX/0cVbNrNLCFg7JYFeRHj0qqubLm5g" +
			"48d/4hXznAwXxsq06jjR68auspCr1IGZMJviyZtzsbwYXNo6r4jsNF+nNPFV5DiH" +
			"rnyznVRaAgNePT5qU3nXAfCZJqE6T8lFPNEbo6ScwrBovebeclYE2EXKshZmqELF" +
			"gf6rknU9AgMBAAECggEBAK7A79Q/GcKqbNBTb7KFlgta/sZKfwbyS5sI8NUL1bg2" +
			"eRihFgNrCpV1QWzG0siPub+m1CHYt2Hs+nizV2mqxBnqzQ4QonCDYFgeE62MSc+J" +
			"huCGrhq3x1d48OH6btLhF54Y2COf9bdHZaJFF1YZxVIVIVTMecRIjUV9U8t02rOH" +
			"xypDNhdXeBieuOvF6ylV5D4Wdh0hoo6D+tmmOKgOso/6I4PJe/KeljD0+ISIOmob" +
			"c2oHJP+/DmSHku9cTDBf5iVd7AuCn7D7c5v0PDIQcryE9mzBzEL5Ntb+WnideTZq" +
			"KSf/2RJQA3z/6n7fvbyyY/OsgXeIAoui+B2TrCXzqn0CgYEA80kxhUxAocKP70rL" +
			"JKSsyWHbRK6o//PqzcxkUBMjksHUlwqqp7LbKxe6d0UAwjful9qXLX1i9xqsqses" +
			"kFDoetdFeCQAErh7B0emL5b0qC9BVwmyBy+edJNCZyzGDyigCLa/DEtypu5OgDC/" +
			"77ZPqutDcDle2tQOGY8Uu6IPnAMCgYEA8lcvOhbI43k8FojhchZPItiicqRzg0hi" +
			"tVqQJOW30nlLmfEDPixIxywqlFc3A4qTRrk05H/0Gwjkk6uyepk8+a3kePrN5SOp" +
			"jGRCTpfPDmo1DAK2LMUcdAKoOYcwErXoZuJthDoSrl4fuWQKmwXEcrhDWj/fYPgG" +
			"BBVH0pVLBb8CgYBWPs9YYvTSo4elHA2x+tLqPU+fJI/y1MSbmmbBYFePEjnMJdLj" +
			"ymsZ9rp6x7KCJ5Pkr3BChx3LXeByVoDdVGFmEuSkt5ZzDIVIymSmoEwxRzdRpLVB" +
			"COeSihXTP7Mi8pLxI9/Mev61IOwhU6bnUukhPW/V2/C9gMlS1D9UWRpRgwKBgQCD" +
			"/AHHpFMiuIuGRmRqi0XsV4oZ4ADddlX0KW+p+MalxdPr8nxupxW4DBDgm6NltVpC" +
			"wWnSLPHYq346AuAAr2arIQ726LmsW0DjKlOGlF17n1JmyS9IsM2vTScrtbnz5si9" +
			"CALXaf10o+SpRpgUGA8G394mz/SrzkkQfjqQsSib5wKBgFOYXoNvp5yeSX1UZtNt" +
			"i0knBlbzmbePftdCH3RqMP7oC9HMPpkGdxQfx5ut4LkFZcl0iS5nPyby1KdXrNwu" +
			"wSga13SPCUIqhJrfrCRyvPOvXeEVlU6mWE6fsR6bxQrZfWqjMI0QuftmkyjSAuuc" +
			"r80SOd/6YfkKZ6MVrCq+89u+";

	// --我方公钥
	public final static String COOPER_PUBLIC_KEY = 
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwkZc1cwCDeLCSX8XfoQ5" +
			"BZ+cj3hRBQ5SenPOsY5w0o8XxK5lYG6/g9aynu64XC18yN8s9C60GP9yrKHhlY/8" +
			"LTHY0Ba+MR6ot8eMphMdyeLSxpMkVQPr7Dn10CaeHQRnQB1iT90/SKX53017AJlk" +
			"22wJRGPVBt37CmQjMhwRYdt6gPwDDWCa48n9j3tuTDhD9FZdlu3esz2Opu78uiPU" +
			"NJv5A4y2arUE1WKI4/ASE/luju+r+wEfN+p11k3U4k2XyVWzgFceJ1jvLyndbMlr" +
			"nNzIQ+o0jFWbKkH5/kbAGpPORLg5qAiQ2ARc9KdYLgsHnhaecZMtJdCd41pRyoQ6" +
			"5wIDAQAB";

	static {
		try {
			publicKey = RSAUtil.getRSAPublicKey(Constants.COOPER_PUBLIC_KEY, "pem", "RSA");
			privateKey = RSAUtil.getRSAPrivateKey(Constants.LOCAL_PRIVATE_KEY, "pem", null, "RSA");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
