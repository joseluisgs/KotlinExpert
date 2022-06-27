println("Jugando con scripts en Kotlin REPL")
var x = 10
x++


var y: Long = x.toLong()
y

println("$y Jugando con Strintg templates $x")

// Forma normal
fun sum(a: Int, b: Int): Int {
    return a + b
}

sum(2, 3)

// Usando una función lambda
val suma = { a: Int, b: Int -> a + b }
suma(2, 3)

// Por defecto en Kotlin las clases son finales, por lo que necesitamos el uso de la palabra reservada open para poder heredar
open class Persona(open var nombre: String, var edad: Int = 20)
val p = Persona("Juan", 18)
println("${p.nombre} tiene ${p.edad} años")

class Alumno(nombre: String, edad: Int, curso: String = "daw") : Persona(nombre, edad) {
    // Si quiero persolaizar los getter y setter de la clase padre, debo usar la palabra reservada override
    // También puedo personalizar su comportamiento
    var curso = curso
    get() = field.uppercase()
    set(value) {
        field = value.lowercase()
    }
}
val a = Alumno("Juan", 18, "DAM")
println("${a.nombre} tiene ${a.edad} años en el curso ${a.curso}")
// Usamos propiedades para acceder al estado
a.nombre = "Pepe"
a.edad = 20
a.curso = "ASir"
println("${a.nombre} tiene ${a.edad} años en el curso ${a.curso}")