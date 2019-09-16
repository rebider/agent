<%--
  Created by IntelliJ IDEA.
  User: chenqiutian
  Date: 2019/03/27
  Time: 10:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
</script>
<div class="easyui-panel" data-options="fit:true,border:false">
    <div data-options="region:'west',border:true"  style="width:100%;overflow: hidden; ">
        <table class="grid" data-options="fit:true,border:false">
            <tr>
                <td style="width: 80px;">月份</td>
                <!--本代理商-->
                <td style="width: 200px;">欠款代理商名称</td>
                <td style="width: 200px;">欠款代理商唯一吗</td>
                <td style="width: 200px;">欠款代理商上级名称</td>
                <td style="width: 200px;">欠款代理商上级唯一码</td>
                <!--关联代理商-->
                <td style="width: 200px;">代扣款代理商名称</td>
                <td style="width: 200px;">代扣款代理商唯一码</td>
                <td style="width: 200px;">代扣款代理商上级名称</td>
                <td style="width: 200px;">代扣款代理商上级唯一码</td>
                <td style="width: 100px;">金额</td>
            </tr>
            <c:forEach items="${revList}" var="list">
                <tr>
                    <td>${list.DEDUCTION_DATE}</td>
                    <td>${list.AGENT_NAME}</td>
                    <td>${list.AGENT_ID}</td>
                    <td>${list.PARENT_AGENT_NAME}</td>
                    <td>${list.PARENT_AGENT_ID}</td>
                    <td>${list.AGENTNAMEREV2}</td>
                    <td>${list.AGENTIDREV2}</td>
                    <td>${list.PARENTAGENTNAMEREV2}</td>
                    <td>${list.PARENTAGENTIDREV2}</td>
                    <td>${list.DEDUCTION_AMT}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>



