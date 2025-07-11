package ynu.edu.storeservice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import ynu.edu.storeservice.entity.Store;
import ynu.edu.storeservice.vo.StoreVO;

@Mapper
public interface StoreMapper {
    
    /**
     * 新增商家
     */
    @Insert("INSERT INTO store (store_name, description, address, phone, status, category_id, " +
            "avg_rating, delivery_fee, min_order_amount, business_hours, image_url) VALUES " +
            "(#{storeName}, #{description}, #{address}, #{phone}, #{status}, #{categoryId}, " +
            "#{avgRating}, #{deliveryFee}, #{minOrderAmount}, #{businessHours}, #{imageUrl})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Store store);
    
    /**
     * 根据ID查询商家
     */
    @Select("SELECT * FROM store WHERE id = #{id} AND deleted = 0")
    Store selectById(Long id);
    
    /**
     * 更新商家信息
     */
    @Update("UPDATE store SET store_name = #{storeName}, description = #{description}, " +
            "address = #{address}, phone = #{phone}, status = #{status}, " +
            "avg_rating = #{avgRating}, delivery_fee = #{deliveryFee}, " +
            "min_order_amount = #{minOrderAmount}, business_hours = #{businessHours}, " +
            "image_url = #{imageUrl}, updated_at = NOW() WHERE id = #{id}")
    int updateById(Store store);
    
    /**
     * 删除商家（逻辑删除）
     */
    @Update("UPDATE store SET deleted = 1, updated_at = NOW() WHERE id = #{id}")
    int deleteById(Long id);
    
    /**
     * 根据距离和其他条件查询商家列表
     */
    @Select("SELECT *, " +
            "ROUND(6371 * ACOS(COS(RADIANS(#{latitude})) * COS(RADIANS(latitude)) * " +
            "COS(RADIANS(longitude) - RADIANS(#{longitude})) + SIN(RADIANS(#{latitude})) * " +
            "SIN(RADIANS(latitude))), 2) AS distance " +
            "FROM store " +
            "WHERE status = 1 AND deleted = 0 " +
            "AND (#{keyword} IS NULL OR name LIKE CONCAT('%', #{keyword}, '%')) " +
            "AND (#{categoryId} IS NULL OR category_id = #{categoryId}) " +
            "HAVING distance <= IFNULL(delivery_range, 999) " +
            "ORDER BY " +
            "CASE WHEN #{sortBy} = 'distance' THEN distance END ASC, " +
            "CASE WHEN #{sortBy} = 'rating' THEN rating END DESC, " +
            "CASE WHEN #{sortBy} = 'sales' THEN monthly_sales END DESC, " +
            "CASE WHEN #{sortBy} = 'delivery_time' THEN estimated_delivery_time END ASC")
    List<StoreVO> findStoresWithDistance(@Param("latitude") Double latitude,
                                        @Param("longitude") Double longitude,
                                        @Param("keyword") String keyword,
                                        @Param("categoryId") Long categoryId,
                                        @Param("sortBy") String sortBy);
    
    /**
     * 查询所有商家
     */
    @Select("SELECT * FROM store WHERE deleted = 0")
    List<Store> selectAll();
}
