<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var AgentUserA;
    $(function() {
    	AgentUserA = $('#AgentUserA').datagrid({
            url : '${path }/agentUser/agentUserList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'userId',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                width : '80',
                title : '用户名',
                field : 'userName',
            }, {
                width : '80',
                title : '用户来源',
                field : 'userSource',
                formatter : function(value, row, index) {
                switch (value) {
                case 'S':
                    return '手刷';
                case 'E':
                    return '瑞刷';
                case 'Y':
                    return '瑞烟';
                case 'D':
                    return '其他';
                }
            }
            },{
                width : '90',
                title : '用户状态',
                field : 'userStatus',
                formatter : function(value, row, index) {
                switch (value) {
                case 'N':
                    return '冻结';
                case 'Y':
                    return '正常';
                }
            }
            }, {
                width : '170',
                title : '商户名称',
                field : 'merchantName',
            } ] ],
         onLoadSuccess:function(data){
             $('.product-easyui-linkbutton-edit').linkbutton({text:'编辑'});
             $('.product-easyui-linkbutton-del').linkbutton({text:'删除'});
             $('.product-easyui-linkbutton-query').linkbutton({text:'查看'});
         },
            toolbar : '#agentUserToolbar'
        });
    });
    </script>
    <div class="easyui-layout" data-options="fit:true,border:false">
        <div data-options="region:'center',border:false">
            <table id="AgentUserA" data-options="fit:true,border:false"></table>
        </div>

