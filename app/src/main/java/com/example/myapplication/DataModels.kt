package com.example.myapplication

import com.example.myapplication.common.getInstanceId
import com.example.myapplication.common.log

data class Test0(
    val str: String
)

class Test1(
    val test0: Test0
)

class Test2(
    private val test1: Test1
) {
    fun print() {
        log("this-> ${this.getInstanceId()} -> Test1: ${test1.getInstanceId()}")
    }
}