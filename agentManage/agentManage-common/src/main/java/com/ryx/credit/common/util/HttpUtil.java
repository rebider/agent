package com.ryx.credit.common.util;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
/**
 * HttpUtil
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2017/5/3
 * @Time: 16:53
 * To change this template use File | Settings | File Templates.
 */

public class HttpUtil
{
    private static final String CHARSET = "UTF-8";
    private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(80000)
            .setConnectionRequestTimeout(20000)
            .setConnectTimeout(20000).build();

    public static String get(String url)
    {
        HttpGet request = new HttpGet(url);
        return httpRequest(request);
    }

    public static String post(String url)
    {
        return post(url, "");
    }

    public static String post(String url, Map<String, ?> params)
    {
        HttpPost request = new HttpPost(url);
        if ((params != null) && (!(params.isEmpty())))
        {
            UrlEncodedFormEntity entity;
            List formparams = new ArrayList();
            Object v = null;
            String str = null;
            for (String k : params.keySet()) {
                v = params.get(k);
                str = "";
                if (v != null) {
                    str = v.toString();
                }
                NameValuePair n = new BasicNameValuePair(k, str);
                formparams.add(n);
            }
            try
            {
                entity = new UrlEncodedFormEntity(formparams, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return "{\"code\":\"4004\", \"msg\":\"请求服务器失败，参数数据编码错误！\", \"result\":'UnsupportedEncodingException:" + e.getMessage() + "'}";
            }
            request.setEntity(entity);
        }
        return httpRequest(request);
    }

    public static String post(String url, String strEntity)
    {
        HttpPost request = new HttpPost(url);
        HttpEntity entity = null;
        try {
            entity = new StringEntity(strEntity, "UTF-8");
        } catch (Exception e) {
            return "{\"code\":\"4004\", \"msg\":\"请求服务器失败，参数数据编码错误！\", \"result\":'UnsupportedEncodingException:" + e.getMessage() + "'}";
        }
        request.setEntity(entity);
        return httpRequest(request);
    }

    public static String httpRequest(HttpRequestBase request)
    {
        try
        {
            CloseableHttpClient client = HttpClients.createDefault();
            request.setConfig(requestConfig);

            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() != 200) {
                request.abort();
                return "{\"code\":\"4000\", \"msg\":\"请求服务器失败，返回代码：" + response.getStatusLine().getStatusCode() + "\", \"result\":'" + response.getStatusLine().toString() + "'}";
            }
            HttpEntity resEntity = response.getEntity();
            return ((resEntity == null) ? null : EntityUtils.toString(resEntity, "UTF-8"));
        }
        catch (UnsupportedEncodingException e) {
            return "{\"code\":\"4002\", \"msg\":\"请求服务器失败，返回的数据编码错误！\", \"result\":'UnsupportedEncodingException:" + e.getMessage() + "'}";
        }
        catch (ClientProtocolException e) {
            return "{\"code\":\"4003\", \"msg\":\"请求服务器失败，不支持的网络协议！\", \"result\":'ClientProtocolException:" + e.getMessage() + "'}";
        } catch (IOException e) {
            return "{\"code\":\"4001\", \"msg\":\"请求服务器失败！\", \"result\":'IOException:" + e.getMessage() + "'}";
        }
    }
}