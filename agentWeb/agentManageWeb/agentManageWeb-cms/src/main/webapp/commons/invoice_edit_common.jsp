<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="开票信息"  data-options="iconCls:'fi-wrench',tools:'#paylist_tt'" style="width:98%">
<table style="" id="invoice_edit_src_table">
    <c:forEach items="${invoiceList}" var="invoice">
      <tbody>
        <tr class="invoiceClass">
            <td>开票公司：</td>
            <td >
                <input name="invoiceId" maxlength="15" type="hidden" value="${invoice.id}">
                <input name="invoiceCompany" maxlength="15" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true"
                        style="width:160px;" value="${invoice.invoiceCompany}">
            </td>
            <td>开票项目：</td>
            <td><input name="invoiceProject" maxlength="15" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true"
                       style="width:160px;" value="${invoice.invoiceProject}"></td>
            <td>金额：</td>
            <td ><input name="invoiceAmt" maxlength="15" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true"
                        style="width:160px;" value="${invoice.invoiceAmt}"></td>
            <td>发票号：</td>
            <td ><input name="invoiceNum" maxlength="15" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true"
                        style="width:160px;" value="${invoice.invoiceNum}"></td>
            <td>发票代码：</td>
            <td ><input name="invoiceCode" maxlength="15" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true"
                        style="width:160px;" value="${invoice.invoiceCode}"></td>
        </tr>
        <tr class="invoiceClass">
            <td>快递单号：</td>
            <td ><input name="expressNum" maxlength="15" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true"
                        style="width:160px;" value="${invoice.expressNum}"></td>
            <td>快递公司：</td>
            <td ><input name="expressComp" maxlength="15" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true"
                        style="width:160px;" value="${invoice.expressComp}"></td>
            <td>寄出时间：</td>
            <td><input name="sendTime" id="sendTime" maxlength="15" type="text" placeholder="请输入" class="easyui-datetimebox dyTime" data-options="required:true"
                       style="width:160px;" editable="false" value="<fmt:formatDate pattern="yyyy-MM-dd HH：mm：ss" value="${invoice.sendTime}" />"></td>
        </tr>
        <tr class="invoiceClass">
            <td width="80px">附件:</td>
            <td>
                <a class="attrInput">
                    <c:if test="${!empty invoice.attachments}">
                        <c:forEach items="${invoice.attachments}" var="attachment">
                            <span onclick='invoice_edit_removeFile(this)' >${attachment.attName}<input type='hidden' name='invoiceTableFile' value='${attachment.id}' /></span>
                        </c:forEach>
                    </c:if>
                </a>
                <a href="javascript:void(0)" class="busck-easyui-linkbutton-edit"
                   data-options="plain:true,iconCls:'fi-magnifying-glass'"
                   onclick="invoice_edit_AttFile_uploadView(this)">添加附件</a>
            </td>
            <td colspan="8">
                <a href="javascript:;" class="easyui-linkbutton" style="width: 50px;float: right"  onclick="invoice_edit_delete_row(this)">删除</a>
            </td>
        </tr>
      </tbody>
    </c:forEach>
</table>
</div>
<div id="paylist_tt">
    <a href="javascript:void(0)" class="fi-plus" onclick="invoice_edit_add_row('#invoice_edit_src_table','#invoice_edit_row_templet')" title="添加记录"></a>
</div>
<table id="invoice_edit_row_templet" style="display: none;">
    <tbody>
    <tr class="invoiceClass">
        <td>开票公司：</td>
        <td ><input name="invoiceCompany" maxlength="15" type="text" placeholder="请输入" input-class="easyui-validatebox" data-options="required:true"
                    style="width:160px;"></td>
        <td>开票项目：</td>
        <td><input name="invoiceProject" maxlength="15" type="text" placeholder="请输入" input-class="easyui-validatebox" data-options="required:true"
                   style="width:160px;"></td>
        <td>金额：</td>
        <td ><input name="invoiceAmt" maxlength="15" type="text" placeholder="请输入" input-class="easyui-validatebox" data-options="required:true"
                    style="width:160px;"></td>
        <td>发票号：</td>
        <td ><input name="invoiceNum" maxlength="15" type="text" placeholder="请输入" input-class="easyui-validatebox" data-options="required:true"
                    style="width:160px;"></td>
        <td>发票代码：</td>
        <td ><input name="invoiceCode" maxlength="15" type="text" placeholder="请输入" input-class="easyui-validatebox" data-options="required:true"
                    style="width:160px;"></td>
    </tr>
    <tr class="invoiceClass">
        <td>快递单号：</td>
        <td ><input name="expressNum" maxlength="15" type="text" placeholder="请输入" input-class="easyui-validatebox" data-options="required:true"
                    style="width:160px;"></td>
        <td>快递公司：</td>
        <td ><input name="expressComp" maxlength="15" type="text" placeholder="请输入" input-class="easyui-validatebox" data-options="required:true"
                    style="width:160px;"></td>
        <td>寄出时间：</td>
        <td><input name="sendTime" maxlength="15" type="text" placeholder="请输入" input-class="easyui-datebox dyTime" data-options="required:true"
                   style="width:160px;" editable="false"></td>
    </tr>
    <tr class="invoiceClass">
        <td width="80px">附件:</td>
        <td>
            <a class="attrInput">
            </a>
            <a href="javascript:void(0)" class="busck-easyui-linkbutton-edit"
               data-options="plain:true,iconCls:'fi-magnifying-glass'"
               onclick="invoice_edit_AttFile_uploadView(this)">添加附件</a>
        </td>
        <td colspan="8">
            <a href="javascript:;" class="easyui-linkbutton" style="width: 50px;float: right"  onclick="invoice_edit_delete_row(this)">删除</a>
        </td>
    </tr>
    </tbody>
</table>
<script type="text/javascript">
    //删除打款记录行
    function invoice_edit_delete_row(t){
        //删除行元素
        $(t).parent().parent().parent().remove();
    }

    function invoice_edit_add_row(target,templet){
        //删除行元素
        $(target).append($(templet).html());
        var inputs = $(target+" tbody:last").find("input");
        for(var i=0;i<inputs.length;i++){
            if($(inputs[i]).attr("input-class") && $(inputs[i]).attr("input-class").length>0){
                $(inputs[i]).addClass($(inputs[i]).attr("input-class"));
            }
        }
        $.parser.parse($(target+" tbody:last"));
        $(target+" .dyTime:last").datebox({
            onSelect:function(beginDate){
                var date = new Date();
                if(beginDate>date){
                    info("时间不能大于当前时间");
                    $(this).combo('setText','');
                }
            }
        });
    }

    /*获取打款列表*/
    function getInvoiceEditList(){
        var dataList = [];
        var listTr = $("#invoice_edit_src_table").find("tbody");
        if(listTr!=undefined && listTr.length>0){
            for(var i = 0;i<listTr.length;i++) {
              var data =  getInvoiceItemData(listTr[i]);
              dataList.push(data);
            }
        }
        return dataList;
    }

    /**
     * 获取tr的数据
     * @param tr
     */
    function getInvoiceItemData(tr){
         var invoiceCompany = $(tr).find("input[name='invoiceCompany']").val();
         var invoiceProject = $(tr).find("input[name='invoiceProject']").val();
         var invoiceAmt = $(tr).find("input[name='invoiceAmt']").val();
         var invoiceNum = $(tr).find("input[name='invoiceNum']").val();
         var invoiceCode = $(tr).find("input[name='invoiceCode']").val();
         var expressNum = $(tr).find("input[name='expressNum']").val();
         var expressComp = $(tr).find("input[name='expressComp']").val();
         var sendTime = $(tr).find("input[name='sendTime']").val();
         var invoiceId = $(tr).find("input[name='invoiceId']").val();
         var files =  $(tr).find(".attrInput").find("input[name='invoiceTableFile']");
         var invoiceTableFileTemp = [];
         for(var j=0;j<files.length;j++){
             invoiceTableFileTemp.push($(files[j]).val());
         }
         return {
             invoiceCompany:invoiceCompany,
             invoiceProject:invoiceProject,
             invoiceAmt:invoiceAmt,
             invoiceNum:invoiceNum,
             invoiceCode:invoiceCode,
             expressNum:expressNum,
             expressComp:expressComp,
             sendTime:sendTime,
             invoiceTableFile:invoiceTableFileTemp,
             id:invoiceId
         };
    }

    var invoice_edit_AttFile_attrDom;
    //上传窗口
    function invoice_edit_AttFile_uploadView(t) {
        invoice_edit_AttFile_attrDom = $(t).parent().find(".attrInput");
        multFileUpload(invoice_edit_AttFile__jxkxUploadFile);
    }
    //附件解析
    function invoice_edit_AttFile__jxkxUploadFile(data) {
        var jsonData = eval(data);
        for (var i = 0; i < jsonData.length; i++) {
            $(invoice_edit_AttFile_attrDom).append("<span onclick='invoice_edit_removeFile(this)'>" + jsonData[i].attName + "<input type='hidden' name='invoiceTableFile' value='" + jsonData[i].id + "' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }
    }

    //删除附件
    function invoice_edit_removeFile(t) {
        parent.$.messager.confirm('询问', '确定删除附件么？', function (b) {
            if (b) {
                $(t).remove();
            }
        });
    }
</script>