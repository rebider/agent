<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>

<script type="text/javascript">

    var templatApplyList;

    $(function() {
        templatApplyList = $('#applyList').datagrid({
            url : '${path }/profit/template/getTemplateList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit: true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [
                {
                    title: 'id',
                    field: 'id',
                    hidden: true
                },{
                    title: '代理商名称',
                    field: 'agentName',
                    width: 170
                },{
                    title: '代理商唯一编码',
                    field: 'agentId',
                    width: 170
                },{
                    title: '业务平台编码',
                    field: 'busNum',
                    width: 120
                },{
                    title: '业务平台',
                    field: 'busPlatform',
                    width: 120,
                    formatter:function (value) {
                        for(var i=0;i< db_options.ablePlatForm.length;i++){
                            if (db_options.ablePlatForm[i].platformNum == value) {
                                var temp = db_options.ablePlatForm[i].platformName;
                                return temp;
                            }
                        }
                    }
                },{
                    title: '模板名称',
                    field: 'templateName',
                    width: 140
                },{
                    title: '申请时间',
                    field: 'createDate',
                    width: 140
                },{
                    title: '审批结果',
                    field: 'applyResult',
                    width: 140,
                    formatter:function (value) {
                        switch (value) {
                            case '0':
                                return '暂存';
                            case '1':
                                return '审批中';
                            case '2':
                                return '退回';
                            case '3':
                                return '撤销';
                            case '4':
                                return '通过';
                        }
                    }
                },{
                    title: '分配结果',
                    field: 'assignResult',
                    width: 140,
                    formatter:function (value) {
                        switch (value) {
                            case '0':
                                return '成功';
                            case '1':
                                return '失败';
                        }
                    }
                },{
                    title: '原因',
                    field: 'assignReason',
                    width: 140
                },{
                    title: '操作',
                    field: 'adjustOPR',
                    width: 170,
                    formatter: function (value, row) {
                        var str1 = '';
                            str1 += $.formatString('<a id="adjustAMT" href="javascript:void(0)" class="easyui-linkbutton-add" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="showDetail(\'{0}\');" >查看</a>', row.id);
                        return str1;
                    }
                }
            ]]
        });
    });


    //查看
    function showDetail(id) {
        if(id == null || id == undefined){
            alertMsg("系统错误，请重试！");
            return;
        }
        addTab({
            title : '查看详情'+id,
            border : false,
            closable : true,
            fit : true,
            href : '${path}/profit/template/showDetail?id='+id
        });

    }
    //查询
    function searchInfo(){
        templatApplyList.datagrid('load', $.serializeObject($('#applyListGrip')));
    }
    //重置
    function cleanhSearchInfo(){
        $("#applyListGrip input").val("");
        $("#applyListGrip select").val("");
        templatApplyList.datagrid('load',{});
    }

    function alertMsg(msg) {
        parent.$.messager.alert('提示',msg, 'info');
    }

    //分润模板申请
    function templateApply() {
        addTab({
            title : '模板申请',
            border : false,
            closable : true,
            fit : true,
            href : '${path}/profit/template/toTemplateApplyPage'
        });

    }

</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="applyInfo" data-options="region:'west',border:true" style="width: 100%;overflow: hidden;">
        <table id="applyList" data-options="fit:true,border:false,toolbar:'#toolbar'"></table>
    </div>
    <div id="" data-options="region:'north',border:false" style="height:85px;overflow: hidden;background-color: #fff;">
        <form id="applyListGrip" method="post">
            <table>
                <tr>
                    <td>代理商唯一编码</td>
                    <td>
                        <input id="agentId" name="agentId" style="line-height: 17px;border: 1px solid #ccc;width: 160px"/>
                    </td>
                    <td>代理商名称</td>
                    <td>
                        <input id="agentName" name="agentName" style="line-height: 17px;border: 1px solid #ccc;width: 160px"/>
                    </td>
                    <td>业务平台</td>
                    <td>
                        <select id="busPlatform" name="busPlatform" style="line-height: 17px;border: 1px solid #ccc;width: 160px">
                            <option value="">--请选择--</option>
                            <c:forEach items="${ablePlatForm}" var="ablePlatFormItem"  >
                                <option value="${ablePlatFormItem.platformNum}">${ablePlatFormItem.platformName}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'icon-search',plain:true" onclick="searchInfo();">查询</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanhSearchInfo();">重置</a>
                    </td>
                </tr>
                <tr>
                    <td>业务平台码</td>
                    <td>
                        <input id="busNum" name="busNum" style="line-height: 17px;border: 1px solid #ccc;width: 160px"/>
                    </td>
                    <td>模板名称</td>
                    <td>
                        <input id="templateName" name="templateName" style="line-height: 17px;border: 1px solid #ccc;width: 160px"/>
                    </td>
                    <td>审批结果</td>
                    <td>
                        <select id="applyResult" name="applyResult" style="line-height: 17px;border: 1px solid #ccc;width: 160px">
                            <option value="">--请选择--</option>
                            <option value="4">通过</option>
                            <option value="1">申请中</option>
                            <option value="2">退回</option>
                            <option value="3">撤销</option>
                        </select>
                    </td>
                </tr>
            </table>
        </form>
        <shiro:hasPermission name="/profit/template/toTemplateApplyPage" >
            <a  href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'" onclick="templateApply()">分润模板申请</a>
        </shiro:hasPermission>
    </div>
</div>
