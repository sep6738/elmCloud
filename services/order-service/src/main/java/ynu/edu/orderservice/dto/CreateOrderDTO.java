package ynu.edu.orderservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 创建订单DTO
 */
@Data
public class CreateOrderDTO {
    
    /**
     * 商家ID
     */
    private Long storeId;
    
    /**
     * 收货地址ID
     */
    private Long addressId;
    
    /**
     * 支付方式：1-微信支付，2-支付宝，3-余额支付
     */
    private Integer paymentMethod;
    
    /**
     * 优惠券ID
     */
    private Long couponId;
    
    /**
     * 用户备注
     */
    private String remark;
    
    /**
     * 商品总金额
     */
    private BigDecimal productAmount;
    
    /**
     * 实际支付金额
     */
    private BigDecimal actualAmount;
    
    /**
     * 订单商品列表
     */
    private List<OrderItemDTO> orderItems;
    
    @Data
    public static class OrderItemDTO {
        /**
         * 商品ID
         */
        private Long productId;
        
        /**
         * 商品名称
         */
        private String productName;
        
        /**
         * 商品图片
         */
        private String productImage;
        
        /**
         * 商品规格
         */
        private String productSpec;
        
        /**
         * 商品单价
         */
        private BigDecimal unitPrice;
        
        /**
         * 购买数量
         */
        private Integer quantity;
    }
}
