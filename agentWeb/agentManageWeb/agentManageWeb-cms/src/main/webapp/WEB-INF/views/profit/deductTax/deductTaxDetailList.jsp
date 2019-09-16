<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">


	//设置默认日期：分润月
    var time = new Date();
    var year_month;
    if(time.getMonth()==0){
        year_month = (time.getFullYear()-1)+'-12'+'-01';
    }else{
        year_month = time.getFullYear()+'-'+(time.getMonth()>=10?time.getMonth():0+''+(time.getMonth()))+'-01';
    }

    $('#DATESTART').datebox({
        required:true
    });
    $('#DATEEND').datebox({
        required:true
    });

    $("#DATESTART,#DATEEND").datebox({
		required:true,
        formatter:function(data){
            var date_temp=new Date(data);
            return date_temp.getFullYear()+''+(date_temp.getMonth()+1>=10?date_temp.getMonth()+1:('0'+''+(date_temp.getMonth()+1)));
        },
        parser:function(data) {
            if(data.indexOf('-')<0){
                data=data.substring(0,4)+'-'+data.substring(4,data.length);
            }
            var t = Date.parse(data);
            if (!isNaN(t)) {
                return new Date(t);
            } else {
                return new Date();
            }
        }
    });

    $('#DATESTART').datebox('setValue',year_month);
    $('#DATEEND').datebox('setValue',year_month);

    var start = $('#DATESTART').datebox('getValue');
    var end = $('#DATEEND').datebox('getValue');

    var posDeductTaxList;
    var temp=true;
    $(function() {
		posDeductTaxList = $('#deductTaxDetailList').datagrid({
            url : '${path }/deductTaxDetail/query',
            striped : true,
            pagination : true,
            queryParams:{'DATESTART':start ,'DATEEND':end},
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
				title : '编号',
				field : 'id',
                align : 'center',
                width : 30,
                formatter: function(value,row,index){
                    if(row.id=='合计'){
                        return '合计';
                    }
					var pageNum=posDeductTaxList.datagrid('options').pageNumber;
					var pageSize=posDeductTaxList.datagrid('options').pageSize;
                    return (pageNum-1)*pageSize+index+1;
                }
            },{
                title : '分润月份',
                field : 'profitMonth',
                align : 'center',
                width : 100
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
            },
			<shiro:hasPermission name="parentAgent">
				{
					title : '上级代理商唯一码',
					field : 'parentAgentId',
					align : 'center',
					width : 150
				},{
					title : '上级代理商名称',
					field : 'parentAgentName',
					align : 'center',
					width : 200
				},
			</shiro:hasPermission>
            {
                title : '上月留抵基数',
                field : 'preLdAmt',
                align : 'center',
                width : 100
            },{
				title : '本月已打款日分润',
				field : 'dayProfitAmt',
				align : 'center',
				width : 100
			},{
                title : '本月已打款日返现',
                field : 'dayBackAmt',
                align : 'center',
                width : 100
            },{
                title : '本月涉税前月分润',
                field : 'basicProfitAmt',
                align : 'center',
            	width : 100,
            },{
				title : '保理业务款',
				field : 'blAmt',
				align : 'center',
				width : 100,
			},{
				title : '机具实收货款',
				field : 'merchanOrderAmt',
				align : 'center',
				width : 100,
			},{
				title : '代代理商垫付款项',
				field : 'agentDfAmt',
				align : 'center',
				width : 100,
			},{
				title : '调整金额',
				field : 'adjustAmt',
				align : 'center',
				width : 100,
			},{
				title : '本月扣税基数',
				field : 'taxBase',
				align : 'center',
				width : 100,
			},{
				title : '当期税点',
				field : 'taxRate',
				align : 'center',
				width : 50,
			},{
				title : '本月新增税额',
				field : 'addTaxAmt',
				align : 'center',
				width : 100,
			},{
				title : '上月未扣足税额',
				field : 'preNotDeductionAmt1',
				align : 'center',
				width : 100,
			},{
				title : '本月应扣税额',
				field : 'supposedTaxAmt',
				align : 'center',
				width : 100,
			},{
				title : '本月实扣税额',
				field : 'realTaxAmt',
				align : 'center',
				width : 100,
			},{
				title : '本月未扣足税额',
				field : 'notDeductionTaxAmt',
				align : 'center',
				width : 100,
			},{
				title : '调整原因',
				field : 'adjustReson',
				align : 'center',
				width : 200,
			}
            <shiro:hasPermission name="parentAgent">
                ,{
                    title : '操作',
                    field : 'operation',
                    align : 'center',
                    width : 160,
                    formatter:function(value,row,index){
                        if(row.id!='合计'){
                        	var str='';
							str+='<a href="javascript:void;" onclick="adjust(\''+row.id+'\')">调整</a>';
							str+='&nbsp;&nbsp;&nbsp;||&nbsp;&nbsp;&nbsp;';
							str+='<a href="javascript:void;" onclick="examineAdjustDetail(\''+row.parentAgentId+'\',\''+row.profitMonth+'\',\''+row.agentId+'\',\''+row.adjustAmt+'\')">调整详情</a>';
                            return str;
                        }
                    }
			    }
            </shiro:hasPermission>
			]],
			onLoadSuccess:function(){
            	var data=posDeductTaxList.datagrid('getRows');
            	if (data.length==0){
            		return;
				}
				var count_preLdAmt =0;
				var count_dayProfitAmt =0;
				var count_dayBackAmt =0;
				var count_basicProfitAmt =0;
				var count_blAmt =0;
				var count_merchanOrderAmt =0;
				var count_agentDfAmt =0;
				var count_adjustAmt =0;
				var count_taxBase =0;
				var count_addTaxAmt =0;
				var count_preNotDeductionAmt1 =0;
				var count_supposedTaxAmt =0;
				var count_realTaxAmt =0;
				var count_notDeductionTaxAmt =0;
				for (var i = 0; i <data.length; i++) {
					count_preLdAmt += data[i]['preLdAmt']==''||data[i]['preLdAmt']==null?parseFloat('0'):parseFloat(data[i]['preLdAmt']);
					count_dayProfitAmt += data[i]['dayProfitAmt']==''||data[i]['dayProfitAmt']==null?parseFloat('0'):parseFloat(data[i]['dayProfitAmt']);
					count_dayBackAmt += data[i]['dayBackAmt']==''||data[i]['dayBackAmt']==null?parseFloat('0'):parseFloat(data[i]['dayBackAmt']);
					count_basicProfitAmt += data[i]['basicProfitAmt']==''||data[i]['basicProfitAmt']==null?parseFloat('0'):parseFloat(data[i]['basicProfitAmt']);
					count_blAmt += data[i]['blAmt']==''||data[i]['blAmt']==null?parseFloat('0'):parseFloat(data[i]['blAmt']);
					count_merchanOrderAmt += data[i]['merchanOrderAmt']==''||data[i]['merchanOrderAmt']==null?parseFloat('0'):parseFloat(data[i]['merchanOrderAmt']);
					count_agentDfAmt += data[i]['agentDfAmt']==''||data[i]['agentDfAmt']==null?parseFloat('0'):parseFloat(data[i]['agentDfAmt']);
					count_adjustAmt += data[i]['adjustAmt']==''||data[i]['adjustAmt']==null?parseFloat('0'):parseFloat(data[i]['adjustAmt']);
					count_taxBase += data[i]['taxBase']==''||data[i]['taxBase']==null?parseFloat('0'):parseFloat(data[i]['taxBase']);
					count_addTaxAmt += data[i]['addTaxAmt']==''||data[i]['addTaxAmt']==null?parseFloat('0'):parseFloat(data[i]['addTaxAmt']);
					count_preNotDeductionAmt1 += data[i]['preNotDeductionAmt1']==''||data[i]['preNotDeductionAmt1']==null?parseFloat('0'):parseFloat(data[i]['preNotDeductionAmt1']);
					count_supposedTaxAmt += data[i]['supposedTaxAmt']==''||data[i]['supposedTaxAmt']==null?parseFloat('0'):parseFloat(data[i]['supposedTaxAmt']);
					count_realTaxAmt += data[i]['realTaxAmt']==''||data[i]['realTaxAmt']==null?parseFloat('0'):parseFloat(data[i]['realTaxAmt']);
					count_notDeductionTaxAmt += data[i]['notDeductionTaxAmt']==''||data[i]['notDeductionTaxAmt']==null?parseFloat('0'):parseFloat(data[i]['notDeductionTaxAmt']);
				}

				posDeductTaxList.datagrid('appendRow',{
					id:'合计',
					preLdAmt:count_preLdAmt.toFixed(2),
					dayProfitAmt:count_dayProfitAmt.toFixed(2),
					dayBackAmt:count_dayBackAmt.toFixed(2),
					basicProfitAmt:count_basicProfitAmt.toFixed(2),
					blAmt:count_blAmt.toFixed(2),
					merchanOrderAmt:count_merchanOrderAmt.toFixed(2),
					agentDfAmt:count_agentDfAmt.toFixed(2),
					adjustAmt:count_adjustAmt.toFixed(2),
					taxBase:count_taxBase.toFixed(2),
					addTaxAmt:count_addTaxAmt.toFixed(2),
					preNotDeductionAmt1:count_preNotDeductionAmt1.toFixed(2),
					supposedTaxAmt:count_supposedTaxAmt.toFixed(2),
					realTaxAmt:count_realTaxAmt.toFixed(2),
					notDeductionTaxAmt:count_notDeductionTaxAmt.toFixed(2)
				});
			}
        });
    });
    /*调整*/
	function adjust(id) {
		parent.$.modalDialog({
			title : '税前调整',
			width : 640,
			height : 300,
			href : '${path }/deductTaxDetail/adjust?id='+id,
			buttons : [{
				text : '调整',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid =posDeductTaxList;
					var f = parent.$.modalDialog.handler.find('#adjustForm');
					f.submit();
				}
			}]
		});
	}
	/*查看本月调整详情*/
	function examineAdjustDetail(parentAgentId,profitMonth,agentId,adjustAmt){
		if (adjustAmt=='0') {
			parent.$.messager.alert('提示', '此代理商本月暂无调整事项', 'info');
			return ;
		}
		parent.$.modalDialog({
			title : agentId+'的调整详情',
			width : 340,
			height : 200,
			resizable:"true",
			href : '${path }/deductTaxDetail/examineAdjustDetail?agentId='+agentId+'&profitMonth='+profitMonth+'&parentAgentId='+parentAgentId
		});
	}

	//查询
    function searchDeductTax() {
	   posDeductTaxList.datagrid('load', $.serializeObject($('#searchDeductTaxForm')));
	}

	//清空
	function cleanDeductTax() {
        $('#searchDeductTaxForm input:not(#directly)').val('');
        $('#DATESTART').datebox('setValue','');
        $('#DATEEND').datebox('setValue','');
		$("#td_sub").html('');
		posDeductTaxList.datagrid('load', {});
	}
	/**
	 * 统计事件
	 */
	function profitDeductTaxCount(){
		var data=$.serializeObject($('#searchDeductTaxForm'));
		var isQuerySubordinate='';
		if($('#isQuerySubordinate').length>0) {
			isQuerySubordinate=data.isQuerySubordinate;
		}
		var profitCountUrl=encodeURI('/deductTaxDetail/profitCount?agentId='+data.agentId+'&agentName='+data.agentName+'&DATESTART='+data.DATESTART+'&DATEEND='+data.DATEEND+'&directly='+data.directly+'&isQuerySubordinate='+isQuerySubordinate);
		parent.$.modalDialog({
			title : '统计',
			width : 200,
			height : 100,
			href : profitCountUrl
		});
		console.log(data);
		console.log(encodeURI);
	}

    function RefreshCloudHomePageTab() {
		posDeductTaxList.datagrid('reload');
    }
    function contract(){
        $('.ISNO').hide();
		$('#searchDeductTaxForm input').val('');
        $('#DATESTART').datebox('setValue','');
        $('#DATEEND').datebox('setValue','');
        $("#td_sub").html('');
		$("#directly").val('');
		$("#dd_dispense").html('<button onclick="sendOut()">直发</button>');
        $("#dd_sign").html('<button onclick="contract()" disabled>直签</button>');
		posDeductTaxList.datagrid({
			url : '${path }/deductTaxDetail/query',
			queryParams:{},
			columns : [
					[{
				title : '编号',
				field : 'id',
				align : 'center',
				width : 30,
                formatter: function(value,row,index){
				    if(row.id=='合计'){
                        return '合计';
                    }
					var pageNum=posDeductTaxList.datagrid('options').pageNumber;
					var pageSize=posDeductTaxList.datagrid('options').pageSize;
					return (pageNum-1)*pageSize+index+1;
                }
			},{
				title : '分润月份',
				field : 'profitMonth',
				align : 'center',
				width : 100
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
			},
			{
				title : '上级代理商唯一码',
				field : 'parentAgentId',
				align : 'center',
				width : 200
			},{
				title : '上级代理商名称',
				field : 'parentAgentName',
				align : 'center',
				width : 200
			},
			{
				title : '上月留抵基数',
				field : 'preLdAmt',
				align : 'center',
				width : 100
			},{
				title : '本月已打款日分润',
				field : 'dayProfitAmt',
				align : 'center',
				width : 100
			},{
				title : '本月已打款日返现',
				field : 'dayBackAmt',
				align : 'center',
				width : 100
			},{
				title : '本月涉税前月分润',
				field : 'basicProfitAmt',
				align : 'center',
				width : 100,
			},{
				title : '保理业务款',
				field : 'blAmt',
				align : 'center',
				width : 100,
			},{
				title : '机具实收货款',
				field : 'merchanOrderAmt',
				align : 'center',
				width : 100,
			},{
				title : '代代理商垫付款项',
				field : 'agentDfAmt',
				align : 'center',
				width : 100,
			},{
				title : '调整金额',
				field : 'adjustAmt',
				align : 'center',
				width : 100,
			},{
				title : '本月扣税基数',
				field : 'taxBase',
				align : 'center',
				width : 100,
			},{
				title : '当期税点',
				field : 'taxRate',
				align : 'center',
				width : 100,
			},{
				title : '本月新增税额',
				field : 'addTaxAmt',
				align : 'center',
				width : 100,
			},{
				title : '上月未扣足税额',
				field : 'preNotDeductionAmt1',
				align : 'center',
				width : 100,
			},{
				title : '本月应扣税额',
				field : 'supposedTaxAmt',
				align : 'center',
				width : 100,
			},{
				title : '本月实扣税额',
				field : 'realTaxAmt',
				align : 'center',
				width : 100,
			},{
				title : '本月未扣足税额',
				field : 'notDeductionTaxAmt',
				align : 'center',
				width : 100,
			},{
				title : '调整原因',
				field : 'adjustReson',
				align : 'center',
				width : 200,
			},{
				title : '操作',
				field : 'operation',
				align : 'center',
						width : 160,
						formatter:function(value,row,index){
							if(row.id!='合计'){
								var str='';
								str+='<a href="javascript:void;" onclick="adjust(\''+row.id+'\')">调整</a>';
								str+='&nbsp;&nbsp;&nbsp;||&nbsp;&nbsp;&nbsp;';
								str+='<a href="javascript:void;" onclick="examineAdjustDetail(\''+row.parentAgentId+'\',\''+row.profitMonth+'\',\''+row.agentId+'\')">调整详情</a>';
								return str;
							}
						}
			}]],
            onLoadSuccess:function(){
                var data=posDeductTaxList.datagrid('getRows');
                if (data.length==0){
                    return;
                }
                var count_preLdAmt =0;
                var count_dayProfitAmt =0;
                var count_dayBackAmt =0;
                var count_basicProfitAmt =0;
                var count_blAmt =0;
                var count_merchanOrderAmt =0;
                var count_agentDfAmt =0;
                var count_adjustAmt =0;
                var count_taxBase =0;
                var count_addTaxAmt =0;
                var count_preNotDeductionAmt1 =0;
                var count_supposedTaxAmt =0;
                var count_realTaxAmt =0;
                var count_notDeductionTaxAmt =0;
                for (var i = 0; i <data.length; i++) {
                    count_preLdAmt += data[i]['preLdAmt']==''||data[i]['preLdAmt']==null?parseFloat('0'):parseFloat(data[i]['preLdAmt']);
                    count_dayProfitAmt += data[i]['dayProfitAmt']==''||data[i]['dayProfitAmt']==null?parseFloat('0'):parseFloat(data[i]['dayProfitAmt']);
                    count_dayBackAmt += data[i]['dayBackAmt']==''||data[i]['dayBackAmt']==null?parseFloat('0'):parseFloat(data[i]['dayBackAmt']);
                    count_basicProfitAmt += data[i]['basicProfitAmt']==''||data[i]['basicProfitAmt']==null?parseFloat('0'):parseFloat(data[i]['basicProfitAmt']);
                    count_blAmt += data[i]['blAmt']==''||data[i]['blAmt']==null?parseFloat('0'):parseFloat(data[i]['blAmt']);
                    count_merchanOrderAmt += data[i]['merchanOrderAmt']==''||data[i]['merchanOrderAmt']==null?parseFloat('0'):parseFloat(data[i]['merchanOrderAmt']);
                    count_agentDfAmt += data[i]['agentDfAmt']==''||data[i]['agentDfAmt']==null?parseFloat('0'):parseFloat(data[i]['agentDfAmt']);
                    count_adjustAmt += data[i]['adjustAmt']==''||data[i]['adjustAmt']==null?parseFloat('0'):parseFloat(data[i]['adjustAmt']);
                    count_taxBase += data[i]['taxBase']==''||data[i]['taxBase']==null?parseFloat('0'):parseFloat(data[i]['taxBase']);
                    count_addTaxAmt += data[i]['addTaxAmt']==''||data[i]['addTaxAmt']==null?parseFloat('0'):parseFloat(data[i]['addTaxAmt']);
                    count_preNotDeductionAmt1 += data[i]['preNotDeductionAmt1']==''||data[i]['preNotDeductionAmt1']==null?parseFloat('0'):parseFloat(data[i]['preNotDeductionAmt1']);
                    count_supposedTaxAmt += data[i]['supposedTaxAmt']==''||data[i]['supposedTaxAmt']==null?parseFloat('0'):parseFloat(data[i]['supposedTaxAmt']);
                    count_realTaxAmt += data[i]['realTaxAmt']==''||data[i]['realTaxAmt']==null?parseFloat('0'):parseFloat(data[i]['realTaxAmt']);
                    count_notDeductionTaxAmt += data[i]['notDeductionTaxAmt']==''||data[i]['notDeductionTaxAmt']==null?parseFloat('0'):parseFloat(data[i]['notDeductionTaxAmt']);
                }
                posDeductTaxList.datagrid('appendRow',{
                    id:'合计',
                    preLdAmt:count_preLdAmt.toFixed(2),
                    dayProfitAmt:count_dayProfitAmt.toFixed(2),
                    dayBackAmt:count_dayBackAmt.toFixed(2),
                    basicProfitAmt:count_basicProfitAmt.toFixed(2),
                    blAmt:count_blAmt.toFixed(2),
                    merchanOrderAmt:count_merchanOrderAmt.toFixed(2),
                    agentDfAmt:count_agentDfAmt.toFixed(2),
                    adjustAmt:count_adjustAmt.toFixed(2),
                    taxBase:count_taxBase.toFixed(2),
                    addTaxAmt:count_addTaxAmt.toFixed(2),
                    preNotDeductionAmt1:count_preNotDeductionAmt1.toFixed(2),
                    supposedTaxAmt:count_supposedTaxAmt.toFixed(2),
                    realTaxAmt:count_realTaxAmt.toFixed(2),
                    notDeductionTaxAmt:count_notDeductionTaxAmt.toFixed(2)
                })
            }
		});
		temp=true;
	}

	function sendOut(){
        $('.ISNO').show();
		temp=false;
		$('#searchDeductTaxForm input').val('');
        $('#DATESTART').datebox('setValue','');
        $('#DATEEND').datebox('setValue','');
        $("#td_sub").html('');
        $("#directly").val('directly');
		$("#dd_dispense").html('<button onclick="sendOut()" disabled>直发</button>');
		$("#dd_sign").html('<button onclick="contract()">直签</button>');
		posDeductTaxList.datagrid({
			url : '${path }/deductTaxDetail/queryDirectly',
            queryParams:{'DATESTART':start ,'DATEEND':end},
			columns : [
					[{
				title : '编号',
				field : 'id',
				align : 'center',
				width : 30,
                formatter: function(value,row,index){
				    if(row.id=='合计'){
				        return '合计';
                    }
					var pageNum=posDeductTaxList.datagrid('options').pageNumber;
					var pageSize=posDeductTaxList.datagrid('options').pageSize;
					return (pageNum-1)*pageSize+index+1;
                }
			},{
				title : '分润月份',
				field : 'profitMonth',
				align : 'center',
				width : 100
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
			},
			{
				title : '一级代理商唯一码',
				field : 'fristAgentId',
				align : 'center',
				width : 150
			},{
				title : '一级代理商名称',
				field : 'fristAgentName',
				align : 'center',
				width : 150
			},{
                title : '涉税前月分润',
                field : 'basicProfitAmt',
                align : 'center',
                width : 150,
            },{
				title : '已打款日分润',
				field : 'dayProfitAmt',
				align : 'center',
				width : 150
			},{
				title : '本月扣税基数',
				field : 'taxBase',
				align : 'center',
				width : 150,
			},{
				title : '当期税点',
				field : 'taxRate',
				align : 'center',
				width : 150,
			},{
				title : '本月新增税额',
				field : 'addTaxAmt',
				align : 'center',
				width : 150,
			},{
				title : '上月未扣足税额',
				field : 'preNotDeductionAmt1',
				align : 'center',
				width : 150,
			},{
				title : '本月应扣税额',
				field : 'supposedTaxAmt',
				align : 'center',
				width : 150,
			},{
				title : '本月实扣税额',
				field : 'realTaxAmt',
				align : 'center',
				width : 150,
			},{
				title : '本月未扣足税额',
				field : 'notDeductionTaxAmt',
				align : 'center',
				width : 150,
			}]],
            onLoadSuccess:function(){
                var data=posDeductTaxList.datagrid('getRows');
                if (data.length==0){
                    return;
                }
                var count_dayProfitAmt =0;
                var count_basicProfitAmt =0;
                var count_taxBase =0;
                var count_addTaxAmt =0;
                var count_preNotDeductionAmt1 =0;
                var count_supposedTaxAmt =0;
                var count_realTaxAmt =0;
                var count_notDeductionTaxAmt =0;
                for (var i = 0; i <data.length; i++) {
                    count_dayProfitAmt += data[i]['dayProfitAmt']==''||data[i]['dayProfitAmt']==null?parseFloat('0'):parseFloat(data[i]['dayProfitAmt']);
                    count_basicProfitAmt += data[i]['basicProfitAmt']==''||data[i]['basicProfitAmt']==null?parseFloat('0'):parseFloat(data[i]['basicProfitAmt']);
                    count_taxBase += data[i]['taxBase']==''||data[i]['taxBase']==null?parseFloat('0'):parseFloat(data[i]['taxBase']);
                    count_addTaxAmt += data[i]['addTaxAmt']==''||data[i]['addTaxAmt']==null?parseFloat('0'):parseFloat(data[i]['addTaxAmt']);
                    count_preNotDeductionAmt1 += data[i]['preNotDeductionAmt1']==''||data[i]['preNotDeductionAmt1']==null?parseFloat('0'):parseFloat(data[i]['preNotDeductionAmt1']);
                    count_supposedTaxAmt += data[i]['supposedTaxAmt']==''||data[i]['supposedTaxAmt']==null?parseFloat('0'):parseFloat(data[i]['supposedTaxAmt']);
                    count_realTaxAmt += data[i]['realTaxAmt']==''||data[i]['realTaxAmt']==null?parseFloat('0'):parseFloat(data[i]['realTaxAmt']);
                    count_notDeductionTaxAmt += data[i]['notDeductionTaxAmt']==''||data[i]['notDeductionTaxAmt']==null?parseFloat('0'):parseFloat(data[i]['notDeductionTaxAmt']);
                }

                posDeductTaxList.datagrid('appendRow',{
                    id:'合计',
                    dayProfitAmt:count_dayProfitAmt.toFixed(2),
                    basicProfitAmt:count_basicProfitAmt.toFixed(2),
                    taxBase:count_taxBase.toFixed(2),
                    addTaxAmt:count_addTaxAmt.toFixed(2),
                    preNotDeductionAmt1:count_preNotDeductionAmt1.toFixed(2),
                    supposedTaxAmt:count_supposedTaxAmt.toFixed(2),
                    realTaxAmt:count_realTaxAmt.toFixed(2),
                    notDeductionTaxAmt:count_notDeductionTaxAmt.toFixed(2)
                })
            }
		});
	}
	<shiro:hasPermission name="parentAgent">
    function text_change(){
		if(!temp){
			return ;
		}
		var agentId=$("#deductTaxAgentId").val();
		var agentName=$("#deductTaxAgentName").val();
		if(agentId!=''||agentName!=''){
			$("#td_sub").html('<input id="isQuerySubordinate" name="isQuerySubordinate" value="isQuerySubordinate" type="checkbox"><label id="label_isQS" for="isQuerySubordinate">是否显示下级</label>');
		}else {
			$("#td_sub").html('');
		}
	}

	var curTime=new Date();
	var lastMonth=curTime.getMonth()==0?12:curTime.getMonth();
	var lastMonth_Year=curTime.getMonth()>0?curTime.getFullYear():curTime.getFullYear()-1;
	$('#DATESTART').datebox({
		required:true
	});
    $('#DATEEND').datebox({
        required:true
    });
	$("#DATESTART").datebox('setValue',lastMonth_Year+'-'+lastMonth+'-01');
    $("#DATEEND").datebox('setValue',lastMonth_Year+'-'+lastMonth+'-01');
	</shiro:hasPermission>

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="TABLE" data-options="region:'west',border:true"  style="width:100%;overflow: hidden; ">
		<table id="deductTaxDetailList" data-options="fit:true,border:false"></table>
	</div>
    <div id="formList" data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
	   <form  method="post" id ="searchDeductTaxForm" >
		   <table>
			   <tr>
				   <th>代理商名称:</th>
				   <td><input id="deductTaxAgentName" name="agentName" onchange="text_change()" style="line-height:17px;border:1px solid #ccc"></td>
				   <th>代理商唯一码:</th>
				   <td><input id="deductTaxAgentId" name="agentId" onchange="text_change()" style="line-height:17px;border:1px solid #ccc"></td>
				   <th>扣税月份</th>
				   <td><input id="DATESTART" name="DATESTART" /></td>
				   <th>-</th>
				   <td><input id="DATEEND" name="DATEEND"/></td>
				   <td id="td_sub"></td>
				   <shiro:hasPermission name="forAgent">
					   <td><input id="isQuerySubordinate" name="isQuerySubordinate" value="isQuerySubordinate" type="checkbox"><label id="label_isQS" for="isQuerySubordinate">是否显示下级</label></td>
				   </shiro:hasPermission>
				   <td>
					   <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchDeductTax();">查询</a>
					   <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanDeductTax();">清空</a>
					   <shiro:hasPermission name="parentAgent">
					   		<a class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="profitDeductTaxCount()">统计</a>
					   </shiro:hasPermission>
				   </td>
				   <td>
					   <input type="hidden" name="directly" id="directly" value=""/>
				   </td>
			   </tr>
			   <tr>

				   <shiro:hasPermission name="parentAgent">
					   <td>
						   <i id="dd_sign"><button onclick="contract()" disabled>直签</button></i>
					   </td>
					   <td>
						   <i id="dd_dispense"><button onclick="sendOut()">直发</button></i>
					   </td>
				   </shiro:hasPermission>
				   <th class ="ISNO" style="display: none">一级代理商唯一码:</th>
				   <td class ="ISNO" style="display: none"><input id="fristAgentId" name="fristAgentId"  style="line-height:17px;border:1px solid #ccc"></td>
				   <th class ="ISNO" style="display: none">一级代理商名称:</th>
				   <td class ="ISNO" style="display: none"><input id="fristAgentName" name="fristAgentName"  style="line-height:17px;border:1px solid #ccc"></td>
			   </tr>
		   </table>
		</form>

	</div>
</div>