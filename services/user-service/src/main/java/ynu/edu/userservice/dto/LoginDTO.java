package ynu.edu.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class LoginDTO {
    
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;
    
    /**
     * 登录方式：password-密码登录，code-验证码登录
     */
    @NotBlank(message = "登录方式不能为空")
    private String loginType;
    
    /**
     * 密码（密码登录时必填）
     */
    private String password;
    
    /**
     * 验证码（验证码登录时必填）
     */
    private String code;
    
    // Getter and Setter methods
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getLoginType() {
        return loginType;
    }
    
    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
}
