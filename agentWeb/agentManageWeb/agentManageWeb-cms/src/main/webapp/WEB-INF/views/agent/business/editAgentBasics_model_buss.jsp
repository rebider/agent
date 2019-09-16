<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="代理商基本信息"  data-options="iconCls:'fi-results'">
    <form id="agentBasicsInformation">
        <table class="grid">
            <tr>
                <td>代理商名称<input name="id" type="hidden" value="${agent.id}" readonly="readonly"/></td>
                <td style="width:180px">
                    <input name="agName" id="agentName" type="text"  class="easyui-validatebox" readonly="readonly" style="width:120px;" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission>    data-options="required:true" value="${agent.agName}">
                    <a href="javascript:void(0);">工商认证</a></td>
                <input name="caStatus" id="caStatus" type="hidden" value="0" readonly="readonly">
                </td>
                <td>公司性质</td>
                <td>
                    <select id="agNature" name="agNature" style="width:160px;height:21px" disabled="disabled"  <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">    readonly="readonly"</shiro:lacksPermission>>
                        <c:forEach items="${agNatureType}" var="agnatureTypeItem"  >
                            <option value="${agnatureTypeItem.dItemvalue}" <c:if test="${agnatureTypeItem.dItemvalue==agent.agNature}">selected="selected"</c:if>>${agnatureTypeItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>注册资本</td>
                <td><input name="agCapital" type="text"  readonly="readonly" class="easyui-validatebox"  style="width:120px;"  data-options="required:true,validType:['length[1,20]','Money']" value="${agent.agCapital}" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission>>/万元</td>
                <td>营业执照</td>
                <td><input name="agBusLic" type="text"  readonly="readonly" class="easyui-validatebox"  style="width:120px;"   data-options="required:true,validType:'length[1,30]'" value="${agent.agBusLic}" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission>></td>
            </tr>
            <tr>
                <td>营业执照开始时间</td>
                <td><input name="agBusLicb" id="agBusLicb" readonly="readonly" type="text"  class="easyui-datebox" editable="false" placeholder="请输入"  value=" <fmt:formatDate pattern="yyyy-MM-dd" value="${agent.agBusLicb}" />"  style="width:120px;"  data-options="required:true" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission>/></td>
                <td>营业执照到期日</td>
                <td><input name="agBusLice" id="agBusLice" type="text" readonly="readonly" class="easyui-datebox" editable="false" placeholder="请输入" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${agent.agBusLice}" />"  style="width:120px;"  data-options="required:true"<shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission>/></td>
                <td>负责人</td>
                <td><input name="agHead" type="text" readonly="readonly" class="easyui-validatebox"  style="width:120px;" value="${agent.agHead}"   data-options="required:true,validType:['length[1,6]','CHS']" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission> /></td>
                <td>负责人联系电话</td>
                <td><input name="agHeadMobile" type="text" readonly="readonly"  class="easyui-validatebox"  style="width:120px;"  value="${agent.agHeadMobile}"  data-options="required:true,validType:['length[7,12]','Mobile']" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission> /></td>
            </tr>
            <tr>
                <td>法人证件类型</td>
                <td>
                    <select name="agLegalCertype" id="agLegalCertype" style="width:160px;height:21px" disabled="true" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo"> disabled="true" </shiro:lacksPermission> >
                        <c:forEach items="${certType}" var="CertTypeItem" varStatus="certTypeStatus" >
                            <option value="${CertTypeItem.dItemvalue}" <c:if test="${CertTypeItem.dItemvalue== agent.agLegalCertype}"> selected="selected"</c:if>>${CertTypeItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>法人证件号</td>
                <td><input name="agLegalCernum" readonly="readonly" id="agLegalCernum" type="text"  class="easyui-validatebox" value="${agent.agLegalCernum}" editable="false" placeholder="请输入"   style="width:120px;" data-options="required:true,validType:['length[1,18]','IdCard']" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission> /></td>
                <td>法人姓名</td>
                <td>
                    <input name="agLegal" id="agLegal" type="text"  readonly="readonly" class="easyui-validatebox"  value="${agent.agLegal}" style="width:120px;"   data-options="required:true,validType:['length[1,10]','CHS']" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission> >
                    <a href="javascript:void(0);" >身份认证</a></td>
                <input id="livenessStatus" type="hidden" value="0">
                </td>
                <td>法人联系电话</td>
                <td><input name="agLegalMobile" type="text"  readonly="readonly" class="easyui-validatebox" value="${agent.agLegalMobile}"  style="width:120px;" data-options="required:true,validType:['length[7,12]','Mobile']" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission> ></td>
            </tr>
            <tr>
                <td>注册地区</td>
                <td>
                    <input type="text" readonly="readonly" input-class="easyui-validatebox" id="agRegAreaText" style="width:100px;"  data-options="required:true"  readonly="readonly" value="<agent:show type="region" busId="${agent.agRegArea}"/>">
                    <input name="agRegArea" readonly="readonly" type="hidden" id="agRegArea" value="${agent.agRegArea}"/>
                    <a href="javascript:void(0);" )>选择</a>
                    <a href="javascript:void(0);" >清除</a>
                </td>
                <td>注册地址</td>
                <td colspan="6"><input name="agRegAdd" readonly="readonly" type="text"  class="easyui-validatebox" value="${agent.agRegAdd}"  style="width:80%;"   data-options="required:true,validType:'length[1,333]'" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission> /></td>
            </tr>
            <tr>
                <%--<td>税点</td>--%>
                <%--<td><input name="cloTaxPoint" type="text"  class="easyui-validatebox"  style="width:80%;" value="${agent.cloTaxPoint}" readonly="readonly" data-options="required:true,validType:['length[1,11]','Money']" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission> /></td>--%>
                <td>营业范围</td>
                <td><input name="agBusScope" type="text" readonly="readonly"  class="easyui-validatebox"  style="width:80%;" value="${agent.agBusScope}" data-options="required:true,validType:'length[1,3000]'" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission> /></td>
                <td>业务对接大区</td>
                <td>
                    <input type="text"  readonly="readonly" class="easyui-validatebox" id="agDocDistrict1" style="width:60%;"  data-options="required:true" readonly="readonly" value="<agent:show type="dept" busId="${agent.agDocDistrict}"/>" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission> />
                    <input name="agDocDistrict" readonly="readonly" type="hidden" id="agDocDistrict2" value="${agent.agDocDistrict}"/>
                    <shiro:hasPermission name="/agentEnter/agentEdit/baseInfo">
                        <a href="javascript:void(0);" )">选择</a>
                    </shiro:hasPermission>
                    <a href="javascript:void(0);" >清除</a>
                </td>
                <td>业务对接省区</td>
                <td>
                    <input type="text"  readonly="readonly" class="easyui-validatebox" id="agDocPro1" style="width:60%;"  data-options="required:true" readonly="readonly" value="<agent:show type="dept" busId="${agent.agDocPro}" />" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission> />
                    <input name="agDocPro"  readonly="readonly" type="hidden" value="${agent.agDocPro}" id="agDocPro2"/>
                    <shiro:hasPermission name="/agentEnter/agentEdit/baseInfo">
                        <a href="javascript:void(0);">选择</a>
                    </shiro:hasPermission>
                    <a href="javascript:void(0);">清除</a>
                </td>
            </tr>
            <tr>
                <td>投诉及风险风控对接邮箱</td>
                 <td><input name="busRiskEmail" readonly="readonly" id="busRiskEmail1" type="text"  class="easyui-validatebox"  style="width:160px;"  value="${agent.busRiskEmail}" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo"> readonly="readonly" </shiro:lacksPermission> ></td>
                 <td>分润对接邮箱</td>
                 <td><input name="busContactEmail" readonly="readonly" type="text" id="busContactEmail1"  class="easyui-validatebox"  style="width:160px;"   value="${agent.busContactEmail}" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo"> readonly="readonly" </shiro:lacksPermission> ></td>
            </tr>
            <tr>
                <td>备注</td>
                <td colspan="5"><input name="agRemark" readonly="readonly" type="text"  class="easyui-validatebox" value="${agent.agRemark}"  style="width:80%;" <shiro:lacksPermission name="/agentEnter/agentEdit/baseInfo">readonly="readonly"</shiro:lacksPermission> /></td>
                <td>是否有效</td>
                <td>
                    <select name="status" style="width:160px;height:21px" disabled="disabled" id="status">
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

    //获取form数据
    function get_editAgentBasics(){
        $("#agNature").removeAttr("disabled");
        $("#status").removeAttr("disabled");
        $("#agLegalCertype").removeAttr("disabled");
        return agentData = $.serializeObject($("#agentBasicsInformation"));
    }

</script>