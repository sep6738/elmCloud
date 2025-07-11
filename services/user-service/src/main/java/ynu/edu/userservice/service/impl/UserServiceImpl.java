package ynu.edu.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ynu.edu.userservice.common.Result;
import ynu.edu.userservice.dto.*;
import ynu.edu.userservice.entity.User;
import ynu.edu.userservice.entity.UserAddress;
import ynu.edu.userservice.mapper.UserAddressMapper;
import ynu.edu.userservice.mapper.UserMapper;
import ynu.edu.userservice.service.UserService;
import ynu.edu.userservice.util.JwtUtils;
import ynu.edu.userservice.util.PasswordUtils;
import ynu.edu.userservice.util.SmsUtils;
import ynu.edu.userservice.vo.LoginVO;
import ynu.edu.userservice.vo.UserVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserMapper userMapper;
    private final UserAddressMapper userAddressMapper;
    private final JwtUtils jwtUtils;
    private final PasswordUtils passwordUtils;
    private final SmsUtils smsUtils;
    
    @Override
    public Result<Void> sendSms(SendSmsDTO sendSmsDTO) {
        try {
            // 根据类型进行不同的校验
            if ("register".equals(sendSmsDTO.getType())) {
                // 注册时检查手机号是否已存在
                User existUser = userMapper.selectByPhone(sendSmsDTO.getPhone());
                if (existUser != null) {
                    return Result.error("手机号已注册");
                }
            } else if ("reset".equals(sendSmsDTO.getType())) {
                // 重置密码时检查手机号是否存在
                User resetUser = userMapper.selectByPhone(sendSmsDTO.getPhone());
                if (resetUser == null) {
                    return Result.error("手机号未注册");
                }
            }
            
            // 发送短信验证码
            boolean success = smsUtils.sendCode(sendSmsDTO.getPhone(), sendSmsDTO.getType());
            if (success) {
                return Result.<Void>success();
            } else {
                return Result.error("验证码发送失败");
            }
        } catch (Exception e) {
            log.error("发送短信验证码失败", e);
            return Result.error("验证码发送失败");
        }
    }
    
    @Override
    @Transactional
    public Result<LoginVO> register(RegisterDTO registerDTO) {
        try {
            // 校验验证码
            if (!smsUtils.verifyCode(registerDTO.getPhone(), "register", registerDTO.getCode())) {
                return Result.error("验证码错误或已过期");
            }
            
            // 检查手机号是否已注册
            User existUser = userMapper.selectByPhone(registerDTO.getPhone());
            if (existUser != null) {
                return Result.error("手机号已注册");
            }
            
            // 创建新用户
            User user = new User();
            user.setPhone(registerDTO.getPhone());
            user.setPassword(passwordUtils.encode(registerDTO.getPassword()));
            user.setNickname(StringUtils.hasText(registerDTO.getNickname()) ? 
                registerDTO.getNickname() : "用户" + registerDTO.getPhone().substring(7));
            user.setStatus(1); // 正常状态
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            
            userMapper.insert(user);
            
            // 生成token
            String accessToken = jwtUtils.generateAccessToken(user.getId(), user.getPhone());
            String refreshToken = jwtUtils.generateRefreshToken(user.getId(), user.getPhone());
            
            // 构建返回结果
            LoginVO loginVO = new LoginVO();
            UserVO userVO = convertToUserVO(user);
            loginVO.setUser(userVO);
            loginVO.setAccessToken(accessToken);
            loginVO.setRefreshToken(refreshToken);
            loginVO.setExpiresIn(86400000L); // 24小时
            
            return Result.success(loginVO);
        } catch (Exception e) {
            log.error("用户注册失败", e);
            return Result.error("注册失败");
        }
    }
    
    @Override
    public Result<LoginVO> login(LoginDTO loginDTO) {
        try {
            // 根据手机号查询用户
            User user = userMapper.selectByPhone(loginDTO.getPhone());
            if (user == null) {
                return Result.error("用户不存在");
            }
            
            // 检查用户状态
            if (user.getStatus() != 1) {
                return Result.error("账号已被禁用");
            }
            
            // 根据登录类型进行验证
            boolean loginSuccess = false;
            if ("password".equals(loginDTO.getLoginType())) {
                // 密码登录
                if (!StringUtils.hasText(loginDTO.getPassword())) {
                    return Result.error("密码不能为空");
                }
                loginSuccess = passwordUtils.matches(loginDTO.getPassword(), user.getPassword());
            } else if ("code".equals(loginDTO.getLoginType())) {
                // 验证码登录
                if (!StringUtils.hasText(loginDTO.getCode())) {
                    return Result.error("验证码不能为空");
                }
                loginSuccess = smsUtils.verifyCode(loginDTO.getPhone(), "login", loginDTO.getCode());
            }
            
            if (!loginSuccess) {
                return Result.error("登录验证失败");
            }
            
            // 生成token
            String accessToken = jwtUtils.generateAccessToken(user.getId(), user.getPhone());
            String refreshToken = jwtUtils.generateRefreshToken(user.getId(), user.getPhone());
            
            // 构建返回结果
            LoginVO loginVO = new LoginVO();
            UserVO userVO = convertToUserVO(user);
            loginVO.setUser(userVO);
            loginVO.setAccessToken(accessToken);
            loginVO.setRefreshToken(refreshToken);
            loginVO.setExpiresIn(86400000L); // 24小时
            
            return Result.success(loginVO);
        } catch (Exception e) {
            log.error("用户登录失败", e);
            return Result.error("登录失败");
        }
    }
    
    @Override
    public Result<LoginVO> refreshToken(String refreshToken) {
        try {
            // 验证refresh token
            if (!jwtUtils.validateToken(refreshToken)) {
                return Result.error("刷新令牌无效");
            }
            
            // 从token中获取用户信息
            Long userId = jwtUtils.getUserIdFromToken(refreshToken);
            String phone = jwtUtils.getPhoneFromToken(refreshToken);
            
            if (userId == null || phone == null) {
                return Result.error("刷新令牌无效");
            }
            
            // 查询用户信息
            User user = userMapper.selectById(userId);
            if (user == null || !phone.equals(user.getPhone())) {
                return Result.error("用户信息不匹配");
            }
            
            // 生成新的token
            String newAccessToken = jwtUtils.generateAccessToken(userId, phone);
            String newRefreshToken = jwtUtils.generateRefreshToken(userId, phone);
            
            // 构建返回结果
            LoginVO loginVO = new LoginVO();
            UserVO userVO = convertToUserVO(user);
            loginVO.setUser(userVO);
            loginVO.setAccessToken(newAccessToken);
            loginVO.setRefreshToken(newRefreshToken);
            loginVO.setExpiresIn(86400000L); // 24小时
            
            return Result.success(loginVO);
        } catch (Exception e) {
            log.error("刷新令牌失败", e);
            return Result.error("刷新令牌失败");
        }
    }
    
    @Override
    @Transactional
    public Result<Void> resetPassword(ResetPasswordDTO resetPasswordDTO) {
        try {
            // 校验验证码
            if (!smsUtils.verifyCode(resetPasswordDTO.getPhone(), "reset", resetPasswordDTO.getCode())) {
                return Result.error("验证码错误或已过期");
            }
            
            // 查询用户
            User user = userMapper.selectByPhone(resetPasswordDTO.getPhone());
            if (user == null) {
                return Result.error("用户不存在");
            }
            
            // 更新密码
            user.setPassword(passwordUtils.encode(resetPasswordDTO.getNewPassword()));
            user.setUpdateTime(LocalDateTime.now());
            userMapper.updateById(user);
            
            return Result.success();
        } catch (Exception e) {
            log.error("密码重置失败", e);
            return Result.error("密码重置失败");
        }
    }
    
    @Override
    public Result<UserVO> getUserInfo(Long userId) {
        try {
            User user = userMapper.selectById(userId);
            if (user == null) {
                return Result.error("用户不存在");
            }
            
            UserVO userVO = convertToUserVO(user);
            return Result.success(userVO);
        } catch (Exception e) {
            log.error("获取用户信息失败", e);
            return Result.error("获取用户信息失败");
        }
    }
    
    @Override
    @Transactional
    public Result<UserVO> updateUserInfo(Long userId, UserUpdateDTO updateDTO) {
        try {
            User user = userMapper.selectById(userId);
            if (user == null) {
                return Result.error("用户不存在");
            }
            
            // 更新用户信息
            if (StringUtils.hasText(updateDTO.getNickname())) {
                user.setNickname(updateDTO.getNickname());
            }
            if (StringUtils.hasText(updateDTO.getAvatar())) {
                user.setAvatar(updateDTO.getAvatar());
            }
            if (updateDTO.getGender() != null) {
                user.setGender(updateDTO.getGender());
            }
            user.setUpdateTime(LocalDateTime.now());
            
            userMapper.updateById(user);
            
            UserVO userVO = convertToUserVO(user);
            return Result.success(userVO);
        } catch (Exception e) {
            log.error("更新用户信息失败", e);
            return Result.error("更新用户信息失败");
        }
    }
    
    @Override
    public Result<Void> logout(String token) {
        // 在实际项目中，这里应该将token加入黑名单
        // 由于是演示项目，这里简单返回成功
        return Result.success();
    }
    
    @Override
    @Transactional
    public Result<Void> addAddress(Long userId, AddressDTO addressDTO) {
        try {
            UserAddress address = new UserAddress();
            BeanUtils.copyProperties(addressDTO, address);
            address.setUserId(userId);
            address.setCreateTime(LocalDateTime.now());
            address.setUpdateTime(LocalDateTime.now());
            
            // 如果是第一个地址，设置为默认地址
            List<UserAddress> existingAddresses = userAddressMapper.selectByUserId(userId);
            if (existingAddresses.isEmpty()) {
                address.setIsDefault(1);
            } else {
                address.setIsDefault(0);
            }
            
            userAddressMapper.insert(address);
            return Result.success();
        } catch (Exception e) {
            log.error("添加地址失败", e);
            return Result.error("添加地址失败");
        }
    }
    
    @Override
    @Transactional
    public Result<Void> updateAddress(Long userId, AddressDTO addressDTO) {
        try {
            UserAddress address = userAddressMapper.selectById(addressDTO.getId());
            if (address == null || !address.getUserId().equals(userId)) {
                return Result.error("地址不存在");
            }
            
            BeanUtils.copyProperties(addressDTO, address);
            address.setUpdateTime(LocalDateTime.now());
            userAddressMapper.updateById(address);
            
            return Result.success();
        } catch (Exception e) {
            log.error("更新地址失败", e);
            return Result.error("更新地址失败");
        }
    }
    
    @Override
    @Transactional
    public Result<Void> deleteAddress(Long userId, Long addressId) {
        try {
            UserAddress address = userAddressMapper.selectById(addressId);
            if (address == null || !address.getUserId().equals(userId)) {
                return Result.error("地址不存在");
            }
            
            userAddressMapper.deleteById(addressId);
            return Result.success();
        } catch (Exception e) {
            log.error("删除地址失败", e);
            return Result.error("删除地址失败");
        }
    }
    
    @Override
    @Transactional
    public Result<Void> setDefaultAddress(Long userId, Long addressId) {
        try {
            UserAddress address = userAddressMapper.selectById(addressId);
            if (address == null || !address.getUserId().equals(userId)) {
                return Result.error("地址不存在");
            }
            
            // 先取消所有默认地址
            userAddressMapper.resetDefault(userId);
            
            // 设置新的默认地址
            address.setIsDefault(1);
            address.setUpdateTime(LocalDateTime.now());
            userAddressMapper.updateById(address);
            
            return Result.success();
        } catch (Exception e) {
            log.error("设置默认地址失败", e);
            return Result.error("设置默认地址失败");
        }
    }
    
    @Override
    public Result<List<UserAddress>> getUserAddresses(Long userId) {
        try {
            List<UserAddress> addresses = userAddressMapper.selectByUserId(userId);
            return Result.success(addresses);
        } catch (Exception e) {
            log.error("获取用户地址列表失败", e);
            return Result.error("获取用户地址列表失败");
        }
    }
    
    /**
     * 转换User为UserVO
     */
    private UserVO convertToUserVO(User user) {
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setPhone(user.getPhone());
        userVO.setNickname(user.getNickname());
        userVO.setAvatar(user.getAvatar());
        userVO.setGender(user.getGender());
        userVO.setStatus(user.getStatus());
        userVO.setCreateTime(user.getCreateTime());
        return userVO;
    }
}
