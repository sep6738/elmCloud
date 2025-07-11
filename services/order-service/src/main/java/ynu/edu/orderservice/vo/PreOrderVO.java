package ynu.edu.orderservice.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 预结算结果VO
 */
@Data
public class PreOrderVO {
    
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
     * 实际应付金额
     */
    private BigDecimal actualAmount;
    
    /**
     * 预估送达时间
     */
    private LocalDateTime estimatedDeliveryTime;
    
    /**
     * 商品列表
     */
    private List<ProductItem> productItems;
    
    /**
     * 可用优惠券列表
     */
    private List<CouponInfo> availableCoupons;
    
    @Data
    public static class ProductItem {
        private Long productId;
        private String productName;
        private String productImage;
        private BigDecimal unitPrice;
        private Integer quantity;
        private BigDecimal subtotal;
    }
    
    @Data
    public static class CouponInfo {
        private Long couponId;
        private String couponName;
        private String couponDesc;
        private BigDecimal discountAmount;
        private BigDecimal minAmount;
    }
}
