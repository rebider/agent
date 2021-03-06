package com.ryx.internet.dao;

import com.ryx.internet.pojo.OInternetCardMerch;
import com.ryx.internet.pojo.OInternetCardMerchExample;

import java.util.List;

public interface OInternetCardMerchMapper {

    long countByExample(OInternetCardMerchExample example);

    int deleteByExample(OInternetCardMerchExample example);

    int insert(OInternetCardMerch record);

    int insertSelective(OInternetCardMerch record);

    List<OInternetCardMerch> selectByExample(OInternetCardMerchExample example);

    OInternetCardMerch selectChnTermposi(String chnTermposi);

}