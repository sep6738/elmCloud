package ynu.edu.paymentservice.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支付记录VO
 */
@Data
@Accessors(chain = true)
public class PaymentVO {
    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 支付单号
     */
    private String paymentNo;
    
    /**
     * 订单号
     */
    private String orderNo;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 支付金额
     */
    private BigDecimal amount;
    
    /**
     * 支付方式：1-支付宝，2-微信，3-银行卡
     */
    private Integer paymentMethod;
    
    /**
     * 支付方式名称
     */
    private String paymentMethodName;
    
    /**
     * 支付状态：1-待支付，2-支付中，3-支付成功，4-支付失败，5-已退款
     */
    private Integer status;
    
    /**
     * 支付状态名称
     */
    private String statusName;
    
    /**
     * 第三方支付流水号
     */
    private String thirdPartyTradeNo;
    
    /**
     * 支付完成时间
     */
    private LocalDateTime paidAt;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
