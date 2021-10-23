package com.kamedon.ktestcase

class TestCaseStep(val title: String, val verifies: List<TestCaseVerify>) {
}

class TestCaseStepBuilder(private val action: String) {
    private val verifies = mutableListOf<TestCaseVerify>()

    fun build() = TestCaseStep(action, verifies)

    fun verify(verify: String) {
        verifies.add(TestCaseVerify(verify))
    }

}
