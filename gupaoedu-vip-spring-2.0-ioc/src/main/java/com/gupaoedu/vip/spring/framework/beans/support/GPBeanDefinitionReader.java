package com.gupaoedu.vip.spring.framework.beans.support;

import com.gupaoedu.vip.spring.framework.beans.config.GPBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GPBeanDefinitionReader {

    private Properties contextConfig = new Properties();

    private List<String> regitryBeanClasses = new ArrayList<String>();
    public GPBeanDefinitionReader(String[] configLocations) {

        //加载配置文件

        doLoadConfig(configLocations[0]);

        //扫描配置文件中的配置的相关的类

        doScanner(contextConfig.getProperty("scanPackage"));
    }

    private void doScanner(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));

        File file = new File(url.getFile());

        for (File listFile : file.listFiles()) {
            if(!listFile.isDirectory()){
                doScanner(scanPackage+listFile.getName());
            }else{
                if(!listFile.getName().endsWith(".class")){continue;}

                //全类名=包名.类名
                String className = (scanPackage+"."+listFile.getName().replaceAll(".class",""));

                regitryBeanClasses.add(className);
            }
        }
    }

    private void doLoadConfig(String configLocation) {

        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(configLocation.replaceAll("classpath:", ""));
        try {
            contextConfig.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != resourceAsStream){
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<GPBeanDefinition> loadBeanDefinitions() {

        List<GPBeanDefinition> list = new ArrayList<GPBeanDefinition>();

        for(String bean:regitryBeanClasses){

            try {
                Class<?> clazz = Class.forName(bean);

                list.add(doCreateBeanDefinition(toLowerFirstCase(clazz.getSimpleName()), clazz.getName()));

                //接口
                for (Class<?> anInterface : clazz.getInterfaces()) {
                    list.add(doCreateBeanDefinition(anInterface.getName(),clazz.getName()));
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

        return list;
    }

    private GPBeanDefinition doCreateBeanDefinition(String beanName, String beanClassName) {
        GPBeanDefinition beanDefinition = new GPBeanDefinition();
        beanDefinition.setFactoryBeanName(beanName);
        beanDefinition.setBeanClassName(beanClassName);
        return beanDefinition;
    }

    private String toLowerFirstCase(String simpleName) {
        char [] chars = simpleName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
