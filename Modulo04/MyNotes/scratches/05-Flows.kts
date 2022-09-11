import kotlinx.coroutines.flow.*

val mySequence = sequence {
    for (i in 1..3) {
        yield(i)
    }
}

println(mySequence.toList())

val myFlow = listOf(1,2,3,4,5,6,7,8,9).asFlow()

val otherFlow = flow {
    for (i in 1..3) {
        emit(i)
    }
}