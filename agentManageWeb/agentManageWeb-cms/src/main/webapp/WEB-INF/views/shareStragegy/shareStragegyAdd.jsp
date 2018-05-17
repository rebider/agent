<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#shareStragegyAddForm').form({
            url : '${path }/shareStragegy/addShareStragegy',
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
<div class="easyui-layout" data-options="fit:true,border:true" >
    <div data-options="region:'center',border:false" style="padding: 3px;" >
        <form id="shareStragegyAddForm" method="post">
            <table class="grid">
                <!-- <tr>
	                <td>策略id</td>
	                <td><input id="strategyId" name="strategyId" style="width:160px;" class="easyui-validatebox" data-options="required:true"></td>
                </tr> -->
                 <tr>
                    <td>执行金额起</td>
                    <td><input name="strategyAmtSta" type="text" placeholder="请输入" class="easyui-validatebox"  style="width:160px;"></td>
                    <td>执行金额止</td>
                    <td><input name="strategyAmtEnd" type="text" placeholder="请输入" class="easyui-validatebox" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>执行时间起</td>
                    <td><input id="starTime" name="starTime" type="text" class="easyui-timespinner" style="width:160px;"></td>
                    <td>执行时间止</td>
                    <td><input id="endTime" name="endTime" type="text" class="easyui-timespinner" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>策略类型</td>
                    <td>
		                <select class="easyui-combobox" name="strategyType" data-options="required:true" style="width:160px;">
			                <option></option>
			                <option value="A">息与费</option>
						  	<option value="T">利息</option>
						  	<option value="L">罚息</option>
						  	<option value="K">利息罚息</option>
						</select>
				    </td>
                    <td>单笔分润封顶</td>
                    <td><input name="shareCap" type="text" placeholder="请输入" class="easyui-validatebox" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>分润比</td>
                    <td><input name="shareProp" type="text" placeholder="请输入"  style="width:160px;"></td>
                    <td>策略录入时间</td>
                    <td><input name="insertTime" type="text" placeholder="请输入" class="easyui-datebox" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>策略更新时间</td>
                    <td><input name="updateTime" type="text" placeholder="请输入" class="easyui-datebox" style="width:160px;"></td>
                    <td>策略更新人员</td>
                    <td><input name="updateUser" type="text" placeholder="请输入"  style="width:160px;"></td>
                </tr>
                <tr>
                    <td>策略是否启用</td>
                     <td>
		                <select class="easyui-combobox" name="strategyStatus" style="width:160px;" data-options="required:true">
			                <option></option>
			                <option value="N">不启用</option>
			                <option value="Y">启用</option>
						</select>
	                </td>
                </tr>
            </table>
        </form>
    </div>
</div>