package com.shop.ui.home.goods

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.shop.model.Goods
import com.shop.model.GoodsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GoodsViewModel(
    private val goodsRepository: GoodsRepo = GoodsRepo
) : ViewModel() {

    private val editGoods = MutableStateFlow(Goods())

    fun findEditGoods(goodsId : Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            editGoods.value = goodsRepository.findGoods(goodsId)
        }
    }

    suspend fun editOrAddGoods(goods : Goods) {
        goodsRepository.editOrAddGoods(goods)
    }

    companion object {
        fun provideFactory(

        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return GoodsViewModel() as T
            }
        }
    }
}