<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>
<script>
    var nodeList;
    function myLoadFilter(data, parent){
        var t = $(this);
        var opts = t.tree('options');
        opts.onBeforeExpand = function(node){
            if(node && node!=undefined){
                if(opts.url.indexOf("?")!=-1 ){
                    opts.url = opts.url.substr(0,opts.url.indexOf("?"));
                    $('#regionTree').tree('options').url = opts.url+"?pCode=" + node.id;
                }else{
                    $('#regionTree').tree('options').url = opts.url+"?pCode=" + node.id;
                }
            }else{
                $('#regionTree').tree('options').url = opts.url;
            }

        };
        opts.onClick = function(node) {
            nodeList = node;
        };
        opts.onCheck = function(node) {
            nodeList = $('#regionTree').tree('getChecked');
        };
        return data;
    }
</script>
<div class="easyui-panel" style="padding:5px">
    <ul id="regionTree" class="easyui-tree" data-options="url:'${url}',method:'get',animate:true,loadFilter:myLoadFilter,checkbox:${isCheckbox}"></ul>
</div>