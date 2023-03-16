package com.example.exameniibmoviles

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreDAO {

    //private val db = FirebaseFirestore.getInstance()
    private val db = Firebase.firestore

    private val proveedoresRef = db.collection("proveedores")

    // Crear un nuevo proveedor
    fun crearProveedor(
        proveedor: Proveedor,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        // Agregar el proveedor a Firestore
        proveedoresRef.add(proveedor)
            .addOnSuccessListener { documentReference ->
                // Obtener referencia a la subcolección "productos" dentro del proveedor
                val productosRef = documentReference.collection("productos")

                // Agregar cada producto a la subcolección "productos" dentro del proveedor
                for (producto in proveedor.productos) {
                    productosRef.add(producto)
                        .addOnSuccessListener {
                            producto.id = it.id
                        }
                        .addOnFailureListener { onFailure(it) }
                }
                onSuccess(documentReference.id)
            }
            .addOnFailureListener { onFailure(it) }
    }

    // Actualizar un proveedor existente
    fun actualizarProveedor(
        idProveedor: String,
        proveedor: Proveedor,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        proveedoresRef.document(idProveedor).set(proveedor)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    // Eliminar un proveedor existente
    fun eliminarProveedor(
        idProveedor: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        proveedoresRef.document(idProveedor).delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    // Obtener todos los proveedores
    fun getProveedores(onSuccess: (List<Proveedor>) -> Unit, onFailure: (Exception) -> Unit) {
        proveedoresRef.get()
            .addOnSuccessListener { result ->
                val proveedores = mutableListOf<Proveedor>()
                for (document in result) {
                    val proveedor = document.toObject(Proveedor::class.java)
                    proveedor.id = document.id
                    proveedores.add(proveedor)
                    getProductosDeProveedor(proveedor.id!!, onSuccess = {
                        proveedor.productos = it
                    }, onFailure = {})
                }
                onSuccess(proveedores)
            }
            .addOnFailureListener { exception ->
                Log.e("TAG", "Error al obtener los proveedores", exception)
                onFailure(exception)
            }
    }


    // Obtener todos los productos de un proveedor
    fun getProductosDeProveedor(
        idProveedor: String,
        onSuccess: (List<Producto>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val productosRef = proveedoresRef.document(idProveedor).collection("productos")

        productosRef.get()
            .addOnSuccessListener { result ->
                val productos = mutableListOf<Producto>()
                for (document in result) {
                    val producto = document.toObject(Producto::class.java)
                    producto.id = document.id
                    productos.add(producto)
                }
                onSuccess(productos)
            }
            .addOnFailureListener { onFailure(it) }
    }

    /* Agregar una nueva canción a una playlist existente
    fun agregarCancion(
        idPlaylist: String,
        cancion: Cancion,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        // Obtener referencia a la playlist
        val playlistRef = proveedoresRef.document(idPlaylist)
        playlistRef.get()
            .addOnSuccessListener { playlistDoc ->
                // Obtener matriz de canciones y asignar un nuevo ID a la nueva canción
                val canciones = playlistDoc.get("canciones") as? List<Cancion> ?: emptyList()


                // Agregar la nueva canción a la matriz de canciones
                val nuevasCanciones = canciones + cancion

                // Actualizar el campo canciones en Firestore con la nueva matriz
                playlistRef.update("canciones", nuevasCanciones)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { onFailure(it) }
            }
            .addOnFailureListener { onFailure(it) }
    }*/

    // Actualizar un producto existente dentro de un proveedor
    fun actualizarProducto(idProveedor: String, producto: Producto, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val productosRef = proveedoresRef.document(idProveedor).collection("productos")
        productosRef.document(producto.id!!).set(producto)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    // Añadir un producto dentro de un proveedor
    fun agregarProducto(idProveedor: String, producto: Producto, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val productosRef = proveedoresRef.document(idProveedor).collection("productos")
        productosRef.add(producto)
            .addOnSuccessListener {
                producto.id = it.id
                onSuccess()
            }
            .addOnFailureListener { onFailure(it) }
    }

    // Eliminar un producto existente dentro de un proveedor
    fun eliminarProducto(
        idProveedor: String,
        idProducto: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        // Obtener referencia al documento del producto dentro de la subcolección
        val productoRef =
            proveedoresRef.document(idProveedor).collection("productos").document(idProducto)

        productoRef.get()
            .addOnSuccessListener { productoDoc ->
                // Verificar que el documento exista antes de eliminarlo
                if (productoDoc.exists()) {
                    // Eliminar el documento de la subcolección
                    productoRef.delete()
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { onFailure(it) }
                } else {
                    onFailure(Exception("No se encontró el producto con el ID $idProducto"))
                }
            }
            .addOnFailureListener { onFailure(it) }
    }
}
