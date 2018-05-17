<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#shareSummaryAddForm').form({
            url : '${path }/shareSummary/addShareSummary',
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
        <form id="shareSummaryAddForm" method="post">
            <table class="grid">
                <!-- <tr>
	                <td>分润汇总id</td>
	                <td><input id="shareSummaryId" name="shareSummaryId" style="width:160px;" class="easyui-validatebox" data-options="required:true"></td>
                </tr> -->
                 <tr>
                    <td>所属代理商</td>
                    <td><input name="agentId" type="text" placeholder="请输入" class="easyui-validatebox"  data-options="required:true" style="width:160px;"></td>
                    <td>交易所属日期</td>
                    <td><input name="transTime" type="text" placeholder="请输入" class="easyui-datebox" data-options="required:true" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>汇总日期</td>
                    <td><input name="summaryTime" type="text" class="easyui-datebox" data-options="required:true" style="width:160px;"></td>
                    <td>交易总额</td>
                    <td><input name="summaryAmt" type="text" class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>本金总额</td>
                    <td><input name="summaryLoanAmt" type="text" class="easyui-validatebox" style="width:160px;"></td>
                    <td>息与费总额</td>
                    <td><input name="summaryAllRate" type="text" placeholder="请输入" class="easyui-validatebox" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>利息总额</td>
                    <td><input name="summaryRate" type="text" placeholder="请输入"  style="width:160px;"></td>
                    <td>罚息总额</td>
                    <td><input name="summaryPenalty" type="text" placeholder="请输入" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>有效分润总额</td>
                    <td><input name="summaryValidAmt" type="text" placeholder="请输入" style="width:160px;"></td>
                    <td>分润金额</td>
                    <td><input name="summaryShareAmt" type="text" placeholder="请输入"  style="width:160px;"></td>
                </tr>
                <tr>
                    <td>分润参考总额</td>
                     <td><input name="summaryDemoAmt" type="text" style="width:160px;"></td>
                </tr>
            </table>
        </form>
    </div>
</div>