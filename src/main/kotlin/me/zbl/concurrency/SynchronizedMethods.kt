/*
 * Copyright 2019 ZHENG BAO LE
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.zbl.concurrency

import kotlin.concurrent.thread

/**
 * @author ZHENG BAO LE
 * @since 2019-04-28
 */
class SynchronizedCounter(var c: Int = 0) {
    @Synchronized fun increment() {
        c++
    }

    @Synchronized fun decrement() {
        c--
    }
}

fun main() {
    val times = 100_000
    var notZero = false
    for (i in 0 until 100) {
        val interference = SynchronizedCounter(0)
        val increment = {
            for (i in 0 until times) {
                interference.increment()
            }
        }
        val decrement = {
            for (i in 0 until times) {
                interference.decrement()
            }
        }
        val a = thread(block = increment)
        val b = thread(block = decrement)
        a.join()
        b.join()
        val final = interference.c
        if (0 != final) {
            println("final result: ${interference.c}")
            notZero = true
        }
    }
    if (!notZero) {
        println("All results are zero")
    }
}