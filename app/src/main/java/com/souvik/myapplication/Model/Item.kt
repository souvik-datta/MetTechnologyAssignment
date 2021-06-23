package com.souvik.myapplication.Model

data class Item(
    val category_id: String,
    val category_name: String,
    val created_at: String,
    val description: String,
    val price: Double,
    val product_id: String,
    val product_image: String,
    val product_name: String,
    val product_suk_id: String,
    val status: String,
    val updated_at: String,
    val vendor_id: String,
    val vendor_name: String,
    var quantity : Int = 0
)