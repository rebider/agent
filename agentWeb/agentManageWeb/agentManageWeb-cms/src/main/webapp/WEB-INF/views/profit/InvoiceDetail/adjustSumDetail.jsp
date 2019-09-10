
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var adjustList;
    $(function () {
        adjustList = $('#adjustList').datagrid({
            url : '${path }/profit/InvoiceSumController/adjustdetail',
            striped : true,
            queryParams:{'id':'${param.id}'},
            fit : true,
            rownumbers : true,
            singleSelect : true,
            idField : 'adjustAmt',
            columns : [ [ {
                title : '调整原因',
                field : 'adjustReson'
            } , {
                title : '调整金额',
                field : 'adjustAmt'
            } , {
                title : '调整时间',
                field : 'adjustTime'
            }]]

        });
        });

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
<div id="" data-options="region:'west',border:true" style="width:100%;overflow: hidden; ">
    <table id="adjustList" data-options="fit:true,border:false"></table>
</div>
</div>