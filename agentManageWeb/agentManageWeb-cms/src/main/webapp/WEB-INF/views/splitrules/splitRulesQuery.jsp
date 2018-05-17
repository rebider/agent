<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $("#splitType").val('${splitRules.splitType}');
        $("#status").val('${splitRules.status}');
    });
</script>
<div style="padding: 3px;">
    <form id="splitRulesEditForm" method="post">
        <table class="grid">
            <tr>
            <td style="width:160px;">拆分编号</td>
            <td><input name="splitId" type="text"  class="easyui-textbox" data-options="required:true, disabled:true" style="width:160px;" value="${splitRules.splitId}"></td>
            </tr>
             <tr>
                <td>规则类型<input name="splitId" type="hidden"  value="${splitRules.splitId}"></td>
                <td>
                <select class="easyui-combobox" id="splitType" name="splitType" style="width:160px;" data-options="required:true, disabled:true" >
				<option value="Y">逾期</option>
				<option value="O">其他</option>
				</select>
				</td>
            </tr>
            
            <tr>
            <td style="width:160px;">拆分笔数</td>
            <td><input name="splitCount" type="text"  class="easyui-textbox" data-options="required:true, disabled:true" style="width:160px;" value="${splitRules.splitCount}"></td>
            </tr>
            
            <tr>
            <td style="width:160px;">拆分比例</td>
            <td><input name="splitArray" type="text"  class="easyui-textbox" data-options="required:true, disabled:true" style="width:160px;" value="${splitRules.splitArray}"></td>
            </tr>
            
            <tr>
            <td style="width:160px;">金额起始</td>
            <td><input name="amtStart" type="text"  class="easyui-textbox" data-options="required:true, disabled:true" style="width:160px;" value="${splitRules.amtStart}"></td>
            </tr>
            
            <tr>
            <td style="width:160px;">金额终止</td>
            <td><input name="amtEnd" type="text"  class="easyui-textbox" data-options="required:true, disabled:true" style="width:160px;" value="${splitRules.amtEnd}"></td>
            </tr>
            <tr>
            <td style="width:160px;">创建时间</td>
            <td><input name="strForCreateTime" type="text"  class="easyui-textbox" data-options="required:true, disabled:true" style="width:160px;" value="${splitRules.strForCreateTime}"></td>
            </tr>
		    <tr>
                <td>是否有效</td>
                <td>
                <select class="easyui-combobox" id="status" name="status" style="width:160px;" data-options="required:true, disabled:true" >
				<option value="Y">有效</option>
				<option value="N">无效</option>
				</select>
				</td>
            </tr>
            
            
        </table>
    </form>
</div>
