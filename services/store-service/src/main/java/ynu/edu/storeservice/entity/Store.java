package ynu.edu.storeservice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 商家实体类
 */
public class Store {
    
    private Long id;
    
    /**
     * 商家名称
     */
    private String name;
    
    /**
     * 商家LOGO
     */
    private String logo;
    
    /**
     * 商家描述
     */
    private String description;
    
    /**
     * 商家公告
     */
    private String announcement;
    
    /**
     * 商家地址
     */
    private String address;
    
    /**
     * 联系电话
     */
    private String phone;
    
    /**
     * 经度
     */
    private Double longitude;
    
    /**
     * 纬度
     */
    private Double latitude;
    
    /**
     * 营业开始时间
     */
    private LocalTime openTime;
    
    /**
     * 营业结束时间
     */
    private LocalTime closeTime;
    
    /**
     * 起送价
     */
    private BigDecimal minDeliveryAmount;
    
    /**
     * 配送费
     */
    private BigDecimal deliveryFee;
    
    /**
     * 平均评分
     */
    private BigDecimal rating;
    
    /**
     * 月销量
     */
    private Integer monthlySales;
    
    /**
     * 人均消费
     */
    private BigDecimal avgPrice;
    
    /**
     * 商家分类ID
     */
    private Long categoryId;
    
    /**
     * 商家状态：0-休息中，1-营业中，2-已关闭
     */
    private Integer status;
    
    /**
     * 商家特色标签（JSON格式存储）
     */
    private String tags;
    
    /**
     * 配送范围（公里）
     */
    private Double deliveryRange;
    
    /**
     * 预估送达时间（分钟）
     */
    private Integer estimatedDeliveryTime;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 逻辑删除
     */
    private Integer deleted;
    
    // Getter and Setter methods
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getAnnouncement() { return announcement; }
    public void setAnnouncement(String announcement) { this.announcement = announcement; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    
    public LocalTime getOpenTime() { return openTime; }
    public void setOpenTime(LocalTime openTime) { this.openTime = openTime; }
    
    public LocalTime getCloseTime() { return closeTime; }
    public void setCloseTime(LocalTime closeTime) { this.closeTime = closeTime; }
    
    public BigDecimal getMinDeliveryAmount() { return minDeliveryAmount; }
    public void setMinDeliveryAmount(BigDecimal minDeliveryAmount) { this.minDeliveryAmount = minDeliveryAmount; }
    
    public BigDecimal getDeliveryFee() { return deliveryFee; }
    public void setDeliveryFee(BigDecimal deliveryFee) { this.deliveryFee = deliveryFee; }
    
    public BigDecimal getRating() { return rating; }
    public void setRating(BigDecimal rating) { this.rating = rating; }
    
    public Integer getMonthlySales() { return monthlySales; }
    public void setMonthlySales(Integer monthlySales) { this.monthlySales = monthlySales; }
    
    public BigDecimal getAvgPrice() { return avgPrice; }
    public void setAvgPrice(BigDecimal avgPrice) { this.avgPrice = avgPrice; }
    
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    
    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
    
    public Double getDeliveryRange() { return deliveryRange; }
    public void setDeliveryRange(Double deliveryRange) { this.deliveryRange = deliveryRange; }
    
    public Integer getEstimatedDeliveryTime() { return estimatedDeliveryTime; }
    public void setEstimatedDeliveryTime(Integer estimatedDeliveryTime) { this.estimatedDeliveryTime = estimatedDeliveryTime; }
    
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    
    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
    
    public Integer getDeleted() { return deleted; }
    public void setDeleted(Integer deleted) { this.deleted = deleted; }
}
