<shiro:hasPermission name="/order/busApproval">
<c:if test="${oRefundPriceDiff.applyCompType=='2'}">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="业务扣除款项" data-options="iconCls:'fi-results',tools:'#Agentcapital_model_tools'">
    <form id="refundPriceDiff_model_form">
    </form>
</div>

<div id="Agentcapital_model_tools">
    <a href="javascript:void(0)" class="icon-add" style="margin-right: 50px;" onclick="addRefundPriceDiffTable()"></a>
</div>
<div style="display: none" id="refundPriceDiff_bus_model_templet">
    <table class="grid">
        <tbody>
        <tr>
            <td>扣款类型：</td>
            <td>
                <select name="cType" style="width:160px;height:21px" >
                    <c:forEach items="${cType}" var="cType">
                        <option value="${cType.dItemvalue}">${cType.dItemname}</option>
                    </c:forEach>
                </select>
            </td>
            <td>金额：</td>
            <td>
                <input name="cAmount" type="text"  type="text" maxlength="11" precision="2" input-class="easyui-numberbox"   style="width:160px;"  data-options="required:true,validType:['length[1,11]','Money']"/>
                <input name="sourceId" type="hidden" value="${oRefundPriceDiff.id}"/>
            </td>
            <td>
                <a href="javascript:void(0)" class="addAgentcapitalTableDel-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="removeRefundPriceDiff_model(this)" >删除</a>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<script>

    //删除事件
    function removeRefundPriceDiff_model(t){
        $(t).parent().parent().parent().parent().remove();
    }

    function addRefundPriceDiffTable(){
        $("#refundPriceDiff_model_form").append($("#refundPriceDiff_bus_model_templet").html());
        var inputs = $("#refundPriceDiff_model_form .grid:last input");
        for(var i=0;i<inputs.length;i++){
            if($(inputs[i]).attr("input-class") && $(inputs[i]).attr("input-class").length>0)
            $(inputs[i]).addClass($(inputs[i]).attr("input-class"));
        }
        $.parser.parse($("#refundPriceDiff_model_form .grid:last"));
    }

    //解析打个table
    function get_addRefundPriceDiffTable_FormDataItem(table){
        var data = {};
        data.cType = $(table).find("select[name='cType']").val();
        data.cAmount = $(table).find("input[name='cAmount']").val();
        data.sourceId = $(table).find("input[name='sourceId']").val();
        return data;
    }

    //获取form数据
    function get_addRefundPriceDiffTable_FormData(){
        var addRefundPriceDiff_FormDataJson = [];
        var tables = $("#refundPriceDiff_model_form .grid");
        for (var  i=0;i<tables.length;i++){
            var table = tables[i];
            addRefundPriceDiff_FormDataJson.push(get_addRefundPriceDiffTable_FormDataItem(table));
        }
        return addRefundPriceDiff_FormDataJson;
    }
</script>
</c:if>
</shiro:hasPermission>