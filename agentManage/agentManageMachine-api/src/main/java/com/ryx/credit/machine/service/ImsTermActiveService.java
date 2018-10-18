package com.ryx.credit.machine.service;

import com.ryx.credit.machine.entity.ImsTermActive;

/**
 * Created by RYX on 2018/10/11.
 */
public interface ImsTermActiveService {

    ImsTermActive selectByPrimaryKey(String posSn);

}
