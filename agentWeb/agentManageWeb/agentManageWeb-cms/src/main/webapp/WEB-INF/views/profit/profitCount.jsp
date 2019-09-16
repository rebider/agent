<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script></script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;">
        <form id="adjustForm" method="post">
            <input type="hidden" id="deductTaxID" name="id" value="${id}">
            <table class="grid">
                <tr>
                    <td>笔数：</td>
                    <td>
                        <input id="totalNum" name="totalNum" value="${totalNum}" type="text" readonly style="width:80px;" />&nbsp;笔
                    </td>
                </tr>
                <tr>
                    <td>金额：</td>
                    <td>
                        <input id="totalMoney" name="totalMoney" value="${totalMoney}" type="text" readonly style="width:80px;">&nbsp;元
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>