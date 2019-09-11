<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var setServerAmtList;
    $(function() {
        setServerAmtList = $('#setServerAmtList').datagrid({
            url : '${path }/SetServerAmtController/setServerAmtList',
            striped : true,
            pagination : true,
            rownumbers : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
                title : 'ID',
                field : 'id',
                hidden:true
            },{
                title : '日期',
                field : 'createDate',
                align : 'center',
                width : 200
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
				field : 'bumCode',
				align : 'center',
				width : 200
			},{
                title : '业务平台',
                field : 'bumId',
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
                title : '费用名称',
                field : 'serverType',
                align : 'center',
                width : 150,
                formatter : function(value, row, index) {
                    switch (value) {
                        case "00":
                            return '服务费';
                    }
                }
            },{
                title : '收取比列（%）',
                field : 'chargeProportion',
                align : 'center',
                width : 150
            },{
                title : '收取周期',
                field : 'chargePeriod',
                align : 'center',
                width : 150
            },{
				title : '收费类型',
				field : 'chargeType',
				align : 'center',
				width : 150,
                formatter : function(value, row, index) {
                    switch (value) {
                        case "00":
                            return '借记S0';
                        case "02":
                            return '贷记S0';
                        case "03":
                            return '贷记D0';
                        case "01":
                            return '借记D0';
                    }
                }
			},{
                title : '收费基数',
                field : 'chargeBase',
                align : 'center',
                width : 150,
                formatter : function(value, row, index) {
                    switch (value) {
                        case "00":
                            return '交易量总和';
                    }
                }
            },{
                title : '是否包含下级',
                field : 'isNoSon',
                align : 'center',
            	width : 150,
                formatter : function(value, row, index) {
                    switch (value) {
                        case "00":
                            return '是';
                        case "99":
                            return '否';
                    }
                }
            },{
                title : '当前状态',
                field : 'status',
                align : 'center',
                width : 150,
                formatter : function(value, row, index) {
                    switch (value) {
                        case "00":
                            return '生效';
                        case "99":
                            return '失效';
                    }
                }
            },{
                field : 'action',
                title : '操作',
                align : 'center',
                width : 80,
                formatter : function(value, row, index) {
                    var str = '';
                    if(row.status == '00'){
                        str += $.formatString('<a href="javascript:void(0)" class="reward-easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="cancel(\'{0}\');">取消</a>', row.id);
                    }
                    return str;
                }
            }]]
        });
    });

   //申请服务费设置
    function setServerDialog() {
        parent.$.modalDialog({
            title : '收费项目设置',
            width : 1000,
            height : 350,
            href : '${path }/SetServerAmtController/setServerAmtAdd',
            buttons : [ {
                text : '提交',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = setServerAmtList;	//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#setServerAmtAddForm');
                    f.submit();
                }
            }]
        });
    }

    function cancel(id) {

        $.ajax({
            type: 'POST',
            url: '${path }/SetServerAmtController/cancel',
            data: {"id":id},
            dataType: "json",
            success: function (result) {
                setServerAmtList.datagrid('reload');
            },
        });
    }


       function searchreward() {
           $('#setServerAmtList').datagrid('load', $.serializeObject($('#setServerAmtForm')));
        }
        function cleanreward() {
            $('#setServerAmtForm input').val('');
            $('#status').combobox('setValue','');
            setServerAmtList.datagrid('load', {});
        }




</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="" data-options="region:'west',border:true,title:''"  style="width:100%;overflow: hidden; ">
		<table id="setServerAmtList" data-options="fit:true,border:false"></table>
	</div>
    <div data-options="region:'north',border:false" style="height: 90px; overflow: hidden;background-color: #fff">
	   <form  method="post" action="${path }/SetServerAmtController/setServerAmtList" id ="setServerAmtForm" >
		   <table>
			   <tr>
				   <th>代理商名称:</th>
				   <td><input name="agentName" style="line-height:17px;border:1px solid #ccc"></td>
				   <th>代理商唯一码:</th>
				   <td><input name="agentId" style="line-height:17px;border:1px solid #ccc"></td>
				   <th>业务平台:</th>
				   <td><input id="bumId" class="easyui-combobox" name="bumId"
							  data-options="valueField:'BUM_ID',textField:'BUM_NAME',url:'${path}/SetServerAmtController/queryBumId'" />
				   </td>
				   <th>业务编码:</th>
				   <td><input name="bumCode" style="line-height:17px;border:1px solid #ccc"></td>
				   <td>
					   <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchreward();">查询</a>
					   <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanreward();">清空</a>
				   </td>
			   </tr>
			   <tr>
				   <th>状态:</th>
				   <td>
					   <select id="status" class="easyui-combobox" name="status" >
						   <option value="">-----------请选择----------</option>
						   <option value="00">生效</option>
						   <option value="99">失效</option>
					   </select>
				   </td>
			   </tr>
		   </table>
		</form>
<tr>
            <a onclick="setServerDialog();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">服务费设置</a>
</tr>

	</div>



</div>