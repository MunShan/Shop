package com.shop.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import com.shop.ui.components.ShopDivider
import com.shop.ui.theme.AlphaNearOpaque
import com.shop.ui.theme.ShopTheme
import com.shop.ui.utils.mirroringBackIcon


@Composable
fun DestinationBar(
    modifier: Modifier = Modifier,
    title: String = "",
    upPress: (() -> Unit)? = null,
    otherClickAction: (() -> Unit)? = null,
    imageVector: ImageVector? = Icons.Outlined.ExpandMore
) {
    Column(modifier = modifier.statusBarsPadding()) {
        TopAppBar(
            backgroundColor = ShopTheme.colors.uiBackground.copy(alpha = AlphaNearOpaque),
            contentColor = ShopTheme.colors.textSecondary,
            elevation = 0.dp
        ) {
            if (upPress != null) {
                IconButton(
                    onClick = upPress,
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = mirroringBackIcon(),
                        tint = ShopTheme.colors.brand,
                        contentDescription = ""
                    )
                }
            }
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle1,
                color = ShopTheme.colors.textSecondary,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )
            if (otherClickAction != null && imageVector != null) {
                IconButton(
                    onClick = otherClickAction,
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = imageVector,
                        tint = ShopTheme.colors.brand,
                        contentDescription = ""
                    )
                }
            }
        }
        ShopDivider()
    }
}
