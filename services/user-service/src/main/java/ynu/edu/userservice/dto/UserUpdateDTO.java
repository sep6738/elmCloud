package ynu.edu.userservice.dto;

/**
 * 用户信息更新DTO
 */
public class UserUpdateDTO {
    
    /**
     * 昵称
     */
    private String nickname;
    
    /**
     * 头像URL
     */
    private String avatar;
    
    /**
     * 性别：0-未知，1-男，2-女
     */
    private Integer gender;
    
    // Getter and Setter methods
    public String getNickname() {
        return nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public String getAvatar() {
        return avatar;
    }
    
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
    public Integer getGender() {
        return gender;
    }
    
    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
