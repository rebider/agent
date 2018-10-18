package com.ryx.credit.service;


import com.ryx.credit.pojo.admin.RequestInfo;
import com.ryx.credit.pojo.admin.ResponseInfo;

/**
 * @Author wangqi
 * @Date 2017/8/4
 * @Time: 11:06
 */
public interface ISendSMSService {

	/**
	 * 第三方接口：短息发送
	 * @return ResponseInfo
	 * @throws Exception
	 */
	public ResponseInfo send(RequestInfo content) throws Exception;
}
