package com.kamedon.ktestcase

fun suite(title: String, init: TestSuiteBuilder.() -> Unit): TestSuite {
    return TestSuiteBuilder(title).apply(init).build()
}

data class TestSuite(val title: String, val cases: List<TestCase>)

class TestSuiteBuilder(private val title: String) {
    private val cases = mutableListOf<TestCase>()

    fun build() = TestSuite(title, cases)

    fun case(title: String, init: TestCaseBuilder.() -> Unit) {
        TestCaseBuilder(title).apply(init).build().also { cases.add(it) }
    }


}

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

data class TestCaseConditions(val conditions: List<TestCaseCondition>)

class TestCaseConditionsBuilder() {
    private val conditions = mutableListOf<TestCaseCondition>()

    fun condition(title: String) {
        conditions.add(TestCaseCondition(title))
    }

    fun build() = TestCaseConditions(conditions)
}

data class TestCaseCondition(val title: String)


data class TestCaseStep(val title: String, val verifies: List<TestCaseVerify>)

class TestCaseStepBuilder(private val action: String) {
    private val verifies = mutableListOf<TestCaseVerify>()

    fun build() = TestCaseStep(action, verifies)

    fun verify(verify: String) {
        verifies.add(TestCaseVerify(verify))
    }

}

data class TestCaseVerify(val title: String)

