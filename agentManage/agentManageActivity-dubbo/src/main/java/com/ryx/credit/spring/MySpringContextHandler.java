package com.ryx.credit.spring;

import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.DataChangeActivityService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;

/**
 * Created by cx on 2018/5/31.
 */
public class MySpringContextHandler implements ApplicationContextAware {


    public static ApplicationContext applicationContext;

    public static AgentEnterService s_agentEnterService;

    public static DataChangeActivityService s_dataChangeActivityService;

    @PostConstruct
    public void init(){

        s_agentEnterService = agentEnterService;

        s_dataChangeActivityService = dataChangeActivityService;

    }

    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private DataChangeActivityService dataChangeActivityService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public AgentEnterService getAgentEnterService() {
        return agentEnterService;
    }

    public void setAgentEnterService(AgentEnterService agentEnterService) {
        this.agentEnterService = agentEnterService;
    }

    public DataChangeActivityService getDataChangeActivityService() {
        return dataChangeActivityService;
    }

    public void setDataChangeActivityService(DataChangeActivityService dataChangeActivityService) {
        this.dataChangeActivityService = dataChangeActivityService;
    }
}
