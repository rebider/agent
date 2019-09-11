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

    function editAgentMerge() {
        var payTempletList = JSON.stringify(getUpdatePayList('${paylist_model}'));
        $("#payTemplet").val(payTempletList);
        parent.$.messager.confirm('询问', '确认要保存数据？', function(b) {
            if (b) {
                $.ajax({
                    url: "/agentMerge/editAgentMerge",
                    type: 'POST',
                    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                    data: $("#agentMargeEditFrom").serialize(),
                    dataType: 'json',
                    success: function(data) {
                        if (!data.success) {
                            info(data.msg);
                            return;
                        }
                        info(data.msg);
                        $('#index_tabs').tabs('close', "代理商合并-修改");
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

    function removeMergeEditFile(t){
        parent.$.messager.confirm('询问', '确定删除附件么？', function(b) {
            if (b) {
                $(t).parent().remove();
            }
        });
    }

    var mergeEdit_AttFile_attrDom;
    //上传窗口
    function mergeEdit_AttFile_uploadView(t) {
        mergeEdit_AttFile_attrDom = $(t).parent().find(".attrInput");
        multFileUpload(mergeEdit_AttFile__jxkxUploadFile);
    }
    //附件解析
    function mergeEdit_AttFile__jxkxUploadFile(data) {
        var jsonData = eval(data);
        for (var i = 0; i < jsonData.length; i++) {
            $(mergeEdit_AttFile_attrDom).append("<span ><a onclick='removeMergeEditFile(this)'>" + jsonData[i].attName + "</a><input type='hidden' name='agentMergeFile' value='" + jsonData[i].id + "' /><a href='<%=imgPath%>"+jsonData[i].attDbpath+"' data-options='plain:true' target='_blank' >查看</a>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }
    }

</script>
<div>
    <form id="agentMargeEditFrom">
        <input type="hidden" name="payTemplet" id="payTemplet">
        <input type="hidden" name="proIns" id="proIns" value="${proIns}">
        <%--主代理商信息--%>
        <input type="hidden" class="easyui-textbox" id="id" name="id" value="${agentMerge.id}">
        <div class="easyui-panel" title="主代理商信息" style="background:#fafafa;">
            <div data-options="region:'north',title:'填写主信息',split:true,iconCls:'icon-ok'" style="">
                <table style="min-height: 50px;">
                    <tr>
                        <td>代理商名称：</td>
                        <td width="260px">
                            <input id="mainAgentName" name="mainAgentName" value="${agentMerge.mainAgentName}" type="text" class="easyui-textbox" data-options="prompt:'请选择主代理商'" readonly="readonly">
                            <input type="hidden" id="agentId" name="agentId" value="${data.agent.id}">
                            <c:if test="${!isagent.isOK()}">
                                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="showAgentMainSelectDialog({data:this,callBack:agentMainSelectBuild})">检索代理商</a>
                                <script type="application/javascript">
                                    function agentMainSelectBuild(item, data) {
                                        if (item) {
                                            $($(data).parent('td').find('#mainAgentName')).textbox('setValue', item.agName);
                                            $("#mainAgentId").textbox('setValue', item.id);
                                            $($(data).parent('td').find('input[name=\'agentId\']')).val(item.id);
                                        }
                                    }
                                </script>
                            </c:if>
                        </td>
                        <td>代理商编码：</td>
                        <td><input id="mainAgentId" name="mainAgentId" value="${agentMerge.mainAgentId}" class="easyui-textbox" style="width: 100%;" readonly="readonly"/>
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
                            <input id="subAgentName" name="subAgentName" value="${agentMerge.subAgentName}" type="text" class="easyui-textbox" data-options="prompt:'请选择副代理商'" readonly="readonly">
                            <input type="hidden" id="agentId" name="agentId" value="${data.agent.id}">
                            <c:if test="${!isagent.isOK()}">
                                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="showAgentSubSelectDialog({data:this,callBack:agentSubSelectBuild})">检索代理商</a>
                                <script type="application/javascript">
                                    function agentSubSelectBuild(item, data) {
                                        if (item) {
                                            $($(data).parent('td').find('#subAgentName')).textbox('setValue', item.agName);
                                            $("#subAgentId").textbox('setValue', item.id);
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
                                                    $("#editBusTypeTable").html("");
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
                                                    $("#editBusTypeTable").html(str);

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
                        <td><input id="subAgentId" name="subAgentId" value="${agentMerge.subAgentId}" class="easyui-textbox" style="width: 100%;" readonly="readonly"/><td/>
                        <td>欠款：</td>
                        <td><input id="subAgentDebt" class="easyui-textbox" name="subAgentDebt" style="width: 100%;" value="${agentMerge.subAgentDebt}" readonly="readonly"><td/>
                        <td>欠票：</td>
                        <td><input id="subAgentOweTicket" class="easyui-textbox" name="subAgentOweTicket" style="width: 100%;" value="${agentMerge.subAgentOweTicket}" readonly="readonly"><td/>
                    </tr>
                </table>
                <table id="editBusTypeTable">
                    <c:forEach items="${agentMerge.subAgentBusInfoList}" var="subAgentBusInfo">
                        <tr>
                            <td colspan="5"><input type="checkbox" name="busType" value="${subAgentBusInfo.ID}"
                            <c:forEach items="${agentMerge.agentMergeBusInfosList}" var="agentMergeBusInfo">
                                <c:if test="${agentMergeBusInfo.busId==subAgentBusInfo.ID}">checked</c:if>
                            </c:forEach>
                            >
                                业务平台：${subAgentBusInfo.PLATFORM_NAME} |
                                平台类型：${subAgentBusInfo.BUS_TYPE_NAME} |
                                业务平台编号：
                                <c:if test="${subAgentBusInfo.BUS_NUM!='undefined'}">
                                    ${subAgentBusInfo.BUS_NUM}
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <table class="agentTabel" <c:if test="${agentMerge.suppType==3}">style="display: none"</c:if>>
                    <tr class="bjType">
                        <td>补缴类型：</td>
                        <td>
                            <c:forEach items="${mergeSuppTypeList}" var="mergeSuppType">
                                <input name="suppType" type="radio" value="${mergeSuppType.key}"
                                <c:if test="${agentMerge.suppType==mergeSuppType.key}">checked="checked"</c:if>/>${mergeSuppType.value}
                            </c:forEach>
                        </td>
                    </tr>
                    <tr class="bjxx">
                        <td>代理商ID：</td>
                        <td><input  name="suppAgentId" style="width: 100%;" value="${agentMerge.suppAgentId}"><td/>
                        <td>代理商名称：</td>
                        <td><input  name="suppAgentName" style="width: 100%;" value="${agentMerge.suppAgentName}"><td/>
                    </tr>
                </table>
            </div>
        </div>
        <div class="easyui-panel" title="申请信息" data-options="iconCls:'fi-results'">
            <table class="grid">
                <tbody>
                <tr>
                    <td width="80px">打款凭证:</td>
                    <td colspan="5">
                        <span class="attrInput">
                            <c:if test="${!empty agentMerge.attachments}">
                                <c:forEach items="${agentMerge.attachments}" var="attachment">
                                     <span>
                                             <a onclick='removeMergeEditFile(this)'>${attachment.attName}</a>
                                             <input type='hidden' name='agentMergeFile' value='${attachment.id}' />
                                            <a href="<%=imgPath%>${attachment.attDbpath}" target="_blank" >查看</a>&nbsp;&nbsp;&nbsp;&nbsp;
                                     </span>
                                </c:forEach>
                            </c:if>
                        </span>
                        <a href="javascript:void(0)" class="busck-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'"
                           style="margin-left: 20px" onclick="mergeEdit_AttFile_uploadView(this)">添加附件</a>
                    </td>
                </tr>
                <tr>
                    <td width="80px">申请备注</td>
                    <td><input name="remark" type="text" class="easyui-validatebox"  style="width:500px;" value="${agentMerge.remark}"/></td>
                </tr>
                </tbody>
            </table>
        </div>
    </form>
    <%@ include file="/commons/order_cash_receivables_edit.jsp"%>
    <div style="text-align:right;padding:10px;margin-bottom: 50px;">
        <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 160px;" data-options="iconCls:'icon-ok'" onclick="editAgentMerge()">修改</a>
    </div>
</div>