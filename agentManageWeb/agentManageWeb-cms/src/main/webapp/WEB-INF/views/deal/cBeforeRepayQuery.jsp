<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $("#planId").val('${cBeforeRepay.planId}');
        $("#contractId").val('${cBeforeRepay.contractId}');
        $("#subProductId").val('${cBeforeRepay.subProductId}');
        $("#parentProductId").val('${cBeforeRepay.parentProductId}');
        $("#custId").val('${cBeforeRepay.custId}');
        
        $("#planType").val('${cBeforeRepay.planType}');
        $("#loanId").val('${cBeforeRepay.loanId}');
        $("#createDate").val('${cBeforeRepay.createDate}');
        $("#period").val('${cBeforeRepay.period}');
        $("#maxPeriod").val('${cBeforeRepay.maxPeriod}');
        
        $("#statementDate").val('${cBeforeRepay.statementDate}');
        $("#repayDate").val('${cBeforeRepay.repayDate}');
        $("#gracePeriod").val('${cBeforeRepay.gracePeriod}');
        $("#leaseAmt").val('${cBeforeRepay.leaseAmt}');
        $("#dpd").val('${cBeforeRepay.dpd}');
        
        $("#planStatus").val('${cBeforeRepay.planStatus}');
        $("#repayStatus").val('${cBeforeRepay.repayStatus}');
        $("#settledDate").val('${cBeforeRepay.settledDate}');
        $("#interestAmt").val('${cBeforeRepay.interestAmt}');
        $("#returnAmt").val('${cBeforeRepay.returnAmt}');
        $("#amt").val('${cBeforeRepay.amt}');
        
        $("#tolerance").val('${cBeforeRepay.tolerance}');
        $("#remainAmt").val('${cBeforeRepay.remainAmt}');
        $("#overdueAmt").val('${cBeforeRepay.overdueAmt}');
        $("#batchStatus").val('${cBeforeRepay.batchStatus}');
        $("#interestType").val('${cBeforeRepay.interestType}');
    });
</script>
<div style="padding: 3px;">
    <form id="BeforeRepayPlanEditForm" method="post">
        <table class="grid">
             	<tr>
                <td style="width:120px;">还款计划编号</td>
                <td><input name="planId" type="text" value="${cBeforeRepay.planId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>合同编号</td>
                <td><input name="contractId" type="text" value="${cBeforeRepay.contractId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>产品编号</td>
                <td><input name="subProductId" type="text" value="${cBeforeRepay.subProductId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>父产品编号</td>
                <td><input name="parentProductId" type="text" value="${cBeforeRepay.parentProductId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
                
                
                
                <tr>
                <td>客户唯一编号</td>
                <td><input name="custId" type="text" value="${cBeforeRepay.custId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>还款计划类型</td>
                <td><select class="easyui-combobox"  id="planType" name="planType" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="K">账分</option>
				<option value="H">账汇</option>
				<option value="R">无还款计划</option>
				<option value="Z">借款时生成</option>
				<option value="Y">账单日生成</option>
				</select></td>
                <td>借据编号</td>
                <td><input name="loanId" type="text" value="${cBeforeRepay.loanId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>创建日期</td>
                <td><input name="createDate" type="text" value="${cBeforeRepay.createDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
                
                
                <tr>
                <td>当前期数</td>
                <td><input name="period" type="text" value="${cBeforeRepay.period}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>总期数</td>
                <td><input name="maxPeriod" type="text" value="${cBeforeRepay.maxPeriod}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>账单日</td>
                <td><input name="statementDate" type="text" value="${cBeforeRepay.statementDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>还款日</td>
                <td><input name="repayDate" type="text" value="${cBeforeRepay.repayDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
                
                <tr>
                <td>宽限期</td>
                <td><input name="gracePeriod" type="text" value="${cBeforeRepay.gracePeriod}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>最低还款额</td>
                <td><input name="leaseAmt" type="text" value="${cBeforeRepay.leaseAmt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>逾期天数</td>
                <td><input name="dpd" type="text" value="${cBeforeRepay.dpd}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>计划状态</td>
                <td><select class="easyui-combobox"  id="planStatus" name="planStatus" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="S">有效</option>
				<option value="D">暂停</option>
				<option value="C">超时</option>
				<option value="A">申请</option>
				<option value="L">无效</option>
				<option value="H">挂起</option>
				</select></td>
                </tr>
                
                
                <tr>
                <td>还款状态</td>
                <td><select class="easyui-combobox"  id="repayStatus" name="repayStatus" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="T">未结清</option>
				<option value="L">提前结清</option>
				<option value="U">已结清</option>
				<option value="G">虚拟结清</option>
				</select></td>
                <td>还清时间</td>
                <td><input name="settledDate" type="text" value="${cBeforeRepay.settledDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>应还总额</td>
                <td><input name="interestAmt" type="text" value="${cBeforeRepay.interestAmt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>已还总额</td>
                <td><input name="returnAmt" type="text" value="${cBeforeRepay.returnAmt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
                
                <tr>
                <td>贷款本金</td>
                <td><input name="amt" type="text" value="${cBeforeRepay.amt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>容差</td>
                <td><input name="tolerance" type="text" value="${cBeforeRepay.tolerance}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>剩余本金</td>
                <td><input name="remainAmt" type="text" value="${cBeforeRepay.remainAmt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>发生</td>
                <td><input name="overdueAmt" type="text" value="${cBeforeRepay.overdueAmt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
                
                <tr>
                <td>批量状态锁定</td>
                <td><input name="batchStatus" type="text" value="${cBeforeRepay.batchStatus}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>是否计息</td>
                <td><input name="interestType" type="text" value="${cBeforeRepay.interestType}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
                
        </table>
    </form>
</div>
