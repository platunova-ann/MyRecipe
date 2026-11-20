package com.example.myrecipe

import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myrecipe.model.Difficulty
import java.time.LocalDateTime
import android.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha
import com.example.myrecipe.model.Recipe


@Composable
fun RecipeEditScreen(
    initialRecipe: Recipe? = null,
    onSave: (Recipe) -> Unit = {},
    onOpenColorPicker: () -> Unit = {},
    pickedColor: Int? = null,
    onPickedColorConsumed: () -> Unit = {},
) {
    var name by rememberSaveable { mutableStateOf(initialRecipe?.name ?: "") }
    var cookingTimeText by rememberSaveable {
        mutableStateOf(
            initialRecipe?.cookingTimeMin?.toString() ?: ""
        )
    }
    var date by rememberSaveable { mutableStateOf<LocalDateTime?>(initialRecipe?.lastCookedAt) }
    var isTried by rememberSaveable { mutableStateOf(initialRecipe?.isTried ?: false) }
    var color by rememberSaveable { mutableStateOf(initialRecipe?.color ?: Color.WHITE) }
    var lastCustomColor by rememberSaveable { mutableStateOf<Int?>(null) }
    LaunchedEffect(pickedColor) {
        if (pickedColor != null) {
            color = pickedColor
            lastCustomColor = pickedColor
            onPickedColorConsumed()

        }
    }
    LaunchedEffect(pickedColor) {
        if (pickedColor != null) {
            color = pickedColor
            onPickedColorConsumed()
        }
    }
    var difficulty by rememberSaveable {
        mutableStateOf(
            initialRecipe?.difficulty ?: Difficulty.EASY
        )
    }
    val cookingTimeMin = cookingTimeText.toIntOrNull()
    val canSave = name.isNotBlank() &&
            cookingTimeMin != null &&
            cookingTimeMin > 0
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(16.dp)
            .alpha(if (isTried) 0.5f else 1f)

    ) {
        RecipeNameField(name = name, onNameChange = { name = it })
        OutlinedTextField(
            value = cookingTimeText,
            onValueChange = { cookingTimeText = it },
            label = { Text("Время приготовления, мин") },
            modifier = Modifier.fillMaxWidth()
        )
        DatePickerField(date = date, onDateChange = { date = it })
        IsTriedCheckbox(isTried = isTried, onIsTriedChange = { isTried = it })
        ColorPickerRow(
            lastCustomColor = lastCustomColor,
            selectedColor = color,
            onColorSelect = { color = it },
            onOpenColorPicker = onOpenColorPicker
        )
        DifficultyPicker(selected = difficulty, onSelect = { difficulty = it })
        Button(
            onClick = {
                val validTime = cookingTimeMin
                if (validTime != null && validTime > 0) {
                    val recipe = if (initialRecipe != null) {
                        initialRecipe.copy(
                            name = name,
                            cookingTimeMin = validTime,
                            lastCookedAt = date,
                            isTried = isTried,
                            color = color,
                            difficulty = difficulty
                        )
                    } else {
                        Recipe(
                            name = name,
                            cookingTimeMin = validTime,
                            lastCookedAt = date,
                            isTried = isTried,
                            color = color,
                            difficulty = difficulty
                        )
                    }
                    onSave(recipe)
                }

            },
            enabled = canSave
        ) { Text("Сохранить") }

    }

}

@Preview(showBackground = true, name = "Готовый рецепт")
@Composable
fun RecipeEditScreenPreview() {
    RecipeEditScreen()
}


