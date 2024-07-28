package com.example.newfoodme.ui.theme.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newfoodme.ui.theme.BasicSettings.NewFoodMeTheme
import com.example.newfoodme.R
import com.example.newfoodme.ui.theme.classes.CustomTextField

// When the app starts, the login page is the first thing the user sees
// Note for myself: When the user has already logged in once, the data should already be typed in the boxes; user only has to click login
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
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

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_registration_background_dark),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(top = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.foodme_logo),
                contentDescription = null
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = 420.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 38.sp),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))

            CustomTextField(
                value = username,
                onValueChange = { username = it },
                placeholder = "E-Mail-Adresse",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Password",
                // visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )


            Spacer(modifier = Modifier.height(32.dp))

            MyButton()
        }
    }
}

@Composable
fun MyButton() {
    Button(
        onClick = { /* Placeholder until database is finished */ },
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color.Black, shape = RoundedCornerShape(50.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))
    ) {
        Text(text = "Login", color = Color.White)
    }
}
