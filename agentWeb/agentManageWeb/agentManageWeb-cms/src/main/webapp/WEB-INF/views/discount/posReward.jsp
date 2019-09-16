<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var posRewardList;
    $(function() {
        posRewardList = $('#posRewardList').datagrid({
            url : '${path }/discount/posRewardList',
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
				field : 'createTm',
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
                title : '业务平台编码',
                field : 'busNum',
                align : 'center',
                width : 200
            },{
				title : '业务平台',
				field : 'busPlatform',
				align : 'center',
				width : 200,
				formatter:function (value, row, index) {
					var strTemp='';
					for(var i=0;i< db_options.ablePlatForm.length;i++){
						if (db_options.ablePlatForm[i].platformType=='POS'&& db_options.ablePlatForm[i].platformNum == value) {
							strTemp = db_options.ablePlatForm[i].platformName;
							break;
						}
					}
					return strTemp;
				}
			},{
                title : '考核周期',
                field : 'appraisalCycle',
                align : 'center',
                width : 200
            },{
                title : '奖励类型',
                field : 'rewardType',
                align : 'center',
                width : 150
            },{
                title : '是否包含其他pos平台',
                field : 'iscontainotherpos',
                align : 'center',
                width : 150,
				formatter : function (value, row, index) {
					if(value=='0'){
						return '否';
					}else
						return '是';
				}
            },{
                title : '承诺交易金额（万）',
                field : 'growAmt',
                align : 'center',
                width : 150
            },{
				title : '机具数量',
				field : 'machineryNum',
				align : 'center',
				width : 150
			},{
                title : '奖励标准',
                field : 'rewardScale',
                align : 'center',
                width : 150
            },{
                title : '考核方式',
                field : 'assessWay',
                align : 'center',
                width : 230,
				formatter : function (value, row, index) {
					if(value=='0'){
						return '单月交易量核算';
					}else
						return '累计交易量考核';
				}
            },{
                title : '奖励核算周期',
                field : 'accountingCycle',
                align : 'center',
                width : 230,
				formatter : function (value, row, index) {
					if(value=='0'){
						return '月度核算';
					}else
						return '季度核算';
				}
            },{
                title : '状态',
                field : 'applyStatus',
                align : 'center',
            	width : 150,
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
				align : 'center',
                width : 150,
                formatter : function(value, row, index) {
                    var str = '';
					str += $.formatString('<a href="javascript:void(0)" class="reward-easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="showActivity(\'{0}\');">查看审批进度</a>', row.id);
                    return str;
                }
            }]],
            onLoadSuccess:function(data){
                $('.reward-easyui-linkbutton-query').linkbutton({text:'查看审批进度'});
            },
            toolbar : '#rewardToolbar'
        });
    });

    //申请奖励form
    function posRewardDialog(id) {
        parent.$.modalDialog({
            title : '优惠政策-POS特殊奖励申请',
            width : 640,
            height : 550,
            href : '${path }/discount/addPage',
            buttons : [ {
                text : '申请',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = posRewardList;	//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#posRewardAddForm');
                    f.submit();
                }
            } ]
        });
    }
    //POS奖励抱团申请奖励form
    function posHuddleRewardDialog(id) {
        parent.$.modalDialog({
            title : '优惠政策-POS特殊奖励申请',
            width : 800,
            height : 350,
            resizable:"true",
            href : '${path }/discount/addHuddlePage',
            buttons : [ {
                text : '申请',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = posRewardList;	//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#posRewardAddForm');
                    f.submit();
                }
            } ]
        });
    }

    function showActivity(id) {
        addTab({
            title : '奖励考核申请审批进度',
            border : false,
            closable : true,
            fit : true,
            href : '/rewardActivity/gotoTaskApproval?id='+id
        });
    }
    function showHuddleActivity(id) {
        addTab({
            title : '抱团奖励考核申请审批进度',
            border : false,
            closable : true,
            fit : true,
            href : '/rewardActivity/gotoHuddleTaskApproval?id='+id
        });
    }

   function searchreward() {
    	console.log($.serializeObject($('#searchrewardForm')));
       $('#posRewardList').datagrid('load', $.serializeObject($('#searchrewardForm')));
	}
	function cleanreward() {
		$('#searchrewardForm input').val('');
		$('#busPlatform').val('0')
		$('#applyStatus').val('0')
        posRewardList.datagrid('load', {});
	}

    function RefreshCloudHomePageTab() {
        posRewardList.datagrid('reload');
    }
    function  only(){
    	console.log('1');
        $('#posRewardList').datagrid({
            url : '${path }/discount/posRewardList',
            striped : true,
            rownumbers : true,
            queryParams:{},
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
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
                title : '申请时间',
                field : 'createTm',
                align : 'center',
                width : 200
            },{
                title : '预发周期（起始）',
                field : 'totalConsMonth',
                align : 'center',
                width : 200
            },{
                title : '预发周期（结束）',
                field : 'creditConsMonth',
                align : 'center',
                width : 150
            },{
                title : '承诺交易金额（万）',
                field : 'growAmt',
                align : 'center',
                width : 150
            },{
                title : '考核月份',
                field : 'totalEndMonth',
                align : 'center',
                width : 150
            },{
                title : '机具数量',
                field : 'machineryNum',
                align : 'center',
                width : 150
            },{
                title : '奖励标准',
                field : 'rewardScale',
                align : 'center',
                width : 150
            },{
                title : '申请状态',
                field : 'applyStatus',
                align : 'center',
                width : 150,
                formatter : function(value, row, index) {
                    switch (value) {
                        case "9":
                            return '新建';
                        case "0":
                            return '申请中';
                        case "1":
                            return '生效';
                        case "2":
                            return '无效';
                    }
                }
            },{
                field : 'action',
                title : '操作',
                width : 150,
                formatter : function(value, row, index) {
                    var str = '';
                    if(row.applyStatus == '0'|| row.applyStatus == '1'){
                        str += $.formatString('<a href="javascript:void(0)" class="reward-easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="showActivity(\'{0}\');">查看审批进度</a>', row.id);
                    }
                    return str;
                }
            }]],
            onLoadSuccess:function(data){
                $('.reward-easyui-linkbutton-query').linkbutton({text:'查看审批进度'});
            },
            toolbar : '#rewardToolbar'
        });
    }
    function more(){
        $('#posRewardList').datagrid({
            url : '${path }/discount/posHuddleRewardList',
            striped : true,
            rownumbers : true,
            queryParams:{},
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
                title : '代理商名称',
                field : 'agentName',
                align : 'center',
                width : 200
            },{
                title : '申请时间',
                field : 'createTm',
                align : 'center',
                width : 200
            },{
                title : '预发周期（起始）',
                field : 'totalConsMonth',
                align : 'center',
                width : 200
            },{
                title : '预发周期（结束）',
                field : 'creditConsMonth',
                align : 'center',
                width : 150
            },{
                title : '承诺交易金额（万）',
                field : 'growAmt',
                align : 'center',
                width : 150
            },{
                title : '考核月份',
                field : 'totalEndMonth',
                align : 'center',
                width : 150
            },{
                title : '机具数量',
                field : 'machineryNum',
                align : 'center',
                width : 150
            },{
                title : '奖励标准',
                field : 'rewardScale',
                align : 'center',
                width : 150
            },{
                title : '所属团',
                field : 'huddleCode',
                align : 'center',
                width : 150
		},{
                title : '申请状态',
                field : 'applyStatus',
                align : 'center',
                width : 150,
                formatter : function(value, row, index) {
                    switch (value) {
                        case "9":
                            return '新建';
                        case "0":
                            return '申请中';
                        case "1":
                            return '生效';
                        case "2":
                            return '无效';
                    }
                }
            },{
                field : 'action',
                title : '操作',
                width : 300,
                formatter : function(value, row, index) {
                    var str = '';
                    if(row.applyStatus == '0'|| row.applyStatus == '1'){
                        str += $.formatString('<a href="javascript:void(0)" class="reward-easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="showHuddleActivity(\'{0}\');">查看审批进度</a>', row.id);
                    }
                    str += $.formatString('<a href="javascript:void(0)" class="reward-easyui-linkbutton-detail" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="showDetail(\'{0}\');">查看明细</a>', row.huddleCode);
                    return str;
                }
            }]],
            onLoadSuccess:function(data){
                $('.reward-easyui-linkbutton-query').linkbutton({text:'查看审批进度'});
                $('.reward-easyui-linkbutton-detail').linkbutton({text:'查看明细'});
            },
            toolbar : '#rewardToolbar'
        });
    }


    function showDetail(huddleCode){
        parent.$.modalDialog({
            title : '抱团明细',
            width : 500,
            height : 300,
            resizable:"true",
            href : '${path }/discount/addHuddleDetail?huddleCode='+huddleCode,

        });
    }


</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="" data-options="region:'west',border:true,title:''"  style="width:100%;overflow: hidden; ">
		<table id="posRewardList" data-options="fit:true,border:false"></table>
	</div>
    <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
	   <form  method="post" action="${path}/reward/exportrewardD" id ="searchrewardForm" >
		   <table>
			   <tr>
				   <th>代理商名称:</th>
				   <td><input name="agentName" style="line-height:17px;border:1px solid #ccc"></td>
				   <th>代理商唯一码:</th>
				   <td><input name="agentId" style="line-height:17px;border:1px solid #ccc"></td>
				   <th>业务编码:</th>
				   <td><input name="busNum" style="line-height:17px;border:1px solid #ccc"></td>
				   <td>
					   <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchreward();">查询</a>
					   <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanreward();">清空</a>
				   </td>
			   </tr>
			   <tr>
				   <th>业务平台:</th>
				   <td>
					   <select name="busPlatform" id="busPlatform" style="width: 148px;height: 21px">
						   <option value="0">请选择</option>
						   <c:forEach items="${platformList}" var="platform">
							   <option value="${platform.getValue()}">${platform.getContent()}</option>
						   </c:forEach>
					   </select>
				   </td>
				   <th>状态</th>
				   <td>
					   <select name="applyStatus" id="applyStatus" style="width: 148px;height: 21px">
						   <option value="0">申请中</option>
						   <option value="1">生效</option>
						   <option value="2">失效</option>
						   <option value="3">拒绝</option>
					   </select>
				   </td>
				   <td>
					   <%--<input type="button" value="单个申请" onclick="only()">&nbsp&nbsp&nbsp<input type="button" value="抱团申请" onclick="more()">--%>
					   <shiro:hasPermission name="/discount/addReward">
						   <a onclick="posRewardDialog();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">申请奖励</a>
					   </shiro:hasPermission>
					   <shiro:hasPermission name="/discount/addHuddleRewardNotUse"><%--抱团申请现在不需要(现将权限名+NotUse)--%>
						   <a onclick="posHuddleRewardDialog();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">抱团申请</a>
					   </shiro:hasPermission>
				   </td>
			   </tr>
		   </table>
		</form>
	</div>



</div>