<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="old_order_return" class="easyui-panel" title="退款信息" style="background:#fafafa;" data-options="iconCls:'fi-results'">
    <table class="grid" style="width: 100%">
            <tr>
                <td>退货手续费:</td>
                <td>${old_order_return_info_detail_item.returnCount}</td>
                <td>起始SN:</td>
                <td>${old_order_return_info_detail_item.beginSn}</td>
                <td>结束SN:</td>
                <td>${old_order_return_info_detail_item.endSn}</td>
            </tr>
    </table>
</div>
<script type="text/javascript">
</script>