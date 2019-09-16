<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" isELIgnored="false" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="agent" uri="/WEB-INF/tld/agent.tld" %>
<%@ taglib prefix="desensitization" uri="/WEB-INF/tld/desensitization.tld" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<c:set var="path" value="${ctxPath}" />
<c:set var="staticPath" value="${ctxPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="easyui-tabs" id="taskAppTabs">
    <div title="待审信息">
            <%@ include file="showDetail.jsp" %>
    </div>
    <div title="审批记录">
        <%@ include file="/commons/approval_record.jsp" %>
    </div>
</div>