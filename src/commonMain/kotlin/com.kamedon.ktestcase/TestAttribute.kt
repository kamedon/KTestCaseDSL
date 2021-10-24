package com.kamedon.ktestcase

sealed class TestAttribute {
    object NONE : TestAttribute()
    class Attribute<out T>(val value: T) : TestAttribute()
}