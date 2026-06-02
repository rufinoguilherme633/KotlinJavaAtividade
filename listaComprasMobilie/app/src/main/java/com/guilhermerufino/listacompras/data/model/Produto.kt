package com.guilhermerufino.listacompras.data.model

import android.R

data class Produto(

    val id:Long,
    var nome:String,
    var checked: Boolean,
    var categoria: Long
)
