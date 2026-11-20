package com.sena.dummy.ui.state

import com.sena.dummy.domain.model.ProductModel

data class ProductUIState (
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val products: List<ProductModel> = emptyList(),
    val selectedProduct: ProductModel? = null,
    val errorMessage: String? = null,
    val successMessage: String? = null
)