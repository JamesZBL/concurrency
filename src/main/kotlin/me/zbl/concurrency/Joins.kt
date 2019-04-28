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

/**
 * @author ZHENG BAO LE
 * @since 2019-04-28
 */
val runToBeJoined = {
    println("sub thread start")
    sleep(2000)
    println("sub thread finished")
}

fun main() {
    println("main thread started")
    val thread = Thread(runToBeJoined)
    thread.start()
    thread.join()
    println("main thread has finished")
}