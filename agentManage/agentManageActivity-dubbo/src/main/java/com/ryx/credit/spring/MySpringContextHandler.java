package com.ryx.credit.spring;

import com.ryx.credit.service.agent.AgentEnterService;
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


    @PostConstruct
    public void init(){
        s_agentEnterService = agentEnterService;
    }

    @Autowired
    private AgentEnterService agentEnterService;

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
}
