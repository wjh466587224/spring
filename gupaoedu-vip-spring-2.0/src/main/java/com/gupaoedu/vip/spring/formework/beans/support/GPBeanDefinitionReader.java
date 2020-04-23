package com.gupaoedu.vip.spring.formework.beans.support;


import com.gupaoedu.vip.spring.formework.beans.config.GPBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GPBeanDefinitionReader {

    private Properties config = new Properties();

    private final String SACN_PACKAGE = "scanPackage";


    private List<String> registyBeanClasses = new ArrayList<String>();

    public GPBeanDefinitionReader(String... locations){
        //通过url定位找到所对应的文件转换成文件流读取
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath:", ""));

        try {
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        doScanner(config.getProperty(SACN_PACKAGE));

    }

    private void doScanner(String scanPackage) {
        //转换为文件路径，实际上就是把.替换为/就OK了
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.","/"));
        File classPath = new File(url.getFile());
        for (File file : classPath.listFiles()) {
            if(file.isDirectory()){
                doScanner(scanPackage + "." + file.getName());
            }else{
                if(!file.getName().endsWith(".class")){ continue;}
                String className = (scanPackage + "." + file.getName().replace(".class",""));
                registyBeanClasses.add(className);
            }
        }
    }

    public Properties getConfig(){
        return this.config;
    }

    public List<GPBeanDefinition> loadBeanDefinitions(){
        List<GPBeanDefinition> result = new ArrayList<GPBeanDefinition>();
        for (String className : registyBeanClasses) {
            GPBeanDefinition gpBeanDefinition = doCreateBeanDefinition(className);
            if(null == gpBeanDefinition){continue;}
            result.add(gpBeanDefinition);
        }

       return result;
    }

    private GPBeanDefinition doCreateBeanDefinition(String className){
        try {

            Class<?> beanClass = Class.forName(className);
            if(!beanClass.isInterface()){
                GPBeanDefinition gpBeanDefinition = new GPBeanDefinition();
                gpBeanDefinition.setBeanClassName(className);
                gpBeanDefinition.setFactoryBeanName(toLowerFirstCase(beanClass.getSimpleName()));
                return gpBeanDefinition;
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private String toLowerFirstCase(String simpleName) {
        char [] chars = simpleName.toCharArray();
        //之所以加，是因为大小写字母的ASCII码相差32，
        // 而且大写字母的ASCII码要小于小写字母的ASCII码
        //在Java中，对char做算学运算，实际上就是对ASCII码做算学运算
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
