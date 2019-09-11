<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var addressManageList;
    $(function() {
        addressManageList = $('#addressManageList').datagrid({
            url : '${path }/address/addressManageDataList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 10,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
                title : '地址序号',
                field : 'id'
            },{
                title : '收货人',
                field : 'addrRealname'
            },{
                title : '联系电话',
                field : 'addrMobile'
            },{
                title : '省',
                field : 'addrProvinceString'
            },{
                title : '市',
                field : 'addrCityString'
            },{
                title : '区',
                field : 'addrDistrictString'
            },{
                title : '详细地址',
                field : 'addrDetail'
            },{
                title : '邮编',
                field : 'zipCode'
            },{
                title : '备注',
                field : 'remark'
            },{
                title : '是否默认地址',
                field : 'isdefault',
                formatter : function(value, row, index) {
                            if(1==row.isdefault){
                                return "<span style='color: red;'>默认地址</span>"
                            }
                    return "否";
                }
            },{
                field : 'action',
                title : '操作',
                width : 350,
                formatter : function(value, row, index) {
                    var str = '';
                        <shiro:hasPermission name="/address/addressEdit">
                        str += $.formatString('<a href="javascript:void(0)" class="addressManageList_editAddressManage-up-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="addressManageList_editAddressManage(\'{0}\');" >编辑</a>', row.id);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/address/addressDel">
                        str += $.formatString('&nbsp;||&nbsp;<a href="javascript:void(0)" class="addressManageList_delAddressManage-up-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-trash icon-blue\'" onclick="addressManageList_delAddressManage(\'{0}\');" >删除</a>', row.id);
                        </shiro:hasPermission>
                        return str;
                }
            }
            ]],
            onLoadSuccess:function(data){
                $('.addressManageList_editAddressManage-up-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                $('.addressManageList_delAddressManage-up-easyui-linkbutton-edit').linkbutton({text:'删除'});
            },
            toolbar : '#addressManageListbar'
        });

    });

    function searchAddressManageList() {
        addressManageList.datagrid('load', $.serializeObject($('#addressManageListForm')));
    }
    function cleanAddressManageList() {
        $('#addressManageListForm input').val('');
        addressManageList.datagrid('load', {});
    }

    function RefreshCloudHomePageTab() {
        addressManageList.datagrid('reload');
    }

    function addressManageList_addAddressManage() {
        parent.$.modalDialog({
            title : '地址添加',
            width : 800,
            height : 300,
            maximizable:true,
            href : '${path}/address/addressManageAddView',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = addressManageList;
                    var gr = parent.$.modalDialog.handler.find('#addressAddForm');
                    gr.submit();
                }
            } ]
        });
    }

    function addressManageList_editAddressManage(id){
        parent.$.modalDialog({
            title : '编辑添加',
            width : 800,
            height : 300,
            maximizable:true,
            href : '${path}/address/addressManageEditView?id='+id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = addressManageList;
                    var gr = parent.$.modalDialog.handler.find('#addressAddForm');
                    gr.submit();
                }
            } ]
        });
    }

    function addressManageList_delAddressManage(id){
        parent.$.messager.confirm('询问', '确认要删除么？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: basePath+" /address/addressManageDele?id="+id,
                    dataType:'json',
                    success: function(msg){
                        info(msg.msg);
                        if(msg.ok){
                            searchAddressManageList();
                        }
                    },
                    complete:function (XMLHttpRequest, textStatus) {

                    }
                });
            }
        });
    }


    // 导入数据
    $("#importAddressFun").click(function(){
        parent.$.modalDialog({
            title : '导入地址信息',
            width : 300,
            height : 110,
            href : "/address/importView",
            buttons : [ {
                text : '确定',
                handler : function() {
                    var fun = parent.$.modalDialog.handler.find('#addressImportFileForm');
                    fun.submit();
                }
            } ]
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true"  style="width:100%;overflow: hidden; ">
        <table id="addressManageList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;background-color: #fff">
        <form id ="addressManageListForm">
            <table>
                <tr>
                    <th>姓名:</th>
                    <td><input name="addrRealname" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>手机号:</th>
                    <td><input name="addrMobile" style="line-height:17px;border:1px solid #ccc" ></td>
                    <th>备注:</th>
                    <td><input name="remark" style="line-height:17px;border:1px solid #ccc"></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchAddressManageList();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanAddressManageList();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div id="addressManageListbar">
    <shiro:hasPermission name="/address/addressAdd">
    <a onclick="addressManageList_addAddressManage()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加地址</a>
    </shiro:hasPermission>
    <%--<a href="javascript:void(0);" onclick="showAddressInfoSelectDialog({data:'',callBack:function(item,data){console.log(item)}})" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">选择地址例子</a>--%>
    <shiro:hasPermission name="/address/addressImport">
        &nbsp;&nbsp;&nbsp;&nbsp;
        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-archive icon-green'" id="importAddressFun">导入地址信息</a>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <a href="/static/template/address.xlsx" style="margin:5px 5px 5px 5px" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'" >下载模板</a>
    </shiro:hasPermission>
</div>

