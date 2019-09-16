<%--
  Created by IntelliJ IDEA.
  User: renshenghao
  Date: 2019/02/19
  Time: 13:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
</script>
<style type="text/css">
    #supply_div td{
        height: 16px;
        font-size: 14px;
        border-left:1px solid lightgrey;
        border-top:1px solid lightgrey;
    }
    .main_agent td span{
        color: red;
    }
</style>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="supply_div" data-options="region:'center',border:false" style="overflow: scroll;font-size: 14px;">
        <c:forEach items="${ChargebackAgentList}" var="list">
            <table width="380px" border="0" cellspacing="0" cellpadding="0" style="margin:15px auto;border-right:1px solid lightgrey;border-bottom:1px solid lightgrey">
                <tr><td colspan="3">${list.title}</td></tr>
                <tr>
                    <td>代理商编码：</td>
                    <td width="200px">代理商名称：</td>
                    <td>补款金额</td>
                </tr>
                <c:forEach items="${list.ChargebackAgents}" var="ChargebackAgent">
                    <c:if test="${ChargebackAgent.mainAgent!=null}">
                        <tr class="main_agent">
                    </c:if>
                    <c:if test="${ChargebackAgent.mainAgent==null}">
                        <tr>
                    </c:if>
                    <td><span>${ChargebackAgent.BEAR_AGENT_ID}</span></td>
                    <td><span>${ChargebackAgent.AGENT_NAME}</span></td>
                    <td><span>${ChargebackAgent.SUPPLY_AMT}</span></td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="2">补款合计：</td>
                    <td>${list.total}</td>
                </tr>
            </table>
            <hr>
        </c:forEach>
    </div>
</div>