package ynu.edu.storeservice.entity;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 商品分类实体类
 */
@Data
public class ProductCategory {
    
    private Long id;
    
    /**
     * 分类名称
     */
    private String name;
    
    /**
     * 分类图标
     */
    private String icon;
    
    /**
     * 商家ID
     */
    private Long store_id;
    
    /**
     * 排序序号
     */
    private Integer sort_order;
    
    /**
     * 状态：0-禁用，1-启用
     */
    private Integer status;
    
    /**
     * 创建时间
     */
    private LocalDateTime create_time;
    
    /**
     * 更新时间
     */
    private LocalDateTime update_time;
    
    /**
     * 逻辑删除
     */
    private Integer deleted;
}
