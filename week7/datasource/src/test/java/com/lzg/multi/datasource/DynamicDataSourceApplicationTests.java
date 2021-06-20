package com.lzg.multi.datasource;

import com.lzg.multi.datasource.entity.Student;
import com.lzg.multi.datasource.dao.TestDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @Author lzg
 * @Date 2021-06-20 16:57
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DynamicDataSourceApplicationTests {

    @Autowired
    private TestDao testDao;

    /**
     * 插入数据走主库
     */
    @Test
    public void test() {
        Student s = new Student();
        s.setAge(22);
        s.setName("lzg");
        testDao.addStudent(s);
    }

    /**
     * 读取数据走从库
     */
    @Test
    public void test2() {

        Student student = testDao.queryStudent(1);
        System.out.println(student.toString());
    }

}
