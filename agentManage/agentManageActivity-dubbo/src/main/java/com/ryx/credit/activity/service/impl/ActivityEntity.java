package com.ryx.credit.activity.service.impl;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * ActivityEntity
 * Created by IntelliJ IDEA.
 *
 * @author Wang Qi
 * @version 1.0 2018/8/14 16:20
 * @see ActivityEntity
 * To change this template use File | Settings | File Templates.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityEntity {
    boolean value();
}
