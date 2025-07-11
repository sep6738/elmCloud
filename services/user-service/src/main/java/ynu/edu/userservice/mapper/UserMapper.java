package ynu.edu.userservice.mapper;

import org.apache.ibatis.annotations.*;
import ynu.edu.userservice.entity.User;

@Mapper
public interface UserMapper {
    
    @Select("SELECT * FROM user WHERE id = #{id}")
    User selectById(Long id);
    
    @Select("SELECT * FROM user WHERE phone = #{phone}")
    User selectByPhone(String phone);
    
    @Insert("INSERT INTO user(phone, password, nickname, avatar, create_time, update_time) " +
            "VALUES(#{phone}, #{password}, #{nickname}, #{avatar}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);
    
    @Update("UPDATE user SET password = #{password}, nickname = #{nickname}, avatar = #{avatar}, " +
            "update_time = #{updateTime} WHERE id = #{id}")
    int updateById(User user);
    
    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteById(Long id);
}
