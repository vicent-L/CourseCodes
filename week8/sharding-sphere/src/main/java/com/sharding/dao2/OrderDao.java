package com.sharding.dao2;

import java.util.List;

/**
 * @Author lzg
 * @Date 2021-06-25 14:42
 */
public interface OrderDao {

      List<Long> insertOrder();

      void selectOrder();
}
