package ynu.edu.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ynu.edu.userservice.common.Result;
import ynu.edu.userservice.dto.*;
import ynu.edu.userservice.entity.UserAddress;
import ynu.edu.userservice.service.UserService;
import ynu.edu.userservice.vo.LoginVO;
import ynu.edu.userservice.vo.UserVO;

import java.util.List;

/**
 * 用户控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    /**
     * 发送短信验证码
     */
    @PostMapping("/sms/send")
    public Result<Void> sendSms(@RequestBody SendSmsDTO sendSmsDTO) {
        return userService.sendSms(sendSmsDTO);
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<LoginVO> register(@RequestBody RegisterDTO registerDTO) {
        return userService.register(registerDTO);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

    /**
     * 刷新令牌
     */
    @PostMapping("/token/refresh")
    public Result<LoginVO> refreshToken(@RequestParam String refreshToken) {
        return userService.refreshToken(refreshToken);
    }

    /**
     * 重置密码
     */
    @PostMapping("/password/reset")
    public Result<Void> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {
        return userService.resetPassword(resetPasswordDTO);
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/info")
    public Result<UserVO> getUserInfo(@RequestHeader("userId") Long userId) {
        return userService.getUserInfo(userId);
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/info")
    public Result<UserVO> updateUserInfo(
            @RequestHeader("userId") Long userId,
            @Valid @RequestBody UserUpdateDTO updateDTO) {
        return userService.updateUserInfo(userId, updateDTO);
    }

    /**
     * 用户退出登录
     */
    @PostMapping("/logout")
    public Result<Void> logout(@RequestHeader("Authorization") String authorization) {
        // 提取token（去掉Bearer前缀）
        String token = authorization.replace("Bearer ", "");
        return userService.logout(token);
    }

    /**
     * 添加收货地址
     */
    @PostMapping("/address")
    public Result<Void> addAddress(
            @RequestHeader("userId") Long userId,
            @Valid @RequestBody AddressDTO addressDTO) {
        return userService.addAddress(userId, addressDTO);
    }

    /**
     * 更新收货地址
     */
    @PutMapping("/address")
    public Result<Void> updateAddress(
            @RequestHeader("userId") Long userId,
            @Valid @RequestBody AddressDTO addressDTO) {
        return userService.updateAddress(userId, addressDTO);
    }

    /**
     * 删除收货地址
     */
    @DeleteMapping("/address/{addressId}")
    public Result<Void> deleteAddress(
            @RequestHeader("userId") Long userId,
            @PathVariable Long addressId) {
        return userService.deleteAddress(userId, addressId);
    }

    /**
     * 设置默认地址
     */
    @PutMapping("/address/{addressId}/default")
    public Result<Void> setDefaultAddress(
            @RequestHeader("userId") Long userId,
            @PathVariable Long addressId) {
        return userService.setDefaultAddress(userId, addressId);
    }

    /**
     * 获取用户地址列表
     */
    @GetMapping("/addresses")
    public Result<List<UserAddress>> getUserAddresses(@RequestHeader("userId") Long userId) {
        return userService.getUserAddresses(userId);
    }
}
