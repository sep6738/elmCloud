package ynu.edu.userservice.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 短信工具类(模拟实现)
 */
@Slf4j
@Component
public class SmsUtils {
    
    private static final int CODE_LENGTH = 6;
    private static final int CODE_EXPIRE_MINUTES = 5;
    
    // 使用内存存储验证码（生产环境应使用Redis或数据库）
    private final Map<String, CodeInfo> codeStorage = new ConcurrentHashMap<>();
    
    /**
     * 发送短信验证码
     */
    public boolean sendCode(String phone, String type) {
        try {
            String code = generateCode();
            String key = type + ":" + phone;
            
            // 将验证码存储到内存，有效期5分钟
            CodeInfo codeInfo = new CodeInfo(code, LocalDateTime.now().plusMinutes(CODE_EXPIRE_MINUTES));
            codeStorage.put(key, codeInfo);
            
            // 模拟发送短信
            log.info("发送短信验证码到 {} , 类型: {}, 验证码: {}", phone, type, code);
            
            return true;
        } catch (Exception e) {
            log.error("发送短信验证码失败", e);
            return false;
        }
    }
    
    /**
     * 验证短信验证码
     */
    public boolean verifyCode(String phone, String type, String code) {
        try {
            String key = type + ":" + phone;
            CodeInfo codeInfo = codeStorage.get(key);
            
            if (codeInfo != null && codeInfo.getCode().equals(code)) {
                // 检查是否过期
                if (LocalDateTime.now().isBefore(codeInfo.getExpireTime())) {
                    // 验证成功后删除验证码
                    codeStorage.remove(key);
                    return true;
                } else {
                    // 过期删除
                    codeStorage.remove(key);
                }
            }
            
            return false;
        } catch (Exception e) {
            log.error("验证短信验证码失败", e);
            return false;
        }
    }
    
    /**
     * 生成随机验证码
     */
    private String generateCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(random.nextInt(10));
        }
        
        return code.toString();
    }
    
    /**
     * 验证码信息类
     */
    private static class CodeInfo {
        private final String code;
        private final LocalDateTime expireTime;
        
        public CodeInfo(String code, LocalDateTime expireTime) {
            this.code = code;
            this.expireTime = expireTime;
        }
        
        public String getCode() {
            return code;
        }
        
        public LocalDateTime getExpireTime() {
            return expireTime;
        }
    }
}
