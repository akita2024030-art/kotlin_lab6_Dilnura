package task11
sealed interface Chargeable { val batteryHours: Int }
sealed interface Wireless { val range: Int }
sealed interface Waterproof
data class Phone(override val batteryHours: Int = 24, override val range: Int = 50) : Chargeable, Wireless
data class Headphones(override val batteryHours: Int = 8, override val range: Int = 10) : Chargeable, Wireless, Waterproof
data class SmartLamp(override val range: Int = 30, override val batteryHours: Int = 4) : Wireless, Chargeable
class Toaster
private fun create(name: String): Any? = when (name.lowercase()) {
    "phone" -> Phone(); "headphones" -> Headphones(); "lamp" -> SmartLamp(); "toaster" -> Toaster(); else -> null
}
private fun chargingNote(device: Chargeable): String = when (device) {
    is Phone -> "Charge the phone every night"
    is Headphones -> "Charge the headphones after 8 hours"
    is SmartLamp -> "Charge the smart lamp"
}
fun runTask11() {
    val devices = mutableListOf<Any>()
    println("Add devices: phone, headphones, lamp, toaster (type 'done' to finish)")
    while (true) {
        print("> "); val name = readln().trim(); if (name == "done") break
        val d = create(name); if (d == null) println("Unknown device") else devices.add(d)
    }
    for (d in devices) {
        val abilities = mutableListOf<String>()
        if (d is Chargeable) { abilities.add("battery ${d.batteryHours} h"); println(chargingNote(d)) }
        if (d is Wireless) abilities.add("wireless ${d.range} m")
        if (d is Waterproof) abilities.add("waterproof")
        println("${d::class.simpleName}: ${if (abilities.isEmpty()) "no special abilities" else abilities.joinToString()}")
    }
    println("Chargeable devices: ${devices.count { it is Chargeable }}")
}

fun main() = runTask11()
