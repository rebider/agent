package com.ryx.credit.service.impl.dict;

import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.dao.agent.DictMapper;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.agent.DictExample;
import com.ryx.credit.service.dict.DictOptionsService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Autowired
    private DictMapper dictMapper;

    @Override
    public List<Dict> dictList(String group, String artifact) {
        DictExample example  = new DictExample();
        example.or().andDGroupEqualTo(group)
                .andDArtifactEqualTo(artifact)
                .andDStatusEqualTo(Status.STATUS_1.status);
        example.setOrderByClause(" D_sort desc");
        return  dictMapper.selectByExample(example);
    }

    @Override
    public Dict findDictByValue(String group, String artifact,String itemValue) {
        DictExample example = new DictExample();
        DictExample.Criteria criteria = example.createCriteria();
        criteria.andDGroupEqualTo(group);
        criteria.andDItemvalueEqualTo(itemValue);
        criteria.andDArtifactEqualTo(artifact);
        List<Dict> dicts = dictMapper.selectByExample(example);
        if(null==dicts || dicts.size()!=1){
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

    @Override
    public boolean insertDict(Dict dict, @Param("tableName")String tableName) {
        if(StringUtils.isEmpty(dict.getdGroup()))dict.setdGroup(dictMapper.sqlId(tableName)+"");
        if(1 == dictMapper.insertDict(dict)) {
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int updateByPrimaryKeySelective(Dict record) {
        record.setdStatus(new BigDecimal(String.valueOf(Status.STATUS_0.status)));
        return dictMapper.updateByPrimaryKeySelective(record);
    }

}
