package com.shop.model

import androidx.room.*

@Entity
data class Goods(
    @PrimaryKey
    @ColumnInfo(name = "goods_id")
    var goodsId : Int = 0,
    var uri : String? = null,
    val name : String,
    val stock : Int = 0,
    val price : Float = 0f,
)

@Dao
interface GoodsDao{
    @Query("SELECT * FROM Goods")
    suspend fun getAll() : List<Goods>
    @Delete
    suspend fun deleteGoods(goods: Goods)
    @Update
    suspend fun editGoods(goods: Goods)
    @Insert
    suspend fun addGoods(goods: Goods)
}

object GoodsRepo {
    fun getGoodsList() {

    }
    fun editGoods() {

    }
    fun deleteGoods() {

    }
    fun addGoods() {

    }
}