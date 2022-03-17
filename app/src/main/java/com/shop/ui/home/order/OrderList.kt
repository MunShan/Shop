package com.shop.ui.home.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shop.ui.components.ShopDivider
import com.shop.ui.components.ShopSurface
import com.shop.ui.home.DestinationBar
import com.shop.ui.home.goods.GoodsViewModel
import com.shop.ui.home.record.RecordViewModel
import com.shop.ui.theme.ShopTheme

@Composable
fun OrderList(
    upPress: () -> Unit,
    viewModel: GoodsViewModel = viewModel(factory = GoodsViewModel.provideFactory()),
    recordViewModel: RecordViewModel = viewModel(factory = RecordViewModel.provideFactory())
) {
    ShopSurface(Modifier.fillMaxSize()) {
        Column {
            DestinationBar(
                title = "下单",
                upPress = upPress
            )
            val goodsMap = viewModel.orderMap
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                itemsIndexed(goodsMap.keys.toList()) { index, item ->
                    if (index > 0) {
                        ShopDivider(thickness = 2.dp)
                    }
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(text = item.name, modifier = Modifier.align(Alignment.Start))
                        Text(
                            text = "${goodsMap[item] ?: 0}×${item.price} = ${goodsMap[item] ?: 0 * item.price}",
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                }
            }
            IconButton(
                onClick = {
                    recordViewModel.addRecord(goodsMap)
                    goodsMap.clear()
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(bottom = 30.dp, end = 12.dp)
                    .background(ShopTheme.colors.brandSecondary),
            ) {
                Text(text = "下单 ${goodsMap.entries.fold(0f) { a, b -> a + b.key.price * b.value }}")
            }
        }
    }
}
