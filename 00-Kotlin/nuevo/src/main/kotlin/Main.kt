import java.util.*
import kotlin.collections.ArrayList

//Main kt
fun main(){
    println("Hola")

    //Tipos de variables
    //INMUTABLES (No Re Asignar) =
    val inmutable: String = "Gandhy";
    //inmutable = "Gabriel"; //No se puede reasignar

    //MUTABLES (Re Asignar) =
    var mutable: String = "Gabriel"
    mutable = "Gandhy"

    //val > var

    //Sintaxis Duck typing
    val ejemploVariable = "Ejemplo"
    val edadEjemplo: Int = 12
    ejemploVariable.trim()

    //Variables primitivas
    val nombreProfesor: String = "Adrian Eguez"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'S'
    val mayorEdad: Boolean = true

    //Clases JAVA
    val fechaNacimiento: Date = Date()

    //Condicionales
    if(true){
    } else {
    }

    //switch no existe
    val estadoCivilWhen = "S"
    when (estadoCivilWhen) {
        ("S") -> {
            println("acercarse")
        }
        "C" -> {
            println("alejarse")
        }
        "UN" -> println("hablar")
        else -> println("No reconocido")
    }

    val coqueteo = if (estadoCivilWhen == "S") "SI" else "NO"

    ////
    val sumaUno = Suma(1, 1);
    val sumaDos = Suma(null, 1);
    val sumaTres = Suma(1, null);
    val sumaCuatro = Suma(null, null);
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    Suma.pi
    Suma.elevarAlCuadrado(2)
    println(Suma.historialSumas)

    //ARREGLOS
    //Tipos de Arreglos

    //Arreglo Estático
    val arregloEstatico: Array<Int> = arrayOf<Int>(1, 2, 3)
    println(arregloEstatico)

    //Arreglo Dinámico
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    //OPERADORES -> Sirven para los arreglos estáticos y dinámicos

    //FOR EACH -> Unit
    //Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico
        .forEach {
            valorActual: Int ->
            println("Valor actual: ${valorActual}")
        }

    arregloEstatico
        .forEachIndexed{ indice: Int, valorActual: Int ->
            println("Valor ${valorActual} Indice: ${indice}")
        }
    println(respuestaForEach)

    // MAP -> Muta el arreglo (cambia el arreglo)
    // 1) Enviemos el nuevo valor de la iteración
    // 2) Nos devuelve un NUEVO ARREGLO con valores modificados

    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }
    println(respuestaMap)

    val respuestaMapDos = arregloDinamico.map{it + 15}
//        .map { valorActual: Int ->
//           return@map valorActual + 15
//        }
    println(respuestaMapDos)
}

//Funciones
//void imprimirNombre(String nombre){}
//Unit == void
fun imprimirNombre(nombre: String): Unit {
    println("Nombre: ${nombre}")
}

fun calcularSueldo(
    sueldo: Double, //Requerido
    tasa: Double = 12.00, //Opcional (Defecto)
    bonoEspecial: Double? = null, //Opcional (Null) -> nullable
): Double {
    //String -> String?
    //Int -> Int?
    //Date -> Date?
    if (bonoEspecial == null) {
        return sueldo * (100 / tasa)
    } else {
        return sueldo * (100 / tasa) + bonoEspecial
    }
}

abstract class NumerosJava {
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor(
        uno: Int,
        dos: Int
    ) { //Bloque de código constructor
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializado")
    }
}

abstract class Numeros( //Constructor Primario
    // uno: Int, //Parametro
    // public var uno: Int, //Propiedad de la clase pública
    // var uno: Int, //Propiedad de la clase pública
    protected val numeroUno: Int, //Propiedad de la clase protected
    protected val numeroDos: Int //Propiedad de la clase protected
) {
    // protected val numeroUno: Int
    // var cedula: String = "";
    // public var cedula: String = "";
    init { //Bloque código constructor Primario
        //this.numeroUno = uno
        this.numeroUno
        numeroUno
        this.numeroDos
        numeroDos
        println("Inicializado")
    }
}

class Suma( //Constructor Primario Suma
    uno: Int, //Parámetro
    dos: Int  //Parámetro
): Numeros(uno, dos) {
    init { //Bloque constructor primario
        this.numeroUno
        this.numeroDos
    }

    constructor( //Segundo constructor
        uno: Int?, //parámetro
        dos: Int   //parámetro
    ): this( //Llamada constructor primario
        if (uno == null) 0 else uno,
        dos
    )

    constructor( //Tercer constructor
        uno: Int, //parámetro
        dos: Int?   //parámetro
    ): this( //Llamada constructor primario
        uno,
        if (dos == null) 0 else dos
    )

    constructor( //Cuarto constructor
        uno: Int?, //parámetro
        dos: Int?   //parámetro
    ): this( //Llamada constructor primario
        if (uno == null) 0 else uno,
        if (dos == null) 0 else dos
    ){
    }

    public fun sumar(): Int {
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }

    companion object { //Atributos y Métodos "Compartidos" entre las instancias
        val pi = 3.14
        fun elevarAlCuadrado(num: Int): Int {
            return num * num
        }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma: Int){
            historialSumas.add(valorNuevaSuma)
        }
    }
}