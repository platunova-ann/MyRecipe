package com.example.myrecipe.model

import java.io.File
import org.json.JSONArray
import com.example.myrecipe.serialization.json
import com.example.myrecipe.serialization.parseRecipe
import org.slf4j.LoggerFactory


class RecipeStorage(private val file: File) {

    private val log = LoggerFactory.getLogger(RecipeStorage::class.java)

    private val _recipes = mutableListOf<Recipe>()
    val recipes: List<Recipe> get() = _recipes

    fun add(recipe: Recipe) {
        log.debug("add: {}", recipe.name)

        val index = _recipes.indexOfFirst { it.uid == recipe.uid }
        if (index != -1) {
            _recipes.removeAt(index)
        }
        _recipes.add(recipe)
    }

    fun remove(targetUid: String) {
        log.debug("remove: uid={}", targetUid)

        val index = _recipes.indexOfFirst { it.uid == targetUid }
        if (index != -1) {
            _recipes.removeAt(index)
        }
    }

    fun saveToFile() {

        log.info("saveToFile: start, {} recipes", _recipes.size)
        try {
            val array = JSONArray()
            for (recipe in _recipes) {
                array.put(recipe.json)
            }
            file.writeText(array.toString())
            log.info("saveToFile: done, {} bytes", file.length())
        } catch (e: Exception) {
            log.error("saveToFile: failed", e)
        }


    }

    fun loadFromFile() {
        if (!file.exists()) {
            log.warn("loadFromFile: file does not exist, returning empty list")
            return
        }

        try {
            val text = file.readText()
            val array = JSONArray(text)
            for (i in 0 until array.length()) {
                val jsonObject = array.getJSONObject(i)
                val recipe = parseRecipe(jsonObject)
                if (recipe != null) {
                    _recipes.add(recipe)
                } else {
                    log.debug("loadFromFile: skipped broken recipe at index {}", i)
                }
            }
            log.info("loadFromFile: loaded {} recipes", _recipes.size)

        } catch (e: Exception) {
            log.error("loadFromFile: failed", e)

        }

    }

}
