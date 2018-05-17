<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var caseId; 
    var auditTrackDataGrid;
    $(function () {
        caseId = "${caseId}";
        $("#contactRel").val('${busyMan.contactRel}');
        $('#facePhotoImg').attr("src", $('#facePhotoUrl').val());
        $('#backPhotoImg').attr("src", $('#backPhotoUrl').val());
        $('#handPhotoImg').attr("src", $('#handPhotoUrl').val());
        $('#merchPhotoImg').attr("src", $('#merchPhotoUrl').val());
        $('#companyPhotoImg').attr("src", $('#companyPhotoUrl').val());
        $('#creditPhotoImg').attr("src", $('#creditPhotoUrl').val());
        $('#checkPhotoImg1').attr("src", $('#checkPhotoImg1Url').val());
        $('#checkPhotoImg2').attr("src", $('#checkPhotoImg2Url').val());
        $('#photoURL').attr("src", $('#photoURLUrl').val());

        $.get("${path}/credit/companyInfo?caseId=" + caseId,
                function (data) {
                    data = $.parseJSON(data);
                    for (var i = 0; i < data.length; i++) {
                        //alert(data.checked)
                        if (data[i].checked == "1") {
                            $("#companyInfo")
                                    .append(
                                    "<input type='checkbox' class='combobox-checkbox' checked value='" + data[i].paramId + "' name='companyInfo'/>"
                                    + data[i].paramValue);
                        } else {
                            $("#companyInfo")
                                    .append(
                                    "<input type='checkbox' class='combobox-checkbox' value='" + data[i].paramId + "' name='companyInfo'/>"
                                    + data[i].paramValue);
                        }

                    }
                })

        $.get("${path}/credit/baseInfo?caseId=" + caseId,
                function (data) {
                    data = $.parseJSON(data);
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].checked == "1") {
                            $("#baseInfo")
                                    .append(
                                    "<input type='checkbox' class='combobox-checkbox' checked value='" + data[i].paramId + "' name='baseInfo'/>"
                                    + data[i].paramValue);
                        } else {
                            $("#baseInfo")
                                    .append(
                                    "<input type='checkbox' class='combobox-checkbox' value='" + data[i].paramId + "' name='baseInfo'/>"
                                    + data[i].paramValue);
                        }

                    }
                })

        $.get("${path}/credit/photoInfo?caseId=" + caseId,
                function (data) {
                    data = $.parseJSON(data);
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].checked == "1") {
                            $("#photoInfo")
                                    .append(
                                    "<input type='checkbox' class='combobox-checkbox' checked value='" + data[i].paramId + "' name='photoInfo'/>"
                                    + data[i].paramValue);
                        } else {
                            $("#photoInfo")
                                    .append(
                                    "<input type='checkbox' class='combobox-checkbox' value='" + data[i].paramId + "' name='photoInfo'/>"
                                    + data[i].paramValue);
                        }

                    }
                })

        $.get("${path}/credit/contactsInfo?caseId=" + caseId,
                function (data) {
                    data = $.parseJSON(data);
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].checked == "1") {
                            $("#busyManInfo")
                                    .append(
                                    "<input type='checkbox' class='combobox-checkbox' checked value='" + data[i].paramId + "' name='contactsInfo'/>"
                                    + data[i].paramValue);
                        } else {
                            $("#busyManInfo")
                                    .append(
                                    "<input type='checkbox' class='combobox-checkbox' value='" + data[i].paramId + "' name='contactsInfo'/>"
                                    + data[i].paramValue);
                        }

                    }
                })

        auditTrackDataGrid = $('#auditTrackDataGrid').datagrid({
            url: '${path}/credit/auditTrackDataGrid?caseId=' + caseId,
            striped: true,
            rownumbers: true,
            pagination: true,
            sortName: 'caseId',
            sortOrder: 'asc',
            singleSelect: true,
            pageSize: 10,
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            frozenColumns: [[{
                width: '80',
                title: '初审人',
                field: 'firstAuditUsername'
            }, {
                width: '120',
                title: '修改字段',
                field: 'attributeName'
            }, {
                width: '218',
                title: '修改前信息',
                field: 'prevValue',
                formatter: function (value, row, index) {
                    switch (value) {
                        case 'O':
                            return '自有';
                        case 'R':
                            return '租赁';
                        case 'H':
                            return '酒店';
                        case 'S':
                            return '商超';
                        case 'C':
                            return '便利店';
                        case 'L':
                            return '本地';
                        case 'F':
                            return '外地';
                        default :
                            return value;
                    }

                }
            }, {
                width: '218',
                title: '修改后信息',
                field: 'afterValue',
                formatter: function (value, row, index) {
                    switch (value) {
                        case 'O':
                            return '自有';
                        case 'R':
                            return '租赁';
                        case 'H':
                            return '酒店';
                        case 'S':
                            return '商超';
                        case 'C':
                            return '便利店';
                        case 'L':
                            return '本地';
                        case 'F':
                            return '外地';
                        default :
                            return value;
                    }

                }
            }, {
                width: '80',
                title: '终审人',
                field: 'lastAuditUsername'
            }, {
                width: '130',
                title: '修改时间',
                field: 'createDate'
            }]],
            onLoadSuccess: function (data) {
            }
        });
    })

    function submitBaseInfoForm() {
        var paramIdList = "";
        $('input[name="baseInfo"]:checked').each(function (i) {
            if (0 == i) {
                paramIdList = $(this).val();
            } else {
                paramIdList += ("," + $(this).val());
            }
        });
        $('#baseInfoForm').form(
                'submit',
                {
                    url: '${path}/credit/editInfo?caseId=' + caseId
                    + '&paramIdList=' + paramIdList,
                    onSubmit: function () {
                        progressLoad();
                        var isValid = $(this).form('validate');
                        if (!isValid) {
                            progressClose();
                        }
                        return isValid;
                    },
                    success: function (result) {
                        progressClose();
                        result = $.parseJSON(result);
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                            parent.$.modalDialog.openner_dataGrid
                                    .datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                        } else {
                            parent.$.messager.alert('错误', result.msg, 'error');
                        }
                    }
                });
    }
    function submitCompanyInfoForm() {
        var paramIdList = "";
        $('input[name="companyInfo"]:checked').each(function (i) {
            if (0 == i) {
                paramIdList = $(this).val();
            } else {
                paramIdList += ("," + $(this).val());
            }
        });
        $('#companyInfoForm').form(
                'submit',
                {
                    url: '${path}/credit/editInfo?caseId=' + caseId
                    + '&paramIdList=' + paramIdList,
                    onSubmit: function () {
                        progressLoad();
                        var isValid = $(this).form('validate');
                        if (!isValid) {
                            progressClose();
                        }
                        return isValid;
                    },
                    success: function (result) {
                        progressClose();
                        result = $.parseJSON(result);
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                            parent.$.modalDialog.openner_dataGrid
                                    .datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                        } else {
                            parent.$.messager.alert('错误', result.msg, 'error');
                        }
                    }
                });
    }

    function submitPhotoInfoForm() {
        var paramIdList = "";
        $('input[name="photoInfo"]:checked').each(function (i) {
            if (0 == i) {
                paramIdList = $(this).val();
            } else {
                paramIdList += ("," + $(this).val());
            }
        });
        $('#photoInfoForm').form(//
                'submit',
                {
                    url: '${path}/credit/editInfo?facePhoto=facePhoto&backPhoto=backPhoto&'
                    + 'handPhoto=handPhoto&merchPhoto=merchPhoto&companyPhoto=companyPhoto&creditPhoto=creditPhoto&caseId=' + caseId
                    + '&paramIdList=' + paramIdList,
                    onSubmit: function () {
                        progressLoad();
                        var isValid = $(this).form('validate');
                        if (!isValid) {
                            progressClose();
                        }
                        return isValid;
                    },
                    success: function (result) {
                        progressClose();
                        result = $.parseJSON(result);
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                            parent.$.modalDialog.openner_dataGrid
                                    .datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                        } else {
                            parent.$.messager.alert('错误', result.msg, 'error');
                        }
                    }
                });
    }

    function submitCheckPhotoForm() {
        $('#checkePhotoForm').form(
                'submit',
                {
                    url: '${path}/credit/checkSubmit?checkPhoto1=1&checkPhoto2=1&caseId=' + caseId,
                    onSubmit: function () {
                        progressLoad();
                        var isValid = $(this).form('validate');
                        if (!isValid) {
                            progressClose();
                        }
                        return isValid;
                    },
                    success: function (result) {
                        progressClose();
                        result = $.parseJSON(result);
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                            parent.$.modalDialog.openner_dataGrid
                                    .datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                        } else {
                            parent.$.messager.alert('错误', result.msg, 'error');
                        }
                    }
                });
    }

    function submitContactsInfoForm() {
        var paramIdList = "";
        $('input[name="contactsInfo"]:checked').each(function (i) {
            if (0 == i) {
                paramIdList = $(this).val();
            } else {
                paramIdList += ("," + $(this).val());
            }
        });
        $("#busyManForm").form(
                'submit',
                {
                    url: '${path}/credit/editInfo?caseId=' + caseId
                    + '&paramIdList=' + paramIdList,
                    onSubmit: function () {
                        progressLoad();
                        var isValid = $(this).form('validate');
                        if (!isValid) {
                            progressClose();
                        }
                        return isValid;
                    },
                    success: function (result) {
                        progressClose();
                        result = $.parseJSON(result);
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                            parent.$.modalDialog.openner_dataGrid
                                    .datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                        } else {
                            parent.$.messager.alert('错误', result.msg, 'error');
                        }
                    }
                });
    }

    function submitFirstAuditForm() {
        $('#firstAuditForm').form('submit', {
            url: '${path}/credit/firstAudit?caseId=' + caseId,
            onSubmit: function () {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success: function (result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.messager.alert('提示', result.msg, 'info');
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    }

    function submitLastAuditForm() {
        $('#lastAuditForm').form('submit', {
            url: '${path}/credit/lastAudit?caseId=' + caseId,
            onSubmit: function () {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success: function (result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.messager.alert('提示', result.msg, 'info');
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    }


    function submitBlAuditForm() {
        $('#firstAuditForm').form('submit', {
            url: '${path}/credit/blAudit?caseId=' + caseId,
            onSubmit: function () {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success: function (result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.messager.alert('提示', result.msg, 'info');
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    }


    function submitBackSubmitForm(type) {
        $('#firstAuditForm').form('submit', {
            url: '${path}/credit/backSubmit?caseId=' + caseId+"&type="+type,
            onSubmit: function () {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success: function (result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.messager.alert('提示', result.msg, 'info');
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    }

    function previewFaceImage(file) {
        var tip = "只能上传 jpg or png or gif 格式图片!";
        var filters = {
            "jpeg": "/9j/4",
            "gif": "R0lGOD",
            "png": "iVBORw"
        }
        if (window.FileReader) {
            for (var i = 0, f; f = file.files[i]; i++) {
                var fr = new FileReader();
                fr.onload = function (e) {
                    var src = e.target.result;
                    if (!validateImg(src)) {
                        alert(tip);
                    } else {
                        showFacePhotoImg(src);
                    }
                }
                fr.readAsDataURL(f);
            }
        } else {
            if (!/\.jpg$|\.png$|\.gif$/i.test(file.value)) {
                alert(tip);
            } else {
                showFacePhotoImg(file.value);
            }
        }

        function validateImg(data) {
            var pos = data.indexOf(",") + 1;
            for (var e in filters) {
                if (data.indexOf(filters[e]) === pos) {
                    return e;
                }
            }
            return null;
        }

        function showFacePhotoImg(src) {
            $("#facePhotoImg").attr("src", src);
        }
    }
    function previewBackImage(file) {
        var tip = "只能上传 jpg or png or gif 格式图片!";
        var filters = {
            "jpeg": "/9j/4",
            "gif": "R0lGOD",
            "png": "iVBORw"
        }
        if (window.FileReader) {
            for (var i = 0, f; f = file.files[i]; i++) {
                var fr = new FileReader();
                fr.onload = function (e) {
                    var src = e.target.result;
                    if (!validateImg(src)) {
                        alert(tip);
                    } else {
                        showBackPhotoImg(src);
                    }
                }
                fr.readAsDataURL(f);
            }
        } else {
            if (!/\.jpg$|\.png$|\.gif$/i.test(file.value)) {
                alert(tip);
            } else {
                showBackPhotoImg(file.value);
            }
        }

        function validateImg(data) {
            var pos = data.indexOf(",") + 1;
            for (var e in filters) {
                if (data.indexOf(filters[e]) === pos) {
                    return e;
                }
            }
            return null;
        }

        function showBackPhotoImg(src) {
            $("#backPhotoImg").attr("src", src);
        }
    }
    function previewHandImage(file) {
        var tip = "只能上传 jpg or png or gif 格式图片!";
        var filters = {
            "jpeg": "/9j/4",
            "gif": "R0lGOD",
            "png": "iVBORw"
        }
        if (window.FileReader) {
            for (var i = 0, f; f = file.files[i]; i++) {
                var fr = new FileReader();
                fr.onload = function (e) {
                    var src = e.target.result;
                    if (!validateImg(src)) {
                        alert(tip);
                    } else {
                        showHandPhotoImg(src);
                    }
                }
                fr.readAsDataURL(f);
            }
        } else {
            if (!/\.jpg$|\.png$|\.gif$/i.test(file.value)) {
                alert(tip);
            } else {
                showHandPhotoImg(file.value);
            }
        }

        function validateImg(data) {
            var pos = data.indexOf(",") + 1;
            for (var e in filters) {
                if (data.indexOf(filters[e]) === pos) {
                    return e;
                }
            }
            return null;
        }

        function showHandPhotoImg(src) {
            $("#handPhotoImg").attr("src", src);
        }
    }
    function previewMerchImage(file) {
        var tip = "只能上传 jpg or png or gif 格式图片!";
        var filters = {
            "jpeg": "/9j/4",
            "gif": "R0lGOD",
            "png": "iVBORw"
        }
        if (window.FileReader) {
            for (var i = 0, f; f = file.files[i]; i++) {
                var fr = new FileReader();
                fr.onload = function (e) {
                    var src = e.target.result;
                    if (!validateImg(src)) {
                        alert(tip);
                    } else {
                        showMerchPhotoImg(src);
                    }
                }
                fr.readAsDataURL(f);
            }
        } else {
            if (!/\.jpg$|\.png$|\.gif$/i.test(file.value)) {
                alert(tip);
            } else {
                showMerchPhotoImg(file.value);
            }
        }


        function validateImg(data) {
            var pos = data.indexOf(",") + 1;
            for (var e in filters) {
                if (data.indexOf(filters[e]) === pos) {
                    return e;
                }
            }
            return null;
        }

        function showMerchPhotoImg(src) {
            $("#merchPhotoImg").attr("src", src);//
        }
    }


    function previewCompanyImage(file) {
        var tip = "只能上传 jpg or png or gif 格式图片!";
        var filters = {
            "jpeg": "/9j/4",
            "gif": "R0lGOD",
            "png": "iVBORw"
        }
        if (window.FileReader) {
            for (var i = 0, f; f = file.files[i]; i++) {
                var fr = new FileReader();
                fr.onload = function (e) {
                    var src = e.target.result;
                    if (!validateImg(src)) {
                        alert(tip);
                    } else {
                        showCompanyPhotoImg(src);
                    }
                }
                fr.readAsDataURL(f);
            }
        } else {
            if (!/\.jpg$|\.png$|\.gif$/i.test(file.value)) {
                alert(tip);
            } else {
                showCompanyPhotoImg(file.value);
            }
        }


        function validateImg(data) {
            var pos = data.indexOf(",") + 1;
            for (var e in filters) {
                if (data.indexOf(filters[e]) === pos) {
                    return e;
                }
            }
            return null;
        }

        function showCompanyPhotoImg(src) {
            $("#companyPhotoImg").attr("src", src);
        }
    }
    function previewCreditImage(file) {
        var tip = "只能上传 jpg or png or gif 格式图片!";
        var filters = {
            "jpeg": "/9j/4",
            "gif": "R0lGOD",
            "png": "iVBORw"
        }
        if (window.FileReader) {
            for (var i = 0, f; f = file.files[i]; i++) {
                var fr = new FileReader();
                fr.onload = function (e) {
                    var src = e.target.result;
                    if (!validateImg(src)) {
                        alert(tip);
                    } else {
                        showCreditPhotoImg(src);
                    }
                }
                fr.readAsDataURL(f);
            }
        } else {
            if (!/\.jpg$|\.png$|\.gif$/i.test(file.value)) {
                alert(tip);
            } else {
                showCreditPhotoImg(file.value);
            }
        }


        function validateImg(data) {
            var pos = data.indexOf(",") + 1;
            for (var e in filters) {
                if (data.indexOf(filters[e]) === pos) {
                    return e;
                }
            }
            return null;
        }

        function showCreditPhotoImg(src) {
            $("#creditPhotoImg").attr("src", src);
        }
    }

    function previewCheckPhotoImage1(file) {
        var tip = "只能上传 jpg or png or gif 格式图片!";
        var filters = {
            "jpeg": "/9j/4",
            "gif": "R0lGOD",
            "png": "iVBORw"
        }
        if (window.FileReader) {
            for (var i = 0, f; f = file.files[i]; i++) {
                var fr = new FileReader();
                fr.onload = function (e) {
                    var src = e.target.result;
                    if (!validateImg(src)) {
                        alert(tip);
                    } else {
                        showCheckPhotoImg1(src);
                    }
                }
                fr.readAsDataURL(f);
            }
        } else {
            if (!/\.jpg$|\.png$|\.gif$/i.test(file.value)) {
                alert(tip);
            } else {
                showCheckPhotoImg1(file.value);
            }
        }

        function validateImg(data) {
            var pos = data.indexOf(",") + 1;
            for (var e in filters) {
                if (data.indexOf(filters[e]) === pos) {
                    return e;
                }
            }
            return null;
        }

        function showCheckPhotoImg1(src) {
            $("#checkPhotoImg1").attr("src", src);
        }
    }

    function previewCheckPhotoImage2(file) {
        var tip = "只能上传 jpg or png or gif 格式图片!";
        var filters = {
            "jpeg": "/9j/4",
            "gif": "R0lGOD",
            "png": "iVBORw"
        }
        if (window.FileReader) {
            for (var i = 0, f; f = file.files[i]; i++) {
                var fr = new FileReader();
                fr.onload = function (e) {
                    var src = e.target.result;
                    if (!validateImg(src)) {
                        alert(tip);
                    } else {
                        showCheckPhotoImg2(src);
                    }
                }
                fr.readAsDataURL(f);
            }
        } else {
            if (!/\.jpg$|\.png$|\.gif$/i.test(file.value)) {
                alert(tip);
            } else {
                showCheckPhotoImg2(file.value);
            }
        }

        function validateImg(data) {
            var pos = data.indexOf(",") + 1;
            for (var e in filters) {
                if (data.indexOf(filters[e]) === pos) {
                    return e;
                }
            }
            return null;
        }

        function showCheckPhotoImg2(src) {
            $("#checkPhotoImg2").attr("src", src);
        }
    }


    function uploadFacePhoto(file) {
        $("#facePhoto").click();
        previewFaceImage(file);
    }
    function uploadBackPhoto(file) {
        $("#backPhoto").click();
        previewBackImage(file);
    }
    function uploadHandPhoto(file) {
        $("#handPhoto").click();
        previewHandImage(file);
    }
    function uploadMerchPhoto(file) {
        $("#merchPhoto").click();
        previewMerchImage(file);
    }
    function uploadCompanyPhoto(file) {
        $("#companyPhoto").click();
        previewCompanyImage(file);
    }
    function uploadCreditPhoto(file) {
        $("#creditPhoto").click();
        previewCreditImage(file);
    }
    function uploadCheckPhoto1(file) {
        $("#checkPhoto1").click();
        previewCheckPhotoImage1(file);
    }
    function uploadCheckPhoto2(file) {
        $("#checkPhoto2").click();
        previewCheckPhotoImage2(file);
    }
    // $('#companyInfo').combobox({
    //     url:'${path}/credit/companyInfo',
    //     method:'get',
    //     editable: false,
    //     valueField:'paramId',
    //     textField:'paramValue',
    //     panelHeight:'auto',
    //     multiple:true,
    //     formatter: function (row) {
    //         var opts = $(this).combobox('options');
    //         return '<input type="checkbox" class="combobox-checkbox">' + row[opts.textField]
    //     },
    //     onLoadSuccess: function () {
    //         var opts = $(this).combobox('options');
    //         var target = this;
    //         var values = $(target).combobox('getValues');
    //         $.map(values, function (value) {
    //             var el = opts.finder.getEl(target, value);
    //             el.find('input.combobox-checkbox')._propAttr('checked', true);
    //         })
    //     },
    //     onSelect: function (row) {
    //         var opts = $(this).combobox('options');
    //         var el = opts.finder.getEl(this, row[opts.valueField]);
    //         el.find('input.combobox-checkbox')._propAttr('checked', true);
    //     },
    //     onUnselect: function (row) {
    //         var opts = $(this).combobox('options');
    //         var el = opts.finder.getEl(this, row[opts.valueField]);
    //         el.find('input.combobox-checkbox')._propAttr('checked', false);
    //     }
    // });
</script>
<div class="easyui-layout" data-options="fit:true,border:false"
     style="overflow-y: auto">
    <div class="easyui-tabs" style="width: 100%; height: auto">
        <div title="案件信息">
            <div title="案件信息">
                <div class="easyui-panel" title="基本信息" style="width: 100%">
                    <div style="padding: 10px 60px 20px 10px">
                        <table cellpadding="5">
                            <tr>
                                <td>姓名:</td>
                                <td><input class="easyui-textbox" type="text"
                                           value="${cmCust.custName}" readonly="readonly"/></td>
                               
                                <td>手机号码:</td>
                                <td><input class="easyui-textbox" type="text"
                                           value="${cmCust.custMobile}" readonly="readonly"/></td>
                            </tr>
                            <tr>
                                <td>案件编号:</td>
                                <td><input id="caseId" class="easyui-textbox" type="text"
                                           value="${cmCust.caseId}" readonly="readonly"/></td>
                                <td>年龄:</td>
                                <td><input class="easyui-textbox" type="text"
                                           value="${cmCust.age}" readonly="readonly"/></td>
                                <td>性别:</td>
                                <td><input class="easyui-textbox" type="text"
                                           value="${cmCust.sex}" readonly="readonly"/></td>
                            </tr>
                        </table>
                    </div>
                </div>

                <div class="easyui-panel" title="行业信息" style="width: 100%">
                    <div style="padding: 10px 60px 20px 10px">
                        <form id="companyInfoForm" method="post">
                            <table cellpadding="5">
                                <tr>
                                    <td>行业类型:</td>
                                    <td><input class="easyui-textbox" type="text" name="businessType"
                                               value="烟草行业" readonly="readonly"/></td>
                                    <td>公司编号:</td>
                                    <td><input class="easyui-textbox" type="text"
                                               name="companyId" value="${cmCustCpy.companyId}" readonly="readonly"/></td>
                                    <td>店铺业态:</td>
                                    <td><select class="easyui-combobox" name="shopsType"
                                                data-options="panelHeight:'auto'"
                                                value="${cmCustCpy.shopsType }" style="width: 150px">
                                        <option value=""></option>
                                        <option value="H"
                                        ${cmCustCpy.shopsType=="H" ? 'selected = "selected"' : '' }>酒店
                                        </option>
                                        <option value="S"
                                        ${cmCustCpy.shopsType=="S" ? 'selected = "selected"' : '' }>商超
                                        </option>
                                        <option value="C"
                                        ${cmCustCpy.shopsType=="C" ? 'selected = "selected"' : '' }>便利店
                                        </option>
                                    </select></td>
                                </tr>
                                <tr>
                                    <td>公司名称:</td>
                                    <td><input class="easyui-textbox" type="text"
                                               name="companyName" value="${cmCustCpy.companyName}"/></td>
                                    <td>经营年限:</td>
                                    <td><input class="easyui-textbox" type="text"
                                               name="manageAge" value="${cmCustCpy.manageAge}" readOnly="true"/></td>
                                    <td>烟草专卖证号:</td>
                                    <td><input class="easyui-textbox" type="text"
                                               name="liceId" value="${cmCustCpy.liceId}"/></td>
                                </tr>
                                <tr>
                                    <td>店铺规模:</td>
                                    <td><input class="easyui-textbox" type="text"
                                               name="manageScale" value="${cmCustCpy.manageScale}"/></td>
                                    <td>场地类型:</td>
                                    <td><select class="easyui-combobox" name="placeType"
                                                data-options="panelHeight:'auto'"
                                                value="${cmCustCpy.placeType }" style="width: 150px">
                                        <option value=""></option>
                                        <option value="O"
                                        ${cmCustCpy.placeType=="O" ? 'selected = "selected"' : '' }>自有
                                        </option>
                                        <option value="R"
                                        ${cmCustCpy.placeType=="R" ? 'selected = "selected"' : '' }>租赁
                                        </option>
                                    </select></td>
                                    <td>店铺详细地址:</td>
                                    <td><input class="easyui-textbox" type="text"
                                               name="companyAddress" value="${cmCustCpy.companyAddress}"/></td>
                                </tr>
                            </table>

                            <shiro:hasPermission name="/credit/bl">
                                <table cellpadding="5">
                                    <tr>
                                        <td>不属实项:</td>
                                        <td>
                                            <div id="companyInfo"></div>
                                        </td>
                                        <c:if test="${cmCase.pointStage!='4'}">
                                        <td valign="bottom"><%-- <c:if test="${cmCase.firstAuditResult=='3' and cmCase.lastAuditResult=='3'}">--%>
                                            <a href="javascript:void(0)" class="easyui-linkbutton"
                                               onclick="submitCompanyInfoForm()">提交</a></td>
                                        </c:if>
                                    </tr>
                                </table>
                            </shiro:hasPermission>

                            <input name="groupId" type="hidden" value="HYXJ">
                        </form>
                    </div>
                </div>

                <div class="easyui-panel" title="其他信息" style="width: 100%">
                    <div style="padding: 10px 60px 20px 10px">
                        <form id="baseInfoForm" method="post">
                            <table cellpadding="5">
                                <tr>
                                    <td>教育程度:</td>
                                    <td><input class="easyui-textbox" type="text"
                                               name="eduStatus" value="${cmCust.eduStatus}"/></td>
                                    <td>婚姻状况:</td>
                                    <td><input class="easyui-textbox" type="text"
                                               name="maritalStatus" value="${cmCust.maritalStatus}"/></td>
                                    <td>行业类型:</td>
                                    <td><input class="easyui-textbox" type="text"
                                               name="industryType" value="${cmCust.industryType}"/></td>
                                </tr>
                                <tr>
                                    <td>户口类型:</td>
                                    <td><select class="easyui-combobox" name="personType"
                                                data-options="panelHeight:'auto'" value="${cmCust.personType}"
                                                style="width: 150px">
                                        <option value=""></option>
                                        <option value="L"
                                        ${cmCust.personType=="L" ? 'selected = "selected"' : '' }>本地
                                        </option>
                                        <option value="F"
                                        ${cmCust.personType=="F" ? 'selected = "selected"' : '' }>外地
                                        </option>
                                    </select>
                                    <td>住宅类型:</td>
                                    <td><select class="easyui-combobox" name="roomType"
                                                data-options="panelHeight:'auto'" value="${cmCust.roomType}"
                                                style="width: 150px">
                                        <option value=""></option>
                                        <option value="O"
                                        ${cmCust.roomType=="O" ? 'selected = "selected"' : '' }>自有
                                        </option>
                                        <option value="R"
                                        ${cmCust.roomType=="R" ? 'selected = "selected"' : '' }>租赁
                                        </option>
                                    </select></td>
                                    <td>月收入:</td>
                                    <td><input class="easyui-textbox" type="text"
                                               name="monthlyIncome" value="${cmCust.monthlyIncome}"/></td>
                                </tr>
                            </table>
                            <shiro:hasPermission name="/credit/bl">
                                <table cellpadding="5">
                                    <tr>
                                        <td>不属实项:</td>
                                        <td>
                                            <div id="baseInfo"></div>
                                        </td>
                                        <c:if test="${cmCase.pointStage!='4'}">
                                        <td valign="bottom">
                                            <a href="javascript:void(0)" class="easyui-linkbutton"
                                               onclick="submitBaseInfoForm()">提交</a></td>
                                        </c:if>
                                    </tr>
                                </table>
                            </shiro:hasPermission>

                            <input name="groupId" type="hidden" value="QTXJ">
                        </form>
                    </div>
                </div>

                <div class="easyui-panel" title="紧急联系人" style="width: 100%">
                    <div style="padding: 10px 60px 20px 10px">
                        <form id="busyManForm" method="post">
                            <table cellpadding="5">
                                <tr>
                                    <td>联系人姓名:</td>
                                   <td> <input class="easyui-textbox" type="text"
                                               name="contactName" value="${busyMan.contactName}"/></td>
                                            
                                               
                                               
                                               
                                               </td>
                                               
                                               
                                    <td>联系人电话:</td>
                                    <td><input class="easyui-textbox" type="text"
                                               name="contactPhone" value="${busyMan.contactPhone}"/></td>
                                    <td>关系:</td>
                                    <%-- <td><input class="easyui-textbox" type="text"
                                               name="contactRel" value="${busyMan.contactRel}"/></td> --%>
                                               <td>
                                    <select  id="contactRel" name="contactRel" style="width:150px;" >
                                    <option value=""></option>
									<option value="1">家人</option>
									<option value="2">朋友</option>
									<option value="3">同事</option>
									</select>  
                                </tr>
                            </table>
                            <shiro:hasPermission name="/credit/bl">
                                <table cellpadding="5">
                                    <tr>
                                        <td>不属实项:</td>
                                        <td>
                                            <div id="busyManInfo"></div>
                                        </td>
                                        <c:if test="${cmCase.pointStage!='4'}">
                                        <td valign="bottom">
                                            <a href="javascript:void(0)" class="easyui-linkbutton"
                                               onclick="submitContactsInfoForm()">提交</a></td>
                                        </c:if>
                                    </tr>
                                </table>
                            </shiro:hasPermission>

                            <input name="groupId" type="hidden" value="LXRXJ">
                        </form>
                    </div>
                </div>
                <%--<c:if test="${cmCase.taskStage=='1'}">--%>
                <div class="easyui-panel" title="授权信息(初审)" style="width: 100%">
                    <div style="padding: 10px 60px 20px 10px">
                        <form id="firstAuditForm" method="post">
                            <table cellpadding="5">
                                <tr>
                                    <td>系统评分:</td>
                                    <td><input class="easyui-textbox" type="text"
                                               name="sysPoint" value="${cmCase.sysPoint}" readonly="readonly"/></td>
                                    <td>系统额度:</td>
                                    <td><input class="easyui-textbox" type="text"
                                               name="sysLimit" value="${cmCase.sysLimit}" readonly="readonly"/></td>
                                    <td>初审结果:</td>
                                    <td><select class="easyui-combobox"
                                                name="firstAuditResult" data-options="panelHeight:'auto'"
                                                value="${cmCase.firstAuditResult }" style="width: 80px">
                                        <option value="0"
                                        ${cmCase.firstAuditResult==0 ? 'selected = "selected"' : '' }>待处理
                                        </option>
                                        <option value="1"
                                        ${cmCase.firstAuditResult==1 ? 'selected = "selected"' : '' }>通过
                                        </option>
                                        <option value="2"
                                        ${cmCase.firstAuditResult==2 ? 'selected = "selected"' : '' }>拒绝
                                        </option>
                                    </select></td>
                                </tr>
                            </table>
                            <table cellpadding="5">
                                <tr>
                                    <td>初审意见:</td>
                                     <!-- data-options="multiline:true" -->
                                    <td><input class="easyui-textbox"
                                               data-options="required:true" name="comment"
                                               value="${cmCase.firstAuditView}"
                                               style="width: 300px; height: 100px"></td>
                                    <td valign="bottom">
                                        <shiro:hasPermission name="/credit/submit">
                                            <c:if test="${cmCase.firstAuditResult=='3'}">
                                                <a href="javascript:void(0)" class="easyui-linkbutton"
                                                   onclick="submitFirstAuditForm()">提交</a>
                                            </c:if>
                                        </shiro:hasPermission>
                                        <shiro:hasPermission name="/credit/back">
                                            <c:if test="${cmCase.firstAuditResult=='3'}">
                                                <a href="javascript:void(0)" class="easyui-linkbutton"
                                                   onclick="submitBackSubmitForm('1')">驳回</a>
                                            </c:if>
                                        </shiro:hasPermission>

                                        <shiro:hasPermission name="/credit/bl">
                                            <c:if test="${cmCase.taskStage=='0'}">
                                            <a href="javascript:void(0)" class="easyui-linkbutton"
                                               onclick="submitBlAuditForm()">补录完毕</a>
                                            </c:if>
                                        </shiro:hasPermission>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                </div>
                <%-- </c:if>--%>
                <c:if test="${cmCase.taskStage=='2' }">
                    <div class="easyui-panel" title="授权信息(终审)" style="width: 100%">
                        <div style="padding: 10px 60px 20px 10px">
                            <form id="lastAuditForm" method="post">
                                <table cellpadding="5">
                                    <tr>
                                        <td>系统评分:</td>
                                        <td><input class="easyui-textbox" type="text"
                                                   name="creditPoint" value="${cmCase.sysPoint}"
                                                   readonly="readonly"/></td>
                                        <td>授信额度:</td>
                                        <td><input class="easyui-textbox" type="text"
                                                   name="creditLimit" value="${cmCase.sysLimit}"/></td>
                                        <td>终审结果:</td>
                                        <td><select class="easyui-combobox"
                                                    name="lastAuditResult" data-options="panelHeight:'auto'"
                                                    value="${cmCase.lastAuditResult }" style="width: 80px">
                                            <option value="0"
                                                ${cmCase.lastAuditResult==0 ? 'selected = "selected"' : '' }>待处理
                                            </option>
                                            <option value="1"
                                                ${cmCase.lastAuditResult==1 ? 'selected = "selected"' : '' }>通过
                                            </option>
                                            <option value="2"
                                                ${cmCase.lastAuditResult==2 ? 'selected = "selected"' : '' }>拒绝
                                            </option>
                                        </select></td>
                                    </tr>
                                </table>
                                <table cellpadding="5">
                                    <tr>
                                        <td>终审意见:</td>
                                        <td><input class="easyui-textbox"
                                                   data-options="multiline:true" name="comment"
                                                   value="${cmCase.lastAuditView}"
                                                   style="width: 300px; height: 100px"></td>
                                        <td valign="bottom">
                                            <shiro:hasPermission name="/credit/submit">
                                                <c:if test="${cmCase.lastAuditResult=='3'}">
                                                    <a href="javascript:void(0)" class="easyui-linkbutton"
                                                       onclick="submitLastAuditForm()">提交</a>
                                                </c:if>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="/credit/back">
                                                <c:if test="${cmCase.lastAuditResult=='3'}">
                                                    <a href="javascript:void(0)" class="easyui-linkbutton"
                                                       onclick="submitBackSubmitForm('2')">驳回</a>
                                                </c:if>
                                            </shiro:hasPermission>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
        <div title="证件资料">
            <form id="photoInfoForm" method="post" enctype="multipart/form-data">
                <div class="easyui-panel" title="身份证信息" style="width: 100%">
                    <div style="padding: 10px 60px 20px 10px">
                        <table cellpadding="5">
                            <tr>
                                <td>姓名:</td>
                                <td><input class="easyui-textbox" type="text"
                                           value="${cmCust.custName}" readonly="readonly"/></td>
                                <td>证件号码:</td>
                                <td><input class="easyui-textbox" type="text"
                                           value="${cmCust.custPid}" readonly="readonly"/></td>
                            </tr>
                        </table>
                        <table cellpadding="5">
                            <tr>
                                <td>正面:</td>
                                <td><img id="facePhotoImg" width="350" height="240"
                                         onclick="uploadFacePhoto(this);"/></td>
                                <td>反面:</td>
                                <td colspan="10"><img id="backPhotoImg" width="350"
                                                      height="240" onclick="uploadBackPhoto(this);"/></td>
                            </tr>
                            <tr>
                                <td>手持:</td>
                                <td><img id="handPhotoImg" width="350" height="240"
                                         onclick="uploadHandPhoto(this);"/></td>
                                <td>烟草:</td>
                                <td><img id="merchPhotoImg" width="350" height="240"
                                         onclick="uploadMerchPhoto(this);"/></td>
                            </tr>
                            <tr>
                                <td>店铺:</td>
                                <td><img id="companyPhotoImg" width="350" height="240"
                                         onclick="uploadCompanyPhoto(this);"/></td>
                            </tr>

                            <shiro:hasPermission name="/credit/bl">
                                <tr>
                                    <td colspan="10">不属实项:
                                        <div id="photoInfo"></div>
                                    </td>
                                </tr>
                                <c:if test="${cmCase.pointStage!='4'}">
                                <td><a href="javascript:void(0)"
                                       class="easyui-linkbutton" onclick="submitPhotoInfoForm()">提交</a>
                                </td>
                                </c:if>
                            </shiro:hasPermission>

                        </table>
                    </div>
                </div>
                <input id="facePhoto" type="file" name="facePhoto" hidden="none"
                       onchange="previewFaceImage(this);" multiple="multiple">
                <input id="backPhoto" type="file" name="backPhoto" hidden="none"
                       onchange="previewBackImage(this);" multiple="multiple">
                <input id="handPhoto" type="file" name="handPhoto" hidden="none"
                       onchange="previewHandImage(this);" multiple="multiple">
                <input id="merchPhoto" type="file" name="merchPhoto" hidden="none"
                       onchange="previewMerchImage(this);" multiple="multiple">
                <input id="companyPhoto" type="file" name="companyPhoto" hidden="none"
                       onchange="previewCompanyImage(this);" multiple="multiple">
                <input id="creditPhoto" type="file" name="creditPhoto" hidden="none"
                       onchange="previewCreditImage(this);" multiple="multiple">
                <input name="groupId" type="hidden" value="ZJZXJ">

                <div class="easyui-panel" title="烟草专卖证信息" style="width: 100%">
                    <div style="padding: 10px 60px 20px 10px">
                        <table cellpadding="5">
                            <tr>
                                <td>证件号:</td>
                                <td><input class="easyui-textbox" type="text"
                                           value="${cmCustCpy.liceId}" readonly="readonly"/></td>
                                <td>发证时间:</td>
                                <td><input class="easyui-textbox" type="text"
                                           value="<fmt:formatDate value='${cmCustCpy.grantTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"
                                           readonly="readonly"/></td>
                            </tr>
                        </table>
                        <table cellpadding="5">
                            <tr>
                                <td>照片:</td>
                                <td>营业执照:</td>
                                <td><img id="creditPhotoImg" width="350" height="240"
                                         onclick="uploadCreditPhoto(this);"/></td>
                            </tr>
                        </table>
                    </div>
                </div>
            </form>
        </div>
        <div title="经营数据">
            <div class="easyui-panel" title="订单数据" style="width: 100%">
                <div style="padding: 10px 60px 20px 10px">
                    <table cellpadding="5">
                        <tr>
                            <td>订单周期:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${cmCustBusiness.orCycle}" readonly="readonly"/></td>
                            <td>订单环比:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${cmCustBusiness.orMonthOnMonth}" readonly="readonly"/></td>
                            <td>订单金额/日:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${cmCustBusiness.orMoneyDay}" readonly="readonly"/></td>
                        </tr>
                        <tr>
                            <td>订单同比:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${cmCustBusiness.orYearOnYear}" readonly="readonly"/></td>
                            <td>一年总金额:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${cmCustBusiness.orYearTotalAmount}" readonly="readonly"/></td>
                            <td>订单连续性:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${cmCustBusiness.orContitnuity}" readonly="readonly"/></td>
                        </tr>
                        <tr>
                            <td>订单总金额:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${cmCustBusiness.orTotalAmount}" readonly="readonly"/></td>
                            <td>增长系数:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${cmCustBusiness.or6MonthRation}" readonly="readonly"/></td>
                            <td>六个月总金额:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${cmCustBusiness.or6MonthTotal}" readonly="readonly"/></td>
                        </tr>
                        <tr>
                            <td>六个月平均值:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${cmCustBusiness.orMonth6Avg}" readonly="readonly"/></td>
                            <td>六个月方差:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${cmCustBusiness.orMonth6Variance}" readonly="readonly"/></td>
                            <td>六个月平均差:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${cmCustBusiness.orMonth6MeanDifference}" readonly="readonly"/></td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="easyui-panel" title="收款数据" style="width: 100%;">
                <div style="padding: 10px 60px 20px 10px">
                    <table cellpadding="5">
                        <tr>
                            <td>二维码环比:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${cmCustBusiness.coMonthOnMonth}" readonly="readonly"/></td>
                            <td>二维码第一次消费:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${cmCustBusiness.coConsumeDay}" readonly="readonly"/></td>
                            <td>二维码同比:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${cmCustBusiness.coYearOnYear}" readonly="readonly"/></td>
                        </tr>
                        <tr>
                            <td>一个月总金额:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${cmCustBusiness.coRecentlyMonthAmount}" readonly="readonly"/></td>
                            <td>二维码连续性:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${cmCustBusiness.coContitnuity}" readonly="readonly"/></td>
                            <td>二维码总金额:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${cmCustBusiness.coTotalAmount}" readonly="readonly"/></td>
                        </tr>
                        <tr>
                            <td>六个月增长系数:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${cmCustBusiness.co6MonthRation}" readonly="readonly"/></td>
                            <td>六个月的总金额:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${cmCustBusiness.co6MonthTotal}" readonly="readonly"/></td>
                            <td>六个月平均值:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${cmCustBusiness.coMonth6Avg}" readonly="readonly"/></td>
                        </tr>
                        <tr>
                            <td>六个月方差:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${cmCustBusiness.coMonth6Variance}" readonly="readonly"/></td>
                            <td>六个月平均差:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${cmCustBusiness.coMonth6MeanDifference}" readonly="readonly"/></td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <div title="联系人数据">
            <table class="easyui-datagrid" title="紧急联系人" style="width: 100%"
                   data-options="
				url: '${path}/credit/rushContactDataGrid?caseId='+caseId,
				fitColumns: true,
				rownumbers: true">
                <thead>
                <tr>
                    <th data-options="field:'contactName',width:100">联系人姓名</th>
                    <th data-options="field:'contactPhone',width:100">联系人电话</th>
                    <th data-options="field:'contactRel',width:100">联系人关系</th>
                    <th data-options="field:'createDate',width:100">创建时间</th>
                </tr>
                </thead>
            </table>
            <table class="easyui-datagrid" title="其他联系人" style="width: 100%"
                   data-options="
				url: '${path}/credit/otherContactDataGrid?caseId='+caseId,
				fitColumns: true,
				rownumbers: true">
                <thead>
                <tr>
                    <th data-options="field:'contactName',width:100">联系人姓名</th>
                    <th data-options="field:'contactPhone',width:100">联系人电话</th>
                    <th data-options="field:'contactRel',width:100">联系人关系</th>
                    <th data-options="field:'createDate',width:100">创建时间</th>
                </tr>
                </thead>
            </table>
        </div>
        <div title="审批轨迹">
            <div class="easyui-panel" title="查询结果"
                 style="width: 100%; height: 520px">
                <table id="auditTrackDataGrid" data-options="fit:true,border:false,fitColumns: true"></table>
            </div>
        </div>
        <div title="三方数据">
            <div class="easyui-panel" title="贷前审核报告" style="width: 100%">
                <div style="padding: 10px 60px 20px 10px">
                    <table cellpadding="5">
                        <tr>
                            <td>报告编号:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${reportId }" readonly="readonly"/></td>
                            <td>风险评分:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${score }" readonly="readonly"/></td>
                            <td>风险结果:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${reson }" readonly="readonly"/></td>
                        </tr>
                    </table>
                </div>
            </div>
            <table class="easyui-datagrid" title="风险项明细" style="width: 100%"
                   data-options="fitColumns: true,url:'${path}/credit/threeData?caseId='+caseId">
                <thead>
                <tr>
                    <th data-options="field:'ITEM_ID',width:100">项目编号</th>
                    <th data-options="field:'GROUPS',width:200">所属组</th>
                    <th data-options="field:'ITEM_NAME',width:250">项目名称</th>
                    <th
                            data-options="field:'RISK_LEVEL',width:'100',
					formatter: function(value, row, index) {
						switch (value) { case 'low':return '低';
						case 'medium':return '中';
						case 'high':return '高';}}">风险级别
                    </th>
                </tr>
                </thead>
            </table>
        </div>
        <div title="人脸识别">
            <div class="easyui-panel" title="认证结果" style="width: 100%;">
                <div style="padding: 10px 60px 20px 10px">
                    <table cellpadding="5">
                        <tr>
                            <td>人脸相似度分数:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${cCustFace.score}" readonly="readonly"/></td>
                            <td>原因:</td>
                            <td><input class="easyui-textbox" type="text"
                                       value="${cCustFace.reason}" readonly="readonly"/></td>
                        </tr>
                    </table>
                    <table cellpadding="5">
                        <tr>
                            <td>照片:</td>
                            <td><img id="photoURL" width="350" height="240"/></td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>

        <div title="现场调查">
            <form id="checkePhotoForm" method="post" enctype="multipart/form-data">
                <div class="easyui-panel" title="现场调查图片" style="width: 100%">
                    <div style="padding: 10px 60px 20px 10px">
                        <table cellpadding="5">
                            <tr>
                                <td>现场图1:</td>
                                <td><img id="checkPhotoImg1" width="350" height="240"
                                         onclick="uploadCheckPhoto1(this);"/></td>
                                <td>现场图2:</td>
                                <td colspan="10"><img id="checkPhotoImg2" width="350"
                                                      height="240" onclick="uploadCheckPhoto2(this);"/></td>
                            </tr>

                        </table>
                    </div>
                </div>
                <table cellpadding="5">
                    <tr>
                        <td>备注:</td>
                        <td><input class="easyui-textbox"
                                   data-options="multiline:true" name="checkComment"
                                   value="${checkComment}"
                                   style="width: 300px; height: 100px"></td>
                        <shiro:hasPermission name="/credit/bl">
                            <c:if test="${cmCase.taskStage=='0'}">
                            <td valign="bottom">
                                <a href="javascript:void(0)" class="easyui-linkbutton"
                                   onclick="submitCheckPhotoForm()">提交</a>
                            </td>
                            </c:if>
                        </shiro:hasPermission>
                    </tr>
                </table>
                <input id="checkPhoto1" type="file" name="checkPhoto1" hidden="none"
                       onchange="previewCheckPhotoImage1(this);" multiple="multiple">
                <input id="checkPhoto2" type="file" name="checkPhoto2" hidden="none"
                       onchange="previewCheckPhotoImage2(this);" multiple="multiple">
            </form>
        </div>
        
    </div>


    <input id="photoURLUrl" type="hidden" value="${cCustFace.photoUrl }">
    <input id="facePhotoUrl" type="hidden" value="${cCustCertPhoto.facePhoto }">
    <input id="backPhotoUrl" type="hidden" value="${cCustCertPhoto.backPhoto }">
    <input id="handPhotoUrl" type="hidden" value="${cCustCertPhoto.handPhoto }">
    <input id="merchPhotoUrl" type="hidden" value="${cCustCertPhoto.merchPhoto }">
    <input id="companyPhotoUrl" type="hidden" value="${certPhoto.companyPhoto}">
    <input id="creditPhotoUrl" type="hidden" value="${cCustCertPhoto.creditPhoto }">
    <input id="checkPhotoImg1Url" type="hidden" value="${checkPhoto1 }">
    <input id="checkPhotoImg2Url" type="hidden" value="${checkPhoto2 }">


</div>
