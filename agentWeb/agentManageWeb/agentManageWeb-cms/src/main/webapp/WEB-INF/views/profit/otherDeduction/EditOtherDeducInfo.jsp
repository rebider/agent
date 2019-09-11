<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-panel" title="修改其他扣款申请信息" data-options="iconCls:'fi-results'">
    <form id="otherDeductEditFrom" method="post">
        <table class="grid">
            <input type="hidden" name="id" value="${pCityApplicationDetail.id}">
            <tr>
                <td width="200px">代理商唯一码：</td>
                <td><input id="agentId" name="agentId" value="${pCityApplicationDetail.agentId}"
                           class="easyui-textbox" data-options="editable:false" style="width:270px;"></td>
            </tr>
            <tr>
                <td width="200px">代理商名称：</td>
                <td><input name="agentName" style="width:270px;" value="${pCityApplicationDetail.agentName}" class="easyui-textbox" data-options="editable:false"></td>
            </tr>
            <tr>
                <td width="200px">上级代理商唯一码：</td>
                <td><input id="parentAgentId" name="parentAgentId" style="width:270px;" value="${pCityApplicationDetail.parentAgentId}">
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="queryParentAgentCheck();">校验</a>
                </td>
            </tr>
            <tr>
                <td width="200px">上级代理商名称：</td>
                <td><input id="parentAgentName" name="parentAgentName" style="width:270px;" value="${pCityApplicationDetail.parentAgentName}" class="easyui-textbox" data-options="editable:false"></td>
            </tr>
            <tr>
                <td width="200px">扣款类型：</td>
                <td><input name="deductionRemark" style="width:270px;" value="${pCityApplicationDetail.deductionRemark}" data-options="required:true"></td>
            </tr>
            <tr>
                <td width="200px">扣款金额：</td>
                <td><input name="applicationAmt" style="width:270px;" value="${pCityApplicationDetail.applicationAmt}" data-options="required:true"></td>
            </tr>
            <tr>
                <td width="200px">备注：</td>
                <td><input name="remark" style="width:270px;" value="${pCityApplicationDetail.remark}" data-options="required:true"></td>
            </tr>
        </table>
    </form>
    <script type="text/javascript">
        function get_FormData() {
            var data = {};
            data.id = $("input[name='id']").val();
            data.agentId = $("input[name='agentId']").val();
            data.agentName = $("input[name='agentName']").val();
            data.parentAgentName = $("input[name='parentAgentName']").val();
            data.parentAgentId = $("input[name='parentAgentId']").val();
            data.deductionRemark = $("input[name='deductionRemark']").val();
            data.applicationAmt = $("input[name='applicationAmt']").val();
            data.remark = $("input[name='remark']").val();
            return data;
        }


        //校验上级代理商名称
        function queryParentAgentCheck() {
            $("#parentAgentName").textbox("setValue", "");
            var parentAgentId = $("#parentAgentId").val();
            var agentId = $("#agentId").val();
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
                        $("#parentAgentName").textbox("setValue", result);
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
</div>


