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
import androidx.compose.ui.layout.onSizeChanged
import android.util.Log
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.drawscope.Stroke




@Composable
fun ColorPicker(
    colorSelected: Int,
    onColorSelected: (Int) -> Unit,
    onDismiss: () -> Unit,


) {
    var pickedColorArgb by rememberSaveable { mutableStateOf(colorSelected) }
    val pickedColor = Color(pickedColorArgb)
    Column {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(color = pickedColor, shape = RoundedCornerShape(12.dp))
                    .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(12.dp))
            )

            Text("выберите цвет", modifier = Modifier.padding(start = 20.dp))
        }

        ColorPalette(
            onColorPicked = { newColor ->
                pickedColorArgb = newColor
            },
            pickedColor = pickedColor
        )

        Button(onClick = {
            onColorSelected(pickedColor.toArgb())
            onDismiss()
            onColorSelected(pickedColorArgb)


        }) {
            Text("Выбрать")
        }
    }


}

@Composable
fun ColorPalette(
    onColorPicked: (Int) -> Unit,
    pickedColor: Color
) {
    var fingerPosition by remember { mutableStateOf(Offset(150f, 150f)) }
    var canvasSize by remember { mutableStateOf(Offset(300f, 300f)) }

    Canvas(
        modifier = Modifier
            .size(300.dp)
            .onSizeChanged { intSize ->
                canvasSize = Offset(intSize.width.toFloat(), intSize.height.toFloat())
            }
            .pointerInput(Unit)
            {
                detectTapGestures(onTap = { offset ->
                    Log.d("Palette", "tap: $offset")
                    fingerPosition = offset
                    Log.d("Palette", "fingerPosition=$fingerPosition")
                    val hue = (offset.x / canvasSize.x) * 300f
                    val value = 1f - (offset.y / canvasSize.y)
                    val pickedColor = Color.hsv(hue, 1f, value)
                    Log.d("Palette", "hue=$hue, value=$value, color=$pickedColor")
                    onColorPicked(pickedColor.toArgb())
                })
            }
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
        drawCircle(color = Color.Black,
            radius = 27f,
            center = fingerPosition)
        drawCircle(
            color = pickedColor,
            radius = 25f,
            center = fingerPosition,

        )

    }


}


@Preview(showBackground = true)
@Composable
fun ColorPickerPreview() {
    ColorPicker(
        colorSelected = Color.Red.toArgb(),
        onColorSelected = {},
        onDismiss = {}
    )
}

@Preview(showBackground = true)
@Composable
fun ColorPalettePreview() {
    ColorPalette(
        onColorPicked = {},
        pickedColor = Color.Black)

}