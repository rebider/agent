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
@Service
public class DictOptionsServiceImpl implements DictOptionsService {


    @Autowired
    private DictMapper dictMapper;

    @Override
    public List<Dict> dictList(String group, String artifact) {
        DictExample example  = new DictExample();
        example.or().andDGroupEqualTo(group).andDArtifactEqualTo(artifact).andDStatusEqualTo(Status.STATUS_1.status);
        example.setOrderByClause(" D_sort desc");
        return  dictMapper.selectByExample(example);
    }
}
