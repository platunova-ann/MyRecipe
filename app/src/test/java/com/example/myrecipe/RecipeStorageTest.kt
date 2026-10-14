package com.example.myrecipe

import com.example.myrecipe.model.Recipe
import com.example.myrecipe.model.RecipeStorage
import com.example.myrecipe.serialization.json
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Test
import java.io.File
import org.junit.Assert.assertEquals



class RecipeStorageTest {

    @Test
    fun testAddNew() {
        val storage = RecipeStorage(File.createTempFile("test", ".json"))
        val recipe = Recipe(name = "суп", cookingTimeMin = 30)
        storage.add(recipe)
        assertEquals(1, storage.recipes.size)
        assertEquals(recipe, storage.recipes[0])
    }

    @Test
    fun testAddExistingReplaces() {
        val storage = RecipeStorage(File.createTempFile("test", ".json"))
        val original = Recipe(name = "суп", cookingTimeMin = 30)
        val updated = original.copy(name = "борщ", cookingTimeMin = 90)

        storage.add(original)
        storage.add(updated)
        assertEquals(1, storage.recipes.size)
        assertEquals("борщ", storage.recipes[0].name)
        assertEquals(90, storage.recipes[0].cookingTimeMin)
    }

    @Test
    fun testRemoveNonexistent() {

        val storage = RecipeStorage(File.createTempFile("test", ".json"))
        val recipe = Recipe(name = "суп", cookingTimeMin = 30)
        storage.add(recipe)

        storage.remove("nonexistent-uid-12345")

        assertEquals(1, storage.recipes.size)
        assertEquals(recipe, storage.recipes[0])


    }

    @Test
    fun testSaveLoadRoundTrip() {
        val file = File.createTempFile("test", ".json")
        val storage1 = RecipeStorage(file)
        storage1.add(Recipe(name = "суп", cookingTimeMin = 30))
        storage1.add(Recipe(name = "каша", cookingTimeMin = 15))
        storage1.saveToFile()

        val storage2 = RecipeStorage(file)
        storage2.loadFromFile()

        assertEquals(2, storage2.recipes.size)
        assertEquals(storage1.recipes, storage2.recipes)
    }

    @Test
    fun testLoadFromNonexistentFile() {
        val file = File("does_not_exist_12345.json")
        if (file.exists()) file.delete()

        val storage = RecipeStorage(file)
        storage.loadFromFile()

        assertEquals(0, storage.recipes.size)

    }

    @Test
    fun testLoadWithBrokenRecord() {

        val file = File.createTempFile("test", ".json")

        val array = JSONArray()

        val valid1 = Recipe(name = "суп", cookingTimeMin = 30)
        array.put(valid1.json)

        val broken  = JSONObject()
        broken.put("uid", "broken-123")
        broken.put("cookingTimeMin", 30)

        array.put(broken)

        val valid2 = Recipe(name = "каша", cookingTimeMin = 15)
        array.put(valid2.json)

        file.writeText(array.toString())

        val storage = RecipeStorage(file)
        storage.loadFromFile()


        assertEquals(2, storage.recipes.size)
        assertEquals(valid1, storage.recipes[0])
        assertEquals(valid2, storage.recipes[1])

    }

    @Test
    fun testLoadFromGarbageFile() {
        val file = File.createTempFile("test", ".json")
        file.writeText("это не json, а мусор")

        val storage = RecipeStorage(file)
        storage.loadFromFile()   // не должно упасть

        assertEquals(0, storage.recipes.size)
    }

}
