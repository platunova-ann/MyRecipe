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
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.dialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.input.pointer.pointerInput


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
                    recipes.removeAll { it.uid == recipeId }
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
                onOpenColorPicker = { navController.navigate("colorPicker") },
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


//MutableTransitionState(false) создаёт состояние перехода.
//        apply { ... } позволяет настроить только что
//        созданный объект и возвращает этот же объект.
//        targetState = true означает «перейти к видимому состоянию».
//        DialogProperties(...) создаёт объект с настройками окна
//        dismissOnBackPress = false отключает стандартное закрытие по «Назад»,
//        чтобы мы могли сначала проиграть уход панели.
//        dismissOnClickOutside = false отключает мгновенное закрытие при тапе снаружи.
//        usePlatformDefaultWidth = false позволяет окну занять всю доступную ширину, чтобы мы могли обработать нажатие в области вокруг палитры

        dialog(
            route = "colorPicker",
            dialogProperties = DialogProperties
                (
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            )
        ) {
            val panelVisuals = remember {
                MutableTransitionState(false).apply {
                    targetState = true
                }
            }
            BackHandler { panelVisuals.targetState = false }
            val isPanelHidden = panelVisuals.isIdle && !panelVisuals.targetState

            LaunchedEffect(isPanelHidden) {
                if (isPanelHidden) {
                    navController.popBackStack()
                }
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .pointerInput(Unit) {
                            detectTapGestures {
                                panelVisuals.targetState = false
                            }
                        }
                )


                AnimatedVisibility(
                    visibleState = panelVisuals,
                    enter = slideInVertically(initialOffsetY = { height -> height }),
                    exit = slideOutVertically(targetOffsetY = { height -> height })
                ) {
                    Surface(shape = MaterialTheme.shapes.large) {
                        ColorPicker(
                            colorSelected = Color.WHITE,
                            onColorSelected = { newColor ->
                                navController.previousBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("pickedColor", newColor)
                            },
                            onDismiss = { panelVisuals.targetState = false }
                        )
                    }
                }
            }

        }

    }

}


@Preview
@Composable
fun RecipeAppPreview(
) {
    RecipeApp(

    )


}