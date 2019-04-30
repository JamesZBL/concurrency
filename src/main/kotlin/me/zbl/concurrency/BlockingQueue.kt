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
import java.util.concurrent.Executors
import java.util.concurrent.PriorityBlockingQueue
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.random.Random

/**
 * @author ZHENG BAO LE
 * @since 2019-04-29
 */
fun main() {
    val breadQueue = PriorityBlockingQueue<Int>()
    val breadCount = 20
    thread {
        for (i in 0 until breadCount) {
            breadQueue.add(Random.nextInt(80, 100))
            sleep(1000)
        }
    }
    val consumer = {
        var bread: Int?
        do {
            bread = breadQueue.poll(2, TimeUnit.SECONDS)
            if (null != bread) threadMessage("I got a bread which is about $bread grams weight")
        } while (null != bread)
        threadMessage("I got that breads has been sold out")
    }
    val executor = Executors.newFixedThreadPool(10)
    for (i in 0 until 10) {
        executor.submit(consumer)
    }
    executor.shutdown()
}