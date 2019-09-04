<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript">
    function alertMsg(msg) {
        parent.$.messager.alert('提示',msg, 'info');
    }
    $('#stagCount').combobox({
        onSelect:function(newValue){
            var stagAmt = $('#sumAmt').val()/newValue.value;
            $('#stagAmt').val(stagAmt.toFixed(2)) ;
        }
    });
</script>
<div class="easyui-panel" title="其他扣款分期" data-options="iconCls:'fi-results'">
    <form id="stagingForm" method="post">
    <input name="id" type="text" hidden="true" value="${staging.id}">
    <input name="sourceId" type="text" hidden="true" value="${staging.sourceId}">
    <table class="grid">
            <tr>
                <td>代理商编号</td>
                <td>${agentId}</td>
                <td>代理商名称</td>
                <td>${agentName}</td>
            </tr>
            <tr>
                <td>总金额</td>
                <td><input id="sumAmt" name="sumAmt" type="text"  readonly="true" value="${staging.sumAmt}"></td>
                <td>期数</td>
                <td >
                    <select id="stagCount" name="stagCount"   class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                        <option value="2" <c:if test="${staging.stagCount==2}"> selected = "selected" </c:if>>2</option>
                        <option value="3"  <c:if test="${staging.stagCount==3}"> selected = "selected" </c:if>>3</option>
                        <option value="4"  <c:if test="${staging.stagCount==4}"> selected = "selected" </c:if>>4</option>
                        <option value="5" <c:if test="${staging.stagCount==5}"> selected = "selected" </c:if>>5</option>
                        <option value="6"  <c:if test="${staging.stagCount==6}"> selected = "selected" </c:if>>6</option>
                        <option value="7"  <c:if test="${staging.stagCount==7}"> selected = "selected" </c:if>>7</option>
                        <option value="8" <c:if test="${staging.stagCount==8}"> selected = "selected" </c:if>>8</option>
                        <option value="9"  <c:if test="${staging.stagCount==9}"> selected = "selected" </c:if>>9</option>
                        <option value="10"  <c:if test="${staging.stagCount==10}"> selected = "selected" </c:if>>10</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>每期金额</td>
                <td><input id ="stagAmt" name="stagAmt" type="text" readonly value="${staging.stagAmt}"></td>
                <td>备注</td>
                <td>  <textarea name="remark" width=100>${staging.remark}</textarea></td>
            </tr>
    </table>
  </form>
</div>

