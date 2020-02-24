package com.ryx.jobOrder.service.impl;

import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.service.dict.IdService;
import com.ryx.jobOrder.dao.JoCustomKeyMapper;
import com.ryx.jobOrder.dao.JoKeyManageMapper;
import com.ryx.jobOrder.pojo.JoKeyManage;
import com.ryx.jobOrder.pojo.JoKeyManageExample;
import com.ryx.jobOrder.service.JobOrderManageService;
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
    public List<JoKeyManage> queryKeywordByJoStatus(String joKeyType) {
        JoKeyManageExample joKeyManageExample = new JoKeyManageExample();
        JoKeyManageExample.Criteria criteria = joKeyManageExample.createCriteria();
        if (StringUtils.isNotBlank(joKeyType)){
            criteria.andJoKeyTypeEqualTo(joKeyType);
        }
        /*if(StringUtils.isNotBlank(joKeyBackNum)){
            criteria.andJoKeyBackNumEqualTo(joKeyBackNum);
        }*/
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
}
