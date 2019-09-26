<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="终端明细信息"  data-options="iconCls:'fi-results'">
    <table class="grid">
        <c:if test="${!empty terminalTransfer.terminalTransferDetailList}">
            <c:forEach items="${terminalTransfer.terminalTransferDetailList}" var="terminalTransferDetail" >
                <tr >
                    <td>SN开始</td>
                    <td>
                        ${terminalTransferDetail.snBeginNum}
                    </td>
                    <td>SN结束</td>
                    <td>
                        ${terminalTransferDetail.snEndNum}
                    </td>
                    <td>数量</td>
                    <td>
                        ${terminalTransferDetail.comSnNum}
                    </td>
                    <%--<td>厂商</td>--%>
                    <%--<td>--%>
                        <%--${terminalTransferDetail.proCom}--%>
                    <%--</td>--%>
                    <%--<td>型号</td>--%>
                    <%--<td>--%>
                        <%--${terminalTransferDetail.proModel}--%>
                    <%--</td>--%>
                    <td>平台类型</td>
                    <td>
                        <c:forEach items="${platformTypeList}" var="platformType">
                            <c:if test="${terminalTransferDetail.platformType==platformType.key}">${platformType.value}</c:if>
                        </c:forEach>
                    </td>
                </tr>
                <tr >
                    <td>原业务平台编码</td>
                    <td>
                        ${terminalTransferDetail.originalOrgId}
                    </td>
                    <td>原业务平台名称</td>
                    <td>
                        ${terminalTransferDetail.originalOrgName}
                    </td>
                    <td>原业务类型</td>
                    <td>
                            ${terminalTransferDetail.originalType}
                    </td>
                    <td>划拨下限</td>
                    <td>
                            ${terminalTransferDetail.snCount}
                    </td>
                </tr>
                <tr>
                    <td>目标业务平台编码</td>
                    <td>
                        ${terminalTransferDetail.goalOrgId}
                    </td>
                    <td>目标业务平台名称</td>
                    <td>
                        ${terminalTransferDetail.goalOrgName}
                    </td>
                    <td>目标业务类型</td>
                    <td>
                            ${terminalTransferDetail.goalType}
                    </td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
</div>