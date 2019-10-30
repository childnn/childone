package com.xq.live.globalVoyage$.dao;

import com.xq.live.globalVoyage$.model.InvoiceAuditInfo;
import com.xq.live.globalVoyage$.model.InvoiceAuditInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InvoiceAuditInfoMapper {
    long countByExample(InvoiceAuditInfoExample example);

    int deleteByExample(InvoiceAuditInfoExample example);

    int insert(InvoiceAuditInfo record);

    int insertSelective(InvoiceAuditInfo record);

    List<InvoiceAuditInfo> selectByExample(InvoiceAuditInfoExample example);

    int updateByExampleSelective(@Param("record") InvoiceAuditInfo record, @Param("example") InvoiceAuditInfoExample example);

    int updateByExample(@Param("record") InvoiceAuditInfo record, @Param("example") InvoiceAuditInfoExample example);
}