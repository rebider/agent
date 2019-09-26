<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs" id="taskAppTabs">
    <%--<shiro:hasPermission name="/agActivity/approvalRecordSee">--%>
    <div title="审批记录">
        <%@ include file="/commons/approval_record.jsp" %>
    </div>
    <%--</shiro:hasPermission>--%>

</div>
