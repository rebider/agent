<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
    	$('#subproductAddPid').combotree({
            url : '${path }/product/tree',
            parentField : 'parentProductId',
            lines : true,
            panelHeight : 'auto',
            value : <%=request.getParameter("id")%>
        });
    	
    	$('#productrateAddPid').combotree({
            url : '${path }/productrate/tree',
            parentField : 'subProductId',
            lines : true,
            panelHeight : 'auto',
            value : <%=request.getParameter("id")%>
        });
    	
        $('#stageAddForm').form({
            url : '${path }/stage/addStage',
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
        <form id="stageAddForm" method="post">
            <table class="grid">
                <tr>
	                <td style="width:140px;">父产品编号</td>
	                <td><select id="subproductAddPid" name="parentProductId" style="width:160px;" data-options="required:true"></select>
                </tr>
                <tr>
                    <td>子产品编号</td>
                    <td><select id="productrateAddPid" name="subProductId" style="width:160px;" data-options="required:true"></select>
	                </td>
                </tr>
                 <tr>
                    <td>逾期天数</td>
                    <td><input name="overdueDays" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
                </tr>
                 <tr>
                    <td>五级分类</td>
                    <td>
		                <select class="easyui-combobox" name="cStageType" style="width:160px;" data-options="required:true">
			                <option></option>
							<option value="O">正常</option>
							<option value="Y">关注</option>
							<option value="F">次级</option>
							<option value="K">可疑</option>
							<option value="S">损失</option>
						</select>
				    </td>
                </tr> 
            </table>
        </form>
    </div>
</div>