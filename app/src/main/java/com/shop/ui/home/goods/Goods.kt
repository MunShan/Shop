package com.shop.ui.home.goods

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsHeight
import com.shop.model.Goods
import com.shop.ui.components.ShopDivider
import com.shop.ui.components.ShopSurface
import com.shop.ui.home.DestinationBar

@Composable
fun Goods(modifier: Modifier = Modifier) {
    ShopSurface(modifier.fillMaxSize()) {
        Box {
            DestinationBar()
        }
    }
}

@Composable
private fun GoodsList(
    goodsList: List<Goods>,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        LazyColumn {
            item {
                Spacer(modifier = Modifier.statusBarsHeight(additional = 56.dp))
            }
            itemsIndexed(goodsList) { index, goods ->
                if (index > 0) {
                    ShopDivider(thickness = 2.dp)
                }
                GoodsItem(goods)
            }
        }
    }
}

@Composable
private fun GoodsItem(
    goods: Goods
) {

}