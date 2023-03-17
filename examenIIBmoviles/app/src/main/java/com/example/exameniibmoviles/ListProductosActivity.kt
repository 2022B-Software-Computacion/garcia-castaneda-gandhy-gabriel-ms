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

class ListProductosActivity : AppCompatActivity() {
    lateinit var productosListView: ListView
    lateinit var nombreProveedorTextView: TextView

    val firestoreDAO = FirestoreDAO()

    var idItemSeleccionado = 0
    var productos: List<Producto>? = null
    lateinit var productosNombres: ArrayList<String>
    lateinit var nombreProveedor: String
    lateinit var idProveedor: String

    private val updateProductoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val updatedProducto = result.data?.getParcelableExtra<Producto>("productoActualizado")
            Log.e("TAG", updatedProducto.toString())

            //Actualizar el producto en el listview
            if (updatedProducto != null) {
                //Buscar y actualizar el producto en la lista del proveedor
                val index: Int = productos!!.indexOfFirst { it.id == updatedProducto.id }
                if (index >= 0) {
                    val updatedProductos = productos!!.toMutableList()
                    updatedProductos[index] = updatedProducto
                    productos = updatedProductos.toList()

                    //Se actualiza con el nuevo producto en la lista local
                    productosNombres[index] = updatedProducto.nombre
                    Log.e("TAG", productosNombres.toString())

                    //Se actualiza la vista de la lista
                    (productosListView.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                } else {
                    val updatedProductos = productos!!.toMutableList()
                    updatedProductos.add(updatedProducto)
                    productos = updatedProductos.toList()

                    //Se actualiza con el nuevo producto en la lista local
                    productosNombres.add(updatedProducto.nombre)
                    Log.e("TAG", productosNombres.toString())

                    //Se actualiza la vista en la lista
                    (productosListView.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_productos)

        val btnCrearProducto = findViewById<Button>(R.id.btn_crear_producto)
        btnCrearProducto.setOnClickListener {
            editarCrearProducto(null)
        }

        //Obtener el listview de productos y textview
        nombreProveedorTextView = findViewById<TextView>(R.id.tv_nombre_proveedor)
        productosListView = findViewById<ListView>(R.id.lv_productos)

        //Obtener la lista de productos del proveedor seleccionado, su nombre e id
        idProveedor = intent.getStringExtra("idProveedor")!!
        nombreProveedor = intent.getStringExtra("nombreProveedor")!!
        productos = intent.getParcelableArrayListExtra<Producto>("productos")

        productosNombres = productos?.map { it.nombre } as ArrayList<String>

        //Crear un adaptador para la lista de canciones
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, productosNombres as List<String>)

        //Asignar el adaptador al listview
        productosListView.adapter = adapter

        //Actualizar la vista del listview y asignar al textview el nombre del proveedor
        nombreProveedorTextView.text = nombreProveedor
        adapter.notifyDataSetChanged()

        registerForContextMenu(productosListView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        //Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_contextual_productos, menu)

        //Obtener el id del array
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_producto -> {
                editarCrearProducto(productos!![idItemSeleccionado])
                return true
            }

            R.id.mi_eliminar_producto -> {
                eliminarProducto()
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    fun editarCrearProducto(
        producto: Producto?
    ){
        //Crear un intent con el id del proveedor
        val intent = Intent(this, EditProductoActivity::class.java)
        intent.putExtra("idProveedor", idProveedor)
        intent.putExtra("producto", producto)

        //Iniciar la actividad y enviar datos
        updateProductoLauncher.launch(intent)
    }

    fun eliminarProducto() {
        //Se selecciona el indice de la lista para identificar el producto
        val productoIndex = idItemSeleccionado

        //Se identifica el id del documento del producto
        val productoId = productos!![productoIndex].id!!

        //Se elimina el elemento del docuemnto del proveedor
        firestoreDAO.eliminarProducto(
            idProveedor, productoId,
            onSuccess = {
                Toast.makeText(this, "Producto eliminado con exito", Toast.LENGTH_SHORT)
                    .show()
                productos = productos?.filterIndexed { index, _ -> index != productoIndex }
                productosNombres.removeAt(productoIndex)
                (productosListView.adapter as ArrayAdapter<*>).notifyDataSetChanged()
            },
            onFailure = { error ->
                Toast.makeText(
                    this,
                    "Error al eliminar el producto: $error",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("Error al eliminar", error.toString())
            }
        )
    }
}