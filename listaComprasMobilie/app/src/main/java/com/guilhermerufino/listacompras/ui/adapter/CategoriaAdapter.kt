package com.guilhermerufino.listacompras.ui.adapter
import com.guilhermerufino.listacompras.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.guilhermerufino.listacompras.data.model.Categoria

class CategoriaAdapter(
                    private val lista: MutableList<Categoria>,
                    private val onEditar: (Categoria) -> Unit,
                    private val onExcluir: (Categoria) -> Unit,
                    private val onClickItem: (Categoria) -> Unit
)  : RecyclerView.Adapter<CategoriaAdapter.ViewHolder>()
{


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nome = view.findViewById<TextView>(R.id.txtNome)
        val editar = view.findViewById<Button>(R.id.btnEditarProduto)
        val excluir = view.findViewById<Button>(R.id.btnExcluirProduto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_categoria, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]

        holder.nome.text = item.nome

        holder.itemView.setOnClickListener {
            onClickItem(item)
        }

        holder.editar.setOnClickListener {
            onEditar(item)
        }

        holder.excluir.setOnClickListener {
            onExcluir(item)
        }
    }
}