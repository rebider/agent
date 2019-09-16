<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>
<script>
    var nodeList;
    //选中数据
    var bufdata="${bufdata}".length>0?"${bufdata}".split(","):[];
    var busRegionTree = null;
    $(function(){
        $('#busRegionTree').tree({
            url:'/region/posRegionTree?bufdata=${bufdata}',
            method:'get',
            animate:true,
            cascadeCheck:true,
            checkbox:true,
            onBeforeExpand:function(node){
                var t = $(this);
                var opts = t.tree('options');
                if(node && node!=undefined){
                    opts.url = opts.url.substr(0,opts.url.indexOf("?"));
                    $('#busRegionTree').tree('options').url = opts.url+"?bufdata=${bufdata}&pCode=" + node.id;
                }else{
                    $('#busRegionTree').tree('options').url = opts.url;
                }
            },
            onLoadSuccess : function(node, data) {

            }
        });
        busRegionTree = $('#busRegionTree');
    });
</script>
<div class="easyui-panel" style="padding:5px">
    <%--<ul id="busRegionTree" class="easyui-tree" data-options="url:'/region/posRegionTree',method:'get',animate:true,loadFilter:myLoadFilter,checkbox:true"></ul>--%>
    <ul id="busRegionTree" class="easyui-tree" ></ul>
</div>