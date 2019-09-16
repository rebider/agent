<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="easyui-panel" title="审批" data-options="iconCls:'fi-results'">
    <table class="grid" id="accountTableId">
        <tr>
            <td>欠款</td>
            <td>
                <input name="debt" type="text"
                       placeholder="请输入" class="easyui-validatebox" data-options="required:true"
                       style="width:160px;">
            </td>
            <td>欠票</td>
            <td>
                <input name="oweTicket" type="text"
                       placeholder="请输入" class="easyui-validatebox" data-options="required:true"
                       style="width:160px;">
            </td>
        </tr>
    </table>
</div>
<script>

    function get_addAppTable_FormDataItem() {
        var data = {};
        data.debt = $("input[name='debt']").val();
        data.oweTicket  = $("input[name='oweTicket']").val();
        return data;
    }

</script>