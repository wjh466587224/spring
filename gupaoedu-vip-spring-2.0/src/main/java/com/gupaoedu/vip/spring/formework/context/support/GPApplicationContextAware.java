package com.gupaoedu.vip.spring.formework.context.support;


//通过解耦方式获得ioc容器的底层设计
//通过一个监听器扫描所有的类，只要实现了此接口，自动调用setApplicationContext方法
//从而将ioc容器注入到目标类
public interface GPApplicationContextAware {

    void setApplicationContext();
}
