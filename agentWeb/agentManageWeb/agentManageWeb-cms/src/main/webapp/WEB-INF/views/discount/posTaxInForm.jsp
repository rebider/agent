<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-panel" title="税点调整-填写申请信息" data-options="iconCls:'fi-results'">
    <form id="posTaxBaseData">
        <table class="grid">
            <tr>
                <td width="360px">代理商唯一码：
                    <input id="agentId" maxlength="30" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true" style="width:160px;margin-left: 10px">
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentTax();">校验</a>
                </td>
                <td width="360px"><a id="agentName"></a></td>
                <td width="360px"><a id="taxOld"></a></td>
            </tr>
            <tr>
                <td width="360px">申请税点：
                    <input id="taxIng" maxlength="20" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true" style="width:160px;">
                </td>
                <td>分润月份：
                    <input id="profitMonth" name="profitMonth" placeholder="请选择分润月份" class="easyui-datebox" data-options="required:true" style="width:200px;">
                </td>
                <td></td>
            </tr>
        </table>
    </form>
</div>
<div style="text-align:right;padding:5px;margin-bottom: 50px;">
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'" onclick="saveposTaxEnterIn()">提交</a>
</div>
<script type="text/javascript">
    $(function () {
        $("#profitMonth").datebox({
            required : true,
            formatter: function(date){
                var y = date.getFullYear();
                var m = date.getMonth() + 1;
                var d = date.getDate();
                return y + "-" + (m<10?('0'+m):m);
            },
            parser: function(s){
                var t = Date.parse(s);
                if (!isNaN(t)){
                    return new Date(t);
                } else {
                    return new Date();
                }
            }
        });
        $('#profitMonth').datebox().datebox('calendar').calendar({
            validator : function(date){
                var now = new Date();
                var d1 = new Date(now.getFullYear(),now.getMonth()-1,now.getDate());
                return d1 <= date;
            }
        });
    });

    var agName = undefined;
    var cloTaxPoint = undefined;
    function alertMsg(msg) {
        parent.$.messager.alert('提示',msg, 'info');
    }

    function queryAgentTax(agentId) {
        //校验代理商----名称
        var agUniqNum = $("#agentId").val();
        if(agUniqNum=='' || agUniqNum==undefined){
            alertMsg("代理商唯一码不能为空");
            return false;
        }
        $.ajax({
            url :"${path}/business/queryAgentName",
            type:'POST',
            data:{
                id:agUniqNum
            },
            dataType:'json',
            success:function(data){
                if(data.success){
                    var jsonObj = JSON.parse(data.msg);
                    var text = "<a id=\"agentAname\" style=\"margin-left: 20px\">代理商名称："+jsonObj.agName+"</a>";
                    agName = jsonObj.agName;
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

        //校验代理商----原税点
        var agentId = $("#agentId").val();
        if(agentId=='' || agentId==undefined){
            alertMsg("代理商唯一码不能为空");
            return false;
        }
        $.ajax({
            url :"${path}/discount/queryPoint",
            type:'POST',
            data:{
                agentId:agentId
            },
            dataType:'json',
            success:function(data){
                if(data.success){
                    var jsonObj = JSON.parse(data.msg);
                    var text = "<a id=\"taxPoint\" style=\"margin-left: 20px\">原税点："+jsonObj.cloTaxPoint+"</a>";
                    cloTaxPoint = jsonObj.cloTaxPoint;
                    $("#taxOld").html(text);
                    $('input[name="agentId"]').val(jsonObj.id);
                }else{
                    alertMsg(data.msg);
                    $('input[name="agentId"]').val("");
                    $("#taxPoint").remove();
                }
            },
            error:function(data){
                alertMsg("获取代理商失败，请联系管理员！");
            }
        });
    }

    //执行申请
    function saveposTaxEnterIn() {
        var agentBase = $("#posTaxBaseData").form('validate');
        var agUniqNum = $("#agentId").val();
        var agentId = $("#agentId").val();
        var agUniqNam = agName;
        var xxOld = cloTaxPoint;
        var xxIng = $("#taxIng").val();
        var month = $("#profitMonth").datebox("getValue");
        if(agUniqNum=='' || agUniqNum==undefined){
            alertMsg("请输入代理商唯一码！");
            return;
        }
        if(agentId=='' || agentId==undefined){
            alertMsg("请输入代理商唯一码！");
            return;
        }
        if(agUniqNam=='' || agUniqNam==undefined){
            alertMsg("请校验获取代理商名称！");
            return;
        }
        if(xxOld=='' || xxOld==undefined){
            alertMsg("请校验获取原税点！");
            return;
        }
        if(xxIng < 0.03 || xxIng > 0.06){
            alertMsg("申请税点不能小于0.03且不能大于0.06！");
            return;
        }
        if(month=='' || month==undefined){
            alertMsg("请选择分润月份！");
            return;
        }
        if (agentBase) {
            parent.$.messager.confirm('询问', '确认提交申请？', function(b) {
                if (b) {
                    $.ajaxL({
                        type: "POST",
                        url: "/discount/posTaxEnterIn",
                        dataType:'json',
                        data: {
                            agNam : agUniqNam,
                            agNum : agUniqNum,
                            agPid : agentId,
                            old : xxOld,
                            ing : xxIng,
                            month : month
                        },
                        beforeSend : function() {
                            progressLoad();
                        },
                        success: function(msg){
                            console.log(msg);
                            info(msg.resInfo);
                            if(msg.resCode=='1'){
                                $('#index_tabs').tabs('close',"优惠政策-申请税点调整");
                                posTaxList.datagrid('reload');
                            }
                        },
                        complete:function (XMLHttpRequest, textStatus) {
                            progressClose();
                        }
                    });
                }
            });
        } else {
            info("请输入必填项")
        }
    }
</script>
