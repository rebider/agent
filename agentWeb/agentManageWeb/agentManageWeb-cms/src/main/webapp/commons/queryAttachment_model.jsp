<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:if test="${!empty attachment}">
<div class="easyui-panel" title="附件信息"  data-options="iconCls:'fi-results'">
    <table class="grid">
        <shiro:hasPermission name="/base/attFiles">
            <c:forEach items="${attachment}" var="attachment" >
                <tr >
                    <td>附件名称</td>
                    <td SCAN="TRUE" MODEL="AGENT" MODELID="${attachment.id}" MODELKEY="ATTACHMENT"><a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true">${attachment.attName}</a></td>
                    <td><a href="<%=imgPath%>${attachment.attDbpath}" class="easyui-linkbutton" data-options="plain:true" target="_blank" >查看附件</a></td>
                </tr>
            </c:forEach>
        </shiro:hasPermission>
    </table>
</div>
</c:if>
