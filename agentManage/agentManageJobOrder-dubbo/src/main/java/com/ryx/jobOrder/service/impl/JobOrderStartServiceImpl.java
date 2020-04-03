package com.ryx.jobOrder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Service("jobOrderStartService")
public class JobOrderStartServiceImpl implements JobOrderStartService {

    private static Logger logger = LoggerFactory.getLogger(JobOrderStartServiceImpl.class);

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
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public FastMap createJobOrder(JoOrder jo, Map otherMap) throws Exception {
        logger.info("创建工单，代理商ID"+jo.getAgId());
        jo.setId(idService.genId(TabId.jo_order));// 生成ID
        jo.setLaunchTime(new Date());
        jo.setVersion(version);
        jo.setJoProgress(JoOrderStatus.WCL.getValue());
        jo.setAcceptNowGroup(jo.getAcceptGroup());
        Object annoTableFile = (Object)otherMap.get("annoTableFile");
        if(annoTableFile != null ){
            saveAttachments(jo.getId(), (String)otherMap.get("userId"), ((String)annoTableFile).split(","));
        }
        otherMap.remove("userId");
        otherMap.remove("annoTableFile");
        int insertStu =  joOrderMapper.insert(jo);
        if(insertStu!=1){
            throw  new  MessageException("工单创建失败,工单编号"+jo.getId()+"代理商ID:"+jo.getAgId());
        }
        String sedGroup = (String)otherMap.get("sedGroup");
        otherMap.remove("sedGroup");
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
        JoTask joTask = new JoTask();
        joTask.setId( idService.genId(TabId.jo_task) );
        joTask.setJoId(jo.getId());
        joTask.setDealGroup(jo.getAcceptGroup());
        joTask.setDealGroupId(jo.getAcceptGroupCode());
        joTask.setSecondDealGroup(sedGroup);
        FastMap status = jobOrderTaskService.createJobOrderTask(joTask);
        if(!FastMap.isSuc(status)){
           throw new MessageException("创建工单任务失败！工单任务:" + joTask.getId());
        }
        logger.info("创建工单成功");
        return FastMap.fastSuccessMap();
    }


    /**
     * 保存工单扩展字段值
     * @param joExpandKey
     * @return
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
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
        logger.info("taskId查工单任务，taskID"+taskId);
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
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
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
        joExpandKeyExample.setOrderByClause("jm.JO_KEY_SORT");
        List<JoExpandKey> list = joExpandKeyMapper.selectByExampleLeftManage(joExpandKeyExample);
        return list;
    }
}
