package com.ryx.credit.service.impl;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.dao.CuserAgentMapper;
import com.ryx.credit.pojo.admin.CuserAgent;
import com.ryx.credit.pojo.admin.CuserAgentExample;
import com.ryx.credit.pojo.admin.CuserAgentKey;
import com.ryx.credit.service.ICuserAgentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CuserAgentServiceImpl
 * Created by IntelliJ IDEA.
 *
 * @author Wang Qi
 * @version 1.0 2018/8/2 19:57
 * @see CuserAgentServiceImpl
 * To change this template use File | Settings | File Templates.
 */
@Service("cuserAgentService")
public class CuserAgentServiceImpl implements ICuserAgentService {
    @Autowired
    private CuserAgentMapper cuserAgentMapper;

    @Override
    public long countByExample(CuserAgentExample example) {
        return cuserAgentMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(CuserAgentExample example) {
        return cuserAgentMapper.deleteByExample(example);
    }

    @Override
    public int insert(CuserAgent record) {
        return cuserAgentMapper.insert(record);
    }

    @Override
    public int insertSelective(CuserAgent record) {
        return cuserAgentMapper.insertSelective(record);
    }

    @Override
    public List<CuserAgent> selectByExample(CuserAgentExample example) {
        return cuserAgentMapper.selectByExample(example);
    }

    @Override
    public CuserAgent selectByPrimaryKey(CuserAgentKey key) {
        return cuserAgentMapper.selectByPrimaryKey(key);
    }

    @Override
    public int updateByPrimaryKeySelective(CuserAgent record) {
        return cuserAgentMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CuserAgent record) {
        return cuserAgentMapper.updateByPrimaryKey(record);
    }

    @Override
    public Map configExample(Page page, CuserAgent cuserAgent) {
        if(cuserAgent!=null && page !=null){
            CuserAgentExample cuserAgentExample = new CuserAgentExample();
            CuserAgentExample.Criteria criteria = cuserAgentExample.or();
            if(cuserAgent.getAgentid()!=null)
                criteria.andAgentidEqualTo(cuserAgent.getAgentid());
            if(cuserAgent.getUserid()!=null)
                criteria.andUseridEqualTo(cuserAgent.getUserid());
            Long count = cuserAgentMapper.countByExample(cuserAgentExample);
            page.setCount(count.intValue());
            cuserAgentExample.setPage(page);
//            cuserAgentExample.setOrderByClause("CREATE_TIME "+Page.ORDER_DIRECTION_DESC);
            HashMap hashMap = new HashMap();
            hashMap.put("list",cuserAgentMapper.selectByExample(cuserAgentExample));
            hashMap.put("page",page);
            return  hashMap;
        }else{
            return  null;
        }
    }
}
