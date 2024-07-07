package com.example.newfoodme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.newfoodme.ui.theme.NewFoodMeTheme

//when app starts the login page is the first what the user sees
//note for myself: when user already login in once the data should be already typed in the boxes; user only have to click login
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewFoodMeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// entire login page
@Composable

//aktualisert die Eingabefelder wenn etwas eingetippt wird; Beispiel:Solange der User nichts eintippt ist es ein leerer string der sich mit der eingabe füllt
fun LoginScreen(modifier: Modifier = Modifier) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    //column is like div in html; formes boxes and you can push them around the page
    //change area within the page
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp) // side distance
            .padding(top = 420.dp),
        horizontalAlignment = Alignment.CenterHorizontally // center horizontally
    ) {

        //Überschrift
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineLarge.copy(fontSize = 32.sp),
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(20.dp)) // space between text and boxes

        // Eingabefelder
        // noch auslagern als Klasse
        BasicTextField(
            value = username,
            onValueChange = { username = it },

            // all whats inside the text field
            decorationBox = { innerTextField ->

                //username box
                Box(
                    modifier = Modifier
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .border(2.dp, Color.Black, shape = RoundedCornerShape(50.dp))
                        .padding(16.dp)
                ) {

                    // when no text is typed in set color to
                    if (username.isEmpty()) {
                        Text("Username", color = Color(0xFFFFA500))
                    }
                    innerTextField()
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        //space between username box and passwort box
        Spacer(modifier = Modifier.height(16.dp))

        //text field for password
        BasicTextField(
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation(),
            decorationBox = { innerTextField ->

                //password box
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .border(2.dp, Color.Black, shape = RoundedCornerShape(50.dp))
                        .padding(16.dp)
                ) {

                    //when text field is empty these are the following "instrutions"
                    if (password.isEmpty()) {
                        Text("Password", color = Color(0xFFFFA500))
                    }
                    innerTextField()
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        //space between password box and login button
        Spacer(modifier = Modifier.height(32.dp))

        // login button when information is typed in
        MyButton()
    }
}


//Button soll nicht die selbe klasse wie die Eingabefelder haben; trennen und auslagern!
@Composable
//login button
fun MyButton() {
    Button(
        onClick = { /* spacer until database is finished */ },
        modifier = Modifier
            .fillMaxWidth() //füllt die gesamte bildschrimbreite bsi zu dem punkt aus bis zu dem wir den seitenabstand anfangs festgelegt haben
            .border(2.dp, Color.Black, shape = RoundedCornerShape(50.dp)), // Schwarzer Rand mit abgerundeten ecken
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)) //color of button
    ) {
        Text(text = "Login", color = Color.White) //color of text
    }
}