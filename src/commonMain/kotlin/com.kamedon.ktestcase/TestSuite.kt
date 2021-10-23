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
