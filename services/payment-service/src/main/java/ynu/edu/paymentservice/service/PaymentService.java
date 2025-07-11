package ynu.edu.paymentservice.service;

import java.util.List;

import ynu.edu.paymentservice.dto.CreatePaymentDTO;
import ynu.edu.paymentservice.dto.PaymentQueryDTO;
import ynu.edu.paymentservice.dto.RefundDTO;
import ynu.edu.paymentservice.vo.PaymentResultVO;
import ynu.edu.paymentservice.vo.PaymentVO;
import ynu.edu.paymentservice.vo.RefundVO;

/**
 * 支付服务接口
 */
public interface PaymentService {
    
    /**
     * 创建支付订单
     */
    PaymentResultVO createPayment(CreatePaymentDTO createPaymentDTO);
    
    /**
     * 查询支付结果
     */
    PaymentVO getPaymentByNo(String paymentNo);
    
    /**
     * 根据订单号查询支付记录
     */
    PaymentVO getPaymentByOrderNo(String orderNo);
    
    /**
     * 分页查询支付记录
     */
    List<PaymentVO> getPaymentPage(PaymentQueryDTO queryDTO);
    
    /**
     * 支付回调处理
     */
    boolean handlePaymentCallback(String paymentNo, String thirdPartyTradeNo, boolean success);
    
    /**
     * 申请退款
     */
    RefundVO applyRefund(RefundDTO refundDTO);
    
    /**
     * 查询退款记录
     */
    RefundVO getRefundByNo(String refundNo);
    
    /**
     * 根据支付单号查询退款记录
     */
    RefundVO getRefundByPaymentNo(String paymentNo);
    
    /**
     * 退款回调处理
     */
    boolean handleRefundCallback(String refundNo, String thirdPartyRefundNo, boolean success);
    
    /**
     * 检查支付状态
     */
    PaymentResultVO checkPaymentStatus(String paymentNo);
}
