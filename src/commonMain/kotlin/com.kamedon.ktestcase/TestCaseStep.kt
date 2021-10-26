package com.kamedon.ktestcase

data class TestCaseStep(val title: String, val verifies: List<TestCaseVerify>)

class TestCaseStepBuilder(private val action: String) {
    internal val verifies = mutableListOf<TestCaseVerify>()

    fun build() = TestCaseStep(action, verifies)

    fun verify(verify: String) {
        verifies.add(testCaseVerify(verify))
    }

}

fun TestCaseStepBuilder.verify(verify: TestCaseVerify) {
    verifies.add(verify)
}
