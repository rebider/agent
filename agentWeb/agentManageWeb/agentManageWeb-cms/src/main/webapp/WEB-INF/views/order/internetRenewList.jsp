<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var internetRenewList;
    $(function () {
        internetRenewList = $('#internetRenewList').datagrid({
            url: '${path }/internetRenew/internetRenewList',
            striped: true,
            pagination: true,
            rownumbers: true,
            fit: true,
            idField: 'id',
            pageSize: 20,
            pageList: [20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: '申请编号',
                field: 'id'
            },{
                title: '续费方式',
                field: 'renewWayName'
            },{
                title: '数量',
                field: 'renewCardCount'
            },{
                title: '轧差金额',
                field: 'sumOffsetAmt'
            },{
                title: '补缴金额',
                field: 'suppAmt'
            },{
                title: '审批状态',
                field: 'reviewStatus',
                formatter: function (value, row, index) {
                    return db_options.agStatusi_map[value]
                }
            },{
                title: '代理商ID',
                field: 'agentId'
            },{
                title: '代理商名称',
                field: 'agentName'
            },{
                title: '申请备注',
                field: 'applyRemark'
            },{
                title: '创建时间',
                field: 'cTime'
            },{
                title: '审批通过时间',
                field: 'reviewPassTime'
            },{
                title: '创建人',
                field: 'cUser'
            }, {
                field: 'action',
                title: '操作',
                width: 110,
                formatter: function (value, row, index) {
                    var str = '';
                    str += $.formatString('<a href="javascript:void(0)" class="renew-easyui-linkbutton-look" data-options="plain:true,iconCls:\'fi-page\'" onclick="internetCardRenewQuery(\'{0}\',\'{1}\');" >查看</a>', row.id,row.reviewStatus);
                    return str;
                }
            }]],
            onLoadSuccess: function (data) {
                $('.renew-easyui-linkbutton-look').linkbutton({text: '查看'});
            }
        });

    });

    function searchInternetCardRenew() {
        internetRenewList.datagrid('load', $.serializeObject($('#searchCardRenewForm')));
    }

    function cleanInternetCardRenew() {
        $('#searchCardRenewForm input').val('');
        internetRenewList.datagrid('load', {});
    }

    function RefreshInternetCardTab() {
        internetRenewList.datagrid('reload');
    }

    function internetCardRenewQuery(id, cloReviewStatus) {
        addTab({
            title: '物联网卡续费-查看',
            border: false,
            closable: true,
            fit: true,
            href: '/internetRenew/toInternetCardRenewQuery?id=' + id + "&cloReviewStatus=" + cloReviewStatus
        });
    }

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true" style="width:100%;overflow: hidden; ">
        <table id="internetRenewList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
        <form id="searchCardRenewForm">
            <table>
                <tr>
                    <th>申请编号:</th>
                    <td><input name="id" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>续费方式:</th>
                    <td>
                        <select class="easyui-combobox" name="renewWay" style="width:160px;height:21px">
                            <option value="">-全部-</option>
                            <c:forEach items="${internetRenewWayList}" var="internetRenewWay" >
                                <option value="${internetRenewWay.key}">${internetRenewWay.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <th>审批状态:</th>
                    <td>
                        <select class="easyui-combobox" name="reviewStatus"  style="width:160px;height:21px">
                            <option value="">--全部--</option>
                            <c:forEach var="agStatus" items="${agStatusList}">
                                <option value="${agStatus.dItemvalue}">${agStatus.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <c:if test="${isAgent==false}">
                        <th>代理商ID:</th>
                        <td><input name="agentId" style="line-height:17px;border:1px solid #ccc"></td>
                        <th>代理商名称:</th>
                        <td><input name="agentName" style="line-height:17px;border:1px solid #ccc"></td>
                    </c:if>
                </tr>
                <tr>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchInternetCardRenew();">查询</a>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanInternetCardRenew();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>