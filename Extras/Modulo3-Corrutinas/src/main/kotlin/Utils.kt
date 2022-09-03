fun log(message: String) {
    println("(${Thread.currentThread().name}) : $message")
}

fun log(character: Char) {
    print("$character")
}