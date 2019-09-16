<%--
  Created by IntelliJ IDEA.
  User: renshenghao
  Date: 2019/02/26
  Time: 10:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var adjustList;
    $(function () {
        var adjustType=$('#adjustType').val();
        var url='';
        if (adjustType=='KS'){
            url='${path }/deductTaxDetail/adjustDetailList';
        }
        if (adjustType=='QP'){
            url='${path}/profit/invoiceDetail/adjustDetailList';
        }
        adjustList = $('#adjustList').datagrid({
            url : url,
            striped : true,
            queryParams:{'profitMonth':'${param.profitMonth}' ,'agentId':'${param.agentId}','parentAgentId':'${param.parentAgentId}'},
            fit : true,
            rownumbers : true,
            singleSelect : true,
            idField : 'ADJUST_AMT',
            columns : [ [ {
                title : '调整原因',
                field : 'ADJUST_RESON'
            } , {
                title : '调整金额',
                field : 'ADJUST_AMT'
            } , {
                title : '调整时间',
                field : 'ADJUST_TIME'
            }]],
            onLoadSuccess:function(){
                var data=adjustList.datagrid('getRows');
                if (data.length==0){
                    return;
                }
                var sum_adjustAmt =0;
                for (var i = 0; i <data.length; i++) {
                    sum_adjustAmt += data[i]['ADJUST_AMT']==''||data[i]['ADJUST_AMT']==null?parseFloat('0'):parseFloat(data[i]['ADJUST_AMT']);
                }
                adjustList.datagrid('appendRow', {
                    ADJUST_RESON: '合计',
                    ADJUST_AMT: sum_adjustAmt.toFixed(2)
                });
            }
        });
        });

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
<div id="" data-options="region:'west',border:true" style="width:100%;overflow: hidden; ">
    <input id="adjustType" type="hidden" value="${adjustType}">
    <table id="adjustList" data-options="fit:true,border:false"></table>
</div>
</div>