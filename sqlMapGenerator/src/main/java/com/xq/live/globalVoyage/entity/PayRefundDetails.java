package com.xq.live.globalVoyage.entity;

import java.math.BigDecimal;
import java.util.Date;

public class PayRefundDetails {
    private Long id;

    private String outTradeNo;

    private Integer orderType;

    private BigDecimal totalFee;

    private BigDecimal refundFee;

    private String applyReason;

    private Date applyTime;

    private Integer shopAuditStatus;

    private Date shopAuditTime;

    private String shopAuditRemarks;

    private Integer operatorAuditStatus;

    private Date operatorAuditTime;

    private String operatorAuditRemarks;

    private Integer refundStatus;

    private BigDecimal settlementRefundFee;

    private Date refundTime;

    private Integer type;

    private Long userId;

    private String otherReason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public BigDecimal getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(BigDecimal refundFee) {
        this.refundFee = refundFee;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason == null ? null : applyReason.trim();
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getShopAuditStatus() {
        return shopAuditStatus;
    }

    public void setShopAuditStatus(Integer shopAuditStatus) {
        this.shopAuditStatus = shopAuditStatus;
    }

    public Date getShopAuditTime() {
        return shopAuditTime;
    }

    public void setShopAuditTime(Date shopAuditTime) {
        this.shopAuditTime = shopAuditTime;
    }

    public String getShopAuditRemarks() {
        return shopAuditRemarks;
    }

    public void setShopAuditRemarks(String shopAuditRemarks) {
        this.shopAuditRemarks = shopAuditRemarks == null ? null : shopAuditRemarks.trim();
    }

    public Integer getOperatorAuditStatus() {
        return operatorAuditStatus;
    }

    public void setOperatorAuditStatus(Integer operatorAuditStatus) {
        this.operatorAuditStatus = operatorAuditStatus;
    }

    public Date getOperatorAuditTime() {
        return operatorAuditTime;
    }

    public void setOperatorAuditTime(Date operatorAuditTime) {
        this.operatorAuditTime = operatorAuditTime;
    }

    public String getOperatorAuditRemarks() {
        return operatorAuditRemarks;
    }

    public void setOperatorAuditRemarks(String operatorAuditRemarks) {
        this.operatorAuditRemarks = operatorAuditRemarks == null ? null : operatorAuditRemarks.trim();
    }

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    public BigDecimal getSettlementRefundFee() {
        return settlementRefundFee;
    }

    public void setSettlementRefundFee(BigDecimal settlementRefundFee) {
        this.settlementRefundFee = settlementRefundFee;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOtherReason() {
        return otherReason;
    }

    public void setOtherReason(String otherReason) {
        this.otherReason = otherReason == null ? null : otherReason.trim();
    }
}