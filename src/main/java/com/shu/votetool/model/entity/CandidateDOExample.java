package com.shu.votetool.model.entity;

import java.util.ArrayList;
import java.util.List;

public class CandidateDOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CandidateDOExample() {
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

        public Criteria andCandidateNameIsNull() {
            addCriterion("candidate_name is null");
            return (Criteria) this;
        }

        public Criteria andCandidateNameIsNotNull() {
            addCriterion("candidate_name is not null");
            return (Criteria) this;
        }

        public Criteria andCandidateNameEqualTo(String value) {
            addCriterion("candidate_name =", value, "candidateName");
            return (Criteria) this;
        }

        public Criteria andCandidateNameNotEqualTo(String value) {
            addCriterion("candidate_name <>", value, "candidateName");
            return (Criteria) this;
        }

        public Criteria andCandidateNameGreaterThan(String value) {
            addCriterion("candidate_name >", value, "candidateName");
            return (Criteria) this;
        }

        public Criteria andCandidateNameGreaterThanOrEqualTo(String value) {
            addCriterion("candidate_name >=", value, "candidateName");
            return (Criteria) this;
        }

        public Criteria andCandidateNameLessThan(String value) {
            addCriterion("candidate_name <", value, "candidateName");
            return (Criteria) this;
        }

        public Criteria andCandidateNameLessThanOrEqualTo(String value) {
            addCriterion("candidate_name <=", value, "candidateName");
            return (Criteria) this;
        }

        public Criteria andCandidateNameLike(String value) {
            addCriterion("candidate_name like", value, "candidateName");
            return (Criteria) this;
        }

        public Criteria andCandidateNameNotLike(String value) {
            addCriterion("candidate_name not like", value, "candidateName");
            return (Criteria) this;
        }

        public Criteria andCandidateNameIn(List<String> values) {
            addCriterion("candidate_name in", values, "candidateName");
            return (Criteria) this;
        }

        public Criteria andCandidateNameNotIn(List<String> values) {
            addCriterion("candidate_name not in", values, "candidateName");
            return (Criteria) this;
        }

        public Criteria andCandidateNameBetween(String value1, String value2) {
            addCriterion("candidate_name between", value1, value2, "candidateName");
            return (Criteria) this;
        }

        public Criteria andCandidateNameNotBetween(String value1, String value2) {
            addCriterion("candidate_name not between", value1, value2, "candidateName");
            return (Criteria) this;
        }

        public Criteria andVoteIdIsNull() {
            addCriterion("vote_id is null");
            return (Criteria) this;
        }

        public Criteria andVoteIdIsNotNull() {
            addCriterion("vote_id is not null");
            return (Criteria) this;
        }

        public Criteria andVoteIdEqualTo(Integer value) {
            addCriterion("vote_id =", value, "voteId");
            return (Criteria) this;
        }

        public Criteria andVoteIdNotEqualTo(Integer value) {
            addCriterion("vote_id <>", value, "voteId");
            return (Criteria) this;
        }

        public Criteria andVoteIdGreaterThan(Integer value) {
            addCriterion("vote_id >", value, "voteId");
            return (Criteria) this;
        }

        public Criteria andVoteIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("vote_id >=", value, "voteId");
            return (Criteria) this;
        }

        public Criteria andVoteIdLessThan(Integer value) {
            addCriterion("vote_id <", value, "voteId");
            return (Criteria) this;
        }

        public Criteria andVoteIdLessThanOrEqualTo(Integer value) {
            addCriterion("vote_id <=", value, "voteId");
            return (Criteria) this;
        }

        public Criteria andVoteIdIn(List<Integer> values) {
            addCriterion("vote_id in", values, "voteId");
            return (Criteria) this;
        }

        public Criteria andVoteIdNotIn(List<Integer> values) {
            addCriterion("vote_id not in", values, "voteId");
            return (Criteria) this;
        }

        public Criteria andVoteIdBetween(Integer value1, Integer value2) {
            addCriterion("vote_id between", value1, value2, "voteId");
            return (Criteria) this;
        }

        public Criteria andVoteIdNotBetween(Integer value1, Integer value2) {
            addCriterion("vote_id not between", value1, value2, "voteId");
            return (Criteria) this;
        }

        public Criteria andAgreeIsNull() {
            addCriterion("agree is null");
            return (Criteria) this;
        }

        public Criteria andAgreeIsNotNull() {
            addCriterion("agree is not null");
            return (Criteria) this;
        }

        public Criteria andAgreeEqualTo(Integer value) {
            addCriterion("agree =", value, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeNotEqualTo(Integer value) {
            addCriterion("agree <>", value, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeGreaterThan(Integer value) {
            addCriterion("agree >", value, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeGreaterThanOrEqualTo(Integer value) {
            addCriterion("agree >=", value, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeLessThan(Integer value) {
            addCriterion("agree <", value, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeLessThanOrEqualTo(Integer value) {
            addCriterion("agree <=", value, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeIn(List<Integer> values) {
            addCriterion("agree in", values, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeNotIn(List<Integer> values) {
            addCriterion("agree not in", values, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeBetween(Integer value1, Integer value2) {
            addCriterion("agree between", value1, value2, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeNotBetween(Integer value1, Integer value2) {
            addCriterion("agree not between", value1, value2, "agree");
            return (Criteria) this;
        }

        public Criteria andAgainstIsNull() {
            addCriterion("against is null");
            return (Criteria) this;
        }

        public Criteria andAgainstIsNotNull() {
            addCriterion("against is not null");
            return (Criteria) this;
        }

        public Criteria andAgainstEqualTo(Integer value) {
            addCriterion("against =", value, "against");
            return (Criteria) this;
        }

        public Criteria andAgainstNotEqualTo(Integer value) {
            addCriterion("against <>", value, "against");
            return (Criteria) this;
        }

        public Criteria andAgainstGreaterThan(Integer value) {
            addCriterion("against >", value, "against");
            return (Criteria) this;
        }

        public Criteria andAgainstGreaterThanOrEqualTo(Integer value) {
            addCriterion("against >=", value, "against");
            return (Criteria) this;
        }

        public Criteria andAgainstLessThan(Integer value) {
            addCriterion("against <", value, "against");
            return (Criteria) this;
        }

        public Criteria andAgainstLessThanOrEqualTo(Integer value) {
            addCriterion("against <=", value, "against");
            return (Criteria) this;
        }

        public Criteria andAgainstIn(List<Integer> values) {
            addCriterion("against in", values, "against");
            return (Criteria) this;
        }

        public Criteria andAgainstNotIn(List<Integer> values) {
            addCriterion("against not in", values, "against");
            return (Criteria) this;
        }

        public Criteria andAgainstBetween(Integer value1, Integer value2) {
            addCriterion("against between", value1, value2, "against");
            return (Criteria) this;
        }

        public Criteria andAgainstNotBetween(Integer value1, Integer value2) {
            addCriterion("against not between", value1, value2, "against");
            return (Criteria) this;
        }

        public Criteria andAbstainIsNull() {
            addCriterion("abstain is null");
            return (Criteria) this;
        }

        public Criteria andAbstainIsNotNull() {
            addCriterion("abstain is not null");
            return (Criteria) this;
        }

        public Criteria andAbstainEqualTo(Integer value) {
            addCriterion("abstain =", value, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainNotEqualTo(Integer value) {
            addCriterion("abstain <>", value, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainGreaterThan(Integer value) {
            addCriterion("abstain >", value, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainGreaterThanOrEqualTo(Integer value) {
            addCriterion("abstain >=", value, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainLessThan(Integer value) {
            addCriterion("abstain <", value, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainLessThanOrEqualTo(Integer value) {
            addCriterion("abstain <=", value, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainIn(List<Integer> values) {
            addCriterion("abstain in", values, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainNotIn(List<Integer> values) {
            addCriterion("abstain not in", values, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainBetween(Integer value1, Integer value2) {
            addCriterion("abstain between", value1, value2, "abstain");
            return (Criteria) this;
        }

        public Criteria andAbstainNotBetween(Integer value1, Integer value2) {
            addCriterion("abstain not between", value1, value2, "abstain");
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