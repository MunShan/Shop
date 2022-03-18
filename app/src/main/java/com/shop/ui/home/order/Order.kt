package com.shop.ui.home.order

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shop.model.Goods
import com.shop.ui.components.ShopDivider
import com.shop.ui.components.ShopSurface
import com.shop.ui.home.DestinationBar
import com.shop.ui.home.goods.GoodsViewModel
import com.shop.ui.home.record.RecordViewModel
import com.shop.ui.theme.ShopTheme

@Composable
fun Order(
    addGoods: () -> Unit,
    goodsViewModel: GoodsViewModel = viewModel(factory = GoodsViewModel.provideFactory()),
    recordViewModel: RecordViewModel = viewModel(factory = RecordViewModel.provideFactory())
) {
    var isOrder by remember { mutableStateOf(false) }
    ShopSurface(Modifier.fillMaxSize()) {
        Box(Modifier.fillMaxSize()) {
            GoodsList(addGoods, {
                isOrder = true
            }, goodsViewModel)
            if (isOrder) {
                var msg by remember {
                    mutableStateOf(TextFieldValue("订单"))
                }
                Column(
                    modifier = Modifier
                        .background(ShopTheme.colors.uiBorder)
                        .padding(8.dp)
                        .fillMaxWidth(0.7f)
                        .align(Alignment.Center)
                ) {
                    Icon(
                        modifier = Modifier.clickable {
                            isOrder = false
                        },
                        imageVector = Icons.Default.Close, contentDescription = ""
                    )
                    OutlinedTextField(
                        modifier = Modifier.padding(4.dp),
                        shape = MaterialTheme.shapes.large,
                        value = msg,
                        onValueChange = {
                            msg = it
                        }
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.End)
                            .clickable {
                                isOrder = false
                                recordViewModel.addRecord(
                                    msg.text,
                                    goodsViewModel.orderMap
                                )
                                goodsViewModel.orderMap = mapOf()
                            },
                        text = "确定"
                    )
                }
            }
        }
    }
}

@Composable
fun GoodsList(
    addGoods: () -> Unit,
    order: () -> Unit,
    goodsViewModel: GoodsViewModel
) {
    // todo
    goodsViewModel.loadGoodsList()
    val goodsList = goodsViewModel.goodsList
    val orderMap = goodsViewModel.orderMap
    Column {
        DestinationBar(
            title = "订单"
        )
        if (goodsList.isEmpty()) {
            IconButton(
                onClick = addGoods,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = "添加物品"
                )
            }
            return@Column
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            itemsIndexed(goodsList) { index, item ->
                if (index > 0) {
                    ShopDivider(thickness = 2.dp)
                }
                GoodsItem(item, orderMap[item] ?: 0, incClick = { goods ->
                    val map = orderMap.toMutableMap()
                    map[goods] = (map[goods] ?: 0) + 1
                    goodsViewModel.orderMap = map
                }, decClick = { goods ->
                    val map = orderMap.toMutableMap()
                    map[goods] = (map[goods] ?: 1).coerceAtLeast(1) - 1
                    goodsViewModel.orderMap = map
                })
            }
        }
        ShopDivider(thickness = 2.dp)
        IconButton(
            onClick = order,
            modifier = Modifier
                .align(Alignment.End)
                .padding(bottom = 4.dp, end = 12.dp),
        ) {
            Text(
                modifier = Modifier.padding(4.dp),
                text = "下单 ${orderMap.entries.fold(0f) { a, b -> a + b.key.price * b.value }}"
            )
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
            .padding(1.dp)
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
                imageVector = Icons.Default.Add,
                tint = ShopTheme.colors.brand,
                contentDescription = "加入"
            )
        }
        Text(text = "$goodsSum",
            modifier = Modifier.constrainAs(sum) {
                end.linkTo(inc.start, 1.dp)
                top.linkTo(inc.top)
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
                imageVector = Icons.Default.Remove,
                tint = ShopTheme.colors.brand,
                contentDescription = "减少"
            )
        }
    }
}