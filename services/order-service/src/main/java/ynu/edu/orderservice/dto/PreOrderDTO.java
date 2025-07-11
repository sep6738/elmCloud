package ynu.edu.orderservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 预结算DTO
 */
@Data
public class PreOrderDTO {
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 商家ID
     */
    private Long storeId;
    
    /**
     * 收货地址ID
     */
    private Long addressId;
    
    /**
     * 购物车项目列表
     */
    private List<CartItemDTO> cartItems;
    
    /**
     * 购物车项目信息
     */
    @Data
    public static class CartItemDTO {
        private Long cartId;
        private Long productId;
        private String productName;
        private BigDecimal unitPrice;
        private Integer quantity;
        private BigDecimal subtotal;
    }
}
