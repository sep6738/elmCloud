// 文件路径: C:\Users\q\Desktop\软件服务工程\elmCloud\services\order-service\src\main\java\ynu\edu\orderservice\mapper\OrderItemMapper.java
package ynu.edu.orderservice.mapper;

import org.apache.ibatis.annotations.*;
import ynu.edu.orderservice.entity.OrderItem;
import java.util.List;

@Mapper
public interface OrderItemMapper {

    // (insert方法保持不变)
    @Insert("INSERT INTO order_item (order_id, product_id, product_name, product_image, product_spec, unit_price, quantity, subtotal, create_time, update_time) " +
            "VALUES (#{orderId}, #{productId}, #{productName}, #{productImage}, #{productSpec}, #{unitPrice}, #{quantity}, #{subtotal}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(OrderItem orderItem);

    // ==================== 核心修复点在这里 ====================
    String ITEM_COLUMNS = "id, order_id, product_id, product_name, product_image, product_spec, unit_price, quantity, subtotal, create_time, update_time";

    @Results(id = "orderItemResultMap", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "orderId", column = "order_id"),
            @Result(property = "productId", column = "product_id"),
            @Result(property = "productName", column = "product_name"),
            @Result(property = "productImage", column = "product_image"),
            @Result(property = "productSpec", column = "product_spec"),
            @Result(property = "unitPrice", column = "unit_price"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "subtotal", column = "subtotal"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time")
    })
    @Select("SELECT " + ITEM_COLUMNS + " FROM order_item WHERE id = #{id}")
    OrderItem selectById(Long id);

    @ResultMap("orderItemResultMap")
    @Select("SELECT " + ITEM_COLUMNS + " FROM order_item WHERE order_id = #{orderId}")
    List<OrderItem> selectByOrderId(Long orderId);

    // (update方法保持不变)
    @Update("UPDATE order_item SET " +
            "order_id = #{orderId}, product_id = #{productId}, product_name = #{productName}, " +
            "product_image = #{productImage}, product_spec = #{productSpec}, unit_price = #{unitPrice}, " +
            "quantity = #{quantity}, subtotal = #{subtotal}, update_time = NOW() " +
            "WHERE id = #{id}")
    int updateById(OrderItem orderItem);
}