package ynu.edu.storeservice.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * 商家视图对象
 */
@Data
public class StoreVO {
    
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
     * 商家状态：0-休息中，1-营业中，2-已关闭
     */
    private Integer status;
    
    /**
     * 商家特色标签
     */
    private List<String> tags;
    
    /**
     * 配送范围（公里）
     */
    private Double deliveryRange;
    
    /**
     * 预估送达时间（分钟）
     */
    private Integer estimatedDeliveryTime;
    
    /**
     * 距离用户的距离（公里）
     */
    private Double distance;
    
    /**
     * 是否营业中
     */
    private Boolean isOpen;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
