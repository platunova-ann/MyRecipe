package com.example.myrecipe


import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text


@Composable
fun IsTriedCheckbox(
    isTried: Boolean,
    onIsTriedChange: (Boolean) -> Unit
) {
    Row() {
        Checkbox(
            checked = isTried,
            onCheckedChange = onIsTriedChange
        )
        Text("Приготовлено")
    }
}

@Preview(showBackground = true)
@Composable
fun IsTriedCheckboxPreview() {
    IsTriedCheckbox(
        isTried = false,
        onIsTriedChange = { }

    )

}