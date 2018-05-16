package com.ryx.credit.common.util;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;


/**
 *
 * @description DES加密解密工具类
 * @author wangqi
 * @date 2015年8月3日 下午1:14:15
 * @since 1.0
 */
public class EncryptUtil {
    private static Logger logger = Logger.getLogger(EncryptUtil.class);
    public final static String DES = "DES";
    public final static String DESEDE = "DESede";
    public final static String BLOWFISH = "BlowFish";
    public final static String MD5 = "MD5";
    public final static String SHA = "SHA";

    /**
     * md5加密
     *
     * @param plainText
     * @return 加密后的大写字符串
     */
    public static String getMD5ofStr(String plainText) {
        try {
            return getMD5ofStr(string2Bytes(plainText));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * md5加密
     *
     * @param str
     * @return 加密后的大写字符串
     */
    public static String getMD5ofStr(byte str[]) {
        return getMessageWord(MD5,str);
    }
    /**
     * sha加密
     *
     * @param plainText
     * @return 加密后的大写字符串
     */
    public static String getSHAofStr(String plainText) {
        try {
            return getSHAofStr(string2Bytes(plainText));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * sha 加密
     * @param str
     * @return 加密后的大写字符串
     */
    public static String getSHAofStr(byte str[]){
        return getMessageWord(SHA,str);
    }
    private static String getMessageWord(String type,byte str[]){
        try {
            MessageDigest md = MessageDigest.getInstance(type);
            md.update(str);
            byte b[] = md.digest();
            return bytes2HexString(b);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 字符串加密
     *
     * @param data 字符串数据
     * @param key  密钥
     * @param name 算法名称
     * @throws Exception
     */
    public static String encrypt(String data, String key, String name) {
        try {
            return bytes2HexString(encrypt(data.getBytes(), string2Bytes(key),
                    name));
        } catch (Exception e) {
            logger.error("加密时出现异常..." + e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串加密
     *
     * @param data 字符串数据
     * @param key  密钥
     * @param name 算法名称
     * @throws Exception
     */
    public static String encrypt(String data, byte[] key, String name) {
        try {
            return bytes2HexString(encrypt(data.getBytes(), key, name));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串解密
     *
     * @param data 字符串加密数据
     * @param key  密钥
     * @param name 算法名称
     * @return
     * @throws Exception
     */
    public static String decrypt(String data, String key, String name) {
        try {
            return new String(decrypt(hex2byte(data.getBytes()),
                    string2Bytes(key), name));
        } catch (Exception e) {
            logger.error("解密时出现异常,需要解密的字符：" + data);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串解密
     *
     * @param data 字符串加密数据
     * @param key  密钥
     * @param name 算法名称
     * @return
     * @throws Exception
     */
    public static String decrypt(String data, byte[] key, String name) {
        try {
            return new String(decrypt(hex2byte(data.getBytes()), key, name));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对数据源进行加密
     *
     * @param src  数据源
     * @param key  密钥
     * @param name 算法的名称
     * @return 返回加密后的数据
     * @throws Exception
     */
    private static byte[] encrypt(byte[] src, byte[] key, String name)
            throws Exception {

        SecretKeySpec securekey = new SecretKeySpec(key, name);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(name);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey);
        // 现在，获取数据并加密
        // 正式执行加密操作
        return cipher.doFinal(src);
    }

    /**
     * 对加密的数据源进行解密
     *
     * @param src  数据源
     * @param key  密钥
     * @param name 算法的名称
     * @return 返回解密后的原始数据
     * @throws Exception
     */
    private static byte[] decrypt(byte[] src, byte[] key, String name)
            throws Exception {

        SecretKeySpec securekey = new SecretKeySpec(key, name);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(name);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey);
        // 现在，获取数据并解密
        // 正式执行解密操作
        return cipher.doFinal(src);
    }

    /**
     * 二行制转字符串
     *
     * @param bytes
     * @return
     */
    private static String bytes2HexString(byte[] bytes) {
        String hs = null;
        if (bytes != null) {
            final int size = bytes.length;
            if (size > 0) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < size; i++) {
                    String tmp = (Integer
                            .toHexString(bytes[i] & 0XFF));
                    if (tmp.length() == 1) {
                        sb.append("0" + tmp);
                    } else {
                        sb.append(tmp);
                    }
                }
                hs = sb.toString().toUpperCase();
            }
        }
        return hs;
    }

    /**
     * 把字符串转化成 Unicode Bytes.
     *
     * @param s String
     * @return byte[]
     */
    private static byte[] string2Bytes(String s) {
        byte[] bytes = null;
        if (s != null) {
            try {
                bytes = s.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    /**
     * 结字节进行转换
     *
     * @param b
     * @return
     */
    private static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0) {
            return null;
        }
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    public static void main(String[] args) {
        // DES加密与解密
        String key = "12345678";
        String encrypt = EncryptUtil.encrypt("fuckyou2013", key,
                EncryptUtil.DES); // 加密
        System.out.println("1:" + encrypt);
        String decrypt = EncryptUtil.decrypt(encrypt, key,
                EncryptUtil.DES);// 解密
        System.out.println("2:" + decrypt);

        // MD5加密，不可逆的
        String md5 = EncryptUtil.getMD5ofStr("fuckyou2013");
        System.out.println("3:" + md5);
        String sha = getSHAofStr("fuckyou2013 ");
        System.out.println("4:"+sha);
//        result
//        1:29F8024C890D65795F5677355567A1C4
//        2:fuckyou2013
//        3:F2977EE4F987FD801DB8F93390A74A7D
//        4:04EDEE47FA8682A0C2E61F790F022CAB05A403F1
    }


    public static String sha512(String originalText) {
        if (originalText == null)
            return null;
        return DigestUtils.sha512Hex(originalText.getBytes());
    }

    public static String MD5(String originalText) {
        if (originalText == null)
            return null;
        return DigestUtils.md5Hex(originalText);
    }

    /**
     * MD5 16位摘要
     * @param text
     * @return
     */
    public static String MD5to16(String text){
        if(text==null)return null;
        return MD5(text).substring(8,24);
    }

    public static String base64Decode(String text)
            throws UnsupportedEncodingException {
        if (text == null)
            return null;
        return new String(Base64.decodeBase64(text.getBytes()), "UTF-8");
    }

    public static String base64Encode(String text)
            throws UnsupportedEncodingException {
        if (text == null)
            return null;
        return new String(Base64.encodeBase64(text.getBytes()), "UTF-8");
    }
}


