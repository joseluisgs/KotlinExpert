if (7 in (5..7)) {
   println("7 está en el rango");
} else {
    println("7 no está en el rango");
}

println(
    if (7 in (5..7)) {
        "7 está en el rango"
    } else {
        "7 no está en el rango"
    }
)

when (7) {
    in (5..7) -> "7 está en el rango"
    else -> "7 no está en el rango"
}

var it = 0
while (it < 10) {
    println("it = $it")
    it++
}

it = 0
do {
    println("it = $it")
    it++
} while (it < 10)

for (i in 1..10) {
    println("i = $i")
}

val list = listOf("a", "b", "c")
for (i in list) {
    println("i = $i")
}

for (i in list.indices) {
    println("i[$i] = ${list[i]}")
}

for (i in list.withIndex()) {
    println("i[${i.index}] = ${i.value}")
}