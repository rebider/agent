<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var tempAgreeDataGrid;
    $(function () {
        tempAgreeDataGrid = $('#tempAgreeListId').datagrid({
            url : '${path }/tempAgreement/list',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
                title : 'id',
                field : 'id',
                hidden : true
            },{
                title : '协议名称',
                field : 'agreName'
            },{
                title : '协议版本',
                field : 'agreVersion'
            },{
                title : '协议类型',
                field : 'agreType',
            },{
                title : '展示方式',
                field : 'agreViewType'
            },{
                title : '创建时间',
                field : 'cTime'
            },{
                title : '更新时间',
                field : 'cUtime'
            },{
                title : '状态',
                field : 'status',
                formatter : function(value, row, index) {
                    switch (value) {
                        case "2":
                            return '无效';
                        case "1":
                            return '生效';
                    }
                }
            }, {
                field : 'action',
                title : '操作',
                width : 170,
                formatter : function(value, row, index) {
                    var str = '';
                    str += $.formatString('<a href="javascript:void(0)" class="role-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editTempAgreeFun(\'{0}\');" >编辑</a>', row.id);
                    str += $.formatString('&nbsp;||&nbsp;<a href="<%=imgPath%>'+row.attrid+'"  class="easyui-linkbutton" data-options="plain:true" target="_blank">下载附件</a>', row.attrid);
                    return str;
                }
            }
            ]],
            onLoadSuccess:function(data) {
                $('.tzsdcl-up-easyui-linkbutton-edit').linkbutton({text: '编辑'});
            },
            toolbar : '#tempAgreeToolbar'
        });
    });

    function searchTempl() {
        tempAgreeDataGrid.datagrid('load', $.serializeObject($('#tempAgreeForm')));
    }

    function cleanTempl() {
        $('#tempAgreeForm input').val('');
        tempAgreeDataGrid.datagrid('load', {});
    }

    $("#addTempAgreeIdAdd").click(function(){
        addTab({
            title : '新增协议模板',
            border : false,
            closable : true,
            fit : true,
            href:'/tempAgreement/addPage'
        });
    });

    function editTempAgreeFun(id){
        addTab({
            title : '修改协议模板',
            border : false,
            closable : true,
            fit : true,
            href:'/tempAgreement/editPage?id='+id
        });
    }

</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true"  style="width:100%;overflow: hidden; ">
        <table id="tempAgreeListId" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;background-color: #fff">
        <form id ="tempAgreeForm">
            <table>
                <tr>
                    <th>协议名称:</th>
                    <td><input name="agreName" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>协议版本:</th>
                    <td><input name="agreVersion" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>状态:</th>
                    <td>
                        <select name="status" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value=""></option>
                            <option value="1">生效</option>
                            <option value="2">无效</option>
                        </select>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchTempl();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanTempl();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div id="tempAgreeToolbar" style="display: none;">
    <a id="addTempAgreeIdAdd" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
</div>
