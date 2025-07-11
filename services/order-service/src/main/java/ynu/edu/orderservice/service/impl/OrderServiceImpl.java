// 文件路径: C:\Users\q\Desktop\软件服务工程\elmCloud\services\order-service\src\main\java\ynu\edu\orderservice\service\impl\OrderServiceImpl.java
package ynu.edu.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ynu.edu.orderservice.common.Result;
import ynu.edu.orderservice.dto.*;
import ynu.edu.orderservice.entity.Order;
import ynu.edu.orderservice.entity.OrderItem;
import ynu.edu.orderservice.entity.ShoppingCart;
import ynu.edu.orderservice.mapper.OrderItemMapper;
import ynu.edu.orderservice.mapper.OrderMapper;
import ynu.edu.orderservice.mapper.ShoppingCartMapper;
import ynu.edu.orderservice.service.OrderService;
import ynu.edu.orderservice.util.OrderUtils;
import ynu.edu.orderservice.vo.OrderVO;
import ynu.edu.orderservice.vo.PreOrderVO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    @Transactional
    public Result<Void> addToCart(Long userId, AddToCartDTO addToCartDTO) {
        try {
            ShoppingCart existCart = shoppingCartMapper.selectByUserIdAndProductId(userId, addToCartDTO.getProductId());
            if (existCart != null) {
                existCart.setQuantity(existCart.getQuantity() + addToCartDTO.getQuantity());
                // Service层不需要设置updateTime，因为Mapper的SQL语句会自动处理
                shoppingCartMapper.updateById(existCart);
            } else {
                ShoppingCart cart = new ShoppingCart();
                cart.setUserId(userId);
                cart.setStoreId(addToCartDTO.getStoreId());
                cart.setProductId(addToCartDTO.getProductId());
                cart.setProductName(addToCartDTO.getProductName());
                cart.setProductImage(addToCartDTO.getProductImage());
                cart.setProductSpec(addToCartDTO.getProductSpec());
                cart.setUnitPrice(addToCartDTO.getUnitPrice());
                cart.setQuantity(addToCartDTO.getQuantity());
                // Service层不需要设置createTime和updateTime，因为Mapper的SQL语句会自动处理
                shoppingCartMapper.insert(cart);
            }
            return Result.success();
        } catch (Exception e) {
            log.error("添加到购物车失败", e);
            return Result.error("添加到购物车失败");
        }
    }

    @Override
    @Transactional
    public Result<Void> updateCartQuantity(Long userId, UpdateCartDTO updateCartDTO) {
        try {
            ShoppingCart cart = shoppingCartMapper.selectById(updateCartDTO.getCartId());
            if (cart == null || !cart.getUserId().equals(userId)) {
                return Result.error("购物车项不存在");
            }
            cart.setQuantity(updateCartDTO.getQuantity());
            shoppingCartMapper.updateById(cart);
            return Result.success();
        } catch (Exception e) {
            log.error("更新购物车失败", e);
            return Result.error("更新购物车失败");
        }
    }

    @Override
    @Transactional
    public Result<Void> removeFromCart(Long userId, Long cartId) {
        try {
            ShoppingCart cart = shoppingCartMapper.selectById(cartId);
            if (cart == null || !cart.getUserId().equals(userId)) {
                return Result.error("购物车项不存在");
            }
            shoppingCartMapper.deleteById(cartId);
            return Result.success();
        } catch (Exception e) {
            log.error("删除购物车项失败", e);
            return Result.error("删除失败");
        }
    }

    @Override
    @Transactional
    public Result<Void> clearCart(Long userId, Long storeId) {
        try {
            shoppingCartMapper.deleteByUserIdAndStoreId(userId, storeId);
            return Result.success();
        } catch (Exception e) {
            log.error("清空购物车失败", e);
            return Result.error("清空购物车失败");
        }
    }

    @Override
    public Result<List<ShoppingCart>> getCartItems(Long userId) {
        try {
            List<ShoppingCart> cartItems = shoppingCartMapper.selectByUserId(userId);
            return Result.success(cartItems);
        } catch (Exception e) {
            log.error("获取购物车失败", e);
            return Result.error("获取购物车失败: " + e.getMessage());
        }
    }

    @Override
    public Result<PreOrderVO> preOrder(Long userId, PreOrderDTO preOrderDTO) {
        try {
            // ... (此处逻辑不变)
            PreOrderVO preOrderVO = new PreOrderVO();
            preOrderVO.setProductAmount(new BigDecimal("100.00"));
            preOrderVO.setPackageFee(new BigDecimal("2.00"));
            preOrderVO.setDeliveryFee(new BigDecimal("5.00"));
            preOrderVO.setDiscountAmount(new BigDecimal("0.00"));
            preOrderVO.setActualAmount(new BigDecimal("107.00"));
            preOrderVO.setEstimatedDeliveryTime(LocalDateTime.now().plusMinutes(30));
            return Result.success(preOrderVO);
        } catch (Exception e) {
            log.error("预结算失败", e);
            return Result.error("预结算失败");
        }
    }

    @Override
    @Transactional
    public Result<OrderVO> createOrder(Long userId, CreateOrderDTO createOrderDTO) {
        try {
            Order order = new Order();
            // 修正: 使用驼峰命名
            order.setOrderNo(OrderUtils.generateOrderNo());
            order.setUserId(userId);
            order.setStoreId(createOrderDTO.getStoreId());
            order.setStatus(0);
            order.setProductAmount(createOrderDTO.getProductAmount());
            order.setPackageFee(new BigDecimal("2.00"));
            order.setDeliveryFee(new BigDecimal("5.00"));
            order.setActualAmount(createOrderDTO.getActualAmount());
            order.setAddressId(createOrderDTO.getAddressId());
            order.setRemark(createOrderDTO.getRemark());
            // 时间由数据库自动生成

            orderMapper.insert(order);

            // ... (此处逻辑不变)
            OrderVO orderVO = convertToOrderVO(order);
            return Result.success(orderVO);
        } catch (Exception e) {
            log.error("创建订单失败", e);
            return Result.error("创建订单失败");
        }
    }

    @Override
    public Result<List<OrderVO>> getOrderList(OrderQueryDTO queryDTO) {
        try {
            // ... (此处逻辑不变)
            List<Order> orders;
            if (queryDTO.getStatus() != null) {
                orders = orderMapper.selectByStatus(queryDTO.getStatus());
            } else if (queryDTO.getUserId() != null) {
                orders = orderMapper.selectByUserId(queryDTO.getUserId());
            } else {
                int offset = (queryDTO.getPageNum() - 1) * queryDTO.getPageSize();
                orders = orderMapper.selectAll(offset, queryDTO.getPageSize());
            }
            List<OrderVO> orderVOList = orders.stream()
                    .map(this::convertToOrderVO)
                    .collect(Collectors.toList());
            return Result.success(orderVOList);
        } catch (Exception e) {
            log.error("获取订单列表失败", e);
            return Result.error("获取订单列表失败");
        }
    }

    @Override
    public Result<OrderVO> getOrderDetail(Long userId, Long orderId) {
        try {
            Order order = orderMapper.selectById(orderId);
            // 修正: 使用驼峰命名
            if (order == null || !order.getUserId().equals(userId)) {
                return Result.error("订单不存在");
            }
            OrderVO orderVO = convertToOrderVO(order);
            List<OrderItem> orderItems = orderItemMapper.selectByOrderId(orderId);
            orderVO.setOrderItems(orderItems);
            return Result.success(orderVO);
        } catch (Exception e) {
            log.error("获取订单详情失败", e);
            return Result.error("获取订单详情失败");
        }
    }

    @Override
    @Transactional
    public Result<Void> cancelOrder(Long userId, Long orderId, String reason) {
        try {
            Order order = orderMapper.selectById(orderId);
            // 修正: 使用驼峰命名
            if (order == null || !order.getUserId().equals(userId)) {
                return Result.error("订单不存在");
            }
            if (!OrderUtils.canCancel(order.getStatus())) {
                return Result.error("订单当前状态不可取消");
            }
            order.setStatus(7);
            orderMapper.updateById(order);
            return Result.success();
        } catch (Exception e) {
            log.error("取消订单失败", e);
            return Result.error("取消订单失败");
        }
    }

    @Override
    @Transactional
    public Result<Void> applyRefund(Long userId, Long orderId, String reason) {
        try {
            Order order = orderMapper.selectById(orderId);
            // 修正: 使用驼峰命名
            if (order == null || !order.getUserId().equals(userId)) {
                return Result.error("订单不存在");
            }
            if (!OrderUtils.canRefund(order.getStatus())) {
                return Result.error("订单当前状态不可申请退款");
            }
            // ... (此处逻辑不变)
            return Result.success();
        } catch (Exception e) {
            log.error("申请退款失败", e);
            return Result.error("申请退款失败");
        }
    }

    @Override
    @Transactional
    public Result<Void> reorder(Long userId, Long orderId) {
        try {
            Order order = orderMapper.selectById(orderId);
            // 修正: 使用驼峰命名
            if (order == null || !order.getUserId().equals(userId)) {
                return Result.error("订单不存在");
            }
            if (!OrderUtils.canReorder(order.getStatus())) {
                return Result.error("订单当前状态不可再来一单");
            }
            List<OrderItem> orderItems = orderItemMapper.selectByOrderId(orderId);
            for (OrderItem item : orderItems) {
                AddToCartDTO addToCartDTO = new AddToCartDTO();
                // 修正: 使用驼峰命名
                addToCartDTO.setStoreId(order.getStoreId());
                addToCartDTO.setProductId(item.getProductId());
                addToCartDTO.setProductName(item.getProductName());
                addToCartDTO.setProductImage(item.getProductImage());
                addToCartDTO.setProductSpec(item.getProductSpec());
                addToCartDTO.setUnitPrice(item.getUnitPrice());
                addToCartDTO.setQuantity(item.getQuantity());
                addToCart(userId, addToCartDTO);
            }
            return Result.success();
        } catch (Exception e) {
            log.error("再来一单失败", e);
            return Result.error("再来一单失败");
        }
    }

    @Override
    @Transactional
    public Result<Void> updateOrderStatus(Long orderId, Integer status) {
        try {
            Order order = orderMapper.selectById(orderId);
            if (order == null) {
                return Result.error("订单不存在");
            }
            order.setStatus(status);
            // 根据状态更新相应的时间字段
            // 修正: 使用驼峰命名
            if (status == 1) {
                order.setPaymentTime(LocalDateTime.now());
            } else if (status == 6) {
                order.setFinishTime(LocalDateTime.now());
            }
            orderMapper.updateById(order);
            return Result.success();
        } catch (Exception e) {
            log.error("更新订单状态失败", e);
            return Result.error("更新订单状态失败");
        }
    }

    private OrderVO convertToOrderVO(Order order) {
        OrderVO orderVO = new OrderVO();
        orderVO.setId(order.getId());
        orderVO.setStatus(order.getStatus());
        // ... 其他转换逻辑
        return orderVO;
    }
}