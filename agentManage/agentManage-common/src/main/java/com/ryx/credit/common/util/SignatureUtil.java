package com.ryx.credit.common.util;

import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.io.*;
import java.nio.charset.Charset;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Scanner;

public class SignatureUtil {
    public final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    public final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding"; // 加密block需要预留11字节
    public final int KEYBIT = 2048;
    public final int RESERVEBYTES = 11;

    private PrivateKey localPrivKey;
    private PublicKey peerPubKey;

    /**
     * 初始化自己的私钥,对方的公钥以及密钥长度.
     *
     * @param localPrivKeyBase64Str Base64编码的私钥,PKCS#8编码. (去掉pem文件中的头尾标识)
     * @param peerPubKeyBase64Str   Base64编码的公钥. (去掉pem文件中的头尾标识)
     * @param keysize               密钥长度, 一般2048
     */
    public void initKey(String localPrivKeyBase64Str, String peerPubKeyBase64Str, int keysize) throws Exception {
        try {
            localPrivKey = getPrivateKey(new FileInputStream(AppConfig.getProperty("sms.prikeypath")));
            peerPubKey = getPublicKey(new FileInputStream(AppConfig.getProperty("sms.pubkeypath")));
        } catch (InvalidKeySpecException e) {
            throw e;
        }
    }

    /**
     * 从文件中输入流中加载公钥
     *
     * @param in 公钥输入流
     * @throws Exception 加载公钥时产生的异常
     */
    public RSAPublicKey getPublicKey(InputStream in) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        try {
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            return getPublicKey(sb.toString());
        } catch (IOException e) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥输入流为空");
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                throw new Exception("关闭输入缓存流出错");
            }

            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                throw new Exception("关闭输入流出错");
            }
        }
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */


    public RSAPublicKey getPublicKey(String publicKeyStr) throws Exception {
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (IOException e) {
            throw new Exception("公钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }


    /**
     * 从文件中加载私钥
     *
     * @throws Exception
     */
    public RSAPrivateKey getPrivateKey(InputStream in) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        try {
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            return getPrivateKey(sb.toString());
        } catch (IOException e) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥输入流为空");
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                throw new Exception("关闭输入缓存流出错");
            }

            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                throw new Exception("关闭输入流出错");
            }
        }
    }

    /**
     * 从字符串中加载私钥
     *
     * @param privateKeyStr 公钥数据字符串
     * @throws Exception 加载私钥时产生的异常
     */
    public RSAPrivateKey getPrivateKey(String privateKeyStr) throws Exception {
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
            return privateKey;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (IOException e) {
            throw new Exception("私钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * RAS加密
     *
     * @return byte[]
     * @throws Exception
     */
    public byte[] encryptRSA(byte[] plainBytes, String charset) throws Exception {
        //        String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding"; // 加密block需要预留11字节
        //        int KEYBIT = 2048;
        //        int RESERVEBYTES = 11;
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        int decryptBlock = KEYBIT / 8; // 256 bytes
        int encryptBlock = decryptBlock - RESERVEBYTES; // 245 bytes
        // 计算分段加密的block数 (向上取整)
        int nBlock = (plainBytes.length / encryptBlock);
        if ((plainBytes.length % encryptBlock) != 0) { // 余数非0，block数再加1
            nBlock += 1;
        }
        // 输出buffer, 大小为nBlock个decryptBlock
        ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * decryptBlock);
        cipher.init(Cipher.ENCRYPT_MODE, peerPubKey);

        for (int offset = 0; offset < plainBytes.length; offset += encryptBlock) {
            // block大小: encryptBlock 或 剩余字节数
            int inputLen = (plainBytes.length - offset);
            if (inputLen > encryptBlock) {
                inputLen = encryptBlock;
            }
            // 得到分段加密结果
            byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);
            // 追加结果到输出buffer中
            outbuf.write(encryptedBlock);
        }
        return outbuf.toByteArray();
    }

    /**
     * RSA解密
     *
     * @return byte[]
     * @throws Exception
     */
    public byte[] decryptRSA(byte[] cryptedBytes, Charset charset) throws Exception {
        //        String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding"; // 加密block需要预留11字节
        byte[] data = null;

        data = cryptedBytes;

        //        int KEYBIT = 2048;
        //        int RESERVEBYTES = 11;
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        int decryptBlock = KEYBIT / 8; // 256 bytes
        int encryptBlock = decryptBlock - RESERVEBYTES; // 245 bytes
        // 计算分段解密的block数 (理论上应该能整除)
        int nBlock = (data.length / decryptBlock);
        // 输出buffer, , 大小为nBlock个encryptBlock
        ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * encryptBlock);
        cipher.init(Cipher.DECRYPT_MODE, localPrivKey);
        // plaintext = new
        // String(cipher.doFinal(Base64.decodeBase64(cryptedBase64Str)));
        // 分段解密
        for (int offset = 0; offset < data.length; offset += decryptBlock) {
            // block大小: decryptBlock 或 剩余字节数
            int inputLen = (data.length - offset);
            if (inputLen > decryptBlock) {
                inputLen = decryptBlock;
            }

            // 得到分段解密结果
            byte[] decryptedBlock = cipher.doFinal(data, offset, inputLen);
            // 追加结果到输出buffer中
            outbuf.write(decryptedBlock);
        }
        outbuf.flush();
        outbuf.close();
        return outbuf.toByteArray();
    }

    /**
     * RSA签名
     *
     * @return byte[]
     * @throws Exception
     */
    public byte[] signRSA(byte[] plainBytes, String charset) throws Exception {
        //        String SIGNATURE_ALGORITHM = "SHA1withRSA";
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(localPrivKey);
        signature.update(plainBytes);

        return signature.sign();
    }

    /**
     * 验签操作
     *
     * @return boolean
     */
    public boolean verifyRSA(byte[] plainBytes, byte[] signBytes, Charset charset) throws Exception {
        boolean isValid = false;
        //        String SIGNATURE_ALGORITHM = "SHA1withRSA";
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(peerPubKey);
        signature.update(plainBytes);

        return signature.verify(signBytes);
    }

    public String getFile(String realpath) {
        Scanner scanner = null;
        String text = "";
        try {    
            File file= new File(realpath);
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(scanner!=null){
        while(scanner.hasNextLine()){
            text+=scanner.nextLine();
        }
            scanner.close();
        }
        return text;
    }
}
