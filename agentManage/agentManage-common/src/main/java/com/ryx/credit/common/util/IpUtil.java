package com.ryx.credit.common.util;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {

  public static String getIpAddr(HttpServletRequest request) {

    String ip = request.getHeader("X-Real-IP");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("X-Forwarded-For");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Cdn-Src-Ip");
    }
    String oldIp=ip;
    //由于目前IP获取有问题 需要获取的多个IP截取第一个 删除空格
    if(ip != null){
      if(ip.indexOf(",")>0){
        ip = ip.split(",")[0].replaceAll(" ","");
      }else if(ip.indexOf("，")>0){
        ip = ip.split("，")[0].replaceAll(" ","");
      }
    }
    //logger.info("工具类获取的IP：{},截取之后的IP：{}",oldIp,ip);
    return ip;
  }
}
