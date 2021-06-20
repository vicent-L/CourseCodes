import com.sharding.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


@Slf4j
@RunWith(SpringRunner.class)
//@SpringBootTest
@SpringBootTest(classes = Application.class)
public class shardingsphereDataSourceApplicationTests {

    @Resource
    private DataSource dataSource;

    @Test
    public void readWrite() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            Statement st = conn.createStatement();

            //从库读数据
            ResultSet rs = st.executeQuery("select * from student");
            while (rs.next()) {
                log.info(rs.getString("id") + "|" + rs.getString("name"));
            }
            //写入主库
            st.executeUpdate("insert into student(name,age) values('ssyy',20)");

            //从从库读
            rs = st.executeQuery("select * from student");
            while (rs.next()) {
                log.info(rs.getString(1) + "|" + rs.getString(2) + "|" + rs.getString(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn);
        }
    }

    public void close(Connection conn) {

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
