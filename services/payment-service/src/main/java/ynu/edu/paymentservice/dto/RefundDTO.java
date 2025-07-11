package ynu.edu.paymentservice.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * 退款申请DTO
 */
@Data
public class RefundDTO {
    
    /**
     * 支付单号
     */
    @NotBlank(message = "支付单号不能为空")
    private String paymentNo;
    
    /**
     * 退款金额
     */
    @NotNull(message = "退款金额不能为空")
    @Positive(message = "退款金额必须大于0")
    private BigDecimal amount;
    
    /**
     * 退款原因
     */
    @NotBlank(message = "退款原因不能为空")
    private String reason;
    
    /**
     * 备注
     */
    private String remark;
}
