package com.kamedon.ktestcase

data class TestCaseCondition(val title: String)
data class TestCaseConditions(val conditions: List<TestCaseCondition>)

class TestCaseConditionsBuilder() {
    private val conditions = mutableListOf<TestCaseCondition>()

    fun condition(title: String) {
        conditions.add(TestCaseCondition(title))
    }

    fun build() = TestCaseConditions(conditions)
}

