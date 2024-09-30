package com.example.newfoodme.ui.theme.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newfoodme.ui.theme.BasicSettings.NewFoodMeTheme
import com.example.newfoodme.R
import com.example.newfoodme.ui.theme.classes.CustomTextField
import com.example.newfoodme.ui.theme.classes.MyButton
import com.example.newfoodme.ui.theme.registration.RegistrationActivity
import com.example.newfoodme.ui.theme.home.HomePageActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            NewFoodMeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Hier wird das LoginScreen-Layout gerendert
                    LoginScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// mutableStateOf for the data first saved on the registration page
@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
    var email by remember { mutableStateOf(sharedPreferences.getString("email", "") ?: "") }
    var passwort by remember { mutableStateOf(sharedPreferences.getString("password", "") ?: "") }
    var isEmailValid by remember { mutableStateOf(true) }

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_registration_background_dark),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.padding(top = 30.dp),
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
                value = email,
                onValueChange = {
                    email = it
                    isEmailValid = isValidEmail(it)
                },
                placeholder = "E-Mail-Adresse",
                modifier = Modifier.fillMaxWidth()
            )

            if (!isEmailValid) {
                Text(
                    text = "Bitte geben Sie eine gültige E-Mail-Adresse ein.",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = passwort,
                onValueChange = { passwort = it },
                placeholder = "Passwort",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Noch keinen Account? Hier registrieren!",
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 18.sp),
                color = Color.White,
                modifier = Modifier.clickable {
                    val intent = Intent(context, RegistrationActivity::class.java)
                    context.startActivity(intent)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            MyButton(
                text = "Login",
                username = email,
                password = passwort,
                onButtonClick = { _, _, username, password ->
                    if (username.isNotEmpty() && password.isNotEmpty()) {
                        println("E-Mail: $username, Passwort: $password")

                        // Navigate to HomePageActivity
                        val intent = Intent(context, HomePageActivity::class.java)
                        context.startActivity(intent)
                    } else {
                        println("Bitte geben Sie eine gültige E-Mail und ein Passwort ein.")
                    }
                }
            ).Display()
        }
    }
}
