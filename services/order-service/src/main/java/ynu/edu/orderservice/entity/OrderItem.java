// 文件路径: C:\Users\q\Desktop\软件服务工程\elmCloud\services\order-service\src\main\java\ynu\edu\orderservice\entity\OrderItem.java
package ynu.edu.orderservice.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderItem {
    private Long id;
    private Long orderId;
    private Long productId;
    private String productName;
    private String productImage;
    private String productSpec;
    private BigDecimal unitPrice;
    private Integer quantity;
    private BigDecimal subtotal;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}