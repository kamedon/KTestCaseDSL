package com.kamedon.ktestcase

import kotlinx.serialization.Serializable

@Serializable
sealed class TestAttribute {
    @Serializable
    object NONE : TestAttribute()

    @Serializable
    class Attribute<T>(val value: T) : TestAttribute()
}