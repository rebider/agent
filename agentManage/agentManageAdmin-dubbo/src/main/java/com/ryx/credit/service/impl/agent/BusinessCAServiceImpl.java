package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.EnvironmentUtil;
import com.ryx.credit.common.util.agentUtil.AESUtil;
import com.ryx.credit.common.util.agentUtil.HttpUtil;
import com.ryx.credit.common.util.agentUtil.RSAUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentExample;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.agent.BusinessCAService;
import com.ryx.credit.util.Constants;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;


@Service("businessCAService")
public class BusinessCAServiceImpl implements BusinessCAService{

    private static Logger logger = LoggerFactory.getLogger(BusinessCAServiceImpl.class);
	@Autowired
	private BusinessCAService businessCAService;
	@Autowired
	private AgentMapper agentMapper;
	@Resource(name = "taskExecutor")
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	@Autowired
	private AgentService agentService;

	@Override
	public AgentResult agentBusinessCA(String agentBusinfoName) {

		AgentResult result = new AgentResult(500,"参数错误","");
		if (StringUtils.isBlank(agentBusinfoName)) {
			logger.info("代理商工商认证:{}","代理商名称为空");
			return result;
		}
		try {
			result.setMsg("服务器异常");
			String httpResult = null;

			if(EnvironmentUtil.isProduction()){
				httpResult = industryAuthRequest(agentBusinfoName);
			}else{	//测试环境不作工商认证
				httpResult = "{'respType':'TEST','data':{'isTest':'1'}}";
			}

			if(StringUtils.isBlank(httpResult)){
				return result;
			}
			JSONObject jsonObject = JSONObject.parseObject(httpResult);
			JSONObject dataMap = JSONObject.parseObject(String.valueOf(jsonObject.get("data")));
			String respType = (String)jsonObject.get("respType");
			if ("TEST".equals(respType)){
				logger.info("-------------测试环境不作工商认证-----------");
				return AgentResult.ok(dataMap);
			}
			if(respType.equals("E")){
				return new AgentResult(404,String.valueOf(jsonObject.get("respMsg")),"");
			}else{
				if(StringUtils.isNotBlank(dataMap.getString("creditCode"))){
					AgentResult agentResult = agentService.checkAgBusLicIsEst(null,dataMap.getString("creditCode"));
					if(agentResult.isOK()){
						return new AgentResult(405,"营业执照号已存在："+dataMap.getString("creditCode")+"代理商编号为："+agentResult.getData(),"");
					}
				}
				return AgentResult.ok(dataMap);
			}
		} catch (Exception e) {
			logger.info("代理商工商认证异常:{}",e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	private String industryAuthRequest(String entName)throws Exception{

		String cooperator = Constants.cooperator;
		String charset = "UTF-8"; // 字符集
		String tranCode = "ENT001"; // 交易码
		String reqMsgId = UUID.randomUUID().toString().replace("-", ""); // 请求流水
		String reqDate = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"); // 请求时间

		JSONObject jsonParams = new JSONObject();
		JSONObject data = new JSONObject();
		jsonParams.put("version", "1.0.0");
		jsonParams.put("msgType", "01");
		jsonParams.put("reqDate", reqDate);

		data.put("entName", entName);
		data.put("isCache", "1");

		jsonParams.put("data", data);

		String plainXML = jsonParams.toString();
		System.out.println("传入明文======" + plainXML);

		// 请求报文加密开始
		String keyStr = AESUtil.getAESKey();
		byte[] plainBytes = plainXML.getBytes(charset);
		byte[] keyBytes = keyStr.getBytes(charset);
		String encryptData = new String(Base64.encodeBase64((AESUtil.encrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
		String signData = new String(Base64.encodeBase64(RSAUtil.digitalSign(plainBytes, Constants.privateKey, "SHA1WithRSA")), charset);
		String encrtptKey = new String(Base64.encodeBase64(RSAUtil.encrypt(keyBytes, Constants.publicKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
		// 请求报文加密结束

		Map<String, String> map = new HashMap<String, String>();
		map.put("encryptData", encryptData);
		map.put("encryptKey", encrtptKey);
		map.put("cooperator", cooperator);
		map.put("signData", signData);
		map.put("tranCode", tranCode);
		map.put("reqMsgId", reqMsgId);

		String respStr = HttpUtil.doPost(AppConfig.getProperty("gs_auth_url"), map);
		System.out.println("返回密文======" + respStr);

		//返回报文解密开始
		JSONObject jsonObject = JSONObject.parseObject(respStr);
		if (!jsonObject.containsKey("encryptData") || !jsonObject.containsKey("encryptKey")) {
			System.out.println("请求异常======" + respStr);
		} else {
			String resEncryptData = jsonObject.getString("encryptData");
			String resEncryptKey = jsonObject.getString("encryptKey");
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			byte[] merchantAESKeyBytes = RSAUtil.decrypt(decodeBase64KeyBytes, Constants.privateKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			byte[] merchantXmlDataBytes = AESUtil.decrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String respXML = new String(merchantXmlDataBytes, charset);
			System.out.println("返回明文======" + respXML);

			// 报文验签
			String resSignData = jsonObject.getString("signData");
			byte[] signBytes = Base64.decodeBase64(resSignData);
			if (!RSAUtil.verifyDigitalSign(respXML.getBytes(charset), signBytes, Constants.publicKey, "SHA1WithRSA")) {
				System.out.println("签名验证失败");
			} else {
				System.out.println("签名验证成功");
			}
			return respXML;
		}
		return "";
	}


	/**
	 * 对没有用户信息的代理商进行工商认证提取数据
	 * @param agentId
	 * @return
	 */
	@Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	@Override
	public AgentResult agentBusinessCaToAgentDb(String agentId) {
		Agent agent = agentMapper.selectByPrimaryKey(agentId);
		if(agent==null)return AgentResult.fail("工商认证代理商未找到"+agentId);
		//查询代理商信息检查是否需要提取数据
		if(StringUtils.isNotBlank(agent.getAgLegal())){return AgentResult.fail("工商认证代理商法人信息不为空不进行工商认证"+agentId);}
		//处理代理名称去掉前缀和括号后的信息
		String agname = agent.getAgName();
		logger.info("后台任务进行工商认证|{}|{}",agent.getId(),agname);
		agname = agname.replaceAll("[A-Z]|\\(*\\)","");
		logger.info("后台任务进行工商认证|{}|替换后名称|{}",agent.getId(),agname);
		//工商认证
		AgentResult agentResult = agentBusinessCA(agname);
		if(agentResult.isOK()){
			//补全代理商信息
			JSONObject dataObj = (JSONObject)agentResult.getData();

			if(StringUtils.isNotBlank(dataObj.getString("regCap")))
			 agent.setAgCapital(new BigDecimal(dataObj.getString("regCap").trim()));

			if(StringUtils.isNotBlank(dataObj.getString("openFrom")))
			agent.setAgBusLicb(DateUtil.format(dataObj.getString("openFrom"),"yyyy-MM-dd"));

			if(StringUtils.isNotBlank(dataObj.getString("openTo")) && dataObj.getString("openTo").equals("长期")){
				agent.setAgBusLice(DateUtil.format("2099-12-31","yyyy-MM-dd"));
			}else if(StringUtils.isNotBlank(dataObj.getString("openTo"))){
				agent.setAgBusLice(DateUtil.format(dataObj.getString("openTo"),"yyyy-MM-dd"));
			}
			agent.setAgLegal(dataObj.getString("frName"));//法人姓名
			agent.setAgRegAdd(dataObj.getString("address"));//注册地址
			agent.setAgBusScope(dataObj.getString("operateScope"));
			agent.setAgBusLic(dataObj.getString("creditCode"));//营业执照
			//如果负责人没有，采用工商认证后的法人
			if(StringUtils.isBlank(agent.getAgHead())){
				agent.setAgHead(agent.getAgLegal());
			}
			agent.setCaStatus(Status.STATUS_1.status);
			if(1==agentMapper.updateByPrimaryKeySelective(agent)){
				logger.info("工商认证成功，提取信息成功"+agent.getId());
			}
			return AgentResult.ok();
		}else{
			//工商认证失败
			agent.setCaStatus(Status.STATUS_2.status);
			if(1==agentMapper.updateByPrimaryKeySelective(agent)){
				logger.info("工商认证失败"+agent.getId());
			}
			return AgentResult.fail();
		}

	}


	@Override
	public AgentResult caAgentList(){

		threadPoolTaskExecutor.execute(new Runnable() {

			@Override
			public void run() {

				logger.info("后台任务进行批量工商认证");
				AgentExample example = new AgentExample();
				example.or().andCaStatusEqualTo(Status.STATUS_0.status).andAgLegalIsNull();
				example.or().andCaStatusIsNull().andAgLegalIsNull();
				List<Agent> agents = agentMapper.selectByExample(example);
				for (Agent agent : agents) {
					try {
						logger.info("后台任务进行工商认证{}|{}",agent.getId(),agent.getAgName());
						AgentResult res = businessCAService.agentBusinessCaToAgentDb(agent.getId());
						logger.info("后台任务进行工商认证{}|{}|结果|{}",agent.getId(),agent.getAgName(),res.getMsg());
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("后台任务进行工商认证"+agent.getId(),e);
					}
				}

			}
		});

		return AgentResult.ok();
	}



}
