package ynu.edu.storeservice.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商家查询DTO
 */
@Data
public class StoreQueryDTO {
    
    /**
     * 关键词搜索（商家名称）
     */
    private String keyword;
    
    /**
     * 分类ID
     */
    private Long categoryId;
    
    /**
     * 用户经度
     */
    private Double longitude;
    
    /**
     * 用户纬度
     */
    private Double latitude;
    
    /**
     * 排序方式：rating-评分，distance-距离，sales-销量，delivery_time-送达时间
     */
    private String sortBy = "rating";
    
    /**
     * 排序方向：asc-升序，desc-降序
     */
    private String sortDir = "desc";
    
    /**
     * 最低评分
     */
    private BigDecimal minRating;
    
    /**
     * 最大配送费
     */
    private BigDecimal maxDeliveryFee;
    
    /**
     * 页码
     */
    private Integer pageNum = 1;
    
    /**
     * 页大小
     */
    private Integer pageSize = 10;
}
