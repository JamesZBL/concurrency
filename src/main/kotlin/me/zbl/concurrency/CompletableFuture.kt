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

import java.lang.Thread.sleep
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletableFuture.supplyAsync

/**
 * @author ZHENG BAO LE
 * @since 2019-04-30
 */
fun calc(i: Int): Int {
    threadMessage("Starting calculating...")
    sleep(1000)
    threadMessage("Finished calculating.")
    return i * i
}

fun main() {
    val future: CompletableFuture<Int> = supplyAsync { calc(3890) }
    for (i in 0 until 5) {
        threadMessage("I'm doing something else...")
        sleep(1000)
    }
    println("I got the final result: ${future.get()}")
}