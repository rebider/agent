<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script>
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <table class="grid" >
            <tr>
                <th align="center" >序号</th>
                <th align="center" >扣款代理商</th>
                <th align="center" >代理商名称</th>
                <th align="center" >分润扣款月</th>
                <th align="center" >扣款明细流水</th>
                <th align="center" >应扣金额(元)</th>
                <th align="center" >实扣金额(元)</th>
                <th align="center" >未扣足金额(元)</th>
                <th align="center" >扣款时间</th>
                <th align="center" >备注</th>
            </tr>
            <c:forEach items="${deductList}" var="data" varStatus="dataNumber">
                <tr>
                    <td>${dataNumber.index + 1}</td>
                    <td>${data.agentId}</td>
                    <td>${data.agentName}</td>
                    <td>${data.deductionDate}</td>
                    <td>${data.deductionId}</td>
                    <td>${data.mustDeductionAmt}</td>
                    <td>${data.deductionAmt}</td>
                    <td>${data.notDeductionAmt}</td>
                    <td>${data.createDateTime}</td>
                    <td>${data.remark}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>


