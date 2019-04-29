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
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

/**
 * @author ZHENG BAO LE
 * @since 2019-04-29
 */
class SafeFriend(private val name: String) {

    var lock = ReentrantLock()

    fun bow(friend: SafeFriend) {
        if (beforeBowing(friend)) {
            try {
                println("$name: ${friend.name} has bowed to me")
                friend.bowBack(this)
            } finally {
                lock.unlock()
                friend.lock.unlock()
            }
        } else {
            println("$name: ${friend.name} started to bow to me, but saw I was ready bowing to him")
        }
    }

    private fun beforeBowing(friend: SafeFriend): Boolean {
        val myLock = lock.tryLock()
        val yourLock = friend.lock.tryLock()
        if (!(myLock && yourLock)) {
            if (myLock) lock.unlock()
            if (yourLock) friend.lock.unlock()
        }
        return myLock && yourLock
    }

    private fun bowBack(friend: SafeFriend) {
        println("$name: ${friend.name} has bowed back to me")
    }
}

class BowLoop(
    private val bowee: SafeFriend,
    private val bower: SafeFriend
) : () -> Unit {
    override fun invoke() {
        val times = 10
        for (i in 0 until times) {
            bowee.bow(bower)
            sleep(2000)
        }
    }
}

fun main() {

    val james = SafeFriend("James")
    val tom = SafeFriend("Tom")
    val bowLoop = BowLoop(james, tom)
    thread(block = bowLoop)
}