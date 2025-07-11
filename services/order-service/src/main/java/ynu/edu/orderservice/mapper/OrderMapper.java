// 文件路径: C:\Users\q\Desktop\软件服务工程\elmCloud\services\order-service\src\main\java\ynu\edu\orderservice\mapper\OrderMapper.java
package ynu.edu.orderservice.mapper;

import org.apache.ibatis.annotations.*;
import ynu.edu.orderservice.entity.Order;
import java.util.List;

@Mapper
public interface OrderMapper {
    // (insert方法保持不变，因为是对象->数据库，MyBatis通常能正确处理)
    @Insert("INSERT INTO orders (order_no, user_id, store_id, store_name, status, " +
            "product_amount, package_fee, delivery_fee, discount_amount, actual_amount, " +
            "payment_method, address_id, contact_name, contact_phone, delivery_address, " +
            "remark, create_time, update_time, deleted) VALUES " +
            "(#{orderNo}, #{userId}, #{storeId}, #{storeName}, #{status}, " +
            "#{productAmount}, #{packageFee}, #{deliveryFee}, #{discountAmount}, #{actualAmount}, " +
            "#{paymentMethod}, #{addressId}, #{contactName}, #{contactPhone}, #{deliveryAddress}, " +
            "#{remark}, NOW(), NOW(), 0)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Order order);

    // ==================== 核心修复点在这里 ====================
    // 使用 @Results 注解明确定义映射关系

    String ORDER_COLUMNS = "id, order_no, user_id, store_id, store_name, status, product_amount, package_fee, " +
            "delivery_fee, discount_amount, actual_amount, payment_method, payment_time, address_id, contact_name, " +
            "contact_phone, delivery_address, remark, estimated_delivery_time, actual_delivery_time, finish_time, " +
            "create_time, update_time, deleted";

    @Results(id = "orderResultMap", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "orderNo", column = "order_no"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "storeId", column = "store_id"),
            @Result(property = "storeName", column = "store_name"),
            @Result(property = "status", column = "status"),
            @Result(property = "productAmount", column = "product_amount"),
            @Result(property = "packageFee", column = "package_fee"),
            @Result(property = "deliveryFee", column = "delivery_fee"),
            @Result(property = "discountAmount", column = "discount_amount"),
            @Result(property = "actualAmount", column = "actual_amount"),
            @Result(property = "paymentMethod", column = "payment_method"),
            @Result(property = "paymentTime", column = "payment_time"),
            @Result(property = "addressId", column = "address_id"),
            @Result(property = "contactName", column = "contact_name"),
            @Result(property = "contactPhone", column = "contact_phone"),
            @Result(property = "deliveryAddress", column = "delivery_address"),
            @Result(property = "remark", column = "remark"),
            @Result(property = "estimatedDeliveryTime", column = "estimated_delivery_time"),
            @Result(property = "actualDeliveryTime", column = "actual_delivery_time"),
            @Result(property = "finishTime", column = "finish_time"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "deleted", column = "deleted")
    })
    @Select("SELECT " + ORDER_COLUMNS + " FROM orders WHERE id = #{id} AND deleted = 0")
    Order selectById(Long id);

    @ResultMap("orderResultMap")
    @Select("SELECT " + ORDER_COLUMNS + " FROM orders WHERE order_no = #{orderNo} AND deleted = 0")
    Order selectByOrderNo(String orderNo);

    @ResultMap("orderResultMap")
    @Select("SELECT " + ORDER_COLUMNS + " FROM orders WHERE user_id = #{userId} AND deleted = 0 ORDER BY create_time DESC")
    List<Order> selectByUserId(Long userId);

    @ResultMap("orderResultMap")
    @Select("SELECT " + ORDER_COLUMNS + " FROM orders WHERE store_id = #{storeId} AND deleted = 0 ORDER BY create_time DESC")
    List<Order> selectByStoreId(Long storeId);

    @ResultMap("orderResultMap")
    @Select("SELECT " + ORDER_COLUMNS + " FROM orders WHERE status = #{status} AND deleted = 0 ORDER BY create_time DESC")
    List<Order> selectByStatus(Integer status);

    @ResultMap("orderResultMap")
    @Select("SELECT " + ORDER_COLUMNS + " FROM orders WHERE deleted = 0 ORDER BY create_time DESC LIMIT #{offset}, #{size}")
    List<Order> selectAll(@Param("offset") int offset, @Param("size") int size);

    // (update和delete方法保持不变)
    @Update("UPDATE orders SET status = #{status}, " +
            "actual_delivery_time = #{actualDeliveryTime}, finish_time = #{finishTime}, " +
            "remark = #{remark}, update_time = NOW() WHERE id = #{id}")
    int updateById(Order order);

    @Update("UPDATE orders SET deleted = 1, update_time = NOW() WHERE id = #{id}")
    int deleteById(Long id);
}