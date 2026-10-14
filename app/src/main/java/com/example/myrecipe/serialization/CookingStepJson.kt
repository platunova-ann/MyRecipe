package com.example.myrecipe.serialization

import org.json.JSONObject
import com.example.myrecipe.model.CookingStep

val CookingStep.json: JSONObject
    get() {
        val json = JSONObject()
        json.put("order", order)
        json.put("text", text)
        return json
    }

fun parseCookingStep(json: JSONObject): CookingStep? {
    val order = json.optInt("order", -1)
    val text = json.optString("text", "")
    if (order == -1 || text.isEmpty()) return null
    return CookingStep(order, text)

}