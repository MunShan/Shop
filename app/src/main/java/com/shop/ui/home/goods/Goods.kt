package com.shop.ui.home.goods

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.insets.statusBarsPadding
import com.shop.model.Goods
import com.shop.ui.components.ShopDivider
import com.shop.ui.components.ShopSurface
import com.shop.ui.home.DestinationBar
import com.shop.ui.theme.ShopTheme

@Composable
fun Goods(
    editGoods: (goodsId: Int?) -> Unit,
    viewModel: GoodsViewModel = viewModel(factory = GoodsViewModel.provideFactory())
) {
    ShopSurface(Modifier.fillMaxSize()) {
        Column {
            DestinationBar(
                title = "物品列表",
                otherClickAction = {
                    editGoods(-1)
                },
                imageVector = Icons.Default.Add
            )
            // todo
            viewModel.loadGoodsList()
            val goodsList = viewModel.goodsList
            GoodsList(
                editGoods,
                goodsList = goodsList
            )
        }
    }
}

@Composable
private fun GoodsList(
    editGoods: (goodsId: Int?) -> Unit,
    goodsList: List<Goods>,
) {
    LazyColumn {
        itemsIndexed(goodsList) { index, goods ->
            if (index > 0) {
                ShopDivider(thickness = 2.dp)
            }
            GoodsItem(goods, editGoods = editGoods)
        }
    }
}

@Composable
private fun GoodsItem(
    goods: Goods,
    editGoods: (goodsId: Int?) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (name, price, count, edit) = createRefs()
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
                editGoods(goods.goodsId)
            },
            modifier = Modifier.constrainAs(edit) {
                end.linkTo(parent.end, 12.dp)
                top.linkTo(parent.top, 16.dp)
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.Edit,
                tint = ShopTheme.colors.brand,
                contentDescription = "编辑"
            )
        }
    }
}