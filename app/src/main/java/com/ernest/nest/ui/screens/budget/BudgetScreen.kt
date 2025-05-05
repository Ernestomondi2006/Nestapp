package com.ernest.nest.ui.screens.budget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ernest.nest.ui.theme.newblue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(navController: NavController){
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Budget") },

            navigationIcon = {
                IconButton(onClick = { /* Handle back/nav */ }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = newblue,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White
            )
        )
        Text("Monthly Budget",fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("KSH 200000 ",fontSize = 35.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))






            }


}

@Preview(showBackground = true)
@Composable
fun BudgetScreenPreview(){
    BudgetScreen(rememberNavController())

}