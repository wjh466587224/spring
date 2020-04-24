package com.gupaoedu.vip.spring.formework.aop.support;

import java.lang.reflect.Method;
import java.util.List;

public class GPAdvisedSupport {

    private Class<?> targetClass;


    public Class<?> getTargetClass(){
        return this.targetClass;
    }

    public Object getTarget(){
        return  null;
    }

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) throws Exception{

        return null;
    }
}
