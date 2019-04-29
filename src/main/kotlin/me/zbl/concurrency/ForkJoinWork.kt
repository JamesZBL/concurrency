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

import java.util.concurrent.ForkJoinPool
import java.util.concurrent.RecursiveAction

/**
 * @author ZHENG BAO LE
 * @since 2019-04-29
 */
class ForkAverage(
    private var source: Array<Int>,
    private var start: Int,
    private var length: Int,
    private var destination: MutableList<Double>
) : RecursiveAction() {

    private val threshold = 10_000_000

    private fun computeDirectly() {
        val average = source.sliceArray(start until start + threshold).average()
        destination.add(average)
        println("start: $start, compute result : $average")
    }

    override fun compute() {
        if (length <= threshold) {
            computeDirectly()
            return
        }
        invokeAll(
            ForkAverage(source, start, threshold, destination),
            ForkAverage(source, start + threshold, length - threshold, destination)
        )
    }
}

fun main() {
    val pool = ForkJoinPool()
    val source = Array(100_000_000) { java.util.Random().nextInt(100) }
    val destination = mutableListOf<Double>()
    val action = ForkAverage(source, 0, source.size, destination)
    pool.invoke(action)
}