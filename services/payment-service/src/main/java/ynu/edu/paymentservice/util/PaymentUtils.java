package ynu.edu.paymentservice.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

/**
 * 支付工具类
 */
public class PaymentUtils {
    
    private static final String PAYMENT_PREFIX = "PAY";
    private static final String REFUND_PREFIX = "REF";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final Random RANDOM = new Random();
    
    /**
     * 生成支付单号
     */
    public static String generatePaymentNo() {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String randomSuffix = String.format("%04d", RANDOM.nextInt(10000));
        return PAYMENT_PREFIX + timestamp + randomSuffix;
    }
    
    /**
     * 生成退款单号
     */
    public static String generateRefundNo() {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String randomSuffix = String.format("%04d", RANDOM.nextInt(10000));
        return REFUND_PREFIX + timestamp + randomSuffix;
    }
    
    /**
     * 生成第三方交易流水号（模拟）
     */
    public static String generateThirdPartyTradeNo() {
        return "THIRD_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
    }
    
    /**
     * 生成支付二维码内容（模拟）
     */
    public static String generateQrCode(String paymentNo, String amount) {
        return "elmcloud://pay?no=" + paymentNo + "&amount=" + amount;
    }
    
    /**
     * 生成支付链接（模拟）
     */
    public static String generatePaymentUrl(String paymentNo, Integer paymentMethod) {
        String baseUrl = "https://pay.elmcloud.com";
        String methodPath;
        
        switch (paymentMethod) {
            case 1: // 支付宝
                methodPath = "/alipay";
                break;
            case 2: // 微信
                methodPath = "/wechat";
                break;
            case 3: // 银行卡
                methodPath = "/bank";
                break;
            default:
                methodPath = "/default";
        }
        
        return baseUrl + methodPath + "/pay?no=" + paymentNo;
    }
    
    /**
     * 模拟支付结果（随机成功或失败）
     */
    public static boolean simulatePaymentResult() {
        // 90% 成功率
        return RANDOM.nextInt(100) < 90;
    }
    
    /**
     * 模拟退款结果（随机成功或失败）
     */
    public static boolean simulateRefundResult() {
        // 95% 成功率
        return RANDOM.nextInt(100) < 95;
    }
}
