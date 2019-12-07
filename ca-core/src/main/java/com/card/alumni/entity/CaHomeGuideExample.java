package com.card.alumni.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CaHomeGuideExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CaHomeGuideExample() {
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andHomePageIdIsNull() {
            addCriterion("home_page_id is null");
            return (Criteria) this;
        }

        public Criteria andHomePageIdIsNotNull() {
            addCriterion("home_page_id is not null");
            return (Criteria) this;
        }

        public Criteria andHomePageIdEqualTo(Integer value) {
            addCriterion("home_page_id =", value, "homePageId");
            return (Criteria) this;
        }

        public Criteria andHomePageIdNotEqualTo(Integer value) {
            addCriterion("home_page_id <>", value, "homePageId");
            return (Criteria) this;
        }

        public Criteria andHomePageIdGreaterThan(Integer value) {
            addCriterion("home_page_id >", value, "homePageId");
            return (Criteria) this;
        }

        public Criteria andHomePageIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("home_page_id >=", value, "homePageId");
            return (Criteria) this;
        }

        public Criteria andHomePageIdLessThan(Integer value) {
            addCriterion("home_page_id <", value, "homePageId");
            return (Criteria) this;
        }

        public Criteria andHomePageIdLessThanOrEqualTo(Integer value) {
            addCriterion("home_page_id <=", value, "homePageId");
            return (Criteria) this;
        }

        public Criteria andHomePageIdIn(List<Integer> values) {
            addCriterion("home_page_id in", values, "homePageId");
            return (Criteria) this;
        }

        public Criteria andHomePageIdNotIn(List<Integer> values) {
            addCriterion("home_page_id not in", values, "homePageId");
            return (Criteria) this;
        }

        public Criteria andHomePageIdBetween(Integer value1, Integer value2) {
            addCriterion("home_page_id between", value1, value2, "homePageId");
            return (Criteria) this;
        }

        public Criteria andHomePageIdNotBetween(Integer value1, Integer value2) {
            addCriterion("home_page_id not between", value1, value2, "homePageId");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andRedirectPathIsNull() {
            addCriterion("redirect_path is null");
            return (Criteria) this;
        }

        public Criteria andRedirectPathIsNotNull() {
            addCriterion("redirect_path is not null");
            return (Criteria) this;
        }

        public Criteria andRedirectPathEqualTo(String value) {
            addCriterion("redirect_path =", value, "redirectPath");
            return (Criteria) this;
        }

        public Criteria andRedirectPathNotEqualTo(String value) {
            addCriterion("redirect_path <>", value, "redirectPath");
            return (Criteria) this;
        }

        public Criteria andRedirectPathGreaterThan(String value) {
            addCriterion("redirect_path >", value, "redirectPath");
            return (Criteria) this;
        }

        public Criteria andRedirectPathGreaterThanOrEqualTo(String value) {
            addCriterion("redirect_path >=", value, "redirectPath");
            return (Criteria) this;
        }

        public Criteria andRedirectPathLessThan(String value) {
            addCriterion("redirect_path <", value, "redirectPath");
            return (Criteria) this;
        }

        public Criteria andRedirectPathLessThanOrEqualTo(String value) {
            addCriterion("redirect_path <=", value, "redirectPath");
            return (Criteria) this;
        }

        public Criteria andRedirectPathLike(String value) {
            addCriterion("redirect_path like", value, "redirectPath");
            return (Criteria) this;
        }

        public Criteria andRedirectPathNotLike(String value) {
            addCriterion("redirect_path not like", value, "redirectPath");
            return (Criteria) this;
        }

        public Criteria andRedirectPathIn(List<String> values) {
            addCriterion("redirect_path in", values, "redirectPath");
            return (Criteria) this;
        }

        public Criteria andRedirectPathNotIn(List<String> values) {
            addCriterion("redirect_path not in", values, "redirectPath");
            return (Criteria) this;
        }

        public Criteria andRedirectPathBetween(String value1, String value2) {
            addCriterion("redirect_path between", value1, value2, "redirectPath");
            return (Criteria) this;
        }

        public Criteria andRedirectPathNotBetween(String value1, String value2) {
            addCriterion("redirect_path not between", value1, value2, "redirectPath");
            return (Criteria) this;
        }

        public Criteria andRankIsNull() {
            addCriterion("rank is null");
            return (Criteria) this;
        }

        public Criteria andRankIsNotNull() {
            addCriterion("rank is not null");
            return (Criteria) this;
        }

        public Criteria andRankEqualTo(Integer value) {
            addCriterion("rank =", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankNotEqualTo(Integer value) {
            addCriterion("rank <>", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankGreaterThan(Integer value) {
            addCriterion("rank >", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankGreaterThanOrEqualTo(Integer value) {
            addCriterion("rank >=", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankLessThan(Integer value) {
            addCriterion("rank <", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankLessThanOrEqualTo(Integer value) {
            addCriterion("rank <=", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankIn(List<Integer> values) {
            addCriterion("rank in", values, "rank");
            return (Criteria) this;
        }

        public Criteria andRankNotIn(List<Integer> values) {
            addCriterion("rank not in", values, "rank");
            return (Criteria) this;
        }

        public Criteria andRankBetween(Integer value1, Integer value2) {
            addCriterion("rank between", value1, value2, "rank");
            return (Criteria) this;
        }

        public Criteria andRankNotBetween(Integer value1, Integer value2) {
            addCriterion("rank not between", value1, value2, "rank");
            return (Criteria) this;
        }

        public Criteria andBackColorIsNull() {
            addCriterion("back_color is null");
            return (Criteria) this;
        }

        public Criteria andBackColorIsNotNull() {
            addCriterion("back_color is not null");
            return (Criteria) this;
        }

        public Criteria andBackColorEqualTo(String value) {
            addCriterion("back_color =", value, "backColor");
            return (Criteria) this;
        }

        public Criteria andBackColorNotEqualTo(String value) {
            addCriterion("back_color <>", value, "backColor");
            return (Criteria) this;
        }

        public Criteria andBackColorGreaterThan(String value) {
            addCriterion("back_color >", value, "backColor");
            return (Criteria) this;
        }

        public Criteria andBackColorGreaterThanOrEqualTo(String value) {
            addCriterion("back_color >=", value, "backColor");
            return (Criteria) this;
        }

        public Criteria andBackColorLessThan(String value) {
            addCriterion("back_color <", value, "backColor");
            return (Criteria) this;
        }

        public Criteria andBackColorLessThanOrEqualTo(String value) {
            addCriterion("back_color <=", value, "backColor");
            return (Criteria) this;
        }

        public Criteria andBackColorLike(String value) {
            addCriterion("back_color like", value, "backColor");
            return (Criteria) this;
        }

        public Criteria andBackColorNotLike(String value) {
            addCriterion("back_color not like", value, "backColor");
            return (Criteria) this;
        }

        public Criteria andBackColorIn(List<String> values) {
            addCriterion("back_color in", values, "backColor");
            return (Criteria) this;
        }

        public Criteria andBackColorNotIn(List<String> values) {
            addCriterion("back_color not in", values, "backColor");
            return (Criteria) this;
        }

        public Criteria andBackColorBetween(String value1, String value2) {
            addCriterion("back_color between", value1, value2, "backColor");
            return (Criteria) this;
        }

        public Criteria andBackColorNotBetween(String value1, String value2) {
            addCriterion("back_color not between", value1, value2, "backColor");
            return (Criteria) this;
        }

        public Criteria andBackImgIsNull() {
            addCriterion("back_img is null");
            return (Criteria) this;
        }

        public Criteria andBackImgIsNotNull() {
            addCriterion("back_img is not null");
            return (Criteria) this;
        }

        public Criteria andBackImgEqualTo(String value) {
            addCriterion("back_img =", value, "backImg");
            return (Criteria) this;
        }

        public Criteria andBackImgNotEqualTo(String value) {
            addCriterion("back_img <>", value, "backImg");
            return (Criteria) this;
        }

        public Criteria andBackImgGreaterThan(String value) {
            addCriterion("back_img >", value, "backImg");
            return (Criteria) this;
        }

        public Criteria andBackImgGreaterThanOrEqualTo(String value) {
            addCriterion("back_img >=", value, "backImg");
            return (Criteria) this;
        }

        public Criteria andBackImgLessThan(String value) {
            addCriterion("back_img <", value, "backImg");
            return (Criteria) this;
        }

        public Criteria andBackImgLessThanOrEqualTo(String value) {
            addCriterion("back_img <=", value, "backImg");
            return (Criteria) this;
        }

        public Criteria andBackImgLike(String value) {
            addCriterion("back_img like", value, "backImg");
            return (Criteria) this;
        }

        public Criteria andBackImgNotLike(String value) {
            addCriterion("back_img not like", value, "backImg");
            return (Criteria) this;
        }

        public Criteria andBackImgIn(List<String> values) {
            addCriterion("back_img in", values, "backImg");
            return (Criteria) this;
        }

        public Criteria andBackImgNotIn(List<String> values) {
            addCriterion("back_img not in", values, "backImg");
            return (Criteria) this;
        }

        public Criteria andBackImgBetween(String value1, String value2) {
            addCriterion("back_img between", value1, value2, "backImg");
            return (Criteria) this;
        }

        public Criteria andBackImgNotBetween(String value1, String value2) {
            addCriterion("back_img not between", value1, value2, "backImg");
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

        public Criteria andYnIsNull() {
            addCriterion("yn is null");
            return (Criteria) this;
        }

        public Criteria andYnIsNotNull() {
            addCriterion("yn is not null");
            return (Criteria) this;
        }

        public Criteria andYnEqualTo(Integer value) {
            addCriterion("yn =", value, "yn");
            return (Criteria) this;
        }

        public Criteria andYnNotEqualTo(Integer value) {
            addCriterion("yn <>", value, "yn");
            return (Criteria) this;
        }

        public Criteria andYnGreaterThan(Integer value) {
            addCriterion("yn >", value, "yn");
            return (Criteria) this;
        }

        public Criteria andYnGreaterThanOrEqualTo(Integer value) {
            addCriterion("yn >=", value, "yn");
            return (Criteria) this;
        }

        public Criteria andYnLessThan(Integer value) {
            addCriterion("yn <", value, "yn");
            return (Criteria) this;
        }

        public Criteria andYnLessThanOrEqualTo(Integer value) {
            addCriterion("yn <=", value, "yn");
            return (Criteria) this;
        }

        public Criteria andYnIn(List<Integer> values) {
            addCriterion("yn in", values, "yn");
            return (Criteria) this;
        }

        public Criteria andYnNotIn(List<Integer> values) {
            addCriterion("yn not in", values, "yn");
            return (Criteria) this;
        }

        public Criteria andYnBetween(Integer value1, Integer value2) {
            addCriterion("yn between", value1, value2, "yn");
            return (Criteria) this;
        }

        public Criteria andYnNotBetween(Integer value1, Integer value2) {
            addCriterion("yn not between", value1, value2, "yn");
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