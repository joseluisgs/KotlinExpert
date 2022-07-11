package models

// data class Note, ideal como POKO de una nota para nuestra app
data class Note(val title: String, val description: String, val priority: Int = 1)