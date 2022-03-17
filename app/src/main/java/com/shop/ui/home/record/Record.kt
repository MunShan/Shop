package com.shop.ui.home.record

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shop.model.Record
import com.shop.ui.components.ShopDivider
import com.shop.ui.components.ShopSurface
import com.shop.ui.home.DestinationBar
import com.shop.ui.theme.ShopTheme
import com.shop.ui.utils.mirroringBackIcon

@Composable
fun Record(
    clickDetails: (recordId: Int) -> Unit,
    recordViewModel: RecordViewModel = viewModel(factory = RecordViewModel.provideFactory())
) {
    ShopSurface(Modifier.fillMaxSize()) {
        val recordList = recordViewModel.recordList
        Column {
            DestinationBar(
                title = "记录列表"
            )
            LazyColumn {
                itemsIndexed(recordList) { index, item ->
                    if (index > 0) {
                        ShopDivider(thickness = 2.dp)
                    }
                    RecordItem(
                        item,
                        clickDetails
                    ) {
                        recordViewModel.updateRecordStatus(item)
                    }
                }
            }
        }
    }
}

@Composable
private fun RecordItem(
    record: Record,
    clickDetails: (recordId: Int) -> Unit,
    changeRecordStatus: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                clickDetails(record.id)
            }
    ) {
        val (msg, recordTime, sumPrices, given) = createRefs()
        Text(text = record.msg ?: "订单", modifier = Modifier
            .constrainAs(msg) {
                start.linkTo(parent.start, 12.dp)
                top.linkTo(parent.top, 8.dp)
            }
            .fillMaxWidth(0.6f))
        if (!record.given) {
            IconButton(
                onClick = changeRecordStatus,
                modifier = Modifier.constrainAs(given) {
                    end.linkTo(parent.end, 12.dp)
                    top.linkTo(parent.top, 8.dp)
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.AccessTime,
                    tint = ShopTheme.colors.brand,
                    contentDescription = ""
                )
            }
        }
        Text(text = record.recordTime, modifier = Modifier.constrainAs(recordTime) {
            start.linkTo(msg.start, 0.dp)
            top.linkTo(msg.bottom, 6.dp)
        })
        Text(text = "总价: ${record.sumPrices}", modifier = Modifier.constrainAs(sumPrices) {
            start.linkTo(msg.start, 0.dp)
            top.linkTo(recordTime.bottom, 6.dp)
        })

    }
}