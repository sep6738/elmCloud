// 文件路径: C:\Users\q\Desktop\软件服务工程\elmCloud\services\order-service\src\main\java\ynu\edu\orderservice\mapper\ShoppingCartMapper.java
package ynu.edu.orderservice.mapper;

import org.apache.ibatis.annotations.*;
import ynu.edu.orderservice.entity.ShoppingCart;
import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    // (此部分保持不变)
    @Insert("INSERT INTO shopping_cart (user_id, store_id, product_id, product_name, product_image, product_spec, unit_price, quantity, create_time, update_time, deleted) " +
            "VALUES (#{userId}, #{storeId}, #{productId}, #{productName}, #{productImage}, #{productSpec}, #{unitPrice}, #{quantity}, NOW(), NOW(), 0)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ShoppingCart shoppingCart);

    // ==================== 核心修复点在这里 ====================
    // 使用 @Results 注解来明确定义数据库列和Java实体字段的映射关系
    // 这样可以彻底解决因命名风格不一致导致的映射失败问题

    String CART_COLUMNS = "id, user_id, store_id, product_id, product_name, product_image, product_spec, unit_price, quantity, create_time, update_time";

    @Results(id = "shoppingCartResultMap", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "userId", column = "user_id"), // 显式映射 user_id -> userId
            @Result(property = "storeId", column = "store_id"),
            @Result(property = "productId", column = "product_id"),
            @Result(property = "productName", column = "product_name"),
            @Result(property = "productImage", column = "product_image"),
            @Result(property = "productSpec", column = "product_spec"),
            @Result(property = "unitPrice", column = "unit_price"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time")
    })
    @Select("SELECT " + CART_COLUMNS + " FROM shopping_cart WHERE id = #{id} AND deleted = 0")
    ShoppingCart selectById(Long id);

    @ResultMap("shoppingCartResultMap") // 复用上面定义的映射
    @Select("SELECT " + CART_COLUMNS + " FROM shopping_cart WHERE user_id = #{userId} AND deleted = 0 ORDER BY create_time DESC")
    List<ShoppingCart> selectByUserId(Long userId);

    @ResultMap("shoppingCartResultMap") // 复用上面定义的映射
    @Select("SELECT " + CART_COLUMNS + " FROM shopping_cart WHERE user_id = #{userId} AND product_id = #{productId} AND deleted = 0")
    ShoppingCart selectByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

    // (更新和删除部分保持不变)
    @Update("UPDATE shopping_cart SET quantity = #{quantity}, update_time = NOW() WHERE id = #{id}")
    int updateById(ShoppingCart shoppingCart);

    @Update("UPDATE shopping_cart SET deleted = 1, update_time = NOW() WHERE id = #{id}")
    int deleteById(Long id);

    @Update("UPDATE shopping_cart SET deleted = 1, update_time = NOW() WHERE user_id = #{userId}")
    int deleteByUserId(Long userId);

    @Update("UPDATE shopping_cart SET deleted = 1, update_time = NOW() WHERE user_id = #{userId} AND store_id = #{storeId}")
    int deleteByUserIdAndStoreId(@Param("userId") Long userId, @Param("storeId") Long storeId);
}