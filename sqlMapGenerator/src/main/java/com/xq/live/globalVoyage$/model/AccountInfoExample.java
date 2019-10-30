package com.xq.live.globalVoyage$.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AccountInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andShopIdIsNull() {
            addCriterion("shop_id is null");
            return (Criteria) this;
        }

        public Criteria andShopIdIsNotNull() {
            addCriterion("shop_id is not null");
            return (Criteria) this;
        }

        public Criteria andShopIdEqualTo(Long value) {
            addCriterion("shop_id =", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotEqualTo(Long value) {
            addCriterion("shop_id <>", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdGreaterThan(Long value) {
            addCriterion("shop_id >", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdGreaterThanOrEqualTo(Long value) {
            addCriterion("shop_id >=", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdLessThan(Long value) {
            addCriterion("shop_id <", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdLessThanOrEqualTo(Long value) {
            addCriterion("shop_id <=", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdIn(List<Long> values) {
            addCriterion("shop_id in", values, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotIn(List<Long> values) {
            addCriterion("shop_id not in", values, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdBetween(Long value1, Long value2) {
            addCriterion("shop_id between", value1, value2, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotBetween(Long value1, Long value2) {
            addCriterion("shop_id not between", value1, value2, "shopId");
            return (Criteria) this;
        }

        public Criteria andAccountBankNameIsNull() {
            addCriterion("account_bank_name is null");
            return (Criteria) this;
        }

        public Criteria andAccountBankNameIsNotNull() {
            addCriterion("account_bank_name is not null");
            return (Criteria) this;
        }

        public Criteria andAccountBankNameEqualTo(String value) {
            addCriterion("account_bank_name =", value, "accountBankName");
            return (Criteria) this;
        }

        public Criteria andAccountBankNameNotEqualTo(String value) {
            addCriterion("account_bank_name <>", value, "accountBankName");
            return (Criteria) this;
        }

        public Criteria andAccountBankNameGreaterThan(String value) {
            addCriterion("account_bank_name >", value, "accountBankName");
            return (Criteria) this;
        }

        public Criteria andAccountBankNameGreaterThanOrEqualTo(String value) {
            addCriterion("account_bank_name >=", value, "accountBankName");
            return (Criteria) this;
        }

        public Criteria andAccountBankNameLessThan(String value) {
            addCriterion("account_bank_name <", value, "accountBankName");
            return (Criteria) this;
        }

        public Criteria andAccountBankNameLessThanOrEqualTo(String value) {
            addCriterion("account_bank_name <=", value, "accountBankName");
            return (Criteria) this;
        }

        public Criteria andAccountBankNameLike(String value) {
            addCriterion("account_bank_name like", value, "accountBankName");
            return (Criteria) this;
        }

        public Criteria andAccountBankNameNotLike(String value) {
            addCriterion("account_bank_name not like", value, "accountBankName");
            return (Criteria) this;
        }

        public Criteria andAccountBankNameIn(List<String> values) {
            addCriterion("account_bank_name in", values, "accountBankName");
            return (Criteria) this;
        }

        public Criteria andAccountBankNameNotIn(List<String> values) {
            addCriterion("account_bank_name not in", values, "accountBankName");
            return (Criteria) this;
        }

        public Criteria andAccountBankNameBetween(String value1, String value2) {
            addCriterion("account_bank_name between", value1, value2, "accountBankName");
            return (Criteria) this;
        }

        public Criteria andAccountBankNameNotBetween(String value1, String value2) {
            addCriterion("account_bank_name not between", value1, value2, "accountBankName");
            return (Criteria) this;
        }

        public Criteria andAccountBankAddressIsNull() {
            addCriterion("account_bank_address is null");
            return (Criteria) this;
        }

        public Criteria andAccountBankAddressIsNotNull() {
            addCriterion("account_bank_address is not null");
            return (Criteria) this;
        }

        public Criteria andAccountBankAddressEqualTo(String value) {
            addCriterion("account_bank_address =", value, "accountBankAddress");
            return (Criteria) this;
        }

        public Criteria andAccountBankAddressNotEqualTo(String value) {
            addCriterion("account_bank_address <>", value, "accountBankAddress");
            return (Criteria) this;
        }

        public Criteria andAccountBankAddressGreaterThan(String value) {
            addCriterion("account_bank_address >", value, "accountBankAddress");
            return (Criteria) this;
        }

        public Criteria andAccountBankAddressGreaterThanOrEqualTo(String value) {
            addCriterion("account_bank_address >=", value, "accountBankAddress");
            return (Criteria) this;
        }

        public Criteria andAccountBankAddressLessThan(String value) {
            addCriterion("account_bank_address <", value, "accountBankAddress");
            return (Criteria) this;
        }

        public Criteria andAccountBankAddressLessThanOrEqualTo(String value) {
            addCriterion("account_bank_address <=", value, "accountBankAddress");
            return (Criteria) this;
        }

        public Criteria andAccountBankAddressLike(String value) {
            addCriterion("account_bank_address like", value, "accountBankAddress");
            return (Criteria) this;
        }

        public Criteria andAccountBankAddressNotLike(String value) {
            addCriterion("account_bank_address not like", value, "accountBankAddress");
            return (Criteria) this;
        }

        public Criteria andAccountBankAddressIn(List<String> values) {
            addCriterion("account_bank_address in", values, "accountBankAddress");
            return (Criteria) this;
        }

        public Criteria andAccountBankAddressNotIn(List<String> values) {
            addCriterion("account_bank_address not in", values, "accountBankAddress");
            return (Criteria) this;
        }

        public Criteria andAccountBankAddressBetween(String value1, String value2) {
            addCriterion("account_bank_address between", value1, value2, "accountBankAddress");
            return (Criteria) this;
        }

        public Criteria andAccountBankAddressNotBetween(String value1, String value2) {
            addCriterion("account_bank_address not between", value1, value2, "accountBankAddress");
            return (Criteria) this;
        }

        public Criteria andAccountBankTotalnameIsNull() {
            addCriterion("account_bank_totalname is null");
            return (Criteria) this;
        }

        public Criteria andAccountBankTotalnameIsNotNull() {
            addCriterion("account_bank_totalname is not null");
            return (Criteria) this;
        }

        public Criteria andAccountBankTotalnameEqualTo(String value) {
            addCriterion("account_bank_totalname =", value, "accountBankTotalname");
            return (Criteria) this;
        }

        public Criteria andAccountBankTotalnameNotEqualTo(String value) {
            addCriterion("account_bank_totalname <>", value, "accountBankTotalname");
            return (Criteria) this;
        }

        public Criteria andAccountBankTotalnameGreaterThan(String value) {
            addCriterion("account_bank_totalname >", value, "accountBankTotalname");
            return (Criteria) this;
        }

        public Criteria andAccountBankTotalnameGreaterThanOrEqualTo(String value) {
            addCriterion("account_bank_totalname >=", value, "accountBankTotalname");
            return (Criteria) this;
        }

        public Criteria andAccountBankTotalnameLessThan(String value) {
            addCriterion("account_bank_totalname <", value, "accountBankTotalname");
            return (Criteria) this;
        }

        public Criteria andAccountBankTotalnameLessThanOrEqualTo(String value) {
            addCriterion("account_bank_totalname <=", value, "accountBankTotalname");
            return (Criteria) this;
        }

        public Criteria andAccountBankTotalnameLike(String value) {
            addCriterion("account_bank_totalname like", value, "accountBankTotalname");
            return (Criteria) this;
        }

        public Criteria andAccountBankTotalnameNotLike(String value) {
            addCriterion("account_bank_totalname not like", value, "accountBankTotalname");
            return (Criteria) this;
        }

        public Criteria andAccountBankTotalnameIn(List<String> values) {
            addCriterion("account_bank_totalname in", values, "accountBankTotalname");
            return (Criteria) this;
        }

        public Criteria andAccountBankTotalnameNotIn(List<String> values) {
            addCriterion("account_bank_totalname not in", values, "accountBankTotalname");
            return (Criteria) this;
        }

        public Criteria andAccountBankTotalnameBetween(String value1, String value2) {
            addCriterion("account_bank_totalname between", value1, value2, "accountBankTotalname");
            return (Criteria) this;
        }

        public Criteria andAccountBankTotalnameNotBetween(String value1, String value2) {
            addCriterion("account_bank_totalname not between", value1, value2, "accountBankTotalname");
            return (Criteria) this;
        }

        public Criteria andAccountNameIsNull() {
            addCriterion("account_name is null");
            return (Criteria) this;
        }

        public Criteria andAccountNameIsNotNull() {
            addCriterion("account_name is not null");
            return (Criteria) this;
        }

        public Criteria andAccountNameEqualTo(String value) {
            addCriterion("account_name =", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameNotEqualTo(String value) {
            addCriterion("account_name <>", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameGreaterThan(String value) {
            addCriterion("account_name >", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameGreaterThanOrEqualTo(String value) {
            addCriterion("account_name >=", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameLessThan(String value) {
            addCriterion("account_name <", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameLessThanOrEqualTo(String value) {
            addCriterion("account_name <=", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameLike(String value) {
            addCriterion("account_name like", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameNotLike(String value) {
            addCriterion("account_name not like", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameIn(List<String> values) {
            addCriterion("account_name in", values, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameNotIn(List<String> values) {
            addCriterion("account_name not in", values, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameBetween(String value1, String value2) {
            addCriterion("account_name between", value1, value2, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameNotBetween(String value1, String value2) {
            addCriterion("account_name not between", value1, value2, "accountName");
            return (Criteria) this;
        }

        public Criteria andCashBankIdIsNull() {
            addCriterion("cash_bank_id is null");
            return (Criteria) this;
        }

        public Criteria andCashBankIdIsNotNull() {
            addCriterion("cash_bank_id is not null");
            return (Criteria) this;
        }

        public Criteria andCashBankIdEqualTo(Long value) {
            addCriterion("cash_bank_id =", value, "cashBankId");
            return (Criteria) this;
        }

        public Criteria andCashBankIdNotEqualTo(Long value) {
            addCriterion("cash_bank_id <>", value, "cashBankId");
            return (Criteria) this;
        }

        public Criteria andCashBankIdGreaterThan(Long value) {
            addCriterion("cash_bank_id >", value, "cashBankId");
            return (Criteria) this;
        }

        public Criteria andCashBankIdGreaterThanOrEqualTo(Long value) {
            addCriterion("cash_bank_id >=", value, "cashBankId");
            return (Criteria) this;
        }

        public Criteria andCashBankIdLessThan(Long value) {
            addCriterion("cash_bank_id <", value, "cashBankId");
            return (Criteria) this;
        }

        public Criteria andCashBankIdLessThanOrEqualTo(Long value) {
            addCriterion("cash_bank_id <=", value, "cashBankId");
            return (Criteria) this;
        }

        public Criteria andCashBankIdIn(List<Long> values) {
            addCriterion("cash_bank_id in", values, "cashBankId");
            return (Criteria) this;
        }

        public Criteria andCashBankIdNotIn(List<Long> values) {
            addCriterion("cash_bank_id not in", values, "cashBankId");
            return (Criteria) this;
        }

        public Criteria andCashBankIdBetween(Long value1, Long value2) {
            addCriterion("cash_bank_id between", value1, value2, "cashBankId");
            return (Criteria) this;
        }

        public Criteria andCashBankIdNotBetween(Long value1, Long value2) {
            addCriterion("cash_bank_id not between", value1, value2, "cashBankId");
            return (Criteria) this;
        }

        public Criteria andInvoiceStatusIsNull() {
            addCriterion("invoice_status is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceStatusIsNotNull() {
            addCriterion("invoice_status is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceStatusEqualTo(Integer value) {
            addCriterion("invoice_status =", value, "invoiceStatus");
            return (Criteria) this;
        }

        public Criteria andInvoiceStatusNotEqualTo(Integer value) {
            addCriterion("invoice_status <>", value, "invoiceStatus");
            return (Criteria) this;
        }

        public Criteria andInvoiceStatusGreaterThan(Integer value) {
            addCriterion("invoice_status >", value, "invoiceStatus");
            return (Criteria) this;
        }

        public Criteria andInvoiceStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("invoice_status >=", value, "invoiceStatus");
            return (Criteria) this;
        }

        public Criteria andInvoiceStatusLessThan(Integer value) {
            addCriterion("invoice_status <", value, "invoiceStatus");
            return (Criteria) this;
        }

        public Criteria andInvoiceStatusLessThanOrEqualTo(Integer value) {
            addCriterion("invoice_status <=", value, "invoiceStatus");
            return (Criteria) this;
        }

        public Criteria andInvoiceStatusIn(List<Integer> values) {
            addCriterion("invoice_status in", values, "invoiceStatus");
            return (Criteria) this;
        }

        public Criteria andInvoiceStatusNotIn(List<Integer> values) {
            addCriterion("invoice_status not in", values, "invoiceStatus");
            return (Criteria) this;
        }

        public Criteria andInvoiceStatusBetween(Integer value1, Integer value2) {
            addCriterion("invoice_status between", value1, value2, "invoiceStatus");
            return (Criteria) this;
        }

        public Criteria andInvoiceStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("invoice_status not between", value1, value2, "invoiceStatus");
            return (Criteria) this;
        }

        public Criteria andInvoicePayableIsNull() {
            addCriterion("invoice_payable is null");
            return (Criteria) this;
        }

        public Criteria andInvoicePayableIsNotNull() {
            addCriterion("invoice_payable is not null");
            return (Criteria) this;
        }

        public Criteria andInvoicePayableEqualTo(String value) {
            addCriterion("invoice_payable =", value, "invoicePayable");
            return (Criteria) this;
        }

        public Criteria andInvoicePayableNotEqualTo(String value) {
            addCriterion("invoice_payable <>", value, "invoicePayable");
            return (Criteria) this;
        }

        public Criteria andInvoicePayableGreaterThan(String value) {
            addCriterion("invoice_payable >", value, "invoicePayable");
            return (Criteria) this;
        }

        public Criteria andInvoicePayableGreaterThanOrEqualTo(String value) {
            addCriterion("invoice_payable >=", value, "invoicePayable");
            return (Criteria) this;
        }

        public Criteria andInvoicePayableLessThan(String value) {
            addCriterion("invoice_payable <", value, "invoicePayable");
            return (Criteria) this;
        }

        public Criteria andInvoicePayableLessThanOrEqualTo(String value) {
            addCriterion("invoice_payable <=", value, "invoicePayable");
            return (Criteria) this;
        }

        public Criteria andInvoicePayableLike(String value) {
            addCriterion("invoice_payable like", value, "invoicePayable");
            return (Criteria) this;
        }

        public Criteria andInvoicePayableNotLike(String value) {
            addCriterion("invoice_payable not like", value, "invoicePayable");
            return (Criteria) this;
        }

        public Criteria andInvoicePayableIn(List<String> values) {
            addCriterion("invoice_payable in", values, "invoicePayable");
            return (Criteria) this;
        }

        public Criteria andInvoicePayableNotIn(List<String> values) {
            addCriterion("invoice_payable not in", values, "invoicePayable");
            return (Criteria) this;
        }

        public Criteria andInvoicePayableBetween(String value1, String value2) {
            addCriterion("invoice_payable between", value1, value2, "invoicePayable");
            return (Criteria) this;
        }

        public Criteria andInvoicePayableNotBetween(String value1, String value2) {
            addCriterion("invoice_payable not between", value1, value2, "invoicePayable");
            return (Criteria) this;
        }

        public Criteria andTaxpayerIdennumberIsNull() {
            addCriterion("taxpayer_idenNumber is null");
            return (Criteria) this;
        }

        public Criteria andTaxpayerIdennumberIsNotNull() {
            addCriterion("taxpayer_idenNumber is not null");
            return (Criteria) this;
        }

        public Criteria andTaxpayerIdennumberEqualTo(String value) {
            addCriterion("taxpayer_idenNumber =", value, "taxpayerIdennumber");
            return (Criteria) this;
        }

        public Criteria andTaxpayerIdennumberNotEqualTo(String value) {
            addCriterion("taxpayer_idenNumber <>", value, "taxpayerIdennumber");
            return (Criteria) this;
        }

        public Criteria andTaxpayerIdennumberGreaterThan(String value) {
            addCriterion("taxpayer_idenNumber >", value, "taxpayerIdennumber");
            return (Criteria) this;
        }

        public Criteria andTaxpayerIdennumberGreaterThanOrEqualTo(String value) {
            addCriterion("taxpayer_idenNumber >=", value, "taxpayerIdennumber");
            return (Criteria) this;
        }

        public Criteria andTaxpayerIdennumberLessThan(String value) {
            addCriterion("taxpayer_idenNumber <", value, "taxpayerIdennumber");
            return (Criteria) this;
        }

        public Criteria andTaxpayerIdennumberLessThanOrEqualTo(String value) {
            addCriterion("taxpayer_idenNumber <=", value, "taxpayerIdennumber");
            return (Criteria) this;
        }

        public Criteria andTaxpayerIdennumberLike(String value) {
            addCriterion("taxpayer_idenNumber like", value, "taxpayerIdennumber");
            return (Criteria) this;
        }

        public Criteria andTaxpayerIdennumberNotLike(String value) {
            addCriterion("taxpayer_idenNumber not like", value, "taxpayerIdennumber");
            return (Criteria) this;
        }

        public Criteria andTaxpayerIdennumberIn(List<String> values) {
            addCriterion("taxpayer_idenNumber in", values, "taxpayerIdennumber");
            return (Criteria) this;
        }

        public Criteria andTaxpayerIdennumberNotIn(List<String> values) {
            addCriterion("taxpayer_idenNumber not in", values, "taxpayerIdennumber");
            return (Criteria) this;
        }

        public Criteria andTaxpayerIdennumberBetween(String value1, String value2) {
            addCriterion("taxpayer_idenNumber between", value1, value2, "taxpayerIdennumber");
            return (Criteria) this;
        }

        public Criteria andTaxpayerIdennumberNotBetween(String value1, String value2) {
            addCriterion("taxpayer_idenNumber not between", value1, value2, "taxpayerIdennumber");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeIsNull() {
            addCriterion("invoice_type is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeIsNotNull() {
            addCriterion("invoice_type is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeEqualTo(String value) {
            addCriterion("invoice_type =", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeNotEqualTo(String value) {
            addCriterion("invoice_type <>", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeGreaterThan(String value) {
            addCriterion("invoice_type >", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeGreaterThanOrEqualTo(String value) {
            addCriterion("invoice_type >=", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeLessThan(String value) {
            addCriterion("invoice_type <", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeLessThanOrEqualTo(String value) {
            addCriterion("invoice_type <=", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeLike(String value) {
            addCriterion("invoice_type like", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeNotLike(String value) {
            addCriterion("invoice_type not like", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeIn(List<String> values) {
            addCriterion("invoice_type in", values, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeNotIn(List<String> values) {
            addCriterion("invoice_type not in", values, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeBetween(String value1, String value2) {
            addCriterion("invoice_type between", value1, value2, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeNotBetween(String value1, String value2) {
            addCriterion("invoice_type not between", value1, value2, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceRecipientIsNull() {
            addCriterion("invoice_recipient is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceRecipientIsNotNull() {
            addCriterion("invoice_recipient is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceRecipientEqualTo(String value) {
            addCriterion("invoice_recipient =", value, "invoiceRecipient");
            return (Criteria) this;
        }

        public Criteria andInvoiceRecipientNotEqualTo(String value) {
            addCriterion("invoice_recipient <>", value, "invoiceRecipient");
            return (Criteria) this;
        }

        public Criteria andInvoiceRecipientGreaterThan(String value) {
            addCriterion("invoice_recipient >", value, "invoiceRecipient");
            return (Criteria) this;
        }

        public Criteria andInvoiceRecipientGreaterThanOrEqualTo(String value) {
            addCriterion("invoice_recipient >=", value, "invoiceRecipient");
            return (Criteria) this;
        }

        public Criteria andInvoiceRecipientLessThan(String value) {
            addCriterion("invoice_recipient <", value, "invoiceRecipient");
            return (Criteria) this;
        }

        public Criteria andInvoiceRecipientLessThanOrEqualTo(String value) {
            addCriterion("invoice_recipient <=", value, "invoiceRecipient");
            return (Criteria) this;
        }

        public Criteria andInvoiceRecipientLike(String value) {
            addCriterion("invoice_recipient like", value, "invoiceRecipient");
            return (Criteria) this;
        }

        public Criteria andInvoiceRecipientNotLike(String value) {
            addCriterion("invoice_recipient not like", value, "invoiceRecipient");
            return (Criteria) this;
        }

        public Criteria andInvoiceRecipientIn(List<String> values) {
            addCriterion("invoice_recipient in", values, "invoiceRecipient");
            return (Criteria) this;
        }

        public Criteria andInvoiceRecipientNotIn(List<String> values) {
            addCriterion("invoice_recipient not in", values, "invoiceRecipient");
            return (Criteria) this;
        }

        public Criteria andInvoiceRecipientBetween(String value1, String value2) {
            addCriterion("invoice_recipient between", value1, value2, "invoiceRecipient");
            return (Criteria) this;
        }

        public Criteria andInvoiceRecipientNotBetween(String value1, String value2) {
            addCriterion("invoice_recipient not between", value1, value2, "invoiceRecipient");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingTelephoneIsNull() {
            addCriterion("invoice_receiving_telephone is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingTelephoneIsNotNull() {
            addCriterion("invoice_receiving_telephone is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingTelephoneEqualTo(String value) {
            addCriterion("invoice_receiving_telephone =", value, "invoiceReceivingTelephone");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingTelephoneNotEqualTo(String value) {
            addCriterion("invoice_receiving_telephone <>", value, "invoiceReceivingTelephone");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingTelephoneGreaterThan(String value) {
            addCriterion("invoice_receiving_telephone >", value, "invoiceReceivingTelephone");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingTelephoneGreaterThanOrEqualTo(String value) {
            addCriterion("invoice_receiving_telephone >=", value, "invoiceReceivingTelephone");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingTelephoneLessThan(String value) {
            addCriterion("invoice_receiving_telephone <", value, "invoiceReceivingTelephone");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingTelephoneLessThanOrEqualTo(String value) {
            addCriterion("invoice_receiving_telephone <=", value, "invoiceReceivingTelephone");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingTelephoneLike(String value) {
            addCriterion("invoice_receiving_telephone like", value, "invoiceReceivingTelephone");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingTelephoneNotLike(String value) {
            addCriterion("invoice_receiving_telephone not like", value, "invoiceReceivingTelephone");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingTelephoneIn(List<String> values) {
            addCriterion("invoice_receiving_telephone in", values, "invoiceReceivingTelephone");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingTelephoneNotIn(List<String> values) {
            addCriterion("invoice_receiving_telephone not in", values, "invoiceReceivingTelephone");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingTelephoneBetween(String value1, String value2) {
            addCriterion("invoice_receiving_telephone between", value1, value2, "invoiceReceivingTelephone");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingTelephoneNotBetween(String value1, String value2) {
            addCriterion("invoice_receiving_telephone not between", value1, value2, "invoiceReceivingTelephone");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingAddressIsNull() {
            addCriterion("invoice_receiving_address is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingAddressIsNotNull() {
            addCriterion("invoice_receiving_address is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingAddressEqualTo(String value) {
            addCriterion("invoice_receiving_address =", value, "invoiceReceivingAddress");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingAddressNotEqualTo(String value) {
            addCriterion("invoice_receiving_address <>", value, "invoiceReceivingAddress");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingAddressGreaterThan(String value) {
            addCriterion("invoice_receiving_address >", value, "invoiceReceivingAddress");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingAddressGreaterThanOrEqualTo(String value) {
            addCriterion("invoice_receiving_address >=", value, "invoiceReceivingAddress");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingAddressLessThan(String value) {
            addCriterion("invoice_receiving_address <", value, "invoiceReceivingAddress");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingAddressLessThanOrEqualTo(String value) {
            addCriterion("invoice_receiving_address <=", value, "invoiceReceivingAddress");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingAddressLike(String value) {
            addCriterion("invoice_receiving_address like", value, "invoiceReceivingAddress");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingAddressNotLike(String value) {
            addCriterion("invoice_receiving_address not like", value, "invoiceReceivingAddress");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingAddressIn(List<String> values) {
            addCriterion("invoice_receiving_address in", values, "invoiceReceivingAddress");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingAddressNotIn(List<String> values) {
            addCriterion("invoice_receiving_address not in", values, "invoiceReceivingAddress");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingAddressBetween(String value1, String value2) {
            addCriterion("invoice_receiving_address between", value1, value2, "invoiceReceivingAddress");
            return (Criteria) this;
        }

        public Criteria andInvoiceReceivingAddressNotBetween(String value1, String value2) {
            addCriterion("invoice_receiving_address not between", value1, value2, "invoiceReceivingAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyRegisteredAddressIsNull() {
            addCriterion("company_registered_address is null");
            return (Criteria) this;
        }

        public Criteria andCompanyRegisteredAddressIsNotNull() {
            addCriterion("company_registered_address is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyRegisteredAddressEqualTo(String value) {
            addCriterion("company_registered_address =", value, "companyRegisteredAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyRegisteredAddressNotEqualTo(String value) {
            addCriterion("company_registered_address <>", value, "companyRegisteredAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyRegisteredAddressGreaterThan(String value) {
            addCriterion("company_registered_address >", value, "companyRegisteredAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyRegisteredAddressGreaterThanOrEqualTo(String value) {
            addCriterion("company_registered_address >=", value, "companyRegisteredAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyRegisteredAddressLessThan(String value) {
            addCriterion("company_registered_address <", value, "companyRegisteredAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyRegisteredAddressLessThanOrEqualTo(String value) {
            addCriterion("company_registered_address <=", value, "companyRegisteredAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyRegisteredAddressLike(String value) {
            addCriterion("company_registered_address like", value, "companyRegisteredAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyRegisteredAddressNotLike(String value) {
            addCriterion("company_registered_address not like", value, "companyRegisteredAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyRegisteredAddressIn(List<String> values) {
            addCriterion("company_registered_address in", values, "companyRegisteredAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyRegisteredAddressNotIn(List<String> values) {
            addCriterion("company_registered_address not in", values, "companyRegisteredAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyRegisteredAddressBetween(String value1, String value2) {
            addCriterion("company_registered_address between", value1, value2, "companyRegisteredAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyRegisteredAddressNotBetween(String value1, String value2) {
            addCriterion("company_registered_address not between", value1, value2, "companyRegisteredAddress");
            return (Criteria) this;
        }

        public Criteria andTelephoneIsNull() {
            addCriterion("telephone is null");
            return (Criteria) this;
        }

        public Criteria andTelephoneIsNotNull() {
            addCriterion("telephone is not null");
            return (Criteria) this;
        }

        public Criteria andTelephoneEqualTo(String value) {
            addCriterion("telephone =", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneNotEqualTo(String value) {
            addCriterion("telephone <>", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneGreaterThan(String value) {
            addCriterion("telephone >", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneGreaterThanOrEqualTo(String value) {
            addCriterion("telephone >=", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneLessThan(String value) {
            addCriterion("telephone <", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneLessThanOrEqualTo(String value) {
            addCriterion("telephone <=", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneLike(String value) {
            addCriterion("telephone like", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneNotLike(String value) {
            addCriterion("telephone not like", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneIn(List<String> values) {
            addCriterion("telephone in", values, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneNotIn(List<String> values) {
            addCriterion("telephone not in", values, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneBetween(String value1, String value2) {
            addCriterion("telephone between", value1, value2, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneNotBetween(String value1, String value2) {
            addCriterion("telephone not between", value1, value2, "telephone");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankIsNull() {
            addCriterion("invoice_bank is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankIsNotNull() {
            addCriterion("invoice_bank is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankEqualTo(String value) {
            addCriterion("invoice_bank =", value, "invoiceBank");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNotEqualTo(String value) {
            addCriterion("invoice_bank <>", value, "invoiceBank");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankGreaterThan(String value) {
            addCriterion("invoice_bank >", value, "invoiceBank");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankGreaterThanOrEqualTo(String value) {
            addCriterion("invoice_bank >=", value, "invoiceBank");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankLessThan(String value) {
            addCriterion("invoice_bank <", value, "invoiceBank");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankLessThanOrEqualTo(String value) {
            addCriterion("invoice_bank <=", value, "invoiceBank");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankLike(String value) {
            addCriterion("invoice_bank like", value, "invoiceBank");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNotLike(String value) {
            addCriterion("invoice_bank not like", value, "invoiceBank");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankIn(List<String> values) {
            addCriterion("invoice_bank in", values, "invoiceBank");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNotIn(List<String> values) {
            addCriterion("invoice_bank not in", values, "invoiceBank");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankBetween(String value1, String value2) {
            addCriterion("invoice_bank between", value1, value2, "invoiceBank");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNotBetween(String value1, String value2) {
            addCriterion("invoice_bank not between", value1, value2, "invoiceBank");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNumIsNull() {
            addCriterion("invoice_bank_num is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNumIsNotNull() {
            addCriterion("invoice_bank_num is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNumEqualTo(String value) {
            addCriterion("invoice_bank_num =", value, "invoiceBankNum");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNumNotEqualTo(String value) {
            addCriterion("invoice_bank_num <>", value, "invoiceBankNum");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNumGreaterThan(String value) {
            addCriterion("invoice_bank_num >", value, "invoiceBankNum");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNumGreaterThanOrEqualTo(String value) {
            addCriterion("invoice_bank_num >=", value, "invoiceBankNum");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNumLessThan(String value) {
            addCriterion("invoice_bank_num <", value, "invoiceBankNum");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNumLessThanOrEqualTo(String value) {
            addCriterion("invoice_bank_num <=", value, "invoiceBankNum");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNumLike(String value) {
            addCriterion("invoice_bank_num like", value, "invoiceBankNum");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNumNotLike(String value) {
            addCriterion("invoice_bank_num not like", value, "invoiceBankNum");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNumIn(List<String> values) {
            addCriterion("invoice_bank_num in", values, "invoiceBankNum");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNumNotIn(List<String> values) {
            addCriterion("invoice_bank_num not in", values, "invoiceBankNum");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNumBetween(String value1, String value2) {
            addCriterion("invoice_bank_num between", value1, value2, "invoiceBankNum");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNumNotBetween(String value1, String value2) {
            addCriterion("invoice_bank_num not between", value1, value2, "invoiceBankNum");
            return (Criteria) this;
        }

        public Criteria andAccountStatusIsNull() {
            addCriterion("account_status is null");
            return (Criteria) this;
        }

        public Criteria andAccountStatusIsNotNull() {
            addCriterion("account_status is not null");
            return (Criteria) this;
        }

        public Criteria andAccountStatusEqualTo(Integer value) {
            addCriterion("account_status =", value, "accountStatus");
            return (Criteria) this;
        }

        public Criteria andAccountStatusNotEqualTo(Integer value) {
            addCriterion("account_status <>", value, "accountStatus");
            return (Criteria) this;
        }

        public Criteria andAccountStatusGreaterThan(Integer value) {
            addCriterion("account_status >", value, "accountStatus");
            return (Criteria) this;
        }

        public Criteria andAccountStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("account_status >=", value, "accountStatus");
            return (Criteria) this;
        }

        public Criteria andAccountStatusLessThan(Integer value) {
            addCriterion("account_status <", value, "accountStatus");
            return (Criteria) this;
        }

        public Criteria andAccountStatusLessThanOrEqualTo(Integer value) {
            addCriterion("account_status <=", value, "accountStatus");
            return (Criteria) this;
        }

        public Criteria andAccountStatusIn(List<Integer> values) {
            addCriterion("account_status in", values, "accountStatus");
            return (Criteria) this;
        }

        public Criteria andAccountStatusNotIn(List<Integer> values) {
            addCriterion("account_status not in", values, "accountStatus");
            return (Criteria) this;
        }

        public Criteria andAccountStatusBetween(Integer value1, Integer value2) {
            addCriterion("account_status between", value1, value2, "accountStatus");
            return (Criteria) this;
        }

        public Criteria andAccountStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("account_status not between", value1, value2, "accountStatus");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNull() {
            addCriterion("create_by is null");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNotNull() {
            addCriterion("create_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreateByEqualTo(String value) {
            addCriterion("create_by =", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotEqualTo(String value) {
            addCriterion("create_by <>", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThan(String value) {
            addCriterion("create_by >", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThanOrEqualTo(String value) {
            addCriterion("create_by >=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThan(String value) {
            addCriterion("create_by <", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThanOrEqualTo(String value) {
            addCriterion("create_by <=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLike(String value) {
            addCriterion("create_by like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotLike(String value) {
            addCriterion("create_by not like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByIn(List<String> values) {
            addCriterion("create_by in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotIn(List<String> values) {
            addCriterion("create_by not in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByBetween(String value1, String value2) {
            addCriterion("create_by between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotBetween(String value1, String value2) {
            addCriterion("create_by not between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNull() {
            addCriterion("update_by is null");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNotNull() {
            addCriterion("update_by is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateByEqualTo(String value) {
            addCriterion("update_by =", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotEqualTo(String value) {
            addCriterion("update_by <>", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThan(String value) {
            addCriterion("update_by >", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThanOrEqualTo(String value) {
            addCriterion("update_by >=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThan(String value) {
            addCriterion("update_by <", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThanOrEqualTo(String value) {
            addCriterion("update_by <=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLike(String value) {
            addCriterion("update_by like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotLike(String value) {
            addCriterion("update_by not like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByIn(List<String> values) {
            addCriterion("update_by in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotIn(List<String> values) {
            addCriterion("update_by not in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByBetween(String value1, String value2) {
            addCriterion("update_by between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotBetween(String value1, String value2) {
            addCriterion("update_by not between", value1, value2, "updateBy");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}