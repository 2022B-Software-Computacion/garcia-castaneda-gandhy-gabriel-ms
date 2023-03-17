package com.example.exameniibmoviles

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class EditProveedorActivity : AppCompatActivity() {
    val firestoreDAO = FirestoreDAO()
    lateinit var idProveedor: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_proveedor)

        //Referencia a los elementos del layout
        val etNombre = findViewById<EditText>(R.id.et_nombre_proveedor)
        val etTelefono = findViewById<EditText>(R.id.et_telefono_proveedor)
        val btnGuardarProveedor = findViewById<Button>(R.id.btn_guardar_proveedor)

        //Obtener el id del proveedor
        val proveedor = intent.getParcelableExtra<Proveedor>("proveedor")

        if (proveedor != null) {
            idProveedor = proveedor.id!!
            etNombre.setText(proveedor.nombre)
            etTelefono.setText(proveedor.telefono)
            btnGuardarProveedor.setOnClickListener {
                //Obtener los valores ingresados
                val nuevoNombre = etNombre.text.toString()
                val nuevoTelefono = etTelefono.text.toString()
                val nuevoProveedor = Proveedor(proveedor.id, nuevoNombre, nuevoTelefono)

                firestoreDAO.actualizarProveedor(idProveedor, nuevoProveedor, {
                    Toast.makeText(this, "Se actualizo el proveedor ${proveedor.nombre} correctamente", Toast.LENGTH_SHORT)
                        .show()
                    setResult(Activity.RESULT_OK, Intent().putExtra("proveedorActualizado", nuevoProveedor))
                    finish()
                }, { exception ->
                    Toast.makeText(this, "Error al actualizar el proveedor: $exception", Toast.LENGTH_SHORT)
                        .show()
                })
            }
        } else {
            btnGuardarProveedor.setOnClickListener {
                //Obtener los valores ingresados
                val nuevoNombre = etNombre.text.toString()
                val nuevoTelefono = etTelefono.text.toString()

                //Crear el nuevo proveedor
                val proveedor = Proveedor(nuevoNombre, nuevoTelefono, emptyList())
                firestoreDAO.crearProveedor(proveedor,
                    {
                        proveedor.id = it
                        Toast.makeText(this, "Se creó el Proveedor ${proveedor.nombre} correctamente", Toast.LENGTH_SHORT)
                            .show()
                        setResult(Activity.RESULT_OK, Intent().putExtra("proveedorActualizado", proveedor))
                        finish()
                    }, { exception ->
                        //Manejar el error si ocurre
                        Toast.makeText(this, "Error al actualizar el Proveedor: $exception", Toast.LENGTH_SHORT)
                            .show()
                    })
            }
        }
    }
}