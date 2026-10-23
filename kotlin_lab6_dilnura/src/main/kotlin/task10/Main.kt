package task10
sealed interface PlayerCommand
enum class Basic : PlayerCommand { PLAY, PAUSE, STOP, MUTE }
data class Seek(val seconds: Int) : PlayerCommand
data class Volume(val level: Int) : PlayerCommand
private fun parse(line: String): PlayerCommand? {
    val p = line.trim().lowercase().split(Regex("\\s+")); val n = p.getOrNull(1)?.toIntOrNull()
    return when (p[0]) {
        "play" -> Basic.PLAY; "pause" -> Basic.PAUSE; "stop" -> Basic.STOP; "mute" -> Basic.MUTE
        "seek" -> n?.takeIf { it >= 0 }?.let { Seek(it) }
        "volume" -> n?.takeIf { it in 0..100 }?.let { Volume(it) }
        else -> null
    }
}
private class Player {
    private var position = 0; private var volume = 50
    fun handle(c: PlayerCommand) = when (c) {
        Basic.PLAY -> println("Playing from ${position}s")
        Basic.PAUSE -> println("Paused at ${position}s")
        Basic.STOP -> { position = 0; println("Stopped") }
        Basic.MUTE -> println("Muted")
        is Seek -> { position = c.seconds; println("Jumped to ${position}s") }
        is Volume -> { volume = c.level; println("Volume is now $volume%") }
    }
}
fun runTask10() {
    val player = Player()
    println("Commands: play, pause, stop, mute, seek N, volume N (0-100), quit")
    while (true) { print("> "); val line = readln(); if (line.trim() == "quit") break
        val c = parse(line); if (c == null) println("Unknown command") else player.handle(c)
    }
}

fun main() = runTask10()
