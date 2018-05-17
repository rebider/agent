<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
     $(function() {});
</script>
<div style="padding: 3px;">
    <form id="shareSummaryQueryForm" method="post">
    <input type="hidden" name="tempID" value="${CshareSummary.shareSummaryId}">
        <table class="grid">
               	<tr>
	                <td>分润汇总ID</td>
	                <td><input id="shareSummaryId" name="shareSummaryId" value="${CshareSummary.shareSummaryId}" style="width:160px;" class="easyui-textbox" data-options="required:true, disabled:true" ></td>
                    <td>所属代理商</td>
                    <td><input name="agentId" value="${CshareSummary.agentId}" type="text" class="easyui-textbox" data-options="required:true, disabled:true" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>交易所属日期</td>
                    <td><input name="transTime" value="${CshareSummary.transTime}" type="text" class="easyui-datebox" data-options="required:true, disabled:true" style="width:160px;"></td>
                    <td>汇总日期</td>
                    <td><input name="summaryTime" value="${CshareSummary.summaryTime}" type="text" class="easyui-datebox" class="easyui-validatebox" data-options="required:true, disabled:true" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>交易总额</td>
                    <td><input name="summaryAmt" value="${CshareSummary.summaryAmt}" type="text" class="easyui-textbox" data-options="required:true, disabled:true" style="width:160px;"></td>
                    <td>本金总额</td>
                    <td><input name="summaryLoanAmt" value="${CshareSummary.summaryLoanAmt}" type="text" class="easyui-textbox" data-options="required:true, disabled:true" style="width:160px;"></td>
                </tr> 
                <tr>
                    <td>息与费总额</td>
                    <td><input name="summaryAllRate" value="${CshareSummary.summaryAllRate}" type="text" class="easyui-textbox" data-options="required:true, disabled:true" style="width:160px;"></td>
                    <td>利息总额</td>
                    <td><input name="summaryRate" value="${CshareSummary.summaryRate}" type="text" class="easyui-textbox" data-options="required:true, disabled:true" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>罚息总额</td>
                    <td><input name="summaryPenalty" value="${CshareSummary.summaryPenalty}" type="text" class="easyui-textbox" data-options="required:true, disabled:true" style="width:160px;"></td>
                    <td>有效分润总额</td>
                    <td><input name="summaryValidAmt" value="${CshareSummary.summaryValidAmt}" type="text" class="easyui-textbox" data-options="required:true, disabled:true" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>分润金额</td>
                    <td><input name="summaryShareAmt" value="${CshareSummary.summaryShareAmt}" type="text" class="easyui-textbox" data-options="required:true, disabled:true" style="width:160px;"></td>
                    <td>分润参考总额</td>
                    <td><input name="summaryDemoAmt" value="${CshareSummary.summaryDemoAmt}" type="text" class="easyui-textbox" data-options="required:true, disabled:true" style="width:160px;"></td>
                </tr>
        </table>
    </form>
</div>
