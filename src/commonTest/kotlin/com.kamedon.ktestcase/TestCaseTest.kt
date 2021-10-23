package com.kamedon.ktestcase

import kotlin.test.*

class TestCaseTest {
    @Test
    fun createStepTest() {
        val step = testCaseStep("create TestStep") {
            verify("create Verify")
        }
        assertEquals(step.title, "create TestStep")
        assertEquals(step.verifies[0].title, "create Verify")
    }

    @Test
    fun addStepTest() {
        val step = TestCaseStep("Add TestStep", listOf())
        val suite = testSuite("TestSuite 1") {
            case("case1") {
                step("step1")
                step(step)
                step("step2")
            }
        }
        assertEquals(suite.cases[0].caseSteps[1].title, step.title)
    }

    @Test
    fun addStepsTest() {
        val steps = listOf(TestCaseStep("Add1 TestStep", listOf()), TestCaseStep("Add2 TestStep", listOf()))
        val suite = testSuite("TestSuite 1") {
            case("case1") {
                step("step1")
                steps(steps)
                step("step2")
            }
        }
        assertEquals(suite.cases[0].caseSteps[1].title, steps[0].title)
        assertEquals(suite.cases[0].caseSteps[2].title, steps[1].title)
    }

}

