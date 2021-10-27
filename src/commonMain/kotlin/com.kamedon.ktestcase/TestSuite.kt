package com.kamedon.ktestcase


data class TestSuite(val title: String, val cases: List<TestCase>, val attribute: TestAttribute = TestAttribute.NONE) {
    @Suppress("UNCHECKED_CAST")
    inline fun <T> filterByAttribute(includeNoneAttribute: Boolean = false, f: (T) -> Boolean): TestSuite {
        val filterCases = cases.filter {
            when (val caseAttribute = it.attribute) {
                TestAttribute.NONE -> includeNoneAttribute
                is TestAttribute.Attribute<*> -> {
                    val attributeValue = caseAttribute.value as? T ?: return@filter false
                    f(attributeValue)
                }
            }
        }
        return copy(cases = filterCases)
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <T> filterByAttributeElseError(includeNoneAttribute: Boolean = false, f: (T) -> Boolean): TestSuite {
        val filterCases = cases.filter {
            when (val caseAttribute = it.attribute) {
                TestAttribute.NONE -> includeNoneAttribute
                is TestAttribute.Attribute<*> -> {
                    val attributeValue = caseAttribute.value as T
                    f(attributeValue)
                }
            }
        }
        return copy(cases = filterCases)
    }
}

class TestSuiteBuilder(private val title: String) {
    val cases = mutableListOf<TestCase>()
    var attribute: TestAttribute = TestAttribute.NONE

    fun build() = TestSuite(title, cases, attribute)

    inline fun case(title: String, init: TestCaseBuilder.() -> Unit) {
        testCase(title, init).also { cases.add(it) }
    }

    inline fun <T> attributeWith(testAttribute: () -> T) {
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
