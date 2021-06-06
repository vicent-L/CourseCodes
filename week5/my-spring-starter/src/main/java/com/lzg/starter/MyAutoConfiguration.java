package com.lzg.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @Author lzg
 * @Date 2021-06-04 17:51
 * 自定义配置类
 */
@Configuration
@ConditionalOnClass(ISchool.class)
//@EnableConfigurationProperties(School.class)
public class MyAutoConfiguration {


    @Bean
//    @Primary
    public Klass getKlass(Student student) {
        ArrayList<Student> students = new ArrayList<>();
        students.add(student);
        Klass klass = new Klass();
        klass.setStudents(students);
        return klass;
    }

    @Bean
    @Primary
    public Student getStudent() {
        Student student = new Student();
        student.setId(123);
        student.setName("lzg");
//        student.setBeanName("lzg");
        return student;
    }



    @Bean
    public School getSchool() {
        return new School();
    }

}
