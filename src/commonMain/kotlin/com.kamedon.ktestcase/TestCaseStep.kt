package com.kamedon.ktestcase

data class TestCaseStep(
    val title: String,
    val attribute: TestAttribute = TestAttribute.NONE,
    val verifies: List<TestCaseVerify> = listOf()
)

class TestCaseStepBuilder(private val action: String) {
    internal val verifies = mutableListOf<TestCaseVerify>()
    internal var attribute: TestAttribute = TestAttribute.NONE

    fun build() = TestCaseStep(action, attribute, verifies)

    fun verify(verify: String) {
        verifies.add(testCaseVerify(verify))
    }

    fun <T> attributeWith(testAttribute: () -> T) {
        attribute = testAttribute(testAttribute)
    }

}

fun TestCaseStepBuilder.verify(verify: TestCaseVerify) {
    verifies.add(verify)
}

fun TestCaseStepBuilder.attribute(testAttribute: TestAttribute) {
    attribute = testAttribute
}
