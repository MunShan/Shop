package com.shop

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.shop.ui.components.ShopScaffold
import com.shop.ui.home.HomeSections
import com.shop.ui.home.ShopBottomBar
import com.shop.ui.home.addHomeGraph
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
                    shopNavGraph(upPress = appState::upPress)
                }
            }
        }
    }
}

private fun NavGraphBuilder.shopNavGraph(
    upPress: () -> Unit
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.ORDER.route
    ) {
        addHomeGraph()
    }
}