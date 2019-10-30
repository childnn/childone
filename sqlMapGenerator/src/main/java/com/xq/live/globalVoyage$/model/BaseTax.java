package com.xq.live.globalVoyage$.model;

import java.math.BigDecimal;
import java.util.Date;

public class BaseTax {
    private Integer taxId;

    private BigDecimal lowLevel;

    private BigDecimal highLevel;

    private BigDecimal rate;

    private BigDecimal quickCalDeduction;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private Byte isDelete;

    public Integer getTaxId() {
        return taxId;
    }

    public void setTaxId(Integer taxId) {
        this.taxId = taxId;
    }

    public BigDecimal getLowLevel() {
        return lowLevel;
    }

    public void setLowLevel(BigDecimal lowLevel) {
        this.lowLevel = lowLevel;
    }

    public BigDecimal getHighLevel() {
        return highLevel;
    }

    public void setHighLevel(BigDecimal highLevel) {
        this.highLevel = highLevel;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getQuickCalDeduction() {
        return quickCalDeduction;
    }

    public void setQuickCalDeduction(BigDecimal quickCalDeduction) {
        this.quickCalDeduction = quickCalDeduction;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }
}