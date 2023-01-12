import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.BufferedWriter
import java.io.InputStream
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.io.File
import java.io.Writer
import java.nio.file.Paths
import java.util.*

fun main(){
    val fecha = LocalDate.parse("2022-12-25")
    //print(fecha)

    val p = Proveedor("Supermaxi", fecha, true, "Cumbayá", "0983473043")

    val inputStreamPrueba: InputStream = File("resources/csv/sourceBD.csv").inputStream()

    val proveedores = leerCsv(inputStreamPrueba)
    inputStreamPrueba.close()



    val respuesta: Unit = proveedores
        .forEach {
                valorActual: Proveedor ->
            println(valorActual)
        }

    /*val archivo = File("resources/csv/sourceBD.csv")
    archivo.inputStream().bufferedReader().readLines()

    archivo.printWriter().use { out ->
        out.println("Superior;2015-06-12;true;Mariscal Sucre y Ajaví;0983150975")
    }*/

    val writer: BufferedWriter = File("resources/csv/sourceBD.csv").bufferedWriter();

    val csvPrinter = CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("NOMBRE","FECHA REGISTRO","DISPONIBLE","DIRECCION","TELEFONO"))


    for (proveedor in proveedores) {
        val datosProveedor = Arrays.asList(
            proveedor.nombre,
            proveedor.fechaRegistro,
            proveedor.estaDisponible,
            proveedor.direccion,
            proveedor.telefono
        )
        csvPrinter.printRecord(datosProveedor)
    }



    csvPrinter.flush();
    csvPrinter.close()
}

fun leerCsv(inputStream: InputStream): List<Proveedor> {
    val reader = inputStream.bufferedReader()
    val header = reader.readLine()
    return reader.lineSequence()
        .filter { it.isNotBlank() }
        .map {
            val (nombre, fechaRegistro, disponible, direccion, telf) = it.split(";", ignoreCase = false, limit = 5)
            Proveedor(nombre, LocalDate.parse(fechaRegistro), disponible.toBoolean(), direccion, telf)
        }.toList()
}

fun Writer.writeCsv(proveedores: List<Object>) {
}