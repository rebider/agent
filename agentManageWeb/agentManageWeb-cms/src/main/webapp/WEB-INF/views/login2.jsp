<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title>瑞银信代理商综合管理平台</title>
	<%@ include file="/commons/basejs.jsp" %>
    <link rel="stylesheet" type="text/css" href="${staticPath }/static/style/css/login2.css" />
    <script type="text/javascript" src="${staticPath }/static/login.js" charset="utf-8"></script>
</head>

<body onkeydown="enterlogin();">
	<div>
    	<div class="login_bg">
        	<div class="sys_title">瑞银信代理商综合管理平台</div>
        </div>
        <form method="post" id="loginform">
       	 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
       	 <div style="margin:0 auto;">
        	<div class="login_main">
				<div style="display:inline-block;vertical-align:middle;">
					<img src="${staticPath }/static/style/images/ryhc-logo.png" />
				</div>
				<div style="display:inline-block;vertical-align:middle;">
					<div class="login_title">用户登录</div>
					<div class="login_line">
						<span class="login_text">用户名：</span>
						<input class="login_input" type="text" name="username" placeholder="邮箱/手机号" />
					</div>
					<div class="login_line">
						<span class="login_text">密<span style="margin-left:16px;"></span>码：</span>
						<input class="login_input" id="password" type="password" name="password" />
					</div>
					<div class="login_line">
						<span class="login_text" >验证码：</span>
						<input name="captcha" style="width:105px;" class="login_input" type="text" />
						<img id="captcha" alt="验证码" src="${path }/captcha.jpg" data-src="${path }/captcha.jpg?t=" style="vertical-align:middle;border-radius:4px;width:70px;height:33px;cursor:pointer;">
					</div>
					<div class="login_line">
						<a class="login_btn" href="javascript:;" onclick="submitForm()">登 录</a> 
					</div>
				</div>
            </div>
        </div>
        </form>
    </div>
</body>
</html>
