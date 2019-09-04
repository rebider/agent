<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div id="organization_data" style="display: none;">
    <form method="post" action="${path}/oorganization/organizationList" id="organization_form">
        <table>
            <tr>
                <th>机构名称:</th>
                <td><input name="orgName" style="line-height:17px;border:1px solid #ccc"></td>
                <th>机构编号:</th>
                <td><input name="orgId" style="line-height:17px;border:1px solid #ccc"></td>
                <th>业务平台:</th>
                <td>
                    <select name="platId" style="width:140px;height:21px">
                        <option value="">--请选择--</option>
                        <c:forEach items="${ablePlatForm}" var="ablePlatFormItem">
                            <option value="${ablePlatFormItem.platformNum}">${ablePlatFormItem.platformName}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <a class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true"
                       onclick="searchOrganization_list()">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanOrganization();">清空</a>
                </td>
            </tr>
        </table>
    </form>
    <shiro:hasPermission name="/oorganization/organizationAddView">
        <a id="organization_add" href="javascript:void(0);"
           class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加机构</a>
    </shiro:hasPermission>
</div>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="organization_List" data-options="fit:true,border:false"></table>
    </div>
</div>
<script type="text/javascript">
    var organization_List;
    $(function () {
        //订单补款
        organization_List = $('#organization_List').datagrid({
            url: '${path}/oorganization/organizationList',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            idField: 'ORG_ID',
            pageSize: 10,
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: '机构编码',
                field: 'ORG_ID',
                sortable: true
            }, {
                title: '机构名称',
                field: 'ORG_NAME',
                sortable: true
            }, {
                title: '业务平台',
                field: 'PLAT_ID',
                sortable: true,
                formatter: function (value, row, index) {
                    var expresslist = row.PLAT_ID;
                    var express = expresslist.split(',');
                    var platId = " ";
                    if (db_options.platFormList)
                        for (var i = 0; i < db_options.platFormList.length; i++) {
                            for (j = 0; j < express.length; j++) {
                                if (db_options.platFormList[i].platformNum ==  express[j]) {
                                    platId += db_options.platFormList[i].platformName+",";
                                }
                            }
                        }
                    platId = platId.substr(0, platId.length - 1)
                    return platId;
                }
            }, {
                field: 'action',
                title: '操作',
                width: 250,
                formatter: function (value, row, index) {

                    var str = '';
                    <shiro:hasPermission name="/oorganization/queryOrganization">
                    str += $.formatString('<a href="javascript:void(0)" class="organization-easyui-linkbutton-look" data-options="plain:true,iconCls:\'fi-page\'" onclick="queryOrganization(\'{0}\');" >查看</a>', row.ORG_ID);
                    </shiro:hasPermission>

                    <shiro:hasPermission name="/oorganization/organizationEditView">
                    str += $.formatString('<a href="javascript:void(0)" class="organization-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-trash icon-blue\'" onclick="organizationEdit(\'{0}\');" >修改</a>', row.ORG_ID);
                    </shiro:hasPermission>

                    <shiro:hasPermission name="/oorganization/organizationDelete">
                    str += $.formatString('<a href="javascript:void(0)" class="organization-easyui-linkbutton-delete" data-options="plain:true,iconCls:\'fi-trash icon-blue\'" onclick="organizationDelete(\'{0}\');" >删除</a>', row.ORG_ID);

                    </shiro:hasPermission>

                    return str;
                }
            }]],
            onLoadSuccess: function (data) {
                $('.organization-easyui-linkbutton-look').linkbutton({text: '查看'});
                $('.organization-easyui-linkbutton-edit').linkbutton({text: '修改'});
                $('.organization-easyui-linkbutton-delete').linkbutton({text: '删除'});
            },
            toolbar: '#organization_data'
        });

        //添加机构
        $("#organization_add").click(function () {
            addTab({
                title: '添加机构',
                border: false,
                closable: true,
                fit: true,
                href: '/oorganization/organizationAddView'
            });
        });
    });

    /**
     * 搜索事件
     */
    function searchOrganization_list() {
        organization_List.datagrid('load', $.serializeObject($('#organization_form')));
    }

    /**
     * 清空事件
     */
    function cleanOrganization() {
        $('#organization_form input').not("input[name='supplementShrio']").val('');
        $("[name='platId']").val('');
        organization_List.datagrid('load', $.serializeObject($('#organization_form')));
    }


    //查看机构详情
    function queryOrganization(ID) {
        addTab({
            title: '机构详情' + ID,
            border: false,
            closable: true,
            fit: true,
            href: '/oorganization/queryOrganization?id=' + ID
        });
    }

    //修改机构
    function organizationEdit(id) {
        addTab({
            title: '修改机构',
            border: false,
            closable: true,
            fit: true,
            href: '/oorganization/organizationEditView?id=' + id
        });
    }


    //删除机构
    function organizationDelete(id) {
        parent.$.messager.confirm('询问', '确认要删除么？', function (b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: basePath + "/oorganization/organizationDelete?id=" + id,
                    dataType: 'json',
                    success: function (msg) {
                        info(msg.msg);
                        if (msg.ok) {
                            searchOrganization_list();
                        }
                    },
                    complete: function (XMLHttpRequest, textStatus) {

                    }
                });
            }
        });
    }
</script>
