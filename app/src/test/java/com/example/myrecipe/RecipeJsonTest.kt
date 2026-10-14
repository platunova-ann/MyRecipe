package com.example.myrecipe

import android.graphics.Color
import com.example.myrecipe.model.Category
import com.example.myrecipe.model.CookingStep
import com.example.myrecipe.model.Difficulty
import com.example.myrecipe.model.Ingredient
import com.example.myrecipe.model.Recipe
import com.example.myrecipe.serialization.json
import com.example.myrecipe.serialization.parseRecipe
import org.json.JSONObject
import org.junit.Assert
import org.junit.Test
import java.time.LocalDateTime

class RecipeJsonTest {
    @Test
    fun testRoundTrip() {

        val original = Recipe(
            name = "суп",
            cookingTimeMin = 7
        )
        val json = original.json
        val restored = parseRecipe(json)
        Assert.assertEquals(original, restored)
    }


    @Test
    fun testFullData() {
        val originals = Recipe(
            name = "Суп",
            cookingTimeMin = 90,
            category = Category.SOUP,
            difficulty = Difficulty.MEDIUM,
            isTried = true,
            color = Color.RED,
            ingredients = listOf(
                Ingredient("Соль", amount = 5.0, unit = "грамм"),
                Ingredient(name = "морковь", amount = 300.0, unit = "грамм")
            ),
            steps = listOf(
                CookingStep(order = 1, text = "добавить морковь"),
                CookingStep(order = 2, text = "посолить")
            ),
            rating = 5,
            lastCookedAt = LocalDateTime.of(2026, 10, 1, 14, 30),
            servings = 4,
            note = "текст"
        )
        val json = originals.json
        val restored = parseRecipe(json)
        Assert.assertEquals(originals, restored)
    }

    @Test
    fun testBrokenJson() {
        val emptyJson = JSONObject()
        Assert.assertNull(parseRecipe(emptyJson))

        val noName = JSONObject()
        noName.put("uid", "abc-123")
        noName.put("cookingTimeMin", 30)
        Assert.assertNull(parseRecipe(noName))

        val emptyName = JSONObject()
        emptyName.put("uid", "abc-123")
        emptyName.put("name", "")
        emptyName.put("cookingTimeMin", 30)
        Assert.assertNull(parseRecipe(emptyName))

        val zeroTime = JSONObject()
        zeroTime.put("uid", "abc-123")
        zeroTime.put("name", "суп")
        zeroTime.put("cookingTimeMin", 0)
        Assert.assertNull(parseRecipe(zeroTime))


    }


}