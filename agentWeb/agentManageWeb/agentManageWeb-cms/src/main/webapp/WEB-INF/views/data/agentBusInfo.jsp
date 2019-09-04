<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div id="agnet_list_datahistory" style="display: none;">
    <form method="post" action="${path}/dataHistory/selectAll" id="agent_dataHistory">
        <table>
            <tr>
                <td>数据类型:</td>
                <td>
                    <select name="dataType" style="width:160px;height:21px">
                        <option value="">--请选择--</option>
                        <c:forEach items="${dataList}" var="dataList">
                            <option value="${dataList.dItemnremark}">${dataList.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>数据ID:</td>
                <td><input style="border:1px solid #ccc" name="dataId" ></td>
                <td>
                    <a class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true"
                       onclick="searchData_list()">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanData();">清空</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="agnet_bus_info" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="dataWin" class="easyui-window" title="具体内容" closed="true" style="width:600px;height:200px;"
     data-options="iconCls:'icon-save',modal:true"/>
<script type="text/javascript">
    var agnet_bus_info;
    $(function () {
        //代理商表格
        agnet_bus_info = $('#agnet_bus_info').datagrid({
            url: '${path}/agentBusInfoManage/queryForPage',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            idField: 'id',
            pageSize: 10,
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: 'id',
                field: 'id',
                hidden : true,
                sortable: true
            }, {
                title: '代理商ID',
                field: 'agentId'
            }, {
                title: '业务平台号(平台机构编号)',
                field: 'busNum'
            }, {
                title: '业务平台',
                field: 'busPlatform'
            }, {
                title: '业务类型(一个)',
                field: 'busType'
            }, {
                title: '所属上级代理(平台机构编号)',
                field: 'busParent'
            }, {
                title: '风险承担所属代理商',
                field: 'busRiskParent'
            }, {
                title: '激活及返现所属代理商',
                field: 'busActivationParent'
            }, {
                title: '业务区域(地区)',
                field: 'busRegion'
            }, {
                title: '是否直发',
                field: 'busSentDirectly'
            }, {
                title: '是否直接返现',
                field: 'busDirectCashback'
            }, {
                title: '是否独立考核',
                field: 'busIndeAss'
            }, {
                title: '业务联系人',
                field: 'busContact'
            }, {
                title: '业务联系电话',
                field: 'busContactMobile'
            }, {
                title: '分润对接邮箱',
                field: 'busContactEmail'
            }, {
                title: '业务对接人',
                field: 'busContactPerson'
            }, {
                title: '投诉及风险风控对接邮箱',
                field: 'busRiskEmail'
            }, {
                title: '税点',
                field: 'cloTaxPoint'
            }, {
                title: '是否开具分润发票',
                field: 'cloInvoice'
            }, {
                title: '是否要求收据',
                field: 'cloReceipt'
            }, {
                title: '打款公司',
                field: 'cloPayCompany'
            }, {
                title: '收款账户',
                field: 'cloAgentColinfo'
            }, {
                title: '财务自编码',
                field: 'agZbh'
            }, {
                title: '业务状态',
                field: 'busStatus'
            }, {
                title: '使用范围',
                field: 'busUseOrgan'
            }, {
                title: '审核状态',
                field: 'cloReviewStatus'
            }, {
                title: '创建时间',
                field: 'cTime'
            }, {
                title: '更新时间',
                field: 'cUtime'
            }, {
                title: '入网时间',
                field: 'approveTime'
            }, {
                title: '创建用户',
                field: 'cUser'
            }, {
                title: '状态',
                field: 'status'
            }, {
                title: '版本号',
                field: 'version'
            }, {
                title: '业务范围',
                field: 'busScope'
            }, {
                title: '是否开通s0：1是，0否',
                field: 'dredgeS0'
            }, {
                title: '业务系统登录账号',
                field: 'busLoginNum'
            }, {
                title: '借记费率下限(%)',
                field: 'debitRateLower'
            }, {
                title: '借记封顶额(元)',
                field: 'debitCapping'
            }, {
                title: '借记出款费率(%)',
                field: 'debitAppearRate'
            }, {
                title: '终端数量下限',
                field: 'terminalsLower'
            }, {
                title: '贷记费率下限(%)',
                field: 'creditRateFloor'
            }, {
                title: '业务对接大区(分公司)',
                field: 'agDocDistrict'
            }, {
                title: '业务对接省区（分公司）',
                field: 'agDocPro'
            }, {
                title: '顶级机构',
                field: 'organNum'
            }, {
                title: '出款机构',
                field: 'finaceRemitOrgan'
            }, {
                title: '品牌号',
                field: 'brandNum'
            },{
                field: 'action',
                title: '操作',
                width: 500,
                formatter: function (value, row, index) {
                    var str = '';

                    <shiro:hasPermission name="/agentEnter/agentQuery">
                    str += $.formatString('<a href="javascript:void(0)" class="agentinlist-look-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="agentQueryCity(\'{0}\',\'{1}\');" >查看---</a>', row.ID, row.AG_STATUS);
                    </shiro:hasPermission>

                    <%--<shiro:hasPermission name="/agActivity/startAg">
                        if (row.AG_STATUS != 'Approved' && row.AG_STATUS != 'Approving' && row.AG_STATUS != 'Refuse')
                            str += $.formatString('<a href="javascript:void(0)" class="agentinlist-sp-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="enterIn(\'{0}\');" >提交审批</a>', row.ID);
                    </shiro:hasPermission>--%>

                    if (row.AG_STATUS == 'Approved' && row.C_INCOM_STATUS=='1') {
//                        str += $.formatString('<a href="javascript:void(0)" class="agentinlist-xq-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="a(\'{0}\');" >新签业务</a>', row.id);
                        <%--<shiro:hasPermission name="/agent/baseinfoappy">--%>
                        <%--str += $.formatString('<a href="javascript:void(0)" class="agentinlist-sjxgsq-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="dataChangeAppy(\'{0}\',\'agent\');" >申请修改</a>', row.ID);--%>
                        <%--</shiro:hasPermission>--%>
                        <shiro:hasPermission name="/agent/colinfoappy">
                        str += $.formatString('<a href="javascript:void(0)" class="agentinlist-zhsq-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="dataChangeAppy(\'{0}\',\'agentColInfo\');" >基础信息修改</a>', row.ID);
                        </shiro:hasPermission>
                    }

                    <shiro:hasPermission name="/agentEnter/editinfo">
                    if (row.AG_STATUS == 'Create' )
                        str += $.formatString('<a href="javascript:void(0)" class="agentinlist-up-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="agentEdit(\'{0}\');" >修改</a>', row.ID);
                    </shiro:hasPermission>

                    <shiro:hasPermission name="/agentEnter/toEditCompany/agentEnter/editCompany">
                    if (row.AG_STATUS == 'Approved') {
                        str += $.formatString('<a href="javascript:void(0)" class="company-easyui-linkbutton-editDkgs" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editCompany(\'{0}\');" >修改打款公司</a>', row.ID);
                    }
                    </shiro:hasPermission>

                    <%--<shiro:hasPermission name="/agentEnter/queryMergeBus">--%>
                    if (row.AG_STATUS == 'Approved') {
                        str += $.formatString('<a href="javascript:void(0)" class="agentBus-look-easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="agentBusQuery(\'{0}\');" >查看合并业务</a>', row.ID);
                    }
                    <%--</shiro:hasPermission>--%>

                    <shiro:hasPermission name="/agentEnter/agentUnfreeze">
                    if(row.FREESTATUS == 0) {
                        str += $.formatString('<a href="javascript:void(0)" class="agentinlist-unfreeze-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'icon-ok\'" onclick="agentUnfreeze(\'{0}\');" >解冻</a>', row.ID);
                    }
                    </shiro:hasPermission>
                    return str;
                }
            } ]],
            onDblClickCell: function (rowIndex, field, value) {
                if (field == 'dataCotent') {
                    $('#dataWin').html("");
                    $('#dataWin').html("详情：" + value + "<br>");
                    $('#dataWin').window('open');
                }

            },
            toolbar: '#agnet_list_datahistory'
        });
    });

    /**
     * 搜索事件
     */
    function searchData_list() {
        agnet_bus_info.datagrid('load', $.serializeObject($('#agent_dataHistory')));
    }

    /**
     * 查看历史
     */
    function showListHistory(dataId,dataType){
        addTab({
            title : '历史查看',
            border : false,
            closable : true,
            fit : true,
            href:'dataHistory/selectHistory?dataId='+dataId+'&dataType='+dataType
        });
    }
     /**
     * 清空事件
     */
    function cleanData() {
        $('#agent_dataHistory input').val('');
        $("[name='dataType']").val('');
        agnet_bus_info.datagrid('load', {});
    }
</script>