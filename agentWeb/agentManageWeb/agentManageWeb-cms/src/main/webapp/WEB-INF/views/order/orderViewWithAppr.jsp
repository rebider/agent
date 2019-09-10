<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs" id="taskAppTabs">
    <div title="待审信息">
        <jsp:include page="/orderbuild/orderView" ></jsp:include>
    </div>
    <div title="审批记录">
        <%@ include file="/commons/approval_record.jsp" %>
    </div>
    <c:if test="${reviewStatus=='2'}">
        <div title="审批流程图">
            <img src="/agActivity/approvalActImage?busId=${orderId}&busType=${busTypeImg}" />
        </div>
    </c:if>
</div>
<script>

</script>
