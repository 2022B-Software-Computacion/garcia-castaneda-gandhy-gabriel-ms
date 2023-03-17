package com.example.exameniibmoviles

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class EditProductoActivity : AppCompatActivity() {
    val firestoreDAO = FirestoreDAO()
    lateinit var idProveedor: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_producto)

        //Referencia a los elementos del layout
        val etNombre = findViewById<EditText>(R.id.et_nombre_producto)
        val etPrecio = findViewById<EditText>(R.id.et_precio_producto)
        val etStock = findViewById<EditText>(R.id.et_stock_producto)
        val etDescripcion = findViewById<EditText>(R.id.et_descripcion_producto)
        val btnGuardarProducto = findViewById<Button>(R.id.btn_guardar_producto)

        //Obtener el id del proveedor y el producto
        idProveedor = intent.getStringExtra("idProveedor")!!
        val producto = intent.getParcelableExtra<Producto>("producto")
        if (producto != null) {
            etNombre.setText(producto.nombre)
            etPrecio.setText(producto.precioUnit.toString())
            etStock.setText(producto.stock.toString())
            etDescripcion.setText(producto.descripcion)
            btnGuardarProducto.setOnClickListener {
                //Obtener los valores ingresados
                val nuevoNombre = etNombre.text.toString()
                val nuevoPrecio = (etPrecio.text.toString()).toFloat()
                val nuevoStock = (etStock.text.toString()).toInt()
                val nuevaDescricion = etDescripcion.text.toString()
                val nuevoProducto = Producto(producto.id, nuevoNombre, nuevoPrecio, nuevoStock, nuevaDescricion)

                firestoreDAO.actualizarProducto(idProveedor, nuevoProducto, {
                    Toast.makeText(this, "Se actualizo el producto ${producto.nombre} correctamente", Toast.LENGTH_SHORT)
                        .show()
                    setResult(Activity.RESULT_OK, Intent().putExtra("productoActualizado", nuevoProducto))
                    finish()
                }, { exception ->
                    Toast.makeText(this, "Error al actualizar el producto: $exception", Toast.LENGTH_SHORT)
                        .show()
                })
            }
        } else {
            btnGuardarProducto.setOnClickListener {
                //Obtener los valores ingresados
                val nuevoNombre = etNombre.text.toString()
                val nuevoPrecio = (etPrecio.text.toString()).toFloat()
                val nuevoStock = (etStock.text.toString()).toInt()
                val nuevaDescricion = etDescripcion.text.toString()

                //Crear el nuevo producto
                val producto = Producto(nuevoNombre, nuevoPrecio, nuevoStock, nuevaDescricion)
                firestoreDAO.agregarProducto(idProveedor, producto,
                    {
                        Toast.makeText(this, "Se agrego el producto ${producto.nombre} correctamente", Toast.LENGTH_SHORT)
                            .show()
                        setResult(Activity.RESULT_OK, Intent().putExtra("productoActualizado", producto))
                        finish()
                    }, {exception ->
                        //Manejar el error si ocurre
                        Toast.makeText(this, "Error al agregar el producto: $exception", Toast.LENGTH_SHORT)
                            .show()
                    })
            }
        }
    }
}