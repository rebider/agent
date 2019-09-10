<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>

<div class="easyui-tabs">
    <div title="查看信息">
        <%@ include file="/commons/queryTerminalTransfer_model.jsp" %>
        <div class="easyui-panel" title="申请信息" data-options="iconCls:'fi-results'">
            <table class="grid">
                <tbody>
                <tr>
                    <td width="80px">附件</td>
                    <td>
                        <c:if test="${!empty terminalTransfer.attachments}">
                            <c:forEach items="${terminalTransfer.attachments}" var="attachment">
                                     <span>
                                            <a>${attachment.attName}</a>
                                            <a href="<%=imgPath%>${attachment.attDbpath}" target="_blank" >查看</a>&nbsp;&nbsp;&nbsp;&nbsp;
                                     </span>
                            </c:forEach>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td width="80px">申请备注</td>
                    <td>${terminalTransfer.remark}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <shiro:hasPermission name="/agActivity/approvalRecordSee">
        <div title="审批记录">
            <%@ include file="/commons/approval_record.jsp" %>
        </div>
    </shiro:hasPermission>
    <c:if test="${reviewStatus=='2'}">
        <div title="审批流程图">
            <img src="/agActivity/approvalActImage?busId=${busIdImg}&busType=${busTypeImg}" />
        </div>
    </c:if>
</div>