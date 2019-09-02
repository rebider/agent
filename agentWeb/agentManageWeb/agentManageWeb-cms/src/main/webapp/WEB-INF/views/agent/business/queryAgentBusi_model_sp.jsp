<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="业务信息"  data-options="iconCls:'fi-results'">
    <div class="easyui-tabs">
        <c:if test="${!empty agentBusInfos}">
        <c:if test="${!empty preBusInfoVoList}">
            <c:forEach items="${preBusInfoVoList}" var="preBusInfoVoList">
            <c:forEach items="${agentBusInfos}" var="agentBusInfos">
                <div title="<c:forEach items="${ablePlatForm}" var="ablePlatFormItem">
                            <c:if test="${ablePlatFormItem.platformNum== agentBusInfos.busPlatform}">${ablePlatFormItem.platformName}</c:if>
                        </c:forEach>">
                    <table class="grid">
                        <shiro:hasPermission name="/agentEnter/agentQuery/AgentBusiInfoParentStracture">
                        <c:if test="${!empty agentBusInfos.parentInfo}">
                            <tr style="color: #00a65a;">
                                <td >
                                    上级关系
                                </td>
                                <td >
                                    <c:if test="${!empty agentBusInfos.parentInfo.AAGENTNAME}">
                                        <c:forEach items="${busType}" var="busTypeItem">
                                            <c:if test="${busTypeItem.dItemvalue== agentBusInfos.parentInfo.ABUSTYPE}">${busTypeItem.dItemname}</c:if>
                                        </c:forEach>
                                        -${agentBusInfos.parentInfo.AAGENTNAME}</br>(${agentBusInfos.parentInfo.AAGENTID})(${agentBusInfos.parentInfo.ABUSNUM})
                                    </c:if>
                                </td>
                                <td colspan="2">
                                    <c:if test="${!empty agentBusInfos.parentInfo.BAGENTNAME}">
                                        (<c:forEach items="${busType}" var="busTypeItem"><c:if test="${busTypeItem.dItemvalue== agentBusInfos.parentInfo.BBUSTYPE}">${busTypeItem.dItemname}</c:if></c:forEach>)
                                        -${agentBusInfos.parentInfo.BAGENTNAME}</br>(${agentBusInfos.parentInfo.BAGENTID})(${agentBusInfos.parentInfo.BBUSNUM})
                                    </c:if>
                                </td>
                                <td colspan="2">
                                    <c:if test="${!empty agentBusInfos.parentInfo.CAGENTNAME}">
                                        (<c:forEach items="${busType}" var="busTypeItem"><c:if test="${busTypeItem.dItemvalue== agentBusInfos.parentInfo.CBUSTYPE}">${busTypeItem.dItemname}</c:if></c:forEach>)
                                        -${agentBusInfos.parentInfo.CAGENTNAME}</br>(${agentBusInfos.parentInfo.CAGENTID})(${agentBusInfos.parentInfo.CBUSNUM})
                                    </c:if>
                                </td>
                                <td colspan="2">
                                    <c:if test="${!empty agentBusInfos.parentInfo.DAGENTNAME}">
                                        (<c:forEach items="${busType}" var="busTypeItem"><c:if test="${busTypeItem.dItemvalue== agentBusInfos.parentInfo.DBUSTYPE}">${busTypeItem.dItemname}</c:if></c:forEach>)
                                        -${agentBusInfos.parentInfo.DAGENTNAME} </br>(${agentBusInfos.parentInfo.DAGENTID})(${agentBusInfos.parentInfo.DBUSNUM})
                                    </c:if>
                                </td>
                            </tr>
                        </c:if>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/posOrg/statistics">
                        <tr>
                            <td>
                                <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 140px;" data-options="iconCls:'fi-magnifying-glass'" onclick="queryNetInTermNum('${agentBusInfos.busNum}','${agentBusInfos.busParent}','${agentBusInfos.busPlatform}')">查询终端数量</a>
                            </td>
                        </tr>
                        </shiro:hasPermission>
                        <tr >
                            <shiro:hasPermission name="/bus/busPlatform">
                            <td>业务平台</td>
                            <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSPLATFORM">
                                <c:forEach items="${ablePlatForm}" var="ablePlatFormItem">
                                    <c:if test="${ablePlatFormItem.platformNum==agentBusInfos.busPlatform}">
                                        <a target="_blank" href="${agentBusInfos.platformUrl}">${ablePlatFormItem.platformName}</a>
                                        <a href="javascript:void(0)" style="display: none" id="busPlatform">
                                            ${ablePlatFormItem.platformNum}
                                        </a>
                                    </c:if>
                                </c:forEach>
                            </td>
                                <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="BUSPLATFORM" >
                                 <%--   <c:forEach items="${ablePlatForm}" var="ablePlatFormItem">
                                        <c:if test="${ablePlatFormItem.platformNum==preBusInfoVoList.busPlatform}">
                                        <c:if test="${preBusInfoVoList.busPlatform!=agentBusInfos.busPlatform}">
                                            <a target="_blank" href="${preBusInfoVoList.platformUrl}">
                                            </a>
                                            <a href="javascript:void(0)" style="display: none" id="busPlatform">
                                                    ${ablePlatFormItem.platformNum}
                                            </a>
                                        </c:if>
                                        </c:if>
                                    </c:forEach>--%>
                                </td>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="/bus/busNum">
                                <c:if test="${agentBusInfos.busPlatform!='100013' || MainLoginUserId==1}">
                                    <td>业务平台编号</td>
                                    <td  SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSNUM"  width="180px">
                                        <input type="hidden" id="busId" value="${agentBusInfos.id}">
                                        <c:if test="${agentBusInfos.busStatus==0 || agentBusInfos.busStatus==2}">
                                            <shiro:hasPermission name="/agentEnter/busNum">
                                                <a href="javascript:void(0)" style="text-decoration:none;color:#333;" id="busNum">${agentBusInfos.busNum}</a>
                                            </shiro:hasPermission>
                                        </c:if>
                                        <c:if test="${agentBusInfos.busStatus==1}">
                                            <a href="javascript:void(0)" style="text-decoration:none;color:#333;" id="busNum">${agentBusInfos.busNum}</a>
                                        </c:if>
                                    </td>
                                </c:if>

                               <%-- <c:if test="${preBusInfoVoList.busPlatform!='100013' || MainLoginUserId==1}">
                                    <td  SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="BUSNUM"  width="180px">
                                        <input type="hidden" id="busId" value="${preBusInfoVoList.id}">
                                        <c:if test="${!empty preBusInfoVoList.busStatus && preBusInfoVoList.busStatus==0 || preBusInfoVoList.busStatus==2}">
                                            <shiro:hasPermission name="/agentEnter/busNum">
                                                <a href="javascript:void(0)" id="busNum" style="color: red">
                                                    <c:if test="${ preBusInfoVoList.busNum!=agentBusInfos.busNum}">
                                                   <div style="color: red">
                                                       (原有信息:<agent:show type="agentBusIdForAgent" busId="${preBusInfoVoList.busNum}" />)
                                                   </div>
                                                    </a>
                                                    </c:if>
                                            </shiro:hasPermission>
                                        </c:if>
                                      &lt;%&ndash;  <c:if test="${preBusInfoVoList.busStatus==1}">
                                            <c:if test="${!empty preBusInfoVoList.busStatus &&  preBusInfoVoList.busStatus!=agentBusInfos.busStatus}">
                                            <a href="javascript:void(0)" style="text-decoration:none;color:#333;" id="busNum" style="color: red">
                                                <div style="color: red">  (原有信息:${preBusInfoVoList.busNum})</div>
                                            </a>
                                            </c:if>
                                        </c:if>&ndash;%&gt;
                                    </td>
                                </c:if>--%>
                            </shiro:hasPermission>

                    <shiro:hasPermission name="/bus/busType">
                        </tr>
                        <tr>
                            <td>类型</td>
                            <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSTYPE">
                                <c:forEach items="${busType}" var="busTypeItem">
                                    <c:if test="${busTypeItem.dItemvalue== agentBusInfos.busType}">${busTypeItem.dItemname}</c:if>
                                </c:forEach>
                            </td>
                            <td  SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="BUSTYPE">
                               <%-- <c:if test="${!empty preBusInfoVoList.busType &&  preBusInfoVoList.busType!=agentBusInfos.busType}">
                                <c:forEach items="${busType}" var="busTypeItem">
                                    <c:if test="${busTypeItem.dItemvalue== preBusInfoVoList.busType}">(原有信息:${busTypeItem.dItemname})</c:if>
                                </c:forEach>
                                </c:if>--%>
                            </td>

                            </shiro:hasPermission>
                            <shiro:hasPermission name="/bus/busParent">
                                <td>上级代理</td>
                                <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSPARENT"><agent:show type="agentBusIdForAgent" busId="${agentBusInfos.busParent}"/>
                                    <shiro:hasPermission name="/agentEnter/agentQuery/AgentBusiInfoParentStracture">||<a href="javascript:void(0);" onclick="showSynRegionFrame({
                                        target:this,
                                        callBack:agentQueryBusTreeCallBach
                                        },'/region/busTreee?currentId=${agentBusInfos.id}',false)">业务结构</a>
                                    </shiro:hasPermission></td>

                                <td style="color: red" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="BUSPARENT">
                                    <c:if test="${!empty preBusInfoVoList.busParent &&!empty agentBusInfos.busParent &&  preBusInfoVoList.busParent !=agentBusInfos.busParent}">
                                    (原有信息:<agent:show type="agentBusIdForAgent" busId="${preBusInfoVoList.busParent}"/>)
                                    <shiro:hasPermission name="/agentEnter/agentQuery/AgentBusiInfoParentStracture">||<a href="javascript:void(0);" onclick="showSynRegionFrame({
                                        target:this,
                                        callBack:agentQueryBusTreeCallBach
                                        },'/region/busTreee?currentId=${preBusInfoVoList.id}',false)">业务结构</a>
                                    </shiro:hasPermission></td>
                                    </c:if>
                            </shiro:hasPermission>
                        </tr>
                        <tr >
                            <shiro:hasPermission name="/bus/busRiskParent">
                            <td>风险承担所属代理商</td>
                            <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSRISKPARENT">
                                <agent:show type="agentBusIdForAgent" busId="${agentBusInfos.busRiskParent}"/>
                            </td>
                                <td style="color: red" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="BUSRISKPARENT">
                                    <c:if test="${!empty preBusInfoVoList.busRiskParent &&!empty agentBusInfos.busRiskParent && preBusInfoVoList.busRiskParent !=agentBusInfos.busRiskParent}">
                                    ( 原有信息:<agent:show type="agentBusIdForAgent" busId="${preBusInfoVoList.busRiskParent}"/>)
                                    </c:if>
                                </td>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="/bus/busActivationParent">
                            <td>激活及返现所属代理商</td>
                            <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSACTIVATIONPARENT">
                                <agent:show type="agentBusIdForAgent" busId="${agentBusInfos.busActivationParent}"/>
                            </td>

                                <td style="color: red" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="BUSACTIVATIONPARENT">
                                    <c:if test="${!empty preBusInfoVoList.busActivationParent &&!empty agentBusInfos.busActivationParent && preBusInfoVoList.busActivationParent !=agentBusInfos.busActivationParent}">
                                    ( 原有信息: <agent:show type="agentBusIdForAgent" busId="${preBusInfoVoList.busActivationParent}"/>)
                                    </c:if>
                                </td>
                            </shiro:hasPermission>

                        </tr>
                        <tr>
                            <shiro:hasPermission name="/bus/busRegion">
                                <td>业务区域</td>
                                <td title="<agent:show type="posRegion" busId="${agentBusInfos.busRegion}"/>" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSREGION" >
                                    <div onclick="showBusRegionFrame({target:this,bufData:'${agentBusInfos.busRegion}',but:false})">查看</div><input type="hidden" value="${agentBusInfos.busRegion}"/>
                                </td>

                                <td  title="(原有信息:<agent:show type="posRegion" busId="${preBusInfoVoList.busRegion}"/>)" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="BUSREGION" >
                                    <c:if test="${!empty preBusInfoVoList.busRegion && preBusInfoVoList.busRegion !=agentBusInfos.busRegion}">
                                    <div style="color: red" onclick="showBusRegionFrame({target:this,bufData:'${preBusInfoVoList.busRegion}',but:false})">查看</div><input type="hidden" value="${preBusInfoVoList.busRegion}"/>
                                    </c:if>
                                </td>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="/bus/busContact">
                                <td>业务联系人</td>
                                <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSCONTACT" ><desensitization:show type="name" value="${agentBusInfos.busContact}" jurisdiction="/agent/busNameSee"/></td>
                                <td style="color: red"  SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="BUSCONTACT" >
                                    <c:if test="${!empty preBusInfoVoList.busContact && preBusInfoVoList.busContact !=agentBusInfos.busContact}">
                                    (原有信息:<desensitization:show type="name" value="${preBusInfoVoList.busContact}" jurisdiction="/agent/busNameSee"/>)
                                    </c:if>
                                </td>
                            </shiro:hasPermission>
                        </tr>
                        <tr >
                            <shiro:hasPermission name="/bus/busContactMobile">
                            <td>业务联系电话</td>
                            <td  SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSCONTACTMOBILE"><desensitization:show type="mobile" value="${agentBusInfos.busContactMobile}" jurisdiction="/agent/busMobileSee"/></td>
                                <td style="color: red" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="BUSCONTACTMOBILE">
                                    <c:if test="${!empty preBusInfoVoList.busContactMobile && preBusInfoVoList.busContactMobile !=agentBusInfos.busContactMobile}">
                                    (原有信息:<desensitization:show type="mobile" value="${preBusInfoVoList.busContactMobile}" jurisdiction="/agent/busMobileSee"/>)
                                    </c:if>
                                </td>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="/bus/busContactPerson">
                            <td>业务对接人</td>
                            <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSCONTACTPERSON"><desensitization:show type="name" value="${agentBusInfos.busContactPerson}" jurisdiction="/agent/busNameSee"/></td>

                                <c:if test="${!empty preBusInfoVoList.busContactPerson && agentBusInfos.busContactPerson !=preBusInfoVoList.busContactPerson}">
                            <td style="color: red" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="BUSCONTACTPERSON">
                                (原有信息:<desensitization:show type="name" value="${preBusInfoVoList.busContactPerson}" jurisdiction="/agent/busNameSee"/>)
                            </td>
                                </c:if>
                            </shiro:hasPermission>
                        </tr>
                        <tr >
                            <shiro:hasPermission name="/bus/busSentDirectly">
                            <td>是否直发</td>
                            <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSSENTDIRECTLY">
                                <c:forEach items="${yesOrNo}" var="yesOrNoItem">
                                    <c:if test="${yesOrNoItem.dItemvalue== agentBusInfos.busSentDirectly}">${yesOrNoItem.dItemname}</c:if>
                                </c:forEach>
                            </td>

                                <td style="color: red" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="BUSSENTDIRECTLY">
                                    <c:forEach items="${yesOrNo}" var="yesOrNoItem">
                                        <c:if test="${yesOrNoItem.dItemvalue== preBusInfoVoList.busSentDirectly}">
                                            <c:if test="${!empty preBusInfoVoList.busSentDirectly && preBusInfoVoList.busSentDirectly !=agentBusInfos.busSentDirectly}">
                                        <option  style="color: red">
                                            (原有信息:${yesOrNoItem.dItemname})
                                        </option>
                                            </c:if>
                                        </c:if>
                                    </c:forEach>
                                </td>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="/bus/busDirectCashback">
                            <td>是否直接返现</td>
                            <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSDIRECTCASHBACK">
                                <c:forEach items="${yesOrNo}" var="yesOrNoItem">
                                    <c:if test="${yesOrNoItem.dItemvalue== agentBusInfos.busDirectCashback}">${yesOrNoItem.dItemname}</c:if>
                                </c:forEach>
                            </td>

                                <td  style="color: red" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="BUSDIRECTCASHBACK">
                                    <c:forEach items="${yesOrNo}" var="yesOrNoItem">
                                        <c:if test="${yesOrNoItem.dItemvalue== preBusInfoVoList.busDirectCashback}">
                                            <c:if test="${!empty preBusInfoVoList.busDirectCashback && agentBusInfos.busDirectCashback !=preBusInfoVoList.busDirectCashback}">
                                            <option  style="color: red">
                                            (原有信息:${yesOrNoItem.dItemname})
                                        </option>
                                        </c:if>
                                        </c:if>
                                    </c:forEach>
                                </td>
                            </shiro:hasPermission>

                        </tr>
                        <tr>
                            <shiro:hasPermission name="/bus/busIndeAss">
                                <td>是否独立考核</td>
                                <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSINDEASS">
                                    <c:forEach items="${yesOrNo}" var="yesOrNoItem">
                                        <c:if test="${yesOrNoItem.dItemvalue== agentBusInfos.busIndeAss}">${yesOrNoItem.dItemname}</c:if>
                                    </c:forEach>
                                </td>

                                <td style="color: red" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="BUSINDEASS">
                                    <c:forEach items="${yesOrNo}" var="yesOrNoItem">
                                        <c:if test="${!empty preBusInfoVoList.busIndeAss && agentBusInfos.busIndeAss !=preBusInfoVoList.busIndeAss}">
                                        <c:if test="${yesOrNoItem.dItemvalue== preBusInfoVoList.busIndeAss}">
                                        <option  style="color: red">
                                            (原有信息:${yesOrNoItem.dItemname})
                                        </option>
                                        </c:if>
                                        </c:if>
                                    </c:forEach>
                                </td>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="/bus/cloReceipt">
                                <td>是否要求收据</td>
                                <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="CLORECEIPT">
                                    <c:forEach items="${yesOrNo}" var="yesOrNoItem">
                                        <c:if test="${yesOrNoItem.dItemvalue== agentBusInfos.cloReceipt}">${yesOrNoItem.dItemname}</c:if>
                                    </c:forEach>
                                </td>
                                <td style="color: red" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="CLORECEIPT">
                                    <c:forEach items="${yesOrNo}" var="yesOrNoItem">
                                        <c:if test="${yesOrNoItem.dItemvalue== preBusInfoVoList.cloReceipt}">
                                            <c:if test="${!empty preBusInfoVoList.cloReceipt &&  agentBusInfos.cloReceipt !=preBusInfoVoList.cloReceipt}">
                                        <option  style="color: red">
                                            (原有信息:${yesOrNoItem.dItemname})
                                        </option>
                                        </c:if>
                                        </c:if>
                                    </c:forEach>
                                </td>
                            </shiro:hasPermission>
                        </tr>
                        <tr >
                            <shiro:hasPermission name="/bus/cloPayCompany">
                            <td>打款公司</td>
                            <td ><c:forEach items="${compList}" var="compListItem"  ><c:if test="${compListItem.id== agentBusInfos.cloPayCompany}">${compListItem.comName}</c:if></c:forEach></td>
                                <td style="color: red">
                                    <c:forEach items="${compList}" var="compListItem"  >
                                        <c:if test="${compListItem.id== preBusInfoVoList.cloPayCompany}">
                                        <c:if test="${!empty preBusInfoVoList.cloPayCompany && agentBusInfos.cloPayCompany!= preBusInfoVoList.cloPayCompany}">
                                            (原有信息:${compListItem.comName})
                                        </c:if>
                                        </c:if>
                                    </c:forEach>
                                </td>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="/bus/agZbh">
                            <td>财务编号</td>
                            <td  SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="AGZBH">${agentBusInfos.agZbh}</td>
                                <td  style="color: red" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="AGZBH">
                                    <c:if test="${ !empty preBusInfoVoList.agZbh && agentBusInfos.agZbh !=preBusInfoVoList.agZbh}">
                                    (原有信息:${preBusInfoVoList.agZbh})
                                    </c:if>
                                </td>
                            </shiro:hasPermission>
                        </tr>
                        <tr>
                            <shiro:hasPermission name="/bus/busUseOrgan">
                                <td>使用范围</td>
                                <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSUSEORGAN">
                                    <c:forEach items="${useScope}" var="useScopeItem"  >
                                        <c:if test="${useScopeItem.dItemvalue == agentBusInfos.busUseOrgan}">${useScopeItem.dItemname}</c:if>
                                    </c:forEach>
                                </td>
                                <td style="color: red" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="BUSUSEORGAN">
                                    <c:forEach items="${useScope}" var="useScopeItem"  >
                                        <c:if test="${useScopeItem.dItemvalue == preBusInfoVoList.busUseOrgan}">
                                        <c:if test="${!empty preBusInfoVoList.busUseOrgan && agentBusInfos.busUseOrgan !=preBusInfoVoList.busUseOrgan}">
                                            (原有信息:${useScopeItem.dItemname})
                                        </c:if>
                                        </c:if>
                                    </c:forEach>
                                </td>
                            </shiro:hasPermission>

                            <shiro:hasPermission name="/bus/busScope">
                                <td>业务范围</td>
                                <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSSCOPE">
                                    <c:forEach items="${busScope}" var="busScopeItem"  >
                                        <c:if test="${busScopeItem.dItemvalue == agentBusInfos.busScope}">${busScopeItem.dItemname}</c:if>
                                    </c:forEach>
                                </td>
                                <td style="color: red" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="BUSSCOPE">
                                    <c:forEach items="${busScope}" var="busScopeItem"  >
                                        <c:if test="${busScopeItem.dItemvalue == preBusInfoVoList.busScope}">
                                        <c:if test="${!empty preBusInfoVoList.busScope && agentBusInfos.busScope !=preBusInfoVoList.busScope}">
                                            (原有信息:${busScopeItem.dItemname})
                                        </c:if>
                                        </c:if>
                                    </c:forEach>
                                </td>
                            </shiro:hasPermission>
                        </tr>
                        <tr>
                            <shiro:hasPermission name="/bus/dredgeS0">
                            <c:if test="${agentBusInfos.busPlatformType=='POS' || agentBusInfos.busPlatformType=='ZPOS' || agentBusInfos.busPlatformType=='ZHPOS' || agentBusInfos.busPlatformType=='SSPOS' || agentBusInfos.busPlatformType=='RJPOS'}">
                                <td>是否开通S0</td>
                                <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="DREDGES0">
                                    <c:forEach items="${yesOrNo}" var="yesOrNoItem">
                                        <c:if test="${yesOrNoItem.dItemvalue == agentBusInfos.dredgeS0}">${yesOrNoItem.dItemname}</c:if>
                                    </c:forEach>
                                </td>
                            </c:if>

                                <c:if test="${preBusInfoVoList.busPlatformType=='POS' || preBusInfoVoList.busPlatformType=='ZPOS' || preBusInfoVoList.busPlatformType=='ZHPOS'  || preBusInfoVoList.busPlatformType=='SSPOS' || preBusInfoVoList.busPlatformType=='RJPOS'}">
                                    <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="DREDGES0">
                                        <c:forEach items="${yesOrNo}" var="yesOrNoItem">
                                            <c:if test="${yesOrNoItem.dItemvalue == preBusInfoVoList.dredgeS0}">
                                            <c:if test="${!empty preBusInfoVoList.dredgeS0 && agentBusInfos.dredgeS0 !=preBusInfoVoList.dredgeS0}">
                                                <option  style="color: red">
                                                (原有信息:${yesOrNoItem.dItemname})
                                                </option>
                                            </c:if>
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                </c:if>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="/bus/busLoginNum">
                                <c:if test="${agentBusInfos.busPlatform!='100013' || MainLoginUserId==1}">
                                    <td>平台登陆账号</td>
                                    <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSLOGINNUM" >${agentBusInfos.busLoginNum}</td>
                                </c:if>
                                <c:if test="${preBusInfoVoList.busPlatform!='100013' || MainLoginUserId==1}">
                                    <td style="color: red" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="BUSLOGINNUM" >
                                    <c:if test="${!empty preBusInfoVoList.busLoginNum && agentBusInfos.busLoginNum !=preBusInfoVoList.busLoginNum}">
                                         (原有信息:${preBusInfoVoList.busLoginNum})
                                    </c:if>
                                    </td>
                                </c:if>
                            </shiro:hasPermission>

                        </tr>
                        <tr>
                            <shiro:hasPermission name="/bus/busStatus">
                                <td>业务状态</td>
                                <td  style="color: red" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSSTATUS" >
                                    <select id="busStatus"  disabled="disabled">
                                        <c:forEach items="${busStatus}" var="busStatusItem"  >
                                            <option value="${busStatusItem.dItemvalue}" <c:if test="${busStatusItem.dItemvalue == agentBusInfos.busStatus}">selected="selected"</c:if>>${busStatusItem.dItemname}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td  style="color: red" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="BUSSTATUS" >
                                    <c:forEach items="${busStatus}" var="busStatusItem"  >
                                     <c:if test="${!empty preBusInfoVoList.busStatus &&  agentBusInfos.busStatus !=preBusInfoVoList.busStatus}">
                                         <c:if test="${busStatusItem.dItemvalue == preBusInfoVoList.busStatus}">
                                            <option value="${busStatusItem.dItemvalue}">
                                                (原有信息:${busStatusItem.dItemname})
                                            </option>
                                     </c:if>
                                     </c:if>
                                    </c:forEach>
                                </td>
                            </shiro:hasPermission>
                        </tr>
                        <c:if test="${ agentBusInfos.busPlatformType=='POS' || agentBusInfos.busPlatformType=='ZPOS' || agentBusInfos.busPlatformType=='ZHPOS' }">
                        <c:if test="${ preBusInfoVoList.busPlatformType=='POS' || preBusInfoVoList.busPlatformType=='ZPOS' || preBusInfoVoList.busPlatformType=='ZHPOS' }">
                        <tr>
                            <shiro:hasPermission name="/bus/debitRateLower">
                                <td>借记费率下限（%）</td>
                                <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="DEBITRATELOWER" >${agentBusInfos.debitRateLower}</td>
                                <td  style="color: red" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="DEBITRATELOWER" >
                                    <c:if test="${!empty preBusInfoVoList.debitRateLower &&  agentBusInfos.debitRateLower !=preBusInfoVoList.debitRateLower}">
                                    (原有信息:${preBusInfoVoList.debitRateLower})
                                    </c:if>
                                </td>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="/bus/debitCapping">
                                <td>借记封顶额（元）</td>
                                <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="DEBITCAPPING" >${agentBusInfos.debitCapping}</td>
                                <td style="color: red" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="DEBITCAPPING" >
                                    <c:if test="${!empty preBusInfoVoList.debitCapping &&  agentBusInfos.debitCapping !=preBusInfoVoList.debitCapping}">
                                    (原有信息:${preBusInfoVoList.debitCapping})
                                    </c:if>
                                </td>
                            </shiro:hasPermission>
                        </tr>
                            <tr>
                                <shiro:hasPermission name="/bus/debitAppearRate">
                                    <td>借记出款费率（%）</td>
                                    <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="DEBITAPPEARRATE" >${agentBusInfos.debitAppearRate}</td>
                                    <td style="color: red" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="DEBITAPPEARRATE" >
                                        <c:if test="${!empty preBusInfoVoList.debitAppearRate &&  agentBusInfos.debitAppearRate !=preBusInfoVoList.debitAppearRate}">
                                        (原有信息:${preBusInfoVoList.debitAppearRate})
                                        </c:if>
                                    </td>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="/bus/creditRateFloor">
                                    <td>贷记费率下限（%）</td>
                                    <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="CREDITRATEFLOOR" >${agentBusInfos.creditRateFloor}</td>
                                    <td style="color: red" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="CREDITRATEFLOOR" >
                                        <c:if test="${!empty preBusInfoVoList.creditRateFloor && agentBusInfos.creditRateFloor !=preBusInfoVoList.creditRateFloor}">
                                        (原有信息:${preBusInfoVoList.creditRateFloor})
                                        </c:if>
                                    </td>
                                </shiro:hasPermission>
                            </tr>
                        <tr>
                            <shiro:hasPermission name="/bus/creditRateCeiling">
                                <td>贷记费率上限（%）</td>
                                <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="CREDITRATEFLOOR" >${agentBusInfos.creditRateCeiling}</td>
                                <td style="color: red" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="CREDITRATEFLOOR" >
                                    <c:if test="${!empty preBusInfoVoList.creditRateCeiling &&  agentBusInfos.creditRateCeiling !=preBusInfoVoList.creditRateCeiling}">
                                    (原有信息:${preBusInfoVoList.creditRateCeiling})
                                    </c:if>
                                </td>
                            </shiro:hasPermission>
                        </tr>
                        </c:if>
                        </c:if>

                        <c:if test="${agentBusInfos.busPlatformType=='RDBPOS'}">
                        <c:if test="${preBusInfoVoList.busPlatformType=='RDBPOS'}">
                            <tr>
                                <td>终端数量下限</td>
                                <td colspan="4" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="TERMINALSLOWER" >${agentBusInfos.terminalsLower}</td>
                                <td style="color: red" colspan="1" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="TERMINALSLOWER" >
                            <c:if test="${!empty preBusInfoVoList.terminalsLower && agentBusInfos.terminalsLower !=preBusInfoVoList.terminalsLower}">
                                    (原有信息:${preBusInfoVoList.terminalsLower})
                            </c:if>
                                </td>
                            </tr>
                        </c:if>
                        </c:if>
                        <tr>
                        <shiro:hasPermission name="/bus/agDocDistrict">
                            <td>业务对接大区</td>
                            <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="AGDOCDISTRICT">
                                <agent:show type="dept" busId="${agentBusInfos.agDocDistrict}" />
                            </td>
                            <td style="color: red" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="AGDOCDISTRICT">
                                <c:if test="${!empty preBusInfoVoList.agDocDistrict &&  agentBusInfos.agDocDistrict !=preBusInfoVoList.agDocDistrict}">
                                (原有信息:<agent:show type="dept" busId="${preBusInfoVoList.agDocDistrict}" />)
                                </c:if>
                            </td>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/bus/agDocPro">
                            <td>业务对接省区</td>
                            <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="AGDOCPRO">
                                <agent:show type="dept" busId="${agentBusInfos.agDocPro}" />
                            </td>
                            <td style="color: red" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="AGDOCPRO">
                                <c:if test="${!empty preBusInfoVoList.agDocPro && agentBusInfos.agDocPro !=preBusInfoVoList.agDocPro}">
                                (原有信息:<agent:show type="dept" busId="${preBusInfoVoList.agDocPro}" />)
                                </c:if>
                            </td>
                        </shiro:hasPermission>
                        </tr>
                        <tr>
                        <shiro:hasPermission name="/bus/organName">
                            <td>顶级机构</td>
                            <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="ORGANNUM">
                                <agent:busInfo type="BUS_ORG" busId="${agentBusInfos.organNum}"></agent:busInfo>
                            </td>
                            <td SCAN="TRUE" style="color: red" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="ORGANNUM">
                                <c:if test="${!empty preBusInfoVoList.organNum && agentBusInfos.organNum !=preBusInfoVoList.organNum}">
                                (原有信息: <agent:busInfo type="BUS_ORG" busId="${preBusInfoVoList.organNum}"></agent:busInfo>)
                                </c:if>
                            </td>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/bus/remitOrgan">
                            <td>出款机构</td>
                            <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="FINACEREMITORGAN">
                                <agent:busInfo type="BUS_ORG" busId="${agentBusInfos.finaceRemitOrgan}"></agent:busInfo>
                            </td>
                            <td style="color: red" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${preBusInfoVoList.id}" MODELKEY="FINACEREMITORGAN">
                                <c:if test="${!empty preBusInfoVoList.finaceRemitOrgan && agentBusInfos.finaceRemitOrgan !=preBusInfoVoList.finaceRemitOrgan}">
                                (原有信息: <agent:busInfo type="BUS_ORG" busId="${preBusInfoVoList.finaceRemitOrgan}"></agent:busInfo>)
                                </c:if>
                            </td>
                        </shiro:hasPermission>
                        <tr>
                        <tr>
                            <shiro:hasPermission name="/bus/dredgeS0">
                                <c:if test="${agentBusInfos.busPlatformType=='RJPOS'}">
                                    <td>是否开通D1</td>
                                    <td>
                                        <c:forEach items="${yesOrNo}" var="yesOrNoItem">
                                            <c:if test="${yesOrNoItem.dItemvalue == agentBusInfos.dredgeD1}">${yesOrNoItem.dItemname}</c:if>
                                        </c:forEach>
                                    </td>
                                </c:if>

                                <c:if test="${preBusInfoVoList.busPlatformType=='RJPOS'}">
                                    <td>
                                        <c:forEach items="${yesOrNo}" var="yesOrNoItem">
                                            <c:if test="${yesOrNoItem.dItemvalue == preBusInfoVoList.dredgeD1}">
                                                <c:if test="${!empty preBusInfoVoList.dredgeD1 && agentBusInfos.dredgeD1 !=preBusInfoVoList.dredgeD1}">
                                                    <option  style="color: red">
                                                        (原有信息:${yesOrNoItem.dItemname})
                                                    </option>
                                                </c:if>
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                </c:if>
                            </shiro:hasPermission>
                        </tr>
                    </table>
                </div>
            </c:forEach>
            </c:forEach>
        </c:if>
        </c:if>
    </div>
</div>
<script type="application/javascript">
   function agentQueryBusTreeCallBach(data){

   }

   function queryNetInTermNum(busNum,busParent,busPlatform){

       if(busPlatform=='' || busPlatform==undefined){
           info("平台不能为空");
           return false;
       }
       parent.$.modalDialog({
           title : '查询终端数量',
           width : 500,
           height : '100%',
           href : '${path }/posOrg/statistics?busParent='+busParent+'&busPlatform='+busPlatform+'&busNum='+busNum
       });

   }
</script>