package com.xq.live.globalVoyage$.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BaseTaxExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BaseTaxExample() {
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

        public Criteria andTaxIdIsNull() {
            addCriterion("tax_id is null");
            return (Criteria) this;
        }

        public Criteria andTaxIdIsNotNull() {
            addCriterion("tax_id is not null");
            return (Criteria) this;
        }

        public Criteria andTaxIdEqualTo(Integer value) {
            addCriterion("tax_id =", value, "taxId");
            return (Criteria) this;
        }

        public Criteria andTaxIdNotEqualTo(Integer value) {
            addCriterion("tax_id <>", value, "taxId");
            return (Criteria) this;
        }

        public Criteria andTaxIdGreaterThan(Integer value) {
            addCriterion("tax_id >", value, "taxId");
            return (Criteria) this;
        }

        public Criteria andTaxIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("tax_id >=", value, "taxId");
            return (Criteria) this;
        }

        public Criteria andTaxIdLessThan(Integer value) {
            addCriterion("tax_id <", value, "taxId");
            return (Criteria) this;
        }

        public Criteria andTaxIdLessThanOrEqualTo(Integer value) {
            addCriterion("tax_id <=", value, "taxId");
            return (Criteria) this;
        }

        public Criteria andTaxIdIn(List<Integer> values) {
            addCriterion("tax_id in", values, "taxId");
            return (Criteria) this;
        }

        public Criteria andTaxIdNotIn(List<Integer> values) {
            addCriterion("tax_id not in", values, "taxId");
            return (Criteria) this;
        }

        public Criteria andTaxIdBetween(Integer value1, Integer value2) {
            addCriterion("tax_id between", value1, value2, "taxId");
            return (Criteria) this;
        }

        public Criteria andTaxIdNotBetween(Integer value1, Integer value2) {
            addCriterion("tax_id not between", value1, value2, "taxId");
            return (Criteria) this;
        }

        public Criteria andLowLevelIsNull() {
            addCriterion("low_level is null");
            return (Criteria) this;
        }

        public Criteria andLowLevelIsNotNull() {
            addCriterion("low_level is not null");
            return (Criteria) this;
        }

        public Criteria andLowLevelEqualTo(BigDecimal value) {
            addCriterion("low_level =", value, "lowLevel");
            return (Criteria) this;
        }

        public Criteria andLowLevelNotEqualTo(BigDecimal value) {
            addCriterion("low_level <>", value, "lowLevel");
            return (Criteria) this;
        }

        public Criteria andLowLevelGreaterThan(BigDecimal value) {
            addCriterion("low_level >", value, "lowLevel");
            return (Criteria) this;
        }

        public Criteria andLowLevelGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("low_level >=", value, "lowLevel");
            return (Criteria) this;
        }

        public Criteria andLowLevelLessThan(BigDecimal value) {
            addCriterion("low_level <", value, "lowLevel");
            return (Criteria) this;
        }

        public Criteria andLowLevelLessThanOrEqualTo(BigDecimal value) {
            addCriterion("low_level <=", value, "lowLevel");
            return (Criteria) this;
        }

        public Criteria andLowLevelIn(List<BigDecimal> values) {
            addCriterion("low_level in", values, "lowLevel");
            return (Criteria) this;
        }

        public Criteria andLowLevelNotIn(List<BigDecimal> values) {
            addCriterion("low_level not in", values, "lowLevel");
            return (Criteria) this;
        }

        public Criteria andLowLevelBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("low_level between", value1, value2, "lowLevel");
            return (Criteria) this;
        }

        public Criteria andLowLevelNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("low_level not between", value1, value2, "lowLevel");
            return (Criteria) this;
        }

        public Criteria andHighLevelIsNull() {
            addCriterion("high_level is null");
            return (Criteria) this;
        }

        public Criteria andHighLevelIsNotNull() {
            addCriterion("high_level is not null");
            return (Criteria) this;
        }

        public Criteria andHighLevelEqualTo(BigDecimal value) {
            addCriterion("high_level =", value, "highLevel");
            return (Criteria) this;
        }

        public Criteria andHighLevelNotEqualTo(BigDecimal value) {
            addCriterion("high_level <>", value, "highLevel");
            return (Criteria) this;
        }

        public Criteria andHighLevelGreaterThan(BigDecimal value) {
            addCriterion("high_level >", value, "highLevel");
            return (Criteria) this;
        }

        public Criteria andHighLevelGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("high_level >=", value, "highLevel");
            return (Criteria) this;
        }

        public Criteria andHighLevelLessThan(BigDecimal value) {
            addCriterion("high_level <", value, "highLevel");
            return (Criteria) this;
        }

        public Criteria andHighLevelLessThanOrEqualTo(BigDecimal value) {
            addCriterion("high_level <=", value, "highLevel");
            return (Criteria) this;
        }

        public Criteria andHighLevelIn(List<BigDecimal> values) {
            addCriterion("high_level in", values, "highLevel");
            return (Criteria) this;
        }

        public Criteria andHighLevelNotIn(List<BigDecimal> values) {
            addCriterion("high_level not in", values, "highLevel");
            return (Criteria) this;
        }

        public Criteria andHighLevelBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("high_level between", value1, value2, "highLevel");
            return (Criteria) this;
        }

        public Criteria andHighLevelNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("high_level not between", value1, value2, "highLevel");
            return (Criteria) this;
        }

        public Criteria andRateIsNull() {
            addCriterion("rate is null");
            return (Criteria) this;
        }

        public Criteria andRateIsNotNull() {
            addCriterion("rate is not null");
            return (Criteria) this;
        }

        public Criteria andRateEqualTo(BigDecimal value) {
            addCriterion("rate =", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateNotEqualTo(BigDecimal value) {
            addCriterion("rate <>", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateGreaterThan(BigDecimal value) {
            addCriterion("rate >", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("rate >=", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateLessThan(BigDecimal value) {
            addCriterion("rate <", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("rate <=", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateIn(List<BigDecimal> values) {
            addCriterion("rate in", values, "rate");
            return (Criteria) this;
        }

        public Criteria andRateNotIn(List<BigDecimal> values) {
            addCriterion("rate not in", values, "rate");
            return (Criteria) this;
        }

        public Criteria andRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("rate between", value1, value2, "rate");
            return (Criteria) this;
        }

        public Criteria andRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("rate not between", value1, value2, "rate");
            return (Criteria) this;
        }

        public Criteria andQuickCalDeductionIsNull() {
            addCriterion("quick_cal_deduction is null");
            return (Criteria) this;
        }

        public Criteria andQuickCalDeductionIsNotNull() {
            addCriterion("quick_cal_deduction is not null");
            return (Criteria) this;
        }

        public Criteria andQuickCalDeductionEqualTo(BigDecimal value) {
            addCriterion("quick_cal_deduction =", value, "quickCalDeduction");
            return (Criteria) this;
        }

        public Criteria andQuickCalDeductionNotEqualTo(BigDecimal value) {
            addCriterion("quick_cal_deduction <>", value, "quickCalDeduction");
            return (Criteria) this;
        }

        public Criteria andQuickCalDeductionGreaterThan(BigDecimal value) {
            addCriterion("quick_cal_deduction >", value, "quickCalDeduction");
            return (Criteria) this;
        }

        public Criteria andQuickCalDeductionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("quick_cal_deduction >=", value, "quickCalDeduction");
            return (Criteria) this;
        }

        public Criteria andQuickCalDeductionLessThan(BigDecimal value) {
            addCriterion("quick_cal_deduction <", value, "quickCalDeduction");
            return (Criteria) this;
        }

        public Criteria andQuickCalDeductionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("quick_cal_deduction <=", value, "quickCalDeduction");
            return (Criteria) this;
        }

        public Criteria andQuickCalDeductionIn(List<BigDecimal> values) {
            addCriterion("quick_cal_deduction in", values, "quickCalDeduction");
            return (Criteria) this;
        }

        public Criteria andQuickCalDeductionNotIn(List<BigDecimal> values) {
            addCriterion("quick_cal_deduction not in", values, "quickCalDeduction");
            return (Criteria) this;
        }

        public Criteria andQuickCalDeductionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("quick_cal_deduction between", value1, value2, "quickCalDeduction");
            return (Criteria) this;
        }

        public Criteria andQuickCalDeductionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("quick_cal_deduction not between", value1, value2, "quickCalDeduction");
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

        public Criteria andIsDeleteIsNull() {
            addCriterion("is_delete is null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNotNull() {
            addCriterion("is_delete is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteEqualTo(Byte value) {
            addCriterion("is_delete =", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotEqualTo(Byte value) {
            addCriterion("is_delete <>", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThan(Byte value) {
            addCriterion("is_delete >", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_delete >=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThan(Byte value) {
            addCriterion("is_delete <", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThanOrEqualTo(Byte value) {
            addCriterion("is_delete <=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIn(List<Byte> values) {
            addCriterion("is_delete in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotIn(List<Byte> values) {
            addCriterion("is_delete not in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteBetween(Byte value1, Byte value2) {
            addCriterion("is_delete between", value1, value2, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotBetween(Byte value1, Byte value2) {
            addCriterion("is_delete not between", value1, value2, "isDelete");
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