package com.example.myrecipe

import androidx.compose.runtime.Composable
import java.time.LocalDateTime
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@Composable
fun DatePickerField(
    date: LocalDateTime?,
    onDateChange: (LocalDateTime?) -> Unit
) {
    var isDialogOpen by remember { mutableStateOf(false) }
    Column {
        Button(onClick = { isDialogOpen = true }) {
            Text("Выбрать дату")
        }
        Text(
            text = if (date == null) "Дата не выбрана" else date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) //Формат типа 2026-10-15T14:30
        )
        if (isDialogOpen) {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = date?.atZone(ZoneId.systemDefault())?.toInstant()
                    ?.toEpochMilli()
            )
            DatePickerDialog(
                onDismissRequest = { isDialogOpen = false },
                confirmButton = {
                    TextButton(onClick = {
                        val millis = datePickerState.selectedDateMillis
                        if (millis != null) {
                            val newDate = Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime()
                            onDateChange(newDate)

                        }
                        isDialogOpen = false

                    }) {
                        Text("Ok")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { isDialogOpen = false }) {
                        Text("Отмена")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DatePickerFieldNoEmptyPreview() {
    DatePickerField(
        date = LocalDateTime.of(2026, 10, 15, 14, 30),
        onDateChange = { }
    )

}

@Preview(showBackground = true)
@Composable
fun DatePickerFieldEmptyPreview() {
    DatePickerField(
        date = null,
        onDateChange = { }
    )

}