package com.ryx.credit.common.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Http请求工具类
 * @author hairtail7
 *
 */
public final class HttpClientUtil {

	private static final Logger log = LoggerFactory.getLogger("HttpClientUtil");
	
	
	public static String simpleHttpGet(String strUrl) {
		URL url = null;
		HttpURLConnection conn = null;
		try {
			url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setUseCaches(false);
			conn.setConnectTimeout(60000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setReadTimeout(60000);
			conn.setRequestMethod("GET");
			BufferedReader reader = 
					new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			StringBuffer buffer = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			log.info("http请求：url:{}, return:{}", new Object[]{strUrl, buffer.toString()});
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("http请求异常：url:{}, error:{}", new Object[]{strUrl, e.getMessage()});
			return null;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}
	
	public static String simpleHttpPOST(String strUrl, String writeStr) {
		URL url = null;
		HttpURLConnection conn = null;
		try {
			byte[] writeByte = writeStr.getBytes("UTF-8");
			url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setUseCaches(false);
			conn.setConnectTimeout(3000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setReadTimeout(6000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "text/plain");
			conn.setRequestProperty("Content-Length",String.valueOf(writeByte.length));
			BufferedOutputStream out = new BufferedOutputStream(conn.getOutputStream());
			out.write(writeByte);
			out.flush();
			out.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			StringBuffer buffer = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			log.info("http请求：url:{}, params:{}, return:{}", new Object[] {
					strUrl, writeStr, buffer.toString() });
			return buffer.toString();
		} catch (Exception e) {
			log.error("http请求异常：url:{}, params:{}, error:{}", new Object[] {
					strUrl, writeStr, e.getMessage() });
			e.printStackTrace();
			return null;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}
	
	
	public static String upload(String url, ParamterPost par) {
	    log.info("uploadFaceUtil" + url);
	    String result = "";
	    CloseableHttpClient httpclient = null;
	    try {
	      httpclient = HttpClients.createDefault();
	      HttpPost post = new HttpPost(url);
	      MultipartEntityBuilder builder = par.getMultipart();
	      HttpEntity entity = builder.build();
	      post.setEntity(entity);
	      HttpResponse response = httpclient.execute(post);// 执行提交
	      HttpEntity resultResponse = response.getEntity();
	      if(resultResponse != null) {
	        result = EntityUtils.toString(resultResponse, "UTF-8");
	      }
	    } catch (Exception e) {
	      throw new RuntimeException("收发消息异常", e);
	      
	    } finally {
	      try {
	        httpclient.close();
	      } catch (Exception e) {
	        throw new RuntimeException("关闭通道失败", e);
	      }
	    }
	    return result;
	  }
	
}