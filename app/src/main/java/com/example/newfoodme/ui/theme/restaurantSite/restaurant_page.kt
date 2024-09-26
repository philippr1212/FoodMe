package com.example.newfoodme.ui.theme.restaurantSite

// all imports which are necessary for this site
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.util.*

//get the name information for the upper bar from the sharedPreferences
class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        val initialVorname = sharedPreferences.getString("vorname", "") ?: ""
        val initialNachname = sharedPreferences.getString("nachname", "") ?: ""

        setContent {
            ProfileScreen(initialVorname, initialNachname)
        }
    }
}

@Composable
fun ProfileScreen(initialVorname: String, initialNachname: String) {
    var vorname by remember { mutableStateOf(initialVorname) } // stores the users first name
    var nachname by remember { mutableStateOf(initialNachname) } // stores the users last name

    // booleans to control if dialogs are shown or not
    var showTimeAndPersonDialog by remember { mutableStateOf(false) } //shows or hides the dialog to choose the time and number of people for "Tischreservierung"
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
    var selectedTime by remember { mutableStateOf("18:00") } // Stores the selected time for the reservation and 18:00 is the standard time which is choosen beforehand
    var selectedDate by remember { mutableStateOf("Heute") } //stores the selected date for the reservation and "heute" is the standard date which is choosen beforehand
    var numberOfPersons by remember { mutableStateOf(1) } // stores the number of people for the reservation
    var resultMessage by remember { mutableStateOf<String?>(null) } // stores the message to be shown in the result dialog
    var resultTextColor by remember { mutableStateOf(Color.Black) } // stores the color of the result text

    // selected menus
    val totalMenus = 8 // total number of available menus
    var selectedMenus by remember { mutableStateOf(List(totalMenus) { false }) } //identifies which menus are selected

    // selected date for validation
    var selectedYear by remember { mutableStateOf(0) } // stores the selected year to validate the reservation date
    var selectedMonth by remember { mutableStateOf(0) } //Stores the selected month to validate the reservation date
    var selectedDay by remember { mutableStateOf(0) } // stores the selected day to validate the reservation date

    val context = LocalContext.current

    // upper and bottom bars
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
                        text = "Guten Tag $vorname $nachname", // where the name of the user is shown
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

        bottomBar = {
            BottomNavigation(
                backgroundColor = Color(0xFFFFA500),
                elevation = 10.dp
            ) {
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Place, contentDescription = "Entdecke") },
                    label = { Text("Entdecke") },
                    selected = false,
                    onClick = {  }
                )

                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Search, contentDescription = "Suche") },
                    label = { Text("Suche") },
                    selected = false,
                    onClick = {  }
                )

                BottomNavigationItem(
                    icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Mein Profil") },
                    label = { Text("Mein Profil") },
                    selected = true,
                    onClick = {  }
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


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 80.dp),
                verticalArrangement = Arrangement.Top
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                // Restaurant name
                Text(
                    text = "Restaurant XYZ", // make this part dynamic
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))


                // Menu selection
                ClickableFieldsGrid(
                    selectedMenus = selectedMenus, //which menues are selected
                    //when user selects or deselects a menu item, this function runs
                    onMenuSelectionChanged = { index, isSelected -> //index tells us what menu was selected and isSelcted tells us if its true or false
                        selectedMenus = selectedMenus.toMutableList().also { it[index] = isSelected }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // display selected menus
                if (selectedMenus.any { it }) {

                    //standard text
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

                // billing address (dropdown); other code is below
                RechnungsadresseSection(
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                )

                //buttons
                {
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
                                showErrorDialog = true //error dialog is in this code at next
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
    }


    // error message dialog when user tries something which isnt possible
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

    // dialog to select the date and time for preorder
    if (showDateTimeDialog) {
        AlertDialog(
            onDismissRequest = { showDateTimeDialog = false },
            title = { Text(text = "Vorbestellung") },
            text = {
                Column {

                    //choose the date
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

                    //choose the time
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

    //confirmation dialog after selecting the date and time for preorder
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

            //when user confirmed the preorder by clicking "ja"
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

            //when user confirmed the preorder by clicking "nein"
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

                    //user selects date for the table reservation
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

                    //user sleects time for table reservation
                    Text(text = "Wählen Sie eine Uhrzeit:")
                    Button(onClick = {
                        val calendar = Calendar.getInstance()
                        val timePickerDialog = TimePickerDialog(
                            context,
                            { _, hourOfDay, minute ->

                                //check if the selected date and time are not in the past
                                val now = Calendar.getInstance()
                                val selectedDateTime = Calendar.getInstance().apply {
                                    set(Calendar.YEAR, selectedYear)
                                    set(Calendar.MONTH, selectedMonth)
                                    set(Calendar.DAY_OF_MONTH, selectedDay)
                                    set(Calendar.HOUR_OF_DAY, hourOfDay)
                                    set(Calendar.MINUTE, minute)
                                }

                                //if user selects a time in the past
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

                    //user can pick the number of people for the table reservation
                    Text(text = "Anzahl der Personen:")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        //min 1 person have to be choosen for the table reservation
                        Button(onClick = { if (numberOfPersons > 1) numberOfPersons-- }) { Text("-") }
                        Text(
                            text = "$numberOfPersons",
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        //max 10 persons can be choosen for the table reservation
                        Button(onClick = { if (numberOfPersons < 10) numberOfPersons++ }) { Text("+") }
                    }
                }
            },


            //confirm table reservation
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

    //confirmation dialog after the user selected the date, time, and number of persons for the table reservation
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

            //after the reservation is completed
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

    //result message dialog after confirmation of preorder and table reservation
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

//function to create the grid with menu boxes
@Composable
fun ClickableFieldsGrid(
    selectedMenus: List<Boolean>,
    onMenuSelectionChanged: (Int, Boolean) -> Unit
) {
    //grid building
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

                    //checks which box was selected and update the true/false in the index
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

    //color change of boxes when they their selected state changes
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Color.Green else Color.Black
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
fun RechnungsadresseSection( //is Rechnungsadresse now; maybe I will change that
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
