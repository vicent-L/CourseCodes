package com.lzg.multi.datasource.dao;

import com.lzg.multi.datasource.entity.Student;

/**
 * @Author lzg
 * @Date 2021-06-20 17:30
 */
public interface TestDao {

    public void addStudent(Student student);

    public Student queryStudent(int id);
}
