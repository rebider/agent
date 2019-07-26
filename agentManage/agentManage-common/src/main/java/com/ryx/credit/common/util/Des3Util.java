package com.ryx.credit.common.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * 
 * ClassName: Des3Util <br/>
 * Reason: Des3Util <br/>
 * date: 2016年7月28日 下午5:12:19 <br/>
 * 
 * @author madman
 * @version
 * @since JDK 1.6 PayIFramework 1.0
 */
public class Des3Util {

    /**
     * 
     * 方法名： Base64Encode.<br/>
     * 适用条件:加密.<br/>
     *
     * 创建者：Lance.Wu.<br/>
     * 创建日期：2015年12月29日.<br/>
     * 创建时间：下午2:58:57.<br/>
     * 参数者异常：@param b 传入数据 参数者异常：@throws Exception 系统异常 .<br/>
     * 返回值： @return 返回结果：byte[] b.<br/>
     * 其它内容： JDK 1.6 qtservices 1.0.<br/>
     */
    public static byte[] Base64Encode(byte[] b) throws Exception {
        return Base64.encodeBase64(b);
    }

    /**
     * 
     * 方法名： Base64Decode.<br/>
     * 适用条件:解密.<br/>
     *
     * 创建者：Lance.Wu.<br/>
     * 创建日期：2015年12月29日.<br/>
     * 创建时间：下午2:58:57.<br/>
     * 参数者异常：@param b 传入数据 参数者异常：@throws Exception 系统异常 .<br/>
     * 返回值： @return 返回结果：byte[].<br/>
     * 其它内容： JDK 1.6 qtservices 1.0.<br/>
     */
    public static byte[] Base64Decode(byte[] b) throws Exception {
        return Base64.decodeBase64(b);
    }

    /**
     * 
     * 方法名： URLEncode.<br/>
     * 适用条件:URL加密.<br/>
     *
     * 创建者：Lance.Wu.<br/>
     * 创建日期：2015年12月29日.<br/>
     * 创建时间：下午2:58:57.<br/>
     * 参数者异常：@param strToBeEncode 传入数据 参数者异常：@throws Exception 系统异常 .<br/>
     * 返回值： @return 返回结果：byte[].<br/>
     * 其它内容： JDK 1.6 qtservices 1.0.<br/>
     */
    public static String URLEncode(String strToBeEncode) throws Exception {
        return URLEncoder.encode(strToBeEncode, codingType);
    }

    /**
     * 
     * 方法名： URLDecode.<br/>
     * 适用条件:url解密.<br/>
     *
     * 创建者：Lance.Wu.<br/>
     * 创建日期：2015年12月29日.<br/>
     * 创建时间：下午2:58:57.<br/>
     * 参数者异常：@param strToBeDecode 传入数据 参数者异常：@throws Exception 系统异常 .<br/>
     * 返回值： @return 返回结果：byte[].<br/>
     * 其它内容： JDK 1.6 qtservices 1.0.<br/>
     */
    public static String URLDecode(String strToBeDecode) throws Exception {
        return URLDecoder.decode(strToBeDecode, codingType);
    }

    /**
     * 
     * 方法名： IvGenerator.<br/>
     *
     * 创建者：Lance.Wu.<br/>
     * 创建日期：2016年1月15日.<br/>
     * 创建时间：下午3:28:47.<br/>
     * 参数者异常：@param b 参数者异常：@return 参数者异常：@throws Exception .<br/>
     * 其它内容： JDK 1.6 QtServerAPI 1.0.<br/>
     */
    private static IvParameterSpec IvGenerator(byte[] b) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(b);
        return iv;
    }

    /**
     * 
     * 方法名： KeyGenerator.<br/>
     *
     * 创建者：Lance.Wu.<br/>
     * 创建日期：2016年1月15日.<br/>
     * 创建时间：下午3:29:13.<br/>
     * 参数者异常：@param keyStr 参数者异常：@return 参数者异常：@throws Exception .<br/>
     * 其它内容： JDK 1.6 QtServerAPI 1.0.<br/>
     */
    private static Key KeyGenerator(String keyStr) throws Exception {
        DESedeKeySpec keySpec = new DESedeKeySpec(keyStr.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(keyAlgorithm);
        return keyFactory.generateSecret(keySpec);
    }

    /**
     * 
     * 方法名： IVGenerator.<br/>
     *
     * 创建者：Lance.Wu.<br/>
     * 创建日期：2016年1月15日.<br/>
     * 创建时间：下午3:30:13.<br/>
     * 参数者异常：@param strIV 参数者异常：@return 参数者异常：@throws Exception .<br/>
     * 其它内容： JDK 1.6 QtServerAPI 1.0.<br/>
     */
    public static byte[] IVGenerator(String strIV) throws Exception {
        return Hex.decodeHex(strIV.toCharArray());
    }

    /**
     * 
     * 方法名： GenerateDigest.<br/>
     *
     * 创建者：Lance.Wu.<br/>
     * 创建日期：2016年1月15日.<br/>
     * 创建时间：下午3:30:20.<br/>
     * 参数者异常：@param strTobeDigest 参数者异常：@return 参数者异常：@throws Exception .<br/>
     * 其它内容： JDK 1.6 QtServerAPI 1.0.<br/>
     */
    public static String GenerateDigest(String strTobeDigest) throws Exception {
        byte[] input = strTobeDigest.getBytes(codingType);
        byte[] output = (byte[]) null;
        MessageDigest digestGenerator = MessageDigest.getInstance(digestAlgorithm);
        digestGenerator.update(input);
        output = digestGenerator.digest();
        return new String(Base64Encode(output), codingType);
    }

    /**
     * 
     * 方法名： Encrypt.<br/>
     * 3DES加密 创建者：Lance.Wu.<br/>
     * 创建日期：2016年1月15日.<br/>
     * 创建时间：下午3:31:06.<br/>
     * 参数者异常：@param strTobeEnCrypted 参数者异常：@param strKey 参数者异常：@param byteIV
     * 参数者异常：@return 参数者异常：@throws Exception .<br/>
     * 其它内容： JDK 1.6 QtServerAPI 1.0.<br/>
     */
    public static String Encrypt(String strTobeEnCrypted, String strKey, byte[] byteIV) throws Exception {
        byte[] input = strTobeEnCrypted.getBytes(codingType);
        Key k = KeyGenerator(strKey);
        IvParameterSpec iVSpec = byteIV.length != 0 ? IvGenerator(byteIV) : IvGenerator(defaultIV);
        Cipher c = Cipher.getInstance(cryptAlgorithm);
        c.init(1, k, iVSpec);
        byte[] output = c.doFinal(input);
        return new String(Base64Encode(output), codingType);
    }

    /**
     * 
     * 方法名： Decrypt.<br/>
     * 方法作用:3DES解密).<br/>
     *
     * 创建者：Lance.Wu.<br/>
     * 创建日期：2016年1月15日.<br/>
     * 创建时间：下午3:31:39.<br/>
     * 参数者异常：@param strTobeDeCrypted 参数者异常：@param strKey 参数者异常：@param byteIV
     * 参数者异常：@return 参数者异常：@throws Exception .<br/>
     * 其它内容： JDK 1.6 QtServerAPI 1.0.<br/>
     */
    public static String Decrypt(String strTobeDeCrypted, String strKey, byte[] byteIV) throws Exception {
        byte[] input = Base64Decode(strTobeDeCrypted.getBytes(codingType));
        Key k = KeyGenerator(strKey);
        IvParameterSpec iVSpec = byteIV.length != 0 ? IvGenerator(byteIV) : IvGenerator(defaultIV);
        Cipher c = Cipher.getInstance(cryptAlgorithm);
        c.init(2, k, iVSpec);
        byte[] output = c.doFinal(input);
        return new String(output, codingType);
    }

    /**
     * 
     * 方法名： HexStringToByteArray.<br/>
     *
     * 创建者：Lance.Wu.<br/>
     * 创建日期：2016年1月15日.<br/>
     * 创建时间：下午3:32:15.<br/>
     * 参数者异常：@param s 参数者异常：@return .<br/>
     * 其它内容： JDK 1.6 QtServerAPI 1.0.<br/>
     */
    public static byte[] HexStringToByteArray(String s) {
        byte[] buf = new byte[s.length() / 2];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = (byte) (chr2hex(s.substring(i * 2, i * 2 + 1)) * 16 + chr2hex(s.substring(i * 2 + 1, i * 2 + 1 + 1)));
        }

        return buf;
    }

    /**
     * '
     * 
     * 方法名： SignMd5.<br/>
     *
     * 创建者：Lance.Wu.<br/>
     * 创建日期：2016年1月15日.<br/>
     * 创建时间：下午3:32:33.<br/>
     * 参数者异常：@param str 参数者异常：@param md5 参数者异常：@return .<br/>
     * 其它内容： JDK 1.6 QtServerAPI 1.0.<br/>
     */
//    public static boolean SignMd5(String str, String md5) {
//        return MD5.md5(str).equals(md5);
//    }

    /**
     * 
     * 方法名： chr2hex.<br/>
     *
     * 创建者：Lance.Wu.<br/>
     * 创建日期：2016年1月15日.<br/>
     * 创建时间：下午3:33:44.<br/>
     * 参数者异常：@param chr 参数者异常：@return .<br/>
     * 其它内容： JDK 1.6 QtServerAPI 1.0.<br/>
     */
    private static byte chr2hex(String chr) {
        if ("0".equals(chr)) {
            return 0;
        }
        if ("1".equals(chr)) {
            return 1;
        }
        if ("2".equals(chr)) {
            return 2;
        }
        if ("3".equals(chr)) {
            return 3;
        }
        if ("4".equals(chr)) {
            return 4;
        }
        if ("5".equals(chr)) {
            return 5;
        }
        if ("6".equals(chr)) {
            return 6;
        }
        if ("7".equals(chr)) {
            return 7;
        }
        if ("8".equals(chr)) {
            return 8;
        }
        if ("9".equals(chr)) {
            return 9;
        }
        if ("A".equals(chr)) {
            return 10;
        }
        if ("B".equals(chr)) {
            return 11;
        }
        if ("C".equals(chr)) {
            return 12;
        }
        if ("D".equals(chr)) {
            return 13;
        }
        if ("E".equals(chr)) {
            return 14;
        }
        return (byte) (!"F".equals(chr) ? 0 : 15);
    }

    /** 参数 */
    private static String codingType = "UTF-8";
    /** 参数 */
    private static String digestAlgorithm = "SHA1";
    /** 参数 */
    private static String cryptAlgorithm = "DESede/CBC/PKCS5Padding";
    /** 参数 */
    private static String keyAlgorithm = "DESede";
    /** 参数 */
    private static byte[] defaultIV = "12345678".getBytes();

    /**
     * 
     * 方法名： getcodingType.<br/>
     *
     * 创建者：Lance.Wu.<br/>
     * 创建日期：2016年1月15日.<br/>
     * 创建时间：下午3:36:06.<br/>
     * 参数者异常：@return .<br/>
     * 其它内容： JDK 1.6 QtServerAPI 1.0.<br/>
     */
    public static String getcodingType() {
        return codingType;
    }

    /**
     * 
     * 方法名： setcodingType.<br/>
     *
     * 创建者：Lance.Wu.<br/>
     * 创建日期：2016年1月15日.<br/>
     * 创建时间：下午3:36:14.<br/>
     * 参数者异常：@param codingType .<br/>
     * 其它内容： JDK 1.6 QtServerAPI 1.0.<br/>
     */
    public static void setcodingType(String codingType) {
        Des3Util.codingType = codingType;
    }
    
    /**
     * 
     * 方法名： Encrypt.<br/>
     * 3DES加密 创建者：Lance.Wu.<br/>
     * 创建日期：2016年1月15日.<br/>
     * 创建时间：下午3:31:06.<br/>
     * 参数者异常：@param strTobeEnCrypted 参数者异常： .<br/>
     * 
     * @param strKey
     *            参数者异常： .<br/>
     * @param byteIV
     *            参数者异常： .<br/>
     * @return 参数者异常： .<br/>
     * @throws Exception .<br/>
     *         其它内容： JDK 1.6 QtServerAPI 1.0.<br/>
     */
//    public static String Encrypt2(String strTobeEnCrypted, String strKey, byte[] byteIV) throws Exception {
//        byte[] input = strTobeEnCrypted.getBytes(codingType);
//        Key k = KeyGenerator(strKey);
//        IvParameterSpec iVSpec = byteIV.length != 0 ? IvGenerator(byteIV) : IvGenerator(defaultIV);
//        Cipher c = Cipher.getInstance(cryptAlgorithm);
//        c.init(1, k, iVSpec);
//        byte[] output = c.doFinal(input);
//
//        return com.imobpay.base.util.Hex.toString(output);
//    }

}
