package com.xq.live.globalVoyage$.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PayRefundDetailsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PayRefundDetailsExample() {
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

        public Criteria andOutTradeNoIsNull() {
            addCriterion("out_trade_no is null");
            return (Criteria) this;
        }

        public Criteria andOutTradeNoIsNotNull() {
            addCriterion("out_trade_no is not null");
            return (Criteria) this;
        }

        public Criteria andOutTradeNoEqualTo(String value) {
            addCriterion("out_trade_no =", value, "outTradeNo");
            return (Criteria) this;
        }

        public Criteria andOutTradeNoNotEqualTo(String value) {
            addCriterion("out_trade_no <>", value, "outTradeNo");
            return (Criteria) this;
        }

        public Criteria andOutTradeNoGreaterThan(String value) {
            addCriterion("out_trade_no >", value, "outTradeNo");
            return (Criteria) this;
        }

        public Criteria andOutTradeNoGreaterThanOrEqualTo(String value) {
            addCriterion("out_trade_no >=", value, "outTradeNo");
            return (Criteria) this;
        }

        public Criteria andOutTradeNoLessThan(String value) {
            addCriterion("out_trade_no <", value, "outTradeNo");
            return (Criteria) this;
        }

        public Criteria andOutTradeNoLessThanOrEqualTo(String value) {
            addCriterion("out_trade_no <=", value, "outTradeNo");
            return (Criteria) this;
        }

        public Criteria andOutTradeNoLike(String value) {
            addCriterion("out_trade_no like", value, "outTradeNo");
            return (Criteria) this;
        }

        public Criteria andOutTradeNoNotLike(String value) {
            addCriterion("out_trade_no not like", value, "outTradeNo");
            return (Criteria) this;
        }

        public Criteria andOutTradeNoIn(List<String> values) {
            addCriterion("out_trade_no in", values, "outTradeNo");
            return (Criteria) this;
        }

        public Criteria andOutTradeNoNotIn(List<String> values) {
            addCriterion("out_trade_no not in", values, "outTradeNo");
            return (Criteria) this;
        }

        public Criteria andOutTradeNoBetween(String value1, String value2) {
            addCriterion("out_trade_no between", value1, value2, "outTradeNo");
            return (Criteria) this;
        }

        public Criteria andOutTradeNoNotBetween(String value1, String value2) {
            addCriterion("out_trade_no not between", value1, value2, "outTradeNo");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNull() {
            addCriterion("order_type is null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNotNull() {
            addCriterion("order_type is not null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeEqualTo(Integer value) {
            addCriterion("order_type =", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotEqualTo(Integer value) {
            addCriterion("order_type <>", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThan(Integer value) {
            addCriterion("order_type >", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_type >=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThan(Integer value) {
            addCriterion("order_type <", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThanOrEqualTo(Integer value) {
            addCriterion("order_type <=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIn(List<Integer> values) {
            addCriterion("order_type in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotIn(List<Integer> values) {
            addCriterion("order_type not in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeBetween(Integer value1, Integer value2) {
            addCriterion("order_type between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("order_type not between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andTotalFeeIsNull() {
            addCriterion("total_fee is null");
            return (Criteria) this;
        }

        public Criteria andTotalFeeIsNotNull() {
            addCriterion("total_fee is not null");
            return (Criteria) this;
        }

        public Criteria andTotalFeeEqualTo(BigDecimal value) {
            addCriterion("total_fee =", value, "totalFee");
            return (Criteria) this;
        }

        public Criteria andTotalFeeNotEqualTo(BigDecimal value) {
            addCriterion("total_fee <>", value, "totalFee");
            return (Criteria) this;
        }

        public Criteria andTotalFeeGreaterThan(BigDecimal value) {
            addCriterion("total_fee >", value, "totalFee");
            return (Criteria) this;
        }

        public Criteria andTotalFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_fee >=", value, "totalFee");
            return (Criteria) this;
        }

        public Criteria andTotalFeeLessThan(BigDecimal value) {
            addCriterion("total_fee <", value, "totalFee");
            return (Criteria) this;
        }

        public Criteria andTotalFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_fee <=", value, "totalFee");
            return (Criteria) this;
        }

        public Criteria andTotalFeeIn(List<BigDecimal> values) {
            addCriterion("total_fee in", values, "totalFee");
            return (Criteria) this;
        }

        public Criteria andTotalFeeNotIn(List<BigDecimal> values) {
            addCriterion("total_fee not in", values, "totalFee");
            return (Criteria) this;
        }

        public Criteria andTotalFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_fee between", value1, value2, "totalFee");
            return (Criteria) this;
        }

        public Criteria andTotalFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_fee not between", value1, value2, "totalFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeIsNull() {
            addCriterion("refund_fee is null");
            return (Criteria) this;
        }

        public Criteria andRefundFeeIsNotNull() {
            addCriterion("refund_fee is not null");
            return (Criteria) this;
        }

        public Criteria andRefundFeeEqualTo(BigDecimal value) {
            addCriterion("refund_fee =", value, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeNotEqualTo(BigDecimal value) {
            addCriterion("refund_fee <>", value, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeGreaterThan(BigDecimal value) {
            addCriterion("refund_fee >", value, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("refund_fee >=", value, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeLessThan(BigDecimal value) {
            addCriterion("refund_fee <", value, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("refund_fee <=", value, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeIn(List<BigDecimal> values) {
            addCriterion("refund_fee in", values, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeNotIn(List<BigDecimal> values) {
            addCriterion("refund_fee not in", values, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("refund_fee between", value1, value2, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("refund_fee not between", value1, value2, "refundFee");
            return (Criteria) this;
        }

        public Criteria andApplyReasonIsNull() {
            addCriterion("apply_reason is null");
            return (Criteria) this;
        }

        public Criteria andApplyReasonIsNotNull() {
            addCriterion("apply_reason is not null");
            return (Criteria) this;
        }

        public Criteria andApplyReasonEqualTo(String value) {
            addCriterion("apply_reason =", value, "applyReason");
            return (Criteria) this;
        }

        public Criteria andApplyReasonNotEqualTo(String value) {
            addCriterion("apply_reason <>", value, "applyReason");
            return (Criteria) this;
        }

        public Criteria andApplyReasonGreaterThan(String value) {
            addCriterion("apply_reason >", value, "applyReason");
            return (Criteria) this;
        }

        public Criteria andApplyReasonGreaterThanOrEqualTo(String value) {
            addCriterion("apply_reason >=", value, "applyReason");
            return (Criteria) this;
        }

        public Criteria andApplyReasonLessThan(String value) {
            addCriterion("apply_reason <", value, "applyReason");
            return (Criteria) this;
        }

        public Criteria andApplyReasonLessThanOrEqualTo(String value) {
            addCriterion("apply_reason <=", value, "applyReason");
            return (Criteria) this;
        }

        public Criteria andApplyReasonLike(String value) {
            addCriterion("apply_reason like", value, "applyReason");
            return (Criteria) this;
        }

        public Criteria andApplyReasonNotLike(String value) {
            addCriterion("apply_reason not like", value, "applyReason");
            return (Criteria) this;
        }

        public Criteria andApplyReasonIn(List<String> values) {
            addCriterion("apply_reason in", values, "applyReason");
            return (Criteria) this;
        }

        public Criteria andApplyReasonNotIn(List<String> values) {
            addCriterion("apply_reason not in", values, "applyReason");
            return (Criteria) this;
        }

        public Criteria andApplyReasonBetween(String value1, String value2) {
            addCriterion("apply_reason between", value1, value2, "applyReason");
            return (Criteria) this;
        }

        public Criteria andApplyReasonNotBetween(String value1, String value2) {
            addCriterion("apply_reason not between", value1, value2, "applyReason");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIsNull() {
            addCriterion("apply_time is null");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIsNotNull() {
            addCriterion("apply_time is not null");
            return (Criteria) this;
        }

        public Criteria andApplyTimeEqualTo(Date value) {
            addCriterion("apply_time =", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotEqualTo(Date value) {
            addCriterion("apply_time <>", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeGreaterThan(Date value) {
            addCriterion("apply_time >", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("apply_time >=", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeLessThan(Date value) {
            addCriterion("apply_time <", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeLessThanOrEqualTo(Date value) {
            addCriterion("apply_time <=", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIn(List<Date> values) {
            addCriterion("apply_time in", values, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotIn(List<Date> values) {
            addCriterion("apply_time not in", values, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeBetween(Date value1, Date value2) {
            addCriterion("apply_time between", value1, value2, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotBetween(Date value1, Date value2) {
            addCriterion("apply_time not between", value1, value2, "applyTime");
            return (Criteria) this;
        }

        public Criteria andShopAuditStatusIsNull() {
            addCriterion("shop_audit_status is null");
            return (Criteria) this;
        }

        public Criteria andShopAuditStatusIsNotNull() {
            addCriterion("shop_audit_status is not null");
            return (Criteria) this;
        }

        public Criteria andShopAuditStatusEqualTo(Integer value) {
            addCriterion("shop_audit_status =", value, "shopAuditStatus");
            return (Criteria) this;
        }

        public Criteria andShopAuditStatusNotEqualTo(Integer value) {
            addCriterion("shop_audit_status <>", value, "shopAuditStatus");
            return (Criteria) this;
        }

        public Criteria andShopAuditStatusGreaterThan(Integer value) {
            addCriterion("shop_audit_status >", value, "shopAuditStatus");
            return (Criteria) this;
        }

        public Criteria andShopAuditStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("shop_audit_status >=", value, "shopAuditStatus");
            return (Criteria) this;
        }

        public Criteria andShopAuditStatusLessThan(Integer value) {
            addCriterion("shop_audit_status <", value, "shopAuditStatus");
            return (Criteria) this;
        }

        public Criteria andShopAuditStatusLessThanOrEqualTo(Integer value) {
            addCriterion("shop_audit_status <=", value, "shopAuditStatus");
            return (Criteria) this;
        }

        public Criteria andShopAuditStatusIn(List<Integer> values) {
            addCriterion("shop_audit_status in", values, "shopAuditStatus");
            return (Criteria) this;
        }

        public Criteria andShopAuditStatusNotIn(List<Integer> values) {
            addCriterion("shop_audit_status not in", values, "shopAuditStatus");
            return (Criteria) this;
        }

        public Criteria andShopAuditStatusBetween(Integer value1, Integer value2) {
            addCriterion("shop_audit_status between", value1, value2, "shopAuditStatus");
            return (Criteria) this;
        }

        public Criteria andShopAuditStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("shop_audit_status not between", value1, value2, "shopAuditStatus");
            return (Criteria) this;
        }

        public Criteria andShopAuditTimeIsNull() {
            addCriterion("shop_audit_time is null");
            return (Criteria) this;
        }

        public Criteria andShopAuditTimeIsNotNull() {
            addCriterion("shop_audit_time is not null");
            return (Criteria) this;
        }

        public Criteria andShopAuditTimeEqualTo(Date value) {
            addCriterion("shop_audit_time =", value, "shopAuditTime");
            return (Criteria) this;
        }

        public Criteria andShopAuditTimeNotEqualTo(Date value) {
            addCriterion("shop_audit_time <>", value, "shopAuditTime");
            return (Criteria) this;
        }

        public Criteria andShopAuditTimeGreaterThan(Date value) {
            addCriterion("shop_audit_time >", value, "shopAuditTime");
            return (Criteria) this;
        }

        public Criteria andShopAuditTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("shop_audit_time >=", value, "shopAuditTime");
            return (Criteria) this;
        }

        public Criteria andShopAuditTimeLessThan(Date value) {
            addCriterion("shop_audit_time <", value, "shopAuditTime");
            return (Criteria) this;
        }

        public Criteria andShopAuditTimeLessThanOrEqualTo(Date value) {
            addCriterion("shop_audit_time <=", value, "shopAuditTime");
            return (Criteria) this;
        }

        public Criteria andShopAuditTimeIn(List<Date> values) {
            addCriterion("shop_audit_time in", values, "shopAuditTime");
            return (Criteria) this;
        }

        public Criteria andShopAuditTimeNotIn(List<Date> values) {
            addCriterion("shop_audit_time not in", values, "shopAuditTime");
            return (Criteria) this;
        }

        public Criteria andShopAuditTimeBetween(Date value1, Date value2) {
            addCriterion("shop_audit_time between", value1, value2, "shopAuditTime");
            return (Criteria) this;
        }

        public Criteria andShopAuditTimeNotBetween(Date value1, Date value2) {
            addCriterion("shop_audit_time not between", value1, value2, "shopAuditTime");
            return (Criteria) this;
        }

        public Criteria andShopAuditRemarksIsNull() {
            addCriterion("shop_audit_remarks is null");
            return (Criteria) this;
        }

        public Criteria andShopAuditRemarksIsNotNull() {
            addCriterion("shop_audit_remarks is not null");
            return (Criteria) this;
        }

        public Criteria andShopAuditRemarksEqualTo(String value) {
            addCriterion("shop_audit_remarks =", value, "shopAuditRemarks");
            return (Criteria) this;
        }

        public Criteria andShopAuditRemarksNotEqualTo(String value) {
            addCriterion("shop_audit_remarks <>", value, "shopAuditRemarks");
            return (Criteria) this;
        }

        public Criteria andShopAuditRemarksGreaterThan(String value) {
            addCriterion("shop_audit_remarks >", value, "shopAuditRemarks");
            return (Criteria) this;
        }

        public Criteria andShopAuditRemarksGreaterThanOrEqualTo(String value) {
            addCriterion("shop_audit_remarks >=", value, "shopAuditRemarks");
            return (Criteria) this;
        }

        public Criteria andShopAuditRemarksLessThan(String value) {
            addCriterion("shop_audit_remarks <", value, "shopAuditRemarks");
            return (Criteria) this;
        }

        public Criteria andShopAuditRemarksLessThanOrEqualTo(String value) {
            addCriterion("shop_audit_remarks <=", value, "shopAuditRemarks");
            return (Criteria) this;
        }

        public Criteria andShopAuditRemarksLike(String value) {
            addCriterion("shop_audit_remarks like", value, "shopAuditRemarks");
            return (Criteria) this;
        }

        public Criteria andShopAuditRemarksNotLike(String value) {
            addCriterion("shop_audit_remarks not like", value, "shopAuditRemarks");
            return (Criteria) this;
        }

        public Criteria andShopAuditRemarksIn(List<String> values) {
            addCriterion("shop_audit_remarks in", values, "shopAuditRemarks");
            return (Criteria) this;
        }

        public Criteria andShopAuditRemarksNotIn(List<String> values) {
            addCriterion("shop_audit_remarks not in", values, "shopAuditRemarks");
            return (Criteria) this;
        }

        public Criteria andShopAuditRemarksBetween(String value1, String value2) {
            addCriterion("shop_audit_remarks between", value1, value2, "shopAuditRemarks");
            return (Criteria) this;
        }

        public Criteria andShopAuditRemarksNotBetween(String value1, String value2) {
            addCriterion("shop_audit_remarks not between", value1, value2, "shopAuditRemarks");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditStatusIsNull() {
            addCriterion("operator_audit_status is null");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditStatusIsNotNull() {
            addCriterion("operator_audit_status is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditStatusEqualTo(Integer value) {
            addCriterion("operator_audit_status =", value, "operatorAuditStatus");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditStatusNotEqualTo(Integer value) {
            addCriterion("operator_audit_status <>", value, "operatorAuditStatus");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditStatusGreaterThan(Integer value) {
            addCriterion("operator_audit_status >", value, "operatorAuditStatus");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("operator_audit_status >=", value, "operatorAuditStatus");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditStatusLessThan(Integer value) {
            addCriterion("operator_audit_status <", value, "operatorAuditStatus");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditStatusLessThanOrEqualTo(Integer value) {
            addCriterion("operator_audit_status <=", value, "operatorAuditStatus");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditStatusIn(List<Integer> values) {
            addCriterion("operator_audit_status in", values, "operatorAuditStatus");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditStatusNotIn(List<Integer> values) {
            addCriterion("operator_audit_status not in", values, "operatorAuditStatus");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditStatusBetween(Integer value1, Integer value2) {
            addCriterion("operator_audit_status between", value1, value2, "operatorAuditStatus");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("operator_audit_status not between", value1, value2, "operatorAuditStatus");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditTimeIsNull() {
            addCriterion("operator_audit_time is null");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditTimeIsNotNull() {
            addCriterion("operator_audit_time is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditTimeEqualTo(Date value) {
            addCriterion("operator_audit_time =", value, "operatorAuditTime");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditTimeNotEqualTo(Date value) {
            addCriterion("operator_audit_time <>", value, "operatorAuditTime");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditTimeGreaterThan(Date value) {
            addCriterion("operator_audit_time >", value, "operatorAuditTime");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("operator_audit_time >=", value, "operatorAuditTime");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditTimeLessThan(Date value) {
            addCriterion("operator_audit_time <", value, "operatorAuditTime");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditTimeLessThanOrEqualTo(Date value) {
            addCriterion("operator_audit_time <=", value, "operatorAuditTime");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditTimeIn(List<Date> values) {
            addCriterion("operator_audit_time in", values, "operatorAuditTime");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditTimeNotIn(List<Date> values) {
            addCriterion("operator_audit_time not in", values, "operatorAuditTime");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditTimeBetween(Date value1, Date value2) {
            addCriterion("operator_audit_time between", value1, value2, "operatorAuditTime");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditTimeNotBetween(Date value1, Date value2) {
            addCriterion("operator_audit_time not between", value1, value2, "operatorAuditTime");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditRemarksIsNull() {
            addCriterion("operator_audit_remarks is null");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditRemarksIsNotNull() {
            addCriterion("operator_audit_remarks is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditRemarksEqualTo(String value) {
            addCriterion("operator_audit_remarks =", value, "operatorAuditRemarks");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditRemarksNotEqualTo(String value) {
            addCriterion("operator_audit_remarks <>", value, "operatorAuditRemarks");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditRemarksGreaterThan(String value) {
            addCriterion("operator_audit_remarks >", value, "operatorAuditRemarks");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditRemarksGreaterThanOrEqualTo(String value) {
            addCriterion("operator_audit_remarks >=", value, "operatorAuditRemarks");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditRemarksLessThan(String value) {
            addCriterion("operator_audit_remarks <", value, "operatorAuditRemarks");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditRemarksLessThanOrEqualTo(String value) {
            addCriterion("operator_audit_remarks <=", value, "operatorAuditRemarks");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditRemarksLike(String value) {
            addCriterion("operator_audit_remarks like", value, "operatorAuditRemarks");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditRemarksNotLike(String value) {
            addCriterion("operator_audit_remarks not like", value, "operatorAuditRemarks");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditRemarksIn(List<String> values) {
            addCriterion("operator_audit_remarks in", values, "operatorAuditRemarks");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditRemarksNotIn(List<String> values) {
            addCriterion("operator_audit_remarks not in", values, "operatorAuditRemarks");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditRemarksBetween(String value1, String value2) {
            addCriterion("operator_audit_remarks between", value1, value2, "operatorAuditRemarks");
            return (Criteria) this;
        }

        public Criteria andOperatorAuditRemarksNotBetween(String value1, String value2) {
            addCriterion("operator_audit_remarks not between", value1, value2, "operatorAuditRemarks");
            return (Criteria) this;
        }

        public Criteria andRefundStatusIsNull() {
            addCriterion("refund_status is null");
            return (Criteria) this;
        }

        public Criteria andRefundStatusIsNotNull() {
            addCriterion("refund_status is not null");
            return (Criteria) this;
        }

        public Criteria andRefundStatusEqualTo(Integer value) {
            addCriterion("refund_status =", value, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusNotEqualTo(Integer value) {
            addCriterion("refund_status <>", value, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusGreaterThan(Integer value) {
            addCriterion("refund_status >", value, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("refund_status >=", value, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusLessThan(Integer value) {
            addCriterion("refund_status <", value, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusLessThanOrEqualTo(Integer value) {
            addCriterion("refund_status <=", value, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusIn(List<Integer> values) {
            addCriterion("refund_status in", values, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusNotIn(List<Integer> values) {
            addCriterion("refund_status not in", values, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusBetween(Integer value1, Integer value2) {
            addCriterion("refund_status between", value1, value2, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("refund_status not between", value1, value2, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andSettlementRefundFeeIsNull() {
            addCriterion("settlement_refund_fee is null");
            return (Criteria) this;
        }

        public Criteria andSettlementRefundFeeIsNotNull() {
            addCriterion("settlement_refund_fee is not null");
            return (Criteria) this;
        }

        public Criteria andSettlementRefundFeeEqualTo(BigDecimal value) {
            addCriterion("settlement_refund_fee =", value, "settlementRefundFee");
            return (Criteria) this;
        }

        public Criteria andSettlementRefundFeeNotEqualTo(BigDecimal value) {
            addCriterion("settlement_refund_fee <>", value, "settlementRefundFee");
            return (Criteria) this;
        }

        public Criteria andSettlementRefundFeeGreaterThan(BigDecimal value) {
            addCriterion("settlement_refund_fee >", value, "settlementRefundFee");
            return (Criteria) this;
        }

        public Criteria andSettlementRefundFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("settlement_refund_fee >=", value, "settlementRefundFee");
            return (Criteria) this;
        }

        public Criteria andSettlementRefundFeeLessThan(BigDecimal value) {
            addCriterion("settlement_refund_fee <", value, "settlementRefundFee");
            return (Criteria) this;
        }

        public Criteria andSettlementRefundFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("settlement_refund_fee <=", value, "settlementRefundFee");
            return (Criteria) this;
        }

        public Criteria andSettlementRefundFeeIn(List<BigDecimal> values) {
            addCriterion("settlement_refund_fee in", values, "settlementRefundFee");
            return (Criteria) this;
        }

        public Criteria andSettlementRefundFeeNotIn(List<BigDecimal> values) {
            addCriterion("settlement_refund_fee not in", values, "settlementRefundFee");
            return (Criteria) this;
        }

        public Criteria andSettlementRefundFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("settlement_refund_fee between", value1, value2, "settlementRefundFee");
            return (Criteria) this;
        }

        public Criteria andSettlementRefundFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("settlement_refund_fee not between", value1, value2, "settlementRefundFee");
            return (Criteria) this;
        }

        public Criteria andRefundTimeIsNull() {
            addCriterion("refund_time is null");
            return (Criteria) this;
        }

        public Criteria andRefundTimeIsNotNull() {
            addCriterion("refund_time is not null");
            return (Criteria) this;
        }

        public Criteria andRefundTimeEqualTo(Date value) {
            addCriterion("refund_time =", value, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeNotEqualTo(Date value) {
            addCriterion("refund_time <>", value, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeGreaterThan(Date value) {
            addCriterion("refund_time >", value, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("refund_time >=", value, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeLessThan(Date value) {
            addCriterion("refund_time <", value, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeLessThanOrEqualTo(Date value) {
            addCriterion("refund_time <=", value, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeIn(List<Date> values) {
            addCriterion("refund_time in", values, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeNotIn(List<Date> values) {
            addCriterion("refund_time not in", values, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeBetween(Date value1, Date value2) {
            addCriterion("refund_time between", value1, value2, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeNotBetween(Date value1, Date value2) {
            addCriterion("refund_time not between", value1, value2, "refundTime");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andOtherReasonIsNull() {
            addCriterion("other_reason is null");
            return (Criteria) this;
        }

        public Criteria andOtherReasonIsNotNull() {
            addCriterion("other_reason is not null");
            return (Criteria) this;
        }

        public Criteria andOtherReasonEqualTo(String value) {
            addCriterion("other_reason =", value, "otherReason");
            return (Criteria) this;
        }

        public Criteria andOtherReasonNotEqualTo(String value) {
            addCriterion("other_reason <>", value, "otherReason");
            return (Criteria) this;
        }

        public Criteria andOtherReasonGreaterThan(String value) {
            addCriterion("other_reason >", value, "otherReason");
            return (Criteria) this;
        }

        public Criteria andOtherReasonGreaterThanOrEqualTo(String value) {
            addCriterion("other_reason >=", value, "otherReason");
            return (Criteria) this;
        }

        public Criteria andOtherReasonLessThan(String value) {
            addCriterion("other_reason <", value, "otherReason");
            return (Criteria) this;
        }

        public Criteria andOtherReasonLessThanOrEqualTo(String value) {
            addCriterion("other_reason <=", value, "otherReason");
            return (Criteria) this;
        }

        public Criteria andOtherReasonLike(String value) {
            addCriterion("other_reason like", value, "otherReason");
            return (Criteria) this;
        }

        public Criteria andOtherReasonNotLike(String value) {
            addCriterion("other_reason not like", value, "otherReason");
            return (Criteria) this;
        }

        public Criteria andOtherReasonIn(List<String> values) {
            addCriterion("other_reason in", values, "otherReason");
            return (Criteria) this;
        }

        public Criteria andOtherReasonNotIn(List<String> values) {
            addCriterion("other_reason not in", values, "otherReason");
            return (Criteria) this;
        }

        public Criteria andOtherReasonBetween(String value1, String value2) {
            addCriterion("other_reason between", value1, value2, "otherReason");
            return (Criteria) this;
        }

        public Criteria andOtherReasonNotBetween(String value1, String value2) {
            addCriterion("other_reason not between", value1, value2, "otherReason");
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