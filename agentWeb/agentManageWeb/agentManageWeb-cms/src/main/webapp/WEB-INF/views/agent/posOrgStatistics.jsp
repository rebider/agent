<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow:scroll;padding: 3px;width: 400px;height:250px" >
        <form id="rulePlatformAddForm" method="post">
            <table class="grid">
                <c:if test="${platformType=='MPOS'}">
                    <c:forEach items="${statisticsList}" var="statisticsListVar">
                        <c:forEach items="${statisticsListVar}" var="statisticsListVarVar">
                            <c:if test="${statisticsListVarVar.key=='term'}">
                                <c:forEach items="${statisticsListVarVar.value}" var="statisticsListVarVarVar">
                                    <tr>
                                        <td style="width:100px">业务平台编号</td>
                                        <td>${statisticsListVarVarVar.agencyId}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px">终端类型名称</td>
                                        <td>${statisticsListVarVarVar.termTypeName}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px">终端数量</td>
                                        <td>${statisticsListVarVarVar.termNum}</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            —————————————————————————————
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                    <c:if test="${empty statisticsList}">
                        暂无数据
                    </c:if>
                </c:if>
                <c:if test="${platformType=='POS' || platformType=='ZPOS' || platformType=='RJPOS'}">
                    <c:forEach items="${statisticsMapList.organSnList}" var="statisticsMap">
                        <tr>
                            <td style="width:100px">机构Id</td>
                            <td>${statisticsMap.orgId}</td>
                        </tr>
                        <tr>
                            <td style="width:100px">机构名称</td>
                            <td>${statisticsMap.orgName}</td>
                        </tr>
                        <tr>
                            <td style="width:100px">类型|上级机构Id</td>
                            <td>${statisticsMap.busType}|${statisticsMap.supDorgId}</td>
                        </tr>
                        <tr>
                            <td style="width:100px">首商户建立日期</td>
                            <td>${statisticsMap.firstMerchTime}</td>
                        </tr>
                        <tr>
                            <td style="width:100px">机构建立时间</td>
                            <td>${statisticsMap.createTime}</td>
                        </tr>
                        <tr>
                            <td style="width:100px">普通机数量</td>
                            <td>${statisticsMap.termCount1}</td>
                        </tr>
                        <tr>
                            <td style="width:100px">瑞易送机器数量</td>
                            <td>${statisticsMap.termCount2}</td>
                        </tr>
                        <tr>
                            <td style="width:100px">智能pos数量</td>
                            <td>${statisticsMap.termCount3}</td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                —————————————————————————————
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${platformType=='RDBPOS'}">
                    <c:forEach items="${rdbStatisticsList}" var="rdbStatistics">
                        <c:forEach items="${rdbStatistics}" var="rdbStatis">
                            <tr>
                                <td style="width:100px">业务平台编码</td>
                                <td>${rdbStatis.agency_id}</td>
                            </tr>
                            <tr>
                                <td style="width:100px">终端名称</td>
                                <td>${rdbStatis.name}</td>
                            </tr>
                            <tr>
                                <td style="width:100px">终端数量</td>
                                <td>${rdbStatis.count}</td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </c:if>
                <c:if test="${platformType=='RHPOS'}">
                    <c:forEach items="${rhbStatisticsList}" var="rhbStatistics">
                        <tr>
                            <td style="width:100px">机构号</td>
                            <td>${rhbStatistics.agentId}</td>
                        </tr>
                        <tr>
                            <td style="width:100px">机构名称</td>
                            <td>${rhbStatistics.agentName}</td>
                        </tr>
                        <tr>
                            <td style="width:100px">上级机构id</td>
                            <td>${rhbStatistics.upAgentId}</td>
                        </tr>
                        <tr>
                            <td style="width:100px">机构建立时间</td>
                            <td>${rhbStatistics.creatTime}</td>
                        </tr>
                        <tr>
                            <td style="width:100px">终端总数</td>
                            <td>${rhbStatistics.totalCount}</td>
                        </tr>
                        <tr>
                            <td style="width:100px">激活终端总数</td>
                            <td>${rhbStatistics.activationCount}</td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                ————————————————————————————————————
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
            </table>
        </form>
    </div>
</div>