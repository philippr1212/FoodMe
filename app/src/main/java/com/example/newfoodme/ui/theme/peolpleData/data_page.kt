package com.example.newfoodme.ui.theme.peopleData

import android.content.Context
import android.content.Intent
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newfoodme.ui.theme.classes.CustomTextField
import androidx.compose.ui.text.font.FontWeight
import com.example.newfoodme.ui.theme.home.HomePageActivity
import com.example.newfoodme.ui.theme.profil.ProfileActivity
import com.example.newfoodme.ui.theme.search.SearchPageActivity

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        val initialVorname = sharedPreferences.getString("vorname", "") ?: ""
        val initialNachname = sharedPreferences.getString("nachname", "") ?: ""
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
                            initialPassword = initialPassword,
                            initialEmail = initialEmail,
                            sharedPreferences = sharedPreferences,
                            modifier = Modifier.padding(innerPadding)
                        )
                    },
                    bottomBar = {
                        BottomNavigation(
                            backgroundColor = Color(0xFFFFA500)
                        ) {
                            // Navigation to HomePageActivity
                            BottomNavigationItem(
                                icon = { Icon(Icons.Default.Home, contentDescription = "Entdecke") },
                                label = { Text("Entdecke") },
                                selected = false,
                                onClick = {
                                    startActivity(
                                        Intent(
                                            this@ProfileActivity,
                                            HomePageActivity::class.java
                                        )
                                    )
                                }
                            )
                            // Navigation to SearchPageActivity
                            BottomNavigationItem(
                                icon = { Icon(Icons.Default.Search, contentDescription = "Suche") },
                                label = { Text("Suche") },
                                selected = false,
                                onClick = {
                                    startActivity(
                                        Intent(
                                            this@ProfileActivity,
                                            SearchPageActivity::class.java
                                        )
                                    )
                                }
                            )
                            // Navigation to ProfileActivity
                            BottomNavigationItem(
                                icon = { Icon(Icons.Default.Person, contentDescription = "Mein Profil") },
                                label = { Text("Mein Profil") },
                                selected = true,
                                onClick = {
                                    startActivity(
                                        Intent(
                                            this@ProfileActivity,
                                            ProfileActivity::class.java
                                        )
                                    )
                                }
                            )
                        }
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
    initialPassword: String,
    initialEmail: String,
    sharedPreferences: SharedPreferences,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var vorname by remember { mutableStateOf(initialVorname) }
    var nachname by remember { mutableStateOf(initialNachname) }
    var password by remember { mutableStateOf(initialPassword) }
    var email by remember { mutableStateOf(initialEmail) }
    var isEmailValid by remember { mutableStateOf(true) }

    var newVorname by remember { mutableStateOf(initialVorname) }
    var newNachname by remember { mutableStateOf(initialNachname) }
    var newPassword by remember { mutableStateOf(initialPassword) }
    var newEmail by remember { mutableStateOf(initialEmail) }

    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // generell site layout settings
    Column(modifier = Modifier.fillMaxSize()) {
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
                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = newVorname,
                onValueChange = { newVorname = it },
                placeholder = "Vorname",
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = newNachname,
                onValueChange = { newNachname = it },
                placeholder = "Nachname",
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = newEmail,
                onValueChange = {
                    newEmail = it
                    isEmailValid = isValidEmail(it)
                },
                placeholder = "E-Mail-Adresse",
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                placeholder = "Passwort",
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            Button(
                onClick = {
                    when {
                        newVorname.isBlank() || newNachname.isBlank() || newPassword.isBlank() -> {
                            errorMessage = "Bitte füllen Sie alle Eingabefelder aus."
                            successMessage = ""
                        }
                        !isEmailValid -> {
                            errorMessage = "Bitte geben Sie eine gültige E-Mail-Adresse ein."
                            successMessage = ""
                        }
                        else -> {
                            vorname = newVorname
                            nachname = newNachname
                            password = newPassword
                            email = newEmail
                            with(sharedPreferences.edit()) {
                                putString("vorname", vorname)
                                putString("nachname", nachname)
                                putString("password", password)
                                putString("email", email)
                                apply()
                            }
                            errorMessage = ""
                            successMessage = "Daten wurden erfolgreich geändert."
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFA500))
            ) {
                Text(text = "Ändern", color = Color.White)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                when {
                    errorMessage.isNotEmpty() -> {
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.body1,
                        )
                    }
                    successMessage.isNotEmpty() -> {
                        Text(
                            text = successMessage,
                            color = Color.Green,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.body1,
                        )
                    }
                }
            }
        }
    }
}
