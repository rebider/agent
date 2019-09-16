<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var internetCardList;
    $(function () {
        internetCardList = $('#internetCardList').datagrid({
            url: '${path }/internet/internetCardList',
            striped: true,
            pagination: true,
            rownumbers: true,
            fit: true,
            idField: 'iccidNumId',
            pageSize: 20,
            pageList: [20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                field: 'iccidNumId',
                checkbox:true
            },{
                title: 'iccid号',
                field: 'iccidNum'
            },{
                title: '批次号',
                field: 'batchNum'
            },{
                title: '导入ID',
                field: 'cardImportId'
            },{
                title: '发货厂家',
                field: 'manufacturer'
            },{
                title: '发货日期',
                field: 'deliverTime'
            },{
                title: '订单号',
                field: 'orderId'
            },{
                title: '机具SN',
                field: 'snNum'
            },{
                title: '代理商ID',
                field: 'agentId'
            },{
                title: '代理商名称',
                field: 'agentName'
            },{
                title: '商户名称',
                field: 'merName'
            },{
                title: '商户编码',
                field: 'merId'
            },{
                title: '卡状态',
                field: 'internetCardStatus',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 1:
                            return '正常';
                    }
                    switch (value) {
                        case 2:
                            return '待激活';
                    }
                    switch (value) {
                        case 3:
                            return '停用';
                    }
                    switch (value) {
                        case 4:
                            return '注销';
                    }
                    switch (value) {
                        case 0:
                            return '未知';
                    }
                }
            },{
                title: '收货人',
                field: 'consignee'
            },{
                title: '发卡方',
                field: 'issuer'
            },{
                title: '物联卡号',
                field: 'internetCardNum'
            },{
                title: '开户日期',
                field: 'openAccountTime'
            },{
                title: '到期日期',
                field: 'expireTime'
            },{
                title: '总延期时间',
                field: 'sumPostponeTime',
                formatter : function(value, row, index) {
                    if(value!=null)
                    return  value+'个月';
                }
            },{
                title: '是否需续费',
                field: 'renew',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 0:
                            return '否';
                        case 1:
                            return '是';
                    }
                }
            }
//            ,{
//                title: '是否需关停',
//                field: 'stop',
//                formatter : function(value, row, index) {
//                    switch (value) {
//                        case 0:
//                            return '否';
//                        case 1:
//                            return '是';
//                    }
//                }
//            }
            ,{
                title: '关停原因',
                field: 'stopReason'
            },{
                title: '续费状态',
                field: 'renewStatus',
                formatter : function(value, row, index) {
                    switch (value) {
                        case "1":
                            return '未续费';
                        case "2":
                            return '已续费';
                        case "3":
                            return '未扣足';
                        case "4":
                            return '续费中';
                    }
                }
            }, {
                field: 'action',
                title: '操作',
                width: 200,
                formatter: function (value, row, index) {
                    var str = '';
                    <shiro:hasPermission name="/internetRenew/toInternetRenewAdd">
                        if(row.renewButton=='1')
                        str += $.formatString('<a href="javascript:void(0)" class="in-card-up-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="renewSave(\'{0}\',\'{1}\');" >续费</a>', row.iccidNumId,row.agentId);
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/internetRenew/postponeSave">
                        if(row.expireTime!=null && row.internetCardStatus!=4)
                        str += $.formatString('<a href="javascript:void(0)" class="in-card-pone-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="postponeSave(\'{0}\',\'{1}\');" >延期</a>', row.iccidNumId);
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/internetRenew/postponeSee">
                        if(row.expireTime!=null)
                        str += $.formatString('<a href="javascript:void(0)" class="in-card-poneSee-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="postponeSee(\'{0}\',\'{1}\');" >查看延期</a>', row.iccidNumId);
                    </shiro:hasPermission>
                    return str;
                }
            }]],
            onLoadSuccess: function (data) {
                $('.in-card-up-easyui-linkbutton-edit').linkbutton({text: '续费'});
                $('.in-card-pone-easyui-linkbutton-edit').linkbutton({text: '延期'});
                $('.in-card-poneSee-easyui-linkbutton-edit').linkbutton({text: '查看延期'});
            },
            toolbar: '#internetCardToolbar'
        });

    });

    function searchInternetCard() {
        internetCardList.datagrid('load', $.serializeObject($('#searchInternetCardForm')));
    }

    function cleanInternetCard() {
        $('#searchInternetCardForm input').val('');
        internetCardList.datagrid('load', {});
    }

    function RefreshInternetCardTab() {
        internetCardList.datagrid('reload');
    }

    function internetCardexport() {
        $("#searchInternetCardForm").submit();
    }

    function updateMechIsNull() {
        $.ajax({
            url :"${path}/internet/updateMechIsNull",
            type:'POST',
            dataType:'json',
            success:function(result){
                if(result.status==200){
                    info("处理中请稍后 。。。");
                }
            },
            error:function(data){
                info("系统异常，请联系管理员！");
            }
        });
    }

    function batchRenewSave() {
        var selRows = $('#internetCardList').datagrid('getChecked');
        if(selRows.length==0){
            info("最少选中一条记录");
            return false;
        }
        var iccidNumIds = "";
        var agentId = "";
        for(var i=0;i<selRows.length;i++){
            iccidNumIds += selRows[i].iccidNumId+",";
            if(i==0){
                agentId = selRows[i].agentId;
            }
            if(agentId!=selRows[i].agentId){
                info("必须选择同一代理商");
                return false;
            }
        }
        iccidNumIds = iccidNumIds.substr(0,iccidNumIds.length-1);

        var flag = "";
        $.ajax({
            url: "/internetRenew/renewVerify",
            type:'POST',
            async:false,
            data: {"iccidNumIds":iccidNumIds},
            dataType:'json',
            success:function(data){
                if(data.status!=200){
                    flag = data.msg;
                }
            },
            error:function(data){
                info("系统异常，请联系管理员！");
            }
        });
        if(flag!=''){
            info(flag);
            return false;
        }
        renewAddTab(iccidNumIds,agentId);
    }

    function renewSave(iccidNumId,gentId) {
        if(iccidNumId==''){
            info("最少选中一条记录");
            return false;
        }
        renewAddTab(iccidNumId,gentId);
    }


    function renewAddTab(iccidNumId, agentId) {
        sessionStorage.setItem("iccidNumIds",iccidNumId);
        addTab({
            title: '物联网卡_续费',
            border: false,
            closable: true,
            fit: true,
            href: '/internetRenew/toInternetRenewAdd?agentId='+agentId
        });
    }



    function postponeSave(iccidNumId) {
        if(iccidNumId==''){
            info("最少选中一条记录");
            return false;
        }

        parent.$.modalDialog({
            title: '设置延期',
            width: 300,
            height: 200,
            maximizable: true,
            href: '${path }/internet/toInternetCardPostpone?iccidNum='+iccidNumId,
            buttons: [{
                text: '确定',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid = internetCardList;
                    var gr = parent.$.modalDialog.handler.find('#postponeAddForm');
                    gr.submit();
                }
            }]
        });
    }


    function postponeSee(iccidNumId) {
        if(iccidNumId==''){
            info("最少选中一条记录");
            return false;
        }
        parent.$.modalDialog({
            title: '查看延期',
            width : 900,
            height : 450,
            maximizable: true,
            href: '${path }/internet/toInternetCardPostponeSee?iccid='+iccidNumId
        });
    }

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true" style="width:100%;overflow: hidden; ">
        <table id="internetCardList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 150px; overflow: hidden;background-color: #fff">
        <form id="searchInternetCardForm" action="/internet/internetCardexport" method="post">
            <table>
                <tr>
                    <th width="60px">iccid:</th>
                    <td><input name="iccidNum" style="line-height:17px;border:1px solid #ccc" placeholder="支持模糊查询,多个逗号分隔" ></td>
                    <th width="60px">iccid开始:</th>
                    <td><input name="iccidNumBegin" style="line-height:17px;border:1px solid #ccc"></td>
                    <th width="60px">iccid结束:</th>
                    <td><input name="iccidNumEnd" style="line-height:17px;border:1px solid #ccc"></td>
                    <th width="60px">物联卡号:</th>
                    <td><input name="internetCardNum" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>批次号:</th>
                    <td><input name="batchNum" style="line-height:17px;border:1px solid #ccc"></td>
                </tr>
                <tr>
                    <th>SN:</th>
                    <td><input name="snNum" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>导入ID:</th>
                    <td><input name="cardImportId" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>续费状态:</th>
                    <td>
                        <select class="easyui-combobox" name="renewStatus" style="width:160px;height:21px">
                            <option value="">-全部-</option>
                            <c:forEach items="${internetRenewStatusList}" var="internetRenewStatus" >
                                <option value="${internetRenewStatus.key}">${internetRenewStatus.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <th>订单号:</th>
                    <td><input name="orderId" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>卡状态:</th>
                    <td>
                        <select class="easyui-combobox" name="internetCardStatus" style="width:160px;height:21px">
                            <option value="">-全部-</option>
                            <c:forEach items="${internetCardStatusList}" var="internetCardStatus" >
                                <option value="${internetCardStatus.key}">${internetCardStatus.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>是否续费:</th>
                    <td>
                        <select class="easyui-combobox" name="renew" style="width:150px;height:21px">
                            <option value="">-全部-</option>
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                    </td>
                    <%--<th>是否关停:</th>--%>
                    <%--<td>--%>
                        <%--<select class="easyui-combobox" name="stop" style="width:150px;height:21px">--%>
                            <%--<option value="">-全部-</option>--%>
                            <%--<option value="1">是</option>--%>
                            <%--<option value="0">否</option>--%>
                    <%--</select>--%>
                    </td>
                    <th>开户开始</th>
                    <td>
                        <input name="openAccountTimeBeginStr" type="text" class="easyui-datebox" editable="false" placeholder="请输入">
                    </td>
                    <th>开户结束</th>
                    <td>
                        <input name="openAccountTimeEndStr" type="text" class="easyui-datebox" editable="false" placeholder="请输入">
                    </td>
                    <th>到期开始</th>
                    <td>
                        <input name="expireTimeBeginStr" type="text" class="easyui-datebox" editable="false" placeholder="请输入">
                    </td>
                    <th>到期结束</th>
                    <td>
                        <input name="expireTimeEndStr" type="text" class="easyui-datebox" editable="false" placeholder="请输入">
                    </td>
                </tr>
                <tr>
                    <c:if test="${isAgent==false}">
                        <th>代理商ID:</th>
                        <td><input name="agentId" style="line-height:17px;border:1px solid #ccc"></td>
                    </c:if>
                    <c:if test="${isAgent==false}">
                        <th>代理商名称:</th>
                        <td><input name="agentName" style="line-height:17px;border:1px solid #ccc"></td>
                    </c:if>
                </tr>
            </table>
            <table>
                <tr>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchInternetCard();">查询</a>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanInternetCard();">清空</a>
                    </td>
                    <td>
                        <shiro:hasPermission name="/internet/internetCardexport">
                            <a href="javascript:void(0);" class="easyui-linkbutton"
                               data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="internetCardexport()">导出</a>
                        </shiro:hasPermission>
                    </td>
                    <td>
                        <shiro:hasPermission name="/internet/updateMechIsNull">
                            <a href="javascript:void(0);" class="easyui-linkbutton"
                               data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="updateMechIsNull()">更新商户</a>
                        </shiro:hasPermission>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div id="internetCardToolbar">
    <shiro:hasPermission name="/internetRenew/toInternetRenewAdd">
        <a onclick="batchRenewSave()" href="javascript:void(0);" class="easyui-linkbutton"
           data-options="plain:true,iconCls:'fi-save icon-green'">批量续费</a>
    </shiro:hasPermission>
</div>
