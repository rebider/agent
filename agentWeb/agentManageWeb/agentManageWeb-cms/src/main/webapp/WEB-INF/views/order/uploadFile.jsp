<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">

    function splitUploadFile() {
        progressLoad();
        $.ajax({
            url: '/split/analysisFile',
            type: 'POST',
            cache: false,
            data: new FormData($('#uploadFileForm')[0]),
            processData: false,
            contentType: false
        }).done(function(data) {
            $("#newOrderTable").html("");
            $("#oldOrderTable").html("");
            var jsonObj =  eval("(" + data + ")");
            if(!jsonObj.success && jsonObj.success!=undefined){
                info(jsonObj.msg);
                progressClose();
                return;
            }
            var head = "<tr><td>sn开始</td><td>sn结束</td><td>数量</td><td>机型</td></tr>";
            var newTableStr = head;
            var oldTableStr = head;
            $.each(jsonObj,function(n,item) {
                var text= "<tr>"+
                          "<td>"+item.startSn+"</td>"+
                          "<td>"+item.endSn+"</td>"+
                          "<td>"+item.num+"</td>"+
                          "<td>"+item.proModel+"</td>"+
                          "</tr>";
                if(item.flag==1){
                    newTableStr+=text;
                }else{
                    oldTableStr+=text;
                }
            });
            $("#newOrderTable").html(newTableStr);
            $("#oldOrderTable").html(oldTableStr);
            progressClose();
        }).fail(function(res) {
            info("系统异常，请联系管理员！"+res);
            progressClose();
        });
    }

    function skipPage(type,orderType){
        var file = $("#uploadFileForm").find("input[name='file']").val();
        if(file=='' || file==null || file==undefined){
            info("请先上传文件");
            return;
        }

        var url = "";
        var tab = "";
        if(type=='compensation'){
            if(orderType=='1'){
                url="${path}/compensate/toCompensateAmtAddPage?orderType="+orderType;
                tab = "新订单";
            }else if(orderType=='0'){
                url="${path}/oldCompensate/toCompensateAmtAddPage?orderType="+orderType;
                tab = "老订单";
            }
        }else if(type=='returnOrder'){
            if(orderType=='1'){
                url="${path}/order/return/page/create?orderType="+orderType;
                tab = "新订单";
            }else if(orderType=='0'){
                url="${path}/oldorderreturn/apply?orderType="+orderType;
                tab = "老订单";
            }
        }
        addTab({
            title : '申请-'+tab,
            border : false,
            closable : true,
            fit : true,
            href:url
        });
    }

    function showUpload(item, data) {
        if (item) {
            addTab({
                title: '申请-老订单',
                border: false,
                closable: true,
                fit: true,
                href: '/oldCompensate/toCompensateAmtAddPage?orderType=0&agentId='+item.id
            });
        }
    }

    function showUploadDialog(options) {
        var file = $("#uploadFileForm").find("input[name='file']").val();
        if(file=='' || file==null || file==undefined){
            info("请先上传文件");
            return;
        }
        agentInfoSelectDialogViewTierPass(options);
    }

    function exportUpload(exportType) {
        progressLoad();
        var file = $("#uploadFileForm").find("input[name='file']").val();
        if(file=='' || file==null || file==undefined){
            info("请先上传文件");
            return;
        }
        if(exportType==null || exportType==undefined){
            info("导出类型错误");
            return;
        }
        $("#exportForm").find("input[name='exportType']").val(exportType);
        $("#exportForm").submit();
        progressClose();
    }
</script>
<div class="easyui-panel" data-options="iconCls:'fi-results'" >
    <form id="uploadFileForm" enctype="multipart/form-data" >
        <input type="file" name="file" style="width: 160px"/>
        <a href="javascript:void(0);" style="margin:5px 5px 5px 5px" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'" onclick="splitUploadFile();">上传SN号</a>
        <a href="/static/template/commonUpload.xlsx" style="margin:5px 5px 5px 5px" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'" >下载模板</a>
    </form>
</div>
<div  style="float: left;width: 50%">
    <div class="easyui-panel" title="新订单SN列表" data-options="iconCls:'fi-results'" >
        <table class="grid" id="newOrderTable">
        </table>
    </div>
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;float: right" data-options="iconCls:'fi-save'" onclick="skipPage('${type}','1')">新订单提交</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 80px;float: right" data-options="iconCls:'fi-save'" onclick="exportUpload('1')">导出</a>
</div>
<div  style="float: left;width: 50%;">
    <div class="easyui-panel" title="历史订单SN列表" data-options="iconCls:'fi-results'" >
        <table class="grid" id="oldOrderTable">
        </table >
    </div>
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;float: right" data-options="iconCls:'fi-save'" onclick="(${((type=='compensation' || type=='returnOrder') && not empty isAgent)})?skipPage('${type}','0'):showUploadDialog({data:this,callBack:showUpload})">历史订单提交</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 80px;float: right" data-options="iconCls:'fi-save'" onclick="exportUpload('0')">导出</a>
</div>
<form id="exportForm" action="/split/exportSplit">
    <input type="hidden" name="exportType">
</form>