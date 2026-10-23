package task15
enum class Priority { LOW, NORMAL, HIGH, URGENT }
sealed interface Action
data class Open(val title: String, val priority: Priority) : Action
data class Close(val id: Int) : Action
data class Comment(val id: Int, val text: String) : Action
data class Reopen(val id: Int) : Action
data object ListAll : Action
class Ticket(val id: Int, val title: String, val priority: Priority) {
    var isOpen = true
    val comments = mutableListOf<String>()
}
private val tickets = mutableListOf<Ticket>()
private fun parse(line: String): Action? {
    val p = line.trim().split(" ", limit = 3)
    return when (p[0].lowercase()) {
        "open" -> {
            val priority = Priority.entries.firstOrNull { it.name.equals(p.getOrNull(1), true) }
            val title = p.getOrNull(2)
            if (priority == null || title.isNullOrBlank()) null else Open(title, priority)
        }
        "close" -> p.getOrNull(1)?.toIntOrNull()?.let { Close(it) }
        "reopen" -> p.getOrNull(1)?.toIntOrNull()?.let { Reopen(it) }
        "comment" -> {
            val id = p.getOrNull(1)?.toIntOrNull(); val text = p.getOrNull(2)
            if (id == null || text.isNullOrBlank()) null else Comment(id, text)
        }
        "list" -> ListAll
        else -> null
    }
}
private fun execute(action: Action) {
    when (action) {
        is Open -> { val t = Ticket(tickets.size + 1, action.title, action.priority); tickets.add(t); println("Opened #${t.id}: ${t.title} [${t.priority}]") }
        is Close -> {
            val t = tickets.find { it.id == action.id }
            if (t == null) println("There is no ticket #${action.id}") else { t.isOpen = false; println("Closed #${t.id}") }
        }
        is Reopen -> {
            val t = tickets.find { it.id == action.id }
            if (t == null) println("There is no ticket #${action.id}") else { t.isOpen = true; println("Reopened #${t.id}") }
        }
        is Comment -> {
            val t = tickets.find { it.id == action.id }
            if (t == null) println("There is no ticket #${action.id}") else { t.comments.add(action.text); println("Comment added to #${t.id} (${t.comments.size} in total)") }
        }
        ListAll -> {
            for (t in tickets.sortedByDescending { it.priority }) {
                val state = if (t.isOpen) "open" else "closed"
                println("#${t.id} $state [${t.priority}] ${t.title}")
                t.comments.forEach { println("   - $it") }
            }
            println("Open tickets: ${tickets.count { it.isOpen }}")
        }
    }
}
fun runTask15() {
    println("Commands: open PRIORITY TITLE, close ID, reopen ID, comment ID TEXT, list, exit")
    println("Priorities: ${Priority.entries.joinToString()}")
    while (true) { print("> "); val line = readln(); if (line.trim() == "exit") break
        val action = parse(line); if (action == null) println("I do not understand that") else execute(action)
    }
}

fun main() = runTask15()
