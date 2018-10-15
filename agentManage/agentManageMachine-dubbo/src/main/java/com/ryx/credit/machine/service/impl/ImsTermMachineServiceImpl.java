package com.ryx.credit.machine.service.impl;

import com.ryx.credit.machine.dao.ImsTermMachineMapper;
import com.ryx.credit.machine.entity.ImsTermMachine;
import com.ryx.credit.machine.entity.ImsTermMachineExample;
import com.ryx.credit.machine.service.ImsTermMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by RYX on 2018/10/9.
 */
@Service("imsTermMachineService")
public class ImsTermMachineServiceImpl implements ImsTermMachineService {

    @Autowired
    private ImsTermMachineMapper imsTermMachineMapper;


    @Override
    public List<ImsTermMachine>  selectByExample(){
        ImsTermMachineExample imsTermMachineExample = new ImsTermMachineExample();
        return imsTermMachineMapper.selectByExample(imsTermMachineExample);
    }
}
