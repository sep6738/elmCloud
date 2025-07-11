package ynu.edu.storeservice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import ynu.edu.storeservice.entity.ProductCategory;

@Mapper
public interface ProductCategoryMapper {
    
    /**
     * 新增商品分类
     */
    @Insert("INSERT INTO product_category (category_name, description, image_url, sort_order, status) VALUES " +
            "(#{categoryName}, #{description}, #{imageUrl}, #{sortOrder}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ProductCategory category);
    
    /**
     * 根据ID查询商品分类
     */
    @Select("SELECT * FROM product_category WHERE id = #{id} AND deleted = 0")
    ProductCategory selectById(Long id);
    
    /**
     * 查询所有启用的商品分类
     */
    @Select("SELECT * FROM product_category WHERE deleted = 0 AND status = 1 ORDER BY sort_order ASC")
    List<ProductCategory> selectAllEnabled();
    
    /**
     * 查询所有商品分类（包括禁用的）
     */
    @Select("SELECT * FROM product_category WHERE deleted = 0 ORDER BY sort_order ASC")
    List<ProductCategory> selectAll();
    
    /**
     * 更新商品分类
     */
    @Update("UPDATE product_category SET category_name = #{categoryName}, " +
            "description = #{description}, image_url = #{imageUrl}, " +
            "sort_order = #{sortOrder}, status = #{status}, updated_at = NOW() WHERE id = #{id}")
    int updateById(ProductCategory category);
    
    /**
     * 删除商品分类（逻辑删除）
     */
    @Update("UPDATE product_category SET deleted = 1, updated_at = NOW() WHERE id = #{id}")
    int deleteById(Long id);
    
    /**
     * 根据商家ID查询商品分类
     */
    @Select("SELECT * FROM product_category WHERE store_id = #{storeId} AND deleted = 0")
    List<ProductCategory> selectByStoreId(Long storeId);
}
