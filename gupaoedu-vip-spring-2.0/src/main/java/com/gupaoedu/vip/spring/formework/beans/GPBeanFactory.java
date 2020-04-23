package com.gupaoedu.vip.spring.formework.beans;

//单例工厂的顶层
public interface GPBeanFactory {

    /*
    *根据beanName从ioc容器中获取一个实例的bean
    */
    Object getBean(String beanName);
}
