<%--&lt;%&ndash;数据修改申请展示当前库里的数据&ndash;%&gt;--%>
<%--<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>--%>
<%--<div class="easyui-tabs">--%>
    <%--<c:if test="${!empty agentBusInfos}">--%>
        <%--<c:forEach items="${agentBusInfos}" var="agentBusInfos">--%>
            <%--<div title="<c:forEach items="${ablePlatForm}" var="ablePlatFormItem">--%>
                            <%--<c:if test="${ablePlatFormItem.platformNum== agentBusInfos.busPlatform}">${ablePlatFormItem.platformName}</c:if>--%>
                        <%--</c:forEach>">--%>
                <%--<table class="grid">--%>
                    <%--<c:if test="${!empty agentBusInfos.parentInfo}">--%>
                        <%--<tr style="color: #00a65a;">--%>
                            <%--<td >--%>
                                <%--上级关系--%>
                            <%--</td>--%>
                            <%--<td >--%>
                                <%--<c:if test="${!empty agentBusInfos.parentInfo.AAGENTNAME}">--%>
                                    <%--<c:forEach items="${busType}" var="busTypeItem">--%>
                                        <%--<c:if test="${busTypeItem.dItemvalue== agentBusInfos.parentInfo.ABUSTYPE}">${busTypeItem.dItemname}</c:if>--%>
                                    <%--</c:forEach>-${agentBusInfos.parentInfo.AAGENTNAME}-${agentBusInfos.parentInfo.ABUSNUM}--%>
                                <%--</c:if>--%>
                            <%--</td>--%>
                            <%--<td colspan="2">--%>
                                <%--<c:if test="${!empty agentBusInfos.parentInfo.BAGENTNAME}">--%>
                                    <%--(<c:forEach items="${busType}" var="busTypeItem">--%>
                                        <%--<c:if test="${busTypeItem.dItemvalue== agentBusInfos.parentInfo.BBUSTYPE}">${busTypeItem.dItemname}</c:if>--%>
                                    <%--</c:forEach>)-${agentBusInfos.parentInfo.BAGENTNAME}-${agentBusInfos.parentInfo.BBUSNUM}--%>
                                <%--</c:if>--%>

                            <%--</td>--%>
                            <%--<td colspan="2">--%>
                                <%--<c:if test="${!empty agentBusInfos.parentInfo.CAGENTNAME}">--%>
                                    <%--(<c:forEach items="${busType}" var="busTypeItem">--%>
                                        <%--<c:if test="${busTypeItem.dItemvalue== agentBusInfos.parentInfo.CBUSTYPE}">${busTypeItem.dItemname}</c:if>--%>
                                    <%--</c:forEach>)-${agentBusInfos.parentInfo.CAGENTNAME}-${agentBusInfos.parentInfo.CBUSNUM}--%>
                                <%--</c:if>--%>

                            <%--</td>--%>
                            <%--<td colspan="2">--%>
                                <%--<c:if test="${!empty agentBusInfos.parentInfo.DAGENTNAME}">--%>
                                    <%--(<c:forEach items="${busType}" var="busTypeItem">--%>
                                        <%--<c:if test="${busTypeItem.dItemvalue== agentBusInfos.parentInfo.DBUSTYPE}">${busTypeItem.dItemname}</c:if>--%>
                                    <%--</c:forEach>)-${agentBusInfos.parentInfo.DAGENTNAME}-${agentBusInfos.parentInfo.DBUSNUM}--%>
                                <%--</c:if>--%>
                            <%--</td>--%>
                        <%--</tr>--%>
                    <%--</c:if>--%>
                    <%--<tr >--%>
                        <%--<shiro:hasPermission name="/bus/busPlatform">--%>
                        <%--<td>业务平台</td>--%>
                        <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSPLATFORM">--%>
                            <%--<c:forEach items="${ablePlatForm}" var="ablePlatFormItem">--%>
                                <%--<c:if test="${ablePlatFormItem.platformNum== agentBusInfos.busPlatform}">--%>
                                    <%--${ablePlatFormItem.platformName}--%>
                                    <%--<a href="javascript:void(0)" style="display: none" id="busPlatform">--%>
                                            <%--${ablePlatFormItem.platformNum}--%>
                                    <%--</a>--%>
                                <%--</c:if>--%>
                            <%--</c:forEach>--%>
                        <%--</td>--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="/bus/busNum">--%>
                            <%--<td>业务平台编号</td>--%>
                            <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSNUM"  width="180px">--%>
                                <%--<input type="hidden" id="busId" value="${agentBusInfos.id}">--%>
                                <%--<c:if test="${agentBusInfos.busStatus==0 || agentBusInfos.busStatus==2}">--%>
                                    <%--<shiro:hasPermission name="/agentEnter/busNum">--%>
                                        <%--<a href="javascript:void(0)" style="text-decoration:none;color:#333;" id="busNum">${agentBusInfos.busNum}</a>--%>
                                        <%--&lt;%&ndash;<c:if test="${not empty agentBusInfos.busNum && not empty agentBusInfos.busParent}">&ndash;%&gt;--%>
                                            <%--<!--<a href="javascript:void(0)" style="float: right" onclick="queryAgencyTermNum(this)">查询终端数量</a>-->--%>
                                        <%--&lt;%&ndash;</c:if>&ndash;%&gt;--%>
                                    <%--</shiro:hasPermission>--%>
                                <%--</c:if>--%>
                                <%--<c:if test="${agentBusInfos.busStatus==1}">--%>
                                    <%--<a href="javascript:void(0)" style="text-decoration:none;color:#333;" id="busNum">${agentBusInfos.busNum}</a>--%>
                                    <%--&lt;%&ndash;<c:if test="${not empty agentBusInfos.busNum && not empty agentBusInfos.busParent}">&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;<a href="javascript:void(0)" style="float: right" onclick="queryAgencyTermNum(this)">查询终端数量</a>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;</c:if>&ndash;%&gt;--%>
                                <%--</c:if>--%>
                            <%--</td>--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="/bus/busType">--%>
                        <%--<td>类型</td>--%>
                        <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSTYPE">--%>
                            <%--<c:forEach items="${busType}" var="busTypeItem">--%>
                                <%--<c:if test="${busTypeItem.dItemvalue== agentBusInfos.busType}">${busTypeItem.dItemname}</c:if>--%>
                            <%--</c:forEach>--%>
                        <%--</td>--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="/bus/busParent">--%>
                        <%--<td>上级代理</td>--%>
                        <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSPARENT"><agent:show type="agentBusIdForAgent" busId="${agentBusInfos.busParent}"/><shiro:hasPermission name="/agentEnter/agentQuery/AgentBusiInfoParentStracture">||<a href="javascript:void(0);" onclick="showSynRegionFrame({--%>
                                <%--target:this,--%>
                                <%--callBack:agentQueryBusTreeCallBach--%>
                                <%--},'/region/busTreee?currentId=${agentBusInfos.id}',false)">业务结构</a></shiro:hasPermission></td>--%>
                        <%--</shiro:hasPermission>--%>
                    <%--</tr>--%>
                    <%--<tr >--%>
                        <%--<shiro:hasPermission name="/bus/busRiskParent">--%>
                        <%--<td>风险承担所属代理商</td>--%>
                        <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSRISKPARENT">--%>
                            <%--<agent:show type="agentBusIdForAgent" busId="${agentBusInfos.busRiskParent}"/>--%>
                        <%--</td>--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="/bus/busActivationParent">--%>
                        <%--<td>激活及返现所属代理商</td>--%>
                        <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSACTIVATIONPARENT">--%>
                            <%--<agent:show type="agentBusIdForAgent" busId="${agentBusInfos.busActivationParent}"/>--%>
                        <%--</td>--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="/bus/busRegion">--%>
                        <%--<td>业务区域</td>--%>
                        <%--<td title="<agent:show type="posRegion" busId="${agentBusInfos.busRegion}"/>" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSREGION" >--%>
                            <%--<div onclick="showBusRegionFrame({target:this,bufData:'${agentBusInfos.busRegion}',but:false})">查看</div><input type="hidden" value="${agentBusInfos.busRegion}"/>--%>
                        <%--</td>--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="/bus/busRiskEmail">--%>
                        <%--<td>投诉及风险风控对接邮箱</td>--%>
                        <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSRISKEMAIL">${agentBusInfos.busRiskEmail}</td>--%>
                        <%--</shiro:hasPermission>--%>
                    <%--</tr>--%>
                    <%--<tr >--%>
                        <%--<shiro:hasPermission name="/bus/busContact">--%>
                        <%--<td>业务联系人</td>--%>
                        <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSCONTACT" ><desensitization:show type="name" value="${agentBusInfos.busContact}" jurisdiction="/agent/busNameSee"/></td>--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="/bus/busContactMobile">--%>
                        <%--<td>业务联系电话</td>--%>
                        <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSCONTACTMOBILE"><desensitization:show type="mobile" value="${agentBusInfos.busContactMobile}" jurisdiction="/agent/busMobileSee"/></td>--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="/bus/busContactEmail">--%>
                        <%--<td>分润对接邮箱</td>--%>
                        <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSCONTACTEMAIL">${agentBusInfos.busContactEmail}</td>--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="/bus/busContactPerson">--%>
                        <%--<td>业务对接人</td>--%>
                        <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSCONTACTPERSON"><desensitization:show type="name" value="${agentBusInfos.busContactPerson}" jurisdiction="/agent/busNameSee"/></td>--%>
                        <%--</shiro:hasPermission>--%>
                    <%--</tr>--%>
                    <%--<tr >--%>
                        <%--<shiro:hasPermission name="/bus/busSentDirectly">--%>
                        <%--<td>是否直发</td>--%>
                        <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSSENTDIRECTLY">--%>
                            <%--<c:forEach items="${yesOrNo}" var="yesOrNoItem">--%>
                                <%--<c:if test="${yesOrNoItem.dItemvalue== agentBusInfos.busSentDirectly}">${yesOrNoItem.dItemname}</c:if>--%>
                            <%--</c:forEach>--%>
                        <%--</td>--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="/bus/busDirectCashback">--%>
                        <%--<td>是否直接返现</td>--%>
                        <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSDIRECTCASHBACK">--%>
                            <%--<c:forEach items="${yesOrNo}" var="yesOrNoItem">--%>
                                <%--<c:if test="${yesOrNoItem.dItemvalue== agentBusInfos.busDirectCashback}">${yesOrNoItem.dItemname}</c:if>--%>
                            <%--</c:forEach>--%>
                        <%--</td>--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="/bus/busIndeAss">--%>
                        <%--<td>是否独立考核</td>--%>
                        <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSINDEASS">--%>
                            <%--<c:forEach items="${yesOrNo}" var="yesOrNoItem">--%>
                                <%--<c:if test="${yesOrNoItem.dItemvalue== agentBusInfos.busIndeAss}">${yesOrNoItem.dItemname}</c:if>--%>
                            <%--</c:forEach>--%>
                        <%--</td>--%>
                        <%--</shiro:hasPermission>--%>
                            <%--&lt;%&ndash;<td>是否开具分润发票</td>&ndash;%&gt;--%>
                            <%--&lt;%&ndash;<td>&ndash;%&gt;--%>
                            <%--&lt;%&ndash;<c:forEach items="${yesOrNo}" var="yesOrNoItem">&ndash;%&gt;--%>
                            <%--&lt;%&ndash;<c:if test="${yesOrNoItem.dItemvalue== agentBusInfos.cloInvoice}">${yesOrNoItem.dItemname}</c:if>&ndash;%&gt;--%>
                            <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
                            <%--&lt;%&ndash;</td>&ndash;%&gt;--%>

                        <%--<shiro:hasPermission name="/bus/cloReceipt">--%>
                        <%--<td>是否要求收据</td>--%>
                        <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="CLORECEIPT">--%>
                            <%--<c:forEach items="${yesOrNo}" var="yesOrNoItem">--%>
                                <%--<c:if test="${yesOrNoItem.dItemvalue== agentBusInfos.cloReceipt}">${yesOrNoItem.dItemname}</c:if>--%>
                            <%--</c:forEach>--%>
                        <%--</td>--%>
                        <%--</shiro:hasPermission>--%>
                    <%--</tr>--%>
                    <%--<tr >--%>
                            <%--&lt;%&ndash;<td>税点</td>&ndash;%&gt;--%>
                            <%--&lt;%&ndash;<td>${agentBusInfos.cloTaxPoint}</td>&ndash;%&gt;--%>
                        <%--<shiro:hasPermission name="/bus/cloPayCompany">--%>
                        <%--<td>打款公司</td>--%>
                        <%--<td>--%>
                            <%--<c:forEach items="${compList}" var="compListItem"  >--%>
                                <%--<c:if test="${compListItem.id== agentBusInfos.cloPayCompany}">${compListItem.comName}</c:if>--%>
                            <%--</c:forEach>--%>
                        <%--</td>--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="/bus/protocolRuleRel">--%>
                        <%--<td>分管协议</td>--%>
                        <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="ASSPROTOCOLID">--%>
                            <%--&lt;%&ndash;<c:forEach items="${ass}" var="assItem"  >&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<c:forEach items="${assProtoColRelList}" var="assProtoColRelListItem"  >&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<c:if test="${assProtoColRelListItem.agentBusinfoId== agentBusInfos.id}">&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;<c:if test="${assProtoColRelListItem.assProtocolId== assItem.id}">&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;${assItem.protocolDes}&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;</c:if>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;</c:if>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
                            <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
                            <%--${agentBusInfos.assProtocolMap.PROTOCOL_RULE_REL}--%>
                        <%--</td>--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="/bus/agZbh">--%>
                        <%--<td>财务编号</td>--%>
                        <%--<td  SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="AGZBH">${agentBusInfos.agZbh}</td>--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="/bus/busUseOrgan">--%>
                        <%--<td>使用范围</td>--%>
                        <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSUSEORGAN">--%>
                            <%--<c:forEach items="${useScope}" var="useScopeItem"  >--%>
                                <%--<c:if test="${useScopeItem.dItemvalue == agentBusInfos.busUseOrgan}">${useScopeItem.dItemname}</c:if>--%>
                            <%--</c:forEach>--%>
                        <%--</td>--%>
                        <%--</shiro:hasPermission>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<shiro:hasPermission name="/bus/busScope">--%>
                        <%--<td>业务范围</td>--%>
                        <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSSCOPE">--%>
                            <%--<c:forEach items="${busScope}" var="busScopeItem"  >--%>
                                <%--<c:if test="${busScopeItem.dItemvalue == agentBusInfos.busScope}">${busScopeItem.dItemname}</c:if>--%>
                            <%--</c:forEach>--%>
                        <%--</td>--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="/bus/dredgeS0">--%>
                        <%--<c:if test="${agentBusInfos.busPlatformType=='POS' || agentBusInfos.busPlatformType=='ZPOS' || agentBusInfos.busPlatformType=='ZHPOS' || agentBusInfos.busPlatformType=='SSPOS'}">--%>
                            <%--<td>是否开通S0</td>--%>
                            <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="DREDGES0">--%>
                                <%--<c:forEach items="${yesOrNo}" var="yesOrNoItem">--%>
                                    <%--<c:if test="${yesOrNoItem.dItemvalue == agentBusInfos.dredgeS0}">${yesOrNoItem.dItemname}</c:if>--%>
                                <%--</c:forEach>--%>
                            <%--</td>--%>
                        <%--</c:if>--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="/bus/busLoginNum">--%>
                            <%--<c:if test="${agentBusInfos.busPlatform!='100013'}">--%>
                                <%--<td>平台登陆账号</td>--%>
                                <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSLOGINNUM" >${agentBusInfos.busLoginNum}</td>--%>
                            <%--</c:if>--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="/bus/busStatus">--%>
                            <%--<td>业务状态</td>--%>
                            <%--<td  style="color: red" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSSTATUS" >--%>
                                <%--<select id="busStatus"  disabled="disabled">--%>
                                    <%--<c:forEach items="${busStatus}" var="busStatusItem"  >--%>
                                        <%--<option value="${busStatusItem.dItemvalue}" <c:if test="${busStatusItem.dItemvalue == agentBusInfos.busStatus}">selected="selected"</c:if>>${busStatusItem.dItemname}</option>--%>
                                    <%--</c:forEach>--%>
                                <%--</select>--%>
                            <%--</td>--%>
                        <%--</shiro:hasPermission>--%>
                    <%--</tr>--%>
                    <%--<c:if test="${agentBusInfos.busPlatformType=='POS' || agentBusInfos.busPlatformType=='ZPOS' || agentBusInfos.busPlatformType=='ZHPOS' || agentBusInfos.busPlatformType=='SSPOS'}">--%>
                    <%--<tr>--%>
                        <%--<shiro:hasPermission name="/bus/debitRateLower">--%>
                            <%--<td>借记费率下限（%）</td>--%>
                            <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="DEBITRATELOWER" >${agentBusInfos.debitRateLower}</td>--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="/bus/debitCapping">--%>
                            <%--<td>借记封顶额（元）</td>--%>
                            <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="DEBITCAPPING" >${agentBusInfos.debitCapping}</td>--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="/bus/debitAppearRate">--%>
                            <%--<td>借记出款费率（%）</td>--%>
                            <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="DEBITAPPEARRATE" >${agentBusInfos.debitAppearRate}</td>--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="/bus/creditRateFloor">--%>
                            <%--<td>贷记费率下限（%）</td>--%>
                            <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="CREDITRATEFLOOR" >${agentBusInfos.creditRateFloor}</td>--%>
                        <%--</shiro:hasPermission>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<shiro:hasPermission name="/bus/creditRateCeiling">--%>
                            <%--<td>贷记费率上限（%）</td>--%>
                            <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="CREDITRATEFLOOR" >${agentBusInfos.creditRateCeiling}</td>--%>
                        <%--</shiro:hasPermission>--%>
                    <%--</tr>--%>
                    <%--</c:if>--%>

                    <%--<c:if test="${agentBusInfos.busPlatformType=='RDBPOS'}">--%>
                        <%--<tr>--%>
                            <%--<td>终端数量下限</td>--%>
                            <%--<td colspan="7" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="TERMINALSLOWER" >${agentBusInfos.terminalsLower}</td>--%>
                        <%--</tr>--%>
                    <%--</c:if>--%>

                    <%--<shiro:hasPermission name="/bus/agDocDistrict">--%>
                        <%--<td>业务对接大区</td>--%>
                        <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="AGDOCDISTRICT">--%>
                            <%--<agent:show type="dept" busId="${agentBusInfos.agDocDistrict}" />--%>
                        <%--</td>--%>
                    <%--</shiro:hasPermission>--%>
                    <%--<shiro:hasPermission name="/bus/agDocPro">--%>
                        <%--<td>业务对接省区</td>--%>
                        <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="AGDOCPRO">--%>
                            <%--<agent:show type="dept" busId="${agentBusInfos.agDocPro}" />--%>
                        <%--</td>--%>
                    <%--</shiro:hasPermission>--%>
                    <%--<shiro:hasPermission name="/bus/organName">--%>
                        <%--<td>顶级</td>--%>
                        <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="ORGANNUM">--%>
                            <%--<c:forEach items="${organList}" var="organListItem">--%>
                                <%--<c:if test="${organListItem.orgId==agentBusInfos.organNum}">${organListItem.orgName}</c:if>--%>
                            <%--</c:forEach>--%>
                        <%--</td>--%>
                    <%--</shiro:hasPermission>--%>
                    <%--<shiro:hasPermission name="/bus/remitOrgan">--%>
                        <%--<td>出款机构</td>--%>
                        <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="FINACEREMITORGAN">--%>
                            <%--<c:forEach items="${finceOrganList}" var="organListItem">--%>
                                <%--<c:if test="${organListItem.orgId==agentBusInfos.finaceRemitOrgan}">${organListItem.orgName}</c:if>--%>
                            <%--</c:forEach>--%>
                        <%--</td>--%>
                    <%--</shiro:hasPermission>--%>

                    <%--<shiro:hasPermission name="/bus/yfpAgentColinfo">--%>
                    <%--<c:if test="${!empty agentBusInfos.agentColinfoList}">--%>
                        <%--<tr>--%>
                            <%--<td colspan="8" style="color: red">已分配收款账户：</td>--%>
                        <%--</tr>--%>
                        <%--<c:forEach items="${agentBusInfos.agentColinfoList}" var="agentColinfos">--%>
                            <%--<tr >--%>
                                <%--<td>收款账户类型</td>--%>
                                <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="CLOTYPE">--%>
                                    <%--<c:forEach items="${colInfoType}" var="colInfoTypeItem">--%>
                                        <%--<c:if test="${colInfoTypeItem.dItemvalue==agentColinfos.cloType}">${colInfoTypeItem.dItemname}</c:if>--%>
                                    <%--</c:forEach>--%>
                                <%--</td>--%>
                                <%--<td>收款账户名</td>--%>
                                <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="CLOREALNAME">${agentColinfos.cloRealname}</td>--%>
                                <%--<td>收款总开户行</td>--%>
                                <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="CLOBANK">${agentColinfos.cloBank}</td>--%>
                                <%--<td>收款开户支行</td>--%>
                                <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="CLOBANKBRANCH">${agentColinfos.cloBankBranch}</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                                <%--<td>收款账号</td>--%>
                                <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="CLOBANKACCOUNT">${agentColinfos.cloBankAccount}</td>--%>
                                <%--<td>备注</td>--%>
                                <%--<td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="REMARK">${agentColinfos.remark}</td>--%>
                            <%--</tr>--%>
                        <%--</c:forEach>--%>
                    <%--</c:if>--%>
                    <%--</shiro:hasPermission>--%>
                <%--</table>--%>
            <%--</div>--%>
        <%--</c:forEach>--%>
    <%--</c:if>--%>
<%--</div>--%>
