package task05
sealed class Payment {
    data class CreditCard(val number: String, val expiry: String) : Payment()
    data class PayPal(val email: String) : Payment()
    data object Cash : Payment()
    data class Crypto(val wallet: String) : Payment()
}
private fun readPayment(): Payment? {
    println("1) Credit card  2) PayPal  3) Cash  4) Crypto")
    print("Choose a payment method: ")
    return when (readln().trim()) {
        "1" -> { print("Card number: "); val n = readln().trim(); print("Expiry (MM/YY): "); Payment.CreditCard(n, readln().trim()) }
        "2" -> { print("PayPal e-mail: "); Payment.PayPal(readln().trim()) }
        "3" -> Payment.Cash
        "4" -> { print("Wallet address: "); Payment.Crypto(readln().trim()) }
        else -> null
    }
}
private fun process(p: Payment, amount: Double) {
    when (p) {
        is Payment.CreditCard -> println("Charged $amount to the card ending ${p.number.takeLast(4)} (expires ${p.expiry})")
        is Payment.PayPal -> println("Sent a payment request for $amount to ${p.email}")
        Payment.Cash -> println("Please pay $amount in cash at the desk")
        is Payment.Crypto -> println("Waiting for a transfer to wallet ${p.wallet}")
    }
}
fun runTask05() {
    print("Amount to pay: "); val amount = readln().trim().toDoubleOrNull()
    if (amount == null || amount < 0) { println("Invalid amount"); return }
    val payment = readPayment()
    if (payment == null) println("Unknown payment method") else { println("You chose: $payment"); process(payment, amount) }
}

fun main() = runTask05()
