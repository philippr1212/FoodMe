package com.example.newfoodme.ui.theme.registration

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newfoodme.ui.theme.BasicSettings.NewFoodMeTheme
import com.example.newfoodme.R
import com.example.newfoodme.ui.theme.classes.CustomTextField
import com.example.newfoodme.ui.theme.classes.MyButton as MyButton1
import com.example.newfoodme.ui.theme.login.MainActivity

class RegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            NewFoodMeTheme {
                RegistrationScreen()
            }
        }
    }
}

@Composable
fun RegistrationScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)


    var vorname by remember { mutableStateOf(sharedPreferences.getString("vorname", "") ?: "") }
    var nachname by remember { mutableStateOf(sharedPreferences.getString("nachname", "") ?: "") }
    var email by remember { mutableStateOf(sharedPreferences.getString("email", "") ?: "") }
    var passwort by remember { mutableStateOf(sharedPreferences.getString("passwort", "") ?: "") }
    var isEmailValid by remember { mutableStateOf(true) }


    fun isValidName(name: String): Boolean {
        return name.length >= 3
    }


    fun isValidPassword(password: String): Boolean {
        return password.length >= 8 && password.any { it.isDigit() }
    }


    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        // Background image for the registration page
        Image(
            painter = painterResource(id = R.drawable.login_registration_background_dark),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // FoodMe logo
        Column(
            modifier = Modifier.padding(top = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.foodme_logo),
                contentDescription = null
            )
        }

        // Column for headline, text fields, and button
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = 300.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Registration headline
            Text(
                text = "Registrierung",
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 38.sp),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))

            // First name text field
            CustomTextField(
                value = vorname,
                onValueChange = { newValue ->
                    vorname = newValue
                    with(sharedPreferences.edit()) {
                        putString("vorname", vorname)
                        apply()
                    }
                },
                placeholder = "Vorname",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Last name text field
            CustomTextField(
                value = nachname,
                onValueChange = { newValue ->
                    nachname = newValue
                    with(sharedPreferences.edit()) {
                        putString("nachname", nachname)
                        apply()
                    }
                },
                placeholder = "Nachname",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email address text field
            CustomTextField(
                value = email,
                onValueChange = { newValue ->
                    email = newValue
                    isEmailValid = isValidEmail(newValue)
                    if (isEmailValid) {
                        with(sharedPreferences.edit()) {
                            putString("email", email)
                            apply()
                        }
                    }
                },
                placeholder = "E-Mail-Adresse",
                modifier = Modifier.fillMaxWidth()
            )

            // Error message if email is not valid
            if (!isEmailValid) {
                Text(
                    text = "Bitte geben Sie eine gültige E-Mail-Adresse ein.",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Password text field
            CustomTextField(
                value = passwort,
                onValueChange = { newValue ->
                    passwort = newValue
                    with(sharedPreferences.edit()) {
                        putString("passwort", passwort)
                        apply()
                    }
                },
                placeholder = "Passwort",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Navigate to login page if already registered
            ClickableText(
                text = AnnotatedString(
                    "Schon Registriert? Hier geht's zum Login!"
                ),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 18.sp,
                    color = Color.White,
                ),
                onClick = {
                    // Navigate to Login page
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Registration button
            val username = ""
            val password = ""
            MyButton1(
                text = "Registrieren",
                vorname = vorname,
                nachname = nachname,
                username = username,
                password = password,
                onButtonClick = { vorname, nachname, username, password ->
                    // Hier kannst du die Registrierungslogik implementieren
                    println("Vorname: $vorname, Nachname: $nachname, E-Mail: $username, Passwort: $password")
                }
            ).Display()

        }
    }
}
