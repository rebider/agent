package com.ryx.credit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.HttpJsonClient;
import com.ryx.credit.common.util.ThreadPool;
import com.ryx.credit.pojo.admin.RequestInfo;
import com.ryx.credit.pojo.admin.ResponseInfo;
import com.ryx.credit.service.ISendSMSService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author wangqi
 * @Date 2017/8/4
 * @Time: 11:06
 */
@Service("sendSMSService")
public class SendSMSServiceImpl implements ISendSMSService {
	
	@Autowired
	private RedisService redisService;
	
    private static final Log logger = LogFactory.getLog(SendSMSServiceImpl.class.getName());

	protected String baseUrl;
	protected String priKeyPath;//秘钥路径
	protected String pubKeyPath;//公钥路径

	public SendSMSServiceImpl() {
		baseUrl  = AppConfig.getProperty("sms.msgurl");//主程序启动加载配置
		priKeyPath= AppConfig.getProperty("sms.prikeypath");
		pubKeyPath= AppConfig.getProperty("sms.pubkeypath");
	}

	@Override
	public ResponseInfo send(final RequestInfo content) throws Exception {
		final ResponseInfo result = new ResponseInfo();
		try {
			ThreadPool.putThreadPool(() -> {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {

				}

				try {
					Map<String, Object> mapMQ = new HashMap<>();
					try{
						redisService.setValue(content.getMobileNos(),content.getParams(),60L);
						redisService.hSet("sms",content.getMobileNos(),content.getParams());
					}catch(Exception e){
						logger.info("插入短信记录失败");
					}
					logger.info("内容服务平台短信发送开始：");
					/**
					 ** 交易码 trancode 32 是 000030 系统来源 sysId 32 是 请联系内容服务方获取 手机号 mobileNos 11
					 * 是 短信模板ID templateId 10 是 必须在已经定义好的模板中选择，模板参数见
					 * 3.3.3节，如需新增短信模版，请联系开发人员。 短信参数 params 是 多个参数以英文逗号（,）分隔必须与模板中的参数一一对应
					 */
					/**
					 * 固定值上送
					 */
					/**
					 * 前端获取上送
					 */
					mapMQ.put("smsType", content.getSmsType());
					mapMQ.put("mobileNos", content.getMobileNos());
					String messageInfo = getMessageInfo(content.getTemplateId(),content.getParams());
					if(null==messageInfo){
						logger.error("无效的模板编号！");
					}
					mapMQ.put("templateId", "00000001");
					mapMQ.put("params", messageInfo);

					String req_json = JSONObject.toJSONString(mapMQ);
					// 生产：http://10.3.30.48:30200/sms/sendSMS
					// 测试：http://10.3.30.52:30200/sms/sendSMS

					logger.info("内容服务平台短信发送上送参数：" + req_json);
					AppConfig.sendEmails(req_json,"内容服务平台短信发送上送参数：");

					String urlStr = baseUrl;
					String resp_json = HttpJsonClient.postForCmbm(urlStr, req_json,AppConfig.getProperty("systemId"),priKeyPath,pubKeyPath);

					logger.info("内容服务平台短信发送返回数据：" + resp_json);

					Map<String, Object> mapMQ_resp = new HashMap<>();

					if (!"".equals(resp_json)) {

						mapMQ_resp = JSONObject.parseObject(resp_json);
						//			result.setTrancode(String.valueOf(mapMQ_resp.get("trancode")));
						result.setRspCode(String.valueOf(mapMQ_resp.get("rspCode")));
						result.setRspMsg(String.valueOf(mapMQ_resp.get("rspMsg")));
					}
					String rspCode=String.valueOf(mapMQ_resp.get("rspCode"));
					if(!"00".equals(rspCode)){
						logger.info("内容服务平台短信发送失败，返回码："+String.valueOf(mapMQ_resp.get("rspCode"))+"，返回信息："+(String)mapMQ_resp.get("rspMsg"));
					}else{
						logger.info("内容服务平台短信发送成功。返回码：" + String.valueOf(mapMQ_resp.get("rspCode")) + "，返回信息：" + (String) mapMQ_resp.get("rspMsg"));
					}
					logger.info("开始插入短信记录");
					logger.debug("内容服务平台短信发送结束。");
				} catch (Exception e) {
					logger.error("sendMsg",e);
				}

			});
		} catch (Exception e) {
			logger.error("contentsPool Asynchronous call failed", e);
		}
		result.setRspCode("00");
		result.setRspMsg("发送成功");
		return result;
	}
	/**
	 * 结合模板id，参数返回短信内容
	 * @param templateId 模板编号
	 * @param params 参数|多个参数用,隔开
	 * @return
	 */
	public String getMessageInfo(String templateId,String params){
		String info = redisService.hGet("config","smsModel");
		params = params.replace("，", ",");
		String param[] = params.split(",");
		for(int i=0;i<param.length;i++){
			info = info.replace("{"+i+"}", param[i]);
		}
		return info;
	}
	
}
