import kotlin.properties.Delegates

val nombre = lazy {
    println("Lazy: Inicializando la variable en este momento ya que soy lazy")
    "Juan"
}

println("Inicializado " + nombre.isInitialized())
println("Hola " + nombre.value)
println("Inicializado " + nombre.isInitialized())

class Database() {

    var cliente: Int by Delegates.observable(0) { prop, old, new ->
        println("Observable: Se cambio el valor de la propiedad $prop de $old a $new")
    }

    var instancias: Int by Delegates.vetoable(0) { prop, old, new ->
        println("Se cambio el valor de la propiedad $prop de $old a $new")
        new >= 0 && new <= 10
    }
    
    init {
        println("Inicializando la base de datos")
    }

    fun save() {
        println("Guardando en la base de datos")
    }
}

// sin lazy ya se carga completa
val database = Database()
database.save()

// Con lazy se inicializa al momento de usarla
// Podemos quitar el = y poner el by para pasar de poner value
val databaseLazy by lazy {
    // println("Lazy: Inicializando la base de datos")
    Database() }

databaseLazy.save()

// Observable
database.cliente = 4
databaseLazy.cliente = 5
database.instancias = 5
println("Instancias: " + database.instancias)
database.instancias = -1
println("Instancias: " + database.instancias)
database.nombre