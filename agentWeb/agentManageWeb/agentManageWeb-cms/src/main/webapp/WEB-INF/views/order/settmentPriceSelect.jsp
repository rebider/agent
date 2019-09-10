<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    function getSETTLEMENT_PRICESTR_TABLE(){
        var str = "";
        $("#SETTLEMENT_PRICESTR_TABLE .trClass").each(function(index,element){
            var bfh = $(this).find("input[name='bfh']").val();
            var price = $(this).find("input[name='price']").val();
            if(bfh!=undefined && bfh.length > 0 ){
                if(!isFloat(bfh)){
                    info("结算价必须是小数型");
                    return "";
                }
                if(bfh > 1){
                    info("结算价不能大于1");
                    return "";
                }
                str = str + $(this).find("#itemName").html() + bfh + "%" + ","+price+"元/笔;";
            }
        });
        return str;
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <table class="grid" width="100%" id="SETTLEMENT_PRICESTR_TABLE">
        <c:forEach items="${SETTLEMENT_PRICESTR}" var="SETTLEMENT_PRICESTR_item">
          <tr class="trClass">
              <td id="itemName">${SETTLEMENT_PRICESTR_item.dItemname}:</td>
              <td><input type="text" name="bfh" value="" style="width: 100px"/>%</td>
              <td><input type="text" name="price" value="" style="width: 100px"/>元/笔</td>
              <td>(选填)</td>
          </tr>
        </c:forEach>
    </table>
</div>