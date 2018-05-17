<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
     $(function() {
        $("#strategyType").val('${CshareStrategy.strategyType}');
        $("#strategyStatus").val('${CshareStrategy.strategyStatus}');
    });
</script>
<div style="padding: 3px;">
    <form id="shareStragegyQueryForm" method="post">
    <input type="hidden" name="tempID" value="${CshareStrategy.strategyId}">
        <table class="grid">
              	<tr>
	                <td>策略id</td>
	                <td><input id="strategyId" name="strategyId" value="${CshareStrategy.strategyId}" style="width:160px;" class="easyui-validatebox" data-options="required:true"></td>
                    <td>执行金额起</td>
                    <td><input name="strategyAmtSta" value="${CshareStrategy.strategyAmtSta}" type="text" placeholder="请输入" class="easyui-validatebox"  style="width:160px;"></td>
                </tr>
                <tr>
                    <td>执行金额止</td>
                    <td><input name="strategyAmtEnd" value="${CshareStrategy.strategyAmtEnd}" type="text" placeholder="请输入" class="easyui-validatebox" style="width:160px;"></td>
                    <td>执行时间起</td>
                    <td><input name="starTime" value="${CshareStrategy.starTime}" type="text" class="easyui-timespinner" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>执行时间止</td>
                    <td><input name="endTime" value="${CshareStrategy.endTime}" type="text" class="easyui-timespinner" style="width:160px;"></td>
                    <td>策略类型</td>
                    <td>
		                <select class="easyui-combobox" id="strategyType" name="strategyType" data-options="required:true" style="width:160px;">
			                <option></option>
			                <option value="A">息与费</option>
						  	<option value="T">利息</option>
						  	<option value="L">罚息</option>
						  	<option value="K">利息罚息</option>
						</select>
				    </td>
                </tr> 
                <tr>
                    <td>单笔分润封顶</td>
                    <td><input name="shareCap" value="${CshareStrategy.shareCap}" type="text" placeholder="请输入" class="easyui-validatebox" style="width:160px;"></td>
                    <td>分润比</td>
                    <td><input name="shareProp" value="${CshareStrategy.shareProp}" type="text" placeholder="请输入"  style="width:160px;"></td>
                </tr>
                <tr>
                    <td>策略录入时间</td>
                    <td><input name="insertTime" value="${CshareStrategy.insertTime}" type="text" placeholder="请输入" class="easyui-datebox" style="width:160px;"></td>
                    <td>策略更新时间</td>
                    <td><input name="updateTime" value="${CshareStrategy.updateTime}" type="text" placeholder="请输入" class="easyui-datebox" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>策略更新人员</td>
                    <td><input name="updateUser" value="${CshareStrategy.updateUser}" type="text" placeholder="请输入"  style="width:160px;"></td>
                    <td>策略是否启用</td>
                     <td>
		                <select class="easyui-combobox" id="strategyStatus" name="strategyStatus" style="width:160px;" data-options="required:true">
			                <option></option>
			                <option value="N">不启用</option>
			                <option value="Y">启用</option>
						</select>
	                </td>
                </tr>
        </table>
    </form>
</div>
