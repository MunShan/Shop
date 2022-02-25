package com.shop.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.atomic.AtomicReference

@Database(entities = [Goods::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun goodsDao() : GoodsDao
    abstract fun recordDao() : RecordDao
}

private val tempDB = AtomicReference<AppDatabase>()

fun getDB(): AppDatabase? = tempDB.get()

fun Context.initDB(){
    var db = tempDB.get()
    if (db != null) {
        return
    }
    while (true) {
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "abc"
        ).build()
        tempDB.compareAndSet(null,db)
        db = tempDB.get()
        if (db != null) {
            return
        }
    }
}