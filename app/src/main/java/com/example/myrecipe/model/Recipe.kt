package com.example.myrecipe.model

import java.util.UUID
import android.graphics.Color
import java.time.LocalDateTime

data class Recipe(
    val uid: String = UUID.randomUUID().toString(),//индификатор блюда
    val name: String,
    val cookingTimeMin: Int,
    val category: Category = Category.OTHER,
    val difficulty: Difficulty = Difficulty.EASY,
    val isTried: Boolean = false,
    val color: Int = Color.WHITE,
    val ingredients: List<Ingredient> = emptyList(),
    val steps: List<CookingStep> = emptyList(),
    val rating: Int? = null,
    val lastCookedAt: LocalDateTime? = null,
    val servings: Int? = null,
    val note: String? = null
)