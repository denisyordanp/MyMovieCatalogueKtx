package com.denisyordanp.mymoviecatalogue.ui.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.denisyordanp.mymoviecatalogue.tools.currentActiveRoute
import com.denisyordanp.mymoviecatalogue.ui.MainDestinations

@Composable
fun GeneralBottomBar(
    mainNavController: NavController,
) {
    val navBackStackEntry =
        mainNavController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.currentActiveRoute

    if (currentRoute != null && currentRoute != MainDestinations.DETAIL_SCREEN) {
        BottomNavigation {
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    mainNavController.navigate(MainDestinations.MAIN_SCREEN)
                },
                content = {
                    val isActive = currentRoute == MainDestinations.MAIN_SCREEN
                    Icon(
                        modifier = Modifier
                            .alpha(if (isActive) 1f else 0.7f),
                        imageVector = Icons.Default.Home, contentDescription = null
                    )
                })
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    mainNavController.navigate(MainDestinations.FAVORITE_SCREEN)
                },
                content = {
                    val isActive = currentRoute == MainDestinations.FAVORITE_SCREEN
                    Icon(
                        modifier = Modifier
                            .alpha(if (isActive) 1f else 0.7f),
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null
                    )
                }
            )
        }
    }
}
