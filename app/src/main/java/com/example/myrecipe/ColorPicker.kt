package com.example.myrecipe

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


@Composable
fun ColorPicker(
    colorSelected: Int,
    onColorSelected: (Int) -> Unit

) {
    Column {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(color = Color(colorSelected), shape = RoundedCornerShape(12.dp))
                    .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(12.dp))
            )

            Text("выберите цвет", modifier = Modifier.padding(start = 20.dp))
        }

        ColorPalette(onColorPicked = onColorSelected)

        Button(onClick = { onColorSelected(Color.Red.toArgb()) }) {
            Text("Выбрать")
        }
    }


}

@Composable
fun ColorPalette(
    onColorPicked: (Int) -> Unit
) {
    var fingerPosition by remember { mutableStateOf(Offset(150f, 150f)) }

    Canvas(
        modifier = Modifier
            .size(300.dp)
            .pointerInput(Unit) { detectTapGestures(onTap = { offset -> fingerPosition = offset }) }
    )
    {
        drawRect(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color.Red,
                    Color.Yellow,
                    Color.Green,
                    Color.Cyan,
                    Color.Blue,
                    Color.Magenta
                )
            )
        )
        drawRect(brush = Brush.verticalGradient(colors = listOf(Color.Transparent, Color.Black)))
        drawCircle(
            color = Color.Black,
            radius = 25f,
            center = fingerPosition
        )

    }


}


@Preview(showBackground = true)
@Composable
fun ColorPickerPreview() {
    ColorPicker(
        colorSelected = Color.Red.toArgb(),
        onColorSelected = {}
    )
}

@Preview(showBackground = true)
@Composable
fun ColorPalettePreview() {
    ColorPalette(onColorPicked = {})

}