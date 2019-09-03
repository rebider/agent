<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var posCheckList;
    $(function() {
        posCheckList = $('#posCheckList').datagrid({
            url : '${path }/discount/posCheckList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
				title : '申请日期',
				field : 'appDate',
				align : 'center',
				width : 200,
				/*formatter : function(value, row, index) {
					var str = value.substring(0,value.indexOf(" "));
					return str;
				}*/
			},{
				title : '代理商唯一码',
				field : 'agentId',
				align : 'center',
				width : 200
			},{
				title : '代理商名称',
				field : 'agentName',
				align : 'center',
				width : 200
			},{
				title : '业务编码',
				field : 'busNum',
				align : 'center',
				width : 200
			},{
				title : '业务平台',
				field : 'playformCode',
				align : 'center',
				width : 200,
				formatter : function(value, row, index) {
					for(var i=0;i< db_options.ablePlatForm.length;i++){
						if (db_options.ablePlatForm[i].platformNum == value) {
							var temp = db_options.ablePlatForm[i].platformName;
							return temp;
						}
					}
				}
			},{
				title : '分润比例类型',
				field : 'checkType',
				align : 'center',
				width : 200,
				formatter : function(value, row, index) {
					switch (value) {
						case "0":
							return '付款分润比例';
						case "1":
							return '出款分润比例';
						case "2":
							return '2019新商户付款分润比例';
					}
				}
			},{
				title : '承诺交易金额(万)',
				field : 'totalAmt',
				align : 'center',
				width : 200
			},{
				title : '机具数量(台)',
				field : 'posOrders',
				align : 'center',
				width : 200
			},{
				title : '收取比例(%)',
				field : 'profitScale',
				align : 'center',
				width : 200,
				formatter : function(value, row, index) {
					if ( value == 0) {
						return 0;
					}
					var str = Number(value * 100).toFixed();
					str += "%";
					return str;
				}
			},{
				title : '收取周期',
				field : 'cycle',
				align : 'center',
				width : 200,
				formatter : function(value, row, index) {
					if(row.checkDateE == '1'){
						return row.checkDateS+"--"+'长期';
					}else{
						return row.checkDateS+"--"+row.checkDateE;
					}
				}
			},{
				title : '当前状态',
				field : 'checkStatus',
				align : 'center',
				width : 200,
				formatter : function(value, row, index) {
					switch (value) {
						case "0":
							return '申请中';
						case "1":
							return '生效';
						case "2":
							return '失效';
						case "3":
							return '拒绝';
					}
				}
			},{
				field : 'action',
				title : '操作',
				width : 130,
				formatter : function(value, row, index) {
					var str = '';
						str += $.formatString('<a href="javascript:void(0)" class="check-easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="showActivity(\'{0}\');">查看审批进度</a>', row.id);
					return str;
				}
			}]],
            onLoadSuccess:function(data){
                $('.check-easyui-linkbutton-query').linkbutton({text:'查看审批进度'});
            },
            toolbar : '#checkToolbar'
        });
    });

    function posCheckDialog() {
        parent.$.modalDialog({
            title : '分润比例考核申请',
            width : 500,
            height : 500,
            href : '${path }/discount/addCheckPage',
            buttons : [ {
                text : '提交',
				left:300,
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = posCheckList;	//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#posCheckAddForm');
                    f.submit();
                }
            } ]
        });
    }

    function showActivity(id) {
        addTab({
            title : '分润比例申请审批进度',
            border : false,
            closable : true,
            fit : true,
            href : '/checkActivity/gotoTaskApproval?id='+id
        });
    }


    //查询
   function searchcheck() {
       posCheckList.datagrid('load', $.serializeObject($('#searchcheckForm')));
	}

	//重置
	function cleancheck() {
		$('#searchcheckForm input').val('');
		$('#searchcheckForm select').val('');
        posCheckList.datagrid('load', {});
	}

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="" data-options="region:'west',border:true,title:''"  style="width:100%;overflow: hidden; ">
		<table id="posCheckList" data-options="fit:true,border:false"></table>
	</div>
    <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
	   <form method="post" id ="searchcheckForm" >
		   <table>
			   <tr>
				   <th>代理商名称:</th>
				   <td><input name="agentName" style="line-height:17px;border:1px solid #ccc"></td>
				   <th>代理商唯一码:</th>
				   <td><input name="agentId" style="line-height:17px;border:1px solid #ccc"></td>
				   <th>业务编码</th>
				   <td><input name="busNum" style="line-height:17px;border:1px solid #ccc"></td>
				   <th>状态:</th>
				   <td>
					   <select name="checkStatus" style="width:120px;height:21px">
						   <option value=""></option>
						   <option value="1">生效</option>
						   <option value="2">失效</option>
						   <option value="0">申请中</option>
						   <option value="3">拒绝</option>
					   </select>
				   </td>
				   <td>
					   <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchcheck();">查询</a>
					   <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleancheck();">清空</a>
				   </td>
			   </tr>
		   </table>
		</form>
        <shiro:hasPermission name="/discount/addCheck" >
            <a onclick="posCheckDialog();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">申请分润比例</a>
        </shiro:hasPermission>
	</div>
</div>

