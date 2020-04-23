package com.gupaoedu.vip.spring.formework.context;

import com.gupaoedu.vip.spring.formework.beans.GPBeanFactory;
import com.gupaoedu.vip.spring.formework.beans.GPBeanWrapper;
import com.gupaoedu.vip.spring.formework.beans.config.GPBeanDefinition;
import com.gupaoedu.vip.spring.formework.beans.support.GPBeanDefinitionReader;
import com.gupaoedu.vip.spring.formework.beans.support.GPDefaultListableBeanFactory;

import java.util.List;
import java.util.Map;

public class GPApplicationContext extends GPDefaultListableBeanFactory implements GPBeanFactory {


    private GPBeanDefinitionReader reader;

    private String[] configLocaiotns;

    public GPApplicationContext(String... configLocaiotns){
        this.configLocaiotns = configLocaiotns;
        refresh();
    }
    @Override
    protected void refresh() {
        //1.定位配置文件
        reader = new GPBeanDefinitionReader(this.configLocaiotns);

        //2.加载配置文件，扫描相关的类，封装成BeanDefinition
        List<GPBeanDefinition> gpBeanDefinitions = new GPBeanDefinitionReader().loadBeanDefinitions();


        //3.注册 把配置信息放到容器（伪ioc容器 beanDefinitionMap）

        doRegisterBeanDefinition(gpBeanDefinitions);
        //4.把不是延时加载的类提前初始化

        //5.注入
        doAutowrited();
    }

    //只处理非延迟加载的类
    private void doAutowrited() {
        for (Map.Entry<String, GPBeanDefinition> beanDefinitionEntry : super.beanDefinitionMap.entrySet()) {
            String key = beanDefinitionEntry.getKey();
            if(!beanDefinitionEntry.getValue().isLazyInit()){
                getBean(key);
            }
        }
    }

    private void doRegisterBeanDefinition(List<GPBeanDefinition> gpBeanDefinitions) {

        for (GPBeanDefinition gpBeanDefinition : gpBeanDefinitions) {
            super.beanDefinitionMap.put(gpBeanDefinition.getFactoryBeanName(),gpBeanDefinition);
        }

    }

    @Override
    public Object getBean(String beanName) {
        //1.生成实例

        instantiateBean(beanName,new GPBeanDefinition());

        //2.把属性注入

        populateBean(beanName,new GPBeanDefinition(),new GPBeanWrapper());


        return null;
    }

    private void populateBean(String beanName, GPBeanDefinition gpBeanDefinition, GPBeanWrapper gpBeanWrapper) {
    }

    private void instantiateBean(String beanName, GPBeanDefinition gpBeanDefinition) {
    }
}
