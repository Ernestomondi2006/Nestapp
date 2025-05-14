
package com.ernest.nest.ui.screens.accounts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ernest.nest.navigation.ROUT_HOME
import com.ernest.nest.ui.theme.newblue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Accounts") },
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Navigate to add account screen */ },
                containerColor = newblue
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Account", tint = Color.White)
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AccountSummary(totalBalance = 385000f)

                Text(
                    text = "Linked Accounts",
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.titleMedium
                )

                AccountItem(name = "GTBank", balance = 150000f)
                AccountItem(name = "Opay Wallet", balance = 55000f)
                AccountItem(name = "UBA Savings", balance = 180000f)
            }
        }
    )
}

@Composable
fun AccountSummary(totalBalance: Float) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFE0F7FA),
        tonalElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Total Balance", fontSize = 16.sp, color = Color.Gray)
            Text("KSH${totalBalance.toInt()}", fontSize = 24.sp, color = Color.Black, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
        }
    }
}

@Composable
fun AccountItem(name: String, balance: Float) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF5F5F5),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Navigate to account details */ }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(name, fontSize = 16.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold)
            Text("KSH${balance.toInt()}", fontSize = 16.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Medium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccountsScreenPreview() {
    AccountsScreen(rememberNavController())
}
