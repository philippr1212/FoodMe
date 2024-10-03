package com.example.newfoodme.ui.theme.home

// Import of different android, compose and some individuals (libraries)
//noinspection UsingMaterialAndMaterial3Libraries
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DropdownMenu
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DropdownMenuItem
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.FloatingActionButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Switch
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.newfoodme.R
import com.example.newfoodme.ui.theme.profil.ProfileActivity
import com.example.newfoodme.ui.theme.search.SearchPageActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import androidx.compose.ui.graphics.Color as AppColor

//Creating class "HomePageActivity"
class HomePageActivity : ComponentActivity(), OnMapReadyCallback { //HomepageActivity inherits from ComponentActivity, OnMapReadyCallback --> Interface with method OnMapRaedy

    //Variables are declared (for Activity) --> Late-init --> All variables with late-init are later on initialized
    private lateinit var mapView: com.google.android.gms.maps.MapView //Stores the showing of the gooogle map
    private lateinit var googleMap: GoogleMap //Stores the interactiv map
    private lateinit var locationPermissionRequest: ActivityResultLauncher<Array<String>> //Stores the requested permission for the location
    private lateinit var fusedLocationClient: FusedLocationProviderClient //Stores the exact location of the user

    //Variables for showing the restaurants, cafes and bars on the map
    private var showRestaurants by mutableStateOf(true)
    private var showCafes by mutableStateOf(true)
    private var showBars by mutableStateOf(true)

    private var searchQuery by mutableStateOf(TextFieldValue("")) //Saves the text in search query
    private var suggestions by mutableStateOf(listOf<String>()) //Saves the list of suggestions

    private var markersMap = mutableMapOf<String, MarkerOptions>() //Saves sposition and tile for the example markers
    private var markerReferences = mutableMapOf<String, Marker>() // Saves the referneces of the example markers
    private var lastClickedMarker: Marker? = null // Saves the last clicked marker
    private var permissionsGranted = false //Saves the permissions for the locstion of the users
    private var mapReady = false // Map ready for interaction

//-------------------Creating a list of example markers (restaurants, cafes and bars) with AI (artificial inelligence)------------------------------------------------------------------------------------
    private val restaurants = listOf(
        MarkerOptions().position(LatLng(53.5416, 9.9911)).title("Vlet in der Speicherstadt").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5588, 9.9814)).title("Henssler Henssler").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5534, 9.9912)).title("Restaurant Haerlin").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5638, 9.9735)).title("Gasthaus Wichmann").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5515, 9.9812)).title("Taverna Kiriakos").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5585, 9.9819)).title("Lüftl-Keller").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5528, 9.9926)).title("Café du Nord").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5565, 9.9794)).title("Tschebull").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5510, 9.9660)).title("Bullerei").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5491, 9.9810)).title("Heldenplatz").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5515, 9.9912)).title("Lili's Bistro").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5473, 9.9920)).title("Hofbräuhaus Hamburg").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5524, 9.9787)).title("Zum Alten Mädchen").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5514, 9.9897)).title("La Vela").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5482, 9.9821)).title("Mutterland").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5564, 9.9510)).title("Fischereihafen Restaurant").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5440, 9.9770)).title("Skyline Bar 20up").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5511, 9.9723)).title("Bistro du Bac").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5478, 9.9781)).title("Bistro Kutscher").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5474, 9.9709)).title("Schweinske").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5501, 9.9776)).title("Gosch Sylt").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5520, 9.9928)).title("Zwei Stühle").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5580, 9.9801)).title("Park Restaurant").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5400, 9.9632)).title("Le Canard").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5543, 9.9760)).title("Auster Bar & Grill").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5523, 9.9801)).title("Café Erdapfel").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5538, 9.9874)).title("Elb-Insel").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5549, 9.9798)).title("Mayer's Restaurant").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5566, 9.9822)).title("Marion's Restaurant").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5504, 9.9716)).title("Edelcafé").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5587, 9.9702)).title("Ristorante Da Vinci").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5492, 9.9678)).title("Harbour View Restaurant").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5517, 9.9645)).title("Sonnendeck").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5631, 9.9789)).title("Marina Restaurant").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5578, 9.9733)).title("Hafenblick").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5468, 9.9819)).title("Scharhörn").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5603, 9.9797)).title("Wolkenlos").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5584, 9.9904)).title("Alsterperle").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5513, 9.9742)).title("Avenue").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5591, 9.9703)).title("City Grill").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5505, 9.9657)).title("Wienerwald").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5531, 9.9770)).title("Hamburg 7").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5562, 9.9750)).title("Alsterküche").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5537, 9.9758)).title("Schöne Aussichten").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5602, 9.9737)).title("Eisvogel").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5583, 9.9748)).title("Portofino").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5557, 9.9698)).title("Fischmarkt").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5540, 9.9736)).title("Kleine Welt").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5610, 9.9715)).title("Café Linde").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5601, 9.9723)).title("Kaffekommune").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5542, 9.9696)).title("Wunderbar").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5484, 9.9745)).title("Oberdeck").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5525, 9.9644)).title("Gusto").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5568, 9.9794)).title("Seepferdchen").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5485, 9.9806)).title("Eckstein").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5503, 9.9764)).title("Altes Land").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5496, 9.9695)).title("Schäferstube").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5506, 9.9717)).title("Kaffekommune").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5553, 9.9754)).title("Eiermann").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5479, 9.9710)).title("Tafelspitz").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5567, 9.9651)).title("Münchner Kindl").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5489, 9.9720)).title("Bistrot du Lyon").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5522, 9.9706)).title("Grillmeister").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5536, 9.9637)).title("Lutter & Wegner").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5534, 9.9692)).title("Vino").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5482, 9.9676)).title("Kasematten").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5508, 9.9741)).title("Imbiss Zentrale").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5514, 9.9646)).title("Biergarten").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5544, 9.9670)).title("Wok & Roll").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5529, 9.9653)).title("Hafenhotel").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5599, 9.9744)).title("Schnitzelhaus").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5530, 9.9707)).title("Kaiser Café").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5606, 9.9720)).title("Café der Hafen").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5547, 9.9658)).title("Luzifer").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5495, 9.9732)).title("Rote Küche").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5535, 9.9712)).title("Alsterbistro").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5600, 9.9761)).title("Seevogel").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5588, 9.9650)).title("Boathouse").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5521, 9.9792)).title("Ristorante Aurora").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5518, 9.9662)).title("Bierkeller").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5534, 9.9713)).title("Hamburger Fischmarkt").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5569, 9.9671)).title("Café Stern").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5582, 9.9709)).title("Kaffehus").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5571, 9.9725)).title("Hafenlokal").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5523, 9.9654)).title("Delirium").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5509, 9.9727)).title("Café Schöne Aussicht").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5490, 9.9779)).title("Gourmetküche").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5528, 9.9738)).title("Hafenblick Café").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5589, 9.9656)).title("Kleine Kneipe").snippet("Restaurant"),
        MarkerOptions().position(LatLng(53.5563, 9.9772)).title("Marina Bistro").snippet("Restaurant")
    )
    private val cafes = listOf(
        MarkerOptions().position(LatLng(53.5501, 9.9942)).title("Café Paris").snippet("Café"),
        MarkerOptions().position(LatLng(53.5515, 9.9932)).title("Café Schmidt").snippet("Café"),
        MarkerOptions().position(LatLng(53.5538, 9.9822)).title("Café Elbgold").snippet("Café"),
        MarkerOptions().position(LatLng(53.5521, 9.9702)).title("Kaffekommune").snippet("Café"),
        MarkerOptions().position(LatLng(53.5581, 9.9787)).title("Kaffeemühle").snippet("Café"),
        MarkerOptions().position(LatLng(53.5528, 9.9763)).title("Café Kleinod").snippet("Café"),
        MarkerOptions().position(LatLng(53.5546, 9.9772)).title("Café Aroma").snippet("Café"),
        MarkerOptions().position(LatLng(53.5553, 9.9730)).title("Café Deli").snippet("Café"),
        MarkerOptions().position(LatLng(53.5610, 9.9715)).title("Café Linde").snippet("Café"),
        MarkerOptions().position(LatLng(53.5505, 9.9812)).title("Mutterland Café").snippet("Café"),
        MarkerOptions().position(LatLng(53.5534, 9.9711)).title("Kaffeekommune").snippet("Café"),
        MarkerOptions().position(LatLng(53.5585, 9.9799)).title("Coffee House").snippet("Café"),
        MarkerOptions().position(LatLng(53.5516, 9.9865)).title("Café Marie").snippet("Café"),
        MarkerOptions().position(LatLng(53.5539, 9.9652)).title("Café Kreuzberg").snippet("Café"),
        MarkerOptions().position(LatLng(53.5551, 9.9754)).title("Café Convent").snippet("Café"),
        MarkerOptions().position(LatLng(53.5544, 9.9679)).title("Coffee Fellows").snippet("Café"),
        MarkerOptions().position(LatLng(53.5567, 9.9788)).title("Café im Park").snippet("Café"),
        MarkerOptions().position(LatLng(53.5588, 9.9749)).title("Café Eppendorfer").snippet("Café"),
        MarkerOptions().position(LatLng(53.5523, 9.9730)).title("Espresso & Co.").snippet("Café"),
        MarkerOptions().position(LatLng(53.5537, 9.9721)).title("Café Waffelhaus").snippet("Café"),
        MarkerOptions().position(LatLng(53.5582, 9.9741)).title("Café Planten un Blomen").snippet("Café"),
        MarkerOptions().position(LatLng(53.5559, 9.9717)).title("Café Rösterei").snippet("Café"),
        MarkerOptions().position(LatLng(53.5561, 9.9751)).title("Kaffee-Kommune").snippet("Café"),
        MarkerOptions().position(LatLng(53.5603, 9.9736)).title("Café Alsterblick").snippet("Café"),
        MarkerOptions().position(LatLng(53.5572, 9.9739)).title("Café Hamburg").snippet("Café"),
        MarkerOptions().position(LatLng(53.5580, 9.9707)).title("Café Kuchen & Kaffe").snippet("Café"),
        MarkerOptions().position(LatLng(53.5534, 9.9670)).title("Café Spezial").snippet("Café"),
        MarkerOptions().position(LatLng(53.5527, 9.9714)).title("Café Waffelzeit").snippet("Café"),
        MarkerOptions().position(LatLng(53.5509, 9.9732)).title("Café Maria").snippet("Café"),
        MarkerOptions().position(LatLng(53.5567, 9.9728)).title("Kaffekommune").snippet("Café"),
        MarkerOptions().position(LatLng(53.5524, 9.9704)).title("Café Treibgut").snippet("Café"),
        MarkerOptions().position(LatLng(53.5558, 9.9737)).title("Café Espresso").snippet("Café"),
        MarkerOptions().position(LatLng(53.5563, 9.9711)).title("Café Art").snippet("Café"),
        MarkerOptions().position(LatLng(53.5545, 9.9728)).title("Kaffekommune").snippet("Café"),
        MarkerOptions().position(LatLng(53.5584, 9.9746)).title("Café Brecht").snippet("Café"),
        MarkerOptions().position(LatLng(53.5530, 9.9775)).title("Café am Elbstrand").snippet("Café"),
        MarkerOptions().position(LatLng(53.5522, 9.9756)).title("Café Alster").snippet("Café"),
        MarkerOptions().position(LatLng(53.5577, 9.9759)).title("Kaffeehaus").snippet("Café"),
        MarkerOptions().position(LatLng(53.5535, 9.9706)).title("Café Kreation").snippet("Café"),
        MarkerOptions().position(LatLng(53.5566, 9.9743)).title("Café Guter Tag").snippet("Café"),
        MarkerOptions().position(LatLng(53.5503, 9.9788)).title("Café Rathaus").snippet("Café"),
        MarkerOptions().position(LatLng(53.5525, 9.9700)).title("Café Elbstrand").snippet("Café"),
        MarkerOptions().position(LatLng(53.5570, 9.9726)).title("Café Lilla").snippet("Café"),
        MarkerOptions().position(LatLng(53.5514, 9.9760)).title("Café Literatur").snippet("Café"),
        MarkerOptions().position(LatLng(53.5543, 9.9736)).title("Café Kaffeekommune").snippet("Café"),
        MarkerOptions().position(LatLng(53.5568, 9.9755)).title("Café Alsterpark").snippet("Café"),
        MarkerOptions().position(LatLng(53.5524, 9.9679)).title("Café Lieder").snippet("Café"),
        MarkerOptions().position(LatLng(53.5538, 9.9748)).title("Café Stolzen").snippet("Café"),
        MarkerOptions().position(LatLng(53.5550, 9.9703)).title("Café Küstengold").snippet("Café"),
        MarkerOptions().position(LatLng(53.5526, 9.9723)).title("Café Striebel").snippet("Café"),
        MarkerOptions().position(LatLng(53.5531, 9.9719)).title("Café Becher").snippet("Café"),
        MarkerOptions().position(LatLng(53.5569, 9.9694)).title("Café Heiß").snippet("Café"),
        MarkerOptions().position(LatLng(53.5528, 9.9733)).title("Café Rösterei").snippet("Café"),
        MarkerOptions().position(LatLng(53.5576, 9.9730)).title("Café Nord").snippet("Café"),
        MarkerOptions().position(LatLng(53.5560, 9.9691)).title("Café Frisch").snippet("Café"),
        MarkerOptions().position(LatLng(53.5507, 9.9736)).title("Café Stübchen").snippet("Café"),
        MarkerOptions().position(LatLng(53.5521, 9.9708)).title("Café Kunst").snippet("Café"),
        MarkerOptions().position(LatLng(53.5581, 9.9677)).title("Café Perle").snippet("Café"),
        MarkerOptions().position(LatLng(53.5561, 9.9715)).title("Café Nostalgie").snippet("Café"),
        MarkerOptions().position(LatLng(53.5510, 9.9690)).title("Café Oase").snippet("Café"),
        MarkerOptions().position(LatLng(53.5568, 9.9682)).title("Café Lale").snippet("Café"),
        MarkerOptions().position(LatLng(53.5532, 9.9695)).title("Café Schöner Morgen").snippet("Café"),
        MarkerOptions().position(LatLng(53.5523, 9.9689)).title("Café Seepark").snippet("Café"),
        MarkerOptions().position(LatLng(53.5587, 9.9693)).title("Café Altona").snippet("Café"),
        MarkerOptions().position(LatLng(53.5534, 9.9700)).title("Café Himmelreich").snippet("Café"),
        MarkerOptions().position(LatLng(53.5542, 9.9706)).title("Café Kaffeekommune").snippet("Café"),
        MarkerOptions().position(LatLng(53.5562, 9.9724)).title("Café Lieblingsplatz").snippet("Café"),
        MarkerOptions().position(LatLng(53.5569, 9.9740)).title("Café Nordsee").snippet("Café"),
        MarkerOptions().position(LatLng(53.5575, 9.9731)).title("Café Helgoland").snippet("Café"),
        MarkerOptions().position(LatLng(53.5547, 9.9691)).title("Café Kloster").snippet("Café"),
        MarkerOptions().position(LatLng(53.5563, 9.9721)).title("Café Sternschanze").snippet("Café"),
        MarkerOptions().position(LatLng(53.5581, 9.9743)).title("Café Hansestadt").snippet("Café"),
        MarkerOptions().position(LatLng(53.5522, 9.9724)).title("Café Frischluft").snippet("Café"),
        MarkerOptions().position(LatLng(53.5537, 9.9702)).title("Café Tête-à-Tête").snippet("Café"),
        MarkerOptions().position(LatLng(53.5566, 9.9705)).title("Café Elb-Lounge").snippet("Café"),
        MarkerOptions().position(LatLng(53.5527, 9.9745)).title("Café Vollmond").snippet("Café"),
        MarkerOptions().position(LatLng(53.5582, 9.9698)).title("Café Alstertal").snippet("Café"),
        MarkerOptions().position(LatLng(53.5538, 9.9694)).title("Café Süß").snippet("Café"),
        MarkerOptions().position(LatLng(53.5565, 9.9723)).title("Café Kultur").snippet("Café"),
        MarkerOptions().position(LatLng(53.5514, 9.9719)).title("Café Bunt").snippet("Café"),
        MarkerOptions().position(LatLng(53.5587, 9.9679)).title("Café Timmermann").snippet("Café"),
        MarkerOptions().position(LatLng(53.5529, 9.9688)).title("Café Spürnase").snippet("Café"),
        MarkerOptions().position(LatLng(53.5556, 9.9728)).title("Café Luise").snippet("Café"),
        MarkerOptions().position(LatLng(53.5562, 9.9696)).title("Café Träumerei").snippet("Café"),
        MarkerOptions().position(LatLng(53.5523, 9.9702)).title("Café Verliebt").snippet("Café"),
        MarkerOptions().position(LatLng(53.5545, 9.9714)).title("Café Geniessen").snippet("Café"),
        MarkerOptions().position(LatLng(53.5560, 9.9746)).title("Café Gaumenfreude").snippet("Café"),
        MarkerOptions().position(LatLng(53.5583, 9.9749)).title("Café Kaffeekommune").snippet("Café"),
        MarkerOptions().position(LatLng(53.5526, 9.9752)).title("Café Roter Faden").snippet("Café"),
        MarkerOptions().position(LatLng(53.5563, 9.9715)).title("Café Genusswelt").snippet("Café"),
        MarkerOptions().position(LatLng(53.5585, 9.9761)).title("Café Laune").snippet("Café"),
        MarkerOptions().position(LatLng(53.5528, 9.9675)).title("Café Ritter").snippet("Café"),
        MarkerOptions().position(LatLng(53.5577, 9.9703)).title("Café Goldstück").snippet("Café"),
        MarkerOptions().position(LatLng(53.5521, 9.9683)).title("Café Schnecke").snippet("Café"),
        MarkerOptions().position(LatLng(53.5561, 9.9698)).title("Café Lichtblick").snippet("Café"),
        MarkerOptions().position(LatLng(53.5582, 9.9744)).title("Café Blau").snippet("Café")
    )
    private val bars = listOf(
        MarkerOptions().position(LatLng(53.5525, 9.9754)).title("The Bird").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5502, 9.9800)).title("Le Lion").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5541, 9.9705)).title("Zur Alten Liebe").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5553, 9.9742)).title("Goldene Bar").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5534, 9.9724)).title("Bar Centrale").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5508, 9.9713)).title("Elb Lounge").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5537, 9.9691)).title("The Ricks").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5586, 9.9706)).title("Hafenklang").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5501, 9.9672)).title("Schellfischposten").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5513, 9.9760)).title("Bock auf Bar").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5562, 9.9708)).title("Kiez Club").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5520, 9.9717)).title("Bar Italia").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5556, 9.9693)).title("Millerntor Stadium Bar").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5518, 9.9745)).title("Oberhafenkantine").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5529, 9.9694)).title("Zur alten Mühle").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5500, 9.9753)).title("Bar 69").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5531, 9.9725)).title("Boiler Room").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5563, 9.9736)).title("Psycho Fuzz").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5540, 9.9744)).title("The Usual").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5528, 9.9772)).title("Schmidt Theater Bar").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5512, 9.9714)).title("Astra Stube").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5536, 9.9763)).title("Club 20457").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5552, 9.9687)).title("Küche 48").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5507, 9.9691)).title("Kaiserkeller").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5519, 9.9734)).title("Café des Artistes").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5526, 9.9708)).title("Dodo").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5537, 9.9691)).title("Bar The Ricks").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5554, 9.9688)).title("Die Bank").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5524, 9.9712)).title("The St. Pauli Bar").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5508, 9.9713)).title("Elb Lounge").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5532, 9.9697)).title("Hafenliebe").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5543, 9.9760)).title("The Chapel").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5568, 9.9721)).title("The Old School").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5515, 9.9700)).title("Bierhaus").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5522, 9.9741)).title("Essen & Trinken").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5538, 9.9750)).title("Die Schnapsbar").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5503, 9.9712)).title("Schultenhof").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5558, 9.9740)).title("Platzhirsch").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5527, 9.9707)).title("Bar Coaster").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5560, 9.9736)).title("Kommandohaus").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5524, 9.9677)).title("Schmidtchen").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5562, 9.9708)).title("Kiez Club").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5511, 9.9732)).title("Ristorante Bella Italia").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5506, 9.9700)).title("Fischmarkt Bar").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5523, 9.9762)).title("Bar & Lounge").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5550, 9.9716)).title("Café Terrasse").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5545, 9.9692)).title("St. Pauli Club").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5553, 9.9742)).title("Goldene Bar").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5527, 9.9756)).title("Hafenbräu").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5549, 9.9723)).title("Schwaben Quatier").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5518, 9.9745)).title("Oberhafenkantine").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5525, 9.9723)).title("Der Feuervogel").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5537, 9.9744)).title("Fischerhaus").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5514, 9.9646)).title("Biergarten").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5548, 9.9757)).title("Bar 54").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5523, 9.9682)).title("Hafenbar").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5530, 9.9752)).title("Trattoria Milano").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5524, 9.9696)).title("Café Olé").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5511, 9.9685)).title("Club Saufhaus").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5544, 9.9700)).title("Hafen Lounge").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5555, 9.9756)).title("Gosch Bar").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5539, 9.9698)).title("Günters Bar").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5529, 9.9721)).title("Fischer's").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5562, 9.9708)).title("Kiez Club").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5508, 9.9713)).title("Elb Lounge").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5568, 9.9743)).title("Alte Freiheit").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5519, 9.9725)).title("Kieztanz").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5586, 9.9706)).title("Hafenklang").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5532, 9.9751)).title("Bar 4U").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5510, 9.9706)).title("Fischmarkt Lounge").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5528, 9.9762)).title("Tanzbar").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5534, 9.9703)).title("Club Orange").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5541, 9.9734)).title("Millerntor Lounge").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5521, 9.9738)).title("Wolke 7").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5537, 9.9752)).title("Café Bourbon").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5516, 9.9731)).title("Schöne Aussichten").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5509, 9.9741)).title("Club 76").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5525, 9.9717)).title("Schwaben Lounge").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5547, 9.9762)).title("Hafen Club").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5512, 9.9692)).title("Kaffee & Co.").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5568, 9.9700)).title("Kaffee Lounge").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5514, 9.9646)).title("Biergarten").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5527, 9.9718)).title("Kellerbar").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5535, 9.9760)).title("Bar Altstadt").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5546, 9.9756)).title("Bar Café").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5519, 9.9748)).title("Gießerei").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5524, 9.9697)).title("Schiffbar").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5521, 9.9704)).title("Bar Rote Rose").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5513, 9.9724)).title("Trinkhalle").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5526, 9.9706)).title("Alte Bar").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5544, 9.9700)).title("Hafen Lounge").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5537, 9.9751)).title("Bar Boot").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5538, 9.9755)).title("Kiez & Co.").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5512, 9.9694)).title("Bar Stadtpark").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5532, 9.9726)).title("Bar Café").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5520, 9.9710)).title("Bar Lounge").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5528, 9.9702)).title("Kaiserbar").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5561, 9.9700)).title("Club Oase").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5518, 9.9701)).title("Schwaben Bar").snippet("Bar"),
        MarkerOptions().position(LatLng(53.5535, 9.9715)).title("Bar Seeterrasse").snippet("Bar")
    )
//-----------------------------------End of the list---------------------------------------------------------------------------------------------------------

    //Suppression of the lint-warning for barrier-free jot
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) { //OnCreate --> Initial configuration for th activity
        super.onCreate(savedInstanceState)

        //Initialisation of mapView
        mapView = com.google.android.gms.maps.MapView(this) //mapView --> Show Google Map on Screen
        mapView.onCreate(savedInstanceState) //Method to ensure right Creation and Initialisation of the mapView
        mapView.getMapAsync(this) //mapView should be asynchronous in the background

        // Initialization of  fusedLocationClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this) // --> Google Api, locates the exact location of the user

        // Request of locationPermission
        locationPermissionRequest = registerForActivityResult( //--> Method to process the location permission
            ActivityResultContracts.RequestMultiplePermissions() // Multiple permissions are required
        ) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true || //Callback-function --> User allows access to location, location is called up
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                permissionsGranted = true
                if (mapReady && this::googleMap.isInitialized) {
                    googleMap.isMyLocationEnabled = true
                    getCurrentLocation()
                }
            } else {
                Log.e("Permissions", "Location permission denied") //Otherwise there is a error message in Log
            }
        }


        // Prepares a list of the example markers, that are shown in the map
        prepareMarkers()

        //Function, defines the layout of the activity with Jetpack Compose
        setContent {
            val context = LocalContext.current  // Current context of the acitivity
            val focusManager = LocalFocusManager.current //Manages the focus within the Compose UI
            val focusRequester = remember { FocusRequester() } //Variable, focus for text input field
            var isFocused by remember { mutableStateOf(false) }  // Variable, focus for text input field

            //Create a Scaffold --> Definition of the structure for page
            Scaffold(
                // Create a top bar for the logo and filter
                topBar = {
                    TopAppBar(
                        title = {
                            // Box for centering the FoodMe Logo
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                // Customizing the Foodme Logo
                                Image(
                                    painter = painterResource(id = R.drawable.foodme_logo),
                                    contentDescription = "FoodMe Logo",
                                    modifier = Modifier
                                        .height(60.dp)
                                        .padding(start = 35.dp),
                                    contentScale = ContentScale.Fit
                                )
                            }
                        },
                        actions = {
                            var expanded by remember { mutableStateOf(false) } // At the beginning, the drop-down menu is closed
                            val filterOptions = listOf("Restaurants", "Cafés", "Bars") // List of filter options

                            // Filter-Button
                            IconButton(onClick = { expanded = !expanded }) {
                                Icon(Icons.Default.Menu, contentDescription = "Filter")
                            }

                            //Create a dropdown menu for the filters (restaurants, cafés and bars)
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }) {
                                filterOptions.forEach { option ->
                                    DropdownMenuItem(onClick = {
                                        when (option) {
                                            "Restaurants" -> showRestaurants = !showRestaurants
                                            "Cafés" -> showCafes = !showCafes
                                            "Bars" -> showBars = !showBars
                                        }
                                        updateMarkers()
                                        expanded = false
                                    }) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(text = option)
                                            Switch(
                                                checked = when (option) {
                                                    "Restaurants" -> showRestaurants
                                                    "Cafés" -> showCafes
                                                    else -> showBars
                                                },
                                                onCheckedChange = {
                                                    when (option) {
                                                        "Restaurants" -> showRestaurants = it
                                                        "Cafés" -> showCafes = it
                                                        "Bars" -> showBars = it
                                                    }
                                                    updateMarkers()
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        },
                        backgroundColor = Color(0xFFFFA500)
                    )
                },
                // Create the zoom in and zoom out button
                content = { padding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        // Show Google Map
                        AndroidView(
                            factory = {
                                mapView.apply {
                                    //Focus gets away, when user interacts with the map
                                    setOnTouchListener { _, event ->
                                        if (event.action == MotionEvent.ACTION_DOWN) {
                                            focusManager.clearFocus()
                                        }
                                        false
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                        )

                        //Customizing the zoom in and out button
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp)
                        ) {
                            //Zoom in button
                            FloatingActionButton(
                                onClick = {
                                    googleMap.animateCamera(CameraUpdateFactory.zoomIn())
                                },
                                backgroundColor = Color(0xFFFFA500)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Zoom In")
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            //Zoom out button
                            FloatingActionButton(
                                onClick = {
                                    googleMap.animateCamera(CameraUpdateFactory.zoomOut())
                                },
                                backgroundColor = Color(0xFFFFA500)
                            ) {
                                Icon(Icons.Default.Remove, contentDescription = "Zoom Out")
                            }
                        }

                        // Search bar under the top bar
                        //Customizing the searchbar
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .background(Color(0xFFFFA500), shape = RoundedCornerShape(16.dp))
                                .border(1.dp, Color(0xFFFFA500), shape = RoundedCornerShape(16.dp))
                                .width(268.dp)
                                .height(43.dp)
                                .align(Alignment.TopStart)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp)
                                    .padding(start = 8.dp)
                                    .clickable { focusRequester.requestFocus() }
                            ) {
                                //Search Icon and customizing it
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = Color.Black,
                                    modifier = Modifier.size(20.dp)
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                //Create basic text field and customizing it
                                BasicTextField(
                                    value = searchQuery,
                                    onValueChange = { newValue ->
                                        searchQuery = newValue
                                        if (newValue.text.length > 1) {
                                            updateSuggestions()
                                        } else {
                                            suggestions = listOf()
                                        }
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                        .background(Color.Transparent)
                                        .focusRequester(focusRequester)
                                        .onFocusChanged { focusState ->
                                            isFocused = focusState.isFocused
                                        },
                                    //Conditions and adjustments for writing in searchbar
                                    textStyle = TextStyle(
                                        color = Color.Black,
                                        fontSize = MaterialTheme.typography.body1.fontSize
                                    ),
                                    cursorBrush = SolidColor(Color.Black),
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Done,
                                        keyboardType = KeyboardType.Text
                                    ),
                                    singleLine = true,
                                    maxLines = 1
                                )

                                if (searchQuery.text.isNotEmpty()) { //Checks that the searchbar isn't empty
                                    IconButton(
                                        onClick = {
                                            searchQuery = TextFieldValue("")  //Empties the searchbar
                                            suggestions = listOf() //Empties the suggestions
                                            updateMarkers()
                                        },
                                        modifier = Modifier.padding(end = 2.dp)
                                    ) {
                                        //Close-Icon and customizing it
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Clear",
                                            tint = Color.Black
                                        )
                                    }
                                }
                            }

                            // Dropdown with suggestions for searchbar
                            DropdownMenu(
                                expanded = suggestions.isNotEmpty() && isFocused,
                                onDismissRequest = { suggestions = listOf() }
                            ) {
                                suggestions.forEach { suggestion ->
                                    DropdownMenuItem(onClick = {
                                        zoomToMarker(suggestion)
                                        suggestions = listOf()
                                    }) {
                                        Text(suggestion)
                                    }
                                }
                            }
                        }
                    }
                },
                // Create bottom bar to click on "Entdecke"-Page, "Suche"-Page and "Mein Profil"-Page
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
                                context.startActivity(
                                    Intent(
                                        context,
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
                                context.startActivity(
                                    Intent(
                                        context,
                                        SearchPageActivity::class.java
                                    )
                                )
                            }
                        )
                        // Navigation to ProfileActivity
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = "Mein Profil"
                                )
                            },
                            label = { Text("Mein Profil") },
                            selected = true,
                            onClick = {
                                context.startActivity(
                                    Intent(
                                        context,
                                        ProfileActivity::class.java
                                    )
                                )
                            }
                        )
                    }
                }
            )
        }

            // Check if there is a permission for using the location of the user
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                locationPermissionRequest.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            } else {
                permissionsGranted = true
        }
    }

        //Prepare markers for the map
        private fun prepareMarkers() {
            markersMap.apply { //--> saves the markers (strings and objects)
                clear()
                restaurants.forEach { marker -> // Every restaurant marker to markersMap
                    put(marker.title ?: "", marker) //Marker to markersMap
                }
                cafes.forEach { marker ->
                    put(marker.title ?: "", marker)
                }
                bars.forEach { marker ->
                    put(marker.title ?: "", marker)
                }
            }
        }

        //Method for configuration of the google parameter
        override fun onMapReady(map: GoogleMap) {
            googleMap = map
            mapReady = true

            // Customizing the styleJson based on suggestions from AI (artificial intelligence)
            val styleJson = """
                [
                    {
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "color": "#f5f5f5"
                            }
                        ]
                    },
                    {
                        "elementType": "labels.icon",
                        "stylers": [
                            {
                                "visibility": "off"
                            }
                        ]
                    },
                    {
                        "elementType": "labels.text.fill",
                        "stylers": [
                            {
                                "color": "#616161"
                            }
                        ]
                    },
                    {
                        "elementType": "labels.text.stroke",
                        "stylers": [
                            {
                                "color": "#f5f5f5"
                            }
                        ]
                    },
                    {
                        "featureType": "administrative.land_parcel",
                        "elementType": "labels",
                        "stylers": [
                            {
                                "visibility": "off"
                            }
                        ]
                    },
                    {
                        "featureType": "poi",
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "color": "#c5e1a5"
                            }
                        ]
                    },
                    {
                        "featureType": "poi",
                        "elementType": "labels",
                        "stylers": [
                            {
                                "visibility": "off"
                            }
                        ]
                    },
                    {
                        "featureType": "poi.park",
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "color": "#a5d6a7"
                            }
                        ]
                    },
                    {
                        "featureType": "road",
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "color": "#ffffff"
                            }
                        ]
                    },
                    {
                        "featureType": "road",
                        "elementType": "labels",
                        "stylers": [
                            {
                                "visibility": "on"
                            }
                        ]
                    },
                    {
                        "featureType": "water",
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "color": "#81d4fa"
                            }
                        ]
                    }
                ]
            """.trimIndent()

            // styleJson on the map
            googleMap.setMapStyle(MapStyleOptions(styleJson))

            // Info Window for every example marker
            googleMap.setInfoWindowAdapter(CustomInfoWindowAdapter(this))

            //If the user clicks on a example marker, the user will be send to the SearchPageActivity with all the information of the marker
            googleMap.setOnInfoWindowClickListener { clickedMarker ->
                val intent = Intent(this, SearchPageActivity::class.java).apply {
                    putExtra("locationTitle", clickedMarker.title)
                    putExtra("infoText", clickedMarker.snippet)
                }
                startActivity(intent)
            }

            updateMarkers()

            //Fallback coordinates Hamburg
            val hamburg = LatLng(53.5511, 9.9937)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hamburg, 16f))

            //Check if there is a permission for using the location of the user, otherwise there is a fallback location with some coordinates in Hamburg
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                googleMap.isMyLocationEnabled = true
                getCurrentLocation()
            } else {
                if (permissionsGranted) {
                    googleMap.isMyLocationEnabled = true
                    getCurrentLocation()
                } else {
                    showFallbackLocation(hamburg)
                }
            }

            //Listener for marker that got clicked
            googleMap.setOnMarkerClickListener { marker ->
                marker.showInfoWindow() //Info Window for Markers
                lastClickedMarker = marker
                true
            }
        }

    //Updating markers based on using the filters
    private fun updateMarkers() {
        if (!this::googleMap.isInitialized) { // Checks if the google map variable has been already initialized. After that the other operation can start
            return
        }
        googleMap.clear()
        markerReferences.clear()

        if (showRestaurants) { //Shows restaurants on the map, if the filter is activated (same for cafes and bars)
            restaurants.forEach {
                val marker = googleMap.addMarker(it)
                if (marker != null) {
                    markerReferences[marker.title ?: ""] = marker
                }
            }
        }
        if (showCafes) {
            cafes.forEach {
                val marker = googleMap.addMarker(it)
                if (marker != null) {
                    markerReferences[marker.title ?: ""] = marker
                }
            }
        }
        if (showBars) {
            bars.forEach {
                val marker = googleMap.addMarker(it)
                if (marker != null) {
                    markerReferences[marker.title ?: ""] = marker
                }
            }
        }

        //Checks that the search query isn't empty
        if (searchQuery.text.isNotEmpty()) {
            val queryMarkers = markersMap.filterKeys {
                it.contains(searchQuery.text.trim(), ignoreCase = true)
            }.values

            queryMarkers.forEach {
                val marker = googleMap.addMarker(it)
                if (marker != null) {
                    markerReferences[marker.title ?: ""] = marker
                }
            }
        }
    }

        //Generates a list of suggestions
        private fun updateSuggestions() {
            val query = searchQuery.text.trim()
            suggestions = if (query.length > 2) {  // Length of words, so that the suggestions can show up
                markersMap.keys.filter { it.contains(query, ignoreCase = true) }
            } else {
                listOf()
            }
        }

    //Searches the marker based on the name
    private fun zoomToMarker(title: String) {
        if (!this::googleMap.isInitialized) {
            Log.e("zoomToMarker", "GoogleMap is not initialized")
            return
        }

        // Searching for marker based on the name
        val markerOptions = markersMap[title]
        if (markerOptions != null) {
            // Removing the marker
            markerReferences[title]?.remove()
            markerReferences.remove(title)

            // Put the marker on the map
            val marker = googleMap.addMarker(markerOptions)
            if (marker != null) {
                // Info window of the marker is shown
                marker.showInfoWindow()

                // Saving the reference of the marker
                markerReferences[title] = marker

                // Move the camera to the elected marker and zooming in
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 15f))
            } else {
                Log.e("zoomToMarker", "Marker could not be added to GoogleMap")
            }
        } else {
            Log.e("zoomToMarker", "No marker options found for title: $title")
        }
    }


    //Lint-warning for missing permission
    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {  //Checks if there is a permission for the location, if not, there is fallback location in Hamburg
        if (!this::googleMap.isInitialized) { //If Google Map is not initialized, the function gets canceled
            return
        }

        //Checks the permission for the location
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        //Getting the exact location of the user
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                if (location != null) {
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))

                    // Customizing the position marker
                    val circleOptions = CircleOptions()
                        .center(currentLatLng)
                        .fillColor(AppColor(0x400000FF).toArgb())
                        .strokeWidth(2f)

                    googleMap.addCircle(circleOptions)
                } else {
                    Log.e("LocationError", "Location is null")
                    val fallbackLatLng = LatLng(53.5511, 9.9937) //Fallback location, when location information is null
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(fallbackLatLng, 16f))
                    showFallbackLocation(fallbackLatLng)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("LocationError", "Error getting location", exception)
                val fallbackLatLng = LatLng(53.5511, 9.9937)
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(fallbackLatLng, 16f))
                showFallbackLocation(fallbackLatLng)
            }
    }

    //Customizing the fallback location
    private fun showFallbackLocation(location: LatLng) {
            if (!this::googleMap.isInitialized) {
                return
            }
            val circleOptions = CircleOptions()
                .center(location)
                .fillColor(AppColor(0x400000FF).toArgb())
                .strokeWidth(2f)

            googleMap.addCircle(circleOptions)
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 18f))
        }

        override fun onResume() {
            super.onResume()
            mapView.onResume()
        }

        override fun onStart() {
            super.onStart()
            mapView.onStart()
        }

        override fun onStop() {
            super.onStop()
            mapView.onStop()
        }

        override fun onPause() {
            mapView.onPause()
            super.onPause()
        }

        override fun onDestroy() {
            mapView.onDestroy()
            super.onDestroy()
        }

        override fun onLowMemory() {
            super.onLowMemory()
            mapView.onLowMemory()
        }

        //Creates a info window by using the custom_info_window.xml and getting the user from the info window to the search page by clicking on info
        private class CustomInfoWindowAdapter(context: Context) : GoogleMap.InfoWindowAdapter {
            @SuppressLint("InflateParams")
            private val mWindow: View =
                LayoutInflater.from(context).inflate(R.layout.custom_info_window, null)

            override fun getInfoContents(marker: Marker): View {
                render(marker, mWindow)
                return mWindow
            }

            override fun getInfoWindow(marker: Marker): View {
                render(marker, mWindow)
                return mWindow
            }

            private fun render(marker: Marker, view: View) {
                val title = marker.title
                val snippet = marker.snippet

                val titleUi = view.findViewById<TextView>(R.id.title)
                val snippetUi = view.findViewById<TextView>(R.id.snippet)
                val button = view.findViewById<Button>(R.id.infoButton)

                titleUi.text = title
                snippetUi.text = snippet

                button.setOnClickListener {
                    val context = view.context
                    val intent = Intent(context, SearchPageActivity::class.java).apply {
                        putExtra("locationTitle", marker.title)
                        putExtra("infoText", marker.snippet)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }
