<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-panel" title="代理商基本信息" data-options="iconCls:'fi-results'">
    <form id="agentBaseData">
        <table class="grid">
            <input name="id" type="hidden" value=""/>
            <tr>
                <td>代理商名称</td>
                <td style="width:180px">
                    <input name="agName" id="agentName" type="text" class="easyui-validatebox" style="width:120px;"
                           data-options="required:true" value="">
                    <a href="javascript:void(0);" onclick="industrialAuth()">工商认证</a></td>
                <input name="caStatus" id="caStatus" type="hidden" value="0">
                </td>
                <td>公司性质</td>
                <td>
                    <select name="agNature" style="width:160px;height:21px">
                        <c:forEach items="${agNatureType}" var="agnatureTypeItem">
                            <option value="${agnatureTypeItem.dItemvalue}">${agnatureTypeItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>注册资本</td>
                <td><input name="agCapital" id="agCapital" type="text" class="easyui-validatebox" style="width:120px;"
                           data-options="required:true,validType:['length[1,20]','Money']" value="">/万元</td>
                <td>营业执照</td>
                <td><input name="agBusLic" id="agBusLic" type="text" class="easyui-validatebox" style="width:120px;"
                           data-options="required:true,validType:'length[1,30]'" value=""></td>
            </tr>
            <tr>
                <td>营业执照开始时间</td>
                <td><input name="agBusLicb" id="agBusLicb" type="text" class="easyui-datebox" editable="false"
                           placeholder="请输入"
                           style="width:120px;"  ></td>
                <td>营业执照到期日</td>
                <td>
                    <input name="agBusLice" id="agBusLice" type="text" class="easyui-datebox" editable="false"
                           placeholder="请输入"
                           style="width:120px;"  editable="false">
                    <a href="javascript:void(0);" id="wxqId" onclick="setAgBusLice()">无限期</a></td>
                </td>
                <td>负责人</td>
                <td><input name="agHead" id="agHead" type="text" class="easyui-validatebox" style="width:120px;" value=""
                           data-options="required:true,validType:['length[1,6]','CHS']"></td>
                <td>负责人联系电话</td>
                <td><input name="agHeadMobile" id="agHeadMobile" type="text" class="easyui-validatebox" style="width:120px;"
                           data-options="required:true,validType:['length[7,12]','Mobile']"  value=""></td>
            </tr>
            <tr>
                <td>法人证件类型</td>
                <td>
                    <select name="agLegalCertype" style="width:160px;height:21px">
                        <c:forEach items="${certType}" var="CertTypeItem" varStatus="certTypeStatus">
                            <option value="${CertTypeItem.dItemvalue}">${CertTypeItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>法人证件号</td>
                <td><input name="agLegalCernum" id="agLegalCernum" type="text" class="easyui-validatebox" value=""
                           editable="false" placeholder="请输入" style="width:120px;" data-options="required:true,validType:['length[1,18]','IdCard']"></td>
                <td>法人姓名</td>
                <td><input name="agLegal" id="agLegal" type="text" class="easyui-validatebox" value="" style="width:120px;"
                           data-options="required:true,validType:['length[1,10]','CHS']">
                    <a href="javascript:void(0);" onclick="livenessAuth()">身份认证</a></td>
                <input id="livenessStatus" type="hidden" value="0">
                </td>
                <td>法人联系电话</td>
                <td><input name="agLegalMobile" id="agLegalMobile" type="text" class="easyui-validatebox" style="width:120px;"
                           data-options="required:true,validType:['length[7,12]','Mobile']" value=""></td>
            </tr>
            <tr>
                <td>注册地区</td>
                <td>
                    <input type="text"  input-class="easyui-validatebox" id="agRegAreaText" style="width:100px;"  data-options="required:true"  readonly="readonly">
                    <input name="agRegArea" type="hidden" id="agRegArea"/>
                    <a href="javascript:void(0);" onclick="showRegionFrame({target:this,callBack:returnAgRegRegionAdd},'/region/regionShowAreaTree',false)">选择</a>
                    <a href="javascript:void(0);" onclick="del(this)">清除</a>
                </td>
                <td>注册地址</td>
                <td colspan="6"><input name="agRegAdd" type="text" class="easyui-validatebox" value=""
                                       style="width:80%;" data-options="required:true,validType:'length[1,333]'"></td>
            </tr>
            <tr>
                <%--<td>税点</td>--%>
                <%--<td><input name="cloTaxPoint" type="text" input-class="easyui-validatebox" style="width:80%;" readonly="readonly" value="6"--%>
                <%--data-options="required:true,validType:['length[1,11]','Money']"></td>--%>
                <td>营业范围</td>
                <td><input name="agBusScope" id="agBusScope" type="text" class="easyui-validatebox" style="width:80%;" value=""
                           data-options="required:true,validType:'length[1,3000]'"></td>
                <td>业务对接大区</td>
                <td>
                    <input type="text" id="agDocDistrict" input-class="easyui-validatebox" style="width:60%;" data-options="required:true" readonly="readonly" value="<agent:show type="dept" busId="${userOrg.isRegion==true?userOrg.ORGPID:userOrg.ORGPPID}" />">
                    <input name="agDocDistrict" type="hidden" value="${userOrg.isRegion==true?userOrg.ORGPID:userOrg.ORGPPID}" id="agDocDistricts"/>
                    <a href="javascript:void(0);"
                       onclick="showRegionFrame({target:this,callBack:dqReturnNetInRegion},'/region/departmentTree',false)">选择</a>
                    <a href="javascript:void(0);" onclick="del(this)">清除</a>
                </td>
                <td>业务对接省区</td>
                <td>
                    <input type="text" id="agDocPro" input-class="easyui-validatebox" style="width:60%;" data-options="required:true" readonly="readonly" value="<agent:show type="dept" busId="${userOrg.ORGID}" />">
                    <input name="agDocPro" type="hidden" value="${userOrg.ORGID}" id="agDocPros"/>
                    <a href="javascript:void(0);"onclick="showRegionFrame({target:this,callBack:sqReturnNetInRegion,pid:$(this).parent().prev('td').prev('td').children('input[name=\'agDocDistrict\']').val()},'/region/departmentTree',false)">选择</a>
                    <a href="javascript:void(0);" onclick="del(this)">清除</a>
                </td>
                <td>投诉及风险风控对接邮箱</td>
                <td><input name="busRiskEmail" type="text" class="easyui-validatebox" style="width:160px;"
                           data-options="required:true,validType:'Email'" id="busRiskEmail"></td>
            </tr>
            <tr>
                <td>分润对接邮箱</td>
                <td><input name="busContactEmail" type="text" class="easyui-validatebox" style="width:160px;"
                           data-options="required:true,validType:'Email'" id="busContactEmail"></td>
                <td>备注</td>
                <td colspan="6"><input name="agRemark" type="text" class="easyui-validatebox" value=""
                                       style="width:80%;"  ></td>
            </tr>
        </table>
    </form>
</div>
<div class="easyui-panel" title="添加附件" data-options="iconCls:'fi-results'">
    <form id="AgentBase_AttFile_model_form">
        <table class="grid">
            <tr>
                <td class="attrInput" colspan="4">
                </td>
                <td colspan="2">

                    <a href="javascript:void(0)" class="busck-easyui-linkbutton-edit" style="cursor: pointer;"
                       data-options="plain:true,iconCls:'fi-magnifying-glass'"
                       onclick="AgentBase_AttFile_model_form_uploadView(this,ATT_DATA_TYPE_STATIC.YYZZ.key)">营业执照（营业执照扫描件）</a>

                    ||

                    <a href="javascript:void(0)" class="busck-easyui-linkbutton-edit" style="cursor: pointer;"
                       data-options="plain:true,iconCls:'fi-magnifying-glass'"
                       onclick="AgentBase_AttFile_model_form_uploadView(this,ATT_DATA_TYPE_STATIC.SFZZM.key)">法人身份证（法人身份证扫描件）</a>

                </td>
            </tr>
        </table>
    </form>
</div>
<%@ include file="/commons/agentCapital_model.jsp" %>
<%@ include file="/commons/agentContractTable_model.jsp" %>
<%@ include file="/commons/agentColinfoTable_model.jsp" %>
<%@ include file="/commons/agentBusi_model.jsp" %>
<%@ include file="/commons/validate.jsp" %>
<div style="text-align:right;padding:5px;margin-bottom: 50px;">
    <shiro:hasPermission name="/agentEnter/agentEnterIn">
        <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="saveAgentEnterIn()">保存</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="/agentEnter/saveAndaudit">
        <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="saveAndaudit()">保存并审核</a>
    </shiro:hasPermission>
</div>

<script type="text/javascript">

    function setValidateboxFalse() {
        return true;
        try{
        // 验证框
        $("#agentName,#agCapital,#agBusLic,#agHead,#agHeadMobile,#agHead,#agHeadMobile,#agLegalCernum,#agLegal,#agLegalMobile,#agRegAreaText,#agBusScope,#busRiskEmail,#busContactEmail").validatebox({
            required: false
        });
        $("#bankRegion,#busRegion,#busContact,#busContactMobile,#busContactPerson,#agDocDistrict,#agDocPro").validatebox({
            required: false
        });
        $("input[name='cAmount'],input[name='cloRealname'],input[name='cloBankAccount'],input[name='cloBank'],input[name='branchLineNum'],input[name='agRegAdd']").validatebox({
            required: false
        });
        $("input[name='contDate'],input[name='contEndDate']").datebox({
            required: false
        });
        }catch (e){
            console.log(e);
        }
    }

    //  将**项必填设置为必填
    function setValidateboxTrue() {
        return true;
        try{
            // 验证框
            $("#agentName,#agCapital,#agBusLic,#agHead,#agHeadMobile,#agHead,#agHeadMobile,#agLegalCernum,#agLegal,#agLegalMobile#agRegAreaText,#agRegAdd,#agBusScope,#busRiskEmail,#busContactEmail").validatebox({
                required: true
            });
            $("#bankRegion,#busRegion,#busContact,#busContactMobile,#busContactPerson,#agDocDistrict,#agDocPro").validatebox({
                required: true
            });
            $("input[name='cAmount'],input[name='cloRealname'],input[name='cloBankAccount'],input[name='cloBank'],input[name='branchLineNum']").validatebox({
                required: true
            });
            $("input[name='contDate'],input[name='contEndDate']").datebox({
                required: false
            });
        }catch (e){
            console.log(e);
        }
    }

    //注册地区选择
    function returnAgRegRegionAdd(data,options){
        if(data.tType!='3' && data.id!='710000' && data.id!='810000' && data.id!='820000'){
            info("注册地区必须精确到区");
            return false;
        }
        $(options.target).prev("input").val(data.id);
        $(options.target).prev("input").prev("input").val(data.text);
    }

    //地区选择
    function dqReturnNetInRegion(data,options){
        $(options.target).parent().parent().find("#agDocPro").val("");
        $(options.target).parent().parent().find("input[name='agDocPro']").val("");

        $(options.target).prev("input").val(data.id);
        $(options.target).prev("input").prev("input").val(data.text);
    }

    //地区选择
    function sqReturnNetInRegion(data,options){
        $(options.target).parent().parent().find("#agDocPro").val("");
        $(options.target).parent().parent().find("input[name='agDocPro']").val("");

        $(options.target).prev("input").val(data.id);
        $(options.target).prev("input").prev("input").val(data.text);
    }


    $(function () {
        //手动输入清除工商认证状态
        $('#agentName').bind('input propertychange', function() {
            $("#caStatus").val("0");
        });
    });

    var AgentBase_AttFile_model_form_attrDom;

    //上传窗口
    function AgentBase_AttFile_model_form_uploadView(t,type) {
        AgentBase_AttFile_model_form_attrDom = $(t).parent().parent().find(".attrInput");
        multFileUpload(AgentBase_AttFile_model_form_jxkxUploadFile,type);
    }

    //附件解析
    function AgentBase_AttFile_model_form_jxkxUploadFile(data) {
        var jsondata = eval(data);
        for (var i = 0; i < jsondata.length; i++) {
            $(AgentBase_AttFile_model_form_attrDom).append("<span onclick='removeFile(this)'>" + jsondata[i].attName + "<input type='hidden' name='agentBaseTableFile' value='" + jsondata[i].id + "' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }
    }

    function removeFile(t){
        parent.$.messager.confirm('询问', '确定删除附件么？', function(b) {
            if (b) {
                $(t).remove();
            }
        });
    }


    function saveAgentEnterIn() {

        setValidateboxFalse();  // 取消验证框必填验证

        var baseData = $.serializeObject($("#agentBaseData"));

        /*if(baseData.agRegArea=='' || baseData.agRegArea==undefined){
            info("请选择注册地区！");
            return;
        }*/
        var addAgentcapitalTable = (typeof get_addAgentcapitalTable_FormData === "function") ? get_addAgentcapitalTable_FormData() : [];
        var addAgentContractTable = (typeof get_addAgentContractTable_FormData === "function") ? get_addAgentContractTable_FormData() : [];
        var addAgentColinfoTable = (typeof get_addAgentColinfoTable_FormData === "function") ? get_addAgentColinfoTable_FormData() : [];
        var addAgentBusiTable = (typeof get_addAgentBusiTable_FormData === "function") ? get_addAgentBusiTable_FormData() : [];
        /*if(addAgentColinfoTable=='' || addAgentColinfoTable==undefined){
            info("最少添加一个收款账户！");
            return;
        }*/

        /*if(addAgentBusiTable=='' || addAgentBusiTable==undefined){
            info("最少添加一个业务信息！");
            return;
        }*/

        var attrFlag = true;
        for(var i=0;i<addAgentcapitalTable.length;i++){
            if(addAgentcapitalTable[i].cPayType=='YHHK'){
                if(addAgentcapitalTable[i].capitalTableFile==undefined || addAgentcapitalTable[i].capitalTableFile.length==0){
                    attrFlag = false;
                    break;
                }
            }
        }
        /*if(!attrFlag){
            info("请上传打款凭据");
            return false;
        }*/
        var AgentBase_AttFile_model_formFiles = $("#AgentBase_AttFile_model_form").find("input");
        var files = [];
        for (var i = 0; i < AgentBase_AttFile_model_formFiles.length; i++) {
            files.push($(AgentBase_AttFile_model_formFiles[i]).val());
        }
        var agentBase = $("#agentBaseData").form('validate');
        var agentcapital = $("#Agentcapital_model_form").form('validate');
        var agentContract = $("#AgentContract_model_form").form('validate');
        var agentColinfo = $("#AgentColinfoTable_model_form").form('validate');
        var agentBusi = $("#AgentBusi_model").form('validate');

        /*if(files.length<2){
            info("请上传营业执照扫描件和法人身份证扫描件！");
            return;
        }*/
        var cloType = addAgentColinfoTable[0].cloType;
        var cloTaxPoint = addAgentColinfoTable[0].cloTaxPoint;
        /*if (addAgentColinfoTable[0].colinfoTableFile == undefined) {
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
*/
        if (agentBase && agentcapital && agentContract && agentColinfo && agentBusi) {
            parent.$.messager.confirm('询问', '确认要添加？', function(b) {
                if (b) {
                    $.ajaxL({
                        type: "POST",
                        url: "/agentEnter/agentEnterIn",
                        dataType: 'json',
                        traditional: true,//这使json格式的字符不会被转码
                        contentType: 'application/json;charset=UTF-8',
                        data: JSON.stringify({
                            agent: baseData,
                            capitalVoList: addAgentcapitalTable,
                            contractVoList: addAgentContractTable,
                            colinfoVoList: addAgentColinfoTable,
                            busInfoVoList: addAgentBusiTable,
                            agentTableFile: files
                        }),
                        beforeSend:function(){
                            progressLoad();
                        },
                        success: function (msg) {
                            info(msg.resInfo);
                            if (msg.resCode == '1') {
                             //   $('#index_tabs').tabs('close', "代理商操作-新签代理商");
                                agnet_list_ConditionDataGrid.datagrid('load',{});
                            }
                        },
                        complete: function (XMLHttpRequest, textStatus) {
                            progressClose();
                        }
                    });
                }
            });
        } else {
            info("请输入必填项")
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
            dataType: 'json',
            success: function (data) {
                var status = data.status;
                if (status == 404) {
                    info("暂无该代理商信息");
                    return false;
                }
                if (status == 500) {
                    info("服务器异常，请联系管理员！");
                    return false;
                }
                if(status==405){
                    info(data.msg);
                }
                $("#caStatus").val("1");  //工商认证状态
                var dataObj = data.data;

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
                if (dataObj.openFrom != '' && dataObj.openFrom != undefined) {
                    $("input[name='agBusLicb']").removeClass("validatebox-invalid");
                    $("input[name='agBusLicb']").attr("disabled");
                    $("input[name='agBusLicb']").parent().find(".textbox-addon").remove();
                }
                if (dataObj.openTo != '' && dataObj.openTo != undefined) {
                    $("input[name='agBusLice']").removeClass("validatebox-invalid");
//                    $("input[name='agBusLice']").textbox({readonly:true});
                    $("input[name='agBusLice']").attr("disabled");
                    $("input[name='agBusLice']").parent().find(".textbox-addon").remove();
                    $("input[name='agBusLice']").parent().parent().find("#wxqId").remove();
                }
                if (dataObj.frName != '' && dataObj.frName != undefined) {
                    $("input[name='agLegal']").removeClass("validatebox-invalid");
                    $("input[name='agLegal']").textbox({readonly: true});
                }
                if (dataObj.address != '' && dataObj.address != undefined) {
                    $("input[name='agRegAdd']").removeClass("validatebox-invalid");
                    $("input[name='agRegAdd']").textbox({readonly: true});
                }
                if (dataObj.operateScope != '' && dataObj.operateScope != undefined) {
                    $("input[name='agBusScope']").removeClass("validatebox-invalid");
                    $("input[name='agBusScope']").textbox({readonly: true});
                }
                if (dataObj.creditCode != '' && dataObj.creditCode != undefined) {
                    $("input[name='agBusLic']").removeClass("validatebox-invalid");
                    $("input[name='agBusLic']").textbox({readonly: true});

                }
                $("input[name='agName']").textbox({readonly: true});
                $("#agentBaseData .datebox :text").attr("disabled", "true");

            },
            error: function (data) {
                info("服务器异常，请联系管理员！");
            }
        });
    }


    function saveAndaudit() {

        setValidateboxTrue();

        var agBusLicb = $("#agBusLicb").datebox("getValue");
        if (agBusLicb == '' || agBusLicb == undefined) {
            info("请选择营业执照开始日期！");
            return;
        }
        var agBusLice = $("#agBusLice").datebox("getValue");
        if (agBusLice == '' || agBusLice == undefined) {
            info("请选择营业执照到期日！");
            return;
        }

        var baseData = $.serializeObject($("#agentBaseData"));
        if (baseData.agRegArea == '' || baseData.agRegArea == undefined) {
            info("请选择注册地区！");
            return;
        }
        var addAgentcapitalTable = (typeof get_addAgentcapitalTable_FormData === "function") ? get_addAgentcapitalTable_FormData() : [];
        var addAgentContractTable = (typeof get_addAgentContractTable_FormData === "function") ? get_addAgentContractTable_FormData() : [];
        var addAgentColinfoTable = (typeof get_addAgentColinfoTable_FormData === "function") ? get_addAgentColinfoTable_FormData() : [];
        var addAgentBusiTable = (typeof get_addAgentBusiTable_FormData === "function") ? get_addAgentBusiTable_FormData() : [];
        if (addAgentColinfoTable == '' || addAgentColinfoTable == undefined) {
           info("最少添加一个收款账户！");
            return;
        }
        // 获取开户支行
        var cloBankBranch = addAgentColinfoTable[0].cloBankBranch;
        //console.log(cloBankBranch);
        if (cloBankBranch == '' || cloBankBranch == undefined) {
            info("请添加收款开户支行！");
            return;
        }
        if (addAgentBusiTable == '' || addAgentBusiTable == undefined) {
           info("最少添加一个业务信息！");
            return;
        }

        var AgentBase_AttFile_model_formFiles = $("#AgentBase_AttFile_model_form").find("input");
        var files = [];
        for (var i = 0; i < AgentBase_AttFile_model_formFiles.length; i++) {
            files.push($(AgentBase_AttFile_model_formFiles[i]).val());
        }
        var agentBase = $("#agentBaseData").form('validate');
        var agentcapital = $("#Agentcapital_model_form").form('validate');
        var agentContract = $("#AgentContract_model_form").form('validate');
        var agentColinfo = $("#AgentColinfoTable_model_form").form('validate');
        var agentBusi = $("#AgentBusi_model").form('validate');

        if (files.length < 2) {
            info("请上传营业执照扫描件和法人身份证扫描件！");
            return;
        }
        var cloType = addAgentColinfoTable[0].cloType;
        var cloTaxPoint = addAgentColinfoTable[0].cloTaxPoint;
        if (addAgentColinfoTable[0].colinfoTableFile == undefined) {
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

        if (agentBase && agentcapital && agentContract && agentColinfo && agentBusi) {
            parent.$.messager.confirm('询问', '确认要添加？', function(b) {
                if (b) {
                    $.ajaxL({
                        type: "POST",
                        url: "/agentEnter/saveAndaudit",
                        dataType: 'json',
                        traditional: true,//这使json格式的字符不会被转码
                        contentType: 'application/json;charset=UTF-8',
                        data: JSON.stringify({
                            agent: baseData,
                            capitalVoList: addAgentcapitalTable,
                            contractVoList: addAgentContractTable,
                            colinfoVoList: addAgentColinfoTable,
                            busInfoVoList: addAgentBusiTable,
                            agentTableFile: files
                        }),
                        beforeSend:function(){
                            progressLoad();
                        },
                        success: function (msg) {
                            info(msg.resInfo);
                            if (msg.resCode == '1') {
                            //    $('#index_tabs').tabs('close', "代理商操作-新签代理商");
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
        } else {
            info("请输入必填项")
        }
    }

    function setAgBusLice() {
        $("#agBusLice").datebox("setValue", "2099-12-31");
    }

    function livenessAuth() {
        var trueName = $('#agLegal').val();
        var certNo = $("#agentBaseData").find("input[name='agLegalCernum']").val();

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
                    $("#agentBaseData").find("input[name='agLegalCernum']").textbox({readonly:true});
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
