<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="easyui-panel" title="缴纳款项" data-options="iconCls:'fi-results'">
    <table class="grid" id="capitalsTable">
        <c:if test="${!empty capitals}">
            <c:forEach items="${capitals}" var="capitals">
                <tr class="typeId">
                    <shiro:hasPermission name="/capital/cType">
                    <td>缴纳款项</td>
                    <td SCAN="TRUE" MODEL="CAPITALS" MODELID="${capitals.id}" MODELKEY="CTYPE">
                        <c:forEach items="${capitalType}" var="capitalTypeItem">
                            <c:if test="${capitalTypeItem.dItemvalue== capitals.cType}">${capitalTypeItem.dItemname}</c:if>
                        </c:forEach>
                    </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/capital/cAmount">
                    <td>缴纳金额</td>
                    <td  SCAN="TRUE" MODEL="CAPITALS" MODELID="${capitals.id}" MODELKEY="CAMOUNT">
                        ${capitals.cAmount}
                    </td>
                    <td>可用金额</td>
                    <td  SCAN="TRUE" MODEL="CAPITALS" MODELID="${capitals.id}" MODELKEY="CAMOUNT">
                            ${capitals.cFqInAmount}
                    </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/capital/cPayType">
                    <td>打款方式</td>
                    <td  id="payType" >
                        <select SCAN="TRUE" MODEL="CAPITALS" MODELID="${capitals.id}" MODELKEY="CPAYTYPE" disabled="disabled" readonly="readonly" name="payType">
                            <c:forEach items="${payType}" var="payTypeItem">
                                <option value="${payTypeItem.dItemvalue}"   <c:if test="${payTypeItem.dItemvalue== capitals.cPayType}">selected="selected"</c:if>     >${payTypeItem.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/capital/cFqCount">
                    <td id="cFqCount">分期期数</td>
                    <td id="cFqCountId" SCAN="TRUE" MODEL="CAPITALS" MODELID="${capitals.id}" MODELKEY="CFQCOUNT">
                            ${capitals.cFqCount}
                    </td>
                    </shiro:hasPermission>
                </tr>
                <tr>
                    <shiro:hasPermission name="/capital/cPayuser">
                    <td id="cPayuser">打款人</td>
                    <td id="cPayuserId" SCAN="TRUE" MODEL="CAPITALS" MODELID="${capitals.id}" MODELKEY="CPAYUSER">
                            ${capitals.cPayuser}
                    </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/capital/cInCom">
                    <td id="cInCom">收款地方</td>
                    <td SCAN="TRUE" MODEL="CAPITALS" MODELID="${capitals.id}" MODELKEY="CINCOM" id="cInComId">
                        <c:forEach items="${payCompList}" var="payCompItem">
                            <c:if test="${payCompItem.id==capitals.cInCom}">${payCompItem.comName}</c:if>
                        </c:forEach>
                    </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/capital/cPaytime">
                    <td id="cPaytime">打款时间</td>
                    <td SCAN="TRUE" MODEL="CAPITALS" MODELID="${capitals.id}" MODELKEY="CPAYTIME" id="cPaytimeId">
                            <fmt:formatDate pattern="yyyy-MM-dd" value="${capitals.cPaytime}"/>
                    </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/capital/remark">
                    <td>备注</td>
                    <td SCAN="TRUE" MODEL="CAPITALS" MODELID="${capitals.id}" MODELKEY="REMARK">
                        ${capitals.remark}
                    </td>
                    </shiro:hasPermission>
                </tr>
                <tr>
                    <shiro:hasPermission name="/capital/cInAmount">
                    <td id="cInAmountC">实际到账金额</td>
                    <td SCAN="TRUE" MODEL="CAPITALS" MODELID="${capitals.id}" MODELKEY="CINAMOUNT" id="cInAmountCId">
                            ${capitals.cInAmount}
                    </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/capital/cloReviewStatus">
                    <td>审批状态</td>
                    <td SCAN="TRUE" MODEL="AGENTCONTRACTS" MODELID="${capitals.id}" MODELKEY="CLOREVIEWSTATUS">
                        <c:forEach items="${agStatusi}" var="agStatusi">
                            <c:if test="${agStatusi.dItemvalue==capitals.cloReviewStatus}">${agStatusi.dItemname}</c:if>
                        </c:forEach>
                    </td>
                    </shiro:hasPermission>
                </tr>
                <tr>
                    <shiro:hasPermission name="/capital/attFiles">
                        <c:if test="${!empty capitals.attachmentList}">
                            <c:forEach items="${capitals.attachmentList}" var="attachment">
                                <td>附件名称</td>
                                <td SCAN="TRUE" MODEL="CAPITALS" MODELID="${attachment.id}" MODELKEY="ATTACHMENT"><a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true"  >${attachment.attName}</a></td>
                                <td><a href="<%=imgPath%>${attachment.attDbpath}" class="easyui-linkbutton" data-options="plain:true" target="_blank" >查看附件</a></td>
                            </c:forEach>
                        </c:if>
                    </shiro:hasPermission>
                </tr>
            </c:forEach>
        </c:if>
    </table>
</div>
<script>
    $(function(){
        $('#capitalsTable .typeId').each(function(i){
            var  payType = $(this).find("select[name='payType']").val();
            if(payType=='FRDK') { //分润抵扣
//分期期數
                $(this).find("td[id='cFqCount']").show();
                $(this).find("td[id='cFqCountId']").show();
//打款人
                $(this).next().find("td[id='cPayuser']").hide();
                $(this).next().find("td[id='cPayuserId']").hide();
                $(this).next().find("td[id='cInCom']").hide();
                $(this).next().find("td[id='cInComId']").hide();

                $(this).next().find("td[id='cPaytime']").hide();
                $(this).next().find("td[id='cPaytimeId']").hide();
                $(this).next().find("td[id='cPaytime']").next("td").remove();
                $(this).next().find("td[id='cPaytimeId']").next("td").remove();
                $(this).next().find("td[id='cPaytime']").remove();
                $(this).next().find("td[id='cPaytimeId']").remove();


                $(this).nextAll().find("td[id='cInAmountC']").hide();
                $(this).nextAll().find("td[id='cInAmountCId']").hide();
            }else if(payType=='YHHK') {//银行汇款

                $(this).find("td[id='cFqCount']").hide();
                $(this).find("td[id='cFqCountId']").hide();
                $(this).next().find("td[id='cPayuser']").show();
                $(this).next().find("td[id='cPayuserId']").show();
                $(this).next().find("td[id='cInCom']").show();
                $(this).next().find("td[id='cInComId']").show();

                $(this).next().find("td[id='cPaytime']").show();
                $(this).next().find("td[id='cPaytimeId']").show();


                $(this).nextAll().find("td[id='cInAmountC']").show();
                $(this).nextAll().find("td[id='cInAmountCId']").show();
            }
        });
    });

</script>