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
    	 
        $("#cStageType").val('${Cstage.cStageType}');
    });
</script>
<div style="padding: 3px;">
    <form id="stageQueryForm" method="post">
    <input type="hidden" name="tempID" value="${Cstage.subProductId}">
        <table class="grid">
             <tr>
	             <td style="width:140px;">父产品编号</td>
	             <td><select id="subproductAddPid" name="parentProductId" data-options="required:true, disabled:true" style="width:160px;" data-options="required:true"></select>
	             </td>
             </tr>
             <tr>
                 <td>子产品编号</td>
                 <td><select id="productEditPid" name="subProductId" data-options="required:true, disabled:true" style="width:160px;" ></select>
	                <!-- <a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#productEditPid').combotree('clear');" >清空</a> -->
	             </td>
             </tr>
              <tr>
                 <td>逾期天数</td>
                 <td><input name="overdueDays" value="${Cstage.overdueDays}" class="easyui-textbox" type="text" class="easyui-validatebox" data-options="required:true, disabled:true" style="width:160px;"></td>
             </tr>
              <tr>
                 <td>五级分类</td>
                 <td>
		             <select class="easyui-combobox" id="cStageType"  name="cStageType" style="width:160px;" data-options="required:true, disabled:true">
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
