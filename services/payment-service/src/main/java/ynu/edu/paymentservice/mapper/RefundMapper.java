package ynu.edu.paymentservice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import ynu.edu.paymentservice.entity.Refund;
import ynu.edu.paymentservice.vo.RefundVO;

/**
 * 退款记录Mapper接口
 */
@Mapper
public interface RefundMapper {
    
    /**
     * 新增退款记录
     */
    @Insert("INSERT INTO refund (refund_no, payment_no, order_no, user_id, amount, reason, " +
            "status, third_party_refund_no, refunded_at, remark) VALUES " +
            "(#{refundNo}, #{paymentNo}, #{orderNo}, #{userId}, #{amount}, #{reason}, " +
            "#{status}, #{thirdPartyRefundNo}, #{refundedAt}, #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Refund refund);
    
    /**
     * 根据ID查询退款记录
     */
    @Select("SELECT * FROM refund WHERE id = #{id} AND deleted = 0")
    Refund selectById(Long id);
    
    /**
     * 根据退款单号查询退款记录
     */
    RefundVO selectByRefundNo(@Param("refundNo") String refundNo);
    
    /**
     * 根据退款单号查询退款记录（返回实体）
     */
    @Select("SELECT * FROM refund WHERE refund_no = #{refundNo} AND deleted = 0")
    Refund selectEntityByRefundNo(@Param("refundNo") String refundNo);
    
    /**
     * 根据支付单号查询退款记录
     */
    RefundVO selectByPaymentNo(@Param("paymentNo") String paymentNo);
    
    /**
     * 根据支付单号查询退款记录（返回实体）
     */
    @Select("SELECT * FROM refund WHERE payment_no = #{paymentNo} AND deleted = 0")
    Refund selectEntityByPaymentNo(@Param("paymentNo") String paymentNo);
    
    /**
     * 分页查询退款记录
     */
    List<RefundVO> selectRefundPage(@Param("paymentNo") String paymentNo, 
                                   @Param("userId") Long userId,
                                   @Param("offset") int offset, 
                                   @Param("size") int size);
    
    /**
     * 统计退款记录数量
     */
    @Select("<script>" +
            "SELECT COUNT(*) FROM refund WHERE deleted = 0 " +
            "<if test='paymentNo != null and paymentNo != \"\"'> AND payment_no = #{paymentNo} </if>" +
            "<if test='userId != null'> AND user_id = #{userId} </if>" +
            "</script>")
    int countRefunds(@Param("paymentNo") String paymentNo, @Param("userId") Long userId);
    
    /**
     * 更新退款记录
     */
    @Update("UPDATE refund SET status = #{status}, third_party_refund_no = #{thirdPartyRefundNo}, " +
            "refunded_at = #{refundedAt}, remark = #{remark}, updated_at = NOW() WHERE id = #{id}")
    int updateById(Refund refund);
    
    /**
     * 删除退款记录（逻辑删除）
     */
    @Update("UPDATE refund SET deleted = 1, updated_at = NOW() WHERE id = #{id}")
    int deleteById(Long id);
}
