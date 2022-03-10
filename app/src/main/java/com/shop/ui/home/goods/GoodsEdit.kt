package com.shop.ui.home.goods

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.statusBarsHeight
import com.shop.R

@Composable
fun GoodsEdit(
    goodsId: Int,
    viewModel: GoodsViewModel = viewModel(factory = GoodsViewModel.provideFactory()),
    upPress: @Composable () -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        upPress()
        Text(
            text = stringResource(id = R.string.paper_paper),
            modifier = Modifier.align(
                Alignment.Center
            )
        )
        ConstraintLayout(
            modifier = Modifier
                .statusBarsHeight()
                .padding(
                    bottom = 40.dp
                )
        ) {
            ColumnGoods(
                text = "12",
                modifier = Modifier,
                change = { name: String->
                }
            )
        }
        IconButton(
            onClick = {

            },
            modifier = Modifier.align(
                Alignment.BottomCenter
            )
        ) {
            Text(text = "sure")
        }
    }
}

@Composable
private fun <T> ColumnGoods(
    text: String,
    modifier: Modifier,
    change: (T) -> Unit
) {

}