package com.kamedon.ktestcase

data class TestCase(
    val title: String,
    val preConditions: TestCaseConditions,
    val caseSteps: List<TestCaseStep>,
    val verifies: List<TestCaseVerify>,
    val postConditions: TestCaseConditions
)

class TestCaseBuilder(internal val title: String) {

    internal val caseSteps = mutableListOf<TestCaseStep>()
    internal val verifies = mutableListOf<TestCaseVerify>()
    private var preCondition = TestCaseConditions(listOf())
    private var postCondition = TestCaseConditions(listOf())

    fun build() = TestCase(title, preCondition, caseSteps, verifies, postCondition)

    fun preCondition(init: TestCaseConditionsBuilder.() -> Unit) {
        preCondition = TestCaseConditionsBuilder().apply(init).build()
    }

    fun preCondition(condition: TestCaseConditions) {
        preCondition = condition
    }

    fun postCondition(init: TestCaseConditionsBuilder.() -> Unit) {
        postCondition = TestCaseConditionsBuilder().apply(init).build()
    }

    fun postCondition(condition: TestCaseConditions) {
        postCondition = condition
    }

    fun verify(title: String) {
        verifies.add(TestCaseVerify(title))
    }

    fun step(title: String, init: TestCaseStepBuilder.() -> Unit = {}) {
        TestCaseStepBuilder(title).apply(init).build().also { caseSteps.add(it) }
    }


}

fun TestCaseBuilder.step(step: TestCaseStep) {
    caseSteps.add(step)
}

fun TestCaseBuilder.steps(steps: List<TestCaseStep>) {
    caseSteps.addAll(steps)
}

fun TestCaseBuilder.verify(verify: TestCaseVerify) {
    verifies.add(verify)
}

fun TestCaseBuilder.verifies(caseVerifies: List<TestCaseVerify>) {
    verifies.addAll(caseVerifies)
}


