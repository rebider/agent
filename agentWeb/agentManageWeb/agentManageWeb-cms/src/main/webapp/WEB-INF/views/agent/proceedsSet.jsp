<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div title="查看信息">
    <%@ include file="/commons/queryAgentBase_model.jsp" %>
    <%@ include file="/commons/queryAgentBusi_model.jsp" %>
    <%@ include file="/commons/approval_account.jsp" %>
</div>
<shiro:hasPermission name="/agent/capitalsetting">
<div style="text-align:center;padding:5px">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveProceedsSet()">保存</a>
</div>
</shiro:hasPermission>
<script type="text/javascript">
    function saveProceedsSet() {
        var payCompanyTable = (typeof get_payCompanyTable_FormDataItem === "function") ? get_payCompanyTable_FormDataItem() : [];
        var addAgentAccountTable = (typeof get_addAgentAccountTable_FormDataItem === "function") ? get_addAgentAccountTable_FormDataItem() : [];
        var busInfoVoList = [];
        if (payCompanyTable != '') {
            busInfoVoList = payCompanyTable;
        } else if (busEditTable != '') {
            busInfoVoList = busEditTable;
        }
        var subFlag = false;
        if (payCompanyTable != '') {
            $.each(payCompanyTable, function (index, content) {
                if (content.cloPayCompany == '') {
                    info("请分配打款公司！");
                    return false;
                }
                subFlag = true;
            });
        } else {
            subFlag = true;
        }
        if (subFlag) {
            $.ajaxL({
                type: "POST",
                url: "/business/save",
                dataType: 'json',
                contentType: 'application/json;charset=UTF-8',
                data: JSON.stringify({
                    agentColinfoRelList: addAgentAccountTable,
                    busInfoVoList: busInfoVoList,
                }),
                success: function (msg) {
                    info(msg.resInfo);
                    if (msg.resCode == '1') {
                        $('#index_tabs').tabs('close',"收款账户设置");
                        RefreshCloudHomePageTab();
                    }
                },
                complete: function (XMLHttpRequest, textStatus) {

                }
            });
        }
    }
</script>