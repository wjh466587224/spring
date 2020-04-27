package com.gupaoedu.vip.spring.formework.aop;

import com.gupaoedu.vip.spring.formework.aop.intercept.GPMethodInvocation;
import com.gupaoedu.vip.spring.formework.aop.support.GPAdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class GPJdkDynamicAopProxy implements GPAopProxy, InvocationHandler {


    private GPAdvisedSupport advised;

    public GPJdkDynamicAopProxy(GPAdvisedSupport config){
        this.advised = config;

    }
    @Override
    public Object getProxy() {
        return getProxy(this.advised.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {

        return Proxy.newProxyInstance(classLoader,this.advised.getTargetClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object> list = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, this.advised.getTargetClass());

        GPMethodInvocation gpMethodInvocation = new GPMethodInvocation(proxy, this.advised.getTarget(), method, args, this.advised.getTargetClass(),list);


        return gpMethodInvocation.proceed();
    }
}
