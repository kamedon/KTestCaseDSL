package com.kamedon.ktestcase

import com.kamedon.ktestcase.helper.markdown
import kotlin.test.*

class TestSuiteWithAttributeTest {
    private val suite = testSuite("TestSuite 1") {
        attributeWith {
            mapOf(
                "Feature" to "Login"
            )
        }
        case("case1") {
            attributeWith {
                mapOf(
                    "Tags" to "tag1, tag2, tag3",
                    "Priority" to "High"
                )
            }
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
            attributeWith {
                mapOf(
                    "Tags" to "tag1, tag2, tag3",
                    "Priority" to "Low"
                )
            }
            step("step2-1") {
                verify("verify2-1-1")
            }
        }
        case("case3") {
            step("step3-1") {
                verify("verify2-1-1")
            }
        }
    }

    @Test
    fun suiteTest() {
        assertEquals("TestSuite 1", suite.title)
        assertTrue { suite.attribute is TestAttribute.Attribute<*> }
        val suiteAttribute = (suite.attribute as TestAttribute.Attribute<*>).value as Map<*, *>
        assertEquals("Login", suiteAttribute["Feature"])
        assertEquals("case1", suite.cases[0].title)
        assertTrue { suite.cases[0].attribute is TestAttribute.Attribute<*> }
        val testAttribute = (suite.cases[0].attribute as TestAttribute.Attribute<*>).value as Map<*, *>
        assertEquals("tag1, tag2, tag3", testAttribute["Tags"])
        assertEquals(TestAttribute.NONE, suite.cases[2].attribute)
        assertEquals("pre-condition-1", suite.cases[0].preConditions.conditions[0].title)
        assertEquals("step2-2", suite.cases[0].caseSteps[1].title)
        assertEquals("verify1-3-2", suite.cases[0].caseSteps[2].verifies[1].title)
        assertEquals("verify-2", suite.cases[0].verifies[1].title)
        assertEquals("post-condition-2", suite.cases[0].postConditions.conditions[1].title)
        assertEquals("verify2-1-1", suite.cases[1].caseSteps[0].verifies[0].title)
    }

    @Test
    fun outputToMarkdownTest() {
        val markdown = suite.markdown()
        assertEquals(
            """# TestSuite 1
### Attribute 
- Feature: Login
## case1
### Attribute 
- Tags: tag1, tag2, tag3
- Priority: High
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
### Attribute 
- Tags: tag1, tag2, tag3
- Priority: Low
### PreCondition

### Test Step
1. step2-1
    - [ ] verify2-1-1
### Expected Result
### PostCondition

## case3
### PreCondition

### Test Step
1. step3-1
    - [ ] verify2-1-1
### Expected Result
### PostCondition
""", markdown
        )

    }
}