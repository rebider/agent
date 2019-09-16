<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">


    var time = new Date();
    var year_month;
    if(time.getMonth()==0){
        year_month = (time.getFullYear()-1)+'-12'+'-01';
    }else{
        year_month = time.getFullYear()+'-'+(time.getMonth()>=10?time.getMonth():0+''+(time.getMonth()))+'-01';
    }

    $('#PAY_DATE_BEGIN').datebox({
        required:true
    });
    $('#PAY_DATE_END').datebox({
        required:true
    });

    $('#PAY_DATE_BEGIN,#PAY_DATE_END').datebox({
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

    $('#PAY_DATE_BEGIN').datebox('setValue',year_month);
    $('#PAY_DATE_END').datebox('setValue',year_month);

    var start = $('#PAY_DATE_BEGIN').datebox('getValue');
    var end = $('#PAY_DATE_END').datebox('getValue');

	$(function () {
	    $("#paragraphList").datagrid({
                url : '${path }/pBalanceSerialLs/pBalanceSerialLsList',
                striped : true,
                rownumbers : true,
                pagination : true,
                singleSelect : true,
            	queryParams:{'PAY_DATE_BEGIN':start ,'PAY_DATE_END':end},
                fit : true,
                idField : 'ID',
                pageSize : 20,
                pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
                columns : [ [ {
                    title: 'id',
                    field: 'PROFIT_ID',
                    hidden: true
                } , {
                    title : '分润月份',
                    field : 'PAY_DATE',
                    align : 'center',
                    width : 120
                }, {
                    title : '代理商名称',
                    field : 'AGENT_NAME',
                    align : 'center',
                    width : 120
                } , {
                    title : '代理商唯一码',
                    field : 'AGENT_ID',
                    align : 'center',
                    width : 120
                },{
                    title : '上级代理商名称',
                    field : 'PARENT_AGENT_NAME',
                    align : 'center',
                    width : 120
                } ,{
                    title : '上级代理商唯一码',
                    field : 'PARENT_AGENT_ID',
                    align : 'center',
                    width : 120
                },{
                    title : '一级代理商名称',
                    field : 'FIRFT_AGENT_NAME',
                    align : 'center',
                    width : 120
                } ,{
                    title : '一级代理商唯一码',
                    field : 'FIRFT_AGENT_ID',
                    align : 'center',
                    width : 120
                },{
                    title : '出款日期',
                    field : 'PAY_DATE',
                    align : 'center',
                    width : 120
                } ,{
                    title : '出款金额',
                    field : 'PROFIT_AMT',
                    align : 'center',
                    width : 120
                } ,{
                    title : '收款人',
                    field : 'ACCOUNT_NAME',
                    align : 'center',
                    width : 120
                },{
                    title : '交易流水号',
                    field : 'BALANCE_ID',
                    align : 'center',
                    width : 120
                },{
                    title : '卡号',
                    field : 'CARD_NO',
                    align : 'center',
                    width : 120
                } ,{
                    title : '状态',
                    field : 'STATUS',
                    align : 'center',
                    width : 120
                },{
                    title : '出款账户',
                    field : 'PAY_COMPANY',
                    align : 'center',
                    width : 120
                } ,{
                    title : '备注',
                    field : 'REMARK',
                    align : 'center',
                    width : 120
                }] ],
                onLoadSuccess: function (data) {
                    $('.easyui-linkbutton-query').linkbutton();
                },
                toolbar : '#ProfitSupplyToolbar'
            });





    });

   function searchProfitSupply(){
       $('#paragraphList').datagrid('load',$.serializeObject($('#form_data')))

   }

   function  reset(){
       $('#AGENT_NAME').val('');
       $('#AGENT_ID').val('');
       $('#PAY_DATE_BEGIN').datebox('setValue','')
       $('#PAY_DATE_END').datebox('setValue','')
       $('#is_subordinate').combobox('setValue','')
       $('#paragraph_status').combobox('setValue','')
       $('#paragraph_name').combobox('setValue','')
   }


</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div  data-options="region:'west',border:true" style="width:100%;overflow: hidden; ">
		<table id="paragraphList"  data-options="fit:true,border:false"></table>
	</div>

<div data-options="region:'north',border:false">
	<div data-options="region:'north',border:false" style="height: 56px; overflow: hidden;background-color: #fff">
       <form id="form_data"  method="post">
		   <table>
			   <tr>
				   <th>代理商名称</th>
				       <td><input id = "AGENT_NAME" name="AGENT_NAME"  style="line-height:17px;border:1px solid #ccc;width:160px;">&nbsp;</td>
			       <th>代理商唯一码</th>
				       <td><input id = "AGENT_ID" name="AGENT_ID"  style="line-height:17px;border:1px solid #ccc;width:160px;">&nbsp;</td>
			       <th>出款日期</th>
				       <td><input id="PAY_DATE_BEGIN" name="PAY_DATE_BEGIN"  style="line-height:17px;border:1px solid #ccc;width:160px;" >-
				   		<input id="PAY_DATE_END" name="PAY_DATE_END"   style="line-height:17px;border:1px solid #ccc;width:160px;"></td>
			   </tr>
			   <tr>
				   <th>是否包含下级</th>
				       <td><select id="is_subordinate" class="easyui-combobox" name="is_subordinate" style="width:200px;">
		                   <option value=""> ----请选择-----</option>
                           <option value="1">是</option>
                           <option value="2">否</option>
                           </select>
				       </td>
				   <th>出款状态</th>
				       <td><select id="STATUS" class="easyui-combobox" name="STATUS" style="width:200px;">
		                          <option value=""> ----请选择-----</option>
		                          <option value="1">出款成功</option>
                                  <option value="2">出款失败</option>
                                  <option value="3">出款进行中</option>
						          <option value="4">未出款</option>
                                  <option value="5">冻结款项</option>
                           </select>
				</td>
                               <th>出款账户</th>
				<td><select id="ACCOUNT_NAME" class="easyui-combobox" name="ACCOUNT_NAME" style="width:200px;">
					<option value=""> ----请选择-----</option>
					<option value="1">瑞韬</option>
					<option value="2">瑞银信</option>
					<option value="3">瑞灏</option>
					<option value="4">瑞致</option>
					<option value="5">瑞澜</option>
					<option value="6">瑞韵</option>
					<option value="7">卓朗</option>
					<option value="8">瑞熙</option>
				    </select>
				</td>
				<td>
		   <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchProfitSupply()">查询</a>

			<a href='javascript:void(0)' class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true" onclick="excelexport()">导出</a>

		   <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="reset()">清空</a>
				</td>
			</tr>
		   </table>
	   </form>
		</div>
	</div>
</div>
