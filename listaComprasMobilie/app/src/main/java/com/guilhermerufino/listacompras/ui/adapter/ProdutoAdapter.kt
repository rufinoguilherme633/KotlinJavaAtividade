package com.guilhermerufino.listacompras.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.guilhermerufino.listacompras.R
import com.guilhermerufino.listacompras.data.model.Produto
import com.guilhermerufino.listacompras.data.model.ProdutoRequestUpdate

class ProdutoAdapter(
    private val lista: MutableList<ProdutoRequestUpdate>,
    private val onEditar: (ProdutoRequestUpdate) -> Unit,
    private val onExcluir: (ProdutoRequestUpdate) -> Unit,
    private val checked: (ProdutoRequestUpdate) -> Unit) : RecyclerView.Adapter<ProdutoAdapter.ViewHolder>()
{

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nome = view.findViewById<TextView>(R.id.txtNome)
        val editar = view.findViewById<Button>(R.id.btnEditarProduto)
        val excluir = view.findViewById<Button>(R.id.btnExcluirProduto)
        val checkd = view.findViewById<CheckBox>(R.id.checkBoxProduto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_produto, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]

        holder.nome.text = item.nome

        holder.checkd.setOnCheckedChangeListener(null)

        holder.checkd.isChecked = item.checked
        
        holder.editar.setOnClickListener {
            onEditar(item)
        }

        holder.excluir.setOnClickListener {
            onExcluir(item)
        }

        holder.checkd.setOnCheckedChangeListener { _, isChecked  ->

            item.checked = isChecked
            checked(item)
        }

    }
}