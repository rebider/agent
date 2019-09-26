<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-panel" title="填写信息" data-options="iconCls:'fi-results',tools:'#TerminalTransfer_model_tools'">
    <form id="TerminalTransfer_model_form">
    </form>
</div>
<div id="TerminalTransfer_model_tools">
    <a href="javascript:void(0)" class="icon-add" style="margin-right: 50px;" onclick="addTerminalTransfer_model_Table()"></a>
</div>
<div style="display: none;" id="TerminalTransfer_model_templet">
    <table  class="grid">
        <tbody>
            <tr>
                <td>SN开始</td>
                <td><input name="snBeginNum" type="text" onBlur="getComSnNumStart(this)"  input-class="easyui-validatebox"  style="width:160px;"  data-options="required:true"/></td>
                <td>SN结束</td>
                <td><input name="snEndNum" type="text"  input-class="easyui-validatebox" onBlur="getComSnNum(this)"   style="width:160px;"  data-options="required:true"/></td>
                <td>数量</td>
                <td><input name="comSnNum" type="text"  readonly="readonly" input-class="easyui-validatebox"  style="width:160px;" /></td>
                <td>
                    平台类型
                </td>
                <td>
                    <select name="platformType" style="width:160px;height:21px">
                        <c:forEach items="${platformTypeList}" var="platformType">
                            <option value="${platformType.key}">${platformType.value}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>原业务平台编码</td>
                <td>
                    <input name="originalOrgId" type="text"  input-class="easyui-validatebox"   onBlur="getAgentType(this)"   style="width:160px;"  data-options="required:true"/>
                    <%--<a href="javascript:void(0)" style="float: right" onclick="queryAddTermNum(this)">查询终端数量</a>--%>
                </td>
                <td>原业务平台名称</td>
                <td><input name="originalOrgName" type="text"  input-class="easyui-validatebox"  style="width:160px;"  data-options="required:true"/></td>
                <td>原类型</td>
                <td><input name="agentType" type="text"  readonly="readonly" input-class="easyui-validatebox"  style="width:160px;" /></td>
                <td>划拨数量下限</td>
                <td><input name="snCount" type="text"  readonly="readonly" input-class="easyui-validatebox"  style="width:160px;" /></td>
            </tr>
        <tr>
            <td>目标业务平台编码</td>
            <td><input name="goalOrgId" type="text"  input-class="easyui-validatebox"  onBlur="getGoAgentType(this)" style="width:160px;"  data-options="required:true"/></td>
            <td>目标业务平台名称</td>
            <td><input name="goalOrgName" type="text"  input-class="easyui-validatebox"  style="width:160px;"  data-options="required:true"/></td>
            <td>目标类型</td>
            <td><input name="goAgentType" type="text"  input-class="easyui-validatebox"  readonly="readonly" style="width:160px;"  /></td>

            <td>
                <a href="javascript:void(0)" class="addAgentContractTableDel-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="removeAddTerminalTransfer_model_Table(this)" >删除</a>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<div class="easyui-panel" title="申请信息" data-options="iconCls:'fi-results'">
    <form id="add_TerminalTransfer_model_form">
        <table class="grid">
            <tbody>

            <tr>
                <td width="80px">平台类型</td>
                <td>
                    <select name="platformType" style="width:350px;height:21px">
                        <c:forEach items="${agentPlatformTypeList}" var="agentPlatformType">
                            <option value="${agentPlatformType.id}">${agentPlatformType.busPlatform}-${agentPlatformType.busNum}-${agentPlatformType.agDocDistrict}-${agentPlatformType.agDocPro}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td width="80px">附件</td>
                <td>
                    <a class="attrInput">
                    </a>
                    <a href="javascript:void(0)"
                       data-options="plain:true,iconCls:'fi-magnifying-glass'"
                       onclick="terminalTran_AttFile_uploadView(this)">添加附件</a>
                </td>
            </tr>
            <tr>
                <td width="80px">申请备注</td>
                <td><input name="remark" type="text" class="easyui-validatebox"  style="width:160px;" /></td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<div style="text-align:right;padding:5px;margin-bottom: 50px;">
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="saveTerminalTransfer(1)">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="saveTerminalTransfer(2)">保存并审核</a>
</div>
<script type="text/javascript">

    var terminalTran_AttFile_attrDom;
    //上传窗口
    function terminalTran_AttFile_uploadView(t) {
        terminalTran_AttFile_attrDom = $(t).parent().find(".attrInput");
        multFileUpload(terminalTran_AttFile_UploadFile);
    }
    //附件解析
    function terminalTran_AttFile_UploadFile(data) {
        var jsonData = eval(data);
        for (var i = 0; i < jsonData.length; i++) {
            $(terminalTran_AttFile_attrDom).append("<span onclick='terminalTran_removeFile(this)'>" + jsonData[i].attName + "<input type='hidden' name='terTranFile' value='" + jsonData[i].id + "' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }
    }

    function terminalTran_removeFile(t){
        parent.$.messager.confirm('询问', '确定删除附件么？', function(b) {
            if (b) {
                $(t).remove();
            }
        });
    }


    $(function () {
        addTerminalTransfer_model_Table();
    });

    function removeAddTerminalTransfer_model_Table(t){
        $(t).parent().parent().parent().parent().remove();
    }

    function addTerminalTransfer_model_Table(){
        var html = $("#TerminalTransfer_model_templet").html();
        $("#TerminalTransfer_model_form").append(html);
        var inputs = $("#TerminalTransfer_model_form .grid:last input");
        for(var i=0;i<inputs.length;i++){
            if($(inputs[i]).attr("input-class") && $(inputs[i]).attr("input-class").length>0)
                $(inputs[i]).addClass($(inputs[i]).attr("input-class"));
        }
        $.parser.parse($("#TerminalTransfer_model_form .grid:last"));
    }

    //解析打个table
    function get_addTerminalTransfer_FormDataItem(table){
       var agentType = $(table).find("input[name='agentType']").val();
        var goAgentType = $(table).find("input[name='goAgentType']").val();
        if(agentType=="机构" && goAgentType=='机构'){
            return 1;
        }
        if((agentType=='机构' && goAgentType=='标准一代')|| (agentType=='标准一代' && goAgentType=='机构')){
            return 2;
        }
        if(agentType=="标准一代" && goAgentType=='标准一代'){
            return 3;
        }
        var data = {};
        data.snBeginNum = $(table).find("input[name='snBeginNum']").val();
        data.snEndNum = $(table).find("input[name='snEndNum']").val();
        data.snCount = $(table).find("input[name='snCount']").val();
        data.originalOrgId = $(table).find("input[name='originalOrgId']").val();
        data.originalOrgName = $(table).find("input[name='originalOrgName']").val();
        data.goalOrgId = $(table).find("input[name='goalOrgId']").val();
        data.goalOrgName = $(table).find("input[name='goalOrgName']").val();
        data.platformType = $(table).find("select[name='platformType']").val();
        data.comSnNum = $(table).find("input[name='comSnNum']").val();
        console.info(data.comSnNum);
        data.goalType = $(table).find("input[name='goAgentType']").val();
        console.info(data.goalType);
        data.originalType = $(table).find("input[name='agentType']").val();
        console.info(data.originalType);
        return data;
    }

    //获取form数据
    function get_addTerminalTransferTable_FormData(){
        var addTerminalTransfer_FormDataJson = [];
        var tables = $("#TerminalTransfer_model_form .grid");
        for (var  i=0;i<tables.length;i++){
            var table = tables[i];
            addTerminalTransfer_FormDataJson.push(get_addTerminalTransfer_FormDataItem(table));
        }
        return addTerminalTransfer_FormDataJson;
    }
    //休眠方法
    function sleep(numberMillis) {
        var now = new Date();
        var exitTime = now.getTime() + numberMillis;
        while (true) {
            now = new Date();
            if (now.getTime() > exitTime)
                return;
        }
    }
    function saveTerminalTransfer(saveFlag) {
        sleep(2000);
        var terminalTransferTable = get_addTerminalTransferTable_FormData();
        for(var  i=0;i<terminalTransferTable.length;i++){
            var terminal = terminalTransferTable[i];
            if(terminal==1){
                $.messager.alert("提示","机构和机构不能提交");
                return;
            }
            if(terminal==2){
                $.messager.alert("提示","机构和标准一代不能提交");
                return;
            }
            if(terminal==3){
                $.messager.alert("提示","标准一代和标准一代不能提交");
                return;
            }
        }
        var terminalTransferBase = $("#TerminalTransfer_model_form").form('validate');
        if (terminalTransferBase) {
            parent.$.messager.confirm('询问', '确认要保存？', function(b) {
                if (b) {
                    $.ajaxL({
                        type: "POST",
                        url: "/terminal/saveTerminalTransfer",
                        dataType:'json',
                        traditional:true,//这使json格式的字符不会被转码
                        contentType:'application/json;charset=UTF-8',
                        data: JSON.stringify({
                            terminalTransferDetailList:terminalTransferTable,
                            flag:saveFlag,
                            terminalTransfer:$.serializeObject($("#add_TerminalTransfer_model_form"))
                        }),
                        beforeSend : function() {
                            progressLoad();
                        },
                        success: function(data){
                            if(!data.success){
                                info(data.msg);
                                return;
                            }
                            info(data.msg);
                            $('#index_tabs').tabs('close', "终端划拨-申请");
                            terminalTransferList.datagrid('reload');
                        },
                        complete:function (XMLHttpRequest, textStatus) {
                            progressClose();
                        },
                        error:function () {
                            progressClose();
                        }
                    });
                }
            });
        } else {
            info("请输入必填项")
        }
    }

    function queryAddTermNum(o) {
        var originalOrgId = $(o).parent().find("input[name='originalOrgId']").val();
        var platformType = $(o).parent().parent().parent().find("select[name='platformType']").val();
        if(originalOrgId=='' || originalOrgId==undefined){
            info("请填写原机构ID");
            return false;
        }
        if(platformType=='' || platformType==undefined){
            info("请选择平台");
            return false;
        }

        parent.$.modalDialog({
            title : '查询终端数量',
            width : 500,
            height : '100%',
            href : '${path }/terminal/statistics?originalOrgId='+originalOrgId+"&platformType="+platformType
        });
    }


    function getGoAgentType(t) {
        var goalOrgId = $(t).val();
        $.ajax({
            type:"POST",
            url:"/terminal/getGoAgentType",
            data:{"goalOrgId":goalOrgId},
            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
            success:function(data){
                if(data.length>30){
                    data='';
                }
                var array =data.split("+"); //字符分割
                $(t).parent().parent().find("[name='goAgentType']").val(array[0]);
            }
        });

    }

    function  getAgentType(t) {
        var goalOrgId = $(t).val();
        $.ajax({
            type:"POST",
            url:"/terminal/getGoAgentType",
            data:{"goalOrgId":goalOrgId},
            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
            success:function(data){
                if(data.length>30){
                    data='';
                }
                var array =data.split("+"); //字符分割
                $(t).parent().parent().find("[name='agentType']").val(array[0]);
                $(t).parent().parent().parent().find("[name='snCount']").val(array[1]);
            }
        });
    }

    function  getComSnNumStart(t){
        var startNum= $(t).val();
        var endNum = $(t).parent().parent().find("[name='snEndNum']").val();
        $.ajax({
            type:"POST",
            url:"/terminal/disposeSN",
            data:{"StratNum":startNum,"endNum":endNum},
            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
            success:function(data){
                if(data.length>30){
                    data='';
                }
                var array =data.split("+"); //字符分割
                $(t).parent().parent().find("[name='comSnNum']").val(data);

            }
        });


    }
    function  getComSnNum(t){
        var endNum= $(t).val();
        var StratNum = $(t).parent().parent().find("[name='snBeginNum']").val();
        $.ajax({
            type:"POST",
            url:"/terminal/disposeSN",
            data:{"StratNum":StratNum,"endNum":endNum},
            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
            success:function(data){
                if(data.length>30){
                    data='';
                }
                var array =data.split("+"); //字符分割
                $(t).parent().parent().find("[name='comSnNum']").val(data);

            }
        });


    }

</script>
