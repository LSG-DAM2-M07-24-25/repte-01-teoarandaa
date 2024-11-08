package com.example.androidstudio_koala_template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidstudio_koala_template.ui.theme.AndroidStudioKoalaTemplateTheme
import java.time.format.TextStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidStudioKoalaTemplateTheme {
                var selectedIcon by remember { mutableStateOf("Email") }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Text(
                            text = "Repte 01",
                            style = androidx.compose.ui.text.TextStyle(
                                fontWeight = FontWeight.Bold,
                                color = Color.Blue,
                                fontSize = 24.sp
                            ),
                            modifier = Modifier
                                .padding(36.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        MyDropDownMenu(selectedIcon = selectedIcon, onIconSelected = { selectedIcon = it })
                        MyRangeSlider(selectedIcon = selectedIcon)
                    }
                }
            }
        }
    }
}

@Composable
fun MyDropDownMenu(selectedIcon: String, onIconSelected: (String) -> Unit) {
    var expanded: Boolean by remember { mutableStateOf(false) }
    val iconsList = listOf("Add", "Call", "Email", "Home", "Favourite", "Person", "Search", "ShoppingCart", "Notifications", "Delete")

    Column(
        modifier = Modifier
            .padding(20.dp, 80.dp)
    ) {
        OutlinedTextField(
            value = selectedIcon,
            onValueChange = {},
            enabled = false,
            readOnly = true,
            placeholder = { Text(text = "Select an Icon") },
            modifier = Modifier
                .clickable { expanded = true }
                .background(Color.LightGray)
                .fillMaxWidth()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Black, RoundedCornerShape(4.dp))
        ) {
            iconsList.forEach { icon ->
                DropdownMenuItem(
                    text = { Text(text = icon) },
                    onClick = {
                        expanded = false
                        onIconSelected(icon)
                    }
                )
            }
        }
    }
}

@Composable
fun MyRangeSlider(selectedIcon: String) {
    var minValueText by remember { mutableStateOf("0") }
    var maxValueText by remember { mutableStateOf("10") }
    var sliderValue by remember { mutableFloatStateOf(5f) }
    var finalSelectedIcon by remember { mutableStateOf("Email") }
    var finalSliderValue by remember { mutableFloatStateOf(5f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                value = minValueText,
                onValueChange = { minValueText = it },
                label = { Text("Min") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = maxValueText,
                onValueChange = { maxValueText = it },
                label = { Text("Max") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        val minValue = minValueText.toFloatOrNull() ?: 0f
        val maxValue = maxValueText.toFloatOrNull() ?: 10f

        Slider(
            value = sliderValue,
            onValueChange = { sliderValue = it },
            valueRange = minValue..maxValue,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Valor actual: ${"%.1f".format(sliderValue)}")

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                finalSelectedIcon = selectedIcon
                finalSliderValue = sliderValue
            }
        ) {
            Text("Enviar")
        }

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(24.dp))

        if (finalSelectedIcon.isNotEmpty()) {
            MySelectedIcon(finalSelectedIcon, finalSliderValue)
        }
    }
}

@Composable
fun MySelectedIcon(selectedIcon: String, sliderValue: Float) {
    val icon = when (selectedIcon) {
        "Add" -> Icons.Default.Add
        "Call" -> Icons.Default.Call
        "Email" -> Icons.Default.Email
        "Home" -> Icons.Default.Home
        "Favourite" -> Icons.Default.Favorite
        "Person" -> Icons.Default.Person
        "Search" -> Icons.Default.Search
        "ShoppingCart" -> Icons.Default.ShoppingCart
        "Notifications" -> Icons.Default.Notifications
        "Delete" -> Icons.Default.Delete
        else -> Icons.Default.Email
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BadgedBox(
            modifier = Modifier.padding(16.dp),
            badge = { Badge { Text("${"%.1f".format(sliderValue)}") } }
        ) {
            Icon(
                icon,
                contentDescription = selectedIcon,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Valor: ${"%.1f".format(sliderValue)}",
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidStudioKoalaTemplateTheme {
        var selectedIcon by remember { mutableStateOf("Email") }

        MyRangeSlider(selectedIcon = selectedIcon)
    }
}