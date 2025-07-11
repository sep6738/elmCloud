package ynu.edu.storeservice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import ynu.edu.storeservice.entity.Product;

@Mapper
public interface ProductMapper {
    
    /**
     * 新增商品
     */
    @Insert("INSERT INTO product (product_name, description, price, category_id, store_id, " +
            "image_url, status, stock, sales_count, unit) VALUES " +
            "(#{productName}, #{description}, #{price}, #{categoryId}, #{storeId}, " +
            "#{imageUrl}, #{status}, #{stock}, #{salesCount}, #{unit})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Product product);
    
    /**
     * 根据ID查询商品
     */
    @Select("SELECT * FROM product WHERE id = #{id} AND deleted = 0")
    Product selectById(Long id);
    
    /**
     * 根据商家ID查询商品列表
     */
    @Select("SELECT * FROM product WHERE store_id = #{storeId} AND deleted = 0 " +
            "AND status = 1 ORDER BY sort_order ASC, created_at DESC")
    List<Product> selectByStoreId(Long storeId);
    
    /**
     * 根据分类ID查询商品列表
     */
    @Select("SELECT * FROM product WHERE category_id = #{categoryId} AND deleted = 0 " +
            "AND status = 1 ORDER BY sort_order ASC, created_at DESC")
    List<Product> selectByCategoryId(Long categoryId);
    
    /**
     * 搜索商品
     */
    @Select("<script>" +
            "SELECT * FROM product WHERE deleted = 0 AND status = 1 " +
            "<if test='keyword != null and keyword != \"\"'>" +
            "AND (product_name LIKE CONCAT('%', #{keyword}, '%') " +
            "OR description LIKE CONCAT('%', #{keyword}, '%')) " +
            "</if>" +
            "<if test='storeId != null'> AND store_id = #{storeId} </if>" +
            "<if test='categoryId != null'> AND category_id = #{categoryId} </if>" +
            "ORDER BY sales_count DESC, created_at DESC " +
            "LIMIT #{offset}, #{size}" +
            "</script>")
    List<Product> searchProducts(@Param("keyword") String keyword,
                                @Param("storeId") Long storeId,
                                @Param("categoryId") Long categoryId,
                                @Param("offset") int offset,
                                @Param("size") int size);
    
    /**
     * 统计商品数量
     */
    @Select("<script>" +
            "SELECT COUNT(*) FROM product WHERE deleted = 0 AND status = 1 " +
            "<if test='keyword != null and keyword != \"\"'>" +
            "AND (product_name LIKE CONCAT('%', #{keyword}, '%') " +
            "OR description LIKE CONCAT('%', #{keyword}, '%')) " +
            "</if>" +
            "<if test='storeId != null'> AND store_id = #{storeId} </if>" +
            "<if test='categoryId != null'> AND category_id = #{categoryId} </if>" +
            "</script>")
    int countProducts(@Param("keyword") String keyword,
                     @Param("storeId") Long storeId,
                     @Param("categoryId") Long categoryId);
    
    /**
     * 更新商品信息
     */
    @Update("UPDATE product SET product_name = #{productName}, description = #{description}, " +
            "price = #{price}, category_id = #{categoryId}, image_url = #{imageUrl}, " +
            "status = #{status}, stock = #{stock}, unit = #{unit}, " +
            "updated_at = NOW() WHERE id = #{id}")
    int updateById(Product product);
    
    /**
     * 更新商品库存
     */
    @Update("UPDATE product SET stock = stock - #{quantity}, sales_count = sales_count + #{quantity}, " +
            "updated_at = NOW() WHERE id = #{id} AND stock >= #{quantity}")
    int updateStock(@Param("id") Long id, @Param("quantity") Integer quantity);
    
    /**
     * 删除商品（逻辑删除）
     */
    @Update("UPDATE product SET deleted = 1, updated_at = NOW() WHERE id = #{id}")
    int deleteById(Long id);
    
    /**
     * 查询所有商品
     */
    @Select("SELECT * FROM product WHERE deleted = 0")
    List<Product> selectAll();
}
