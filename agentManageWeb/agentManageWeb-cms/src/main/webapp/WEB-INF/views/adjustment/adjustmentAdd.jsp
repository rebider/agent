<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#repaymentAddEditForm').form({
            url : '${path}/adjustment/edit',
            onSubmit : function() {
                var isValid = $(this).form('validate');
                if (!isValid) {
                }
                return isValid;
            },
            success : function(result) {
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close'); 
                 	parent.$.messager.alert('提示', result.msg, 'info');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="repaymentAddEditForm" method="post">
            <table class="grid">
                <!-- =========添加========= -->
                <tr>
                    <td>合同编号</td>
                     <td>
                     <input type="hidden" name="adjustmentState" value="S"> 
                     <input id="contractId" name="contractId" type="text" placeholder="请输入合同编号" class="easyui-validatebox" data-options="required:true"><span id="tip"></span></td>
                </tr>
               <tr>
                    <td>资金流向</td>
                     <td>   
		                <select class="easyui-combobox" id="moneyFlow" name="moneyFlow" data-options="required:true">  
	                	<option></option> 
		                <option value="D">借记增加</option>
		                <option value="C">贷记减少</option>      
		             	</select>   
		            </td> 
                </tr>
               <tr>
                    <td>调整交易时间</td>
                    <td><input class="easyui-datetimebox" id="adjustmentTime" name="adjustmentTime" data-options="required:true" style="width:150px"></td>
                </tr>
                <tr>
                    <td>调整金额</td>
                    <td><input id="amount" name="amount" type="text" placeholder="请输入调账金额"  class="easyui-validatebox" data-options="required:true"><span id="tip"></span></td>
                </tr>
                <tr>
                    <td>交易类型</td>
                     <td>   
		                <select class="easyui-combobox" id="transCode" name="transCode" data-options="required:true">   
	                	<option></option> 
<!-- 						<option value="A002">放款申请</option> -->
						<option value="A003">还款</option>
						<option value="A011">提前还款</option>
						<option value="C010">利息计提</option>  
		             	</select>  
		            </td> 
                </tr>
                <tr>
                    <td>备注</td>
                    <td>
                    <textarea name="remarks" rows="4" cols="20" placeholder="请输入备注"></textarea>
                    <span id="tip"></span></td>
                </tr> 
            </table>
        </form>
    </div>
</div>