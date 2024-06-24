package com.fekent.medimate.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fekent.medimate.ui.theme.MediMateTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AddMedsScreen(back: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var dose by remember { mutableStateOf("") }
    var pillCount by remember { mutableStateOf("") }
    var refill by remember { mutableStateOf("") }


    Column(modifier = Modifier.fillMaxSize()) {
        AddMedsBar{ back() }
        Spacer(Modifier.size(24.dp))
        TextField(value = name, onValueChange = {name = it})
        TextField(value = dose, onValueChange = {dose = it})
        TextField(value = pillCount, onValueChange = {pillCount = it})





    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedsBar(back: () -> Unit) {
    CenterAlignedTopAppBar(title = {
        Text(
            text = "Add Meds",
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.primary
        )
    }, navigationIcon = {
        IconButton(onClick = { back() }) {
            Icon(
                Icons.Filled.KeyboardArrowLeft,
                "Back",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(60.dp)
            )
        }
    }, modifier = Modifier
        .padding(4.dp)
        .shadow(elevation = 5.dp, spotColor = Color.DarkGray, shape = RoundedCornerShape(10.dp))
    )
}


@Preview(showSystemUi = true)
@Composable
fun AddMedsPreview() {
    MediMateTheme {
        AddMedsScreen {}
    }
}