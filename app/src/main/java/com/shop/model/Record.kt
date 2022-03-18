package com.shop.model

import androidx.room.*
import java.util.*

@Entity
data class Record(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
//    var isPush : Boolean = false,
    var given: Boolean = false,
    // yyyy-mm-dd
    @ColumnInfo(name = "record_time")
    val recordTime: String,
    val msg: String? = null,
    @ColumnInfo(name = "sum_prices")
    val sumPrices: Float = 0f,
)

@Entity(tableName = "record_item")
data class RecordItem(
    @PrimaryKey(autoGenerate = true)
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

    @Insert
    fun addRecord(record: Record) : Long

    @Insert
    suspend fun addRecordItem(recordItems : List<RecordItem>)
}

object RecordRepo {
    private val recordDao = getDB()?.recordDao()

    suspend fun changeRecordStatus(record: Record) {
        recordDao?.changeRecordStatus(record = record)
    }

    suspend fun getAllRecord() = recordDao?.getAllRecord() ?: listOf()

    suspend fun getRecordDetails(recordId: Int) = recordDao?.recordDetails(recordId) ?: listOf()

    fun addRecord(record: Record) = recordDao?.addRecord(record) ?: 0

    suspend fun addRecordItem(recordItems: List<RecordItem>) = recordDao?.addRecordItem(recordItems)

}