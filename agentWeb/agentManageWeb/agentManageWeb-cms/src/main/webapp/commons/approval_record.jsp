<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<table class="grid">
    <tr >
        <td width="5%">序号</td>
        <td width="5%">审批部门</td>
        <shiro:hasPermission name="/approval/approvalPerson">
        <td width="5%">审批人</td>
        </shiro:hasPermission>
        <td width="10%">审批时间</td>
        <td width="5%">审批结果</td>
        <td width="50%">审批意见</td>
    </tr>
    <c:if test="${!empty actRecordList}">
        <c:forEach items="${actRecordList}" var="actRecord" varStatus="status">
            <tr >
                <td>
                   ${status.index +1}
                </td>
                <td>
                   ${actRecord.approvalDep}
                </td>
              <shiro:hasPermission name="/approval/approvalPerson">
                <td>
                   ${actRecord.approvalPerson}
                </td>
              </shiro:hasPermission>
                <td>
                   ${actRecord.createTime}
                </td>
                <td>
                    <c:forEach items="${allApprovalType}" var="appTypeItem"  >
                        <c:if test="${appTypeItem.dItemvalue==actRecord.rs}">
                            ${appTypeItem.dItemname}
                        </c:if>
                    </c:forEach>
                </td>
                <td>
                   ${actRecord.approvalOpinion}
                </td>
            </tr>
        </c:forEach>
    </c:if>
</table>