package com.lzg.starter;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

@Data
public class School implements ISchool {
    
    //  @Autowired按类型装配依赖对象
    @Autowired(required = true) //primary
    Klass class1;
    
    @Resource
    Student student;
    
    @Override
    public void ding(){
    
        System.out.println("Class1 have " + this.class1.getStudents().size() + " students and one is " + this.student);
        
    }
    
}
