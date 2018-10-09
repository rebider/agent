package com.ryx.credit.machine.service;

import com.ryx.credit.machine.entity.ImsTermMachine;

import java.util.List;

/**
 * Created by RYX on 2018/10/9.
 */
public interface ImsTermMachineService {

    List<ImsTermMachine> selectByExample();
}
