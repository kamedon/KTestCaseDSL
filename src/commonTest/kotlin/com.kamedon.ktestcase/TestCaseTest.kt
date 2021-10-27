package com.kamedon.ktestcase

import kotlin.test.*

class TestCaseTest {
    @Test
    fun createStepTest() {
        val step = testCaseStep("create TestStep") {
            verify("create Verify")
        }
        assertEquals("create TestStep", step.title)
        assertEquals("create Verify", step.verifies[0].title)
    }

    @Test
    fun addStepTest() {
        val step = TestCaseStep("Add TestStep", verifies = listOf())
        val suite = testSuite("TestSuite 1") {
            case("case1") {
                step("step1")
                step(step)
                step("step2")
            }
        }
        assertEquals(step.title, suite.cases[0].steps[1].title)
    }

    @Test
    fun addStepsTest() {
        val steps = listOf(TestCaseStep("Add1 TestStep", verifies = listOf()), TestCaseStep("Add2 TestStep"))
        val suite = testSuite("TestSuite 1") {
            case("case1") {
                step("step1")
                steps(steps)
                step("step2")
            }
        }
        assertEquals(steps[0].title, suite.cases[0].steps[1].title)
        assertEquals(steps[1].title, suite.cases[0].steps[2].title)
    }

}

