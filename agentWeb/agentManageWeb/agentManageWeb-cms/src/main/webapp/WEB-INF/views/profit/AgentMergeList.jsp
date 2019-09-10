<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
	var index_tabs;
    function showAgentInfoSelectDialog(options) {
        parent.$.modalDialog({
            title : '代理商选择',
            width : 800,
            height : 500,
            href : '/abusinfo/agentInfoSelectDialogView'
        });
        parent.$.modalDialog.handler.par_callBack_options=options;
    }
    function showAgentInfoSelectDialogT(options) {
        parent.$.modalDialog({
            title : '代理商选择',
            width : 800,
            height : 500,
            href : '/abusinfo/agentInfoSelectDialogViewT'
        });
        parent.$.modalDialog.handler.par_callBack_options=options;
    }
    function saveposTaxEnterIn() {
        var agUniqNum = $("#agUniqNum").textbox('getValue');//主代理商唯一码
        var agUniqNumT = $("#agUniqNumT").textbox('getValue');//附代理唯一码  agNameT
		var agName = $("#agName").textbox('getValue');//代理商名称
        var agNameT = $("#agNameT").textbox('getValue');//附代理商名称
        var mainHeadT = $("#mainHeadT").textbox('getValue');//代理商负责人
        var mainHeadMobileT = $("#mainHeadMobileT").textbox('getValue');//代理商负责人联系电话
        var subHeadT = $("#subHeadT").textbox('getValue');//附代理商负责人
        var subHeadMobileT = $("#subHeadMobileT").textbox('getValue');//附代理商负责人联系电话
            parent.$.messager.confirm('询问', '确认提交申请？', function(b) {
                if (b) {
                    $.ajaxL({
                        type: "POST",
                        url: "/profitAgentMerge/agentMergeTaxEnterIn",
                        dataType:'json',
                        data: {
                            agUniqNum:agUniqNum,
                            agUniqNumT:agUniqNumT,
                            agName:agName,
                            agNameT:agNameT,
                            mainHeadT:mainHeadT,
                            mainHeadMobileT:mainHeadMobileT,
                            subHeadT:subHeadT,
                            subHeadMobileT:subHeadMobileT
                        },
                        beforeSend : function() {
                            progressLoad();
                        },
                        success: function(data){;
                            info(data.msg);
							$('#index_tabs').tabs('close',"代理商合并申请");
							profitMergeList.datagrid('reload');
                        },
                        complete:function (XMLHttpRequest, textStatus) {
                            progressClose();
                        }
                    });
                }
            });
    }
</script>
<div>
	<div  class="easyui-panel" title="主代理商信息" style="background:#fafafa;">
		<%--//填写订单布局--%>
		<div data-options="region:'north',title:'填写订单',split:true,iconCls:'icon-ok'" style="">
			<table style="min-height: 50px;" >
				<tr>
					<td >代理商名称:</td>
					<td width="300px">
						<input id="agName" name="agName" type="text" class="easyui-textbox" data-options="prompt:'请选择代理商'"  readonly="readonly"   value="${data.agent.agName}">
						<input type="hidden" name="agentId" value="${data.agent.id}" id="agentId" >
						<input type="hidden" name="orderId" value="${data.order.id}" id="orderId" >
						<c:if test="${!isagent.isOK()}">
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="showAgentInfoSelectDialog({data:this,callBack:agentSelectOrderBuild})">检索代理商</a>
							<script type="application/javascript">
                                function agentSelectOrderBuild(item,data){
                                    if(item){
                                        $($(data).parent('td').find('#agName')).textbox('setValue',item.agName);
                                        $("#agUniqNum").textbox('setValue',item.id);
                                        $("#mainHeadT").textbox('setValue',item.agHead);//负责人
                                       $("#mainHeadMobileT").textbox('setValue',item.agHeadMobile);//负责人联系电话
                                        $($(data).parent('td').find('input[name=\'agentId\']')).val(item.id);
                                        $.ajaxL({
                                            type:'POST',
                                            url: basePath+'/orderbuild/orderAgentPlatformBus',//
                                            dataType:'json',
                                            contentType:'application/json;charset=UTF-8',
                                            data:{agentId:item.id},
                                            beforeSend : function() {
                                                progressLoad();
                                            },
                                            success:function(data){
                                                //删除商品
                                                //platformChangeDelProduct_update();

                                                $('#update_orderPlatform').combobox("clear");

                                                //加载平台数据
                                                $('#update_orderPlatform').combobox({
                                                    url:'/orderbuild/orderAgentPlatformBus?agentId='+item.id,
                                                    valueField:'ID',
                                                    textField:'FIELDSHOW',
                                                    onBeforeLoad:function(){
                                                        progressLoad();
                                                    },
                                                    onLoadSuccess:function(){
                                                        progressClose();
                                                        $('#update_orderPlatform').combobox("setValue");
                                                    }
                                                });
                                            },
                                            complete:function(){
                                                progressClose();
                                            }
                                        });
                                    }
                                }
							</script>
						</c:if>
					</td>
					<td >业务类型:</td>
					<td>
						<select name="update_orderPlatform" style="width: 100%;" class="easyui-combobox" id="update_orderPlatform">
							<options>
								<c:forEach items="${listPlateform}" var="listPlateformItem">
									<option value="${listPlateformItem.ID}"
											<c:if test="${listPlateformItem.ID==data.order.busId}" >
												selected="selected"
											</c:if>
									>${listPlateformItem.FIELDSHOW}</option>
								</c:forEach>
							</options>
						</select>
					</td>
					<td >代理商唯一码:</td>
					<td><input id="agUniqNum" class="easyui-textbox"  name="agUniqNum" value=""></td>
					<td><input id="mainHeadT" class="easyui-textbox"  name="agHead" value="" type="hidden"></td>
					<td><input id="mainHeadMobileT" class="easyui-textbox"  name="agHeadMobile" value="" type="hidden"></td>
				</tr>
			</table>
		</div>
	</div>
	<div  class="easyui-panel" title="附代理商信息" style="background:#fafafa;"></div>
	<div data-options="region:'north',title:'填写订单',split:true,iconCls:'icon-ok'" style="">
		<table style="min-height: 50px;">
			<tr>
				<td >代理商名称:</td>
				<td width="300px">
					<input id="agNameT" name="agName" type="text" class="easyui-textbox" data-options="prompt:'请选择代理商'"  readonly="readonly"   value="${data.agent.agName}">
					<input type="hidden" name="agentId" value="${data.agent.id}" id="agentId" >
					<input type="hidden" name="orderId" value="${data.order.id}" id="orderId" >
					<c:if test="${!isagent.isOK()}">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="showAgentInfoSelectDialogT({data:this,callBack:agentSelectOrderBuildT})">检索代理商</a>
						<script type="application/javascript">
                            function agentSelectOrderBuildT(item,data){
                                if(item){
                                    $($(data).parent('td').find('#agNameT')).textbox('setValue',item.agName);
                                    $("#agUniqNumT").textbox('setValue',item.id);
                                    $("#subHeadT").textbox('setValue',item.agHead);//负责人
                                    $("#subHeadMobileT").textbox('setValue',item.agHeadMobile);//负责人联系电话
                                    $($(data).parent('td').find('input[name=\'agentId\']')).val(item.id);
                                    $.ajaxL({
                                        type:'POST',
                                        url: basePath+'/orderbuild/orderAgentPlatformBus',//
                                        dataType:'json',
                                        contentType:'application/json;charset=UTF-8',
                                        data:{agentId:item.id},
                                        beforeSend : function() {
                                            progressLoad();
                                        },
                                        success:function(data){
                                            //删除商品
                                            //platformChangeDelProduct_update();

                                            $('#update_orderPlatformT').combobox("clear");

                                            //加载平台数据
                                            $('#update_orderPlatformT').combobox({
                                                url:'/orderbuild/orderAgentPlatformBus?agentId='+item.id,
                                                valueField:'ID',
                                                textField:'FIELDSHOW',
                                                onBeforeLoad:function(){
                                                    progressLoad();
                                                },
                                                onLoadSuccess:function(){
                                                    progressClose();
                                                    $('#update_orderPlatformT').combobox("setValue");
                                                }
                                            });
                                        },
                                        complete:function(){
                                            progressClose();
                                        }
                                    });
                                    $.ajaxL({
                                        type:'POST',
                                        url: basePath+'/abusinfo/zjlx',
                                        dataType:'json',
                                        data:{agentId:item.agUniqNum},
                                        beforeSend : function() {
                                            progressLoad();
                                        },
                                        success:function(data){
                                            $("#zjlx").html("");
                                            var str = "";
                                            $(data).each(function (index,element) {
                                                str+="<td>"+element.cType+"</td><td>  "+element.cAmount+"/元</td>"
                                            })
                                            $("#zjlx").html(str);
                                        },
                                        complete:function(){
                                            progressClose();
                                        }
                                    });
                                    /*$.ajaxL({
                                        type:'POST',
                                        url: basePath+'/abusinfo/zjlxT',
                                        dataType:'json',
                                        data:{agentId:item.agUniqNum},
                                        beforeSend : function() {
                                            progressLoad();
                                        },
                                        success:function(data){
                                            alert(data);
                                            $("#zjlxT").html("");
                                            var strt = "";
                                            $(data).each(function (index,element) {
                                                str+="<td>"+element.cType+"</td><td>  "+element.cAmount+"/元</td>"
                                            })
                                            $("#zjlxT").html(strt);
                                        },
                                        complete:function(){
                                            progressClose();
                                        }
                                    });*/
                                }
                            }
						</script>
					</c:if>
				</td>
				<td >业务类型:</td>
				<td>
					<select name="update_orderPlatformT" style="width: 100%;" class="easyui-combobox" id="update_orderPlatformT">
						<options>
							<c:forEach items="${listPlateform}" var="listPlateformItem">
								<option value="${listPlateformItem.ID}"
										<c:if test="${listPlateformItem.ID==data.order.busId}" >
											selected="selected"
										</c:if>
								>${listPlateformItem.FIELDSHOW}</option>
							</c:forEach>
						</options>
					</select>
				</td>
				<td >代理商唯一码:</td>
				<td><input id="agUniqNumT" class="easyui-textbox"  name="agUniqNum" value=""></td>
				<td><input id="subHeadT" class="easyui-textbox"  name="agHead" value="" type="hidden"></td>
				<td><input id="subHeadMobileT" class="easyui-textbox"  name="agHeadMobile" value="" type="hidden"></td>
			</tr>
			<tr id="zjlx">
				<%--<td>瑞和宝服务费</td>--%>
				<%--<td>${capitalListItem.cAmount}/元</td>--%>
			</tr>
			<tr id="zjlxT">
				<%--<td>瑞和宝服务费</td>--%>
				<%--<td>${capitalListItem.cAmount}/元</td>--%>
			</tr>
<%--			<tr>
				<td>欠款</td>
				<td><input id="subHeadMobileT" class="easyui-textbox"  name="agHeadMobile" value="" type="hidden"></td>
			</tr>--%>
			<%--<tr>
				<td>瑞和宝服务费</td>
				<c:forEach items="${list}" var="capitalListItem">
					<td>${capitalListItem.cType}</td>
					<td >${capitalListItem.cAmount}/元</td>
				</c:forEach>
			</tr>--%>
		</table>
	</div>
	<div>
		<div style="text-align:right;padding:5px;margin-bottom: 50px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" style="width: 100px;" data-options="iconCls:'fi-save'"  onclick="saveposTaxEnterIn()">提交</a>
		</div>
	</div>
</div>




