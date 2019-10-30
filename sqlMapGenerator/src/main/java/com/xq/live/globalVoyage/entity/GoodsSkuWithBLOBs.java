package com.xq.live.globalVoyage.entity;

public class GoodsSkuWithBLOBs extends GoodsSku {
    private String goodsMainPic;

    private String goodsSmallPics;

    private String goodsSpecs;

    private String goodsAttrs;

    private String remark;

    private String detailsImg;

    private String rejectedImg;

    public String getGoodsMainPic() {
        return goodsMainPic;
    }

    public void setGoodsMainPic(String goodsMainPic) {
        this.goodsMainPic = goodsMainPic == null ? null : goodsMainPic.trim();
    }

    public String getGoodsSmallPics() {
        return goodsSmallPics;
    }

    public void setGoodsSmallPics(String goodsSmallPics) {
        this.goodsSmallPics = goodsSmallPics == null ? null : goodsSmallPics.trim();
    }

    public String getGoodsSpecs() {
        return goodsSpecs;
    }

    public void setGoodsSpecs(String goodsSpecs) {
        this.goodsSpecs = goodsSpecs == null ? null : goodsSpecs.trim();
    }

    public String getGoodsAttrs() {
        return goodsAttrs;
    }

    public void setGoodsAttrs(String goodsAttrs) {
        this.goodsAttrs = goodsAttrs == null ? null : goodsAttrs.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDetailsImg() {
        return detailsImg;
    }

    public void setDetailsImg(String detailsImg) {
        this.detailsImg = detailsImg == null ? null : detailsImg.trim();
    }

    public String getRejectedImg() {
        return rejectedImg;
    }

    public void setRejectedImg(String rejectedImg) {
        this.rejectedImg = rejectedImg == null ? null : rejectedImg.trim();
    }
}