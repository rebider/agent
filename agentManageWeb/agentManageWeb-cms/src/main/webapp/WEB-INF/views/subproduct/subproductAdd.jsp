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
    	
        $('#subAddForm').form({
            url : '${path }/product/add_Sub',
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
        <form id="subAddForm" method="post">
            <table class="grid">
                <tr>
	                <td style="width:150px">子产品名称</td>
	                <td><input name="productName"  type="text" value=""  class="easyui-validatebox" style="width: 150px; height: 21px;" required="required" data-options="required:true"></td>
                </tr>
				<tr>
	                <td>期数</td>
	                <td><input name="period" value=""  class="easyui-numberspinner" style="width: 150px; height: 23px;" required="required" data-options="required:true"></td>
                </tr>
				<tr>
	                <td>每期数值</td>
	                <td><input name="periodUnit" value=""  class="easyui-numberspinner" style="width: 150px; height: 23px;" required="required" data-options="required:true"></td>
                </tr>
                <tr>
                	<td>名义日利率</td>
                <td><input type="text" name="titularRate" style="width:150px;" precision="6" class="easyui-numberbox" required="required" data-options="required:true"></td>
                </tr>
                 <tr>
	                <td>父产品</td>
	                <td colspan="1"><select id="subproductAddPid" name="parentProductId" style="width:150px;height: 23px;" data-options="required:true"></select>
	                <!-- <a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#subproductAddPid').combotree('clear');" >清空</a></td> -->
            	</tr>
            </table>
        </form>
    </div>
</div>