package com.kamedon.ktestcase

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlin.test.Test
import kotlin.test.assertEquals

class JsonTest {
    @Test
    fun encodeVerifyJson() {
        val verify = testCaseVerify("create TestStep")
        val json = Json.encodeToString(verify)
        assertEquals("""{"title":"create TestStep"}""", json)
    }

    @Test
    fun encodeAttributeNoneJson() {
        val attribute = TestAttribute.NONE
        val json = Json.encodeToString(attribute)
        assertEquals(""""NONE"""", json)
    }

    @Test
    fun decodeAttributeNoneJson() {
        val json = Json.encodeToString(TestAttribute.NONE)
        val d = Json.decodeFromString<TestAttribute.NONE>(json)
        println(d)
    }

    @Test
    fun encodeAttributeMapJson() {
        val attribute = testAttribute { mapOf("tags" to listOf("tag1", "tag2")) }
        val json = Json.encodeToString(attribute)
        assertEquals("""{"tags":["tag1","tag2"]}""", json)
    }

    @ExperimentalSerializationApi
    @Test
    fun decodeAttributeJson() {
        val attribute: TestAttribute = testAttribute { mapOf("tags" to listOf("tag1", "tag2")) }
        val json = Json {
            serializersModule = SerializersModule {
                contextual(TestAttribute::class, TestAttributeSerializer)
            }
        }.encodeToString(attribute)
        assertEquals("""{"tags":["tag1","tag2"]}""", json)
    }

    @Test
    fun encodeAttributeClassJson() {
        val attribute = testAttribute { SampleData("hoge", "piyo") }
        val json = Json.encodeToString(attribute)
        assertEquals("""{"a":"hoge","b":"piyo"}""", json)
    }

    @ExperimentalSerializationApi
    @Test
    fun encodeAttributeJson() {
        val attribute = Json {
            serializersModule = SerializersModule {
                contextual(TestAttribute::class, TestAttributeSerializer)
            }
        }.decodeFromString<TestAttribute>("""{"tags":["tag3","tag4"]}""")
        println(attribute)
    }


    @Test
    fun decodeAttributeStringJson() {
        val attribute =
            Json.decodeFromString<TestAttribute.Attribute<Map<String, List<String>>>>("""{"tags":["tag1","tag2"]}""")
        assertEquals(listOf("tag1", "tag2"), attribute.value["tags"])
    }


    @Test
    fun decodeAttributeClassJson() {
        val attribute =
            Json.decodeFromString<TestAttribute.Attribute<SampleData>>("""{"a":"hoge","b":"piyo"}""")
        assertEquals(SampleData("hoge", "piyo"), attribute.value)
    }

    @ExperimentalSerializationApi
    @Test
    fun encodeSuiteJson() {

        val suite = testSuite("suite") {
            attributeWith {
                mapOf("tags" to listOf("tag1", "tag2"))
            }

            case("case1") {
                preCondition {
                    condition("preCondition")
                }
                step("step1-1") {
                    attributeWith {
                        mapOf("tags" to listOf("tag3", "tag4"))
                    }
                }
                step("step1-2")
                verify("verify1-1")
                verify("verify1=2")
                postCondition {
                    condition("postCondition")
                }
            }
            case("case2") {
                step("step2-1")
            }
        }
        val json = Json {
            serializersModule = SerializersModule {
                contextual(TestAttribute::class, TestAttributeSerializer)
            }
        }.encodeToString(suite)
        assertEquals(
            """{"title":"suite","cases":[{"title":"case1","preConditions":{"conditions":[{"title":"preCondition"}]},"steps":[{"title":"step1-1","attribute":{"tags":["tag3","tag4"]}},{"title":"step1-2"}],"verifies":[{"title":"verify1-1"},{"title":"verify1=2"}],"postConditions":{"conditions":[{"title":"postCondition"}]}},{"title":"case2","steps":[{"title":"step2-1"}]}],"attribute":{"tags":["tag1","tag2"]}}""",
            json
        )
    }

    @Test
    fun decodeSuiteJson() {
        val json =
            """{"title":"suite","cases":[{"title":"case1","preConditions":{"conditions":[{"title":"preCondition"}]},"steps":[{"title":"step1-1","attribute":{"tags":["tag3","tag4"]}},{"title":"step1-2"}],"verifies":[{"title":"verify1-1"},{"title":"verify1=2"}],"postConditions":{"conditions":[{"title":"postCondition"}]}},{"title":"case2","steps":[{"title":"step2-1"}]}],"attribute":{"tags":["tag1","tag2"]}}"""
        val suite = Json {
            serializersModule = SerializersModule {
                contextual(TestAttribute::class, TestAttributeSerializer)
            }
        }.decodeFromString<TestSuite>(json)
        val suiteAttribute = suite.attribute as TestAttribute.Attribute<Map<String, List<String>>>
        assertEquals("suite", suite.title)
        assertEquals("tag1", suiteAttribute.value["tags"]!![0])

    }


}

@Serializable
data class SampleData(val a: String, val b: String)
