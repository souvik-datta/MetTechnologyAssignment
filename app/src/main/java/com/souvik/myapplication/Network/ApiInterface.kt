package com.souvik.myapplication.Network

import com.souvik.myapplication.Model.ProductDetails
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import kotlin.collections.HashMap

interface ApiInterface {

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("/PinkDelivery/dev/api/product/get")
    fun getProductDetailsApi(@Body data: HashMap<String, String>): Call<ProductDetails>?
}