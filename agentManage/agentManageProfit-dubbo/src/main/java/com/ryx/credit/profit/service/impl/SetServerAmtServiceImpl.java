package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.dao.SetServerAmtMapper;
import com.ryx.credit.profit.pojo.SetServerAmt;
import com.ryx.credit.profit.pojo.SetServerAmtExample;
import com.ryx.credit.profit.service.ISetServerAmtService;
import com.ryx.credit.service.dict.IdService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 服务费相关业务处理
 * @author chenliang
 * 2019/5/10
 *
 */
@Service("setServerAmtService")
public class SetServerAmtServiceImpl implements ISetServerAmtService {
    private static Logger logger = LoggerFactory.getLogger(SetServerAmtServiceImpl.class);
    @Autowired
    SetServerAmtMapper setServerAmtMapper;
    @Autowired
    IdService idService;

    @Override
    public List<Map<String, Object>> queryBumCode() {
        return setServerAmtMapper.queryBumCode();
    }

    @Override
    public List<SetServerAmt> selectByExample(SetServerAmtExample example) {
        return setServerAmtMapper.selectByExample(example);
    }

    @Override
    public PageInfo setServerAmtList(Map<String, Object> param, PageInfo pageInfo) {
        Integer count = setServerAmtMapper.setServerAmtCount(param);
        List<SetServerAmt> list = setServerAmtMapper.setServerAmtList(param);
        pageInfo.setTotal(count);
        pageInfo.setRows(list);
        logger.info("查询============================================服务费设置" + JSONObject.toJSON(list));
        return pageInfo;
    }

    @Override
    public List<Map<String, Object>> queryD(String bumId) {
        return setServerAmtMapper.queryD(bumId);
    }
    @Override
    public int insertSelective(SetServerAmt record){
        if(record.getId()==null||"".equals(record.getId())){
            record.setId(idService.genId(TabId.P_SERVER_AMT));
        }
        return setServerAmtMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(SetServerAmt record) {
        return setServerAmtMapper.updateByPrimaryKeySelective(record);
    }

}
