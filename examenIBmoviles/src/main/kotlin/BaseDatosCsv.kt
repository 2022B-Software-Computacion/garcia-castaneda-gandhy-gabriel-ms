import java.io.File
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import java.time.LocalDate
import java.util.function.Predicate

class BaseDatosCsv {
    companion object {
        fun <E> leerElementoById(id: Int, elemento: E): Any? {
            val archivo = seleccionarArchivo(elemento)

            val elementos = leerElementos(elemento)

            return when (elemento!!::class) {
                (Proveedor::class) -> {
                    var coincideId = Predicate { elemento: Proveedor -> elemento.id == id}
                    elementos.find { x: Any -> coincideId.test(x as Proveedor) }
                }
                else -> {
                    var coincideId = Predicate { elemento: Producto -> elemento.id == id}
                    elementos.find { x: Any -> coincideId.test(x as Producto) }
                }
            }
        }

        fun <E> leerElementos(elemento: E): List<Any>{
            val archivo = seleccionarArchivo(elemento)
            val inputStream = archivo.inputStream()
            val reader = inputStream.bufferedReader()
            reader.readLine() //No se toma en cuenta el encabezado

            when (elemento!!::class) {
                (Proveedor::class) -> {
                    val resultado = reader.lineSequence()
                        .filter { it.isNotBlank() }
                        .map {
                            val stringsInfo: List<String> = it.split(",", ignoreCase = false, limit = 6)
                            Proveedor(stringsInfo[0].toInt(), stringsInfo[1], LocalDate.parse(stringsInfo[2]), stringsInfo[3].toBoolean(),
                                stringsInfo[4], extraerArrayProductos(stringsInfo[5]))
                        }.toList()
                    inputStream.close()
                    return resultado
                }
                else -> {
                    val resultado = reader.lineSequence()
                        .filter { it.isNotBlank() }
                        .map {
                            val stringsInfo: List<String> = it.split(",", ignoreCase = false, limit = 5)
                            Producto(stringsInfo[0].toInt(), stringsInfo[1], stringsInfo[2].toFloat(), stringsInfo[3].toInt(),
                                stringsInfo[4])
                        }.toList()
                    inputStream.close()
                    return resultado
                }
            }
        }

        fun extraerArrayProductos(input: String): ArrayList<Int>{
            //Ej: 1-2-3
            val idsStringLimpio = input.replace(" ", "").removePrefix("[").removeSuffix("]")
            val idsString = idsStringLimpio.split(",", ignoreCase = true)
            val idsInt = idsString.map { valorActual: String -> valorActual.toInt() }
            return ArrayList(idsInt)
        }

        fun <E> crearElemento(nuevoElemento: E) {
            val archivo = seleccionarArchivo(nuevoElemento)
            Files.write(archivo.toPath(), "\n".toByteArray() + nuevoElemento.toString().toByteArray(),
                StandardOpenOption.APPEND)
        }

        fun <E> eliminarElementoById(id: Int, elemento: E) {
            val archivo = seleccionarArchivo(elemento)
            val elementos = ArrayList(leerElementos(elemento))
            var encabezado: String = ""

            println("\n" + elementos)


            when (elemento!!::class) {
                (Proveedor::class) -> {
                    var coincideId = Predicate { elemento: Proveedor -> elemento.id == id}
                    remove<Proveedor> (elementos, coincideId)
                    encabezado = "CODIGO,NOMBRE,PRECIOUNIT,STOCK,DESCRIPCION\n"
                }
                else -> {
                    var coincideId = Predicate { elemento: Producto -> elemento.id == id}
                    remove<Producto> (elementos, coincideId)
                    encabezado = "CODIGO,NOMBRE,FECHA REGISTRO,DISPONIBLE,TELEFONO,PRODUCTOS\n"
                }
            }

            //  println("\n" + elementos)

            archivo.writeText(encabezado)

            var iterador: Int = 0
            elementos.forEach {
                //Agregar con un foreachindex que solo al ultimo no se ponga un salto de linea
                    valorActual: Any? -> run {
                if (iterador < elementos.size - 1)
                    archivo.appendText(valorActual.toString() + "\n")
                else
                    archivo.appendText(valorActual.toString())

                iterador++
            }
            }
        }

        fun <E> actualizarElemento(id: Int, nuevoElemento: E) {
            val archivo = seleccionarArchivo(nuevoElemento)
            val elementos = ArrayList(leerElementos(nuevoElemento))

            eliminarElementoById(id, nuevoElemento)
            crearElemento(nuevoElemento)
        }

        fun <E : Any> remove(list: ArrayList<Any>, predicate: Predicate<E>){
            list.removeIf { x: Any -> predicate.test(x as E) }
        }

        fun <E> seleccionarArchivo(elemento: E): File {
            val archivo: File
            when (elemento!!::class) {
                (Proveedor::class) -> return archivoProveedores
                else -> return archivoProducto
            }
        }
    }
}