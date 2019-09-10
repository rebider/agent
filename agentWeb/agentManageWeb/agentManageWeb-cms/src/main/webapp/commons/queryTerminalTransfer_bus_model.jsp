<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
    var terminalTransferAppDetailsList;
    $(function () {
        terminalTransferAppDetailsList = $('#terminalTransferAppDetailsList').datagrid({
            url: '${path }/terminal/terminalTransferDetailsList',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            fit:false,
            idField: 'id',
            pageSize: 20,
            queryParams: {
                terminalTransferId:"${busId}",
                dataRole:"all"
            },
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: '平台类型',
                field: 'PLATFORM_TYPE',
                formatter : function(value, row, index) {
                    if(value==1){
                       return 'pos'
                    }else if(value==2){
                        return '手刷'
                    }else if(value==3){
                        return '瑞大宝'
                    }else{
                        return value;
                    }
                }
            },{
                    field : 'action',
                    title : '是否已付',
                    width : 120,
                    formatter : function(value, row, index) {
                        var str = '';
                        console.info(22);
                        console.info(row.PLATFORM_TYPE);
                        if(row.PLATFORM_TYPE==1){
                            str+="<input type='checkbox' name = 'isNo'  value='"+row.ID+"'/>是否已付"
                            return str;
                        }

                    }
            },{
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
            }
//            ,{
//                title: '厂商',
//                field: 'PRO_COM'
//            },{
//                title: '型号',
//                field: 'PRO_MODEL'
//            }
            ,{
                title: '原业务平台编码',
                field: 'ORIGINAL_ORG_ID'
            },{
                title: '原业务平台名称',
                field: 'ORIGINAL_ORG_NAME'
            },{
                title: '目标业务平台编码',
                field: 'GOAL_ORG_ID'
            },{
                title: '目标业务平台名称',
                field: 'GOAL_ORG_NAME'
            },{
                title: '代理商ID',
                field: 'AGENT_ID'
            },{
                title: '代理商名称',
                field: 'AG_NAME'
            },{
                title: '对接人',
                field: 'BUTT_JOINT_PERSON_NAME'
            },{
                title: '创建时间',
                field: 'C_TIME'
            }, {
                title: '更新时间',
                field: 'U_TIME'
            }
            ]],
            toolbar: '#terminalTransferAppDetailsToolbar'
        });

       /* // 导入数据
        $("#importTerminalId").click(function(){
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
        });*/
    });



    function exprotTerminal() {
        $("#exprotTerminal").submit();
    }

    function notifyMsg(result) {
        $("#improtMsg").html("");
        var data = eval("(" + result + ")");
        $.each(data,function(n,value) {
            var text = "<tr>";
            text+="<td width='100px'>明细ID</td>";
            text+="<td width='100px'>"+value.id+"</td>";
            text+="<td width='100px'>调整结果</td>";
            text+="<td width='100px'>"+value.adjustStatusCon+"</td>";
            text+="<td width='100px'>备注</td>";
            text+="<td width='100px'>"+value.remark+"</td>";
            text+=" </tr>";
            $("#improtMsg").append(text);
        });
    }

    //勿删
    function terminal_Transfer_bus_model() {
        return "1";
    }
</script>
<form id="exprotTerminal" action="/terminal/exprotTerminalTransferDetails" method="post">
     <input type="hidden" name="terminalTransferId" value="${busId}">
     <input type="hidden" name="excelType" value="1">
     <input type="hidden" name="dataRole" value="all">
</form>
<div id="" data-options="region:'west',border:true,title:'明细列表'" style="width:100%;overflow: hidden; ">
    <table id="terminalTransferAppDetailsList" data-options="fit:true,border:false"></table>
</div>
<%--<div class="easyui-panel" title="导入信息"  data-options="iconCls:'fi-results'">
    <table class="grid" id="improtMsg">
        <c:if test="${improtMsgList!=null}">
            <c:forEach items="${improtMsgList}" var="improtMsg">
                <tr>
                    <td width='100px'>明细ID</td>
                    <td width='100px'>${improtMsg.id}</td>
                    <td width='100px'>调整结果</td>
                    <td width='100px'>${improtMsg.adjustMsg}</td>
                    <td width='100px'>备注</td>
                    <td width='100px'>${improtMsg.remark}</td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
</div>--%>
<div id="terminalTransferAppDetailsToolbar">
    <a onclick="exprotTerminal()" href="javascript:void(0);" class="easyui-linkbutton"
       data-options="plain:true,iconCls:'fi-plus icon-green'">导出</a>
   <%-- <a id="importTerminalId" href="javascript:void(0);" class="easyui-linkbutton"
       data-options="plain:true,iconCls:'fi-plus icon-green'">导入</a>--%>
</div>