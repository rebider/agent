package com.ryx.credit.activity.service.impl;

import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

/**
 * CreateActivityAspect
 * Created by IntelliJ IDEA.
 *
 * @author Wang Qi
 * @version 1.0 2018/8/14 16:20
 * @see CreateActivityAspect
 * To change this template use File | Settings | File Templates.
 */
@Component
@Aspect
public class CreateActivityAspect {
    @Autowired
    private StandaloneProcessEngineConfiguration processEngineConfiguration;

    @Before("execution(* com.ryx.credit.activity.service.impl.ActivityServiceImpl.*(..))")
    private void pre(JoinPoint pjp) {
        if (ActivityServiceImpl.processEngine == null) {
            ActivityServiceImpl.processEngine = processEngineConfiguration
                    .buildProcessEngine();
        }

    }
}
