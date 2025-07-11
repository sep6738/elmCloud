package ynu.edu.storeservice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品实体类
 */
public class Product {
    
    private Long id;
    
    /**
     * 商品名称
     */
    private String name;
    
    /**
     * 商品描述
     */
    private String description;
    
    /**
     * 商品图片
     */
    private String image;
    
    /**
     * 商品价格
     */
    private BigDecimal price;
    
    /**
     * 原价
     */
    private BigDecimal original_price;
    
    /**
     * 商家ID
     */
    private Long store_id;
    
    /**
     * 分类ID
     */
    private Long category_id;
    
    /**
     * 销量
     */
    private Integer sales_count;
    
    /**
     * 库存数量
     */
    private Integer stock_count;
    
    /**
     * 商品状态：0-下架，1-上架
     */
    private Integer status;
    
    /**
     * 排序序号
     */
    private Integer sort_order;
    
    /**
     * 商品标签（JSON格式）
     */
    private String tags;
    
    /**
     * 商品规格（JSON格式）
     */
    private String specs;
    
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
    
    // Getter and Setter methods
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    
    public BigDecimal getOriginal_price() { return original_price; }
    public void setOriginal_price(BigDecimal original_price) { this.original_price = original_price; }
    
    public Long getStore_id() { return store_id; }
    public void setStore_id(Long store_id) { this.store_id = store_id; }
    
    public Long getCategory_id() { return category_id; }
    public void setCategory_id(Long category_id) { this.category_id = category_id; }
    
    public Integer getSales_count() { return sales_count; }
    public void setSales_count(Integer sales_count) { this.sales_count = sales_count; }
    
    public Integer getStock_count() { return stock_count; }
    public void setStock_count(Integer stock_count) { this.stock_count = stock_count; }
    
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    
    public Integer getSort_order() { return sort_order; }
    public void setSort_order(Integer sort_order) { this.sort_order = sort_order; }
    
    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
    
    public String getSpecs() { return specs; }
    public void setSpecs(String specs) { this.specs = specs; }
    
    public LocalDateTime getCreate_time() { return create_time; }
    public void setCreate_time(LocalDateTime create_time) { this.create_time = create_time; }
    
    public LocalDateTime getUpdate_time() { return update_time; }
    public void setUpdate_time(LocalDateTime update_time) { this.update_time = update_time; }
    
    public Integer getDeleted() { return deleted; }
    public void setDeleted(Integer deleted) { this.deleted = deleted; }
}
