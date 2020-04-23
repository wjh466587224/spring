package com.gupaoedu.vip.spring.formework.beans.config;

import lombok.Data;

@Data
public class GPBeanDefinition {

    private String factoryBeanName;

    private String beanClassName;

    private boolean lazyInit = false;
}
