package com.lzg.spring.test;

import com.lzg.spring.assemble.javaconfig.User;
import com.lzg.spring.assemble.myimport.Teacher;
import com.lzg.spring.assemble.myimport.Test1;
import com.lzg.spring.assemble.myimport.Test2;
import com.lzg.spring.assemble.xml.MyXmlTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @Author lzg
 * @Date 2021-06-05 15:40
 * <p>
 * <p>
 * classpath：只会到你的class路径中查找找文件。
 * classpath*：不仅包含class路径，还包括jar文件中（class路径）进行查找
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
//@ContextConfiguration(locations = {"classpath*:/*.xml"})
//@ContextConfiguration(classes= MyAutoConfig.class)
public class SpringTest {

//    @Autowired
//    private MyTest myTest;
    @Autowired
    private Teacher teacher;

    @Autowired
    private Test1 test1;

    @Autowired
    private Test2 test2;

    @Autowired
    private User user;

    @Autowired
    private MyXmlTest yXmlTest;

    @Test
    public void test1() {
//        myTest.print();
    }

    @Test
    public void test2() {
        System.out.println(user.toString());
    }

    @Test
    public void test3() {
        yXmlTest.print();
    }

    @Test
    public void test4(){
        System.out.println(user.toString());
    }

    @Test
    public void test5(){
        System.out.println(teacher.toString());
    }

    @Test
    public void test6(){
        System.out.println(test1.toString());
    }


    @Test
    public void test7(){
        System.out.println(test2.toString());
    }
}
