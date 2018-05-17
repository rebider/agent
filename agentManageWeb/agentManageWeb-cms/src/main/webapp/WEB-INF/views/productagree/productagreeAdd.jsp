<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
    	$('#productagreeAddPid').combotree({
            url : '${path }/product/tree',//{path }/productrate/tree
            parentField : 'parentProductId',
            lines : true,
            panelHeight : 'auto',
            value : <%=request.getParameter("id")%>
        });
    	
        $('#agreeAddForm').form({
            url : '${path }/productagree/add',
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
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('提示', result.msg, 'warning');
                }
            }
        });
        
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="agreeAddForm" method="post">
            <table class="grid">
                <tr>
                    <td>协议版本</td>
	                <td><input name="version" style="width: 300px;" data-options="required:true"></td>
					</tr><tr>
	                <td>模板地址</td>
	                <td><input name="tempUrl" value="http://" style="width: 300px;" data-options="required:true"></td>
                </tr>
                 <tr>
	                <td>所属产品</td>
	                <td colspan="1"><select id="productagreeAddPid" name="parentProductId" style="width:300px;height: 29px;" data-options="required:true"></select>
	                <a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#productagreeAddPid').combotree('clear');" >清空</a></td>
            	</tr>
            </table>
        </form>
    </div>
</div>