package com.kamedon.ktestcase

data class TestCase(
    val title: String,
    val preConditions: TestCaseConditions,
    val caseSteps: List<TestCaseStep>,
    val verifies: List<TestCaseVerify>,
    val postConditions: TestCaseConditions,
    val attribute: TestAttribute = TestAttribute.NONE
)

class TestCaseBuilder(private val title: String) {

    internal val caseSteps = mutableListOf<TestCaseStep>()
    internal val verifies = mutableListOf<TestCaseVerify>()
    internal var preCondition = TestCaseConditions(listOf())
    internal var postCondition = TestCaseConditions(listOf())
    internal var attribute: TestAttribute = TestAttribute.NONE

    fun build() = TestCase(title, preCondition, caseSteps, verifies, postCondition, attribute)

    fun preCondition(init: TestCaseConditionsBuilder.() -> Unit) {
        preCondition = testCaseCondition(init)
    }

    fun postCondition(init: TestCaseConditionsBuilder.() -> Unit) {
        postCondition = testCaseCondition(init)
    }

    fun verify(title: String) {
        verifies.add(testCaseVerify(title))
    }

    fun step(title: String, init: TestCaseStepBuilder.() -> Unit = {}) {
        testCaseStep(title, init).also { caseSteps.add(it) }
    }

    fun <T> attributeWith(testAttribute: () -> T) {
        attribute = testAttribute(testAttribute)
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

fun TestCaseBuilder.preCondition(condition: TestCaseConditions) {
    preCondition = condition
}

fun TestCaseBuilder.postCondition(condition: TestCaseConditions) {
    postCondition = condition
}

fun <T> TestCaseBuilder.attributeWith(testAttribute: TestAttribute.Attribute<T>) {
    attribute = testAttribute
}



