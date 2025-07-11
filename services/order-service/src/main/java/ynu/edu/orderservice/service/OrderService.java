package ynu.edu.orderservice.service;

import java.util.List;

import ynu.edu.orderservice.common.Result;
import ynu.edu.orderservice.dto.AddToCartDTO;
import ynu.edu.orderservice.dto.CreateOrderDTO;
import ynu.edu.orderservice.dto.OrderQueryDTO;
import ynu.edu.orderservice.dto.PreOrderDTO;
import ynu.edu.orderservice.dto.UpdateCartDTO;
import ynu.edu.orderservice.entity.ShoppingCart;
import ynu.edu.orderservice.vo.OrderVO;
import ynu.edu.orderservice.vo.PreOrderVO;

/**
 * 订单服务接口
 */
public interface OrderService {
    
    // ==================== 购物车管理 ====================
    
    /**
     * 添加商品到购物车
     */
    Result<Void> addToCart(Long userId, AddToCartDTO addToCartDTO);
    
    /**
     * 更新购物车商品数量
     */
    Result<Void> updateCartQuantity(Long userId, UpdateCartDTO updateCartDTO);
    
    /**
     * 删除购物车商品
     */
    Result<Void> removeFromCart(Long userId, Long cartId);
    
    /**
     * 清空指定商家的购物车
     */
    Result<Void> clearCart(Long userId, Long storeId);
    
    /**
     * 获取用户购物车
     */
    Result<List<ShoppingCart>> getCartItems(Long userId);
    
    // ==================== 订单管理 ====================
    
    /**
     * 预结算订单
     */
    Result<PreOrderVO> preOrder(Long userId, PreOrderDTO preOrderDTO);
    
    /**
     * 创建订单
     */
    Result<OrderVO> createOrder(Long userId, CreateOrderDTO createOrderDTO);
    
    /**
     * 获取订单列表
     */
    Result<List<OrderVO>> getOrderList(OrderQueryDTO queryDTO);
    
    /**
     * 获取订单详情
     */
    Result<OrderVO> getOrderDetail(Long userId, Long orderId);
    
    /**
     * 取消订单
     */
    Result<Void> cancelOrder(Long userId, Long orderId, String reason);
    
    /**
     * 申请退款
     */
    Result<Void> applyRefund(Long userId, Long orderId, String reason);
    
    /**
     * 再来一单
     */
    Result<Void> reorder(Long userId, Long orderId);
    
    /**
     * 更新订单状态
     */
    Result<Void> updateOrderStatus(Long orderId, Integer status);
}
