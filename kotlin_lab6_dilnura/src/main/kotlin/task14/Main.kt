package task14
sealed interface Outcome<out T> {
    data class Ok<T>(val value: T) : Outcome<T>
    data class Fail(val reason: String) : Outcome<Nothing>
}
private fun parseQuantity(text: String): Outcome<Int> {
    val n = text.trim().toIntOrNull() ?: return Outcome.Fail("'$text' is not a whole number")
    if (n <= 0) return Outcome.Fail("The quantity must be positive")
    if (n > 100) return Outcome.Fail("We cannot sell more than 100 at once")
    return Outcome.Ok(n)
}
private fun parsePrice(text: String): Outcome<Double> {
    val p = text.trim().toDoubleOrNull() ?: return Outcome.Fail("'$text' is not a price")
    if (p <= 0.0) return Outcome.Fail("The price must be positive")
    return Outcome.Ok(p)
}
private fun parseEmail(text: String): Outcome<String> =
    if ('@' in text && text.substringAfter('@').contains('.')) Outcome.Ok(text)
    else Outcome.Fail("Invalid e-mail address")
private fun <T> showError(label: String, result: Outcome<T>): Boolean {
    if (result is Outcome.Fail) { println("$label error: ${result.reason}"); return true }
    return false
}
fun runTask14() {
    print("Quantity: "); val q = parseQuantity(readln())
    if (showError("Quantity", q)) return
    print("Unit price: "); val p = parsePrice(readln())
    if (showError("Price", p)) return
    print("E-mail: "); val e = parseEmail(readln())
    if (showError("E-mail", e)) return
    if (q is Outcome.Ok && p is Outcome.Ok && e is Outcome.Ok)
        println("Total: ${q.value * p.value}; receipt will be sent to ${e.value}")
}

fun main() = runTask14()
