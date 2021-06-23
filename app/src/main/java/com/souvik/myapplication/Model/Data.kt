package com.souvik.myapplication.Model

data class Data(
    val cat_id: String,
    val cat_name: String,
    val items: List<Item>,
    var isSelected :Boolean = false
)