<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="easyui-panel" title="机构详情" data-options="iconCls:'fi-results'">
    <form id="OrganizationTable_model_form">
    </form>
</div>
<div style="display: none;" id="OrganizationTable_model_templet">
    <table class="grid">
        <tbody>
        <c:if test="${!empty Organization}">
            <c:forEach items="${Organization}" var="Organization">
                <tr>
                    <td width="150px">机构名称</td>
                    <td width="200px">
                            ${Organization.orgName}
                    </td>
                    <td>云账户名</td>
                    <td>${Organization.accountName}</td>
                    <td width="150px">云账号</td>
                    <td width="200px">
                            ${Organization.accountNum}
                    </td>
                </tr>


                <tr>
                    <td>云账户开户行</td>
                    <td>${Organization.accountBank}</td>
                    <td width="150px">银行卡号</td>
                    <td width="200px">
                            ${Organization.bankCard}
                    </td>
                    <td>代理商编码</td>
                    <td>${Organization.agentId}</td>
                </tr>

                <tr>

                    <td>开户行地区</td>
                    <td>
                        <agent:show type="region" busId="${Organization.bankRegion}"/>
                    </td>
                    <td>收款账户名</td>
                    <td>${Organization.cloRealname}</td>
                    <td>收款账户类型</td>
                    <td>
                        <select name="cloType" style="width:160px;height:21px" disabled="disabled">
                            <option value="1">对公</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>收款银行总行</td>
                    <td>
                            ${Organization.cloBank}
                        <input name="cloBankCode" type="hidden">
                    </td>
                    <td>收款银行支行</td>
                    <td class="branchTd">
                            ${Organization.cloBankBranch}
                    </td>
                    <td>总行联行号</td>
                    <td>${Organization.allLineNum}</td>
                </tr>
                <tr>
                    <td>支行联行号</td>
                    <td>${Organization.branchLineNum}</td>
                </tr>
                <tr>
                    <td>业务平台</td>
                    <td colspan="3">
                        <c:forEach items="${ablePlatForm}" var="ablePlatFormItem" varStatus="status">
                            <c:if test="${status.count eq 1 || (status.count-1) % 3 eq 0}">
                                <tr>
                            </c:if>
                            <td>
                            <input name="platId" type="checkbox" id="platId"
                                   value="${ablePlatFormItem.platformNum}"/>
                            ${ablePlatFormItem.platformName}
                            </td>
                            <td>
                            <input name="businessNum_${ablePlatFormItem.platformNum}" type="text"
                                   input-class="easyui-validatebox"/>
                            </td>
                            <c:if test="${status.count % 3 eq 0 || status.count eq 3}">
                                </tr>
                            </c:if>
                        </c:forEach>
                    </td>

                </tr>
                <tr>
                    <td>备注</td>
                    <td>${Organization.remark}</td>
                </tr>
                <tr>
                    <c:if test="${!empty Organization}">
                        <c:if test="${!empty Organization.attachmentList}">
                            <td>附件</td>
                            <td colspan="7">
                                <c:forEach items="${Organization.attachmentList}" var="attachment">
                                    ${attachment.attName}
                                    <span><a href="<%=imgPath%>${attachment.attDbpath}" class="easyui-linkbutton"
                                             data-options="plain:true" target="_blank">查看附件</a></span>
                                </c:forEach>
                            </td>
                        </c:if>
                    </c:if>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
</div>
<script>
    var cloBankId = "";
    $(function () {
        addAgentColinfoTable_model();
        var boxObj = $("input:checkbox[name='platId']");  //获取所有的复选框
        var expresslist = '${platId}'; //用el表达式获取在控制层存放的复选框的值为字符串类型
        var businessNum = '${businessNum}';
        var express = expresslist.split(','); //去掉它们之间的分割符“，”
        var businessNums = businessNum.split(','); //去掉它们之间的分割符“，”
        for (i = 0; i < boxObj.length; i++) {
            for (j = 0; j < express.length; j++) {
                if (boxObj[i].value == express[j])  //如果值与修改前的值相等
                {
                    $("input[name='businessNum_"+express[j]+"']").val(businessNums[j]);
                    boxObj[i].checked = true;
                    break;
                }
            }
        }
    });

    function addAgentColinfoTable_model() {
        $("#OrganizationTable_model_form").append($("#OrganizationTable_model_templet").html());
        var inputs = $("#OrganizationTable_model_form .grid:last input");
        for (var i = 0; i < inputs.length; i++) {
            if ($(inputs[i]).attr("input-class") && $(inputs[i]).attr("input-class").length > 0)
                $(inputs[i]).addClass($(inputs[i]).attr("input-class"));
        }
        $.parser.parse($("#OrganizationTable_model_form .grid:last"));
        addAgentColinfoTableAttr_colchange();
    }


    function addAgentColinfoTableAttr_colchange() {
        $("select[name='cloType']").change();
    }


</script>
