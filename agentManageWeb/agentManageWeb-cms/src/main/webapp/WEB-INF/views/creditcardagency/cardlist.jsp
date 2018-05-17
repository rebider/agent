<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var cardList;
    $(function() {
        cardList = $('#cardList').datagrid({
            url : '${path }/creditcard/queryCardList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'ID',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                title : '消费者用户',
                field : 'CUST_ID'
            },  {
                title : '银行名称',
                field : 'BANK_ID'

            },  {
                title : '合作商名称',
                field : 'AGENT_ID'

            },{
                width : '70',
                title : '申请时间',
                field : 'APPLY_DATE'

            } ,{
                title : '身份证号',
                field : 'PID'

            }, {
                title : '手机号',
                field : 'MOBILE'
            },  {
                title : '状态',
                field : 'CARD_STATUS',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 0:
                            return "失效";
                        case 1:
                            return '申请成功';
                        case 2:
                            return '审批中';
                        case 3:
                            return '申请失败';
                    }
                }

            }, {
                title : '分润模式(固定金额，比例)',
                field : 'SHARE_MODE',
                formatter : function(value, row, index) {
                    switch (value) {
                        case "RATE":
                            return "比率";
                        case "STATIC":
                            return '固定金额';
                        case "RATE_CODE":
                            return '比率代码';
                    }
                }
            }, {
                title : '分润比率',
                field : 'SHARE_RATE'
            }, {
                title : '单笔分润封顶',
                field : 'SHARE_CAP'
            } , {
                title : '分润类型(佣金)',
                field : 'SHARE_TYPE',
                formatter : function(value, row, index) {
                    switch (value) {
                        case "YJ":
                            return "佣金";
                        case "SS":
                            return '首刷';
                    }
                }
            },{
            	 field : 'SHARE_AMOUNT',
                 title : '分润实际金额(收到分润金额)',
             }, {
                title : '实际分润时间',
                field : 'REAL_SHARE_DATE'

            },  {
                title : '代付状态',
                field : 'PAY_STATE'

            }
            ] ],
            toolbar : '#agentRuleToolbar'
        });
    });

   function searchAgent() {
       cardList.datagrid('load', $.serializeObject($('#searchcardForm')));
	}
	function cleanAgent() {
		$('#searchcardForm input').val('');
        cardList.datagrid('load', {});
	}


</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false">
        <table id="cardList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
        <form id ="searchcardForm">
            <table>
                <tr>
                    <th>合作商名称:</th>
                    <td><input style="line-height:17px;border:1px solid #ccc" name="agentName"/></td>
                    <th>银行名称:</th>
                    <td>
                        <select class="easyui-combobox"  name="bankId" style="width:160px;">
                            <option></option>
                            <c:forEach var="bank" items="${banklist}" >
                                <option value="${bank.code}">${bank.name}</option>
                            </c:forEach>
                        </select>
                    </td>

                    <th>申请时间:</th>
                    <td><input class="easyui-datetimebox" style="line-height:17px;border:1px solid #ccc" name="time"/></td>
                    <th>身份证号:</th>
                    <td><input style="line-height:17px;border:1px solid #ccc" name="pid"/></td>
                    <th>手机号:</th>
                    <td><input style="line-height:17px;border:1px solid #ccc" name="mobile"/></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchAgent();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanAgent();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>


