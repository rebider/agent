<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="收款账户"  data-options="iconCls:'fi-results',tools:'#editAgentColinfoTable_model_tools'">
    <form id="editAgentColinfoTable_model_form">

        <c:if test="${!empty agentColinfos}">
            <c:forEach items="${agentColinfos}" var="agentColinfos">
                <table class="grid">
                    <tbody>
                    <tr >
                        <td>收款账户类型<input type="hidden" name="id" value="${agentColinfos.id}"></td>
                        <td>
                            <select name="cloType" style="width:160px;height:21px" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentColinfoInfo"> disabled="true" </shiro:lacksPermission> onchange="editAgentColinfoTable_cloTypeChange(this)">
                                <c:forEach items="${colInfoType}" var="ColInfoTypeItem"  >
                                    <option value="${ColInfoTypeItem.dItemvalue}"  <c:if test="${ColInfoTypeItem.dItemvalue==agentColinfos.cloType}">selected="selected"</c:if>>${ColInfoTypeItem.dItemname}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>收款账户名</td>
                        <td><input name="cloRealname" type="text"  class="easyui-validatebox"  style="width:160px;"  data-options="required:true,validType:['length[1,100]','CP']" value="${agentColinfos.cloRealname}" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentColinfoInfo"> readonly="readonly" </shiro:lacksPermission>/></td>
                        <td>收款账号</td>
                        <td>
                            <input name="cloBankAccount" type="text"  class="easyui-validatebox"  style="width:130px;"   data-options="required:true,validType:['length[1,30]','Number']" value="${agentColinfos.cloBankAccount}" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentColinfoInfo"> readonly="readonly" </shiro:lacksPermission>>
                            <a href="javascript:void(0);" onclick="discernCardNo(this)">识别</a>
                        </td>
                        <td>收款开户总行</td>
                        <td>
                            <input name="cloBank" type="text"  class="easyui-validatebox"  style="width:160px;"  data-options="required:true,validType:['length[1,100]','CHS']" value="${agentColinfos.cloBank}" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentColinfoInfo"> readonly="readonly" </shiro:lacksPermission>>
                            <input name="cloBankCode" type="hidden" value="${agentColinfos.cloBankCode}">
                        </td>
                    </tr>
                    <tr>
                        <td>开户行地区</td>
                        <td>
                            <input type="text"  input-class="easyui-validatebox" id="bankRegion" style="width:130px;"  data-options="required:true"  readonly="readonly" value="<agent:show type="region" busId="${agentColinfos.bankRegion}"/>">
                            <input name="bankRegion" type="hidden" id="bankRegions" value="${agentColinfos.bankRegion}"/>
                            <a href="javascript:void(0);" onclick="showRegionFrame({target:this,callBack:returnBankRegion},'/region/regionTree',false)">选择</a>
                            <a href="javascript:void(0);" onclick="del(this)">清除</a>
                        </td>
                        <td>收款开户支行</td>
                        <td>
                            <input class="easyui-combobox" name="cloBankBranch" id="cloBankBranch"data-options="required:true,valueField:'id',textField:'text',panelHeight:'auto',panelMaxHeight:200,panelMinHeight:100"  style="width: 160px;height: 29px" value="${agentColinfos.cloBankBranch}"/>
                        </td>
                        <td>总行联行号</td>
                        <td><input name="allLineNum" type="text" maxlength="14" input-class="easyui-validatebox"  style="width:160px;"  data-options="validType:['length[1,32]','CHS']" value="${agentColinfos.allLineNum}" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentColinfoInfo"> readonly="readonly" </shiro:lacksPermission>></td>
                        <td>支行联行号</td>
                        <td><input name="branchLineNum" type="text"  maxlength="14" input-class="easyui-validatebox"  style="width:160px;"  data-options="required:true,validType:['length[1,32]','CHS']" value="${agentColinfos.branchLineNum}" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentColinfoInfo"> readonly="readonly" </shiro:lacksPermission>></td>
                    </tr>
                    <tr>
                        <td>税点</td>
                        <td>
                                <%-- <input name="cloTaxPoint" type="text" input-class="easyui-validatebox" style="width:80%;"
                                        data-options="required:true,validType:['length[1,11]','Money']" value="${agentColinfos.cloTaxPoint}">--%>

                            <select name="cloTaxPoint" style="width:160px;height:21px"  data-options="required:true,validType:['length[1,11]','Money']"class="cloTaxPoint">
                                <option value="${agentColinfos.cloTaxPoint}">${agentColinfos.cloTaxPoint}</option>
                            </select>
                        </td>
                        <td>是否开具分润发票</td>
                        <td>
                            <select class="cloInvoice" name="cloInvoice" style="width:160px;height:21px"  >
                            </select>
                        </td>
                        <td>备注</td>
                        <td><input name="remark" type="text"  class="easyui-validatebox"  style="width:160px;"  value="${agentColinfos.remark}" <shiro:lacksPermission name="/agentEnter/agentEdit/AgentColinfoInfo"> readonly="readonly" </shiro:lacksPermission>></td>
                        <td>是否有效</td>
                        <td>
                            <select name="status" style="width:160px;height:21px" >
                                <c:forEach items="${yesOrNoIsYes}" var="yesOrNoItem"  >
                                    <option value="${yesOrNoItem.dItemvalue}">${yesOrNoItem.dItemname}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>户主证件号</td>
                        <td><input name="agLegalCernum" id="agLegalCernum" type="text" class="easyui-validatebox" value="${agentColinfos.agLegalCernum}"
                                   style="width:160px;" data-options="validType:['length[1,18]','IdCard']">
                                <%--<shiro:lacksPermission name="/agentEnter/agentEdit/AgentColinfoInfo">readonly="readonly"</shiro:lacksPermission> />--%></td>
                    </tr>
                    <tr>
                        <td>附件</td>
                        <td colspan="3" class="attrInput" >
                            <c:if test="${!empty agentColinfos.attachmentList}">
                                <c:forEach items="${agentColinfos.attachmentList}" var="attachment">
                                    <span
                                            <shiro:hasPermission name="/agentEnter/agentEdit/AgentColinfoInfo">
                                                onclick='removeaddEditAgentColinfoTable_jxkxUploadFile(this)'
                                            </shiro:hasPermission>
                                    >${attachment.attName}<input type='hidden' name='colinfoTableFile' value='${attachment.id}' /></span>
                                </c:forEach>
                            </c:if>
                        </td>
                        <td colspan="3">
                            <shiro:hasPermission name="/agentEnter/agentEdit/AgentColinfoInfo">
                                <%--<a href="javascript:void(0)" class="editAgentColinfoTabledel-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="removeEditAgentColinfoTable_model(this)" >删除</a>||--%>
                                <a href="javascript:void(0)" class="editAgentColinfoTabledAddAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addEditAgentColinfoTable_uploadView(this,ATT_DATA_TYPE_STATIC.KHXUZ.key)" >开户许可证扫描件</a>||
                                <a href="javascript:void(0)" class="editAgentColinfoTabledAddAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addEditAgentColinfoTable_uploadView(this,ATT_DATA_TYPE_STATIC.YHKSMJ.key)" >银行卡扫描件</a>||
                                <a href="javascript:void(0)" class="editAgentColinfoTabledAddAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addEditAgentColinfoTable_uploadView(this,ATT_DATA_TYPE_STATIC.YBNSRZM.key)" >一般纳税人证明</a>||
                                <a href="javascript:void(0)" class="editAgentColinfoTabledAddAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addEditAgentColinfoTable_uploadView(this,ATT_DATA_TYPE_STATIC.ZHBGB.key)" >账户变更表</a>||
                                <a href="javascript:void(0)" class="editAgentColinfoTabledAddAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addEditAgentColinfoTable_uploadView(this,ATT_DATA_TYPE_STATIC.JSRSFZ.key)" >结算人身份证</a>||
                                <a href="javascript:void(0)" class="editAgentColinfoTabledAddAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addEditAgentColinfoTable_uploadView(this,ATT_DATA_TYPE_STATIC.FFRSQS.key)" >非法人授权书</a>
                            </shiro:hasPermission>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </c:forEach>
        </c:if>
    </form>
</div>
<%--<shiro:hasPermission name="/agentEnter/agentEdit/AgentColinfoInfo">--%>
<%--<div id="editAgentColinfoTable_model_tools">--%>
<%--<a href="javascript:void(0)" class="icon-add" style="margin-right: 50px;" onclick="addEditAgentColinfoTable_model()"></a>--%>
<%--</div>--%>
<%--</shiro:hasPermission>--%>
<div style="display: none;" id="editAgentColinfoTable_model_templet">
    <table class="grid">
        <tbody>
        <tr >
            <td>收款账户类型</td>
            <td>
                <select name="cloType" style="width:160px;height:21px"  onchange="editAgentColinfoTable_cloTypeChange(this)">
                    <c:forEach items="${colInfoType}" var="ColInfoTypeItem"  >
                        <option value="${ColInfoTypeItem.dItemvalue}">${ColInfoTypeItem.dItemname}</option>
                    </c:forEach>
                </select>
            </td>
            <td>收款账户名</td>
            <td><input name="cloRealname" type="text"  input-class="easyui-validatebox"  style="width:160px;"   data-options="required:true,validType:['length[1,100]','CP']" /></td>
            <td>收款账号</td>
            <td>
                <input name="cloBankAccount" type="text"  input-class="easyui-validatebox"  style="width:160px;"  data-options="required:true,validType:['length[1,20]','Number']">
                <a href="javascript:void(0);" onclick="discernCardNo(this)">识别</a>
            </td>
            <td>收款开户总行</td>
            <td>
                <input name="cloBank" type="text"  input-class="easyui-validatebox"  style="width:160px;"  data-options="required:true,validType:['length[1,100]','CHS']">
                <input name="cloBankCode" type="hidden" value="">
            </td>
        </tr>
        <tr>
            <td>开户行地区</td>
            <td>
                <input type="text"  input-class="easyui-validatebox"  style="width:100px;"  data-options="required:true"  readonly="readonly" >
                <input name="bankRegion" type="hidden"  />
                <a href="javascript:void(0);" onclick="showRegionFrame({target:this,callBack:returnBankRegion},'/region/regionTree',false)">选择</a>
                <a href="javascript:void(0);" onclick="del(this)">清除</a>
            </td>
            <td>收款开户支行</td>
            <td>
                <input class="easyui-combobox" name="cloBankBranch" id="cloBankBranch"data-options="required:true,valueField:'id',textField:'text',panelHeight:'auto',panelMaxHeight:200,panelMinHeight:100"  style="width: 160px;height: 29px" value=""/>
                <%--<input name="cloBankBranch" type="text"  input-class="easyui-validatebox"  style="width:150px;"  data-options="required:true,validType:['length[1,16]','CHS']">--%>
            </td>
            <td>总行联行号</td>
            <td><input name="allLineNum" type="text" maxlength="14" input-class="easyui-validatebox"  style="width:160px;"  data-options="validType:['length[1,32]','CHS']" ></td>
            <td>支行联行号</td>
            <td><input name="branchLineNum" type="text"  maxlength="14" input-class="easyui-validatebox"  style="width:160px;"  data-options="required:true,validType:['length[1,32]','CHS']" ></td>
        </tr>
        <tr>
            <td>税点</td>
            <td>

                <select name="cloTaxPoint" style="width:160px;height:21px"  data-options="required:true,validType:['length[1,11]','Money']"class="cloTaxPoint">
                    <c:forEach items="${agentColinfos}" var="agentColinfos"  >
                        <option value="${agentColinfos.cloTaxPoint}">${agentColinfos.cloTaxPoint}</option>
                    </c:forEach>
                </select>
            <td>
            <td>是否开具分润发票</td>
            <td>
                <select name="cloInvoice" class="cloInvoice" style="width:160px;height:21px" >
                </select>
            </td>
            <td>备注</td>
            <td><input name="remark" type="text"  input-class="easyui-validatebox"  style="width:160px;" data-options="validType:['length[1,66]','CHS']"></td>
            <td>是否有效</td>
            <td>
                <select name="status" style="width:160px;height:21px" >
                    <c:forEach items="${yesOrNoIsYes}" var="yesOrNoItem"  >
                        <option value="${yesOrNoItem.dItemvalue}">${yesOrNoItem.dItemname}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>户主证件号</td>
            <td><input name="agLegalCernum" type="text" class="easyui-validatebox" value=""
                       style="width:160px;" data-options="validType:['length[1,18]','IdCard']"></td>
        </tr>
        <tr>
            <td>附件</td>
            <td colspan="2" class="attrInput" ></td>
            <td>
                <a href="javascript:void(0)" class="editAgentColinfoTabledAddAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addEditAgentColinfoTable_uploadView(this,ATT_DATA_TYPE_STATIC.KHXUZ.key)" >开户许可证扫描件</a>
                <a href="javascript:void(0)" class="editAgentColinfoTabledAddAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addEditAgentColinfoTable_uploadView(this,ATT_DATA_TYPE_STATIC.YHKSMJ.key)" >银行卡扫描件</a>
                <a href="javascript:void(0)" class="editAgentColinfoTabledAddAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addEditAgentColinfoTable_uploadView(this,ATT_DATA_TYPE_STATIC.YBNSRZM.key)" >一般纳税人证明</a>
                <a href="javascript:void(0)" class="editAgentColinfoTabledAddAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addEditAgentColinfoTable_uploadView(this,ATT_DATA_TYPE_STATIC.ZHBGB.key)" >账户变更表</a>
                <a href="javascript:void(0)" class="editAgentColinfoTabledAddAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addEditAgentColinfoTable_uploadView(this,ATT_DATA_TYPE_STATIC.JSRSFZ.key)" >结算人身份证</a>
                <a href="javascript:void(0)" class="editAgentColinfoTabledAddAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addEditAgentColinfoTable_uploadView(this,ATT_DATA_TYPE_STATIC.FFRSQS.key)" >非法人授权书</a>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<script>
    var cloBankId = "";
    $(function () {
        //针对老数据没有账户的情况
        var table_length = $('#editAgentColinfoTable_model_form').find('table').length;
        console.info(table_length);
        if (table_length == 0){
            var strHtml = $('#editAgentColinfoTable_model_templet').html();
            $('#editAgentColinfoTable_model_form').html(strHtml);
        }
//        cloBankId = result.issUsers;
        editAgentColinfoTable_colchange();
    });
    function addEditAgentColinfoTable_model(){
        $("#editAgentColinfoTable_model_form").append($("#editAgentColinfoTable_model_templet").html());
        var inputs = $("#editAgentColinfoTable_model_form .grid:last input");
        for(var i=0;i<inputs.length;i++){
            if($(inputs[i]).attr("input-class") && $(inputs[i]).attr("input-class").length>0)
                $(inputs[i]).addClass($(inputs[i]).attr("input-class"));
        }
        $.parser.parse($("#editAgentColinfoTable_model_form .grid:last"));
        editAgentColinfoTable_colchange();
    }

    function removeEditAgentColinfoTable_model(t){
        $(t).parent().parent().parent().parent().remove();
    }


    var addEditAgentColinfoTable_attrDom ;


    //上传窗口
    function addEditAgentColinfoTable_uploadView(t,attDataType){
        addEditAgentColinfoTable_attrDom = $(t).parent().parent().find(".attrInput");
        multFileUpload(addEditAgentColinfoTable_jxkxUploadFile,attDataType);
    }

    //附件解析
    function addEditAgentColinfoTable_jxkxUploadFile(data) {
        var jsondata = eval(data);
        for(var i=0;i<jsondata.length ;i++){
            $(addEditAgentColinfoTable_attrDom).append("<span onclick='removeaddEditAgentColinfoTable_jxkxUploadFile(this)'>"+jsondata[i].attName+"<input type='hidden' name='colinfoTableFile' value='"+jsondata[i].id+"' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }

    }
    function removeaddEditAgentColinfoTable_jxkxUploadFile(t){
        parent.$.messager.confirm('询问', '确定删除附件么？', function(b) {
            if (b) {
                $(t).remove();
            }
        });
    }

    //解析打个table
    function get_editAgentColinfoTable_FormDataItem(table){
        var data = {};
        data.id =$(table).find("input[name='id']").length>0 ? $(table).find("input[name='id']").val() : "";
        data.cloType = $(table).find("select[name='cloType']").val();
        data.cloRealname = $(table).find("input[name='cloRealname']").val();
        data.cloBank = $(table).find("input[name='cloBank']").val();
        data.cloBankBranch = $(table).find("input[name='cloBankBranch']").val();
        var branchInput = $(table).find("input[name='cloBankBranch']").val();
        if(branchInput=='' || branchInput==undefined){
            data.cloBankBranch = $(table).find("select[name='cloBankBranch']").val();
        }
        data.cloBankAccount = $(table).find("input[name='cloBankAccount']").val();
        data.remark = $(table).find("input[name='remark']").val();
        data.status = $(table).find("select[name='status']").val();
        data.allLineNum = $(table).find("input[name='allLineNum']").val();
        data.branchLineNum = $(table).find("input[name='branchLineNum']").val();
        data.bankRegion = $(table).find("input[name='bankRegion']").val();
        data.cloTaxPoint = $(table).find("select[name='cloTaxPoint']").val();
        data.cloInvoice = $(table).find("select[name='cloInvoice']").val();
        data.cloBankCode = $(table).find("input[name='cloBankCode']").val();
        data.agLegalCernum = $(table).find("input[name='agLegalCernum']").val();
        var files =  $(table).find(".attrInput").find("input[name='colinfoTableFile']");
        var colinfoTableFileTemp = [];
        for(var j=0;j<files.length;j++){
            colinfoTableFileTemp.push($(files[j]).val());
        }
        if(colinfoTableFileTemp.length>0)
            data.colinfoTableFile=colinfoTableFileTemp;
        return data;
    }
    //获取form数据
    function get_editAgentColinfoTable_FormData(){
        var editAgentColinfoTable_FormDataJson = [];
        var tables = $("#editAgentColinfoTable_model_form .grid");
        for (var  i=0;i<tables.length;i++){
            var table = tables[i];
            editAgentColinfoTable_FormDataJson.push(get_editAgentColinfoTable_FormDataItem(table));
        }
        return editAgentColinfoTable_FormDataJson;
    }

    function discernCardNo(obj) {
        var bankCardNo =$(obj).parent("td").find("input").val();
        if(bankCardNo=='' || bankCardNo==undefined){
            info("卡号不可为空！");
            return false;
        }
//        if(bankCardNo.length<10){
//            info("卡号长度有误！");
//            return false;
//        }
        $.ajax({
            url :"${path}/etbSysCard/queryCardNo",
            type:'POST',
            data:{
                bankCardNo:bankCardNo
            },
            dataType:'json',
            success:function(result){
                if(result.msg!='' && result.msg!=undefined){
                    info(result.msg);
                    return false;
                }
                cloBankId = result.issUsers;
                $(obj).parent().parent().parent().find("input[name='cloBank']").val(result.bankName);
                $(obj).parent().parent().parent().find("input[name='cloBankCode']").val(result.issUsers);
                $(obj).parent().parent().parent().find("input[name='cloBank']").removeClass("validatebox-invalid");
            },
            error:function(data){
                info("系统异常，请联系管理员！");
            }
        });
    }

    //地区选择
    function returnBankRegion(data,options){
        $(options.target).prev("input").val(data.id);
        $(options.target).prev("input").prev("input").val(data.text);
        if(cloBankId!='' && data.id!=''){
            $('#cloBankBranch').combobox({
                prompt:'输入首关键字自动检索',
                required:false,
                url:'/etbSysCard/queryLineNumList?cloBankId='+cloBankId+"&regionId="+data.id,
                editable:true,
                hasDownArrow:true,
                filter: function(q, row){
                    var opts = $(this).combobox('options');
                    return row[opts.textField].indexOf(q) == 0;
                },
                onChange: function (n,o) {
                    var data = $(this).combobox("getData");
                    var bankBranchId = "";
                    var liqBankId = "";
                    for(var i=0;i<data.length;i++){
                        if(data[i].text==n){
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

    /**
     * 收款账户类型变更
     * 1、如果由开票变更成不开票，需要判断是否系统中有欠票，如果有欠票，不能进行修改
     2、如果前面是对私户进行打款，那么是否开票默认为否且不可修改
     3、如果代理商前面是对私户进行打款，那么扣税点在代理商填写时默认为6%且不可修改
     4、如果代理商前面是对公户进行打款，且代理商是否开票为否，那么扣税点在代理商填写时默认为6%，且不可修改
     5、如果代理商前面是对公户进行打款，且代理商是否开票为是，那么扣税点在代理商填写时只能选择6%或3%
     */
    function editAgentColinfoTable_cloTypeChange(t) {
        var cloType = $(t).val();
        var cloInvoice = $(t).parent().parent().parent().find("select[name='cloInvoice']").val();
        var cloTaxPoint = $(t).parent().parent().parent().find("select[name='cloTaxPoint']").val();
        $(".cloTaxPoint").removeAttr("disabled");
        $("#editAgentColinfoTable_model_form").find("input[name='agLegalCernum']").removeAttr("disabled");
        $("#editAgentColinfoTable_model_templet").find("input[name='agLegalCernum']").removeAttr("disabled");
        if (cloType==2) {//对私
            $(".cloTaxPoint").empty();
            $(".cloInvoice").empty();
            $(".cloTaxPoint").append("<option value='0.08'>0.08</option>");
            $(".cloInvoice").append("<option value='0'>否</option>");
            $(".cloInvoice").attr("disabled","disabled");
            $(".cloTaxPoint").attr("disabled","disabled");
        } else if (cloType==1) {//对公
            $(".cloTaxPoint").empty();
            $(".cloInvoice").empty();
            if(cloTaxPoint=='0.03'){
                $(".cloTaxPoint").append("<option value='0.03' selected='selected'>0.03</option>");
                $(".cloTaxPoint").append("<option value='0.06' >0.06</option>");
            }
            else if(cloTaxPoint=='0.06'){
                $(".cloTaxPoint").append("<option value='0.03' >0.03</option>");
                $(".cloTaxPoint").append("<option value='0.06' selected='selected'>0.06</option>");
            }else{
                $(".cloTaxPoint").append("<option value='0.03'>0.03</option><option value='0.06'>0.06</option>");
            }
            $(".cloInvoice").append("<option value='1'>是</option>");
            $(".cloInvoice").attr("disabled", "disabled");
            $("#editAgentColinfoTable_model_form").find("input[name='agLegalCernum']").val("");
            $("#editAgentColinfoTable_model_form").find("input[name='agLegalCernum']").attr("disabled", "disabled");
            $("#editAgentColinfoTable_model_templet").find("input[name='agLegalCernum']").val("");
            $("#editAgentColinfoTable_model_templet").find("input[name='agLegalCernum']").attr("disabled", "disabled");
        }
    }

    function editAgentColinfoTable_colchange() {
        $.each($("select[name='cloType']"),function(i,item){
            $(item).change();
        });
    }

    function accountVerifyIntegrity(){
        var formData = get_editAgentColinfoTable_FormData()[0];

        var table = $('#editAgentColinfoTable_model_form .grid');

        if (formData.cloType == '2') {  //对私时验证户主证件号
            if (formData.agLegalCernum == ''){
                if (confirm("请填写户主证件号!")) {
                    table.find('[name=agLegalCernum]').focus();
                }
                else {
                    table.find('[name=agLegalCernum]').focus();
                }
                return false;
            }
        }
        if (formData.cloBank == ''){
            if (confirm("请填写收款开户总行!")) {
                table.find('[name=cloBank]').focus();
            }
            else {
                table.find('[name=cloBank]').focus();
            }
            return false;
        }

        if (formData.cloRealname == ''){
            if (confirm("请填写收款账户名!")) {
                table.find('[name=cloRealname]').focus();
            }
            else {
                table.find('[name=cloRealname]').focus();
            }
            return false;
        }

        if (formData.cloBankAccount == ''){
            if (confirm("请填写收款账号!")) {
                table.find('[name=cloBankAccount]').focus();
            }
            else {
                table.find('[name=cloBankAccount]').focus();
            }
            return false;
        }

        if (formData.allLineNum == ''){
            if (confirm("请填写总行联行号!")) {
                table.find('[name=allLineNum]').focus();
            }
            else {
                table.find('[name=allLineNum]').focus();
            }
            return false;
        }

        if (formData.branchLineNum == ''){
            if (confirm("请填写支行联行号!")) {
                table.find('[name=branchLineNum]').focus();
            }
            else {
                table.find('[name=branchLineNum]').focus();
            }
            return false;
        }

        if (formData.cloBankBranch == ''){
            if (confirm("请填写收款开户支行!")) {
                table.find('[name=cloBankBranch]').focus();
            }
            else {
                table.find('[name=cloBankBranch]').focus();
            }
            return false;
        }

        if (formData.bankRegion == ''){
            confirm("请选择开户行地区!");
            return false;
        }



        return true;
    }
</script>
