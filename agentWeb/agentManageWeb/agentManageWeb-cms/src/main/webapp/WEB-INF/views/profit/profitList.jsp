<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var profitDedcutionList;
    $(function() {
        profitDedcutionList = $('#profitDedcutionList').datagrid({
            url : '${path }/profit/deduction/getProfitDeductionList',
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
				hidden:true
			},{
                title : '月份',
                field : 'deductionDate',
                width : 80
            },{
                title : '上月未扣足',
                field : 'upperNotDeductionAmt',
                width : 130
            },{
                title : '本月新增',
                field : 'addDeductionAmt',
                width : 130
            },{
                title : '总应扣',
                field : 'sumDeductionAmt',
                width : 130
            },{
                title : '本月应扣',
                field : 'mustDeductionAmt',
                width : 130
            },{
                title : '本月实扣',
                field : 'actualDeductionAmt',
                width : 130
            },{
                title : '未扣足',
                field : 'notDeductionAmt',
                width : 130
            },{
                title : '备注',
                field : 'remark',
                width : 130
            } , {
                field : 'action',
                title : '操作',
                width : 160,
                formatter : function(value, row) {
                    var str = '';
                    <%--<shiro:hasPermission name="/creditcardagency/editRule">--%>
                    str += $.formatString('<a href="javascript:void(0)" class="easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="querySettleErrDetail(\'{0}\');" >查看明细</a>', row.id);
                    <%--</shiro:hasPermission>--%>
					if(row.stagingStatus=='0'){
                        str += $.formatString('<a id="stagButton" href="javascript:void(0)" class="easyui-linkbutton-add" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="applyStaging(\'{0}\');" >申请分期</a>', row.id);
                    }else{
                        str += $.formatString('<a id="stagButton" href="javascript:void(0)" class="easyui-linkbutton-add" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="queryStaging(\'{0}\');" >查看分期</a>', row.id);
                    }
                   	return str;
                }
            }
            ]],
            onLoadSuccess:function(data){
                $('.easyui-linkbutton-query').linkbutton();
                $('.easyui-linkbutton-add').linkbutton();
            },
            toolbar : '#settleErrToolbar'
        });

    });

    /**
	 * 查看退单明细
     */
    function querySettleErrDetail(id) {
        addTab({
            title : '退单细数据展示',
            border : false,
            closable : true,
            fit : true,
            href:'${path }/profit/settleErr/gotoProfitSettleErrList?sourceId='+id
        });
    }

    /**
	 * 查看分期
    function queryStaging() {

	}

    /**
	 * 申请分期
     */
    function  applyStaging(id) {
        parent.$.modalDialog({
            title : '申请分期',
            width : 500,
            height : 300,
            href : '${path }/profit/staging/gotoAddPage?sourceId='+id+'&stagType=1',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = profitDedcutionList;
                    var f = parent.$.modalDialog.handler.find('#stagingForm');
                    f.submit();
                }
            } ]
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
    function myformatter(date){
        var y = date.getFullYear();
        var m = date.getMonth()+1;
        var d = date.getDate();
        return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
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
        required:true,
        formatter: function(date){
            var y = date.getFullYear();
            var m = date.getMonth()+1;
            var d = date.getDate();
            return y+"-"+(m<10?('0'+m):m);
        },
        parser: function(s){
            var t = Date.parse(s);
            if (!isNaN(t)){
                return new Date(t);
            } else {
                return new Date();
            }
        }
    })
    function exportDedcution() {
        $('#searchDedcutionForm').form({
            url : '${path }/profit/deduction/export',
            onSubmit : function() {
                return $(this).form('validate');
            }
        });
        $('#searchDedcutionForm').submit();
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="" data-options="region:'west',border:true,title:'业务平台列表'"  style="width:100%;overflow: hidden; ">
		<table id="profitDedcutionList" data-options="fit:true,border:false"></table>
	</div>
    <div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;background-color: #fff">
	   <form id ="searchDedcutionForm">
			<table>
				<tr>
					<th>月份:</th>
					<td>
					<input id="deductionDateStart" name="deductionDateStart">
					</td>
					<th>-</th>
					<td><input id="deductionDateEnd" name="deductionDateEnd"></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchDedcution();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanhDedcution();">清空</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true" onclick="exportDedcution();">导出</a>
                    </td>
				</tr>
			</table>
		</form>
	</div>
</div>


