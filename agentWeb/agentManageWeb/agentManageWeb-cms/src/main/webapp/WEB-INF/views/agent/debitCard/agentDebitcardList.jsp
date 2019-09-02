<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>

<script type="text/javascript">

    var dataTableInfoList ;

    $(function() {
        dataTableInfoList = $('#agentCardList').datagrid({
            url : '${path}/agentDebitCard/getAgentCardList',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            fit: true,
            idField: 'id',
            pageSize: 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [[{
                title : '代理商唯一码',
                field : 'ID',
                align : 'center',
                width:140
            },{

                title : '代理商名称',
                field : 'AG_NAME',
                align : 'center',
                width:140
            },{
                title : '审批状态',
                field : 'AG_STATUS',
                align : 'center',
                width:140,
                formatter:function (value, row,index) {
                    return db_options.agStatuss_map[value];
                }
            },{
                title : '收款账户类型',
                field : 'CLO_TYPE',
                align : 'center',
                width:120,
                formatter:function (value, row,index) {
                    switch (value) {
                        case 2:
                            return '对私';
                        case 1:
                            return '对公';
                    }
                }

            },{
                title : '账户验证状态',
                field : 'FLAG',
                align : 'center',
                width:140,
                formatter:function (value, row,index) {
                    switch (value) {
                        case '1':
                            return '已生批';
                        case '0':
                            return '未处理';
                        case '2':
                            return '打款失败-已重试';
                        case '3':
                            return '复合通过';
                        case '4':
                            return '风险拦截';
                        case '5':
                            return '已撤销';
                        case '20':
                            return '银行处理失败';
                        case '7':
                            return '复合中';
                        case '8':
                            return '复合不通过';
                        case '9':
                            return '银行处理成功';
                        case '11':
                            return '打款成功';
                        case '12':
                            return '打款失败';
                    }
                }
            },{
                title : '收款账户名',
                field : 'CLO_REALNAME',
                align : 'center',
                width:140
            },{
                title : '收款账号',
                field : 'CLO_BANK_ACCOUNT',
                align : 'center',
                width:140
            },{
                title : '收款账户总行',
                field : 'CLO_BANK',
                align : 'center',
                width:140
            },{
                title : '收款账户支行',
                field : 'CLO_BANK_BRANCH',
                align : 'center',
                width:140
            },{
                title : '总行联行号',
                field : 'ALL_LINE_NUM',
                align : 'center',
                width:140
            },{
                title : '支行联行号',
                field : 'BRANCH_LINE_NUM',
                align : 'center',
                width:140
            },{
                title : '支付流水id',
                field : 'PAY_ID',
                align : 'center',
                hidden:true
            },{
                title : '收款账户id',
                field : 'AC_ID',
                align : 'center',
                hidden:true
            },{
                title : '操作',
                field : 'aaa',
                align : 'center',
                width:200,
                formatter: function (value, row,index) {
                    var str = '';
                    str += $.formatString('<a id="stagButton" href="javascript:void(0)" class="easyui-linkbutton-add" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="queryDetailInfo(\'{0}\');" >查看详情</a>', row.ID);
                    <shiro:hasPermission name="/agentUpdateApy/agentByidForUpdateColInfoView">
                        str += '&nbsp;&nbsp;&nbsp;&nbsp;';
                        str += $.formatString('<a id="stagButton" href="javascript:void(0)" class="easyui-linkbutton-add" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="applyUpdate(\'{0}\');" >申请修改</a>', row.ID);
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/agentDebitCard/doUpdateAdvice">
                        if(row.SUGGEST_STATUS != '1'){
                        str += '&nbsp;&nbsp;&nbsp;&nbsp;';
                        str += $.formatString('<a id="stagButton" href="javascript:void(0)" class="easyui-linkbutton-add" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="adviceUpdate(\'{0}\');" >通知修改</a>', row.AC_ID);
                        }
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/agentDebitCard/doUpdateAdvice">
                        str += '&nbsp;&nbsp;&nbsp;&nbsp;';
                        str += $.formatString('<a id="stagButton" href="javascript:void(0)" class="easyui-linkbutton-add" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="updateuNow(\'{0}\',\'{1}\');" >直接修改</a>', row.ID,row.AC_ID);
                    </shiro:hasPermission>
                    return str;
                }
            }]],
        });
    });

    //直接修改
    function updateuNow(id,acId) {
        if(id == '' || id == undefined){
            alertMsg("系统错误，请重试！");
        }
        addTab({
            title: '代理商收款信息编辑' + id,
            border: false,
            closable: true,
            fit: true,
            href: '/agentDebitCard/updateNow?id=' + id + '&acId='+acId
        });
    }

    //申请修改
    function applyUpdate(id){
        if(id == '' || id == undefined){
            alertMsg("系统错误，请重试！");
            return;
        }
        addTab({
            title: '代理商收款信息修改申请' + id,
            border: false,
            closable: true,
            fit: true,
            href: '/agentUpdateApy/agentByidForUpdateColInfoView?id=' + id
        });
    }

    function updateNoticeStatus(id) {
        $.ajaxL({
            type: "POST",
            url: "/agentDebitCard/doUpdateAdvice",
            dataType: 'json',
            data: {"id": id,"status": '0'},
            success: function (data) {
                if(data.resCode == '1'){
                }
            }

        });
    }

    //通知修改
    function adviceUpdate(id) {
        if(id == '' || id == undefined){
            alertMsg("系统错误，请重试！");
            return;
        }
        parent.$.messager.confirm('询问', '确认要通知对应省区？', function (b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/agentDebitCard/doUpdateAdvice",
                    dataType: 'json',
                    data: {id: id,status: '1'},
                    success: function (data) {
                        console.log(data.resCode);
                        if(data.resCode == '1'){
                            info("通知成功！");
                            dataTableInfoList.datagrid('load', $.serializeObject($('#AgentCardSearchForm')));
                            /*$("#hiddenSuggestInfo").show();
                            console.log("show");
                            var timer = setTimeout(function () {
                                $("#hiddenSuggestInfo").css('display','none');
                            }, 500);*/
                        }else{
                            info("通知失败!");
                        }
                    },
                    complete: function (XMLHttpRequest, textStatus) {

                    }
                });
            }
        });
    }

    //查看详情
    function queryDetailInfo(id) {
        console.log(id);
        if(id == '' || id == undefined){
            alertMsg("系统错误，请重试！");
            return;
        }
        addTab({
            title: '查看详情'+id,
            border: false,
            closable: true,
            fit: true,
            href:'/agentDebitCard/toAgentDetailPage?id='+id
        });
    }

    // 查询
    function queryInfo() {
        dataTableInfoList.datagrid('load', $.serializeObject($('#AgentCardSearchForm')));
    }
    //重置
    function cleanSearchInfo() {
        $("#AgentCardSearchForm input").val("");
        $("#cloCheckStatus").val("");
        $("#cloType").val("");
        $("#agStatus").val("");
        dataTableInfoList.datagrid('load', $.serializeObject($('#AgentCardSearchForm')));
    }

    function alertMsg(str) {
        parent.$.messager.alert('提示', str, 'info');
    }
    
    // 导出
    function exports() {
        $('#AgentCardSearchForm').form({
            url : '/agentDebitCard/doDownload',
        });
        $('#AgentCardSearchForm').submit();
    }

</script>

<div id="hiddenSuggestInfo" style="display: none;">通知已发送至省区</div>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="dataTableInfo" data-options="region:'west',border:true" style="width: 100%;overflow: hidden;">
        <table id="agentCardList" data-options="fit:true,border:false"></table>
    </div>
    <div id="cardDataForm" data-options="region:'north',border:false" style="height:58px;overflow: hidden;">
        <form id="AgentCardSearchForm" method="post">
            <table>
                <tr>
                   <%-- <th>审批状态:</th>
                    <td>
                        <select name="agStatus" id="agStatus" style="width:140px;height:21px">
                            <option value="">--请选择--</option>
                            <c:forEach items="${agStatuss}" var="aagStatusItem">
                                <c:if test="${aagStatusItem.dItemvalue}">
                                    <option value="${aagStatusItem.dItemvalue}">${aagStatusItem.dItemname}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </td>--%>
                    <th>收款账户类型:</th>
                    <td>
                        <select name="cloType" id="cloType" style="width:140px;height:21px">
                            <option value="">--请选择--</option>
                            <c:forEach items="${colInfoType}" var="ColInfoTypeItem">
                                <option value="${ColInfoTypeItem.dItemvalue}">${ColInfoTypeItem.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <th>代理商名称:</th>
                    <td>
                        <input name="agentName" style="border: 1px solid #ccc;width: 160px" />
                    </td>
                    <th>代理商唯一码:</th>
                    <td>
                        <input name="agentId" style="border: 1px solid #ccc;width: 160px" />
                    </td>
                       <th>收款账户名:</th>
                       <td>
                           <input name="cloRealname" style="line-height: 17px;border: 1px solid #ccc;width: 160px"/>
                       </td>
                </tr>
                <tr>

                    <th>账户验证状态:</th>
                    <td>
                        <select name="flag" id="cloCheckStatus" style="width:140px;height:21px">
                            <option  value="">---请选择---</option>
                            <c:forEach items="${flagList}" var="flagListItem"  >
                                <option value="${flagListItem.key}">${flagListItem.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <th></th>
                    <td></td>
                    <th></th>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'icon-search',plain:true" onclick="queryInfo();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanSearchInfo();">清空</a>
                        <shiro:hasPermission name="/agentCard/export">
                            <a href="javascript:void(0);" class="easyui-linkbutton"
                               data-options="iconCls:'fi-x-circle',plain:true" onclick="exports();">导出</a>
                        </shiro:hasPermission>
                    </td>
                    <th></th>
                    <td></td>
                </tr>
            </table>
        </form>
    </div>
</div>
