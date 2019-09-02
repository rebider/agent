<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="easyui-panel" title="业务信息" data-options="iconCls:'fi-results',tools:'#AgentBusi_model_tools'" id="busDiv">
    <div class="easyui-tabs" id="AgentBusi_model">
    </div>
</div>
<c:if test="${reqType=='netInBus'}">
<div id="AgentBusi_model_tools">
    <a href="javascript:void(0)" class="icon-add" style="margin-right: 50px;" onclick="addAgentBusiTable_model()"></a>
</div>
</c:if>
<div id="AgentBusi_model_templet" style="display: none;">
    <div title="业务">
        <table class="grid" id="busTable">
            <tr>
                <td width="50px">业务平台</td>
                <td>
                    <select name="busPlatform" id="busPlatform" style="width:160px;height:21px" onchange="changBus(this)" >
                        <c:forEach items="${ablePlatForm}" var="ablePlatFormItem">
                            <option value="${ablePlatFormItem.platformNum}" platformType="${ablePlatFormItem.platformType}">${ablePlatFormItem.platformName}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>业务平台编号</td>
                <td><input name="busNum" type="text" input-class="easyui-validatebox" style="width:200px;" data-options="validType:'length[1,32]'" id="busNum" onchange="busNum(this);"/></td>
                <td>类型</td>
                <td>
                    <select name="busType" style="width:200px;height:21px" id="busSelect" onchange="busSelect($(this).parent().prev().prev().find('#busNum'),this)">
                        <c:forEach items="${busType}" var="BusTypeItem">
                            <option value="${BusTypeItem.dItemvalue}"
                                    busType="${BusTypeItem.dItemnremark}">${BusTypeItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>上级代理</td>
                <td>
                    <input type="text" input-class="easyui-validatebox" id="busParent" style="width:100px;" readonly="readonly">
                    <input name="busParent" type="hidden" id="busParents"/>
                    <a href="javascript:void(0);"  onclick="showAgentSelectDialog({data:{
                        target:this,
                        urlpar:'?busPlatform='+$(this).parent().parent().find('select[name=\'busPlatform\']').val()},callBack:returnAgentSeleParent})">选择</a>
                    <a href="javascript:void(0);" onclick="del(this)" >||清除</a>
                    <shiro:hasPermission name="/agentEnter/agentQuery/AgentBusiInfoParentStracture">
                        <a href="javascript:void(0);" onclick="showSynRegionFrame({
                        target:this,
                        callBack:function(data){},
                        },'/region/busTreee?currentId='+$($(this).parent('td').find('input[name=\'busParent\']')).val(),false)">||业务结构</a>
                    </shiro:hasPermission>
                </td>
            </tr>
            <tr>
                <td>风险承担所属代理商</td>
                <td>
                    <input type="text" input-class="easyui-validatebox" id="busRiskParent" style="width:100px;"
                           readonly="readonly"/>
                    <input name="busRiskParent" type="hidden" id="busRiskParents"/>
                    <a href="javascript:void(0);" onclick="showAgentSelectDialog({data:{
                        target:this,
                        urlpar:'?busPlatform='+$(this).parent().parent().prev().find('select[name=\'busPlatform\']').val()},callBack:returnAgentSele})">选择</a>
                    <a href="javascript:void(0);" onclick="del(this)">清除</a>
                </td>

                <td>激活及返现所属代理商</td>
                <td>
                    <input type="text" input-class="easyui-validatebox" id="busActivationParent" style="width:130px;"
                           readonly="readonly"/>
                    <input name="busActivationParent" type="hidden" id="busActivationParents"/>
                    <a href="javascript:void(0);" onclick="showAgentSelectDialog({data:{
                        target:this,
                        urlpar:'?busPlatform='+$(this).parent().parent().prev().find('select[name=\'busPlatform\']').val()},callBack:returnAgentSele})">选择</a>
                    <a href="javascript:void(0);" onclick="del(this)">清除</a>
                </td>

                <td>业务区域</td>
                <td>
                    <input type="text" input-class="easyui-validatebox" id="busRegion" style="width:130px;"
                           data-options="required:true" readonly="readonly">
                    <input name="busRegion" type="hidden" id="busRegions"/>
                    <a class="xz" href="javascript:void(0);"
                       onclick="showBusRegionFrame({target:this,bufData:$(this).prev('input').val(),callBack:returnRegion,but:true})">选择</a>
                    <a class="qc" href="javascript:void(0);" onclick="del(this)" >清除</a>
                </td>

               <%-- <td>投诉及风险风控邮箱</td>
                <td><input name="busRiskEmail" type="text" input-class="easyui-validatebox" style="width:160px;"
                           data-options="required:true,validType:'Email'" id="busRiskEmail"></td>--%>
                <td>业务联系人</td>
                <td><input name="busContact" type="text" input-class="easyui-validatebox" style="width:160px;"
                           data-options="required:true,validType:['length[1,10]','CHS']" id="busContact"/></td>
            </tr>
            <tr>

                <td>业务联系电话</td>
                <td><input name="busContactMobile" type="text" input-class="easyui-validatebox" style="width:160px;"
                           data-options="required:true,validType:['length[7,12]','Mobile']" id="busContactMobile"/></td>
            <%--    <td>分润邮箱</td>
                <td><input name="busContactEmail" type="text" input-class="easyui-validatebox" style="width:160px;"
                           data-options="required:true,validType:'Email'" id="busContactEmail"></td>--%>
                <td>业务对接人</td>
                <td><input name="busContactPerson" type="text" input-class="easyui-validatebox" style="width:160px;"
                           data-options="required:true,validType:['length[1,30]','ChinaAndEng']" id="busContactPerson"></td>
                <td>是否直发</td>
                <td>
                    <select name="busSentDirectly" style="width:160px;height:21px" id="busSentDirectly">
                        <c:forEach items="${yesOrNo}" var="yesOrNoItem">
                            <option value="${yesOrNoItem.dItemvalue}">${yesOrNoItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>是否独立考核</td>
                <td>
                    <select name="busIndeAss" style="width:160px;height:21px" id="busIndeAss">
                        <c:forEach items="${yesOrNo}" var="yesOrNoItem">
                            <option value="${yesOrNoItem.dItemvalue}">${yesOrNoItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <%--<td>是否直接返现</td>
                <td>
                    <select name="busDirectCashback" style="width:160px;height:21px" id="busDirectCashback">
                        <c:forEach items="${yesOrNo}" var="yesOrNoItem">
                            <option value="${yesOrNoItem.dItemvalue}">${yesOrNoItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>--%>
            </tr>
            <tr>
                <%--<td>是否要求收据</td>
                <td>
                    <select name="cloReceipt" style="width:160px;height:21px" id="cloReceipt">
                        <c:forEach items="${yesOrNo}" var="yesOrNoItem">
                            <option value="${yesOrNoItem.dItemvalue}">${yesOrNoItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>--%>
                <td>财务编号</td>
                <td><input name="agZbh" type="text" disabled input-class="easyui-validatebox" style="width:160px;"
                           data-options="validType:'length[1,50]'" id="agZbh"/>
                </td>
                <td>使用范围</td>
                <td>
                    <select name="busUseOrgan" style="width:160px;height:21px" id="busUseOrgan">
                        <c:forEach items="${useScope}" var="useScopeItem">
                            <option value="${useScopeItem.dItemvalue}">${useScopeItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>业务范围</td>
                <td>
                    <select name="busScope" style="width:160px;height:21px" id="busScope" disabled="disabled">
                        <c:forEach items="${busScope}" var="busScopeItem">
                            <option value="${busScopeItem.dItemvalue}">${busScopeItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                    <td>平台登录账号</td>
                    <td><input name="busLoginNum" id="busLoginNum" type="text" placeholder="请输入手机号" input-class="easyui-validatebox" style="width:160px;" id="busLoginNum"></td>
            </tr>
            <%--<tr>--%>
               <%-- <td>分管协议</td>
                <td>
                    <select name="agentAssProtocol" style="width:110px;height:21px" id="agentAssProtocol" onchange="assProSelect(this)">
                        <option value="">请选择</option>
                        <c:forEach items="${ass}" var="assListItem">
                            <option value="${assListItem.id}">${assListItem.protocolDes}</option>
                        </c:forEach>
                    </select>
                </td>--%>
            <%--</tr>--%>
            <tr>
                <td class="dredgeS0Class" style="display: none">是否开通S0</td>
                <td class="dredgeS0Class" style="display: none" id="dredgeS0Class">
                    <select name="dredgeS0" style="width:160px;height:21px" id="dredgeS0">
                        <c:forEach items="${yesOrNoIsYes}" var="yesOrNoItem">
                            <option value="${yesOrNoItem.dItemvalue}">${yesOrNoItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td class="dredgeD1Class" style="display: none">是否开通D1</td>
                <td class="dredgeD1Class" style="display: none" id="dredgeD1Class">
                    <select name="dredgeD1" style="width:160px;height:21px" id="dredgeD1">
                        <c:forEach items="${yesOrNoIsYes}" var="yesOrNoItem">
                            <option value="${yesOrNoItem.dItemvalue}">${yesOrNoItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>大区</td>
                <td>
                    <input type="text" id="agDocDistrict" input-class="easyui-validatebox" style="width:60%;" data-options="required:true" readonly="readonly" value="<agent:show type="dept" busId="${userOrg.isRegion==true?userOrg.ORGPID:userOrg.ORGPPID}" />">
                    <input name="agDocDistrict" type="hidden" value="${userOrg.isRegion==true?userOrg.ORGPID:userOrg.ORGPPID}" id="agDocDistricts"/>
                    <a href="javascript:void(0);"
                       onclick="showRegionFrame({target:this,callBack:dqReturnNetInRegion},'/region/departmentTree',false)">选择</a>
                    <a href="javascript:void(0);" onclick="del(this)">清除</a>
                </td>
                <td>省区</td>
                <td>
                    <input type="text" id="agDocPro" input-class="easyui-validatebox" style="width:60%;" data-options="required:true" readonly="readonly" value="<agent:show type="dept" busId="${userOrg.ORGID}" />">
                    <input name="agDocPro" type="hidden" value="${userOrg.ORGID}" id="agDocPros"/>
                    <a href="javascript:void(0);"onclick="showRegionFrame({target:this,callBack:sqReturnNetInRegion,pid:$(this).parent().prev('td').prev('td').children('input[name=\'agDocDistrict\']').val()},'/region/departmentTree',false)">选择</a>
                    <a href="javascript:void(0);" onclick="del(this)">清除</a>
                </td>
            </tr>
        </table>
    </div>
</div>

<script>
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

    function busSelect(busNumInput,bustype) {
        var busType = $(bustype).find("option:selected").attr("busType");
        var busTypeVal = $(bustype).find("option:selected").val();//选中的值
        if (1 == busType) {
            //可编辑的
            $(busNumInput).attr("disabled", true);
        } else if (0 == busType) {
            //不可编辑的
            $(busNumInput).attr("disabled", false);
        }
        $(bustype).parent().parent().parent().find("#busPlatform").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busNum").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busParent").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busParent").nextAll().removeAttr("style");
        $(bustype).parent().parent().parent().find("#busRiskParent").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busRiskParent").nextAll().removeAttr("style");
        $(bustype).parent().parent().parent().find("#busActivationParent").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busActivationParent").nextAll().removeAttr("style");
        $(bustype).parent().parent().parent().find("#busRegion").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busRegion").nextAll().removeAttr("style");
        $(bustype).parent().parent().parent().find("#busRegion").addClass("validatebox-invalid");
//        $(bustype).parent().parent().parent().find("#busRiskEmail").attr("disabled", false);
//        $(bustype).parent().parent().parent().find("#busRiskEmail").addClass("validatebox-invalid");
        $(bustype).parent().parent().parent().find("#busContact").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busContact").addClass("validatebox-invalid");
        $(bustype).parent().parent().parent().find("#busContactMobile").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busContactMobile").addClass("validatebox-invalid");
//        $(bustype).parent().parent().parent().find("#busContactEmail").attr("disabled", false);
//        $(bustype).parent().parent().parent().find("#busContactEmail").addClass("validatebox-invalid");
        $(bustype).parent().parent().parent().find("#busContactPerson").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busContactPerson").addClass("validatebox-invalid");
        $(bustype).parent().parent().parent().find("#busSentDirectly").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busDirectCashback").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busIndeAss").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#cloInvoice").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#cloReceipt").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#cloTaxPoint").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#cloPayCompany").attr("disabled", false);
//        $(bustype).parent().parent().parent().find("#agentAssProtocol").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busUseOrgan").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busScope").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#dredgeS0Class").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busLoginNum").attr("disabled", false);
        if(busTypeVal==2 || busTypeVal==6){
            $(bustype).parent().parent().parent().find("#busParent").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busParent").nextAll().attr("style","display: none");
            $(bustype).parent().parent().parent().find("#busRiskParent").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busRiskParent").nextAll().attr("style","display: none");
            $(bustype).parent().parent().parent().find("#busActivationParent").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busActivationParent").nextAll().attr("style","display: none");
            $(bustype).parent().parent().parent().find("#busSentDirectly").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busDirectCashback").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busIndeAss").attr("disabled", true);
//            $(bustype).parent().parent().parent().find("#agentAssProtocol").attr("disabled", true);

            $(bustype).parent().parent().parent().find("#busParent").val("");
            $(bustype).parent().parent().parent().find("#busParents").attr("value","");
            $(bustype).parent().parent().parent().find("#busRiskParent").val("");
            $(bustype).parent().parent().parent().find("#busRiskParents").attr("value","");
            $(bustype).parent().parent().parent().find("#busActivationParent").val("");
            $(bustype).parent().parent().parent().find("#busActivationParents").attr("value","");
            $(bustype).parent().parent().parent().find("#busSentDirectly").val("");
            $(bustype).parent().parent().parent().find("#busDirectCashback").val("");
            $(bustype).parent().parent().parent().find("#busIndeAss").val("");
//            $(bustype).parent().parent().parent().find("#agentAssProtocol").val("");
            $(bustype).parent().parent().parent().find("#busLoginNum").val("");

        }else if(busTypeVal==3 ||busTypeVal==5){

//            $(bustype).parent().parent().parent().find("#agentAssProtocol").attr("disabled", true);
//            $(bustype).parent().parent().parent().find("#agentAssProtocol").val("");
        }else if(busTypeVal==8){
            $(bustype).parent().parent().parent().find("#busPlatform").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busPlatform").val("");

            $(bustype).parent().parent().parent().find("#busNum").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busNum").val("");

//            $(bustype).parent().parent().parent().find("#busParent").attr("disabled", true);
//            $(bustype).parent().parent().parent().find("#busParent").nextAll().attr("style","display: none");
//            $(bustype).parent().parent().parent().find("#busParent").val(" ");
//            $(bustype).parent().parent().parent().find("#busParents").attr("value"," ");

            $(bustype).parent().parent().parent().find("#busRiskParent").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busRiskParent").nextAll().attr("style","display: none");
            $(bustype).parent().parent().parent().find("#busRiskParent").val("");
            $(bustype).parent().parent().parent().find("#busRiskParents").attr("value","");

            $(bustype).parent().parent().parent().find("#busActivationParent").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busActivationParent").nextAll().attr("style","display: none");
            $(bustype).parent().parent().parent().find("#busActivationParent").val("");
            $(bustype).parent().parent().parent().find("#busActivationParents").attr("value","");

            $(bustype).parent().parent().parent().find("#busRegion").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busRegion").nextAll().attr("style","display: none");
            $(bustype).parent().parent().parent().find("#busRegion").removeClass("validatebox-invalid");
            $(bustype).parent().parent().parent().find("#busRegions").attr("value","");
            $(bustype).parent().parent().parent().find("#busRegion").val("");


//            $(bustype).parent().parent().parent().find("#busRiskEmail").attr("disabled", true);
//            $(bustype).parent().parent().parent().find("#busRiskEmail").removeClass("validatebox-invalid");
//            $(bustype).parent().parent().parent().find("#busRiskEmail").val("");

            $(bustype).parent().parent().parent().find("#busContact").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busContact").removeClass("validatebox-invalid");
            $(bustype).parent().parent().parent().find("#busContact").val("");

            $(bustype).parent().parent().parent().find("#busContactMobile").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busContactMobile").removeClass("validatebox-invalid");
            $(bustype).parent().parent().parent().find("#busContactMobile").val("");

//            $(bustype).parent().parent().parent().find("#busContactEmail").attr("disabled", true);
//            $(bustype).parent().parent().parent().find("#busContactEmail").removeClass("validatebox-invalid");
//            $(bustype).parent().parent().parent().find("#busContactEmail").val("");

            $(bustype).parent().parent().parent().find("#busContactPerson").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busContactPerson").removeClass("validatebox-invalid");
            $(bustype).parent().parent().parent().find("#busContactPerson").val("");

            $(bustype).parent().parent().parent().find("#busSentDirectly").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busSentDirectly").val("");

            $(bustype).parent().parent().parent().find("#busDirectCashback").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busDirectCashback").val("");

            $(bustype).parent().parent().parent().find("#busIndeAss").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busIndeAss").val("");

            $(bustype).parent().parent().parent().find("#cloInvoice").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#cloInvoice").val("");

            $(bustype).parent().parent().parent().find("#cloReceipt").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#cloReceipt").val("");

            $(bustype).parent().parent().parent().find("#cloTaxPoint").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#cloTaxPoint").val("");

            $(bustype).parent().parent().parent().find("#cloPayCompany").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#cloPayCompany").val("");

//            $(bustype).parent().parent().parent().find("#agentAssProtocol").attr("disabled", true);
//            $(bustype).parent().parent().parent().find("#agentAssProtocol").val("");

            $(bustype).parent().parent().parent().find("#busUseOrgan").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busUseOrgan").val("");

            $(bustype).parent().parent().parent().find("#busScope").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busScope").val("");

            $(bustype).parent().parent().parent().find("#dredgeS0Class").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#dredgeS0Class").val("");

            $(bustype).parent().parent().parent().find("#busLoginNum").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busLoginNum").val("");
        }
        if (busTypeVal=='8'){
            $(bustype).parent().parent().parent().find("#busSentDirectly").val('0');
        }else{
            $(bustype).parent().parent().parent().find("#busSentDirectly").val('1');
        }
        $(bustype).parent().parent().parent().find("#busSentDirectly").attr("disabled", true);
    }
    //代理商选择
    function returnAgentSele(data, srcData) {
        $(srcData.target).prev("input").val();
        $(srcData.target).prev("input").val(data.ID);
        $(srcData.target).prev("input").prev("input").val(data.AG_NAME);
    }

    //代理商选择
    function returnAgentSeleParent(data, srcData) {
        $.ajaxL({
            type: "POST",
            url: "/business/queryByBusId",
            dataType: 'json',
            data: {
                busId: data.ID
            },
            beforeSend: function () {
                progressLoad();
            },
            success: function (msg) {
                if(msg!=null){
                    if(msg.busType=='1' || msg.busType=='8'){
                        info("不能选择该代理商为上级代理");
                        return false;
                    }
                    if(msg.busRegion!=null){
                        $(srcData.target).parent().parent().parent().find("#busRegion").val(msg.busRegionName);
                        $(srcData.target).parent().parent().parent().find("#busRegions").val(msg.busRegion);
                        $(srcData.target).parent().parent().parent().find("#busRegion").removeClass("validatebox-text");
                        $(srcData.target).parent().parent().parent().find("#busRegion").removeClass("validatebox-invalid");

                        var codeTypes = msg.codeTypes.split(",");
                        var busRegions = msg.busRegion.split(",");
                        var busScope = "city"; //nation 国代 province省代
                        for(var i=0;i<codeTypes.length;i++){
                            if(busRegions[i]==0){
                                busScope = "nation";
                                break;
                            }
                            if(codeTypes[i]==1){
                                busScope = "province";
                                break;
                            }
                        }
                        $(srcData.target).parent().parent().parent().find("select[name='busScope']").removeAttr("disabled");
                        $(srcData.target).parent().parent().parent().find("select[name='busScope'] option[value='"+busScope+"']").prop("selected",true);
//                        $(srcData.target).parent().parent().parent().find("select[name='busScope']").attr("disabled","disabled");
                    }
                    if(msg.dredgeS0==0){
                        $(srcData.target).parent().parent().parent().find("select[name='dredgeS0']").val(0);
                        $(srcData.target).parent().parent().parent().find("select[name='dredgeS0']").attr("disabled",true);
                    }else{
                        $(srcData.target).parent().parent().parent().find("select[name='dredgeS0']").removeAttr("disabled");
                        $(srcData.target).parent().parent().parent().find("select[name='dredgeS0']").val(1);
                    }
                }else{
                    $(srcData.target).parent().parent().parent().find(".xz").css("display","");
                    $(srcData.target).parent().parent().parent().find(".qc").css("display","");
                }
            },
            complete: function (XMLHttpRequest, textStatus) {
                progressClose();
            }
        });

        $(srcData.target).prev("input").val();
        $(srcData.target).prev("input").val(data.ID);
        $(srcData.target).prev("input").prev("input").val(data.AG_NAME);

    }

    //地区选择
    function returnRegion(data, options) {
        var arrName = [];
        var arrId = [];

        for(var i=0;i<data.length;i++) {
            arrName.push(data[i].text);
            arrId.push(data[i].id);
        }
        var busScope = "city"; //nation 国代 province省代
        for(var i=0;i<data.length;i++) {
            if(data[i].id==0){
                busScope = "nation";
                break;
            }
            if(data[i].tType==1){
                busScope = "province";
                break;
            }
        }
        $(options.target).parent().parent().parent().find("select[name='busScope']").removeAttr("disabled");
        $(options.target).parent().parent().parent().find("select[name='busScope'] option[value='"+busScope+"']").prop("selected",true);
        $(options.target).parent().parent().parent().find("select[name='busScope']").attr("disabled","disabled");

        $(options.target).prev("input").val(arrId.join(","));
        $(options.target).prev("input").prev("input").val(arrName.join(","));

    }

    $(function(){
        $.parser.parse($("#busDiv"));
        addAgentBusiTable_model();
        $("select[name='busSentDirectly']").val('1');
        $("select[name='busSentDirectly']").attr("disabled", true);

        var platformType = $("#AgentBusi_model_templet").find("select[name='busPlatform']").find("option:selected").attr("platformType");
        if(platformType=='POS' ||  platformType=='ZPOS' ||  platformType=='ZHPOS' ||  platformType=='SSPOS' ||  platformType=='RJPOS') {
            $("#busTable .dredgeS0Class").removeAttr("style");
        }
        if(platformType=='RJPOS') {
            $("#busTable .dredgeD1Class").removeAttr("style");
        }
    });


    function addAgentBusiTable_model() {
        if("${reqType}"=='netInBus'){
            $("#AgentBusi_model").tabs('add', {
                title: "代理商业务",
                content: $("#AgentBusi_model_templet").html(),
                closable: true
            });
        }else{
            $("#AgentBusi_model").tabs('add', {
                title: "代理商业务",
                content: $("#AgentBusi_model_templet").html(),
            });
        }
        var inputs = $("#AgentBusi_model .grid:last input");
        for (var i = 0; i < inputs.length; i++) {
            if ($(inputs[i]).attr("input-class") && $(inputs[i]).attr("input-class").length > 0)
                $(inputs[i]).addClass($(inputs[i]).attr("input-class"));
        }
        $.parser.parse($("#AgentBusi_model .grid:last"));

    }

    //解析打个table
    function get_addAgentBusiTable_FormDataItem(table) {
        var data = {};
        data.busPlatform = $(table).find("select[name='busPlatform']").val();
        data.busNum = $(table).find("input[name='busNum']").val();
        data.busType = $(table).find("select[name='busType']").val();
        data.busParent = $(table).find("input[name='busParent']").val();
        data.busRiskParent = $(table).find("input[name='busRiskParent']").val();
        data.busActivationParent = $(table).find("input[name='busActivationParent']").val();
        data.busRegion = $(table).find("input[name='busRegion']").val();
//        data.busRiskEmail = $(table).find("input[name='busRiskEmail']").val();
        data.busContact = $(table).find("input[name='busContact']").val();
        data.busContactMobile = $(table).find("input[name='busContactMobile']").val();
//        data.busContactEmail = $(table).find("input[name='busContactEmail']").val();
        data.busContactPerson = $(table).find("input[name='busContactPerson']").val();
        data.busSentDirectly = $(table).find("select[name='busSentDirectly']").val();
        data.busDirectCashback = $(table).find("select[name='busDirectCashback']").val();
        data.busIndeAss = $(table).find("select[name='busIndeAss']").val();
        data.cloInvoice = $(table).find("select[name='cloInvoice']").val();
        data.cloReceipt = $(table).find("select[name='cloReceipt']").val();
        data.cloTaxPoint = $(table).find("input[name='cloTaxPoint']").val();
        data.cloPayCompany = $(table).find("select[name='cloPayCompany']").val();
//        data.agentAssProtocol = $(table).find("select[name='agentAssProtocol']").val();
        data.agZbh = $(table).find("input[name='agZbh']").val();
        data.busUseOrgan = $(table).find("select[name='busUseOrgan']").val();
        data.busScope = $(table).find("select[name='busScope']").val();
        data.dredgeS0 = $(table).find("select[name='dredgeS0']").val();
        data.dredgeD1 = $(table).find("select[name='dredgeD1']").val();
        data.protocolRuleValue = $(table).find("input[name='protocolRuleValue']").val();
        data.busLoginNum = $(table).find("input[name='busLoginNum']").val();
        data.busPlatformType = $(table).find("select[name='busPlatform']").find("option:checked").attr("platformtype");
        data.agDocDistrict = $(table).find("input[name='agDocDistrict']").val();
        data.agDocPro = $(table).find("input[name='agDocPro']").val();
        return data;
    }

    //获取form数据
    function get_addAgentBusiTable_FormData() {
        var addAgentBusiTable_FormDataJson = [];
        var tables = $("#AgentBusi_model .grid");
        for (var i = 0; i < tables.length; i++) {
            var table = tables[i];
            addAgentBusiTable_FormDataJson.push(get_addAgentBusiTable_FormDataItem(table));
        }
        return addAgentBusiTable_FormDataJson;
    }

    function changBus(o){
        var platformType = $(o).find("option:selected").attr("platformType");
        if(platformType=='POS' ||  platformType=='ZPOS' ||  platformType=='ZHPOS' ||  platformType=='SSPOS' ||  platformType=='RJPOS'){
            $(o).parent().parent().parent().find(".dredgeS0Class").removeAttr("style");
            if($(o).parent().parent().parent().find("#busNum").val() !="" ){
                $(o).parent().parent().parent().find("#busLoginNum").addClass("validatebox-invalid");
            }else{
                $(obj).parent().parent().parent().find("#busLoginNum").removeClass("validatebox-invalid");
            }
            if(platformType=='RJPOS'){
                $(o).parent().parent().parent().find(".dredgeD1Class").removeAttr("style");
            }else {
                $(o).parent().parent().parent().find(".dredgeD1Class").attr("style","display: none");
            }
        }else{
            if($(o).parent().parent().parent().find("#busNum").val() =="" ){
                $(o).parent().parent().parent().find("#busLoginNum").removeClass("validatebox-invalid");
            }
            $(o).parent().parent().parent().find(".dredgeS0Class").attr("style","display: none");
            $(o).parent().parent().parent().find(".dredgeD1Class").attr("style","display: none");
            $(o).parent().parent().parent().find("#busLoginNum").removeClass("validatebox-invalid");
        }
    }





    function busNum(obj){
        var platformType = $(obj).parent().parent().parent().find("option:selected").attr("platformType");

        if(platformType=='POS' ||  platformType=='ZPOS'){
            if($(obj).parent().parent().parent().find("#busNum").val() !="" ){
                $(obj).parent().parent().parent().find("#busLoginNum").addClass("validatebox-invalid");
            }else
                $(obj).parent().parent().parent().find("#busLoginNum").removeClass("validatebox-invalid");
        }else{
            if($(obj).parent().parent().parent().find("#busNum").val() =='' ){
                $(obj).parent().parent().parent().find("#busLoginNum").removeClass("validatebox-invalid");
            }
            $(obj).parent().parent().parent().find("#busLoginNum").removeClass("validatebox-invalid");
        }
    }

</script>


