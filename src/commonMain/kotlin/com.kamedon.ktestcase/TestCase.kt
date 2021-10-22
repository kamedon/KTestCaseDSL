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

data class TestCase(val title: String, val caseSteps: List<TestCaseStep>)
class TestCaseBuilder(private val title: String) {

    private val caseSteps = mutableListOf<TestCaseStep>()

    fun build() = TestCase(title, caseSteps)

    fun step(title: String, init: TestCaseStepBuilder.() -> Unit = {}) {
        TestCaseStepBuilder(title).apply(init).build().also { caseSteps.add(it) }
    }
}

data class TestCaseStep(val title: String, val verifies: List<TestCaseStepVerify>)

class TestCaseStepBuilder(private val action: String) {
    private val verifies = mutableListOf<TestCaseStepVerify>()

    fun build() = TestCaseStep(action, verifies)

    fun verify(verify: String) {
        verifies.add(TestCaseStepVerify(verify))
    }

}

data class TestCaseStepVerify(val title: String)

