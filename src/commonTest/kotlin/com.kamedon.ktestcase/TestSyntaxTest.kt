package com.kamedon.ktestcase

import kotlin.test.*

class TestSyntaxKtTest {

    @Test
    fun testSuiteTest() {
        val suite = testSuite("suite") {
            case("case1") {}
            case("case2") {}
        }
        assertEquals("suite", suite.title)
        assertEquals(2, suite.cases.size)
        assertEquals("case2", suite.cases[1].title)
    }

    @Test
    fun testCaseTest() {
        val case = testCase("case") {
            preCondition {
                condition("preCondition1")
                condition("preCondition2")
            }
            step("step1")
            step("step2") {}
            postCondition {
                condition("postCondition1")
                condition("postCondition2")
            }
        }
        assertEquals("case", case.title)
        assertEquals(2, case.caseSteps.size)
        assertEquals("step2", case.caseSteps[1].title)
        assertEquals("preCondition1", case.preConditions.conditions[0].title)
        assertEquals(2, case.preConditions.conditions.size)
        assertEquals("postCondition2", case.postConditions.conditions[1].title)
        assertEquals(2, case.postConditions.conditions.size)
    }

    @Test
    fun testCaseStepTest() {
        val step = testCaseStep("step") {
            verify("verify1")
            verify("verify2")
        }
        assertEquals("step", step.title)
        assertEquals(2, step.verifies.size)
        assertEquals("verify2", step.verifies[1].title)
    }


    @Test
    fun testCaseConditionTest() {
        val condition = testCaseCondition {
            condition("condition1")
            condition("condition2")
        }

        assertEquals(2, condition.conditions.size)
        assertEquals("condition2", condition.conditions[1].title)

    }

    @Test
    fun testCaseVerifyTest() {
        val verify = testCaseVerify("verify")
        assertEquals("verify", verify.title)
    }
}