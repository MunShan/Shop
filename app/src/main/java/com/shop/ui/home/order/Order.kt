package com.shop.ui.home.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ReduceCapacity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shop.model.Goods
import com.shop.ui.components.ShopDivider
import com.shop.ui.components.ShopSurface
import com.shop.ui.home.DestinationBar
import com.shop.ui.home.goods.GoodsViewModel
import com.shop.ui.theme.ShopTheme

@Composable
fun Order(
    clickOrder: () -> Unit,
    goodsViewModel: GoodsViewModel = viewModel(factory = GoodsViewModel.provideFactory())
) {
    val goodsList = goodsViewModel.goodsList
    val orderMap = goodsViewModel.orderMap
    ShopSurface(Modifier.fillMaxSize()) {
        Column {
            DestinationBar(
                title = "订单"
            )
            LazyColumn {
                itemsIndexed(goodsList) { index, item ->
                    if (index > 0) {
                        ShopDivider(thickness = 2.dp)
                    }
                    GoodsItem(item, orderMap[item] ?: 0, incClick = { goods ->
                        orderMap[goods] = (orderMap[goods] ?: 0) + 1
                    }, decClick = { goods ->
                        orderMap[goods] = (orderMap[goods] ?: 1).coerceAtLeast(1) - 1
                    })
                }
            }
            IconButton(
                onClick = clickOrder,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(bottom = 30.dp, end = 12.dp)
                    .background(ShopTheme.colors.brandSecondary),
            ) {
                Text(text = "下单 ${orderMap.entries.fold(0f) { a, b -> a + b.key.price * b.value }}")
            }
        }
    }
}

@Composable
private fun GoodsItem(
    goods: Goods,
    goodsSum: Int,
    incClick: (goods: Goods) -> Unit,
    decClick: (goods: Goods) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxWidth(1f)
    ) {
        val (name, price, count, dec, sum, inc) = createRefs()
        Text(
            text = "物名： ${goods.name}",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.constrainAs(name) {
                start.linkTo(parent.start, 16.dp)
                top.linkTo(parent.top, 12.dp)
            }
        )
        Text(
            text = "价格: ${goods.price}",
            modifier = Modifier.constrainAs(price) {
                start.linkTo(name.start, 0.dp)
                top.linkTo(name.bottom, 5.dp)
            }
        )
        Text(text = "库存： ${goods.stock}",
            modifier = Modifier.constrainAs(count) {
                start.linkTo(price.start, 0.dp)
                top.linkTo(price.bottom, 5.dp)
            }
        )
        IconButton(
            onClick = {
                incClick(goods)
            },
            modifier = Modifier.constrainAs(inc) {
                end.linkTo(parent.end, 12.dp)
                bottom.linkTo(parent.bottom, 16.dp)
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                tint = ShopTheme.colors.brand,
                contentDescription = "加入"
            )
        }
        Text(text = "$goodsSum",
            modifier = Modifier.constrainAs(sum) {
                end.linkTo(inc.start, 1.dp)
                bottom.linkTo(inc.bottom)
            }
        )
        IconButton(
            onClick = {
                decClick(goods)
            },
            modifier = Modifier.constrainAs(dec) {
                end.linkTo(sum.start, 1.dp)
                bottom.linkTo(inc.bottom)
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.ReduceCapacity,
                tint = ShopTheme.colors.brand,
                contentDescription = "减少"
            )
        }
    }
}