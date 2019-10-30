package com.xq.live.globalVoyage$.model;

import java.util.Date;

public class AccountInfo {
    private Long id;

    private Long shopId;

    private String accountBankName;

    private String accountBankAddress;

    private String accountBankTotalname;

    private String accountName;

    private Long cashBankId;

    private Integer invoiceStatus;

    private String invoicePayable;

    private String taxpayerIdennumber;

    private String invoiceType;

    private String invoiceRecipient;

    private String invoiceReceivingTelephone;

    private String invoiceReceivingAddress;

    private String companyRegisteredAddress;

    private String telephone;

    private String invoiceBank;

    private String invoiceBankNum;

    private Integer accountStatus;

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

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getAccountBankName() {
        return accountBankName;
    }

    public void setAccountBankName(String accountBankName) {
        this.accountBankName = accountBankName == null ? null : accountBankName.trim();
    }

    public String getAccountBankAddress() {
        return accountBankAddress;
    }

    public void setAccountBankAddress(String accountBankAddress) {
        this.accountBankAddress = accountBankAddress == null ? null : accountBankAddress.trim();
    }

    public String getAccountBankTotalname() {
        return accountBankTotalname;
    }

    public void setAccountBankTotalname(String accountBankTotalname) {
        this.accountBankTotalname = accountBankTotalname == null ? null : accountBankTotalname.trim();
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public Long getCashBankId() {
        return cashBankId;
    }

    public void setCashBankId(Long cashBankId) {
        this.cashBankId = cashBankId;
    }

    public Integer getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(Integer invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getInvoicePayable() {
        return invoicePayable;
    }

    public void setInvoicePayable(String invoicePayable) {
        this.invoicePayable = invoicePayable == null ? null : invoicePayable.trim();
    }

    public String getTaxpayerIdennumber() {
        return taxpayerIdennumber;
    }

    public void setTaxpayerIdennumber(String taxpayerIdennumber) {
        this.taxpayerIdennumber = taxpayerIdennumber == null ? null : taxpayerIdennumber.trim();
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType == null ? null : invoiceType.trim();
    }

    public String getInvoiceRecipient() {
        return invoiceRecipient;
    }

    public void setInvoiceRecipient(String invoiceRecipient) {
        this.invoiceRecipient = invoiceRecipient == null ? null : invoiceRecipient.trim();
    }

    public String getInvoiceReceivingTelephone() {
        return invoiceReceivingTelephone;
    }

    public void setInvoiceReceivingTelephone(String invoiceReceivingTelephone) {
        this.invoiceReceivingTelephone = invoiceReceivingTelephone == null ? null : invoiceReceivingTelephone.trim();
    }

    public String getInvoiceReceivingAddress() {
        return invoiceReceivingAddress;
    }

    public void setInvoiceReceivingAddress(String invoiceReceivingAddress) {
        this.invoiceReceivingAddress = invoiceReceivingAddress == null ? null : invoiceReceivingAddress.trim();
    }

    public String getCompanyRegisteredAddress() {
        return companyRegisteredAddress;
    }

    public void setCompanyRegisteredAddress(String companyRegisteredAddress) {
        this.companyRegisteredAddress = companyRegisteredAddress == null ? null : companyRegisteredAddress.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getInvoiceBank() {
        return invoiceBank;
    }

    public void setInvoiceBank(String invoiceBank) {
        this.invoiceBank = invoiceBank == null ? null : invoiceBank.trim();
    }

    public String getInvoiceBankNum() {
        return invoiceBankNum;
    }

    public void setInvoiceBankNum(String invoiceBankNum) {
        this.invoiceBankNum = invoiceBankNum == null ? null : invoiceBankNum.trim();
    }

    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
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