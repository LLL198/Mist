package com.una.embyhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.una.embyhub.payment.model.PaymentAccountOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PaymentAccountOrderMapper extends BaseMapper<PaymentAccountOrder> {
   @Select({"SELECT * FROM payment_account_order WHERE order_no = #{orderNo} AND del_flag = 0 LIMIT 1 FOR UPDATE"})
   PaymentAccountOrder selectByOrderNoForUpdate(@Param("orderNo") String orderNo);
}
