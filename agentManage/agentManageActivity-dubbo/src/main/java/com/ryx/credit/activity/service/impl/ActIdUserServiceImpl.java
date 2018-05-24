package com.ryx.credit.activity.service.impl;

import com.ryx.credit.activity.dao.ActIdUserMapper;
import com.ryx.credit.activity.entity.ActIdUser;
import com.ryx.credit.activity.entity.ActIdUserExample;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.service.ActIdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * ActIdUserServiceImpl
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2018/5/22
 * @Time: 15:05
 * @description: ActIdUserServiceImpl
 * To change this template use File | Settings | File Templates.
 */
@Service("actIdUserService")
public class ActIdUserServiceImpl implements ActIdUserService {
    @Autowired
    private ActIdUserMapper actIdUserMapper;

    @Override
    public int insert(ActIdUser record) {
        return actIdUserMapper.insert(record);
    }

    @Override
    public int insertSelective(ActIdUser record) {
        return actIdUserMapper.insertSelective(record);
    }

    @Override
    public ActIdUser selectByPrimaryKey(Object id) {
        return actIdUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ActIdUser record) {
        return actIdUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ActIdUser record) {
        return actIdUserMapper.updateByPrimaryKey(record);
    }

    @Override
    public HashMap<String, Object> configExample(Page page, ActIdUser actIdUser) {
        if (actIdUser != null && page != null) {
            ActIdUserExample actIdUserExample = new ActIdUserExample();
            ActIdUserExample.Criteria criteria = actIdUserExample.or();
            if (actIdUser.getId() != null) {
                criteria.andIdEqualTo(actIdUser.getId());
            }

            if (actIdUser.getEmail() != null) {
                criteria.andEmailEqualTo(actIdUser.getEmail());
            }
            if (actIdUser.getFirst() != null) {
                criteria.andFirstEqualTo(actIdUser.getFirst());
            }

            if (actIdUser.getLast() != null) {
                criteria.andLastEqualTo(actIdUser.getLast());
            }

            if (actIdUser.getPwd() != null) {
                criteria.andPwdEqualTo(actIdUser.getPwd());
            }

            int count = actIdUserMapper.countByExample(actIdUserExample);
            page.setCount(count);
            actIdUserExample.setPage(page);
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("list", actIdUserMapper.selectByExample(actIdUserExample));
            hashMap.put("page", page);
            return hashMap;
        } else {
            return null;
        }
    }
}
