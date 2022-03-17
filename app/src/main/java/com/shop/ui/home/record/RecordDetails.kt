package com.shop.ui.home.record

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shop.model.RecordItem
import com.shop.ui.components.ShopDivider
import com.shop.ui.components.ShopSurface
import com.shop.ui.home.DestinationBar

@Composable
fun RecordDetails(
    recordId: Int,
    upPress: () -> Unit,
    recordViewModel: RecordViewModel = viewModel(factory = RecordViewModel.provideFactory())
) {
    recordViewModel.loadRecordItemList(recordId = recordId)
    ShopSurface(Modifier.fillMaxSize()) {
        val recordItemList = recordViewModel.recordItemList
        Column {
            DestinationBar(
                title = "记录详细",
                upPress = upPress
            )
            LazyColumn {
                itemsIndexed(recordItemList) { index, item ->
                    if (index > 0) {
                        ShopDivider(thickness = 2.dp)
                    }
                    RecordItem(item)
                }
            }
        }
    }
}

@Composable
private fun RecordItem(recordItem: RecordItem) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (name, price) = createRefs()
        Text(
            text = "物名： ${recordItem.goodsName}",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.constrainAs(name) {
                start.linkTo(parent.start, 16.dp)
                top.linkTo(parent.top, 12.dp)
            }
        )
        Text(
            text = "价格: ${recordItem.nums}×${recordItem.price} = ${recordItem.nums * recordItem.price}",
            modifier = Modifier.constrainAs(price) {
                start.linkTo(name.start, 0.dp)
                top.linkTo(name.bottom, 5.dp)
            }
        )
    }
}