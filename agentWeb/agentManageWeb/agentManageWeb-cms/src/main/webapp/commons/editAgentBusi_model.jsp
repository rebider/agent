<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="easyui-panel" title="业务信息"  data-options="iconCls:'fi-results'">
    <div class="easyui-tabs" id="editAgentBusi_model">

        <c:if test="${!empty agentBusInfos}">
            <c:forEach items="${agentBusInfos}" var="agentBusInfos">
                <div title="代理商业务">
                    <table  class="grid">
                        <tr >
                            <td>业务平台<input name="id" type="hidden"   value="${agentBusInfos.id}"/></td>
                            <input name="agentId" type="hidden"   value="${agentBusInfos.agentId}"/>
                            <td>
                                <select id="busPlatform" name="busPlatform" style="width:200px;height:21px" onchange="changBus(this)" <c:if test="${appEditFlag=='1'}">disabled="true"</c:if> >
                                    <c:forEach items="${ablePlatForm}" var="ablePlatFormItem"  >
                                        <option value="${ablePlatFormItem.platformNum}" platformType="${ablePlatFormItem.platformType}" <c:if test="${ablePlatFormItem.platformNum== agentBusInfos.busPlatform}">selected="selected"</c:if> >${ablePlatFormItem.platformName}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <c:if test="${agentBusInfos.busPlatform!='100013'}">
                            <td>业务平台编号</td>
                            <td><input name="busNum" id="busNums" type="text"  class="easyui-validatebox"  style="width:200px;"  data-options="validType:'length[1,32]'" value="${agentBusInfos.busNum}"  /></td>
                            </c:if>
                            <td>类型</td>
                            <td>
                                <select name="busType" style="width:200px;height:21px" onchange="busTypeSelect($(this).parent().prev().prev().find('#busNums'),this)" id="busTypeSelect" <c:if test="${appEditFlag=='1'}">disabled="true"</c:if>>
                                    <c:forEach items="${busType}" var="BusTypeItem"  >
                                        <option value="${BusTypeItem.dItemvalue}" busNums="${BusTypeItem.dItemnremark}" <c:if test="${BusTypeItem.dItemvalue== agentBusInfos.busType}">selected="selected"</c:if>>${BusTypeItem.dItemname}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>上级代理</td>
                            <td>
                                <input  type="text" readonly="readonly" class="easyui-validatebox" id="busParent1"  style="width:100px;" value="<agent:show type="agentBusIdForAgent" busId="${agentBusInfos.busParent}" />" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentBusiInfo"> readonly="readonly" </shiro:lacksPermission>  >
                                <input name="busParent" type="hidden" value="${agentBusInfos.busParent}" id="busParent2"/>
                                <shiro:hasPermission name="/agentEnter/agentEdit/AgentBusiInfo">
                                    <a href="javascript:void(0);" onclick="showAgentSelectDialog({data:{
                                target:this,
                                urlpar:'?busPlatform='+$(this).parent().parent().find('select[name=\'busPlatform\']').val()},callBack:returnEditAgentSeleParent})">选择</a>
                                </shiro:hasPermission>
                                <a href="javascript:void(0);" onclick="del(this)">||清除</a>
                                <shiro:hasPermission name="/agentEnter/agentQuery/AgentBusiInfoParentStracture">
                                    <a href="javascript:void(0);" onclick="showSynRegionFrame({
                                    target:this,
                                    callBack:function(data){},
                                    },'/region/busTreee?currentId='+$($(this).parent('td').find('input[name=\'busParent\']')).val(),false)"> ||业务结构</a>
                                </shiro:hasPermission>
                            </td>

                        </tr>
                        <tr >
                            <td>风险承担所属代理商</td>
                            <td>
                                <input  type="text" class="easyui-validatebox" readonly="readonly" id="busRiskParent3" style="width:100px;"  data-options="required:true" value="<agent:show type="agentBusIdForAgent" busId="${agentBusInfos.busRiskParent}"/>" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentBusiInfo"> readonly="readonly" </shiro:lacksPermission> />
                                <input name="busRiskParent" type="hidden" value="${agentBusInfos.busRiskParent}" id="busRiskParent4"/>
                                <shiro:hasPermission name="/agentEnter/agentEdit/AgentBusiInfo">
                                    <a href="javascript:void(0);" onclick="showAgentSelectDialog({data:{
                                    target:this,
                                    urlpar:'?busPlatform='+$(this).parent().parent().prev().find('select[name=\'busPlatform\']').val()},callBack:returnEditAgentSele})">选择</a>
                                    <a href="javascript:void(0);" onclick="del(this)">清除</a>
                                </shiro:hasPermission>
                            </td>

                            <td>激活及返现所属代理商</td>
                            <td>
                                <input  type="text"  class="easyui-validatebox" id="busActivationParent1" readonly="readonly" style="width:130px;"  value="<agent:show type="agentBusIdForAgent" busId="${agentBusInfos.busActivationParent}"/>" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentBusiInfo"> readonly="readonly" </shiro:lacksPermission> />
                                <input name="busActivationParent" type="hidden" value="${agentBusInfos.busActivationParent}" id="busActivationParent2"/>
                                <shiro:hasPermission name="/agentEnter/agentEdit/AgentBusiInfo">
                                    <a href="javascript:void(0);" onclick="showAgentSelectDialog({data:{
                                target:this,
                                urlpar:'?busPlatform='+$(this).parent().parent().prev().find('select[name=\'busPlatform\']').val()},callBack:returnEditAgentSele})">选择</a>
                                    <a href="javascript:void(0);" onclick="del(this)">清除</a>
                                </shiro:hasPermission>
                            </td>

                            <td>业务区域</td>
                            <td>
                                <input type="text"  class="easyui-validatebox"  readonly="readonly" id="busRegion1" style="width:130px;"  data-options="required:true" value="<agent:show type="posRegion" busId="${agentBusInfos.busRegion}"/>" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentBusiInfo"> readonly="readonly" </shiro:lacksPermission> />
                                <input name="busRegion" type="hidden" value="${agentBusInfos.busRegion}" id="busRegion2"/>
                                <shiro:hasPermission name="/agentEnter/agentEdit/AgentBusiInfo">
                                    <a class="xz" href="javascript:void(0);" onclick="showBusRegionFrame({target:this,bufData:$(this).prev('input').val(),callBack:returnEditRegion,but:true})">选择</a>
                                    <a class="qc" href="javascript:void(0);" onclick="del(this)">清除</a>
                                </shiro:hasPermission>
                            </td>
                            <td>业务联系人</td>
                            <td><input name="busContact" id="busContact1" type="text"  class="easyui-validatebox"  style="width:160px;" data-options="required:true,validType:['length[1,10]','CHS']" value="${agentBusInfos.busContact}" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentBusiInfo"> readonly="readonly" </shiro:lacksPermission> /></td>
                            <%--<td>投诉及风险风控对接邮箱</td>
                            <td><input name="busRiskEmail" id="busRiskEmail1" type="text"  class="easyui-validatebox"  style="width:160px;" data-options="required:true,validType:'Email'" value="${agentBusInfos.busRiskEmail}" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentBusiInfo"> readonly="readonly" </shiro:lacksPermission> ></td>--%>
                        </tr>
                        <tr >
                            <td>业务联系电话</td>
                            <td><input name="busContactMobile" id="busContactMobile1" type="text"  class="easyui-validatebox"  style="width:160px;"  data-options="required:true,validType:['length[7,12]','Mobile']" value="${agentBusInfos.busContactMobile}" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentBusiInfo"> readonly="readonly" </shiro:lacksPermission> /></td>
                           <%-- <td>分润对接邮箱</td>
                            <td><input name="busContactEmail" type="text" id="busContactEmail1"  class="easyui-validatebox"  style="width:160px;"  data-options="required:true,validType:'Email'" value="${agentBusInfos.busContactEmail}" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentBusiInfo"> readonly="readonly" </shiro:lacksPermission> ></td>
                            --%><td>业务对接人</td>
                            <td><input name="busContactPerson" type="text" id="busContactPerson1" class="easyui-validatebox"  style="width:160px;"  data-options="required:true,validType:['length[1,30]','ChinaAndEng']" value="${agentBusInfos.busContactPerson}" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentBusiInfo"> readonly="readonly" </shiro:lacksPermission> ></td>
                            <td>是否直发</td>
                            <td>
                                <select id="busSentDirectly1" name="busSentDirectly" style="width:160px;height:21px" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentBusiInfo"> disabled="true" </shiro:lacksPermission> >
                                    <c:forEach items="${yesOrNo}" var="yesOrNoItem"  >
                                        <option value="${yesOrNoItem.dItemvalue}" <c:if test="${yesOrNoItem.dItemvalue== agentBusInfos.busSentDirectly}"> selected="selected"</c:if>>${yesOrNoItem.dItemname}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>是否独立考核</td>
                            <td>
                                <select id="busIndeAss1" name="busIndeAss" style="width:160px;height:21px"  <shiro:lacksPermission name="/agentEnter/agentEdit/AgentBusiInfo"> disabled="true" </shiro:lacksPermission> >
                                    <c:forEach items="${yesOrNo}" var="yesOrNoItem"  >
                                        <option value="${yesOrNoItem.dItemvalue}" <c:if test="${yesOrNoItem.dItemvalue== agentBusInfos.busIndeAss}">selected="selected"</c:if>>${yesOrNoItem.dItemname}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                            <td>财务编号</td>
                            <td><input name="agZbh" type="text" disabled input-class="easyui-validatebox"  style="width:160px;"  data-options="validType:'length[1,50]'" value="${agentBusInfos.agZbh}"/></td>
                            <td>使用范围</td>
                            <td>
                                <select id="busUseOrgan1" name="busUseOrgan" style="width:160px;height:21px" >
                                    <c:forEach items="${useScope}" var="useScopeItem"  >
                                        <option value="${useScopeItem.dItemvalue}" <c:if test="${useScopeItem.dItemvalue == agentBusInfos.busUseOrgan}">selected="selected"</c:if> >${useScopeItem.dItemname}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>业务范围</td>
                            <td>
                                <select id="busScope1" name="busScope" style="width:160px;height:21px" disabled="disabled">
                                    <c:forEach items="${busScope}" var="busScopeItem"  >
                                        <option value="${busScopeItem.dItemvalue}" <c:if test="${busScopeItem.dItemvalue == agentBusInfos.busScope}">selected="selected"</c:if> >${busScopeItem.dItemname}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <c:if test="${agentBusInfos.busPlatform!='100013'}">
                                <td>平台登录账号</td>
                                <td><input name="busLoginNum" type="text" input-class="easyui-validatebox" placeholder="请输入手机号" value="${agentBusInfos.busLoginNum}" ></td>
                            </c:if>
                        </tr>
                        <tr>
                            <td class="dredgeS0Class" <c:if test="${agentBusInfos.busPlatformType!='POS' && agentBusInfos.busPlatformType!='ZPOS' && agentBusInfos.busPlatformType!='ZHPOS' && agentBusInfos.busPlatformType!='SSPOS' && agentBusInfos.busPlatformType!='RJPOS'}">style="display: none"</c:if>>是否开通S0</td>
                            <td class="dredgeS0Class" <c:if test="${agentBusInfos.busPlatformType!='POS' && agentBusInfos.busPlatformType!='ZPOS' && agentBusInfos.busPlatformType!='ZHPOS' && agentBusInfos.busPlatformType!='SSPOS' && agentBusInfos.busPlatformType!='RJPOS'}">style="display: none"</c:if>>
                                <select name="dredgeS0" style="width:160px;height:21px" id="dredgeS0Class1">
                                    <c:forEach items="${yesOrNoIsYes}" var="yesOrNoItem">
                                        <option value="${yesOrNoItem.dItemvalue}" <c:if test="${yesOrNoItem.dItemvalue == agentBusInfos.dredgeS0}">selected="selected"</c:if> >${yesOrNoItem.dItemname}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td class="dredgeD1Class" <c:if test="${agentBusInfos.busPlatformType!='RJPOS'}">style="display: none"</c:if>>是否开通D1</td>
                            <td class="dredgeD1Class" <c:if test="${agentBusInfos.busPlatformType!='RJPOS'}">style="display: none"</c:if>>
                                <select name="dredgeD1" style="width:160px;height:21px" id="dredgeD1Class1">
                                    <c:forEach items="${yesOrNoIsYes}" var="yesOrNoItem">
                                        <option value="${yesOrNoItem.dItemvalue}" <c:if test="${yesOrNoItem.dItemvalue == agentBusInfos.dredgeD1}">selected="selected"</c:if> >${yesOrNoItem.dItemname}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>业务对接大区</td>
                            <td>
                                <input type="text" id="agDocDistrict1" input-class="easyui-validatebox" style="width:60%;" data-options="required:true" readonly="readonly"
                                       value="<agent:show type="dept" busId="${agentBusInfos.agDocDistrict}" />">
                                <input name="agDocDistrict" type="hidden" value="${agentBusInfos.agDocDistrict}" id="agDocDistrict2"/>
                                <a href="javascript:void(0);" onclick="showRegionFrame({target:this,callBack:returnBaseRegion},'/region/departmentTree',false)">选择</a>
                                <a href="javascript:void(0);" onclick="del(this)">清除</a>
                            </td>
                            <td>业务对接省区</td>
                            <td>
                                <input type="text" id="agDocPro1" input-class="easyui-validatebox" style="width:60%;" data-options="required:true" readonly="readonly"
                                       value="<agent:show type="dept" busId="${agentBusInfos.agDocPro}" />">
                                <input name="agDocPro" type="hidden" value="${agentBusInfos.agDocPro}" id="agDocPro2"/>
                                <a href="javascript:void(0);" onclick="showRegionFrame({target:this,callBack:returnBaseRegion,pid:$(this).parent().prev('td').prev('td').children('input[name=\'agDocDistrict\']').val()},'/region/departmentTree',false)">选择</a>
                                <a href="javascript:void(0);" onclick="del(this)">清除</a>
                            </td>
                        </tr>
                        <tr>
                            <td>业务状态</td>
                            <td>
                                <select name="busStatus" style="width:160px;height:21px"  id="busStatus1">
                                    <c:forEach items="${busStatus}" var="busStatusItem"  >
                                        <option value="${busStatusItem.dItemvalue}" <c:if test="${busStatusItem.dItemvalue == agentBusInfos.busStatus}">selected="selected"</c:if> >${busStatusItem.dItemname}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>是否有效</td>
                            <td>
                                <select name="status" style="width:160px;height:21px" id="status1">
                                    <c:forEach items="${yesOrNoIsYes}" var="yesOrNoItem"  >
                                        <option value="${yesOrNoItem.dItemvalue}">${yesOrNoItem.dItemname}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                    </table>
                </div>
            </c:forEach>
        </c:if>

    </div>
</div>
<shiro:hasPermission name="/agentEnter/agentEdit/AgentBusiInfo">
    <c:if test="${appEditFlag!='1'}">
    <div id="editAgentBusi_model_tools">
        <a href="javascript:void(0)" class="icon-add" style="margin-right: 50px;" onclick="editAgentBusiTable_model()"></a>
    </div>
    </c:if>
</shiro:hasPermission>
<%--<div id="editAgentBusi_model_templet" style="display: none;">--%>
    <%--<div title="业务">--%>
        <%--<table  class="grid">--%>
            <%--<tr >--%>
                <%--<td>业务平台</td>--%>
                <%--<td>--%>
                    <%--<select name="busPlatform" style="width:200px;height:21px" onchange="changBus(this)" id="busPlatform2">--%>
                        <%--<c:forEach items="${ablePlatForm}" var="ablePlatFormItem"  >--%>
                            <%--<option value="${ablePlatFormItem.platformNum}" platformType="${ablePlatFormItem.platformType}">${ablePlatFormItem.platformName}</option>--%>
                        <%--</c:forEach>--%>
                    <%--</select>--%>
                <%--</td>--%>
                <%--<td>业务平台编号</td>--%>
                <%--<td><input name="busNum"id="busNum" type="text"  input-class="easyui-validatebox"  style="width:200px;"  data-options="validType:'length[1,32]'"/></td>--%>
                <%--<td>类型</td>--%>
                <%--<td>--%>
                    <%--<select name="busType" style="width:200px;height:21px"  onchange="busNum($(this).parent().prev().prev().find('#busNum'),this)" id="busType">--%>
                        <%--<c:forEach items="${busType}" var="BusTypeItem"  >--%>
                            <%--<option value="${BusTypeItem.dItemvalue}" busNum="${BusTypeItem.dItemnremark}">${BusTypeItem.dItemname}</option>--%>
                        <%--</c:forEach>--%>
                    <%--</select>--%>
                <%--</td>--%>
                <%--<td>上级代理</td>--%>
                <%--<td>--%>
                    <%--<input  type="text" readonly="readonly" id="busParent3" input-class="easyui-validatebox"  style="width:100px;"  >--%>
                    <%--<input name="busParent" type="hidden" id="busParent4"/>--%>
                    <%--<a href="javascript:void(0);" onclick="showAgentSelectDialog({data:{--%>
                        <%--target:this,--%>
                        <%--urlpar:'?busPlatform='+$(this).parent().parent().find('select[name=\'busPlatform\']').val()},callBack:returnEditAgentSeleParent})">选择</a>--%>
                    <%--<a href="javascript:void(0);" onclick="del(this)">||清除</a>--%>
                    <%--<shiro:hasPermission name="/agentEnter/agentQuery/AgentBusiInfoParentStracture">--%>
                        <%--<a href="javascript:void(0);" onclick="showSynRegionFrame({--%>
                        <%--target:this,--%>
                        <%--callBack:function(data){},--%>
                        <%--},'/region/busTreee?currentId='+$($(this).parent('td').find('input[name=\'busParent\']')).val(),false)">||业务结构</a>--%>
                    <%--</shiro:hasPermission>--%>
                <%--</td>--%>

            <%--</tr>--%>
            <%--<tr >--%>
                <%--<td>风险承担所属代理商</td>--%>
                <%--<td>--%>
                    <%--<input  type="text" readonly="readonly" input-class="easyui-validatebox" id="busRiskParent1"  style="width:100px;" />--%>
                    <%--<input name="busRiskParent" type="hidden" id="busRiskParent2"/>--%>
                    <%--<a href="javascript:void(0);" onclick="showAgentSelectDialog({data:{--%>
                        <%--target:this,--%>
                        <%--urlpar:'?busPlatform='+$(this).parent().parent().prev().find('select[name=\'busPlatform\']').val()},callBack:returnEditAgentSele})">选择</a>--%>
                    <%--<a href="javascript:void(0);" onclick="del(this)">清除</a>--%>
                <%--</td>--%>
                <%--<td>激活及返现所属代理商</td>--%>
                <%--<td>--%>
                    <%--<input  type="text" readonly="readonly" input-class="easyui-validatebox" id="busActivationParent3" style="width:130px;"  />--%>
                    <%--<input name="busActivationParent" type="hidden" id="busActivationParent4"/>--%>
                    <%--<a href="javascript:void(0);" onclick="showAgentSelectDialog({data:{--%>
                        <%--target:this,--%>
                        <%--urlpar:'?busPlatform='+$(this).parent().parent().prev().find('select[name=\'busPlatform\']').val()},callBack:returnEditAgentSele})">选择</a>--%>
                    <%--<a href="javascript:void(0);" onclick="del(this)">清除</a>--%>
                <%--</td>--%>
                <%--<td>业务区域</td>--%>
                <%--<td>--%>
                    <%--<input type="text" readonly="readonly" input-class="easyui-validatebox" id="busRegion3" style="width:130px;"  data-options="required:true">--%>
                    <%--<input name="busRegion" type="hidden" id="busRegion4"/>--%>
                    <%--<a class="xz" href="javascript:void(0);" onclick="showBusRegionFrame({target:this,bufData:$(this).prev('input').val(),callBack:returnEditRegion,but:true})">选择</a>--%>
                    <%--<a class="qc" href="javascript:void(0);" onclick="del(this)">清除</a>--%>
                <%--</td>--%>
              <%--&lt;%&ndash;  <td>投诉及风险风控对接邮箱</td>--%>
                <%--<td><input id="busRiskEmail2" name="busRiskEmail" type="text"  input-class="easyui-validatebox"  style="width:160px;"  data-options="required:true,validType:'Email'"></td>&ndash;%&gt;--%>
            <%--</tr>--%>
            <%--<tr >--%>
                <%--<td>业务联系人</td>--%>
                <%--<td><input id="busContact2" name="busContact" type="text"  input-class="easyui-validatebox"  style="width:160px;"  data-options="required:true,validType:['length[1,3]','CHS']"/></td>--%>
                <%--<td>业务联系电话</td>--%>
                <%--<td><input id="busContactMobile2" name="busContactMobile" type="text"  input-class="easyui-validatebox"  style="width:160px;"  data-options="required:true,validType:['length[7,12]','Mobile']"/></td>--%>
                <%--&lt;%&ndash;<td>分润对接邮箱</td>--%>
                <%--<td><input id="busContactEmail2" name="busContactEmail" type="text"  input-class="easyui-validatebox"  style="width:160px;" data-options="required:true,validType:'Email'"></td>&ndash;%&gt;--%>
                <%--<td>业务对接人</td>--%>
                <%--<td><input id="busContactPerson2" name="busContactPerson" type="text"  input-class="easyui-validatebox"  style="width:160px;"   data-options="required:true,validType:['length[1,30]','ChinaAndEng']"></td>--%>
            <%--</tr>--%>
            <%--<tr >--%>
                <%--<td>是否直发</td>--%>
                <%--<td>--%>
                    <%--<select name="busSentDirectly" style="width:160px;height:21px" id="busSentDirectly2">--%>
                        <%--<c:forEach items="${yesOrNo}" var="yesOrNoItem"  >--%>
                            <%--<option value="${yesOrNoItem.dItemvalue}">${yesOrNoItem.dItemname}</option>--%>
                        <%--</c:forEach>--%>
                    <%--</select>--%>
                <%--</td>--%>
                <%--<td>是否直接返现</td>--%>
                <%--<td>--%>
                    <%--<select name="busDirectCashback" style="width:160px;height:21px" id="busDirectCashback2" >--%>
                        <%--<c:forEach items="${yesOrNo}" var="yesOrNoItem"  >--%>
                            <%--<option value="${yesOrNoItem.dItemvalue}">${yesOrNoItem.dItemname}</option>--%>
                        <%--</c:forEach>--%>
                    <%--</select>--%>
                <%--</td>--%>
                <%--<td>是否独立考核</td>--%>
                <%--<td>--%>
                    <%--<select name="busIndeAss" style="width:160px;height:21px" id="busIndeAss2">--%>
                        <%--<c:forEach items="${yesOrNo}" var="yesOrNoItem"  >--%>
                            <%--<option value="${yesOrNoItem.dItemvalue}">${yesOrNoItem.dItemname}</option>--%>
                        <%--</c:forEach>--%>
                    <%--</select>--%>
                <%--</td>--%>
                <%--<td>是否要求收据</td>--%>
                <%--<td>--%>
                    <%--<select name="cloReceipt" style="width:160px;height:21px" id="cloReceipt2">--%>
                        <%--<c:forEach items="${yesOrNo}" var="yesOrNoItem"  >--%>
                            <%--<option value="${yesOrNoItem.dItemvalue}">${yesOrNoItem.dItemname}</option>--%>
                        <%--</c:forEach>--%>
                    <%--</select>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr >--%>
               <%--&lt;%&ndash; <td>分管协议</td>--%>
                <%--<td>--%>
                    <%--<select name="agentAssProtocol" style="width:110px;height:21px" id="agentAssProtocol2" onchange="assProSelect(this)">--%>
                        <%--<option value="">请选择</option>--%>
                        <%--<c:forEach items="${ass}" var="assListItem"  >--%>
                            <%--<option value="${assListItem.id}">${assListItem.protocolDes}</option>--%>
                        <%--</c:forEach>--%>
                    <%--</select>--%>
                <%--</td>&ndash;%&gt;--%>
                <%--<td>财务编号</td>--%>
                <%--<td><input name="agZbh" type="text" disabled input-class="easyui-validatebox"  style="width:160px;"  data-options="validType:'length[1,50]'" /></td>--%>
                <%--<td>使用范围</td>--%>
                <%--<td>--%>
                    <%--<select name="busUseOrgan" style="width:160px;height:21px" id="busUseOrgan2">--%>
                        <%--<c:forEach items="${useScope}" var="useScopeItem"  >--%>
                            <%--<option value="${useScopeItem.dItemvalue}">${useScopeItem.dItemname}</option>--%>
                        <%--</c:forEach>--%>
                    <%--</select>--%>
                <%--</td>--%>
                <%--<td>业务范围</td>--%>
                <%--<td>--%>
                    <%--<select name="busScope" style="width:160px;height:21px" id="busScope2" disabled="disabled">--%>
                        <%--<c:forEach items="${busScope}" var="busScopeItem"  >--%>
                            <%--<option value="${busScopeItem.dItemvalue}">${busScopeItem.dItemname}</option>--%>
                        <%--</c:forEach>--%>
                    <%--</select>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td class="dredgeS0Class" style="display: none">是否开通S0</td>--%>
                <%--<td class="dredgeS0Class" style="display: none">--%>
                    <%--<select name="dredgeS0" style="width:160px;height:21px" id="dredgeS0Class2">--%>
                        <%--<c:forEach items="${yesOrNoIsYes}" var="yesOrNoItem">--%>
                            <%--<option value="${yesOrNoItem.dItemvalue}">${yesOrNoItem.dItemname}</option>--%>
                        <%--</c:forEach>--%>
                    <%--</select>--%>
                <%--</td>--%>
                <%--<td class="dredgeD1Class" style="display: none">是否开通S0</td>--%>
                <%--<td class="dredgeD1Class" style="display: none">--%>
                    <%--<select name="dredgeD1" style="width:160px;height:21px" id="dredgeD1Class2">--%>
                        <%--<c:forEach items="${yesOrNoIsYes}" var="yesOrNoItem">--%>
                            <%--<option value="${yesOrNoItem.dItemvalue}">${yesOrNoItem.dItemname}</option>--%>
                        <%--</c:forEach>--%>
                    <%--</select>--%>
                <%--</td>--%>
                <%--<td>业务状态</td>--%>
                <%--<td>--%>
                    <%--<select name="busStatus" style="width:160px;height:21px" id="busStatus2">--%>
                        <%--<c:forEach items="${busStatus}" var="busStatusItem"  >--%>
                            <%--<option value="${busStatusItem.dItemvalue}">${busStatusItem.dItemname}</option>--%>
                        <%--</c:forEach>--%>
                    <%--</select>--%>
                <%--</td>--%>
                <%--<td>平台登录账号</td>--%>
                <%--<td><input name="busLoginNum" type="text" placeholder="请输入手机号" input-class="easyui-validatebox" style="width:160px;" ></td>--%>
                <%--<td>是否有效</td>--%>
                <%--<td>--%>
                    <%--<select name="status" style="width:160px;height:21px" id="status2">--%>
                        <%--<c:forEach items="${yesOrNoIsYes}" var="yesOrNoItem"  >--%>
                            <%--<option value="${yesOrNoItem.dItemvalue}">${yesOrNoItem.dItemname}</option>--%>
                        <%--</c:forEach>--%>
                    <%--</select>--%>
                <%--</td>--%>
            <%--</tr>--%>
        <%--</table>--%>
    <%--</div>--%>
<%--</div>--%>

<script>

    //地区选择
    function returnBaseRegion(data,options){
        $(options.target).prev("input").val(data.id);
        $(options.target).prev("input").prev("input").val(data.text);
    }

    function busNum(busInput,value) {
        var busType = $(value).find("option:selected").attr("busNum");
        if (1 == busType) {
            //可编辑的
            $(busInput).attr("disabled", true);
        } else if (0 == busType) {
            //不可编辑的
            $(busInput).attr("disabled", false);
        }

        var busVal = $(value).find("option:selected").val();//选中的值
        $(value).parent().parent().parent().find("#busPlatform2").attr("disabled", false);
        $(value).parent().parent().parent().find("#busNum").attr("disabled", false);
        $(value).parent().parent().parent().find("#busParent3").attr("disabled", false);
        $(value).parent().parent().parent().find("#busParent3").nextAll().removeAttr("style");
        $(value).parent().parent().parent().find("#busParent3").addClass("validatebox-invalid");

        $(value).parent().parent().parent().find("#busRiskParent1").attr("disabled", false);
        $(value).parent().parent().parent().find("#busRiskParent1").nextAll().removeAttr("style");
        $(value).parent().parent().parent().find("#busRiskParent1").addClass("validatebox-invalid");

        $(value).parent().parent().parent().find("#busActivationParent3").attr("disabled", false);
        $(value).parent().parent().parent().find("#busActivationParent3").nextAll().removeAttr("style");
        $(value).parent().parent().parent().find("#busActivationParent3").addClass("validatebox-invalid");

        $(value).parent().parent().parent().find("#busRegion3").attr("disabled", false);
        $(value).parent().parent().parent().find("#busRegion3").nextAll().removeAttr("style");
        $(value).parent().parent().parent().find("#busRegion3").addClass("validatebox-invalid");
        $(value).parent().parent().parent().find("#busRiskEmail2").attr("disabled", false);
        $(value).parent().parent().parent().find("#busRiskEmail2").addClass("validatebox-invalid");
        $(value).parent().parent().parent().find("#busContact2").attr("disabled", false);
        $(value).parent().parent().parent().find("#busContact2").addClass("validatebox-invalid");
        $(value).parent().parent().parent().find("#busContactMobile2").attr("disabled", false);
        $(value).parent().parent().parent().find("#busContactMobile2").addClass("validatebox-invalid");
        $(value).parent().parent().parent().find("#busContactEmail2").attr("disabled", false);
        $(value).parent().parent().parent().find("#busContactEmail2").addClass("validatebox-invalid");
        $(value).parent().parent().parent().find("#busContactPerson2").attr("disabled", false);
        $(value).parent().parent().parent().find("#busContactPerson2").addClass("validatebox-invalid");
        $(value).parent().parent().parent().find("#busSentDirectly2").attr("disabled", false);
        $(value).parent().parent().parent().find("#busDirectCashback2").attr("disabled", false);
        $(value).parent().parent().parent().find("#busIndeAss2").attr("disabled", false);
        $(value).parent().parent().parent().find("#cloInvoice2").attr("disabled", false);
        $(value).parent().parent().parent().find("#cloReceipt2").attr("disabled", false);
        $(value).parent().parent().parent().find("#cloTaxPoint2").attr("disabled", false);
        $(value).parent().parent().parent().find("#cloPayCompany2").attr("disabled", false);
        $(value).parent().parent().parent().find("#agentAssProtocol2").attr("disabled", false);
        $(value).parent().parent().parent().find("#busUseOrgan2").attr("disabled", false);
        $(value).parent().parent().parent().find("#busScope2").attr("disabled", false);
        $(value).parent().parent().parent().find("#dredgeS0Class2").attr("disabled", false);
        $(value).parent().parent().parent().find("#busStatus2").attr("disabled", false);
        $(value).parent().parent().parent().find("#status2").attr("disabled", false);
        $(value).parent().parent().parent().find("#busRegion3").removeClass("validatebox-invalid");
        $(value).parent().parent().parent().find("#busRiskEmail2").removeClass("validatebox-invalid");
        $(value).parent().parent().parent().find("#busContact2").removeClass("validatebox-invalid");
        $(value).parent().parent().parent().find("#busContactMobile2").removeClass("validatebox-invalid");
        $(value).parent().parent().parent().find("#busContactEmail2").removeClass("validatebox-invalid");
        $(value).parent().parent().parent().find("#busContactPerson2").removeClass("validatebox-invalid");

        if($(value).parent().parent().parent().find("#busRegion3").val()==''){
            $(value).parent().parent().parent().find("#busRegion3").addClass("validatebox-invalid");
        }
        if($(value).parent().parent().parent().find("#busContact2").val()==''){
            $(value).parent().parent().parent().find("#busContact2").addClass("validatebox-invalid");
        }
        if($(value).parent().parent().parent().find("#busContactMobile2").val()==''){
            $(value).parent().parent().parent().find("#busContactMobile2").addClass("validatebox-invalid");
        }
        if($(value).parent().parent().parent().find("#busContactPerson2").val()==''){
            $(value).parent().parent().parent().find("#busContactPerson2").addClass("validatebox-invalid");
        }

        if(busVal==2 || busVal==6){
            $(value).parent().parent().parent().find("#busParent3").attr("disabled", true);
            $(value).parent().parent().parent().find("#busParent3").nextAll().attr("style","display: none");
            $(value).parent().parent().parent().find("#busParent3").removeClass("validatebox-invalid");
            $(value).parent().parent().parent().find("#busRiskParent1").attr("disabled", true);
            $(value).parent().parent().parent().find("#busRiskParent1").nextAll().attr("style","display: none");
            $(value).parent().parent().parent().find("#busRiskParent1").removeClass("validatebox-invalid");
            $(value).parent().parent().parent().find("#busActivationParent3").attr("disabled", true);
            $(value).parent().parent().parent().find("#busActivationParent3").nextAll().attr("style","display: none");
            $(value).parent().parent().parent().find("#busActivationParent3").removeClass("validatebox-invalid");
            $(value).parent().parent().parent().find("#busSentDirectly2").attr("disabled", true);
            $(value).parent().parent().parent().find("#busDirectCashback2").attr("disabled", true);
            $(value).parent().parent().parent().find("#busIndeAss2").attr("disabled", true);
            $(value).parent().parent().parent().find("#agentAssProtocol2").attr("disabled", true);

            $(value).parent().parent().parent().find("#busParent3").val("");
            $(value).parent().parent().parent().find("#busParent4").attr("value","");
            $(value).parent().parent().parent().find("#busRiskParent1").val("");
            $(value).parent().parent().parent().find("#busRiskParent2").attr("value","");
            $(value).parent().parent().parent().find("#busActivationParent3").val("");
            $(value).parent().parent().parent().find("#busActivationParent4").attr("value","");
            $(value).parent().parent().parent().find("#busSentDirectly2").val("");
            $(value).parent().parent().parent().find("#busDirectCashback2").val("");
            $(value).parent().parent().parent().find("#busIndeAss2").val("");
            $(value).parent().parent().parent().find("#agentAssProtocol2").val("");




        }else if(busVal==3 ||busVal==5){

            $(value).parent().parent().parent().find("#agentAssProtocol2").attr("disabled", true);
            $(value).parent().parent().parent().find("#agentAssProtocol2").val("");

        }else if(busVal==8){
            $(value).parent().parent().parent().find("#busPlatform2").attr("disabled", true);
            $(value).parent().parent().parent().find("#busPlatform2").val("");

            $(value).parent().parent().parent().find("#busNum").attr("disabled", true);
            $(value).parent().parent().parent().find("#busNum").val("");

            $(value).parent().parent().parent().find("#busParent3").attr("disabled", true);
            $(value).parent().parent().parent().find("#busParent3").nextAll().attr("style","display: none");
            $(value).parent().parent().parent().find("#busParent3").removeClass("validatebox-invalid");
            $(value).parent().parent().parent().find("#busParent3").val("");
            $(value).parent().parent().parent().find("#busParent4").attr("value","");



            $(value).parent().parent().parent().find("#busRiskParent1").attr("disabled", true);
            $(value).parent().parent().parent().find("#busRiskParent1").nextAll().attr("style","display: none");
            $(value).parent().parent().parent().find("#busRiskParent1").removeClass("validatebox-invalid");
            $(value).parent().parent().parent().find("#busRiskParent1").val("");
            $(value).parent().parent().parent().find("#busRiskParent2").attr("value","");

            $(value).parent().parent().parent().find("#busActivationParent3").attr("disabled", true);
            $(value).parent().parent().parent().find("#busActivationParent3").nextAll().attr("style","display: none");
            $(value).parent().parent().parent().find("#busActivationParent3").removeClass("validatebox-invalid");
            $(value).parent().parent().parent().find("#busActivationParent3").val("");
            $(value).parent().parent().parent().find("#busActivationParent4").attr("value","");

            $(value).parent().parent().parent().find("#busRegion3").attr("disabled", true);
            $(value).parent().parent().parent().find("#busRegion3").nextAll().attr("style","display: none");
            $(value).parent().parent().parent().find("#busRegion3").removeClass("validatebox-invalid");
            $(value).parent().parent().parent().find("#busRegion3").val("");
            $(value).parent().parent().parent().find("#busRegion4").attr("value","");

            $(value).parent().parent().parent().find("#busRiskEmail2").attr("disabled", true);
            $(value).parent().parent().parent().find("#busRiskEmail2").removeClass("validatebox-invalid");
            $(value).parent().parent().parent().find("#busRiskEmail2").val("");

            $(value).parent().parent().parent().find("#busContact2").attr("disabled", true);
            $(value).parent().parent().parent().find("#busContact2").removeClass("validatebox-invalid");
            $(value).parent().parent().parent().find("#busContact2").val("");

            $(value).parent().parent().parent().find("#busContactMobile2").attr("disabled", true);
            $(value).parent().parent().parent().find("#busContactMobile2").removeClass("validatebox-invalid");
            $(value).parent().parent().parent().find("#busContactMobile2").val(" ");

            $(value).parent().parent().parent().find("#busContactEmail2").attr("disabled", true);
            $(value).parent().parent().parent().find("#busContactEmail2").removeClass("validatebox-invalid");
            $(value).parent().parent().parent().find("#busContactEmail2").val("");

            $(value).parent().parent().parent().find("#busContactPerson2").attr("disabled", true);
            $(value).parent().parent().parent().find("#busContactPerson2").removeClass("validatebox-invalid");
            $(value).parent().parent().parent().find("#busContactPerson2").val("");

            $(value).parent().parent().parent().find("#busSentDirectly2").attr("disabled", true);
            $(value).parent().parent().parent().find("#busSentDirectly2").val("");

            $(value).parent().parent().parent().find("#busDirectCashback2").attr("disabled", true);
            $(value).parent().parent().parent().find("#busDirectCashback2").val("");

            $(value).parent().parent().parent().find("#busIndeAss2").attr("disabled", true);
            $(value).parent().parent().parent().find("#busIndeAss2").val("");

            $(value).parent().parent().parent().find("#cloInvoice2").attr("disabled", true);
            $(value).parent().parent().parent().find("#cloInvoice2").val("");

            $(value).parent().parent().parent().find("#cloReceipt2").attr("disabled", true);
            $(value).parent().parent().parent().find("#cloReceipt2").val("");

            $(value).parent().parent().parent().find("#cloTaxPoint2").attr("disabled", true);
            $(value).parent().parent().parent().find("#cloTaxPoint2").val("");

            $(value).parent().parent().parent().find("#cloPayCompany2").attr("disabled", true);
            $(value).parent().parent().parent().find("#cloPayCompany2").val("");

            $(value).parent().parent().parent().find("#agentAssProtocol2").attr("disabled", true);
            $(value).parent().parent().parent().find("#agentAssProtocol2").val("");

            $(value).parent().parent().parent().find("#busUseOrgan2").attr("disabled", true);
            $(value).parent().parent().parent().find("#busUseOrgan2").val("");

            $(value).parent().parent().parent().find("#busScope2").attr("disabled", true);
            $(value).parent().parent().parent().find("#busScope2").val("");

            $(value).parent().parent().parent().find("#dredgeS0Class2").attr("disabled", true);
            $(value).parent().parent().parent().find("#dredgeS0Class2").val("");

            $(value).parent().parent().parent().find("#busStatus2").attr("disabled", true);
            $(value).parent().parent().parent().find("#busStatus2").val("");

            $(value).parent().parent().parent().find("#status2").attr("disabled", true);
            $(value).parent().parent().parent().find("#status2").val("");
        }

    }
    function busTypeSelect(input,bustype) {
        var busType = $(bustype).find("option:selected").attr("busNums");
        if (1 == busType) {
            //可编辑的
            $(input).attr("disabled", true);
        } else if (0 == busType) {
            //不可编辑的
            $(input).attr("disabled", false);
        }
        var busVal = $(bustype).find("option:selected").val();//选中的值
        $(bustype).parent().parent().parent().find("#busPlatform").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busNums").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busParent1").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busParent1").nextAll().removeAttr("style");
        $(bustype).parent().parent().parent().find("#busRiskParent3").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busRiskParent3").nextAll().removeAttr("style");
        $(bustype).parent().parent().parent().find("#busActivationParent1").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busActivationParent1").nextAll().removeAttr("style");
        $(bustype).parent().parent().parent().find("#busRegion1").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busRegion1").nextAll().removeAttr("style");
        $(bustype).parent().parent().parent().find("#busRegion1").addClass("validatebox-invalid");
        $(bustype).parent().parent().parent().find("#busRiskEmail").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busRiskEmail").addClass("validatebox-invalid");
        $(bustype).parent().parent().parent().find("#busContact1").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busContact1").addClass("validatebox-invalid");
        $(bustype).parent().parent().parent().find("#busContactMobile1").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busContactMobile1").addClass("validatebox-invalid");
        $(bustype).parent().parent().parent().find("#busContactEmail1").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busContactEmail1").addClass("validatebox-invalid");
        $(bustype).parent().parent().parent().find("#busContactPerson1").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busContactPerson1").addClass("validatebox-invalid");
        $(bustype).parent().parent().parent().find("#busSentDirectly1").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busDirectCashback1").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busIndeAss1").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#cloInvoice1").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#cloReceipt1").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#cloTaxPoint1").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#cloPayCompany1").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#agentAssProtocol1").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busUseOrgan1").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busScope1").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#dredgeS0Class1").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busStatus1").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#status1").attr("disabled", false);
        $(bustype).parent().parent().parent().find("#busRegion1").removeClass("validatebox-invalid");
        $(bustype).parent().parent().parent().find("#busRiskEmail1").removeClass("validatebox-invalid");
        $(bustype).parent().parent().parent().find("#busContact1").removeClass("validatebox-invalid");
        $(bustype).parent().parent().parent().find("#busContactMobile1").removeClass("validatebox-invalid");
        $(bustype).parent().parent().parent().find("#busContactEmail1").removeClass("validatebox-invalid");
        $(bustype).parent().parent().parent().find("#busContactPerson1").removeClass("validatebox-invalid");
        $(bustype).parent().parent().parent().find("#busRiskEmail1").attr("disabled", false);
        if($(bustype).parent().parent().parent().find("#busRegion1").val()==''){
            $(bustype).parent().parent().parent().find("#busRegion1").addClass("validatebox-invalid");
        }
        if($(bustype).parent().parent().parent().find("#busContact1").val()==''){
            $(bustype).parent().parent().parent().find("#busContact1").addClass("validatebox-invalid");
        }
        if($(bustype).parent().parent().parent().find("#busContactMobile1").val()==''){
            $(bustype).parent().parent().parent().find("#busContactMobile1").addClass("validatebox-invalid");
        }
        if($(bustype).parent().parent().parent().find("#busContactPerson1").val()==''){
            $(bustype).parent().parent().parent().find("#busContactPerson1").addClass("validatebox-invalid");
        }



        if(busVal==2 || busVal==6){
            $(bustype).parent().parent().parent().find("#busParent1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busParent1").nextAll().attr("style","display: none");
            $(bustype).parent().parent().parent().find("#busRiskParent3").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busRiskParent3").nextAll().attr("style","display: none");
            $(bustype).parent().parent().parent().find("#busActivationParent1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busActivationParent1").nextAll().attr("style","display: none");
            $(bustype).parent().parent().parent().find("#busSentDirectly1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busDirectCashback1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busIndeAss1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#agentAssProtocol1").attr("disabled", true);

            $(bustype).parent().parent().parent().find("#busParent1").val("");
            $(bustype).parent().parent().parent().find("#busParent2").attr("value","");
            $(bustype).parent().parent().parent().find("#busRiskParent3").val("");
            $(bustype).parent().parent().parent().find("#busRiskParent4").attr("value","");
            $(bustype).parent().parent().parent().find("#busActivationParent1").val("");
            $(bustype).parent().parent().parent().find("#busActivationParent2").attr("value","");
            $(bustype).parent().parent().parent().find("#busSentDirectly1").val("");
            $(bustype).parent().parent().parent().find("#busDirectCashback1").val("");
            $(bustype).parent().parent().parent().find("#busIndeAss1").val("");
            $(bustype).parent().parent().parent().find("#agentAssProtocol1").val("");
        }else if(busVal==3 ||busVal==5){
            $(bustype).parent().parent().parent().find("#agentAssProtocol1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#agentAssProtocol1").val("");

        }else if(busVal==8){
            $(bustype).parent().parent().parent().find("#busParent1").nextAll().attr("style","display: none");
            $(bustype).parent().parent().parent().find("#busRiskParent3").nextAll().attr("style","display: none");
            $(bustype).parent().parent().parent().find("#busActivationParent1").nextAll().attr("style","display: none");
            $(bustype).parent().parent().parent().find("#busRegion1").nextAll().attr("style","display: none");
            $(bustype).parent().parent().parent().find("#busRegion1").removeClass("validatebox-invalid");
            $(bustype).parent().parent().parent().find("#busRiskEmail1").removeClass("validatebox-invalid");
            $(bustype).parent().parent().parent().find("#busContact1").removeClass("validatebox-invalid");
            $(bustype).parent().parent().parent().find("#busContactMobile1").removeClass("validatebox-invalid");
            $(bustype).parent().parent().parent().find("#busContactEmail1").removeClass("validatebox-invalid");
            $(bustype).parent().parent().parent().find("#busContactPerson1").removeClass("validatebox-invalid");

            $(bustype).parent().parent().parent().find("#busPlatform").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busPlatform").val("");

            $(bustype).parent().parent().parent().find("#busNums").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busNums").val("");

            $(bustype).parent().parent().parent().find("#busParent1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busParent1").val("");
            $(bustype).parent().parent().parent().find("#busParent2").attr("value","");

            $(bustype).parent().parent().parent().find("#busRiskParent3").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busRiskParent3").val("");
            $(bustype).parent().parent().parent().find("#busRiskParent4").attr("value","");

            $(bustype).parent().parent().parent().find("#busActivationParent1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busActivationParent1").val("");
            $(bustype).parent().parent().parent().find("#busActivationParent2").attr("value","");

            $(bustype).parent().parent().parent().find("#busRegion1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busRegion1").val("");
            $(bustype).parent().parent().parent().find("#busRegion2").attr("value","");

            $(bustype).parent().parent().parent().find("#busRiskEmail1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busRiskEmail1").val("");

            $(bustype).parent().parent().parent().find("#busContact1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busContact1").val("");

            $(bustype).parent().parent().parent().find("#busContactMobile1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busContactMobile1").val("");

            $(bustype).parent().parent().parent().find("#busContactEmail1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busContactEmail1").val("");

            $(bustype).parent().parent().parent().find("#busContactPerson1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busContactPerson1").val("");

            $(bustype).parent().parent().parent().find("#busSentDirectly1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busSentDirectly1").val("");
            $(bustype).parent().parent().parent().find("#busDirectCashback1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busDirectCashback1").val("");

            $(bustype).parent().parent().parent().find("#busIndeAss1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busIndeAss1").val("");

            $(bustype).parent().parent().parent().find("#cloInvoice1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#cloInvoice1").val("");

            $(bustype).parent().parent().parent().find("#cloReceipt1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#cloReceipt1").val("");

            $(bustype).parent().parent().parent().find("#cloTaxPoint1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#cloTaxPoint1").val("");

            $(bustype).parent().parent().parent().find("#cloPayCompany1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#cloPayCompany1").val("");

            $(bustype).parent().parent().parent().find("#agentAssProtocol1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#agentAssProtocol1").val("");

            $(bustype).parent().parent().parent().find("#busUseOrgan1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busUseOrgan1").val("");

            $(bustype).parent().parent().parent().find("#busScope1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busScope1").val("");

            $(bustype).parent().parent().parent().find("#dredgeS0Class1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#dredgeS0Class1").val("");

            $(bustype).parent().parent().parent().find("#busStatus1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#busStatus1").val("");

            $(bustype).parent().parent().parent().find("#status1").attr("disabled", true);
            $(bustype).parent().parent().parent().find("#status1").val("");
        }


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

    //代理商选择
    function returnEditAgentSele(data,srcData){
        $(srcData.target).prev("input").val(data.ID);
        $(srcData.target).prev("input").prev("input").val(data.AG_NAME);
    }

    //地区选择
    function returnEditRegion(data,options){

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


//        var id = data.id;
//        var dataIds = $(options.target).prev("input").val();
//        if(dataIds!=''){
//            var idSplit = dataIds.split(",");
//            for(var i=0;i<idSplit.length;i++){
//                if(id==idSplit[i]){
//                    info("该业务区域已选择！");
//                    return;
//                }
//            }
//        }
//        if(id=='0' && dataIds!=''){
//            info("全国已包括其他城市请清除后再选择全国！");
//            return false;
//        }
//        $(options.target).prev("input").val($(options.target).prev("input").val()!=''?$(options.target).prev("input").val()+","+data.id:""+data.id);
//        $(options.target).prev("input").prev("input").val($(options.target).prev("input").prev("input").val()!=''?$(options.target).prev("input").prev("input").val()+","+data.text:""+data.text);
    }


    function editAgentBusiTable_model(t){
        $("#editAgentBusi_model").tabs('add',{
            title:"代理商业务",
            content:$("#editAgentBusi_model_templet").html(),
            closable:true
        });
        var inputs = $("#editAgentBusi_model .grid:last input");
        for(var i=0;i<inputs.length;i++){
            if($(inputs[i]).attr("input-class") && $(inputs[i]).attr("input-class").length>0)
                $(inputs[i]).addClass($(inputs[i]).attr("input-class"));
        }
        $.parser.parse($("#editAgentBusi_model .grid:last"));

    }
    //解析打个table
    function get_editAgentBusiTable_FormDataItem(table){
        var data = {};
        data.id = $(table).find("input[name='id']").length>0?$(table).find("input[name='id']").val():"";
        data.agentId = $(table).find("input[name='agentId']").length>0?$(table).find("input[name='agentId']").val():"";
        data.busPlatform = $(table).find("select[name='busPlatform']").val();
        data.busNum = $(table).find("input[name='busNum']").val();
        data.busType = $(table).find("select[name='busType']").val();
        data.busParent = $(table).find("input[name='busParent']").val();
        data.busRiskParent = $(table).find("input[name='busRiskParent']").val();
        data.busActivationParent = $(table).find("input[name='busActivationParent']").val();
        data.busRegion = $(table).find("input[name='busRegion']").val();
        data.busRiskEmail = $(table).find("input[name='busRiskEmail']").val();
        data.busContact = $(table).find("input[name='busContact']").val();
        data.busContactMobile = $(table).find("input[name='busContactMobile']").val();
        data.busContactEmail = $(table).find("input[name='busContactEmail']").val();
        data.busContactPerson = $(table).find("input[name='busContactPerson']").val();
        data.busSentDirectly = $(table).find("select[name='busSentDirectly']").val();
        data.busDirectCashback = $(table).find("select[name='busDirectCashback']").val();
        data.busIndeAss = $(table).find("select[name='busIndeAss']").val();
        data.cloInvoice = $(table).find("select[name='cloInvoice']").val();
        data.cloReceipt = $(table).find("select[name='cloReceipt']").val();
        data.cloTaxPoint = $(table).find("input[name='cloTaxPoint']").val();
        data.cloPayCompany = $(table).find("select[name='cloPayCompany']").val();
        data.agentAssProtocol= $(table).find("select[name='agentAssProtocol']").val();
        data.agZbh= $(table).find("input[name='agZbh']").val();
        data.busStatus= $(table).find("select[name='busStatus']").val();
        data.status = $(table).find("select[name='status']").val();
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
    function get_editAgentBusiTable_FormData(){
        var editAgentBusiTable_FormDataJson = [];
        var tables = $("#editAgentBusi_model .grid");
        for (var  i=0;i<tables.length;i++){
            var table = tables[i];
            editAgentBusiTable_FormDataJson.push(get_editAgentBusiTable_FormDataItem(table));
        }
        return editAgentBusiTable_FormDataJson;
    }


    function changBus(o){
        var platformType = $(o).find("option:selected").attr("platformType");
        if(platformType=='POS' ||  platformType=='ZPOS' ||  platformType=='SSPOS' || platformType=='ZHPOS' || platformType=='RJPOS'){
            $(o).parent().parent().parent().find(".dredgeS0Class").removeAttr("style");
            if(platformType=='RJPOS'){
                $(o).parent().parent().parent().find(".dredgeD1Class").removeAttr("style");
            }else {
                $(o).parent().parent().parent().find(".dredgeD1Class").attr("style","display: none");
            }
        }else{
            $(o).parent().parent().parent().find(".dredgeS0Class").attr("style","display: none");
            $(o).parent().parent().parent().find(".dredgeD1Class").attr("style","display: none");
        }
    }

</script>


