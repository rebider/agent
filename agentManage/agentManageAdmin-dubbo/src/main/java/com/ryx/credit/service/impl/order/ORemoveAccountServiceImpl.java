package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.dao.order.ORemoveAccountMapper;
import com.ryx.credit.service.order.ORemoveAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lrr
 * @Date: 2019/9/20 09:44
 * @Description:销账实现类
 */
@Service("oRemoveAccountService")
public class ORemoveAccountServiceImpl implements ORemoveAccountService {
    @Autowired
    private ORemoveAccountMapper oRemoveAccountMapper;
    @Override
    public PageInfo removeAccountDetail(Map<String, Object> param, PageInfo pageInfo) {
//        Long count = oRemoveAccountMapper.rAccountDetailCount(param);
        List<Map<String, Object>> list = oRemoveAccountMapper.rAccountDetailList(param);
        pageInfo.setTotal(list.size());
        pageInfo.setRows(list);
        return pageInfo;
    }
}