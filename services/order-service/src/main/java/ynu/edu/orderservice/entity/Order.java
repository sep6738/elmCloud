// 文件路径: C:\Users\q\Desktop\软件服务工程\elmCloud\services\order-service\src\main\java\ynu\edu\orderservice\entity\Order.java
package ynu.edu.orderservice.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order {
    private Long id;
    private String orderNo;
    private Long userId;
    private Long storeId;
    private String storeName;
    private Integer status;
    private BigDecimal productAmount;
    private BigDecimal packageFee;
    private BigDecimal deliveryFee;
    private BigDecimal discountAmount;
    private BigDecimal actualAmount;
    private Integer paymentMethod;
    private LocalDateTime paymentTime;
    private Long addressId;
    private String contactName;
    private String contactPhone;
    private String deliveryAddress;
    private String remark;
    private LocalDateTime estimatedDeliveryTime;
    private LocalDateTime actualDeliveryTime;
    private LocalDateTime finishTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer deleted;
}