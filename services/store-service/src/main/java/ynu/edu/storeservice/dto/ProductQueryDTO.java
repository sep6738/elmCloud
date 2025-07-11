package ynu.edu.storeservice.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * 商品查询DTO
 */
@Data
public class ProductQueryDTO {
    
    /**
     * 商家ID
     */
    @NotNull(message = "商家ID不能为空")
    private Long storeId;
    
    /**
     * 关键词搜索（商品名称）
     */
    private String keyword;
    
    /**
     * 分类ID
     */
    private Long categoryId;
    
    /**
     * 排序方式：sales-销量，price-价格，rating-评分
     */
    private String sortBy = "sales";
    
    /**
     * 排序方向：asc-升序，desc-降序
     */
    private String sortDir = "desc";
    
    /**
     * 最低价格
     */
    private BigDecimal minPrice;
    
    /**
     * 最高价格
     */
    private BigDecimal maxPrice;
    
    /**
     * 页码
     */
    @Positive(message = "页码必须大于0")
    private Integer pageNum = 1;
    
    /**
     * 页大小
     */
    @Positive(message = "页大小必须大于0")
    private Integer pageSize = 10;
}
