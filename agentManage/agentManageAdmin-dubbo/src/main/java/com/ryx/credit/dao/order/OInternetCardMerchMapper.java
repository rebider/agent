package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.OInternetCardMerch;
import com.ryx.credit.pojo.admin.order.OInternetCardMerchExample;

import java.util.List;

public interface OInternetCardMerchMapper {

    long countByExample(OInternetCardMerchExample example);

    int deleteByExample(OInternetCardMerchExample example);

    int insert(OInternetCardMerch record);

    int insertSelective(OInternetCardMerch record);

    List<OInternetCardMerch> selectByExample(OInternetCardMerchExample example);

    OInternetCardMerch selectChnTermposi(String chnTermposi);

}