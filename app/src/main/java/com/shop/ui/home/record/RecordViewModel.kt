package com.shop.ui.home.record

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.shop.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class RecordViewModel(private val repo: RecordRepo = RecordRepo) : ViewModel() {
    init {
        loadRecordList()
    }

    var recordList by mutableStateOf(listOf<Record>())
    var recordItemList by mutableStateOf(listOf<RecordItem>())

    private fun loadRecordList() {
        viewModelScope.launch(Dispatchers.IO) {
            recordList = withContext(Dispatchers.Main) {
                repo.getAllRecord()
            }
        }
    }

    fun loadRecordItemList(recordId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            recordItemList = withContext(Dispatchers.Main) {
                repo.getRecordDetails(recordId)
            }
        }
    }

    fun updateRecordStatus(record: Record) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.changeRecordStatus(record)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun addRecord(msg : String, recordMap: Map<Goods, Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            val time = SimpleDateFormat("yyyy年MM月dd日").format(Date())
            val record = Record(
                id = 0,
                recordTime = time,
                msg = msg,
                sumPrices = recordMap.entries.fold(0f) { a, b -> a + b.key.price * b.value }
            )
            val recordId = repo.addRecord(record)
            if (recordId == 0L) {
                return@launch
            }
            val list = recordMap.map {
                RecordItem(
                    0,
                    recordId.toInt(),
                    it.key.name,
                    it.value,
                    it.key.price
                )
            }
            repo.addRecordItem(list)
            recordMap.keys.map {
                it.stock = (it.stock - (recordMap[it]?:0)).coerceAtLeast(0)
                it
            }.let {
                GoodsRepo.updateGoods(it)
            }
        }
    }

    companion object {
        fun provideFactory(

        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RecordViewModel() as T
            }
        }
    }
}