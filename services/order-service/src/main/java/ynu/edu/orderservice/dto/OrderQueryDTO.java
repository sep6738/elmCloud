package ynu.edu.orderservice.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单查询DTO
 */
@Data
public class OrderQueryDTO {
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 订单状态：
     * null-全部，0-待支付，1-待接单，2-备餐中，3-待取餐，4-配送中，5-已送达，6-已完成，7-已取消
     */
    private Integer status;
    
    /**
     * 订单类型：1-进行中（0-6状态），2-历史订单（6,7状态）
     */
    private Integer orderType;
    
    /**
     * 页码
     */
    private Integer pageNum = 1;
    
    /**
     * 页大小
     */
    private Integer pageSize = 10;
}
