import java.time.LocalDate

fun main() {
    //Variables auxiliares
    var salir = false
    var opcion: Int
    val productoVacio = Producto()
    val proveedorVacio = Proveedor()

    //Variables de datos para PRODUCTO
    var idProducto: Int
    var nombreProducto: String
    var precioUnitProducto: Float
    var stockProducto: Int
    var descripcionProducto: String

    //Variables de datos para PROVEEDOR
    var idProveedor: Int
    var nombreProveedor: String
    var fechaRegistroProveedor: LocalDate
    var estaDisponibleProveedor: Boolean
    var telefonoProveedor: String
    var productosProveedor: ArrayList<Int>

    println("BIENVENIDO AL SISTEMA CRUD DE PROVEEDORES Y PRODUCTOS")

    while (!salir) {
        println("\nMENÚ DE OPCIONES")
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

        print("\nEscriba el número de la opción que desea realizar: ")
        opcion = readLine()!!.toInt()

        println()

        when (opcion) {
            (1) -> {
                //Crear producto
                print("\nIngrese el código del nuevo producto: ")
                idProducto = readLine()!!.toInt()
                print("Ingrese el nombre del nuevo producto: ")
                nombreProducto = readLine().toString()
                print("Ingrese el precio del nuevo producto: ")
                precioUnitProducto = readLine()!!.toFloat()
                print("Ingrese el stock del nuevo producto: ")
                stockProducto = readLine()!!.toInt()
                print("Ingrese la descripción del nuevo producto: ")
                descripcionProducto = readLine().toString()

                val nuevoProducto = Producto(idProducto, nombreProducto, precioUnitProducto, stockProducto,
                    descripcionProducto)

                BaseDatosCsv.crearElemento(nuevoProducto)
                println("Producto ingresado correctamente: \n" + nuevoProducto)
            }
            (2) -> {
                //Visualizar todos los productos
                println("CODIGO / NOMBRE / PRECIOUNIT / STOCK / DESCRIPCION")
                BaseDatosCsv.leerElementos(productoVacio)
                    .forEach { valorActual: Any ->
                        println(valorActual)
                    }
            }
            (3) -> {
                //Visualizar un producto por su id
                print("Ingrese el código del producto que desea consultar: ")
                idProducto = readLine()!!.toInt()
                println("\nCODIGO / NOMBRE / PRECIOUNIT / STOCK / DESCRIPCION")
                println(BaseDatosCsv.leerElementoById(idProducto, productoVacio))
            }
            (4) -> {
                //Actualizar los datos de un producto
                print("Ingrese el código del producto que desea actualizar: ")
                idProducto = readLine()!!.toInt()
                val productoAActualizar = BaseDatosCsv.leerElementoById(idProducto, productoVacio) as Producto
                println("\nCODIGO / NOMBRE / PRECIOUNIT / STOCK / DESCRIPCION")
                println(productoAActualizar)

                print("\nIngrese un nuevo nombre (nombre antiguo: '" + productoAActualizar.nombre + "'): ")
                nombreProducto = readLine().toString()
                print("Ingrese un nuevo precio (precio antiguo: '" + productoAActualizar.precioUnit + "'): ")
                precioUnitProducto = readLine()!!.toFloat()
                print("Ingrese un nuevo stock (stock antiguo: '" + productoAActualizar.stock + "'): ")
                stockProducto = readLine()!!.toInt()
                print("Ingrese una nueva descripción ('" + productoAActualizar.descripcion + "'): ")
                descripcionProducto = readLine().toString()

                productoAActualizar.nombre = nombreProducto
                productoAActualizar.precioUnit = precioUnitProducto
                productoAActualizar.stock = stockProducto
                productoAActualizar.descripcion = descripcionProducto

                BaseDatosCsv.actualizarElemento(idProducto, productoAActualizar)
                println("Producto actualizado correctamente: \n" + productoAActualizar)
            }
            (5) -> {
                //Eliminar producto por id
                print("Ingrese el código del producto que desea eliminar: ")
                idProducto = readLine()!!.toInt()
                val productoEliminado = BaseDatosCsv.leerElementoById(idProducto, productoVacio)

                BaseDatosCsv.eliminarElementoById(idProducto, productoEliminado)
                println("Producto eliminado correctamente: \n" + productoEliminado)
            }
            (6) -> {
                //Crear un proveedor
                print("Ingrese el código del nuevo proveedor: ")
                idProveedor = readLine()!!.toInt()
                print("\nIngrese el nombre del nuevo proveedor: ")
                nombreProveedor = readLine().toString()
                fechaRegistroProveedor = LocalDate.now()
                print("Ingrese la disponibilidad del nuevo proveedor ('true' o 'false'): ")
                estaDisponibleProveedor = readLine()!!.toBoolean()
                print("Ingrese el teléfono del nuevo proveedor: ")
                telefonoProveedor = readLine().toString()
                print("Ingrese los ids de productos del nuevo proveedor (EJEMPLO: '[1, 2, 4]'): ")
                productosProveedor = BaseDatosCsv.extraerArrayProductos(readLine().toString())

                val nuevoProveedor = Proveedor(idProveedor, nombreProveedor, fechaRegistroProveedor,
                    estaDisponibleProveedor, telefonoProveedor, productosProveedor)

                BaseDatosCsv.crearElemento(nuevoProveedor)
                println("Proveedor ingresado correctamente: \n" + nuevoProveedor)
            }
            (7) -> {
                //Visualizar todos los proveedores
                println("CODIGO / NOMBRE / FECHA REGISTRO / DISPONIBLE / TELEFONO / ID PRODUCTOS")
                BaseDatosCsv.leerElementos(proveedorVacio)
                    .forEach { valorActual: Any ->
                        println(valorActual)
                    }
            }
            (8) -> {
                //Visualizar proveedor por id
                print("Ingrese el código del proveedor que desea consultar: ")
                idProveedor = readLine()!!.toInt()
                println("\nCODIGO / NOMBRE / FECHA REGISTRO / DISPONIBLE / TELEFONO / ID PRODUCTOS")
                println(BaseDatosCsv.leerElementoById(idProveedor, proveedorVacio))
            }
            (9) -> {
                //Actualizar los datos de un proveedor
                print("Ingrese el código del proveedor que desea actualizar: ")
                idProveedor = readLine()!!.toInt()
                val proveedorAActualizar = BaseDatosCsv.leerElementoById(idProveedor, proveedorVacio) as Proveedor
                println("\nCODIGO / NOMBRE / FECHA REGISTRO / DISPONIBLE / TELEFONO / ID PRODUCTOS")
                println(proveedorAActualizar)

                print("\nIngrese un nuevo nombre (nombre antiguo: '" + proveedorAActualizar.nombre + "'): ")
                nombreProveedor = readLine().toString()
                print("Ingrese la disponibilidad (disponibilidad antigua: '" + proveedorAActualizar.estaDisponible + "'): ")
                estaDisponibleProveedor = readLine()!!.toBoolean()
                print("Ingrese un nuevo teléfono (teléfono antiguo: '" + proveedorAActualizar.telefono + "'): ")
                telefonoProveedor = readLine()!!.toString()
                print("Ingrese una nueva lista de ids de productos (lista antigua:  '" + proveedorAActualizar.productos + "'): ")
                productosProveedor = BaseDatosCsv.extraerArrayProductos(readLine().toString())

                proveedorAActualizar.nombre = nombreProveedor
                proveedorAActualizar.estaDisponible = estaDisponibleProveedor
                proveedorAActualizar.telefono = telefonoProveedor
                proveedorAActualizar.productos = productosProveedor

                BaseDatosCsv.actualizarElemento(idProveedor, proveedorAActualizar)
                println("Proveedor actualizado correctamente: \n" + proveedorAActualizar)
            }
            (10) -> {
                //Eliminar un proveedor por su id
                print("Ingrese el código del proveedor que desea eliminar: ")
                idProveedor = readLine()!!.toInt()
                val proveedorEliminado = BaseDatosCsv.leerElementoById(idProveedor, proveedorVacio)

                BaseDatosCsv.eliminarElementoById(idProveedor, proveedorEliminado)
                println("Proveedor eliminado correctamente: \n" + proveedorEliminado)
            }
            (11) -> {
                salir = true
            }
            else -> println("Solo números entre 1 y 11")
        }
        println("Saliendo...")
    }
}

