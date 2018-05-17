<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
    	
    	$('#LinkTerminalId').combotree({
            url : '${path }/terminal/terminalTree',
            parentField : 'terminalGroup',
            lines : true,
            panelHeight : 'auto',
            value : <%=request.getParameter("terminalId")%>
        });
    	
    	$('#LinkProductId').combotree({
            url : '${path }/product/tree',
            parentField : 'parentProductId',
            lines : true,
            panelHeight : 'auto'
        });
    	
        $('#terminalLinkAddForm').form({
            url : '${path }/terminal/add_Link',
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
                    parent.$.messager.alert('提示', result.msg, 'info');
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="terminalLinkAddForm" method="post">
            <table class="grid">
                <tr>
	                <td style="width:140px;">终端ID</td>
	                <td colspan="1"><select id="LinkTerminalId" name="terminalId" style="width:150px;height: 24px;" data-options="required:true"></select>
	                <a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#LinkTerminalId').combotree('clear');" >清空</a></td>
            	</tr>
            	<tr>
	                <td>产品</td>
	                <td colspan="1"><select id="LinkProductId" name="parentProductId" style="width:150px;height: 24px;" data-options="required:true"></select>
	                <a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#LinkProductId').combotree('clear');" >清空</a></td>
            	</tr>
            </table>
        </form>
    </div>
</div>