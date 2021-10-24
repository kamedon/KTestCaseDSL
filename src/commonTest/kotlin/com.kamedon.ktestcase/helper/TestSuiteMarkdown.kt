package com.kamedon.ktestcase.helper

import com.kamedon.ktestcase.*

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

        val attributeMarkdown = when (val attr = attribute) {
            TestAttribute.NONE -> ""
            is TestAttribute.Attribute<*> -> {
                "### Attribute \n" + (attr.value as? Map<String, String>)?.let {
                    it.entries.joinToString("") { entry ->
                        "- ${entry.key}: ${entry.value}\n"
                    }
                }
            }
        }

        val stepMarkdown =
            stepTitle() + caseSteps.mapIndexed { index, caseStep ->
                caseStep.markdown(index + 1)
            }.joinToString("\n")

        return title() + attributeMarkdown + preConditionMarkdown + stepMarkdown + verifyMarkdown + postConditionMarkdown
    }

    return cases.joinToString("\n") { it.markdown() }
}
