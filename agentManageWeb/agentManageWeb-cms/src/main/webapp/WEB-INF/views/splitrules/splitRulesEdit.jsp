<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#splitRulesEditForm').form({
            url : '${path }/cSplitRules/edit',
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
                	parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为product.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
       
        $("#splitType").val('${splitRules.splitType}');
        $("#status").val('${splitRules.status}');
    });
</script>
<div style="padding: 3px;">
    <form id="splitRulesEditForm" method="post">
        <table class="grid">
             <tr>
                <td style="width:160px;">规则类型<input name="splitId" type="hidden"  value="${splitRules.splitId}"></td>
                <td>
                <select class="easyui-combobox" id="splitType" name="splitType" style="width:160px;" data-options="editable:false" >
				<option value="Y">逾期</option>
				<option value="O">其他</option>
				</select>
				</td>
            </tr>
            
            <tr>
            <td style="width:160px;">拆分笔数</td>
            <td><input name="splitCount" type="text"  class="easyui-numberbox" style="width:160px;" value="${splitRules.splitCount}"></td>
            </tr>
            
            <tr>
            <td style="width:160px;">拆分比例</td>
            <td><input name="splitArray" type="text"  class="easyui-textbox" style="width:160px;" value="${splitRules.splitArray}"></td>
            </tr>
            
            <tr>
            <td style="width:160px;">金额起始</td>
            <td><input name="amtStart" type="text"  class="easyui-numberbox" style="width:160px;" value="${splitRules.amtStart}"></td>
            </tr>
            
            <tr>
            <td style="width:160px;">金额终止</td>
            <td><input name="amtEnd" type="text"  class="easyui-numberbox" style="width:160px;" value="${splitRules.amtEnd}"></td>
            </tr>
            
		    <tr>
                <td>是否有效</td>
                <td>
                <select class="easyui-combobox" id="status" name="status" style="width:160px;" data-options="editable:false" >
				<option value="Y">有效</option>
				<option value="N">无效</option>
				</select>
				</td>
            </tr>
            
            
        </table>
    </form>
</div>
