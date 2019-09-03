<shiro:hasPermission name="/agActivity/approvalAccount">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="easyui-panel" title="审批" data-options="iconCls:'fi-results'">
    <table class="grid" id="accountTableId">
        <tr>
            <td>结算业务类型</td>
            <td>收款账户</td>
            <td style="display: none;">打款公司分配</td>
            <td>出款机构</td>
        </tr>
            <tr>
                <c:forEach items="${agentBusInfoList}" var="agentBusInfo"  >
                    <td>
                        <input type="hidden" name="agentbusPlatForm" value="${agentBusInfo.BUS_PLATFORM}">
                        <input type="hidden" name="agentbusid" value="${agentBusInfo.ID}">
                        <input type="hidden" name="agentAgentId" value="${agentBusInfo.AGENT_ID}">
                        <c:forEach items="${ablePlatForm}" var="ablePlatFormItem">
                            <c:if test="${ablePlatFormItem.platformNum== agentBusInfo.BUS_PLATFORM}">${ablePlatFormItem.platformName}</c:if>
                        </c:forEach>
                    </td>
                    <td>
                        <select name="agentColinfoid" id="agentColinfoid" style="width:320px;height:21px" >
                            <c:forEach items="${agentColinfos}" var="agentColinfos">
                                <option value="${agentColinfos.id}">
                                    <c:forEach items="${colInfoType}" var="colInfoTypeItem">
                                        <c:if test="${colInfoTypeItem.dItemvalue==agentColinfos.cloType}">${colInfoTypeItem.dItemname}</c:if>
                                    </c:forEach>
                                    |${agentColinfos.cloRealname}
                                    |${agentColinfos.cloBank}
                                    |${agentColinfos.cloBankBranch}
                                    |${agentColinfos.cloBankAccount}
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                </c:forEach>
                <td style="display: none;">
                    <c:choose>
                        <c:when test="${not empty comp.PAYCOMANYID}">
                            <input name="cloPayCompany" value="${comp.PAYCOMANYID}" type="hidden" disabled="disabled">
                            <input name="comp" value="${comp.COM_NAME}" disabled="disabled">
                        </c:when>
                        <c:otherwise>
                            <select name="cloPayCompany" style="width:160px;height:21px" id="cloPayCompany" disabled="disabled">
                            </select>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <select name="finaceRemitOrgan" id="finaceRemitOrgan" style="width:200px;height:21px" data-options="required:true">
                        <option value="">---请选择---</option>
                        <c:forEach items="${allFinceOrganList}" var="organListItem">
                            <option value="${organListItem.orgId}">${organListItem.orgName}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
    </table>
</div>
<script>
    $(function(){
        var agentColinfo = (typeof get_agentColinfo=== "function")?get_agentColinfo():[];
        $.each(agentColinfo, function(index, content) {
            var cloType= content.cloType;//1---对公  2--对私
            var cloInvoice= content.cloInvoice;//0--否  1--是
            if(cloType==1 && cloInvoice==1){
                //对公开发票--深圳瑞银信公司
                $("#cloPayCompany").empty();
                $("#cloPayCompany").append("<option value='Q000029564'>深圳瑞银信信息技术有限公司</option>");
            }else if(cloType==1 && cloInvoice==0){
                //对公不开发票--烟台瑞熙公司
                $("#cloPayCompany").empty();
                $("#cloPayCompany").append("<option value='Q1'>烟台公司</option>");
            }else if (cloType==2){
                //对私---烟台瑞熙公司
                $("#cloPayCompany").empty();
                $("#cloPayCompany").append("<option value='Q1'>烟台公司</option>");
            }
        });
    })

    function get_payCompanyTable_FormDataItem() {
        var formDataJson = [];
        $('#accountTableId tr').each(function(i){
            if(i>=1){
                var data = {};
                var selectComp= $(this).find("select[name='cloPayCompany']").val();
                var inputComp=$(this).find("input[name='cloPayCompany']").val();
                if(null==selectComp){
                    data.cloPayCompany  =inputComp ;
                }else if(null==inputComp){
                    data.cloPayCompany  =selectComp;
                }
                data.id  = $(this).find("input[name='agentbusid']").val();
                formDataJson.push(data);
            }
        });
        return formDataJson;
    }

    function get_addAgentAccountTable_FormDataItem() {
        var formDataJson = [];
        $('#accountTableId tr').each(function(i){
            if(i>=1){
                var data = {};
                data.agentColinfoid = $(this).find("select[name='agentColinfoid']").val();
                data.busPlatform = $(this).find("input[name='agentbusPlatForm']").val();
                data.agentbusid = $(this).find("input[name='agentbusid']").val();
                data.agentid = $(this).find("input[name='agentAgentId']").val();
                formDataJson.push(data);
            }
        });
       return formDataJson;
    }

    function get_organNumTable_Form() {
        var formDataJson = [];
        $('#accountTableId tr').each(function(i){
            if (i >= 1) {
                var data = {};
                data.finaceRemitOrgan = $(this).find("select[name='finaceRemitOrgan']").val();
                data.id = $(this).find("input[name='agentbusid']").val();
                formDataJson.push(data);
            }
        });
       return formDataJson;
    }
</script>
</shiro:hasPermission>