package com.example.myrecipe

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myrecipe.model.Difficulty

@Composable
fun DifficultyPicker(
    selected: Difficulty,
    onSelect: (Difficulty) -> Unit
) {
    Row {
        Difficulty.values().forEach { option ->

            FilterChip(
                selected = option == selected,
                onClick = { onSelect(option) },
                label = { Text(option.name) }

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DifficultyPickerPreview() {
    DifficultyPicker(

        selected = Difficulty.EASY,
        onSelect = { }
    )

}
