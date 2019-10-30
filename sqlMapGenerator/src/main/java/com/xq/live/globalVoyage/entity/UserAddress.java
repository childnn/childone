package com.xq.live.globalVoyage.entity;

import java.util.Date;

public class UserAddress {
    private Long id;

    private Long userId;

    private Long dictProvinceId;

    private Long dictCityId;

    private Long dictCountyId;

    private Long dictAreaId;

    private String detailAddress;

    private String longitude;

    private String latitud;

    private String sign;

    private String chatName;

    private String mobile;

    private Integer isDefault;

    private Date createTime;

    private Date updateTime;

    private Integer isDeleted;

    private String createBy;

    private String updateBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDictProvinceId() {
        return dictProvinceId;
    }

    public void setDictProvinceId(Long dictProvinceId) {
        this.dictProvinceId = dictProvinceId;
    }

    public Long getDictCityId() {
        return dictCityId;
    }

    public void setDictCityId(Long dictCityId) {
        this.dictCityId = dictCityId;
    }

    public Long getDictCountyId() {
        return dictCountyId;
    }

    public void setDictCountyId(Long dictCountyId) {
        this.dictCountyId = dictCountyId;
    }

    public Long getDictAreaId() {
        return dictAreaId;
    }

    public void setDictAreaId(Long dictAreaId) {
        this.dictAreaId = dictAreaId;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress == null ? null : detailAddress.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud == null ? null : latitud.trim();
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName == null ? null : chatName.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
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