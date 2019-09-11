<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="业务信息" data-options="iconCls:'fi-results',tools:'#editBusinfo_model_tools'">
    <div class="easyui-tabs" id="editBusinfo_model">
        <c:forEach items="${agentBusInfos}" var="agentBusInfos">
            <div  title="<c:forEach items="${ablePlatForm}" var="ablePlatFormItem">
                            <c:if test="${ablePlatFormItem.platformNum==agentBusInfos.busPlatform}">${ablePlatFormItem.platformName}</c:if>
                        </c:forEach>">
                <table class="grid">
                    <tr id="trId">
                        <td>
                            <select name="busPlatform" style="width:200px;height:21px">
                                <c:forEach items="${ablePlatForm}" var="ablePlatFormItem"  >
                                    <option value="${ablePlatFormItem.platformNum}" platformType="${ablePlatFormItem.platformType}" <c:if test="${ablePlatFormItem.platformNum== agentBusInfos.busPlatform}">selected="selected"</c:if> >${ablePlatFormItem.platformName}</option>
                                </c:forEach>

                            </select>
                        </td>
                    </tr>
                    <tr>
                        <input name="id" type="hidden" value="${agentBusInfos.id}"/>
                        <input type="hidden" name="id" value="${agentBusInfos.id}">
                        <shiro:hasPermission name="/businfo/busType">
                            <td>类型</td>
                            <td>
                                <select name="busType" id="busTypeSelect" style="width:200px;height:21px"
                                    <shiro:lacksPermission name="/agentEnter/agentEdit/AgentBusiInfo"> disabled="true" </shiro:lacksPermission>>
                                    <c:forEach items="${busType}" var="BusTypeItem">
                                        <option value="${BusTypeItem.dItemvalue}" busNums="${BusTypeItem.dItemnremark}"
                                                <c:if test="${BusTypeItem.dItemvalue==agentBusInfos.busType}">selected="selected"</c:if>>
                                                ${BusTypeItem.dItemname}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/businfo/agDocDistrict">
                            <td>业务对接大区</td>
                            <td>
                                <input type="text" id="agDocDistrict1" input-class="easyui-validatebox" style="width:60%;" data-options="required:true"
                                       readonly="readonly" value="<agent:show type="dept" busId="${agentBusInfos.agDocDistrict}" />">
                                <input name="agDocDistrict" type="hidden" value="${agentBusInfos.agDocDistrict}" id="agDocDistrict2"/>
                                <a href="javascript:void(0);" onclick="showRegionFrame({target:this,callBack:returnBaseRegion},'/region/departmentTree',false)">选择</a>
                                <a href="javascript:void(0);" onclick="del(this)">清除</a>
                            </td>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/businfo/agDocPro">
                            <td>业务对接省区</td>
                            <td>
                                <input type="text" id="agDocPro1" input-class="easyui-validatebox" style="width:60%;" data-options="required:true"
                                       readonly="readonly" value="<agent:show type="dept" busId="${agentBusInfos.agDocPro}" />">
                                <input name="agDocPro" type="hidden" value="${agentBusInfos.agDocPro}" id="agDocPro2"/>
                                <a href="javascript:void(0);" onclick="showRegionFrame({target:this,callBack:returnBaseRegion,pid:$(this).parent().prev('td').prev('td').children('input[name=\'agDocDistrict\']').val()},'/region/departmentTree',false)">选择</a>
                                <a href="javascript:void(0);" onclick="del(this)">清除</a>
                            </td>
                        </shiro:hasPermission>
                    </tr>
                    <tr>
                        <shiro:hasPermission name="/businfo/busContact">
                            <td>业务联系人</td>
                            <td><input name="busContact" id="busContact1" type="text"  class="easyui-validatebox"  style="width:160px;"
                                       data-options="required:true,validType:['length[1,10]','CHS']" value="${agentBusInfos.busContact}"
                                       <shiro:lacksPermission name="/agentEnter/agentEdit/AgentBusiInfo"> readonly="readonly" </shiro:lacksPermission> /></td>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/businfo/busContactMobile">
                            <td>业务联系电话</td>
                            <td><input name="busContactMobile" id="busContactMobile1" type="text"  class="easyui-validatebox"  style="width:160px;"
                                       data-options="required:true,validType:['length[7,12]','Mobile']" value="${agentBusInfos.busContactMobile}"
                                       <shiro:lacksPermission name="/agentEnter/agentEdit/AgentBusiInfo"> readonly="readonly" </shiro:lacksPermission> /></td>
                        </shiro:hasPermission>
                        <%--<shiro:hasPermission name="/businfo/busContactEmail">
                            <td>分润对接邮箱</td>
                            <td><input name="busContactEmail" type="text" id="busContactEmail1"  class="easyui-validatebox"  style="width:160px;"
                                       data-options="required:true,validType:'Email'" value="${agentBusInfos.busContactEmail}"
                                       <shiro:lacksPermission name="/agentEnter/agentEdit/AgentBusiInfo"> readonly="readonly" </shiro:lacksPermission> /></td>
                        </shiro:hasPermission>--%>

                        <td>上级代理</td>
                        <td>
                            <input  type="text" readonly="readonly" class="easyui-validatebox" id="busParent1"  style="width:100px;" value="<agent:show type="agentBusIdForAgent" busId="${agentBusInfos.busParent}" />" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentBusiInfo"> readonly="readonly" </shiro:lacksPermission>  >
                            <input name="busParent" type="hidden" value="${agentBusInfos.busParent}" id="busParent2"/>
                            <shiro:hasPermission name="/agentEnter/agentEdit/AgentBusiInfo">
                                <a href="javascript:void(0);" onclick="showAgentSelectDialog({data:{
                                target:this,
                                urlpar:'?busPlatform='+$(this).parent().parent().parent().find('select[name=\'busPlatform\']').val()},callBack:returnEditAgentSeleParent})">选择</a>
                            </shiro:hasPermission>
                            <a href="javascript:void(0);" onclick="del(this)">||清除</a>
                        </td>

                    </tr>
                    <tr>
                        <shiro:hasPermission name="/businfo/busContactPerson">
                            <td>业务对接人</td>
                            <td><input name="busContactPerson" type="text" id="busContactPerson1" class="easyui-validatebox"  style="width:160px;"
                                       data-options="required:true,validType:['length[1,30]','ChinaAndEng']" value="${agentBusInfos.busContactPerson}"
                                    <shiro:lacksPermission name="/agentEnter/agentEdit/AgentBusiInfo"> readonly="readonly" </shiro:lacksPermission> /></td>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/businfo/busLoginNum">
                            <c:if test="${agentBusInfos.busPlatform!='100013'}">
                                <td>平台登录账号</td>
                                <td><input name="busLoginNum" type="text" input-class="easyui-validatebox" value="${agentBusInfos.busLoginNum}" ></td>
                            </c:if>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/businfo/busStatus">
                            <td>业务状态</td>
                            <td >
                                <select name="busStatus" style="width:160px;height:21px" id="busStatus1">
                                    <c:forEach items="${busStatus}" var="busStatusItem">
                                        <option value="${busStatusItem.dItemvalue}"
                                                <c:if test="${busStatusItem.dItemvalue==agentBusInfos.busStatus}">selected="selected"</c:if>>
                                                ${busStatusItem.dItemname}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </shiro:hasPermission>
                    </tr>
                    <tr>
                        <shiro:hasPermission name="/businfo/editOrgNum">
                            <td>顶级机构</td>
                            <td colspan="5">
                                <select name="organNum" style="width:160px;height:21px" id="organNum">
                                    <option value="">---请选择---</option>
                                    <c:forEach items="${platOrg_list}" var="platOrglistItem">
                                        <option value="${platOrglistItem.orgId}"
                                                <c:if test="${platOrglistItem.orgId==agentBusInfos.organNum}">selected="selected"</c:if>>
                                                ${platOrglistItem.orgName}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </shiro:hasPermission>
                    </tr>
                </table>
            </div>
        </c:forEach>
    </div>
</div>
<script>
    $(function () {
        document.getElementById("trId").style.display = "none";
    });
    //地区选择
    function returnBaseRegion(data, options) {
        $(options.target).prev("input").val(data.id);
        $(options.target).prev("input").prev("input").val(data.text);
    }

    //解析打个table
    function get_editBusinfoTable_FormDataItem(table) {
        var data = {};
        data.id = $(table).find("input[name='id']").length>0?$(table).find("input[name='id']").val():"";
        data.busType = $(table).find("select[name='busType']").val();
        data.agDocDistrict = $(table).find("input[name='agDocDistrict']").val();
        data.agDocPro = $(table).find("input[name='agDocPro']").val();
        data.busParent = $(table).find("input[name='busParent']").val();
        data.busContact = $(table).find("input[name='busContact']").val();
        data.busContactMobile = $(table).find("input[name='busContactMobile']").val();
        data.busContactEmail = $(table).find("input[name='busContactEmail']").val();
        data.busContactPerson = $(table).find("input[name='busContactPerson']").val();
        data.busLoginNum = $(table).find("input[name='busLoginNum']").val();
        data.busStatus= $(table).find("select[name='busStatus']").val();
        data.organNum= $(table).find("select[name='organNum']").val();
        return data;
    }

    //获取form数据
    function get_editBusinfoTable_FormData(){
        var editBusinfoTable_FormDataJson = [];
        var tables = $("#editBusinfo_model .grid");
        for (var  i=0;i<tables.length;i++){
            var table = tables[i];
            editBusinfoTable_FormDataJson.push(get_editBusinfoTable_FormDataItem(table));
        }
        return editBusinfoTable_FormDataJson;
    }

    //代理商选择
    function returnEditAgentSeleParent(data,srcData){

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
                        $(srcData.target).parent().parent().parent().find("#busRegion1").val(msg.busRegionName);
                        $(srcData.target).parent().parent().parent().find("#busRegion2").val(msg.busRegion);
                        $(srcData.target).parent().parent().parent().find("#busRegion1").removeClass("validatebox-text");
                        $(srcData.target).parent().parent().parent().find("#busRegion1").removeClass("validatebox-invalid");

                        $(srcData.target).parent().parent().parent().find("#busRegion3").val(msg.busRegionName);
                        $(srcData.target).parent().parent().parent().find("#busRegion4").val(msg.busRegion);
                        $(srcData.target).parent().parent().parent().find("#busRegion3").removeClass("validatebox-text");
                        $(srcData.target).parent().parent().parent().find("#busRegion3").removeClass("validatebox-invalid");

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

        $(srcData.target).prev("input").val(data.ID);
        $(srcData.target).prev("input").prev("input").val(data.AG_NAME);
    }
</script>
