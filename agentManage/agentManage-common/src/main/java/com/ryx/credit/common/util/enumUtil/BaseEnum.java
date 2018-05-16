package com.ryx.credit.common.util.enumUtil;

import org.omg.CORBA.*;

import java.io.Serializable;

public interface BaseEnum<E extends Enum<?>, T> {
    public T getValue();  
}  
