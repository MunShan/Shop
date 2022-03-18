package com.shop.ui.home.goods

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shop.model.Goods
import com.shop.ui.home.DestinationBar
import com.shop.ui.theme.ShopTheme

private const val TAG = "GoodsEdit"

@Composable
fun GoodsEdit(
    goodsId: Int,
    viewModel: GoodsViewModel = viewModel(factory = GoodsViewModel.provideFactory()),
    upPress: () -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        DestinationBar(
            title = if (goodsId >= 0) "编辑" else "添加",
            upPress = upPress,
            imageVector = if (goodsId >= 0) Icons.Outlined.Delete else null,
            otherClickAction = {
                upPress()
                viewModel.deleteGoods(goodsId)
            }
        )
        viewModel.findEditGoods(goodsId)
        val goods = viewModel.editGoods
        val editState = rememberEditGoodsState(goods = goods)
        ConstraintLayout(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            val (name, price, count) = createRefs()
            TextField(
                value = editState.name,
                label = {
                    Text(text = "物名")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(name) {
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(parent.top, 0.dp)
                    }
                    .background(ShopTheme.colors.brandSecondary),
                shape = MaterialTheme.shapes.large,
                onValueChange = {
                    editState.name = it
                })
            TextField(
                value = editState.price,
                label = {
                    Text(text = "价格")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(price) {
                        start.linkTo(name.start, 0.dp)
                        top.linkTo(name.bottom, 12.dp)
                    }
                    .background(ShopTheme.colors.brandSecondary),
                shape = MaterialTheme.shapes.large,
                onValueChange = {
                    editState.price = it
                })
            TextField(
                value = editState.count,
                label = {
                    Text(text = "库存")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(count) {
                        start.linkTo(name.start, 0.dp)
                        top.linkTo(price.bottom, 12.dp)
                    }.background(ShopTheme.colors.brandSecondary),
                shape = MaterialTheme.shapes.large,
                onValueChange = {
                    editState.count = it
                })
        }
        IconButton(
            onClick = {
                upPress()
                viewModel.editOrAddGoods(editState.toGoods(goodsId))
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(bottom = 30.dp, end = 12.dp)
                .background(ShopTheme.colors.brandSecondary),
        ) {
            Text(
                modifier = Modifier.padding(4.dp),
                text = "保存"
            )
        }
    }
}

@Composable
private fun rememberEditGoodsState(
    goods: Goods
): GoodsEditState {
    return remember(goods) {
        GoodsEditState(
            goods.name,
            goods.price,
            goods.stock
        )
    }
}


@Stable
class GoodsEditState(
    name: String,
    price: Float,
    count: Int,
) {
    var name by mutableStateOf(TextFieldValue(name))
    var price by mutableStateOf(TextFieldValue(price.toString()))
    var count by mutableStateOf(TextFieldValue(count.toString()))
    fun toGoods(goodsId: Int) = Goods(
        goodsId = goodsId,
        name = name.text,
        price = price.text.toFloatOrNull() ?: 0f,
        stock = count.text.toIntOrNull() ?: 0
    )
}


@Preview
@Composable
fun PreGoodsEdit() {
    GoodsEdit(goodsId = 1) {

    }
}