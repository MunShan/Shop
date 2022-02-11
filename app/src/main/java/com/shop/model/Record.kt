package com.shop.model

import java.util.*

data class Record(
    val id : Int,
//    var isPush : Boolean = false,
    var isReserve : Boolean = false,
    val recordTime : Date,
    val msg : String? = null,
)

data class RecordItem(
    val id : Int,
    val goodsName : String,
    var nums : Int = 0,
    var price : Float = 0f,
)
