package com.ryx.credit.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HttpPostUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpPostUtil.class);

	public static String postForJSON(String url, String jsonParams) {
		String result = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		httppost.addHeader("Content-type", "application/json; charset=utf-8");
		httppost.setHeader("Accept", "application/json");
		// 设置 超时时间
		RequestConfig defaultRequestConfig = RequestConfig.custom()
				.setSocketTimeout(8000).setConnectTimeout(8000)
				.setConnectionRequestTimeout(8000)
				.setStaleConnectionCheckEnabled(true).build();
		httpclient = HttpClients.custom()
				.setDefaultRequestConfig(defaultRequestConfig).build();
		try {
			httppost.setEntity(new StringEntity(jsonParams, Charset
					.forName("UTF-8")));
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, "UTF-8");
					Map<String, String> resultMap = JSONObject.parseObject(result, Map.class);
					resultMap.put("http", String.valueOf(response.getStatusLine().getStatusCode()));
					result = JSONObject.toJSONString(resultMap);
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("httpPostUtil收发消息出现异常：" + e.getMessage(), e);
			throw new RuntimeException("收发消息出现异常：" + e.getMessage());
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String getForJSON(String url, String jsonParams) {
		String result = "";
		CloseableHttpClient httpclient = null;
		try {
			httpclient = HttpClients.custom().setConnectionManager(ConnectionManagerUtil.getConManagerContext()).build();
			if(!"".equals(jsonParams)) {
				url += "?params=" + jsonParams;
			}
			HttpGet get = new HttpGet(url);
			CloseableHttpResponse response = httpclient.execute(get);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, "UTF-8");
					Map<String, String> resultMap = JSONObject.parseObject(result, Map.class);
					resultMap.put("http", String.valueOf(response.getStatusLine().getStatusCode()));
					result = JSONObject.toJSONString(resultMap);
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("httpPostUtil收发消息出现异常：" + e.getMessage(), e);
			throw new RuntimeException("收发消息出现异常：" + e.getMessage());
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String httpsForPost(String url, String jsonParams) {
		String result = "";
		CloseableHttpClient httpclient = null;
		try {
			httpclient = HttpClients.custom().setConnectionManager(ConnectionManagerUtil.getConManagerContext()).build();
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Content-type", "application/json; charset=utf-8");
			httpPost.setHeader("Accept", "*/*");
			httpPost.setEntity(new StringEntity(jsonParams, Charset.forName("UTF-8")));
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, "UTF-8");
					Map<String, String> resultMap = JSONObject.parseObject(result, Map.class);
					resultMap.put("http", String.valueOf(response.getStatusLine().getStatusCode()));
					result = JSONObject.toJSONString(resultMap);
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("httpPostUtil收发消息出现异常：" + e.getMessage(), e);
			throw new RuntimeException("收发消息出现异常：" + e.getMessage());
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static String httpsForGet(String url) {
		String result = "";
		CloseableHttpClient httpclient = null;
		try {
			httpclient = HttpClients.custom().setConnectionManager(ConnectionManagerUtil.getConManagerContext()).build();
			HttpGet get = new HttpGet(url);
			CloseableHttpResponse response = httpclient.execute(get);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, "UTF-8");
					Map<String, String> resultMap = JSONObject.parseObject(result, Map.class);
					resultMap.put("http", String.valueOf(response.getStatusLine().getStatusCode()));
					result = JSONObject.toJSONString(resultMap);
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("httpPostUtil收发消息出现异常：" + e.getMessage(), e);
			throw new RuntimeException("收发消息出现异常：" + e.getMessage());
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static String upload(String url, ParamterPost par) {
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

	public static String postForJSON_deduct(String url, Map<String,Object> map)  {
		String result = "";
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			String str = JSON.toJSONStringWithDateFormat(Collections.singletonMap("REQ", map), "yyyyMMddHHmmss", new SerializerFeature[0]);
			String params = str.replaceAll("(Date|DATE|date)\":\"(\\d{8})000000", "$1\":\"$2");
			logger.info("============ post in transaction ================"+params);
//			byte[] b = params.getBytes("utf-8");
			HttpPost httppost = new HttpPost(url);
			httppost.addHeader("Content-Type", "text/html");
//			httppost.addHeader("Content-Length", String.valueOf(b.length));
			httppost.addHeader("MESSAGEFORMAT", "1");
			httppost.addHeader("TRANCODE", "830000");
			httppost.addHeader("PARTNERID", "xiaodai001");
			httppost.addHeader("BODYENCRYPT", "0");
			httppost.addHeader("SIGNATURE", "0");
			// 设置 超时时间
			RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setSocketTimeout(30000).setConnectTimeout(30000)
                    .setConnectionRequestTimeout(30000)
                    .setStaleConnectionCheckEnabled(true).build();
			httpclient = HttpClients.custom()
                    .setDefaultRequestConfig(defaultRequestConfig).build();
			try {
				StringEntity se = new StringEntity(params,"utf-8");
				se.setContentType("text/json");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
				httppost.setEntity(se);
                CloseableHttpResponse response = httpclient.execute(httppost);
                try {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        result = EntityUtils.toString(entity, "UTF-8");
                        logger.info("result:{}",result);
						Map<String, String> resultMap = JSONObject.parseObject(result, Map.class);
                        resultMap.put("http", String.valueOf(response.getStatusLine().getStatusCode()));
                        result = JSONObject.toJSONString(resultMap);
                    }
                } finally {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("httpPostUtil收发消息出现异常：" + e.getMessage(), e);
            } finally {
                // 关闭连接,释放资源
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		} catch (Exception e) {
			logger.error("postForJSON_deduct error",e);
		}
		return result;
	}
	
	
}
