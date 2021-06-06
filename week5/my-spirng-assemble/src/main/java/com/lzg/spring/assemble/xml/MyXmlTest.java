package com.lzg.spring.assemble.xml;

import com.lzg.spring.assemble.Student;
import com.lzg.spring.assemble.javaconfig.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author lzg
 * @Date 2021-06-05 14:48
 */
@Component
public class MyXmlTest {

    @Autowired
    private Student student;

    public void print(){
        System.out.println(student.toString());
    }
}
