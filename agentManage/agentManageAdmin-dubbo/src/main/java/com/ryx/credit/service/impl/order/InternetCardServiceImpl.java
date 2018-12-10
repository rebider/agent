package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.order.OInternetCardMapper;
import com.ryx.credit.dao.order.OLogisticsDetailMapper;
import com.ryx.credit.dao.order.OLogisticsMapper;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.InternetCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2018/12/4 14:38
 * @Param
 * @return
 **/
@Service("internetCardService")
public class InternetCardServiceImpl implements InternetCardService {

    @Autowired
    private OInternetCardMapper internetCardMapper;
    @Autowired
    private OLogisticsMapper logisticsMapper;
    @Autowired
    private OLogisticsDetailMapper logisticsDetailMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private DictOptionsService dictOptionsService;


    @Override
    public PageInfo internetCardList(OInternetCard internetCard, Page page){

        OInternetCardExample oInternetCardExample = new OInternetCardExample();
        OInternetCardExample.Criteria criteria = oInternetCardExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        oInternetCardExample.setPage(page);
        oInternetCardExample.setOrderByClause(" c_time desc ");
        List<OInternetCard> oInternetCards = internetCardMapper.selectByExample(oInternetCardExample);
        for (OInternetCard oInternetCard : oInternetCards) {
            Dict dict = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(),oInternetCard.getProCom());
            if(null!=dict)
            oInternetCard.setProCom(dict.getdItemname());
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(oInternetCards);
        pageInfo.setTotal((int)internetCardMapper.countByExample(oInternetCardExample));
        return pageInfo;
    }

    @Override
    public List<OInternetCard> exportInternetCard(OInternetCard internetCard){
        OInternetCardExample oInternetCardExample = new OInternetCardExample();
        OInternetCardExample.Criteria criteria = oInternetCardExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);

        List<OInternetCard> oInternetCards = internetCardMapper.selectByExample(oInternetCardExample);
        for (OInternetCard oInternetCard : oInternetCards) {
            Dict dict = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(),oInternetCard.getProCom());
            if(null!=dict)
            oInternetCard.setProCom(dict.getdItemname());
        }
        return oInternetCards;
    }


    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void importInternetCard(List<List<Object>> excelList, String importType, String userId)throws Exception{

        if(StringUtils.isBlank(CardImportType.getContentByValue(importType))){
             throw new MessageException("导入类型错误");
        }
        if(null==excelList && excelList.size()==0){
            throw new MessageException("excel列表为空");
        }
        try {
            int index = 1;
            for (List<Object> object : excelList) {
                String snNum = "";
                String iccidNum = "";
                if(importType.equals(CardImportType.COM.getValue()) || importType.equals(CardImportType.TY.getValue())
                   || importType.equals(CardImportType.XDL.getValue()) || importType.equals(CardImportType.XGG.getValue())){
                    snNum = String.valueOf(object.get(0));
                    iccidNum = String.valueOf(object.get(1));
                }else if(importType.equals(CardImportType.LD.getValue())){
                    snNum = String.valueOf(object.get(1));
                    iccidNum = String.valueOf(object.get(2));
                }
                if(StringUtils.isBlank(snNum)){
                    throw new MessageException("第"+index+"个sn号为空");
                }
                if(StringUtils.isBlank(iccidNum)){
                    throw new MessageException("第"+index+"个iccid号为空");
                }
                importCard(index,snNum,iccidNum,userId);
            }
        }catch (MessageException e) {
            e.printStackTrace();
            throw new MessageException("excel解析失败:"+e.getMsg());
        }catch (Exception e) {
            e.printStackTrace();
            throw new MessageException("excel解析失败");
        }
    }


    public void importCard(int index,String snNum,String iccidNum,String userId)throws Exception{
        OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
        OLogisticsDetailExample.Criteria LogisticsDetailCriteria = oLogisticsDetailExample.createCriteria();
        LogisticsDetailCriteria.andSnNumEqualTo(snNum);
        LogisticsDetailCriteria.andStatusEqualTo(OLogisticsDetailStatus.STATUS_FH.code);
        LogisticsDetailCriteria.andRecordStatusEqualTo(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
        List<OLogisticsDetail> oLogisticsDetails = logisticsDetailMapper.selectByExample(oLogisticsDetailExample);
        if(oLogisticsDetails==null){
            throw new MessageException("第"+index+"个查询SN失败.");
        }
        if(oLogisticsDetails.size()!=1) {
            throw new MessageException("第"+index+"个请检查sn有效性.");
        }
        OLogisticsDetail oLogisticsDetail = oLogisticsDetails.get(0);

        OInternetCardExample oInternetCardExample = new OInternetCardExample();
        OInternetCardExample.Criteria criteria = oInternetCardExample.createCriteria();
        criteria.andSnNumEqualTo(snNum);
        List<OInternetCard> oInternetCards = internetCardMapper.selectByExample(oInternetCardExample);
        if(oInternetCards==null){
            throw new MessageException("第"+index+"个查询SN失败..");
        }

        OInternetCard oInternetCard = new OInternetCard();
        oInternetCard.setSnNum(snNum);
        oInternetCard.setIccidNum(iccidNum);
        oInternetCard.setAgentId(oLogisticsDetail.getAgentId());
        oInternetCard.setLogisticsDetailId(oLogisticsDetail.getId());
        Agent agent = agentMapper.selectByPrimaryKey(oLogisticsDetail.getAgentId());
        if(agent==null){
            throw new MessageException("第"+index+"个查询代理商信息失败..");
        }
        oInternetCard.setAgentName(agent.getAgName());
        OLogistics oLogistics = logisticsMapper.selectByPrimaryKey(oLogisticsDetail.getLogisticsId());
        oInternetCard.setLogisticsId(oLogistics.getId());
        oInternetCard.setProCom(oLogistics.getProCom());
        oInternetCard.setuTime(new Date());
        oInternetCard.setuUser(userId);
        if(oInternetCards.size()==0) {
            oInternetCard.setDebtAmt(new BigDecimal(0));
            oInternetCard.setcTime(new Date());
            oInternetCard.setVersion(Status.STATUS_0.status);
            oInternetCard.setStatus(Status.STATUS_1.status);
            oInternetCard.setCardStatus(CardStatus.WZ.getValue());
            oInternetCard.setId(idService.genId(TabId.O_INTERNET_CARD));
            oInternetCard.setcUser(userId);
            oInternetCard.setExceedFlow("0");
            oInternetCard.setExceedFlowUnit(ExceedFlowUnit.M.getValue());
            internetCardMapper.insert(oInternetCard);
        }else{
            OInternetCard internetCard = oInternetCards.get(0);
            oInternetCard.setId(internetCard.getId());
            int i = internetCardMapper.updateBySnSelective(oInternetCard);
            if(i!=1){
                throw new MessageException("第"+index+"个更新数据失败");
            }
        }
    }
}
