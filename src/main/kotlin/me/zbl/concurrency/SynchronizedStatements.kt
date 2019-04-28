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

import java.util.concurrent.CopyOnWriteArrayList

/**
 * @author ZHENG BAO LE
 * @since 2019-04-28
 */
class InstanceWrapper(
    var nameList: MutableList<String>? = CopyOnWriteArrayList(),
    var size: Int = nameList?.size ?: 0,
    var lastName: String? = "not initialized yet"
) {
    fun addName(name: String): Unit {
        synchronized(this) {
            lastName = name
            size++
        }
        nameList?.add(name)
    }
}

fun main() {
    val times = 1_000
    val wrapper = InstanceWrapper()
    val addNameA = {
        for (i in 0 until times) {
            wrapper.addName(i.toString())
        }
    }
    val addNameB = {
        for (i in 0 until times) {
            wrapper.addName(i.toString())
        }
    }
    val a = Thread(addNameA)
    val b = Thread(addNameB)
    a.start()
    b.start()
    a.join()
    b.join()
    print(
        """wrapper size: ${wrapper.size},
                |wrapper list size: ${wrapper.nameList?.size},
                |wrapper last name: ${wrapper.lastName}""".trimMargin()
    )
}