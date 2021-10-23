package com.kamedon.ktestcase

fun suite(title: String, init: TestSuiteBuilder.() -> Unit): TestSuite {
    return TestSuiteBuilder(title).apply(init).build()
}
