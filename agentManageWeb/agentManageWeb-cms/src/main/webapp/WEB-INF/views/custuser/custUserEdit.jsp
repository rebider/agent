<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
    	
    	$('#custEditPid').combotree({
            url : '${path }/cCustUser/tree?flag=false',
            parentField : 'agencyId',
            lines : true,
            panelHeight : 'auto',
            value :'${cCustUser.agencyId}'
        });
         
    	
        $('#cCustUserEditForm').form({
            url : '${path }/cCustUser/edit',
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
                	parent.$.messager.alert('提示', result.msg, 'info');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
        $("#userStatus").val('${cCustUser.userStatus}');
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="cCustUserEditForm" method="post">
            <table class="grid">
            	<tr>
                    <td>用户编号</td>
                    <td><input name="userId" type="text"  value="${cCustUser.userId}" readonly="readonly"></td>
                </tr>
            	<tr>
                    <td>客户编号</td>
                    <td><input name="custId" type="text"  value="${cCustUser.custId}" readonly="readonly"></td>
                </tr>
               <%--  <tr>
                    <td>用户名</td>
                    <td>
                    <input name="userName" type="text"  value="${cCustUser.userName}" >
                    </td>
                </tr>
                <tr>
                    <td>密码</td>
                    <td><input name="password" type="text" value="${cCustUser.password}"></td>
                </tr>
                <tr>
                <tr>
                    <td>输错密码次数</td>
                    <td><input name="loginTimes" type="text" class="easyui-numberbox" value="${cCustUser.loginTimes}"></td>
                </tr> --%>
                <tr>
                    <td>状态</td>
                    <td >
                        <select id="userStatus" name="userStatus" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="Y">正常</option>
                            <option value="N">冻结</option>
                        </select>
                    </td>
                </tr>
                <td>所属代理商</td>
	                <td colspan="3"><select id="custEditPid" name="agencyId" style="width:380px;height: 29px;"></select>
	                <a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#custEditPid').combotree('clear');" >清空</a></td>
            </table>
        </form>
    </div>
</div>