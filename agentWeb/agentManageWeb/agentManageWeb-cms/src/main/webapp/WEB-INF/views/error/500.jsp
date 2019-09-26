<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>500-系统内部错误</title>
    </head>
    <body>
        <div>错误代码：500</div>
        <div>错误描述：
            <c:if test="${error!=''}">
                ${error}
            </c:if>
            <c:if test="${error==''}">
                系统内部错误
            </c:if>
        </div>
    </body>
</html>