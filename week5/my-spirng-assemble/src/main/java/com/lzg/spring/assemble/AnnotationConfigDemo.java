package com.lzg.spring.assemble;

import com.lzg.spring.assemble.auto.MyAutoConfig;
import com.lzg.spring.assemble.myimport.MyJavaConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author lzg
 * @Date 2021-06-05 17:56
 */
public class AnnotationConfigDemo {

    public static void main(String[] args) {
//        AnnotationConfigApplicationContext applicationContext=new AnnotationConfigApplicationContext(MyAutoConfig.class);
        AnnotationConfigApplicationContext applicationContext=new AnnotationConfigApplicationContext(MyJavaConfig.class);

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanDefinitionNames){
            System.out.println(name);
        }

    }
}
