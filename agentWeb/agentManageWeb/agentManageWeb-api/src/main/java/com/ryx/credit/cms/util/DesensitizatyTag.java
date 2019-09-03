package com.ryx.credit.cms.util;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

/**
 * 自定义脱敏标签
 *
 * @Description lrr
 * @Author RYX
 * @Date 2018/6/13
 **/
public class DesensitizatyTag extends SimpleTagSupport {
    private String jurisdiction;//权限
    private String type;//类型(手机号，身份证号)
    private String value;//值

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
            if (StringUtils.isEmpty(value)) {
                getJspContext().getOut().write("无");
                return;
            }
            switch (type) {
                case "mobile":
                    if (null == jurisdiction || "" == jurisdiction) {
                        //判断是手机号还是座机号
                        if (value.length() == 7 || value.length() == 8 || value.length() == 12) {
                            String mobile = value.substring(value.length() - 4);
                            String flag = "";
                            for (int i = 0; i < value.length() - 4; i++) {
                                flag = "*" + flag;
                            }
                            getJspContext().getOut().write(flag + mobile);
                        } else {
                            String mobile = value.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
                            getJspContext().getOut().write(mobile);
                        }

                    } else if (SecurityUtils.getSubject() != null && SecurityUtils.getSubject().isPermitted(jurisdiction)) {
                        getJspContext().getOut().write(value);
                    } else if (SecurityUtils.getSubject() == null || !SecurityUtils.getSubject().isPermitted(jurisdiction)) {
                        if (value.length() == 7 || value.length() == 8 || value.length() == 12) {
                            String mobile = value.substring(value.length() - 4);
                            String flag = "";
                            for (int i = 0; i < value.length() - 4; i++) {
                                flag = "*" + flag;
                            }
                            getJspContext().getOut().write(flag + mobile);
                        } else {
                            String mobile = value.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
                            getJspContext().getOut().write(mobile);
                        }
                    }
                    break;

                case "card":
                    if (null == jurisdiction || "" == jurisdiction) {
                        String card = value.replaceAll("(.{"+(value.length()<12?3:6)+"})(.*)(.{4})", "$1" + "****" + "$3");
                        getJspContext().getOut().write(card);
                    } else if (SecurityUtils.getSubject() != null && SecurityUtils.getSubject().isPermitted(jurisdiction)) {
                        getJspContext().getOut().write(value);
                    } else if (SecurityUtils.getSubject() == null || !SecurityUtils.getSubject().isPermitted(jurisdiction)) {
                        String card = value.replaceAll("(.{"+(value.length()<12?3:6)+"})(.*)(.{4})", "$1" + "****" + "$3");
                        getJspContext().getOut().write(card);
                    }
                    break;

                case "bankCard":
                    if (null == jurisdiction || "" == jurisdiction) {
                        String card = value.replaceAll("(.{"+(value.length()<12?3:6)+"})(.*)(.{4})", "$1" + "****" + "$3");
                        getJspContext().getOut().write(card);
                    } else if (SecurityUtils.getSubject() != null && SecurityUtils.getSubject().isPermitted(jurisdiction)) {
                        getJspContext().getOut().write(value);
                    } else if (SecurityUtils.getSubject() == null || !SecurityUtils.getSubject().isPermitted(jurisdiction)) {
                        String card = value.replaceAll("(.{"+(value.length()<12?3:6)+"})(.*)(.{4})", "$1" + "****" + "$3");
                        getJspContext().getOut().write(card);
                    }
                    break;

                case "name":
                    if (null == jurisdiction || "" == jurisdiction) {
                        String name = value.replaceAll("([\\u4e00-\\u9fa5]{1})(.*)", "$1" + createAsterisk(value.length() - 1));
                        getJspContext().getOut().write(name);
                    } else if (SecurityUtils.getSubject() != null && SecurityUtils.getSubject().isPermitted(jurisdiction)) {
                        getJspContext().getOut().write(value);
                    } else if (SecurityUtils.getSubject() == null || !SecurityUtils.getSubject().isPermitted(jurisdiction)) {
                        String name = value.replaceAll("([\\u4e00-\\u9fa5]{1})(.*)", "$1" + createAsterisk(value.length() - 1));
                        getJspContext().getOut().write(name);
                    }
                    break;

                case "address":
                    if (null == jurisdiction || "" == jurisdiction) {
                        String address = value.replaceAll("(.{" + (value.length() < 12 ? 3 : 6) + "})(.*)(.{5})", "$1" + "****" + "$3");
                        getJspContext().getOut().write(address);
                    } else if (SecurityUtils.getSubject() != null && SecurityUtils.getSubject().isPermitted(jurisdiction)) {
                        getJspContext().getOut().write(value);
                    } else if (SecurityUtils.getSubject() == null || !SecurityUtils.getSubject().isPermitted(jurisdiction)) {
                        String address = value.replaceAll("(.{" + (value.length() < 12 ? 3 : 6) + "})(.*)(.{5})", "$1" + "****" + "$3");
                        getJspContext().getOut().write(address);
                    }
                    break;

                default:
                    getJspContext().getOut().println(value);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            getJspContext().getOut().write("");
        }


    }

    public static String createAsterisk(int length) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            stringBuffer.append("*");
        }
        return stringBuffer.toString();
    }


}
