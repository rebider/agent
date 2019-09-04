<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var alreadyTaskList;
    $(function() {
        alreadyTaskList = $('#alreadyTaskList').datagrid({
            url : '${path }/activity/task/alreadyTaskList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            queryParams: {
                1:1
                <shiro:hasPermission name="/activity/task/userSee">,approvalPerson:${userId}</shiro:hasPermission>
                <shiro:hasPermission name="/activity/task/orgSee">,approvalDep:${orgId}</shiro:hasPermission>
            },
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                title : '任务编号',
                field : 'taskId'
            }, {
                title : '任务实例编号',
                field : 'executionId'
            } , {
                title : '业务编号',
                field : 'busId'
            }, {
                title : '业务类型',
                field : 'busTypeName'
            } , {
                title : '代理商唯一编码',
                field : 'agentId'
            }, {
                title : '代理商名称',
                field : 'agentName'
            } , {
                title : '申请提交日',
                field : 'subMitDate'
            }, {
                title : '审批人',
                field : 'approvalPerson'
            } , {
                title : '审批部门',
                field : 'approvalDep'
            } , {
                title : '审批时间',
                field : 'approvalTime'
            } , {
                title : '审批结果',
                field : 'approvalResult',
                formatter: function (value, row) {
                    if (value == 'pass' ) {
                        return "通过";
                    }else if(value == 'reject' ) {
                        return "拒绝";
                    }else if(value == 'back' ) {
                        return "退回";
                    }else if(value == 'cancel' ){
                        return "撤销";
                    }
                    return "未知";
                }
            } , {
                title : '审批意见',
                field : 'approvalOpinion'
            }, {
                field : 'action',
                title : '操作',
                width : 120,
                formatter : function(value, row, index) {
                    var str = '';
                    str += $.formatString('<a href="javascript:void(0)" class="alrTask-easyui-linkbutton-see"  data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="alreadyTaskSee(\'{0}\',\'{1}\');" >查看</a>', row.busId,row.busType);
                    return str;
                }
            }
            ]],
            onLoadSuccess:function(data){
                $('.alrTask-easyui-linkbutton-see').linkbutton({text:'查看'});
            }
        });
    });

    function alreadyTaskSee(busId,busType) {
        var url = "";
        if(busType=='Agent'){
            /*url = '/agentEnter/agentQuery?id=' + busId + "&agStatus=Approved";*/
            url = '/agentEnter/agentQuery?id=' + busId ;
        }else if(busType=='Business'){
            url = '/business/toSeeBusPlatPage?id='+busId+"&cloReviewStatus=3";
        }else if(busType=='DC_Agent'){
            url = '/dataChangeReq/selectById?id=' + busId;
        }else if(busType=='DC_Colinfo'){
            url = '/dataChangeReq/selectById?id=' + busId + "&agStatus=Approved";
        }else if(busType=='STAGING'){
            url = '${path}/stagActivity/gotoTaskApproval?sourceId=' + busId;
        }else if(busType=='OTHER_DEDUCTION'){
            url = '/stagActivity/gotoTaskApproval?sourceId=' + busId;
        }
        else if(busType=='ASSESS_DEDUCTION'){
            url = '/stagActivity/gotoTaskApproval?sourceId=' + busId;
        } else if(busType=='THAW'){
            url = '${path}/thawActivity/gotoTaskApproval?id=' + busId;
        }else if(busType=='TOOLS'){
            url = '/toolsActivity/gotoTaskApproval?id='+busId;
        }else if(busType=='PkType'){
            url = '/supplement/queryById?id='+ busId;
        }else if(busType=='ORDER'){
            url = '/orderbuild/orderApprView?orderId='+busId;
        }else if(busType=='COMPENSATE'){
            url = '/compensate/refundPriceDiffQuery?id='+busId+"&reviewStatus=3";
        }else if(busType=='refund'){
            url = '/order/return/page/viewAgentIndex?returnId='+busId;
        }else if(busType=='POSREWARD'){
            url = '/rewardActivity/gotoTaskApproval?id='+busId;
        } if(busType=='POSHUDDLEREWARD'){
            url = '/rewardActivity/gotoHuddleTaskApproval?id='+busId;
        } else if(busType=='POSCHECK'){
            url = '/checkActivity/gotoTaskApproval?id='+busId;
        }else if(busType=='POSTAX'){
            url = '/posTaxActivity/gotoTaskApproval?id='+busId;
        }else if(busType=='MERGE'){
            url = '/agentMerge/toAgentMergeQuery?id='+busId+"&cloReviewStatus=3";
        }else if(busType=='agentTerminal'){
            url = '/terminal/toTerminalQuery?id='+busId+"&reviewStatus=3";
        }else if(busType=='MERGE'){
            url = '/agentMerge/toAgentMergeQuery?id=' + busId + "&cloReviewStatus=3";
        }else if(busType=='QUIT'){
            url = '/agentQuit/toAgentQuitQuery?id=' + busId + "&reviewStatus=3";
        }else if(busType=='agentQuitRefund'){
            url = '/quitRefund/toQuitRefundQuery?id=' + busId + "&reviewStatus=3";
        }else if(busType=='capitalChange'){
            url = '/capitalChange/toCapitalChangeQuery?id=' + busId + "&cloReviewStatus=3";
        }else if(busType=='CityApplyDeduction'){
            url = '/profit/application/toAgentMergeQuery?id='+busId;
        }else if(busType=='CityApplySupply'){
            url = '/profitSupply/toAgentMergeQuery?id='+busId;
        }else if(busType=='INVOICEAPPLY'){
            url = '/profit/invoiceDetail/toAgentInvoiceQuery?id='+busId;
        }else if(busType=='agentRelate'){
            url = '/agentRelate/examineDetail?busId='+busId
        }else if(busType=='thawAgentByCity'){
            url = '/agentFreeze/examineDetail?busId='+busId+'&busType='+busType
        }else if(busType=='thawAgentByBusiness'){
            url = '/agentFreeze/examineDetail?busId='+busId+'&busType='+busType
        }else if(busType=='cardRenew'){
            url = '/internetRenew/toInternetCardRenewQuery?id=' + busId + "&cloReviewStatus=3"
        }else if(busType=='profitTempalteApply'){
            url = '/profit/template/examineDetail?id='+busId
        }
        alreayTaskSee(busId,url);
    }

    function alreayTaskSee(id,url) {
        addTab({
            title: '已处理-查看' + id,
            border: false,
            closable: true,
            fit: true,
            href: url
        });
    }

    function searchAlreadyTask() {
        alreadyTaskList.datagrid('load', $.serializeObject($('#searchAlreadyTaskForm')));
    }

    function cleanAlreadyTask() {
        $('#searchAlreadyTaskForm input').not("input[name='approvalPerson']").not("input[name='approvalDep']").val('');
        $('#searchAlreadyTaskForm select').val('');
        alreadyTaskList.datagrid('load', $.serializeObject($('#searchAlreadyTaskForm')));
    }

    //地区选择
    function taskAlrRegion(data, options) {
        $(options.target).prev("input").val(data.id);
        $(options.target).prev("input").prev("input").val(data.text);
    }

    //导出数据
    function exportTask() {
        var busType = $("select[name='busType']").val();
        if(busType==''){
            info("请选择业务类型");
            return false;
        }
        if(busType=='MERGE' || busType=='Agent' || busType=='DC_Agent'){
            $('#searchAlreadyTaskForm').form({
                url: '${path }/activity/task/exportTaskData',
                onSubmit: function() {
                    return $(this).form('validate');
                }
            });
            $('#searchAlreadyTaskForm').submit();
        }else{
            info("暂无该导出类型")
        }
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true"  style="width:100%;overflow: hidden; ">
        <table id="alreadyTaskList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 100px; overflow: hidden;background-color: #fff">
        <form id ="searchAlreadyTaskForm">
            <shiro:hasPermission name="/activity/task/userSee">
                <input type="hidden" name="approvalPerson" value="${userId}">
            </shiro:hasPermission>
            <shiro:hasPermission name="/activity/task/orgSee">
                <input type="hidden" name="approvalDep" value="${orgId}">
            </shiro:hasPermission>
            <table>
                <tr>
                    <th>业务编号:</th>
                    <td><input name="busId" style="line-height:17px;border:1px solid #ccc"></td>
                    <th style="width: 70px">业务类型:</th>
                    <td>
                        <select name="busType" style="width:160px;height:21px">
                            <option value="">--请选择--</option>
                            <c:forEach items="${busTypeList}" var="busTypeList">
                                <option value="${busTypeList.key}">${busTypeList.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <th>审批时间：</th>
                    <td><input name="beginTime" class="easyui-datebox" style="line-height:17px;border:1px solid #ccc;width:160px;" value=""></td>
                    <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;至</th>
                    <td><input name="endTime" class="easyui-datebox" style="line-height:17px;border:1px solid #ccc;width:160px;" value=""></td>
                </tr>
                <tr>
                    <th>审批结果:</th>
                    <td>
                        <select name="appResult" style="width:145px;height:21px">
                            <option value="">--请选择--</option>
                            <c:forEach items="${approvalResultList}" var="approvalResultList">
                                <option value="${approvalResultList.dItemvalue}">${approvalResultList.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <th>代理商唯一编码:</th>
                    <td><input name="agentId" style="line-height:17px;border:1px solid #ccc"></td>
                </tr>
                <tr>
                    <th>代理商名称:</th>
                    <td><input name="agentName" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>提交时间：</th>
                    <td><input name="subMitDateSta" class="easyui-datebox" style="line-height:17px;border:1px solid #ccc;width:160px;" value=""></td>
                    <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;至</th>
                    <td><input name="subMitDateEnd" class="easyui-datebox" style="line-height:17px;border:1px solid #ccc;width:160px;" value=""></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchAlreadyTask();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanAlreadyTask();">清空</a>
                    </td>
                    <td>
                        <a href='javascript:void(0)' class="easyui-linkbutton" data-options="iconCls:'icon-filter',plain:true" onclick="exportTask()">导出</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>