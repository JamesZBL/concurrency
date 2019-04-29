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
import java.util.concurrent.Executors.newSingleThreadExecutor
import java.util.concurrent.TimeUnit

/**
 * @author ZHENG BAO LE
 * @since 2019-04-29
 */
fun main() {
    val service = newSingleThreadExecutor()
    val call: () -> String = {
        sleep(1000)
        "I'm the result"
    }
    val future = service.submit(call)
    service.shutdown()
    val get = future.get(1001, TimeUnit.MILLISECONDS)
    println("get value: $get")
}