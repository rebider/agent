<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
     $(function() {
    	 $('#LinkGrouptId').combotree({
             url : '${path }/product/groupTree',
             parentField : 'parentProductGroup',
             lines : true,
             panelHeight : 'auto',
             value :'${link.parentProductGroup}'
         });
     	
     	$('#LinkProductId').combotree({
             url : '${path }/product/tree',
             parentField : 'parentProductId',
             lines : true,
             panelHeight : 'auto',
             value :'${link.parentProductId}'
         });
     	
        $('#productLinkEditForm').form({
            url : '${path }/product/edit_Link',
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
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.messager.alert('提示', result.msg, 'info');
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });
</script>
<div style="padding: 3px;">
    <form id="productLinkEditForm" method="post">
        <table class="grid">
                <tr>
	                <td>产品组</td>
	                <td colspan="1"><input name="id" type="hidden"  value="${link.id}"><select id="LinkGrouptId" name="parentProductGroup" style="width:300px;height: 29px;" data-options="required:true, disabled:true"></select>
	                </td>
            	</tr>
            	<tr>
	                <td>产品</td>
	                <td colspan="1"><select id="LinkProductId" name="parentProductId" style="width:300px;height: 29px;" data-options="required:true, disabled:true"></select>
	                </td>
            	</tr>
        </table>
    </form>
</div>
