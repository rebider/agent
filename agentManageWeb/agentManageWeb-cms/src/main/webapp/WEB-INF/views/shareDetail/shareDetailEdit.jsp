<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
     $(function() {
        $('#shareDetailEditForm').form({
            url : '${path }/shareDetail/editShareDetail',
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
        $("#transCode").val('${CshareDetail.transCode}');
    });
</script>
<div style="padding: 3px;">
    <form id="shareDetailEditForm" method="post">
    <input type="hidden" name="shareDetailId" value="${CshareDetail.shareDetailId}">
        <table class="grid">
               	<tr>
	                <td>流水ID</td>
	                <td><input id="flowId" name="flowId" value="${CshareDetail.flowId}" style="width:160px;" class="easyui-validatebox" data-options="required:true"></td>
                    <td>交易类型</td>
                    <td>
                    	<select class="easyui-combobox" name="transCode" id="transCode" data-options="required:true" style="width:160px;">
			                <option></option>
			                <option value="A003">还款</option>
			                <option value="A011">提前还款</option>
						</select>
                    </td>
                </tr>
                <tr>
                    <td>所属代理商</td>
                    <td><input name="agentId" value="${CshareDetail.agentId}" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
                    <td>交易时间</td>
                    <td><input name="transTime" value="${CshareDetail.transTime}" type="text" class="easyui-datebox" data-options="required:true" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>分润处理时间</td>
                    <td><input name="shareTime" value="${CshareDetail.shareTime}" type="text" class="easyui-datebox" data-options="required:true" style="width:160px;"></td>
                    <td>交易金额</td>
                    <td><input name="amount" value="${CshareDetail.amount}" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>返回码</td>
                    <td><input name="retCode" value="${CshareDetail.retCode}" type="text" placeholder="请输入"  style="width:160px;"></td>
                    <td>返回消息</td>
                    <td><input name="retText" value="${CshareDetail.retText}" type="text" placeholder="请输入"  style="width:160px;"></td>
                </tr>
                <tr>
                    <td>交易状态</td>
                    <td>
                    	<select class="easyui-combobox" name="transType" id="transType" data-options="required:true" style="width:160px;">
			                <option value="S">成功</option>
						</select>
                    </td>
                    <td>本金</td>
                    <td><input name="loanAmt" value="${CshareDetail.loanAmt}" type="text" placeholder="请输入"  style="width:160px;"></td>
                </tr>
                <tr>
                    <td>息与费</td>
                    <td><input name="loanAllRate" value="${CshareDetail.loanAllRate}" type="text" placeholder="请输入"  style="width:160px;"></td>
                    <td>利息</td>
                    <td><input name="loanRate" value="${CshareDetail.loanRate}" type="text" placeholder="请输入"  style="width:160px;"></td>
                </tr>
                <tr>
                    <td>罚息</td>
                    <td><input name="loanPenalty" value="${CshareDetail.loanPenalty}" type="text" placeholder="请输入"  style="width:160px;"></td>
                    <td>有效分润金额</td>
                    <td><input name="shareValidAmt" value="${CshareDetail.shareValidAmt}" type="text" placeholder="请输入"  style="width:160px;"></td>
                </tr>
                <tr>
                    <td>分润成本</td>
                    <td><input name="shareCost" value="${CshareDetail.shareCost}" type="text" placeholder="请输入"  style="width:160px;"></td>
                    <td>分润比</td>
                    <td><input name="shareScale"  value="${CshareDetail.shareScale}" type="text" placeholder="请输入"  style="width:160px;"></td>
                </tr>
                <tr>
                    <td>分润金额</td>
                    <td><input name="shareAmt" value="${CshareDetail.shareAmt}" type="text" placeholder="请输入"  style="width:160px;"></td>
                    <td>分润参考金额</td>
                    <td><input name="shareDemoAmt" value="${CshareDetail.shareDemoAmt}" type="text" placeholder="请输入"  style="width:160px;"></td>
                </tr>
        </table>
    </form>
</div>
