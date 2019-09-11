<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/validate.jsp" %>
<div class="easyui-panel" title="缴纳款项" data-options="iconCls:'fi-results'" id="capitalsDiv">
    <c:if test="${!empty capitals}">
    <c:forEach items="${capitals}" var="capitals">
    <table class="grid" id="capitalsId">
                <tr class="payType">
                    <shiro:hasPermission name="/capital/cType">
                        <td>缴纳款项</td>
                        <td>
                            <select SCAN="TRUE" MODEL="CAPITALS" MODELID="${capitals.id}" MODELKEY="CTYPE" name="cType" disabled="disabled" readonly="readonly">
                                <c:forEach items="${capitalType}" var="capitalTypeItem">
                                    <option value="${capitalTypeItem.dItemvalue}"   <c:if test="${capitalTypeItem.dItemvalue== capitals.cType}">selected="selected"</c:if>     >${capitalTypeItem.dItemname}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/capital/cAmount">
                        <td>缴纳金额</td>
                        <td  SCAN="TRUE" MODEL="CAPITALS" MODELID="${capitals.id}" MODELKEY="CAMOUNT" id="cAmountId">
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
                        <td id="cFqCountCId" SCAN="TRUE" MODEL="CAPITALS" MODELID="${capitals.id}" MODELKEY="CFQCOUNT" name="cFqCount">
                                ${capitals.cFqCount}
                        </td>
                    </shiro:hasPermission>
                </tr>
                <tr class="payUser">
                    <shiro:hasPermission name="/capital/cPayuser">
                        <td id="cPayuserC">打款人</td>
                        <td id="cPayuserCId" SCAN="TRUE" MODEL="CAPITALS" MODELID="${capitals.id}" MODELKEY="CPAYUSER" >
                                ${capitals.cPayuser}
                        </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/capital/cInCom">
                        <td id="cInComC">收款地方</td>
                        <td SCAN="TRUE" MODEL="CAPITALS" MODELID="${capitals.id}" MODELKEY="CINCOM" id="cInComCId" >
                            <select  id="cInCom" disabled="disabled" readonly="readonly">
                                <c:forEach items="${payCompList}" var="payCompItem">
                                    <option value="${payCompItem.id}"   <c:if test="${payCompItem.id==capitals.cInCom}">selected="selected"</c:if>     >${payCompItem.comName}</option>
                                </c:forEach>

                            </select>
                        </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/capital/cPaytime">
                        <td id="cPaytimeC">打款时间</td>
                        <td SCAN="TRUE" MODEL="CAPITALS" MODELID="${capitals.id}" MODELKEY="CPAYTIME" id="cPaytimeCId" >
                            <fmt:formatDate pattern="yyyy-MM-dd" value="${capitals.cPaytime}"/>
                        </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/capital/remark">
                        <td>备注</td>
                        <td SCAN="TRUE" MODEL="CAPITALS" MODELID="${capitals.id}" MODELKEY="REMARK" id="remarkId">
                                ${capitals.remark}
                        </td>
                    </shiro:hasPermission>
                </tr>
                <tr class="cinamout">
                    <shiro:hasPermission name="/capital/cInAmount">
                        <input type="hidden" value="${capitals.id}" name="id">
                        <c:if test="${!empty capitals.cInAmount}">
                            <td id="cInAmountC">实际到账金额</td>
                            <td SCAN="TRUE" MODEL="CAPITALS" MODELID="${capitals.id}" MODELKEY="CINAMOUNT" id="cInAmountCId" >
                                <input name="cInAmount" value="${capitals.cInAmount}" class="easyui-numberbox" readonly="readonly">
                            </td>
                        </c:if>
                        <c:if test="${empty capitals.cInAmount}">
                            <td id="cInAmount">实际到账金额</td>
                            <td id="cInAmountId">
                                <input name="cInAmount" type="text" class="easyui-numberbox" style="width:80px;" data-options="required:true,validType:['length[1,20]','Money']"/>
                            </td>
                        </c:if>
                    </shiro:hasPermission>

                    <shiro:hasPermission name="/capital/attFiles">
                        <c:if test="${!empty capitals.attachmentList}">
                            <c:forEach items="${capitals.attachmentList}" var="attachment">
                                <td>附件名称</td>
                                <td id="attName"  SCAN="TRUE" MODEL="CAPITALS" MODELID="${attachment.id}" MODELKEY="ATTACHMENT">
                                    <input type='hidden' name='capitalTableFiles' value='${attachment.id}' />
                                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true"  >${attachment.attName}
                                       </a>
                                </td>
                                <td id="attDbpath"><a href="<%=imgPath%>${attachment.attDbpath}" class="easyui-linkbutton" data-options="plain:true" target="_blank" >查看附件</a></td>
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
        $('#capitalsId .payType').each(function(i){
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



    function get_agentCapitalTask() {
        var formDataJson = [];
        $('#capitalsDiv #capitalsId').each(function(i){
            var data = {};
            data.cInAmount= $(this).find("input[name='cInAmount']").val();
            data.id=$(this).find("input[name='id']").val();
            data.cPayType =$(this).find("select[name='payType']").find("option:selected").val();
            data.cType=$(this).find("select[name='cType']").find("option:selected").val();
            data.cAmount=$(this).find("td[id='cAmountId']").text().trim();
            data.cFqCount=$(this).find("td[id='cFqCountCId']").text().trim();
            data.cPayuser=$(this).find("td[id='cPayuserCId']").text().trim();
            data.cInCom=$(this).find("select[id='cInCom']").find("option:selected").val();
            data.time=$(this).find("td[id='cPaytimeCId']").text().trim();
            data.remark=$(this).find("td[id='remarkId']").text().trim();
            var files =  $(this).find("td[id='attName']").find("input[name='capitalTableFiles']");
            var capitalTableFileTemp = [];
            for(var j=0;j<files.length;j++){
                capitalTableFileTemp.push($(files[j]).val());
            }
            if(capitalTableFileTemp.length>0)
                data.capitalTableFile=capitalTableFileTemp;
            formDataJson.push(data);
            console.log("@@"+data.id)
        });
        return formDataJson;
    }

</script>