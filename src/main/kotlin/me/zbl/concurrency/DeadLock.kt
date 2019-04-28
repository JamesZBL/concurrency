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

/**
 * @author ZHENG BAO LE
 * @since 2019-04-29
 */
class Friend(private val name: String) {
    @Synchronized
    fun bow(friend: Friend) {
        println("$name: ${friend.name} has bowed to me.")
        friend.bowBack(this)
    }

    @Synchronized
    fun bowBack(friend: Friend) {
        println("$name: ${friend.name} has bowed back to me.")
    }
}

fun main() {
    val james = Friend("James")
    val tom = Friend("Tom")
    val a = Thread { james.bow(tom) }
    val b = Thread { tom.bow(james) }
    a.start()
    b.start()
}