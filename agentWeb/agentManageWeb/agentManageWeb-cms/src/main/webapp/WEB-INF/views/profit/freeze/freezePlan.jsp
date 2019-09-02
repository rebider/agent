<%--
  Created by IntelliJ IDEA.
  User: renshenghao
  Date: 2019/4/26
  Time: 11:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs" id="taskAppTabs">
    <div title="待审信息">
        <div class="easyui-panel" title="分润信息" data-options="iconCls:'fi-results'">
            <table class="grid">
                <tr>
                    <td>代理商唯一码</td>
                    <td>代理商名称</td>
                    <td>上级代理商唯一码</td>
                    <td>上级代理商名称</td>
                    <td>当前状态</td>
                    <td>分润类型</td>
                    <td>分润金额</td>
                    <td>申请时间</td>
                </tr>
                <c:forEach items="${freezeAgents}" var="freezeAgent">
                    <tr>
                        <td>${freezeAgent.agentId}</td>
                        <td>${freezeAgent.agentName}</td>
                        <td>${freezeAgent.parentAgentId}</td>
                        <td>${freezeAgent.parentAgentName}</td>
                        <td>已冻结</td>
                        <td><span name="freezeType">${freezeAgent.freezeType}</span></td>
                        <td>${freezeAgent.rev1}</td><%--查询时将冻结金额写入该字段--%>
                        <td>${freezeAgent.rev2}</td><%--查询的时候将申请时间写入该字段--%>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="easyui-panel" title="申请信息" data-options="iconCls:'fi-results'">
            <table class="grid">
                <tr>
                    <td>申请人</td>
                    <td>归属省区</td>
                    <td>申请时间</td>
                    <td>申请类型</td>
                    <td>原因</td>
                </tr>
                <tr>
                    <td>${thawOperator.user}</td>
                    <td>${thawOperator.city}</td>
                    <td>${thawOperator.time}</td>
                    <td><%--${thawOperator.type}--%>申请解冻</td>
                    <td>${thawOperator.reason}</td>
                </tr>
            </table>
        </div>
    </div>
    <div title="审批记录">
        <%@ include file="/commons/approval_record.jsp" %>
    </div>
    <div title="审批流程图">
        <img src="/agActivity/approvalActImage?busId=${busId}&busType=thawAgentByCity" />
    </div>
</div>