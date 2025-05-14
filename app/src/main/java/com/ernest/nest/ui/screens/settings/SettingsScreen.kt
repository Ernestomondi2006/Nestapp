package com.ernest.nest.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
fun SettingsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(ROUT_HOME) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = newblue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Account", fontWeight = FontWeight.Bold, fontSize = 18.sp)

                SettingsItem(icon = Icons.Default.Person, title = "Profile", onClick = { /* Navigate to profile */ })
                SettingsItem(icon = Icons.Default.Lock, title = "Change Password", onClick = { /* Handle */ })

                Spacer(modifier = Modifier.height(16.dp))

                Text("Preferences", fontWeight = FontWeight.Bold, fontSize = 18.sp)

                SettingsItem(icon = Icons.Default.Notifications, title = "Notifications", onClick = { /* Handle */ })
                SettingsItem(icon = Icons.Default.Lock, title = "Language", onClick = { /* Handle */ })

                Spacer(modifier = Modifier.height(16.dp))

                Text("Others", fontWeight = FontWeight.Bold, fontSize = 18.sp)

                SettingsItem(icon = Icons.Default.Info, title = "About App", onClick = { /* Handle */ })
                SettingsItem(icon = Icons.Default.ExitToApp, title = "Logout", onClick = { /* Logout */ })
            }
        }
    )
}

@Composable
fun SettingsItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 2.dp,
        color = Color(0xFFF9F9F9)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = newblue,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(title, fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(rememberNavController())
}
