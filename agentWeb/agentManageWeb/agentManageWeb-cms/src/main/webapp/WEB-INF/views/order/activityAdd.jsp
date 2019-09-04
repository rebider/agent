<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $('#activityAddForm').form({
        url: '${path}/activity/activityAdd',
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
            eval("var data = " + result + ";");
            info(data.resInfo);
           if(data.resCode=='1'){
               parent.$.modalDialog.handler.dialog('close');
               activityList.datagrid('reload');
           }
        },

    });
    $(function () {
        $("#testSelect").change(function () {
            var value = $("#testSelect").find("option:selected").attr("value");
            if (undefined == value) {
                $("#proType").val("");
                $("#proTypeName").val("");
            }
            var proType = $("#testSelect").find("option:selected").attr("proType");
            var proTypeName = $("#testSelect").find("option:selected").attr("proTypeName");
            $("#proType").val(proType);
            $("#proTypeName").val(proTypeName);

        });


        /*$("#busProCode").change(function () {
          var  busProName = $("#busProCode").find("option:selected").text();
            document.getElementById('busProName').value=busProName;
        });*/




        $("#termBatchcode").change(function () {
            var  termBatchname = $("#termBatchcode").find("option:selected").text();
            document.getElementById('termBatchname').value=termBatchname;
        });

        $("#termtype").change(function () {
            var  termtypename = $("#termtype").find("option:selected").text();
            document.getElementById('termtypename').value=termtypename;
        });

        $("#platform").combobox({
            onSelect: function(rec){
                queryList(rec.value);
            },
            filter: function(q, row){
                var opts = $(this).combobox('options');
                return row[opts.textField].indexOf(q) >= 0;
            }
        });
    });
    function queryList(platform) {
        var platformValue = platform;
        var flag=true;
        if(" "==platformValue){
            $("#busProCode").empty();
            $("#termBatchcode").empty();
            $("#termtype").empty();
            $("#termtypename").remove();
            $("#busProName").remove();
            $("#termBatchname").remove();
            return false;
        }
        if(flag){

            $.ajaxL({
                type: "GET",
                url: "/activity/queryTermMachine",
                dataType:'json',
                data: {platformNum:platformValue},
                beforeSend:function(){
                    progressLoad();
                },
                success: function(data){
                    $("#busProCode").empty();
                    $("#termBatchcode").empty();
                    $("#termtype").empty();
                    $("#termtypename").remove();
                    $("#busProName").remove();
                    $("#termBatchname").remove();

                    $("#busProCode").append("<option value=''  >请选择</option>");
                    for (var i = 0; i < data.termMachineList.length; i++) {
                        $("#busProCode").append("<option value='"+ data.termMachineList[i].id+"' model='"+ data.termMachineList[i].model+"' standTime='"+ data.termMachineList[i].standTime+"' activityStartTime='"+ data.termMachineList[i].activityStartTime+"'  activityEndTime='"+ data.termMachineList[i].activityEndTime+"' price='"+ data.termMachineList[i].price+"'  manufactor='"+data.termMachineList[i].manufactor+"'  posType='"+data.termMachineList[i].posType+"'  >" +  data.termMachineList[i].mechineName + "</option>");
                    }
                    $("#busProCode").combobox({
                        onSelect:function (rec) {
//                            console.info(rec);
                            var busProName =$("#busProCode").combobox('getText');
                            //console.info(busProName);
                            if (busProName!=null && busProName != undefined){
                                document.getElementById('busProName').value=busProName;
                            }
                            //检查是否有活动日期，机型，达标周期等字段
                            var value = $("#busProCode").combobox('getValue');
                            var option_item = $("select[comboname='busProCode'] option[value='"+value+"']");
                            if(option_item.length>0){
                                var model =  $(option_item).attr("model");
                                var standTime =   $(option_item).attr("standTime");
                                var activityStartTime =   $(option_item).attr("activityStartTime");
                                var activityEndTime =   $(option_item).attr("activityEndTime");
                                var price =   $(option_item).attr("price");
                                var manufactor =   $(option_item).attr("manufactor");
                                var posType =   $(option_item).attr("posType");

                                if(null!=model && model!=undefined && "null"!=model && ""!=model ){
                                   $("#proModel").combobox('setValue',model);
                                }
                                if(null!=standTime && standTime!=undefined && "null"!=standTime && ""!=standTime ){
                                    $("#standTime").numberbox('setValue',standTime);
                                }
                                if(null!=activityStartTime && activityStartTime!=undefined && "null"!=activityStartTime  && ""!=activityStartTime ){
                                    $('#beginTimeStr').datebox('setValue', activityStartTime);
                                }
                                if(null!=activityEndTime && activityEndTime!=undefined && "null"!=activityEndTime && ""!=activityEndTime){
                                    $('#endTimeStr').datebox('setValue', activityEndTime);
                                }
                                if(null!=price && price!=undefined && "null"!=price && ""!=price){
                                    $("#originalPrice").numberbox('setValue',price);
                                    $("#price").numberbox('setValue',price);
                                    $("#posSpePrice").numberbox('setValue',price);
                                }
                                if(null!=manufactor && manufactor!=undefined && "null"!=manufactor  && ""!=manufactor){
                                    $("#vender").combobox('setValue',manufactor);
                                }
                                if(null!=posType && posType!=undefined && "null"!=posType  && ""!=posType){
                                    $("#posType").val(posType);
                                }

                            }


                        },
                        filter: function(q, row){
                            var opts = $(this).combobox('options');
                            return row[opts.textField].indexOf(q) >= 0;
                        }
                    });
                    $("#busProCode").after("<input id='busProName' type='hidden' name='busProName' value=''>");
                    for (var i = 0; i < data.mposTermBatchList.length; i++) {
                        $("#termBatchcode").append("<option value='"+ data.mposTermBatchList[i].batchId+"'>" +  data.mposTermBatchList[i].batchName + "</option>");
                    }

                    $("#termBatchcode").after("<input id='termBatchname' type='hidden' name='termBatchname' value='"+$('#termBatchcode option:selected').text()+"'>");

                    for (var i = 0; i < data.mposTermTypeList.length; i++) {
                        $("#termtype").append("<option value='"+ data.mposTermTypeList[i].termTypeId+"'>" +  data.mposTermTypeList[i].termTypeName + "</option>");
                    }

                    $("#termtype").after("<input id='termtypename' type='hidden' name='termtypename' value='"+$('#termtype option:selected').text()+"'>");


                },
                complete:function (XMLHttpRequest, textStatus) {
                    progressClose();
                },
                error:function () {
                    progressClose();
                }
            });



        }
     /*  */
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:true">
    <div data-options="region:'center',border:false" style="padding: 3px;">
        <form id="activityAddForm" method="post">
            <table class="grid">
                <tr>
                    <td>活动代码：</td>
                    <td ><input name="actCode" maxlength="15" type="text" placeholder="请输入"
                               class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
                    <td>原价：</td>
                    <td ><input name="originalPrice" id="originalPrice"  maxlength="15" type="text" placeholder="请输入"
                                           class="easyui-numberbox" data-options="required:true" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>活动名称：</td>
                    <td><input name="activityName" maxlength="15" type="text" placeholder="请输入"
                               class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
                    <td>商品名称：</td>
                    <td>
                        <select name="productId" style="width: 165px" id="testSelect" class="easyui-combobox">
                            <c:forEach items="${productList}" var="product">
                                <option value="${product.id}" proModel="${product.proModel}"
                                        vender="${product.proCom}" name="${product.name}" proType="${product.proType}" proTypeName="${product.proTypeName}">${product.proName}/${product.proPrice}元</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>厂家：</td>
                    <td>
                        <select name="vender" style="width: 165px" id="vender" class="easyui-combobox">
                            <c:forEach items="${manufacturer}" var="manufacturerItem">
                                <option value="${manufacturerItem.dItemvalue}"
                                           >${manufacturerItem.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>型号：</td>
                    <td>
                        <select name="proModel" style="width: 165px" id="proModel" class="easyui-combobox">
                            <c:forEach items="${proMode}" var="proModeItem">
                                <option value="${proModeItem.dItemvalue}"
                                >${proModeItem.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>规则编号：</td>
                    <td><input name="ruleId" maxlength="15" type="text" placeholder="请输入" class="easyui-validatebox"
                               style="width:160px;"></td>
                    <td>机具类型：</td>
                    <td>
                    <input name="proType" maxlength="15" type="hidden" placeholder="请输入" class="easyui-validatebox"
                           style="width:160px;" id="proType" readonly="readonly">
                    <input name="proTypeName" maxlength="15" type="text" placeholder="请输入" class="easyui-validatebox"
                           style="width:160px;" id="proTypeName" readonly="readonly">
                        <%--

                        <select class="easyui-combobox" name="proType" editable="false" style="width:160px;"
                                data-options="required:true">
                            <c:forEach items="${modelType}" var="modelTypeItem">
                                <option value="${modelTypeItem.dItemvalue}">${modelTypeItem.dItemname}</option>
                            </c:forEach>
                        </select>--%>
                    </td>
                </tr>
                <tr>
                    <td>优惠条件：</td>
                    <td>
                        <select class="easyui-combobox" name="activityWay" style="width:160px;height:21px">
                            <c:forEach items="${activityDisType}" var="activityDisItem">
                                <option value="${activityDisItem.dItemvalue}">${activityDisItem.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>参与条件：</td>
                    <td>
                        <select class="easyui-combobox" name="activityCondition" style="width:160px;height:21px">
                            <c:forEach items="${activityCondition}" var="activityConItem">
                                <option value="${activityConItem.dItemvalue}">${activityConItem.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>优惠方式：</td>
                    <td><input name="activityRule" maxlength="15" type="text" placeholder="请输入"
                               class="easyui-validatebox" style="width:160px;" data-options="required:true" ></td>
                    <td>活动价格：</td>
                    <td><input name="price" maxlength="15" id="price" type="text" placeholder="请输入" class="easyui-numberbox" data-options="required:true"
                               style="width:160px;"></td>
                </tr>
                <tr>
                    <td>开始时间</td>
                    <td><input name="beginTimeStr" id="beginTimeStr" type="text" class="easyui-datebox" editable="false"
                               placeholder="请输入"
                               style="width:160px;" data-options="required:true" value=""></td>
                    <td>结束时间</td>
                    <td><input name="endTimeStr" id="endTimeStr" type="text" class="easyui-datebox" editable="false"
                               placeholder="请输入"
                               style="width:160px;" data-options="required:true" value=""></td>
                </tr>
                <tr>
                    <td>平台类型:</td>
                    <td>
                        <select  name="platform" id="platform" style="width:160px;height:21px" class="easyui-combobox">
                            <option value=" ">---请选择---</option>
                            <c:forEach items="${ablePlatForm}" var="ablePlatFormItem">
                                <option value="${ablePlatFormItem.platformNum}">${ablePlatFormItem.platformName}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>保价时间：</td>
                    <td>
                        <input name="gTime" maxlength="15" type="text" placeholder="请输入" class="easyui-numberbox"
                               style="width:150px;">天
                    </td>
                </tr>
                <tr >
                <td>平台机具</td>
                    <td >
                    <select  name="busProCode" style="width:160px;height:21px" id="busProCode" class="easyui-combobox">
                    </select>
                    </td>
                    <td>终端批次</td>
                    <td >
                        <select  name="termBatchcode" style="width:160px;height:21px" id="termBatchcode">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>终端类型</td>
                    <td >
                        <select  name="termtype" style="width:160px;height:21px" id="termtype">
                        </select>
                    </td>
                    <td>pos类型</td>
                    <td>
                        <select  name="posType" id="posType" style="width:160px;height:21px" >
                            <c:forEach var="posType" items="${posTypeList}">
                                <option value="${posType.key}">${posType.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>特价机价格</td>
                    <td><input name="posSpePrice" id="posSpePrice" maxlength="15" type="text" placeholder="请输入" class="easyui-numberbox"
                               style="width:160px;"></td>
                    </td>
                    <td>达标时间（天）</td>
                    <td><input name="standTime" id="standTime" maxlength="15" type="text" placeholder="请输入" class="easyui-numberbox"
                               style="width:160px;"></td>
                </tr>
            </table>
        </form>
    </div>
</div>