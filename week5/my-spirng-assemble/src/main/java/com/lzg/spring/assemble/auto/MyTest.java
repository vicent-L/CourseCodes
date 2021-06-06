package com.lzg.spring.assemble.auto;

import org.springframework.stereotype.Component;


/**
 * @Author lzg
 * @Date 2021-06-05 14:44
 */
@Component
public class MyTest {
    public MyTest(){
        System.out.println("my-test.........");
    }

    public void print(){
        System.out.println("MyTest print ....");
    }
}
