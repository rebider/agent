package com.ryx.credit.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.commons.shiro.ShiroUser;
import com.ryx.credit.dao.agent.DataHistoryRecordMapper;
import com.ryx.credit.pojo.admin.agent.DataHistory;
import com.ryx.credit.pojo.admin.agent.DataHistoryRecord;
import com.ryx.credit.service.dict.IdService;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;


public class DataHistoryInterceptor implements MethodInterceptor {

    private static final Logger log = LoggerFactory.getLogger(DataHistoryInterceptor.class);
    @Autowired
    private DataHistoryRecordMapper dataHistoryRecordMapper;
    @Autowired
    private IdService idService;

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        Method method = mi.getMethod();
        String name = method.getName();

        boolean result = name.contains("update") || name.contains("insert") || name.contains("delete");
        String clazz = method.toString();
        if(result && !clazz.contains("CSysLogMapper") && !clazz.contains("DataHistoryRecordMapper") && !clazz.contains("DataHistoryMapper")){
            //获取该方法的传参
            Object[] paramsArr = mi.getArguments();
            Object o = paramsArr[0];
            Map<String, Object> objectMap = JsonUtil.objectToMap(o);

            String params = getParams(paramsArr);
            log.info("请求参数："+params);
            log.info("方法名："+method.getName());
            log.info("类名："+method.toString());
            DataHistoryRecord dataHistory = new DataHistoryRecord();
            dataHistory.setId(idService.genId(TabId.data_history));
            dataHistory.setDataClass(clazz);
            dataHistory.setDataMethod(method.getName());
            dataHistory.setDataParameter(params);
            dataHistory.setDataVersion(Status.STATUS_1.status);
            dataHistory.setcTime(new Date());
            String user = "";
            String cUser = String.valueOf(objectMap.get("cUser"));
            String uUser = String.valueOf(objectMap.get("uUser"));
            if(StringUtils.isNotBlank(cUser) && !cUser.equals("null")){
                user = cUser;
            }else if(StringUtils.isNotBlank(uUser) && !uUser.equals("null")){
                user = uUser;
            }
            dataHistory.setcUser(user);
            String busId = "";
            String id = String.valueOf(objectMap.get("id"));
            String busid = String.valueOf(objectMap.get("busId"));
            if(StringUtils.isNotBlank(id) && !id.equals("null")){
                busId = id;
            }else if(StringUtils.isNotBlank(busid) && !busid.equals("null")){
                busId = busid;
            }
            dataHistory.setBusId(busId);
            dataHistory.setStatus(Status.STATUS_1.status);
            dataHistoryRecordMapper.insert(dataHistory);
        }
        return mi.proceed();
    }

    private String getParams(Object[] params) {
        String paramsStr = "";
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                Object obj = params[i];
                paramsStr += JSON.toJSON(obj)+",";
            }
        }
        return StringUtils.isBlank(paramsStr)?paramsStr:paramsStr.substring(0,paramsStr.length()-1);
    }


}