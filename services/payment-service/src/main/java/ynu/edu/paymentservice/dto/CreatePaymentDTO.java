package ynu.edu.paymentservice.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * 创建支付订单DTO
 */
@Data
public class CreatePaymentDTO {
    
    /**
     * 订单号
     */
    @NotBlank(message = "订单号不能为空")
    private String orderNo;
    
    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;
    
    /**
     * 支付金额
     */
    @NotNull(message = "支付金额不能为空")
    @Positive(message = "支付金额必须大于0")
    private BigDecimal amount;
    
    /**
     * 支付方式：1-支付宝，2-微信，3-银行卡
     */
    @NotNull(message = "支付方式不能为空")
    private Integer paymentMethod;
    
    /**
     * 备注
     */
    private String remark;
}
