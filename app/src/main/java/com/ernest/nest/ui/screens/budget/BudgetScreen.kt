package com.ernest.nest.ui.screens.budget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.ernest.nest.navigation.ROUT_HOME
import com.ernest.nest.ui.theme.newblue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(navController: NavController){
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Budget") },

            navigationIcon = {
                IconButton(onClick = { navController.navigate(ROUT_HOME) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = newblue,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White
            )
        )
       //MAINCONTENTS





            }


}

@Preview(showBackground = true)
@Composable
fun BudgetScreenPreview(){
    BudgetScreen(rememberNavController())

}