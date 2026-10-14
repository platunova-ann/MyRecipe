package com.example.myrecipe.model

import java.io.File
import org.json.JSONArray
import com.example.myrecipe.serialization.json
import com.example.myrecipe.serialization.parseRecipe


class RecipeStorage(private val file: File) {
    private val _recipes = mutableListOf<Recipe>()
    val recipes: List<Recipe> get() = _recipes

    fun add(recipe: Recipe) {
        val index = _recipes.indexOfFirst { it.uid == recipe.uid }
        if (index != -1) {
            _recipes.removeAt(index)
        }
        _recipes.add(recipe)
    }

    fun remove(targetUid: String) {
        val index = _recipes.indexOfFirst { it.uid == targetUid }
        if (index != -1) {
            _recipes.removeAt(index)
        }
    }

    fun saveToFile() {
        val array = JSONArray()
        for (recipe in _recipes) {
            array.put(recipe.json)
        }
        file.writeText(array.toString())
    }

    fun loadFromFile() {
        if (!file.exists()) return
        val text = file.readText()
        val array = try {
            JSONArray(text)
        } catch (e: Exception) {
            return
        }

        for (i in 0 until array.length()) {
            val jsonObject = array.getJSONObject(i)
            val recipe = parseRecipe(jsonObject)
            if (recipe != null) {
                _recipes.add(recipe)
            }
        }
    }

}
