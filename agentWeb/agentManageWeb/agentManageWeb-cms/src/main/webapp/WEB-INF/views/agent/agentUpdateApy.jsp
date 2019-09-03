<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>


<%@ include file="/commons/editAgentBasics_model.jsp" %>
<%@ include file="/commons/editAgentCapital_model.jsp" %>
<%@ include file="/commons/editAgentContractTable_model.jsp" %>
<%--<%@ include file="/commons/editAgentColinfoTable_model.jsp" %>--%>
<%@ include file="/commons/editAgentBusi_model.jsp" %>
<%@ include file="/commons/editAttachment_model.jsp" %>

<shiro:hasPermission name="/agentUpdateApy/agentBaseUpdateApy">
    <div style="text-align:right;padding:5px;margin-bottom: 50px;">
        <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="saveAgentEnterEditApy()">保存</a>
    </div>
</shiro:hasPermission>

<script type="text/javascript">
    $(function () {
        //手动输入清除工商认证状态
        $('#agentName').bind('input propertychange', function() {
            $("#caStatus").val("0");
        });
    });
    function saveAgentEnterEditApy(){
       var agent =get_editAgentBasics_FormData();
       var editAgentcapitalTable =(typeof get_editAgentcapitalTable_FormData=== "function")?get_editAgentcapitalTable_FormData():[];
       var editAgentContractTable =(typeof get_editAgentContractTable_FormData=== "function")?get_editAgentContractTable_FormData():[];
       var editAgentColinfoTable =[];
        var editAgentBusiTable =(typeof get_editAgentBusiTable_FormData=== "function")?get_editAgentBusiTable_FormData():[];
        var AgentBase_AttFile_model_formFiles = (typeof get_addAttAgentcapitalTable_attrfiles=== "function")?get_addAttAgentcapitalTable_attrfiles():[];

        parent.$.messager.confirm('询问', '确认要保存？', function (b) {
            if (b) {
                $.ajaxL({
                   type: "POST",
                    url: "/agentUpdateApy/agentBaseUpdateApy",
                    dataType:'json',
                    contentType:'application/json;charset=UTF-8',
                    data: JSON.stringify({
                        agent: agent,
                        capitalVoList:editAgentcapitalTable,
                        contractVoList:editAgentContractTable,
                        busInfoVoList:editAgentBusiTable,
                        agentTableFile:AgentBase_AttFile_model_formFiles
                    }),
                    beforeSend:function(){
                       progressLoad();
                    },
                    success: function(msg){
                       if(msg.resCode && msg.resCode=='1'){
                           info(msg.resInfo);
                           $('#index_tabs').tabs('close',"代理商信息修改申请");
                       }else{
                           info(msg.resInfo);
                       }
                   },
                    complete:function (XMLHttpRequest, textStatus) {
                        progressClose();
                    }
                });
            }
        });
    }
    function industrialAuth() {
        var agentName = $('#agentName').val();
        if (agentName == '' || agentName == undefined) {
            info("请填写代理商名称！");
            return;
        }

        $.ajax({
            url: "${path}/agent/businessCA",
            type: 'POST',
            data: {
                agentBusinfoName: agentName
            },
            dataType:'json',
            success:function(data){
                var status = data.status;
                if(status==404){
                    info("暂无该代理商信息");
                    return false;
                }
                if(status==500){
                    info("服务器异常，请联系管理员！");
                    return false;
                }
                if(status==405){
                    info(data.msg);
                }
                var dataObj = data.data;
                $("#caStatus").val("1");  //工商认证状态
//                $("input[name='agBusLic']").val(dataObj.regNo);  //营业执照
                $("input[name='agCapital']").val(dataObj.regCap); //注册资金
                $("#agBusLicb").datebox("setValue",dataObj.openFrom); //经营开始日期
                if(dataObj.openTo=='长期'){
                    $("#agBusLice").datebox("setValue","2099-12-12");//经营结束日期
                }else{
                    $("#agBusLice").datebox("setValue",dataObj.openTo);//经营结束日期
                }
                $("input[name='agLegal']").val(dataObj.frName);  //法人姓名
                $("input[name='agRegAdd']").val(dataObj.address); //注册地址
                $("input[name='agBusScope']").val(dataObj.operateScope); //经营范围
                $("input[name='agBusLic']").val(dataObj.creditCode); //营业执照

//
                if(dataObj.regCap!='' && dataObj.regCap!=undefined){
                    if(dataObj.regCap.trim()!=''){
                        $("input[name='agCapital']").removeClass("validatebox-invalid");
                        $("input[name='agCapital']").textbox({readonly:true});
                    }
                }
                if(dataObj.openFrom!=''  && dataObj.openFrom!=undefined){
                    $("input[name='agBusLicb']").attr("disabled","disabled");
                    $("input[name='agBusLicb']").removeClass("validatebox-invalid");
                    $("input[name='agBusLicb']").parent().find(".textbox-addon").remove();
                }
                if(dataObj.openTo!='' && dataObj.openTo!=undefined){
                    $("input[name='agBusLice']").removeClass("validatebox-invalid");
//                    $("input[name='agBusLice']").textbox({readonly:true});
                    $("input[name='agBusLice']").attr("disabled");
                    $("input[name='agBusLice']").parent().find(".textbox-addon").remove();
                    $("input[name='agBusLice']").parent().parent().find("#wxqId").remove();
                }
                if(dataObj.frName!='' && dataObj.frName!=undefined){
                    $("input[name='agLegal']").removeClass("validatebox-invalid");
                    $("input[name='agLegal']").textbox({readonly:true});
                }
                if(dataObj.address!='' && dataObj.address!=undefined){
                    $("input[name='agRegAdd']").removeClass("validatebox-invalid");
                    $("input[name='agRegAdd']").textbox({readonly:true});
                }
                if(dataObj.operateScope!='' && dataObj.operateScope!=undefined){
                    $("input[name='agBusScope']").removeClass("validatebox-invalid");
                    $("input[name='agBusScope']").textbox({readonly:true});
                }
                if(dataObj.creditCode!='' && dataObj.creditCode!=undefined){
                    $("input[name='agBusLic']").removeClass("validatebox-invalid");
                    $("input[name='agBusLic']").textbox({readonly:true});

                }
                $("input[name='agName']").textbox({readonly:true});
                $("#agentBasics .datebox :text").attr("disabled","true");

            },
            error:function(data){
                info("服务器异常，请联系管理员！");
            }
        });
    }


    function livenessEditAuth() {
        var trueName = $('#agLegal').val();
        var certNo = $("#agentBasics").find("input[name='agLegalCernum']").val();

        if (trueName == '' || trueName == undefined) {
            info("请填写法人姓名！");
            return;
        }
        if (certNo == '' || certNo == undefined) {
            info("请填写法人证件号	！");
            return;
        }

        $.ajax({
            url: "${path}/agent/livenessDetection",
            type: 'POST',
            data: {
                trueName: trueName,
                certNo: certNo
            },
            dataType:'json',
            success:function(data){
                var status = data.status;
                if(status==500){
                    info("请求参数错误！");
                    return false;
                }
                if(status==200){
                    $("#livenessStatus").val("1");
                    $("#agentBasics").find("input[name='agLegalCernum']").textbox({readonly:true});
                    $("input[name='agLegal']").textbox({readonly:true});
                    info("认证成功!");
                    return false;
                }
                if(status==400){
                    $("#livenessStatus").val("0");
                    info(data.msg);
                    return false;
                }
            },
            error:function(data){
                info("服务器异常，请联系管理员！");
            }
        });
    }
</script>
