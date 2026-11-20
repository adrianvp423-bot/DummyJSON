package com.sena.dummy.domain.UseCase

import com.sena.dummy.domain.model.ProductModel
import com.sena.dummy.domain.repository.ProductRepository
import jakarta.inject.Inject

class UpdateProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(product : ProductModel): ProductModel = repository.updateProduct(product)

}