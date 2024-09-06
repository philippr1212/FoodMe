package com.example.newfoodme.ui.theme.profil

//Import of different  android and compose (libraries)
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Description
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import com.example.newfoodme.ui.theme.login.MainActivity
//import com.example.newfoodme.ui.theme.search.SearchPageActivity



//Creating class "ProfileActivity"
class ProfileActivity : ComponentActivity() { //ProfileActivity as a container for compose based UI
    override fun onCreate(savedInstanceState: Bundle?) { //Method starts with the Start of the Activity
        super.onCreate(savedInstanceState)
        setContent { //Activity gets set
            MaterialTheme {//Layout
                Scaffold( //Layout
                    modifier = Modifier.fillMaxSize(),
                    content = { innerPadding ->
                        Profile(username = "Benutzername", modifier = Modifier.padding(innerPadding)) //Main element of the UI
                    }
                )
            }
        }
    }
}
//Creating composable (function) "Profile"
@Composable
fun Profile(username: String, modifier: Modifier = Modifier) { //Function shows the profile page of the user
    val context = LocalContext.current
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
                Text(text = "Guten Tag \"$username\"", style = MaterialTheme.typography.h6)
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFBDBDBD), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "B", style = MaterialTheme.typography.body1)
                }
            }
        }

        // Beginning of the main part
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

        // Button for logout
        Button(
            onClick = {
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent) //After clicking on the button the user is redirected to the next login page
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFA500))
        ) {
            Text(text = "Abmelden", color = Color.White)
        }

        // Beginning of the footer with the bottom navigation
        BottomNavigation(
            backgroundColor = Color(0xFFFFA500)
        ) {
            BottomNavigationItem(
                icon = { Icon(Icons.Default.Home, contentDescription = "Entdecke") },
                label = { Text("Entdecke") },
                selected = false,
                onClick = {} //Navigation to the homepage
            )

            //BottomNavigationItem(
            //icon = { Icon(Icons.Default.Search, contentDescription = "Suche") },
            //label = { Text("Suche") },
            //selected = false,
            //onClick = {context.startActivity(Intent(context, SearchPageActivity::class.java))} //Navigation to the search page
            //)
            BottomNavigationItem(
                icon = { Icon(Icons.Default.Person, contentDescription = "Mein Profil") },
                label = { Text("Mein Profil") },
                selected = true,
                onClick = {}
            )
        }
    }
}
//Creating composable (function) "ProfileItem"
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