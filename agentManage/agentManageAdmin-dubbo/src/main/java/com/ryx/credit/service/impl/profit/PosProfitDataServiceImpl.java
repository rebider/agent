package com.ryx.credit.service.impl.profit;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.agentUtil.AESUtil;
import com.ryx.credit.common.util.agentUtil.HttpUtil;
import com.ryx.credit.common.util.agentUtil.RSAUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.service.profit.IPosProfitDataService;
import com.ryx.credit.util.Constants;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: Zhang Lei
 * @Description: POS分润数据获取服务
 * @Date: 20:23 2018/7/31
 */
@Service("posProfitDataService")
public class PosProfitDataServiceImpl implements IPosProfitDataService {

    private static Logger logger = LoggerFactory.getLogger(PosProfitDataServiceImpl.class);

    /**
     * @Author: Zhang Lei
     * @Description: pos月分润数据获取
     * @Param settleMonth 请求月份（格式：201807）
     * @Date: 20:24 2018/7/31
     */
    @Override
    public AgentResult getPosProfitDate(String settleMonth) throws ProcessException {
        AgentResult result = new AgentResult(500,"参数错误","");
        if (StringUtils.isBlank(settleMonth)) {
            logger.info("pos月分润数据获取:{}","请求月份为空");
            return result;
        }
        try {
            result.setMsg("服务器异常");
            String httpResult = posRequest(settleMonth);
            if(StringUtils.isBlank(httpResult)){
                return result;
            }
            JSONObject jsonObject = JSONObject.parseObject(httpResult);
            JSONObject dataMap = JSONObject.parseObject(String.valueOf(jsonObject.get("data")));
            String respType = (String)jsonObject.get("respType");
            String respCode = (String)jsonObject.get("respCode");
            if(respType.equals("E") || !respCode.equals("000000")){
                return new AgentResult(404,String.valueOf(jsonObject.get("respMsg")),"");
            }else{
                return AgentResult.ok(dataMap);
            }
        } catch (Exception e) {
            logger.info("pos月分润数据获取异常:{}",e.getMessage());
            e.printStackTrace();
        }
        return result;
    }


    private String posRequest(String settleMonth)throws Exception{

        String cooperator = Constants.cooperator;
        String charset = "UTF-8"; // 字符集
        String tranCode = "PFT001"; // 交易码
        String reqMsgId = UUID.randomUUID().toString().replace("-", ""); // 请求流水
        String reqDate = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"); // 请求时间

        JSONObject jsonParams = new JSONObject();
        JSONObject data = new JSONObject();
        jsonParams.put("version", "1.0.0");
        jsonParams.put("msgType", "01");
        jsonParams.put("reqDate", reqDate);

        data.put("settleMonth", settleMonth);
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

        String respStr = HttpUtil.doPost(Constants.serverUrl, map);
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
}
