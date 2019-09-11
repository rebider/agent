<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var terminalTransferDetailsList;
    $(function () {
        terminalTransferDetailsList = $('#terminalTransferDetailsList').datagrid({
            url: '${path }/terminal/terminalTransferDetailsList',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            fit: true,
            idField: 'id',
            queryParams: {
                1:1
                <shiro:hasPermission name="/terminalTransferDetail/dataRole">,dataRole:"all"</shiro:hasPermission>
            },
            pageSize: 10,
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: '明细ID',
                field: 'ID'
            },{
                title: '申请ID',
                field: 'TERMINAL_TRANSFER_ID'
            },{
                title: 'SN开始',
                field: 'SN_BEGIN_NUM'
            },{
                title: 'SN结束',
                field: 'SN_END_NUM'
            },{
                title: 'SN数量',
                field: 'COM_SN_NUM'
            },{
                title: '厂商',
                field: 'PRO_COM',
                hidden:true
            },{
                title: '型号',
                field: 'PRO_MODEL',
                hidden:true
            },{
                title: '原业务平台编码',
                field: 'ORIGINAL_ORG_ID'
            },{
                title: '原业务平台名称',
                field: 'ORIGINAL_ORG_NAME'
            },{
                title: '原业务平台类型',
                field: 'ORIGINAL_TYPE'
            },{
                title: '目标业务平台编码',
                field: 'GOAL_ORG_ID'
            },{
                title: '目标业务平台名称',
                field: 'GOAL_ORG_NAME'
            },{
                title: '目标业务平台类型',
                field: 'GOAL_TYPE'
            },{
                title: '代理商唯一编码',
                field: 'AGENT_ID'
            },{
                title: '代理商名称',
                field: 'AG_NAME'
            },{
                title: '对接人',
                field: 'BUTT_JOINT_PERSON_NAME'
            },{
                title: '调整时间',
                field: 'ADJUST_TIME'
            },{
                title: '调整结果',
                field: 'ADJUST_STATUS_MSG'
            },{
                title: '备注',
                field: 'REMARK'
            }/*,{
                title: '批次号',
                field: 'BATCH_NUM'
            }*/,{
                title: '创建时间',
                field: 'C_TIME'
            }, {
                title: '更新时间',
                field: 'U_TIME'
            }, {
                title: '创建人',
                field: 'C_USER_NAME'
            }, {
                title: '更新人',
                field: 'C_USER_NAME'
            }
               <%--<shiro:hasPermission name="/resource/edit">--%>
            <%--, {--%>

                <%--field : 'action',--%>
                <%--title : '操作',--%>
                <%--width : 130,--%>
                <%--formatter : function(value, row, index) {--%>
                    <%--//alert(row.id);--%>
                    <%--var str = '';--%>
                    <%--<shiro:hasPermission name="/resource/edit">--%>
                    <%--str += $.formatString('<a href="javascript:void(0)" class="resource-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="adjust(\'{0}\');" >重新发起</a>', row.id);--%>
                    <%--</shiro:hasPermission>--%>
                    <%--return str;--%>
                <%--}--%>
            <%--}--%>
                <%--</shiro:hasPermission>--%>
            ]]
        });
    });

    function searchTerminalTransferDetails() {
        terminalTransferDetailsList.datagrid('load', $.serializeObject($('#searchTerminalTransferDetailsForm')));
    }

    function cleanTerminalTransferDetails() {
        $('#searchTerminalTransferDetailsForm input').not("input[name='dataRole']").val('');
        terminalTransferDetailsList.datagrid('load',  $.serializeObject($('#searchTerminalTransferDetailsForm')));
    }

   // 导入数据
        $("#importTerminalIdExport").click(function(){
            parent.$.modalDialog({
                title : '导入',
                width : 300,
                height : 110,
                href : "/terminal/importTerminalPage?busId=${busId}",
                buttons : [ {
                    text : '确定',
                    handler : function() {
                        var fun = parent.$.modalDialog.handler.find('#terminalImportFileForm');
                        fun.submit();
                    }
                } ]
            });
        });
//导出
    function exprotTerminal(){
        console.info($.serializeObject($('#searchTerminalTransferDetailsForm')));
        var TerminalTransferDetail = $.serializeObject($('#searchTerminalTransferDetailsForm'));

        var par = {
             adjustStatus: TerminalTransferDetail.adjustStatus,
             agName:TerminalTransferDetail.agName,
             agentId: TerminalTransferDetail.agentId,
             buttJointPersonName: TerminalTransferDetail.buttJointPersonName,
             /*dataRole:TerminalTransferDetail.dataRole,*/
             goalOrgId: TerminalTransferDetail.goalOrgId,
             goalOrgName: TerminalTransferDetail.goalOrgName,
             id: TerminalTransferDetail.id,
             originalOrgId:TerminalTransferDetail.originalOrgId,
             originalOrgName:TerminalTransferDetail.originalOrgName,
             snBeginNum:TerminalTransferDetail.snBeginNum,
             snEndNum: TerminalTransferDetail.snEndNum,
             terminalTransferId: TerminalTransferDetail.terminalTransferId
        };





        $.ajaxL({
            type: "POST",
            url :"/terminal/terminalTransferDetailsListExport",
            dataType:'json',
            contentType:'application/json;charset=UTF-8',
            traditional: true,//这使json格式的字符不会被转码
            data: JSON.stringify(par),
            beforeSend : function() {
                progressLoad();
            },
            success: function(data){
                console.info(data);
                if(data>0){
                    window.location.href='${path}/terminal/exprotTerminalTransferDetails';
                }else{
                    $.messager.alert("提示","查询为空")
                }
            },
            complete:function (XMLHttpRequest, textStatus) {
                progressClose();
            }
        });

    }

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true" style="width:100%;overflow: hidden; ">
        <table id="terminalTransferDetailsList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 90px; overflow: hidden;background-color: #fff">
        <form id="searchTerminalTransferDetailsForm">
            <table>
                <tr>
                    <th width="100px">申请ID:</th>
                    <td>
                        <input name="terminalTransferId" style="line-height:17px;border:1px solid #ccc">
                        <shiro:hasPermission name="/terminalTransferDetail/dataRole">
                            <input name="dataRole" type="hidden" value="all">
                        </shiro:hasPermission>
                    </td>
                    <th>SN开始:</th>
                    <td><input name="snBeginNum" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>SN结束:</th>
                    <td><input name="snEndNum" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>原业务平台编码:</th>
                    <td><input name="originalOrgId" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>原业务平台编名称:</th>
                    <td><input name="originalOrgName" style="line-height:17px;border:1px solid #ccc"></td>
                </tr>
                <tr>
                    <th>目标业务平台编码:</th>
                    <td><input name="goalOrgId" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>目标业务平台名称:</th>
                    <td><input name="goalOrgName" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>调整结果:</th>
                    <td>
                        <select class="easyui-combobox" name="adjustStatus"  style="width:160px;height:21px">
                            <option value="">--全部--</option>
                            <c:forEach var="adjustStatus" items="${adjustStatusList}">
                                <option value="${adjustStatus.key}">${adjustStatus.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <c:if test="${empty agentId}">
                        <th>代理商唯一编码:</th>
                        <td><input name="agentId" style="line-height:17px;border:1px solid #ccc"></td>
                        <th>代理商名称:</th>
                        <td><input name="agName" style="line-height:17px;border:1px solid #ccc"></td>
                    </c:if>
                </tr>
                <tr>
                    <th>对接人:</th>
                    <td><input name="buttJointPersonName" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>明细ID:</th>

                    <td><input name="id" style="line-height:17px;border:1px solid #ccc"></td>

                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-magnifying-glass',plain:true"
                           onclick="searchTerminalTransferDetails();">查询</a>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-x-circle',plain:true"
                           onclick="cleanTerminalTransferDetails();">清空</a>
                        <shiro:hasPermission name="/terminalTransferDetail/Busness">
                            <a id="importTerminalIdExport" href="javascript:void(0);" class="easyui-linkbutton"
                               data-options="plain:true,iconCls:'fi-plus icon-green'">导入</a>
                            <a onclick="exprotTerminal()" href="javascript:void(0);" class="easyui-linkbutton"
                               data-options="plain:true,iconCls:'fi-plus icon-green'">导出</a>
                        </shiro:hasPermission>
                    </td>

                </tr>
            </table>
        </form>
    </div>
</div>
