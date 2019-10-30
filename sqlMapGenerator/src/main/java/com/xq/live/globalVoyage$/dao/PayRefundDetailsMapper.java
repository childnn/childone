package com.xq.live.globalVoyage$.dao;

import com.xq.live.globalVoyage$.model.PayRefundDetails;
import com.xq.live.globalVoyage$.model.PayRefundDetailsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PayRefundDetailsMapper {
    long countByExample(PayRefundDetailsExample example);

    int deleteByExample(PayRefundDetailsExample example);

    int insert(PayRefundDetails record);

    int insertSelective(PayRefundDetails record);

    List<PayRefundDetails> selectByExample(PayRefundDetailsExample example);

    int updateByExampleSelective(@Param("record") PayRefundDetails record, @Param("example") PayRefundDetailsExample example);

    int updateByExample(@Param("record") PayRefundDetails record, @Param("example") PayRefundDetailsExample example);
}