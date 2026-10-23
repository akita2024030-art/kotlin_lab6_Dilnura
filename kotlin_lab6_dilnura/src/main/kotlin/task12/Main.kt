package task12
sealed interface AppError { val text: String }
sealed class IoError(override val text: String) : AppError {
    class FileMissing(val path: String) : IoError("File not found: $path")
    class NoPermission(val path: String) : IoError("No permission for $path")
}
data object Timeout : AppError { override val text = "The operation timed out" }
open class CustomError(override val text: String) : AppError
class DiskFull(val freeMb: Int) : IoError("Disk full, only $freeMb MB free")
class QuotaExceeded(val limit: Int) : CustomError("Quota of $limit exceeded")
private fun report(error: AppError) {
    val kind = when (error) { is IoError -> "I/O"; Timeout -> "timeout"; is CustomError -> "custom" }
    println("[$kind] ${error.text}")
}
private fun createError(line: String): AppError? {
    val p = line.trim().split(" ", limit = 2); val arg = p.getOrElse(1) { "" }
    return when (p[0]) {
        "missing" -> IoError.FileMissing(arg); "denied" -> IoError.NoPermission(arg)
        "disk" -> DiskFull(arg.toIntOrNull() ?: 0); "timeout" -> Timeout
        "quota" -> QuotaExceeded(arg.toIntOrNull() ?: 0); "other" -> CustomError(arg); else -> null
    }
}
fun runTask12() {
    println("Describe an error: missing PATH, denied PATH, disk MB, timeout, quota N, other TEXT, or end")
    while (true) { print("> "); val line = readln(); if (line.trim() == "end") break
        val e = createError(line); if (e == null) println("Unknown error type") else report(e)
    }
}

fun main() = runTask12()
