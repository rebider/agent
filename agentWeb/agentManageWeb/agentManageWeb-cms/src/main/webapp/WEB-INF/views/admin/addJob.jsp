<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#jobAddForm').form({
            url : '${path }/springbatch/add',
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
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="jobAddForm" method="post">
            <table class="grid">
                <tr>
                    <td>job名称</td>
                    <td >
                        <select name="jobName" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="createRepayPlan">计息计提</option>
                           <!--  <option value="repayPlanJob">逾期跑批</option> -->
                            <option value="batchDeductJob">扣款批</option>
                            <option value="repayNoticeJob">还款通知批</option>
                            <option value="findTransFlowJob">待定</option>
                        </select>
                    </td>
                </tr>
                 <tr>
                    <td>跑批时间</td>
                    <td><input class="easyui-datebox" name="jobTime" data-options="required:true"  style="width:150px">  </td>
                </tr>
                <tr>
                    <td>跑批状态</td>
                    <td >
                        <select name="jobStatus" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="WAIT">等待</option>
                            <option value="COMPLETED">成功</option>
                            <option value="FAILED">失败</option>
                        </select>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>