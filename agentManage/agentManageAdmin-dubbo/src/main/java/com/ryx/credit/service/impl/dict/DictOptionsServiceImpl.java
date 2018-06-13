package com.ryx.credit.service.impl.dict;

import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.dao.agent.DictMapper;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.agent.DictExample;
import com.ryx.credit.service.dict.DictOptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
