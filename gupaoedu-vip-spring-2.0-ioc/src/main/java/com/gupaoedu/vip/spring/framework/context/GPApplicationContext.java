package com.gupaoedu.vip.spring.framework.context;


import com.gupaoedu.vip.spring.framework.beans.config.GPBeanDefinition;
import com.gupaoedu.vip.spring.framework.beans.support.GPBeanDefinitionReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 职责：完成Bean的创建和DI
 */
public class GPApplicationContext {

    private GPBeanDefinitionReader reader;

    private Map<String,GPBeanDefinition> beanDefinitionMap = new HashMap<String, GPBeanDefinition>();

    public GPApplicationContext(String... configLocations) {
        try {
            //加载配置文件
            reader=  new GPBeanDefinitionReader(configLocations);

            //解析配置文件，封装成BeanDefinition
            List<GPBeanDefinition> beanDefinitionList = reader.loadBeanDefinitions();

            //把BeanDefintion缓存起来
            doRegistBeanDefinition(beanDefinitionList);

            //依赖注入
            doAutowrited();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void doAutowrited() {

        for (Map.Entry<String, GPBeanDefinition> map : beanDefinitionMap.entrySet()) {
            String beanName = map.getKey();
            getBean(beanName);
        }
    }

    private void doRegistBeanDefinition(List<GPBeanDefinition> beanDefinitionList) throws Exception{

        for (GPBeanDefinition gpBeanDefinition : beanDefinitionList) {
            if(this.beanDefinitionMap.containsKey(gpBeanDefinition.getFactoryBeanName())){
                throw new Exception("The " + gpBeanDefinition.getFactoryBeanName() + "is exists");
            }
            beanDefinitionMap.put(gpBeanDefinition.getFactoryBeanName(),gpBeanDefinition);
            beanDefinitionMap.put(gpBeanDefinition.getBeanClassName(),gpBeanDefinition);
        }
    }

    public Object getBean(String className){

        //获取beanDefinition
        GPBeanDefinition beanDefinition = beanDefinitionMap.get(className);

        //实例化

        //封装成BeanWrapper

        //保存到IoC容器

        //执行依赖注入
        return null;
    }

    public Object getBean(Class beanClass){
        return getBean(beanClass.getName());
    }
}
