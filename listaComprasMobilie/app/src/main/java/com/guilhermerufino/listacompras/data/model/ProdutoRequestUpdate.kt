package com.guilhermerufino.listacompras.data.model

import android.R

data class ProdutoRequestUpdate(
    var id: Long,
    var nome:String,

    var checked: Boolean
)
