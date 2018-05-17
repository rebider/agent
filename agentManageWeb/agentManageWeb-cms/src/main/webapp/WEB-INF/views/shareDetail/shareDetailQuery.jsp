<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
     $(function() {
        $("#transCode").val('${CshareDetail.transCode}');
        $("#transType").val('${CshareDetail.transType}');
    });
</script>
<div style="padding: 3px;">
    <form id="shareDetailQueryForm" method="post">
    <input type="hidden" name="shareDetailId" value="${CshareDetail.shareDetailId}">
        <table class="grid">
        		<tr>
	                <td>分润明细ID</td>
	                <td><input id="shareDetailId" name="shareDetailId" value="${CshareDetail.shareDetailId}" style="width:160px;" class="easyui-textbox" data-options="required:true, disabled:true" ></td>
	                <td>流水ID</td>
	                <td><input id="flowId" name="flowId" value="${CshareDetail.flowId}" style="width:160px;" class="easyui-textbox" data-options="required:true, disabled:true" ></td>
                </tr>
                 <tr>
                    <td>交易类型</td>
                    <td>
                    	<select class="easyui-combobox" name="transCode" id="transCode" data-options="required:true, disabled:true"  style="width:160px;">
			                <option></option>
			                <option value="A003">还款</option>
			                <option value="A011">提前还款</option>
						</select>
                    </td>
                    <td>所属代理商</td>
                    <td><input name="agentId" value="${CshareDetail.agentId}" type="text" class="easyui-textbox" data-options="required:true, disabled:true" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>交易时间</td>
                    <td><input name="transTime" value="${CshareDetail.transTime}" type="text" class="easyui-datebox" data-options="required:true, disabled:true" style="width:160px;"></td>
                    <td>分润处理时间</td>
                    <td><input name="shareTime" value="${CshareDetail.shareTime}" type="text" class="easyui-datebox" data-options="required:true, disabled:true"  style="width:160px;"></td>
                </tr>
                <tr>
                    <td>交易金额</td>
                    <td><input name="amount" value="${CshareDetail.amount}" type="text" class="easyui-textbox" data-options="required:true, disabled:true"  style="width:160px;"></td>
                    <td>返回码</td>
                    <td><input name="retCode" value="${CshareDetail.retCode}" type="text" data-options="required:true, disabled:true" class="easyui-textbox" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>返回消息</td>
                    <td><input name="retText" value="${CshareDetail.retText}" type="text" data-options="required:true, disabled:true" class="easyui-textbox" style="width:160px;"></td>
                    <td>交易状态</td>
                    <td>
                    	<select class="easyui-combobox" name="transType" id="transType" data-options="required:true, disabled:true"  style="width:160px;">
			                <option value="S">成功</option>
						</select>
                    </td>
                </tr>
                <tr>
                    <td>本金</td>
                    <td><input name="loanAmt" value="${CshareDetail.loanAmt}" type="text" data-options="required:true, disabled:true" class="easyui-textbox" style="width:160px;"></td>
                    <td>息与费</td>
                    <td><input name="loanAllRate" value="${CshareDetail.loanAllRate}" type="text" data-options="required:true, disabled:true" class="easyui-textbox" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>利息</td>
                    <td><input name="loanRate" value="${CshareDetail.loanRate}" type="text" data-options="required:true, disabled:true" class="easyui-textbox" style="width:160px;"></td>
                    <td>罚息</td>
                    <td><input name="loanPenalty" value="${CshareDetail.loanPenalty}" type="text" data-options="required:true, disabled:true" class="easyui-textbox" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>有效分润金额</td>
                    <td><input name="shareValidAmt" value="${CshareDetail.shareValidAmt}" type="text" data-options="required:true, disabled:true" class="easyui-textbox" style="width:160px;"></td>
                    <td>分润成本</td>
                    <td><input name="shareCost" value="${CshareDetail.shareCost}" type="text" data-options="required:true, disabled:true" class="easyui-textbox" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>分润比</td>
                    <td><input name="shareScale"  value="${CshareDetail.shareScale}" type="text" data-options="required:true, disabled:true" class="easyui-textbox" style="width:160px;"></td>
                    <td>分润金额</td>
                    <td><input name="shareAmt" value="${CshareDetail.shareAmt}" type="text" data-options="required:true, disabled:true" class="easyui-textbox" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>分润参考金额</td>
                    <td><input name="shareDemoAmt" value="${CshareDetail.shareDemoAmt}" type="text" data-options="required:true, disabled:true" class="easyui-textbox" style="width:160px;"></td>
                </tr>
        </table>
    </form>
</div>
