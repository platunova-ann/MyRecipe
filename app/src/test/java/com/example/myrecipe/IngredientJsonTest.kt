package com.example.myrecipe

import com.example.myrecipe.model.Ingredient
import com.example.myrecipe.serialization.json
import com.example.myrecipe.serialization.parseIngredient
import org.junit.Assert.assertEquals
import org.junit.Test

class IngredientJsonTest {
    @Test
    fun testRoundTrip() {
        val original = Ingredient(name = "мука", amount = 0.5, unit = "кг")
        val json = original.json
        val restored = parseIngredient(json)
        assertEquals(original, restored)
    }
    @Test
    fun testOnlyName() {
        val original1 = Ingredient(name = "соль")
        val json = original1.json
        val restored = parseIngredient(json)
        assertEquals(original1, restored)

    }



}