<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-panel" title="修改机构" data-options="iconCls:'fi-results'">
    <form id="OrganizationEditTable_model_form">
    </form>
</div>
<div style="display: none;" id="OrganizationTable_model_edit">
    <table class="grid">
        <tbody>
        <c:if test="${!empty Organization}">
            <c:forEach items="${Organization}" var="Organization">
                <tr>
                    <input type="hidden" name="orgId" value="${Organization.orgId}">

                    <td width="150px">机构名称</td>
                    <td width="200px">
                        <input name="orgName" type="text" input-class="easyui-validatebox" style="width:160px;"
                               value="${Organization.orgName}"
                               data-options="required:true,validType:['length[1,100]','CP']"/>
                    </td>
                    <td width="150px">云账户名
                    </td>
                    <td width="200px"><input name="accountName" type="text" input-class="easyui-validatebox"
                                             style="width:160px;"
                                             value="${Organization.accountName}"
                                             data-options="required:true,validType:['length[1,100]','CP']"/></td>

                    <td width="150px">云账号
                    </td>
                    <td width="200px"><input name="accountNum" type="text" input-class="easyui-validatebox"
                                             style="width:160px;"
                                             value="${Organization.accountNum}"
                                             data-options="required:true"/></td>
                </tr>

                <tr>
                    <td>云账户开户行</td>
                    <td>
                        <input name="accountBank" type="text" input-class="easyui-validatebox" style="width:160px;"
                               value="${Organization.accountBank}"
                               data-options="required:true,validType:['length[1,200]','CHS']">
                    </td>
                    <td width="150px">银行卡号</td>
                    <td width="200px">
                        <input name="bankCard" type="text" input-class="easyui-validatebox" style="width:130px;"
                               data-options="required:true,validType:['length[1,30]','Number']"
                               value="${Organization.bankCard}">
                        <a href="javascript:void(0);" onclick="discernCardNo(this)">识别</a>
                    </td>
                    <td>代理商编码</td>
                    <td><input name="agentId" type="text" input-class="easyui-validatebox" style="width:160px;"
                               value="${Organization.agentId}"
                               data-options="required:true"></td>
                </tr>

                <tr>


                    <td>开户行地区</td>
                    <td>
                        <input type="text" input-class="easyui-validatebox" id="bankRegion" style="width:100px;"
                               data-options="required:true" readonly="readonly"
                               value="<agent:show type="region" busId="${Organization.bankRegion}"/>">
                        <input name="bankRegion" type="hidden" id="bankRegions" value="${Organization.bankRegion}"/>
                        <a href="javascript:void(0);"
                           onclick="showRegionFrame({target:this,callBack:returnBankRegion},'/region/regionTree',false)">选择</a>
                        <a href="javascript:void(0);" onclick="del(this)">清除</a>
                    </td>
                    <td>收款账户名</td>
                    <td><input name="cloRealname" type="text" input-class="easyui-validatebox" style="width:160px;"
                               value="${Organization.cloRealname}"
                               data-options="required:true,validType:['length[1,100]','CP']"/></td>
                    <td>收款账户类型</td>
                    <td>
                        <select name="cloType" style="width:160px;height:21px">
                            <option value="1">对公</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>收款银行总行</td>
                    <td>
                        <input name="cloBank" type="text" input-class="easyui-validatebox" style="width:160px;"
                               value="${Organization.cloBank}"
                               data-options="required:true,validType:['length[1,200]','CHS']">
                            <%--<input name="cloBankCode" type="hidden">--%>
                    </td>
                    <td>收款银行支行</td>
                    <td class="branchTd">
                        <input class="easyui-combobox" name="cloBankBranch" id="cloBankBranch"
                               value="${Organization.cloBankBranch}"
                               data-options="valueField:'id',textField:'text',panelHeight:'auto',panelMaxHeight:200,panelMinHeight:100"
                               style="width: 160px;height: 29px"/>
                    </td>
                    <td>总行联行号</td>
                    <td><input name="allLineNum" maxlength="14" type="text" input-class="easyui-validatebox"
                               value="${Organization.allLineNum}"
                               style="width:160px;" data-options="required:true,validType:['length[1,32]','CHS']"></td>
                </tr>
                <tr>
                    <td>支行联行号</td>
                    <td><input name="branchLineNum" maxlength="14" type="text" input-class="easyui-validatebox"
                               value="${Organization.branchLineNum}"
                               style="width:160px;" data-options="required:true,validType:['length[1,32]','CHS']"></td>
                </tr>
                <tr>
                    <td>业务平台</td>
                    <td colspan="3">
                        <c:forEach items="${ablePlatForm}" var="ablePlatFormItem" varStatus="status">
                            <c:if test="${status.count eq 1 || (status.count-1) % 3 eq 0}">
                                <tr>
                            </c:if>
                            <td>
                            <input name="platId" type="checkbox" id="platId"
                                   value="${ablePlatFormItem.platformNum}" onclick="platIdClick(this)"
                                    <%--<c:forEach items="${platIdList}" var="platId">--%>
                                        <%--<c:if test="${platId==ablePlatFormItem.platformNum}">--%>
                                            <%--checked--%>
                                        <%--</c:if>--%>
                                    <%--</c:forEach>--%>
                            />
                            ${ablePlatFormItem.platformName}
                            </td>
                            <td>
                            <input id="businessNum" name="businessNum_${ablePlatFormItem.platformNum}" type="text" />
                            </td>
                            <c:if test="${status.count % 3 eq 0 || status.count eq 3}">
                                </tr>
                            </c:if>
                        </c:forEach>
                    </td>

                </tr>
                <tr>
                    <td>备注</td>
                    <td><input name="remark" type="text" input-class="easyui-validatebox" style="width:160px;"
                               value="${Organization.remark}"
                               data-options="validType:['length[1,500]','CHS']"></td>
                </tr>
                <tr>
                    <td>附件</td>
                    <td colspan="3" class="attrInput">
                        <c:if test="${!empty Organization.attachmentList}">
                            <c:forEach items="${Organization.attachmentList}" var="attachment">
                                    <span
                                            onclick='removeEditOrganizationTable_jxkxUploadFile(this)'
                                    >${attachment.attName}<input type='hidden' name='colinfoTableFile'
                                                                 value='${attachment.id}'/></span>
                            </c:forEach>
                        </c:if>
                    </td>
                    <td colspan="3">
                        <a href="javascript:void(0)" class="editOrganizationTabledAddAttr-easyui-linkbutton-edit"
                           data-options="plain:true,iconCls:'fi-magnifying-glass'"
                           onclick="addEditOrganization_uploadView(this,ATT_DATA_TYPE_STATIC.KHXUZ.key)">开户许可证扫描件</a>||
                        <a href="javascript:void(0)" class="editOrganizationTabledAddAttr-easyui-linkbutton-edit"
                           data-options="plain:true,iconCls:'fi-magnifying-glass'"
                           onclick="addEditOrganization_uploadView(this,ATT_DATA_TYPE_STATIC.YHKSMJ.key)">银行卡扫描件</a>||
                        <a href="javascript:void(0)" class="editOrganizationTabledAddAttr-easyui-linkbutton-edit"
                           data-options="plain:true,iconCls:'fi-magnifying-glass'"
                           onclick="addEditOrganization_uploadView(this,ATT_DATA_TYPE_STATIC.YBNSRZM.key)">一般纳税人证明</a>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
</div>
<div style="text-align:right;padding:5px;margin-bottom: 50px;">
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"
       onclick="organizationEditTable()">保存</a>
</div>
<script>
    var cloBankId = "";
    $(function () {
        editOrganization_model();
        var boxObj = $("input:checkbox[name='platId']");  //获取所有的复选框
        var expresslist = '${platId}'; //用el表达式获取在控制层存放的复选框的值为字符串类型
        var businessNum = '${businessNum}';
        var express = expresslist.split(','); //去掉它们之间的分割符“，”
        var businessNums = businessNum.split(','); //去掉它们之间的分割符“，”
        for (i = 0; i < boxObj.length; i++) {
            for (j = 0; j < express.length; j++) {
                if (boxObj[i].value == express[j])  //如果值与修改前的值相等
                {
                    $("input[name='businessNum_" + express[j] + "']").val(businessNums[j]);
                    boxObj[i].checked = true;
                    break;
                }
            }
        }

    });

    //复选框的点击事件
    function  platIdClick(platId) {
        $(platId).parent().find("#businessNum").val(" ");
    }

    function removeEditOrganizationTable_jxkxUploadFile(t){
        parent.$.messager.confirm('询问', '确定删除附件么？', function(b) {
            if (b) {
                $(t).remove();
            }
        });
    }

    function editOrganization_model() {
        $("#OrganizationEditTable_model_form").append($("#OrganizationTable_model_edit").html());
        var inputs = $("#OrganizationEditTable_model_form .grid:last input");
        for (var i = 0; i < inputs.length; i++) {
            if ($(inputs[i]).attr("input-class") && $(inputs[i]).attr("input-class").length > 0)
                $(inputs[i]).addClass($(inputs[i]).attr("input-class"));
        }
        $.parser.parse($("#OrganizationEditTable_model_form .grid:last"));
        editOrganizationTableAttr_colchange();
    }


    var addOrganization;

    //上传窗口
    function addEditOrganization_uploadView(t, attDataType) {
        addOrganization = $(t).parent().parent().find(".attrInput");
        multFileUpload(addOrganization_jxkxUploadFile, attDataType);
    }

    //附件解析
    function addOrganization_jxkxUploadFile(data) {
        var jsondata = eval(data);
        for (var i = 0; i < jsondata.length; i++) {
            $(addOrganization).append("<span onclick='removeOrganization_jxkxUploadFile(this)'>" + jsondata[i].attName + "<input type='hidden' name='organizationbleFile' value='" + jsondata[i].id + "' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }

    }

    function removeOrganization_jxkxUploadFile(t) {
        parent.$.messager.confirm('询问', '确定删除附件么？', function (b) {
            if (b) {
                $(t).remove();
            }
        });
    }

    //解析打个table
    function get_editOrganization_FormDataItem(table) {
        var data = {};
        data.orgId = $(table).find("input[name='orgId']").val();
        data.orgName = $(table).find("input[name='orgName']").val();
        data.agentId = $(table).find("input[name='agentId']").val();
        var optIDs = '';
        var platcode = '';
        $("#OrganizationEditTable_model_form input[name='platId']:checked").each(function () {
            optIDs += this.value + ',';
            platcode+= $("input[name='businessNum_"+this.value+"']").val()+',';
        }); //遍历，用逗号串联
        data.platId = optIDs;
        data.businessNum = platcode;
        data.orgPlatform=[{platNum:optIDs,platCode:platcode}];
        data.bankCard = $(table).find("input[name='bankCard']").val();
        data.cloRealname = $(table).find("input[name='cloRealname']").val();
        data.cloType = $(table).find("select[name='cloType']").val();
        data.cloBank = $(table).find("input[name='cloBank']").val();
        data.cloBankBranch = $(table).find("input[name='cloBankBranch']").val();
        data.allLineNum = $(table).find("input[name='allLineNum']").val();
        data.branchLineNum = $(table).find("input[name='branchLineNum']").val();
        data.bankRegion = $(table).find("input[name='bankRegion']").val();
        data.remark = $(table).find("input[name='remark']").val();
        data.accountName = $(table).find("input[name='accountName']").val();
        data.accountNum = $(table).find("input[name='accountNum']").val();
        data.accountBank = $(table).find("input[name='accountBank']").val();
        var files = $(table).find(".attrInput").find("input[name='organizationbleFile']");
        var organizationFileTemp = [];
        for (var j = 0; j < files.length; j++) {
            organizationFileTemp.push($(files[j]).val());
        }
        if (organizationFileTemp.length > 0)
            data.organizationbleFile = organizationFileTemp;
        return data;
    }

    //获取form数据
    function addOrganization_FormData() {
        var editOorganizationTable_FormDataJson = [];
        var tables = $("#OrganizationEditTable_model_form .grid");
        for (var i = 0; i < tables.length; i++) {
            var table = tables[i];
            editOorganizationTable_FormDataJson.push(get_editOrganization_FormDataItem(table));
        }
        return editOorganizationTable_FormDataJson;
    }

    function discernCardNo(obj) {
        var bankCardNo = $(obj).parent("td").find("input").val();
        if (bankCardNo == '' || bankCardNo == undefined) {
            info("卡号不可为空！");
            return false;
        }
        $.ajax({
            url: "${path}/etbSysCard/queryCardNo",
            type: 'POST',
            data: {
                bankCardNo: bankCardNo
            },
            dataType: 'json',
            success: function (result) {
                if (result.msg != '' && result.msg != undefined) {
                    info(result.msg);
                    return false;
                }
                cloBankId = result.issUsers;
                $(obj).parent().parent().parent().find("input[name='cloBank']").val(result.bankName);
//                $(obj).parent().parent().parent().find("input[name='cloBankCode']").val(result.issUsers);
                $(obj).parent().parent().parent().find("input[name='cloBank']").removeClass("validatebox-invalid");
            },
            error: function (data) {
                info("系统异常，请联系管理员！");
            }
        });
    }

    //地区选择
    function returnBankRegion(data, options) {
        $(options.target).prev("input").val(data.id);
        $(options.target).prev("input").prev("input").val(data.text);
        if (cloBankId != '' && data.id != '') {
            $('#cloBankBranch').combobox({
                prompt: '输入首关键字自动检索',
                required: false,
                url: '/etbSysCard/queryLineNumList?cloBankId=' + cloBankId + "&regionId=" + data.id,
                editable: true,
                hasDownArrow: true,
                filter: function (q, row) {
                    var opts = $(this).combobox('options');
                    return row[opts.textField].indexOf(q) == 0;
                },
                onChange: function (n, o) {
                    var data = $(this).combobox("getData");
                    var bankBranchId = "";
                    var liqBankId = "";
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].text == n) {
                            bankBranchId = data[i].bankBranchId;
                            liqBankId = data[i].liqBankId;
                        }
                    }
                    $("input[name='allLineNum']").val(liqBankId);
                    $("input[name='branchLineNum']").val(bankBranchId);
                    $("input[name='allLineNum']").removeClass("validatebox-invalid");
                    $("input[name='branchLineNum']").removeClass("validatebox-invalid");
                }
            });
        }
    }

    function editOrganizationTableAttr_colchange() {
        $("select[name='cloType']").change();
    }


    function organizationEditTable() {
        var editOorganizationTable = (typeof addOrganization_FormData === "function") ? addOrganization_FormData() : [];
         var organizationEdit = $("#OrganizationEditTable_model_form").form('validate');
         if (organizationEdit) {
             parent.$.messager.confirm('询问', '确认要保存？', function (b) {
                 if (b) {
                     $.ajaxL({
                         type: "POST",
                         url: "/oorganization/organizationEdit",
                         dataType: 'json',
                         traditional: true,//这使json格式的字符不会被转码
                         contentType: 'application/json;charset=UTF-8',
                         data: JSON.stringify({
                             oorganizationVoList:editOorganizationTable
                         }),
                         beforeSend: function () {
                             progressLoad();
                         },
                         success: function (msg) {
                             info(msg.resInfo);
                             if (msg.resCode == '1') {
                                 $('#index_tabs').tabs('close', "修改机构");
                                 organization_List.datagrid('reload');
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
