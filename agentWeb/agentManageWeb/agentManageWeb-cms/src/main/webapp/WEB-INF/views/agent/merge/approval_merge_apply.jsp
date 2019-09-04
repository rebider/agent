<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    function showAgentMainSelectDialog(options) {
        parent.$.modalDialog({
            title: '代理商选择',
            width: 800,
            height: 500,
            href: '/abusinfo/agentMainSelectDialogView'
        });
        parent.$.modalDialog.handler.par_callBack_options = options;
    }
    function showAgentSubSelectDialog(options) {
        parent.$.modalDialog({
            title: '代理商选择',
            width: 800,
            height: 500,
            href: '/abusinfo/agentSubSelectDialogView'
        });
        parent.$.modalDialog.handler.par_callBack_options = options;
    }

    function saveAgentMerge(saveFlag) {
        $("#flag").val(saveFlag);
        var payTempletList = JSON.stringify(getPayList('${paylist_model}'));
        $("#payTemplet").val(payTempletList);
        parent.$.messager.confirm('询问', '确认要保存数据？', function(b) {
            if (b) {
                $.ajax({
                    url: "/agentMerge/saveAgentMerge",
                    type: 'POST',
                    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                    data: $("#agentMargeFrom").serialize(),
                    dataType: 'json',
                    success: function(data) {
                        if (!data.success) {
                            info(data.msg);
                            return;
                        }
                        info(data.msg);
                        $('#index_tabs').tabs('close', "代理商合并-申请");
                        agentMergeList.datagrid('reload');
                    },
                    complete: function (XMLHttpRequest, textStatus) {
                        progressClose();
                    },
                    error: function(data) {
                        parent.$.messager.alert('错误','系统异常，请联系管理员！', 'error');
                    }
                });
            }
        });
    }

    var agentMarge_AttFile_attrDom;
    //上传窗口
    function agentMerge_AttFile_uploadView(t) {
        agentMarge_AttFile_attrDom = $(t).parent().find(".attrInput");
        multFileUpload(agentMerge_AttFile__jxkxUploadFile);
    }
    //附件解析
    function agentMerge_AttFile__jxkxUploadFile(data) {
        var jsonData = eval(data);
        for (var i = 0; i < jsonData.length; i++) {
            $(agentMarge_AttFile_attrDom).append("<span onclick='removeFile(this)'>" + jsonData[i].attName + "<input type='hidden' name='agentMergeFile' value='" + jsonData[i].id + "' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }
    }

</script>
<div>
<form id="agentMargeFrom">
    <input type="hidden" name="flag" id="flag">
    <input type="hidden" name="payTemplet" id="payTemplet">
    <%--主代理商信息--%>
    <div class="easyui-panel" title="主代理商信息" style="background:#fafafa;">
        <div data-options="region:'north',title:'填写主信息',split:true,iconCls:'icon-ok'" style="">
            <table style="min-height: 50px;">
                <tr>
                    <td>代理商名称：</td>
                    <td width="260px">
                        <input id="mainAgentName" name="mainAgentName" value="${data.agent.agName}" type="text" class="easyui-textbox" data-options="prompt:'请选择主代理商'" readonly="readonly">
                        <input type="hidden" id="agentId" name="agentId" value="${data.agent.id}">
                        <c:if test="${!isagent.isOK()}">
                            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="showAgentMainSelectDialog({data:this,callBack:agentMainSelectBuild})">检索代理商</a>
                            <script type="application/javascript">
                                function agentMainSelectBuild(item, data) {
                                    if (item) {
                                        $($(data).parent('td').find('#mainAgentName')).textbox('setValue', item.agName);
                                        $("#mainAgentId").textbox('setValue',item.id);
                                        $($(data).parent('td').find('input[name=\'agentId\']')).val(item.id);
                                    }
                                }
                            </script>
                        </c:if>
                    </td>
                    <td>代理商编码：</td>
                    <td><input id="mainAgentId" class="easyui-textbox" name="mainAgentId" style="width: 100%;" value="" readonly="readonly"/>
                </tr>
            </table>
        </div>
    </div>
    <%--副代理商信息--%>
    <div class="easyui-panel" title="副代理商信息" style="background:#fafafa;">
        <div data-options="region:'north',title:'填写副信息',split:true,iconCls:'icon-ok'" style="">
            <table style="min-height: 50px;">
                <tr>
                    <td>代理商名称：</td>
                    <td width="260px">
                        <input id="subAgentName" name="subAgentName" value="${data.agent.agName}" type="text" class="easyui-textbox" data-options="prompt:'请选择副代理商'" readonly="readonly">
                        <input type="hidden" id="agentId" name="agentId" value="${data.agent.id}">
                        <c:if test="${!isagent.isOK()}">
                            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="showAgentSubSelectDialog({data:this,callBack:agentSubSelectBuild})">检索代理商</a>
                            <script type="application/javascript">
                                function agentSubSelectBuild(item, data) {
                                    if (item) {
                                        $($(data).parent('td').find('#subAgentName')).textbox('setValue', item.agName);
                                        $("#subAgentId").textbox('setValue',item.id);
                                        $($(data).parent('td').find('input[name=\'agentId\']')).val(item.id);
                                        $.ajaxL({
                                            type: 'POST',
                                            url: basePath + '/agentMerge/agentPlatformBus?agentId=' + item.id,
                                            dataType: 'json',
                                            contentType: 'application/json;charset=UTF-8',
                                            beforeSend: function() {
                                                progressLoad();
                                            },
                                            success: function(data) {
                                                $("#subAgentDebt").textbox('setValue',data.debt);
                                                $("#subAgentOweTicket").textbox('setValue',data.oweTicket);
                                                $("#busTypeTable").html("");
                                                $(".bjType").html("");
                                                $(".agentTabel").hide();
                                                $("input[name='suppAgentId']").val("");
                                                $("input[name='suppAgentName']").val("");
                                                var str ="";
                                                $.each(data.agentbus, function(n, item) {
                                                    var busNum = "";
                                                    if(item.BUS_NUM!=undefined){
                                                        busNum = item.BUS_NUM;
                                                    }else{
                                                        busNum = "";
                                                    }
                                                    str+='<tr><td colspan="5"><input type="checkbox" name="busType" value="'+item.ID+'">业务平台：'+item.PLATFORM_NAME+' | 平台类型：'+item.BUS_TYPE_NAME+' | 业务平台编号：'+busNum+'</td></tr>';
                                                });
                                                $("#busTypeTable").html(str);

                                                if(data.debt!='0' || data.oweTicket!='0'){
                                                    var bjstr ="<td>补缴类型：</td><td>";
                                                    $.each(data.mergeSuppTypeList, function(key, value) {
                                                        bjstr += '<input name="suppType" type="radio" value="'+key+'" />'+value;
                                                    });
                                                    bjstr += "</td>";
                                                    $(".bjType").html(bjstr);
                                                    $(".agentTabel").show();
                                                }
                                            },
                                            complete: function() {
                                                progressClose();
                                            }
                                        });
                                    }
                                }
                            </script>
                        </c:if>
                    </td>
                    <td>代理商编码：</td>
                    <td><input id="subAgentId" class="easyui-textbox" name="subAgentId" style="width: 100%;" value="" readonly="readonly"><td/>
                    <td>欠款：</td>
                    <td><input id="subAgentDebt" class="easyui-textbox" name="subAgentDebt" style="width: 100%;" value="0" readonly="readonly"><td/>
                    <td>欠票：</td>
                    <td><input id="subAgentOweTicket" class="easyui-textbox" name="subAgentOweTicket" style="width: 100%;" value="0" readonly="readonly"><td/>
                </tr>
            </table>
            <table id="busTypeTable">
            </table><br>
            <table class="agentTabel" style="display: none">
                <tr class="bjType">
                </tr>
                <tr class="bjxx">
                    <td>代理商ID：</td>
                    <td><input  name="suppAgentId" style="width: 100%;" ><td/>
                    <td>代理商名称：</td>
                    <td><input  name="suppAgentName" style="width: 100%;" ><td/>
                </tr>
            </table>
        </div>
    </div>
    <div class="easyui-panel" title="申请信息" data-options="iconCls:'fi-results'">
        <table class="grid">
            <tbody>
            <tr>
                <td width="80px">打款凭证:</td>
                <td>
                    <a class="attrInput">
                    </a>
                    <a href="javascript:void(0)" class="busck-easyui-linkbutton-edit"
                       data-options="plain:true,iconCls:'fi-magnifying-glass'"
                       onclick="agentMerge_AttFile_uploadView(this)">添加附件</a>
                </td>
            </tr>
            <tr>
                <td width="80px">申请备注</td>
                <td><input name="remark" type="text" class="easyui-validatebox"  style="width:500px;" /></td>
            </tr>
            </tbody>
        </table>
    </div>
</form>
    <%@ include file="/commons/order_cash_receivables.jsp"%>
    <div style="text-align:right;padding:10px;margin-bottom: 50px;">
        <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 160px;" data-options="iconCls:'icon-ok'" onclick="saveAgentMerge(1)">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 160px;" data-options="iconCls:'icon-ok'" onclick="saveAgentMerge(2)">保存并审核</a>
    </div>
</div>