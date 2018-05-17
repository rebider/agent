<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>

<script type="text/javascript">
    $(function() {
   	 $("#isSettled").val('${cLoan.isSettled}');
   	 $("#isPlan").val('${cLoan.isPlan}');
    });
</script>
<div style="padding: 3px;">
   <form id="queryLoanForm" method="post">
        <table class="grid">
         <!-- 1tr -->
        		<tr>
                <td colspan="10" >借据编号</td> 
                <td><input name="loanId" type="text" value="${cLoan.loanId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>	合同编号  </td>   
                <td><input name="contractId" type="text" value="${cLoan.contractId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
    
                <td>机构号 </td>
                <td><input name="branchId" type="text" value="${cLoan.branchId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>唯一客户号</td>
                <td><input name="custId" type="text" value="${cLoan.custId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
                
          <!-- 2tr -->       
              <tr> 
                <td colspan="10">客户手机号  </td>  
                <td><input name="custMobile" type="text" value="${cLoan.custMobile}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>产品编号</td>       
                <td><input name="subProductId" type="text" value="${cLoan.subProductId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td> 
                <td>父产品编号</td> 
                <td><input name="parentProductId" type="text" value="${cLoan.parentProductId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>借款时间</td>
                <td><input name="loanDate" type="text" value="${cLoan.loanDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
            <!-- 3tr -->     
                <tr>
                <td colspan="10">放款时间</td>    
                <td><input name="payDate" type="text" value="${cLoan.payDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>借款期数 </td>    
                <td><input name="period" type="text" value="${cLoan.period}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
				<td>总应还金额</td> 
                <td><input name="interestAmt" type="text" value="${cLoan.interestAmt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>总已还金额</td>
                <td><input name="returnAmt" type="text" value="${cLoan.returnAmt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;">
                </td>
                </tr>
             <!-- 4tr -->    
                <tr>
                <td colspan="10">发生</td>    
                <td><input name="overdueAmt" type="text" value="${cLoan.overdueAmt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                
                
                <td>是否已结清</td>
                <td><select class="easyui-combobox"  id="isSettled" name="isSettled" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="O">未结清</option>
				<option value="Y">已结清</option>
                </select></td> 
                
                <td>结清时间</td>    
                <td><input name="settledDate" type="text" value="${cLoan.settledDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                
                <td>是否已生成还款计划</td>
                <td><select class="easyui-combobox"  id="isPlan" name="isPlan" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="O">未生成</option>
				<option value="Y">已生成</option>
                </select></td> 
                
                </tr>
             <!-- 5tr -->     
                <tr>
                <td colspan="10">还款计划生成时间 </td>   
                <td><input name="planDate" type="text" value="${cLoan.planDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>还款计划编号 </td>    
                <td><input name="planId" type="text" value="${cLoan.planId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>流水ID </td>
                <td><input name="flowId" type="text" value="${cLoan.flowId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>放款时间 </td>
                <td><input name="payTime" type="text" value="${cLoan.payTime}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
             <!-- 6tr -->   
                <tr>
                <td colspan="10">计划放款时间  </td>   
                <td><input name="planPayTime" type="text" value="${cLoan.planPayTime}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>容差 </td>
                <td><input name="tolerance" type="text" value="${cLoan.tolerance}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>贷款本金</td>
                <td><input name="amt" type="text" value="${cLoan.amt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>剩余本金</td>
                <td><input name="remainAmt" type="text" value="${cLoan.remainAmt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
        </table>
    </form>
</div>
