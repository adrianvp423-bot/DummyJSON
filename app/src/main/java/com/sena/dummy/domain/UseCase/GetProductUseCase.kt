package com.sena.dummy.domain.UseCase

import com.sena.dummy.domain.model.ProductModel
import com.sena.dummy.domain.repository.ProductRepository
import jakarta.inject.Inject

class GetProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): List<ProductModel> = repository.getProducts()
    suspend fun byId(id: Int): ProductModel = repository.getProductById(id)

}