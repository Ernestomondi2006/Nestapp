package com.ernest.nest.ui.screens.reports

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ernest.nest.navigation.ROUT_HOME
import com.ernest.nest.ui.theme.newblue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(navController: NavController){
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Reports") },

            navigationIcon = {
                IconButton(onClick = { /* Handle back/nav */ navController.navigate(ROUT_HOME) }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = newblue,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White
            )
        )


    }
}

@Preview(showBackground = true)
@Composable
fun ReportsScreenPreview(){
    ReportsScreen(rememberNavController())

}