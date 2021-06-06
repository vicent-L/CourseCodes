package com.lzg.autoassemble.swork.controller;

import com.lzg.starter.Klass;
import com.lzg.starter.School;
import com.lzg.starter.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author lzg
 * 自定义starter测试
 */
@RestController
public class AutoConfigController {


    @Autowired
    private Student student;


    @Autowired
    private Klass klass;


    @Autowired
    private School school;

    @RequestMapping("/testStudent")
    public String testStudent() {
        System.out.println("student test begin...............");
        student.init();
        student.print();
        return "testStudent";
    }


    @RequestMapping("/testKlass")
    public String testKlass() {
        System.out.println("klass test begin...............");
        klass.dong();
        return "testKlass";
    }

    @RequestMapping("/testSchool")
    public String testSchool() {
        System.out.println("School test begin...............");
        school.ding();
        return "testSchool";
    }
}
