package com.lzg.spring.assemble.myimport;

import com.lzg.spring.assemble.Student;
import com.lzg.spring.assemble.javaconfig.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author lzg
 * @Date 2021-06-05 14:36
 * 通过java配置类方式装配bean
 */
@Import({Student.class, Teacher.class,MyImportSelector.class,MyImportBeanDefinitionRegistrar.class})
@Configuration("importConfig")
public class MyJavaConfig {

//    @Bean
//    public Teacher getTeacher(Teacher teacher) {
//        teacher.setTname("tttt");
//        return teacher;
//    }

    @Bean("student")
//    @Bean()
    public Student getStudent(Student student) {
        return student;
    }
}
