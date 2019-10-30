package com.xq.live.globalVoyage.entity;

import java.util.Date;

public class CashLog {
    private Long id;

    private Long cashApplyId;

    private String rejectResult;

    private String rejectImgs;

    private Byte isDeleted;

    private Date createTime;

    private Date updateTime;

    private String createBy;

    private String updateBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCashApplyId() {
        return cashApplyId;
    }

    public void setCashApplyId(Long cashApplyId) {
        this.cashApplyId = cashApplyId;
    }

    public String getRejectResult() {
        return rejectResult;
    }

    public void setRejectResult(String rejectResult) {
        this.rejectResult = rejectResult == null ? null : rejectResult.trim();
    }

    public String getRejectImgs() {
        return rejectImgs;
    }

    public void setRejectImgs(String rejectImgs) {
        this.rejectImgs = rejectImgs == null ? null : rejectImgs.trim();
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }
}