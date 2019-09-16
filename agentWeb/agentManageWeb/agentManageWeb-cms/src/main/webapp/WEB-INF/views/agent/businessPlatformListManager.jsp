<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var businessPlat;
    $(function() {
        businessPlat = $('#businessPlat').datagrid({
            url : '${path }/business/queryBusinessPlatformManager',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 10,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
				title : 'ID',
				field : 'ID',
                hidden: 'true'
			},{
                title : '业务平台编码',
                field : 'BUS_NUM',
                formatter : function(value, row, index) {
                    if(row.BUS_STATUS=='0' || row.BUS_STATUS=='2'){
                        <shiro:hasPermission name="/agentEnter/busNum">
                        return row.BUS_NUM;
                        </shiro:hasPermission>
                    }else{
                        return row.BUS_NUM;
					}
                }
            },{
                title : '业务平台',
                field : 'BUS_PLATFORM',
                formatter : function(value, row, index) {
                    if(db_options.ablePlatForm)
                        for(var i=0;i< db_options.ablePlatForm.length;i++){
                            if(db_options.ablePlatForm[i].platformNum==row.BUS_PLATFORM){
                                return db_options.ablePlatForm[i].platformName;
                            }
                        }
                    return "";
                }
            },{
                title : '代理商业务类型',
                field : 'BUS_TYPE',
                formatter : function(value, row, index) {
                    return db_options.busType_map[value]
                }
            },{
                title : '代理商唯一编码',
                field : 'AG_UNIQ_NUM'
            },{
                title : '代理商名称',
                field : 'AG_NAME'
            },{
                title : '风险承担代理商',
                field : 'RISK_PARENT'
            },{
                title : '对接省区',
                field : 'AG_DOC_PRO'
            },{
                title : '是否独立考核',
                field : 'BUS_INDE_ASS',
                formatter : function(value, row, index) {
                    if(row.BUS_INDE_ASS=='1')
                       return '是';
                    else if(row.BUS_INDE_ASS=='0')
                        return '否';
                    else
                    return "";
                }
            },{
                title : '公司性质',
                field : 'AG_NATURE',
                formatter : function(value, row, index) {
                     return db_options.agNatureType_map[value]
                }
            },{
                title : '负责人',
                field : 'AG_HEAD',
                formatter: function (value, row, index) {
                    var phone;
                    if(null!=row.AG_HEAD &&''!=row.AG_HEAD){
                        if (row.AG_HEAD.length >= 2) {
                            phone = row.AG_HEAD.substr(0, 1) + '**';
                        }
                    }
                    return phone;
                }
            },{
                title : '负责人电话',
                field : 'AG_HEAD_MOBILE',
                formatter: function (value, row, index) {
                    var phone;
                    if(null!=row.AG_HEAD_MOBILE &&''!=row.AG_HEAD_MOBILE){
                        if (row.AG_HEAD_MOBILE.length > 7) {
                            phone = row.AG_HEAD_MOBILE.substr(0, 3) + '****' + row.AG_HEAD_MOBILE.substr(7);
                        } else if (row.AG_HEAD_MOBILE.length <= 7) {
                            var mobile = value.substring(row.AG_HEAD_MOBILE.length - 4);
                            var flag = "";
                            for (var i = 0; i < row.AG_HEAD_MOBILE.length - 4; i++) {
                                flag = "*" + flag;
                            }
                            phone = flag + mobile;
                        }
                    }
                    else if ( null==row.AG_HEAD_MOBILE ||''==row.AG_HEAD_MOBILE) {
                        phone="无";
                    }
                    return phone;
                }
            },{
                title : '平台状态',
                field : 'BUS_STATUS',
                formatter : function(value, row, index) {
                    return db_options.busStatus_map[value]
                }
            },{
                title : '审批状态',
                field : 'CLO_REVIEW_STATUS',
                formatter : function(value, row, index) {
                    return db_options.agStatusi_map[value]
                }
            },{
                title : '创建时间',
                field : 'C_TIME'
            },{
                title : '审批通过时间',
                field : 'APPROVE_TIME'
            },{
                field : 'action',
                title : '操作',
                width : 500,
                formatter : function(value, row, index) {
					var str = '';
					str += $.formatString('<a href="javascript:void(0)" class="busck-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="seeBusPlat(\'{0}\',\'{1}\');" >查看</a>', row.ID,row.CLO_REVIEW_STATUS);
					<shiro:hasPermission name="/business/approval">
						if(row.CLO_REVIEW_STATUS!='2' && row.CLO_REVIEW_STATUS!='3' && row.CLO_REVIEW_STATUS!='4')
						str += $.formatString('<a href="javascript:void(0)" class="busprove-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="platformSubProv(\'{0}\');" >提交审批</a>', row.ID);
                    </shiro:hasPermission>
					<shiro:hasPermission name="/business/edit">
						if(row.CLO_REVIEW_STATUS=='1')
						str += $.formatString('<a href="javascript:void(0)" class="busxg-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editBusPlat(\'{0}\');" >修改</a>', row.ID);
                    </shiro:hasPermission>
					<shiro:hasPermission name="/agent/capitalsetting">
                    if(row.CLO_REVIEW_STATUS=='3')
                        str += $.formatString('<a href="javascript:void(0)" class="bussz-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="proceedsSet(\'{0}\',\'{1}\');">收款账户设置</a>', row.ID, row.AGENT_ID);
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/agent/editDkgs">
                        str += $.formatString('<a href="javascript:void(0)" class="busxg-easyui-linkbutton-editDkgs" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editBusPlatDkgs(\'{0}\');" >修改打款公司</a>', row.ID);
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/business/editBusinfo">
                    str += $.formatString('<a href="javascript:void(0)" class="busxg-easyui-linkbutton-editBusinfo" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editBusinfo(\'{0}\');" >业务修改</a>', row.ID);
                    </shiro:hasPermission>
					<shiro:hasPermission name="/business/noticeBusinfo">
					str += $.formatString('<a href="javascript:void(0)" class="busxg-easyui-linkbutton-noticeBusinfo" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="noticeBusinfo(\'{0}\');" >同步业务平台</a>', row.ID);
					</shiro:hasPermission>
                    return str;
                }
            }
            ]],
            onLoadSuccess:function(data){
                $('.busck-easyui-linkbutton-edit').linkbutton({text:'查看'});
                $('.busprove-easyui-linkbutton-edit').linkbutton({text:'提交审批'});
                $('.busxg-easyui-linkbutton-edit').linkbutton({text:'修改'});
                $('.bussz-easyui-linkbutton-edit').linkbutton({text:'收款账户设置'});
                $('.busxg-easyui-linkbutton-editDkgs').linkbutton({text:'修改打款公司'});
                $('.busxg-easyui-linkbutton-editBusinfo').linkbutton({text:'业务修改'});
                $('.busxg-easyui-linkbutton-noticeBusinfo').linkbutton({text:'同步业务平台'});
            },
            toolbar : '#businessToolbarManager'
        });

    });

   function searchBusPlat() {
       businessPlat.datagrid('load', $.serializeObject($('#searchBusPlatForm')));
	}
	function cleanBusPlat() {
		$('#searchBusPlatForm input').val('');
		$("[name='busPlatform']").val('');
		$("[name='cloReviewStatus']").val('');
		$("[name='busType']").val('');
        businessPlat.datagrid('load',  $.serializeObject($('#searchBusPlatForm')));
	}

    function addBusPlat(){
        addTab({
            title : '新增业务',
            border : false,
            closable : true,
            fit : true,
            href:'/business/toAddBusPlatPage'
        });
    }

    function seeBusPlat(id,cloReviewStatus){
        addTab({
            title : '查看业务',
            border : false,
            closable : true,
            fit : true,
            href:'/business/toSeeBusPlatPage?id='+id+"&cloReviewStatus="+cloReviewStatus
        });
    }

    function editBusPlat(id){
        addTab({
            title : '修改业务',
            border : false,
            closable : true,
            fit : true,
            href:'/business/toEditBusPlatPage?id='+id
        });
    }

    function proceedsSet(id,agentId) {
        addTab({
            title : '收款账户设置',
            border : false,
            closable : true,
            fit : true,
            href:'/business/proceedsSet?agentId='+agentId+"&id="+id
        });
    }

    function editBusinfo(id) {
        addTab({
            title: '业务修改',
            border: false,
            closable: true,
            fit: true,
            href: '/business/toEditBusinfoPage?id='+id
        });
    }

	function noticeBusinfo(id) {//通知业务平台
		parent.$.messager.confirm('询问', '确认同步业务平台？', function(b) {
			if (b) {
				$.ajaxL({
					type: "POST",
					url: "/business/noticeBusinfo",
					dataType:'json',
					data: {busId:id},
					beforeSend:function(){
					},
					success: function(msg){
						info(msg.obj);
					},
					complete:function (XMLHttpRequest, textStatus) {
					}
				});
			}
		});
	}

    //提交审批
    function platformSubProv(id){
        parent.$.messager.confirm('询问', '确认提交审批？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/agActivity/startBus",
                    dataType:'json',
                    data: {busId:id},
                    beforeSend:function(){
                    },
                    success: function(msg){
                        info(msg.resInfo);
                    },
                    complete:function (XMLHttpRequest, textStatus) {
                        RefreshCloudHomePageTab();
                    }
                });
            }
        });
	}

    function RefreshCloudHomePageTab() {
        businessPlat.datagrid('reload');
    }

    function editBusPlatDkgs(id) {
        parent.$.modalDialog({
            title : '修改打款公司',
            width : 350,
            height : 220,
            maximizable:true,
            href : '${path }/business/toEditBusPlatDkgsPage?id='+id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = businessPlat;
                    var gr = parent.$.modalDialog.handler.find('#businessPlatFromDkgs');
                    gr.submit();
                }
            } ]
        });
    }


    $("#importBusFun").click(function(){
        parent.$.modalDialog({
            title : '导入业务平台信息',
            width : 300,
            height : 110,
            href : "/business/importView",
            buttons : [ {
                text : '确定',
                handler : function() {
                    var fun = parent.$.modalDialog.handler.find('#busImportFileForm');
                    fun.submit();
                }
            } ]
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="" data-options="region:'west',border:true"  style="width:100%;overflow: hidden; ">
		<table id="businessPlat" data-options="fit:true,border:false"></table>
	</div>
    <div data-options="region:'north',border:false" style="height: 100px; overflow: hidden;background-color: #fff">
	   <form id ="searchBusPlatForm"  method="post" action="${path}/business/exportAgent">
			<table>
				<tr>
					<th>编码:</th>
					<td>
						<input name="id" style="line-height:17px;border:1px solid #ccc">
						<%--<shiro:hasPermission name="/agent/selfBustRecord">--%>
							<%--<input name="flag" value="1" type="hidden">--%>
						<%--</shiro:hasPermission>--%>
						<%--<shiro:hasPermission name="/agent/busIsZpos">--%>
							<%--<input name="isZpos" value="true" type="hidden">--%>
						<%--</shiro:hasPermission>--%>
						<%--<shiro:hasPermission name="/agent/busIsZposAll">--%>
							<%--<input name="isZpos" value="all" type="hidden">--%>
						<%--</shiro:hasPermission>--%>
					</td>
					<th>业务平台编码:</th>
					<td><input name="busNum" style="line-height:17px;border:1px solid #ccc"></td>
					<th>代理商唯一编码:</th>
					<td><input name="agUniqNum" style="line-height:17px;border:1px solid #ccc"></td>
					<th>代理商名称:</th>
					<td><input name="agName" style="line-height:17px;border:1px solid #ccc"></td>
				</tr>
				<tr>
					<th>业务类型:</th>
					<td>
						<select name="busType" style="width:140px;height:21px" >
							<option value="">--请选择--</option>
							<c:forEach items="${busTypes}" var="busTypesItem"  >
								<option value="${busTypesItem.dItemvalue}">${busTypesItem.dItemname}</option>
							</c:forEach>
						</select>
					</td>
					<th>平台类型:</th>
					<td>
						<select name="busPlatform" style="width:140px;height:21px" >
							<option value="">--请选择--</option>
							<c:forEach items="${ablePlatForm}" var="ablePlatFormItem"  >
								<option value="${ablePlatFormItem.platformNum}">${ablePlatFormItem.platformName}</option>
							</c:forEach>
						</select>
					</td>
					<th>审批状态:</th>
					<td>
						<select name="cloReviewStatus" style="width:140px;height:21px" >
							<option value="">--请选择--</option>
							<c:forEach items="${agStatusList}" var="aagStatusItem"  >
								<option value="${aagStatusItem.dItemvalue}">${aagStatusItem.dItemname}</option>
							</c:forEach>
						</select>
					</td>
					<th >审批通过时间:</th>
					<td >
						<input  class="easyui-datebox"  name="approveTimeStart" type="text" placeholder="审批通过起始时间" >
						--
						<input  class="easyui-datebox"  name="approveTimeEnd" type="text" placeholder="审批通过结束时间">
					</td>
				</tr>
				<tr>
					<td colspan="8">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchBusPlat();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanBusPlat();">清空</a>
						<shiro:hasPermission name="/business/selfbussRecord">
							<button type="submit" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true,">导出
							</button>
						</shiro:hasPermission>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<div id="businessToolbarManager" >
	<shiro:hasPermission name="/business/add">
		<a onclick="addBusPlat()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加业务</a>
	</shiro:hasPermission>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<shiro:hasPermission name="/business/importcol">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-archive icon-green'" id="importBusFun">导入收款账户</a>
		&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="/static/template/busImport.xlsx" style="margin:5px 5px 5px 5px" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'" >下载模板</a>
	</shiro:hasPermission>
</div>

