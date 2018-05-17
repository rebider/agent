<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var agentRule;
    $(function() {
    	agentRule = $('#agentRule').datagrid({
            url : '${path }/creditcardagency/rulelist',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                title : '规则名',
                field : 'ruleName',
            }, {
                title : '业务类型',
                field : 'businessType',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'CREDIT_APY':
                    	return '信用卡代办';
                    }
                }
            }, {
                title : '业务支持级别',
                field : 'businessLevel',
                formatter : function(value, row, index) {
                    return value + '级';
                }
            }, {
                title : '规则类型',
                field : 'ruleType',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 'ZDX':
                            return '针对型';
                        case 'TY':
                            return '通用型';
                    }
                }
            } , {
                title : '启用状态',
                field : 'status',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 0:
                            return '不启用';
                        case 1:
                            return '启用';
                    }
                }
            }, {
                title : '商户',
                field : 'agentId'
            }, {
                title : '银行',
                field : 'sourceId'
            }, {
                title : '分润模式',
                field : 'shareMode',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 'RATE':
                            return '比率';
                        case 'STATIC':
                            return '固定金额';
                        case 'RATE_CODE':
                            return '分润代码';
                    }
                }
            }, {
                title : '分润类型',
                field : 'shareType',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 'YJ':
                            return '佣金';
                        case 'SS':
                            return '首刷';
                    }
                }
            }, {
                title : '分润比例值',
                field : 'shareRate'
            }, {
                title : '佣金',
                field : 'commission'
            }, {
                title : '单笔分润封顶',
                field : 'shareCap'
            }, {
                title : '是否阻断',
                field : 'isBlock'
            }, {
                title : '起效开始时间',
                field : 'startDate'
            }, {
                title : '起效结束时间',
                field : 'endDate'
            } , {
                title : '排序',
                field : 'sort'
            }
            ,{
            	 field : 'action',
                 title : '操作',
                 width : 100,
                 formatter : function(value, row, index) {
                     var str = '';
					 <shiro:hasPermission name="/creditcardagency/editRule">
						 str += $.formatString('<a href="javascript:void(0)" class="rule-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editRuleFun(\'{0}\');" >编辑</a>', row.id);
					 </shiro:hasPermission>
                     return str;
                 }
             }]],
            onLoadSuccess:function(data){
                $('.rule-easyui-linkbutton-edit').linkbutton({text:'编辑'});
            },
            toolbar : '#agentRuleToolbar'
        });
    });

   function searchAgent() {
	   agentRule.datagrid('load', $.serializeObject($('#searchAgentForm')));
	}
	function cleanAgent() {
		$('#searchAgentForm input').val('');
		$("[name='agentStatus']").val('');
		agentRule.datagrid('load', {});
	}

    function addRuleFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 800,
            height : 420,
            maximizable:true,
            href : '${path }/creditcardagency/addRulePage',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = agentRule;
                    var gr = parent.$.modalDialog.handler.find('#ruleAddForm');
                    gr.submit();
                }
            } ]
        });
    }

    function editRuleFun(id) {
        parent.$.modalDialog({
            title : '编辑',
            width : 800,
            height : 420,
            href : '${path }/creditcardagency/editRulePage?id=' + id,
            buttons : [ {
                text : '编辑',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = agentRule;
                    var p = parent.$.modalDialog.handler.find('#ruleEditForm');
                    p.submit();
                }
            } ]
        });
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false">
        <table id="agentRule" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
	   <form id ="searchAgentForm">
			<table>
				<tr>
					<th>规则名称:</th>
					<td><input name="ruleName" style="line-height:17px;border:1px solid #ccc"></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchAgent();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanAgent();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<div id="agentRuleToolbar">
	<shiro:hasPermission name="/creditcardagency/addRule">
		<a onclick="addRuleFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加信用卡代办规则</a>
	</shiro:hasPermission>
</div>

