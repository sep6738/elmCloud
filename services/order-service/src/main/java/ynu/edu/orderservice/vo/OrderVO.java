package ynu.edu.orderservice.vo;

import lombok.Data;
import ynu.edu.orderservice.entity.OrderItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单视图对象
 */
@Data
public class OrderVO {
    
    private Long id;
    
    /**
     * 订单号
     */
    private String orderNo;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 商家ID
     */
    private Long storeId;
    
    /**
     * 商家名称
     */
    private String storeName;
    
    /**
     * 订单状态：0-待支付，1-待接单，2-备餐中，3-待取餐，4-配送中，5-已送达，6-已完成，7-已取消
     */
    private Integer status;
    
    /**
     * 订单状态描述
     */
    private String statusDesc;
    
    /**
     * 商品总价
     */
    private BigDecimal productAmount;
    
    /**
     * 包装费
     */
    private BigDecimal packageFee;
    
    /**
     * 配送费
     */
    private BigDecimal deliveryFee;
    
    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;
    
    /**
     * 实际支付金额
     */
    private BigDecimal actualAmount;
    
    /**
     * 支付方式：1-微信支付，2-支付宝，3-余额支付
     */
    private Integer paymentMethod;
    
    /**
     * 支付方式描述
     */
    private String paymentMethodDesc;
    
    /**
     * 支付时间
     */
    private LocalDateTime paymentTime;
    
    /**
     * 收货地址ID
     */
    private Long addressId;
    
    /**
     * 收货人姓名
     */
    private String contactName;
    
    /**
     * 收货人电话
     */
    private String contactPhone;
    
    /**
     * 收货地址
     */
    private String deliveryAddress;
    
    /**
     * 用户备注
     */
    private String remark;
    
    /**
     * 预估送达时间
     */
    private LocalDateTime estimatedDeliveryTime;
    
    /**
     * 实际送达时间
     */
    private LocalDateTime actualDeliveryTime;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 订单商品列表
     */
    private List<OrderItem> orderItems;
    
    /**
     * 是否可以取消
     */
    private Boolean canCancel;
    
    /**
     * 是否可以申请退款
     */
    private Boolean canRefund;
    
    /**
     * 是否可以再来一单
     */
    private Boolean canReorder;
}
