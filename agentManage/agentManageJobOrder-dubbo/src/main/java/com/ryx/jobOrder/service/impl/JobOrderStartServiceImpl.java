package com.ryx.jobOrder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.JoOrderStatus;
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
import com.ryx.jobOrder.service.JobOrderAuthService;
import com.ryx.jobOrder.service.JobOrderManageService;
import com.ryx.jobOrder.service.JobOrderStartService;
import com.ryx.jobOrder.service.JobOrderTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Service("jobOrderStartService")
public class JobOrderStartServiceImpl implements JobOrderStartService {
    private final BigDecimal version = new BigDecimal(0);
    @Autowired
    private JoOrderMapper joOrderMapper;

    @Autowired
    private JobOrderAuthService jobOrderAuthService;

    @Autowired
    private JoExpandKeyMapper joExpandKeyMapper;

    @Autowired
    private JobOrderTaskService jobOrderTaskService;

    @Autowired
    private JobOrderManageService jobOrderManageService;

    @Autowired
    private IdService idService;
    /**
     * 创建工单服务
     * @param jo  工单数据对象
     * @param otherMap 关键词信息对象
     * @return
     */
    @Override
    public FastMap createJobOrder(JoOrder jo, Map otherMap) throws Exception {
        jo.setId(idService.genId(TabId.jo_order));// 生成ID
        jo.setVersion(version);
        jo.setJoProgress(JoOrderStatus.WCL.getValue());
        // 查找受理部门
        Map acceptMap = jobOrderAuthService.getJobOrderType(jo.getJoSecondKeyNum());
        if(acceptMap!=null){
            jo.setAcceptGroup((String)acceptMap.get("name"));
            jo.setAcceptGroupCode((String)acceptMap.get("desdription"));
        }

        joOrderMapper.insert(jo);
        if(otherMap!= null ){
            for(Object keyo : otherMap.keySet()){ //keySet获取map集合key的集合  然后在遍历key即可
                int index = 0;
                String key = (String)keyo;
                String value = (String)otherMap.get(key);
                // 通过key 查询 key 的类型
                JoKeyManage joKeyManage = jobOrderManageService.queryKeywordDialog(key);
                JoExpandKey joExpandKey = new JoExpandKey();
                joExpandKey.setJid(jo.getId());
                joExpandKey.setJoKeyId(joKeyManage.getId());
                joExpandKey.setJoKey(joKeyManage.getJoKey());
                joExpandKey.setJoKeyValueType(joKeyManage.getJoKeyValueType());
                joExpandKey.setJoKeyName(joKeyManage.getJoKeyName());
                joExpandKey.setJoKeyValue(value);
                joExpandKey.setJoExpandSort(new BigDecimal(index));
                saveJobOrderExband(joExpandKey);
                index++;
            }
        }

        // 查询受理组
        String acceptGroup = "";
        JoTask joTask = new JoTask();
        joTask.setId( idService.genId(TabId.jo_task) );
        joTask.setJoId(jo.getId());
        joTask.setDealGroup(acceptGroup);
        joTask.setDealGroupId("");
        joTask.setDealPersonId("");
        joTask.setDealPersonName("");
        joTask.setJoTaskContent(jo.getJoContent());
        jobOrderTaskService.createJobOrderTask(joTask);
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

    /**
     * 根据taskID返回单条工单数据
     * @param taskId
     * @return
     */
    @Override
    public JoOrder queryListByTaskId(String taskId) {
        List<JoOrder> orders =  joOrderMapper.queryListByTaskId(taskId);
        if(orders!=null && orders.size()>0){
            return orders.get(0);
        }
        return null;
    }


}
