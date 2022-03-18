package com.shop.ui.home.goods

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.shop.model.Goods
import com.shop.model.GoodsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GoodsViewModel(
    private val goodsRepository: GoodsRepo = GoodsRepo
) : ViewModel() {

    var editGoods by mutableStateOf(Goods())
        private set
    var goodsList by mutableStateOf(listOf<Goods>())
    // todo
    var orderMap by mutableStateOf(mapOf<Goods, Int>())
    fun findEditGoods(goodsId: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            editGoods = withContext(Dispatchers.Main) {
                goodsRepository.findGoods(goodsId)
            }
        }
    }

    fun deleteGoods(goodsId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            goodsRepository.deleteGoods(goodsId = goodsId)
            loadGoodsList()
        }
    }

    fun loadGoodsList() {
        Log.d(TAG, "loadGoodsList: goods $this")
        viewModelScope.launch(Dispatchers.IO) {
            goodsList = withContext(Dispatchers.Main) {
                goodsRepository.getGoodsList()
            }
        }
    }

    fun editOrAddGoods(goods: Goods) {
        editGoods = Goods()
        viewModelScope.launch(Dispatchers.IO) {
            goodsRepository.editOrAddGoods(goods)
            loadGoodsList()
        }
    }

    companion object {
        private const val TAG = "GoodsViewModel"
        fun provideFactory(

        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return GoodsViewModel() as T
            }
        }
    }
}