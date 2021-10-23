package com.kamedon.ktestcase

import kotlin.test.*

class TestSuiteTest {
    private val suite = suite("TestSuite 1") {
        case("case1") {
            preCondition {
                condition("pre-condition-1")
            }
            step("step1-1")
            step("step2-2") {
                verify("verify1-2-1")
            }
            step("step1-3") {
                verify("verify1-3-1")
                verify("verify1-3-2")
            }
            step("step1-4") {
                verify("verify1-4-1")
            }

            verify("verify-1")
            verify("verify-2")

            postCondition {
                condition("post-condition-1")
                condition("post-condition-2")
            }
        }
        case("case2") {
            step("step2-1") {
                verify("verify2-1-1")
            }
        }
    }

    @Test
    fun suiteTest() {
        assertEquals(suite.title, "TestSuite 1")
        assertEquals(suite.cases[0].title, "case1")
        assertEquals(suite.cases[0].preConditions.conditions[0].title, "pre-condition-1")
        assertEquals(suite.cases[0].caseSteps[1].title, "step2-2")
        assertEquals(suite.cases[0].caseSteps[2].verifies[1].title, "verify1-3-2")
        assertEquals(suite.cases[0].verifies[1].title, "verify-2")
        assertEquals(suite.cases[0].postConditions.conditions[1].title, "post-condition-2")
        assertEquals(suite.cases[1].caseSteps[0].verifies[0].title, "verify2-1-1")
    }

    @Test
    fun outputToMarkdownTest() {
        val markdown = suite.markdown()
        assertEquals(
            markdown, """## case1
### PreCondition
- pre-condition-1

### Test Step
1. step1-1

2. step2-2
    - [ ] verify1-2-1

3. step1-3
    - [ ] verify1-3-1
    - [ ] verify1-3-2

4. step1-4
    - [ ] verify1-4-1
### Expected Result
- [ ] verify-1
- [ ] verify-2
### PostCondition
- post-condition-1
- post-condition-2

## case2
### PreCondition

### Test Step
1. step2-1
    - [ ] verify2-1-1
### Expected Result
### PostCondition
"""
        )

    }
}


fun TestSuite.markdown(): String {
    fun TestCase.title() = "## ${title}\n"

    fun preConditionTitle() = "### PreCondition\n"
    fun postConditionTitle() = "### PostCondition\n"
    fun verifyTitle() = "### Expected Result\n"
    fun stepTitle() = "### Test Step\n"

    fun TestCaseCondition.title() = "- $title\n"

    fun TestCaseStep.title(index: Int) = "${index}. ${title}\n"
    fun TestCaseVerify.title() = "- [ ] $title\n"

    fun TestCaseStep.markdown(index: Int): String {
        return title(index) + verifies.joinToString("") { caseStepVerify ->
            "    ${caseStepVerify.title()}"
        }
    }

    fun TestCase.markdown(): String {

        val preConditionMarkdown = preConditionTitle() + preConditions.conditions.joinToString("") { it.title() } + "\n"
        val postConditionMarkdown =
            postConditionTitle() + postConditions.conditions.joinToString("") { it.title() }
        val verifyMarkdown = verifyTitle() + verifies.joinToString("") { it.title() }

        val stepMarkdown =
            stepTitle() + caseSteps.mapIndexed { index, caseStep ->
                caseStep.markdown(index + 1)
            }.joinToString("\n")

        return title() + preConditionMarkdown + stepMarkdown + verifyMarkdown + postConditionMarkdown
    }

    return cases.joinToString("\n") { it.markdown() }
}
