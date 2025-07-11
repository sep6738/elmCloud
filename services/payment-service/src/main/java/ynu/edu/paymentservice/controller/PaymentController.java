package ynu.edu.paymentservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ynu.edu.paymentservice.dto.CreatePaymentDTO;
import ynu.edu.paymentservice.dto.PaymentQueryDTO;
import ynu.edu.paymentservice.dto.RefundDTO;
import ynu.edu.paymentservice.service.PaymentService;
import ynu.edu.paymentservice.vo.PaymentResultVO;
import ynu.edu.paymentservice.vo.PaymentVO;
import ynu.edu.paymentservice.vo.RefundVO;

import java.util.List;

/**
 * 支付控制器
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {
    
    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
    
    @Autowired
    private PaymentService paymentService;
    
    /**
     * 创建支付订单
     */
    @PostMapping("/create")
    public PaymentResultVO createPayment(@Validated @RequestBody CreatePaymentDTO createPaymentDTO) {
        log.info("创建支付订单请求: {}", createPaymentDTO);
        return paymentService.createPayment(createPaymentDTO);
    }
    
    /**
     * 查询支付记录
     */
    @GetMapping("/{paymentNo}")
    public PaymentVO getPayment(@PathVariable String paymentNo) {
        log.info("查询支付记录: {}", paymentNo);
        return paymentService.getPaymentByNo(paymentNo);
    }
    
    /**
     * 根据订单号查询支付记录
     */
    @GetMapping("/order/{orderNo}")
    public PaymentVO getPaymentByOrderNo(@PathVariable String orderNo) {
        log.info("根据订单号查询支付记录: {}", orderNo);
        return paymentService.getPaymentByOrderNo(orderNo);
    }
    
    /**
     * 分页查询支付记录
     */
    @GetMapping("/page")
    public List<PaymentVO> getPaymentPage(PaymentQueryDTO queryDTO) {
        log.info("分页查询支付记录: {}", queryDTO);
        return paymentService.getPaymentPage(queryDTO);
    }
    
    /**
     * 检查支付状态
     */
    @GetMapping("/status/{paymentNo}")
    public PaymentResultVO checkPaymentStatus(@PathVariable String paymentNo) {
        log.info("检查支付状态: {}", paymentNo);
        return paymentService.checkPaymentStatus(paymentNo);
    }
    
    /**
     * 支付回调接口（模拟第三方支付回调）
     */
    @PostMapping("/callback")
    public String paymentCallback(@RequestParam String paymentNo,
                                @RequestParam String thirdPartyTradeNo,
                                @RequestParam Boolean success) {
        log.info("支付回调: paymentNo={}, thirdPartyTradeNo={}, success={}", paymentNo, thirdPartyTradeNo, success);
        
        boolean result = paymentService.handlePaymentCallback(paymentNo, thirdPartyTradeNo, success);
        return result ? "SUCCESS" : "FAILED";
    }
    
    /**
     * 申请退款
     */
    @PostMapping("/refund")
    public RefundVO applyRefund(@Validated @RequestBody RefundDTO refundDTO) {
        log.info("申请退款: {}", refundDTO);
        return paymentService.applyRefund(refundDTO);
    }
    
    /**
     * 查询退款记录
     */
    @GetMapping("/refund/{refundNo}")
    public RefundVO getRefund(@PathVariable String refundNo) {
        log.info("查询退款记录: {}", refundNo);
        return paymentService.getRefundByNo(refundNo);
    }
    
    /**
     * 根据支付单号查询退款记录
     */
    @GetMapping("/refund/payment/{paymentNo}")
    public RefundVO getRefundByPaymentNo(@PathVariable String paymentNo) {
        log.info("根据支付单号查询退款记录: {}", paymentNo);
        return paymentService.getRefundByPaymentNo(paymentNo);
    }
    
    /**
     * 退款回调接口（模拟第三方退款回调）
     */
    @PostMapping("/refund/callback")
    public String refundCallback(@RequestParam String refundNo,
                               @RequestParam String thirdPartyRefundNo,
                               @RequestParam Boolean success) {
        log.info("退款回调: refundNo={}, thirdPartyRefundNo={}, success={}", refundNo, thirdPartyRefundNo, success);
        
        boolean result = paymentService.handleRefundCallback(refundNo, thirdPartyRefundNo, success);
        return result ? "SUCCESS" : "FAILED";
    }
}
