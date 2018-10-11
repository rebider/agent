package com.ryx.credit.machine.service.impl;

import com.ryx.credit.machine.dao.ImsTermActiveMapper;
import com.ryx.credit.machine.entity.ImsTermActive;
import com.ryx.credit.machine.service.ImsTermActiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * @Author liudh
 * @Description //TODO
 * @Date 2018/10/11 10:20
 * @Param
 * @return
 **/
@Service("imsTermActiveService")
public class ImsTermActiveServiceImpl implements ImsTermActiveService {

    @Autowired
    private ImsTermActiveMapper imsTermActiveMapper;

    @Override
    public ImsTermActive selectByPrimaryKey(String posSn){
       return imsTermActiveMapper.selectByPrimaryKey(posSn);
    }

}
