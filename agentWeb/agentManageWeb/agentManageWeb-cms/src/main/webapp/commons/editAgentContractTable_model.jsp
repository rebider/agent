<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="合同信息" data-options="iconCls:'fi-results',tools:'#editAgentContractTable_model_tools'">
    <form id="editAgentContract_model_form">

        <c:if test="${!empty agentContracts}">
            <c:forEach items="${agentContracts}" var="agentContracts" >
                <table  class="grid">
                    <tbody>
                    <tr >
                        <td>合同类型<input name="id" type="hidden" value="${agentContracts.id}" /></td>
                        <td>
                            <select name="contType" style="width:160px;height:21px" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentContractInfo">disabled="true"</shiro:lacksPermission>>

                                <c:forEach items="${contractType}" var="contractTypeItem">
                                    <option value="${contractTypeItem.dItemvalue}"<c:if test="${contractTypeItem.dItemvalue==agentContracts.contType}">selected="selected"</c:if>>${contractTypeItem.dItemname}</option>
                                </c:forEach>

                            </select>
                        </td>
                        <td>合同号</td>
                        <td><input name="contNum" type="text"  class="easyui-numberbox"  style="width:160px;"   data-options="validType:'length[1,32]'" value="${agentContracts.contNum}" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentContractInfo">readonly="readonly"</shiro:lacksPermission> /></td>
                        <td>合同签约时间</td>
                        <td><input name="contDate" type="text" editable="false"  class="easyui-datebox"  style="width:160px;"   value="<fmt:formatDate pattern="yyyy-MM-dd" value="${agentContracts.contDate}"  />" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentContractInfo">readonly="readonly"</shiro:lacksPermission>></td>
                        <td>合同到期时间</td>
                        <td><input name="contEndDate" type="text"  editable="false" class="easyui-datebox"  style="width:160px;"  value="<fmt:formatDate pattern="yyyy-MM-dd" value="${agentContracts.contEndDate}" />" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentContractInfo">readonly="readonly"</shiro:lacksPermission>></td>
                    </tr>
                    <tr>
                        <td>是否附加协议</td>
                        <td>
                            <select name="appendAgree" style="width:160px;height:21px" >
                                <c:forEach items="${yesOrNo}" var="yesOrNoItem"  >
                                    <option value="${yesOrNoItem.dItemvalue}" <c:if test="${yesOrNoItem.dItemvalue==agentContracts.appendAgree}">selected="selected"</c:if>>${yesOrNoItem.dItemname}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>备注</td>
                        <td><input name="remark" type="text"   class="easyui-validatebox"  style="width:160px;"  value="${agentContracts.remark}" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentContractInfo">readonly="readonly"</shiro:lacksPermission>></td>
                        <td>是否有效</td>
                        <td>
                            <select name="status" style="width:160px;height:21px" >
                                <c:forEach items="${yesOrNoIsYes}" var="yesOrNoItem"  >
                                    <option value="${yesOrNoItem.dItemvalue}">${yesOrNoItem.dItemname}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>分管协议</td>
                        <td>
                            <select id="agentAssProtocol1" name="agentAssProtocol" style="width:110px;height:21px" onchange="assProSelect(this)" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentBusiInfo"> disabled="true" </shiro:lacksPermission>>
                                <option value="">请选择</option>
                                <c:forEach items="${ass}" var="assListItem"  >
                                    <option value="${assListItem.id}"
                                            <c:if test="${assListItem.id==agentContracts.assProtocolMap.ID}">
                                                selected="selected"
                                            </c:if>
                                    >${assListItem.protocolDes}</option>
                                </c:forEach>
                            </select>
                            <c:if test="${agentContracts.assProtocolMap.ID=='2' || agentContracts.assProtocolMap.ID=='3'}">
                                <input name='protocolRuleValue' id='protocolRuleValue' class='easyui-validatebox' style='width:70px;' value="${agentContracts.assProtocolMap.PROTOCOL_RULE_VALUE}"
                                       placeholder='<c:if test="${agentContracts.assProtocolMap.ID=='2'}">台</c:if><c:if test="${agentContracts.assProtocolMap.ID=='3'}">元</c:if>' >
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td>附件</td>
                        <td class="attrInput" >
                            <c:if test="${!empty agentContracts.attachmentList}">
                                <c:forEach items="${agentContracts.attachmentList}" var="attachment">
                                        <span
                                                <shiro:hasPermission name="/agentEnter/agentEdit/AgentContractInfo">onclick='removeaddEditAgentContractTable_jxkxUploadFile(this)'</shiro:hasPermission>
                                        >${attachment.attName}<input type='hidden' name='contractTableFile' value='${attachment.id}' /></span>
                                </c:forEach>
                            </c:if>
                        </td>
                        <td>
                            <shiro:hasPermission name="/agentEnter/agentEdit/AgentContractInfo">
                                <a href="javascript:void(0)" class="editAgentContractTableDel-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="removeEditAAgentContract_model_Table(this)" >删除</a>||
                                <a href="javascript:void(0)" class="editAgentContractTableAddAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addEditAgentContractTable_uploadView(this)" >上传合同信息</a>
                            </shiro:hasPermission>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </c:forEach>
        </c:if>

    </form>
</div>
<shiro:hasPermission name="/agentEnter/agentEdit/AgentContractInfo">
    <div id="editAgentContractTable_model_tools">
        <a href="javascript:void(0)" class="icon-add" style="margin-right: 50px;" onclick="addEditAAgentContract_model_Table()"></a>
    </div>
</shiro:hasPermission>


<div style="display: none;" id="editAgentContractTable_model_templet">
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
            <td><input name="contDate" type="text" editable="false"  input-class="easyui-datebox"  style="width:160px;"  data-options="required:true"></td>
            <td>合同到期时间</td>
            <td><input name="contEndDate" type="text"  editable="false" input-class="easyui-datebox"  style="width:160px;"  data-options="required:true"></td>
        </tr>
        <tr>
            <td>是否附加协议</td>
            <td>
                <select name="appendAgree" style="width:160px;height:21px" >
                    <c:forEach items="${yesOrNo}" var="yesOrNoItem"  >
                        <option value="${yesOrNoItem.dItemvalue}" >${yesOrNoItem.dItemname}</option>
                    </c:forEach>
                </select>
            </td>
            <td>备注</td>
            <td><input name="remark" type="text"   input-class="easyui-validatebox"  style="width:160px;"   data-options="validType:['length[1,66]','CHS']"></td>
            <td>是否有效</td>
            <td>
                <select name="status" style="width:160px;height:21px" >
                    <c:forEach items="${yesOrNoIsYes}" var="yesOrNoItem"  >
                        <option value="${yesOrNoItem.dItemvalue}">${yesOrNoItem.dItemname}</option>
                    </c:forEach>
                </select>
            </td>
            <td>分管协议</td>
            <td>
                <select name="agentAssProtocol" style="width:110px;height:21px" id="agentAssProtocol2" onchange="assProSelect(this)">
                    <option value="">请选择</option>
                    <c:forEach items="${ass}" var="assListItem"  >
                        <option value="${assListItem.id}">${assListItem.protocolDes}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>附件</td>
            <td class="attrInput" ></td>
            <td>
                <a href="javascript:void(0)" class="editAgentContractTableDel-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="removeEditAAgentContract_model_Table(this)" >删除</a>||
                <a href="javascript:void(0)" class="editAgentContractTableAddAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addEditAgentContractTable_uploadView(this)" >添加附件</a>
            </td>
        </tr>
        </tbody>
    </table>
</div>

<script>
    function addEditAAgentContract_model_Table(){
        var html = $("#editAgentContractTable_model_templet").html();
        $("#editAgentContract_model_form").append(html);
        var inputs = $("#editAgentContract_model_form .grid:last input");
        for(var i=0;i<inputs.length;i++){
            if($(inputs[i]).attr("input-class") && $(inputs[i]).attr("input-class").length>0)
                $(inputs[i]).addClass($(inputs[i]).attr("input-class"));
        }
        $.parser.parse($("#editAgentContract_model_form .grid:last"));
    }
    function removeEditAAgentContract_model_Table(t){
        $(t).parent().parent().parent().parent().remove();
    }


    var addEditAgentContractTable_attrDom ;

    //上传窗口
    function addEditAgentContractTable_uploadView(t){
        addEditAgentContractTable_attrDom = $(t).parent().parent().find(".attrInput");
        multFileUpload(addEditAgentContractTable_jxkxUploadFile);
    }

    //附件解析
    function addEditAgentContractTable_jxkxUploadFile(data) {
        var jsondata = eval(data);
        for(var i=0;i<jsondata.length ;i++){
            $(addEditAgentContractTable_attrDom).append("<span onclick='removeaddEditAgentContractTable_jxkxUploadFile(this)'>"+jsondata[i].attName+"<input type='hidden' name='contractTableFile' value='"+jsondata[i].id+"' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }
    }
    function removeaddEditAgentContractTable_jxkxUploadFile(t){
        parent.$.messager.confirm('询问', '确定删除附件么？', function(b) {
            if (b) {
                $(t).remove();
            }
        });
    }
    //解析打个table
    function get_editAgentContractTable_FormDataItem(table){
        var data = {};
        data.id = $(table).find("input[name='id']").length>0?$(table).find("input[name='id']").val():"";
        data.contType = $(table).find("select[name='contType']").val();
        data.contNum = $(table).find("input[name='contNum']").val();
        data.contDate = $(table).find("input[name='contDate']").val();
        data.contEndDate = $(table).find("input[name='contEndDate']").val();
        data.remark = $(table).find("input[name='remark']").val();
        data.status = $(table).find("select[name='status']").val();
        data.appendAgree = $(table).find("select[name='appendAgree']").val();
        data.agentAssProtocol= $(table).find("select[name='agentAssProtocol']").val();
        data.protocolRuleValue = $(table).find("input[name='protocolRuleValue']").val();
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
    function get_editAgentContractTable_FormData(){
        var editAgentContractTable_FormDataJson = [];
        var tables = $("#editAgentContract_model_form .grid ");
        for (var  i=0;i<tables.length;i++){
            var table = tables[i];
            editAgentContractTable_FormDataJson.push(get_editAgentContractTable_FormDataItem(table));
        }
        return editAgentContractTable_FormDataJson;
    }

    function assProSelect(o) {
        var assPro = $(o).find("option:selected").val();
        var text ="<input name='protocolRuleValue' id='protocolRuleValue' class='easyui-validatebox' style='width:70px;' ";
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
</script>

