import java.io.File
import java.lang.NumberFormatException
import java.time.LocalDate

val archivoProveedores = File("resources/csv/proveedores.csv")
val archivoProducto = File("resources/csv/productos.csv")

fun main() {
    /* val fecha = LocalDate.parse("2022-12-25")

    val proveedores = BaseDatosCsv.leerElementos(Proveedor())

    val productos = BaseDatosCsv.leerElementos(Producto())

    proveedores
        .forEach { valorActual: Any ->
            println(valorActual)
        }

    productos
        .forEach { valorActual: Any ->
            println(valorActual)
        }

    val nuevoProveedor = Proveedor(5, "Supermaxi", fecha, true, "0983473043", arrayListOf<Int>(1, 2, 3, 4))
    //crearProveedor(nuevoProveedor)
    val nuevoProducto = Producto(3, "Bom Bom Bum Especial",0.20f,40,"Chupete individual")

    BaseDatosCsv.crearElemento(nuevoProveedor)
    BaseDatosCsv.actualizarElemento(3, nuevoProducto)
    BaseDatosCsv.eliminarElementoById(2, Producto())
    print(BaseDatosCsv.leerElementoById(2, Proveedor())) */

    var salir = false
    var opcion: Int

    println("BIENVENIDO AL SISTEMA CRUD DE PROVEEDORES Y PRODUCTOS")

    while (!salir) {
        println("Escriba el número de la opción que desea realizar")
        println("1. Crear un producto")
        println("2. Visualizar todos los productos")
        println("3. Visualizar un producto por su id")
        println("4. Actualizar los datos de un producto")
        println("5. Eliminar un producto por su id")
        println("6. Crear un proveedor")
        println("7. Visualizar todos los proveedores")
        println("8. Visualizar un proveedor por su id")
        println("9. Actualizar los datos de un proveedor")
        println("10. Eliminar un proveedor por su id")
        println("11. Salir")

        opcion = readLine()!!.toInt()

        when (opcion) {
            (1) -> {
                //Crear producto
            }
            (2) -> {

            }
            (3) -> {

            }
            (4) -> {

            }
            (5) -> {

            }
            (6) -> {

            }
            (7) -> {

            }
            (8) -> {

            }
            (9) -> {

            }
            (10) -> {

            }
            (11) -> {

            }
            else -> println("Solo números entre 1 y 11")
        }
    }
}

