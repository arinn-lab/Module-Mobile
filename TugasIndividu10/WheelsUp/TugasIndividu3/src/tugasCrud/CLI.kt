package tugasCrud

fun main() {
    val manager = AnimalManager()
    var isRunning = true

    while (isRunning) {
        displayMenu()
        when (readLine()?.trim()) {
            "1" -> handleAdd(manager)
            "2" -> handleList(manager)
            "3" -> handleUpdate(manager)
            "4" -> handleDelete(manager)
            "5" -> handleSearch(manager)
            "6" -> {
                println("Sampai jumpa!")
                isRunning = false
            }
            else -> println("Pilihan tidak valid, coba lagi.\n")
        }
    }
}