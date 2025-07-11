// 文件路径: C:\Users\q\Desktop\软件服务工程\elmCloud\services\payment-service\src\main\java\ynu\edu\paymentservice\entity\Payment.java
package ynu.edu.paymentservice.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class Payment {
    private Long id;
    private String paymentNo; // 修正
    private String orderNo;   // 修正
    private Long userId;      // 修正
    private BigDecimal amount;
    private Integer paymentMethod; // 修正
    private Integer status;
    private String thirdPartyTradeNo; // 修正
    private LocalDateTime paidAt; // 修正
    private String remark;
    private LocalDateTime createdAt; // 修正
    private LocalDateTime updatedAt; // 修正
    private Integer deleted;
}