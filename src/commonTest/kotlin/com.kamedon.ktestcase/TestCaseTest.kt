package com.kamedon.ktestcase

import kotlin.test.*

class TestCaseTest {
    private val suite = suite("TestSuite 1") {
        case("case1") {
            step("step1-1")
            step("step2-2") {
                verify("verify1-2-1")
            }
            step("step3") {
                verify("verify1-3-1")
                verify("verify1-3-2")
            }
            step("step1-4") {
                verify("verify1-4-1")
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
        assertEquals(suite.cases[0].caseSteps[1].title, "step2-2")
        assertEquals(suite.cases[0].caseSteps[2].verifies[1].title, "verify1-3-2")
        assertEquals(suite.cases[1].caseSteps[0].verifies[0].title, "verify2-1-1")
    }

    @Test
    fun outputToMarkdownTest() {
        val markdown = suite.markdown()
        assertEquals(markdown, """
### case1
1. step1-1

2. step2-2
    - [ ]  verify1-2-1
3. step3
    - [ ]  verify1-3-1
    - [ ]  verify1-3-2
4. step1-4
    - [ ]  verify1-4-1
### case2
1. step2-1
    - [ ]  verify2-1-1
""".trim())

    }
}


fun TestSuite.markdown(): String {
    fun TestCase.title() = "### ${title}\n"
    fun TestCaseStep.title(index: Int) = "${index}. ${title}\n"
    fun TestCaseStepVerify.title() = "    - [ ]  $title"

    val out = cases.map { case ->
        case.title() + case.caseSteps.mapIndexed { index, caseStep ->
            caseStep.title(index + 1) + caseStep.verifies.joinToString("\n") { caseStepVerify ->
                caseStepVerify.title()
            }
        }.joinToString("\n")
    }.joinToString("\n")

    return out
}
