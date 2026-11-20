package com.sena.dummy.data.repository

import com.sena.dummy.data.mapper.toDomain
import com.sena.dummy.data.remote.api.ProductService
import com.sena.dummy.data.remote.dto.product.UpdateProductRequest
import com.sena.dummy.domain.model.ProductModel
import com.sena.dummy.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductService
) : ProductRepository {

    override suspend fun getProducts(): List<ProductModel> =
        api.getProducts().products.map { it.toDomain() }

    override suspend fun getProductById(id: Int): ProductModel =
        api.getProductById(id).toDomain()

    override suspend fun createProduct(product: ProductModel): ProductModel =
        api.createProduct(product.toRequest()).toDomain()

    override suspend fun updateProduct(product: ProductModel): ProductModel =
        api.updateProduct(product.id, product.toRequest()).toDomain()

    // La API no elimina realmente el producto; solo simula la operación.
    // Aquí únicamente validamos si la respuesta fue exitosa o no.
    override suspend fun deleteProduct(id: Int): Boolean {
        val response = api.deleteProductById(id)
        if (!response.isSuccessful) {
            throw Exception("No se pudo eliminar el producto (código ${response.code()}).")
        }
        return true
    }


    private fun ProductModel.toRequest() = UpdateProductRequest(

        title = title,
        description = description,
        category = category,
        price = price
    )

}
