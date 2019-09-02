<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-panel" title="申请明细" data-options="iconCls:'fi-results', tools:'#editAgentcapital_model_tools' ">
    <form id="editTerminalTransfer_model_form">
        <c:if test="${!empty terminalTransfer.terminalTransferDetailList}">
            <c:forEach items="${terminalTransfer.terminalTransferDetailList}" var="terminalTransferDetail" >
                <table class="grid" id="editAgentcapital_grid">
                    <tbody>
                        <tr>
                            <input name="id" type="hidden" value="${terminalTransferDetail.id}" class="easyui-validatebox"  style="width:160px;"  data-options="required:true"/>
                            <td>SN开始</td>
                            <td><input name="snBeginNum" onBlur="getComSnNumStart(this)"  type="text" value="${terminalTransferDetail.snBeginNum}" class="easyui-validatebox"  style="width:160px;"  data-options="required:true"/></td>
                            <td>SN结束</td>
                            <td><input name="snEndNum" onBlur="getComSnNum(this)" type="text" value="${terminalTransferDetail.snEndNum}" class="easyui-validatebox"  style="width:160px;"  data-options="required:true"/></td>
                            <td>数量</td>
                            <td><input name="comSnNum" type="text"  value="${terminalTransferDetail.comSnNum}" readonly="readonly" input-class="easyui-validatebox"  style="width:160px;" /></td>
                            <td>平台类型</td>
                            <td>
                                <select name="platformType" style="width:160px;height:21px">
                                    <c:forEach items="${platformTypeList}" var="platformType">
                                        <option value="${platformType.key}" <c:if test="${terminalTransferDetail.platformType==platformType.key}">selected</c:if>>${platformType.value}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>原业务平台编码</td>
                            <td>
                                <input name="originalOrgId" type="text"  onBlur="getAgentType(this)"  value="${terminalTransferDetail.originalOrgId}" class="easyui-validatebox"  style="width:160px;"  data-options="required:true"/>
                                <%--<a href="javascript:void(0)" style="float: right" onclick="queryEditTermNum(this)">查询终端数量</a>--%>
                            </td>
                            <td>原业务平台名称</td>
                            <td><input name="originalOrgName" type="text" value="${terminalTransferDetail.originalOrgName}" class="easyui-validatebox"  style="width:160px;"  data-options="required:true"/></td>
                            <td>原类型</td>
                            <td><input name="agentType" readonly="readonly" type="text" value="${terminalTransferDetail.originalType}" readonly="readonly" input-class="easyui-validatebox"  style="width:160px;" /></td>
                            <td>终端数量下限</td>
                            <td><input name="snCount" readonly="readonly" type="text" value="${terminalTransferDetail.snCount}" class="easyui-validatebox"  style="width:160px;"  data-options="required:true"/></td>
                        </tr>


                        <tr>
                            <td>目标业务平台编码</td>
                            <td><input name="goalOrgId" type="text" onBlur="getGoAgentType(this)" value="${terminalTransferDetail.goalOrgId}" class="easyui-validatebox"  style="width:160px;"  data-options="required:true"/></td>
                            <td>目标业务平台名称</td>
                            <td><input name="goalOrgName" type="text" value="${terminalTransferDetail.goalOrgName}" class="easyui-validatebox"  style="width:160px;"  data-options="required:true"/></td>
                            <td>目标类型</td>
                            <td><input name="goAgentType" type="text" readonly="readonly" value="${terminalTransferDetail.goalType}" input-class="easyui-validatebox"  readonly="readonly" style="width:160px;"  /></td>
                            <td>
                                <a href="javascript:void(0)" class="addAgentContractTableDel-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="removeEditTerminalTransfer_model_Table(this)" >删除</a>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </c:forEach>
        </c:if>

    </form>
</div>

<div id="editAgentcapital_model_tools">
    <a href="javascript:void(0)" class="icon-add" style="margin-right: 50px;" onclick="addTerminalTransferTable()"></a>
</div>

<div style="display: none" id="editTerminalTransferTable_model_templet">
    <table class="grid">
        <tbody>
        <tr>
            <td>SN开始</td>
            <td><input name="snBeginNum" type="text"  input-class="easyui-validatebox"  style="width:160px;"  data-options="required:true"/></td>
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
            </tr>
        </tbody>
    </table>
</div>
<div class="easyui-panel" title="申请信息" data-options="iconCls:'fi-results'">
    <form id="addTerminalTransfer_model_form">
        <c:if test="${!empty terminalTransfer}">
            <table class="grid" id="editAgentcapital_grid">
                <tbody>
                <tr>
                    <td width="80px">平台类型</td>
                    <td>
                        <select name="platformType" style="width:350px;height:21px">
                            <c:forEach items="${agentPlatformTypeList}" var="agentPlatformType">
                                <option value="${agentPlatformType.id}" <c:if test="${agentPlatformType.id==terminalTransfer.platformType}">selected</c:if>>${agentPlatformType.busPlatform}-${agentPlatformType.busNum}-${agentPlatformType.agDocDistrict}-${agentPlatformType.agDocPro}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td width="80px">附件:</td>
                    <td colspan="5">
                        <span class="attrInput">
                            <c:if test="${!empty terminalTransfer.attachments}">
                                <c:forEach items="${terminalTransfer.attachments}" var="attachment">
                                     <span>
                                             <a onclick='removeTerminalTransferEditFile(this)'>${attachment.attName}</a>
                                             <input type='hidden' name='terTranFile' value='${attachment.id}' />
                                            <a href="<%=imgPath%>${attachment.attDbpath}" target="_blank" >查看</a>&nbsp;&nbsp;&nbsp;&nbsp;
                                     </span>
                                </c:forEach>
                            </c:if>
                        </span>
                        <a href="javascript:void(0)" class="busck-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'"
                           style="margin-left: 20px" onclick="terminalTransfer_AttFile_uploadView(this)">添加附件</a>
                    </td>
                </tr>
                <tr>
                    <input name="id" type="hidden" value="${terminalTransfer.id}" class="easyui-validatebox"  style="width:160px;"  data-options="required:true"/>
                    <td width="80px">申请备注</td>
                    <td><input name="remark" type="text" value="${terminalTransfer.remark}" class="easyui-validatebox"  style="width:160px;" /></td>
                </tr>
                </tbody>
            </table>
        </c:if>
    </form>
</div>
<div style="text-align:right;padding:5px;margin-bottom: 50px;">
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="editTerminalTransfer()">修改</a>
</div>
<script>

    var terminalTransfer_AttFile_attrDom;
    //上传窗口
    function terminalTransfer_AttFile_uploadView(t) {
        terminalTransfer_AttFile_attrDom = $(t).parent().find(".attrInput");
        multFileUpload(terminalTransfer_AttFile_UploadFile);
    }
    //附件解析
    function terminalTransfer_AttFile_UploadFile(data) {
        var jsonData = eval(data);
        for (var i = 0; i < jsonData.length; i++) {
            $(terminalTransfer_AttFile_attrDom).append("<span onclick='removeFile(this)'>" + jsonData[i].attName + "<input type='hidden' name='terTranFile' value='" + jsonData[i].id + "' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }
    }

    function removeTerminalTransferEditFile(t){
        parent.$.messager.confirm('询问', '确定删除附件么？', function(b) {
            if (b) {
                $(t).parent().remove();
            }
        });
    }

    function removeEditTerminalTransfer_model_Table(t){
        $(t).parent().parent().parent().parent().remove();
    }

    function addTerminalTransferTable(){
        $("#editTerminalTransfer_model_form").append($("#editTerminalTransferTable_model_templet").html());
        var inputs = $("#editTerminalTransfer_model_form .grid:last input");
        for(var i=0;i<inputs.length;i++){
            if($(inputs[i]).attr("input-class") && $(inputs[i]).attr("input-class").length>0)
                $(inputs[i]).addClass($(inputs[i]).attr("input-class"));
        }
        $.parser.parse($("#editTerminalTransfer_model_form .grid:last"));
    }

    //解析打个table
    function get_editTerminalTransferTable_FormDataItem(table){
        var data = {};
        data.id = $(table).find("input[name='id']").length>0?$(table).find("input[name='id']").val():"";
        data.snBeginNum = $(table).find("input[name='snBeginNum']").val();
        data.snEndNum = $(table).find("input[name='snEndNum']").val();
        data.snCount = $(table).find("input[name='snCount']").val();
        data.originalOrgId = $(table).find("input[name='originalOrgId']").val();
        data.originalOrgName = $(table).find("input[name='originalOrgName']").val();
        data.goalOrgId = $(table).find("input[name='goalOrgId']").val();
        data.goalOrgName = $(table).find("input[name='goalOrgName']").val();
        data.platformType = $(table).find("select[name='platformType']").val();

        data.comSnNum = $(table).find("input[name='comSnNum']").val();
        data.goalType = $(table).find("input[name='goAgentType']").val();
        data.originalType = $(table).find("input[name='agentType']").val();
        return data;
    }

    //获取form数据
    function get_editTerminalTransferTable_FormData(){
        var editAgentcapitalTable_FormDataJson = [];
        var tables = $("#editTerminalTransfer_model_form .grid");
        console.info(tables);
        for (var  i=0;i<tables.length;i++){
            var table = tables[i];
            editAgentcapitalTable_FormDataJson.push(get_editTerminalTransferTable_FormDataItem(table));
        }
        return editAgentcapitalTable_FormDataJson;
    }

    function editTerminalTransfer() {
        sleep(2000);
        var editTerminalTransferTable = get_editTerminalTransferTable_FormData();
        var terminalTransferValidate = $("#editTerminalTransfer_model_form").form('validate');

        if (terminalTransferValidate) {
            parent.$.messager.confirm('询问', '确认要保存？', function(b) {
                if (b) {
                    $.ajaxL({
                        type: "POST",
                        url: "/terminal/terminalEdit",
                        dataType:'json',
                        traditional:true,//这使json格式的字符不会被转码
                        contentType:'application/json;charset=UTF-8',
                        data: JSON.stringify({
                            terminalTransferDetailList:editTerminalTransferTable,
                            terminalTransfer:$.serializeObject($("#addTerminalTransfer_model_form"))
                        }),
                        beforeSend : function() {
                            progressLoad();
                        },
                        success: function(data){
                            progressClose();
                            if(!data.success){
                                info(data.msg);
                                return;
                            }
                            info(data.msg);
                            $('#index_tabs').tabs('close', "终端划拨-修改")
                            $('#index_tabs').tabs('close', "处理任务");
                     /*     /!* var currentTab = $('#index_tabs').tabs('getTab',"处理任务");
                            RefreshTab(currentTab);*!/

                            refreshTabData("处理任务",window.top.reload_taskTab);*/
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


    function queryEditTermNum(o) {
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
</script>