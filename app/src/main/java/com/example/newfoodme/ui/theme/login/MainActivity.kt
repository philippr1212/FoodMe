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
    var passwort by remember { mutableStateOf(sharedPreferences.getString("passwort", "") ?: "") }
    var isEmailValid by remember { mutableStateOf(true) }

    //Checks whether there is a valid email address in the input field
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Everything that is built on the website is included in the box because it is easier to move the entire page elements together rather than each element individually
    Box(
        modifier = modifier.fillMaxSize()
    ) {

        //Background image for the login page
        Image(
            painter = painterResource(id = R.drawable.login_registration_background_dark),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // FoodMe-Logo
        Column(
            modifier = Modifier.padding(top = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.foodme_logo),
                contentDescription = null
            )
        }

        // Rough positioning of heading, text fields and buttons
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = 420.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Login-Headline
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 38.sp),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Email address text field
            CustomTextField(
                value = email,
                onValueChange = {
                    email = it
                    isEmailValid = isValidEmail(it)
                },
                placeholder = "E-Mail-Adresse",
                modifier = Modifier.fillMaxWidth()
            )

            // If the email address is not in the correct format, the following output will appear
            if (!isEmailValid) {
                Text(
                    text = "Bitte geben Sie eine gültige E-Mail-Adresse ein.",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Passwort Textfield
            CustomTextField(
                value = passwort,
                onValueChange = { passwort = it },
                placeholder = "Passwort",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Note for the user if he does not yet have an account
            Text(
                text = "Noch keinen Account? Hier registrieren!",
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 18.sp),
                color = Color.White,
                modifier = Modifier.clickable {
                    //Navigate to the registration page
                    val intent = Intent(context, RegistrationActivity::class.java)
                    context.startActivity(intent)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Login-Button
            val username = ""
            val password = ""
            MyButton(
                text = "Login",
                username = username,
                password = password,
                onButtonClick = { _, _, username, password ->

                    // Checks if email address and password is typed in
                    if (username.isNotEmpty() && password.isNotEmpty()) {
                        println("E-Mail: $username, Passwort: $password")

                    } else {
                        println("Bitte geben Sie eine gültige E-Mail und ein Passwort ein.")
                    }
                }
            ).Display()
        }
    }
}
