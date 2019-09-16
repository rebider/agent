<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="代理商基本信息"  data-options="iconCls:'fi-results'">
    <form id="agentBasics">
        <table class="grid">

            <tr>
                <td>代理商名称<input name="id" type="hidden" value="${agent.id}"/></td>
                <td style="width:180px">
                    <input name="agName" id="agentName" type="text"  class="easyui-validatebox"  style="width:120px;" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission>    data-options="required:true" value="${agent.agName}">
                    <a href="javascript:void(0);" onclick="industrialAuth()">工商认证</a></td>
                <input name="caStatus" id="caStatus" type="hidden" value="1">
                </td>
                <td>公司性质</td>
                <td>
                    <select name="agNature" style="width:160px;height:21px" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo"> disabled="true"   readonly="readonly"</shiro:lacksPermission>>
                        <c:forEach items="${agNatureType}" var="agnatureTypeItem"  >
                            <option value="${agnatureTypeItem.dItemvalue}" <c:if test="${agnatureTypeItem.dItemvalue==agent.agNature}">selected="selected"</c:if>>${agnatureTypeItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>注册资本</td>
                <td><input name="agCapital" id="agCapital" type="text"  class="easyui-validatebox"  style="width:120px;"  data-options="required:true,validType:['length[1,20]','Money']" value="${agent.agCapital}" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission>>/万元</td>
                <td>营业执照</td>
                <td><input name="agBusLic" id="agBusLic" type="text"  class="easyui-validatebox"  style="width:120px;"   data-options="required:true,validType:'length[1,30]'" value="${agent.agBusLic}" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission>></td>
            </tr>
            <tr>
                <td>营业执照开始时间</td>
                <td><input name="agBusLicb" id="agBusLicb" type="text"  class="easyui-datebox" editable="false" placeholder="请输入"  value=" <fmt:formatDate pattern="yyyy-MM-dd" value="${agent.agBusLicb}" />"  style="width:120px;"   <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission>/></td>
                <td>营业执照到期日</td>
                <td><input name="agBusLice" id="agBusLice" type="text"  class="easyui-datebox" editable="false" placeholder="请输入"  value=" <fmt:formatDate pattern="yyyy-MM-dd" value="${agent.agBusLice}" />"  style="width:120px;"  <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission>/>
                    <a href="javascript:void(0);" id="wxqId" onclick="setAgBusLice()">无限期</a></td>
                <td>负责人</td>
                <td><input name="agHead" id="agHead" type="text"  class="easyui-validatebox"  style="width:120px;" value="${agent.agHead}"   data-options="required:true,validType:['length[1,6]','CHS']" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission> /></td>
                <td>负责人联系电话</td>
                <td><input name="agHeadMobile" id="agHeadMobile" type="text"  class="easyui-validatebox"  style="width:120px;"  value="${agent.agHeadMobile}"  data-options="required:true,validType:['length[7,12]','Mobile']" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission> /></td>
            </tr>
            <tr>
                <td>法人证件类型</td>
                <td>
                    <select name="agLegalCertype" style="width:160px;height:21px" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo"> disabled="true" </shiro:lacksPermission> >
                        <c:forEach items="${certType}" var="CertTypeItem" varStatus="certTypeStatus" >
                            <option value="${CertTypeItem.dItemvalue}" <c:if test="${CertTypeItem.dItemvalue== agent.agLegalCertype}"> selected="selected"</c:if>>${CertTypeItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>法人证件号</td>
                <td><input name="agLegalCernum" id="agLegalCernum" type="text"  class="easyui-validatebox" value="${agent.agLegalCernum}" editable="false" placeholder="请输入"   style="width:120px;" data-options="required:true,validType:['length[1,18]','IdCard']" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission> /></td>
                <td>法人姓名</td>
                <td>
                    <input name="agLegal" id="agLegal" type="text"  class="easyui-validatebox"  value="${agent.agLegal}" style="width:120px;"   data-options="required:true,validType:['length[1,10]','CHS']" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission> >
                    <a href="javascript:void(0);" onclick="livenessEditAuth()">身份认证</a></td>
                <input id="livenessStatus" type="hidden" value="1">
                </td>
                <td>法人联系电话</td>
                <td><input name="agLegalMobile" id="agLegalMobile" type="text"  class="easyui-validatebox" value="${agent.agLegalMobile}"  style="width:120px;" data-options="required:true,validType:['length[7,12]','Mobile']" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission> ></td>
            </tr>
            <tr>
                <td>注册地区</td>
                <td>
                    <input type="text"  input-class="easyui-validatebox" id="agRegAreaText" style="width:100px;"  data-options="required:true"  readonly="readonly" value="<agent:show type="region" busId="${agent.agRegArea}"/>">
                    <input name="agRegArea" type="hidden" id="agRegArea" value="${agent.agRegArea}"/>
                    <a href="javascript:void(0);" onclick="showRegionFrame({target:this,callBack:returnAgRegRegionEdit},'/region/regionShowAreaTree',false)">选择</a>
                    <a href="javascript:void(0);" onclick="del(this)">清除</a>
                </td>
                <td>注册地址</td>
                <td colspan="6"><input name="agRegAdd" id="agRegAdd" type="text"  class="easyui-validatebox" value="${agent.agRegAdd}"  style="width:80%;"   data-options="required:true,validType:'length[1,333]'" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission> /></td>
            </tr>
            <tr>
                <%--<td>税点</td>--%>
                <%--<td><input name="cloTaxPoint" type="text"  class="easyui-validatebox"  style="width:80%;" value="${agent.cloTaxPoint}" readonly="readonly" data-options="required:true,validType:['length[1,11]','Money']" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission> /></td>--%>
                <td>营业范围</td>
                <td><input name="agBusScope" id="agBusScope" type="text"  class="easyui-validatebox"  style="width:80%;" value="${agent.agBusScope}" data-options="required:true,validType:'length[1,3000]'" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission> /></td>
                <td>业务对接大区</td>
                <td>
                    <input type="text"  class="easyui-validatebox" id="agDocDistrict1" style="width:60%;"  data-options="required:true" readonly="readonly" value="<agent:show type="dept" busId="${agent.agDocDistrict}"/>" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission> />
                    <input name="agDocDistrict" type="hidden" id="agDocDistrict2" value="${agent.agDocDistrict}"/>
                    <shiro:hasPermission name="/agentEnter/agentEdit/baseInfo">
                        <a href="javascript:void(0);" onclick="showRegionFrame({target:this,callBack:returnBaseRegion},'/region/departmentTree',false)">选择</a>
                    </shiro:hasPermission>
                    <a href="javascript:void(0);" onclick="del(this)">清除</a>
                </td>
                <td>业务对接省区</td>
                <td>
                    <input type="text"  class="easyui-validatebox" id="agDocPro1" style="width:60%;"  data-options="required:true" readonly="readonly" value="<agent:show type="dept" busId="${agent.agDocPro}" />" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission> />
                    <input name="agDocPro" type="hidden" value="${agent.agDocPro}" id="agDocPro2"/>
                    <shiro:hasPermission name="/agentEnter/agentEdit/baseInfo">
                        <a href="javascript:void(0);" onclick="showRegionFrame({target:this,callBack:returnBaseRegion,pid:$(this).parent().prev('td').prev('td').children('input[name=\'agDocDistrict\']').val()},'/region/departmentTree',false)">选择</a>
                    </shiro:hasPermission>
                    <a href="javascript:void(0);" onclick="del(this)">清除</a>
                </td>
            </tr>
            <tr>
                <td>投诉及风险风控对接邮箱</td>
                 <td><input name="busRiskEmail" id="busRiskEmail1" type="text"  class="easyui-validatebox"  style="width:160px;" data-options="required:true,validType:'Email'" value="${agent.busRiskEmail}" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo"> readonly="readonly" </shiro:lacksPermission> ></td>
                 <td>分润对接邮箱</td>
                 <td><input name="busContactEmail" type="text" id="busContactEmail1"  class="easyui-validatebox"  style="width:160px;"  data-options="required:true,validType:'Email'" value="${agent.busContactEmail}" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo"> readonly="readonly" </shiro:lacksPermission> ></td>
            </tr>
            <tr>
                <td>备注</td>
                <td colspan="5"><input name="agRemark" type="text"  class="easyui-validatebox" value="${agent.agRemark}"  style="width:80%;" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission> /></td>
                <td>是否有效</td>
                <td>
                    <select name="status" style="width:160px;height:21px" >
                        <c:forEach items="${yesOrNoIsYes}" var="yesOrNoIsYesItem"  >
                            <option value="${yesOrNoIsYesItem.dItemvalue}">${yesOrNoIsYesItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">

    function setAgBusLice() {
        $("#agBusLice").datebox("setValue", "2099-12-31");
    }
    //获取form数据
    function get_editAgentBasics_FormData(){
        return baseData = $.serializeObject($("#agentBasics"));
    }
    //地区选择
    function returnBaseRegion(data,options){
        $(options.target).prev("input").val(data.id);
        $(options.target).prev("input").prev("input").val(data.text);
    }

    //注册地区选择
    function returnAgRegRegionEdit(data,options){
        if(data.tType!='3' && data.id!='710000' && data.id!='810000' && data.id!='820000'){
            info("注册地区必须精确到区");
            return false;
        }
        $(options.target).prev("input").val(data.id);
        $(options.target).prev("input").prev("input").val(data.text);
    }

    function basicsVerifyIntegrity(){
        var formData = get_editAgentBasics_FormData();

        var table = $('#agentBasics .grid');

        if (formData.agName == ''){
            if (confirm("请填写代理商名称!")) {     //代理商名称
                table.find('[name=agName]').focus();
            }
            else {
                table.find('[name=agName]').focus();
            }
            return false;
        }
        if (formData.agCapital == ''){  //注册资本
            if (confirm("请填写注册资本!")) {
                table.find('[name=agCapital]').focus();
            }
            else {
                table.find('[name=agCapital]').focus();
            }
            return false;
        }
        if (formData.agBusLic == ''){     //营业执照
            if (confirm("请填写营业执照!")) {
                table.find('[name=agBusLic]').focus();
            }
            else {
                table.find('[name=agBusLic]').focus();
            }
            return false;
        }
        if (formData.agHead == ''){     //负责人
            if (confirm("请填写负责人!")) {
                table.find('[name=agHead]').focus();
            }
            else {
                table.find('[name=agHead]').focus();
            }
            return false;
        }
        if (formData.agHeadMobile == ''){     //负责人联系电话
            if (confirm("请填写负责人联系电话!")) {
                table.find('[name=agHeadMobile]').focus();
            }
            else {
                table.find('[name=agHeadMobile]').focus();
            }
            return false;
        }
        if (formData.agLegalMobile == ''){      //法人联系电话
            if (confirm("请填写法人联系电话!")) {
                table.find('[name=agLegalMobile]').focus();
            }
            else {
                table.find('[name=agLegalMobile]').focus();
            }
            return false;
        }
        if (formData.agRegArea == ''){      //注册地区
            info("请选择注册地区!");
            return false;
        }
        if (formData.agRegAdd == ''){      //注册地址
            if (confirm("请填写注册地址!")) {
                table.find('[name=agRegAdd]').focus();
            }
            else {
                table.find('[name=agRegAdd]').focus();
            }
            return false;
        }
        if (formData.agBusScope == ''){      //营业范围
            if (confirm("请填写营业范围!")) {
                table.find('[name=agBusScope]').focus();
            }
            else {
                table.find('[name=agBusScope]').focus();
            }
            return false;
        }
        if (formData.agDocDistrict == ''){      //业务对接大区
            info("请选择业务对接大区!");
            return false;
        }
        if (formData.agDocPro == ''){      //业务对接省区
            info("请选择业务对接省区!");
            return false;
        }
        if (formData.busRiskEmail == ''){      //投诉及风险风控对接邮箱
            if (confirm("请填写投诉及风险风控对接邮箱!")) {
                table.find('[name=busRiskEmail]').focus();
            }
            else {
                table.find('[name=busRiskEmail]').focus();
            }
            return false;
        }
        if (formData.busContactEmail == ''){      //分润对接邮箱
            if (confirm("请填写分润对接邮箱!")) {
                table.find('[name=busContactEmail]').focus();
            }
            else {
                table.find('[name=busContactEmail]').focus();
            }
            return false;
        }
        return true;
    }

</script>
