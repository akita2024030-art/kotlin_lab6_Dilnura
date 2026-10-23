package task02
import kotlin.math.pow
enum class Operation(val symbol: String) {
    ADD("+") { override fun apply(a: Double, b: Double) = a + b },
    SUBTRACT("-") { override fun apply(a: Double, b: Double) = a - b },
    MULTIPLY("*") { override fun apply(a: Double, b: Double) = a * b },
    DIVIDE("/") { override fun apply(a: Double, b: Double) = a / b },
    MODULO("%") { override fun apply(a: Double, b: Double) = a % b },
    POWER("^") { override fun apply(a: Double, b: Double) = a.pow(b) };
    abstract fun apply(a: Double, b: Double): Double
    companion object { fun fromSymbol(symbol: String) = entries.firstOrNull { it.symbol == symbol } }
}
fun runTask02() {
    print("Expression (for example 12 * 4): ")
    val parts = readln().trim().split(Regex("\\s+"))
    if (parts.size != 3) { println("Use: number operator number"); return }
    val a = parts[0].toDoubleOrNull(); val b = parts[2].toDoubleOrNull()
    val op = Operation.fromSymbol(parts[1])
    if (a == null || b == null || op == null) { println("Cannot understand '${parts.joinToString(" ")}'"); return }
    if (op == Operation.DIVIDE && b == 0.0) { println("Division by zero"); return }
    println("$a ${op.symbol} $b = ${op.apply(a, b)}")
}

fun main() = runTask02()
