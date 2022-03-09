package com.shop.ui.home.goods

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GoodsViewModel : ViewModel() {
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