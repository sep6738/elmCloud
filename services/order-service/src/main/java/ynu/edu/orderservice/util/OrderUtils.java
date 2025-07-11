package ynu.edu.orderservice.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * 订单工具类
 */
public class OrderUtils {
    
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final Random RANDOM = new Random();
    
    /**
     * 生成订单号
     * 格式：年月日时分秒 + 6位随机数
     */
    public static String generateOrderNo() {
        String dateStr = LocalDateTime.now().format(DATE_FORMAT);
        int randomNum = RANDOM.nextInt(900000) + 100000; // 6位随机数
        return dateStr + randomNum;
    }
    
    /**
     * 获取订单状态描述
     */
    public static String getStatusDesc(Integer status) {
        if (status == null) {
            return "未知状态";
        }
        switch (status) {
            case 0: return "待支付";
            case 1: return "待接单";
            case 2: return "备餐中";
            case 3: return "待取餐";
            case 4: return "配送中";
            case 5: return "已送达";
            case 6: return "已完成";
            case 7: return "已取消";
            case 8: return "退款中";
            case 9: return "已退款";
            default: return "未知状态";
        }
    }
    
    /**
     * 获取支付方式描述
     */
    public static String getPaymentMethodDesc(Integer paymentMethod) {
        if (paymentMethod == null) {
            return "未知支付方式";
        }
        switch (paymentMethod) {
            case 1: return "微信支付";
            case 2: return "支付宝";
            case 3: return "余额支付";
            default: return "未知支付方式";
        }
    }
    
    /**
     * 判断订单是否可以取消
     */
    public static boolean canCancel(Integer status) {
        // 待支付和待接单状态可以取消
        return status != null && (status == 0 || status == 1);
    }
    
    /**
     * 判断订单是否可以申请退款
     */
    public static boolean canRefund(Integer status) {
        // 已完成的订单可以申请退款
        return status != null && status == 6;
    }
    
    /**
     * 判断订单是否可以再来一单
     */
    public static boolean canReorder(Integer status) {
        // 已完成或已取消的订单可以再来一单
        return status != null && (status == 6 || status == 7);
    }
}
