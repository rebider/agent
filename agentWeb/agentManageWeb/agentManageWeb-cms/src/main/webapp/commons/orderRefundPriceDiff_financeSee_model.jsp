<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--<c:if test="${oRefundPriceDiff.applyCompType=='2'}">--%>
<div class="easyui-panel" title="查看扣除款项" data-options="iconCls:'fi-results',tools:'#Agentcapital_model_tools'">
    <form id="refundPriceDiff_model_form">
    </form>
</div>
<div id="refundPriceDiff_model_templet">
    <table class="grid">
        <tbody>
        <c:forEach items="${oRefundPriceDiff.deductCapitalList}" var="deductCapital">
            <tr>
                <td>扣款类型：</td>
                <td>
                    <select name="cType" style="width:160px;height:21px" disabled>
                        <c:forEach items="${cType}" var="cType">
                            <option value="${cType.dItemvalue}"  <c:if test="${cType.dItemvalue==deductCapital.cType}">selected="selected"</c:if>>${cType.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>金额：</td>
                <td>
                    <input name="cAmount" type="text"  disabled input-class="easyui-validatebox"  style="width:160px;"  value="${deductCapital.cAmount}" data-options="required:true,validType:['length[1,11]','Money']"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%--</c:if>--%>