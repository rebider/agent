<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
   	 $("#planType").val('${cRepayPlan.planType}');
   	 $("#planStatus").val('${cRepayPlan.planStatus}');
   	 $("#repayStatus").val('${cRepayPlan.repayStatus}');
   	 $("#interestType").val('${cRepayPlan.interestType}');
    });
</script>
<div style="padding: 3px;">
    <form id="queryRepayForm" method="post">
        <table class="grid">
         <!-- 1tr -->
        		<tr>
                <td colspan="10">还款计划编号</td> 
                <td><input name="planId" type="text" value="${cRepayPlan.planId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>	合同编号  </td>   
                <td><input name="contractId" type="text" value="${cRepayPlan.contractId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>子产品编号</td>
                <td><input name="subProductId" type="text" value="${cRepayPlan.subProductId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>父产品编号</td>
                <td><input name="parentProductId" type="text" value="${cRepayPlan.parentProductId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
                
          <!-- 2tr -->       
              <tr> 
                <td colspan="10">唯一客户号  </td>  
                <td><input name="custId" type="text" value="${cRepayPlan.custId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
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
                <td><input name="loanId" type="text" value="${cRepayPlan.loanId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>创建时间</td>
                <td><input name="createDate" type="text" value="${cRepayPlan.createDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
            <!-- 3tr -->     
                <tr>
                <td colspan="10">当前期数</td>    
                <td><input name="period" type="text" value="${cRepayPlan.period}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>总期数</td>    
                <td><input name="maxPeriod" type="text" value="${cRepayPlan.maxPeriod}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
				<td>账单日</td> 
                <td><input name="statementDate" type="text" value="${cRepayPlan.statementDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>还款日</td>
                <td><input name="repayDate" type="text" value="${cRepayPlan.repayDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;">
                </td>
                </tr>
             <!-- 4tr -->    
                <tr>
                <td colspan="10">宽限日</td>    
                <td><input name="gracePeriod" type="text" value="${cRepayPlan.gracePeriod}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>最低还款额</td>    
                <td><input name="leaseAmt" type="text" value="${cRepayPlan.leaseAmt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
				<td>逾期天数</td> 
                <td><input name="dpd" type="text" value="${cRepayPlan.dpd}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
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
             <!-- 5tr -->     
                <tr>
               <td colspan="10">还款状态</td>
                <td><select class="easyui-combobox"  id="repayStatus" name="repayStatus" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="T">未结清</option>
				<option value="L">提前结清</option>
				<option value="U">已结清</option>
				<option value="G">虚拟结清</option>
				<option value="O">已逾期</option>
                </select></td>
                <td>还清时间 </td>    
                <td><input name="settledDate" type="text" value="${cRepayPlan.settledDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>应还金额</td>
                <td><input name="interestAmt" type="text" value="${cRepayPlan.interestAmt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>已还金额 </td>
                <td><input name="returnAmt" type="text" value="${cRepayPlan.returnAmt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
             <!-- 6tr -->   
                <tr>
                <td colspan="10">贷款本金 </td>   
                <td><input name="amt" type="text" value="${cRepayPlan.amt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>容差 </td>
                <td><input name="tolerance" type="text" value="${cRepayPlan.tolerance}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>剩余本金</td>
                <td><input name="remainAmt" type="text" value="${cRepayPlan.remainAmt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>发生</td>
                <td><input name="overdueAmt" type="text" value="${cRepayPlan.overdueAmt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
             <!-- 7tr -->   
                <tr>
                <td colspan="10">批量状态锁定 </td>   
                <td><input name="batchStatus" type="text" value="${cRepayPlan.batchStatus}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>是否计息</td>
                <td><select class="easyui-combobox"  id="interestType" name="interestType" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="N">否</option>
				<option value="Y">是</option>
                </select></td>
                <td></td>
                </tr>
        </table>
    </form>
</div>
