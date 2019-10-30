package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.InvoiceAuditInfo;

public interface InvoiceAuditInfoMapper {
    int insert(InvoiceAuditInfo record);

    int insertSelective(InvoiceAuditInfo record);
}