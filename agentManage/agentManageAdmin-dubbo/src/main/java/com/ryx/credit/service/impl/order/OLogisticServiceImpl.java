package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.dao.order.OLogisticsMapper;
import com.ryx.credit.service.order.OLogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author Lihl
 * @Date 2018/07/21
 * 排单管理：物流信息
 */
@Service("oLogisticService")
public class OLogisticServiceImpl implements OLogisticsService{

    @Autowired
    private OLogisticsMapper oLogisticsMapper;

    /**
     * 物流信息:
     * 1、列表查询
     * 2、导出物流信息
     */
    @Override
    public PageInfo getOLogisticsList(Map<String, Object> param, PageInfo pageInfo) {
        Long count = oLogisticsMapper.getOLogisticsCount(param);
        List<Map<String, Object>> list = oLogisticsMapper.getOLogisticsList(param);
        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(list);
        System.out.println("============================================" + JSONObject.toJSON(list));
        return pageInfo;
    }



}
