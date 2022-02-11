package com.shop.model

import android.net.Uri

data class Goods(
    val goodsId : Int,
    var uri : Uri? = null,
    val name : String,
    val stock : Int = 0,
    val price : Float = 0f,
)
