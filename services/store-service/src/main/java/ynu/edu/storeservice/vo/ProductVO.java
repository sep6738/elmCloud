package ynu.edu.storeservice.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品视图对象
 */
@Data
public class ProductVO {
    
    private Long id;
    
    /**
     * 商品名称
     */
    private String name;
    
    /**
     * 商品图片
     */
    private String image;
    
    /**
     * 商品描述
     */
    private String description;
    
    /**
     * 商品价格
     */
    private BigDecimal price;
    
    /**
     * 原价（用于显示折扣）
     */
    private BigDecimal originalPrice;
    
    /**
     * 月销量
     */
    private Integer monthlySales;
    
    /**
     * 商品评分
     */
    private BigDecimal rating;
    
    /**
     * 商品分类ID
     */
    private Long categoryId;
    
    /**
     * 商品分类名称
     */
    private String categoryName;
    
    /**
     * 商家ID
     */
    private Long storeId;
    
    /**
     * 商家名称
     */
    private String storeName;
    
    /**
     * 商品状态：0-下架，1-上架
     */
    private Integer status;
    
    /**
     * 商品特色标签
     */
    private String tags;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
