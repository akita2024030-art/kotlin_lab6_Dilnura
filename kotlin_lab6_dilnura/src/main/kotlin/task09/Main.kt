package task09
enum class Severity { MINOR, MAJOR, CRITICAL, BLOCKER }
sealed class Incident(val severity: Severity) {
    class FileProblem(val fileName: String) : Incident(Severity.MAJOR)
    class DatabaseDown(val database: String) : Incident(Severity.CRITICAL)
    class SlowPage(val url: String) : Incident(Severity.MINOR)
    data object Crash : Incident(Severity.CRITICAL)
    data object OutOfMemory : Incident(Severity.BLOCKER)
}
private fun describe(i: Incident): String = when (i) {
    is Incident.FileProblem -> "File ${i.fileName} cannot be read"
    is Incident.DatabaseDown -> "Database ${i.database} is down"
    is Incident.SlowPage -> "Page ${i.url} is slow"
    Incident.Crash -> "The application crashed"
    Incident.OutOfMemory -> "The application ran out of memory"
}
fun runTask09() {
    val log = mutableListOf<Incident>()
    println("Report: file NAME, db NAME, slow URL, crash, oom, or end")
    while (true) {
        print("> "); val parts = readln().trim().split(" ", limit = 2); val arg = parts.getOrElse(1) { "" }
        when (parts[0]) {
            "file" -> log.add(Incident.FileProblem(arg))
            "db" -> log.add(Incident.DatabaseDown(arg))
            "slow" -> log.add(Incident.SlowPage(arg))
            "crash" -> log.add(Incident.Crash)
            "oom" -> log.add(Incident.OutOfMemory)
            "end" -> break
            else -> println("Unknown report")
        }
    }
    for (i in log.sortedByDescending { it.severity }) {
        println("[${i.severity}] ${describe(i)}")
        if (i.severity >= Severity.CRITICAL) println("ALERT")
    }
    println("Critical incidents: ${log.count { it.severity >= Severity.CRITICAL }} of ${log.size}")
}

fun main() = runTask09()
