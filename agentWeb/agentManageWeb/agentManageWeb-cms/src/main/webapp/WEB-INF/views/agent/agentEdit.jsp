<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>


<%@ include file="/commons/editAgentBasics_model.jsp" %>
<%@ include file="/commons/editAttachment_model.jsp" %>
<%@ include file="/commons/editAgentCapital_model.jsp" %>
<%@ include file="/commons/editAgentContractTable_model.jsp" %>
<%@ include file="/commons/editAgentColinfoTable_model.jsp" %>
<%@ include file="/commons/editAgentBusi_model.jsp" %>
<%@ include file="/commons/validate.jsp" %>
<div style="text-align:right;padding:5px;margin-bottom: 50px;">
   <!--暂存代理商信息修改-->
    <c:if test="${editWatch == '666'}">
       <shiro:hasPermission name="/agActivity/startAg">
           <a href="javascript:void(0)" id="saveAndSumitButton" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-magnifying-glass'" onclick="enterIn(1)">提交审批</a>
       </shiro:hasPermission>
       <shiro:hasPermission name="/agentEnter/agentEdit">
           <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'" onclick="saveAgentEnterEdit()">保存</a>
       </shiro:hasPermission>
   </c:if>
    <!--审批被退回信息修改-->
    <c:if test="${editWatch == '999'}">
        <shiro:hasPermission name="/agentEnter/agentEdit">
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'" onclick="enterIn(2)">保存</a>
        </shiro:hasPermission>
    </c:if>
</div>



<script type="text/javascript">
    $(function () {
        //手动输入清除工商认证状态
        $('#agentName').bind('input propertychange', function() {
            $("#caStatus").val("0");
        });
    });

    function enterIn(num){

        setValidateboxTrue();
        var agBusLicb = $("#agBusLicb").datebox("getValue");
        if('' == agBusLicb || undefined == agBusLicb){
            info("请选择营业执照开始时间！");
            return;
        }
        var agBusLice = $("#agBusLice").datebox("getValue");
        if('' == agBusLice || undefined == agBusLice){
            info("请选择营业执照到期日！");
            return;
        }

        var agent =get_editAgentBasics_FormData();
        if(agent.agRegArea=='' || agent.agRegArea==undefined){
            info("请选择注册地区！");
            return;
        }
        var editAgentcapitalTable =(typeof get_editAgentcapitalTable_FormData=== "function")?get_editAgentcapitalTable_FormData():[];
        var editAgentContractTable =(typeof get_editAgentContractTable_FormData=== "function")?get_editAgentContractTable_FormData():[];
        var editAgentColinfoTable =(typeof get_editAgentColinfoTable_FormData=== "function")?get_editAgentColinfoTable_FormData():[];
        var editAgentBusiTable =(typeof get_editAgentBusiTable_FormData=== "function")?get_editAgentBusiTable_FormData():[];
        var AgentBase_AttFile_model_formFiles = (typeof get_addAttAgentcapitalTable_attrfiles=== "function")?get_addAttAgentcapitalTable_attrfiles():[];
        var editAgentBase = $("#agentBasics").form('validate');
        var editAgentcapital = $("#editAgentcapital_model_form").form('validate');
        var editAgentContract = $("#editAgentContract_model_form").form('validate');
        var editAgentColinfo = $("#editAgentColinfoTable_model_form").form('validate');
        var editAgentBusi = $("#editAgentBusi_model").form('validate');

//        for (var i =0;i<editAgentcapitalTable.length; i++) {
//            var aaa = $('input[name="cPayuser"]').val();
//            console.log(aaa);
//            if(aaa != undefined && aaa != ''){
//                var ddd = $('input[name="cPaytime"]').datebox("getValue");
//                if(ddd == undefined || ddd == null){
//                    info("请选择打款时间！");
//                    return;
//                }
//            }
//        }
        if(editAgentBusiTable=='' || editAgentBusiTable==undefined){
            info("最少添加一个业务信息！");
            return;
        }

        if(AgentBase_AttFile_model_formFiles.length<2){
             info("请上传营业执照扫描件和法人身份证扫描件！");
             return;
         }
        var cloType = editAgentColinfoTable[0].cloType;
        var cloTaxPoint = editAgentColinfoTable[0].cloTaxPoint;
        var cloBankBranch = editAgentColinfoTable[0].cloBankBranch;
        console.log(cloBankBranch);
        if (cloBankBranch == undefined || cloBankBranch == '') {
            info("请添加收款开户支行！");
            return false;
        }
        if (editAgentColinfoTable[0].colinfoTableFile == undefined) {
            //对公
            if (cloType == 1 && cloTaxPoint == '0.06') {
                info("请上传一般纳税人证明！");
                return false;
            }
            if (cloType == 1) {
                info("请上传开户许可证扫描件！");
                return false;
            }
            if (cloType == 2) {
                info("请上传银行卡扫描件！");
                return false;
            }
        }

//        var caStatus = $("#caStatus").val();
//        if(caStatus!=1){
//            info("工商认证不通过不允许提交！");
//            return false;
//        }
         var livenessStatus = $("#livenessStatus").val();
         if (livenessStatus != 1) {
             info("身份认证不通过不允许提交！");
             return false;
         }
        var attrFlag = true;
        for(var i=0;i<editAgentcapitalTable.length;i++){
            if(editAgentcapitalTable[i].cPayType=='YHHK'){
                if(editAgentcapitalTable[i].capitalTableFile==undefined ||editAgentcapitalTable[i].capitalTableFile.length==0){
                    attrFlag = false;
                    break;
                }
            }
        }
         if(!attrFlag){
             info("请上传打款凭据");
             return false;
         }
         var infoa = '';
        if(num == '1'){
            infoa = '确认要提交审批？';
        }else if(num == '2'){
            infoa = '确认要保存修改？';
        }

        if (editAgentBase && editAgentcapital && editAgentContract && editAgentColinfo && editAgentBusi) {
            parent.$.messager.confirm('询问', infoa, function (b) {
                if (b) {
                    var url = '';
                    if(num == '1'){
                        url = "/agActivity/startAg";
                    }else if(num == '2'){
                        url = "/agentEnter/agentEditSubmit";
                    }
                    $.ajaxL({
                        type: "POST",
                        url: url,
                        dataType: 'json',
                        contentType: 'application/json;charset=UTF-8',
                        data: JSON.stringify({
                            agent: agent,
                            capitalVoList: editAgentcapitalTable,
                            contractVoList: editAgentContractTable,
                            colinfoVoList: editAgentColinfoTable,
                            busInfoVoList: editAgentBusiTable,
                            agentTableFile: AgentBase_AttFile_model_formFiles
                        }),
                        beforeSend:function(){
                            progressLoad();
                        },
                        success: function (msg) {
                            info(msg.resInfo);
                            if (msg.resCode && msg.resCode == '1') {
                                if('1' == num){
                                    agnet_list_ConditionDataGrid.datagrid('load',{});
                                    colseCurrentTab();
                                }else if('2' == num){
                                //    $('#index_tabs').tabs('close',"审批修改");
                                    colseCurrentTab();
                                }
                            }
                        },
                        complete: function (XMLHttpRequest, textStatus) {
                            progressClose();
                         //   colseCurrentTab();
                        }
                    });
                }
            });
        }else {
            info("请输入必填项");
        }
    }

    function setValidateboxFalse() {
        return true;
        try{
            //代理商基本信息
            $("#agCapital,#agBusLic,#agHead,#agHeadMobile,#agLegalCernum,#agLegal,#agLegalMobile,#agRegAreaText,#agRegAdd,#agBusScope,#agDocDistrict1,#agDocPro1,#busRiskEmail1,#busContactEmail1").validatebox({
                required: false
            });
            //附件信息
            //缴纳款项
            $("input[name='cAmount'],input[name='cPayuser']").validatebox({
                required: false
            });
            //合同信息
            $("input[name='contDate'],input[name='contEndDate']").datebox({
                required: false
            });
            //收款账户
            $("#bankRegion").validatebox({
                required: false
            });
            $("input[name='cloRealname'],input[name='cloBankAccount'],input[name='cloBank'],input[name='branchLineNum'],input[name='cloTaxPoint']").validatebox({
                required: false
            });
            // 业务信息
            $("#busRiskParent3,#busActivationParent1,#busRegion1,#busContact1,#busContactMobile1,#busContactPerson1").validatebox({
                required: false
            });
        }catch(e){
        console.log(e);
        }
    }
    function setValidateboxTrue() {
        return true;
        try{
            //代理商基本信息
            $("#agCapital,#agBusLic,#agHead,#agHeadMobile,#agLegalCernum,#agLegal,#agLegalMobile,#agRegAreaText,#agRegAdd,#agBusScope,#agDocDistrict1,#agDocPro1,#busRiskEmail1,#busContactEmail1").validatebox({
                required: true
            });
            //缴纳款项
            $("input[name='cAmount'],input[name='cPayuser']").validatebox({
                required: true
            });
            //合同信息
            $("input[name='contDate'],input[name='contEndDate']").datebox({
                required: true
            });
            //收款账户
            $("#bankRegion").validatebox({
                required: true
            });
            $("input[name='cloRealname'],input[name='cloBankAccount'],input[name='cloBank'],input[name='branchLineNum'],input[name='cloTaxPoint']").validatebox({
                required: true
            });
            // 业务信息
            $("#busRiskParent3,#busActivationParent1,#busRegion1,#busContact1,#busContactMobile1,#busContactPerson1").validatebox({
                required: true
            });
        }catch(e){
            console.log(e);
        }
    }





    function saveAgentEnterEdit(){

        setValidateboxFalse();

        var agent =get_editAgentBasics_FormData();

        var editAgentcapitalTable =(typeof get_editAgentcapitalTable_FormData=== "function")?get_editAgentcapitalTable_FormData():[];
        var editAgentContractTable =(typeof get_editAgentContractTable_FormData=== "function")?get_editAgentContractTable_FormData():[];
        var editAgentColinfoTable =(typeof get_editAgentColinfoTable_FormData=== "function")?get_editAgentColinfoTable_FormData():[];
        var editAgentBusiTable =(typeof get_editAgentBusiTable_FormData=== "function")?get_editAgentBusiTable_FormData():[];
        var AgentBase_AttFile_model_formFiles = (typeof get_addAttAgentcapitalTable_attrfiles=== "function")?get_addAttAgentcapitalTable_attrfiles():[];
        var editAgentBase = $("#agentBasics").form('validate');
        var editAgentcapital = $("#editAgentcapital_model_form").form('validate');
        var editAgentContract = $("#editAgentContract_model_form").form('validate');
        var editAgentColinfo = $("#editAgentColinfoTable_model_form").form('validate');
        var editAgentBusi = $("#editAgentBusi_model").form('validate');


        var cloType = editAgentColinfoTable[0].cloType;
        var cloTaxPoint = editAgentColinfoTable[0].cloTaxPoint;

        var attrFlag = true;
        for(var i=0;i<editAgentcapitalTable.length;i++){
            if(editAgentcapitalTable[i].cPayType=='YHHK'){
                if(editAgentcapitalTable[i].capitalTableFile==undefined ||editAgentcapitalTable[i].capitalTableFile.length==0){
                    attrFlag = false;
                    break;
                }
            }
        }

        if (editAgentBase && editAgentcapital && editAgentContract && editAgentColinfo && editAgentBusi) {
            parent.$.messager.confirm('询问', '确认要修改？', function(b) {
                if (b) {
                    $.ajaxL({
                        type: "POST",
                        url: "/agentEnter/agentEdit",
                        dataType: 'json',
                        contentType: 'application/json;charset=UTF-8',
                        data: JSON.stringify({
                            agent: agent,
                            capitalVoList: editAgentcapitalTable,
                            contractVoList: editAgentContractTable,
                            colinfoVoList: editAgentColinfoTable,
                            busInfoVoList: editAgentBusiTable,
                            agentTableFile: AgentBase_AttFile_model_formFiles
                        }),
                        beforeSend:function(){
                            progressLoad();
                       },
                        success: function (msg) {
                            info(msg.resInfo);
                            if (msg.resCode && msg.resCode == '1') {
                                //$('#index_tabs').tabs('close', "代理商信息修改");
                                agnet_list_ConditionDataGrid.datagrid('load',{});
                                colseCurrentTab();
                            }
                        },
                        complete: function (XMLHttpRequest, textStatus) {
                            progressClose();

                        }
                    });
                }
            });
        }else {
            info("请输入必填项");
        }

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
                    $("#agBusLice").datebox("setValue","2099-12-31");//经营结束日期
                }else{
                    if(dataObj.openTo=='0000-00-00'){
                        $("#agBusLice").datebox("setValue","2099-12-31");//经营结束日期
                    }else{
                        $("#agBusLice").datebox("setValue",dataObj.openTo);//经营结束日期
                    }
                }
                $("input[name='agLegal']").val(dataObj.frName);  //法人姓名
                $("input[name='agRegAdd']").val(dataObj.address); //注册地址
                $("input[name='agBusScope']").val(dataObj.operateScope); //经营范围
                $("input[name='agBusLic']").val(dataObj.creditCode); //营业执照

                if(dataObj.regCap!='' && dataObj.regCap!=undefined){
                    if(dataObj.regCap.trim()!=''){
                        $("input[name='agCapital']").removeClass("validatebox-invalid");
                        $("input[name='agCapital']").textbox({readonly:true});
                    }
                }
                if(dataObj.openFrom!='' && dataObj.openFrom!=undefined){
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
