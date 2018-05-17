<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#ruleAddForm').form({
            url : '${path}/creditcardagency/addRule',
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
        <form id="ruleAddForm" method="post">
            <table class="grid">
                <tr>
                    <td>规则名称</td>
                    <td><input name="ruleName" maxlength="15" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
                    <td>业务类型</td>
                     <td>
		                <select class="easyui-combobox" editable="false" name="businessType" style="width:160px;" data-options="required:true">
			                <option></option>
							<option value="CREDIT_APY">代办信用卡</option>
						</select>
	                </td>
                </tr>
                <tr>
                    <td>规则类型</td>
                    <td>
                        <select class="easyui-combobox" name="ruleType"  editable="false" style="width:160px;" data-options="required:true">
                            <option></option>
                            <option value="ZDX">针对型</option>
                            <option value="TY">通用型</option>
                        </select>
                    </td>
                    <td>业务支持级别</td>
                    <td><input name="businessLevel" maxlength="5" type="text" placeholder="请输入"  data-options="required:true" style="width:160px;" class="easyui-numberbox"></td>
                </tr>
                <tr>
                    <td>状态</td>
                    <td>
                        <select class="easyui-combobox"  editable="false" name="status" style="width:160px;" data-options="required:true">
                            <option></option>
                            <option value="0">不启用</option>
                            <option value="1">启用</option>
                        </select>
                    </td>
                    <td>商户</td>
                    <td>
                        <select class="easyui-combobox"  editable="false" name="agentId" style="width:160px;" data-options="required:true">
                            <option></option>
                            <c:forEach var="cagent" items="${cagentList}">
                                <option value="${cagent.agentId}">${cagent.agentName}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>银行</td>
                    <td>
                        <select class="easyui-combobox" name="sourceId"  editable="false" style="width:160px;" data-options="required:true">
                            <option></option>
                            <c:forEach var="bankInfo" items="${bankInfoList}">
                                <option value="${bankInfo.code}">${bankInfo.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>分润模式</td>
                    <td>
                        <select class="easyui-combobox" name="shareMode"  editable="false" style="width:160px;" data-options="required:true">
                            <option></option>
                            <option value="RATE">比率</option>
                            <option value="STATIC">固定金额</option>
                            <option value="RATE_CODE">分润代码</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>分润类型</td>
                    <td>
                        <select class="easyui-combobox" name="shareType"  editable="false" style="width:160px;" data-options="required:true">
                            <option></option>
                            <option value="YJ">佣金</option>
                            <option value="SS">首刷</option>
                        </select>
                    </td>
                    <td>分润比例值</td>
                    <td><input name="shareRate" maxlength="15" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>佣金</td>
                    <td><input name="commission"  maxlength="10" type="text" placeholder="请输入" class="easyui-numberbox" data-options="required:true" style="width:160px;"></td>
                    <td>单笔分润封顶值</td>
                    <td><input name="shareCap"  maxlength="10" type="text" placeholder="请输入" class="easyui-numberbox" data-options="required:true" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>开始时间</td>
                    <td><input name="startDateVo" class="easyui-datetimebox"  data-options="required:true" editable="false" placeholder="开始时间" style="width:160px;"/></td>
                    <td>结束时间</td>
                    <td><input name="endDateVo" class="easyui-datetimebox" data-options="required:true"  editable="false" placeholder="结束时间" style="width:160px;"/></td>
                </tr>
            </table>
        </form>
    </div>
</div>