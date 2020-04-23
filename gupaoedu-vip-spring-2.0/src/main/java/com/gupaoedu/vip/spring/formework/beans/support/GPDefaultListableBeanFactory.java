package com.gupaoedu.vip.spring.formework.beans.support;

import com.gupaoedu.vip.spring.formework.beans.config.GPBeanDefinition;
import com.gupaoedu.vip.spring.formework.context.support.GPAbstractApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class GPDefaultListableBeanFactory extends GPAbstractApplicationContext {


    protected final Map<String,GPBeanDefinition> beanDefinitionMap = new HashMap<String, GPBeanDefinition>();
}
