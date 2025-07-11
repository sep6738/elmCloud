package ynu.edu.userservice.vo;

/**
 * 登录响应VO
 */
public class LoginVO {
    
    /**
     * 用户信息
     */
    private UserVO user;
    
    /**
     * 访问令牌
     */
    private String accessToken;
    
    /**
     * 刷新令牌
     */
    private String refreshToken;
    
    /**
     * 令牌过期时间(毫秒)
     */
    private Long expiresIn;
    
    // Getter and Setter methods
    public UserVO getUser() {
        return user;
    }
    
    public void setUser(UserVO user) {
        this.user = user;
    }
    
    public String getAccessToken() {
        return accessToken;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public String getRefreshToken() {
        return refreshToken;
    }
    
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    
    public Long getExpiresIn() {
        return expiresIn;
    }
    
    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
