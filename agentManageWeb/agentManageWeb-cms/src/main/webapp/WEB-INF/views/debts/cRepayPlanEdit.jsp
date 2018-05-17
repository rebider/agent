<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $("#planId").val('${cRepayPlan.planId}');
        $("#contractId").val('${cRepayPlan.contractId}');
        $("#subProductId").val('${cRepayPlan.subProductId}');
        $("#parentProductId").val('${cRepayPlan.parentProductId}');
        $("#custId").val('${cRepayPlan.custId}');
        $("#planType").val('${cRepayPlan.planType}');
        $("#createDate").val('${cRepayPlan.createDate}');
        $("#period").val('${cRepayPlan.period}');
        $("#maxPeriod").val('${cRepayPlan.maxPeriod}');
        $("#repayDate").val('${cRepayPlan.repayDate}');
        $("#gracePeriod").val('${cRepayPlan.gracePeriod}');
        $("#leaseAmt").val('${cRepayPlan.leaseAmt}');
        $("#dpd").val('${cRepayPlan.dpd}');
        $("#planStatus").val('${cRepayPlan.planStatus}');
        $("#settledDate").val('${cRepayPlan.settledDate}');
        $("#returnAmt").val('${cRepayPlan.returnAmt}');
        $("#amt").val('${cRepayPlan.amt}');
    });
</script>
<div style="padding: 3px;">
    <form id="RepayPlanEditForm" method="post">
        <table class="grid">
            	<tr>
                <td style="width:240px;">还款计划编号</td>
               <td><input name="planId" type="text" value="${cRepayPlan.planId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
                </tr>
                
               <tr> <td>合同编号</td>
              
               <td><input name="contractId" type="text" value="${cRepayPlan.contractId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
                </tr>
                <tr><td>产品编号</td>
                
                <td><input name="subProductId" type="text" value="${cRepayPlan.subProductId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
                </tr>
                <tr><td>父产品编号</td>
                
                <td><input name="parentProductId" type="text" value="${cRepayPlan.parentProductId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
                </tr>
               <tr> <td>客户唯一编号</td>
               
                <td><input name="custId" type="text" value="${cRepayPlan.custId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
                </tr>
              <tr>  
              <td>还款计划类型</td>
                <td> <select class="easyui-combobox"  id="planType" name="planType" style="width:155px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="K">账分</option>
				<option value="H">账汇</option>
				<option value="R">无还款计划</option>
				<option value="Z">借款时生成</option>
				<option value="Y">账单日生成</option>
				</select></td></tr>
				
               <tr> <td>创建日期</td>
               
               <td><input name="createDate" type="text" value="${cRepayPlan.createDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
                </tr>
               <tr> <td>当前期数</td>
               
                <td><input name="period" type="text" value="${cRepayPlan.period}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
                </tr>
               <tr> <td>总期数</td>
               
               <td><input name="maxPeriod" type="text" value="${cRepayPlan.maxPeriod}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
                </tr>
               <td>还款日</td>
               
                <td><input name="repayDate" type="text" value="${cRepayPlan.repayDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:240px;"></td>
                </tr>
                <tr><td>宽限期</td>
                
                <td><input name="gracePeriod" type="text" value="${cRepayPlan.gracePeriod}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:240px;"></td>
                </tr>
               <tr> <td>最低还款额</td>
               
                <td><input name="leaseAmt" type="text" value="${cRepayPlan.leaseAmt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
                </tr>
               <tr> <td>逾期天数</td>
               
                <td><input name="dpd" type="text" value="${cRepayPlan.dpd}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
                </tr>
                
                
                <tr>
                
                <td>计划状态</td>
                <td><select class="easyui-combobox"  id="planStatus" name="planStatus" style="width:155px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="S">有效</option>
				<option value="D">暂停</option>
				<option value="C">超时</option>
				<option value="A">申请</option>
				<option value="L">无效</option>
				<option value="H">挂起</option>
				</select></td></td>
                </tr>
                
                
                
                <tr><td>还清时间</td>
                
                <td><input name="settledDate" type="text" value="${cRepayPlan.settledDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
                </tr>
               <tr> <td>已还总额</td>
               
                <td><input name="returnAmt" type="text" value="${cRepayPlan.returnAmt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
                </tr>
                <tr><td>贷款本金</td>
                
                <td><input name="amt" type="text" value="${cRepayPlan.amt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
                </tr>
        </table>
    </form>
</div>
