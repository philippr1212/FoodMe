package com.example.newfoodme.ui.theme.classes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// text fields for login and registration
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,

        // textfield decoration
        decorationBox = { innerTextField ->

            // email input box
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(50.dp))
                    .border(2.dp, Color.Black, shape = RoundedCornerShape(50.dp))
                    .padding(16.dp)
            ) {

                // wenn nichts eingegeben ist
                if (value.isEmpty()) {
                    Text(placeholder, color = Color(0xFFFFA500))
                }
                innerTextField()
            }
        },
        singleLine = true
    )
}

//button for login and registration
class MyButton(
    var text: String,
    var username: String = "",
    var password: String = "",
    var onButtonClick: () -> Unit = {},
    vorname: String,
    nachname: String
) {
    @Composable
    fun Display() {
        Button(
            onClick = { onButtonClick() },
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, Color.Black, shape = RoundedCornerShape(50.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))
        ) {
            Text(text = text, color = Color.White)
        }
    }
}
