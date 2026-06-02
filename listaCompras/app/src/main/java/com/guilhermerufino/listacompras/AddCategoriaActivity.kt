package com.guilhermerufino.listacompras

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.guilhermerufino.listacompras.data.model.Categoria
import com.guilhermerufino.listacompras.data.model.CategoriaRequest
import com.guilhermerufino.listacompras.data.remote.RetrofitClient
import kotlinx.coroutines.launch

class AddCategoriaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_categoria)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val btnAdicionar = findViewById<Button>(R.id.btnAdicionar)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)
        val editText = findViewById<EditText>(R.id.editTextText)

        btnAdicionar.setOnClickListener {

            val novaCategoria  = CategoriaRequest(editText.text.toString())
            lifecycleScope.launch {

                try {
                    RetrofitClient.api.salvar(novaCategoria)
                    Log.d("CADASTRO","castro")
                    val intent = Intent(this@AddCategoriaActivity, MainActivity::class.java)

                    startActivity(intent)

                } catch (e: Exception){
                    Log.e("CADASTRO", "Update ao deletar", e)
                }
            }
        }


        btnCancelar.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
        }
    }
}