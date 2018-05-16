package com.ryx.credit.commons.annotation;


import com.ryx.credit.commons.interceptor.Interceptor;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Before {
	Class<? extends Interceptor>[] value();
}
