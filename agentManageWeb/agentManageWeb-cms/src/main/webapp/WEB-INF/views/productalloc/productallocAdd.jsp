<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
    	$('#productallocAddPid').combotree({
            url : '${path }/product/tree',
            parentField : 'parentProductId',
            lines : true,
            panelHeight : 'auto',
            value : <%=request.getParameter("id")%>
        });
    	
        $('#allocAddForm').form({
            url : '${path }/productalloc/add',
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
        <form id="allocAddForm" method="post">
            <table class="grid">
                <tr>
                    <td style="width:140px;">分配类型</td>
	                <td>
	                <select class="easyui-combobox" name="feeType" style="width:150px;" data-options="required:true">
		                <option></option>
						<option value="O">上期未还清</option>
						<option value="Y">本金</option>
						<option value="V">利息</option>
						<option value="B">砍头息</option>
						<option value="E">服务费</option>
						<option value="P">逾期罚息</option>
						<option value="W">违约金</option>
						<option value="G">溢缴款</option>
						<option value="S">单边账</option>
						<option value="X">提前还款手续费</option>
						<option value="Z">逾期管理费</option>
					</select>
					</td>
				</tr>
				<tr>
                    <td>排序类型</td>
	                <td>
	                <select class="easyui-combobox" name="allocStatus" style="width:150px;" data-options="required:true">
		                <option></option>
						<option value="S">正常分配</option>
						<option value="Y">逾期分配</option>
						<option value="L">临时分配</option>
					</select>
					</td>
				</tr>
				<tr>
	                <td>排序</td>
	                <td><input name="allocNum" value=""  class="easyui-numberspinner" style="width: 150px; height: 22px;" required="required" data-options="required:true"></td>
                </tr>
                 <tr>
	                <td>所属产品</td>
	                <td colspan="1"><select id="productallocAddPid" name="parentProductId" style="width:150px;height: 22px;" data-options="required:true"></select>
	                <!-- <a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#productallocAddPid').combotree('clear');" >清空</a></td> -->
            	</tr>
            </table>
        </form>
    </div>
</div>