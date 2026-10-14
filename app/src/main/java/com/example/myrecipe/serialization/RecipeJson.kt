package com.example.myrecipe.serialization

import com.example.myrecipe.model.Category
import com.example.myrecipe.model.Difficulty
import org.json.JSONObject
import com.example.myrecipe.model.Recipe
import android.graphics.Color
import com.example.myrecipe.model.CookingStep
import com.example.myrecipe.model.Ingredient
import java.util.Locale
import java.time.ZoneId
import org.json.JSONArray
import java.time.Instant
import java.time.LocalDateTime
import java.util.UUID
import kotlin.String


val Recipe.json: JSONObject
    get() {
        val json = JSONObject()
        json.put("uid", uid)
        json.put("name", name)
        json.put("cookingTimeMin", cookingTimeMin)
        if (category != Category.OTHER) json.put("category", category.name)
        if (difficulty != Difficulty.EASY) json.put("difficulty", difficulty.name)
        if (isTried) json.put("isTried", true)
        if (color != Color.WHITE) json.put(
            "color",
            String.format(Locale.ROOT, "#%06X", color and 0xFFFFFF)
        )

        if (ingredients.isNotEmpty()) {
            val ingredientsArray = JSONArray()

            for (ingredient in ingredients) {
                ingredientsArray.put(ingredient.json)
            }
            json.put("ingredients", ingredientsArray)
        }

        lastCookedAt?.let {
            json.put("lastCookedAt", it.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
        }

        if (steps.isNotEmpty()) {
            val stepsArray = JSONArray()
            for (step in steps) {
                stepsArray.put(step.json)
            }
            json.put("steps", stepsArray)
        }

        rating?.let { json.put("rating", it) }

        servings?.let { json.put("servings", it) }

        note?.let { json.put("note", it) }

        return json
    }

fun parseRecipe(json: JSONObject): Recipe? {
    val uid = json.optString("uid", "")
    if (uid.isEmpty()) return null

    val name = json.optString("name", "")
    if (name.isEmpty()) return null

    val cookingTimeMin = json.optInt("cookingTimeMin", 0)
    if (cookingTimeMin == 0) return null

    val category = if (json.has("category")) {
        Category.valueOf(json.getString("category"))
    } else {
        Category.OTHER
    }

    val difficulty = if (json.has("difficulty")) {
        Difficulty.valueOf(json.getString("difficulty"))
    } else {
        Difficulty.EASY
    }

    val isTried = json.optBoolean("isTried", false)

    val color = if (json.has("color")) {
        val hex = json.getString("color").removePrefix("#")
        (hex.toLong(16) or 0xFF000000).toInt()
    } else {
        Color.WHITE
    }

    val ingredients = if (json.has("ingredients")) {
        val ingredientsArray = json.getJSONArray("ingredients")
        val list = mutableListOf<Ingredient>()
        for (i in 0 until ingredientsArray.length()) {
            val ingredientJson = ingredientsArray.getJSONObject(i)
            val ingredient = parseIngredient(ingredientJson)
            if (ingredient != null) list.add(ingredient)

        }
        list
    } else {
        emptyList()
    }

    val steps = if (json.has("steps")) {

        val stepsArray = json.getJSONArray("steps")
        val list = mutableListOf<CookingStep>()
        for (i in 0 until stepsArray.length()) {
            val stepJson = stepsArray.getJSONObject(i)
            val step = parseCookingStep(stepJson)
            if (step != null) list.add(step)
        }
        list

    } else {
        emptyList()
    }


    val rating = if (json.has("rating")) json.getInt("rating") else null

    val lastCookedAt = if (json.has("lastCookedAt")) {
        LocalDateTime.ofInstant(
            Instant.ofEpochMilli(json.getLong("lastCookedAt")),
            ZoneId.systemDefault()
        )
    } else null

    val servings = if (json.has("servings")) json.getInt("servings") else null

    val note = if (json.has("note")) json.getString("note") else null

    return Recipe(
        uid = uid,
        name = name,
        cookingTimeMin = cookingTimeMin,
        category = category,
        difficulty = difficulty,
        isTried = isTried,
        color = color,
        ingredients = ingredients,
        steps = steps,
        rating = rating,
        lastCookedAt = lastCookedAt,
        servings = servings,
        note = note
    )

}
