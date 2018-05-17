<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#splitRulesAddForm').form({
            url : '${path }/cSplitRules/add',
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
        <form id="splitRulesAddForm" method="post">
            <table class="grid">
				<tr>
                    <td style="width:150px;">拆分类型</td>
	                <td>
	                <select class="easyui-combobox" name="splitType" style="width:150px;" data-options="editable:false">
		                <option></option>
						<option value="Y">逾期</option>
						<option value="O">其他</option>
					</select>
					</td>
				</tr>
            	<tr>
                    <td style="width:150px;">拆分笔数</td>
                    <td >
                        <input name="splitCount" id="splitCount" class="easyui-numberbox" data-options="required:true"/>
                    </td>
                </tr>
            	<tr>
                    <td style="width:150px;">拆分比例</td>
                    <td >
                        <input name="splitArray" id="splitArray" class="easyui-textbox" data-options="required:true,validType:'length[1,100]'"/>
                    </td>
                </tr>
            	<tr>
                    <td style="width:150px;">金额起始</td>
                    <td >
                        <input name="amtStart" id="amtStart" class="easyui-numberbox" data-options="required:true"/>
                    </td>
                </tr>
            	<tr>
                    <td style="width:150px;">金额终止</td>
                    <td >
                        <input name="amtEnd" id="amtEnd" class="easyui-numberbox" data-options="required:true"/>
                    </td>
                </tr>
				<tr>
                    <td style="width:150px;">是否有效</td>
	                <td>
	                <select class="easyui-combobox" name="status" style="width:150px;" data-options="editable:false">
		                <option></option>
						<option value="Y">有效</option>
						<option value="N">无效</option>
					</select>
					</td>
				</tr>
            </table>
        </form>
    </div>
</div>