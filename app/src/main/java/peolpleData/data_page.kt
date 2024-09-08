package com.example.newfoodme.ui.theme.peopleData

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newfoodme.ui.theme.classes.CustomTextField
import androidx.compose.ui.text.font.FontWeight

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        val initialVorname = sharedPreferences.getString("vorname", "") ?: ""
        val initialNachname = sharedPreferences.getString("nachname", "") ?: ""
        val initialUsername = sharedPreferences.getString("username", "") ?: ""
        val initialPassword = sharedPreferences.getString("password", "") ?: ""
        val initialEmail = sharedPreferences.getString("email", "") ?: ""

        setContent {
            MaterialTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    content = { innerPadding ->
                        Profile(
                            initialVorname = initialVorname,
                            initialNachname = initialNachname,
                            initialUsername = initialUsername,
                            initialPassword = initialPassword,
                            initialEmail = initialEmail,
                            sharedPreferences = sharedPreferences,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun Profile(
    initialVorname: String,
    initialNachname: String,
    initialUsername: String,
    initialPassword: String,
    initialEmail: String,
    sharedPreferences: SharedPreferences,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var vorname by remember { mutableStateOf(initialVorname) }
    var nachname by remember { mutableStateOf(initialNachname) }
    var username by remember { mutableStateOf(initialUsername) }
    var password by remember { mutableStateOf(initialPassword) }
    var email by remember { mutableStateOf(initialEmail) }
    var isEmailValid by remember { mutableStateOf(true) }

    var newVorname by remember { mutableStateOf(initialVorname) }
    var newNachname by remember { mutableStateOf(initialNachname) }
    var newUsername by remember { mutableStateOf(initialUsername) }
    var newPassword by remember { mutableStateOf(initialPassword) }
    var newEmail by remember { mutableStateOf(initialEmail) }


    var errorMessage by remember { mutableStateOf("") }


    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // Beginning of the header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFA500))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(text = "Guten Tag $vorname $nachname", style = MaterialTheme.typography.h6)
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFBDBDBD), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = vorname.firstOrNull()?.toString() ?: "", style = MaterialTheme.typography.body1)
                }
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {


            Text(
                text = "Personenbezogene Daten",
                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(16.dp))


            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }

            CustomTextField(
                value = newVorname,
                onValueChange = { newValue ->
                    newVorname = newValue
                },
                placeholder = "Vorname",
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )


            CustomTextField(
                value = newNachname,
                onValueChange = { newValue ->
                    newNachname = newValue
                },
                placeholder = "Nachname",
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            CustomTextField(
                value = newEmail,
                onValueChange = {
                    newEmail = it
                    isEmailValid = isValidEmail(it)
                },
                placeholder = "E-Mail-Adresse",
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            // Wenn die E-Mail-Adresse nicht im richtigen Format vorliegt, erscheint die folgende Ausgabe
            if (!isEmailValid) {
                Text(
                    text = "Bitte geben Sie eine gültige E-Mail-Adresse ein.",
                    color = Color.Red,
                    style = MaterialTheme.typography.body1,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp) // Weniger Abstand zum oberen Textfeld
                        .align(Alignment.CenterHorizontally) // Zentrieren des Textes
                )
            }


            CustomTextField(
                value = newPassword,
                onValueChange = { newValue ->
                    newPassword = newValue
                },
                placeholder = "Passwort",
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            // Button for updating the changes
            Button(
                onClick = {
                    if (newVorname.isNotBlank() && newNachname.isNotBlank() && newUsername.isNotBlank() && newPassword.isNotBlank() && isEmailValid) {

                        vorname = newVorname
                        nachname = newNachname
                        username = newUsername
                        password = newPassword
                        email = newEmail
                        with(sharedPreferences.edit()) {
                            putString("vorname", vorname)
                            putString("nachname", nachname)
                            putString("username", username)
                            putString("password", password)
                            putString("email", email)
                            apply()
                        }

                        errorMessage = ""
                    } else {
                        // display error message
                        errorMessage = "Bitte füllen Sie alle Eingabefelder aus."
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFA500))
            ) {
                Text(text = "Ändern", color = Color.White)
            }
        }

        // Footer with the bottom navigation
        BottomNavigation(
            backgroundColor = Color(0xFFFFA500)
        ) {
            BottomNavigationItem(
                icon = { Icon(Icons.Default.Home, contentDescription = "Entdecke") },
                label = { Text("Entdecke") },
                selected = false,
                onClick = {}
            )

            BottomNavigationItem(
                icon = { Icon(Icons.Default.Person, contentDescription = "Mein Profil") },
                label = { Text("Mein Profil") },
                selected = true,
                onClick = {}
            )
        }
    }
}
