package ynu.edu.orderservice.dto;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * 更新购物车数量DTO
 */
@Data
public class UpdateCartDTO {
    
    /**
     * 购物车项ID
     */
    @NotNull(message = "购物车ID不能为空")
    private Long cartId;
    
    /**
     * 新的数量
     */
    @NotNull(message = "数量不能为空")
    @Positive(message = "数量必须大于0")
    private Integer quantity;
}
