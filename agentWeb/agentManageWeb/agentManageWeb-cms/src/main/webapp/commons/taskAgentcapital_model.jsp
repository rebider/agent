<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/validate.jsp" %>
<div class="easyui-panel" title="缴纳款项" data-options="iconCls:'fi-results'">
    <table class="grid" id="capitalsTableId">
        <c:if test="${!empty capitals}">
            <c:forEach items="${capitals}" var="capitals">
                <tr class="payType">
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
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/capital/cPayType">
                        <td>打款方式</td>
                        <td id="payType">
                            <select SCAN="TRUE" MODEL="CAPITALS" MODELID="${capitals.id}" MODELKEY="CPAYTYPE" disabled="disabled" readonly="readonly" name="payType">
                                <c:forEach items="${payType}" var="payTypeItem">
                                    <option value="${payTypeItem.dItemvalue}"   <c:if test="${payTypeItem.dItemvalue== capitals.cPayType}">selected="selected"</c:if>     >${payTypeItem.dItemname}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/capital/cFqCount">
                        <td id="cFqCountC">分期期数</td>
                        <td id="cFqCountCId" SCAN="TRUE" MODEL="CAPITALS" MODELID="${capitals.id}" MODELKEY="CFQCOUNT">
                                ${capitals.cFqCount}
                        </td>
                    </shiro:hasPermission>
                </tr>
                <tr class="payUser">
                    <shiro:hasPermission name="/capital/cPayuser">
                        <td id="cPayuserC">打款人</td>
                        <td id="cPayuserCId" SCAN="TRUE" MODEL="CAPITALS" MODELID="${capitals.id}" MODELKEY="CPAYUSER">
                                ${capitals.cPayuser}
                        </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/capital/cInCom">
                        <td id="cInComC">收款地方</td>
                        <td SCAN="TRUE" MODEL="CAPITALS" MODELID="${capitals.id}" MODELKEY="CINCOM" id="cInComCId">
                            <c:forEach items="${payCompList}" var="payCompItem">
                                <c:if test="${payCompItem.id==capitals.cInCom}">${payCompItem.comName}</c:if>
                            </c:forEach>
                        </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/capital/cPaytime">
                        <td id="cPaytimeC">打款时间</td>
                        <td SCAN="TRUE" MODEL="CAPITALS" MODELID="${capitals.id}" MODELKEY="CPAYTIME" id="cPaytimeCId">
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
                <tr class="cinamout">
                    <shiro:hasPermission name="/capital/cInAmount">
                      <input type="hidden" value="${capitals.id}" name="id">
                        <td id="cInAmount">实际到账金额</td>
                        <td id="cInAmountId">
                            <input name="cInAmount" value="<c:if test="${capitals.cInAmount!=0}">${capitals.cInAmount}</c:if>" type="text" class="easyui-numberbox" style="width:80px;" data-options="required:true,validType:['length[1,20]','Money']"/>
                        </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/capital/cloReviewStatus">
                        <td>审批状态</td>
                        <td SCAN="TRUE" MODEL="CAPITALS" MODELID="${capitals.id}" MODELKEY="REMARK">
                            <c:forEach items="${agStatusi}" var="agStatusi">
                               <c:if test="${agStatusi.dItemvalue== capitals.cloReviewStatus}">${agStatusi.dItemname}</c:if>
                           </c:forEach>
                        </td>
                    </shiro:hasPermission>
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
        $('#capitalsTableId .payType').each(function(i){
            var  payType = $(this).find("select[name='payType']").val();
            if(payType=='FRDK') { //分润抵扣
//分期期數
                $(this).find("td[id='cFqCountC']").show();
                $(this).find("td[id='cFqCountCId']").show();
//打款人
                $(this).next().find("td[id='cPayuserC']").hide();
                $(this).next().find("td[id='cPayuserCId']").hide();
                $(this).next().find("td[id='cInComC']").hide();
                $(this).next().find("td[id='cInComCId']").hide();

                $(this).next().find("td[id='cPaytimeC']").hide();
                $(this).next().find("td[id='cPaytimeCId']").hide();
                $(this).next().find("td[id='cPaytimeC']").next("td").remove();
                $(this).next().find("td[id='cPaytimeCId']").next("td").remove();
                $(this).next().find("td[id='cPaytimeC']").remove();
                $(this).next().find("td[id='cPaytimeCId']").remove();


                $(this).nextAll().find("td[id='cInAmount']").hide();
                $(this).nextAll().find("td[id='cInAmountId']").hide();
            }else if(payType=='YHHK') {//银行汇款

                $(this).find("td[id='cFqCountC']").hide();
                $(this).find("td[id='cFqCountCId']").hide();
                $(this).next().find("td[id='cPayuserC']").show();
                $(this).next().find("td[id='cPayuserCId']").show();
                $(this).next().find("td[id='cInComC']").show();
                $(this).next().find("td[id='cInComCId']").show();

                $(this).next().find("td[id='cPaytimeC']").show();
                $(this).next().find("td[id='cPaytimeCId']").show();


                $(this).nextAll().find("td[id='cInAmount']").show();
                $(this).nextAll().find("td[id='cInAmountId']").show();
            }
        });
    });



    function get_agentCapital() {
        var formDataJson = [];
        $('#capitalsTableId .cinamout').each(function(i){
            var data = {};
            data.cInAmount  = $(this).find("input[name='cInAmount']").val();
            data.id  =$(this).find("input[name='id']").val();
            data.cPayType   =  $(this).prev().prev().find("select[name='payType']").find("option:selected").val();
            formDataJson.push(data);
        });
        return formDataJson;
    }
</script>