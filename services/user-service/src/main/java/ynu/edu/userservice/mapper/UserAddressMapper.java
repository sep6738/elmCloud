package ynu.edu.userservice.mapper;

import org.apache.ibatis.annotations.*;
import ynu.edu.userservice.entity.UserAddress;
import java.util.List;

@Mapper
public interface UserAddressMapper {
    
    @Select("SELECT * FROM user_address WHERE id = #{id}")
    UserAddress selectById(Long id);
    
    @Select("SELECT * FROM user_address WHERE user_id = #{userId} ORDER BY is_default DESC, create_time DESC")
    List<UserAddress> selectByUserId(Long userId);
    
    @Insert("INSERT INTO user_address(user_id, contact_name, contact_phone, province, city, district, detail_address, longitude, latitude, is_default, create_time, update_time) " +
            "VALUES(#{userId}, #{contactName}, #{contactPhone}, #{province}, #{city}, #{district}, #{detailAddress}, #{longitude}, #{latitude}, #{isDefault}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(UserAddress userAddress);
    
    @Update("UPDATE user_address SET contact_name = #{contactName}, contact_phone = #{contactPhone}, " +
            "province = #{province}, city = #{city}, district = #{district}, detail_address = #{detailAddress}, " +
            "longitude = #{longitude}, latitude = #{latitude}, is_default = #{isDefault}, update_time = #{updateTime} WHERE id = #{id}")
    int updateById(UserAddress userAddress);
    
    @Delete("DELETE FROM user_address WHERE id = #{id}")
    int deleteById(Long id);
    
    @Update("UPDATE user_address SET is_default = 0 WHERE user_id = #{userId}")
    int resetDefault(Long userId);
}
