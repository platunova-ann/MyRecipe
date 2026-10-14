package com.example.myrecipe.serialization

import org.json.JSONObject
import com.example.myrecipe.model.Ingredient


val Ingredient.json: JSONObject
    get() {
        val json = JSONObject()
        json.put("name", name)
        amount?.let { json.put("amount", it) }
        unit?.let { json.put("unit", it) }
        return json

    }

fun parseIngredient(json: JSONObject): Ingredient? {
    val name = json.optString("name", "")
    if (name.isEmpty()) return null
    val amount = if (json.has("amount")) json.getDouble("amount") else null
    val unit = if (json.has("unit")) json.getString(("unit")) else null
    return Ingredient(name, amount, unit)
}
