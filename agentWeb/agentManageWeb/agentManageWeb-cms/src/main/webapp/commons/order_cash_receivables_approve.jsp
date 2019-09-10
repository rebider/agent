<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="打款记录实际到账时间"  data-options="iconCls:'fi-wrench'" >
<table class="grid" id="${paylist_model}">
    <c:if test="${not empty paylist}">
    <tr>
        <td style="text-align: center;">打款方式:</td>
        <td style="text-align: center;">打款金额:</td>
        <td style="text-align: center;">审核状态:</td>
        <td style="text-align: center;">收款公司:</td>
        <td style="text-align: center;">打款人:</td>
        <td style="text-align: center;">打款时间:</td>
        <td style="text-align: center;">实际到账时间:</td>
    </tr>
    </c:if>
    <c:forEach items="${paylist_approve}" var="paylistItem">
    <tr>
        <td style="text-align: center;">
            <c:forEach items="${payTypeSelect}" var="payTypes">
                <c:if test="${paylistItem.payType==payTypes.key}">${payTypes.value}</c:if>
            </c:forEach>
        </td>
        <td style="text-align: center;" >${paylistItem.amount}/元</td>
        <td style="text-align: center;"><agent:show type="approve" busId="${paylistItem.reviewStatus}" /></td>
        <c:if test="${paylistItem.payType=='YHHK'}">
            <td style="text-align: center;">
                <c:forEach var="recCompListItem" items="${recCompList}" > <c:if test="${recCompListItem.id==paylistItem.collectCompany}"> ${recCompListItem.comName} </c:if> </c:forEach>
            </td>
            <td style="text-align: center;">${paylistItem.payUser}</td>
            <td style="text-align: center;"> <fmt:formatDate pattern="yyyy-MM-dd" value="${paylistItem.payTime}" /></td>
            <td style="text-align: center;">
                <input  class="easyui-datebox dyTime" name="paylist_realRecTime" id="${paylist_model}_paylist_realRecTime"  editable="false"/>
                <input type="hidden" value="${paylistItem.id}" name="paylist_id" />
            </td>
        </c:if>
    </tr>
    </c:forEach>
</table>
    <script type="application/javascript">
         //获取审核填写的实际到账时间
         function getApproveCashPayListRealRecTime(paylist_model){
             var realRecTime_paylist_approve = [];
             var trs = $("#"+paylist_model).find("tr");
             if(trs.length>0){
                 for(var i=0;i<trs.length;i++){
                     var paylist_realRecTime = $(trs[i]).find("#${paylist_model}_paylist_realRecTime");
                     var paylist_id = $(trs[i]).find("input[name='paylist_id']");
                     if(paylist_realRecTime.length==0){
                         continue;
                     }
                     if(paylist_id.length==0){
                         continue;
                     }
                     var paylist_realRecTime_value =  $(paylist_realRecTime).datebox("getValue");
                     var id_value =  $(paylist_id).val();
                     realRecTime_paylist_approve.push({realRecTime:paylist_realRecTime_value,id:id_value});
                 }
             }
             return realRecTime_paylist_approve;
         }
    </script>
</div>
