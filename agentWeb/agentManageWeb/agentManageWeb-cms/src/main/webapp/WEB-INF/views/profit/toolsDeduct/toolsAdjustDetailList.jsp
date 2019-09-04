<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var values=${profitDeduction.status};
    if (values == '0') {
        $("#status").html('未扣款');
    }else {
        $("#status").html('已扣款');
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="toolsDeductEditForm" method="post">
            <table class="grid">
                <tr>
                    <td>下月扣款月份：</td>
                    <td>${profitDeduction.deductionDate}</td>
                </tr>
                <tr>
                    <td>下月应扣款金额：</td>
                    <td>${profitDeduction.mustAmt}</td>
                </tr>
                <tr>
                    <td>备注：</td>
                    <td>${profitDeduction.remark}</td>
                </tr>
            </table>
        </form>
    </div>
</div>