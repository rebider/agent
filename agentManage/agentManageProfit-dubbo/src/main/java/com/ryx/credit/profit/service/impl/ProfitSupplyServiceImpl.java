package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.ProfitSupplyMapper;
import com.ryx.credit.profit.pojo.ProfitSupply;
import com.ryx.credit.profit.pojo.ProfitSupplyExample;
import com.ryx.credit.profit.service.ProfitSupplyService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @Author Lihl
 * @Date 2018/08/02
 * 分润管理：补款数据维护
 */
@Service("profitSupplyService")
public class ProfitSupplyServiceImpl implements ProfitSupplyService {
    private static Logger logger = LoggerFactory.getLogger(ProfitSupplyServiceImpl.class);

    @Autowired
    private ProfitSupplyMapper pProfitSupplyMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private IdService idService;

    /**
     * 1、列表查询
     */
    @Override
    public PageInfo getProfitSupplyList(Map<String, Object> param, PageInfo pageInfo) {
        if("99".equals(param.get("BUS_BIG_TYPE"))){
            param.put("sign","90");
        }
        Long count = pProfitSupplyMapper.getProfitSupplyCount(param);
        List<Map<String, Object>> list = pProfitSupplyMapper.getProfitSupplyList(param);
        for (int i = 0; i <list.size() ; i++) {
            if(list.get(i).get("BUS_BIG_TYPE").equals("01" )&& list.get(i).get("BUS_TYPE").equals("01" )){
                list.get(i).put("SUPPLY_TYPE","手刷退款补款");
            } else if (list.get(i).get("BUS_BIG_TYPE").equals("01" ) && list.get(i).get("BUS_TYPE").equals("02" )) {
                list.get(i).put("SUPPLY_TYPE","POS退单补款");
            }
        }

        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(list);
//        System.out.println("查询============================================" + JSONObject.toJSON(list));
        return pageInfo;
    }

    @Override
    public int countByExample(ProfitSupplyExample example) {
        return pProfitSupplyMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(ProfitSupplyExample example) {
        return pProfitSupplyMapper.deleteByExample(example);
    }

    @Override
    public int insert(ProfitSupply record) {
        return pProfitSupplyMapper.insert(record);
    }

    @Override
    public int insertSelective(ProfitSupply profitSupply) {
        profitSupply.setId(idService.genId(TabId.p_profit_supply));
        return pProfitSupplyMapper.insertSelective(profitSupply);
    }

    @Override
    public List<ProfitSupply> selectByExample(ProfitSupplyExample example) {
        return pProfitSupplyMapper.selectByExample(example);
    }

    @Override
    public ProfitSupply selectByPrimaryKey(String id) {
        return pProfitSupplyMapper.selectByPrimaryKey(id);
    }

    @Override
    public ProfitSupply selectByAgentMonth(ProfitSupply record) {
        return pProfitSupplyMapper.selectByAgentMonth(record);
    }

    @Override
    public BigDecimal getTotalByMonthAndPid(ProfitSupply record) {
        return pProfitSupplyMapper.getTotalByMonthAndPid(record);
    }


    @Override
    public int updateByPrimaryKeySelective(ProfitSupply record) {
        return pProfitSupplyMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ProfitSupply record) {
        return pProfitSupplyMapper.updateByPrimaryKey(record);
    }

    /**
     * 清除本月导入
     * @return
     */
    @Override

    public int resetData(String busBigType) {
        // 终审后不能清除
        String finalStatus = redisService.getValue("commitFinal");
        if (StringUtils.isBlank(finalStatus)) {
            if("1".equals(finalStatus)){
                logger.info("终审状态不能清除！");
                throw new ProcessException("终审状态不能清除！");
            }
        }
        Map<String,Object> param = new HashMap<>();
        param.put("busBigType",busBigType);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date date = calendar.getTime();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
        dateStr = dateStr.substring(0,7).replaceAll("-","");

        param.put("SUPPLY_DATE",dateStr);
        return pProfitSupplyMapper.resetData(param);
    }

    /**
     * 补款数据维护:
     * 1、导入补款数据
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public  List<String> importSupplyList(List<List<Object>> data,String sign) throws Exception {
        List<String> list = new ArrayList<>();
        if (null == data && data.size() == 0) {
            logger.info("导入数据为空");
            throw new MessageException("导入数据为空");
        }
        for (List<Object> supply : data) {
            if (supply.size() == 6){
                if(null==supply.get(0) || "".equals(supply.get(0))){
                    logger.info("代理商唯一码导入有误，请检查");
                    throw new MessageException("代理商唯一码导入有误，请检查");
                }
                if( null==supply.get(1) || "".equals(supply.get(1))){
                    logger.info("代理商名称导入有误，请检查");
                    throw new MessageException("代理商名称导入有误，请检查");
                }
                if( null==supply.get(4) || "".equals(supply.get(4))){
                    logger.info("补款类型导入有误，请检查");
                    throw new MessageException("补款类型导入有误，请检查");
                }
                if( null==supply.get(5) || "".equals(supply.get(5))){
                    logger.info("补款金额导入有误，请检查");
                    throw new MessageException("补款金额导入有误，请检查");
                }else{
                    try {
                        new BigDecimal(String.valueOf(supply.get(5)));
                    }catch (Exception e){
                        logger.info("补款金额不是小数，请检查");
                        throw new MessageException("补款金额不是小数，请检查");
                    }
                }
            }

        }


        for (List<Object> supply : data) {
            if (supply.size() == 6){
             ProfitSupply profitSupply = new ProfitSupply();
            profitSupply.setId(idService.genId(TabId.p_profit_supply));//ID序列号
            profitSupply.setSourceId(DateUtils.dateToStrings(new Date()));//录入日期
            try {
                //01 退单补款 02:机具返现 99：其它
                if ("02".equals(sign)) {
                    profitSupply.setBusBigType("02");
                } else {
                    profitSupply.setBusBigType("99");
                }
                profitSupply.setAgentId(null != supply.get(0) ? String.valueOf(supply.get(0)) : "");//代理商编码
                profitSupply.setAgentName(null != supply.get(1) ? String.valueOf(supply.get(1)) : "");//代理商名称
                profitSupply.setParentAgentId(null != supply.get(2) ? String.valueOf(supply.get(2)) : "");//上级代理商编号
                profitSupply.setParentAgentName(null != supply.get(3) ? String.valueOf(supply.get(3)) : "");//上级代理商名称
                profitSupply.setSupplyType(null != supply.get(4) ? String.valueOf(supply.get(4)) : "");//补款类型
                profitSupply.setSupplyAmt(new BigDecimal(String.valueOf(supply.get(5))));//补款金额
                profitSupply.setSupplyDate(LocalDate.now().plusMonths(-1).format(DateTimeFormatter.ISO_DATE).substring(0, 7).replace("-", ""));//月份

//                profitSupply.setSupplyDate(null!=supply.get(6)?String.valueOf(supply.get(6)):"");//月份
//                profitSupply.setSupplyCode(supply.get(7)!=null?"":String.valueOf(supply.get(7)));//补款码
                if (pProfitSupplyMapper.insertSelective(profitSupply) == 0) {
                    logger.info("导入失败！");
                    throw new MessageException(supply.toString() + "导入失败！");
                }
                logger.info("补款数据导入信息：{}", JSONObject.toJSON(profitSupply));
                list.add(profitSupply.getId());
            } catch (Exception e) {
                e.printStackTrace();
                throw new MessageException(supply.toString() + "导入格式错误！");
            }


            }
        }
        return list;
    }

    @Override
    public Map<String, Object> profitCount(Map<String, Object> param) {
        Map<String,Object> map=pProfitSupplyMapper.profitCount(param);
        return map;
    }

}
