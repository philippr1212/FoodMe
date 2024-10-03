package com.example.newfoodme.ui.theme.search

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.newfoodme.ui.theme.home.HomePageActivity
import com.example.newfoodme.ui.theme.profil.ProfileActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import java.util.*
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class SearchPageActivity : ComponentActivity() {




    //KI generated begin---------------------------------------------------------------------------------
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var userLocation: LatLng? = null

    //list of example restaurants
    private val exampleMarkers = listOf(
        LatLng(53.5588, 9.9814) to "Henssler Henssler",
        LatLng(53.5534, 9.9912) to "Restaurant Haerlin",
        LatLng(53.5417, 9.9752) to "Brücke 10",
        LatLng(53.5638, 9.9735) to "Gasthaus Wichmann",
        LatLng(53.5515, 9.9812) to "Taverna Kiriakos",
        LatLng(53.5585, 9.9819) to "Lüftl-Keller",
        LatLng(53.5528, 9.9926) to "Café du Nord",
        LatLng(53.5565, 9.9794) to "Tschebull",
        LatLng(53.5510, 9.9660) to "Bullerei",
        LatLng(53.5491, 9.9810) to "Heldenplatz",
        LatLng(53.5515, 9.9912) to "Lili's Bistro",
        LatLng(53.5473, 9.9920) to "Hofbräuhaus Hamburg",
        LatLng(53.5430, 9.9804) to "Störtebeker Elbphilharmonie",
        LatLng(53.5524, 9.9787) to "Zum Alten Mädchen",
        LatLng(53.5514, 9.9897) to "La Vela",
        LatLng(53.5482, 9.9821) to "Mutterland",
        LatLng(53.5564, 9.9510) to "Fischereihafen Restaurant",
        LatLng(53.5440, 9.9770) to "Skyline Bar 20up",
        LatLng(53.5511, 9.9723) to "Bistro du Bac",
        LatLng(53.5478, 9.9781) to "Bistro Kutscher",
        LatLng(53.5532, 9.9941) to "La Tasca",
        LatLng(53.5474, 9.9709) to "Schweinske",
        LatLng(53.5501, 9.9776) to "Gosch Sylt",
        LatLng(53.5520, 9.9928) to "Zwei Stühle",
        LatLng(53.5580, 9.9801) to "Park Restaurant",
        LatLng(53.5405, 9.9850) to "HafenCity",
        LatLng(53.5400, 9.9632) to "Le Canard",
        LatLng(53.5543, 9.9760) to "Auster Bar & Grill",
        LatLng(53.5523, 9.9801) to "Café Erdapfel",
        LatLng(53.5476, 9.9847) to "Asia Gourmet",
        LatLng(53.5538, 9.9874) to "Elb-Insel",
        LatLng(53.5549, 9.9798) to "Mayer's Restaurant",
        LatLng(53.5590, 9.9855) to "Zur Alten Mühle",
        LatLng(53.5566, 9.9822) to "Marion's Restaurant",
        LatLng(53.5504, 9.9716) to "Edelcafé",
        LatLng(53.5587, 9.9702) to "Ristorante Da Vinci",
        LatLng(53.5492, 9.9678) to "Harbour View Restaurant",
        LatLng(53.5517, 9.9645) to "Sonnendeck",
        LatLng(53.5631, 9.9789) to "Marina Restaurant",
        LatLng(53.5578, 9.9733) to "Hafenblick",
        LatLng(53.5468, 9.9819) to "Scharhörn",
        LatLng(53.5423, 9.9750) to "Morgenland",
        LatLng(53.5603, 9.9797) to "Wolkenlos",
        LatLng(53.5584, 9.9904) to "Alsterperle",
        LatLng(53.5513, 9.9742) to "Avenue",
        LatLng(53.5591, 9.9703) to "City Grill",
        LatLng(53.5505, 9.9657) to "Wienerwald",
        LatLng(53.5477, 9.9832) to "Lammert",
        LatLng(53.5531, 9.9770) to "Hamburg 7",
        LatLng(53.5562, 9.9750) to "Alsterküche",
        LatLng(53.5537, 9.9758) to "Schöne Aussichten",
        LatLng(53.5602, 9.9737) to "Eisvogel",
        LatLng(53.5583, 9.9748) to "Portofino",
        LatLng(53.5557, 9.9698) to "Fischmarkt",
        LatLng(53.5540, 9.9736) to "Kleine Welt",
        LatLng(53.5610, 9.9715) to "Café Linde",
        LatLng(53.5601, 9.9723) to "Kaffekommune",
        LatLng(53.5542, 9.9696) to "Wunderbar",
        LatLng(53.5520, 9.9734) to "Bistro Deli",
        LatLng(53.5484, 9.9745) to "Oberdeck",
        LatLng(53.5525, 9.9644) to "Gusto",
        LatLng(53.5568, 9.9794) to "Seepferdchen",
        LatLng(53.5485, 9.9806) to "Eckstein",
        LatLng(53.5503, 9.9764) to "Altes Land",
        LatLng(53.5496, 9.9695) to "Schäferstube",
        LatLng(53.5506, 9.9717) to "Kaffekommune",
        LatLng(53.5553, 9.9754) to "Eiermann",
        LatLng(53.5479, 9.9710) to "Tafelspitz",
        LatLng(53.5567, 9.9651) to "Münchner Kindl",
        LatLng(53.5489, 9.9720) to "Bistrot du Lyon",
        LatLng(53.5522, 9.9706) to "Grillmeister",
        LatLng(53.5536, 9.9637) to "Lutter & Wegner",
        LatLng(53.5534, 9.9692) to "Vino",
        LatLng(53.5482, 9.9676) to "Kasematten",
        LatLng(53.5508, 9.9741) to "Imbiss Zentrale",
        LatLng(53.5514, 9.9646) to "Biergarten",
        LatLng(53.5544, 9.9670) to "Wok & Roll",
        LatLng(53.5529, 9.9653) to "Hafenhotel",
        LatLng(53.5599, 9.9744) to "Schnitzelhaus",
        LatLng(53.5530, 9.9707) to "Kaiser Café",
        LatLng(53.5606, 9.9720) to "Café der Hafen",
        LatLng(53.5547, 9.9658) to "Luzifer",
        LatLng(53.5495, 9.9732) to "Rote Küche",
        LatLng(53.5535, 9.9712) to "Alsterbistro",
        LatLng(53.5600, 9.9761) to "Seevogel",
        LatLng(53.5588, 9.9650) to "Boathouse",
        LatLng(53.5521, 9.9792) to "Ristorante Aurora",
        LatLng(53.5518, 9.9662) to "Bierkeller",
        LatLng(53.5534, 9.9713) to "Hamburger Fischmarkt",
        LatLng(53.5569, 9.9671) to "Café Stern",
        LatLng(53.5582, 9.9709) to "Kaffehus",
        LatLng(53.5571, 9.9725) to "Hafenlokal",
        LatLng(53.5523, 9.9654) to "Delirium",
        LatLng(53.5509, 9.9727) to "Café Schöne Aussicht",
        LatLng(53.5490, 9.9779) to "Gourmetküche",
        LatLng(53.5528, 9.9738) to "Hafenblick Café",
        LatLng(53.5589, 9.9656) to "Kleine Kneipe",
        LatLng(53.5563, 9.9772) to "Marina Bistro",
        LatLng(53.5541, 9.9705) to "Zur Alten Liebe",
        LatLng(53.5553, 9.9742) to "Goldene Bar",
        LatLng(53.5534, 9.9724) to "Bar Centrale",
        LatLng(53.5508, 9.9713) to "Elb Lounge",
        LatLng(53.5537, 9.9691) to "The Ricks",
        LatLng(53.5586, 9.9706) to "Hafenklang",
        LatLng(53.5501, 9.9672) to "Schellfischposten",
        LatLng(53.5513, 9.9760) to "Bock auf Bar",
        LatLng(53.5562, 9.9708) to "Kiez Club",
        LatLng(53.5520, 9.9717) to "Bar Italia",
        LatLng(53.5556, 9.9693) to "Millerntor Stadium Bar",
        LatLng(53.5518, 9.9745) to "Oberhafenkantine",
        LatLng(53.5529, 9.9694) to "Zur alten Mühle",
        LatLng(53.5500, 9.9753) to "Bar 69",
        LatLng(53.5531, 9.9725) to "Boiler Room",
        LatLng(53.5563, 9.9736) to "Psycho Fuzz",
        LatLng(53.5540, 9.9744) to "The Usual",
        LatLng(53.5528, 9.9772) to "Schmidt Theater Bar",
        LatLng(53.5512, 9.9714) to "Astra Stube",
        LatLng(53.5536, 9.9763) to "Club 20457",
        LatLng(53.5552, 9.9687) to "Küche 48",
        LatLng(53.5507, 9.9691) to "Kaiserkeller",
        LatLng(53.5519, 9.9734) to "Café des Artistes",
        LatLng(53.5526, 9.9708) to "Dodo",
        LatLng(53.5537, 9.9691) to "Bar The Ricks",
        LatLng(53.5554, 9.9688) to "Die Bank",
        LatLng(53.5524, 9.9712) to "The St. Pauli Bar",
        LatLng(53.5508, 9.9713) to "Elb Lounge",
        LatLng(53.5532, 9.9697) to "Hafenliebe",
        LatLng(53.5543, 9.9760) to "The Chapel",
        LatLng(53.5568, 9.9721) to "The Old School",
        LatLng(53.5515, 9.9700) to "Bierhaus",
        LatLng(53.5522, 9.9741) to "Essen & Trinken",
        LatLng(53.5538, 9.9750) to "Die Schnapsbar",
        LatLng(53.5503, 9.9712) to "Schultenhof",
        LatLng(53.5558, 9.9740) to "Platzhirsch",
        LatLng(53.5527, 9.9707) to "Bar Coaster",
        LatLng(53.5560, 9.9736) to "Kommandohaus",
        LatLng(53.5524, 9.9677) to "Schmidtchen",
        LatLng(53.5562, 9.9708) to "Kiez Club",
        LatLng(53.5511, 9.9732) to "Ristorante Bella Italia",
        LatLng(53.5506, 9.9700) to "Fischmarkt Bar",
        LatLng(53.5523, 9.9762) to "Bar & Lounge",
        LatLng(53.5550, 9.9716) to "Café Terrasse",
        LatLng(53.5545, 9.9692) to "St. Pauli Club",
        LatLng(53.5553, 9.9742) to "Goldene Bar",
        LatLng(53.5527, 9.9756) to "Hafenbräu",
        LatLng(53.5549, 9.9723) to "Schwaben Quatier",
        LatLng(53.5518, 9.9745) to "Oberhafenkantine",
        LatLng(53.5525, 9.9723) to "Der Feuervogel",
        LatLng(53.5537, 9.9744) to "Fischerhaus",
        LatLng(53.5514, 9.9646) to "Biergarten",
        LatLng(53.5548, 9.9757) to "Bar 54",
        LatLng(53.5523, 9.9682) to "Hafenbar",
        LatLng(53.5530, 9.9752) to "Trattoria Milano",
        LatLng(53.5524, 9.9696) to "Café Olé",
        LatLng(53.5511, 9.9685) to "Club Saufhaus",
        LatLng(53.5544, 9.9700) to "Hafen Lounge",
        LatLng(53.5555, 9.9756) to "Gosch Bar",
        LatLng(53.5539, 9.9698) to "Günters Bar",
        LatLng(53.5529, 9.9721) to "Fischer's",
        LatLng(53.5562, 9.9708) to "Kiez Club",
        LatLng(53.5508, 9.9713) to "Elb Lounge",
        LatLng(53.5568, 9.9743) to "Alte Freiheit",
        LatLng(53.5519, 9.9725) to "Kieztanz",
        LatLng(53.5586, 9.9706) to "Hafenklang",
        LatLng(53.5532, 9.9751) to "Bar 4U",
        LatLng(53.5510, 9.9706) to "Fischmarkt Lounge",
        LatLng(53.5528, 9.9762) to "Tanzbar",
        LatLng(53.5534, 9.9703) to "Club Orange",
        LatLng(53.5541, 9.9734) to "Millerntor Lounge",
        LatLng(53.5521, 9.9738) to "Wolke 7",
        LatLng(53.5537, 9.9752) to "Café Bourbon",
        LatLng(53.5516, 9.9731) to "Schöne Aussichten",
        LatLng(53.5509, 9.9741) to "Club 76",
        LatLng(53.5525, 9.9717) to "Schwaben Lounge",
        LatLng(53.5547, 9.9762) to "Hafen Club",
        LatLng(53.5512, 9.9692) to "Kaffee & Co.",
        LatLng(53.5568, 9.9700) to "Kaffee Lounge",
        LatLng(53.5514, 9.9646) to "Biergarten",
        LatLng(53.5527, 9.9718) to "Kellerbar",
        LatLng(53.5535, 9.9760) to "Bar Altstadt",
        LatLng(53.5546, 9.9756) to "Bar Café",
        LatLng(53.5519, 9.9748) to "Gießerei",
        LatLng(53.5524, 9.9697) to "Schiffbar",
        LatLng(53.5521, 9.9704) to "Bar Rote Rose",
        LatLng(53.5513, 9.9724) to "Trinkhalle",
        LatLng(53.5526, 9.9706) to "Alte Bar",
        LatLng(53.5544, 9.9700) to "Hafen Lounge",
        LatLng(53.5537, 9.9751) to "Bar Boot",
        LatLng(53.5538, 9.9755) to "Kiez & Co.",
        LatLng(53.5512, 9.9694) to "Bar Stadtpark",
        LatLng(53.5532, 9.9726) to "Bar Café",
        LatLng(53.5520, 9.9710) to "Bar Lounge",
        LatLng(53.5528, 9.9702) to "Kaiserbar",
        LatLng(53.5561, 9.9700) to "Club Oase",
        LatLng(53.5518, 9.9701) to "Schwaben Bar",
        LatLng(53.5535, 9.9715) to "Bar Seeterrasse",
        LatLng(53.5538, 9.9822) to "Café Elbgold",
        LatLng(53.5521, 9.9702) to "Kaffekommune",
        LatLng(53.5581, 9.9787) to "Kaffeemühle",
        LatLng(53.5528, 9.9763) to "Café Kleinod",
        LatLng(53.5546, 9.9772) to "Café Aroma",
        LatLng(53.5553, 9.9730) to "Café Deli",
        LatLng(53.5610, 9.9715) to "Café Linde",
        LatLng(53.5505, 9.9812) to "Mutterland Café",
        LatLng(53.5534, 9.9711) to "Kaffeekommune",
        LatLng(53.5585, 9.9799) to "Coffee House",
        LatLng(53.5516, 9.9865) to "Café Marie",
        LatLng(53.5539, 9.9652) to "Café Kreuzberg",
        LatLng(53.5551, 9.9754) to "Café Convent",
        LatLng(53.5544, 9.9679) to "Coffee Fellows",
        LatLng(53.5567, 9.9788) to "Café im Park",
        LatLng(53.5588, 9.9749) to "Café Eppendorfer",
        LatLng(53.5523, 9.9730) to "Espresso & Co.",
        LatLng(53.5537, 9.9721) to "Café Waffelhaus",
        LatLng(53.5582, 9.9741) to "Café Planten un Blomen",
        LatLng(53.5559, 9.9717) to "Café Rösterei",
        LatLng(53.5561, 9.9751) to "Kaffee-Kommune",
        LatLng(53.5603, 9.9736) to "Café Alsterblick",
        LatLng(53.5572, 9.9739) to "Café Hamburg",
        LatLng(53.5580, 9.9707) to "Café Kuchen & Kaffe",
        LatLng(53.5534, 9.9670) to "Café Spezial",
        LatLng(53.5527, 9.9714) to "Café Waffelzeit",
        LatLng(53.5509, 9.9732) to "Café Maria",
        LatLng(53.5567, 9.9728) to "Kaffekommune",
        LatLng(53.5524, 9.9704) to "Café Treibgut",
        LatLng(53.5558, 9.9737) to "Café Espresso",
        LatLng(53.5563, 9.9711) to "Café Art",
        LatLng(53.5545, 9.9728) to "Kaffekommune",
        LatLng(53.5584, 9.9746) to "Café Brecht",
        LatLng(53.5530, 9.9775) to "Café am Elbstrand",
        LatLng(53.5522, 9.9756) to "Café Alster",
        LatLng(53.5577, 9.9759) to "Kaffeehaus",
        LatLng(53.5535, 9.9706) to "Café Kreation",
        LatLng(53.5566, 9.9743) to "Café Guter Tag",
        LatLng(53.5503, 9.9788) to "Café Rathaus",
        LatLng(53.5525, 9.9700) to "Café Elbstrand",
        LatLng(53.5570, 9.9726) to "Café Lilla",
        LatLng(53.5514, 9.9760) to "Café Literatur",
        LatLng(53.5543, 9.9736) to "Café Kaffeekommune",
        LatLng(53.5568, 9.9755) to "Café Alsterpark",
        LatLng(53.5524, 9.9679) to "Café Lieder",
        LatLng(53.5538, 9.9748) to "Café Stolzen",
        LatLng(53.5550, 9.9703) to "Café Küstengold",
        LatLng(53.5526, 9.9723) to "Café Striebel",
        LatLng(53.5531, 9.9719) to "Café Becher",
        LatLng(53.5569, 9.9694) to "Café Heiß",
        LatLng(53.5528, 9.9733) to "Café Rösterei",
        LatLng(53.5576, 9.9730) to "Café Nord",
        LatLng(53.5560, 9.9691) to "Café Frisch",
        LatLng(53.5507, 9.9736) to "Café Stübchen",
        LatLng(53.5521, 9.9708) to "Café Kunst",
        LatLng(53.5581, 9.9677) to "Café Perle",
        LatLng(53.5561, 9.9715) to "Café Nostalgie",
        LatLng(53.5510, 9.9690) to "Café Oase",
        LatLng(53.5568, 9.9682) to "Café Lale",
        LatLng(53.5532, 9.9695) to "Café Schöner Morgen",
        LatLng(53.5523, 9.9689) to "Café Seepark",
        LatLng(53.5587, 9.9693) to "Café Altona",
        LatLng(53.5534, 9.9700) to "Café Himmelreich",
        LatLng(53.5542, 9.9706) to "Café Kaffeekommune",
        LatLng(53.5562, 9.9724) to "Café Lieblingsplatz",
        LatLng(53.5569, 9.9740) to "Café Nordsee",
        LatLng(53.5575, 9.9731) to "Café Helgoland",
        LatLng(53.5547, 9.9691) to "Café Kloster",
        LatLng(53.5563, 9.9721) to "Café Sternschanze",
        LatLng(53.5581, 9.9743) to "Café Hansestadt",
        LatLng(53.5522, 9.9724) to "Café Frischluft",
        LatLng(53.5537, 9.9702) to "Café Tête-à-Tête",
        LatLng(53.5566, 9.9705) to "Café Elb-Lounge",
        LatLng(53.5527, 9.9745) to "Café Vollmond",
        LatLng(53.5582, 9.9698) to "Café Alstertal",
        LatLng(53.5538, 9.9694) to "Café Süß",
        LatLng(53.5565, 9.9723) to "Café Kultur",
        LatLng(53.5514, 9.9719) to "Café Bunt",
        LatLng(53.5587, 9.9679) to "Café Timmermann",
        LatLng(53.5529, 9.9688) to "Café Spürnase",
        LatLng(53.5556, 9.9728) to "Café Luise",
        LatLng(53.5562, 9.9696) to "Café Träumerei",
        LatLng(53.5523, 9.9702) to "Café Verliebt",
        LatLng(53.5545, 9.9714) to "Café Geniessen",
        LatLng(53.5560, 9.9746) to "Café Gaumenfreude",
        LatLng(53.5583, 9.9749) to "Café Kaffeekommune",
        LatLng(53.5526, 9.9752) to "Café Roter Faden",
        LatLng(53.5563, 9.9715) to "Café Genusswelt",
        LatLng(53.5585, 9.9761) to "Café Laune",
        LatLng(53.5528, 9.9675) to "Café Ritter",
        LatLng(53.5577, 9.9703) to "Café Goldstück",
        LatLng(53.5521, 9.9683) to "Café Schnecke",
        LatLng(53.5561, 9.9698) to "Café Lichtblick",
        LatLng(53.5582, 9.9744) to "Café Blau",
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
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

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            userLocation = location?.let { LatLng(it.latitude, it.longitude) }


            val locationTitle = intent.getStringExtra("locationTitle")
            val infoText = intent.getStringExtra("infoText")
            val sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
            val initialVorname = sharedPreferences.getString("vorname", "") ?: ""
            val initialNachname = sharedPreferences.getString("nachname", "") ?: ""

            setContent {
                if (locationTitle != null && infoText != null) {
                    SearchScreen(
                        initialVorname = initialVorname,
                        initialNachname = initialNachname,
                        locationTitle = locationTitle,
                        infoText = infoText,
                        userLocation = null,
                        exampleMarkers = emptyList()
                    )
                } else {
                    SearchScreen(
                        initialVorname = initialVorname,
                        initialNachname = initialNachname,
                        locationTitle = "",
                        infoText = "",
                        userLocation = userLocation,
                        exampleMarkers = exampleMarkers
                    )
                }
            }
        }
    }
}

//calculate distance from your current location
fun calculateDistanceInKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val earthRadiusKm = 6371.0

    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon1 - lon2)

    val a = sin(dLat / 2) * sin(dLat / 2) +
            cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
            sin(dLon / 2) * sin(dLon / 2)

    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return earthRadiusKm * c
}

@SuppressLint("DefaultLocale")
@Composable
fun SearchScreen(
    initialVorname: String,
    initialNachname: String,
    locationTitle: String,
    infoText: String,
    userLocation: LatLng?,
    exampleMarkers: List<Pair<LatLng, String>>
)
//KI generated end---------------------------------------------------------------------------------






{
    val vorname by remember { mutableStateOf(initialVorname) } // stores the users first name
    val nachname by remember { mutableStateOf(initialNachname) } // stores the users last name

    // booleans to control if dialogs are shown or not
    var showConfirmReservationDialog by remember { mutableStateOf(false) } //shows or hides the dialog to confirm the reservation
    var showResultDialog by remember { mutableStateOf(false) } // shows or hides the result dialog
    var showOrderDialog by remember { mutableStateOf(false) } // shows or hides the order dialog
    var showDateTimeDialog by remember { mutableStateOf(false) } //shows or hides the dialog to choose the date and time for an order
    var showErrorDialog by remember { mutableStateOf(false) } // shows or hides the error message dialog
    var errorMessage by remember { mutableStateOf("") } // stores the error message to display in the error dialog
    var showReservationDateTimeDialog by remember { mutableStateOf(false) } // shows or hides the dialog to choose the date and time for a reservation

    // selected items
    var selectedAddress by remember { mutableStateOf<String?>(null) } // stores the selected address
    var selectedPaymentMethod by remember { mutableStateOf<String?>(null) } // stores the selected payment method

    // reservation details
    var selectedTime by remember { mutableStateOf("") } // Stores the selected time for the reservation
    var selectedDate by remember { mutableStateOf("") } //stores the selected date for the reservation
    var numberOfPersons by remember { mutableIntStateOf(1) } // stores the number of people for the reservation
    var resultMessage by remember { mutableStateOf<String?>(null) } // stores the message to be shown in the result dialog
    var resultTextColor by remember { mutableStateOf(Color.Black) } // stores the color of the result text

    // selected menus
    val totalMenus = 8 // total number of available menus
    var selectedMenus by remember { mutableStateOf(List(totalMenus) { false }) } //identifies which menus are selected

    // selected date for validation
    var selectedYear by remember { mutableIntStateOf(0) } // stores the selected year to validate the reservation date
    var selectedMonth by remember { mutableIntStateOf(0) } //Stores the selected month to validate the reservation date
    var selectedDay by remember { mutableIntStateOf(0) } // stores the selected day to validate the reservation date

    val context = LocalContext.current

    Scaffold(
        topBar = {
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
                    Text(
                        text = "Guten Tag $vorname $nachname",
                        style = MaterialTheme.typography.h6
                    )
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color(0xFFBDBDBD), shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = vorname.firstOrNull()?.toString() ?: "",
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        },


        // upper and bottom bars
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color(0xFFFFA500),
                elevation = 10.dp
            ) {
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Entdecke") },
                    label = { Text("Entdecke") },
                    selected = false,
                    onClick = {
                        context.startActivity(Intent(context, HomePageActivity::class.java))
                    }
                )

                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Search, contentDescription = "Suche") },
                    label = { Text("Suche") },
                    selected = true,
                    onClick = {
                        context.startActivity(Intent(context, SearchPageActivity::class.java))
                    }
                )

                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Mein Profil") },
                    label = { Text("Mein Profil") },
                    selected = false,
                    onClick = {
                        context.startActivity(Intent(context, ProfileActivity::class.java))
                    }
                )
            }
        }
    )




    //all content on the page
    { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (locationTitle.isNotEmpty()) {

                // Scenario 1: Display a specific marker's details
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 80.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = locationTitle,
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = infoText,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(16.dp))


                    //menu boxes
                    ClickableFieldsGrid(
                        selectedMenus = selectedMenus, //which menues are selected

                        //when user selects or deselects a menu item, this function runs
                        onMenuSelectionChanged = { index, isSelected -> //index tells us what menu was selected and isSelcted tells us if its true or false
                            selectedMenus = selectedMenus.toMutableList().also { it[index] = isSelected }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (selectedMenus.any { it }) {

                        //standard text if menu was choosen
                        Text(
                            text = "Ausgewählte Menüs:",
                            style = MaterialTheme.typography.subtitle1,
                            fontWeight = FontWeight.Bold
                        )

                        //selected menus are displayed one below the other
                        Column {
                            selectedMenus.forEachIndexed { index, isSelected ->
                                if (isSelected) {
                                    Text(text = "• Menü ${index + 1}")
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    //billing address (dropdown); other code is below
                    LieferadresseSection(
                        selectedAddress = selectedAddress,
                        onAddressSelected = { selectedAddress = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // payment method (dropdown); other code is below
                    ZahlungsmethodeSection(
                        selectedPaymentMethod = selectedPaymentMethod,
                        onPaymentMethodSelected = { selectedPaymentMethod = it }
                    )
                }

                //genrell button placement settings on the entire site
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    //button placement settings in the row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // Button "Vorbestellen"
                        Button(
                            //when which correction dialog is displayed
                            onClick = {
                                if (selectedAddress.isNullOrEmpty() || selectedPaymentMethod.isNullOrEmpty()) {
                                    errorMessage = "Bitte wählen Sie eine Rechnungsadresse und Zahlungsmethode aus."
                                    showErrorDialog = true
                                } else if (selectedMenus.any { it }) {
                                    showDateTimeDialog = true
                                } else {
                                    errorMessage = "Bitte wählen Sie mindestens ein Menü aus."
                                    showErrorDialog = true
                                }
                            },
                            //button design
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .padding(end = 8.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFA500)),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                text = "Vorbestellen",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Button "Tischreservierung"
                        Button(
                            //when which correction dialog is displayed
                            onClick = {
                                if (selectedAddress.isNullOrEmpty() || selectedPaymentMethod.isNullOrEmpty()) {
                                    errorMessage = "Bitte wählen Sie eine Rechnungsadresse und Zahlungsmethode aus."
                                    showErrorDialog = true
                                } else if (selectedMenus.any { it }) {
                                    errorMessage = "Sie können keine Tischreservierung vornehmen, während Menüs ausgewählt sind."
                                    showErrorDialog = true
                                } else {
                                    showReservationDateTimeDialog = true
                                }
                            },
                            //button design
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .padding(start = 8.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFA500)),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                text = "Tischreservierung",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            else {
                // Scenario 2: display list of markers sorted by the distance
                userLocation?.let {
                    val sortedMarkers = exampleMarkers.map { marker ->
                        Triple(marker.first, marker.second, calculateDistanceInKm(it.latitude, it.longitude, marker.first.latitude, marker.first.longitude))
                    }.sortedBy { it.third }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(sortedMarkers.size) { index ->
                            MarkerItem(marker = sortedMarkers[index], onClick = {
                                context.startActivity(
                                    Intent(context, SearchPageActivity::class.java).apply {
                                        putExtra("locationTitle", sortedMarkers[index].second)
                                        putExtra("infoText", "Information about ${sortedMarkers[index].second}")
                                    }
                                )
                            })
                        }
                    }
                }
            }
        }
    }

    // Error message dialog
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text(text = "Fehler") },
            text = { Text(errorMessage) },
            confirmButton = {
                Button(onClick = { showErrorDialog = false }) {
                    Text("OK")
                }
            }
        )
    }


    // Dialog to select date and time for preorder
    if (showDateTimeDialog) {
        AlertDialog(
            onDismissRequest = { showDateTimeDialog = false },
            title = { Text(text = "Vorbestellung") },
            text = {
                Column {

                    //user should choose a date
                    Text(text = "Wählen Sie ein Datum:")
                    Button(onClick = {
                        val calendar = Calendar.getInstance()
                        val datePickerDialog = DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                selectedDate = String.format("%02d.%02d.%04d", dayOfMonth, month + 1, year)
                                selectedYear = year
                                selectedMonth = month
                                selectedDay = dayOfMonth
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        )
                        datePickerDialog.datePicker.minDate = calendar.timeInMillis
                        datePickerDialog.show()
                    }) {
                        Text(text = "Datum auswählen: $selectedDate")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    //user should choose a time
                    Text(text = "Wählen Sie eine Uhrzeit:")
                    Button(onClick = {
                        val calendar = Calendar.getInstance()
                        val timePickerDialog = TimePickerDialog(
                            context,
                            { _, hourOfDay, minute ->

                                // check if the selected date and time are not in the past
                                val now = Calendar.getInstance()
                                val selectedDateTime = Calendar.getInstance().apply {
                                    set(Calendar.YEAR, selectedYear)
                                    set(Calendar.MONTH, selectedMonth)
                                    set(Calendar.DAY_OF_MONTH, selectedDay)
                                    set(Calendar.HOUR_OF_DAY, hourOfDay)
                                    set(Calendar.MINUTE, minute)
                                }

                                // otherwise show error message when a selected date or time is in the past
                                if (selectedDateTime.before(now)) {
                                    errorMessage = "Sie können keine Zeit in der Vergangenheit auswählen."
                                    showErrorDialog = true
                                } else {
                                    selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                                }
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true
                        )
                        timePickerDialog.show()
                    }) {
                        Text(text = "Uhrzeit auswählen: $selectedTime")
                    }
                }
            },
            confirmButton = {
                Button(
                    //validates date and time
                    onClick = {
                        if (selectedDate == "Heute" || selectedTime == "18:00") {
                            errorMessage = "Bitte wählen Sie ein gültiges Datum und Uhrzeit aus."
                            showErrorDialog = true
                        } else {
                            showDateTimeDialog = false
                            showOrderDialog = true
                        }
                    }
                ) {
                    //button to go to the next dialog
                    Text("Weiter")
                }
            },

            //button to close the current dialog
            dismissButton = {
                Button(onClick = { showDateTimeDialog = false }) {
                    Text("Abbrechen")
                }
            }
        )
    }

    //confirmation dialog after selecting date and time for preorder
    if (showOrderDialog) {
        AlertDialog(
            onDismissRequest = { showOrderDialog = false },
            title = { Text(text = "Vorbestellung bestätigen") },
            text = {
                Text(
                    text = "Möchten Sie Ihre Vorbestellung für den $selectedDate um $selectedTime bestätigen?",
                    fontWeight = FontWeight.Bold
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        resultMessage = "Ihre Bestellung wurde vorbestellt!"
                        resultTextColor = Color.Green
                        showOrderDialog = false
                        showResultDialog = true
                    }
                ) {
                    Text("Ja")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        resultMessage = "Ihre Bestellung wurde abgebrochen"
                        resultTextColor = Color.Red
                        showOrderDialog = false
                        showResultDialog = true
                    }
                ) {
                    Text("Nein")
                }
            }
        )
    }

    //dialog to select date and time for table reservation
    if (showReservationDateTimeDialog) {
        AlertDialog(
            onDismissRequest = { showReservationDateTimeDialog = false },
            title = { Text(text = "Tisch reservieren") },
            text = {
                Column {

                    //user should choose a date
                    Text(text = "Wählen Sie ein Datum:")
                    Button(onClick = {
                        val calendar = Calendar.getInstance()
                        val datePickerDialog = DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                selectedDate = String.format("%02d.%02d.%04d", dayOfMonth, month + 1, year)
                                selectedYear = year
                                selectedMonth = month
                                selectedDay = dayOfMonth
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        )
                        datePickerDialog.datePicker.minDate = calendar.timeInMillis
                        datePickerDialog.show()
                    }) {
                        Text(text = "Datum auswählen: $selectedDate")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    //user should choose a time
                    Text(text = "Wählen Sie eine Uhrzeit:")
                    Button(onClick = {
                        val calendar = Calendar.getInstance()
                        val timePickerDialog = TimePickerDialog(
                            context,
                            { _, hourOfDay, minute ->
                                val now = Calendar.getInstance()
                                val selectedDateTime = Calendar.getInstance().apply {
                                    set(Calendar.YEAR, selectedYear)
                                    set(Calendar.MONTH, selectedMonth)
                                    set(Calendar.DAY_OF_MONTH, selectedDay)
                                    set(Calendar.HOUR_OF_DAY, hourOfDay)
                                    set(Calendar.MINUTE, minute)
                                }

                                //if time in the past was choosen; maybe not relevant anymore because it was deactivated
                                if (selectedDateTime.before(now)) {
                                    errorMessage = "Sie können keine Zeit in der Vergangenheit auswählen."
                                    showErrorDialog = true
                                } else {
                                    selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                                }
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true
                        )
                        timePickerDialog.show()
                    }) {
                        Text(text = "Uhrzeit auswählen: $selectedTime")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Anzahl der Personen:")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = { if (numberOfPersons > 1) numberOfPersons-- }) { Text("-") }
                        Text(
                            text = "$numberOfPersons",
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Button(onClick = { if (numberOfPersons < 10) numberOfPersons++ }) { Text("+") }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (selectedDate == "Heute" || selectedTime == "18:00") {
                            errorMessage = "Bitte wählen Sie ein gültiges Datum und Uhrzeit aus."
                            showErrorDialog = true
                        } else {
                            showReservationDateTimeDialog = false
                            showConfirmReservationDialog = true
                        }
                    }
                ) {
                    Text("Weiter")
                }
            },
            dismissButton = {
                Button(onClick = { showReservationDateTimeDialog = false }) {
                    Text("Abbrechen")
                }
            }
        )
    }

    // Confirmation dialog after selecting date, time, and number of persons for table reservation
    if (showConfirmReservationDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmReservationDialog = false },
            title = { Text(text = "Bestätigung") },
            text = {
                Text(
                    text = "Möchten Sie eine Reservierung für $numberOfPersons Personen am $selectedDate um $selectedTime bestätigen?",
                    fontWeight = FontWeight.Bold
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        resultMessage = "Ihre Reservierung wurde erfolgreich bestätigt!"
                        resultTextColor = Color.Green
                        showConfirmReservationDialog = false
                        showResultDialog = true
                    }
                ) {
                    Text("Ja")
                }
            },
            dismissButton = {
                Button(onClick = { showConfirmReservationDialog = false }) {
                    Text("Nein")
                }
            }
        )
    }

    // Result message dialog after confirmation
    if (showResultDialog) {
        AlertDialog(
            onDismissRequest = { showResultDialog = false },
            title = { Text(text = "Status") },
            text = {
                Text(
                    text = resultMessage ?: "",
                    color = resultTextColor,
                    fontWeight = FontWeight.Bold
                )
            },
            confirmButton = {
                Button(
                    onClick = { showResultDialog = false }
                ) {
                    Text("Ok")
                }
            }
        )
    }
}

//displays the current distance to each local based on the suers current location
@Composable
fun MarkerItem(marker: Triple<LatLng, String, Double>, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .border(1.dp, Color.Gray)
            .padding(16.dp)
    ) {
        Text(
            text = marker.second,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Entfernung: %.2f km".format(marker.third))
    }
}

//function to create the grid with menu boxes
@Composable
fun ClickableFieldsGrid(
    selectedMenus: List<Boolean>,
    onMenuSelectionChanged: (Int, Boolean) -> Unit
) {
    val numRows = 2
    val numColumns = 4
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        for (row in 0 until numRows) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (column in 0 until numColumns) {
                    val index = row * numColumns + column
                    IndividualClickableBox(
                        index,
                        isSelected = selectedMenus[index],
                        onSelectionChanged = { isSelected ->
                            onMenuSelectionChanged(index, isSelected)
                        }
                    )
                }
            }
        }
    }
}

//single menu box which can be clicked by the user
@Composable
fun IndividualClickableBox(
    index: Int,
    isSelected: Boolean,
    onSelectionChanged: (Boolean) -> Unit

) {
    //edges color of each box if selected or not
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Color.Green else Color.Black, label = ""
    )
    Box(
        modifier = Modifier
            .size(80.dp)
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                onSelectionChanged(!isSelected)
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = rememberVectorPainter(Icons.Default.Fastfood),
                contentDescription = null
            )
            Text(text = "Menü ${index + 1}")
        }
    }
}

//displays the selectable address dropdown and updates the selected address
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LieferadresseSection(
    selectedAddress: String?,
    onAddressSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val addresses = listOf("Adresse 1", "Adresse 2", "Adresse 3")
    var selectedText by remember { mutableStateOf(selectedAddress ?: "") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = selectedText,
            onValueChange = { },
            readOnly = true,
            label = { Text("Rechnungsadresse") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            addresses.forEach { address ->
                DropdownMenuItem(
                    onClick = {
                        selectedText = address
                        onAddressSelected(address)
                        expanded = false
                    }
                ) {
                    Text(text = address)
                }
            }
        }
    }
}

//displays a dropdown to select and update payment method
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ZahlungsmethodeSection(
    selectedPaymentMethod: String?,
    onPaymentMethodSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val paymentMethods = listOf("Kreditkarte", "PayPal", "Barzahlung")
    var selectedText by remember { mutableStateOf(selectedPaymentMethod ?: "") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = selectedText,
            onValueChange = { },
            readOnly = true,
            label = { Text("Zahlungsmethode hinterlegen") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            paymentMethods.forEach { method ->
                DropdownMenuItem(
                    onClick = {
                        selectedText = method
                        onPaymentMethodSelected(method)
                        expanded = false
                    }
                ) {
                    Text(text = method)
                }
            }
        }
    }
}

