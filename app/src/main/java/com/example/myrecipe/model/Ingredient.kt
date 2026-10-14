package com.example.myrecipe.model

data class Ingredient(
    val name: String,
    val amount: Double? = null,
    val unit: String? = null
)