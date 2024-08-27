package com.example.newfoodme.ui.theme.registration

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.text.style.TextDecoration
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

//mutableStateof for saving the registration data
@Composable
fun RegistrationScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)

    var vorname by remember { mutableStateOf(sharedPreferences.getString("vorname", "") ?: "") }
    var nachname by remember { mutableStateOf(sharedPreferences.getString("nachname", "") ?: "") }
    var email by remember { mutableStateOf(sharedPreferences.getString("email", "") ?: "") }
    var passwort by remember { mutableStateOf(sharedPreferences.getString("passwort", "") ?: "") }
    var isEmailValid by remember { mutableStateOf(true) }

    //name has to have min. 3 letters
    fun isValidName(name: String): Boolean {
        return name.length >= 3
    }

    //password has to have min. 8 letters and 1 digit
    fun isValidPassword(password: String): Boolean {
        return password.length >= 8 && password.any { it.isDigit() }
    }

    //validates if and email adress with its compontens is typed in
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    //everything which is build on the website is in the box because its easier to move the entire page elements together instead each element alone
    Box(
        modifier = modifier.fillMaxSize()
    ) {

        //background for registration page
        Image(
            painter = painterResource(id = R.drawable.login_registration_background_dark),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        //foodme logo
        Column(
            modifier = Modifier.padding(top = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.foodme_logo),
                contentDescription = null
            )
        }

        //for the rough positioning of headline, text fields and button
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = 300.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //registration headline
            Text(
                text = "Registrierung",
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 38.sp),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))

            //first name textfield
            CustomTextField(
                value = vorname,
                onValueChange = { vorname = it },
                placeholder = "Vorname",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            //last name textfield
            CustomTextField(
                value = nachname,
                onValueChange = { nachname = it },
                placeholder = "Nachname",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            //email address text field
            CustomTextField(
                value = email,
                onValueChange = {
                    email = it
                    isEmailValid = isValidEmail(it)
                },
                placeholder = "E-Mail-Adresse",
                modifier = Modifier.fillMaxWidth()
            )

            //when email adress is not in the correct form the following output appears
            if (!isEmailValid) {
                Text(
                    text = "Bitte geben Sie eine gültige E-Mail-Adresse ein.",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            //password text field
            CustomTextField(
                value = passwort,
                onValueChange = { passwort = it },
                placeholder = "Passwort",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))


            //move to login page if you are already registrated
            ClickableText(
                text = AnnotatedString(
                    "Schon Registriert? Hier geht's zum Login!"
                ),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 18.sp,
                    color = Color.White,
                ),
                onClick = {
                    // Navigieren zur Login-Seite
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            //button saves the data in mutableStateOf when you click it
            val registerButton = MyButton1(
                text = "Registrieren",
                vorname = vorname,
                //nachname = nachname, überprüfen!!
                username = email,
                password = passwort,
                onButtonClick = {
                    if (isEmailValid && isValidName(vorname) && isValidName(nachname) && isValidPassword(passwort)) {
                        with(sharedPreferences.edit()) {
                            putString("vorname", vorname)
                            putString("nachname", nachname)
                            putString("email", email)
                            putString("passwort", passwort)
                            apply()
                        }
                    }
                }
            )
            registerButton.Display()
        }
    }
}
