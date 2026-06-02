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
import com.guilhermerufino.listacompras.data.model.CategoriaRequest
import com.guilhermerufino.listacompras.data.model.Produto
import com.guilhermerufino.listacompras.data.model.ProdutoRequest
import com.guilhermerufino.listacompras.data.model.ProdutoRequestUpdate
import com.guilhermerufino.listacompras.data.remote.RetrofitClient
import com.guilhermerufino.listacompras.ui.adapter.ProdutoAdapter
import kotlinx.coroutines.launch

class Produto : AppCompatActivity() {
    lateinit var adapter: ProdutoAdapter
     var  produtos = mutableListOf<ProdutoRequestUpdate>()

    override fun onCreate(savedInstanceState: Bundle?) {


         var categoriaId = intent.getLongExtra("id", -1)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_produto)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnAdicionarProduto = findViewById<Button>(R.id.btnAdicionarProduto)
        val recycler = findViewById<RecyclerView>(R.id.recyclerProduto)
        recycler.layoutManager = LinearLayoutManager(this)
        Log.d("API", "Vou chamar Retrofit")

        lifecycleScope.launch {
            try {
                val resultado = RetrofitClient.api.listarProdutos(categoriaId)
                produtos.addAll(resultado)

                adapter = ProdutoAdapter(produtos,
                    onEditar = { p ->
                        val editText = android.widget.EditText(this@Produto)
                        editText.setText(p.nome)

                        androidx.appcompat.app.AlertDialog.Builder(this@Produto)
                            .setTitle("Editar Produto")
                            .setView(editText)
                            .setPositiveButton("Salvar",{ dialog,which ->
                                val nomeNovo = editText.text.toString()
                                val body = ProdutoRequestUpdate(p.id,nomeNovo,p.checked)

                                lifecycleScope.launch {

                                    try {

                                        RetrofitClient.api.updateProduto(p.id, body)
                                        p.nome = nomeNovo

                                        val posicao = produtos.indexOfFirst {
                                            it.id == p.id
                                        }

                                        if (posicao != -1) {
                                            adapter.notifyDataSetChanged()
                                        }
                                    } catch (e: Exception){
                                        Log.e("Update", "Update", e)
                                    }
                                }
                            } ).show()


                    },
                    onExcluir= {p ->

                        Log.d("EXCLUIR", p.nome)
                        androidx.appcompat.app.AlertDialog.Builder(this@Produto)
                            .setTitle("Excluir produto")
                            .setMessage("Deseja excluir: ${p.nome}?")
                            .setPositiveButton("Sim"){dialog , which->

                                lifecycleScope.launch {
                                    try {

                                        val response = RetrofitClient.api.deletarProduto(p.id) // 👈 captura
                                        if (response.isSuccessful) {
                                            val posicao = produtos.indexOfFirst { it.id == p.id }
                                            if (posicao != -1) {
                                                produtos.removeAt(posicao)
                                                adapter.notifyItemRemoved(posicao)
                                                adapter.notifyItemRangeChanged(posicao, produtos.size)
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

                    checked= { p->
                        lifecycleScope.launch {
                            val body = ProdutoRequestUpdate(p.id,p.nome,p.checked)

                            try {

                                val response = RetrofitClient.api.updateProduto(p.id, body)


                                if (!response.isSuccessful) {
                                    Log.e("CHECKED", "Erro HTTP: ${response.code()}")
                                }


                            } catch (e: Exception){
                                Log.e("Update", "Update", e)
                            }

                        }
                    }
                )
                recycler.adapter = adapter
            }
            catch (e: Exception) {
                Log.e("API", "Erro ao buscar categorias", e)
            }
        }

        btnAdicionarProduto.setOnClickListener {

            val intent = Intent(this, AddProdutoActivity::class.java)
            intent.putExtra("id", categoriaId)
            startActivity(intent)
        }
    }
}