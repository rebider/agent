package com.ryx.credit.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ryx.credit.dao.CUserMapper;
import com.ryx.credit.pojo.admin.CUser;

@Service
public class TestService {
    @Autowired
    private CUserMapper userMapper;

    @Cacheable(value = "hour", key = "#id")
	public CUser selectById(Serializable id) {
		return userMapper.selectById(id);
	}
}
