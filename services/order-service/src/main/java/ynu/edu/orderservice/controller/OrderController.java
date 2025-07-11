package ynu.edu.orderservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ynu.edu.orderservice.common.Result;
import ynu.edu.orderservice.dto.*;
import ynu.edu.orderservice.entity.ShoppingCart;
import ynu.edu.orderservice.service.OrderService;
import ynu.edu.orderservice.vo.OrderVO;
import ynu.edu.orderservice.vo.PreOrderVO;

import java.util.List;

/**
 * 订单控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@Validated
public class OrderController {
    
    private final OrderService orderService;
    
    // ==================== 购物车管理 ====================
    
    /**
     * 添加商品到购物车
     */
    @PostMapping("/cart/add")
    public Result<Void> addToCart(
            @RequestHeader("userId") Long userId,
            @RequestBody AddToCartDTO addToCartDTO) {
        return orderService.addToCart(userId, addToCartDTO);
    }
    
    /**
     * 更新购物车商品数量
     */
    @PutMapping("/cart/update")
    public Result<Void> updateCartQuantity(
            @RequestHeader("userId") Long userId,
            @RequestBody UpdateCartDTO updateCartDTO) {
        return orderService.updateCartQuantity(userId, updateCartDTO);
    }
    
    /**
     * 删除购物车商品
     */
    @DeleteMapping("/cart/{cartId}")
    public Result<Void> removeFromCart(
            @RequestHeader("userId") Long userId,
            @PathVariable Long cartId) {
        return orderService.removeFromCart(userId, cartId);
    }
    
    /**
     * 清空指定商家的购物车
     */
    @DeleteMapping("/cart/clear/{storeId}")
    public Result<Void> clearCart(
            @RequestHeader("userId") Long userId,
            @PathVariable Long storeId) {
        return orderService.clearCart(userId, storeId);
    }
    
    /**
     * 获取用户购物车
     */
    @GetMapping("/cart")
    public Result<List<ShoppingCart>> getCartItems(@RequestHeader("userId") Long userId) {
        return orderService.getCartItems(userId);
    }
    
    // ==================== 订单管理 ====================
    
    /**
     * 预结算订单
     */
    @PostMapping("/pre-order")
    public Result<PreOrderVO> preOrder(
            @RequestHeader("userId") Long userId,
            @RequestBody PreOrderDTO preOrderDTO) {
        return orderService.preOrder(userId, preOrderDTO);
    }
    
    /**
     * 创建订单
     */
    @PostMapping("/create")
    public Result<OrderVO> createOrder(
            @RequestHeader("userId") Long userId,
            @RequestBody CreateOrderDTO createOrderDTO) {
        return orderService.createOrder(userId, createOrderDTO);
    }
    
    /**
     * 获取订单列表
     */
    @PostMapping("/list")
    public Result<List<OrderVO>> getOrderList(@RequestBody OrderQueryDTO queryDTO) {
        return orderService.getOrderList(queryDTO);
    }
    
    /**
     * 获取订单详情
     */
    @GetMapping("/{orderId}")
    public Result<OrderVO> getOrderDetail(
            @RequestHeader("userId") Long userId,
            @PathVariable Long orderId) {
        return orderService.getOrderDetail(userId, orderId);
    }
    
    /**
     * 取消订单
     */
    @PutMapping("/{orderId}/cancel")
    public Result<Void> cancelOrder(
            @RequestHeader("userId") Long userId,
            @PathVariable Long orderId,
            @RequestParam(required = false) String reason) {
        return orderService.cancelOrder(userId, orderId, reason);
    }
    
    /**
     * 申请退款
     */
    @PutMapping("/{orderId}/refund")
    public Result<Void> applyRefund(
            @RequestHeader("userId") Long userId,
            @PathVariable Long orderId,
            @RequestParam(required = false) String reason) {
        return orderService.applyRefund(userId, orderId, reason);
    }
    
    /**
     * 再来一单
     */
    @PostMapping("/{orderId}/reorder")
    public Result<Void> reorder(
            @RequestHeader("userId") Long userId,
            @PathVariable Long orderId) {
        return orderService.reorder(userId, orderId);
    }
    
    /**
     * 更新订单状态（内部接口，用于支付回调等）
     */
    @PutMapping("/{orderId}/status")
    public Result<Void> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam Integer status) {
        return orderService.updateOrderStatus(orderId, status);
    }
}
