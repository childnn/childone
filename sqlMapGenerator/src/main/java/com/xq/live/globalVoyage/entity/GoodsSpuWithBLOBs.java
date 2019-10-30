package com.xq.live.globalVoyage.entity;

public class GoodsSpuWithBLOBs extends GoodsSpu {
    private String goodsSummary;

    private String goodsDetail;

    private String goodsMemo;

    private String goodsNotice;

    public String getGoodsSummary() {
        return goodsSummary;
    }

    public void setGoodsSummary(String goodsSummary) {
        this.goodsSummary = goodsSummary == null ? null : goodsSummary.trim();
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail == null ? null : goodsDetail.trim();
    }

    public String getGoodsMemo() {
        return goodsMemo;
    }

    public void setGoodsMemo(String goodsMemo) {
        this.goodsMemo = goodsMemo == null ? null : goodsMemo.trim();
    }

    public String getGoodsNotice() {
        return goodsNotice;
    }

    public void setGoodsNotice(String goodsNotice) {
        this.goodsNotice = goodsNotice == null ? null : goodsNotice.trim();
    }
}