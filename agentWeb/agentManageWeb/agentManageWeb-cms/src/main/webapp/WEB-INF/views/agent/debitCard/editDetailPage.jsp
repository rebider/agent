<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>

<%@ include file="/commons/editAgentBasics_model.jsp" %>
<%@ include file="/commons/editAgentColinfoTable_model_sqxg.jsp" %>
<%--<%@ include file="/commons/editAgentColinfoTable_model_sqxg.jsp" %>--%>

<%--
<div class="easyui-panel" title="收款账户"  data-options="iconCls:'fi-results',tools:'#editAgentColinfoTable_model_tools'">
    <form id="editAgentColinfoTable_model_form">
        <c:if test="${!empty agentColinfos}">
            <c:forEach items="${agentColinfos}" var="agentColinfos">
                <table class="grid">
                    <tbody>
                    <tr >
                        <td>收款账户类型<input type="hidden" name="id" id="acId" value="${agentColinfos.id}"></td>
                        <td>
                            <select name="cloType" id="cloType111"  style="width:160px;height:21px" onchange="editAgentColinfoTable_cloTypeChange(this)">
                                <c:forEach items="${colInfoType}" var="ColInfoTypeItem"  >
                                    <option value="${ColInfoTypeItem.dItemvalue}"  <c:if test="${ColInfoTypeItem.dItemvalue==agentColinfos.cloType}">selected="selected"</c:if>>${ColInfoTypeItem.dItemname}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>收款账户名</td>
                        <td><input name="cloRealname" id="cloRealname" type="text"  class="easyui-validatebox"  style="width:160px;"  data-options="required:true,validType:['length[1,100]','CP']" value="${agentColinfos.cloRealname}"/></td>
                        <td>收款账号</td>
                        <td>
                            <input name="cloBankAccount" id="cloBankAccount" type="text"  class="easyui-validatebox"  style="width:130px;"   data-options="required:true,validType:['length[1,30]','Number']" value="${agentColinfos.cloBankAccount}" >
                            <a href="javascript:void(0);" onclick="discernCardNo(this)">识别</a>
                        </td>
                        <td>收款开户总行</td>
                        <td>
                            <input name="cloBank" id="cloBank" type="text"  class="easyui-validatebox"  style="width:160px;"  data-options="required:true,validType:['length[1,100]','CHS']" value="${agentColinfos.cloBank}" >
                            <input name="cloBankCode" id="cloBankCode" type="hidden" value="${agentColinfos.cloBankCode}">
                        </td>
                    </tr>
                    <tr>
                        <td>开户行地区</td>
                        <td>
                            <input type="text"  input-class="easyui-validatebox" id="bankRegion" style="width:130px;"  data-options="required:true"  readonly="readonly" value="<agent:show type="region" busId="${agentColinfos.bankRegion}"/>">
                            <input name="bankRegion" type="hidden" id="bankRegions" value="${agentColinfos.bankRegion}"/>
                            <a href="javascript:void(0);" onclick="showRegionFrame({target:this,callBack:returnBankRegion},'/region/regionTree',false)">选择</a>
                            <a href="javascript:void(0);" onclick="del(this)">清除</a>
                        </td>
                        <td>收款开户支行</td>
                        <td>
                            <input class="easyui-combobox" name="cloBankBranch" id="cloBankBranch"data-options="required:true,valueField:'id',textField:'text',panelHeight:'auto',panelMaxHeight:200,panelMinHeight:100"  style="width: 160px;height: 29px" value="${agentColinfos.cloBankBranch}"/>
                        </td>
                        <td>总行联行号</td>
                        <td><input name="allLineNum" id="allLineNum" type="text" maxlength="14" input-class="easyui-validatebox"  style="width:160px;"  data-options="validType:['length[1,32]','CHS']" value="${agentColinfos.allLineNum}" ></td>
                        <td>支行联行号</td>
                        <td><input name="branchLineNum" type="text"  maxlength="14" input-class="easyui-validatebox"  style="width:160px;"  data-options="required:true,validType:['length[1,32]','CHS']" value="${agentColinfos.branchLineNum}" ></td>
                    </tr>
                    <tr>
                        <td>税点</td>
                        <td>
                            <select name="cloTaxPoint" style="width:160px;height:21px"  data-options="required:true,validType:['length[1,11]','Money']"class="cloTaxPoint">
                                <option value="${agentColinfos.cloTaxPoint}">${agentColinfos.cloTaxPoint}</option>
                            </select>
                        </td>
                        <td>是否开具分润发票</td>
                        <td>
                            <select class="cloInvoice" name="cloInvoice" style="width:160px;height:21px"  >
                            </select>
                        </td>
                        <td>备注</td>
                        <td><input name="remark" type="text"  class="easyui-validatebox"  style="width:160px;"  value="${agentColinfos.remark}" ></td>
                        <td>是否有效</td>
                        <td>
                            <select name="status" style="width:160px;height:21px" >
                                <c:forEach items="${yesOrNoIsYes}" var="yesOrNoItem"  >
                                    <option value="${yesOrNoItem.dItemvalue}">${yesOrNoItem.dItemname}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>户主证件号</td>
                        <td><input name="agLegalCernum" id="agLegalCernum" type="text" class="easyui-validatebox" value="${agentColinfos.agLegalCernum}"
                                   style="width:160px;" data-options="validType:['length[1,18]','IdCard']">
                    </tr>
                    <tr>
                        <td>附件</td>
                        <td colspan="3" class="attrInput" >
                            <c:if test="${!empty agentColinfos.attachmentList}">
                                <c:forEach items="${agentColinfos.attachmentList}" var="attachment">
                                    <span  onclick='removeaddEditAgentColinfoTable_jxkxUploadFile(this)'>${attachment.attName}<input type='hidden' name='colinfoTableFile' value='${attachment.id}' /></span>
                                </c:forEach>
                            </c:if>
                        </td>
                        <td colspan="3">
                                <a href="javascript:void(0)" class="editAgentColinfoTabledAddAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addEditAgentColinfoTable_uploadView(this,ATT_DATA_TYPE_STATIC.KHXUZ.key)" >开户许可证扫描件</a>||
                                <a href="javascript:void(0)" class="editAgentColinfoTabledAddAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addEditAgentColinfoTable_uploadView(this,ATT_DATA_TYPE_STATIC.YHKSMJ.key)" >银行卡扫描件</a>||
                                <a href="javascript:void(0)" class="editAgentColinfoTabledAddAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addEditAgentColinfoTable_uploadView(this,ATT_DATA_TYPE_STATIC.YBNSRZM.key)" >一般纳税人证明</a>||
                                <a href="javascript:void(0)" class="editAgentColinfoTabledAddAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addEditAgentColinfoTable_uploadView(this,ATT_DATA_TYPE_STATIC.ZHBGB.key)" >账户变更表</a>||
                                <a href="javascript:void(0)" class="editAgentColinfoTabledAddAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addEditAgentColinfoTable_uploadView(this,ATT_DATA_TYPE_STATIC.JSRSFZ.key)" >结算人身份证</a>||
                                <a href="javascript:void(0)" class="editAgentColinfoTabledAddAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addEditAgentColinfoTable_uploadView(this,ATT_DATA_TYPE_STATIC.FFRSQS.key)" >非法人授权书</a>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </c:forEach>
        </c:if>
    </form>
</div>
--%>

<div style="text-align:right;padding:5px;margin-bottom: 50px;">
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'" onclick="saveAgentColinfoEditApy()">保存</a>
</div>

<script type="text/javascript">

    $(function () {
        //手动输入清除工商认证状态
        $('#agentName').bind('input propertychange', function() {
            $("#caStatus").val("0");
        });
        //手动输入清除身份认证状态
        $('#agLegalCernum,#agLegal').bind('input propertychange', function() {
            $("#livenessStatus").val("0");
        });


    });

    function saveAgentColinfoEditApy(){

        var caStatus = $("#caStatus").val();
        if (caStatus=='0'){
            info('工商认证未通过，请重试！');
            return ;
        }
        var livenessStatus = $('#livenessStatus').val();
        if (livenessStatus == '0'){
            info('请进行法人身份认证！');
            return ;
        }

        //验证必填项

        var isOk = basicsVerifyIntegrity();

        if (isOk){
            isOk=accountVerifyIntegrity();
            if (!isOk) return ;
        }else {
            return ;
        }

        var agent =get_editAgentBasics_FormData();
        var editAgentcapitalTable =(typeof get_editAgentcapitalTable_FormData=== "function")?get_editAgentcapitalTable_FormData():[];
        var editAgentContractTable =(typeof get_editAgentContractTable_FormData=== "function")?get_editAgentContractTable_FormData():[];
        var editAgentColinfoTable =(typeof get_editAgentColinfoTable_FormData=== "function")?get_editAgentColinfoTable_FormData():[];
        var editAgentBusiTable =(typeof get_editAgentBusiTable_FormData=== "function")?get_editAgentBusiTable_FormData():[];
        var AgentBase_AttFile_model_formFiles = (typeof get_addAttAgentcapitalTable_attrfiles=== "function")?get_addAttAgentcapitalTable_attrfiles():[];
        var cloType = editAgentColinfoTable[0].cloType;
        var cloTaxPoint = editAgentColinfoTable[0].cloTaxPoint;
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
        $.ajaxL({
            type: "POST",
            url: "/agentDebitCard/toSubmit",
            dataType:'json',
            contentType:'application/json;charset=UTF-8',
            data:JSON.stringify({
                'agent':agent,
                'colinfoVoList':editAgentColinfoTable
            }),
            success: function(msg){
                if(msg.resCode=='1'){
                    info(msg.resInfo);
                    $('#index_tabs').tabs('close',"代理商收款信息编辑"+ agent.id);
                }else{
                    info(msg.resInfo);
                }
            }
        });

    }

    function updateNoticeStatus(id) {
        $.ajaxL({
            type: "POST",
            url: "/agentDebitCard/doUpdateAdvice",
            dataType: 'json',
            data: {id: id,status: '0'},
            success: function (data) {
                console.log(data.resCode);
                if(data.resCode == '1'){

                }
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

                if (status==200){
                    var tempObj = data.data;
                    if (tempObj.isTest=='1'){   //测试环境不做认证
                        console.info('测试环境未作工商认证');
                        info('认证成功！');
                        $("#caStatus").val("1");  //工商认证状态
                        return false;
                    }
                }

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

<%--<script type="text/javascript">

    var cloBankId;
    //识别
    function discernCardNo(obj) {
        var bankCardNo =$(obj).parent("td").find("input").val();
        if(bankCardNo=='' || bankCardNo==undefined){
            info("卡号不可为空！");
            return false;
        }
        $.ajax({
            url :"${path}/etbSysCard/queryCardNo",
            type:'POST',
            data:{
                bankCardNo:bankCardNo
            },
            dataType:'json',
            success:function(result){
                if(result.msg!='' && result.msg!=undefined){
                    info(result.msg);
                    return false;
                }
                cloBankId = result.issUsers;
                $(obj).parent().parent().parent().find("input[name='cloBank']").val(result.bankName);
                $(obj).parent().parent().parent().find("input[name='cloBankCode']").val(result.issUsers);
                $(obj).parent().parent().parent().find("input[name='cloBank']").removeClass("validatebox-invalid");
            },
            error:function(data){
                info("系统异常，请联系管理员！");
            }
        });
    }

    function getData() {
        var data = {};
        data.id = $("#acId").val();
        data.cloType = document.getElementById('cloType111').value;
        data.cloRealname = $("#cloRealname").val();
        data.cloBank = $("#cloBank").val();
        data.cloBankBranch = $("#cloBankBranch").val();
        var branchInput = $("#cloBankBranch").val();
        if(branchInput=='' || branchInput==undefined){
            data.cloBankBranch = $("#cloBankBranch").val();
        }
        data.cloBankAccount = $("#cloBankAccount").val();
        data.remark = $("input[name='remark']").val();
        data.status = $("select[name='status']").val();
        data.allLineNum = $("input[name='allLineNum']").val();
        data.branchLineNum = $("input[name='branchLineNum']").val();
        data.bankRegion = $("input[name='bankRegion']").val();
        data.cloTaxPoint = $("select[name='cloTaxPoint']").val();
        data.cloInvoice = $("select[name='cloInvoice']").val();
        data.cloBankCode = $("#cloBankCode").val();
        data.agLegalCernum = $("#agLegalCernum").val();
        var files =  $(".attrInput").find("input[name='colinfoTableFile']");
        var colinfoTableFileTemp = [];
        for(var j=0;j<files.length;j++){
            colinfoTableFileTemp.push($(files[j]).val());
        }
        if(colinfoTableFileTemp.length>0)
            data.colinfoTableFile=colinfoTableFileTemp;
        return data;
    }


    //保存
    function saveAgentColinfoEditApy(){
        // 获取全部数据
        var id = $("#acId").val();
        var editAgentColinfoTable =(typeof get_editAgentColinfoTable_FormData=== "function")?get_editAgentColinfoTable_FormData():[];
        console.log(editAgentColinfoTable);
        var id = '${id}';
        var data = [getData()];
        console.info(data);

        $.ajaxL({
            type: "POST",
            url: "/agentDebitCard/toSubmit",
            dataType:'json',
            contentType:'application/json;charset=UTF-8',
            data:JSON.stringify({
                'flag':id,
                'colinfoVoList':data
            }),
            success: function(msg){
                if(msg.resCode=='1'){
                    info(msg.resInfo);
                    $('#index_tabs').tabs('close',"代理商收款信息编辑"+ id );
                }else{
                    info(msg.resInfo);
                }
            }
        });

    }
    //地区选择
    function returnBankRegion(data,options){
        $(options.target).prev("input").val(data.id);
        $(options.target).prev("input").prev("input").val(data.text);
        if(cloBankId!='' && data.id!=''){
            $('#cloBankBranch').combobox({
                prompt:'输入首关键字自动检索',
                required:false,
                url:'/etbSysCard/queryLineNumList?cloBankId='+cloBankId+"&regionId="+data.id,
                editable:true,
                hasDownArrow:true,
                filter: function(q, row){
                    var opts = $(this).combobox('options');
                    return row[opts.textField].indexOf(q) == 0;
                },
                onChange: function (n,o) {
                    var data = $(this).combobox("getData");
                    var bankBranchId = "";
                    var liqBankId = "";
                    for(var i=0;i<data.length;i++){
                        if(data[i].text==n){
                            bankBranchId = data[i].bankBranchId;
                            liqBankId = data[i].liqBankId;
                        }
                    }
                    $("input[name='allLineNum']").val(liqBankId);
                    $("input[name='branchLineNum']").val(bankBranchId);
                    $("input[name='allLineNum']").removeClass("validatebox-invalid");
                    $("input[name='branchLineNum']").removeClass("validatebox-invalid");
                }
            });
        }
    }


    /**
     * 收款账户类型变更
     * 1、如果由开票变更成不开票，需要判断是否系统中有欠票，如果有欠票，不能进行修改
     2、如果前面是对私户进行打款，那么是否开票默认为否且不可修改
     3、如果代理商前面是对私户进行打款，那么扣税点在代理商填写时默认为6%且不可修改
     4、如果代理商前面是对公户进行打款，且代理商是否开票为否，那么扣税点在代理商填写时默认为6%，且不可修改
     5、如果代理商前面是对公户进行打款，且代理商是否开票为是，那么扣税点在代理商填写时只能选择6%或3%
     */
    function editAgentColinfoTable_cloTypeChange(t) {
        var cloType = $(t).val();
        var cloInvoice = $(t).parent().parent().parent().find("select[name='cloInvoice']").val();
        var cloTaxPoint = $(t).parent().parent().parent().find("select[name='cloTaxPoint']").val();
        $(".cloTaxPoint").removeAttr("disabled");
        $("#editAgentColinfoTable_model_form").find("input[name='agLegalCernum']").removeAttr("disabled");
        $("#editAgentColinfoTable_model_templet").find("input[name='agLegalCernum']").removeAttr("disabled");
        if (cloType==2) {//对私
            $(".cloTaxPoint").empty();
            $(".cloInvoice").empty();
            $(".cloTaxPoint").append("<option value='0.07'>0.07</option>");
            $(".cloInvoice").append("<option value='0'>否</option>");
            $(".cloInvoice").attr("disabled","disabled");
            $(".cloTaxPoint").attr("disabled","disabled");
        } else if (cloType==1) {//对公
            $(".cloTaxPoint").empty();
            $(".cloInvoice").empty();
            if(cloTaxPoint=='0.03'){
                $(".cloTaxPoint").append("<option value='0.03' selected='selected'>0.03</option>");
                $(".cloTaxPoint").append("<option value='0.06' >0.06</option>");
            }
            else if(cloTaxPoint=='0.06'){
                $(".cloTaxPoint").append("<option value='0.03' >0.03</option>");
                $(".cloTaxPoint").append("<option value='0.06' selected='selected'>0.06</option>");
            }else{
                $(".cloTaxPoint").append("<option value='0.03'>0.03</option><option value='0.06'>0.06</option>");
            }
            $(".cloInvoice").append("<option value='1'>是</option>");
            $(".cloInvoice").attr("disabled", "disabled");
            $("#editAgentColinfoTable_model_form").find("input[name='agLegalCernum']").val("");
            $("#editAgentColinfoTable_model_form").find("input[name='agLegalCernum']").attr("disabled", "disabled");
            $("#editAgentColinfoTable_model_templet").find("input[name='agLegalCernum']").val("");
            $("#editAgentColinfoTable_model_templet").find("input[name='agLegalCernum']").attr("disabled", "disabled");
        }
    }


    //附件解析
    function addEditAgentColinfoTable_jxkxUploadFile(data) {
        var jsondata = eval(data);
        for(var i=0;i<jsondata.length ;i++){
            $(addEditAgentColinfoTable_attrDom).append("<span onclick='removeaddEditAgentColinfoTable_jxkxUploadFile(this)'>"+jsondata[i].attName+"<input type='hidden' name='colinfoTableFile' value='"+jsondata[i].id+"' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }

    }
    function removeaddEditAgentColinfoTable_jxkxUploadFile(t){
        parent.$.messager.confirm('询问', '确定删除附件么？', function(b) {
            if (b) {
                $(t).remove();
            }
        });
    }


    //上传窗口
    function addEditAgentColinfoTable_uploadView(t,attDataType){
        addEditAgentColinfoTable_attrDom = $(t).parent().parent().find(".attrInput");
        multFileUpload(addEditAgentColinfoTable_jxkxUploadFile,attDataType);
    }


    function removeEditAgentColinfoTable_model(t){
        $(t).parent().parent().parent().parent().remove();
    }


    //获取form数据
    function get_editAgentColinfoTable_FormData(){
        var editAgentColinfoTable_FormDataJson = [];
         var tables = $("#editAgentColinfoTable_model_form.grid");
        console.log(222222);
        console.log(tables);
        for (var  i=0;i<tables.length;i++){
            var table = tables[i];
            editAgentColinfoTable_FormDataJson.push(getData(table));
        }
        return editAgentColinfoTable_FormDataJson;
    }




</script>--%>
