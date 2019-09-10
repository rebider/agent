<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="${staticPath }/static/agent/region.js?version=20180907" charset="utf-8"></script>
<script type="text/javascript" src="${staticPath }/static/agent/agentBusinfoSelect.js?version=20180907" charset="utf-8"></script>
<script type="text/javascript" src="${staticPath }/static/agent/multFileUpload.js?version=20180907" charset="utf-8"></script>
<style type="text/css">
    .star{
        color:red;
        font-size:20px;
        text-align: center;
        margin-left: 10px;
        margin-top: 60px;
    }
</style>
<script type="text/javascript">
    $(function () {
        $(".dyTime").datebox({
            onSelect:function(beginDate){
                var date = new Date();
                if(beginDate>date){
                    info("时间不能大于当前时间");
                    $(this).datebox('setValue','');
                }
            }
        });
    });
</script>