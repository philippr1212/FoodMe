package com.example.newfoodme.ui.theme.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newfoodme.ui.theme.NewFoodMeTheme
import com.example.newfoodme.R

// When the app starts, the login page is the first thing the user sees
// Note for myself: When the user has already logged in once, the data should already be typed in the boxes; user only has to click login
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

// Entire login page
@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // box die die gesamte seite beinhaltet
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {

        // background image
        Image(
            painter = painterResource(id = R.drawable.login_registration_background_dark),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // login form
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) // seitenabstand
                .padding(top = 420.dp),
            horizontalAlignment = Alignment.CenterHorizontally // horizontal zentrieren
        ) {

            // überschrift
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 38.sp),
                color = Color.White
            )

            // space between text and boxes
            Spacer(modifier = Modifier.height(20.dp))

            // Username input field; have to be changed to email
            BasicTextField(
                value = username,
                onValueChange = { username = it },

                // textfield decoration
                decorationBox = { innerTextField ->

                    // email input box
                    Box(

                        //die input boxen können hier beliebig angepasst werden
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, shape = RoundedCornerShape(50.dp))
                            .border(2.dp, Color.Black, shape = RoundedCornerShape(50.dp))
                            .padding(16.dp)
                    ) {

                        // wenn nichts eingegeben ist
                        if (username.isEmpty()) {
                            Text("E-Mail-Adresse", color = Color(0xFFFFA500))
                        }
                        innerTextField()
                    }
                },
                singleLine = true
            )

            // Space between username and password input fields
            Spacer(modifier = Modifier.height(16.dp))

            // passowrt eingabefeld
            BasicTextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(), //passwort verstecken bzw wird in sternchen angezeigt
                decorationBox = { innerTextField ->

                    // Box for the password input
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, shape = RoundedCornerShape(50.dp))
                            .border(2.dp, Color.Black, shape = RoundedCornerShape(50.dp))
                            .padding(16.dp)
                    ) {

                        // wenn nichts eingegeben ist
                        if (password.isEmpty()) {
                            Text("Password", color = Color(0xFFFFA500))
                        }
                        innerTextField()
                    }
                },
                singleLine = true
            )

            // Space between password input and login button
            Spacer(modifier = Modifier.height(32.dp))

            // Login button
            MyButton()
        }
    }
}

// login button
@Composable
fun MyButton() {
    Button(
        onClick = { /* Spacer until database is finished */ },
        modifier = Modifier
            .fillMaxWidth() // füllt die gesamte bildschrimbreite bsi zu dem punkt aus bis zu dem wir den seitenabstand anfangs festgelegt haben
            .border(2.dp, Color.Black, shape = RoundedCornerShape(50.dp)), //  Schwarzer Rand mit abgerundeten ecken
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)) // Color of the button
    ) {
        Text(text = "Login", color = Color.White) // Color of the text
    }
}
