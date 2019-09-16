<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs">
    <div title="待审信息">
        <div class="easyui-panel" title="申请信息" data-options="iconCls:'fi-results'">
            <input id="citySupplyId" type="hidden" value="${citySupplyId}">
            <table class="grid">
                <tr>
                    <td>月份</td>
                    <td>代理商名称</td>
                    <td>代理商唯一码</td>
                    <td>上级代理商名称</td>
                    <td>上级代理商唯一码</td>
                    <td>机具类型</td>
                    <td>入网时间</td>
                    <td>机具欠款总额</td>
                    <td>上月月分润</td>
                    <td>预计还款周期</td>
                    <td>线下补款</td>
                    <td>上级代扣</td>
                </tr>
                <c:forEach items="${pToolSupplies}" var="pToolSupply">
                    <tr>
                        <td>${pToolSupply.profitDate}</td>
                        <td>${pToolSupply.agentName}</td>
                        <td>${pToolSupply.agentId}</td>
                        <td>${pToolSupply.parenterAgentName}</td>
                        <td>${pToolSupply.parenterAgentId}</td>
                        <td><span name="busCode">${pToolSupply.busCode}</span></td>
                        <td>${pToolSupply.netInDate}</td>
                        <td>${pToolSupply.toolsInvoiceAmt}</td>
                        <td>${pToolSupply.monthProfitAmt}</td>
                        <td>${pToolSupply.repaymentPeriod}</td>
                        <td>${pToolSupply.remitAmt}</td>
                        <td>${pToolSupply.parenterSupplyAmt}</td>
                    </tr>
                </c:forEach>
            </table>
            <c:if test="${!empty pRemitInfos}">
                <form>
                    <table class="grid">
                        <tr>
                            <td>收款账户类型</td>
                            <td>
                                <input id="paymentAccountType" name="paymentAccountType" data-options="readonly:true" value="${pRemitInfos.inAccountType}" class="easyui-textbox" style="width:200px;">
                            </td>
                            <td>付款账户名</td>
                            <td>
                                <input name="paymentAccountName" data-options="readonly:true" value="${pRemitInfos.inAccountName}" class="easyui-textbox" style="width:200px">
                            </td>
                            <td>付款账号</td>
                            <td>
                                <input name="paymentAccount" data-options="readonly:true" value="${pRemitInfos.outAccount}" class="easyui-textbox" style="width:200px">
                            </td>
                        </tr>
                        <tr>
                            <td>付款开户行</td>
                            <td>
                                <input name="paymentBank" data-options="readonly:true" value="${pRemitInfos.outAccountBank}" class="easyui-textbox" style="width:200px">
                            </td>
                            <td>缴纳款项</td>
                            <td>
                                <input name="payAmt" data-options="readonly:true" value="${pRemitInfos.remitAmt}" class="easyui-textbox" style="width:200px">
                            </td>
                            <td>附件</td>
                            <td>
                                <a id="annexFileBtn" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="openAnnexFileBtn('${annexFilePath}')">查看附件</a>
                            </td>
                        </tr>
                        <tr>
                            <td>打款日期</td>
                            <td>
                                <input name="paymentDate" type= "text" data-options="readonly:true" value="${remitDate}" class= "easyui-textbox">
                            </td>
                        </tr>
                    </table>
                </form>
            </c:if>
            <c:if test="${!empty supplyAndDeductRemark}">
                <input id="supplyAndDeductRemark" value="${supplyAndDeductRemark}" class="easyui-textbox" data-options="multiline:true,readonly:true" style="width:800px;height:100px">
            </c:if>

            <shiro:hasPermission name="/toolsDeduct/cityApproval">
                <input type="hidden" id="tempInput"/><%--此标签用作标识操作人身份--%>
            <div class="easyui-panel" title="修改信息" data-options="iconCls:'fi-results'">
                <table class="grid" title="修改信息">
                    <tr>
                        <td>月份</td>
                        <td>代理商名称</td>
                        <td>代理商唯一码</td>
                        <td>上级代理商名称</td>
                        <td>上级代理商唯一码</td>
                        <td>机具类型</td>
                        <td>入网时间</td>
                        <td>机具欠款总额</td>
                        <td>上月月分润</td>
                        <td>预计还款周期</td>
                        <td>线下补款</td>
                        <td>上级代扣</td>
                    </tr>
                    <c:forEach items="${pToolSupplies}" var="pToolSupply">
                        <tr>
                            <td>
                                <span name="PROFIT_DATE">${pToolSupply.profitDate}</span>
                            </td>
                            <td><span name="AGENT_NAME">${pToolSupply.agentName}</span></td>
                            <td><span name="AGENT_ID">${pToolSupply.agentId}</span></td>
                            <td><span name="PARENT_AGENT_NAME">${pToolSupply.parenterAgentName}</span></td>
                            <td><span name="PARENT_AGENT_ID">${pToolSupply.parenterAgentId}</span></td>
                            <td><span name="BUS_CODE">${pToolSupply.busCode}</span></td>
                            <td><span name="C_INCOM_TIME">${pToolSupply.netInDate}</span></td>
                            <td><span name="MUST_DEDUCTION_AMT">${pToolSupply.toolsInvoiceAmt}</span></td>
                            <td><span name="PROFIT_AMT">${pToolSupply.monthProfitAmt}</span></td>
                            <td><span name="repaymentCycle">${pToolSupply.repaymentPeriod}</span></td>
                            <td>
                                <input id="${pToolSupply.agentId}_REMIT_AMT" style="width: 100px" name="REMIT_AMT" class="easyui-numberbox" type="text" data-options="min:0,precision:2,max:${pToolSupply.toolsInvoiceAmt}" value="0">
                            </td>
                            <td>
                                <c:if test="${pToolSupply.parenterAgentId != null && pToolSupply.rev3 eq '2' }">
                                    <input name="isDeductParentAgent" type="checkbox" style="width: 20px;height: 20px"/>
                                </c:if>
                                <c:if test="${pToolSupply.parenterAgentId != null && pToolSupply.rev3 eq '1'}">
                                    <input name="isDeductParentAgent" disabled type="checkbox" style="width: 20px;height: 20px"/>
                                </c:if>
                                <c:if test="${pToolSupply.parenterAgentId == null }">
                                    <input name="isDeductParentAgent" disabled type="checkbox" style="width: 20px;height: 20px"/>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <c:if test="${!empty pRemitInfos}">
                    <form id="offlineSupply" enctype="multipart/form-data">
                        <table>
                            <tr>
                                <td>收款账户类型</td>
                                <td>
                                    <select id="up_paymentAccountType" effect="收款账户类型" name="paymentAccountType" class="easyui-combobox" style="width:200px;">
                                        <option value="bj">北京</option>
                                        <option value="sz">深圳</option>
                                    </select>
                                </td>
                                <td>付款账户名</td>
                                <td>
                                    <input id="up_paymentAccountName" name="paymentAccountName" class="easyui-textbox" style="width:200px">
                                </td>
                                <td>付款账号</td>
                                <td>
                                    <input id="up_paymentAccount" name="paymentAccount" class="easyui-textbox" style="width:200px">
                                </td>
                            </tr>
                            <tr>
                                <td>付款开户行</td>
                                <td>
                                    <input id="up_paymentBank" name="paymentBank"class="easyui-textbox" style="width:200px">
                                </td>
                                <td>缴纳款项</td>
                                <td>
                                    <input id="up_payAmt" name="payAmt" class="easyui-textbox" style="width:200px">
                                </td>
                                <td>附件</td>
                                <td>
                                    <input id="up_annexFile" name="annexFile" class="easyui-filebox" data-options="prompt:'请选择...',buttonText:'选择'" style="width:200px">
                                </td>
                            </tr>
                            <tr>
                                <td>打款日期</td>
                                <td>
                                    <input id="up_paymentDate" name="paymentDate" type= "text" class= "easyui-datebox" required ="required">
                                </td>
                            </tr>
                        </table>
                    </form>
                </c:if>
                <c:if test="${!empty supplyAndDeductRemark}">
                    <input id="up_supplyAndDeductRemark" name="supplyAndDeductRemark" class="easyui-textbox" data-options="multiline:true,prompt:'申请说明'" style="width:800px;height:100px">
                </c:if>

             </div>
            </shiro:hasPermission>
        </div>
        <div class="easyui-panel" title="审批" data-options="iconCls:'fi-results'">
        <form id="arrivalFoem" enctype="multipart/form-data">
            <table class="grid">
                <shiro:hasPermission name="/toolsDeduct/financeApproval">
                        <tr>
                            <td>到账时间</td>
                            <td><input class="easyui-datetimebox" id="amtArrivalTime" name="amtArrivalTime"
                                       data-options="required:true,showSeconds:false" style="width:150px"></td>
                            <td>核款人</td>
                            <td><input class="easyui-textbox" id="amtArrivalUser" name="amtArrivalUser" style="width:150px"></td>
                            <td>附件</td>
                            <td>
                                <input id="annexFile" name="annexFile" class="easyui-filebox" data-options="prompt:'请选择...',buttonText:'上传'" style="width:200px">
                            </td>
                        </tr>
                </shiro:hasPermission>
                <tr >
                    <td>审批意见</td>
                    <td>
                        <input class="easyui-textbox" name="approvalOpinion" data-options="multiline:true" value="" style="width:300px;height:100px">
                    </td>
                </tr>
                <tr>
                    <td>审批结果</td>
                    <td>
                        <select name="approvalResult" style="width:160px;height:21px" >
                            <option value="pass">通过</option>
                            <option value="back">退回</option>
                        </select>
                    </td>
                </tr>
                <input type="hidden" name="taskId" value="${taskId}">
            </table>
        </form>
        </div>
        <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="submitSupplyAndDeduction()" >提交</a>
        <shiro:hasPermission name="/toolsDeduct/cityApproval">
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="saveSupplyAndDeduction('0')" >保存</a>
        </shiro:hasPermission>
    </div>
    <shiro:hasPermission name="/agActivity/approvalRecordSee">
        <div title="审批记录">
            <%@ include file="/commons/approval_record.jsp" %>
        </div>
    </shiro:hasPermission>
    <div title="审批流程图">
        <shiro:hasPermission name="/agActivity/approvalRecordImgSee">
            <img src="/agActivity/approvalImage?taskId=${taskId}" />
        </shiro:hasPermission>
    </div>
</div>
<script>
    function supply_deduct_get_common_Approval_Form() {
        /*var data = {};
        data.approvalOpinion = $("input[name='approvalOpinion']").val();
        data.approvalResult  = $("select[name='approvalResult']").val();
        data.taskId  = $("input[name='taskId']").val();
        data.citySupplyId = $("#citySupplyId").val();*/
        var myForm;

        if($("#amtArrivalTime").length>0){
            /*data.amtArrivalTime = $("#amtArrivalTime").datetimebox('getValue');
            data.amtArrivalUser = $("#amtArrivalUser").textbox('getValue');*/
            myForm=new FormData($('#arrivalFoem')[0]);
        }else{
            myForm=new FormData();
        }
        myForm.append("approvalOpinion",$("input[name='approvalOpinion']").val());
        myForm.append("approvalResult",$("select[name='approvalResult']").val());
        myForm.append("taskId",$("input[name='taskId']").val());
        myForm.append("citySupplyId",$("#citySupplyId").val());
        return myForm;
    }
    function saveSupplyAndDeduction(temp) { //temp用作记录调用方法的地方
        var myForm;
        var isSupply=$('#paymentAccountType').length==1;
        if (!isSupply){
            var supplyAndDeductRemark=$('#up_supplyAndDeductRemark').textbox('getValue')
            if(supplyAndDeductRemark==''){
                parent.$.messager.alert('提示', '此说明不能为空！', 'error');
                return ;
            }
            myForm=new FormData();
            myForm.append('supplyAndDeductRemark',supplyAndDeductRemark);

        }else{
            if($('#up_annexFile').filebox('getValue')==''){
                parent.$.messager.alert('提示', '附件不能为空！', 'error');
                return ;
            }
            if($('#up_paymentDate').datebox('getValue')==''){
                parent.$.messager.alert('提示', '打款日期不能为空！', 'error');
                return ;
            }
            if($('#up_paymentAccountName').textbox('getValue')==''){
                parent.$.messager.alert('提示', '付款账户名不能为空！', 'error');
                return ;
            }
            if($('#up_paymentAccount').textbox('getValue')==''){
                parent.$.messager.alert('提示', '付款账号不能为空！', 'error');
                return ;
            }
            if($('#up_paymentBank').textbox('getValue')==''){
                parent.$.messager.alert('提示', '付款开户行不能为空！', 'error');
                return ;
            }
            if($('#up_payAmt').textbox('getValue')==''){
                parent.$.messager.alert('提示', '缴纳款项行不能为空！', 'error');
                return ;
            }
            myForm=new FormData($('#offlineSupply')[0]);
        }
        myForm.append('isSupply',isSupply);
        var REMIT_AMT=new Array();
        var isChecked= new Array();
        $('[name="REMIT_AMT"]').each(function(){
            REMIT_AMT.push($(this).val());
        });
        $('[name="isDeductParentAgent"]').each(function(){
            isChecked.push($(this).is(":checked"));
        });

        myForm.append('REMIT_AMT',REMIT_AMT);
        myForm.append('isDeductParentAgent',isChecked);
        myForm.append("citySupplyId",$("#citySupplyId").val());
        $.ajax({
            url:"${path }/toolsDeduct/updateRecoupAndDeduct",
            type: 'post',
            data:myForm,
            cache: false,
            async:false,//改为同步方法
            processData: false,
            contentType: false,
            success:function (data) {
                if (temp=='1'){
                    return ;
                }
                var msg=$.parseJSON(data);
                info(msg.resInfo);
                if(msg.resCode=='1'){
                    $('#index_tabs').tabs('close',"处理任务");
                    activityDataGrid.datagrid('reload');
                }
            }
        });
    }
    function submitSupplyAndDeduction() {
        if ($('#tempInput').length>0){
            console.info('aaa');
            saveSupplyAndDeduction('1');
        }
        var subApprovalTable = (typeof supply_deduct_get_common_Approval_Form === "function")?supply_deduct_get_common_Approval_Form():{};
        parent.$.messager.confirm('询问', '确认完成任务？', function(b) {
            if (b) {
                /*$.ajaxL({
                    type: "post",
                    url: "/toolsActivity/taskToolSupplyApproval",
                    dataType:'json',

                    data:subApprovalTable,
                    cache: false,
                    processData: false,
                    contentType: false,
                    beforeSend:function(){
                        progressLoad();
                    },
                    success: function(msg){
                        info(msg.resInfo);
                        if(msg.resCode=='1'){
                            $('#index_tabs').tabs('close',"处理任务");
                            activityDataGrid.datagrid('reload');
                        }
                    },
                    complete:function (XMLHttpRequest, textStatus) {
                        progressClose();
                    }
                });*/

                $.ajax({
                    url:"${path }/toolsActivity/taskToolSupplyApproval",
                    type: 'post',
                    data:subApprovalTable,
                    cache: false,
                    async:false,
                    processData: false,
                    contentType: false,
                    beforeSend:function(){
                        progressLoad();
                    },
                    success: function(data){
                        var msg=$.parseJSON(data);
                        info(msg.resInfo);
                        if(msg.resCode=='1'){
                            $('#index_tabs').tabs('close',"处理任务");
                            activityDataGrid.datagrid('reload');
                        }
                    },
                    complete:function (XMLHttpRequest, textStatus) {
                        progressClose();
                    }
                });
            }
        });
    }
    function openAnnexFileBtn(path) {
        var temp=path.split('/');
        var fileName=temp[temp.length-1];
        addTab({
            title : '附件'+fileName,
            border : false,
            closable : true,
            fit : true,
            href : '${path }/toolsDeduct/toExamineAnnexFile?annexFilePath='+path
        });
    }
</script>