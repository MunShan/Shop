package com.shop.model

import androidx.room.*
import java.util.*

@Entity
data class Record(
    @PrimaryKey
    val id: Int,
//    var isPush : Boolean = false,
    var status: Boolean = false,
    // yyyy-mm-dd
    @ColumnInfo(name = "record_time")
    val recordTime: String,
    val msg: String? = null,
)

@Entity(tableName = "record_item")
data class RecordItem(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "record_id")
    val recordId: Int,
    @ColumnInfo(name = "goods_name")
    val goodsName: String,
    var nums: Int = 0,
    var price: Float = 0f,
)

@Dao
interface RecordDao {
    @Query("SELECT * FROM Record")
    suspend fun getAllRecord(): List<Record>

    @Update
    suspend fun changeRecordStatus(record: Record)

    @Query("SELECT * FROM record_item where record_id = :recordId")
    suspend fun recordDetails(recordId: Int): List<RecordItem>
}