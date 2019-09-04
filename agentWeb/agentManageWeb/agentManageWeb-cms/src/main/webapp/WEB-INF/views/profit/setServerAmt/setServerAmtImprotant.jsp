<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var setServerAmtDetailList;
    $(function() {
        setServerAmtDetailList = $('#setServerAmtDetailList').datagrid({
            url : '${path }/SetServerAmtController/setServerAmtDetailList',
            striped : true,
            pagination : true,
            rownumbers : true,
            singleSelect : true,
            fit : true,
            idField : 'psad',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
                title : '服务设置表主键',
                field : 'psa',
                hidden:true
            },{
                title : '服务明细表主键',
                field : 'psad',
                hidden:true
            },{
                title : '月份',
                field : 'SERVER_DATE',
                align : 'center',
                width : 200
            },{
				title : '代理商唯一码',
				field : 'AGENT_ID',
                align : 'center',
                width : 200
			},{
                title : '代理商名称',
                field : 'AGENT_NAME',
                align : 'center',
                width : 200
            },{
				title : '业务编码',
				field : 'BUM_CODE',
				align : 'center',
				width : 200
			},{
                title : '业务平台',
                field : 'BUM_ID',
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
                field : 'SERVER_TYPE',
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
                field : 'CHARGE_PROPORTION',
                align : 'center',
                width : 150
            },{
                title : '收取周期',
                field : 'CHARGE_PERIOD',
                align : 'center',
                width : 150
            },{
                title : '收费基数',
                field : 'CHARGE_BASE',
                align : 'center',
                width : 150,
                formatter : function(value, row, index) {
                    switch (value) {
                        case "00":
                            return '交易量总和';
                    }
                }
            },{
                title : '交易额总额',
                field : 'SUM_AMT',
                align : 'center',
            	width : 150,
            },{
                title : '收费类型',
                field : 'CHARGE_TYPE',
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
                title : '手续费金额',
                field : 'SERVER_AMT',
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
            }]]
        });
    });



       function searchreward() {
           $('#setServerAmtDetailList').datagrid('load', $.serializeObject($('#setServerAmtDetailForm')));
        }
        function cleanreward() {
            $('#setServerAmtDetailForm input').val('');
            setServerAmtDetailList.datagrid('load', {});
        }




</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="" data-options="region:'west',border:true,title:''"  style="width:100%;overflow: hidden; ">
		<table id="setServerAmtDetailList" data-options="fit:true,border:false"></table>
	</div>
    <div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;background-color: #fff">
	   <form  method="post" action="${path }/SetServerAmtController/setServerAmtDetailList" id ="setServerAmtDetailForm" >
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
				   <td>
					   <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchreward();">查询</a>
					   <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanreward();">清空</a>
				   </td>
			   </tr>

		   </table>
		</form>
<tr>

</tr>

	</div>
</div>