package ynu.edu.orderservice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 购物车实体类
 */
@Data
public class ShoppingCart {
    
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 商家ID
     */
    private Long storeId;
    
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
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
