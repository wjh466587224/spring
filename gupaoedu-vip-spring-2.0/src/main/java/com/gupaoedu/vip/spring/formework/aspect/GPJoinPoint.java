package com.gupaoedu.vip.spring.formework.aspect;

import java.lang.reflect.Method;


public interface GPJoinPoint {

    Object getThis();

    Object[] getArguments();

    Method getMethod();

    void setUserAttribute(String key, Object value);

    Object getUserAttribute(String s);
}
