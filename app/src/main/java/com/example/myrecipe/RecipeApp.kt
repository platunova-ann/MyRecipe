package com.example.myrecipe

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import com.example.myrecipe.model.Recipe
import androidx.navigation.NavType
import androidx.navigation.navArgument
import android.graphics.Color
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun RecipeApp() {
    val navController = rememberNavController()
    val recipes = remember { mutableStateListOf<Recipe>() }
    NavHost(
        navController = navController,
        startDestination = "recipes"
    ) {
        composable("recipes") {

            RecipeListScreen(
                recipes = recipes,
                onRecipeClick = { recipeId ->
                    navController.navigate("recipeEdit?recipeId=$recipeId") {
                        launchSingleTop = true
                    }
                },
                onAddClick = {
                    navController.navigate("recipeEdit") {
                        launchSingleTop = true
                    }
                },
                onDeleteClick = { recipeId ->
                    recipes.removeAll{it.uid == recipeId}
                }

            )
        }
        composable(
            route = "recipeEdit?recipeId={recipeId}",
            arguments = listOf(
                navArgument("recipeId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        )
        { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")
            val initialRecipe = recipes.find { it.uid == recipeId }
            val pickedColor by backStackEntry.savedStateHandle
                .getStateFlow<Int?>("pickedColor", null)
                .collectAsState()
            RecipeEditScreen(

                pickedColor = pickedColor,
                onPickedColorConsumed = {
                    backStackEntry.savedStateHandle.set<Int?>("pickedColor", null)
                },
                initialRecipe = initialRecipe,
                onOpenColorPicker = {navController.navigate("colorPicker")},
                onSave = { recipe ->
                    val index = recipes.indexOfFirst { it.uid == recipe.uid }


                    if (index == -1) {
                        recipes.add(recipe)
                    } else {
                        recipes[index] = recipe
                    }
                    navController.popBackStack()
                }
            )


        }




//        composable("colorPicker"){
//            ColorPicker(
//                colorSelected = Color.WHITE,
//                onColorSelected = {newColor ->
//                    navController.previousBackStackEntry
//                        ?.savedStateHandle
//                        ?.set("pickedColor", newColor)
//                },
//                onDismiss = { navController.popBackStack() },
//            )
//        }

    }
}

@Preview
@Composable
fun RecipeAppPreview(
) {
    RecipeApp(

    )

}