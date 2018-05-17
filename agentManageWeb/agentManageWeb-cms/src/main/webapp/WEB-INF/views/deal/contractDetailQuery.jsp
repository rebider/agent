<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!-- <script type="text/javascript">
    $(function() {
    	 $("#").val('${cContract.custPidType}');
    	 $("#productType").val('${cContract.productType}');
    	 $("#repayType").val('${cContract.repayType}');
    	 $("#payType").val('${cContract.payType}');
    	 $("#billDay").val('${cContract.billDay}');
    	 $("#contractStatus").val('${cContract.contractStatus}');
    	 $("#batchStatus").val('${cContract.batchStatus}');
    });
</script> -->

<div style="padding: 3px;">
    <form id="queryHisTransFlowForm" method="post">
        <table class="grid">
        		<tr>
        		<td>合同号</td>
                <td><input name="contractId" type="text" value="${cContract.contractId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
        		<td>应还总额</td>
                <td><input name="minterestAmt" type="text" value="${cContract.minterestAmt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
                <td>发生金额</td>
                <td><input name="moverdueAmt" type="text" value="${cContract.moverdueAmt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
                </tr>
                
                <tr>
                 <td>贷款本金</td>
                <td><input name="mAmt" type="text" value="${cContract.mAmt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
                 <td>已还本金</td>
                <td><input name="mReturnAmt" type="text" value="${cContract.mReturnAmt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
                <td>应还利息</td>
                <td><input name="minterestRate" type="text" value="${cContract.minterestRate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
                </tr>
                
                <tr>
                 <td>已还利息</td>
                <td><input name="mreturnRate" type="text" value="${cContract.mreturnRate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
                 <td>应还服务费</td>
                <td><input name="minterestServ" type="text" value="${cContract.minterestServ}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
                <td>已还服务费</td>
                <td><input name="mrerturnServ" type="text" value="${cContract.mrerturnServ}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
                </tr>
 
 				<tr>
 				<td>应还罚息</td>
                <td><input name="minterestPenalty" type="text" value="${cContract.minterestPenalty}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
 				 <td>已还罚息</td>
                <td><input name="mrerturnPenalty" type="text" value="${cContract.mrerturnPenalty}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
                <td>应还提前还款手续费</td>
                <td><input name="minterestPound" type="text" value="${cContract.minterestPound}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
 				</tr>
 				
                <tr>
                 <td>已还提前换款手续费</td>
                <td><input name="mrerturnPound" type="text" value="${cContract.mrerturnPound}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
                <td>剩余应还金额</td>
                <td><input name="sreturnAmt" type="text" value="${cContract.sreturnAmt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
                <td>剩余期数</td>
                <td><input name="mperiod" type="text" value="${cContract.mperiod}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
                </tr>
        </table>
    </form>
</div>
