import com.sharding.Application;
import com.sharding.dao2.OrderDao;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Author lzg
 * @Date 2021-06-25 14:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class JdbcTemplateTests {


    @Autowired
    private OrderDao orderDao;

//    @Before
//    public static void init() throws SQLException, IOException {
//        DataSource dataSource = YmlDataSourceFactory.getDataSource();
//    }


    @ShardingTransactionType(value = TransactionType.XA)
    @Transactional(rollbackFor = Exception.class)
    @Test
    public void insert() {


        orderDao.insertOrder();
    }


    @ShardingTransactionType(value = TransactionType.XA)
    @Transactional(rollbackFor = Exception.class)
    @Test
    public void select() {
        orderDao.selectOrder();
    }


}
