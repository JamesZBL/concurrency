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
class ImmutableRGB(
    private var red: Int = 0,
    private var green: Int = 0,
    private var blue: Int = 0,
    val name: String = "Black"
) {

    init {
        check(red, green, blue)
        println("red: $red, green: $green, blue: $blue")
    }

    private fun check(red: Int, green: Int, blue: Int) {
        checkColor(red)
        checkColor(green)
        checkColor(blue)
    }

    private fun checkColor(value: Int) {
        if (value !in 0..255)
            throw IllegalArgumentException("Invalid color")
    }

    fun invert() = ImmutableRGB(
        red = 255 - red,
        green = 255 - green,
        blue = 255 - blue,
        name = "Inverse of $name"
    )


    fun getColor(): Int = (red shl 16) xor (green shl 8) xor blue
}

fun main() {
    var color = ImmutableRGB(212, 66, 133, "Customized")
    println(
        "color in int value: ${color.getColor()}, name: ${color.name}"
    )
    color = color.invert()
    println(
        "color in int value: ${color.getColor()}, name: ${color.name}"
    )
}