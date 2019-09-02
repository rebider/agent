<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">

    function alertMsg(msg) {
        parent.$.messager.alert('提示',msg, 'info');
    }

    //校验代理商
    function verifyAgent(agUniqNum) {
        var agUniqNum = $("#agUniqNum").val();
        if(agUniqNum=='' || agUniqNum==undefined){
            alertMsg("唯一编号不能为空");
            return false;
        }
        $.ajax({
            url :"${path}/business/verifyAgent",
            type:'POST',
            data:{
                agUniqNum:agUniqNum
            },
            dataType:'json',
            success:function(data){
                if(data.success){
                    var jsonObj = JSON.parse(data.msg);
                    var text = "<a id=\"agentAname\" style=\"margin-left: 20px\">代理商名称："+jsonObj.agName+"</a>";
                    $("#agentName").html(text);
                    $('input[name="agentId"]').val(jsonObj.id);
                }else{
                    alertMsg(data.msg);
                    $('input[name="agentId"]').val("");
                    $("#agentAname").remove();
                }
            },
            error:function(data){
                alertMsg("获取代理商失败，请联系管理员！");
            }
        });
    }

    function businessPlatSave(approve) {
        var agentId = $('input[name="agentId"]').val();
        if(agentId=='' || agentId==undefined){
            alertMsg("请输入代理商唯一编号！");
            return;
        }
        var addAgentcapitalTable = (typeof get_addAgentcapitalTable_FormData === "function")?get_addAgentcapitalTable_FormData():[];
        var addAgentContractTable = (typeof get_addAgentContractTable_FormData === "function")?get_addAgentContractTable_FormData():[];
//        var addAgentColinfoTable = (typeof get_addAgentColinfoTable_FormData === "function")?get_addAgentColinfoTable_FormData():[];
        var addAgentBusiTable = (typeof get_addAgentBusiTable_FormData === "function")?get_addAgentBusiTable_FormData():[];
//        if(addAgentColinfoTable=='' || addAgentColinfoTable==undefined){
//            alertMsg("最少添加一个收款账户！");
//            return;
//        }
        if(addAgentBusiTable=='' || addAgentBusiTable==undefined){
            alertMsg("最少添加一个业务信息！");
            return;
        }
        parent.$.messager.confirm('询问', '确认要添加？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/business/addBusPlat",
                    dataType:'json',
                    traditional:true,//这使json格式的字符不会被转码
                    contentType:'application/json;charset=UTF-8',
                    data: JSON.stringify({
                        capitalVoList:addAgentcapitalTable,
                        contractVoList:addAgentContractTable,
//                        colinfoVoList:addAgentColinfoTable,
                        busInfoVoList:addAgentBusiTable,
                        agentId:agentId
                    }),
                    success: function(data){
                        if(data.status==200){
                            $('#index_tabs').tabs('close',"新增业务");
                            if(approve==1){
                                $.each(data.data,function(n,value) {
                                    $.ajaxL({
                                        type: "POST",
                                        url: "/agActivity/startBus",
                                        dataType:'json',
                                        data: {busId:value.id},
                                        beforeSend:function(){
                                        },
                                        success: function(msg){
                                            info(msg.resInfo);
                                        },
                                        complete:function (XMLHttpRequest, textStatus) {
                                            RefreshCloudHomePageTab();
                                        }
                                    });
                                });
                            }else{
                                alertMsg("添加成功！");
                            }
                            RefreshCloudHomePageTab();
                        }else{
                            if(data.msg!='' || data.msg!=undefined){
                                alertMsg(data.msg);
                            }else{
                                alertMsg("添加异常！");
                            }

                        }
                    },
                    complete:function (XMLHttpRequest, textStatus) {

                    }
                });
            }
        });
    }
</script>

<div class="easyui-panel" data-options="iconCls:'fi-results'">
    <input type="hidden" name="agentId">
    <table class="grid">
        <tr>
            <td>唯一编码:
                <input id="agUniqNum" maxlength="80" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true" style="width:160px;margin-left: 10px">
                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="verifyAgent();">校验</a>
                <a id="agentName">
                </a>
            </td>
        </tr>
        <tr>
            <td>
                <a style="color: red">注:(可根据"代理商唯一编码"、"业务平台编号"、"代理商名称"查询)</a>
            </td>
        </tr>
    </table>
</div>

<%@ include file="/commons/agentCapital_model.jsp" %>
<%@ include file="/commons/agentContractTable_model.jsp" %>
<%--<%@ include file="/commons/agentColinfoTable_model.jsp" %>--%>
<%@ include file="/commons/agentBusi_model.jsp" %>



<div style="text-align:right;padding:5px;margin-bottom: 50px;">
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'" onclick="businessPlatSave()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="businessPlatSave(1)">保存并审核</a>
</div>

