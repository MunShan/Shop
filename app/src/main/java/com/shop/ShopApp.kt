package com.shop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.insets.systemBarsPadding
import com.shop.ui.components.ShopScaffold
import com.shop.ui.home.HomeSections
import com.shop.ui.home.ShopBottomBar
import com.shop.ui.home.addHomeGraph
import com.shop.ui.home.goods.GoodsEdit
import com.shop.ui.theme.Neutral8
import com.shop.ui.theme.ShopTheme
import com.shop.ui.utils.mirroringBackIcon

@Composable
fun ShopApp() {
    ProvideWindowInsets {
        ShopTheme {
            val appState = rememberShopAppState()
            ShopScaffold(
                bottomBar = {
                    if (appState.shouldShowBottomBar) {
                        ShopBottomBar(
                            tabs = appState.bottomBarTabs,
                            currentRoute = appState.currentRoute ?: "",
                            navigateToRoute = appState::navigateToBottomBarRoute
                        )
                    }
                },
                snackbarHost = {
                    SnackbarHost(
                        hostState = it,
                        modifier = Modifier.systemBarsPadding(),
                        snackbar = { sandbarData ->
                            Snackbar(
                                snackbarData = sandbarData,
                                backgroundColor = ShopTheme.colors.uiBackground,
                                contentColor = ShopTheme.colors.textSecondary,
                                actionColor = ShopTheme.colors.brand,
                                elevation = 6.dp
                            )
                        }
                    )
                },
                scaffoldState = appState.scaffoldState
            ) { innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = MainDestinations.HOME_ROUTE,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    shopNavGraph(
                        editGoods = appState::navigateToGoodsEdit,
                        upPress = appState::upPress
                    )
                }
            }
        }
    }
}

private fun NavGraphBuilder.shopNavGraph(
    editGoods: (Int?,NavBackStackEntry)-> Unit,
    upPress: () -> Unit
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.ORDER.route
    ) {
        addHomeGraph(editGoods)
    }
    composable(
        "${MainDestinations.GOODS_EDIT_ROUTE}/{${MainDestinations.GOODS_EDIT_KEY}}",
        arguments = listOf(
            navArgument(MainDestinations.GOODS_EDIT_KEY) {
                type = NavType.IntType
            }
        )
    ) {
        val goodsId = it.arguments?.getInt(MainDestinations.GOODS_EDIT_KEY) ?: -1
        GoodsEdit(
            goodsId = goodsId
        ) {
            Up(upPress)
        }
    }
    composable(
        MainDestinations.ORDER_LIST_ROUTE
    ) {

    }
    composable(
        MainDestinations.RECORD_DETAILS_ROUTE
    ) {

    }
}

@Composable
private fun Up(upPress: () -> Unit) {
    IconButton(
        onClick = upPress,
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .size(36.dp)
            .background(
                color = Neutral8.copy(alpha = 0.32f),
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = mirroringBackIcon(),
            tint = ShopTheme.colors.iconInteractive,
            contentDescription = stringResource(R.string.label_back)
        )
    }
}
