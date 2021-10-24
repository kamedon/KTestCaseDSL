package com.kamedon.ktestcase

fun testSuite(title: String, init: TestSuiteBuilder.() -> Unit): TestSuite {
    return TestSuiteBuilder(title).apply(init).build()
}

fun testCase(title: String, init: TestCaseBuilder.() -> Unit): TestCase {
    return TestCaseBuilder(title).apply(init).build()
}

fun testCaseStep(title: String, init: TestCaseStepBuilder.() -> Unit = {}): TestCaseStep {
    return TestCaseStepBuilder(title).apply(init).build()
}

fun testCaseCondition(init: TestCaseConditionsBuilder.() -> Unit): TestCaseConditions {
    return TestCaseConditionsBuilder().apply(init).build()
}

fun testCaseVerify(title: String): TestCaseVerify {
    return TestCaseVerify(title)
}


fun <T> testAttribute(attribute: () -> T): TestAttribute.Attribute<T> {
    return TestAttribute.Attribute(attribute())
}
