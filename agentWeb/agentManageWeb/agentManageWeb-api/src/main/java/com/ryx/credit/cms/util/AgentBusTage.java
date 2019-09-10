package com.ryx.credit.cms.util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by RYX on 2018/7/9.
 */
public class AgentBusTage  extends SimpleTagSupport {

    private String busId;

    private String type;

    @Override
    public void doTag() throws JspException, IOException {
       try{

           switch (type){
               case "BUS_ROOT":
                   Map data =  ServiceFactory.agentBusinfoService.getRootFromBusInfo(new ArrayList<>(),busId);
                   if(null!=data) {
                       getJspContext().getOut().write(data.get("ID")+"");
                   }
                   break;
               default:
                    break;
           }


       }catch (Exception e){
           e.printStackTrace();
       }
    }


    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
