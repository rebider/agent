<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $("#loanId").val('${cLoan.loanId}');
        $("#contractId").val('${cLoan.contractId}');
        $("#custId").val('${cLoan.custId}');
        $("#subProductId").val('${cLoan.subProductId}');
        $("#loanDate").val('${cLoan.loanDate}');
        $("#period").val('${cLoan.period}');
        $("#amt").val('${cLoan.amt}');
        $("#payDate").val('${cLoan.payDate}');
    });
</script>
<div style="padding: 3px;">
    <form id="queryLoanInfoForm" method="post">
        <table class="grid">
        		<tr>
                <td style="width:240px;">借款编号</td>
                <td><input name="loanId" type="text" value="${cLoan.loanId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:240px;"></td>
                </tr><tr>
                <td>合同编号</td>
                <td><input name="contractId" type="text" value="${cLoan.contractId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:240px;"></td>
                </tr>
                
                <tr>
                <td>唯一客户号</td>
                <td><input name="custId" type="text" value="${cLoan.custId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:240px;"></td>
                </tr><tr>
                <td>产品编号</td>
                <td><input name="subProductId" type="text" value="${cLoan.subProductId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:240px;"></td>
                </tr>
                
                <tr>
                <td>借款时间</td>
                <td><input name="loanDate" type="text" value="${cLoan.loanDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:240px;"></td>
                </tr><tr>
                <td>借款期数</td>
                 <td><input name="period" type="text" value="${cLoan.period}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                 </tr>
                 
                <tr>
                <td>贷款本金</td>
                <td><input name="amt" type="text" value="${cLoan.amt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
               </tr>
               	<tr>
                <td>剩余本金</td>
                <td><input name="remainAmt" type="text" value="${cLoan.remainAmt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
                <tr>
                <td>放款时间</td>
                <td><input name="payDate" type="text" value="${cLoan.payDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:240px;"></td>
                </tr>
        </table>
    </form>
</div>
