package com.ryx.credit.cms.controller.agent;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.pojo.admin.bank.BankLineNums;
import com.ryx.credit.pojo.admin.bank.EtbSysCardBinNo;
import com.ryx.credit.service.bank.BankLineNumService;
import com.ryx.credit.service.bank.BankRegionService;
import com.ryx.credit.service.bank.EtbSyscardinfoService;
import com.ryx.credit.service.dict.RegionService;
import com.ryx.credit.util.ProcessorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 卡bin控制层
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/5/22 18:02
 */
@Controller
@RequestMapping("/etbSysCard")
public class EtbSysCardInfoController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(EtbSysCardInfoController.class);
    @Autowired
    private EtbSyscardinfoService etbSyscardinfoService;
    @Autowired
    private BankRegionService bankRegionService;
    @Autowired
    private BankLineNumService bankLineNumService;
    @Autowired
    private RegionService regionService;


    @RequestMapping("/queryCardNo")
    @ResponseBody
    public Object queryCardNo(String bankCardNo){
        EtbSysCardBinNo cardBinByCardNo = null;
        try {
           cardBinByCardNo = etbSyscardinfoService.findCardBinByCardNo(bankCardNo);
        } catch (ProcessorException e) {
            return renderError(e.getMessage());
        }
        if(cardBinByCardNo==null){
            return renderError("卡号未能识别！");
        }
        return cardBinByCardNo;
    }

    /**
     * 获取支行列表
     * @param regionId
     * @param cloBankId
     * @return
     */
    @RequestMapping("/queryLineNumList")
    @ResponseBody
    public String queryLineNumList(String regionId,String cloBankId){

        List<BankLineNums> bankLineNumsList = null;
        List<Map<String,String>> resultList = new ArrayList<>();
        String resultJson = "";
        try {
            Boolean city = regionService.isCity(regionId);
            if(city){
                bankLineNumsList = bankLineNumService.findLineByCity(regionId, cloBankId);
            }else{
                bankLineNumsList = bankLineNumService.findLineByProvince(regionId, cloBankId);
            }
            bankLineNumsList.forEach(row->{
                Map<String,String> resultMap = new HashMap<>();
                resultMap.put("id",row.getBankbranchname());
                resultMap.put("text",row.getBankbranchname());
                resultMap.put("bankBranchId",row.getBankbranchid());
                resultMap.put("liqBankId",row.getLiqbankid());
                resultList.add(resultMap);
            });
            resultJson = JsonUtil.objectToJson(resultList);
        } catch (Exception e) {
           log.info("获取支行列表超时：{}",e.getMessage());
        }
        return resultJson;
    }

}
