package com.ernest.nest.ui.screens.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ernest.nest.navigation.ROUT_HOME
import com.ernest.nest.navigation.ROUT_ADD_TRANSACTION
import com.ernest.nest.ui.theme.newblue

data class TransactionItem(
    val title: String,
    val date: String,
    val amount: String,
    val isExpense: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsScreen(navController: NavController) {
    val transactions = listOf(
        TransactionItem("Grocery Shopping", "May 1, 2025", "-KSH12,000", true),
        TransactionItem("Salary", "Apr 30, 2025", "+KSH150,000", false),
        TransactionItem("Electricity Bill", "Apr 28, 2025", "-KSH8,000", true),
        TransactionItem("Freelance Payment", "Apr 25, 2025", "+KSH35,000", false)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transactions") },
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
                onClick = { navController.navigate(ROUT_ADD_TRANSACTION) },
                containerColor = newblue,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Transaction")
            }
        },
        content = { padding ->
            LazyColumn(
                contentPadding = padding,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(transactions) { transaction ->
                    TransactionCard(transaction)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    )
}

@Composable
fun TransactionCard(transaction: TransactionItem) {
    val amountColor = if (transaction.isExpense) Color.Red else Color(0xFF388E3C)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(transaction.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(transaction.date, fontSize = 14.sp, color = Color.Gray)
            }
            Text(
                text = transaction.amount,
                color = amountColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionScreenPreview() {
    TransactionsScreen(navController = rememberNavController())
}
