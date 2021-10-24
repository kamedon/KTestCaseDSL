package com.kamedon.ktestcase


data class TestSuite(val title: String, val attribute: TestAttribute = TestAttribute.NONE, val cases: List<TestCase>)

class TestSuiteBuilder(private val title: String) {
    internal val cases = mutableListOf<TestCase>()
    internal var attribute: TestAttribute = TestAttribute.NONE

    fun build() = TestSuite(title, attribute, cases)

    fun case(title: String, init: TestCaseBuilder.() -> Unit) {
        testCase(title, init).also { cases.add(it) }
    }

    fun <T> attributeWith(testAttribute: () -> T) {
        attribute = testAttribute(testAttribute)
    }

}

fun <T> TestSuiteBuilder.attributeWith(testAttribute: TestAttribute.Attribute<T>) {
    attribute = testAttribute
}

fun TestSuiteBuilder.case(case: TestCase) {
    cases.add(case)
}

fun TestSuiteBuilder.cases(testCases: List<TestCase>) {
    cases.addAll(testCases)
}
