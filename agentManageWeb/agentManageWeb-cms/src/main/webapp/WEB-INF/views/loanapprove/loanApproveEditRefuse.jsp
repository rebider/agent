<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#orderEditRefuseForm').form({
            url : '${path }/cLoanApprove/edit_refuse',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },  
            success : function(result) {
            	progressClose();
            	parent.$.modalDialog.openner_datagrid.datagrid('reload'); 
            	parent.$.modalDialog.handler.dialog('close');
            	
            } 
        });
    });
</script>
<div style="padding: 3px;">
    <form id="orderEditRefuseForm" method="post">
        <table class="grid">
           <input name="orderId" type="hidden"  value="${cOrder.orderId}">
           <tr>
           <td>审批意见</td>
            <td><textarea name="name" cols="30" rows="3"></textarea></td>   
           </tr>
        </table>
    </form>
</div>
