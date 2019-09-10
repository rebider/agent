<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="合同信息" data-options="iconCls:'fi-results',tools:'#AgentContractTable_model_tools'">
    <form id="AgentContract_model_form">
    </form>
</div>
<div id="AgentContractTable_model_tools">
    <a href="javascript:void(0)" class="icon-add" style="margin-right: 50px;" onclick="addAAgentContract_model_Table()"></a>
</div>
<div style="display: none;" id="AgentContractTable_model_templet">
    <table  class="grid">
        <tbody>
        <tr >
            <td>合同类型</td>
            <td>
                <select name="contType" style="width:160px;height:21px" >
                    <c:forEach items="${contractType}" var="ContractTypeItem"  >
                        <option value="${ContractTypeItem.dItemvalue}">${ContractTypeItem.dItemname}</option>
                    </c:forEach>
                </select>
            </td>
            <td>合同号</td>
            <td><input name="contNum" type="text"  input-class="easyui-numberbox"  style="width:160px;"  data-options="validType:'length[1,32]'"/></td>
            <td>合同签约时间</td>
            <td><input name="contDate" type="text" editable="false"  input-class="easyui-datebox"  style="width:160px;"  data-options="required:true" editable="false"></td>
            <td>合同到期时间</td>
            <td><input name="contEndDate" type="text"  editable="false" input-class="easyui-datebox"  style="width:160px;"  data-options="required:true" editable="false"></td>
        </tr>
        <tr>
            <td>是否附加协议</td>
            <td>
                <select name="appendAgree" style="width:160px;height:21px" >
                    <c:forEach items="${yesOrNo}" var="yesOrNoItem"  >
                        <option value="${yesOrNoItem.dItemvalue}">${yesOrNoItem.dItemname}</option>
                    </c:forEach>
                </select>
            </td>
            <td>分管协议</td>
            <td>
                <select name="agentAssProtocol" style="width:110px;height:21px" id="agentAssProtocol" onchange="assProSelect(this)">
                    <option value="">请选择</option>
                    <c:forEach items="${ass}" var="assListItem">
                        <option value="${assListItem.id}">${assListItem.protocolDes}</option>
                    </c:forEach>
                </select>
            </td>
            <td>备注</td>
            <td><input name="remark" type="text"   input-class="easyui-validatebox"  style="width:160px;"></td>
            <td class="attrInput" ></td>
            <td>
                <a href="javascript:void(0)" class="addAgentContractTableDel-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="removeAAgentContract_model_Table(this)" >删除</a>||
                <a href="javascript:void(0)" class="addAgentContractTableAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addAgentContractTable_uploadView(this)" >上传合同信息</a>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<script>
    function assProSelect(o) {
        var assPro = $(o).find("option:selected").val();
        var text ="<input name='protocolRuleValue' id='protocolRuleValue' class='easyui-validatebox' style='width:40px;' ";
        if(assPro==1 || assPro==''){
            $(o).parent().find("input[name='protocolRuleValue']").remove();
        }
        if(assPro==2){
            $(o).parent().find("input[name='protocolRuleValue']").remove();
            text += "placeholder='台'/>"
            $(o).parent().append(text);
            $.parser.parse($(o).parent());
        }
        if(assPro==3){
            $(o).parent().find("input[name='protocolRuleValue']").remove();
            text += "placeholder='元'/>"
            $(o).parent().append(text);
            $.parser.parse($(o).parent());
        }

    }

    function addAAgentContract_model_Table(){
        var html = $("#AgentContractTable_model_templet").html();
        $("#AgentContract_model_form").append(html);
        var inputs = $("#AgentContract_model_form .grid:last input");
        for(var i=0;i<inputs.length;i++){
            if($(inputs[i]).attr("input-class") && $(inputs[i]).attr("input-class").length>0)
            $(inputs[i]).addClass($(inputs[i]).attr("input-class"));
        }
        $.parser.parse($("#AgentContract_model_form .grid:last"));
    }
    function removeAAgentContract_model_Table(t){
        $(t).parent().parent().parent().parent().remove();
    }


    var addAgentContractTable_attrDom ;

    //上传窗口
    function addAgentContractTable_uploadView(t){
        addAgentContractTable_attrDom = $(t).parent().parent().find(".attrInput");
        multFileUpload(addAgentContractTable_jxkxUploadFile);
    }

    //附件解析
    function addAgentContractTable_jxkxUploadFile(data) {
        var jsondata = eval(data);
        for(var i=0;i<jsondata.length ;i++){
            $(addAgentContractTable_attrDom).append("<span onclick='removeAgentContractTable_jxkxUploadFile(this)'>"+jsondata[i].attName+"<input type='hidden' name='contractTableFile' value='"+jsondata[i].id+"' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }

    }
    function removeAgentContractTable_jxkxUploadFile(t){
        parent.$.messager.confirm('询问', '确定删除附件么？', function(b) {
            if (b) {
                $(t).remove();
            }
        });
    }
    //解析打个table
    function get_addAgentContractTable_FormDataItem(table){
        var data = {};
        data.contType = $(table).find("select[name='contType']").val();
        data.contNum = $(table).find("input[name='contNum']").val();
        data.contDate = $(table).find("input[name='contDate']").val();
        data.contEndDate = $(table).find("input[name='contEndDate']").val();
        data.remark = $(table).find("input[name='remark']").val();
        data.appendAgree = $(table).find("select[name='appendAgree']").val();
        data.protocolRuleValue = $(table).find("input[name='protocolRuleValue']").val();
        data.agentAssProtocol = $(table).find("select[name='agentAssProtocol']").val();
        var files =  $(table).find(".attrInput").find("input[name='contractTableFile']");
        var contractTableFileTemp = [];
        for(var j=0;j<files.length;j++){
            contractTableFileTemp.push($(files[j]).val());
        }
        if(contractTableFileTemp.length>0)
        data.contractTableFile=contractTableFileTemp;
        return data;
    }
    //获取form数据
    function get_addAgentContractTable_FormData(){
        var addAgentContractTable_FormDataJson = [];
        var tables = $("#AgentContract_model_form .grid");
        for (var  i=0;i<tables.length;i++){
            var table = tables[i];
            addAgentContractTable_FormDataJson.push(get_addAgentContractTable_FormDataItem(table));
        }
        return addAgentContractTable_FormDataJson;
    }
</script>

