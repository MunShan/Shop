package com.shop.model

import androidx.room.*

@Entity
data class Goods(
    @PrimaryKey
    @ColumnInfo(name = "goods_id")
    var goodsId: Int = 0,
    var uri: String? = null,
    var name: String = "",
    val stock: Int = 0,
    val price: Float = 0f,
)

@Dao
interface GoodsDao {
    @Query("SELECT * FROM Goods")
    suspend fun getAll(): List<Goods>

    @Query("SELECT * FROM Goods Where goods_id = :goodsId")
    suspend fun findGoods(goodsId: Int): Goods?

    @Query("DELETE FROM Goods WHERE goods_id = :goods")
    fun deleteGoods(goods: Int)

    @Update
    suspend fun editGoods(goods: Goods)

    @Insert
    suspend fun addGoods(goods: Goods)
}

object GoodsRepo {
    private val goodsDao = getDB()?.goodsDao()
    suspend fun getGoodsList() = goodsDao?.getAll() ?: listOf()

    suspend fun findGoods(goodsId: Int?): Goods {
        if (goodsId == null || goodsId < 0) {
            return Goods()
        }
        return goodsDao?.findGoods(goodsId) ?: Goods()
    }

    fun deleteGoods(goodsId: Int) {
        goodsDao?.deleteGoods(goodsId)
    }

    suspend fun editOrAddGoods(goods: Goods) {
        if (goods.goodsId <= 0) {
            goodsDao?.addGoods(goods)
        } else {
            goodsDao?.editGoods(goods)
        }
    }
}