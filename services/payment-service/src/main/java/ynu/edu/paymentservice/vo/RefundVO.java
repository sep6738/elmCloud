package ynu.edu.paymentservice.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 退款记录VO
 */
@Data
@Accessors(chain = true)
public class RefundVO {
    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 退款单号
     */
    private String refundNo;
    
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
     * 退款金额
     */
    private BigDecimal amount;
    
    /**
     * 退款原因
     */
    private String reason;
    
    /**
     * 退款状态：1-退款中，2-退款成功，3-退款失败
     */
    private Integer status;
    
    /**
     * 退款状态名称
     */
    private String statusName;
    
    /**
     * 第三方退款流水号
     */
    private String thirdPartyRefundNo;
    
    /**
     * 退款完成时间
     */
    private LocalDateTime refundedAt;
    
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
