    package com.guilhermerufino.listacompras

    import android.content.Intent
    import android.os.Bundle
    import android.util.Log
    import android.widget.Button
    import androidx.activity.enableEdgeToEdge
    import androidx.appcompat.app.AppCompatActivity
    import androidx.core.view.ViewCompat
    import androidx.core.view.WindowInsetsCompat
    import androidx.lifecycle.lifecycleScope
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import com.guilhermerufino.listacompras.data.model.Categoria
    import com.guilhermerufino.listacompras.data.model.CategoriaRequest
    import com.guilhermerufino.listacompras.data.remote.RetrofitClient
    import com.guilhermerufino.listacompras.ui.adapter.CategoriaAdapter
    import kotlinx.coroutines.launch

    class MainActivity : AppCompatActivity() {
        lateinit var adapter: CategoriaAdapter
        private val categorias = mutableListOf<Categoria>()




        override fun onCreate(savedInstanceState: Bundle?) {


            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_main)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets


            }

            val btnAdicionarCategoria = findViewById<Button>(R.id.btnAdicionarProduto)
            val recycler = findViewById<RecyclerView>(R.id.recyclerProduto)
            recycler.layoutManager = LinearLayoutManager(this)

            Log.d("API", "Vou chamar Retrofit")
            lifecycleScope.launch {
                try {
                    val resultado = RetrofitClient.api.listarCategorias()
                    categorias.addAll(resultado)

                    adapter = CategoriaAdapter(
                        categorias,
                        onEditar = { categoria ->
                            Log.d("EDITAR", categoria.nome)

                            val editText = android.widget.EditText(this@MainActivity)
                            editText.setText(categoria.nome)

                            androidx.appcompat.app.AlertDialog.Builder(this@MainActivity)
                                .setTitle("Editar categoria")
                                .setView(editText)
                                .setPositiveButton("Salvar",{ dialog,which ->
                                    val nomeNovo = editText.text.toString()
                                    val body = CategoriaRequest(nomeNovo)


                                    lifecycleScope.launch {

                                        try {
                                            RetrofitClient.api.update(categoria.id, body)
                                            categoria.nome = nomeNovo

                                            val posicao = categorias.indexOfFirst {
                                                it.id == categoria.id
                                            }

                                            if (posicao != -1) {
                                                adapter.notifyDataSetChanged()
                                            }

                                        } catch (e: Exception){
                                            Log.e("Update", "Update ao deletar", e)
                                        }
                                    }
                                }


                                ).show()

                        },
                        onExcluir = { categoria ->
                            Log.d("EXCLUIR", categoria.nome)
                            androidx.appcompat.app.AlertDialog.Builder(this@MainActivity)
                                .setTitle("Excluir categoria")
                                .setMessage("Deseja excluir: ${categoria.nome}?")
                                .setPositiveButton("Sim"){dialog , which->

                                    lifecycleScope.launch {
                                    try {

                                        val response = RetrofitClient.api.deletarCategoria(categoria.id) // 👈 captura
                                        if (response.isSuccessful) {
                                            val posicao = categorias.indexOfFirst { it.id == categoria.id }
                                            if (posicao != -1) {
                                                categorias.removeAt(posicao)
                                                adapter.notifyItemRemoved(posicao)
                                                adapter.notifyItemRangeChanged(posicao, categorias.size)
                                            }
                                        } else {
                                            Log.e("EXCLUIR", "Erro HTTP: ${response.code()}")
                                        }

                                    }catch (e: Exception) {
                                        Log.e("EXCLUIR", "Erro ao deletar", e)

                                    }
                                    }
                                }
                                .setNegativeButton("Não"){dialog , which->
                                dialog.dismiss()
                                }
                                .show()

                        },
                        onClickItem = { categoria ->   // 👈 CLIQUE NO ITEM
                            val intent = Intent(this@MainActivity, Produto::class.java)
                            intent.putExtra("id", categoria.id)
                            intent.putExtra("nome", categoria.nome)
                            startActivity(intent)
                        }

                    )

                    recycler.adapter = adapter

                } catch (e: Exception) {
                    Log.e("API", "Erro ao buscar categorias", e)
                }
            }




            btnAdicionarCategoria.setOnClickListener {

                val intent = Intent(this, AddCategoriaActivity::class.java)
                startActivity(intent)
            }
        }



    }