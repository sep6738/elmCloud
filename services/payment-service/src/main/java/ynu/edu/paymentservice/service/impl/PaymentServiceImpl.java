// 文件路径: C:\Users\q\Desktop\软件服务工程\elmCloud\services\payment-service\src\main\java\ynu\edu\paymentservice\service\impl\PaymentServiceImpl.java

package ynu.edu.paymentservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ynu.edu.paymentservice.constant.PaymentConstants;
import ynu.edu.paymentservice.dto.CreatePaymentDTO;
import ynu.edu.paymentservice.dto.PaymentQueryDTO;
import ynu.edu.paymentservice.dto.RefundDTO;
import ynu.edu.paymentservice.entity.Payment;
import ynu.edu.paymentservice.entity.Refund;
import ynu.edu.paymentservice.mapper.PaymentMapper;
import ynu.edu.paymentservice.mapper.RefundMapper;
import ynu.edu.paymentservice.service.PaymentService;
import ynu.edu.paymentservice.util.PaymentUtils;
import ynu.edu.paymentservice.vo.PaymentResultVO;
import ynu.edu.paymentservice.vo.PaymentVO;
import ynu.edu.paymentservice.vo.RefundVO;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * 支付服务实现类 (完整修正版)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentMapper paymentMapper;
    private final RefundMapper refundMapper;

    @Override
    @Transactional
    public PaymentResultVO createPayment(CreatePaymentDTO createPaymentDTO) {
        log.info("创建支付订单: {}", createPaymentDTO);

        PaymentVO existingPayment = paymentMapper.selectByOrderNo(createPaymentDTO.getOrderNo());
        if (existingPayment != null && existingPayment.getStatus() == PaymentConstants.PaymentStatus.SUCCESS) {
            throw new RuntimeException("订单已支付成功，无需重复支付");
        }

        String paymentNo = PaymentUtils.generatePaymentNo();

        // 修正: 使用链式调用和驼峰字段名，与修正后的实体类匹配
        Payment payment = new Payment()
                .setPaymentNo(paymentNo)
                .setOrderNo(createPaymentDTO.getOrderNo())
                .setUserId(createPaymentDTO.getUserId())
                .setAmount(createPaymentDTO.getAmount())
                .setPaymentMethod(createPaymentDTO.getPaymentMethod())
                .setStatus(PaymentConstants.PaymentStatus.PENDING)
                .setRemark(createPaymentDTO.getRemark());

        paymentMapper.insert(payment);

        String paymentUrl = PaymentUtils.generatePaymentUrl(paymentNo, createPaymentDTO.getPaymentMethod());
        String paymentInfo = PaymentUtils.generateQrCode(paymentNo, createPaymentDTO.getAmount().toString());

        return new PaymentResultVO()
                .setPaymentNo(paymentNo)
                .setStatus(PaymentConstants.PaymentStatus.PENDING)
                .setStatusName(PaymentConstants.getPaymentStatusName(PaymentConstants.PaymentStatus.PENDING))
                .setPaymentUrl(paymentUrl)
                .setPaymentInfo(paymentInfo)
                .setMessage("支付订单创建成功，请完成支付");
    }

    @Override
    public PaymentVO getPaymentByNo(String paymentNo) {
        log.info("查询支付记录: {}", paymentNo);
        PaymentVO paymentVO = paymentMapper.selectByPaymentNo(paymentNo);
        if (paymentVO != null) {
            paymentVO.setPaymentMethodName(PaymentConstants.getPaymentMethodName(paymentVO.getPaymentMethod()));
            paymentVO.setStatusName(PaymentConstants.getPaymentStatusName(paymentVO.getStatus()));
        }
        return paymentVO;
    }

    @Override
    public PaymentVO getPaymentByOrderNo(String orderNo) {
        log.info("根据订单号查询支付记录: {}", orderNo);
        PaymentVO paymentVO = paymentMapper.selectByOrderNo(orderNo);
        if (paymentVO != null) {
            paymentVO.setPaymentMethodName(PaymentConstants.getPaymentMethodName(paymentVO.getPaymentMethod()));
            paymentVO.setStatusName(PaymentConstants.getPaymentStatusName(paymentVO.getStatus()));
        }
        return paymentVO;
    }

    @Override
    public List<PaymentVO> getPaymentPage(PaymentQueryDTO queryDTO) {
        log.info("分页查询支付记录: {}", queryDTO);

        // 注意: 您的PaymentMapper.xml中的selectPaymentPage并未实现真正的分页(没有LIMIT和OFFSET)
        // 此处代码仅为逻辑上正确，但实际效果取决于您的XML文件。
        // 如需真正分页，请修改PaymentMapper.xml。
        List<PaymentVO> result = paymentMapper.selectPaymentPage(queryDTO);

        if (result == null) {
            return Collections.emptyList();
        }

        result.forEach(paymentVO -> {
            paymentVO.setPaymentMethodName(PaymentConstants.getPaymentMethodName(paymentVO.getPaymentMethod()));
            paymentVO.setStatusName(PaymentConstants.getPaymentStatusName(paymentVO.getStatus()));
        });
        return result;
    }

    @Override
    @Transactional
    public boolean handlePaymentCallback(String paymentNo, String thirdPartyTradeNo, boolean success) {
        log.info("处理支付回调: paymentNo={}, thirdPartyTradeNo={}, success={}", paymentNo, thirdPartyTradeNo, success);
        Payment payment = paymentMapper.selectEntityByPaymentNo(paymentNo);

        if (payment == null) {
            log.error("支付记录不存在: {}", paymentNo);
            return false;
        }

        if (payment.getStatus() == PaymentConstants.PaymentStatus.SUCCESS) {
            log.warn("支付记录已处理: {}", paymentNo);
            return true;
        }

        // 修正: 使用驼峰字段名进行设置
        payment.setThirdPartyTradeNo(thirdPartyTradeNo);
        if (success) {
            payment.setStatus(PaymentConstants.PaymentStatus.SUCCESS);
            payment.setPaidAt(LocalDateTime.now());
        } else {
            payment.setStatus(PaymentConstants.PaymentStatus.FAILED);
        }

        int updated = paymentMapper.updateById(payment);
        return updated > 0;
    }

    @Override
    @Transactional
    public RefundVO applyRefund(RefundDTO refundDTO) {
        log.info("申请退款: {}", refundDTO);
        PaymentVO paymentVO = paymentMapper.selectByPaymentNo(refundDTO.getPaymentNo());
        if (paymentVO == null) {
            throw new RuntimeException("支付记录不存在");
        }

        if (paymentVO.getStatus() != PaymentConstants.PaymentStatus.SUCCESS) {
            throw new RuntimeException("订单未支付成功，无法退款");
        }

        RefundVO existingRefund = refundMapper.selectByPaymentNo(refundDTO.getPaymentNo());
        if (existingRefund != null && existingRefund.getStatus() == PaymentConstants.RefundStatus.SUCCESS) {
            throw new RuntimeException("订单已退款成功，无需重复退款");
        }

        if (refundDTO.getAmount().compareTo(paymentVO.getAmount()) > 0) {
            throw new RuntimeException("退款金额不能大于支付金额");
        }

        String refundNo = PaymentUtils.generateRefundNo();

        // 修正: 使用驼峰字段名进行设置
        Refund refund = new Refund()
                .setRefundNo(refundNo)
                .setPaymentNo(refundDTO.getPaymentNo())
                .setOrderNo(paymentVO.getOrderNo())
                .setUserId(paymentVO.getUserId())
                .setAmount(refundDTO.getAmount())
                .setReason(refundDTO.getReason())
                .setStatus(PaymentConstants.RefundStatus.PROCESSING)
                .setRemark(refundDTO.getRemark());

        refundMapper.insert(refund);

        boolean refundSuccess = PaymentUtils.simulateRefundResult();
        String thirdPartyRefundNo = PaymentUtils.generateThirdPartyTradeNo();

        // 修正: 使用驼峰字段名进行设置
        if (refundSuccess) {
            refund.setStatus(PaymentConstants.RefundStatus.SUCCESS);
            refund.setThirdPartyRefundNo(thirdPartyRefundNo);
            refund.setRefundedAt(LocalDateTime.now());

            Payment payment = paymentMapper.selectEntityByPaymentNo(refundDTO.getPaymentNo());
            if (payment != null) {
                payment.setStatus(PaymentConstants.PaymentStatus.REFUNDED);
                paymentMapper.updateById(payment);
            }
        } else {
            refund.setStatus(PaymentConstants.RefundStatus.FAILED);
        }

        refundMapper.updateById(refund);

        RefundVO refundVO = refundMapper.selectByRefundNo(refundNo);
        if (refundVO != null) {
            refundVO.setStatusName(PaymentConstants.getRefundStatusName(refundVO.getStatus()));
        }

        return refundVO;
    }

    @Override
    public RefundVO getRefundByNo(String refundNo) {
        log.info("查询退款记录: {}", refundNo);
        RefundVO refundVO = refundMapper.selectByRefundNo(refundNo);
        if (refundVO != null) {
            refundVO.setStatusName(PaymentConstants.getRefundStatusName(refundVO.getStatus()));
        }
        return refundVO;
    }

    @Override
    public RefundVO getRefundByPaymentNo(String paymentNo) {
        log.info("根据支付单号查询退款记录: {}", paymentNo);
        RefundVO refundVO = refundMapper.selectByPaymentNo(paymentNo);
        if (refundVO != null) {
            refundVO.setStatusName(PaymentConstants.getRefundStatusName(refundVO.getStatus()));
        }
        return refundVO;
    }

    @Override
    @Transactional
    public boolean handleRefundCallback(String refundNo, String thirdPartyRefundNo, boolean success) {
        log.info("处理退款回调: refundNo={}, thirdPartyRefundNo={}, success={}", refundNo, thirdPartyRefundNo, success);
        Refund refund = refundMapper.selectEntityByRefundNo(refundNo);

        if (refund == null) {
            log.error("退款记录不存在: {}", refundNo);
            return false;
        }

        if (refund.getStatus() == PaymentConstants.RefundStatus.SUCCESS) {
            log.warn("退款记录已处理: {}", refundNo);
            return true;
        }

        // 修正: 使用驼峰字段名进行设置
        refund.setThirdPartyRefundNo(thirdPartyRefundNo);
        if (success) {
            refund.setStatus(PaymentConstants.RefundStatus.SUCCESS);
            refund.setRefundedAt(LocalDateTime.now());

            Payment payment = paymentMapper.selectEntityByPaymentNo(refund.getPaymentNo());
            if (payment != null) {
                payment.setStatus(PaymentConstants.PaymentStatus.REFUNDED);
                paymentMapper.updateById(payment);
            }
        } else {
            refund.setStatus(PaymentConstants.RefundStatus.FAILED);
        }

        int updated = refundMapper.updateById(refund);
        return updated > 0;
    }

    @Override
    public PaymentResultVO checkPaymentStatus(String paymentNo) {
        log.info("检查支付状态: {}", paymentNo);
        PaymentVO paymentVO = paymentMapper.selectByPaymentNo(paymentNo);
        if (paymentVO == null) {
            throw new RuntimeException("支付记录不存在");
        }

        if (paymentVO.getStatus() == PaymentConstants.PaymentStatus.PENDING) {
            boolean paymentSuccess = PaymentUtils.simulatePaymentResult();
            if (paymentSuccess) {
                String thirdPartyTradeNo = PaymentUtils.generateThirdPartyTradeNo();
                handlePaymentCallback(paymentNo, thirdPartyTradeNo, true);
                paymentVO.setStatus(PaymentConstants.PaymentStatus.SUCCESS);
            }
        }

        return new PaymentResultVO()
                .setPaymentNo(paymentNo)
                .setStatus(paymentVO.getStatus())
                .setStatusName(PaymentConstants.getPaymentStatusName(paymentVO.getStatus()))
                .setMessage("支付状态查询成功");
    }
}