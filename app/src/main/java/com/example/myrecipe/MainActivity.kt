package com.example.myrecipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.myrecipe.ui.theme.MyRecipeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        AppState.leakedActivity = this // усл.2 - ссылка на текущий оьъект
//        Thread.sleep(5000)
        setContent {
            MyRecipeTheme {
                RecipeEditScreen()

            }
        }

    }

//    override fun onDestroy() {
//        super.onDestroy()
//        AppState.leakedActivity = null
//    }
}

