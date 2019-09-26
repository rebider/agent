<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<%--历史数据展示--%>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
    <c:forEach var="historyItem" items="${history}">
        <div>
            <table>
            <tr><td>操作人:</td><td style="color: red;">${historyItem.cUserName}</td><td>操作时间:</td><td>${historyItem.cTime}</td></tr>
            </table>
            <div>
                <table >
                    <tr style="height:20px;background-color: lightgrey;">
                        <c:if test="${!empty historyItem.dataCotentObj}">
                            <c:forEach var="i" items="${historyItem.dataCotentObj}">
                             <td >
                                 <div style="width: 80px;height:30px;overflow:hidden;" title="${i.key}">
                                    <agent:show type="FieldTranslate" busId="${i.key}"/>
                                </div>
                             </td>
                             <td>
                                <div style="width:100px;height:30px;overflow:hidden;" title="${i.value}">${i.value}</div>
                             </td>
                            </c:forEach>
                        </c:if>
                    </tr>
                </table>

            </div>
        </div>
    </c:forEach>

    </div>
</div>
