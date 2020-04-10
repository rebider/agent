package com.ryx.credit.machine.service.impl;

import com.ryx.credit.machine.dao.LmsUserMapper;
import com.ryx.credit.machine.entity.LmsUser;
import com.ryx.credit.machine.service.LmsUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 内管账号用户实现类
 */
@Service("lmsUserService")
public class LmsUserServiceImpl implements LmsUserService {

    private static Logger logger = LoggerFactory.getLogger(LmsUserServiceImpl.class);

    @Autowired
    private LmsUserMapper lmsUserMapper;

    /**
     * 查询所有内管经理账号
     * @return
     */
    @Override
    public List<Map<String, String>> queryAllLmsUser() {
        List<Map<String, String>> lmsUsers = lmsUserMapper.selectAllLmsUser();
        logger.info("所有的内管账号:{}", lmsUsers);
        return lmsUsers;
    }

    @Override
    public LmsUser queryByLogin(String loginName) {
        return lmsUserMapper.selectByLogin(loginName);
    }

    @Override
    public List<String> queryByBusNum(String busNum) {
        return lmsUserMapper.selectByBusNum(busNum);
    }
}
