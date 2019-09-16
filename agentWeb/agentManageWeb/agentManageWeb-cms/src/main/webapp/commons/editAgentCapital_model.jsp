<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="缴纳款项" data-options="iconCls:'fi-results', tools:'#editAgentcapital_model_tools' ">
    <form id="editAgentcapital_model_form">
            <c:if test="${!empty capitals}">
                <c:forEach items="${capitals}" var="capitals">
                    <table class="grid" id="editAgentcapital_grid">
                        <tbody>
                    <tr class="jnkxTr">
                        <td>缴纳款项<input type="hidden" name="id" value="${capitals.id}"></td>
                        <td>
                            <select name="cType" style="width:160px;height:21px" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentCapitalInfo">disabled="true"</shiro:lacksPermission> <c:if test="${appEditFlag=='1'}">disabled="disabled"</c:if>>
                                <c:forEach items="${capitalType}" var="CapitalTypeItem"  >
                                    <option value="${CapitalTypeItem.dItemvalue}" <c:if test="${CapitalTypeItem.dItemvalue== capitals.cType}">selected="selected"</c:if>>${CapitalTypeItem.dItemname}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>缴纳金额</td>
                        <td><input name="cAmount"  type="text"  class="easyui-validatebox"  style="width:160px;"  data-options="required:true,validType:['length[1,11]','Money']" value="${capitals.cAmount}" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentCapitalInfo">readonly="readonly"</shiro:lacksPermission>
                             <c:if test="${appEditFlag=='1'}">readonly="readonly"</c:if>/>
                        </td>
                        <td>打款方式</td>
                        <td id="payType">
                            <select name="cPayType" style="width:160px;height:21px" onchange="changCpay(this)" <c:if test="${appEditFlag=='1'}">disabled="disabled"</c:if>>
                                <c:forEach items="${payType}" var="payTypeItem"  >
                                    <option value="${payTypeItem.dItemvalue}"<c:if test="${payTypeItem.dItemvalue== capitals.cPayType}">selected="selected"</c:if>>${payTypeItem.dItemname}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>是否有效</td>
                        <td>
                            <select name="status" style="width:160px;height:21px" <c:if test="${appEditFlag=='1'}">disabled="disabled"</c:if>>
                                <c:forEach items="${yesOrNoIsYes}" var="yesOrNoItem"  >
                                    <option value="${yesOrNoItem.dItemvalue}">${yesOrNoItem.dItemname}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr id="nweTr1">
                        <td class="cFqCountY" <c:if test="${capitals.cPayType!='FRDK'}">style="display: none;"</c:if> >分期期数</td>
                        <td class="cFqCountY" <c:if test="${capitals.cPayType!='FRDK'}">style="display: none;"</c:if>>
                                <%--<input name="cFqCount" type="text"    class="easyui-validatebox cFqCountY"  style="width:30px;"  data-options="required:true" value="${capitals.cFqCount}" <c:if test="${appEditFlag=='1'}">readonly="readonly"</c:if>>--%>
                            <select  class="cFqCountY" name="cFqCount" style="width:150px;" data-options="editable:false" <c:if test="${appEditFlag=='1'}">readonly="readonly"</c:if>>
                                <c:forEach var="v"  begin="1"  end="6" step="1">
                                    <option value="${v}" <c:if test="${capitals.cFqCount==v}">selected="selected"</c:if>>${v}期</option>
                                </c:forEach>
                            </select>
                        </td>
                        <c:if test="${capitals.cPayType=='YHHK'}">
                            <td class="cPayuserY">打款人</td>
                            <td class="cPayuserY"><input name="cPayuser" type="text"  class="easyui-validatebox cPayuserY"  style="width:60px;"  data-options="required:true" value="${capitals.cPayuser}" <c:if test="${appEditFlag=='1'}">readonly="readonly"</c:if>></td>
                            <td class="cPaytimeY">打款时间</td>
                            <td class="cPaytimeY"><input name="cPaytime" type="text"   class="easyui-datebox cPaytimeY"  style="width:160px;"  editable="false" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${capitals.cPaytime}" />" <c:if test="${appEditFlag=='1'}">readonly="readonly"</c:if>></td>
                            <td class="cInComY" id="cInComTd">收款地方</td>
                            <td class="cInComY">
                                <select name="cInCom" style="width:160px;height:21px" <c:if test="${appEditFlag=='1'}">disabled="disabled"</c:if>>
                                    <c:forEach items="${payCompList}" var="payCompItem"  >
                                        <option value="${payCompItem.id}" <c:if test="${payCompItem.id== capitals.cInCom}">selected="selected"</c:if>>${payCompItem.comName}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </c:if>
                    </tr>
                    <tr id="cInComTr">
                        <td>备注</td>
                        <td><input name="remark" type="text"    class="easyui-validatebox"  style="width:160px;"   data-options="validType:['length[1,66]','CHS']" value="${capitals.remark}" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentCapitalInfo">readonly="readonly"</shiro:lacksPermission>
                                   <c:if test="${appEditFlag=='1'}">readonly="readonly"</c:if>></td>
                        <td>附件</td>
                        <td class="attrInput" colspan="3">
                            <c:if test="${!empty capitals.attachmentList}">
                                <c:forEach items="${capitals.attachmentList}" var="attachment">
                                <span <shiro:hasPermission name="/agentEnter/agentEdit/AgentCapitalInfo"> onclick='removeaddEditAgentcapitalTable_jxkxUploadFile(this)'</shiro:hasPermission> >${attachment.attName}<input type='hidden' name='capitalTableFile' value='${attachment.id}' /></span>
                                </c:forEach>
                            </c:if>
                        </td>
                        <c:if test="${appEditFlag!='1'}">
                        <td colspan="4">
                            <shiro:hasPermission name="/agentEnter/agentEdit/AgentCapitalInfo">
                                <a href="javascript:void(0)" class="editAgentcapitalTableDel-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="removeEditAgentcapital_model(this)" >删除</a>||
                                <a href="javascript:void(0)" class="editAgentcapitalTableAddAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addEditAgentcapitalTable_uploadView(this)" >添加附件</a>
                            </shiro:hasPermission>
                        </td>
                        </c:if>
                    </tr>
                        </tbody>
                    </table>
                </c:forEach>
            </c:if>

    </form>
</div>

<shiro:hasPermission name="/agentEnter/agentEdit/AgentCapitalInfo">
<div id="editAgentcapital_model_tools">
    <a href="javascript:void(0)" class="icon-add" style="margin-right: 50px;" onclick="addEditAgentcapitalTable()"></a>
</div>
</shiro:hasPermission>

<div style="display: none" id="editAgentcapitalTable_model_templet">
    <table class="grid" id="editAgentcapitalgrid">
        <tbody>
        <tr class="jnkxTr" id="cTypeTr">
            <td>缴纳款项</td>
            <td>
                <select name="cType" style="width:160px;height:21px" >
                    <c:forEach items="${capitalType}" var="CapitalTypeItem"  >
                        <option value="${CapitalTypeItem.dItemvalue}">${CapitalTypeItem.dItemname}</option>
                    </c:forEach>
                </select>
            </td>
            <td>缴纳金额</td>
            <td><input name="cAmount" type="text"  input-class="easyui-numberbox"  style="width:160px;"  data-options="required:true,validType:['length[1,11]','Money']"/></td>
            <td>打款方式</td>
            <td id="cPayType">
                <select name="cPayType" style="width:160px;height:21px" onchange="changCpayType(this)" >
                    <option value="">---请选择---</option>
                    <c:forEach items="${payType}" var="payTypeItem">
                        <option value="${payTypeItem.dItemvalue}">${payTypeItem.dItemname}</option>
                    </c:forEach>
                </select>
            </td>
            <td >是否有效</td>
            <td id="statusTd">
                <select name="status" style="width:160px;height:21px" >
                    <c:forEach items="${yesOrNoIsYes}" var="yesOrNoItem"  >
                        <option value="${yesOrNoItem.dItemvalue}">${yesOrNoItem.dItemname}</option>
                    </c:forEach>
                </select>
            </td>
            <%--分期期数  打款人 --%>
        </tr>
        <tr id="newTr">

        </tr>
        <tr id="statusTr">
            <%--收款地方   收款时间 --%>
            <td id="remarkTd">备注</td>
            <td><input name="remark" type="text"  input-class="easyui-validatebox"  style="width:160px;"   data-options="validType:['length[1,66]','CHS']" ></td>
            <td>附件</td>
            <td class="attrInput">
            </td>
            <td colspan="2">
                <a href="javascript:void(0)" class="editAgentcapitalTableDel-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="removeEditAgentcapital_model(this)" >删除</a>||
                <a href="javascript:void(0)" class="editAgentcapitalTableAddAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addEditAgentcapitalTable_uploadView(this)" >添加附件</a>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<script>

    var addEditAgentcapitalTable_attrDom ;

    function addEditAgentcapitalTable(){
        $("#editAgentcapital_model_form").append($("#editAgentcapitalTable_model_templet").html());
        var inputs = $("#editAgentcapital_model_form .grid:last input");
        for(var i=0;i<inputs.length;i++){
            if($(inputs[i]).attr("input-class") && $(inputs[i]).attr("input-class").length>0)
                $(inputs[i]).addClass($(inputs[i]).attr("input-class"));
        }
        $.parser.parse($("#editAgentcapital_model_form .grid:last"));
    }

    //删除事件
    function removeEditAgentcapital_model(t){
        $(t).parent().parent().parent().parent().remove();
    }

    //上传窗口
    function addEditAgentcapitalTable_uploadView(t){
        addEditAgentcapitalTable_attrDom = $(t).parent().parent().find(".attrInput");
        multFileUpload(addEditAgentcapitalTable_jxkxUploadFile);
    }
    //附件解析
    function addEditAgentcapitalTable_jxkxUploadFile(data) {
        var jsondata = eval(data);
        for(var i=0;i<jsondata.length ;i++){
            $(addEditAgentcapitalTable_attrDom).append("<span onclick='removeaddEditAgentcapitalTable_jxkxUploadFile(this)'>"+jsondata[i].attName+"<input type='hidden' name='capitalTableFile' value='"+jsondata[i].id+"' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }

    }

    function removeaddEditAgentcapitalTable_jxkxUploadFile(t){
        parent.$.messager.confirm('询问', '确定删除附件么？', function(b) {
            if (b) {
                $(t).remove();
            }
        });
    }
    //解析打个table
    function get_editAgentcapitalTable_FormDataItem(table){
        var data = {};
        data.id = $(table).find("input[name='id']").length>0?$(table).find("input[name='id']").val():"";
        data.cType = $(table).find("select[name='cType']").val();
        data.cAmount = $(table).find("input[name='cAmount']").val();
        data.cPaytime = $(table).find("input[name='cPaytime']").val();
        data.remark = $(table).find("input[name='remark']").val();
        data.status = $(table).find("select[name='status']").val();
        data.cFqCount = $(table).find("select[name='cFqCount']").val();
        data.cPayuser = $(table).find("input[name='cPayuser']").val();
        data.cPaytime = $(table).find("input[name='cPaytime']").val();
        data.cInCom = $(table).find("select[name='cInCom']").val();
        data.cPayType = $(table).find("select[name='cPayType']").val();
        var files =  $(table).find(".attrInput").find("input[name='capitalTableFile']");
        var capitalTableFileTemp = [];
        for(var j=0;j<files.length;j++){
            capitalTableFileTemp.push($(files[j]).val());
        }
        if(capitalTableFileTemp.length>0)
            data.capitalTableFile=capitalTableFileTemp;
        return data;
    }

    //获取form数据
    function get_editAgentcapitalTable_FormData(){
        var editAgentcapitalTable_FormDataJson = [];
        var tables = $("#editAgentcapital_model_form .grid");
        for (var  i=0;i<tables.length;i++){
            var table = tables[i];
            editAgentcapitalTable_FormDataJson.push(get_editAgentcapitalTable_FormDataItem(table));
        }
        return editAgentcapitalTable_FormDataJson;
    }

 //添加缴纳款项的打款方式
    function  changCpay(type) {
        var payType = $(type).find("option:selected").val();//选中的值
        if(payType=='FRDK'){ //分润抵扣
            $(type).parent().parent().parent().find(".cInComY").remove();
            $(type).parent().parent().parent().find(".cPayuserY").remove();
            $(type).parent().parent().parent().find(".cPaytimeY").next("td").remove();
            $(type).parent().parent().parent().find(".cPaytimeY").remove();

            $(type).parent().parent().parent().find("#nweTr1").after("<td class=\"cFqCountY\">分期期数</td>\n" +
                //                "<td><input name=\"cFqCount\" type=\"text\"   class=\"easyui-validatebox  cFqCount\"  style=\"width:160px;\"  data-options=\"required:true\"></td>");
                '<td class=\"cFqCountY\"><select   name="cFqCount" style="width:160px;" data-options="editable:false" ><c:forEach var="v"  begin="1"  end="6" step="1"><option value="${v}">${v}期</option></c:forEach></select></td>'
            );

        }else if(payType=='YHHK'){//银行汇款
            $(type).parent().parent().parent().find(".cFqCountY").remove();
            $(type).parent().parent().parent().find("#nweTr1").after("<td class=\"cPayuserY\">打款人</td>\n" +
                "<td class=\"cPayuserY\"><input name=\"cPayuser\" type=\"text\"   class=\"easyui-validatebox cPayuser\"  style=\"width:160px;\"  data-options=\"required:true\" editable=\"false\" ></td>");

            $(type).parent().parent().parent().find("#nweTr1").after("<td class=\"cPaytimeY\">打款时间</td>\n" +
                "            <td class=\"cPaytimeY\"><input name=\"cPaytime\" type=\"text\"   class=\"easyui-datebox  \"  style=\"width:160px;\"  data-options=\"required:true\" editable=\"false\" ></td>");

            $(type).parent().parent().parent().find("#nweTr1").after("<td class=\"cInComY\">收款地方</td>\n" +
                "            <td class=\"cInComY\">\n" +
                "                <select name=\"cInCom\" style=\"width:160px;\" class=\"cInComY\">\n" +
                "                    <c:forEach items="${payCompList}" var="payCompItem"  >\n" +
                "                        <option value=\"${payCompItem.id}\">${payCompItem.comName}</option>\n" +
                "                    </c:forEach>\n" +
                "                </select>\n" +
                "            </td>");
        } else{
            $(type).parent().parent().parent().find(".cPaytimeY").next("td").remove();
            $(type).parent().parent().parent().find(".cPaytimeY").remove();
            $(type).parent().parent().parent().find(".cInComY").remove();
            $(type).parent().parent().parent().find(".cPayuserY").remove();
            $(type).parent().parent().parent().find(".cFqCountY").remove();

        }
        $.parser.parse($("#editAgentcapital_grid td"));
    }






    function  changCpayType(object) {
        var payType = $(object).find("option:selected").val();//选中的值
        if(payType=='FRDK'){ //分润抵扣
            $(object).parent().parent().parent().find(".cInCom").remove();
            $(object).parent().parent().parent().find(".cPayuser").remove();
            $(object).parent().parent().parent().find(".cPaytime").next("td").remove();
            $(object).parent().parent().parent().find(".cPaytime").remove();
            $(object).parent().parent().parent().find("#newTr").after("<td class=\"cFqCount\">分期期数</td>\n" +
//                "<td><input name=\"cFqCount\" type=\"text\"   class=\"easyui-validatebox  cFqCount\"  style=\"width:60px;\"  data-options=\"required:true\"></td>");
                '<td class=\"cFqCount\"><select   name="cFqCount" style="width:150px;" data-options="editable:false" ><c:forEach var="v"  begin="1"  end="6" step="1"><option value="${v}">${v}期</option></c:forEach></select></td>'
            );
        }else if(payType=='YHHK'){//银行汇款
            $(object).parent().parent().parent().find(".cFqCount").remove();
            $(object).parent().parent().parent().find("#newTr").after("<td class=\"cPayuser\">打款人</td>\n" +
                "<td class=\"cPayuser\"><input name=\"cPayuser\" type=\"text\"   class=\"easyui-validatebox cPayuser\"  style=\"width:160px;\"  data-options=\"required:true\" editable=\"false\" ></td>");

            $(object).parent().parent().parent().find("#newTr").after("<td class=\"cPaytime\">打款时间</td>\n" +
                "            <td class=\"cPaytime\"><input name=\"cPaytime\" type=\"text\"   class=\"easyui-datebox  \"  style=\"width:160px;\"  data-options=\"required:true\" editable=\"false\" ></td>");

            $(object).parent().parent().parent().find("#newTr").after("<td class=\"cInCom\">收款地方</td>\n" +
                "            <td class=\"cInCom\">\n" +
                "                <select name=\"cInCom\" style=\"width:160px;height:21px\" class=\"cInCom\">\n" +
                "                    <c:forEach items="${payCompList}" var="payCompItem"  >\n" +
                "                        <option value=\"${payCompItem.id}\">${payCompItem.comName}</option>\n" +
                "                    </c:forEach>\n" +
                "                </select>\n" +
                "            </td>");


        }else{
            $(object).parent().parent().parent().find(".cPaytime").next("td").remove();
            $(object).parent().parent().parent().find(".cPaytime").remove();
            $(object).parent().parent().parent().find(".cInCom").remove();
            $(object).parent().parent().parent().find(".cPayuser").remove();
            $(object).parent().parent().parent().find(".cFqCount").remove();
        }
        $.parser.parse($("#editAgentcapitalgrid td"));
    }



</script>