<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div id="plannerListToolbar" style="display: none;">
	<form  method="post" action="" id ="plannerList_searchform">
		<table>
			<tr>
				<td>订单编号:</td>
				<td><input name="orderId" style="line-height:17px;border:1px solid #ccc" type="text"></td>
				<td>订单生效日期:</td>
				<td><input   name="oInuretime" placeholder="订单生效日期" class="easyui-datebox"></td>
				<td>订单子编号:</td>
				<td><input  style="border:1px solid #ccc" name="receiptNum" type="text"></td>
				<td>收货人姓名:</td>
				<td><input  style="border:1px solid #ccc" name="addrRealname" type="text"></td>
				<td>代理商名称:</td>
				<td><input  style="border:1px solid #ccc" name="agentName" type="text"></td>
			</tr>
			<tr>
				<td>商品名称:</td>
				<td><input  style="border:1px solid #ccc" name="proName" type="text"></td>
				<td>活动名称:</td>
				<td><input  style="border:1px solid #ccc" name="avtivityName" type="text"></td>
				<td>
					<a  class="easyui-linkbutton" data-options="iconCls:'fi-save',region:'center'" onclick="batchPlanner()">批量排单</a>
				</td>
				<td>
					<a  class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchPlannerList()">查询</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanPlannerList();">清空</a>
					<a  class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="inputTotalNumber();">&nbsp;统计&nbsp;</a>
					<shiro:hasPermission name="/planner/exportPlanner">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true" onclick="exportPlannerData();">导出待排单</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</table>
		<div align="left">
			<tr>
				<td>&nbsp;订货量 : <font id="proNum">0</font>&nbsp;&nbsp;</td>
				<td>已排量 : <font id="sendNum">0</font>&nbsp;&nbsp;</td>
				<td>待排单 : <font id="forSendNum">0</font>&nbsp;&nbsp;</td>
				<td>数量 : <font id="totalNum">0</font>&nbsp;&nbsp;</td>
			</tr>
		</div>
	</form>
</div>
<div  class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',fit:true,border:false">
		<table id="plannerList" data-options="fit:true,border:false"></table>
	</div>
</div>
<script type="text/javascript">
    var plannerList;
    $(function() {

        $.extend($.fn.datagrid.defaults.editors, {
            combobox: {
                init: function (container, options) {
                    var proId = $(container).parent().parent().parent().parent().parent().parent().find("td[field='PRO_ID']").children().text();
                    var orderId = $(container).parent().parent().parent().parent().parent().parent().find("td[field='ORDER_ID']").children().text();
                    var activity = [];
                    $.ajaxL({
                        type: "POST",
                        url: basePath+"/planner/findListByProCodeFroPlanner",
                        dataType:'json',
                        async:false,
                        data: {orderId:orderId,proId:proId},
                        beforeSend:function(){
                            progressLoad();
                        },
                        success: function(msg){
                            if(msg && msg.length>0){
                                activity.push('<select style="height:20px;" >');
                                activity.push('<options>');
                                activity.push('<option value="" price="">--请选择--</option>');
                                for (var i=0;i<msg.length;i++){
                                    activity.push('<option value="'+msg[i].proCom+'" proModel="'+msg[i].proModel+'" activityId="'+msg[i].id+'">' );
                                    activity.push(msg[i].proComName+"/"+msg[i].proModel+"/"+msg[i].busProName+"/"+msg[i].backType+"/"+msg[i].standTime+"/"+msg[i].standAmt);
                                    activity.push('</option>');
                                }
                                activity.push('</options>');
                                activity.push('</select>');
                            }
                        },
                        complete:function(){
                            progressClose();
                        }
                    });
                    var input = $(activity.join('')).appendTo(container);
                    return input;
                },
                destroy: function (target) {
                    $(target).remove();
                },
                getValue: function (target) {
                    return $(target).val();
                },
                setValue: function (target, value) {
                    $(target).val(value);
                },
                resize: function (target, width) {
                    $(target)._outerWidth(width);
                }
            }
        });

        plannerList = $('#plannerList').datagrid({
            url : '${path}/planner/plannerList',
            rownumbers : true,
            striped : true,
            pagination : true,
            iconCls:'icon-save',
            editors:$.fn.datagrid.defaults.editors,
            idField : 'ID',
            pageSize : 10,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [
			{
				field : 'ID',
                checkbox:true
			},{
                title : '订单编号',
                field : 'ORDER_ID'
            } , {
                title : '子订单编号',
                field : 'RECEIPT_NUM'
            },{
                title : '代理商唯一编码',
                field : 'AGENT_ID'
            },{
				title : '订单时间',
				field : 'O_INURETIME'
            },{
				title : '代理商名称',
				field : 'AG_NAME'
			} ,{
                title : '收货姓名',
                field : 'ADDR_REALNAME'
            }  ,{
				title : '商品Id',
				field : 'PRO_ID'
			} ,{
				title : '商品编号',
				field : 'PRO_CODE'
			} ,{
                title : '商品名称',
                field : 'PRO_NAME'
            } ,{
                title : '订货量',
                field : 'PRO_NUM'
            }  ,{
                title : '已排量',
                field : 'SEND_NUM'
            } ,{
				title : '待排单',
				field : 'FOR_SEND_NUM',
				sortable : true,
                    formatter : function(value, row, index) {
                        return row.PRO_NUM-row.SEND_NUM;
                    }
            },{
                title : '厂家/机型',
                field : 'proCom',
                width:120,
				editor:'combobox'
            } ,{
                title : '数量',
                field : 'planProNum',
                editor:"numberbox",
                width:80
            },{
                title : '活动名称',
				field : 'ACTIVITY_NAME'
			},{
				title : '订单备注',
				field : 'ORDER_REMARK',
                width : 100
            },{
                field : 'action',
                title : '操作',
                width : 100,
                formatter : function(value, row, index) {
                    var str = '';
                    str += $.formatString('<a href="javascript:void(0)" class="plannerList-look-easyui-linkbutton-add" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="savePlanner(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\',\'{5}\');" >保存</a>', row.ID,index,row.RECEIPT_PRO_ID,row.PRO_NUM,row.ORDER_ID,row.SEND_NUM);
                    return str;
                }
            }  ] ],
            onLoadSuccess:function(data){
                $('.plannerList-look-easyui-linkbutton-add').linkbutton({text:'保存'});
                for (var i in data.rows){
                    plannerList.datagrid("beginEdit",i);
                }
				inputTotalNumber();
            },
            onDblClickRow: function (rowIndex, rowData) {
            },
            onBeforeEdit:function(index,row){
                row.editing = true;
            },
            onAfterEdit:function(index,row){
                row.editing = false;
//                saveRowAction(row);
            },
            onCancelEdit:function(index,row){
                row.editing = false;
            },
            toolbar : '#plannerListToolbar'
        });
    });

    /**
	 * 用户输入统计	(统计数量)
	 */
	function inputTotalNumber() {

		var totalNumber = 0, proNumber = 0, sendNumber = 0;
		var totalRows = $("#plannerList").datagrid("getRows");

		if (totalRows.length > 0) {
			for(var i=0; i<totalRows.length; i++){

				var proNum = totalRows[i].PRO_NUM;
				var sendNum = totalRows[i].SEND_NUM;
				var planProNum = plannerList.datagrid("getEditor",{index:i,field:'planProNum'});
				var planProNumVal = $(planProNum.target).val();

				if (parseInt(planProNumVal) > 0) {
					totalNumber += parseInt(planProNumVal);
				}
				proNumber += proNum;
				sendNumber += sendNum;
			}
			$("#proNum").html(proNumber);
			$("#sendNum").html(sendNumber);
			$("#forSendNum").html(proNumber-sendNumber);
			$("#totalNum").html(totalNumber);
		}
	}

    /**
     * 搜索事件
     */
    function searchPlannerList() {
        plannerList.datagrid('load', $.serializeObject($('#plannerList_searchform')));
    }

    function cleanPlannerList() {
        $('#plannerList_searchform input').val('');
        plannerList.datagrid('load', {});
    }

    function exportPlannerData() {
        $('#plannerList_searchform').form({
            url: '${path }/planner/exportPlanner',
            onSubmit: function() {
                return $(this).form('validate');
            }
        });
        $('#plannerList_searchform').submit();
    }

    function savePlanner(id,index,receiptProId,proNum,orderId,sendNum) {
		var proCom = plannerList.datagrid("getEditor",{index:index,field:'proCom'});
        console.log(proCom);
		var planProNum = plannerList.datagrid("getEditor",{index:index,field:'planProNum'});
		var proComVal = $(proCom.target).val();
		var proModelVal = $(proCom.target).find("option:selected").attr("proModel");
		var activityId = $(proCom.target).find("option:selected").attr("activityId");
		var planProNumVal = $(planProNum.target).val();
		if(proComVal=='' || proComVal==undefined){
			info("请填写厂家");
			return false;
		}
		if(planProNumVal=='' || planProNumVal==undefined){
			info("请填写数量");
			return false;
		}
		if(parseInt(planProNumVal)>parseInt(proNum)-parseInt(sendNum)){
			info("排单数量不能大于订货量");
			return false;
		}
        parent.$.messager.confirm('询问', '确认要保存？', function(b) {
            if (b) {
				$.ajaxL({
					type: "POST",
					url: "${path}/planner/savePlanner",
					dataType:'json',
					data: {
						receiptId:id,
						proCom:proComVal,
						model:proModelVal,
						planProNum:planProNumVal,
						orderId:orderId,
						receiptProId:receiptProId,
                        activityId:activityId
					},
					success: function(result){
						if (result.success) {
							plannerList.datagrid('load', {});
							info(result.msg);
						}else{
							info(result.msg);
						}
					}
				});
            }
        });
    }

    function batchPlanner() {
        var selRows = $('#plannerList').datagrid('getChecked');
        if(selRows.length==0){
            info("最少选中一条记录");
            return false;
        }
        var flag = true;
        var receiptPlanList = [];
        for(var i=0;i<selRows.length;i++){
            var proCom = plannerList.datagrid("getEditor",{index:selRows[i].ROWNUM_-1,field:'proCom'});
            var proComVal = $(proCom.target).val();
            var proModelVal = $(proCom.target).find("option:selected").attr("proModel");
            var activityId = $(proCom.target).find("option:selected").attr("activityId");
            var planProNum = plannerList.datagrid("getEditor",{index:selRows[i].ROWNUM_-1,field:'planProNum'});
            var planProNumVal = $(planProNum.target).val();
            var proNum = selRows[i].PRO_NUM;
            var sendNum = selRows[i].SEND_NUM;
            var proId = selRows[i].RECEIPT_PRO_ID;
            var orderId = selRows[i].ORDER_ID;
            var receiptId = selRows[i].ID;
            if(proComVal=='' || proComVal==undefined){
				info("请填写厂家");
				flag = false;
                break;
			}
			if(proModelVal=='' || proModelVal==undefined){
				info("请填写型号");
                flag = false;
				break;
			}
			if(planProNumVal=='' || planProNumVal==undefined){
				info("请填写数量");
                flag = false;
                break;
			}
            if(parseInt(planProNumVal)>parseInt(proNum)-parseInt(sendNum)){
                info("排单数量不能大于订货量");
                return false;
            }
            var data = {};
            data.proCom = proComVal;
            data.model = proModelVal;
            data.planProNum = planProNumVal;
            data.proId = proId;
            data.receiptId = receiptId;
            data.orderId = orderId;
            data.activityId = activityId;
			receiptPlanList.push(data);
		}
		console.log(receiptPlanList);
		if(flag){
			parent.$.messager.confirm('询问', '确认要批量保存？', function(b) {
				if (b) {
					$.ajaxL({
						type: "POST",
						url: "/planner/batchPlanner",
						dataType: 'json',
						traditional: true,//这使json格式的字符不会被转码
						contentType: 'application/json;charset=UTF-8',
						data: JSON.stringify({
                            receiptPlanList:receiptPlanList
						}),
						beforeSend: function () {
							progressLoad();
						},
						success: function (data) {
							info(data.msg);
							if(data.success){
								plannerList.datagrid('load', {});
								$('#plannerList').datagrid('clearSelections');
							}
						},
						complete: function (XMLHttpRequest, textStatus) {
							progressClose();
						}
					});
				}
			});
        }
    }

</script>
