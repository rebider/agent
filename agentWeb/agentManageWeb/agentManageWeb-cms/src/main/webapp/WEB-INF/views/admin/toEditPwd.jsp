<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>瑞银信代理商综合管理平台</title>
    <%@ include file="/commons/basejs.jsp" %>
    <link rel="stylesheet" type="text/css" href="${staticPath }/static/style/css/login2.css" />
</head>
<body>
<div>
    <div class="login_bg">
        <div class="sys_title">瑞银信代理商综合管理平台</div>
    </div>
    <form method="post" id="loginform">
        <div style="margin:0 auto;">
            <div class="login_main">
                <div style="display:inline-block;vertical-align:middle;">
                    <img src="${staticPath }/static/style/images/ryhc-logo.png" />
                </div>
                <div style="display:inline-block;vertical-align:middle;">
                    <div class="login_line" style="color: #343434;font-size: 24px;text-align: center" id="titleFont">修改密码</div>
                    <div class="login_line">
                        <span class="login_text">用户名：<span style="margin-left:15px;"></span></span>
                        <input class="login_input" type="text" value="<shiro:principal></shiro:principal>" disabled/>
                    </div>
                    <div class="login_line">
                        <span class="login_text">密<span style="margin-left:16px;"></span>码：<span style="margin-left:15px;"></span></span>
                        <input class="login_input" id="oldPwd" type="password" name="oldPwd" />
                        <span id="oldPwdTip" style="color: red"></span>
                    </div>
                    <div class="login_line">
                        <span class="login_text"><span></span>新密码：<span style="margin-left:15px;"></span></span>
                        <input class="login_input" id="pwd" type="password" name="pwd" />
                        <span id="pwdTip" style="color: red"></span>
                    </div>
                    <div class="login_line">
                        <span class="login_text">重复密码：</span>
                        <input class="login_input" id="rePwd" type="password" name="rePwd" />
                    </div>
                    <div class="login_line" style="color: red;font-size: 14px" id="tipFont"></div>
                    <div class="login_line">
                        <a class="login_btn" href="javascript:;" onclick="qzEditPwd()">修改密码</a>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">
    $(function () {
        //密码失去焦点事件
        $("#pwd").blur(function () {
            pwdTip();
        });
        //重复密码失去焦点事件
        $("#rePwd").blur(function () {
            rePwdTip();
        });
    });
    //密码校验
    function pwdTip() {
        var pwdRegex = /^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,16}$/;
        var oldPwd = $("#oldPwd").val();
        var pwd =  $("#pwd").val();

        if (null == oldPwd || "" === oldPwd) {
            $("#tipFont").html("请输入密码！");
            return false;
        }
        if(oldPwd===pwd){
            $("#tipFont").html("新密码不能与旧密码相同！");
            return false;
        }
        if(pwd.length<8){
            $("#tipFont").html("密码长度应大于8位！");
            return false;
        }
        if(pwd.length>16){
            $("#tipFont").html("密码长度应小于16位！");
            return false;
        }
        if (!pwdRegex.test(pwd)) {
            $("#tipFont").html("密码应为8-16位，数字、字母、特殊符号组成！");
            return false;
        }
        $("#tipFont").html("");
        return true;
    }
    //重新输入的密码校验
    function rePwdTip() {
        var pwd =  $("#pwd").val();
        var rePwd =  $("#rePwd").val();
        if(!(pwd===rePwd)){
            $("#tipFont").html("两次输入的密码不一致！");
            return false;
        }
        $("#tipFont").html("");
        return true;
    }
    //修改密码按钮
    function qzEditPwd() {
        //验证密码（数字，字母，特殊字符）
        var oldPwd = $("#oldPwd").val();
        var pwd =  $("#pwd").val();
        if (!(pwdTip() && rePwdTip())) {
            return false;
        }
        //加密传输
        oldPwd = "" + getPass(4) + btoa(oldPwd);
        pwd = "" + getPass(4) + btoa(pwd);
        $.ajaxL({
            type: "POST",
            url: "/user/editUserPwd",
            dataType: 'json',
            data:{
                oldPwd:oldPwd,
                pwd:pwd
            },
            beforeSend: function () {
                progressLoad();
            },
            success: function (result) {
                info(result.msg);
                if (result.success) {
                    window.location.href="/index";
                }
            },
            complete: function (XMLHttpRequest, textStatus) {
                progressClose();
            }
        });
    }

    //生成指定长度的随机大写字母
    function getPass(len) {
        var tmpCh = "";
        for (var i = 0; i < len; i++) {
            tmpCh += String.fromCharCode(Math.floor(Math.random() * 26) + "a".charCodeAt(0));
        }
        return tmpCh.toUpperCase();
    }
</script>
</body>
</html>
