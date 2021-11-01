package com.kamedon.ktestcase

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

sealed class TestAttribute {
    @Serializable(with = TestAttributeNoneSerializer::class)
    object NONE : TestAttribute()

    @Serializable(with = TestAttributeAttributeSerializer::class)
    class Attribute<T>(val value: T) : TestAttribute()
}

object TestAttributeNoneSerializer : KSerializer<TestAttribute.NONE> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Attribute", PrimitiveKind.STRING)

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: TestAttribute.NONE) {
        encoder.encodeString("NONE")
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun deserialize(decoder: Decoder): TestAttribute.NONE {
        val decodeString = decoder.decodeString()
        if (decodeString == "NONE") {
            return TestAttribute.NONE
        }
        error("can not decode TestAttribute.NONE [was: $decodeString]")
    }
}

class TestAttributeAttributeSerializer<T>(val dataSerializer: KSerializer<T>) :
    KSerializer<TestAttribute.Attribute<T>> {
    override val descriptor: SerialDescriptor = dataSerializer.descriptor
    override fun serialize(encoder: Encoder, value: TestAttribute.Attribute<T>) =
        dataSerializer.serialize(encoder, value.value)

    override fun deserialize(decoder: Decoder) = TestAttribute.Attribute(dataSerializer.deserialize(decoder))
}

@ExperimentalSerializationApi
@Serializer(forClass = TestAttribute::class)
object TestAttributeSerializer {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("TestAttribute")

    override fun serialize(encoder: Encoder, value: TestAttribute) {
        when (value) {
            is TestAttribute.NONE -> encoder.encodeSerializableValue(serializer(), value)
            is TestAttribute.Attribute<*> -> {
                (value as? TestAttribute.Attribute<Map<String, List<String>>>)?.let {
                    encoder.encodeSerializableValue(TestAttributeAttributeSerializer(serializer()), it)
                }
            }
        }
    }

    override fun deserialize(decoder: Decoder): TestAttribute {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        require(element is JsonObject)
        if (element.keys.isEmpty()) {
            return TestAttribute.NONE
        }
        val valueSerializer = serializer<Map<String, List<String>>>()
        val value = decoder.json.decodeFromJsonElement(valueSerializer, element)
        return TestAttribute.Attribute(value)
    }


}

