package com.ryx.credit.service.impl.dict;

import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.EnvironmentUtil;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.dao.agent.DictMapper;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.agent.DictExample;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.dict.DictOptionsService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 选项服务类
 * Created by cx on 2018/5/22.
 */
@Service("dictOptionsService")
public class DictOptionsServiceImpl implements DictOptionsService {

    private static Logger logger = LoggerFactory.getLogger(DictOptionsServiceImpl.class);

    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private IUserService iUserService;


    @Override
    public List<Dict> dictList(String group, String artifact) {
        DictExample example = new DictExample();
        example.or().andDGroupEqualTo(group)
                .andDArtifactEqualTo(artifact)
                .andDStatusEqualTo(Status.STATUS_1.status);
        example.setOrderByClause(" D_sort desc");
        return dictMapper.selectByExample(example);
    }

    @Override
    public Dict findDictByValue(String group, String artifact, String itemValue) {
        DictExample example = new DictExample();
        DictExample.Criteria criteria = example.createCriteria();
        criteria.andDGroupEqualTo(group);
        criteria.andDItemvalueEqualTo(itemValue==null?"":itemValue);
        criteria.andDArtifactEqualTo(artifact);
        criteria.andDStatusEqualTo(Status.STATUS_1.status);
        List<Dict> dicts = dictMapper.selectByExample(example);
        if (null == dicts || dicts.size() != 1) {
            return null;
        }
        return dicts.get(0);
    }

    @Override
    public Object dictList(PageInfo pageInfo) {
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
        int size = dictMapper.countDict(condition);
        List<Dict> configShareList = dictMapper.selectDict(condition);

        pageInfo.setRows((ArrayList) configShareList);
        pageInfo.setTotal(size);
        return pageInfo;
    }

    @Override
    public Dict selectByPrimaryKey(String id) {
        Dict platForm = dictMapper.selectByPrimaryKey(id);
        if (platForm == null) {
            return platForm;
        }
        return platForm;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean insertDict(Dict dict) throws Exception {
        int insertDict = dictMapper.insertDict(dict);
        if (1 == insertDict) {
            return true;
        } else {
            throw new Exception("添加失败！");
        }
    }

    @Override
    public int updateByPrimaryKeySelective(Dict record) {
        record.setdStatus(new BigDecimal(String.valueOf(Status.STATUS_0.status)));
        return dictMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Dict findDictByName(String group, String artifact, String itemName) {
        DictExample example = new DictExample();
        DictExample.Criteria criteria = example.createCriteria();
        criteria.andDGroupEqualTo(group);
        criteria.andDItemnameEqualTo(itemName);
        criteria.andDArtifactEqualTo(artifact);
        criteria.andDStatusEqualTo(Status.STATUS_1.status);
        List<Dict> dicts = dictMapper.selectByExample(example);
        if (null == dicts || dicts.size() != 1) {
            return null;
        }
        return dicts.get(0);
    }


    @Override
    public List<Dict> findDictListByName(String group, String artifact, String itemName) {
        DictExample example = new DictExample();
        DictExample.Criteria criteria = example.createCriteria();
        criteria.andDGroupEqualTo(group);
        criteria.andDItemnameEqualTo(itemName);
        criteria.andDArtifactEqualTo(artifact);
        criteria.andDStatusEqualTo(Status.STATUS_1.status);
        List<Dict> dicts = dictMapper.selectByExample(example);
        return dicts;
    }

    /**
     * 工作流获取名称版本
     * @param approveName
     * @return
     */
    @Override
    public String getApproveVersion(String approveName)throws ProcessException{
        if(EnvironmentUtil.isPreProduction()) {
            Dict approveMode = findDictByValue(DictGroup.AGENT.name(), DictGroup.PRE_APPROVE_MODE.name(), approveName);
            if (approveMode == null) {
                throw new ProcessException("审批流配置获取失败!");
            }
            return approveMode.getdItemvalue() + "_" + approveMode.getdItemname();
        }else{
            Dict approveMode = findDictByValue(DictGroup.AGENT.name(), DictGroup.APPROVE_MODE.name(), approveName);
            if (approveMode == null) {
                throw new ProcessException("审批流配置获取失败!");
            }
            return approveMode.getdItemvalue() + "_" + approveMode.getdItemname();
        }
    }

    /**
     * 内部人员根据名称查询指定流量卡
     * @param userId
     * @return
     */
    @Override
    public List<String> getAgentNameList(Long userId){
        CUser cUser = iUserService.selectById(userId);
        List<Dict> dataDictList = findDictListByName(DictGroup.CARD.name(), DictGroup.DATA_SHIRO.name(), cUser.getLoginName());
        List<String> agentNameList = new ArrayList<>();
        for (Dict dict : dataDictList) {
            agentNameList.add(dict.getdItemvalue());
        }
        return agentNameList;
    }

    /**
     * 查询字典值
     * @param dict
     * @return
     */
    @Override
    public Dict findDictByValueAndName(Dict dict) {
        logger.info("字典查询参数:{}",dict);
        Dict oldDict = dictMapper.selectDictByValueAndName(dict);
        logger.info("查询结果:{}", oldDict);
        return oldDict;
    }

    /**
     * 编辑字典值
     * @param dictMap
     * @return
     */
    @Override
    public int editDictByDict(Map<String, Dict> dictMap) {
        return 0;
    }


    /**
     * 更新字典表
     * @param oldDict
     * @param newDict
     * @return
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean editDictByOldDict(Dict oldDict, Dict newDict)throws Exception{
        if ( 1 == dictMapper.updateByOldDict(oldDict, newDict)) {
            return true;
        } else {
            throw new Exception("更新失败，请稍后再试！");
        }
    }

    @Override
    public List<Dict> queryDictList(Dict dict) {
        DictExample example = new DictExample();
        example.or().andDArtifactEqualTo(dict.getdArtifact()).andDStatusEqualTo(Status.STATUS_1.status);
        example.setOrderByClause(" D_sort desc");
        return dictMapper.selectByExample(example);
    }
}
