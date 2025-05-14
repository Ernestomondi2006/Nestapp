package com.ernest.nest.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ernest.nest.navigation.*
import com.ernest.nest.ui.theme.newblue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nest") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(ROUT_LOGIN) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(ROUT_SETTINGS) }) {
                        Icon(Icons.Default.Menu, contentDescription = "Settings")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = newblue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },

        bottomBar = {
            NavigationBar(containerColor = newblue) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = selectedIndex == 0,
                    onClick = {
                        selectedIndex = 0
                        navController.navigate(ROUT_HOME)
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Savings") },
                    label = { Text("Savings") },
                    selected = selectedIndex == 1,
                    onClick = {
                        selectedIndex = 1
                        navController.navigate(ROUT_SAVINGS)
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Budget") },
                    label = { Text("Budget") },
                    selected = selectedIndex == 2,
                    onClick = {
                        selectedIndex = 2
                        navController.navigate(ROUT_BUDGET)
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Call, contentDescription = "Help") },
                    label = { Text("Help") },
                    selected = selectedIndex == 3,
                    onClick = {
                        selectedIndex = 3
                        navController.navigate(ROUT_HELP) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.AccountBox, contentDescription = "Accounts") },
                    label = { Text("Accounts") },
                    selected = selectedIndex == 4,
                    onClick = {
                        selectedIndex = 4
                        navController.navigate(ROUT_ACCOUNTS)
                    }
                )
            }
        },

        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text("Welcome Back!", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))

                // Total Balance Card
                InfoCard(
                    title = "Total Balance",
                    value = "KSH150,000",
                    subtitle = "Last updated: Today",
                    backgroundColor = Color(0xFFE3F2FD)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Savings Progress Card
                ProgressCard(
                    title = "Savings Goal",
                    progress = 0.6f,
                    progressText = "KSH60,000 / KSH100,000",
                    backgroundColor = Color(0xFFE8F5E9)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Budget Progress Card
                ProgressCard(
                    title = "Monthly Budget",
                    progress = 0.75f,
                    progressText = "KSH75,000 / KSH100,000",
                    backgroundColor = Color(0xFFFFF3E0)
                )
            }
        }
    )
}

@Composable
fun InfoCard(title: String, value: String, subtitle: String, backgroundColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, fontSize = 16.sp, color = Color.Gray)
            Text(value, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text(subtitle, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun ProgressCard(title: String, progress: Float, progressText: String, backgroundColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = newblue,
                trackColor = Color.LightGray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(progressText, fontSize = 14.sp, color = Color.Gray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
