package task07
sealed interface ApiRequest
data class LoginRequest(val username: String, val password: String) : ApiRequest
data object LogoutRequest : ApiRequest
data class RefreshRequest(val token: String) : ApiRequest
data class UserData(val userId: String, val name: String)
sealed class ApiResponse {
    data class UserSuccess(val user: UserData) : ApiResponse()
    data object NotLoggedIn : ApiResponse()
    data class Error(val message: String) : ApiResponse()
}
private val accounts = mapOf("ali" to "1234", "dilnoza" to "qwerty")
private var currentUser: UserData? = null
private fun handleRequest(request: ApiRequest): ApiResponse = when (request) {
    is LoginRequest -> if (accounts[request.username] == request.password) {
        val user = UserData("u-${request.username}", request.username.replaceFirstChar { it.uppercase() })
        currentUser = user; ApiResponse.UserSuccess(user)
    } else ApiResponse.Error("Invalid username or password")
    LogoutRequest -> { val user = currentUser; currentUser = null; if (user == null) ApiResponse.NotLoggedIn else ApiResponse.UserSuccess(user) }
    is RefreshRequest -> ApiResponse.Error("Refresh is not supported yet")
}
private fun show(response: ApiResponse) = when (response) {
    is ApiResponse.UserSuccess -> println("OK: ${response.user.name} (${response.user.userId})")
    ApiResponse.NotLoggedIn -> println("Nobody is logged in")
    is ApiResponse.Error -> println("FAILED: ${response.message}")
}
fun runTask07() {
    println("Commands: login, logout, refresh, exit")
    while (true) {
        print("> ")
        when (readln().trim()) {
            "login" -> { print("Username: "); val u = readln().trim(); print("Password: "); show(handleRequest(LoginRequest(u, readln().trim()))) }
            "logout" -> show(handleRequest(LogoutRequest))
            "refresh" -> { print("Token: "); show(handleRequest(RefreshRequest(readln().trim()))) }
            "exit" -> return
            else -> println("Unknown command")
        }
    }
}

fun main() = runTask07()
