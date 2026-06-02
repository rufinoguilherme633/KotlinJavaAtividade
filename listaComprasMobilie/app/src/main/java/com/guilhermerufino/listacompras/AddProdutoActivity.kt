package com.guilhermerufino.listacompras

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.guilhermerufino.listacompras.data.model.CategoriaRequest
import com.guilhermerufino.listacompras.data.model.ProdutoRequest
import com.guilhermerufino.listacompras.data.model.ProdutoRequestUpdate
import com.guilhermerufino.listacompras.data.remote.RetrofitClient
import kotlinx.coroutines.launch

class AddProdutoActivity : AppCompatActivity() {
    private var categoriaId: Long = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoriaId = intent.getLongExtra("id", -1)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_produto)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        val btnAdicionar = findViewById<Button>(R.id.btnAdicionar)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)
        val editText = findViewById<EditText>(R.id.editTextText)

        btnAdicionar.setOnClickListener {

            val novaProduto  = ProdutoRequest(editText.text.toString(),categoriaId)
            lifecycleScope.launch {

                try {
                    RetrofitClient.api.salvarProdutos( novaProduto)
                    Log.d("CADASTRO","castro")
                    val intent = Intent(this@AddProdutoActivity, Produto::class.java)
                    intent.putExtra("id", categoriaId)
                    startActivity(intent)

                } catch (e: Exception){
                    Log.e("CADASTRO", "Update ao deletar", e)
                }
            }
        }


        btnCancelar.setOnClickListener {

            val intent = Intent(this, Produto::class.java)
            intent.putExtra("id", categoriaId)
            startActivity(intent)
        }
    }

}