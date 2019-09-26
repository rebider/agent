<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div id="supplement_data" style="display: none;">
    <form method="post" action="${path}/supplement/queryList" id="supplement_form">
        <table>
            <tr>
                <input type="hidden" name="supplementShrio" <shiro:hasPermission name="/agent/supplementAll">value="all"</shiro:hasPermission>>
                <td>补款类型:</td>
                <td>
                    <select name="pkType" style="width:120px;height:21px">
                        <option value="">--请选择--</option>
                        <c:forEach items="${PkTypeList}" var="PkTypeList">
                            <option value="${PkTypeList.dItemnremark}">${PkTypeList.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>付款方式:</td>
                <td>
                    <select name="payMethod" style="width:100px;height:21px">
                        <option value="">--请选择--</option>
                        <c:forEach items="${PayList}" var="PayList">
                            <option value="${PayList.dItemnremark}">${PayList.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>审批状态:</td>
                <td>
                    <select name="reviewStatus" style="width:100px;height:21px">
                        <option value="">--请选择--</option>
                        <c:forEach items="${agStatusList}" var="agStatusList">
                            <option value="${agStatusList.dStatus}">${agStatusList.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>创建时间:</td>
                <td><input style="border:1px solid #ccc" name="time" class="easyui-datetimebox" editable="false"></td>
                <td>
                    <a class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true"
                       onclick="searchSupplement_list()">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanSupplement();">清空</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="supplement_List" data-options="fit:true,border:false"></table>
    </div>
</div>
<script type="text/javascript">
    var supplement_List;
    $(function () {
        //订单补款
        supplement_List = $('#supplement_List').datagrid({
            url: '${path}/supplement/queryList',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            idField: 'id',
            pageSize: 10,
            queryParams: {
                1:1
                <shiro:hasPermission name="/agent/supplementAll">,supplementShrio:"all"</shiro:hasPermission>
            },
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: '补款类型',
                field: 'PK_TYPE',
                sortable: true
            }, {
                title: '付款方式',
                field: 'PAY_METHOD',
                sortable: true
            }, {
                title: '付款金额',
                field: 'PAY_AMOUNT',
                sortable: true
            }, {
                title: '实际付款金额',
                field: 'REAL_PAY_AMOUNT'
            }, {
                title: '申请备注',
                field: 'REMARK'
            }, {
                title: '代理商唯一编码',
                field: 'AGENT_ID'
            }, {
                title: '代理商名称',
                field: 'AG_NAME'
            }, {
                title: '创建人',
                field: 'C_USER'
            }, {
                title: '审批状态',
                field: 'REVIEW_STATUS'
            }, {
                title: '补款状态',
                field: 'SCHSTATUS'
            }, {
                title: '创建时间',
                field: 'C_TIME'
            }, {
                field: 'action',
                title: '操作',
                width: 150,
                formatter: function (value, row, index) {

                    var str = '';

                    str += $.formatString('<a href="javascript:void(0)" class="supplement-look-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="SerachSupplement(\'{0}\');" >查看</a>', row.ID);
                    if(row.REVIEW_STATUS!='审批中'&&row.REVIEW_STATUS!='审批通过'&&row.REVIEW_STATUS!='审批拒绝')
                    str += $.formatString('<a href="javascript:void(0)" class="supplement-sp-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="examineSupp(\'{0}\');" >提交审批</a>', row.ID);

                    return str;
                }
            }]],
            onLoadSuccess: function (data) {
                $('.supplement-look-easyui-linkbutton-edit').linkbutton({text: '查看'});
                $('.supplement-sp-easyui-linkbutton-edit').linkbutton({text: '提交审批'});
            },
            toolbar: '#supplement_data'
        });
    });

    /**
     * 搜索事件
     */
    function searchSupplement_list() {
        supplement_List.datagrid('load', $.serializeObject($('#supplement_form')));
    }

    /**
     * 清空事件
     */
    function cleanSupplement() {
        $('#supplement_form input').not("input[name='supplementShrio']").val('');
        supplement_List.datagrid('load',$.serializeObject($('#supplement_form')));
    }


    function SerachSupplement(ID) {
        addTab({
            title: '补款审批申请-查看' + ID,
            border: false,
            closable: true,
            fit: true,
            href: '/supplement/queryById?id='+ ID
        });
    }
    //提交审批
    function examineSupp(id) {
        parent.$.messager.confirm('询问', '确认要提交审批？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/supplement/startAg",
                    dataType:'json',
                    data: {agentId:id},
                    success: function(msg){
                        info(msg.resInfo);
                    },
                    complete:function (XMLHttpRequest, textStatus) {
                        supplement_List.datagrid('reload');
                    }
                });
            }
        });
    }
</script>
