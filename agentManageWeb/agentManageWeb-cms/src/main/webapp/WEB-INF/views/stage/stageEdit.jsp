<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
     $(function() {
    	 $('#subproductAddPid').combotree({
             url : '${path }/product/tree',
             parentField : 'parentProductId',
             lines : true,
             panelHeight : 'auto',
             value : '${Cstage.parentProductId}'
         });
    	 
    	 $('#productEditPid').combotree({
             url : '${path }/productrate/tree?flag=false',
             parentField : 'subProductId',
             lines : true,
             panelHeight : 'auto',
             value :'${Cstage.subProductId}'
         });
    	 
        $('#stageEditForm').form({
            url : '${path }/stage/editStage',
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
        
        $("#cStageType").val('${Cstage.cStageType}');
    });
</script>
<div style="padding: 3px;">
    <form id="stageEditForm" method="post">
    <input type="hidden" name="tempID" value="${Cstage.subProductId}">
        <table class="grid">
             <tr>
	             <td style="width:140px;">父产品编号</td>
	             <td><select id="subproductAddPid" name="parentProductId" style="width:160px;" data-options="required:true"></select>
	             <!-- <a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#subproductAddPid').combotree('clear');" >清空</a> --></td>
             </tr>
             <tr>
                 <td>子产品编号</td>
                 <td><select id="productEditPid" name="subProductId" style="width:160px;"></select>
	                <!-- <a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#productEditPid').combotree('clear');" >清空</a> -->
	             </td>
             </tr>
              <tr>
                 <td>逾期天数</td>
                 <td><input name="overdueDays" value="${Cstage.overdueDays}"  type="text" class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
             </tr>
              <tr>
                 <td>五级分类</td>
                 <td>
		             <select class="easyui-combobox" id="cStageType"  name="cStageType" style="width:160px;" data-options="required:true">
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
