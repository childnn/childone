package com.xq.live.globalVoyage.entity;

import java.math.BigDecimal;
import java.util.Date;

public class GoodsSku {
    private Long goodsId;

    private String goodsName;

    private Long goodsSpuId;

    private String goodsSku;

    private BigDecimal goodsPrice;

    private Double goodsWeight;

    private Double goodsVolume;

    private String gsCode;

    private Long goodsClassId;

    private Long goodsBrandId;

    private String gsUnit;

    private String goodsStatus;

    private Integer goodsSeq;

    private Byte isDeleted;

    private Date createTime;

    private Date updateTime;

    private String createBy;

    private String updateBy;

    private BigDecimal preferentialPrice;

    private Integer automaticReplenishment;

    private String skuType;

    private Long shopId;

    private Integer sales;

    private String details;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public Long getGoodsSpuId() {
        return goodsSpuId;
    }

    public void setGoodsSpuId(Long goodsSpuId) {
        this.goodsSpuId = goodsSpuId;
    }

    public String getGoodsSku() {
        return goodsSku;
    }

    public void setGoodsSku(String goodsSku) {
        this.goodsSku = goodsSku == null ? null : goodsSku.trim();
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Double getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(Double goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public Double getGoodsVolume() {
        return goodsVolume;
    }

    public void setGoodsVolume(Double goodsVolume) {
        this.goodsVolume = goodsVolume;
    }

    public String getGsCode() {
        return gsCode;
    }

    public void setGsCode(String gsCode) {
        this.gsCode = gsCode == null ? null : gsCode.trim();
    }

    public Long getGoodsClassId() {
        return goodsClassId;
    }

    public void setGoodsClassId(Long goodsClassId) {
        this.goodsClassId = goodsClassId;
    }

    public Long getGoodsBrandId() {
        return goodsBrandId;
    }

    public void setGoodsBrandId(Long goodsBrandId) {
        this.goodsBrandId = goodsBrandId;
    }

    public String getGsUnit() {
        return gsUnit;
    }

    public void setGsUnit(String gsUnit) {
        this.gsUnit = gsUnit == null ? null : gsUnit.trim();
    }

    public String getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(String goodsStatus) {
        this.goodsStatus = goodsStatus == null ? null : goodsStatus.trim();
    }

    public Integer getGoodsSeq() {
        return goodsSeq;
    }

    public void setGoodsSeq(Integer goodsSeq) {
        this.goodsSeq = goodsSeq;
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

    public BigDecimal getPreferentialPrice() {
        return preferentialPrice;
    }

    public void setPreferentialPrice(BigDecimal preferentialPrice) {
        this.preferentialPrice = preferentialPrice;
    }

    public Integer getAutomaticReplenishment() {
        return automaticReplenishment;
    }

    public void setAutomaticReplenishment(Integer automaticReplenishment) {
        this.automaticReplenishment = automaticReplenishment;
    }

    public String getSkuType() {
        return skuType;
    }

    public void setSkuType(String skuType) {
        this.skuType = skuType == null ? null : skuType.trim();
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details == null ? null : details.trim();
    }
}