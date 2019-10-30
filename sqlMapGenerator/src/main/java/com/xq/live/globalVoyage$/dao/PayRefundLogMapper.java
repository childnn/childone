package com.xq.live.globalVoyage$.dao;

import com.xq.live.globalVoyage$.model.PayRefundLog;
import com.xq.live.globalVoyage$.model.PayRefundLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PayRefundLogMapper {
    long countByExample(PayRefundLogExample example);

    int deleteByExample(PayRefundLogExample example);

    int insert(PayRefundLog record);

    int insertSelective(PayRefundLog record);

    List<PayRefundLog> selectByExample(PayRefundLogExample example);

    int updateByExampleSelective(@Param("record") PayRefundLog record, @Param("example") PayRefundLogExample example);

    int updateByExample(@Param("record") PayRefundLog record, @Param("example") PayRefundLogExample example);
}