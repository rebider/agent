package com.ryx.jobOrder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.pojo.admin.agent.AttachmentRel;
import com.ryx.credit.service.agent.AttachmentRelService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.jobOrder.dao.JoExpandKeyMapper;
import com.ryx.jobOrder.dao.JoOrderMapper;
import com.ryx.jobOrder.pojo.*;
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

    @Autowired
    private AttachmentRelService attachmentRelService;

    /**
     * 创建工单服务
     * @param jo  工单数据对象
     * @param otherMap 关键词信息对象
     * @return
     */
    @Override
    public FastMap createJobOrder(JoOrder jo, Map otherMap) throws Exception {
        jo.setId(idService.genId(TabId.jo_order));// 生成ID
        jo.setLaunchTime(new Date());
        jo.setVersion(version);
        jo.setJoProgress(JoOrderStatus.WCL.getValue());
        jo.setAcceptNowGroup(jo.getAcceptGroup());
        Object annoTableFile = (Object)otherMap.get("annoTableFile");
        String annexId = null;
        if(annoTableFile != null ){
            saveAttachments(jo.getId(), (String)otherMap.get("userId"), ((String)annoTableFile).split(","));
            annexId = (((String)annoTableFile).split(","))[0];
        }
        otherMap.remove("userId");
        otherMap.remove("annoTableFile");
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
        joTask.setJoTaskAnnexId(null);
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

    /**
     * AGID
     * 查询所在省区
     * @param agId
     * @return
     */
    @Override
    public List<Map> queryAgPro(String agId){
        return  joOrderMapper.queryAgPro(agId);
    }
    /**
     * 保存附件信息
     */
    public FastMap saveAttachments(String joId, String userid, String[] attachments) {
        try {
            for (String attach : attachments) {
                AttachmentRel attachmentRel = new AttachmentRel();
                attachmentRel.setId(idService.genId(TabId.a_attachment_rel));
                attachmentRel.setSrcId(joId);
                attachmentRel.setAttId(attach);
                attachmentRel.setBusType(AttachmentRelType.jobOrder.name());
                attachmentRel.setcTime(new Date());
                attachmentRel.setcUser(userid);
                attachmentRel.setStatus(Status.STATUS_1.status);
                attachmentRelService.insertSelective(attachmentRel);
            }
        } catch (Exception e) {
            return FastMap.fastFailMap("附件保存失败");
        }
        return FastMap.fastSuccessMap();
    }

    /**
     * 查询 扩展字段 列表
     * @param joId
     * @return
     */
    @Override
    public List<JoExpandKey> queryExpandKeyByJoid(String joId){
        JoExpandKeyExample joExpandKeyExample = new JoExpandKeyExample();
        JoExpandKeyExample.Criteria criteria = joExpandKeyExample.createCriteria();
        criteria.andJidEqualTo(joId);
        return joExpandKeyMapper.selectByExample(joExpandKeyExample);
    }
}
