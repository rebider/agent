package com.ryx.jobOrder.service.impl;

import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.service.dict.IdService;
import com.ryx.jobOrder.dao.JoCustomKeyMapper;
import com.ryx.jobOrder.dao.JoKeyManageMapper;
import com.ryx.jobOrder.pojo.JoKeyManage;
import com.ryx.jobOrder.service.JobOrderManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
       /* if (null != joCustomKey) {
            if (StringUtils.isNotBlank(joCustomKey.getJoFirstKeyNum())) {
                map.put("keyNum", joCustomKey.getJoFirstKeyNum());
            }
        }*/
        List<Map<String, Object>> joCustomKeyList = joKeyManageMapper.keywordList(map, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(joCustomKeyList);
        pageInfo.setTotal(joKeyManageMapper.keywordCount(map));
        return pageInfo;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean keywordAdd(JoKeyManage joKeyManage) throws Exception {
        String s = idService.genId(TabId.JO_KEY_MANAGE);
        String substring = s.substring(0, 5);
        joKeyManage.setId(substring);
        joKeyManage.setJoStatus(Status.STATUS_1.status.toString());
        int insertDict = joKeyManageMapper.insertSelective(joKeyManage);
        if (1 == insertDict) {
            return true;
        } else {
            throw new Exception("添加失败！");
        }
    }

    @Override
    public AgentResult keywordDelete(String id, String user) {
        return null;
    }

    @Override
    public ResultVO keywordEdit(JoKeyManage joKeyManage) throws Exception {
        return null;
    }
}
