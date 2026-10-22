package com.example.myrecipe

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check


@Composable
fun ColorPickerRow(
    selectedColor: Int,
    onColorSelect: (Int) -> Unit

) {
    Row {
        val colors = listOf(
            Color.WHITE,
            Color.RED,
            Color.GREEN,
            Color.YELLOW
        )
        colors.forEach { color ->

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(ComposeColor(color))
                    .border(2.dp, ComposeColor(Color.BLACK))
                    .clickable { onColorSelect(color) }
            ) {
                if (color == selectedColor) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    )
                }
            }


        }
    }
}
@Preview(showBackground = true)
@Composable
fun ColorPickerRowPreview(){
    ColorPickerRow(
        selectedColor = Color.RED,
        onColorSelect = {}
    )

}