<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <div>代码：1000</div>
        <div>描述：
            <c:if test="${error!=''}">
                ${error}
            </c:if>
            <c:if test="${error!=''}">
                暂无数据
            </c:if>
        </div>
    </body>
</html>