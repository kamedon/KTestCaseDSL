package com.kamedon.ktestcase

data class TestCase(
    val title: String,
    val preConditions: TestCaseConditions,
    val caseSteps: List<TestCaseStep>,
    val verifies: List<TestCaseVerify>,
    val postConditions: TestCaseConditions
)

class TestCaseBuilder(private val title: String) {

    private val caseSteps = mutableListOf<TestCaseStep>()
    private val verifies = mutableListOf<TestCaseVerify>()
    private var preCondition = TestCaseConditions(listOf())
    private var postCondition = TestCaseConditions(listOf())

    fun build() = TestCase(title, preCondition, caseSteps, verifies, postCondition)

    fun preCondition(init: TestCaseConditionsBuilder.() -> Unit) {
        preCondition = TestCaseConditionsBuilder().apply(init).build()
    }

    fun postCondition(init: TestCaseConditionsBuilder.() -> Unit) {
        postCondition = TestCaseConditionsBuilder().apply(init).build()
    }

    fun verify(title: String) {
        verifies.add(TestCaseVerify(title))
    }

    fun step(title: String, init: TestCaseStepBuilder.() -> Unit = {}) {
        TestCaseStepBuilder(title).apply(init).build().also { caseSteps.add(it) }
    }
}




