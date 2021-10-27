package com.kamedon.ktestcase

data class TestCase(
    val title: String,
    val preConditions: TestCaseConditions = TestCaseConditions.NONE,
    val steps: List<TestCaseStep> = listOf(),
    val verifies: List<TestCaseVerify> = listOf(),
    val postConditions: TestCaseConditions = TestCaseConditions.NONE,
    val attribute: TestAttribute = TestAttribute.NONE
) {
    @Suppress("UNCHECKED_CAST")
    inline fun <T> filterByAttribute(includeNoneAttribute: Boolean = false, f: (T) -> Boolean): TestCase {
        val filterCases = steps.filter {
            when (val caseAttribute = it.attribute) {
                TestAttribute.NONE -> includeNoneAttribute
                is TestAttribute.Attribute<*> -> {
                    val attributeValue = caseAttribute.value as? T ?: return@filter false
                    f(attributeValue)
                }
            }
        }
        return copy(steps = filterCases)
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <T> filterByAttributeElseError(includeNoneAttribute: Boolean = false, f: (T) -> Boolean): TestCase {
        val filterCases = steps.filter {
            when (val caseAttribute = it.attribute) {
                TestAttribute.NONE -> includeNoneAttribute
                is TestAttribute.Attribute<*> -> {
                    val attributeValue = caseAttribute.value as T
                    f(attributeValue)
                }
            }
        }
        return copy(steps = filterCases)
    }

}

class TestCaseBuilder(private val title: String) {

    internal val caseSteps = mutableListOf<TestCaseStep>()
    internal val verifies = mutableListOf<TestCaseVerify>()
    internal var preCondition = TestCaseConditions(listOf())
    internal var postCondition = TestCaseConditions(listOf())
    internal var attribute: TestAttribute = TestAttribute.NONE

    fun build() = TestCase(title, preCondition, caseSteps, verifies, postCondition, attribute)

    fun preCondition(init: TestCaseConditionsBuilder.() -> Unit) {
        preCondition = testCaseCondition(init)
    }

    fun postCondition(init: TestCaseConditionsBuilder.() -> Unit) {
        postCondition = testCaseCondition(init)
    }

    fun verify(title: String) {
        verifies.add(testCaseVerify(title))
    }

    fun step(title: String, init: TestCaseStepBuilder.() -> Unit = {}) {
        testCaseStep(title, init).also { caseSteps.add(it) }
    }

    fun <T> attributeWith(testAttribute: () -> T) {
        attribute = testAttribute(testAttribute)
    }


}

fun TestCaseBuilder.step(step: TestCaseStep) {
    caseSteps.add(step)
}

fun TestCaseBuilder.steps(steps: List<TestCaseStep>) {
    caseSteps.addAll(steps)
}

fun TestCaseBuilder.verify(verify: TestCaseVerify) {
    verifies.add(verify)
}

fun TestCaseBuilder.verifies(caseVerifies: List<TestCaseVerify>) {
    verifies.addAll(caseVerifies)
}

fun TestCaseBuilder.preCondition(condition: TestCaseConditions) {
    preCondition = condition
}

fun TestCaseBuilder.postCondition(condition: TestCaseConditions) {
    postCondition = condition
}

fun <T> TestCaseBuilder.attributeWith(testAttribute: TestAttribute.Attribute<T>) {
    attribute = testAttribute
}



