<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var addressManageList_dialog;
    $(function() {
        addressManageList_dialog = $('#addressManageList_dialog').datagrid({
            url : '${path }/address/addressManageDataList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 20,
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
            }
            ]],
            onDblClickRow:function(dataIndex,rowData){
                if(parent.$.modalDialog.handler.par_callBack_options && typeof parent.$.modalDialog.handler.par_callBack_options.callBack == 'function') {
                    parent.$.modalDialog.handler.par_callBack_options.callBack(rowData, parent.$.modalDialog.handler.par_callBack_options.data);
                }
                parent.$.modalDialog.handler.dialog('close');
            },
            toolbar : '#addressManageList_dialogbar'
        });

    });

    function searchaddressManageList_dialog() {
        addressManageList_dialog.datagrid('load', $.serializeObject($('#addressManageList_dialogForm')));
    }
    function cleanaddressManageList_dialog() {
        $('#addressManageList_dialogForm input').val('');
        addressManageList_dialog.datagrid('load', {});
    }






</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true,title:'收货地址'"  style="width:100%;overflow: hidden; ">
        <table id="addressManageList_dialog" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;background-color: #fff">
        <form id ="addressManageList_dialogForm">
            <table>
                <tr>
                    <th>姓名:</th>
                    <td><input name="addrRealname" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>手机号:</th>
                    <td><input name="addrMobile" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>备注:</th>
                    <td><input name="remark" style="line-height:17px;border:1px solid #ccc"></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchaddressManageList_dialog();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanaddressManageList_dialog();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

