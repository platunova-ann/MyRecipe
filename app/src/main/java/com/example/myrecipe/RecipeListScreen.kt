package com.example.myrecipe

import android.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.myrecipe.model.Recipe
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.draw.alpha
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton


@Composable
fun RecipeListScreen(
    recipes: List<Recipe>,
    onRecipeClick: (String) -> Unit,
    onAddClick: () -> Unit,
    onDeleteClick: (String) -> Unit
) {
    var pendingDeleteId by rememberSaveable { mutableStateOf<String?>(null) }
    Column {

        Button(
            onClick = onAddClick
        ) {
            Text("+")
        }

        LazyColumn {
            items(
                items = recipes,
                key = { recipe -> recipe.uid }
            ) { recipe ->
                val swipeState = rememberSwipeToDismissBoxState()

                SwipeToDismissBox(
                    state = swipeState,
                    enableDismissFromStartToEnd = false,
                    backgroundContent = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Button(onClick = { pendingDeleteId = recipe.uid }) {
                                Text("Удалить")
                            }

                        }

                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .clickable { onRecipeClick(recipe.uid) }
                            .padding(16.dp),

                        ) {
                        Text(
                            text = recipe.name,
                            modifier = Modifier.alpha(if (recipe.isTried) 0.5f else 1f)
                        )
                    }


                }


            }

        }
    }
    pendingDeleteId?.let { recipeId ->
        AlertDialog(
            onDismissRequest = {
                pendingDeleteId = null
            },
            title = { Text("удалить рецепт?") },
            confirmButton = {
                TextButton(onClick = {
                    onDeleteClick(recipeId)
                    pendingDeleteId = null
                }) {
                    Text("Удалить")
                }
            },
            dismissButton = {TextButton(onClick = {pendingDeleteId = null}) {
                Text("Отмена")
            }}
        )
    }


}

@Preview
@Composable
fun RecipeListScreenPreview() {
    RecipeListScreen(
        recipes = listOf(
            Recipe(name = "Борщ", cookingTimeMin = 60),
            Recipe(name = "Щи", cookingTimeMin = 50)
        ),
        onRecipeClick = {},
        onAddClick = {},
        onDeleteClick = {}
    )
}
