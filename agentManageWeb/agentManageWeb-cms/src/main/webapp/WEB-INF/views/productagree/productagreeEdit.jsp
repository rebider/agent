<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {

    	$('#productagreeEditPid').combotree({
            url : '${path }/productrate/tree?flag=false',
            parentField : 'parentProductId',
            lines : true,
            panelHeight : 'auto',
            value :'${agree.parentProductId}'
        });
        
        $('#agreeEditForm').form({
            url : '${path }/productagree/edit',
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
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="agreeEditForm" method="post">
           <table class="grid">
                <tr>
                     <td>协议版本</td>
	                <td><input name="agreementId" type="hidden"  value="${agree.agreementId}"><input name="version" value="${agree.version}" style="width: 300px;" data-options="required:true"></td>
					</tr><tr>
	                <td>模板地址</td>
	                <td><input name="tempUrl" value="${agree.tempUrl}" style="width: 300px;" data-options="required:true"></td>
                </tr>
                 <tr>
	                <td>所属产品</td>
	                <td colspan="3"><select id="productagreeEditPid" name="parentProductId" style="width:300px;height: 29px;" data-options="required:true"></select>
	                <a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#productagreeEditPid').combotree('clear');" >清空</a></td>
            	</tr>
            </table>
        </form>
    </div>
</div>