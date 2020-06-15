package com.ryx.kafka.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.PropUtils;
import com.ryx.credit.common.util.agentUtil.HttpUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.kafka.dao.KfkSendMessageMapper;
import com.ryx.kafka.pojo.KfkSendMessage;
import com.ryx.kafka.service.CardChangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 作者：cx
 * 时间：2020/6/15
 * 描述:
 */
@Service("cardChangeService")
public class CardChangeServiceImpl implements CardChangeService {

    private Logger logger = LoggerFactory.getLogger(CardChangeServiceImpl.class);

    @Autowired
    private KfkSendMessageMapper kfkSendMessageMapper;


    @Override
    public FastMap notifyCardChange(String key, String msg)throws MessageException {
        JSONObject cardInfo = JSONObject.parseObject(msg);
        FastMap para = FastMap.fastSuccessMap();
        para.putKeyV("merchId",cardInfo.getString("agentId"));//代理商编号
        para.putKeyV("payeeName",cardInfo.getString("cloRealname"));//结算户名
        para.putKeyV("payeeAcct",cardInfo.getString("cloBankAccount"));//结算卡号
        if(cardInfo.get("cloType")!=null){
            if(2==cardInfo.getIntValue("cloType")){//对私
                para.putKeyV("payeeCardType","0");//结算卡类型1 对公 0 对私
            }else  if(1==cardInfo.getIntValue("cloType")){//对公
                para.putKeyV("payeeCardType","1");//结算卡类型1 对公 0 对私
            }
        }
        para.putKeyV("cardId",cardInfo.getString("agLegalCernum"));//身份证号
        para.putKeyV("payeeBankCode",cardInfo.getString("branchLineNum"));//联行号
        para.putKeyV("payeeBankName",cardInfo.getString("cloBankBranch"));//行名
        para.putKeyV("dataSource",cardInfo.getString("01"));//00 POS综管平台 01 集团代理商 02 手刷分润平台 03 瑞展业 04 手刷代付
        para.putKeyV("status",cardInfo.getString("00"));//00 正常
        String url = PropUtils.getProp("qs_notify_card_change_server")+PropUtils.getProp("qs_notify_card_change_url");
        logger.info("通知清结算系统 {} {}",url,key);
        String res = HttpUtil.doPost(url,para);
        logger.info("通知清结算系统 {} {} {}",url,key,res);
        if(StringUtils.isNotBlank(res)){
            try {
                JSONObject jo = JSONObject.parseObject(res);
                if("00".equals(jo.getString("code"))){
                    return FastMap.fastSuccessMap();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return FastMap.fastFailMap();
    }
}
