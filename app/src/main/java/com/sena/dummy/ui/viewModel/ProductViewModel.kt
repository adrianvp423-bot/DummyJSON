package com.sena.dummy.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sena.dummy.domain.model.ProductModel
import com.sena.dummy.domain.repository.ProductRepository
import com.sena.dummy.ui.state.ProductUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductUIState())
    val uiState: StateFlow<ProductUIState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            runCatching { repository.getProducts() }
                .onSuccess { list ->
                    _uiState.update { it.copy(isLoading = false, products = list) }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = e.message ?: "No se pudieron cargar los productos."
                        )
                    }
                }
        }
    }

    fun loadProductById(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            runCatching { repository.getProductById(id) }
                .onSuccess { product ->
                    _uiState.update { it.copy(isLoading = false, selectedProduct = product) }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = e.message ?: "No se pudo cargar el producto con ID $id."
                        )
                    }
                }
        }
    }

    fun createProduct(product: ProductModel) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, errorMessage = null, successMessage = null) }
            runCatching { repository.createProduct(product) }
                .onSuccess { created ->
                    _uiState.update { state ->
                        state.copy(
                            isSaving = false,
                            products = listOf(created) + state.products,
                            successMessage = "Producto creado exitosamente."
                        )
                    }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            errorMessage = e.message ?: "No se pudo crear el producto."
                        )
                    }
                }
        }
    }

    fun updateProduct(product: ProductModel) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, errorMessage = null, successMessage = null) }
            runCatching { repository.updateProduct(product) }
                .onSuccess { updated ->
                    _uiState.update { state ->
                        val updatedList = state.products.map { if (it.id == updated.id) updated else it }
                        state.copy(
                            isSaving = false,
                            products = updatedList,
                            selectedProduct = if (state.selectedProduct?.id == updated.id) updated else state.selectedProduct,
                            successMessage = "Producto actualizado exitosamente."
                        )
                    }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            errorMessage = e.message ?: "No se pudo actualizar el producto."
                        )
                    }
                }
        }
    }

    fun deleteProduct(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, errorMessage = null, successMessage = null) }
            runCatching { repository.deleteProduct(id) }
                .onSuccess {
                    _uiState.update { state ->
                        state.copy(
                            isSaving = false,
                            successMessage = "Producto eliminado exitosamente."
                        )
                    }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            errorMessage = e.message ?: "No se pudo eliminar el producto."
                        )
                    }
                }
        }
    }

    fun selectProduct(product: ProductModel?) {
        _uiState.update { it.copy(selectedProduct = product) }
    }

    fun clearMessages() {
        _uiState.update { it.copy(errorMessage = null, successMessage = null) }
    }
}
