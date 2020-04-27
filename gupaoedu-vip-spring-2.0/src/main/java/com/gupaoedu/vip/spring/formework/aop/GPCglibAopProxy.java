package com.gupaoedu.vip.spring.formework.aop;

import com.gupaoedu.vip.spring.formework.aop.support.GPAdvisedSupport;

public class GPCglibAopProxy implements GPAopProxy {

    private GPAdvisedSupport advised;

    public GPCglibAopProxy(GPAdvisedSupport config){
        this.advised = config;

    }
    @Override
    public Object getProxy() {
        return null;
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return null;
    }
}
