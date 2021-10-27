package com.kamedon.ktestcase

data class TestCaseCondition(val title: String)
data class TestCaseConditions(val conditions: List<TestCaseCondition>) {
    companion object {
        val NONE = TestCaseConditions(listOf())
    }

}

class TestCaseConditionsBuilder {
    internal val conditions = mutableListOf<TestCaseCondition>()

    fun condition(title: String) {
        conditions.add(TestCaseCondition(title))
    }

    fun build() = TestCaseConditions(conditions)
}

fun TestCaseConditionsBuilder.condition(condition: TestCaseCondition) {
    conditions.add(condition)
}

