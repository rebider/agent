package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.dao.order.OLogisticsMapper;
import com.ryx.credit.pojo.admin.order.OLogistics;
import com.ryx.credit.pojo.admin.order.OLogisticsUtil;
import com.ryx.credit.service.order.OLogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Lihl
 * @Date 2018/07/21
 * 排单管理：物流信息
 */
@Service("oLogisticService")
public class OLogisticServiceImpl implements OLogisticsService{
    public final static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    @Autowired
    private OLogisticsMapper oLogisticsMapper;

    @Override
    public Object oLogisticsList(PageInfo pageInfo) {
        Map<String, Object> condition = new HashMap<>();
        int offset = pageInfo.getNowpage();
        int pageSize = pageInfo.getPagesize();
        condition = pageInfo.getCondition();
        condition.put("pageNumBegin", (offset - 1) * pageSize + 1);
        if (offset <= 1) {
            condition.put("pageNumStop", pageSize);
        } else {
            condition.put("pageNumStop", offset * pageSize);
        }
        int size = oLogisticsMapper.countOLogistics(condition);

        List<OLogisticsUtil> configShareList = oLogisticsMapper.selectOLogistics(condition);
        System.out.println("-----------------------------------------" + JSONObject.toJSON(configShareList));
        pageInfo.setRows((ArrayList) configShareList);
        pageInfo.setTotal(size);
        return pageInfo;
    }

    @Override
    public OLogistics selectByPrimaryKey(String id) {
        OLogistics oLogistics = oLogisticsMapper.selectByPrimaryKey(id);
        if (oLogistics == null) {
            return oLogistics;
        }
        return oLogistics;
    }

    /**
     * 物流信息---导出排单信息
     */
    @Override
    public List<OLogisticsUtil> exportOLogistics(OLogistics oLogistics) {
        List<OLogisticsUtil> oLogisticsUtils = oLogisticsMapper.selectExprotOLogistics(oLogistics);
        return oLogisticsUtils;
    }

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
