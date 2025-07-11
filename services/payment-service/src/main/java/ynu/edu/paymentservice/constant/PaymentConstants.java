package ynu.edu.paymentservice.constant;

/**
 * 支付常量
 */
public class PaymentConstants {
    
    /**
     * 支付状态
     */
    public static class PaymentStatus {
        public static final int PENDING = 1;      // 待支付
        public static final int PROCESSING = 2;   // 支付中
        public static final int SUCCESS = 3;      // 支付成功
        public static final int FAILED = 4;       // 支付失败
        public static final int REFUNDED = 5;     // 已退款
    }
    
    /**
     * 支付方式
     */
    public static class PaymentMethod {
        public static final int ALIPAY = 1;       // 支付宝
        public static final int WECHAT = 2;       // 微信
        public static final int BANK_CARD = 3;    // 银行卡
    }
    
    /**
     * 退款状态
     */
    public static class RefundStatus {
        public static final int PROCESSING = 1;   // 退款中
        public static final int SUCCESS = 2;      // 退款成功
        public static final int FAILED = 3;       // 退款失败
    }
    
    /**
     * 支付状态名称
     */
    public static String getPaymentStatusName(Integer status) {
        if (status == null) {
            return "未知";
        }
        switch (status) {
            case PaymentStatus.PENDING:
                return "待支付";
            case PaymentStatus.PROCESSING:
                return "支付中";
            case PaymentStatus.SUCCESS:
                return "支付成功";
            case PaymentStatus.FAILED:
                return "支付失败";
            case PaymentStatus.REFUNDED:
                return "已退款";
            default:
                return "未知";
        }
    }
    
    /**
     * 支付方式名称
     */
    public static String getPaymentMethodName(Integer method) {
        if (method == null) {
            return "未知";
        }
        switch (method) {
            case PaymentMethod.ALIPAY:
                return "支付宝";
            case PaymentMethod.WECHAT:
                return "微信支付";
            case PaymentMethod.BANK_CARD:
                return "银行卡";
            default:
                return "未知";
        }
    }
    
    /**
     * 退款状态名称
     */
    public static String getRefundStatusName(Integer status) {
        if (status == null) {
            return "未知";
        }
        switch (status) {
            case RefundStatus.PROCESSING:
                return "退款中";
            case RefundStatus.SUCCESS:
                return "退款成功";
            case RefundStatus.FAILED:
                return "退款失败";
            default:
                return "未知";
        }
    }
}
