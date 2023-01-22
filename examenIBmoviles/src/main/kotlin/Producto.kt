class Producto (
    var id: Int,
    var nombre: String,
    var precioUnit: Float,
    var stock: Int,
    var descripcion: String
) {
    constructor( //Segundo constructor (vacío)
    ) : this(0, "", 0.00f, 0, "")

    override fun toString(): String {
        return "$id,$nombre,$precioUnit,$stock,$descripcion"
    }
}