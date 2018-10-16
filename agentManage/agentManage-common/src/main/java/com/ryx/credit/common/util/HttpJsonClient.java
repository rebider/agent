package com.ryx.credit.common.util;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @Author shuai dai
 * @Date 2017/8/4
 * @Time: 11:06 desc：用于HTTP远程请求
 */
public class HttpJsonClient {
	private final static Logger logger = LoggerFactory.getLogger(HttpJsonClient.class);
	private static String ENCODING = "utf-8";
    public static Charset charset = Charset.forName(ENCODING);
    private static String privateKey;
    private static String publicKey;
	public static String postForCmbm(String urlStr, String params,String systemId,String priKeyPath,String pubKeyPath) throws Exception {
		SignatureUtil signUtil = new SignatureUtil();
		privateKey = signUtil.getFile(priKeyPath);
		publicKey  = signUtil.getFile(pubKeyPath);
		URL url = new URL(urlStr);
		byte[] bytesPostData = encode(params,systemId);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setReadTimeout(60 * 1000);
		con.setDoOutput(true);// 打开向外输出
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 内容类型
		con.setRequestProperty("Content-Length", String.valueOf(bytesPostData.length));// 长度
		logger.info("-------------HTTP  POST  START-----------------");
		logger.info("请求地址："+urlStr);
		OutputStream outStream = con.getOutputStream();
		outStream.write(bytesPostData);// 写入数据
		outStream.flush();// 刷新内存
		outStream.close();
		int code = con.getResponseCode();
		//String s = con.getResponseMessage();
		logger.info("-------------HTTP  POST  END-----------------");
		logger.info("响应码：" + code);
		String result = decode(con.getInputStream());
		/*StringBuffer buffer = new StringBuffer();
		if (code == 200) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String temp = null;
			while ((temp = reader.readLine()) != null) {
				buffer.append(temp);
			}
			reader.close();

		}*/
		con.disconnect();// 断开连接
		return result.toString();
	}
	
	public static String postForCmbm(String urlStr, String params) throws Exception {
		URL url = new URL(urlStr);
		byte[] bytesPostData = params.getBytes();
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setReadTimeout(60 * 1000);
		con.setDoOutput(true);// 打开向外输出
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 内容类型
		con.setRequestProperty("Content-Length", String.valueOf(bytesPostData.length));// 长度
		logger.info("-------------HTTP  POST  START-----------------");
		logger.info("请求地址："+urlStr);
		OutputStream outStream = con.getOutputStream();
		outStream.write(bytesPostData);// 写入数据
		outStream.flush();// 刷新内存
		outStream.close();
		int code = con.getResponseCode();
		String s = con.getResponseMessage();
		logger.info("-------------HTTP  POST  END-----------------");
		logger.info("响应码：" + code);
		logger.info("响应内容：" + s);
		StringBuffer buffer = new StringBuffer();
		if (code == 200) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String temp = null;
			while ((temp = reader.readLine()) != null) {
				buffer.append(temp);
			}
			reader.close();

		}
		con.disconnect();// 断开连接
		return buffer.toString();
	}

	public static String postForTran(String urlStr, String params) throws Exception {
		logger.info("-------------HTTP  POST  START-----------------");
		logger.info("请求地址："+urlStr);
		URL url = new URL(urlStr);
		byte[] bytesPostData = params.getBytes("utf-8");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setReadTimeout(60 * 1000);
		con.setDoOutput(true);// 打开向外输出
		con.setRequestProperty("Content-Type", "text/html");
		con.setRequestProperty("Content-Length", String.valueOf(bytesPostData.length));
		con.setRequestProperty("MESSAGEFORMAT", "1");
		con.setRequestProperty("TRANCODE", "830000");
		con.setRequestProperty("PARTNERID", "xiaodai001");
		con.setRequestProperty("BODYENCRYPT", "0");
		con.setRequestProperty("SIGNATURE", "0");

		OutputStream outStream = con.getOutputStream();
		outStream.write(bytesPostData);// 写入数据
		outStream.flush();// 刷新内存
		outStream.close();

		int code = con.getResponseCode();
		String s = con.getResponseMessage();
		logger.info("-------------HTTP  POST  END-----------------");
		StringBuffer buffer = new StringBuffer();
		if (code == 200) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String temp = null;
			while ((temp = reader.readLine()) != null) {
				buffer.append(temp);
			}
			reader.close();

		}
		con.disconnect();// 断开连接
		return buffer.toString();
	}

	/**
	 * 用于处理代扣交易返回数据
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> respHandlerForTrans(String jsonStr) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> mapMQ_resp = (Map<String, Object>) JsonKit.fromJson(jsonStr).get("ANS");
		if (mapMQ_resp.get("serialNo") != null) {
			retMap.put("serialNo", ((List<String>) mapMQ_resp.get("serialNo")).get(0));
		}
		if (mapMQ_resp.get("paySerialNo") != null) {
			retMap.put("paySerialNo", ((List<String>) mapMQ_resp.get("paySerialNo")).get(0));
		}
		if (mapMQ_resp.get("execCode") != null) {
			retMap.put("execCode", ((List<String>) mapMQ_resp.get("execCode")).get(0));
		}
		if (mapMQ_resp.get("execMsg") != null) {
			retMap.put("execMsg", ((List<String>) mapMQ_resp.get("execMsg")).get(0));
		}
		if (mapMQ_resp.get("settDate") != null) {
			retMap.put("settDate", ((List<String>) mapMQ_resp.get("settDate")).get(0));
		}
		if (mapMQ_resp.get("transDate") != null) {
			retMap.put("transDate", ((List<String>) mapMQ_resp.get("transDate")).get(0));
		}
		if (mapMQ_resp.get("transTime") != null) {
			retMap.put("transTime", ((List<String>) mapMQ_resp.get("transTime")).get(0));
		}
		if (mapMQ_resp.get("oriTranDate") != null) {
			retMap.put("oriTranDate", ((List<String>) mapMQ_resp.get("oriTranDate")).get(0));
		}
		if (mapMQ_resp.get("oriTranType") != null) {
			retMap.put("oriTranType", ((List<String>) mapMQ_resp.get("oriTranType")).get(0));
		}
		if (mapMQ_resp.get("oriReqSerialNo") != null) {
			retMap.put("oriReqSerialNo", ((List<String>) mapMQ_resp.get("oriReqSerialNo")).get(0));
		}
		if (mapMQ_resp.get("oriExecCode") != null) {
			retMap.put("oriExecCode", ((List<String>) mapMQ_resp.get("oriExecCode")).get(0));
		}
		if (mapMQ_resp.get("oriExecMsg") != null) {
			retMap.put("oriExecMsg", ((List<String>) mapMQ_resp.get("oriExecMsg")).get(0));
		}
		if (mapMQ_resp.get("customerId") != null) {
			retMap.put("customerId", ((List<String>) mapMQ_resp.get("customerId")).get(0));
		}
		if (mapMQ_resp.get("details") != null) {
			retMap.put("details", ((List<String>) mapMQ_resp.get("details")).get(0));
		}
		return retMap;
	}
	
	private static byte[] encode(String params, String systemId) throws Exception {
        SignatureUtil signUtil = new SignatureUtil();
        signUtil.initKey(privateKey, publicKey, 2048);
        byte[] postData = params.getBytes(ENCODING);
        byte[] signData = signUtil.signRSA(postData, ENCODING);
        byte[] encryptData = signUtil.encryptRSA(postData, ENCODING);
        String dataLen = lpad(String.valueOf(8 + 4 + signData.length + encryptData.length), "0", 8);
        String signDataLen = lpad(signData.length + "", "0", 4);

        //8位报文总长度、8位合作方编号、4位签名域长度、签名域值、报文数据主体密文
        int total = 8 + 8 + 4 + signData.length + encryptData.length;
        byte[] data = new byte[total];

        System.arraycopy(dataLen.getBytes(ENCODING), 0, data, 0, 8);
        System.arraycopy(systemId.getBytes(ENCODING), 0, data, 8, 8);
        System.arraycopy(signDataLen.getBytes(ENCODING), 0, data, 16, 4);
        System.arraycopy(signData, 0, data, 20, signData.length);
        System.arraycopy(encryptData, 0, data, 20 + signData.length, encryptData.length);
        return data;
    }
	
	private static String lpad(String src, String r, int length) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buf.append(r);
        }
        String tmp = buf.toString() + src;
        return tmp.substring(tmp.length() - length);
    }
	
	public  static String  getFile(String realpath) {
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
	
	private static String decode(InputStream input) throws Exception {
        byte[] dataLenBytes = new byte[8];
        input.read(dataLenBytes);
        int dataLen = Integer.parseInt(new String(dataLenBytes, ENCODING));
        logger.info("报文总长度：" + dataLen);
        //8位合作方编号
        byte[] systemIdBytes = new byte[8];
        input.read(systemIdBytes);
        String systemId = new String(systemIdBytes, ENCODING);
        logger.info("合作方编号：" + systemId);
        //4位签名域长度
        byte[] signLenBytes = new byte[4];
        input.read(signLenBytes);
        int signLen = Integer.parseInt(new String(signLenBytes, ENCODING));
        logger.info("签名域长度：" + signLen);
        //签名域值
        byte[] signBytes = new byte[signLen];
        input.read(signBytes);
        //报文数据主体密文
        byte[] dataBytes = new byte[dataLen - 8 - 4 - signLen];
        input.read(dataBytes);
        SignatureUtil signUtil = new SignatureUtil();
        signUtil.initKey(privateKey, publicKey, 2048);
        byte[] plainBytes = signUtil.decryptRSA(dataBytes, charset);
        String plainText = new String(plainBytes, charset);
        logger.info("解析到明文：" + plainText);
        if (!signUtil.verifyRSA(plainBytes, signBytes, charset)) {
        	logger.info("验签失败");
            throw new UnsupportedOperationException("验签失败");
        }
        JSONObject json = new JSONObject(plainText);
        return json.toString();
    }
}
