package ynu.edu.orderservice.dto;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * 添加购物车DTO
 */
@Data
public class AddToCartDTO {
    
    /**
     * 商家ID
     */
    @NotNull(message = "商家ID不能为空")
    private Long storeId;
    
    /**
     * 商品ID
     */
    @NotNull(message = "商品ID不能为空")
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
    @NotNull(message = "商品单价不能为空")
    @Positive(message = "商品单价必须大于0")
    private BigDecimal unitPrice;
    
    /**
     * 购买数量
     */
    @NotNull(message = "购买数量不能为空")
    @Positive(message = "购买数量必须大于0")
    private Integer quantity;
}
