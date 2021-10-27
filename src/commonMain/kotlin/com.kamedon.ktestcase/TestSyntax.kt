package com.kamedon.ktestcase

inline fun testSuite(title: String, init: TestSuiteBuilder.() -> Unit): TestSuite {
    return TestSuiteBuilder(title).apply(init).build()
}

inline fun testCase(title: String, init: TestCaseBuilder.() -> Unit): TestCase {
    return TestCaseBuilder(title).apply(init).build()
}

inline fun testCaseStep(title: String, init: TestCaseStepBuilder.() -> Unit = {}): TestCaseStep {
    return TestCaseStepBuilder(title).apply(init).build()
}

inline fun testCaseCondition(init: TestCaseConditionsBuilder.() -> Unit): TestCaseConditions {
    return TestCaseConditionsBuilder().apply(init).build()
}

fun testCaseVerify(title: String): TestCaseVerify {
    return TestCaseVerify(title)
}


inline fun <T> testAttribute(attribute: () -> T): TestAttribute.Attribute<T> {
    return TestAttribute.Attribute(attribute())
}
