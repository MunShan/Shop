package com.shop.ui.home.goods

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GoodsEdit(
    goodsId: Int,
    upPress: @Composable () -> Unit,
    viewModel: GoodsViewModel = viewModel(factory = GoodsViewModel.provideFactory())
) {
    Box(Modifier.fillMaxSize()) {
        upPress()
    }
}