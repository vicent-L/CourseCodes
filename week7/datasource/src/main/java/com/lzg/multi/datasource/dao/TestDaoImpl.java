package com.lzg.multi.datasource.dao;

import com.lzg.multi.datasource.annotation.SwitchDataSource;
import com.lzg.multi.datasource.config.DataSourceName;
import com.lzg.multi.datasource.config.DynamicDataSource;
import com.lzg.multi.datasource.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author lzg
 * @Date 2021-06-20 17:30
 */
@Component
public class TestDaoImpl implements TestDao {
    @Autowired
    private DynamicDataSource dynamicDataSource;
//    private DataSource dataSource;

    private static final String INSERT_STUDENT_SQL = "insert into student (name,age) values (?,?)";
    private static final String QUERY_STUDENT_SQL = "select * from student where id = ?";

    @Override
    public void addStudent(Student student) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dynamicDataSource.getConnection();
            stmt = conn.prepareStatement(INSERT_STUDENT_SQL);
            stmt.setString(1, student.getName());
            stmt.setInt(2, student.getAge());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(stmt, conn);

        }
    }


    @Override
    @SwitchDataSource(name = DataSourceName.SLAVE)
    public Student queryStudent(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dynamicDataSource.getConnection();
            stmt = conn.prepareStatement(QUERY_STUDENT_SQL);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            Student student = null;
            if (rs.next()) {
                student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setAge(rs.getInt("age"));
            }
            return student;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(stmt, conn);
        }
        return null;
    }

    public void close(PreparedStatement stmt, Connection conn) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
