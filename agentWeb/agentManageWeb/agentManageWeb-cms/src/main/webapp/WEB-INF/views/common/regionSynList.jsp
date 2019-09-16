<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var regionTree;
    var nodeList;
    $(function() {
        regionTree = $('#regionTree').tree({
            url : "${url}",
            lines : true,
            checkbox:${isCheckbox},
            onClick : function(node) {
                nodeList = node;
            },
            onCheck : function(node) {
                nodeList = $('#regionTree').tree('getChecked');
            },
            onLoadSuccess: function () {
            }
        });
    });
</script>
<div data-options="region:'west',border:true,split:false,title:'地区树'"  style="overflow: hidden; ">
    <ul id="regionTree" style="margin: 10px 10px 10px 10px"></ul>
</div>