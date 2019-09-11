<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<div class="easyui-panel" title="税点调整申请修改" data-options="iconCls:'fi-results'">
    <form id="posPosTaxEditFrom" method="post">
        <table class="grid">
            <input type="hidden" name="id" value="${taxAdjust.id}">
            <tr>
                <td>代理商唯一码：</td>
                <td>${taxAdjust.agentId}</td>
            </tr>
            <tr>
                <td>代理商名称：</td>
                <td>${taxAdjust.agentName}</td>
            </tr>
            <tr>
                <td>原税点：</td>
                <td>${taxAdjust.taxOld}</td>
            </tr>
            <tr>
                <td>申请税点：</td>
                <td><input name="taxIng" value="${taxAdjust.taxIng}" data-options="required:true"></td>
            </tr>
        </table>
    </form>
</div>