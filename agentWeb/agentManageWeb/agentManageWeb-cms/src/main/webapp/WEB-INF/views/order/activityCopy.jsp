<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function () {
        $('#activityCopyForm').form({
            url: '${path}/activity/activityCopy',
            onSubmit: function () {
                var beginTimeStr = $("#beginTimeStr").datebox("getValue");
                var endTimeStr = $("#endTimeStr").datebox("getValue");
                if (beginTimeStr > endTimeStr) {
                    info("开始时间不能大于结束时间！")
                    return false;
                }
                var isValid = $(this).form('validate');
                if (!isValid) {
                }
                return isValid;
            },
            success: function (result) {
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.messager.alert('提示', result.msg, 'info');
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });

    $(function () {
        $("#productSelect").change(function () {
            var value = $("#productSelect").find("option:selected").attr("value");
            if (undefined == value) {
                $("#proTypeEdit").val("");
                $("#proTypeNameEdit").val("");
            }
            var proType = $("#productSelect").find("option:selected").attr("proTypeEdit");
            var proTypeName = $("#productSelect").find("option:selected").attr("proTypeNameEdit");
            $("#proTypeEdit").val(proType);
            $("#proTypeNameEdit").val(proTypeName);
        });

        $("#busProCodeEdit").after("<input id='busProNameEdit' type='hidden' name='busProName' value='"+ $("#busProCodeEdit").find("option:selected").text()+"'>");
        $("#termBatchcodeEdit").after("<input id='termBatchnameEdit' type='hidden' name='termBatchname' value='"+$('#termBatchcodeEdit option:selected').text()+"'>");
        $("#termtypeEdit").after("<input id='termtypenameEdit' type='hidden' name='termtypename' value='"+$('#termtypeEdit option:selected').text()+"'>");

        $("#busProCodeEdit").change(function () {
            var  busProNameEdit = $("#busProCodeEdit").find("option:selected").text();
            $("#busProNameEdit").val(busProNameEdit);


            //检查是否有活动日期，机型，达标周期等字段
            var option_item =  $("#busProCodeEdit").find("option:selected");
            if(option_item.length>0){

                var model =  $(option_item).attr("model");
                var standTime =   $(option_item).attr("standTime");
                var activityStartTime =   $(option_item).attr("activityStartTime");
                var activityEndTime =   $(option_item).attr("activityEndTime");
                var price =   $(option_item).attr("price");
                var manufactor =   $(option_item).attr("manufactor");
                var posType =   $(option_item).attr("posType");
                if(null!=model && model!=undefined && "null"!=model && ''!=model ){
                    $("#proModel").combobox('setValue',model);
                }
                if(null!=standTime && standTime!=undefined && "null"!=standTime && ''!=standTime){
                    $("#standTime").numberbox('setValue',standTime);
                }
                if(null!=activityStartTime && activityStartTime!=undefined && "null"!=activityStartTime && ''!=activityStartTime){
                    $('#beginTimeStr').datebox('setValue', activityStartTime);
                }
                if(null!=activityEndTime && activityEndTime!=undefined && "null"!=activityEndTime  && ''!=activityEndTime){
                    $('#endTimeStr').datebox('setValue', activityEndTime);
                }
                if(null!=price && price!=undefined && "null"!=price && ''!=price){
                    $("#originalPrice").numberbox('setValue',price);
                    $("#price").numberbox('setValue',price);
                    $("#posSpePrice").numberbox('setValue',price);
                }
                if(null!=manufactor && manufactor!=undefined && "null"!=manufactor && ''!=manufactor){
                    $("#vender").combobox('setValue',manufactor);
                }
                if(null!=posType && posType!=undefined && "null"!=posType  && ''!=posType){
                    $("#posType").val(posType);
                }

            }


        });

        $("#termBatchcodeEdit").change(function () {
            var  termBatchnameEdit = $("#termBatchcodeEdit").find("option:selected").text();
            document.getElementById('termBatchnameEdit').value=termBatchnameEdit;
        });

        $("#termtypeEdit").change(function () {
            var  termtypenameEdit = $("#termtypeEdit").find("option:selected").text();
            document.getElementById('termtypenameEdit').value=termtypenameEdit;
        })


    })
    function querybusProCode(platform) {
        var platformValue = $(platform).find("option:selected").val();
        $.ajaxL({
            type: "GET",
            url: "/activity/queryTermMachine",
            dataType:'json',
            data: {platformNum:platformValue},
            beforeSend:function(){
                progressLoad();
            },
            success: function(data){
                $("#busProCodeEdit").empty();
                $("#termBatchcodeEdit").empty();
                $("#termtypeEdit").empty();
                $("#termtypenameEdit").remove();
                $("#busProNameEdit").remove();
                $("#termBatchnameEdit").remove();
                $("#busProCodeEdit").append("<option value=''  >请选择</option>");
                for (var i = 0; i < data.termMachineList.length; i++) {
                    $("#busProCodeEdit").append("<option value='"+ data.termMachineList[i].id+"' model='"+ data.termMachineList[i].model+"' standTime='"+ data.termMachineList[i].standTime+"' activityStartTime='"+ data.termMachineList[i].activityStartTime+"'  activityEndTime='"+ data.termMachineList[i].activityEndTime+"' price='"+ data.termMachineList[i].price+"' manufactor='"+data.termMachineList[i].manufactor+"' posType='"+data.termMachineList[i].posType+"' >" +  data.termMachineList[i].mechineName + "</option>");
                }
                $("#busProCodeEdit").after("<input id='busProNameEdit' type='hidden' name='busProName' value=''>");
                for (var i = 0; i < data.mposTermBatchList.length; i++) {
                    $("#termBatchcodeEdit").append("<option value='"+ data.mposTermBatchList[i].batchId+"'>" +  data.mposTermBatchList[i].batchName + "</option>");
                }
                $("#termBatchcodeEdit").after("<input id='termBatchnameEdit' type='hidden' name='termBatchname' value='"+$('#termBatchcodeEdit option:selected').text()+"'>");
                for (var i = 0; i < data.mposTermTypeList.length; i++) {
                    $("#termtypeEdit").append("<option value='"+ data.mposTermTypeList[i].termTypeId+"'>" +  data.mposTermTypeList[i].termTypeName + "</option>");
                }
                $("#termtypeEdit").after("<input id='termtypenameEdit' type='hidden' name='termtypename' value='"+$('#termtypeEdit option:selected').text()+"'>");
            },
            complete:function (XMLHttpRequest, textStatus) {
                progressClose();
            }
        });
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:true">
    <div data-options="region:'center',border:false" style="padding: 3px;">
        <form id="activityCopyForm" method="post">
            <input type="hidden" name="id" value="${toActivityCopy.id}">
            <table class="grid">
                <tr>
                    <td>活动代码：</td>
                    <td ><input name="actCode" maxlength="15" type="text" placeholder="请输入" value="${oActivity.actCode}"
                                class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
                    <td>原价：</td>
                    <td ><input name="originalPrice" id="originalPrice" maxlength="15" type="text" placeholder="请输入" value="${oActivity.originalPrice}"
                                class="easyui-numberbox" data-options="required:true" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>活动名称：</td>
                    <td><input name="activityName" value="${oActivity.activityName}" maxlength="15" type="text"
                               placeholder="请输入" class="easyui-validatebox" data-options="required:true"
                               style="width:160px;"></td>
                    <td>商品名称：</td>
                    <td>
                        <select name="productId" data-options="width:165,height:29,editable:false,panelHeight:'auto'"
                                id="productSelect" class="easyui-combobox">
                            <c:forEach items="${productList}" var="product">
                                <option value="${product.id}" venderEdit="${product.proCom}" name="${product.name}"
                                        proModelEdit="${product.proModel}" proTypeEdit="${product.proType}"
                                        proTypeNameEdit="${product.proTypeName}"
                                        <c:if test="${oActivity.productId==product.id}">selected="selected"</c:if>>${product.proName}/${product.proPrice}元</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>厂家：</td>
                    <td>
                        <select name="vender" style="width: 165px" id="vender" class="easyui-combobox">
                            <c:forEach items="${manufacturer}" var="manufacturerItem">
                                <option value="${manufacturerItem.dItemvalue}" <c:if test="${oActivity.vender==manufacturerItem.dItemvalue}">selected="selected"</c:if>
                                >${manufacturerItem.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>型号：</td>
                    <td>
                        <select name="proModel" style="width: 165px" id="proModel" class="easyui-combobox">
                            <c:forEach items="${proMode}" var="proModeItem">
                                <option value="${proModeItem.dItemvalue}" <c:if test="${oActivity.proModel==proModeItem.dItemvalue}">selected="selected"</c:if>
                                >${proModeItem.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>规则编号：</td>
                    <td><input name="ruleId" value="${oActivity.ruleId}" maxlength="15" type="text" placeholder="请输入"
                               class="easyui-validatebox" style="width:160px;"></td>
                    <td>机具类型：</td>
                    <td>
                        <input name="proType" maxlength="15" type="hidden" placeholder="请输入" class="easyui-validatebox"
                               style="width:160px;" id="proTypeEdit" readonly="readonly" value="${oActivity.proType}">
                        <input name="proTypeName" maxlength="15" type="text" placeholder="请输入"
                               class="easyui-validatebox"
                               style="width:160px;" id="proTypeNameEdit" readonly="readonly"
                               value="${oActivity.proTypeName}">
                    </td>
                </tr>
                <tr>
                    <td>优惠条件：</td>
                    <td>
                        <select class="easyui-combobox" name="activityWay" style="width:160px;height:21px">
                            <c:forEach items="${activityDisType}" var="activityDisItem">
                                <option value="${activityDisItem.dItemvalue}"
                                        <c:if test="${oActivity.activityWay==activityDisItem.dItemvalue}">selected="selected"</c:if>>${activityDisItem.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>参与条件：</td>
                    <td>
                        <select class="easyui-combobox" name="activityCondition" style="width:160px;height:21px">
                            <c:forEach items="${activityCondition}" var="activityConItem">
                                <option value="${activityConItem.dItemvalue}" <c:if test="${oActivity.activityCondition==activityConItem.dItemvalue}">selected="selected"</c:if>>${activityConItem.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>优惠方式：</td>
                    <td><input name="activityRule" value="${oActivity.activityRule}" maxlength="15" type="text"
                               placeholder="请输入" class="easyui-validatebox" style="width:160px;"
                               data-options="required:true"></td>
                    <td>价格：</td>
                    <td><input name="price" value="${oActivity.price}" id="price"  maxlength="15" type="text" placeholder="请输入"
                               class="easyui-numberbox" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>开始时间</td>
                    <td><input name="beginTimeStr" id="beginTimeStr"
                               value="<fmt:formatDate pattern='yyyy-MM-dd' value='${oActivity.beginTime}' />"
                               type="text" class="easyui-datebox" editable="false" placeholder="请输入"
                               style="width:160px;" data-options="required:true"></td>
                    <td>结束时间</td>
                    <td><input name="endTimeStr" id="endTimeStr"
                               value="<fmt:formatDate pattern='yyyy-MM-dd' value='${oActivity.endTime}' />" type="text"
                               class="easyui-datebox" editable="false" placeholder="请输入"
                               style="width:160px;" data-options="required:true"></td>
                </tr>
                <tr>
                    <td>平台类型:</td>
                    <td>
                        <select name="platform" style="width:160px;height:21px" onchange="querybusProCode(this)">
                            <c:forEach items="${ablePlatForm}" var="ablePlatFormItem">
                                <option value="${ablePlatFormItem.platformNum}"
                                        <c:if test="${ablePlatFormItem.platformNum==oActivity.platform}">selected="selected"</c:if>>${ablePlatFormItem.platformName}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>保价时间：</td>
                    <td>
                        <input name="gTime" value="${oActivity.gTime}" maxlength="15" type="text" placeholder="请输入"
                               class="easyui-numberbox" style="width:150px;">天
                    </td>
                </tr>
                <tr >
                    <td>平台机具</td>
                    <td >
                        <select  name="busProCode" style="width:160px;height:21px" id="busProCodeEdit">
                            <c:forEach items="${termMachineList}" var="termMachineItem">
                                <option value="${termMachineItem.id}"
                                        model="${termMachineItem.model}"
                                        standTime="${termMachineItem.standTime}"
                                        activityStartTime="${termMachineItem.activityStartTime}"
                                        activityEndTime="${termMachineItem.activityEndTime}"
                                        price="${termMachineItem.price}"
                                        manufactor="${termMachineItem.manufactor}"
                                        posType="${termMachineItem.posType}"
                                        <c:if test="${termMachineItem.id==oActivity.busProCode}"
                                        >selected="selected"</c:if>>${termMachineItem.mechineName}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>终端批次</td>
                    <td >
                        <select  name="termBatchcode" style="width:160px;height:21px" id="termBatchcodeEdit">
                            <c:forEach items="${mposTermBatchList}" var="mposTermBatchItem">
                                <option value="${mposTermBatchItem.batchId}"
                                        <c:if test="${mposTermBatchItem.batchId==oActivity.termBatchcode}">selected="selected"</c:if>>${mposTermBatchItem.batchName}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>终端类型</td>
                    <td >
                        <select  name="termtype" style="width:160px;height:21px" id="termtypeEdit">
                            <c:forEach items="${mposTermTypeList}" var="mposTermTypeItem">
                                <option value="${mposTermTypeItem.termTypeId}"
                                        <c:if test="${mposTermTypeItem.termTypeId==oActivity.termtype}">selected="selected"</c:if>>${mposTermTypeItem.termTypeName}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>pos类型</td>
                    <td>
                        <select  name="posType" id="posType" style="width:160px;height:21px" >
                            <c:forEach var="posType" items="${posTypeList}">
                                <option value="${posType.key}"  <c:if test="${posType.key==oActivity.posType}">selected="selected"</c:if>>${posType.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>特价机价格</td>
                    <td><input name="posSpePrice" id="posSpePrice" maxlength="15" type="text" placeholder="请输入" class="easyui-numberbox"
                               style="width:160px;" value='${oActivity.posSpePrice}' ></td>
                    </td>
                    <td>达标时间（天）</td>
                    <td><input name="standTime" id="standTime" maxlength="15" type="text" placeholder="请输入" class="easyui-numberbox"
                               style="width:160px;" value='${oActivity.standTime}' ></td>
                </tr>
            </table>
        </form>
    </div>
</div>