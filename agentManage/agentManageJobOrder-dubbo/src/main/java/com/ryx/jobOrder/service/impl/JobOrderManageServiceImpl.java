package com.ryx.jobOrder.service.impl;

import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.service.dict.IdService;
import com.ryx.jobOrder.dao.JoCustomKeyMapper;
import com.ryx.jobOrder.dao.JoKeyManageMapper;
import com.ryx.jobOrder.pojo.JoCustomKey;
import com.ryx.jobOrder.pojo.JoCustomKeyExample;
import com.ryx.jobOrder.pojo.JoKeyManage;
import com.ryx.jobOrder.pojo.JoKeyManageExample;
import com.ryx.jobOrder.service.JobOrderManageService;
import com.ryx.jobOrder.vo.JobKeyManageVo;
import com.ryx.jobOrder.vo.JobOrderVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("jobOrderManageService")
public class JobOrderManageServiceImpl implements JobOrderManageService {

    private static Logger log = LoggerFactory.getLogger(JobOrderManageServiceImpl.class);

    @Autowired
    private JoCustomKeyMapper joCustomKeyMapper;
    @Autowired
    private JoKeyManageMapper joKeyManageMapper;
    @Autowired
    private IdService idService;

    @Override
    public PageInfo keywordList(Page page, JoKeyManage joKeyManage) {
        HashMap<String, Object> map = new HashMap<>();
        if (null != joKeyManage) {
            if (StringUtils.isNotBlank(joKeyManage.getJoKey())) {
                map.put("joKey", joKeyManage.getJoKey());
            }
            if (StringUtils.isNotBlank(joKeyManage.getJoKeyType())) {
                map.put("joKeyType", joKeyManage.getJoKeyType());
            }
            if (StringUtils.isNotBlank(joKeyManage.getJoKeyName())) {
                map.put("joKeyName", joKeyManage.getJoKeyName());
            }
            if (StringUtils.isNotBlank(joKeyManage.getJoKeyBackNum())) {
                map.put("joKeyBackNum", joKeyManage.getJoKeyBackNum());
            }
        }
        List<Map<String, Object>> joCustomKeyList = joKeyManageMapper.keywordList(map, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(joCustomKeyList);
        pageInfo.setTotal(joKeyManageMapper.keywordCount(map));
        return pageInfo;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean keywordAdd(JoKeyManage joKeyManage,String userId) throws Exception {
        joKeyManage.setId(idService.genJoKeyManageId(TabId.JO_KEY_MANAGE,Integer.valueOf(userId)));
        joKeyManage.setJoStatus(Status.STATUS_1.status.toString());
        int insertDict = joKeyManageMapper.insertSelective(joKeyManage);
        if (1 == insertDict) {
            return true;
        } else {
            throw new Exception("添加失败！");
        }
    }

    @Override
    public AgentResult keywordDelete(String id) {
        if (StringUtils.isBlank(id)) return AgentResult.fail("ID不能为空");
        JoCustomKeyExample joCustomKeyExample = new JoCustomKeyExample();
        JoCustomKeyExample.Criteria criteria = joCustomKeyExample.createCriteria().andJoKeyIdEqualTo(id);
        List<JoCustomKey> joCustomKeyList = joCustomKeyMapper.selectByExample(joCustomKeyExample);
        if (null != joCustomKeyList || joCustomKeyList.size() > 0) {
            for (JoCustomKey joCustomKey : joCustomKeyList) {
                if (1 != joCustomKeyMapper.deletejoCustomKeyById(joCustomKey.getId())) {
                    log.info("工单自定义表删除失败");
                    AgentResult.fail("工单自定义表删除失败");
                }
            }
        }
        JoKeyManage joKeyManage = new JoKeyManage();
        joKeyManage.setId(id);
        joKeyManage.setJoStatus(Status.STATUS_0.status.toString());
        if (1 == joKeyManageMapper.updateByPrimaryKeySelective(joKeyManage)) {
            return AgentResult.ok("成功");
        }
        return AgentResult.fail();
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO keywordEdit(JoKeyManage joKeyManage) throws Exception {
        if ( 1 == joKeyManageMapper.updateByPrimaryKey(joKeyManage)) {
            return ResultVO.success(joKeyManage);
        } else {
            return ResultVO.fail("修改关键词失败");
        }
    }

    @Override
    public JoKeyManage queryKeywordDialog(String id) {
        return joKeyManageMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<JoKeyManage> queryKeywordByJoStatus(JoKeyManage joKeyManage) {
        JoKeyManageExample joKeyManageExample = new JoKeyManageExample();
        JoKeyManageExample.Criteria criteria = joKeyManageExample.createCriteria();
        if (StringUtils.isNotBlank(joKeyManage.getJoKeyType())){
            criteria.andJoKeyTypeEqualTo(joKeyManage.getJoKeyType());
        }
        if (StringUtils.isNotBlank(joKeyManage.getJoKey())){
            criteria.andJoKeyEqualTo(joKeyManage.getJoKey());
        }
        if(StringUtils.isNotBlank(joKeyManage.getJoKeyBackNum())){
            criteria.andJoKeyBackNumEqualTo(joKeyManage.getJoKeyBackNum());
        }
        criteria.andJoStatusEqualTo(Status.STATUS_1.status.toString());
        List<JoKeyManage> joKeyManageList = joKeyManageMapper.selectByExample(joKeyManageExample);
        if(null!=joKeyManageList && joKeyManageList.size()>0){
            return joKeyManageList;
        }

        return new ArrayList<>();
    }

    @Override
    public List selectLevel() {
        List<Map<String, Object>> mapList = joKeyManageMapper.selectLevel();
        return mapList;
    }

    @Override
    public List selectCustomListBySedType(JoCustomKey joCustomKey) {
        JoCustomKeyExample example = new JoCustomKeyExample();
        JoCustomKeyExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(joCustomKey.getJoSecondKeyNum())){
            criteria.andJoSecondKeyNumEqualTo(joCustomKey.getJoSecondKeyNum());
        }
        if(StringUtils.isNotBlank(joCustomKey.getJoKeyId())){
            criteria.andJoKeyIdEqualTo(joCustomKey.getJoKeyId());
        }
        List<JoCustomKey> mapList = joCustomKeyMapper.selectByExample(example);
        return mapList;
    }

    @Override
    public List selectCustomListMapBySedType(JoCustomKey joCustomKey) {
        List<Map> mapList = joCustomKeyMapper.selectCustomListMapBySedType(joCustomKey);
        return mapList;
    }

    @Override
    public List<Map> selectKeyWord(JoCustomKey joCustomKey) {
        if(null!=joCustomKey){
            if(StringUtils.isNotBlank(joCustomKey.getJoSecondKeyNum())){
                List<Map> joCustomKeyList=joCustomKeyMapper.selectKeyWord(joCustomKey.getJoSecondKeyNum());
                if(null!=joCustomKeyList && joCustomKeyList.size()>0){
                    return joCustomKeyList;
                }else {
                    new ArrayList<>();
                }

            }
        }
        return new ArrayList<>();
    }

    @Override
    public PageInfo joCustomKeyList(Page page, Map map) {
        List<Map<String, Object>> joCustomKeyList = joCustomKeyMapper.joCustomKeyList(map, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(joCustomKeyList);
        pageInfo.setTotal(joCustomKeyMapper.joCustomKeyCount(map));
        return pageInfo;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO joCustomKeyEdit(JobOrderVo jobOrderVo) throws Exception {
        if (null != jobOrderVo && null != jobOrderVo.getJobKeyManageVoList()) {
            for (JobKeyManageVo jobKeyManage : jobOrderVo.getJobKeyManageVoList()) {
                if(null!=jobKeyManage.getJoCustomKeyList() && jobKeyManage.getJoCustomKeyList().size()>0){
                    //删除原有的自定义表数据(二级类型可确定唯一性)
                    JoCustomKeyExample joCustomKeyExample = new JoCustomKeyExample();
                    JoCustomKeyExample.Criteria criteria1 = joCustomKeyExample.createCriteria().andJoSecondKeyNumEqualTo(jobOrderVo.getSecondId());
                    List<JoCustomKey> joCustomKeys = joCustomKeyMapper.selectByExample(joCustomKeyExample);
                    if(null!=joCustomKeys && joCustomKeys.size()>0){
                        for (JoCustomKey joCustomKey : joCustomKeys) {
                            if (1 != joCustomKeyMapper.deletejoCustomKeyById(joCustomKey.getId())) {
                                log.info("工单自定义表删除失败");
                                throw new MessageException("工单自定义表删除失败");
                            }
                        }
                    }
                    //添加
                    List<JoCustomKey> joCustomKeyList = jobKeyManage.getJoCustomKeyList();
                    for (JoCustomKey joCustomKey : joCustomKeyList) {
                        JoKeyManageExample joKeyManageExample = new JoKeyManageExample();
                        JoKeyManageExample.Criteria criteria = joKeyManageExample.createCriteria().andJoStatusEqualTo(Status.STATUS_1.status.toString()).andJoKeyEqualTo(joCustomKey.getJoKey());
                        List<JoKeyManage> joKeyManageList = joKeyManageMapper.selectByExample(joKeyManageExample);
                        if(null==joKeyManageList || joKeyManageList.size()==0){
                            log.info("查询关键词失败:{}", joKeyManageList);
                            throw new MessageException("查询关键词失败");
                        }
                        JoKeyManage joKeyManage = joKeyManageList.get(0);
                        joCustomKey.setId(idService.genId(TabId.jo_custom_key));
                        joCustomKey.setJoFirstKeyNum(jobOrderVo.getFirstId());
                        joCustomKey.setJoSecondKeyNum(jobOrderVo.getSecondId());
                        joCustomKey.setJoKey(joCustomKey.getJoKey());
                        joCustomKey.setJoKeyId(joKeyManage.getId());
                        joCustomKey.setJoKeyValueType(joKeyManage.getJoKeyValueType());
                        joCustomKey.setJoKeyNull(joCustomKey.getJoKeyNull());
                        joCustomKey.setJoKeySort(Status.STATUS_1.status);
                        if (1 != joCustomKeyMapper.insertSelective(joCustomKey)) {
                            log.info("添加工单自定义表失败");
                            throw new MessageException("添加工单自定义表失败");
                        }
                    }
                }
            }
        }
        return ResultVO.success(null);
    }

    @Override
    public List queryJobOrderType() {
        List<Map<String, Object>> mapsList = joKeyManageMapper.queryJobOrderType();
        return mapsList;
    }

}
