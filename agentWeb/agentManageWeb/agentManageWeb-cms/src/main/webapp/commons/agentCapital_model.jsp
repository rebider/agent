<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="缴纳款项" data-options="iconCls:'fi-results',tools:'#Agentcapital_model_tools'">
    <form id="Agentcapital_model_form">
    </form>
</div>

<div id="Agentcapital_model_tools">
    <a href="javascript:void(0)" class="icon-add" style="margin-right: 50px;" onclick="addAgentcapitalTable()"></a>
</div>

<div style="display: none" id="AgentcapitalTable_model_templet">
    <table class="grid" id="agentcapitalTable_grid">
        <tbody>
        <tr class="jnkxTr" id="jnkxTr">
            <td>缴纳款项</td>
            <td>
                <select name="cType" style="width:160px;height:21px" >
                    <c:forEach items="${capitalType}" var="CapitalTypeItem"  >
                        <option value="${CapitalTypeItem.dItemvalue}">${CapitalTypeItem.dItemname}</option>
                    </c:forEach>
                </select>
            </td>
            <td>缴纳金额</td>
            <td><input name="cAmount" type="text"  input-class="easyui-validatebox"  style="width:160px;"  data-options="required:true,validType:['length[1,11]','Money']"/></td>

            <td>打款方式</td>
            <td id="payType">
                <select name="cPayType" style="width:160px;height:21px" onchange="changPayType(this)" >
                    <option value="">--请选择--</option>
                    <c:forEach items="${payType}" var="payTypeItem"  >
                        <option value="${payTypeItem.dItemvalue}">${payTypeItem.dItemname}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr id="tr">
            <td id="remark">备注</td>
            <td><input name="remark" type="text"  input-class="easyui-validatebox"  style="width:160px;"  data-options="validType:['length[1,66]','CHS']"></td>
            <td>附件</td>
            <td class="attrInput" >
            </td>
            <td colspan="2">
                <a href="javascript:void(0)" class="addAgentcapitalTableDel-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="removeAgentcapital_model(this)" >删除</a>||
                <a href="javascript:void(0)" class="addAgentcapitalTableAddAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addAgentcapitalTable_uploadView(this)" >上传打款凭据</a>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<script>
    var addAgentcapitalTable_attrDom ;

    function addAgentcapitalTable(){
        $("#Agentcapital_model_form").append($("#AgentcapitalTable_model_templet").html());
        var inputs = $("#Agentcapital_model_form .grid:last input");
        for(var i=0;i<inputs.length;i++){
            if($(inputs[i]).attr("input-class") && $(inputs[i]).attr("input-class").length>0)
            $(inputs[i]).addClass($(inputs[i]).attr("input-class"));
        }
        $.parser.parse($("#Agentcapital_model_form .grid:last"));
    }

    //删除事件
    function removeAgentcapital_model(t){
        $(t).parent().parent().parent().parent().remove();
    }
    //上传窗口
    function addAgentcapitalTable_uploadView(t){
        addAgentcapitalTable_attrDom = $(t).parent().parent().find(".attrInput");
        multFileUpload(addAgentcapitalTable_jxkxUploadFile);
    }
    //附件解析
    function addAgentcapitalTable_jxkxUploadFile(data) {
        var jsondata = eval(data);
        for(var i=0;i<jsondata.length ;i++){
           $(addAgentcapitalTable_attrDom).append("<span onclick='removeAgentcapitalTable_jxkxUploadFile(this)'>"+jsondata[i].attName+"<input type='hidden' name='capitalTableFile' value='"+jsondata[i].id+"' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }

    }
    function removeAgentcapitalTable_jxkxUploadFile(t){
        parent.$.messager.confirm('询问', '确定删除附件么？', function(b) {
            if (b) {
                $(t).remove();
            }
        });
    }
    //解析打个table
    function get_addAgentcapitalTable_FormDataItem(table){
            var data = {};
            data.cType = $(table).find("select[name='cType']").val();
            data.cAmount = $(table).find("input[name='cAmount']").val();
            data.cPaytime = $(table).find("input[name='cPaytime']").val();
            data.remark = $(table).find("input[name='remark']").val();
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
    function get_addAgentcapitalTable_FormData(){
        var addAgentcapitalTable_FormDataJson = [];
        var tables = $("#Agentcapital_model_form .grid");
        for (var  i=0;i<tables.length;i++){
            var table = tables[i];
            addAgentcapitalTable_FormDataJson.push(get_addAgentcapitalTable_FormDataItem(table));
        }
        return addAgentcapitalTable_FormDataJson;
    }

    function  changPayType(type) {
        var payType = $(type).find("option:selected").val();//选中的值
        if(payType=='FRDK'){ //分润抵扣
            $(type).parent().parent().parent().find(".cInCom").remove();
            $(type).parent().parent().parent().find(".cPayuser").remove();
            $(type).parent().parent().parent().find(".cPaytime").next("td").remove();
            $(type).parent().parent().parent().find(".cPaytime").remove();

            $(type).parent().parent().parent().find("#payType").after("<td class=\"cFqCount\">分期期数</td>\n" +
              '<td class=\"cFqCount\"><select  name="cFqCount" style="width:150px;" data-options="editable:false" ><c:forEach var="v"  begin="1"  end="6" step="1"><option value="${v}">${v}期</option></c:forEach></select></td>'
            );

            $.parser.parse($(type).parent().parent().parent());
        }else if(payType=='YHHK'){//银行汇款
            $(type).parent().parent().parent().find(".cFqCount").remove();
            $(type).parent().parent().parent().find("#payType").after("<td class=\"cPayuser\">打款人</td>\n" +
                "            <td class=\"cPayuser\" ><input name=\"cPayuser\" type=\"text\"   class=\"easyui-validatebox cPayuser\"  style=\"width:160px;\"  data-options=\"required:true\" editable=\"false\" ></td>");
            var str = "<td class=\"cPaytime\">打款时间</td><td><input name=\"cPaytime\" type=\"text\"   class=\"easyui-datebox \"  style=\"width:160px;\"  data-options=\"required:true\" editable=\"false\" ></td>"
            $(type).parent().parent().parent().find("#remark").before(str);

            $(type).parent().parent().parent().find("#remark").before("<td class=\"cInCom\">收款地方</td>\n" +
                "            <td class=\"cInCom\">\n" +
                "                <select name=\"cInCom\" style=\"width:160px;height:21px\" class=\"cInCom\">\n" +
                "                    <c:forEach items="${payCompList}" var="payCompItem"  >\n" +
                "                        <option value=\"${payCompItem.id}\">${payCompItem.comName}</option>\n" +
                "                    </c:forEach>\n" +
                "                </select>\n" +
                "            </td>");
            $.parser.parse($(type).parent().parent().parent());
        }else{
            $(type).parent().parent().parent().find(".cPaytime").next("td").remove();
            $(type).parent().parent().parent().find(".cPaytime").remove();
            $(type).parent().parent().parent().find(".cInCom").remove();
            $(type).parent().parent().parent().find(".cPayuser").remove();
            $(type).parent().parent().parent().find(".cFqCount").remove();
        }
//        $.parser.parse($("#agentcapitalTable_grid td"));

    }
</script>