package com.kamedon.ktestcase


data class TestSuite(val title: String, val cases: List<TestCase>)

class TestSuiteBuilder(private val title: String) {
    private val cases = mutableListOf<TestCase>()

    fun build() = TestSuite(title, cases)

    fun case(title: String, init: TestCaseBuilder.() -> Unit) {
        testCase(title, init).also { cases.add(it) }
    }


}
