<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var activityList;
    $(function () {
        activityList = $('#activityList').datagrid({
            url: '${path }/activity/activityList',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            fit: true,
            idField: 'id',
            pageSize: 10,
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: '活动代码',
                field: 'actCode'
            },{
                title: '活动名称',
                field: 'activityName'
            }, {
                title: '商品名称',
                field: 'productId'
            }, {
                title: '机具类型',
                field: 'proType'
            }, {
                title: '优惠条件',
                field: 'activityWay'
            }, {
                title: '参与条件',
                field: 'activityCondition'
            }, {
                title: '优惠方式',
                field: 'activityRule'
            }, {
                title: '开始时间',
                field: 'beginTime'
            }, {
                title: '结束时间',
                field: 'endTime'
            }, {
                title: '平台类型',
                field: 'platform',
                formatter: function (value, row, index) {
                    if (db_options.ablePlatForm)
                        for (var i = 0; i < db_options.ablePlatForm.length; i++) {
                            if (db_options.ablePlatForm[i].platformNum == row.platform) {
                                return db_options.ablePlatForm[i].platformName;
                            }
                        }
                    return "";
                }
            }, {
                title: '原价',
                field: 'originalPrice'
            }, {
                title: '活动价',
                field: 'price'
            }, {
                title: '厂家',
                field: 'vender'
            }, {
                title: '型号',
                field: 'proModel'
            }, {
                title: '保价时间(天)',
                field: 'gTime'
            }, {
                title: '业务平台机具',
                field: 'busProName'
            },{
                title: '终端批次名称',
                field: 'termBatchname'
            },{
                title: '终端类型',
                field: 'termtypename'
            },{
                title: 'pos类型',
                field: 'posType'
            },{
                title: '特价机价格',
                field: 'posSpePrice'
            },{
                title: '达标时间（天）',
                field: 'standTime'
            },{
                title: '达标时间（天）',
                field: 'standTime'
            },{
                title : '可见权限',
                field : 'visible',
                width : 70,
                align : 'center',
                formatter:function(value,row,index){
                    switch(value) {
                        case "1":
                            return '全部可见';
                        case "2":
                            return '部分可见';
                        default:
                            return '不可见';
                    }
                }
            },{
                title: '创建时间',
                field: 'cTime'
            }, {
                title: '更新时间',
                field: 'uTime'
            }, {
                title: '创建人',
                field: 'cUser'
            }, {
                title: '更新人',
                field: 'uUser'
            }, {
                field: 'action',
                title: '操作',
                width: 230,
                formatter: function (value, row, index) {
                    var str = '';
                    <shiro:hasPermission name="/activity/activityEdit">
                    str += $.formatString('<a href="javascript:void(0)" class="activity-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editActivity(\'{0}\');" >编辑</a>', row.id);
                    </shiro:hasPermission>
//                    str += $.formatString('<a href="javascript:void(0)" class="activity-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="delActivity(\'{0}\');" >删除</a>', row.id);
                    <shiro:hasPermission name="/activity/toActivityVisible">
                    str += $.formatString('<a href="javascript:void(0)" class="activity-easyui-linkbutton-visible" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="visibleSet(\'{0}\');" >权限设置</a>', row.id);
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/activity/activityCopy">
                        str += $.formatString('<a href="javascript:void(0)" class="activity-easyui-linkbutton-copy" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="copyActivity(\'{0}\');" >活动复制</a>', row.id);
                    </shiro:hasPermission>
                    return str;
                }
            }
            ]],
            onLoadSuccess: function (data) {
                $('.activity-easyui-linkbutton-edit').linkbutton({text: '编辑'});
//                $('.activity-easyui-linkbutton-del').linkbutton({text: '删除'});
                $('.activity-easyui-linkbutton-visible').linkbutton({text: '权限设置'});
                $('.activity-easyui-linkbutton-copy').linkbutton({text: '活动复制'});
            },
            toolbar: '#activityToolbar'
        });

    });

    function searchActivity() {
        activityList.datagrid('load', $.serializeObject($('#searchActivityForm')));
    }

    function cleanActivity() {
        $('#searchActivityForm input').val('');
        $("[name='platform']").val('');
        $("[name='productId']").val('');
        $("[name='posType']").val('');
        activityList.datagrid('load', {});
    }

    function RefreshCloudHomePageTab() {
        activityList.datagrid('reload');
    }

    function addActivity() {
        parent.$.modalDialog({
            title: '添加',
            width: 600,
            height: 500,
            maximizable: true,
            href: '${path }/activity/toActivityAdd',
            buttons: [{
                text: '确定',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid = activityList;
                    var gr = parent.$.modalDialog.handler.find('#activityAddForm');
                    gr.submit();
                }
            }]
        });
    }

    function editActivity(id) {
        parent.$.modalDialog({
            title: '编辑',
            width: 600,
            height: 500,
            maximizable: true,
            href: '${path }/activity/toActivityEdit?id=' + id,
            buttons: [{
                text: '确定',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid = activityList;
                    var gr = parent.$.modalDialog.handler.find('#activityEditForm');
                    gr.submit();
                }
            }]
        });
    }

    function copyActivity(id) {
        parent.$.modalDialog({
            title: '活动复制',
            width: 600,
            height: 500,
            maximizable: true,
            href: '${path }/activity/toActivityCopy?id=' + id,
            buttons: [{
                text: '确定',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid = activityList;
                    var gr = parent.$.modalDialog.handler.find('#activityCopyForm');
                    gr.submit();
                }
            }]
        });
    }


    function visibleSet(id) {
        parent.$.modalDialog({
            title: '权限设置',
            width: 600,
            height: 500,
            maximizable: true,
            href: '${path }/activity/toActivityVisible?id=' + id,
            buttons: [{
                text: '确定',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid = activityList;
                    var gr = parent.$.modalDialog.handler.find('#activityVisibleForm');
                    gr.submit();
                }
            }]
        });
    }

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true" style="width:100%;overflow: hidden; ">
        <table id="activityList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
        <form id="searchActivityForm">
            <table>
                <tr>
                    <th>活动代码:</th>
                    <td><input name="actCode" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>活动名称:</th>
                    <td><input name="activityName" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>厂商:</th>
                    <td><input name="vender" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>型号:</th>
                    <td><input name="proModel" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>商品名称：</th>
                    <td>
                        <select name="productId" style="width:140px;height:21px">
                            <option value="">--请选择--</option>
                            <c:forEach items="${productList}" var="product">
                                <option value="${product.id}" proModel="${product.proModel}" vender="${product.proCom}" name="${product.name}"
                                        proType="${product.proType}" proTypeName="${product.proTypeName}">${product.proName}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>平台类型：</th>
                    <td>
                        <select name="platform" style="width:140px;height:21px">
                            <option value="">--请选择--</option>
                            <c:forEach items="${platFormList}" var="platFormListItem">
                                <option value="${platFormListItem.platformNum}">${platFormListItem.platformName}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <th>平台机具:</th>
                    <td><input name="busProName" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>终端批次:</th>
                    <td><input name="termBatchname" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>终端类型:</th>
                    <td><input name="termtypename" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>pos类型</th>
                    <td>
                        <select name="posType" style="width:160px;height:21px" >
                            <option value="">--请选择--</option>
                            <c:forEach items="${posTypeList}" var="posType">
                                <option value="${posType.key}">${posType.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchActivity();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanActivity();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div id="activityToolbar">
    <shiro:hasPermission name="/activity/activityAdd">
        <a onclick="addActivity()" href="javascript:void(0);" class="easyui-linkbutton"
           data-options="plain:true,iconCls:'fi-plus icon-green'">添加活动</a>
    </shiro:hasPermission>
</div>