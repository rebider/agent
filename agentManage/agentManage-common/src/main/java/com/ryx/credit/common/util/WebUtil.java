package com.ryx.credit.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * WebUtil
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2018/1/15
 * @Time: 15:48
 * @description: WebUtil
 * To change this template use File | Settings | File Templates.
 */
public class WebUtil {

    /**
     * 获取IP地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown host";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取请求后缀名
     *
     * @param request
     * @return
     */
    public static String getReqSuffix(HttpServletRequest request) {
        return request.getRequestURI().substring(
                request.getRequestURI().lastIndexOf(".") + 1);
    }

    public static void writerJson(HttpServletResponse response, String jsonStr) {
        writer(response, jsonStr);
    }

    public static void writerJson(HttpServletResponse response, Object object) {
        response.setContentType("application/json");
        writer(response, JSONObject.toJSONString(object));
    }

    private static void writer(HttpServletResponse response, String str) {
        try {
            // 设置页面不缓存
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = null;
            out = response.getWriter();
            out.print(str);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getUriExpPath(HttpServletRequest request) {
        Pattern p = Pattern.compile(request.getContextPath());
        Matcher m = p.matcher(request.getRequestURI());
        if (m.find()) {
            return request.getRequestURI().substring(m.end());
        }
        return null;
    }

    /**
     * 获取basePath
     *
     * @param request
     * @return
     */
    public static String getBasePath(HttpServletRequest request) {

        if (request == null) {
            return "unknown host";
        }

        return request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath();

    }


    /**
     * 获取basePath
     *
     * @param request
     * @return
     */
    public static String getBasePathPort(HttpServletRequest request) {

        if (request == null) {
            return "unknown host";
        }

        return request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort();

    }


    public static void forward(String uri, HttpServletRequest request,
                               HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(uri).forward(request, response);
    }

    public static void writerArrayJson(HttpServletResponse response, JSONArray jSONArray) {
        response.setContentType("application/json");
        writer(response, jSONArray.toJSONString());
    }


    public static void main(String[] args) {
        Pattern p = Pattern.compile("/denter");
        Matcher m = p.matcher("/account/pr/index.jspx");
        m.find();
        System.out.println("/account/pr/index.jspx".substring(m.end()));
    }
}
