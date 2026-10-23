package task13
sealed class Passenger {
    data class Adult(val name: String) : Passenger()
    data class Child(val name: String, val age: Int) : Passenger()
    data class Senior(val name: String, val age: Int) : Passenger()
}
private fun fare(p: Passenger, baseFare: Int): Int = when (p) {
    is Passenger.Child -> if (p.age < 4) 0 else baseFare / 2
    is Passenger.Senior -> if (p.age > 80) 0 else baseFare * 7 / 10
    is Passenger.Adult -> baseFare
}
fun runTask13() {
    print("Name: "); val name = readln().trim()
    print("Age: "); val age = readln().trim().toIntOrNull()
    if (age == null || age < 0) { println("Invalid age"); return }
    val passenger = when { age >= 60 -> Passenger.Senior(name, age); age >= 18 -> Passenger.Adult(name); else -> Passenger.Child(name, age) }
    println("$passenger pays ${fare(passenger, 2000)} UZS")
}

fun main() = runTask13()
