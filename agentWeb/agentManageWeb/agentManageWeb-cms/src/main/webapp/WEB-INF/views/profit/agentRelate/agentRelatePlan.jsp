<%--
  Created by IntelliJ IDEA.
  User: renshenghao
  Date: 2019/02/20
  Time: 20:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs" id="taskAppTabs">
    <div title="待审信息">
        <%@ include file="agentRelateInfo.jsp" %>
    </div>
    <div title="审批记录">
        <%@ include file="/commons/approval_record.jsp" %>
    </div>
    <c:if test="${relateStatus=='0'}">
    <div title="审批流程图">
        <img src="/agActivity/approvalActImage?busId=${busId}&busType=agentRelate" />
    </div>
    </c:if>
</div>