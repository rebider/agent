<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<div style="padding: 3px;">
    <form id="productGroupQueryForm" method="post">
        <table class="grid">
         	<tr>
                <td>产品组编码</td>
                <td>
                	<input name="groupId" type="hidden"  value="${cProductGroup.groupId}">
                	<input name="productGroupId" type="text" value="${cProductGroup.productGroupId}"  class="easyui-textbox" data-options="required:true, disabled:true" style="width:160px;">
                </td>
            </tr>
            <tr>
                <td>产品组名称</td>
                <td><input name="productName" type="text" value="${cProductGroup.productName}"  class="easyui-textbox" data-options="required:true, disabled:true" style="width:160px;"></td>
            </tr>
            <tr>
                    <td>产品组类型</td>
                    <td>
                    <select class="easyui-combobox" id="productGroupType" name="productGroupType" style="width:160px;" data-options="required:true, disabled:true">
						<option value="N">正常</option>
						<option value="A">优惠活动</option>
						<option value="O">其他</option>
					</select>
					</td>
                </tr>
        </table>
    </form>
</div>
