<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var profitDedcutionList;
    $(function () {
        profitDedcutionList = $('#profitDedcutionList').datagrid({
            url: '${path }/profit/deduction/getProfitDeductionList',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            fit: true,
            idField: 'id',
            pageSize: 20,
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: 'id',
                field: 'id',
                hidden: true
            }, {
                title: '上级代理商唯一码',
                field: 'parentAgentId',
                width: 180
            }, {
                title: '上级代理商名称',
                field: 'parentAgentName',
                width: 130
            },
                {
                    title: '代理商唯一码',
                    field: 'agentId',
                    width: 180
                }, {
                    title: '代理商名称',
                    field: 'agentName',
                    width: 130
                },
                {
                    title: '月份',
                    field: 'deductionDate',
                    width: 80
                },/* {
                    title: '上月未扣足',
                    field: 'upperNotDeductionAmt',
                    width: 80
                }, {
                    title: '本月新增',
                    field: 'addDeductionAmt',
                    width: 80
                }, */{
                    title: '总应扣',
                    field: 'sumDeductionAmt',
                    width: 80
                }, {
                    title: '本月应扣',
                    field: 'mustDeductionAmt',
                    width: 80
                }, {
                    title: '本月实扣',
                    field: 'actualDeductionAmt',
                    width: 80
                }, {
                    title: '未扣足',
                    field: 'notDeductionAmt',
                    width: 80
                } , {
                    title: '扣款状态',
                    field: 'deductionStatus',
                    width: 80,
                    formatter: function (value, row) {
                        if (value == '1' ) {
                            return "已扣款";
                        }
                        return "未扣款";
                    }
                }, {
                    title: '扣款描述',
                    field: 'sourceId',
                    width: 130,
                    formatter: function (value, row) {
                        if (value=="02") {
                            return "POS退单";
                        }else{
                            return "手刷退单";
                        }
                    }
                }, {
                    title: '创建时间',
                    field: 'createDateTime',
                    width: 130
                }, {
                    title: '审批记录',
                    field: 'stagingStatus',
                    width: 130,
                    formatter: function (value, row) {
                        var str = '';
                        if (row.stagingStatus == '1' || row.stagingStatus == '2'  ) {
                            str = "审批中";
                        }else if (row.stagingStatus == '3') {
                            str = "通过";
                        }else if (row.stagingStatus == '4') {
                            str = "不通过";
                        }
                        return str;
                    }
                },  {
                    title: '审批记录',
                    field: 'action2',
                    width: 130,
                    formatter: function (value, row) {
                        var str = '';
                        if (row.stagingStatus == '1' || row.stagingStatus == '2' || row.stagingStatus == '3' || row.stagingStatus == '4' ) {
                            str += $.formatString('<a id="stagButton" href="javascript:void(0)" class="easyui-linkbutton-add" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="queryApproval(\'{0}\');" >查看</a>', row.id);
                        }
                        return str;
                    }
                }, {
                    field: 'action',
                    title: '操作',
                    width: 300,
                    formatter: function (value, row) {
                        var str = '';
                        var deductionStatus = row.deductionStatus;
                        <%--<shiro:hasPermission name="/creditcardagency/editRule">--%>
                        if (row.stagingStatus != '5') {
                            str += $.formatString('<a href="javascript:void(0)" class="easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="querySettleErrDetail(\'{0}\');" >查看明细</a>', row.id);
                        }
                        <%--</shiro:hasPermission>--%>
                        if (row.stagingStatus == '0' && deductionStatus!='1' && row.mustDeductionAmt > 0) {
                            if (row.addDeductionAmt != null) {
                                if ('${noEdit}'==0) {
                                    str += $.formatString('<a id="stagButton" href="javascript:void(0)" class="easyui-linkbutton-add" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="applyStaging(\'{0}\');" >申请分期</a>', row.id);
                                }
                            }
                        } else if (row.stagingStatus == '3') {
                            str += $.formatString('<a id="stagButton" href="javascript:void(0)" class="easyui-linkbutton-add" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="queryStaging(\'{0}\');" >查看分期</a>', row.id);
                        } else if (row.stagingStatus == '4') {
                            if ('${noEdit}'==0) {
                                str += $.formatString('<a id="stagButton" href="javascript:void(0)" class="easyui-linkbutton-add" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="applyStaging(\'{0}\');" >审核不通过,重新申请</a>', row.id);
                            }
                        }
                        return str;
                    }
                }
            ]],
            onLoadSuccess: function (data) {
                $('.easyui-linkbutton-query').linkbutton();
                $('.easyui-linkbutton-add').linkbutton();
            },
            toolbar: '#deductionToolbar'
        });

    });

    /**
     * 查看审批明细
     */
    function queryApproval(id) {
        addTab({
            title: '退单审批展示'+id,
            border: false,
            closable: true,
            fit: true,
            href: '${path}/stagActivity/gotoTaskApproval?sourceId=' + id
        });
    }

    /**
     * 查看退单明细
     */
    function querySettleErrDetail(id) {
        addTab({
            title: '退单明细数据展示'+id,
            border: false,
            closable: true,
            fit: true,
            href: '${path}/profit/settleErr/gotoProfitSettleErrList?sourceId='+id
        });
    }

    /**
     * 查看分期
     **/
    function queryStaging(id) {
        addTab({
            title: '分期明细数据展示'+id,
            border: false,
            closable: true,
            fit: true,
            href: '${path}/profit/staging/gotoStagingDetailList?sourceId=' + id
        });
    }

    /**
     * 申请分期
     */
    function applyStaging(id) {
        parent.$.modalDialog({
            title: '申请分期',
            width: 500,
            height: 300,
            href: '${path }/profit/staging/gotoAddPage?sourceId=' + id + '&stagType=1',
            buttons: [{
                text: '确定',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid = profitDedcutionList;
                    var f = parent.$.modalDialog.handler.find('#stagingForm');
                    f.submit();
                }
            }]
        });
    }

    function searchDedcution() {
        profitDedcutionList.datagrid('load', $.serializeObject($('#searchDedcutionForm')));
    }

    function cleanhDedcution() {
        $('#searchDedcutionForm input').val('');
        profitDedcutionList.datagrid('load', {});
    }

    function RefreshCloudHomePageTab() {
        profitDedcutionList.datagrid('reload');
    }

    function myformatter(date) {
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        var d = date.getDate();
        return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
    }

    function myparser(s) {
        if (!s) return new Date();
        var ss = (s.split('-'));
        var y = parseInt(ss[0], 10);
        var m = parseInt(ss[1], 10);
        var d = parseInt(ss[2], 10);
        if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
            return new Date(y, m - 1, d);
        } else {
            return new Date();
        }
    }

    $("#deductionDateStart,#deductionDateEnd").datebox({
        required: true,
        formatter: function (date) {
            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            var d = date.getDate();
            return y + "" + (m < 10 ? ('0' + m) : m);
        },
        parser: function (s) {
            if(s.indexOf('-')<0){
                s=s.substring(0,4)+'-'+s.substring(4,s.length);
            }
            var t = Date.parse(s);
            if (!isNaN(t)) {
                return new Date(t);
            } else {
                return new Date();
            }
        }
    })

    function exportDedcution() {
        $('#searchDedcutionForm').form({
            url: '${path }/profit/deduction/export',
            onSubmit: function () {
                return $(this).form('validate');
            }
        });
        $('#searchDedcutionForm').submit();
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true" style="width:100%;overflow: hidden; ">
        <table id="profitDedcutionList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;background-color: #fff">
        <form id="searchDedcutionForm">
            <table>
                <tr>
                    <th>代理商名称：</th>
                    <td><input name="agentName" id="agentName" style="line-height:17px;border:1px solid #ccc;width:160px;"></td>
                    <th>代理商唯一码:</th>
                    <td><input id="agentId" name="agentId"></td>
                    <th>月份:</th>
                    <td>
                        <shiro:hasPermission name="/profit/deductionAgentQuery">
                            <input id="agent" hidden name="agent" value="0">
                        </shiro:hasPermission>
                        <input id="deductionDateStart" name="deductionDateStart">
                    </td>
                    <th>-</th>
                    <td><input id="deductionDateEnd" name="deductionDateEnd"></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchDedcution();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanhDedcution();">清空</a>
                        <shiro:hasPermission name="/profit/deduction/export">
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'icon-print',plain:true" onclick="exportDedcution();">导出</a>
                        </shiro:hasPermission>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>


