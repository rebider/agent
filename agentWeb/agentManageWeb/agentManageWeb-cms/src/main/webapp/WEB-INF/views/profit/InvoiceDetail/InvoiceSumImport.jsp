<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>

<script type="text/javascript">




    $(function() {
        $('#InvoiceSumImForm').form({
            url : '${path}/profit/InvoiceSumController/importInvoiceSumFile',
            onSubmit : function() {
                progressLoad();
                if($("#file").val() == null || $("#file").val() == ''){
                    alert("上传文件不能为空！");
                    progressClose();
                    return false;
                }
                if($("#profitMonth1").datebox("getValue")==null || $("#profitMonth1").datebox("getValue")==''){
                    alert("月份选择不能空！");
                    progressClose();
                    return false;
                }
                    progressClose();
            },
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success == '1') {
                    parent.$.messager.alert('成功', result.obj, 'ok');
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                    parent.$.modalDialog.handler.dialog('close');
                }else {
                    parent.$.messager.alert('错误', result.resInfo, 'error');
                }
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;">
        <form id="InvoiceSumImForm" method="post" enctype="multipart/form-data">
            <table class="grid" id="">
                <tr>
                    <th>月份:</th>
                    <td><input id="profitMonth1" name="profitMonth" style="width: 160px;"></td>
                </tr>
                <script type="text/javascript">
                    var time = new Date();
                    var year_month;
                    if (time.getMonth() == 0) {
                        year_month = (time.getFullYear() - 1) + '-12' + '-01';
                    } else {
                        year_month = time.getFullYear() + '-' + (time.getMonth() >= 10 ? time.getMonth() : 0 + '' + (time.getMonth())) + '-01';
                    }

                    $('#profitMonth1').datebox({
                        required: true
                    });
                    $("#profitMonth1").datebox({
                        formatter: function (data) {
                            var date_temp = new Date(data);
                            return date_temp.getFullYear() + '' + (date_temp.getMonth() + 1 >= 10 ? date_temp.getMonth() + 1 : ('0' + '' + (date_temp.getMonth() + 1)));
                        },
                        parser: function (data) {
                            if (data.indexOf('-') < 0) {
                                data = data.substring(0, 4) + '-' + data.substring(4, data.length);
                            }
                            var t = Date.parse(data);
                            if (!isNaN(t)) {
                                return new Date(t);
                            } else {
                                return new Date();
                            }
                        }
                    });

                    $('#profitMonth1').datebox('setValue', year_month);
                </script>
                <tr>
                    <th>上传模板:</th>
                    <td>
                        <input type="file" id="file" name="file" class="form-control" style='width: 200px;margin-left: 20px;float: left;' multiple="true"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>