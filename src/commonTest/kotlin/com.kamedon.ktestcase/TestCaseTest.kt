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

    @Test
    fun addVerifyTest() {
        val verify = testCaseVerify("add verify")
        val suite = testSuite("TestSuite 1") {
            case("case1") {
                verify("verify1")
                verify(verify)
                verify("verify2")
            }
        }
        assertEquals(3, suite.cases[0].verifies.size)
        assertEquals(verify, suite.cases[0].verifies[1])
        assertEquals("verify2", suite.cases[0].verifies[2].title)
    }

    @Test
    fun addVerifiesTest() {
        val verifies = listOf(testCaseVerify("add verify1"), testCaseVerify("add verify2"))
        val suite = testSuite("TestSuite 1") {
            case("case1") {
                verify("verify1")
                verifies(verifies)
                verify("verify2")
            }
        }
        assertEquals(4, suite.cases[0].verifies.size)
        assertEquals(verifies[0], suite.cases[0].verifies[1])
        assertEquals("verify2", suite.cases[0].verifies[3].title)
    }

    @Test
    fun setPreCondition() {
        val condition = testCaseCondition {
            condition("condition")
        }
        val suite = testSuite("TestSuite 1") {
            case("case1") {
                preCondition(condition)
            }
        }
        assertEquals(condition, suite.cases[0].preConditions)
    }

    @Test
    fun setPostCondition() {
        val condition = testCaseCondition {
            condition("condition")
        }
        val suite = testSuite("TestSuite 1") {
            case("case1") {
                postCondition(condition)
            }
        }
        assertEquals(condition, suite.cases[0].postConditions)
    }

    @Test
    fun setAttributeCondition() {
        val attribute = testAttribute {
            mapOf("tag" to "Login", "Priority" to "high")
        }
        val suite = testSuite("TestSuite 1") {
            case("case1") {
                attributeWith(attribute)
            }
        }
        assertEquals(attribute, suite.cases[0].attribute)
    }

    @Test
    fun filterByAttribute() {
        val suite = testSuite("TestSuite 1") {
            case("case1") {
                step("step1") {
                    attributeWith {
                        mapOf("Priority" to "High")
                    }
                }
                step("step2")
            }
        }

        val filterCase = suite.cases[0].filterByAttribute<Map<*, *>> {
            it["Priority"] == "High"
        }

        assertEquals(1, filterCase.steps.size)
        assertEquals("step1", filterCase.steps[0].title)
    }

    @Test
    fun filterByAttributeIncludeNone() {
        val suite = testSuite("TestSuite 1") {
            case("case1") {
                step("step1") {
                    attributeWith {
                        mapOf("Priority" to "High")
                    }
                }
                step("step2")
            }
        }

        val filterCase = suite.cases[0].filterByAttribute<Map<*, *>>(true) {
            it["Priority"] == "High"
        }

        assertEquals(2, filterCase.steps.size)
        assertEquals("step1", filterCase.steps[0].title)
        assertEquals("step2", filterCase.steps[1].title)
    }

    @Test
    fun filterByAttributeElseError() {
        val suite = testSuite("TestSuite 1") {
            case("case1") {
                step("step1") {
                    attributeWith {
                        mapOf("Priority" to "High")
                    }
                }
                step("step2")
                step("step3") {
                    attributeWith {
                        "High"
                    }
                }
            }
        }

        val exception = assertFails {
            suite.cases[0].filterByAttributeElseError<Map<*, *>>(true) {
                it["Priority"] == "High"
            }
        }
        assertTrue { exception is ClassCastException }
    }

    @Test
    fun filterByAttributeElseErrorExcludeNone() {
        val suite = testSuite("TestSuite 1") {
            case("case1") {
                step("step1") {
                    attributeWith {
                        mapOf("Priority" to "High")
                    }
                }
                step("step2")
            }
        }

        val filterCase = suite.cases[0].filterByAttributeElseError<Map<*, *>>(false) {
            it["Priority"] == "High"
        }


        assertEquals(1, filterCase.steps.size)
        assertEquals("step1", filterCase.steps[0].title)


    }


    @Test
    fun filterByAttributeElseErrorIncludeNone() {
        val suite = testSuite("TestSuite 1") {
            case("case1") {
                step("step1") {
                    attributeWith {
                        mapOf("Priority" to "High")
                    }
                }
                step("step2")
            }
        }

        val filterCase = suite.cases[0].filterByAttributeElseError<Map<*, *>>(true) {
            it["Priority"] == "High"
        }


        assertEquals(2, filterCase.steps.size)
        assertEquals("step1", filterCase.steps[0].title)
        assertEquals("step2", filterCase.steps[1].title)


    }


}

