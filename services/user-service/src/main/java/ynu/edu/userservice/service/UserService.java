package ynu.edu.userservice.service;

import ynu.edu.userservice.common.Result;
import ynu.edu.userservice.dto.*;
import ynu.edu.userservice.entity.UserAddress;
import ynu.edu.userservice.vo.LoginVO;
import ynu.edu.userservice.vo.UserVO;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService {
    
    /**
     * 发送短信验证码
     */
    Result<Void> sendSms(SendSmsDTO sendSmsDTO);
    
    /**
     * 用户注册
     */
    Result<LoginVO> register(RegisterDTO registerDTO);
    
    /**
     * 用户登录
     */
    Result<LoginVO> login(LoginDTO loginDTO);
    
    /**
     * 刷新令牌
     */
    Result<LoginVO> refreshToken(String refreshToken);
    
    /**
     * 重置密码
     */
    Result<Void> resetPassword(ResetPasswordDTO resetPasswordDTO);
    
    /**
     * 获取用户信息
     */
    Result<UserVO> getUserInfo(Long userId);
    
    /**
     * 更新用户信息
     */
    Result<UserVO> updateUserInfo(Long userId, UserUpdateDTO updateDTO);
    
    /**
     * 用户退出登录
     */
    Result<Void> logout(String token);
    
    /**
     * 添加收货地址
     */
    Result<Void> addAddress(Long userId, AddressDTO addressDTO);
    
    /**
     * 更新收货地址
     */
    Result<Void> updateAddress(Long userId, AddressDTO addressDTO);
    
    /**
     * 删除收货地址
     */
    Result<Void> deleteAddress(Long userId, Long addressId);
    
    /**
     * 设置默认地址
     */
    Result<Void> setDefaultAddress(Long userId, Long addressId);
    
    /**
     * 获取用户地址列表
     */
    Result<List<UserAddress>> getUserAddresses(Long userId);
}
