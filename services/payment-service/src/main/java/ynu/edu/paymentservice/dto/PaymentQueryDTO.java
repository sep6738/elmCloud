package ynu.edu.paymentservice.dto;

import lombok.Data;

/**
 * 支付查询DTO
 */
@Data
public class PaymentQueryDTO {
    
    /**
     * 页码
     */
    private Integer page = 1;
    
    /**
     * 页大小
     */
    private Integer size = 10;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 订单号
     */
    private String orderNo;
    
    /**
     * 支付单号
     */
    private String paymentNo;
    
    /**
     * 支付状态：1-待支付，2-支付中，3-支付成功，4-支付失败，5-已退款
     */
    private Integer status;
    
    /**
     * 支付方式：1-支付宝，2-微信，3-银行卡
     */
    private Integer paymentMethod;
    
    /**
     * 开始时间
     */
    private String startTime;
    
    /**
     * 结束时间
     */
    private String endTime;
}
