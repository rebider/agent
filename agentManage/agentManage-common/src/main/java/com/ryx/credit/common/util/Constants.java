package com.ryx.credit.common.util;

/**
 * Constants
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2017/5/2
 * @Time: 11:46
 * To change this template use File | Settings | File Templates.
 */

public class Constants {
    public static final String SUCCESS = "0000";
    public static final String SUCCESS_MSG = "请求成功";
    public static final String FAIL = "1000";
    public static final String FAIL_MSG = "请求失败，出现服务器端错误。";
    public static final String CODE_SUCCESS = "0000";
    public static final String CODE_FAIL = "1000";
    public static final String CODE_SYSTEM_ERROR = "1001";
    public static final String CODE_PERMIT_ERROR = "1002";
    public static final String CODE_IDENTITY_ERROR = "1003";
    public static final String CODE_NONE_ERROR = "1004";
    public static final String CODE_PARAMS_ERROR = "1005";
    public static final String TOBACCO = "TOBACCO";
    public static final String RYX_EXCEPTION_PAGE_FILE = "error-page.properties";
    public static final String RYX_DEFAULT_ERRORPAGE = "ryx.Default.errorpage";
    public static final String RYX_SQLEXCEPTION_ERRORPAGE = "ryx.SQLException.errorpage";
    public static final String RYX_NESTEDSERVLETEXCEPTION_ERRORPAGE = "ryx.NestedServletException.errorpage";
    public static final String RYX_HTTPSERVERERROREXCEPTION_ERRORPAGE = "ryx.HttpServerErrorException.errorpage";
    public static final String RYX_RESTCLIENTEXCEPTION_ERRORPAGE = "ryx.RestClientException.errorpage";
    
    public static final String MERCH_ERROR = "1006"; //商户不存在
    public static final String LIMIT_ERROR = "1007"; //客户无额度
    
}
