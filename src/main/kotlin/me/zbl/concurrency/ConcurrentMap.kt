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

import java.lang.System.currentTimeMillis
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

/**
 * @author ZHENG BAO LE
 * @since 2019-04-30
 */
fun main() {
    val startTime = currentTimeMillis()
    val threadCount = 100
    val map = ConcurrentHashMap<String, String>()
    val executor = Executors.newFixedThreadPool(threadCount)
    val latch = CountDownLatch(10)
    for (i in 0 until threadCount) {
        executor.submit {
            for (j in 0 until 10000) {
                val uuid = UUID.randomUUID().toString()
                map[uuid] = uuid
            }
            latch.countDown()
        }
    }
    executor.shutdown()
    latch.await()
    println("checking data")
    map.forEach { (k, v) ->
        if (k != v) throw IllegalStateException("key doesn't equals value")
    }
    val endTime = currentTimeMillis()
    println("time used: ${endTime - startTime} ms")
}