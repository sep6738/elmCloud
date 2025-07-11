// 文件路径: C:\Users\q\Desktop\软件服务工程\elmCloud\services\payment-service\src\main\java\ynu\edu\paymentservice\entity\Refund.java
package ynu.edu.paymentservice.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class Refund {
    private Long id;
    private String refundNo; // 修正
    private String paymentNo; // 修正
    private String orderNo;   // 修正
    private Long userId;      // 修正
    private BigDecimal amount;
    private String reason;
    private Integer status;
    private String thirdPartyRefundNo; // 修正
    private LocalDateTime refundedAt; // 修正
    private String remark;
    private LocalDateTime createdAt; // 修正
    private LocalDateTime updatedAt; // 修正
    private Integer deleted;
}