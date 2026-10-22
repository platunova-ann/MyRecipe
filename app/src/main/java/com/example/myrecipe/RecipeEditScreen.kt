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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha



@Composable
fun RecipeEditScreen() {
    var name by rememberSaveable { mutableStateOf("") }
    var date by rememberSaveable { mutableStateOf<LocalDateTime?>(null) }
    var isTried by rememberSaveable { mutableStateOf(false) }
    var color by rememberSaveable { mutableStateOf(Color.WHITE) }
    var difficulty by rememberSaveable { mutableStateOf(Difficulty.EASY) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(16.dp)
            .alpha(if(isTried) 0.5f else 1f)

    ) {
        RecipeNameField(name = name, onNameChange = { name = it })
        DatePickerField(date = date, onDateChange = { date = it })
        IsTriedCheckbox(isTried = isTried, onIsTriedChange = { isTried = it })
        ColorPickerRow(selectedColor = color, onColorSelect = { color = it })
        DifficultyPicker(selected = difficulty, onSelect = {difficulty = it})
    }

}

@Preview(showBackground = true, name = "Готовый рецепт")
@Composable
fun RecipeEditScreenPreview() {
    RecipeEditScreen()
}


