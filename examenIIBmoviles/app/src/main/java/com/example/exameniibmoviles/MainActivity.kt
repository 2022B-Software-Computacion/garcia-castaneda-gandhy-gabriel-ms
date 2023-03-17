package com.example.exameniibmoviles

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    val firestoreDAO = FirestoreDAO()
    var idItemSeleccionado = 0
    lateinit var proveedores: List<Proveedor>
    lateinit var proveedoresNombres: ArrayList<String>
    lateinit var proveedoresListView: ListView

    private val updateProveedorLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val updatedProveedor = result.data?.getParcelableExtra<Proveedor>("proveedorActualizado")

            //Actualizar el proveedor en el listview
            if (updatedProveedor != null) {
                //Buscar y actualizar el proveedor en la colección
                val index: Int = proveedores!!.indexOfFirst { it.id == updatedProveedor.id }
                if (index >= 0) {
                    val updatedProveedores = proveedores!!.toMutableList()
                    updatedProveedores[index] = updatedProveedor
                    proveedores = updatedProveedores.toList()

                    //Se actualiza el nuevo proveedor en la lista local
                    proveedoresNombres[index] = updatedProveedor.nombre

                    //Se actualiza la vista de la lista
                    (proveedoresListView.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                } else {
                    val updatedProveedores = proveedores!!.toMutableList()
                    updatedProveedores.add(updatedProveedor)
                    proveedores = updatedProveedores.toList()

                    //Se añade el nuevo proveedor en la lista local
                    proveedoresNombres.add(updatedProveedor.nombre)

                    //Se actualiza la vista de la lista
                    (proveedoresListView.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                }
            }
            getProveedoresIniciales()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.proveedoresListView = findViewById<ListView>(R.id.lv_proveedores)

        //Obtener los proveedores de Firestore
        getProveedoresIniciales()

        //Colocar el menu contextual a la listview
        registerForContextMenu(proveedoresListView)

        val btnCrearProveedor = findViewById<Button>(R.id.btn_crear_proveedor)
        btnCrearProveedor.setOnClickListener {
            editarCrearProveedor(null)
        }
    }

    override fun onResume() {
        super.onResume()
        getProveedoresIniciales()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        //Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_contextual_proveedores, menu)

        //Obtener el indice del array
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_proveedor -> {
                editarCrearProveedor(proveedores!![idItemSeleccionado])
                return true
            }

            R.id.mi_eliminar_proveedor -> {
                eliminarProveedor()
                return true
            }

            R.id.mi_verProductos_proveedor -> {
                verProductosProveedor()
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    fun editarCrearProveedor(
        proveedor: Proveedor?
    ){
        // Crear un intent con el id del proveedor
        val intent = Intent(this, EditProveedorActivity::class.java)
        intent.putExtra("proveedor", proveedor)

        //Iniciar la actividad y pasar datos
        updateProveedorLauncher.launch(intent)
    }

    fun eliminarProveedor()  {
        // Se toma el índice de la lista para identificar el proveedor
        val proveedorIndex = idItemSeleccionado

        // Se identifica el id del documento de cada proveedor
        val proveedorId = proveedores[proveedorIndex].id
        Log.e("TAG", proveedores[proveedorIndex].toString())

        // Se elimina el documento del proveedor
        firestoreDAO.eliminarProveedor(proveedorId!!,
            onSuccess = {
                Toast.makeText(this, "Proveedor eliminado con éxito", Toast.LENGTH_SHORT)
                    .show()
                proveedores = proveedores.filterIndexed { index, _ ->  index != proveedorIndex }
                proveedoresNombres.removeAt(proveedorIndex)
                (proveedoresListView.adapter as ArrayAdapter<*>).notifyDataSetChanged()
            },
            onFailure = { error ->
                Toast.makeText(
                    this,
                    "Error al eliminar el proveedor: $error",
                    Toast.LENGTH_SHORT
                ).show()
            })
    }

    fun verProductosProveedor() {
        // Agregar listener al ListView para detectar cuando un elemento es seleccionado
        val proveedor = proveedores[idItemSeleccionado]

        // Crear un intent con los datos del proveedor
        val intent = Intent(this, ListProductosActivity::class.java)
        intent.putExtra("idProveedor", proveedor.id)
        intent.putExtra("nombreProveedor", proveedor.nombre)
        intent.putParcelableArrayListExtra(
            "productos",
            ArrayList(proveedor.productos)
        )

        // Iniciar la actividad y pasar los datos del proveedor
        startActivity(intent)
    }

    fun getProveedoresIniciales(){
        firestoreDAO.getProveedores(
            onSuccess = { proveedores ->
                //Se guardan los proveedores
                this.proveedores = proveedores

                //Crear una lista de strings con los nombres de los proveedores
                proveedoresNombres = proveedores.map { it.nombre } as ArrayList<String>

                //Crear un ArrayAdapter para el ListView
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, proveedoresNombres)

                //Asignar el adapter al ListView
                proveedoresListView.adapter = adapter
                adapter.notifyDataSetChanged()
            },
            onFailure = { error ->
                //Manejar el error
                Toast.makeText(this, "Error al obtener los proveedores: $error", Toast.LENGTH_SHORT)
                    .show()
            }
        )
    }
}