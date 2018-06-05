package com.ryx.credit.service.dict;

import com.ryx.credit.pojo.admin.agent.Dict;

import java.util.List;

/**
 * Created by cx on 2018/5/22.
 */
public interface DictOptionsService {


    public List<Dict> dictList(String group,String artifact);

}
