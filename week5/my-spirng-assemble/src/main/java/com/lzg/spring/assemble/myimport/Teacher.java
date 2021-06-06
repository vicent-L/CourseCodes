package com.lzg.spring.assemble.myimport;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Author lzg
 * @Date 2021-06-05 16:25
 */
@Data
//@Component
public class Teacher {

    public Teacher(){
        System.out.println("teacher  construct");
    }
    private String tname;
}
