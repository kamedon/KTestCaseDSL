package com.kamedon.ktestcase

import kotlin.test.*

class TestCaseTest {
    @Test
    fun testProxy() {
        val suite = suite("TestSuite 1") {
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
        assertEquals(suite.title, "TestSuite 1")
        assertEquals(suite.cases[0].title, "case1")
        assertEquals(suite.cases[0].caseSteps[1].title, "step2-2")
        assertEquals(suite.cases[0].caseSteps[2].verifies[1].title, "verify1-3-2")
        assertEquals(suite.cases[1].caseSteps[0].verifies[0].title, "verify2-1-1")
    }

}
