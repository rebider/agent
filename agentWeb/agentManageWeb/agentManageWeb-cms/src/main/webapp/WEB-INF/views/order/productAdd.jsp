<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#productAddForm').form({
            url : '${path}/product/productAdd',
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
        <form id="productAddForm" method="post">
            <table class="grid">
                <tr>
                    <td>商品编号：</td>
                    <td><input name="proCode" maxlength="15" type="text" placeholder="请输入" class="easyui-numberbox" data-options="required:true" style="width:160px;"></td>
                    <td>商品名称：</td>
                    <td><input name="proName" maxlength="15" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>机具类型：</td>
                    <td>
                        <select class="easyui-combobox"  name="proType" style="width:160px;" data-options="required:true">
                            <option value=" "></option>
                            <c:forEach items="${modelType}" var="modelTypeItem"  >
                                <option value="${modelTypeItem.dItemvalue}">${modelTypeItem.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>单价：</td>
                    <td><input name="proPrice" maxlength="15" type="text" placeholder="请输入" maxlength="11" precision="2" value="0" class="easyui-numberbox"  data-options="required:true,editable: false" style="width:160px;"></td>

                </tr>
                    <td>状态：</td>
                    <td>
                        <select class="easyui-combobox"  editable="false" name="proStatus" style="width:160px;" data-options="required:true">
                            <option value="1">启用</option>
                            <option value="0">禁用</option>
                        </select>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>