package lab6

import task01.runTask01
import task02.runTask02
import task03.runTask03
import task04.runTask04
import task05.runTask05
import task06.runTask06
import task07.runTask07
import task08.runTask08
import task09.runTask09
import task10.runTask10
import task11.runTask11
import task12.runTask12
import task13.runTask13
import task14.runTask14
import task15.runTask15

fun main() {
    val tasks = mapOf<Int, () -> Unit>(
        1 to ::runTask01, 2 to ::runTask02, 3 to ::runTask03,
        4 to ::runTask04, 5 to ::runTask05, 6 to ::runTask06,
        7 to ::runTask07, 8 to ::runTask08, 9 to ::runTask09,
        10 to ::runTask10, 11 to ::runTask11, 12 to ::runTask12,
        13 to ::runTask13, 14 to ::runTask14, 15 to ::runTask15
    )
    while (true) {
        println("\n=== Kotlin Lab 6: Special Classes ===")
        println("Choose task 1-15, or 0 to exit:")
        print("> ")
        when (val choice = readlnOrNull()?.trim()?.toIntOrNull()) {
            0, null -> return
            in 1..15 -> tasks[choice]?.invoke()
            else -> println("Please enter a number from 0 to 15.")
        }
    }
}
