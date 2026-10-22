package com.example.myrecipe

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RecipeNameField(
    name: String,
    onNameChange: (String) -> Unit
) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("Название рецепта") },
        modifier = Modifier.fillMaxWidth()

    )

}

@Preview(showBackground = true)
@Composable
fun RecipeNameFieldPreview(
) {
    RecipeNameField(
        name = "суп",
        onNameChange = {}
    )
}
