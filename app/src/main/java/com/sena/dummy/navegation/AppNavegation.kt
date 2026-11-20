package com.sena.dummy.navegation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sena.dummy.ui.screen.ProductDetailScreen
import com.sena.dummy.ui.screen.ProductFormScreen
import com.sena.dummy.ui.screen.ProductScreen

@Composable
fun AppNavegation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.ProductList,
        modifier = modifier
    ) {
        composable<Routes.ProductList> {
            ProductScreen(
                onProductClick = { productId ->
                    navController.navigate(Routes.ProductDetail(productId = productId))
                },
                onAddProductClick = {
                    navController.navigate(Routes.ProductForm())
                }
            )
        }

        composable<Routes.ProductDetail> { backStackEntry ->
            val route: Routes.ProductDetail = backStackEntry.toRoute()
            ProductDetailScreen(
                productId = route.productId,
                onBack = { navController.popBackStack() },
                onEditProduct = { id ->
                    navController.navigate(Routes.ProductForm(productId = id))
                }
            )
        }

        composable<Routes.ProductForm> { backStackEntry ->
            val route: Routes.ProductForm = backStackEntry.toRoute()
            ProductFormScreen(
                productId = route.productId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
