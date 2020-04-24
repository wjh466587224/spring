package com.gupaoedu.vip.spring.formework.aop.intercept;

/**
 */
public interface GPMethodInterceptor {
    Object invoke(GPMethodInvocation invocation) throws Throwable;
}
