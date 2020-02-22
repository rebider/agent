package com.ryx.jobOrder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.JoTaskStatus;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.service.dict.IdService;
import com.ryx.jobOrder.dao.JoExpandKeyMapper;
import com.ryx.jobOrder.dao.JoOrderMapper;
import com.ryx.jobOrder.pojo.JoExpandKey;
import com.ryx.jobOrder.pojo.JoKeyManage;
import com.ryx.jobOrder.pojo.JoOrder;
import com.ryx.jobOrder.pojo.JoTask;
import com.ryx.jobOrder.service.JobOrderStartService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JobOrderStartServiceImpl implements JobOrderStartService {

    @Autowired
    private JoOrderMapper joOrderMapper;

    @Autowired
    private JoExpandKeyMapper joExpandKeyMapper;

    @Autowired
    private IdService idService;
    /**
     * 创建工单服务
     * @param jo
     * @param otherMap
     * @return
     */
    @Override
    public FastMap createJobOrder(JoOrder jo, Map otherMap) {
        jo.setId("");// 生成ID
        joOrderMapper.insert(jo);
        for(Object keyo : otherMap.keySet()){ //keySet获取map集合key的集合  然后在遍历key即可
            String key = (String)keyo;
            String value = (String)otherMap.get(key);
            // 通过key 查询 key 的类型
            JoKeyManage joKeyManage = new JoKeyManage();//TODO 方法返回对象
            JoExpandKey joExpandKey = new JoExpandKey();
            joExpandKey.setJid(jo.getId());
            joExpandKey.setJoKeyId(joKeyManage.getId());
            joExpandKey.setJoKey(joKeyManage.getJoKey());
            joExpandKey.setJoKeyValueType(joKeyManage.getJoKeyValueType());
            joExpandKey.setJoKeyName(joKeyManage.getJoKeyName());
            joExpandKey.setJoKeyValue(value);
            saveJobOrderExband(joExpandKey);
        }
        // 查询受理组
        String acceptGroup = "";
        JoTask joTask = new JoTask();
        joTask.setId( idService.genId(TabId.jo_order_task) );
        joTask.setJoId(jo.getId());
        joTask.setJoTaskStatus(JoTaskStatus.WSL.getValue());
        joTask.setJoTaskTime(new Date());
        joTask.setDealGroup(acceptGroup);
        joTask.setDealGroupId("");
        joTask.setDealPersonId("");
        joTask.setDealPersonName("");
        joTask.setJoTaskContent(jo.getJoContent());
        return FastMap.fastSuccessMap();
    }

    /**
     * 获取代理商或者内部人员工单页面所需数据
     * @param para
     * @return
     */
    @Override
    public List<FastMap> getStartLaunchVo(JSONObject para) {
        String fir = para.getString("fir");// 一级类型编号
        String sed = para.getString("sed"); // 二级类型编号
        // TODO 查询
        List customs = new ArrayList();
        customs.add(FastMap.fastMap("key","code").putKeyV("value","编码"));
        customs.add(FastMap.fastMap("key","date").putKeyV("value","日期"));
        customs.add(FastMap.fastMap("key","date2").putKeyV("value","日期2"));
        customs.add(FastMap.fastMap("key","date3").putKeyV("value","日期3"));
        customs.add(FastMap.fastMap("key","date3").putKeyV("value","日期4"));
        return customs;
    }

    /**
     * 保存工单扩展字段值
     * @param joExpandKey
     * @return
     */
    @Override
    public FastMap saveJobOrderExband(JoExpandKey joExpandKey) {
        joExpandKeyMapper.insert(joExpandKey);
        return FastMap.fastSuccessMap();
    }
}
