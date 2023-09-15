package com.example.anew

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.util.Log

data class President(val name: String, val startYear: Int, val endYear: Int, val description: String)

object DataProvider {
    val presidents: MutableList<President> = ArrayList()

    init {
        Log.d("USR", "This ($this) is a singleton")

        // construct the data source
        presidents.apply {
            add(President("Kaarlo Stahlberg", 1919, 1925, "Eka presidentti"))
            add(President("Lauri Relander", 1925, 1931, "Toka presidentti"))
            // ... add other presidents ...
            add(President("Sauli Niinistö", 2012, 2024, "Ensimmäisen koiran, Oskun, omistaja"))
        }
    }
}

@Composable
fun PresidentList(presidents: List<President>, onPresidentSelected: (President) -> Unit) {
    LazyColumn {
        items(presidents) { president ->
            PresidentListItem(president, onPresidentSelected)
        }
    }
}

@Composable
fun PresidentListItem(president: President, onPresidentSelected: (President) -> Unit) {
    Box(
        modifier = Modifier
            .clickable { onPresidentSelected(president) }
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(president.name)
    }
}

@Composable
fun PresidentDetail(president: President) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = president.name, style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(4.dp))
        Text("From ${president.startYear} to ${president.endYear}")
        Spacer(modifier = Modifier.height(4.dp))
        Text(president.description)
    }
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var selectedPresident by remember { mutableStateOf<President?>(null) }

            MaterialTheme {
                if (selectedPresident == null) {
                    PresidentList(DataProvider.presidents) { president ->
                        selectedPresident = president
                    }
                } else {
                    selectedPresident?.let {
                        PresidentDetail(it)
                    }
                }
            }
        }
    }
}
