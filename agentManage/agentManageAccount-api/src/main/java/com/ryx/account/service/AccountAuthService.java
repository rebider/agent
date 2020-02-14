package com.ryx.account.service;

import com.ryx.credit.common.exception.MessageException;

import java.util.List;
import java.util.Map;

/**
 * Created by RYX on 2020/2/10.
 */
public interface AccountAuthService {

    Map<String,Object> getAuthCode(String platformType, String serverIp)throws MessageException;

    Map<String,Object> refreshAuthCode(String authCode,String platformType,String serverIp)throws MessageException;

    void approveAuthCode(String platformType,String authCode)throws MessageException;

    Map<String,Object>  getTokenCode(String platformType,String authCode,String serverIp,String loginName,String passWord,List<Map<String,Object>> busInfos)throws MessageException;

    Map<String,Object> refreshTokenCode(String tokenCode,String platformType,String serverIp)throws MessageException;

    Map<String,Object> approveTokenCode(String platformType,String tokenCode)throws MessageException;

}
