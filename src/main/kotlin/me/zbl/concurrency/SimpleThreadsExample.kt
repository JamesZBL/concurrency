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
import java.lang.Thread.currentThread
import java.lang.Thread.sleep

/**
 * @author ZHENG BAO LE
 * @since 2019-04-28
 */
const val patience = 10 * 1000

val threadMessage = { msg: String ->
    println("${currentThread().name} -> [ $msg ]")
}

val messageLoop = {
    val importantInfo = listOf(
        "Mares eat oats",
        "Does eat oats",
        "Little lambs eat ivy",
        "A kid will eat ivy too"
    )
    try {
        for (i in importantInfo) {
            threadMessage(i)
            sleep(4000)
        }
    } catch (e: InterruptedException) {
        threadMessage("I wasn't done")
    }
}

fun main() {
    threadMessage("Starting message loop thread")
    val startTime: Long = currentTimeMillis()
    val thread = Thread(messageLoop)
    thread.start()
    threadMessage("Waiting for message loop thread to finish")
    while (thread.isAlive) {
        threadMessage("Still waiting...")
        thread.join(1000)
        if (patience < currentTimeMillis() - startTime && thread.isAlive) {
            threadMessage("I'm tired.")
            thread.interrupt()
            thread.join()
        }
    }
    threadMessage("Finally")
}