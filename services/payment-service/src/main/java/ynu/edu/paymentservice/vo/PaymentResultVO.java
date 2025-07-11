package ynu.edu.paymentservice.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 支付结果VO
 */
@Data
@Accessors(chain = true)
public class PaymentResultVO {
    
    /**
     * 支付单号
     */
    private String paymentNo;
    
    /**
     * 支付状态：1-待支付，2-支付中，3-支付成功，4-支付失败
     */
    private Integer status;
    
    /**
     * 支付状态名称
     */
    private String statusName;
    
    /**
     * 支付链接或二维码（用于跳转到第三方支付页面）
     */
    private String paymentUrl;
    
    /**
     * 支付信息（如二维码内容等）
     */
    private String paymentInfo;
    
    /**
     * 消息提示
     */
    private String message;
}
