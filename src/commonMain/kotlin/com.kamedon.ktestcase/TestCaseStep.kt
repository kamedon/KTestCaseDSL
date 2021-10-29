package com.kamedon.ktestcase

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class TestCaseStep(
    val title: String,
    @Contextual val attribute: TestAttribute = TestAttribute.NONE,
    val verifies: List<TestCaseVerify> = listOf()
)

class TestCaseStepBuilder(private val action: String) {
    val verifies = mutableListOf<TestCaseVerify>()
    var attribute: TestAttribute = TestAttribute.NONE

    fun build() = TestCaseStep(action, attribute, verifies)

    fun verify(verify: String) {
        verifies.add(testCaseVerify(verify))
    }

    inline fun <T> attributeWith(testAttribute: () -> T) {
        attribute = testAttribute(testAttribute)
    }
}

fun TestCaseStepBuilder.verify(verify: TestCaseVerify) {
    verifies.add(verify)
}

fun TestCaseStepBuilder.attribute(testAttribute: TestAttribute) {
    attribute = testAttribute
}
