package com.example.myrecipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myrecipe.ui.theme.MyRecipeTheme
import java.io.File
import com.example.myrecipe.model.Recipe
import com.example.myrecipe.model.RecipeStorage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val file = File(filesDir, "recipes.json")
        val storage = RecipeStorage(file)

        val recipe1 = Recipe(name = "Борщ", cookingTimeMin = 90)
        val recipe2 = Recipe(name = "Каша", cookingTimeMin = 15)

        storage.add(recipe1)
        storage.add(recipe2)
        storage.saveToFile()

        val storage2 = RecipeStorage(file)
        storage2.loadFromFile()

        setContent {
            MyRecipeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyRecipeTheme {
        Greeting("Android")
    }
}