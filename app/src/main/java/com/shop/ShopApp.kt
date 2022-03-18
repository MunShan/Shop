package com.shop

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.shop.ui.components.ShopScaffold
import com.shop.ui.home.HomeSections
import com.shop.ui.home.ShopBottomBar
import com.shop.ui.home.addHomeGraph
import com.shop.ui.home.goods.GoodsEdit
import com.shop.ui.home.record.RecordDetails
import com.shop.ui.theme.ShopTheme


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
                        clickRecordDetails = appState::navigateToRecordDetails,
                        upPress = appState::upPress,
                        clickOrder = appState::navigateOrderList
                    )
                }
            }
        }
    }
}

private fun NavGraphBuilder.shopNavGraph(
    editGoods: (Int?, NavBackStackEntry) -> Unit,
    clickRecordDetails: (Int, NavBackStackEntry) -> Unit,
    clickOrder: (NavBackStackEntry) -> Unit,
    upPress: () -> Unit
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.ORDER.route
    ) {
        addHomeGraph(editGoods, clickRecordDetails)
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
            goodsId = goodsId,
            upPress = upPress
        )
    }
    composable(
        "${MainDestinations.RECORD_DETAILS_ROUTE}/{${MainDestinations.RECORD_ID_KEY}}",
        arguments = listOf(
            navArgument(MainDestinations.RECORD_ID_KEY) {
                type = NavType.IntType
            }
        )
    ) {
        val recordId = it.arguments?.getInt(MainDestinations.RECORD_ID_KEY) ?: -1
        RecordDetails(
            recordId = recordId,
            upPress = upPress
        )
    }
}
