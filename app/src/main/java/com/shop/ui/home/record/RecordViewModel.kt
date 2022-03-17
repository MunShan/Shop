package com.shop.ui.home.record

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.shop.model.Goods
import com.shop.model.Record
import com.shop.model.RecordItem
import com.shop.model.RecordRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    fun loadRecordItemList(recordId : Int) {
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

    fun addRecord(recordMap : Map<Goods,Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            val record = Record(
                id = 0,
                recordTime = "2002-01-02"
            )
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