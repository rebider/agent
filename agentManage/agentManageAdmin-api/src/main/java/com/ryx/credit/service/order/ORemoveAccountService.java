package com.ryx.credit.service.order;

import com.ryx.credit.common.util.PageInfo;

import java.util.Map;

/**
 * @Auther: lrr
 * @Date: 2019/9/20 09:44
 * @Description:销账服务类
 */
public interface ORemoveAccountService {
    /**
     * 销账明细
     */
    PageInfo removeAccountDetail(Map<String, Object> param, PageInfo pageInfo);
}