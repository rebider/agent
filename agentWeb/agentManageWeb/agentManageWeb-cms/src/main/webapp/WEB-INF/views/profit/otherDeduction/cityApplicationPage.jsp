<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>

<script type="text/javascript">

    //提交申请信息
    $(function(){
        $("#applicationDeductionForm").form({
            url:"${path}/profit/application/commitInfo",
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



    //校验代理商姓名
    function agentCheck() {
        var agentId = $("#agentPId").val();
        if(agentId == '' || agentId == undefined){
            alertMsg("代理商唯一码不能为空");
            return false;
        }
        $.ajax({
            url:"${path}/profit/application/queryAgentName",
            type:"POST",
            data: {"agentId":agentId},
            dataType:'json',
            success:function(data){
                if(data.success){
                    var result = data.obj;
                    $("#agentPName").val(result.agentName);
                    if(result.parentInfo.length >0){
                        $('#parentAgentId,#parentAgentName').validatebox({
                            required: true
                        });
                    }else{
                        $("#parentAgentId,#parentAgentName").attr("readonly","readonly");
                    }
                }else{
                    alertMsg("请输入正确的唯一码");
                    $("#agentPName").val("");
                }
            },
            error:function(data){
                alertMsg("获取代理商失败，请联系管理员！");
            }
        });
    }

    //获取上级代理商名称
    function queryParentAgentCheck() {
        var parentAgentId = $("#parentAgentId").val();
        var agentId = $("#agentPId").val();
        if(agentId == '' || agentId == undefined){
            alertMsg("请先输入代理商唯一码");
            return false;
        }
        if(parentAgentId == '' || parentAgentId == undefined){
            alertMsg("上级代理商唯一码不能为空");
            return false;
        }
        $.ajax({
            url:"${path}/profit/application/queryParentAgentName",
            type:"POST",
            data: {"parentAgentId":parentAgentId,"agentId":agentId},
            dataType:'json',
            success:function(data){
                if(data.success){
                    var result = data.obj;
                    $("#parentAgentName").val(result);
                }else {
                    alertMsg(data.resInfo);
                    $("#parentAgentId").val("");
                }
            },
            error:function(data){
                alertMsg("获取代理商失败，请联系管理员！");
            }
        });
    }

    function alertMsg(msg) {
        parent.$.messager.alert('提示',msg, 'info');
    }

</script>


<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="applicationDeductionForm" method="post">
            <table>
                <tr>
                    <td>代理商唯一码:</td>
                    <td>
                        <input id="agentPId" name="agentId" placeholder="请输入代理商编码" class="easyui-validatebox" data-options="required:true" style="width:200px;" />
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="agentCheck();">校验</a>
                    </td>
                </tr><tr/>
                <tr>
                    <td>代理商名称：</td>
                    <td>
                        <input id="agentPName" name="agentName" value="" class="easyui-validatebox" data-options="required:true" readonly="readonly" style="width:200px;">
                    </td>
                </tr><tr/>
                <tr>
                    <td>上级代理商唯一码：</td>
                    <td>
                        <input id="parentAgentId" name="parentAgentId"  style="width:200px;" />
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="queryParentAgentCheck();">校验</a>
                    </td>
                </tr><tr/>
                <tr>
                    <td>上级代理商名称：</td>
                    <td>
                        <input id="parentAgentName" name="parentAgentName"  readonly="readonly" style="width:200px;">
                    </td>
                </tr><tr/>
                <tr>
                    <td>扣款金额：</td>
                    <td>
                        <input id="applicationAmt" name="applicationAmt" class="easyui-validatebox" data-options="required:true" style="width:200px;">
                    </td>
                </tr><tr/>
                <tr>
                    <td>扣款类型</td>
                    <td>
                        <input id="deductionRemark" name="deductionRemark" class="easyui-validatebox" data-options="required:true" style="width:200px;">
                    </td>
                </tr><tr/>
                <tr>
                    <td>扣款原因:</td>
                    <td>
                        <textarea id="remark"  name= "remark" style="width:200px;height: 80px;"></textarea>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>