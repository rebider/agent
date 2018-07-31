package com.ryx.credit.common.util.agentUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;

public final class HttpUtil {

	public static String doPost(String url, Map<String, String> params) {
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(60000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(60000);
		PostMethod postMethod = new PostMethod(url);
		try {
			Iterator<Entry<String, String>> it = params.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				postMethod.setParameter(entry.getKey(), entry.getValue());
			}
			int status = httpClient.executeMethod(postMethod);
			if (status == HttpStatus.SC_OK) {
				InputStream in = postMethod.getResponseBodyAsStream();
				String rtn = IOUtils.toString(in, "UTF-8");
				return rtn;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}