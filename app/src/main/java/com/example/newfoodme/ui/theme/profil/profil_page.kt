package com.example.newfoodme.ui.theme.profil

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.newfoodme.ui.theme.login.MainActivity
import com.example.newfoodme.ui.theme.search.SearchPageActivity
import com.example.newfoodme.ui.theme.home.HomePageActivity

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //query the stored values for norname and last name
        val sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        val initialVorname = sharedPreferences.getString("vorname", "") ?: ""
        val initialNachname = sharedPreferences.getString("nachname", "") ?: ""

        setContent {
            MaterialTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    content = { innerPadding ->
                        Profile(
                            vorname = initialVorname,
                            nachname = initialNachname,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                )
            }
        }
    }
}

// Creating composable (function) "Profile"
@Composable
fun Profile(vorname: String, nachname: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {

        // Header
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

        // Main part
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Text(
                text = "Fragen an uns?",
                style = MaterialTheme.typography.h6
            )
            Text(
                text = "Alle Fragen und Antworten findest du hier",
                style = MaterialTheme.typography.body2,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            ProfileItem(icon = Icons.Default.Person, title = "Personenbezogen Daten")
            ProfileItem(icon = Icons.Default.LocationOn, title = "Lieferadressen")

            Spacer(modifier = Modifier.height(24.dp))

            ProfileItem(icon = Icons.Default.Public, title = "Land")
            ProfileItem(icon = Icons.Default.GpsFixed, title = "Ortungsdienste")
            ProfileItem(icon = Icons.Default.Language, title = "Sprache")
            ProfileItem(icon = Icons.Default.Notifications, title = "Benachrichtigung")

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Links",
                style = MaterialTheme.typography.h6
            )
            ProfileItem(icon = Icons.Default.Info, title = "Datenschutz")
            ProfileItem(icon = Icons.Default.Gavel, title = "Rechtliches")
            ProfileItem(icon = Icons.Default.Description, title = "Impressum")
            ProfileItem(icon = Icons.Default.Description, title = "AGB")
        }

        // Logout Button
        Button(
            onClick = {
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFA500))
        ) {
            Text(text = "Abmelden", color = Color.White)
        }

        // Footer mit der Bottom Navigation
        BottomNavigation(
            backgroundColor = Color(0xFFFFA500)
        ) {
            BottomNavigationItem(
                icon = { Icon(Icons.Default.Home, contentDescription = "Entdecke") },
                label = { Text("Entdecke") },
                selected = false,
                onClick = { context.startActivity(Intent(context, HomePageActivity::class.java)) }
            )
            BottomNavigationItem(
                icon = { Icon(Icons.Default.Search, contentDescription = "Suche") },
                label = { Text("Suche") },
                selected = false,
                onClick = { context.startActivity(Intent(context, SearchPageActivity::class.java)) }
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

@Composable
fun ProfileItem(icon: ImageVector, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, style = MaterialTheme.typography.body1)
    }
}
