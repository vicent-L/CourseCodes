import com.sharding.dao.IOrderDao;
import com.sharding.dao.OrderDaoImpl;
import com.sharding.datasource.YmlDataSourceFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @Author lzg
 * @Date 2021-06-23 22:41
 */
public class YamlConfigShardingSphereTests {

    private static IOrderDao orderDao;

    @BeforeClass
    public static void init() throws SQLException, IOException {
        DataSource dataSource = YmlDataSourceFactory.getDataSource();
        orderDao = new OrderDaoImpl(dataSource);
    }

    @Test
    //发现一个问题，采用雪花算法随机生成 id 作分库分表shard key，插入数据倾斜，产生分表不均问题
    //可参见美团点评 核心类https://github.com/Meituan-Dianping/Leaf/blob/master/leaf-core/src/main/java/com/sankuai/inf/leaf/snowflake/SnowflakeIDGenImpl.java
    public void test() {

        orderDao.insertOrder();
    }


    @Test
    public void testQuery() {

        orderDao.queryOrder();
    }

}
