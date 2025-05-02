package com.ernest.nest.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ernest.nest.navigation.ROUT_BUDGET
import com.ernest.nest.navigation.ROUT_INTENT
import com.ernest.nest.navigation.ROUT_LOGIN
import com.ernest.nest.navigation.ROUT_REPORTS
import com.ernest.nest.navigation.ROUT_SAVINGS
import com.ernest.nest.navigation.ROUT_SETTINGS
import com.ernest.nest.ui.theme.newblue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController){
    //Scaffold

    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        //TopBar
        topBar = {
            TopAppBar(
                title = { Text("Nest") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back/nav */ navController.navigate(ROUT_LOGIN)}) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }


                },
                actions = {
                    IconButton(onClick = {navController.navigate(ROUT_SETTINGS)}) {
                        Icon(Icons.Default.Menu, contentDescription = "Settings")
                    }
                    IconButton(onClick = {navController.navigate(ROUT_REPORTS)}) {
                        Icon(Icons.Default.Notifications, contentDescription = "Settings")
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = newblue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },

        //BottomBar
        bottomBar = {
            NavigationBar(
                containerColor = newblue
            ){
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = selectedIndex == 0,
                    onClick = { selectedIndex = 0
                        //navController.navigate(ROUT_HOME)
                    }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Profile") },
                    label = { Text("Savings") },
                    selected = selectedIndex == 2,
                    onClick = { selectedIndex = 2
                          navController.navigate(ROUT_SAVINGS)
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Profile") },
                    label = { Text("Budget") },
                    selected = selectedIndex == 2,
                    onClick = { selectedIndex = 2
                         navController.navigate(ROUT_BUDGET)
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.AccountBox, contentDescription = "Profile") },
                    label = { Text("Accounts") },
                    selected = selectedIndex == 2,
                    onClick = { selectedIndex = 2
                          navController.navigate(ROUT_BUDGET)
                    }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Call, contentDescription = "Profile") },
                    label = { Text("Help") },
                    selected = selectedIndex == 2,
                    onClick = { selectedIndex = 2
                        navController.navigate(ROUT_INTENT)
                    }
                )


            }
        },

        //FloatingActionButton
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Add action */ },
                containerColor = newblue
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },

        //Content
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {


                //Main Contents of the page
                Text(text = "Welcome to home screen", fontSize = 20.sp)
                Spacer(modifier = Modifier.height(8.dp))













            }
        }
    )

    //End of scaffold


}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    HomeScreen(navController= rememberNavController())
}