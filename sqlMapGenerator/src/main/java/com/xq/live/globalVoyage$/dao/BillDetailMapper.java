package com.xq.live.globalVoyage$.dao;

import com.xq.live.globalVoyage$.model.BillDetail;
import com.xq.live.globalVoyage$.model.BillDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BillDetailMapper {
    long countByExample(BillDetailExample example);

    int deleteByExample(BillDetailExample example);

    int insert(BillDetail record);

    int insertSelective(BillDetail record);

    List<BillDetail> selectByExample(BillDetailExample example);

    int updateByExampleSelective(@Param("record") BillDetail record, @Param("example") BillDetailExample example);

    int updateByExample(@Param("record") BillDetail record, @Param("example") BillDetailExample example);
}