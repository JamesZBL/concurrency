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
import java.util.concurrent.Callable
import java.util.concurrent.Executors

/**
 * @author ZHENG BAO LE
 * @since 2019-04-30
 */
class Cooking(
    private val seed: String
) : Callable<String> {
    override fun call(): String {
        threadMessage("I'm dealing with the data.")
        sleep(2000)
        threadMessage("I've finished dealing with the data.")
        return "[-----cooked $seed-----]"
    }
}

fun main() {
    val executor = Executors.newFixedThreadPool(1)
    val future = executor.submit(Cooking("egg"))
    executor.shutdown()
    for (i in 0 until 5) {
        threadMessage("I'm doing something else...")
        sleep(1000)
    }
    threadMessage("Ok! Those work have been done and let's try to get the result of the other work.")
    threadMessage("I got the final result: ${future.get()}")
}