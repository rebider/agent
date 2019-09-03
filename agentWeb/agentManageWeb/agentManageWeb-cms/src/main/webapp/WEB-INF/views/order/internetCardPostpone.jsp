<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#postponeAddForm').form({
            url : '${path}/internet/internetCardPostpone',
            onSubmit : function() {
                var isValid = $(this).form('validate');
                if (!isValid) {
                }
                return isValid;
            },
            success : function(result) {
                result = $.parseJSON(result);
                if (result.status==200) {
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
        <form id="postponeAddForm" method="post">
            <input type="hidden" name="iccid" value="${iccidNum}">
            <table class="grid">
                <tr>
                    <td style="text-align: right;"><label for="postponeTime">延期月份:</label></td>
                    <td>
                        <select class="easyui-combobox" name="postponeTime" style="width:150px;" data-options="editable:false" id="postponeTime">
                            <c:forEach var="v" begin="1" end="12" step="1">
                                <option value="${v}">${v}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>