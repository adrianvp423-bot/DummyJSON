package com.sena.dummy.data.remote.api

import com.sena.dummy.data.remote.dto.product.PatchProduct
import com.sena.dummy.data.remote.dto.product.ProductListResponse
import com.sena.dummy.data.remote.dto.product.ProductX
import com.sena.dummy.data.remote.dto.product.UpdateProductRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductService {
    @GET("products")
    suspend fun getProducts(): ProductListResponse

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): ProductX

    @DELETE("products/{id}")
    suspend fun deleteProductById(
        @Path("id") id: Int
    ): Response<ResponseBody>

    @PATCH("products/{id}")
    suspend fun patchProductById(
        @Path("id") id: Int,
        @Body product: PatchProduct
    ): ProductX

    @PUT("products/{id}")
    suspend fun updateProduct(
        @Path("id") id: Int,
        @Body product: UpdateProductRequest
    ): ProductX

    @POST("products/add")
    suspend fun createProduct(
        @Body product: UpdateProductRequest
    ): ProductX
}