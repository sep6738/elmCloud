// 文件路径: C:\Users\q\Desktop\软件服务工程\elmCloud\services\payment-service\src\main\java\ynu\edu\paymentservice\mapper\PaymentMapper.java
package ynu.edu.paymentservice.mapper;

import org.apache.ibatis.annotations.*;
import ynu.edu.paymentservice.dto.PaymentQueryDTO;
import ynu.edu.paymentservice.entity.Payment;
import ynu.edu.paymentservice.vo.PaymentVO;
import java.util.List;

@Mapper
public interface PaymentMapper {
    // 修正: #{...} 参数与实体类驼峰字段匹配
    @Insert("INSERT INTO payment (payment_no, order_no, user_id, amount, payment_method, " +
            "status, third_party_trade_no, paid_at, remark, created_at, updated_at, deleted) VALUES " +
            "(#{paymentNo}, #{orderNo}, #{userId}, #{amount}, #{paymentMethod}, " +
            "#{status}, #{thirdPartyTradeNo}, #{paidAt}, #{remark}, NOW(), NOW(), 0)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Payment payment);

    @Select("SELECT * FROM payment WHERE id = #{id} AND deleted = 0")
    Payment selectById(Long id);

    // XML中定义了，这里不用改
    PaymentVO selectByPaymentNo(@Param("paymentNo") String paymentNo);

    // 修正: 添加显式映射
    @Results({
            @Result(property = "paymentNo", column = "payment_no"),
            @Result(property = "orderNo", column = "order_no"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "paymentMethod", column = "payment_method"),
            @Result(property = "thirdPartyTradeNo", column = "third_party_trade_no"),
            @Result(property = "paidAt", column = "paid_at"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    @Select("SELECT * FROM payment WHERE payment_no = #{paymentNo} AND deleted = 0")
    Payment selectEntityByPaymentNo(@Param("paymentNo") String paymentNo);

    // XML中定义了，这里不用改
    PaymentVO selectByOrderNo(@Param("orderNo") String orderNo);

    // XML中定义了，这里不用改
    List<PaymentVO> selectPaymentPage(@Param("query") PaymentQueryDTO query);

    // 修正: XML中没有定义分页，分页参数是在Service层处理的，这里不需要offset和size
    // int countPayments(@Param("query") PaymentQueryDTO query);

    // 修正: #{...} 参数与实体类驼峰字段匹配
    @Update("UPDATE payment SET status = #{status}, third_party_trade_no = #{thirdPartyTradeNo}, " +
            "paid_at = #{paidAt}, remark = #{remark}, updated_at = NOW() WHERE id = #{id}")
    int updateById(Payment payment);

    @Update("UPDATE payment SET deleted = 1, updated_at = NOW() WHERE id = #{id}")
    int deleteById(Long id);
}