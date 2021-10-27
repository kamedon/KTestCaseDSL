package com.kamedon.ktestcase

sealed class TestAttribute {
    object NONE : TestAttribute()
    class Attribute<T>(val value: T) : TestAttribute()
}