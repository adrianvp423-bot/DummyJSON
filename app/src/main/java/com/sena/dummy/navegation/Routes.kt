package com.sena.dummy.navegation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    object ProductList

    @Serializable
    data class ProductDetail(val productId: Int)

    @Serializable
    data class ProductForm(val productId: Int? = null)
}
