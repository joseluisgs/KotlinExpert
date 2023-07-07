package errors

sealed class NoteError(val message: String) {
    class ApiError(message: String) : NoteError(message)
}