data class Note(val title: String, val description: String, val priority: Int = 1)

val notes = listOf<Note>(
    Note("Title 1", "Description 1"),
    Note("Title 2", "Description 2"),
    Note("Title 3", "Description 3"),
    Note("Title 4", "Description 4"),
    Note("Title 5", "Description 5"),
    Note("Title 6", "Description 6"),
    Note("Title 7", "Description 7"),
    Note("Title 8", "Description 8"),
    Note("Title 9", "Description 9"),
    Note("Title 10", "Description 10"),
)

println(notes)

val titles = notes.filterNot { it.title.contains("4") }
    .map { it.title }
    .sortedByDescending { it }

println(titles)