<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="easyui-panel" title="添加机构" data-options="iconCls:'fi-results'">
    <form id="OrganizationAddTable_form">
    </form>
</div>
<div style="display: none;" id="OrganizationTable_model_Add">
    <table class="grid">
        <tbody>
        <tr>
            <td width="150px">机构名称</td>
            <td width="200px">
                <input name="orgName" type="text" input-class="easyui-validatebox" style="width:160px;"
                       data-options="required:true,validType:['length[1,100]','CP']"/>
            </td>
            <td width="150px">云账户名
            </td>
            <td width="200px"><input name="accountName" type="text" input-class="easyui-validatebox"
                                     style="width:160px;"
                                     data-options="required:true,validType:['length[1,100]','CP']"/></td>

            <td width="150px">云账号
            </td>
            <td width="200px"><input name="accountNum" type="text" input-class="easyui-validatebox" style="width:160px;"
                                     data-options="required:true"/></td>
        </tr>


        <tr>
            <td>云账户开户行</td>
            <td>
                <input name="accountBank" type="text" input-class="easyui-validatebox" style="width:160px;"
                       data-options="required:true,validType:['length[1,200]','CHS']">
            </td>
            <td width="150px">银行卡号</td>
            <td width="200px">
                <input name="bankCard" type="text" input-class="easyui-validatebox" style="width:130px;"
                       data-options="required:true,validType:['length[1,30]','Number']">
                <a href="javascript:void(0);" onclick="discernCardNo(this)">识别</a>
            </td>
            <td>代理商编码</td>
            <td><input name="agentId" type="text" input-class="easyui-validatebox" style="width:160px;"
                       data-options="required:true"></td>
        </tr>
        <tr>


            <td>开户行地区</td>
            <td>
                <input type="text" input-class="easyui-validatebox" id="bankRegion" style="width:100px;"
                       data-options="required:true" readonly="readonly">
                <input name="bankRegion" type="hidden" id="bankRegions"/>
                <a href="javascript:void(0);"
                   onclick="showRegionFrame({target:this,callBack:returnBankRegion},'/region/regionTree',false)">选择</a>
                <a href="javascript:void(0);" onclick="del(this)">清除</a>
            </td>
            <td>收款账户名</td>
            <td><input name="cloRealname" type="text" input-class="easyui-validatebox" style="width:160px;"
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
                       data-options="required:true,validType:['length[1,200]','CHS']">
                <input name="cloBankCode" type="hidden">
            </td>
            <td>收款银行支行</td>
            <td class="branchTd">
                <input class="easyui-combobox" name="cloBankBranch" id="cloBankBranch"
                       data-options="valueField:'id',textField:'text',panelHeight:'auto',panelMaxHeight:200,panelMinHeight:100"
                       style="width: 160px;height: 29px"/>
            </td>
            <td>总行联行号</td>
            <td><input name="allLineNum" maxlength="14" type="text" input-class="easyui-validatebox"
                       style="width:160px;" data-options="required:true,validType:['length[1,32]','CHS']"></td>
        </tr>
        <tr>


            <td>支行联行号</td>
            <td><input name="branchLineNum" maxlength="14" type="text" input-class="easyui-validatebox"
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
                    <input name="platId" type="checkbox" input-class="easyui-validatebox" onclick="platIdAddClick(this)"
                           data-options="required:true" value="${ablePlatFormItem.platformNum}"/>
                    ${ablePlatFormItem.platformName}
                   </td>
                     <td>
                    <input id="businessNumAdd" name="businessNum_${ablePlatFormItem.platformNum}" type="text" input-class="easyui-validatebox"/>
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
                       data-options="validType:['length[1,500]','CHS']"></td>
        </tr>
        <tr>
            <td>附件</td>
            <td class="attrInput" colspan="4"></td>
            <td>
                <a href="javascript:void(0)" style="cursor: pointer;"
                   class="addAgentColinfoTableAttr-easyui-linkbutton-edit"
                   data-options="plain:true,iconCls:'fi-magnifying-glass'"
                   onclick="addAgentColinfoTable_uploadView(this,ATT_DATA_TYPE_STATIC.KHXUZ.key)">开户许可证扫描件</a>||
                <a href="javascript:void(0)" style="cursor: pointer;"
                   class="addAgentColinfoTableAttr-easyui-linkbutton-edit"
                   data-options="plain:true,iconCls:'fi-magnifying-glass'"
                   onclick="addAgentColinfoTable_uploadView(this,ATT_DATA_TYPE_STATIC.YHKSMJ.key)">银行卡扫描件</a>||
                <a href="javascript:void(0)" style="cursor: pointer;"
                   class="addAgentColinfoTableAttr-easyui-linkbutton-edit"
                   data-options="plain:true,iconCls:'fi-magnifying-glass'"
                   onclick="addAgentColinfoTable_uploadView(this,ATT_DATA_TYPE_STATIC.YBNSRZM.key)">一般纳税人证明</a>
            </td>
        </tr>

        </tbody>
    </table>
</div>
<div style="text-align:right;padding:5px;margin-bottom: 50px;">
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"
       onclick="saveOrganization()">保存</a>
</div>
<script>
    var cloBankId = "";
    $(function () {
        addAgentColinfoTable_model();
    });

    //复选框的点击事件
    function  platIdAddClick(platId) {
        $(platId).parent().find("#businessNumAdd").val(" ");
    }

    function addAgentColinfoTable_model() {
        $("#OrganizationAddTable_form").append($("#OrganizationTable_model_Add").html());
        var inputs = $("#OrganizationAddTable_form .grid:last input");
        for (var i = 0; i < inputs.length; i++) {
            if ($(inputs[i]).attr("input-class") && $(inputs[i]).attr("input-class").length > 0)
                $(inputs[i]).addClass($(inputs[i]).attr("input-class"));
        }
        $.parser.parse($("#OrganizationAddTable_form .grid:last"));
        addAgentColinfoTableAttr_colchange();
    }

    function removeAgentColinfoTable_model(t) {
        $(t).parent().parent().parent().parent().remove();
    }

    var addAgentColinfoTable_attrDom;

    //上传窗口
    function addAgentColinfoTable_uploadView(t, attDataType) {
        addAgentColinfoTable_attrDom = $(t).parent().parent().find(".attrInput");
        multFileUpload(addAgentColinfoTable_jxkxUploadFile, attDataType);
    }

    //附件解析
    function addAgentColinfoTable_jxkxUploadFile(data) {
        var jsondata = eval(data);
        for (var i = 0; i < jsondata.length; i++) {
            $(addAgentColinfoTable_attrDom).append("<span onclick='removeAgentColinfoTable_jxkxUploadFile(this)'>" + jsondata[i].attName + "<input type='hidden' name='organizationbleFile' value='" + jsondata[i].id + "' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }

    }

    function removeAgentColinfoTable_jxkxUploadFile(t) {
        parent.$.messager.confirm('询问', '确定删除附件么？', function (b) {
            if (b) {
                $(t).remove();
            }
        });
    }

    //解析打个table
    function get_addAgentColinfoTable_FormDataItem(table) {
        var data = {};
        data.orgName = $(table).find("input[name='orgName']").val();
        data.agentId = $(table).find("input[name='agentId']").val();
        var optIDs = '';
        var platcode = '';
        jQuery("input:checkbox[name='platId']:checked").each(function () {
            optIDs += this.value + ',';
            platcode+= $("input[name='businessNum_"+this.value+"']").val()+',';

        }); //遍历，用逗号串联
        data.orgPlatform=[{platNum:optIDs,platCode:platcode}];
        data.platId = optIDs;
        data.businessNum = platcode;
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
        var colinfoTableFileTemp = [];
        for (var j = 0; j < files.length; j++) {
            colinfoTableFileTemp.push($(files[j]).val());
        }
        if (colinfoTableFileTemp.length > 0)
            data.organizationbleFile = colinfoTableFileTemp;
        return data;
    }

    //获取form数据
    function get_addAgentColinfoTable_FormData() {
        var addAgentColinfoTable_FormDataJson = [];
        var tables = $("#OrganizationAddTable_form .grid");
        for (var i = 0; i < tables.length; i++) {
            var table = tables[i];
            addAgentColinfoTable_FormDataJson.push(get_addAgentColinfoTable_FormDataItem(table));
        }
        return addAgentColinfoTable_FormDataJson;
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
                $(obj).parent().parent().parent().find("input[name='cloBankCode']").val(result.issUsers);
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

    function addAgentColinfoTableAttr_colchange() {
        $("select[name='cloType']").change();
    }


    function saveOrganization() {
        var addOorganizationTable = (typeof get_addAgentColinfoTable_FormData === "function") ? get_addAgentColinfoTable_FormData() : [];
        var organizationAdd = $("#OrganizationAddTable_form").form('validate');
        console.log(addOorganizationTable)
        if (organizationAdd) {
            parent.$.messager.confirm('询问', '确认要保存？', function (b) {
                if (b) {
                    $.ajaxL({
                        type: "POST",
                        url: "/oorganization/organizationAdd",
                        dataType: 'json',
                        traditional: true,//这使json格式的字符不会被转码
                        contentType: 'application/json;charset=UTF-8',
                        data: JSON.stringify({
                            oorganizationVoList: addOorganizationTable
                        }),
                        beforeSend: function () {
                            progressLoad();
                        },
                        success: function (msg) {
                            info(msg.resInfo);
                            if (msg.resCode == '1') {
                                $('#index_tabs').tabs('close', "添加机构");
                                organization_List.datagrid('reload');
                            }
                        },
                        complete: function (XMLHttpRequest, textStatus) {
                            progressClose();
                        }
                    });
                }
            });
        } else {
            info("请输入必填项")
        }


    }
</script>
